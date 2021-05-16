package fr.finanting.server.controller;

import fr.finanting.server.codegen.api.UserApi;
import fr.finanting.server.codegen.model.PasswordUpdateParameter;
import fr.finanting.server.codegen.model.UserDTO;
import fr.finanting.server.codegen.model.UserRegistrationParameter;
import fr.finanting.server.codegen.model.UserUpdateParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import fr.finanting.server.service.UserService;

@RestController
public class UserController extends MotherController implements UserApi {

    protected final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserDTO> userGet() {
        String userName = this.getCurrentPrincipalName();
        UserDTO userDTO = this.userService.getAccountInformations(userName);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> userPasswordUpdate(PasswordUpdateParameter body) {
        String userName = this.getCurrentPrincipalName();
        this.userService.updatePassword(body, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> userRegistration(UserRegistrationParameter body) {
        UserDTO userDTO = this.userService.registerNewAccount(body);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserDTO> userUpdate(UserUpdateParameter body) {
        String userName = this.getCurrentPrincipalName();
        UserDTO userDTO = this.userService.updateAccountInformations(body, userName);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
