package fr.finanting.server.parameter;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Parameter used to remove users from a groupe
 */
@Data
public class RemoveUsersGroupeParameter {

    @NotEmpty
    private List<String> usersName;
    
    @NotEmpty
    @NotNull
    private String groupeName;

}
