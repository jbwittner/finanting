package fr.finanting.server.service.accountservice;

import fr.finanting.server.dto.BankingAccountDTO;
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
    private BankingAccountRepository bankingAccountRepository;

    private BankingAccountServiceImpl bankingAccountServiceImpl;
    private UpdateAccountParameter updateAccountParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingAccountServiceImpl = new BankingAccountServiceImpl(bankingAccountRepository, groupRepository, userRepository);

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

        BankingAccount bankingAccount = this.bankingAccountRepository.save(this.factory.getAccount(group));

        this.updateAccountParameter.setAccountId(bankingAccount.getId());

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.updateAccount(this.updateAccountParameter, user.getUserName());

        bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, this.updateAccountParameter);

    }

    @Test
    public void testUpdateUserAccountOk()
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        final User user = this.userRepository.save(this.factory.getUser());
        BankingAccount bankingAccount = this.bankingAccountRepository.save(this.factory.getAccount(user));

        this.updateAccountParameter.setAccountId(bankingAccount.getId());

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.updateAccount(this.updateAccountParameter, user.getUserName());

        bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, this.updateAccountParameter);

    }

    @Test
    public void testUpdateAccountNotExist()
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        final User user = this.userRepository.save(this.factory.getUser());

        this.updateAccountParameter.setAccountId(this.factory.getRandomInteger());

        Assertions.assertThrows(AccountNotExistException.class,
                () -> this.bankingAccountServiceImpl.updateAccount(this.updateAccountParameter, user.getUserName()));

    }

    @Test
    public void testUpdateGroupAccountNotAdmin() {
        Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);

        final User user2 = this.userRepository.save(this.factory.getUser());

        final BankingAccount bankingAccount = this.bankingAccountRepository.save(this.factory.getAccount(group));

        this.updateAccountParameter.setAccountId(bankingAccount.getId());

        Assertions.assertThrows(NotAdminGroupException.class,
                () -> this.bankingAccountServiceImpl.updateAccount(this.updateAccountParameter, user2.getUserName()));
    }

    @Test
    public void testUpdateUserAccountNotUserAccount()
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        final User user = this.userRepository.save(this.factory.getUser());
        final BankingAccount bankingAccount = this.bankingAccountRepository.save(this.factory.getAccount(user));

        this.updateAccountParameter.setAccountId(bankingAccount.getId());

        final User user2 = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(NotUserAccountException.class,
                () -> this.bankingAccountServiceImpl.updateAccount(this.updateAccountParameter, user2.getUserName()));

    }

    private void checkAccount(final BankingAccountDTO bankingAccountDTO,
                              final BankingAccount bankingAccount,
                              final UpdateAccountParameter updateAccountParameter){

        Assertions.assertEquals(updateAccountParameter.getAbbreviation().toUpperCase(), bankingAccountDTO.getAbbreviation());
        Assertions.assertEquals(updateAccountParameter.getInitialBalance(), bankingAccountDTO.getBalance());
        Assertions.assertEquals(updateAccountParameter.getLabel(), bankingAccountDTO.getLabel());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getCity(),
                bankingAccountDTO.getAddressDTO().getCity());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getStreet(),
                bankingAccountDTO.getAddressDTO().getStreet());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getAddress(),
                bankingAccountDTO.getAddressDTO().getAddress());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getZipCode(),
                bankingAccountDTO.getAddressDTO().getZipCode());
        Assertions.assertEquals(updateAccountParameter.getBankDetailsParameter().getAccountNumber(),
                bankingAccountDTO.getBankDetailsDTO().getAccountNumber());
        Assertions.assertEquals(updateAccountParameter.getBankDetailsParameter().getIban(),
                bankingAccountDTO.getBankDetailsDTO().getIban());

        Assertions.assertEquals(updateAccountParameter.getAbbreviation().toUpperCase(), bankingAccount.getAbbreviation());
        Assertions.assertEquals(updateAccountParameter.getInitialBalance(), bankingAccount.getInitialBalance());
        Assertions.assertEquals(updateAccountParameter.getLabel(), bankingAccount.getLabel());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getCity(),
                bankingAccount.getAddress().getCity());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getStreet(),
                bankingAccount.getAddress().getStreet());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getAddress(),
                bankingAccount.getAddress().getAddress());
        Assertions.assertEquals(updateAccountParameter.getAddressParameter().getZipCode(),
                bankingAccount.getAddress().getZipCode());
        Assertions.assertEquals(updateAccountParameter.getBankDetailsParameter().getAccountNumber(),
                bankingAccount.getBankDetails().getAccountNumber());
        Assertions.assertEquals(updateAccountParameter.getBankDetailsParameter().getIban(),
                bankingAccount.getBankDetails().getIban());

    }

}
