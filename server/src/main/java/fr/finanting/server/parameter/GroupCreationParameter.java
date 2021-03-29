package fr.finanting.server.parameter;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GroupCreationParameter {

    @NotEmpty
    @NotNull
    private String groupName;
    
    private List<String> usersName;

}
