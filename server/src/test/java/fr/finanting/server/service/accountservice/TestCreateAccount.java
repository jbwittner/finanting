package fr.finanting.server.service.accountservice;

import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.CreateAccountParameter;
import fr.finanting.server.parameter.subpart.AddressParameter;
import fr.finanting.server.parameter.subpart.BankDetailsParameter;
import fr.finanting.server.repository.AccountRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.AccountServiceImpl;
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
    private AccountRepository accountRepository;

    private AccountServiceImpl accountServiceImpl;

    private User user;
    private Group group;
    private CreateAccountParameter createAccountParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.accountServiceImpl = new AccountServiceImpl(accountRepository, groupRepository, userRepository);
        this.group = this.factory.getGroup();
        this.user = this.userRepository.save(this.group.getUserAdmin());
        this.group = this.groupRepository.save(this.group);

        this.createAccountParameter = new CreateAccountParameter();
        this.createAccountParameter.setAbbreviation(this.factory.getRandomAlphanumericString());

        AddressParameter addressParameter = new AddressParameter();
        addressParameter.setAddress(this.faker.address().fullAddress());
        addressParameter.setCity(this.faker.address().city());
        addressParameter.setStreet(this.faker.address().streetAddress());
        addressParameter.setZipCode(this.faker.address().zipCode());
        this.createAccountParameter.setAddressParameter(addressParameter);

        BankDetailsParameter bankDetailsParameter = new BankDetailsParameter();
        bankDetailsParameter.setAccountNumber(this.factory.getRandomAlphanumericString());
        bankDetailsParameter.setIban(this.factory.getRandomAlphanumericString());
        this.createAccountParameter.setBankDetailsParameter(bankDetailsParameter);

        this.createAccountParameter.setInitialBalance(this.factory.getRandomInteger());
        this.createAccountParameter.setLabel(this.faker.backToTheFuture().quote());
    }

    @Test
    public void testCreateUserAccountOk() throws UserNotExistException, GroupNotExistException {
        
        this.accountServiceImpl.createAccount(createAccountParameter, this.user.getUserName());
    }

    @Test
    public void testCreateUserAccountUserNotExist() {
        
        Assertions.assertThrows(UserNotExistException.class,
            () -> this.accountServiceImpl.createAccount(createAccountParameter, this.factory.getRandomAlphanumericString()));
    }

    @Test
    public void testCreateGroupAccountGroupNotExist() {

        this.createAccountParameter.setGroupeName(this.factory.getRandomAlphanumericString());
        
        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.accountServiceImpl.createAccount(createAccountParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateGroupAccountOk() throws UserNotExistException, GroupNotExistException {

        this.createAccountParameter.setGroupeName(this.group.getGroupName());
        
        this.accountServiceImpl.createAccount(this.createAccountParameter, this.user.getUserName());
    }
    
}
