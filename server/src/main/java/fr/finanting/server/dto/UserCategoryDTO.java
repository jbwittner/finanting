package fr.finanting.server.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserCategoryDTO {

    private List<GroupingCategoriesDTO> groupingCategoriesDTOs = new ArrayList<>();
    
}
