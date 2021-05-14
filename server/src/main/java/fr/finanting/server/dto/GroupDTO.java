package fr.finanting.server.dto;

import java.util.ArrayList;
import java.util.List;

import fr.finanting.server.codegen.model.UserDTO;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import lombok.Data;

@Data
public class GroupDTO {

    private String groupName;
    private UserDTO userAdmin;
    private List<UserDTO> groupUsers = new ArrayList<>();

    private static final UserDTOBuilder USER_DTO_BUILDER = new UserDTOBuilder();

    public GroupDTO(final Group group){
        this.groupName = group.getGroupName();
        this.userAdmin = USER_DTO_BUILDER.transform(group.getUserAdmin());
        for(final User users : group.getUsers()){
            this.groupUsers.add(USER_DTO_BUILDER.transform(users));
        }
    }
}
