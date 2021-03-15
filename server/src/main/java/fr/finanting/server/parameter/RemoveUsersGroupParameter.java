package fr.finanting.server.parameter;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Parameter used to remove users from a group
 */
@Data
public class RemoveUsersGroupParameter {

    @NotEmpty
    private List<String> usersName;
    
    @NotEmpty
    @NotNull
    private String groupName;

}
