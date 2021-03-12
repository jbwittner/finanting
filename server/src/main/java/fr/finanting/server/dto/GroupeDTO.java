package fr.finanting.server.dto;

import java.util.ArrayList;
import java.util.List;

import fr.finanting.server.model.Groupe;
import fr.finanting.server.model.User;
import lombok.Data;

/**
 * User DTO
 */
@Data
public class GroupeDTO {

    private String groupeName;
    private UserDTO userAdmin;
    private List<UserDTO> groupeUsers = new ArrayList<>(); 

    
    /**
     * Constructor
     */
    public GroupeDTO(final Groupe groupe){
        this.groupeName = groupe.getGroupeName();
        this.userAdmin = new UserDTO(groupe.getUserAdmin());
        for(final User users : groupe.getUsers()){
            this.groupeUsers.add(new UserDTO(users));
        }
    }
}
