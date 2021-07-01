package fr.finanting.server.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import fr.finanting.server.exception.LoginException;
import fr.finanting.server.generated.model.LoginDTO;
import fr.finanting.server.generated.model.LoginParameter;
import fr.finanting.server.security.JwtTokenUtil;
import fr.finanting.server.service.AuthenticationService;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private JwtTokenUtil jwtTokenUtil;

    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager){
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginDTO login(LoginParameter loginParameter) {

        UsernamePasswordAuthenticationToken loginCredentials =
                new UsernamePasswordAuthenticationToken(
                        loginParameter.getUserName(), loginParameter.getPassword());

        Authentication authentication;

        try {
            authentication = 
                authenticationManager.authenticate(loginCredentials);
        } catch (Exception e) {
               throw new LoginException(e);
        }
        
        String jwtToken = this.jwtTokenUtil.getToken(authentication);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setJwt(jwtToken);

        return loginDTO;
    }
    
}
