package fr.finanting.server.service.implementation;

import org.springframework.stereotype.Service;

import fr.finanting.server.dto.BankingTransactionDTO;
import fr.finanting.server.exception.BadAssociationElementException;
import fr.finanting.server.exception.BankingAccountNotExistException;
import fr.finanting.server.exception.BankingTransactionNotExistException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.ThirdNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.BankingTransaction;
import fr.finanting.server.model.Category;
import fr.finanting.server.model.Classification;
import fr.finanting.server.model.Currency;
import fr.finanting.server.model.Third;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.CreateBankingTransactionParameter;
import fr.finanting.server.parameter.UpdateBankingTransactionParameter;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.BankingTransactionRepository;
import fr.finanting.server.repository.CategoryRepository;
import fr.finanting.server.repository.ClassificationRepository;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.repository.ThirdRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.BankingTransactionService;

@Service
public class BankingTransactionServiceImpl implements BankingTransactionService {

    private BankingTransactionRepository bankingTransactionRepository;
    private BankingAccountRepository bankingAccountRepository;
    private ThirdRepository thirdRepository;
    private CategoryRepository categoryRepository;
    private ClassificationRepository classificationRepository;
    private CurrencyRepository currencyRepository;
    private UserRepository userRepository;

    public BankingTransactionServiceImpl(final BankingTransactionRepository bankingTransactionRepository,
                                            final BankingAccountRepository bankingAccountRepository,
                                            final ThirdRepository thirdRepository,
                                            final CategoryRepository categoryRepository,
                                            final ClassificationRepository classificationRepository,
                                            final CurrencyRepository currencyRepository,
                                            final UserRepository userRepository){
        this.bankingTransactionRepository = bankingTransactionRepository;
        this.bankingAccountRepository = bankingAccountRepository;
        this.thirdRepository = thirdRepository;
        this.categoryRepository = categoryRepository;
        this.classificationRepository = classificationRepository;
        this.currencyRepository = currencyRepository;
        this.userRepository = userRepository;
    }

    private void setMirrorTransactionData(final BankingTransaction mirrorBankingTransaction, final BankingTransaction bankingTransaction){
        mirrorBankingTransaction.setAccount(bankingTransaction.getLinkedAccount());
        mirrorBankingTransaction.setLinkedAccount(bankingTransaction.getAccount());

        mirrorBankingTransaction.setThird(bankingTransaction.getThird());
        mirrorBankingTransaction.setCategory(bankingTransaction.getCategory());
        mirrorBankingTransaction.setClassification(bankingTransaction.getClassification());
        mirrorBankingTransaction.setCurrency(bankingTransaction.getCurrency());

        mirrorBankingTransaction.setDescription(bankingTransaction.getDescription());

        mirrorBankingTransaction.setAmountDate(bankingTransaction.getAmountDate());
        mirrorBankingTransaction.setTransactionDate(bankingTransaction.getTransactionDate());

        Double currencyAmount = bankingTransaction.getCurrencyAmount() * -1;
        mirrorBankingTransaction.setCurrencyAmount(currencyAmount);

        Double amount;

        if(!bankingTransaction.getLinkedAccount().getDefaultCurrency().equals(bankingTransaction.getAccount().getDefaultCurrency())){
            //All currency have a rate to convert to the default currency of the application
            //we used the curencyAmount because this is the value that the user paid
            amount = currencyAmount
                * Double.valueOf(bankingTransaction.getAccount().getDefaultCurrency().getRate())
                / Double.valueOf(bankingTransaction.getLinkedAccount().getDefaultCurrency().getRate());
        } else {
            amount = bankingTransaction.getAmount() * -1;
        }

        mirrorBankingTransaction.setAmount(amount);

        mirrorBankingTransaction.setCurrencyAmount(currencyAmount);
    }

    private void setBankingTransactionData(final BankingTransaction bankingTransaction, final CreateBankingTransactionParameter bankingTransactionParameter, final User user)
        throws ThirdNotExistException, BadAssociationElementException, UserNotInGroupException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException {

        Integer thirdId = bankingTransactionParameter.getThirdId();

        if(thirdId != null){
            Third third = this.thirdRepository.findById(thirdId)
                .orElseThrow(() -> new ThirdNotExistException(thirdId));

            third.checkIfUsable(user);
            bankingTransaction.getAccount().checkIfCanAssociated(third);
            bankingTransaction.setThird(third);
        }

        Integer categoryId = bankingTransactionParameter.getCategoryId();

        if(categoryId != null){
            Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotExistException(categoryId));

            category.checkIfUsable(user);
            bankingTransaction.getAccount().checkIfCanAssociated(category);
            bankingTransaction.setCategory(category);
        }

        Integer classificationId = bankingTransactionParameter.getClassificationId();

        if(classificationId != null){
            Classification classification = this.classificationRepository.findById(classificationId)
                .orElseThrow(() -> new ClassificationNotExistException(classificationId));

            classification.checkIfUsable(user);
            bankingTransaction.getAccount().checkIfCanAssociated(classification);
            bankingTransaction.setClassification(classification);
        }

        Integer currencyId = bankingTransactionParameter.getCurrencyId();
        Currency currency = this.currencyRepository.findById(currencyId)
            .orElseThrow(() -> new CurrencyNotExistException(currencyId));
        bankingTransaction.setCurrency(currency);

        bankingTransaction.setAmountDate(bankingTransactionParameter.getAmountDate());
        bankingTransaction.setTransactionDate(bankingTransactionParameter.getTransactionDate());

        bankingTransaction.setAmount(bankingTransactionParameter.getAmount());
        bankingTransaction.setCurrencyAmount(bankingTransactionParameter.getCurrencyAmount());

        bankingTransaction.setDescription(bankingTransactionParameter.getDescription());

    }

    @Override
    public BankingTransactionDTO createBankingTransaction(final CreateBankingTransactionParameter createBankingTransactionParameter, final String userName)
        throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException,
        ClassificationNotExistException, CurrencyNotExistException {

        User user = this.userRepository.findByUserName(userName).orElseThrow();

        BankingTransaction bankingTransaction = new BankingTransaction();

        Integer accountId = createBankingTransactionParameter.getAccountId();
        BankingAccount account = this.bankingAccountRepository.findById(accountId)
            .orElseThrow(() -> new BankingAccountNotExistException(accountId));

        account.checkIfUsable(user);
        bankingTransaction.setAccount(account);

        this.setBankingTransactionData(bankingTransaction, createBankingTransactionParameter, user);

        if(createBankingTransactionParameter.getLinkedAccountId() != null){
            Integer linkedAccountId = createBankingTransactionParameter.getLinkedAccountId();
            BankingAccount linkedAccount = this.bankingAccountRepository.findById(linkedAccountId)
                .orElseThrow(() -> new BankingAccountNotExistException(linkedAccountId));
            
            linkedAccount.checkIfUsable(user);
            account.checkIfCanAssociated(linkedAccount);
            
            bankingTransaction.setLinkedAccount(linkedAccount);

            BankingTransaction mirrorBankingTransaction = new BankingTransaction();
            this.setMirrorTransactionData(mirrorBankingTransaction, bankingTransaction);
            mirrorBankingTransaction = this.bankingTransactionRepository.save(mirrorBankingTransaction);

            mirrorBankingTransaction.setMirrorTransaction(bankingTransaction);
            bankingTransaction.setMirrorTransaction(mirrorBankingTransaction);
        }

        bankingTransaction = this.bankingTransactionRepository.save(bankingTransaction);

        BankingTransactionDTO bankingTransactionDTO = new BankingTransactionDTO(bankingTransaction);

        return bankingTransactionDTO;
        
    }

    
    public BankingTransactionDTO updateBankingTransaction(final UpdateBankingTransactionParameter updateBankingTransactionParameter, final String userName)
        throws BankingTransactionNotExistException, BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException,
        ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{

        User user = this.userRepository.findByUserName(userName).orElseThrow();

        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(updateBankingTransactionParameter.getId())
            .orElseThrow(() -> new BankingTransactionNotExistException(updateBankingTransactionParameter.getId()));

        BankingAccount currentAccount = bankingTransaction.getAccount();
        BankingAccount currentLinkedAccount = bankingTransaction.getLinkedAccount();

        if(!currentAccount.getId().equals(updateBankingTransactionParameter.getAccountId())){
            BankingAccount newAccount = this.bankingAccountRepository.findById(updateBankingTransactionParameter.getAccountId())
                .orElseThrow(() -> new BankingAccountNotExistException(updateBankingTransactionParameter.getAccountId()));

            newAccount.checkIfUsable(user);
            bankingTransaction.setAccount(newAccount);
        }

        this.setBankingTransactionData(bankingTransaction, updateBankingTransactionParameter, user);

        if(updateBankingTransactionParameter.getLinkedAccountId() == null){
            if(currentLinkedAccount != null){
                BankingTransaction mirrorBankingTransaction = bankingTransaction.getMirrorTransaction();
                bankingTransaction.setLinkedAccount(null);
                bankingTransaction.setMirrorTransaction(null);
                this.bankingTransactionRepository.delete(mirrorBankingTransaction);
            }
        } else {
            BankingAccount newLinekdAccount = this.bankingAccountRepository.findById(updateBankingTransactionParameter.getLinkedAccountId())
                    .orElseThrow(() -> new BankingAccountNotExistException(updateBankingTransactionParameter.getLinkedAccountId()));

            newLinekdAccount.checkIfUsable(user);
            bankingTransaction.getAccount().checkIfCanAssociated(newLinekdAccount);
            bankingTransaction.setLinkedAccount(newLinekdAccount);

            BankingTransaction mirrorBankingTransaction;

            if(bankingTransaction.getMirrorTransaction() == null){
                mirrorBankingTransaction = new BankingTransaction();
                
            } else {
                mirrorBankingTransaction = bankingTransaction.getMirrorTransaction();
            }

            this.setMirrorTransactionData(mirrorBankingTransaction, bankingTransaction);
                bankingTransaction.setMirrorTransaction(mirrorBankingTransaction);
        }

        /*
        BankingAccount currentAccount = bankingTransaction.getAccount();
        BankingAccount currentLinkedAccount = bankingTransaction.getLinkedAccount();

        boolean needUpdateAccount = !currentAccount.getId().equals(updateBankingTransactionParameter.getAccountId());
        boolean needUpdateLinkedAccount = false;
        boolean needDeleteLinkedAccount = false;

        if(linkedAccount != null){
            needUpdateLinkedAccount = !linkedAccount.getId().equals(updateBankingTransactionParameter.getLinkedAccountId());
            needDeleteLinkedAccount = updateBankingTransactionParameter.getLinkedAccountId() == null;
        } else {
            needUpdateLinkedAccount = updateBankingTransactionParameter.getLinkedAccountId() != null;
        }

        BankingAccount newAccount = null;
        BankingAccount newLinkedAccount = null;

        if(needUpdateAccount && needUpdateLinkedAccount && !needDeleteLinkedAccount){
            newAccount = this.bankingAccountRepository.findById(updateBankingTransactionParameter.getAccountId())
                .orElseThrow(() -> new BankingAccountNotExistException(updateBankingTransactionParameter.getAccountId()));
            newAccount.checkIfUsable(user);

            bankingTransaction.setAccount(newAccount);

            newLinkedAccount = this.bankingAccountRepository.findById(updateBankingTransactionParameter.getLinkedAccountId())
                .orElseThrow(() -> new BankingAccountNotExistException(updateBankingTransactionParameter.getLinkedAccountId()));
            newLinkedAccount.checkIfUsable(user);

            newAccount.checkIfCanAssociated(newLinkedAccount);

            bankingTransaction.setLinkedAccount(newLinkedAccount);

        }
        
        if(needUpdateAccount) {
            newAccount = this.bankingAccountRepository.findById(updateBankingTransactionParameter.getAccountId())
                .orElseThrow(() -> new BankingAccountNotExistException(updateBankingTransactionParameter.getAccountId()));
            newAccount.checkIfUsable(user);

            bankingTransaction.setAccount(newAccount);
        } 
        
        if(needUpdateLinkedAccount && !needDeleteLinkedAccount){
            newLinkedAccount = this.bankingAccountRepository.findById(updateBankingTransactionParameter.getLinkedAccountId())
                .orElseThrow(() -> new BankingAccountNotExistException(updateBankingTransactionParameter.getLinkedAccountId()));
            newLinkedAccount.checkIfUsable(user);

            bankingTransaction.setLinkedAccount(newLinkedAccount);
        } else if(needDeleteLinkedAccount){
            bankingTransaction.setLinkedAccount(null);
        }

        currentAccount = bankingTransaction.getAccount();
        linkedAccount = bankingTransaction.getLinkedAccount();

        if(currentAccount != null && linkedAccount != null){
            currentAccount.checkIfCanAssociated(linkedAccount);
        }

        this.setBankingTransactionData(bankingTransaction, updateBankingTransactionParameter, user);

        if((currentAccount != null && linkedAccount != null) && !needDeleteLinkedAccount){

            BankingTransaction mirrorBankingTransaction;

            if(bankingTransaction.getMirrorTransaction() != null){
                mirrorBankingTransaction = bankingTransaction.getMirrorTransaction();
            } else {
                mirrorBankingTransaction = new BankingTransaction();
            }

            this.setMirrorTransactionDate(mirrorBankingTransaction, bankingTransaction);
            mirrorBankingTransaction = this.bankingTransactionRepository.save(mirrorBankingTransaction);
            bankingTransaction.setMirrorTransaction(mirrorBankingTransaction);

        } else if (needDeleteLinkedAccount) {
            BankingTransaction mirrorBankingTransaction = bankingTransaction.getMirrorTransaction();
            bankingTransaction.setMirrorTransaction(null);
            this.bankingTransactionRepository.delete(mirrorBankingTransaction);
        }
        */

        BankingTransactionDTO bankingTransactionDTO = new BankingTransactionDTO(bankingTransaction);

        return bankingTransactionDTO;

    }

    
}
