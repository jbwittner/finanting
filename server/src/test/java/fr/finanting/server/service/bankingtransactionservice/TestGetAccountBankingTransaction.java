package fr.finanting.server.service.bankingtransactionservice;

import java.util.List;

import fr.finanting.server.codegen.model.BankingTransactionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import fr.finanting.server.exception.BankingAccountNotExistException;
import fr.finanting.server.exception.NotUserElementException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.BankingAccount;
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

public class TestGetAccountBankingTransaction extends AbstractMotherIntegrationTest {

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
            final BankingAccount linkedAccount = bankingTransaction.getLinkedAccount();
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
    public void testGetUserAccountBankingTransaction() throws NotUserElementException, UserNotInGroupException, BankingAccountNotExistException  {

        final User user = this.testFactory.getUser();

        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(user);

        final BankingTransaction bankingTransaction1 = this.testFactory.getBankingTransaction(user, bankingAccount, true);
        final BankingTransaction bankingTransaction2 = this.testFactory.getBankingTransaction(user, bankingAccount, true);
        final BankingTransaction bankingTransaction3 = this.testFactory.getBankingTransaction(user, bankingAccount, true);
        final BankingTransaction bankingTransaction4 = this.testFactory.getBankingTransaction(user, bankingAccount, true);

        final List<BankingTransactionDTO> bankingTransactionDTOs = this.bankingTransactionServiceImpl.getBankingAccountTransaction(bankingAccount.getId(), user.getUserName());

        Assertions.assertEquals(4, bankingTransactionDTOs.size());

        for(final BankingTransactionDTO bankingTransactionDTO : bankingTransactionDTOs){
            BankingTransaction bankingTransactionToCheck = null;

            if(bankingTransactionDTO.getId().equals(bankingTransaction1.getId())){
                bankingTransactionToCheck = bankingTransaction1;
            } else if(bankingTransactionDTO.getId().equals(bankingTransaction2.getId())){
                bankingTransactionToCheck = bankingTransaction2;
            } else if(bankingTransactionDTO.getId().equals(bankingTransaction3.getId())){
                bankingTransactionToCheck = bankingTransaction3;
            } else if(bankingTransactionDTO.getId().equals(bankingTransaction4.getId())){
                bankingTransactionToCheck = bankingTransaction4;
            } else {
                Assertions.fail();
            }

            this.checkData(bankingTransactionDTO, bankingTransactionToCheck);
        }

    }

    @Test
    public void testGetGroupAccountBankingTransaction() throws NotUserElementException, UserNotInGroupException, BankingAccountNotExistException {

        final User user = this.testFactory.getUser();
        final Group group = this.testFactory.getGroup(user);

        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(group);

        final BankingTransaction bankingTransaction1 = this.testFactory.getBankingTransaction(group, bankingAccount, true);
        final BankingTransaction bankingTransaction2 = this.testFactory.getBankingTransaction(group, bankingAccount, true);
        final BankingTransaction bankingTransaction3 = this.testFactory.getBankingTransaction(group, bankingAccount, true);
        final BankingTransaction bankingTransaction4 = this.testFactory.getBankingTransaction(group, bankingAccount, true);

        final List<BankingTransactionDTO> bankingTransactionDTOs = this.bankingTransactionServiceImpl.getBankingAccountTransaction(bankingAccount.getId(), user.getUserName());

        Assertions.assertEquals(4, bankingTransactionDTOs.size());

        for(final BankingTransactionDTO bankingTransactionDTO : bankingTransactionDTOs){
            BankingTransaction bankingTransactionToCheck = null;

            if(bankingTransactionDTO.getId().equals(bankingTransaction1.getId())){
                bankingTransactionToCheck = bankingTransaction1;
            } else if(bankingTransactionDTO.getId().equals(bankingTransaction2.getId())){
                bankingTransactionToCheck = bankingTransaction2;
            } else if(bankingTransactionDTO.getId().equals(bankingTransaction3.getId())){
                bankingTransactionToCheck = bankingTransaction3;
            } else if(bankingTransactionDTO.getId().equals(bankingTransaction4.getId())){
                bankingTransactionToCheck = bankingTransaction4;
            } else {
                Assertions.fail();
            }

            this.checkData(bankingTransactionDTO, bankingTransactionToCheck);
        }

    }

    @Test
    public void testGetUserAccountBankingTransactionWithOtherUser() {

        final User user = this.testFactory.getUser();
        final User otherUser = this.testFactory.getUser();

        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(user);

        Assertions.assertThrows(NotUserElementException.class,
            () -> this.bankingTransactionServiceImpl.getBankingAccountTransaction(bankingAccount.getId(), otherUser.getUserName()));

    }

    @Test
    public void testGetGroupAccountBankingTransactionWithUserNotInGroup() {

        final User user = this.testFactory.getUser();
        final Group group = this.testFactory.getGroup();

        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(group);

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.bankingTransactionServiceImpl.getBankingAccountTransaction(bankingAccount.getId(), user.getUserName()));
    }

    @Test
    public void testGetAccountBankingTransactionNotExist() {

        final User user = this.testFactory.getUser();

        Assertions.assertThrows(BankingAccountNotExistException.class,
            () -> this.bankingTransactionServiceImpl.getBankingAccountTransaction(this.testFactory.getRandomInteger(), user.getUserName()));
    }
    
}
