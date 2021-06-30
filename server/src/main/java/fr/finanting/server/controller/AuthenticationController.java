package fr.finanting.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.exception.LoginException;
import fr.finanting.server.generated.api.AuthenticationApi;
import fr.finanting.server.generated.model.LoginDTO;
import fr.finanting.server.generated.model.LoginParameter;
import fr.finanting.server.security.JwtTokenUtil;

@RestController
public class AuthenticationController extends MotherController implements AuthenticationApi {
    
    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<LoginDTO> login(final LoginParameter loginParameter) {

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
 
        
        String jwtToken = jwtUtil.getToken(authentication);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setJwt(jwtToken);

        return new ResponseEntity<>(loginDTO, HttpStatus.OK);

    }
   
}