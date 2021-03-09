package fr.finanting.server.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Admin controller
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    /**
     * Test endpoint
     */
    @GetMapping("/test")
    public String test(final Principal principal){
        return principal.getName();
    }
    
}
