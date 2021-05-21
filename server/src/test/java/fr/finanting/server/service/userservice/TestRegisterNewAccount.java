package fr.finanting.server.service.userservice;

import com.github.javafaker.Name;

import fr.finanting.server.codegen.model.UserRegistrationParameter;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.UserServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestRegisterNewAccount extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    private UserRegistrationParameter newUserRegistrationParameter;

    @Override
    protected void initDataBeforeEach() {
        this.userService = new UserServiceImpl(this.userRepository, this.passwordEncoder);

        this.newUserRegistrationParameter = new UserRegistrationParameter();
        this.newUserRegistrationParameter.setEmail(this.testFactory.getUniqueRandomEmail());
        final Name name = this.testFactory.getUniqueRandomName();
        this.newUserRegistrationParameter.setUserName(name.username());
        this.newUserRegistrationParameter.setFirstName(name.firstName());
        this.newUserRegistrationParameter.setLastName(name.lastName());
        this.newUserRegistrationParameter.setPassword(this.testFactory.getRandomAlphanumericString());
        this.newUserRegistrationParameter.setEmail(this.testFactory.getUniqueRandomEmail());
        
    }

    @Test
    public void testRegisterFirstNewAccount() throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        this.userService.registerNewAccount(this.newUserRegistrationParameter);

        final User user = this.userRepository.findByUserName(this.newUserRegistrationParameter.getUserName()).orElseThrow();

        final String userNameToCheck = newUserRegistrationParameter.getUserName().toLowerCase();
        final String firstNameToCheck = StringUtils.capitalize(newUserRegistrationParameter.getFirstName().toLowerCase());

        Assertions.assertEquals(this.newUserRegistrationParameter.getEmail(), user.getEmail());
        Assertions.assertEquals(firstNameToCheck, user.getFirstName());
        Assertions.assertEquals(this.newUserRegistrationParameter.getLastName().toUpperCase(), user.getLastName());
        Assertions.assertTrue(this.passwordEncoder.matches(this.newUserRegistrationParameter.getPassword(), user.getPassword()));
        Assertions.assertEquals(userNameToCheck, user.getUserName());
        Assertions.assertEquals(2, user.getRoles().size());

        Assertions.assertEquals(Role.ADMIN.toString(), user.getRoles().get(0).toString());
        Assertions.assertEquals(Role.USER.toString(), user.getRoles().get(1).toString());

    }

    @Test
    public void testRegisterAnotherNewAccount() throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        this.testFactory.getUser();

        this.userService.registerNewAccount(this.newUserRegistrationParameter);

        final User user = this.userRepository.findByUserName(this.newUserRegistrationParameter.getUserName()).orElseThrow();

        final String userNameToCheck = newUserRegistrationParameter.getUserName().toLowerCase();
        final String firstNameToCheck = StringUtils.capitalize(newUserRegistrationParameter.getFirstName().toLowerCase());

        Assertions.assertEquals(this.newUserRegistrationParameter.getEmail(), user.getEmail());
        Assertions.assertEquals(firstNameToCheck, user.getFirstName());
        Assertions.assertEquals(this.newUserRegistrationParameter.getLastName().toUpperCase(), user.getLastName());
        Assertions.assertTrue(this.passwordEncoder.matches(this.newUserRegistrationParameter.getPassword(), user.getPassword()));
        Assertions.assertEquals(userNameToCheck, user.getUserName());
        Assertions.assertEquals(1, user.getRoles().size());

        Assertions.assertEquals(Role.USER.toString(), user.getRoles().get(0).toString());

    }

    @Test
    public void testRegisterEmailAlreadyUsed() throws UserEmailAlreadyExistException, UserNameAlreadyExistException {

        final User user = this.testFactory.getUser();
        this.newUserRegistrationParameter.setEmail(user.getEmail());

        Assertions.assertThrows(UserEmailAlreadyExistException.class,
            () -> this.userService.registerNewAccount(this.newUserRegistrationParameter));

    }

    @Test
    public void testRegisterUserNameAlreadyUsed() throws UserEmailAlreadyExistException, UserNameAlreadyExistException {

        final User user = this.testFactory.getUser();
        this.newUserRegistrationParameter.setUserName(user.getUserName());

        Assertions.assertThrows(UserNameAlreadyExistException.class,
            () -> this.userService.registerNewAccount(this.newUserRegistrationParameter));

    }
    
}
