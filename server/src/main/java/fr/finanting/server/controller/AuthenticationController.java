package fr.finanting.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.generated.api.AuthenticationApi;
import fr.finanting.server.generated.model.LoginDTO;
import fr.finanting.server.generated.model.LoginParameter;
import fr.finanting.server.security.JwtTokenUtil;

@RestController
public class AuthenticationController extends MotherController implements AuthenticationApi {
    
    @Autowired
    private JwtTokenUtil jwtUtil;

    @Override
    public ResponseEntity<LoginDTO> login(final LoginParameter loginParameter) {
        
        String jwtToken = jwtUtil.getToken(loginParameter);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setJwt(jwtToken);

        return new ResponseEntity<>(loginDTO, HttpStatus.OK);

    }
   
}