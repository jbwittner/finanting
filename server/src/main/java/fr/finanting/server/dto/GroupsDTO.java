package fr.finanting.server.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupsDTO {

    private List<GroupDTO> groupDTO = new ArrayList<>();

}
