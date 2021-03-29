package fr.finanting.server.service.accountservice;

import fr.finanting.server.dto.AccountDTO;
import fr.finanting.server.exception.*;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.UpdateAccountParameter;
import fr.finanting.server.parameter.subpart.AddressParameter;
import fr.finanting.server.parameter.subpart.BankDetailsParameter;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.BankingAccountServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestUpdateAccount extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private BankingAccountRepository accountRepository;

    private BankingAccountServiceImpl accountServiceImpl;
    private UpdateAccountParameter updateAccountParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.accountServiceImpl = new BankingAccountServiceImpl(accountRepository, groupRepository, userRepository);

        Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);

        this.updateAccountParameter = new UpdateAccountParameter();
        final AddressParameter addressParameter = new AddressParameter();
        addressParameter.setAddress(this.faker.address().fullAddress());
        addressParameter.setCity(this.faker.address().city());
        addressParameter.setStreet(this.faker.address().streetAddress());
        addressParameter.setZipCode(this.faker.address().zipCode());
        this.updateAccountParameter.setAddressParameter(addressParameter);

        final BankDetailsParameter bankDetailsParameter = new BankDetailsParameter();
        bankDetailsParameter.setAccountNumber(this.factory.getRandomAlphanumericString());
        bankDetailsParameter.setIban(this.factory.getRandomAlphanumericString());
        this.updateAccountParameter.setBankDetailsParameter(bankDetailsParameter);

        this.updateAccountParameter.setInitialBalance(this.factory.getRandomInteger());
        this.updateAccountParameter.setLabel(this.faker.backToTheFuture().quote());
        this.updateAccountParameter.setAbbreviation(this.factory.getRandomAlphanumericString());

    }

    @Test
    public void testUpdateGroupAccountOk()
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        Group group = this.factory.getGroup();
        final User user = this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);

        BankingAccount account = this.accountRepository.save(this.factory.getAccount(group));

        this.updateAccountParameter.setAccountId(account.getId());

        final AccountDTO accountDTO =
                this.accountServiceImpl.updateAccount(this.updateAccountParameter, user.getUserName());

        account = this.accountRepository.findById(accountDTO.getId()).orElseThrow();

        this.checkAccount(accountDTO, account, this.updateAccountParameter);

    }

    @Test
    public void testUpdateUserAccountOk()
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        final User user = this.userRepository.save(this.factory.getUser());
        BankingAccount account = this.accountRepository.save(this.factory.getAccount(user));

        this.updateAccountParameter.setAccountId(account.getId());

        final AccountDTO accountDTO =
                this.accountServiceImpl.updateAccount(this.updateAccountParameter, user.getUserName());

        account = this.accountRepository.findById(accountDTO.getId()).orElseThrow();

        this.checkAccount(accountDTO, account, this.updateAccountParameter);

    }

    @Test
    public void testUpdateAccountNotExist()
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        final User user = this.userRepository.save(this.factory.getUser());

        this.updateAccountParameter.setAccountId(this.factory.getRandomInteger());

        Assertions.assertThrows(AccountNotExistException.class,
                () -> this.accountServiceImpl.updateAccount(this.updateAccountParameter, user.getUserName()));

    }

    @Test
    public void testUpdateGroupAccountNotAdmin() {
        Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);

        final User user2 = this.userRepository.save(this.factory.getUser());

        final BankingAccount account = this.accountRepository.save(this.factory.getAccount(group));

        this.updateAccountParameter.setAccountId(account.getId());

        Assertions.assertThrows(NotAdminGroupException.class,
                () -> this.accountServiceImpl.updateAccount(this.updateAccountParameter, user2.getUserName()));
    }

    @Test
    public void testUpdateUserAccountNotUserAccount()
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        final User user = this.userRepository.save(this.factory.getUser());
        final BankingAccount account = this.accountRepository.save(this.factory.getAccount(user));

        this.updateAccountParameter.setAccountId(account.getId());

        final User user2 = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(NotUserAccountException.class,
                () -> this.accountServiceImpl.updateAccount(this.updateAccountParameter, user2.getUserName()));

    }

    private void checkAccount(final AccountDTO accountDTO,
                              final BankingAccount account,
                              final UpdateAccountParameter updateAccountParameter){

        Assertions.assertEquals(updateAccountParameter.getAbbreviation().toUpperCase(), accountDTO.getAbbreviation());
        Assertions.assertEquals(updateAccountParameter.getInitialBalance(), accountDTO.getBalance());
        Assertions.assertEquals(updateAccountParameter.getLabel(), accountDTO.getLabel());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getCity(),
                accountDTO.getAddressDTO().getCity());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getStreet(),
                accountDTO.getAddressDTO().getStreet());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getAddress(),
                accountDTO.getAddressDTO().getAddress());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getZipCode(),
                accountDTO.getAddressDTO().getZipCode());
        Assertions.assertEquals(updateAccountParameter.getBankDetailsParameter().getAccountNumber(),
                accountDTO.getBankDetailsDTO().getAccountNumber());
        Assertions.assertEquals(updateAccountParameter.getBankDetailsParameter().getIban(),
                accountDTO.getBankDetailsDTO().getIban());

        Assertions.assertEquals(updateAccountParameter.getAbbreviation().toUpperCase(), account.getAbbreviation());
        Assertions.assertEquals(updateAccountParameter.getInitialBalance(), account.getInitialBalance());
        Assertions.assertEquals(updateAccountParameter.getLabel(), account.getLabel());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getCity(),
                account.getAddress().getCity());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getStreet(),
                account.getAddress().getStreet());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getAddress(),
                account.getAddress().getAddress());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getZipCode(),
                account.getAddress().getZipCode());
        Assertions.assertEquals(updateAccountParameter.getBankDetailsParameter().getAccountNumber(),
                account.getBankDetails().getAccountNumber());
        Assertions.assertEquals(updateAccountParameter.getBankDetailsParameter().getIban(),
                account.getBankDetails().getIban());

    }

}
