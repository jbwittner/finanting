package fr.finanting.server.service.accountservice;

import fr.finanting.server.exception.*;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.DeleteBankingAccountParameter;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.BankingAccountServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TestDeleteBankingAccount extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private BankingAccountRepository bankingAccountRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    private BankingAccountServiceImpl bankingAccountServiceImpl;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingAccountServiceImpl = new BankingAccountServiceImpl(bankingAccountRepository, groupRepository, userRepository, currencyRepository);
    }

    @Test
    public void testDeleteUserAccountOk()
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException {
        final User user = this.testFactory.getUser();

        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(user);

        final DeleteBankingAccountParameter deleteBankingAccountParameter = new DeleteBankingAccountParameter();
        deleteBankingAccountParameter.setId(bankingAccount.getId());

        this.bankingAccountServiceImpl.deleteAccount(deleteBankingAccountParameter, user.getUserName());

        final Optional<BankingAccount> accountOptional = this.bankingAccountRepository.findById(bankingAccount.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteGroupAccountOk()
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException {

        Group group = this.testFactory.getGroup();
        BankingAccount bankingAccount = this.testFactory.getBankingAccount(group);

        final DeleteBankingAccountParameter deleteBankingAccountParameter = new DeleteBankingAccountParameter();
        deleteBankingAccountParameter.setId(bankingAccount.getId());

        this.bankingAccountServiceImpl.deleteAccount(deleteBankingAccountParameter, group.getUserAdmin().getUserName());

        final Optional<BankingAccount> accountOptional = this.bankingAccountRepository.findById(bankingAccount.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteGroupAccountNotAdmin() {

        User user = this.testFactory.getUser();
        Group group = this.testFactory.getGroup(user);
        BankingAccount bankingAccount = this.testFactory.getBankingAccount(group);

        final DeleteBankingAccountParameter deleteBankingAccountParameter = new DeleteBankingAccountParameter();
        deleteBankingAccountParameter.setId(bankingAccount.getId());

        Assertions.assertThrows(NotAdminGroupException.class,
                () -> this.bankingAccountServiceImpl.deleteAccount(deleteBankingAccountParameter, user.getUserName()));
    }

    @Test
    public void testDeleteUserAccountNotUserAccount() {
        User user = this.testFactory.getUser();

        BankingAccount bankingAccount = this.testFactory.getBankingAccount(user);

        final User user2 = this.testFactory.getUser();

        final DeleteBankingAccountParameter deleteBankingAccountParameter = new DeleteBankingAccountParameter();
        deleteBankingAccountParameter.setId(bankingAccount.getId());

        Assertions.assertThrows(NotUserBankingAccountException.class,
                () -> this.bankingAccountServiceImpl.deleteAccount(deleteBankingAccountParameter, user2.getUserName()));
    }

    @Test
    public void testDeleteUserAccountUserNotExist()
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException {

        final User user = this.testFactory.getUser();

        BankingAccount bankingAccount = this.testFactory.getBankingAccount(user);

        final DeleteBankingAccountParameter deleteBankingAccountParameter = new DeleteBankingAccountParameter();
        deleteBankingAccountParameter.setId(bankingAccount.getId());

        this.bankingAccountServiceImpl.deleteAccount(deleteBankingAccountParameter, user.getUserName());

        final Optional<BankingAccount> accountOptional = this.bankingAccountRepository.findById(bankingAccount.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteAccountNotExist() {
        final User user = this.testFactory.getUser();

        final DeleteBankingAccountParameter deleteBankingAccountParameter = new DeleteBankingAccountParameter();
        deleteBankingAccountParameter.setId(this.testFactory.getRandomInteger());

        Assertions.assertThrows(BankingAccountNotExistException.class,
                () -> this.bankingAccountServiceImpl.deleteAccount(deleteBankingAccountParameter, user.getUserName()));
    }
}
