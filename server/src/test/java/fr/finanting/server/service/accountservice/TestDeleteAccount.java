package fr.finanting.server.service.accountservice;

import fr.finanting.server.exception.*;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.DeleteAccountParameter;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.BankingAccountServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TestDeleteAccount extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private BankingAccountRepository bankingAccountRepository;

    private BankingAccountServiceImpl bankingAccountServiceImpl;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingAccountServiceImpl = new BankingAccountServiceImpl(bankingAccountRepository, groupRepository, userRepository);
    }

    @Test
    public void testDeleteUserAccountOk()
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        User user = this.factory.getUser();
        user = this.userRepository.save(user);
        BankingAccount bankingAccount = this.factory.getAccount(user);
        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final DeleteAccountParameter deleteAccountParameter = new DeleteAccountParameter();
        deleteAccountParameter.setId(bankingAccount.getId());

        this.bankingAccountServiceImpl.deleteAccount(deleteAccountParameter, user.getUserName());

        final Optional<BankingAccount> accountOptional = this.bankingAccountRepository.findById(bankingAccount.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteGroupAccountOk()
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        Group group = this.factory.getGroup();
        final User user = this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);
        BankingAccount bankingAccount = this.factory.getAccount(group);
        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final DeleteAccountParameter deleteAccountParameter = new DeleteAccountParameter();
        deleteAccountParameter.setId(bankingAccount.getId());

        this.bankingAccountServiceImpl.deleteAccount(deleteAccountParameter, user.getUserName());

        final Optional<BankingAccount> accountOptional = this.bankingAccountRepository.findById(bankingAccount.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteGroupAccountNotAdmin() {
        Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);
        final BankingAccount bankingAccount = this.bankingAccountRepository.save(this.factory.getAccount(group));

        final User user2 = this.userRepository.save(this.factory.getUser());

        final DeleteAccountParameter deleteAccountParameter = new DeleteAccountParameter();
        deleteAccountParameter.setId(bankingAccount.getId());

        Assertions.assertThrows(NotAdminGroupException.class,
                () -> this.bankingAccountServiceImpl.deleteAccount(deleteAccountParameter, user2.getUserName()));
    }

    @Test
    public void testDeleteUserAccountNotUserAccount() {
        final User user = this.userRepository.save(this.factory.getUser());
        final BankingAccount bankingAccount = this.bankingAccountRepository.save(this.factory.getAccount(user));

        final User user2 = this.userRepository.save(this.factory.getUser());

        final DeleteAccountParameter deleteAccountParameter = new DeleteAccountParameter();
        deleteAccountParameter.setId(bankingAccount.getId());

        Assertions.assertThrows(NotUserAccountException.class,
                () -> this.bankingAccountServiceImpl.deleteAccount(deleteAccountParameter, user2.getUserName()));
    }

    @Test
    public void testDeleteUserAccountUserNotExist()
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        final User user = this.userRepository.save(this.factory.getUser());
        final BankingAccount bankingAccount = this.bankingAccountRepository.save(this.factory.getAccount(user));

        final DeleteAccountParameter deleteAccountParameter = new DeleteAccountParameter();
        deleteAccountParameter.setId(bankingAccount.getId());

        this.bankingAccountServiceImpl.deleteAccount(deleteAccountParameter, user.getUserName());

        final Optional<BankingAccount> accountOptional = this.bankingAccountRepository.findById(bankingAccount.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteAccountNotExist() {
        final User user = this.userRepository.save(this.factory.getUser());

        final DeleteAccountParameter deleteAccountParameter = new DeleteAccountParameter();
        deleteAccountParameter.setId(this.factory.getRandomInteger());

        Assertions.assertThrows(AccountNotExistException.class,
                () -> this.bankingAccountServiceImpl.deleteAccount(deleteAccountParameter, user.getUserName()));
    }
}
