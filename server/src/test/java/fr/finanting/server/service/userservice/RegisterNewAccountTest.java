package fr.finanting.server.service.userservice;

import com.github.javafaker.Name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.UserRegisterParameter;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.UserServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class RegisterNewAccountTest extends AbstractMotherIntegrationTest {

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
        this.newUserRegisterParameter.setEmail(this.factory.getUniqueRandomEmail());
        Name name = this.factory.getUniqueRandomName();
        this.newUserRegisterParameter.setUserName(name.username());
        this.newUserRegisterParameter.setFirstName(name.firstName());
        this.newUserRegisterParameter.setLastName(name.lastName());
        this.newUserRegisterParameter.setPassword(this.factory.getRandomAlphanumericString());
        this.newUserRegisterParameter.setEmail(this.factory.getUniqueRandomEmail());
        
    }

    @Test
    public void testRegisterNewAccount() throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        this.userService.registerNewAccount(this.newUserRegisterParameter);

        final User user = this.userRepository.findByUserName(this.newUserRegisterParameter.getUserName())
            .orElseThrow(() -> new UsernameNotFoundException(this.newUserRegisterParameter.getUserName()));

        Assertions.assertEquals(this.newUserRegisterParameter.getEmail(), user.getEmail());
        Assertions.assertEquals(this.newUserRegisterParameter.getFirstName(), user.getFirstName());
        Assertions.assertEquals(this.newUserRegisterParameter.getLastName(), user.getLastName());
        Assertions.assertTrue(this.passwordEncoder.matches(this.newUserRegisterParameter.getPassword(), user.getPassword()));
        Assertions.assertEquals(this.newUserRegisterParameter.getUserName(), user.getUserName());
        for(final Role role : user.getRoles()){
            Assertions.assertEquals(Role.USER.toString(), role.toString());
        }
    }

    @Test
    public void testRegisterEmailAlreadyUsed() throws UserEmailAlreadyExistException, UserNameAlreadyExistException {

        User user = new User();
        Name name = this.factory.getUniqueRandomName();
        user.setUserName(name.username());
        user.setFirstName(name.firstName());
        user.setLastName(name.lastName());
        user.setPassword(this.passwordEncoder.encode(this.factory.getRandomAlphanumericString()));
        user.setEmail(this.newUserRegisterParameter.getEmail());
        this.userRepository.save(user);

        Assertions.assertThrows(UserEmailAlreadyExistException.class,
            () -> this.userService.registerNewAccount(this.newUserRegisterParameter));

    }

    @Test
    public void testRegisterUserNameAlreadyUsed() throws UserEmailAlreadyExistException, UserNameAlreadyExistException {

        User user = new User();
        Name name = this.factory.getUniqueRandomName();
        user.setUserName(this.newUserRegisterParameter.getUserName());
        user.setFirstName(name.firstName());
        user.setLastName(name.lastName());
        user.setPassword(this.passwordEncoder.encode(this.factory.getRandomAlphanumericString()));
        user.setEmail(this.factory.getUniqueRandomEmail());
        this.userRepository.save(user);

        Assertions.assertThrows(UserNameAlreadyExistException.class,
            () -> this.userService.registerNewAccount(this.newUserRegisterParameter));

    }
    
}
