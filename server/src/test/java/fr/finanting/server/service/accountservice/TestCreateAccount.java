package fr.finanting.server.service.accountservice;

import fr.finanting.server.dto.BankingAccountDTO;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.CreateBankingAccountParameter;
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

public class TestCreateAccount extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private BankingAccountRepository bankingAccountRepository;

    private BankingAccountServiceImpl bankingAccountServiceImpl;

    private User user;
    private Group group;
    private CreateBankingAccountParameter createAccountParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingAccountServiceImpl = new BankingAccountServiceImpl(bankingAccountRepository, groupRepository, userRepository);
        this.group = this.factory.getGroup();
        this.user = this.userRepository.save(this.group.getUserAdmin());
        this.group = this.groupRepository.save(this.group);

        this.createAccountParameter = new CreateBankingAccountParameter();
        this.createAccountParameter.setAbbreviation(this.factory.getRandomAlphanumericString());

        final AddressParameter addressParameter = new AddressParameter();
        addressParameter.setAddress(this.faker.address().fullAddress());
        addressParameter.setCity(this.faker.address().city());
        addressParameter.setStreet(this.faker.address().streetAddress());
        addressParameter.setZipCode(this.faker.address().zipCode());
        this.createAccountParameter.setAddressParameter(addressParameter);

        final BankDetailsParameter bankDetailsParameter = new BankDetailsParameter();
        bankDetailsParameter.setAccountNumber(this.factory.getRandomAlphanumericString());
        bankDetailsParameter.setIban(this.factory.getRandomAlphanumericString());
        this.createAccountParameter.setBankDetailsParameter(bankDetailsParameter);

        this.createAccountParameter.setInitialBalance(this.factory.getRandomInteger());
        this.createAccountParameter.setLabel(this.faker.backToTheFuture().quote());
    }

    @Test
    public void testCreateUserAccountOk() throws UserNotExistException, GroupNotExistException {

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.createAccount(createAccountParameter, this.user.getUserName());

        final BankingAccount bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, createAccountParameter, this.user);
    }

    @Test
    public void testCreateUserAccountUserNotExist() {
        
        Assertions.assertThrows(UserNotExistException.class,
            () -> this.bankingAccountServiceImpl.createAccount(createAccountParameter, this.factory.getRandomAlphanumericString()));
    }

    @Test
    public void testCreateGroupAccountGroupNotExist() {

        this.createAccountParameter.setGroupName(this.factory.getRandomAlphanumericString());
        
        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.bankingAccountServiceImpl.createAccount(createAccountParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateGroupAccountOk() throws UserNotExistException, GroupNotExistException {

        this.createAccountParameter.setGroupName(this.group.getGroupName());

        final BankingAccountDTO bankingAccountDTO =
                this.bankingAccountServiceImpl.createAccount(this.createAccountParameter, this.user.getUserName());

        final BankingAccount bankingAccount = this.bankingAccountRepository.findById(bankingAccountDTO.getId()).orElseThrow();

        this.checkAccount(bankingAccountDTO, bankingAccount, createAccountParameter, this.user);

    }

    private void checkAccount(final BankingAccountDTO bankingAccountDTO,
                              final BankingAccount bankingAccount,
                              final CreateBankingAccountParameter createAccountParameter,
                              final User user){

        Assertions.assertEquals(createAccountParameter.getAbbreviation().toUpperCase(), bankingAccountDTO.getAbbreviation());
        Assertions.assertEquals(createAccountParameter.getInitialBalance(), bankingAccountDTO.getBalance());
        Assertions.assertEquals(createAccountParameter.getLabel(), bankingAccountDTO.getLabel());
        Assertions.assertEquals(createAccountParameter.getAddressParameter().getCity(),
                bankingAccountDTO.getAddressDTO().getCity());
        Assertions.assertEquals(createAccountParameter.getAddressParameter().getStreet(),
                bankingAccountDTO.getAddressDTO().getStreet());
        Assertions.assertEquals(createAccountParameter.getAddressParameter().getAddress(),
                bankingAccountDTO.getAddressDTO().getAddress());
        Assertions.assertEquals(createAccountParameter.getAddressParameter().getZipCode(),
                bankingAccountDTO.getAddressDTO().getZipCode());
        Assertions.assertEquals(createAccountParameter.getBankDetailsParameter().getAccountNumber(),
                bankingAccountDTO.getBankDetailsDTO().getAccountNumber());
        Assertions.assertEquals(createAccountParameter.getBankDetailsParameter().getIban(),
                bankingAccountDTO.getBankDetailsDTO().getIban());
        Assertions.assertEquals(createAccountParameter.getBankDetailsParameter().getBankName(),
                bankingAccountDTO.getBankDetailsDTO().getBankName());

        Assertions.assertEquals(createAccountParameter.getAbbreviation().toUpperCase(), bankingAccount.getAbbreviation());
        Assertions.assertEquals(createAccountParameter.getInitialBalance(), bankingAccount.getInitialBalance());
        Assertions.assertEquals(createAccountParameter.getLabel(), bankingAccount.getLabel());
        Assertions.assertEquals(createAccountParameter.getAddressParameter().getCity(),
                bankingAccount.getAddress().getCity());
        Assertions.assertEquals(createAccountParameter.getAddressParameter().getStreet(),
                bankingAccount.getAddress().getStreet());
        Assertions.assertEquals(createAccountParameter.getAddressParameter().getAddress(),
                bankingAccount.getAddress().getAddress());
        Assertions.assertEquals(createAccountParameter.getAddressParameter().getZipCode(),
                bankingAccount.getAddress().getZipCode());
        Assertions.assertEquals(createAccountParameter.getBankDetailsParameter().getAccountNumber(),
                bankingAccount.getBankDetails().getAccountNumber());
        Assertions.assertEquals(createAccountParameter.getBankDetailsParameter().getIban(),
                bankingAccount.getBankDetails().getIban());

        if(createAccountParameter.getGroupName() == null){
            Assertions.assertEquals(user.getUserName(),
                    bankingAccountDTO.getUserDTO().getUserName());

            Assertions.assertEquals(user.getUserName(),
                    bankingAccount.getUser().getUserName());
        } else {
            Assertions.assertEquals(createAccountParameter.getGroupName(),
                    bankingAccountDTO.getGroupDTO().getGroupName());

            Assertions.assertEquals(createAccountParameter.getGroupName(),
                    bankingAccount.getGroup().getGroupName());
        }

    }
    
}
