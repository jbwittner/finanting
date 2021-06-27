package fr.finanting.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.exception.LoginException;
import fr.finanting.server.generated.api.AuthenticationApi;
import fr.finanting.server.generated.model.LoginParameter;
import fr.finanting.server.security.JwtTokenUtil;

@RestController
public class AuthenticationController extends MotherController implements AuthenticationApi {
    
    @Autowired
    private AuthenticationManager authenticationManager;
 
    @Autowired
    private JwtTokenUtil jwtUtil;

    @Override
    public ResponseEntity<Void> login(final LoginParameter loginParameter) {
        
        String jwtToken = this.getToken(loginParameter);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .build();

    }

    private String getToken(final LoginParameter loginParameter) {
        
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
 
        String userName = authentication.getName();
        String jwtToken = jwtUtil.createJWT(userName);
 
        return jwtToken;
    }
}