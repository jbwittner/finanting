package fr.finanting.server.parameter;

import lombok.Data;

@Data
public class UserRegisterParameter {

    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    
}
