package fr.finanting.server.controller;

import fr.finanting.server.api.UserApi;
import fr.finanting.server.model.PasswordUpdateParameter;
import fr.finanting.server.model.UserDTO;
import fr.finanting.server.model.UserRegistrationParameter;
import fr.finanting.server.model.UserUpdateParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.exception.BadPasswordException;
import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.parameter.UserRegisterParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.UserService;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController extends MotherController implements UserApi {

    protected final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /*
    @PostMapping("/registerNewAccount")
    public UserDTO registerNewAccount(@RequestBody final UserRegisterParameter userRegisterParameter)
            throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        return this.userService.registerNewAccount(userRegisterParameter);
    }

    @PostMapping("/updateAccountInformations")
    public UserDTO updateAccountInformations(final Authentication authentication, @RequestBody final UserUpdateParameter userUpdateParameter) throws UserEmailAlreadyExistException, UserNameAlreadyExistException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.userService.updateAccountInformations(userUpdateParameter, userDetailsImpl.getUsername());
    }

    @GetMapping("/getAccountInformations")
    public UserDTO getAccountInformations(final Authentication authentication){
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.userService.getAccountInformations(userDetailsImpl.getUsername());
    }

    @GetMapping("/updatePassword")
    public void updatePassword(final Authentication authentication, @RequestBody final PasswordUpdateParameter passwordUpdateParameter) throws BadPasswordException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.userService.updatePassword(passwordUpdateParameter, userDetailsImpl.getUsername());
    }
    */

    @Override
    public ResponseEntity<UserDTO> userGet() {
        String userName = this.getCurrentPrincipalName();
        this.userService.getAccountInformations(userName);
        return null;
    }

    @Override
    public ResponseEntity<Void> userPasswordUpdate(PasswordUpdateParameter body) {
        String userName = this.getCurrentPrincipalName();
        //this.userService.updatePassword(body, userName);
        return null;
    }

    @Override
    public ResponseEntity<UserDTO> userRegistration(UserRegistrationParameter body) {
        //this.userService.registerNewAccount(body);
        return null;
    }

    @Override
    public ResponseEntity<UserDTO> userUpdate(UserUpdateParameter body) {
        String userName = this.getCurrentPrincipalName();
        //this.userService.updateAccountInformations(body, userName);
        return null;
    }
}
