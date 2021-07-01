package fr.finanting.server.service.authenticationservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import fr.finanting.server.exception.LoginException;
import fr.finanting.server.generated.model.LoginParameter;
import fr.finanting.server.generated.model.UserDTO;
import fr.finanting.server.generated.model.UserRegistrationParameter;
import fr.finanting.server.model.User;
import fr.finanting.server.security.JwtTokenUtil;
import fr.finanting.server.service.UserService;
import fr.finanting.server.service.implementation.AuthenticationServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestLogin extends AbstractMotherIntegrationTest {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    private AuthenticationServiceImpl authenticationServiceImpl;

    private LoginParameter loginParameter;

    @Override
    protected void initDataBeforeEach() {
        this.authenticationServiceImpl = new AuthenticationServiceImpl(this.jwtTokenUtil, this.authenticationManager);
        UserRegistrationParameter userRegistrationParameter = new UserRegistrationParameter();
        userRegistrationParameter.setEmail(this.testFactory.getUniqueRandomEmail());
        userRegistrationParameter.setFirstName(this.testFactory.getRandomAlphanumericString());
        userRegistrationParameter.setLastName(this.testFactory.getRandomAlphanumericString());
        userRegistrationParameter.setUserName(this.testFactory.getRandomAlphanumericString());
        String password = this.testFactory.getRandomAlphanumericString();
        userRegistrationParameter.setPassword(password);
        UserDTO userDTO = this.userService.registerNewAccount(userRegistrationParameter);
        loginParameter = new LoginParameter();
        loginParameter.setUserName(userDTO.getUserName());
        loginParameter.setPassword(password);
    }

    @Test
    public void testUserNotExist(){
        loginParameter.setUserName(this.testFactory.getRandomAlphanumericString());
        Assertions.assertThrows(LoginException.class, () -> this.authenticationServiceImpl.login(loginParameter));
    }

    @Test
    public void testUserAuthenticationOk(){
        this.authenticationServiceImpl.login(loginParameter);
    }

    @Test
    public void testBadPassword(){
        loginParameter.setPassword(this.testFactory.getRandomAlphanumericString());
        Assertions.assertThrows(LoginException.class, () -> this.authenticationServiceImpl.login(loginParameter));
    }
    
}
