package fr.finanting.server.service.bankingtransactionservice;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
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
import fr.finanting.server.model.Group;
import fr.finanting.server.model.Third;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.UpdateBankingTransactionParameter;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.BankingTransactionRepository;
import fr.finanting.server.repository.CategoryRepository;
import fr.finanting.server.repository.ClassificationRepository;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.ThirdRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.BankingTransactionServiceImpl;

public class TestUpdateBankingTransaction extends AbstractMotherIntegrationTest {

    @Autowired
    private BankingTransactionRepository bankingTransactionRepository;
    
    @Autowired
    private BankingAccountRepository bankingAccountRepository;

    @Autowired
    private ThirdRepository thirdRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ClassificationRepository classificationRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private UserRepository userRepository;

    private BankingTransactionServiceImpl bankingTransactionServiceImpl;

    private UpdateBankingTransactionParameter userUpdateBankingTransactionParameter;
    private UpdateBankingTransactionParameter groupUpdateBankingTransactionParameter;

    private User user;
    private Group group;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingTransactionServiceImpl = new BankingTransactionServiceImpl(this.bankingTransactionRepository,
                                                                                this.bankingAccountRepository,
                                                                                this.thirdRepository,
                                                                                this.categoryRepository,
                                                                                this.classificationRepository,
                                                                                this.currencyRepository,
                                                                                this.userRepository);

        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(user);

        this.userUpdateBankingTransactionParameter = new UpdateBankingTransactionParameter();
        this.groupUpdateBankingTransactionParameter = new UpdateBankingTransactionParameter();

        BankingAccount newUserBankingAccount = this.testFactory.getBankingAccount(this.user);
        BankingAccount newOtherUserBankingAccount = this.testFactory.getBankingAccount(this.user);
        BankingAccount newGroupBankingAccount = this.testFactory.getBankingAccount(this.group);
        BankingAccount newOtherGroupBankingAccount = this.testFactory.getBankingAccount(this.group);


        Double amount = this.testFactory.getRandomDouble();
        Double currencyAmount = newUserBankingAccount.getDefaultCurrency().getRate() * amount;

        this.userUpdateBankingTransactionParameter.setAccountId(newUserBankingAccount.getId());
        this.userUpdateBankingTransactionParameter.setLinkedAccountId(newOtherUserBankingAccount.getId());
        this.userUpdateBankingTransactionParameter.setThirdId(this.testFactory.getThird(this.user).getId());
        this.userUpdateBankingTransactionParameter.setCategoryId(this.testFactory.getCategory(this.user, true).getId());
        this.userUpdateBankingTransactionParameter.setClassificationId(this.testFactory.getClassification(this.user).getId());
        this.userUpdateBankingTransactionParameter.setTransactionDate(new Date());
        this.userUpdateBankingTransactionParameter.setAmountDate(new Date());
        this.userUpdateBankingTransactionParameter.setAmount(amount);
        this.userUpdateBankingTransactionParameter.setCurrencyAmount(currencyAmount);
        this.userUpdateBankingTransactionParameter.setCurrencyId(this.testFactory.getCurrency().getId());

        this.groupUpdateBankingTransactionParameter.setAccountId(newGroupBankingAccount.getId());
        this.groupUpdateBankingTransactionParameter.setLinkedAccountId(newOtherGroupBankingAccount.getId());
        this.groupUpdateBankingTransactionParameter.setThirdId(this.testFactory.getThird(this.group).getId());
        this.groupUpdateBankingTransactionParameter.setCategoryId(this.testFactory.getCategory(this.group, true).getId());
        this.groupUpdateBankingTransactionParameter.setClassificationId(this.testFactory.getClassification(this.group).getId());
        this.groupUpdateBankingTransactionParameter.setTransactionDate(new Date());
        this.groupUpdateBankingTransactionParameter.setAmountDate(new Date());
        this.groupUpdateBankingTransactionParameter.setAmount(amount);
        this.groupUpdateBankingTransactionParameter.setCurrencyAmount(currencyAmount);
        this.groupUpdateBankingTransactionParameter.setCurrencyId(this.testFactory.getCurrency().getId());

    }

    private void checkData(final BankingTransactionDTO bankingTransactionDTO, final BankingTransaction bankingTransaction, final UpdateBankingTransactionParameter parameter){
        
        Assertions.assertEquals(parameter.getAccountId(), bankingTransactionDTO.getBankingAccountDTO().getId());
        Assertions.assertEquals(parameter.getAccountId(), bankingTransaction.getAccount().getId());

        if(parameter.getLinkedAccountId() != null){
            Assertions.assertEquals(parameter.getLinkedAccountId(), bankingTransactionDTO.getLinkedBankingAccountDTO().getId());
            Assertions.assertEquals(parameter.getLinkedAccountId(), bankingTransaction.getLinkedAccount().getId());

            BankingTransaction mirrorTransaction = bankingTransaction.getMirrorTransaction();
            Assertions.assertEquals(parameter.getAccountId(), mirrorTransaction.getLinkedAccount().getId());
            Assertions.assertEquals(parameter.getLinkedAccountId(), mirrorTransaction.getAccount().getId());

            Assertions.assertEquals(bankingTransaction.getAccount().getId(), mirrorTransaction.getLinkedAccount().getId());
            
            if(parameter.getThirdId() != null){
                Assertions.assertEquals(parameter.getThirdId(), mirrorTransaction.getThird().getId());
            }

            if(parameter.getCategoryId() != null){
                Assertions.assertEquals(parameter.getCategoryId(), mirrorTransaction.getCategory().getId());
            }

            if(parameter.getClassificationId() != null){
                Assertions.assertEquals(parameter.getClassificationId(), mirrorTransaction.getClassification().getId());
            }

            Assertions.assertEquals(parameter.getTransactionDate(), mirrorTransaction.getTransactionDate());
            Assertions.assertEquals(parameter.getAmountDate(), mirrorTransaction.getAmountDate());

            Double mirrorAmount;
            Double mirrorCurrencyAmount = parameter.getCurrencyAmount() * -1;

            if(!bankingTransaction.getLinkedAccount().getDefaultCurrency().equals(bankingTransaction.getAccount().getDefaultCurrency())){
                mirrorAmount = mirrorCurrencyAmount
                    * Double.valueOf(bankingTransaction.getAccount().getDefaultCurrency().getRate())
                    / Double.valueOf(bankingTransaction.getLinkedAccount().getDefaultCurrency().getRate());
            } else {
                mirrorAmount = bankingTransaction.getAmount() * -1;
            }


            Assertions.assertEquals(mirrorAmount, mirrorTransaction.getAmount());
            Assertions.assertEquals(mirrorCurrencyAmount, mirrorTransaction.getCurrencyAmount());

            Assertions.assertEquals(parameter.getCurrencyId(), mirrorTransaction.getCurrency().getId());
            Assertions.assertEquals(parameter.getDescription(), mirrorTransaction.getDescription());

        }

        if(parameter.getThirdId() != null){
            Assertions.assertEquals(parameter.getThirdId(), bankingTransactionDTO.getThirdDTO().getId());
            Assertions.assertEquals(parameter.getThirdId(), bankingTransaction.getThird().getId());
        }

        if(parameter.getCategoryId() != null){
            Assertions.assertEquals(parameter.getCategoryId(), bankingTransactionDTO.getCategoryDTO().getId());
            Assertions.assertEquals(parameter.getCategoryId(), bankingTransaction.getCategory().getId());
        }


        if(parameter.getClassificationId() != null){
            Assertions.assertEquals(parameter.getClassificationId(), bankingTransactionDTO.getClassificationDTO().getId());
            Assertions.assertEquals(parameter.getClassificationId(), bankingTransaction.getClassification().getId());
        }

        Assertions.assertEquals(parameter.getTransactionDate(), bankingTransactionDTO.getTransactionDate());
        Assertions.assertEquals(parameter.getTransactionDate(), bankingTransaction.getTransactionDate());

        Assertions.assertEquals(parameter.getAmountDate(), bankingTransactionDTO.getAmountDate());
        Assertions.assertEquals(parameter.getAmountDate(), bankingTransaction.getAmountDate());

        Assertions.assertEquals(parameter.getAmount(), bankingTransactionDTO.getAmount());
        Assertions.assertEquals(parameter.getAmount(), bankingTransaction.getAmount());

        Assertions.assertEquals(parameter.getCurrencyAmount(), bankingTransactionDTO.getCurrencyAmount());
        Assertions.assertEquals(parameter.getCurrencyAmount(), bankingTransaction.getCurrencyAmount());

        Assertions.assertEquals(parameter.getCurrencyId(), bankingTransactionDTO.getCurrencyDTO().getId());
        Assertions.assertEquals(parameter.getCurrencyId(), bankingTransaction.getCurrency().getId());

        Assertions.assertEquals(parameter.getDescription(), bankingTransactionDTO.getDescription());
        Assertions.assertEquals(parameter.getDescription(), bankingTransaction.getDescription());

    }

    @Test
    public void testUpdateUserTransactionWithoutLinkedAccountToAnotherAccountAndLinkedAccount() throws BankingTransactionNotExistException, BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        BankingTransaction userBankingTransaction = this.testFactory.getBankingTransaction(this.user, false);

        Integer id = userBankingTransaction.getId();

        this.userUpdateBankingTransactionParameter.setId(id);

        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.updateBankingTransaction(this.userUpdateBankingTransactionParameter, this.user.getUserName());

        userBankingTransaction = this.bankingTransactionRepository.findById(id).orElseThrow();

        this.checkData(bankingTransactionDTO, userBankingTransaction, this.userUpdateBankingTransactionParameter);

    }

    @Test
    public void testUpdateUserTransactionWithoutLinkedAccountToAnotherAccountAndWithoutLinkedAccount() throws BankingTransactionNotExistException, BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        BankingTransaction userBankingTransaction = this.testFactory.getBankingTransaction(this.user, false);

        Integer id = userBankingTransaction.getId();

        this.userUpdateBankingTransactionParameter.setId(id);
        this.userUpdateBankingTransactionParameter.setLinkedAccountId(null);

        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.updateBankingTransaction(this.userUpdateBankingTransactionParameter, this.user.getUserName());

        userBankingTransaction = this.bankingTransactionRepository.findById(id).orElseThrow();

        this.checkData(bankingTransactionDTO, userBankingTransaction, this.userUpdateBankingTransactionParameter);

    }

    @Test
    public void testUpdateUserTransactionWithoutLinkedAccountToAnotherLinkedAccount() throws BankingTransactionNotExistException, BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        BankingTransaction userBankingTransaction = this.testFactory.getBankingTransaction(this.user, false);

        Integer id = userBankingTransaction.getId();

        this.userUpdateBankingTransactionParameter.setId(id);
        this.userUpdateBankingTransactionParameter.setAccountId(userBankingTransaction.getAccount().getId());

        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.updateBankingTransaction(this.userUpdateBankingTransactionParameter, this.user.getUserName());

        userBankingTransaction = this.bankingTransactionRepository.findById(id).orElseThrow();

        this.checkData(bankingTransactionDTO, userBankingTransaction, this.userUpdateBankingTransactionParameter);

    }

}
