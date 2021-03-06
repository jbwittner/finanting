package fr.finanting.server.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

    @GetMapping("/test")
    public String test(Principal principal){
        System.out.println(principal);
        return principal.getName();
    }
    
}
