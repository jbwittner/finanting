package fr.finanting.server.service.authenticationservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import fr.finanting.server.exception.LoginException;
import fr.finanting.server.generated.model.LoginParameter;
import fr.finanting.server.model.User;
import fr.finanting.server.security.JwtTokenUtil;
import fr.finanting.server.service.implementation.AuthenticationServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestLogin extends AbstractMotherIntegrationTest {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    private AuthenticationServiceImpl authenticationServiceImpl;

    @Override
    protected void initDataBeforeEach() {
        this.authenticationServiceImpl = new AuthenticationServiceImpl(this.jwtTokenUtil, this.authenticationManager);       
    }

    @Test
    public void testUserNotExist(){
        LoginParameter loginParameter = new LoginParameter();
        loginParameter.setUserName(this.testFactory.getRandomAlphanumericString());
        loginParameter.setPassword(this.testFactory.getRandomAlphanumericString());
        Assertions.assertThrows(LoginException.class, () -> this.authenticationServiceImpl.login(loginParameter));
    }

    @Test
    public void testUserNotExistdd(){
        User user = this.testFactory.getUser();
        LoginParameter loginParameter = new LoginParameter();
        loginParameter.setUserName(user.getUserName());
        loginParameter.setPassword(user.getPassword());
        Assertions.assertThrows(LoginException.class, () -> this.authenticationServiceImpl.login(loginParameter));
    }
    
}
