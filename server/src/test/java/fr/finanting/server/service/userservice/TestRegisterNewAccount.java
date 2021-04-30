package fr.finanting.server.service.userservice;

import com.github.javafaker.Name;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.UserRegisterParameter;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.UserServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestRegisterNewAccount extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    private UserRegisterParameter newUserRegisterParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.userService = new UserServiceImpl(this.userRepository, this.passwordEncoder);

        this.newUserRegisterParameter = new UserRegisterParameter();
        this.newUserRegisterParameter.setEmail(this.testFactory.getUniqueRandomEmail());
        final Name name = this.testFactory.getUniqueRandomName();
        this.newUserRegisterParameter.setUserName(name.username());
        this.newUserRegisterParameter.setFirstName(name.firstName());
        this.newUserRegisterParameter.setLastName(name.lastName());
        this.newUserRegisterParameter.setPassword(this.testFactory.getRandomAlphanumericString());
        this.newUserRegisterParameter.setEmail(this.testFactory.getUniqueRandomEmail());
        
    }

    @Test
    public void testRegisterFirstNewAccount() throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        this.userService.registerNewAccount(this.newUserRegisterParameter);

        final User user = this.userRepository.findByUserName(this.newUserRegisterParameter.getUserName()).orElseThrow();

        final String userNameToCheck = newUserRegisterParameter.getUserName().toLowerCase();
        final String firstNameToCheck = StringUtils.capitalize(newUserRegisterParameter.getFirstName().toLowerCase());

        Assertions.assertEquals(this.newUserRegisterParameter.getEmail(), user.getEmail());
        Assertions.assertEquals(firstNameToCheck, user.getFirstName());
        Assertions.assertEquals(this.newUserRegisterParameter.getLastName().toUpperCase(), user.getLastName());
        Assertions.assertTrue(this.passwordEncoder.matches(this.newUserRegisterParameter.getPassword(), user.getPassword()));
        Assertions.assertEquals(userNameToCheck, user.getUserName());
        Assertions.assertEquals(2, user.getRoles().size());

        Assertions.assertEquals(Role.ADMIN.toString(), user.getRoles().get(0).toString());
        Assertions.assertEquals(Role.USER.toString(), user.getRoles().get(1).toString());

    }

    @Test
    public void testRegisterAnotherNewAccount() throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        this.testFactory.getUser();

        this.userService.registerNewAccount(this.newUserRegisterParameter);

        final User user = this.userRepository.findByUserName(this.newUserRegisterParameter.getUserName()).orElseThrow();

        final String userNameToCheck = newUserRegisterParameter.getUserName().toLowerCase();
        final String firstNameToCheck = StringUtils.capitalize(newUserRegisterParameter.getFirstName().toLowerCase());

        Assertions.assertEquals(this.newUserRegisterParameter.getEmail(), user.getEmail());
        Assertions.assertEquals(firstNameToCheck, user.getFirstName());
        Assertions.assertEquals(this.newUserRegisterParameter.getLastName().toUpperCase(), user.getLastName());
        Assertions.assertTrue(this.passwordEncoder.matches(this.newUserRegisterParameter.getPassword(), user.getPassword()));
        Assertions.assertEquals(userNameToCheck, user.getUserName());
        Assertions.assertEquals(1, user.getRoles().size());

        Assertions.assertEquals(Role.USER.toString(), user.getRoles().get(0).toString());

    }

    @Test
    public void testRegisterEmailAlreadyUsed() throws UserEmailAlreadyExistException, UserNameAlreadyExistException {

        final User user = this.testFactory.getUser();
        this.newUserRegisterParameter.setEmail(user.getEmail());

        Assertions.assertThrows(UserEmailAlreadyExistException.class,
            () -> this.userService.registerNewAccount(this.newUserRegisterParameter));

    }

    @Test
    public void testRegisterUserNameAlreadyUsed() throws UserEmailAlreadyExistException, UserNameAlreadyExistException {

        final User user = this.testFactory.getUser();
        this.newUserRegisterParameter.setUserName(user.getUserName());

        Assertions.assertThrows(UserNameAlreadyExistException.class,
            () -> this.userService.registerNewAccount(this.newUserRegisterParameter));

    }
    
}
