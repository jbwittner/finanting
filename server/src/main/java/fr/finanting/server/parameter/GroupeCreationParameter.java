package fr.finanting.server.parameter;

import java.util.List;

import lombok.Data;

@Data
public class GroupeCreationParameter {

    private String groupeName;
    private List<String> usersName;

}
