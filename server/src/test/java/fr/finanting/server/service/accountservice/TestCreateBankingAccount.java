package fr.finanting.server.service.accountservice;

import fr.finanting.server.codegen.model.AddressParameter;
import fr.finanting.server.codegen.model.BankDetailsParameter;
import fr.finanting.server.codegen.model.BankingAccountDTO;
import fr.finanting.server.codegen.model.BankingAccountParameter;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotExistException;
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

public class TestCreateBankingAccount extends AbstractMotherIntegrationTest {

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

    private User user;
    private Group group;
    private BankingAccountParameter bankingAccountParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingAccountServiceImpl = new BankingAccountServiceImpl(bankingAccountRepository, groupRepository, userRepository, currencyRepository, bankingTransactionRepository);
        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(this.user);

        this.bankingAccountParameter = new BankingAccountParameter();
        this.bankingAccountParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString());

        final AddressParameter addressParameter = new AddressParameter();
        addressParameter.setAddress(this.faker.address().fullAddress());
        addressParameter.setCity(this.faker.address().city());
        addressParameter.setStreet(this.faker.address().streetAddress());
        addressParameter.setZipCode(this.faker.address().zipCode());
        this.bankingAccountParameter.setAddressParameter(addressParameter);

        final BankDetailsParameter bankDetailsParameter = new BankDetailsParameter();
        bankDetailsParameter.setBankName(this.faker.dragonBall().character());
        bankDetailsParameter.setAccountNumber(this.testFactory.getRandomAlphanumericString());
        bankDetailsParameter.setIban(this.testFactory.getRandomAlphanumericString());
        this.bankingAccountParameter.setBankDetailsParameter(bankDetailsParameter);

        this.bankingAccountParameter.setInitialBalance(this.testFactory.getRandomDouble());
        this.bankingAccountParameter.setLabel(this.faker.backToTheFuture().quote());

        final Currency currency = this.currencyRepository.save(this.testFactory.getCurrency());
        this.bankingAccountParameter.setDefaultCurrencyISOCode(currency.getIsoCode());
    }

    @Test
    public void testCreateUserAccountOk() throws UserNotExistException, GroupNotExistException, CurrencyNotExistException {

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.createAccount(bankingAccountParameter, this.user.getUserName());

        final BankingAccount bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, bankingAccountParameter, this.user);
    }

    @Test
    public void testCreateUserAccountWithoutAddress() throws UserNotExistException, GroupNotExistException, CurrencyNotExistException {

        this.bankingAccountParameter.setAddressParameter(null);

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.createAccount(bankingAccountParameter, this.user.getUserName());

        final BankingAccount bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, bankingAccountParameter, this.user);
    }

    @Test
    public void testCreateUserAccountWithoutBankDetails() throws UserNotExistException, GroupNotExistException, CurrencyNotExistException {

        this.bankingAccountParameter.setBankDetailsParameter(null);

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.createAccount(bankingAccountParameter, this.user.getUserName());

        final BankingAccount bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, bankingAccountParameter, this.user);
    }

    @Test
    public void testCreateGroupAccountGroupNotExist() {

        this.bankingAccountParameter.setGroupName(this.testFactory.getRandomAlphanumericString());
        
        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.bankingAccountServiceImpl.createAccount(bankingAccountParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateAccountWithCurrencyNotExist() {

        this.bankingAccountParameter.setDefaultCurrencyISOCode(this.testFactory.getRandomAlphanumericString());
        
        Assertions.assertThrows(CurrencyNotExistException.class,
            () -> this.bankingAccountServiceImpl.createAccount(bankingAccountParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateGroupAccountOk() throws UserNotExistException, GroupNotExistException, CurrencyNotExistException {

        this.bankingAccountParameter.setGroupName(this.group.getGroupName());

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.createAccount(this.bankingAccountParameter, this.user.getUserName());

        final BankingAccount bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, bankingAccountParameter, this.user);

    }

    private void checkAccount(final BankingAccountDTO bankingAccountDTO,
                              final BankingAccount bankingAccount,
                              final BankingAccountParameter bankingAccountParameter,
                              final User user){

        Assertions.assertEquals(bankingAccountParameter.getAbbreviation().toUpperCase(), bankingAccountDTO.getAbbreviation());
        Assertions.assertEquals(bankingAccountParameter.getInitialBalance(), bankingAccountDTO.getBalance());
        Assertions.assertEquals(bankingAccountParameter.getLabel(), bankingAccountDTO.getLabel());

        if(bankingAccountParameter.getAddressParameter() != null){
                Assertions.assertEquals(bankingAccountParameter.getAddressParameter().getCity(), bankingAccountDTO.getAddressDTO().getCity());
                Assertions.assertEquals(bankingAccountParameter.getAddressParameter().getStreet(), bankingAccountDTO.getAddressDTO().getStreet());
                Assertions.assertEquals(bankingAccountParameter.getAddressParameter().getAddress(), bankingAccountDTO.getAddressDTO().getAddress());
                Assertions.assertEquals(bankingAccountParameter.getAddressParameter().getZipCode(), bankingAccountDTO.getAddressDTO().getZipCode());
        }

        if(bankingAccountParameter.getBankDetailsParameter() != null){
                Assertions.assertEquals(bankingAccountParameter.getBankDetailsParameter().getAccountNumber(), bankingAccountDTO.getBankDetailsDTO().getAccountNumber());
                Assertions.assertEquals(bankingAccountParameter.getBankDetailsParameter().getIban(), bankingAccountDTO.getBankDetailsDTO().getIban());
                Assertions.assertEquals(bankingAccountParameter.getBankDetailsParameter().getBankName(), bankingAccountDTO.getBankDetailsDTO().getBankName());
        }

        Assertions.assertEquals(bankingAccountParameter.getDefaultCurrencyISOCode(), bankingAccountDTO.getCurrencyDTO().getIsoCode());

        Assertions.assertEquals(bankingAccountParameter.getAbbreviation().toUpperCase(), bankingAccount.getAbbreviation());
        Assertions.assertEquals(bankingAccountParameter.getInitialBalance(), bankingAccount.getInitialBalance());
        Assertions.assertEquals(bankingAccountParameter.getLabel(), bankingAccount.getLabel());

        if(bankingAccountParameter.getAddressParameter() != null){
                Assertions.assertEquals(bankingAccountParameter.getAddressParameter().getCity(), bankingAccount.getAddress().getCity());
                Assertions.assertEquals(bankingAccountParameter.getAddressParameter().getStreet(), bankingAccount.getAddress().getStreet());
                Assertions.assertEquals(bankingAccountParameter.getAddressParameter().getAddress(), bankingAccount.getAddress().getAddress());
                Assertions.assertEquals(bankingAccountParameter.getAddressParameter().getZipCode(), bankingAccount.getAddress().getZipCode());
        }

        if(bankingAccountParameter.getBankDetailsParameter() != null){
                Assertions.assertEquals(bankingAccountParameter.getBankDetailsParameter().getAccountNumber(), bankingAccount.getBankDetails().getAccountNumber());
                Assertions.assertEquals(bankingAccountParameter.getBankDetailsParameter().getIban(), bankingAccount.getBankDetails().getIban());
                Assertions.assertEquals(bankingAccountParameter.getBankDetailsParameter().getBankName(), bankingAccount.getBankDetails().getBankName());
        }

        Assertions.assertEquals(bankingAccountParameter.getDefaultCurrencyISOCode(),
                bankingAccount.getDefaultCurrency().getIsoCode());

        if(bankingAccountParameter.getGroupName() == null){
            Assertions.assertEquals(user.getUserName(),
                    bankingAccount.getUser().getUserName());
        } else {
            Assertions.assertEquals(bankingAccountParameter.getGroupName(),
                    bankingAccount.getGroup().getGroupName());
        }

    }
    
}
