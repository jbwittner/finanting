package fr.finanting.server.service.accountservice;

import fr.finanting.server.dto.BankingAccountDTO;
import fr.finanting.server.exception.*;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Currency;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.UpdateBankingAccountParameter;
import fr.finanting.server.parameter.subpart.AddressParameter;
import fr.finanting.server.parameter.subpart.BankDetailsParameter;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.BankingAccountServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestUpdateBankingAccount extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private BankingAccountRepository bankingAccountRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    private BankingAccountServiceImpl bankingAccountServiceImpl;
    private UpdateBankingAccountParameter updateBankingAccountParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingAccountServiceImpl = new BankingAccountServiceImpl(bankingAccountRepository, groupRepository, userRepository, currencyRepository);

        Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);

        this.updateBankingAccountParameter = new UpdateBankingAccountParameter();
        final AddressParameter addressParameter = new AddressParameter();
        addressParameter.setAddress(this.faker.address().fullAddress());
        addressParameter.setCity(this.faker.address().city());
        addressParameter.setStreet(this.faker.address().streetAddress());
        addressParameter.setZipCode(this.faker.address().zipCode());
        this.updateBankingAccountParameter.setAddressParameter(addressParameter);

        final BankDetailsParameter bankDetailsParameter = new BankDetailsParameter();
        bankDetailsParameter.setAccountNumber(this.factory.getRandomAlphanumericString());
        bankDetailsParameter.setIban(this.factory.getRandomAlphanumericString());
        this.updateBankingAccountParameter.setBankDetailsParameter(bankDetailsParameter);

        this.updateBankingAccountParameter.setInitialBalance(this.factory.getRandomInteger());
        this.updateBankingAccountParameter.setLabel(this.faker.backToTheFuture().quote());
        this.updateBankingAccountParameter.setAbbreviation(this.factory.getRandomAlphanumericString());

        Currency currency = this.currencyRepository.save(this.factory.getCurrency());
        this.updateBankingAccountParameter.setDefaultCurrencyISOCode(currency.getIsoCode());

    }

    @Test
    public void testUpdateGroupAccountOk()
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException, CurrencyNotExistException {
        Group group = this.factory.getGroup();
        final User user = this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);

        BankingAccount bankingAccount = this.factory.getBankingAccount(group);
        this.currencyRepository.save(bankingAccount.getDefaultCurrency());
        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        this.updateBankingAccountParameter.setAccountId(bankingAccount.getId());

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.updateAccount(this.updateBankingAccountParameter, user.getUserName());

        bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, this.updateBankingAccountParameter);

    }

    @Test
    public void testUpdateUserAccountOk()
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException, CurrencyNotExistException {
        final User user = this.userRepository.save(this.factory.getUser());

        BankingAccount bankingAccount = this.factory.getBankingAccount(user);
        this.currencyRepository.save(bankingAccount.getDefaultCurrency());
        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        this.updateBankingAccountParameter.setAccountId(bankingAccount.getId());

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.updateAccount(this.updateBankingAccountParameter, user.getUserName());

        bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, this.updateBankingAccountParameter);

    }

    @Test
    public void testUpdateAccountNotExist() {
        final User user = this.userRepository.save(this.factory.getUser());

        this.updateBankingAccountParameter.setAccountId(this.factory.getRandomInteger());

        Assertions.assertThrows(BankingAccountNotExistException.class,
                () -> this.bankingAccountServiceImpl.updateAccount(this.updateBankingAccountParameter, user.getUserName()));

    }

    @Test
    public void testUpdateGroupAccountNotAdmin() {
        Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);

        final User user2 = this.userRepository.save(this.factory.getUser());

        BankingAccount bankingAccount = this.factory.getBankingAccount(group);
        this.currencyRepository.save(bankingAccount.getDefaultCurrency());
        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        this.updateBankingAccountParameter.setAccountId(bankingAccount.getId());

        Assertions.assertThrows(NotAdminGroupException.class,
                () -> this.bankingAccountServiceImpl.updateAccount(this.updateBankingAccountParameter, user2.getUserName()));
    }

    @Test
    public void testUpdateUserAccountNotUserAccount() {
        final User user = this.userRepository.save(this.factory.getUser());
        
        BankingAccount bankingAccount = this.factory.getBankingAccount(user);
        this.currencyRepository.save(bankingAccount.getDefaultCurrency());
        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        this.updateBankingAccountParameter.setAccountId(bankingAccount.getId());

        final User user2 = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(NotUserBankingAccountException.class,
                () -> this.bankingAccountServiceImpl.updateAccount(this.updateBankingAccountParameter, user2.getUserName()));

    }

    @Test
    public void testUpdateUserAccountISOCodeNotExiste() {
        final User user = this.userRepository.save(this.factory.getUser());
        
        BankingAccount bankingAccount = this.factory.getBankingAccount(user);
        this.currencyRepository.save(bankingAccount.getDefaultCurrency());
        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        this.updateBankingAccountParameter.setAccountId(bankingAccount.getId());
        this.updateBankingAccountParameter.setDefaultCurrencyISOCode(this.factory.getRandomAlphanumericString());

        Assertions.assertThrows(CurrencyNotExistException.class,
                () -> this.bankingAccountServiceImpl.updateAccount(this.updateBankingAccountParameter, user.getUserName()));

    }

    private void checkAccount(final BankingAccountDTO bankingAccountDTO,
                              final BankingAccount bankingAccount,
                              final UpdateBankingAccountParameter updateBankingAccountParameter){

        Assertions.assertEquals(updateBankingAccountParameter.getAbbreviation().toUpperCase(), bankingAccountDTO.getAbbreviation());
        Assertions.assertEquals(updateBankingAccountParameter.getInitialBalance(), bankingAccountDTO.getBalance());
        Assertions.assertEquals(updateBankingAccountParameter.getLabel(), bankingAccountDTO.getLabel());
        Assertions.assertEquals(updateBankingAccountParameter.getAddressParameter().getCity(),
                bankingAccountDTO.getAddressDTO().getCity());
        Assertions.assertEquals(updateBankingAccountParameter.getAddressParameter().getStreet(),
                bankingAccountDTO.getAddressDTO().getStreet());
        Assertions.assertEquals(updateBankingAccountParameter.getAddressParameter().getAddress(),
                bankingAccountDTO.getAddressDTO().getAddress());
        Assertions.assertEquals(updateBankingAccountParameter.getAddressParameter().getZipCode(),
                bankingAccountDTO.getAddressDTO().getZipCode());
        Assertions.assertEquals(updateBankingAccountParameter.getBankDetailsParameter().getAccountNumber(),
                bankingAccountDTO.getBankDetailsDTO().getAccountNumber());
        Assertions.assertEquals(updateBankingAccountParameter.getBankDetailsParameter().getIban(),
                bankingAccountDTO.getBankDetailsDTO().getIban());

        Assertions.assertEquals(updateBankingAccountParameter.getDefaultCurrencyISOCode(),
                bankingAccountDTO.getDefaultCurrencyDTO().getIsoCode());

        Assertions.assertEquals(updateBankingAccountParameter.getAbbreviation().toUpperCase(), bankingAccount.getAbbreviation());
        Assertions.assertEquals(updateBankingAccountParameter.getInitialBalance(), bankingAccount.getInitialBalance());
        Assertions.assertEquals(updateBankingAccountParameter.getLabel(), bankingAccount.getLabel());
        Assertions.assertEquals(updateBankingAccountParameter.getAddressParameter().getCity(),
                bankingAccount.getAddress().getCity());
        Assertions.assertEquals(updateBankingAccountParameter.getAddressParameter().getStreet(),
                bankingAccount.getAddress().getStreet());
        Assertions.assertEquals(updateBankingAccountParameter.getAddressParameter().getAddress(),
                bankingAccount.getAddress().getAddress());
        Assertions.assertEquals(updateBankingAccountParameter.getAddressParameter().getZipCode(),
                bankingAccount.getAddress().getZipCode());
        Assertions.assertEquals(updateBankingAccountParameter.getBankDetailsParameter().getAccountNumber(),
                bankingAccount.getBankDetails().getAccountNumber());
        Assertions.assertEquals(updateBankingAccountParameter.getBankDetailsParameter().getIban(),
                bankingAccount.getBankDetails().getIban());

        Assertions.assertEquals(updateBankingAccountParameter.getDefaultCurrencyISOCode(),
                bankingAccount.getDefaultCurrency().getIsoCode());

    }

}
