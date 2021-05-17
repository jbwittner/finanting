package fr.finanting.server.service.accountservice;

import fr.finanting.server.exception.*;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
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

        this.bankingAccountServiceImpl.deleteAccount(bankingAccount.getId(), user.getUserName());

        final Optional<BankingAccount> accountOptional = this.bankingAccountRepository.findById(bankingAccount.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteGroupAccountOk()
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException {

        final Group group = this.testFactory.getGroup();
        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(group);

        this.bankingAccountServiceImpl.deleteAccount(bankingAccount.getId(), group.getUserAdmin().getUserName());

        final Optional<BankingAccount> accountOptional = this.bankingAccountRepository.findById(bankingAccount.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteGroupAccountNotAdmin() {

        final User user = this.testFactory.getUser();
        final Group group = this.testFactory.getGroup(user);
        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(group);

        Assertions.assertThrows(NotAdminGroupException.class,
                () -> this.bankingAccountServiceImpl.deleteAccount(bankingAccount.getId(), user.getUserName()));
    }

    @Test
    public void testDeleteUserAccountNotUserAccount() {
        final User user = this.testFactory.getUser();
        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(user);

        final User user2 = this.testFactory.getUser();

        Assertions.assertThrows(NotUserBankingAccountException.class,
                () -> this.bankingAccountServiceImpl.deleteAccount(bankingAccount.getId(), user2.getUserName()));
    }

    @Test
    public void testDeleteUserAccountUserNotExist()
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException {

        final User user = this.testFactory.getUser();

        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(user);

        this.bankingAccountServiceImpl.deleteAccount(bankingAccount.getId(), user.getUserName());

        final Optional<BankingAccount> accountOptional = this.bankingAccountRepository.findById(bankingAccount.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteAccountNotExist() {
        final User user = this.testFactory.getUser();

        Assertions.assertThrows(BankingAccountNotExistException.class,
                () -> this.bankingAccountServiceImpl.deleteAccount(this.testFactory.getRandomInteger(), user.getUserName()));
    }
}
