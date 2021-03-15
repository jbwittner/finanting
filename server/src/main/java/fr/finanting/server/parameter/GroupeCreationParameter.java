package fr.finanting.server.parameter;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Parameter used to create a groupe
 */
@Data
public class GroupeCreationParameter {

    @NotEmpty
    @NotNull
    private String groupeName;
    
    private List<String> usersName;

}
