package fr.finanting.server.service.accountservice;

import fr.finanting.server.exception.*;
import fr.finanting.server.model.Account;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.CreateAccountParameter;
import fr.finanting.server.parameter.DeleteAccountParameter;
import fr.finanting.server.parameter.subpart.AddressParameter;
import fr.finanting.server.parameter.subpart.BankDetailsParameter;
import fr.finanting.server.repository.AccountRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.AccountServiceImpl;
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
    private AccountRepository accountRepository;

    private AccountServiceImpl accountServiceImpl;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.accountServiceImpl = new AccountServiceImpl(accountRepository, groupRepository, userRepository);
    }

    @Test
    public void testDeleteUserAccountOk() throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        User user = this.factory.getUser();
        user = this.userRepository.save(user);
        Account account = this.factory.getAccount(user);
        account = this.accountRepository.save(account);

        DeleteAccountParameter deleteAccountParameter = new DeleteAccountParameter();
        deleteAccountParameter.setId(account.getId());

        this.accountServiceImpl.deleteAccount(deleteAccountParameter, user.getUserName());

        Optional<Account> accountOptional = this.accountRepository.findById(account.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteGroupAccountOk() throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        Group group = this.factory.getGroup();
        User user = this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);
        Account account = this.factory.getAccount(group);
        account = this.accountRepository.save(account);

        DeleteAccountParameter deleteAccountParameter = new DeleteAccountParameter();
        deleteAccountParameter.setId(account.getId());

        this.accountServiceImpl.deleteAccount(deleteAccountParameter, user.getUserName());

        Optional<Account> accountOptional = this.accountRepository.findById(account.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteGroupAccountNotAdmin() {
        Group group = this.factory.getGroup();
        User user = this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);
        Account account = this.factory.getAccount(group);
        account = this.accountRepository.save(account);

        User user2 = this.userRepository.save(this.factory.getUser());

        DeleteAccountParameter deleteAccountParameter = new DeleteAccountParameter();
        deleteAccountParameter.setId(account.getId());

        Assertions.assertThrows(NotAdminGroupException.class,
                () -> this.accountServiceImpl.deleteAccount(deleteAccountParameter, user2.getUserName()));
    }

    @Test
    public void testDeleteUserAccountNotUserAccount() {
        User user = this.userRepository.save(this.factory.getUser());
        Account account = this.factory.getAccount(user);
        account = this.accountRepository.save(account);

        User user2 = this.userRepository.save(this.factory.getUser());

        DeleteAccountParameter deleteAccountParameter = new DeleteAccountParameter();
        deleteAccountParameter.setId(account.getId());

        Assertions.assertThrows(NotUserAccountException.class,
                () -> this.accountServiceImpl.deleteAccount(deleteAccountParameter, user2.getUserName()));
    }

    @Test
    public void testDeleteUserAccountUserNotExist() throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        User user = this.factory.getUser();
        user = this.userRepository.save(user);
        Account account = this.factory.getAccount(user);
        account = this.accountRepository.save(account);

        DeleteAccountParameter deleteAccountParameter = new DeleteAccountParameter();
        deleteAccountParameter.setId(account.getId());

        this.accountServiceImpl.deleteAccount(deleteAccountParameter, user.getUserName());

        Optional<Account> accountOptional = this.accountRepository.findById(account.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteAccountNotExist() {
        User user = this.userRepository.save(this.factory.getUser());

        DeleteAccountParameter deleteAccountParameter = new DeleteAccountParameter();
        deleteAccountParameter.setId(this.factory.getRandomInteger());

        Assertions.assertThrows(AccountNotExistException.class,
                () -> this.accountServiceImpl.deleteAccount(deleteAccountParameter, user.getUserName()));
    }
}
