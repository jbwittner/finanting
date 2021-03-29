package fr.finanting.server.service.accountservice;

import fr.finanting.server.dto.AccountDTO;
import fr.finanting.server.exception.AccountNotExistException;
import fr.finanting.server.exception.NotAdminGroupException;
import fr.finanting.server.exception.NotUserAccountException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Account;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.UpdateAccountParameter;
import fr.finanting.server.repository.AccountRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.AccountServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TestGetAccount extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AccountRepository accountRepository;

    private AccountServiceImpl accountServiceImpl;
    private UpdateAccountParameter updateAccountParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.accountServiceImpl = new AccountServiceImpl(accountRepository, groupRepository, userRepository);
    }

    @Test
    public void testGetUserAccount()
            throws AccountNotExistException, NotUserAccountException, UserNotInGroupException {
        User user = this.userRepository.save(this.factory.getUser());
        Account account = this.accountRepository.save(this.factory.getAccount(user));

        AccountDTO accountDTO = this.accountServiceImpl.getAccount(account.getId(), user.getUserName());

        this.checkAccount(accountDTO, account);
    }

    @Test
    public void testGetGroupAccount()
            throws AccountNotExistException, NotUserAccountException, UserNotInGroupException {
        Group group = this.factory.getGroup();
        User user = this.userRepository.save(group.getUserAdmin());
        Account account = this.accountRepository.save(this.factory.getAccount(group));

        List<Account> accountList = new ArrayList<>();
        accountList.add(account);

        group.setAccounts(accountList);
        this.groupRepository.save(group);

        AccountDTO accountDTO = this.accountServiceImpl.getAccount(account.getId(), user.getUserName());

        this.checkAccount(accountDTO, account);
    }

    @Test
    public void testGetGroupAccountNotInGroup() {
        Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());
        Account account = this.accountRepository.save(this.factory.getAccount(group));

        List<Account> accountList = new ArrayList<>();
        accountList.add(account);

        group.setAccounts(accountList);
        this.groupRepository.save(group);

        User user = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(UserNotInGroupException.class,
                () -> this.accountServiceImpl.getAccount(account.getId(), user.getUserName()));

    }

    @Test
    public void testGetNotExistAccount() {

        User user = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(AccountNotExistException.class,
                () -> this.accountServiceImpl.getAccount(this.factory.getRandomInteger(), user.getUserName()));

    }

    @Test
    public void testGetNotUserAccount() {
        User user = this.userRepository.save(this.factory.getUser());
        Account account = this.accountRepository.save(this.factory.getAccount(user));

        User user2 = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(NotUserAccountException.class,
                () -> this.accountServiceImpl.getAccount(account.getId(), user2.getUserName()));
    }

    private void checkAccount(final AccountDTO accountDTO,
                              final Account account){

        Assertions.assertEquals(account.getAbbreviation(), accountDTO.getAbbreviation());
        Assertions.assertEquals(account.getInitialBalance(), accountDTO.getBalance());
        Assertions.assertEquals(account.getLabel(), accountDTO.getLabel());
        Assertions.assertEquals(account.getAddress().getCity(),
                accountDTO.getAddressDTO().getCity());
        Assertions.assertEquals(account.getAddress().getStreet(),
                accountDTO.getAddressDTO().getStreet());
        Assertions.assertEquals(account.getAddress().getAddress(),
                accountDTO.getAddressDTO().getAddress());
        Assertions.assertEquals(account.getAddress().getZipCode(),
                accountDTO.getAddressDTO().getZipCode());
        Assertions.assertEquals(account.getBankDetails().getAccountNumber(),
                accountDTO.getBankDetailsDTO().getAccountNumber());
        Assertions.assertEquals(account.getBankDetails().getIban(),
                accountDTO.getBankDetailsDTO().getIban());

    }

}
