package fr.finanting.server.dto;

import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * User DTO
 */
@Data
public class GroupsDTO {

    private List<GroupDTO> groupDTO = new ArrayList<>();

}
