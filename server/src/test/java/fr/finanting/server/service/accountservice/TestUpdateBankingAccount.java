package fr.finanting.server.service.accountservice;

import fr.finanting.server.generated.model.AddressParameter;
import fr.finanting.server.generated.model.BankDetailsParameter;
import fr.finanting.server.generated.model.BankingAccountDTO;
import fr.finanting.server.generated.model.UpdateBankingAccountParameter;
import fr.finanting.server.exception.*;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Currency;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.*;
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

    @Autowired
    private BankingTransactionRepository bankingTransactionRepository;

    private BankingAccountServiceImpl bankingAccountServiceImpl;
    private UpdateBankingAccountParameter updateBankingAccountParameter;

    @Override
    protected void initDataBeforeEach() {
        this.bankingAccountServiceImpl = new BankingAccountServiceImpl(bankingAccountRepository, groupRepository, userRepository, currencyRepository, bankingTransactionRepository);

        this.updateBankingAccountParameter = new UpdateBankingAccountParameter();
        final AddressParameter addressParameter = new AddressParameter();
        addressParameter.setAddress(this.faker.address().fullAddress());
        addressParameter.setCity(this.faker.address().city());
        addressParameter.setStreet(this.faker.address().streetAddress());
        addressParameter.setZipCode(this.faker.address().zipCode());
        this.updateBankingAccountParameter.setAddressParameter(addressParameter);

        final BankDetailsParameter bankDetailsParameter = new BankDetailsParameter();
        bankDetailsParameter.setAccountNumber(this.testFactory.getRandomAlphanumericString());
        bankDetailsParameter.setIban(this.testFactory.getRandomAlphanumericString());
        this.updateBankingAccountParameter.setBankDetailsParameter(bankDetailsParameter);

        this.updateBankingAccountParameter.setInitialBalance(this.testFactory.getRandomDouble());
        this.updateBankingAccountParameter.setLabel(this.faker.backToTheFuture().quote());
        this.updateBankingAccountParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString());

        final Currency currency = this.currencyRepository.save(this.testFactory.getCurrency());
        this.updateBankingAccountParameter.setDefaultCurrencyISOCode(currency.getIsoCode());

    }

    @Test
    public void testUpdateGroupAccountOk()
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException, CurrencyNotExistException {
        Group group = this.testFactory.getGroup();
        final User user = this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);

        BankingAccount bankingAccount = this.testFactory.getBankingAccount(group);
        this.currencyRepository.save(bankingAccount.getDefaultCurrency());
        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.updateAccount(bankingAccount.getId(), this.updateBankingAccountParameter, user.getUserName());

        bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, this.updateBankingAccountParameter);

    }

    @Test
    public void testUpdateUserAccountOk()
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException, CurrencyNotExistException {
        final User user = this.testFactory.getUser();
        BankingAccount bankingAccount = this.testFactory.getBankingAccount(user);

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.updateAccount(bankingAccount.getId(),
                        this.updateBankingAccountParameter, user.getUserName());

        bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, this.updateBankingAccountParameter);

    }

    @Test
    public void testUpdateAccountNotExist() {
        final User user = this.testFactory.getUser();


        Assertions.assertThrows(BankingAccountNotExistException.class,
                () -> this.bankingAccountServiceImpl.updateAccount(this.testFactory.getRandomInteger(),
                        this.updateBankingAccountParameter, user.getUserName()));

    }

    @Test
    public void testUpdateGroupAccountNotAdmin() {
        final Group group = this.testFactory.getGroup();
        final User user2 = this.testFactory.getUser();
        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(group);

        Assertions.assertThrows(NotAdminGroupException.class,
                () -> this.bankingAccountServiceImpl.updateAccount(bankingAccount.getId(),
                        this.updateBankingAccountParameter, user2.getUserName()));
    }

    @Test
    public void testUpdateUserAccountNotUserAccount() {
        final User user = this.testFactory.getUser();
        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(user);
        final User user2 = this.testFactory.getUser();

        Assertions.assertThrows(NotUserBankingAccountException.class,
                () -> this.bankingAccountServiceImpl.updateAccount(bankingAccount.getId(),
                        this.updateBankingAccountParameter, user2.getUserName()));

    }

    @Test
    public void testUpdateUserAccountISOCodeNotExist() {
        final User user = this.testFactory.getUser();
        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(user);

        this.updateBankingAccountParameter.setDefaultCurrencyISOCode(this.testFactory.getRandomAlphanumericString());

        Assertions.assertThrows(CurrencyNotExistException.class,
                () -> this.bankingAccountServiceImpl.updateAccount(bankingAccount.getId(),
                        this.updateBankingAccountParameter, user.getUserName()));

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
                bankingAccountDTO.getCurrencyDTO().getIsoCode());

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
