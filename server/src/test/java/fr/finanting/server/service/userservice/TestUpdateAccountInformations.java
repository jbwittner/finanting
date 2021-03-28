package fr.finanting.server.service.userservice;

import java.util.ArrayList;
import java.util.List;

import com.github.javafaker.Name;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.finanting.server.dto.UserDTO;
import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.UserUpdateParameter;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.UserServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

/**
 * Test class to test updateAccountInformations method
 */
public class TestUpdateAccountInformations extends AbstractMotherIntegrationTest{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    private User userOne;
    private User userTwo;


    @Override
    protected void initDataBeforeEach() throws Exception {
        this.userService = new UserServiceImpl(this.userRepository, this.passwordEncoder);

        this.userOne = this.factory.getUser();
        this.userOne = this.userRepository.save(this.userOne);

        this.userTwo = this.factory.getUser();
        this.userTwo = this.userRepository.save(this.userTwo);

    }

    /**
     * Test with new data
     */
    @Test
    public void testUpdateNewData() throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        final Name name = this.factory.getUniqueRandomName();

        final UserUpdateParameter userUpdateParameter = new UserUpdateParameter();
        userUpdateParameter.setEmail(this.factory.getUniqueRandomEmail());
        userUpdateParameter.setFirstName(name.firstName());
        userUpdateParameter.setLastName(name.lastName());
        userUpdateParameter.setUserName(name.username());

        final UserDTO userDTO = this.userService.updateAccountInformations(userUpdateParameter, this.userOne.getUserName());

        final String userNameToCheck = userDTO.getUserName().toLowerCase();
        final String firstNameToCheck = StringUtils.capitalize(userDTO.getFirstName().toLowerCase());

        Assertions.assertEquals(this.userOne.getUserName(), userNameToCheck);
        Assertions.assertEquals(this.userOne.getEmail(), userDTO.getEmail());
        Assertions.assertEquals(this.userOne.getFirstName(), firstNameToCheck);
        Assertions.assertEquals(this.userOne.getLastName(), userDTO.getLastName().toUpperCase());
    }

    /**
     * Test with same data
     */
    @Test
    public void testUpdateSameData() throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        final UserUpdateParameter userUpdateParameter = new UserUpdateParameter();
        userUpdateParameter.setEmail(this.userOne.getEmail());
        userUpdateParameter.setFirstName(this.userOne.getFirstName());
        userUpdateParameter.setLastName(this.userOne.getLastName());
        userUpdateParameter.setUserName(this.userOne.getUserName());

        final UserDTO userDTO = this.userService.updateAccountInformations(userUpdateParameter, this.userOne.getUserName());

        final String userNameToCheck = userDTO.getUserName().toLowerCase();
        final String firstNameToCheck = StringUtils.capitalize(userDTO.getFirstName().toLowerCase());

        Assertions.assertEquals(this.userOne.getUserName(), userNameToCheck);
        Assertions.assertEquals(this.userOne.getEmail(), userDTO.getEmail());
        Assertions.assertEquals(this.userOne.getFirstName(), firstNameToCheck);
        Assertions.assertEquals(this.userOne.getLastName(), userDTO.getLastName().toUpperCase());
    }

    /**
     * Try to update with a email already used
     */
    @Test
    public void testEmailAlreadyUsed() {
        final Name name = this.factory.getUniqueRandomName();

        final UserUpdateParameter userUpdateParameter = new UserUpdateParameter();
        userUpdateParameter.setEmail(this.userTwo.getEmail());
        userUpdateParameter.setFirstName(name.firstName());
        userUpdateParameter.setLastName(name.lastName());
        userUpdateParameter.setUserName(name.username());

        Assertions.assertThrows(UserEmailAlreadyExistException.class,
            () -> this.userService.updateAccountInformations(userUpdateParameter, this.userOne.getUserName()));
    }

    /**
     * Try to update with a user name already used
     */
    @Test
    public void testUserNameAlreadyUsed() {
        final Name name = this.factory.getUniqueRandomName();

        final UserUpdateParameter userUpdateParameter = new UserUpdateParameter();
        userUpdateParameter.setEmail(this.factory.getUniqueRandomEmail());
        userUpdateParameter.setFirstName(name.firstName());
        userUpdateParameter.setLastName(name.lastName());
        userUpdateParameter.setUserName(this.userTwo.getUserName());

        Assertions.assertThrows(UserNameAlreadyExistException.class,
            () -> this.userService.updateAccountInformations(userUpdateParameter, this.userOne.getUserName()));
    }
    
}
