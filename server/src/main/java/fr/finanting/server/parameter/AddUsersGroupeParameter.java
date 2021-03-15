package fr.finanting.server.parameter;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Parameter used to add users to a groupe
 */
@Data
public class AddUsersGroupeParameter {

    @NotEmpty
    private List<String> usersName;
    
    @NotEmpty
    @NotNull
    private String groupeName;

}
