package fr.finanting.server.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GroupingCategoriesDTO {
    
    private String groupName;
    private List<TreeCategoriesDTO> treeCategoriesDTOs = new ArrayList<>();

}
