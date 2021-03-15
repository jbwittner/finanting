package fr.finanting.server.service.userservice;

import java.util.ArrayList;
import java.util.List;

import com.github.javafaker.Name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.finanting.server.exception.BadPasswordException;
import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.PasswordUpdateParameter;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.UserServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

/**
 * Test class to test updatePassword method
 */
public class TestUpdatePassword extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    private User user;
    private String previousPassword;


    @Override
    protected void initDataBeforeEach() throws Exception {
        this.userService = new UserServiceImpl(this.userRepository, this.passwordEncoder);

        this.user = new User();
        final Name name = this.factory.getUniqueRandomName();
        this.user.setUserName(name.username());
        this.user.setFirstName(name.firstName());
        this.user.setLastName(name.lastName());
        this.previousPassword = this.factory.getUniqueRandomAlphanumericString();
        this.user.setPassword(this.passwordEncoder.encode(this.previousPassword));
        this.user.setEmail(this.factory.getUniqueRandomEmail());

        final List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        this.user.setRoles(roles);

        this.userRepository.save(this.user);

    }
    
    /**
     * Test with good old password
     */
    @Test
    public void testUpdatePasword() throws BadPasswordException {
        final String newPassword = this.factory.getUniqueRandomAlphanumericString();

        final PasswordUpdateParameter passwordUpdateParameter = new PasswordUpdateParameter();
        passwordUpdateParameter.setNewPassword(newPassword);
        passwordUpdateParameter.setPreviousPassword(this.previousPassword);

        this.userService.updatePassword(passwordUpdateParameter, this.user.getUserName());

        final User userUpdated = this.userRepository.findByUserName(this.user.getUserName()).get();

        Assertions.assertTrue(this.passwordEncoder.matches(newPassword, userUpdated.getPassword()));
        Assertions.assertFalse(this.passwordEncoder.matches(this.previousPassword, userUpdated.getPassword()));

    }

    /**
     * Test with bad old password
     */
    @Test
    public void testBadOldPasword() throws BadPasswordException {
        final String newPassword = this.factory.getUniqueRandomAlphanumericString();

        final PasswordUpdateParameter passwordUpdateParameter = new PasswordUpdateParameter();
        passwordUpdateParameter.setNewPassword(newPassword);
        passwordUpdateParameter.setPreviousPassword(this.factory.getUniqueRandomAlphanumericString());

        Assertions.assertThrows(BadPasswordException.class,
            () -> this.userService.updatePassword(passwordUpdateParameter, this.user.getUserName()));

    }

}
