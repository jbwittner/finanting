package fr.finanting.server.service.accountservice;

import fr.finanting.server.exception.*;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.DeleteBankingAccountParameter;
import fr.finanting.server.repository.BankingAccountRepository;
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

    private BankingAccountServiceImpl bankingAccountServiceImpl;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingAccountServiceImpl = new BankingAccountServiceImpl(bankingAccountRepository, groupRepository, userRepository);
    }

    @Test
    public void testDeleteUserAccountOk()
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserAccountException {
        User user = this.factory.getUser();
        user = this.userRepository.save(user);
        BankingAccount bankingAccount = this.factory.getBankingAccount(user);
        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final DeleteBankingAccountParameter deleteBankingAccountParameter = new DeleteBankingAccountParameter();
        deleteBankingAccountParameter.setId(bankingAccount.getId());

        this.bankingAccountServiceImpl.deleteAccount(deleteBankingAccountParameter, user.getUserName());

        final Optional<BankingAccount> accountOptional = this.bankingAccountRepository.findById(bankingAccount.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteGroupAccountOk()
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserAccountException {
        Group group = this.factory.getGroup();
        final User user = this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);
        BankingAccount bankingAccount = this.factory.getBankingAccount(group);
        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final DeleteBankingAccountParameter deleteBankingAccountParameter = new DeleteBankingAccountParameter();
        deleteBankingAccountParameter.setId(bankingAccount.getId());

        this.bankingAccountServiceImpl.deleteAccount(deleteBankingAccountParameter, user.getUserName());

        final Optional<BankingAccount> accountOptional = this.bankingAccountRepository.findById(bankingAccount.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteGroupAccountNotAdmin() {
        Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);
        final BankingAccount bankingAccount = this.bankingAccountRepository.save(this.factory.getBankingAccount(group));

        final User user2 = this.userRepository.save(this.factory.getUser());

        final DeleteBankingAccountParameter deleteBankingAccountParameter = new DeleteBankingAccountParameter();
        deleteBankingAccountParameter.setId(bankingAccount.getId());

        Assertions.assertThrows(NotAdminGroupException.class,
                () -> this.bankingAccountServiceImpl.deleteAccount(deleteBankingAccountParameter, user2.getUserName()));
    }

    @Test
    public void testDeleteUserAccountNotUserAccount() {
        final User user = this.userRepository.save(this.factory.getUser());
        final BankingAccount bankingAccount = this.bankingAccountRepository.save(this.factory.getBankingAccount(user));

        final User user2 = this.userRepository.save(this.factory.getUser());

        final DeleteBankingAccountParameter deleteBankingAccountParameter = new DeleteBankingAccountParameter();
        deleteBankingAccountParameter.setId(bankingAccount.getId());

        Assertions.assertThrows(NotUserAccountException.class,
                () -> this.bankingAccountServiceImpl.deleteAccount(deleteBankingAccountParameter, user2.getUserName()));
    }

    @Test
    public void testDeleteUserAccountUserNotExist()
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserAccountException {
        final User user = this.userRepository.save(this.factory.getUser());
        final BankingAccount bankingAccount = this.bankingAccountRepository.save(this.factory.getBankingAccount(user));

        final DeleteBankingAccountParameter deleteBankingAccountParameter = new DeleteBankingAccountParameter();
        deleteBankingAccountParameter.setId(bankingAccount.getId());

        this.bankingAccountServiceImpl.deleteAccount(deleteBankingAccountParameter, user.getUserName());

        final Optional<BankingAccount> accountOptional = this.bankingAccountRepository.findById(bankingAccount.getId());

        Assertions.assertFalse(accountOptional.isPresent());
    }

    @Test
    public void testDeleteAccountNotExist() {
        final User user = this.userRepository.save(this.factory.getUser());

        final DeleteBankingAccountParameter deleteBankingAccountParameter = new DeleteBankingAccountParameter();
        deleteBankingAccountParameter.setId(this.factory.getRandomInteger());

        Assertions.assertThrows(BankingAccountNotExistException.class,
                () -> this.bankingAccountServiceImpl.deleteAccount(deleteBankingAccountParameter, user.getUserName()));
    }
}
