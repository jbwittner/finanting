package fr.finanting.server.service.bankingtransactionservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import fr.finanting.server.exception.BankingTransactionNotExistException;
import fr.finanting.server.exception.NotUserElementException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.BankingTransaction;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.BankingTransactionRepository;
import fr.finanting.server.repository.CategoryRepository;
import fr.finanting.server.repository.ClassificationRepository;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.repository.ThirdRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.BankingTransactionServiceImpl;

public class TestDeleteAccountBankingTransaction extends AbstractMotherIntegrationTest {

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

    private Group group;
    private User user;

    @Override
    protected void initDataBeforeEach() {

        this.bankingTransactionServiceImpl = new BankingTransactionServiceImpl(this.bankingTransactionRepository,
                                                                                this.bankingAccountRepository,
                                                                                this.thirdRepository,
                                                                                this.categoryRepository,
                                                                                this.classificationRepository,
                                                                                this.currencyRepository,
                                                                                this.userRepository);

        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(user);
        
    }

    @Test
    protected void testDeleteUserBankingTransaction()
        throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException{

        final BankingTransaction bankingTransaction = this.testFactory.getBankingTransaction(this.user, false);

        final Integer id = bankingTransaction.getId();

        this.bankingTransactionServiceImpl.deleteAccountBankingTransaction(id, this.user.getUserName());

        final Optional<BankingTransaction> result = this.bankingTransactionRepository.findById(id);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    protected void testDeleteUserBankingTransactionWithMirrorTransaction()
        throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException{

        final BankingTransaction bankingTransaction = this.testFactory.getBankingTransaction(this.user, true);

        final Integer id = bankingTransaction.getId();
        final Integer mirrorId = bankingTransaction.getMirrorTransaction().getId();

        this.bankingTransactionServiceImpl.deleteAccountBankingTransaction(id, this.user.getUserName());

        final Optional<BankingTransaction> result = this.bankingTransactionRepository.findById(id);
        final Optional<BankingTransaction> resultMirror = this.bankingTransactionRepository.findById(mirrorId);

        Assertions.assertFalse(result.isPresent());
        Assertions.assertFalse(resultMirror.isPresent());
    }

    @Test
    protected void testDeleteGroupBankingTransaction()
        throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException{

        final BankingTransaction bankingTransaction = this.testFactory.getBankingTransaction(this.group, false);

        final Integer id = bankingTransaction.getId();

        this.bankingTransactionServiceImpl.deleteAccountBankingTransaction(id, this.user.getUserName());

        final Optional<BankingTransaction> result = this.bankingTransactionRepository.findById(id);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    protected void testDeleteGroupBankingTransactionWithMirrorTransaction()
        throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException{

        final BankingTransaction bankingTransaction = this.testFactory.getBankingTransaction(this.group, true);

        final Integer id = bankingTransaction.getId();
        final Integer mirrorId = bankingTransaction.getMirrorTransaction().getId();

        this.bankingTransactionServiceImpl.deleteAccountBankingTransaction(id, this.user.getUserName());

        final Optional<BankingTransaction> result = this.bankingTransactionRepository.findById(id);
        final Optional<BankingTransaction> resultMirror = this.bankingTransactionRepository.findById(mirrorId);

        Assertions.assertFalse(result.isPresent());
        Assertions.assertFalse(resultMirror.isPresent());
    }

    @Test
    protected void testDeleteBankingTransactionNotExist() {

        final Integer id = this.testFactory.getRandomInteger();
        Assertions.assertThrows(BankingTransactionNotExistException.class,
            () -> this.bankingTransactionServiceImpl.deleteAccountBankingTransaction(id, this.user.getUserName()));

    }

    @Test
    protected void testDeleteNotUserElement() {

        final User otherUser = this.testFactory.getUser();
        final BankingTransaction bankingTransaction = this.testFactory.getBankingTransaction(this.user, false);

        final Integer id = bankingTransaction.getId();

        Assertions.assertThrows(NotUserElementException.class,
            () -> this.bankingTransactionServiceImpl.deleteAccountBankingTransaction(id, otherUser.getUserName()));

    }

    @Test
    protected void testDeleteUserNotInGroup() {

        final User otherUser = this.testFactory.getUser();
        final BankingTransaction bankingTransaction = this.testFactory.getBankingTransaction(this.group, false);

        final Integer id = bankingTransaction.getId();

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.bankingTransactionServiceImpl.deleteAccountBankingTransaction(id, otherUser.getUserName()));

    }
    
}