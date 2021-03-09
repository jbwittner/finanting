package fr.finanting.server.service.userservice;

import java.util.ArrayList;
import java.util.List;

import com.github.javafaker.Name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.finanting.server.dto.UserDTO;
import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.UserServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

/**
 * Test class to test getAccountInformations method
 */
public class GetAccountInformationsTest extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    private User user;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.userService = new UserServiceImpl(this.userRepository, this.passwordEncoder);

        this.user = new User();
        final Name name = this.factory.getUniqueRandomName();
        this.user.setUserName(name.username());
        this.user.setFirstName(name.firstName());
        this.user.setLastName(name.lastName());
        this.user.setPassword(this.passwordEncoder.encode(this.factory.getRandomAlphanumericString()));
        this.user.setEmail(this.factory.getUniqueRandomEmail());

        final List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        this.user.setRoles(roles);

        this.userRepository.save(this.user);

    }

    /**
     * Test to get account informations
     */
    @Test
    public void testGetAccountInformations() {

        final UserDTO userDTO = this.userService.getAccountInformations(this.user.getUserName());

        Assertions.assertEquals(this.user.getUserName(), userDTO.getUserName());
        Assertions.assertEquals(this.user.getEmail(), userDTO.getEmail());
        Assertions.assertEquals(this.user.getFirstName(), userDTO.getFirstName());
        Assertions.assertEquals(this.user.getLastName(), userDTO.getLastName());

        for(final String role : userDTO.getRoles()){
            Assertions.assertEquals(Role.USER.toString(), role);
        }
    }
    
}
