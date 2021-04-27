package fr.finanting.server.service.implementation;

import org.springframework.stereotype.Service;

import fr.finanting.server.dto.BankingTransactionDTO;
import fr.finanting.server.exception.BadAssociationBankingTransactionBankingAccountException;
import fr.finanting.server.exception.BankingAccountNotExistException;
import fr.finanting.server.exception.CategoryNoUserException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.ClassificationNoUserException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.ThirdNoUserException;
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

    private void checkAssociationAccountTransaction(User user, BankingAccount bankingAccount)
        throws BadAssociationBankingTransactionBankingAccountException, UserNotInGroupException{

        if(bankingAccount.getGroup() == null){
            if(!bankingAccount.getUser().getId().equals(user.getId())){
                throw new BadAssociationBankingTransactionBankingAccountException();
            }
        } else {
            bankingAccount.getGroup().checkAreInGroup(user);
        }

    }

    private BankingTransaction createMirrorTransaction(final BankingTransaction bankingTransaction){
        BankingTransaction mirrorBankingTransaction = new BankingTransaction();

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

        mirrorBankingTransaction = this.bankingTransactionRepository.save(mirrorBankingTransaction);

        return mirrorBankingTransaction;
    }

    private void setBankingTransactionData(final BankingTransaction bankingTransaction, final CreateBankingTransactionParameter createBankingTransactionParameter, final User user)
        throws ThirdNotExistException, ThirdNoUserException, UserNotInGroupException, CategoryNotExistException, CategoryNoUserException,
        ClassificationNotExistException, ClassificationNoUserException, CurrencyNotExistException{

        Integer thirdId = createBankingTransactionParameter.getThirdId();

        if(thirdId != null){
            Third third = this.thirdRepository.findById(thirdId)
                .orElseThrow(() -> new ThirdNotExistException(thirdId));

            third.checkIfUsable(user);

            bankingTransaction.setThird(third);
            
        }

        Integer categoryId = createBankingTransactionParameter.getCategoryId();

        if(categoryId != null){
            Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotExistException(categoryId));

            category.checkIfUsable(user);

            bankingTransaction.setCategory(category);
        }

        Integer classificationId = createBankingTransactionParameter.getClassificationId();

        if(classificationId != null){
            Classification classification = this.classificationRepository.findById(classificationId)
                .orElseThrow(() -> new ClassificationNotExistException(classificationId));

            classification.checkIfUsable(user);

            bankingTransaction.setClassification(classification);
        }

        Integer currencyId = createBankingTransactionParameter.getCurrencyId();
        Currency currency = this.currencyRepository.findById(currencyId)
            .orElseThrow(() -> new CurrencyNotExistException(currencyId));
        bankingTransaction.setCurrency(currency);

        bankingTransaction.setAmountDate(createBankingTransactionParameter.getAmountDate());
        bankingTransaction.setTransactionDate(createBankingTransactionParameter.getTransactionDate());

        bankingTransaction.setAmount(createBankingTransactionParameter.getAmount());
        bankingTransaction.setCurrencyAmount(createBankingTransactionParameter.getCurrencyAmount());

        bankingTransaction.setDescription(createBankingTransactionParameter.getDescription());

    }

    @Override
    public BankingTransactionDTO createBankingTransaction(final CreateBankingTransactionParameter createBankingTransactionParameter, String userName)
        throws BankingAccountNotExistException, BadAssociationBankingTransactionBankingAccountException,
        UserNotInGroupException, ThirdNotExistException, ThirdNoUserException, CategoryNotExistException, CategoryNoUserException,
        ClassificationNotExistException, ClassificationNoUserException, CurrencyNotExistException {

        User user = this.userRepository.findByUserName(userName).orElseThrow();

        BankingTransaction bankingTransaction = new BankingTransaction();

        Integer accountId = createBankingTransactionParameter.getAccountId();
        BankingAccount sourceAccount = this.bankingAccountRepository.findById(accountId)
            .orElseThrow(() -> new BankingAccountNotExistException(accountId));
        this.checkAssociationAccountTransaction(user, sourceAccount);
        bankingTransaction.setAccount(sourceAccount);

        this.setBankingTransactionData(bankingTransaction, createBankingTransactionParameter, user);


        if(createBankingTransactionParameter.getLinkedAccountId() != null){
            Integer linkedAccountId = createBankingTransactionParameter.getLinkedAccountId();
            BankingAccount linkedAccount = this.bankingAccountRepository.findById(linkedAccountId)
                .orElseThrow(() -> new BankingAccountNotExistException(linkedAccountId));
            this.checkAssociationAccountTransaction(user, linkedAccount);
            bankingTransaction.setLinkedAccount(linkedAccount);

            BankingTransaction mirrorBankingTransaction = this.createMirrorTransaction(bankingTransaction);
            bankingTransaction.setMirrorTransaction(mirrorBankingTransaction);
        }

        bankingTransaction = this.bankingTransactionRepository.save(bankingTransaction);

        BankingTransactionDTO bankingTransactionDTO = new BankingTransactionDTO(bankingTransaction);

        return bankingTransactionDTO;
        
    }
    
}
