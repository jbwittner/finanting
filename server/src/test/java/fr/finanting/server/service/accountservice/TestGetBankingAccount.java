package fr.finanting.server.service.accountservice;

import fr.finanting.server.dto.BankingAccountDTO;
import fr.finanting.server.exception.BankingAccountNotExistException;
import fr.finanting.server.exception.NotUserBankingAccountException;
import fr.finanting.server.exception.UserNotInGroupException;
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

import java.util.ArrayList;
import java.util.List;

public class TestGetBankingAccount extends AbstractMotherIntegrationTest {

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
    public void testGetUserAccount()
            throws BankingAccountNotExistException, NotUserBankingAccountException, UserNotInGroupException {
        final User user = this.userRepository.save(this.factory.getUser());

        BankingAccount bankingAccount = this.factory.getBankingAccount(user);
        this.currencyRepository.save(bankingAccount.getDefaultCurrency());
        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final BankingAccountDTO bankingAccountDTO = this.bankingAccountServiceImpl.getBankingAccount(bankingAccount.getId(), user.getUserName());

        this.checkAccount(bankingAccountDTO, bankingAccount);
    }

    @Test
    public void testGetGroupAccount()
            throws BankingAccountNotExistException, NotUserBankingAccountException, UserNotInGroupException {
        final Group group = this.factory.getGroup();
        final User user = this.userRepository.save(group.getUserAdmin());

        BankingAccount bankingAccount = this.factory.getBankingAccount(group);
        this.currencyRepository.save(bankingAccount.getDefaultCurrency());
        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final List<BankingAccount> bankingAccountList = new ArrayList<>();
        bankingAccountList.add(bankingAccount);

        group.setAccounts(bankingAccountList);
        this.groupRepository.save(group);

        final BankingAccountDTO bankingAccountDTO = this.bankingAccountServiceImpl.getBankingAccount(bankingAccount.getId(), user.getUserName());

        this.checkAccount(bankingAccountDTO, bankingAccount);
    }

    @Test
    public void testGetGroupAccountNotInGroup() {
        final Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());

        BankingAccount bankingAccount = this.factory.getBankingAccount(group);
        this.currencyRepository.save(bankingAccount.getDefaultCurrency());
        final BankingAccount finalBankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final List<BankingAccount> bankingAccountList = new ArrayList<>();
        bankingAccountList.add(bankingAccount);

        group.setAccounts(bankingAccountList);
        this.groupRepository.save(group);

        final User user = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(UserNotInGroupException.class,
                () -> this.bankingAccountServiceImpl.getBankingAccount(finalBankingAccount.getId(), user.getUserName()));

    }

    @Test
    public void testGetNotExistAccount() {

        final User user = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(BankingAccountNotExistException.class,
                () -> this.bankingAccountServiceImpl.getBankingAccount(this.factory.getRandomInteger(), user.getUserName()));

    }

    @Test
    public void testGetNotUserAccount() {
        final User user = this.userRepository.save(this.factory.getUser());

        BankingAccount bankingAccount = this.factory.getBankingAccount(user);
        this.currencyRepository.save(bankingAccount.getDefaultCurrency());
        final BankingAccount finalBankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final User user2 = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(NotUserBankingAccountException.class,
                () -> this.bankingAccountServiceImpl.getBankingAccount(finalBankingAccount.getId(), user2.getUserName()));
    }

    private void checkAccount(final BankingAccountDTO bankingAccountDTO,
                              final BankingAccount bankingAccount){

        Assertions.assertEquals(bankingAccount.getAbbreviation(), bankingAccountDTO.getAbbreviation());
        Assertions.assertEquals(bankingAccount.getInitialBalance(), bankingAccountDTO.getBalance());
        Assertions.assertEquals(bankingAccount.getLabel(), bankingAccountDTO.getLabel());
        Assertions.assertEquals(bankingAccount.getAddress().getCity(),
                bankingAccountDTO.getAddressDTO().getCity());
        Assertions.assertEquals(bankingAccount.getAddress().getStreet(),
                bankingAccountDTO.getAddressDTO().getStreet());
        Assertions.assertEquals(bankingAccount.getAddress().getAddress(),
                bankingAccountDTO.getAddressDTO().getAddress());
        Assertions.assertEquals(bankingAccount.getAddress().getZipCode(),
                bankingAccountDTO.getAddressDTO().getZipCode());
        Assertions.assertEquals(bankingAccount.getBankDetails().getAccountNumber(),
                bankingAccountDTO.getBankDetailsDTO().getAccountNumber());
        Assertions.assertEquals(bankingAccount.getBankDetails().getIban(),
                bankingAccountDTO.getBankDetailsDTO().getIban());

    }

}
