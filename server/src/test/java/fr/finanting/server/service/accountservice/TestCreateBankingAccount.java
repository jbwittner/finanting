package fr.finanting.server.service.accountservice;

import fr.finanting.server.codegen.model.AddressParameter;
import fr.finanting.server.codegen.model.BankDetailsParameter;
import fr.finanting.server.dto.BankingAccountDTO;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Currency;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.CreateBankingAccountParameter;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
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

    private BankingAccountServiceImpl bankingAccountServiceImpl;

    private User user;
    private Group group;
    private CreateBankingAccountParameter createBankingAccountParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingAccountServiceImpl = new BankingAccountServiceImpl(bankingAccountRepository, groupRepository, userRepository, currencyRepository);
        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(this.user);

        this.createBankingAccountParameter = new CreateBankingAccountParameter();
        this.createBankingAccountParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString());

        final AddressParameter addressParameter = new AddressParameter();
        addressParameter.setAddress(this.faker.address().fullAddress());
        addressParameter.setCity(this.faker.address().city());
        addressParameter.setStreet(this.faker.address().streetAddress());
        addressParameter.setZipCode(this.faker.address().zipCode());
        this.createBankingAccountParameter.setAddressParameter(addressParameter);

        final BankDetailsParameter bankDetailsParameter = new BankDetailsParameter();
        bankDetailsParameter.setBankName(this.faker.dragonBall().character());
        bankDetailsParameter.setAccountNumber(this.testFactory.getRandomAlphanumericString());
        bankDetailsParameter.setIban(this.testFactory.getRandomAlphanumericString());
        this.createBankingAccountParameter.setBankDetailsParameter(bankDetailsParameter);

        this.createBankingAccountParameter.setInitialBalance(this.testFactory.getRandomInteger());
        this.createBankingAccountParameter.setLabel(this.faker.backToTheFuture().quote());

        final Currency currency = this.currencyRepository.save(this.testFactory.getCurrency());
        this.createBankingAccountParameter.setDefaultCurrencyISOCode(currency.getIsoCode());
    }

    @Test
    public void testCreateUserAccountOk() throws UserNotExistException, GroupNotExistException, CurrencyNotExistException {

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.createAccount(createBankingAccountParameter, this.user.getUserName());

        final BankingAccount bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, createBankingAccountParameter, this.user);
    }

    @Test
    public void testCreateUserAccountWithoutAddress() throws UserNotExistException, GroupNotExistException, CurrencyNotExistException {

        this.createBankingAccountParameter.setAddressParameter(null);

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.createAccount(createBankingAccountParameter, this.user.getUserName());

        final BankingAccount bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, createBankingAccountParameter, this.user);
    }

    @Test
    public void testCreateUserAccountWithoutBankDetails() throws UserNotExistException, GroupNotExistException, CurrencyNotExistException {

        this.createBankingAccountParameter.setBankDetailsParameter(null);

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.createAccount(createBankingAccountParameter, this.user.getUserName());

        final BankingAccount bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, createBankingAccountParameter, this.user);
    }

    @Test
    public void testCreateGroupAccountGroupNotExist() {

        this.createBankingAccountParameter.setGroupName(this.testFactory.getRandomAlphanumericString());
        
        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.bankingAccountServiceImpl.createAccount(createBankingAccountParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateAccountWithCurrencyNotExist() {

        this.createBankingAccountParameter.setDefaultCurrencyISOCode(this.testFactory.getRandomAlphanumericString());
        
        Assertions.assertThrows(CurrencyNotExistException.class,
            () -> this.bankingAccountServiceImpl.createAccount(createBankingAccountParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateGroupAccountOk() throws UserNotExistException, GroupNotExistException, CurrencyNotExistException {

        this.createBankingAccountParameter.setGroupName(this.group.getGroupName());

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.createAccount(this.createBankingAccountParameter, this.user.getUserName());

        final BankingAccount bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, createBankingAccountParameter, this.user);

    }

    private void checkAccount(final BankingAccountDTO bankingAccountDTO,
                              final BankingAccount bankingAccount,
                              final CreateBankingAccountParameter createBankingAccountParameter,
                              final User user){

        Assertions.assertEquals(createBankingAccountParameter.getAbbreviation().toUpperCase(), bankingAccountDTO.getAbbreviation());
        Assertions.assertEquals(createBankingAccountParameter.getInitialBalance(), bankingAccountDTO.getBalance());
        Assertions.assertEquals(createBankingAccountParameter.getLabel(), bankingAccountDTO.getLabel());

        if(createBankingAccountParameter.getAddressParameter() != null){
                Assertions.assertEquals(createBankingAccountParameter.getAddressParameter().getCity(), bankingAccountDTO.getAddressDTO().getCity());
                Assertions.assertEquals(createBankingAccountParameter.getAddressParameter().getStreet(), bankingAccountDTO.getAddressDTO().getStreet());
                Assertions.assertEquals(createBankingAccountParameter.getAddressParameter().getAddress(), bankingAccountDTO.getAddressDTO().getAddress());
                Assertions.assertEquals(createBankingAccountParameter.getAddressParameter().getZipCode(), bankingAccountDTO.getAddressDTO().getZipCode());
        }

        if(createBankingAccountParameter.getBankDetailsParameter() != null){
                Assertions.assertEquals(createBankingAccountParameter.getBankDetailsParameter().getAccountNumber(), bankingAccountDTO.getBankDetailsDTO().getAccountNumber());
                Assertions.assertEquals(createBankingAccountParameter.getBankDetailsParameter().getIban(), bankingAccountDTO.getBankDetailsDTO().getIban());
                Assertions.assertEquals(createBankingAccountParameter.getBankDetailsParameter().getBankName(), bankingAccountDTO.getBankDetailsDTO().getBankName());
        }

        Assertions.assertEquals(createBankingAccountParameter.getDefaultCurrencyISOCode(), bankingAccountDTO.getDefaultCurrencyDTO().getIsoCode());

        Assertions.assertEquals(createBankingAccountParameter.getAbbreviation().toUpperCase(), bankingAccount.getAbbreviation());
        Assertions.assertEquals(createBankingAccountParameter.getInitialBalance(), bankingAccount.getInitialBalance());
        Assertions.assertEquals(createBankingAccountParameter.getLabel(), bankingAccount.getLabel());

        if(createBankingAccountParameter.getAddressParameter() != null){
                Assertions.assertEquals(createBankingAccountParameter.getAddressParameter().getCity(), bankingAccount.getAddress().getCity());
                Assertions.assertEquals(createBankingAccountParameter.getAddressParameter().getStreet(), bankingAccount.getAddress().getStreet());
                Assertions.assertEquals(createBankingAccountParameter.getAddressParameter().getAddress(), bankingAccount.getAddress().getAddress());
                Assertions.assertEquals(createBankingAccountParameter.getAddressParameter().getZipCode(), bankingAccount.getAddress().getZipCode());
        }

        if(createBankingAccountParameter.getBankDetailsParameter() != null){
                Assertions.assertEquals(createBankingAccountParameter.getBankDetailsParameter().getAccountNumber(), bankingAccount.getBankDetails().getAccountNumber());
                Assertions.assertEquals(createBankingAccountParameter.getBankDetailsParameter().getIban(), bankingAccount.getBankDetails().getIban());
                Assertions.assertEquals(createBankingAccountParameter.getBankDetailsParameter().getBankName(), bankingAccount.getBankDetails().getBankName());
        }

        Assertions.assertEquals(createBankingAccountParameter.getDefaultCurrencyISOCode(),
                bankingAccount.getDefaultCurrency().getIsoCode());

        if(createBankingAccountParameter.getGroupName() == null){
            Assertions.assertEquals(user.getUserName(),
                    bankingAccount.getUser().getUserName());
        } else {
            Assertions.assertEquals(createBankingAccountParameter.getGroupName(),
                    bankingAccount.getGroup().getGroupName());
        }

    }
    
}
