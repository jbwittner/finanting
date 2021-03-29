package fr.finanting.server.dto;

import java.util.ArrayList;
import java.util.List;

import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;
import lombok.Data;

@Data
public class UserDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String userName;
    private List<String> roles = new ArrayList<>();
    
    public UserDTO(final User user){
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUserName();
        for(final Role role : user.getRoles()){
            this.roles.add(role.toString());
        }
    }
}
