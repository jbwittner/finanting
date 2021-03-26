package fr.finanting.server.service.accountservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.dto.GroupDTO;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.NotAdminGroupException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.AddUsersGroupParameter;
import fr.finanting.server.parameter.CreateAccountParameter;
import fr.finanting.server.parameter.subpart.AddressParameter;
import fr.finanting.server.parameter.subpart.BankDetailsParameter;
import fr.finanting.server.repository.AccountRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.AccountServiceImpl;
import fr.finanting.server.service.implementation.GroupServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestCreateAccount extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AccountRepository accountRepository;

    private AccountServiceImpl accountServiceImpl;

    private User user;
    private CreateAccountParameter createAccountParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.accountServiceImpl = new AccountServiceImpl(accountRepository, groupRepository, userRepository);
        this.user = this.userRepository.save(this.factory.getUser());

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
    public void testCreateUserAccountUserNotExist() throws UserNotExistException, GroupNotExistException {
        
        Assertions.assertThrows(UserNotExistException.class,
            () -> this.accountServiceImpl.createAccount(createAccountParameter, this.factory.getRandomAlphanumericString()));
    }

    @Test
    public void testCreateGroupeAccountGroupNotExist() throws UserNotExistException, GroupNotExistException {

        this.createAccountParameter.setGroupeName(this.factory.getRandomAlphanumericString());
        
        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.accountServiceImpl.createAccount(createAccountParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateGroupeAccountOk() throws UserNotExistException, GroupNotExistException {

        Group group = this.factory.getGroup();
        group.setUserAdmin(this.user);
        group = this.groupRepository.save(group);

        this.createAccountParameter.setGroupeName(group.getGroupName());
        
        this.accountServiceImpl.createAccount(createAccountParameter, this.user.getUserName());
    }
    
}
