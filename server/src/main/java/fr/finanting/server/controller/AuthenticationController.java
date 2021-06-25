package fr.finanting.server.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.security.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
 
    @Autowired
    private JwtTokenUtil jwtUtil;
 
    @PostMapping(path = "/token",
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getToken(@Valid @RequestBody ALoginRequest loginRequest) {
 
        
        UsernamePasswordAuthenticationToken loginCredentials =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(), loginRequest.getPassword());
         
        Authentication authentication = 
                authenticationManager.authenticate(loginCredentials);
 
        String userName = authentication.getName();
        String jwtToken = jwtUtil.createJWT(userName);
 
        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .build();
 
    }
}