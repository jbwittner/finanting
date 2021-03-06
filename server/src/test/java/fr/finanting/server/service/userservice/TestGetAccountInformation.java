package fr.finanting.server.service.userservice;

import fr.finanting.server.generated.model.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.UserServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestGetAccountInformation extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    private User user;

    @Override
    protected void initDataBeforeEach() {
        this.userService = new UserServiceImpl(this.userRepository, this.passwordEncoder);
        this.user = this.testFactory.getUser();
    }

    @Test
    public void testGetAccountInformation() {

        final UserDTO userDTO = this.userService.getAccountInformation(this.user.getUserName());

        Assertions.assertEquals(this.user.getUserName(), userDTO.getUserName());
        Assertions.assertEquals(this.user.getEmail(), userDTO.getEmail());
        Assertions.assertEquals(this.user.getFirstName(), userDTO.getFirstName());
        Assertions.assertEquals(this.user.getLastName(), userDTO.getLastName());

        for(final UserDTO.RolesEnum role : userDTO.getRoles()){
            Assertions.assertEquals(Role.USER.toString(), role.toString());
        }
    }
    
}
