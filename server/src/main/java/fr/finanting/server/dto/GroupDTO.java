package fr.finanting.server.dto;

import java.util.ArrayList;
import java.util.List;

import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import lombok.Data;

/**
 * User DTO
 */
@Data
public class GroupDTO {

    private String groupName;
    private UserDTO userAdmin;
    private List<UserDTO> groupUsers = new ArrayList<>(); 

    
    /**
     * Constructor
     */
    public GroupDTO(final Group group){
        this.groupName = group.getGroupName();
        this.userAdmin = new UserDTO(group.getUserAdmin());
        for(final User users : group.getUsers()){
            this.groupUsers.add(new UserDTO(users));
        }
    }
}
