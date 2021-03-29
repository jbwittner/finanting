package fr.finanting.server.service.accountservice;

import fr.finanting.server.dto.AccountDTO;
import fr.finanting.server.exception.AccountNotExistException;
import fr.finanting.server.exception.NotUserAccountException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
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

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.accountServiceImpl = new AccountServiceImpl(accountRepository, groupRepository, userRepository);
    }

    @Test
    public void testGetUserAccount()
            throws AccountNotExistException, NotUserAccountException, UserNotInGroupException {
        final User user = this.userRepository.save(this.factory.getUser());
        final BankingAccount account = this.accountRepository.save(this.factory.getAccount(user));

        final AccountDTO accountDTO = this.accountServiceImpl.getAccount(account.getId(), user.getUserName());

        this.checkAccount(accountDTO, account);
    }

    @Test
    public void testGetGroupAccount()
            throws AccountNotExistException, NotUserAccountException, UserNotInGroupException {
        final Group group = this.factory.getGroup();
        final User user = this.userRepository.save(group.getUserAdmin());
        final BankingAccount account = this.accountRepository.save(this.factory.getAccount(group));

        final List<BankingAccount> accountList = new ArrayList<>();
        accountList.add(account);

        group.setAccounts(accountList);
        this.groupRepository.save(group);

        final AccountDTO accountDTO = this.accountServiceImpl.getAccount(account.getId(), user.getUserName());

        this.checkAccount(accountDTO, account);
    }

    @Test
    public void testGetGroupAccountNotInGroup() {
        final Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());
        final BankingAccount account = this.accountRepository.save(this.factory.getAccount(group));

        final List<BankingAccount> accountList = new ArrayList<>();
        accountList.add(account);

        group.setAccounts(accountList);
        this.groupRepository.save(group);

        final User user = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(UserNotInGroupException.class,
                () -> this.accountServiceImpl.getAccount(account.getId(), user.getUserName()));

    }

    @Test
    public void testGetNotExistAccount() {

        final User user = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(AccountNotExistException.class,
                () -> this.accountServiceImpl.getAccount(this.factory.getRandomInteger(), user.getUserName()));

    }

    @Test
    public void testGetNotUserAccount() {
        final User user = this.userRepository.save(this.factory.getUser());
        final BankingAccount account = this.accountRepository.save(this.factory.getAccount(user));

        final User user2 = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(NotUserAccountException.class,
                () -> this.accountServiceImpl.getAccount(account.getId(), user2.getUserName()));
    }

    private void checkAccount(final AccountDTO accountDTO,
                              final BankingAccount account){

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
