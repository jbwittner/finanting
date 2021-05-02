package fr.finanting.server.service.bankingtransactionservice;

import java.util.Date;

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
import fr.finanting.server.exception.NotUserElementException;
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
import fr.finanting.server.parameter.CreateBankingTransactionParameter;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.BankingTransactionRepository;
import fr.finanting.server.repository.CategoryRepository;
import fr.finanting.server.repository.ClassificationRepository;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.repository.ThirdRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.BankingTransactionServiceImpl;

public class TestGetBankingTransaction extends AbstractMotherIntegrationTest {

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

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingTransactionServiceImpl = new BankingTransactionServiceImpl(this.bankingTransactionRepository,
                                                                                this.bankingAccountRepository,
                                                                                this.thirdRepository,
                                                                                this.categoryRepository,
                                                                                this.classificationRepository,
                                                                                this.currencyRepository,
                                                                                this.userRepository);
    }

    private void checkData(final BankingTransactionDTO bankingTransactionDTO, final BankingTransaction bankingTransaction){
        
        Assertions.assertEquals(bankingTransaction.getAccount().getId(), bankingTransactionDTO.getBankingAccountDTO().getId());

        if(bankingTransaction.getLinkedAccount() != null){
            BankingAccount linkedAccount = bankingTransaction.getLinkedAccount();
            Assertions.assertEquals(linkedAccount.getId(), bankingTransactionDTO.getLinkedBankingAccountDTO().getId());
        }

        Assertions.assertEquals(bankingTransaction.getTransactionDate(), bankingTransactionDTO.getTransactionDate());
        Assertions.assertEquals(bankingTransaction.getAmountDate(), bankingTransactionDTO.getAmountDate());
        Assertions.assertEquals(bankingTransaction.getAmount(), bankingTransactionDTO.getAmount());
        Assertions.assertEquals(bankingTransaction.getCurrencyAmount(), bankingTransactionDTO.getCurrencyAmount());
        Assertions.assertEquals(bankingTransaction.getCurrency().getId(), bankingTransactionDTO.getCurrencyDTO().getId());
        Assertions.assertEquals(bankingTransaction.getDescription(), bankingTransactionDTO.getDescription());

    }

    @Test
    public void testGetUserBankingTransaction() throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException{

        User user = this.testFactory.getUser();
        BankingTransaction bankingTransaction = this.testFactory.getBankingTransaction(user, true);

        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.getBankingTransaction(bankingTransaction.getId(), user.getUserName());

        this.checkData(bankingTransactionDTO, bankingTransaction);
    }

    @Test
    public void testGetGroupBankingTransaction() throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException{

        User user = this.testFactory.getUser();
        Group group = this.testFactory.getGroup(user);

        BankingTransaction bankingTransaction = this.testFactory.getBankingTransaction(group, true);

        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.getBankingTransaction(bankingTransaction.getId(), user.getUserName());

        this.checkData(bankingTransactionDTO, bankingTransaction);
    }

    @Test
    public void testGetUserBankingTransactionWithOtherUser() throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException{

        User user = this.testFactory.getUser();
        User otherUser = this.testFactory.getUser();
        BankingTransaction bankingTransaction = this.testFactory.getBankingTransaction(user, true);

        Assertions.assertThrows(NotUserElementException.class,
            () -> this.bankingTransactionServiceImpl.getBankingTransaction(bankingTransaction.getId(), otherUser.getUserName()));

    }

    @Test
    public void testGetGroupBankingTransactionWithUserNotInGroup() throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException{

        User user = this.testFactory.getUser();
        Group group = this.testFactory.getGroup();

        BankingTransaction bankingTransaction = this.testFactory.getBankingTransaction(group, true);

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.bankingTransactionServiceImpl.getBankingTransaction(bankingTransaction.getId(), user.getUserName()));
    }

    @Test
    public void testGetBankingTransactionNotExist() throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException{

        User user = this.testFactory.getUser();

        Assertions.assertThrows(BankingTransactionNotExistException.class,
            () -> this.bankingTransactionServiceImpl.getBankingTransaction(this.testFactory.getRandomInteger(), user.getUserName()));
    }
    
}
