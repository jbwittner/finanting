package fr.finanting.server.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.dto.UserDTO;
import fr.finanting.server.exception.BadPasswordException;
import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.parameter.PasswordUpdateParameter;
import fr.finanting.server.parameter.UserRegisterParameter;
import fr.finanting.server.parameter.UserUpdateParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    protected final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerNewAccount")
    public UserDTO registerNewAccount(@RequestBody final UserRegisterParameter userRegisterParameter)
            throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        return this.userService.registerNewAccount(userRegisterParameter);
    }

    @PostMapping("/updateAccountInformations")
    public UserDTO updateAccountInformations(Authentication authentication, @RequestBody final UserUpdateParameter userUpdateParameter){
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.userService.updateAccountInformations(userUpdateParameter, userDetailsImpl.getUsername());
    }

    @GetMapping("/getAccountInformations")
    public UserDTO getAccountInformations(Authentication authentication){
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.userService.getAccountInformations(userDetailsImpl.getUsername());
    }

    @GetMapping("/updatePassword")
    public void updatePassword(Authentication authentication, @RequestBody final PasswordUpdateParameter passwordUpdateParameter) throws BadPasswordException{
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.userService.updatePassword(passwordUpdateParameter, userDetailsImpl.getUsername());
    }
}
