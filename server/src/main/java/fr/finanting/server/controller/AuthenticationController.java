package fr.finanting.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.generated.api.AuthenticationApi;
import fr.finanting.server.generated.model.LoginDTO;
import fr.finanting.server.generated.model.LoginParameter;
import fr.finanting.server.service.AuthenticationService;

@RestController
public class AuthenticationController extends MotherController implements AuthenticationApi {
    
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public ResponseEntity<LoginDTO> login(final LoginParameter loginParameter) {

        final LoginDTO loginDTO = this.authenticationService.login(loginParameter);

        return new ResponseEntity<>(loginDTO, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Void> test(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
   
}