package fr.finanting.server.controller;

import fr.finanting.codegen.api.UserApi;
import fr.finanting.codegen.model.PasswordUpdateParameter;
import fr.finanting.codegen.model.UserDTO;
import fr.finanting.codegen.model.UserRegistrationParameter;
import fr.finanting.codegen.model.UserUpdateParameter;
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
        super();
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserDTO> userGet() {
        final String userName = this.getCurrentPrincipalName();
        final UserDTO userDTO = this.userService.getAccountInformation(userName);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> userPasswordUpdate(final PasswordUpdateParameter body) {
        final String userName = this.getCurrentPrincipalName();
        this.userService.updatePassword(body, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> userRegistration(final UserRegistrationParameter body) {
        final UserDTO userDTO = this.userService.registerNewAccount(body);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserDTO> userUpdate(final UserUpdateParameter body) {
        final String userName = this.getCurrentPrincipalName();
        final UserDTO userDTO = this.userService.updateAccountInformation(body, userName);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
