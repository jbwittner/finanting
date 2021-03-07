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

import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.parameter.UserRegisterParameter;
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
    public void registerNewAccount(@RequestBody final UserRegisterParameter userRegisterParameter)
            throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        this.userService.registerNewAccount(userRegisterParameter);
    }

    @GetMapping("/test")
    public String test(Authentication authentication){
        System.out.println(authentication);
        System.out.println("getPrincipal : " + authentication.getPrincipal());
        System.out.println("getCredentials : " + authentication.getCredentials());
        System.out.println("getDetails : " + authentication.getDetails());
        System.out.println("getName : " + authentication.getName());
        System.out.println("getAuthorities : " + authentication.getAuthorities());
        return authentication.getName();
    }
}
