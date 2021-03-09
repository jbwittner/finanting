package fr.finanting.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * User controller
 */
@RestController
@RequestMapping("user")
public class UserController {

    protected final UserService userService;

    /**
     * Constructor
     */
    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint used to register a new account
     */
    @PostMapping("/registerNewAccount")
    public UserDTO registerNewAccount(@RequestBody final UserRegisterParameter userRegisterParameter)
            throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        return this.userService.registerNewAccount(userRegisterParameter);
    }

    /**
     * Endpoint used to update account informations
     */
    @PostMapping("/updateAccountInformations")
    public UserDTO updateAccountInformations(final Authentication authentication, @RequestBody final UserUpdateParameter userUpdateParameter) throws UserEmailAlreadyExistException, UserNameAlreadyExistException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.userService.updateAccountInformations(userUpdateParameter, userDetailsImpl.getUsername());
    }

    /**
     * Endpoint used to get account informations
     */
    @GetMapping("/getAccountInformations")
    public UserDTO getAccountInformations(final Authentication authentication){
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.userService.getAccountInformations(userDetailsImpl.getUsername());
    }

    /**
     * Endpoint used to update password
     */
    @GetMapping("/updatePassword")
    public void updatePassword(final Authentication authentication, @RequestBody final PasswordUpdateParameter passwordUpdateParameter) throws BadPasswordException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.userService.updatePassword(passwordUpdateParameter, userDetailsImpl.getUsername());
    }
}
