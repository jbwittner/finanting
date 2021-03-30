package fr.finanting.server.parameter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import fr.finanting.server.model.CategoryType;
import lombok.Data;

@Data
public class CreateCategoryParameter {
    
    private Integer parentId;
    
    @NotNull
    @NotEmpty
    private String label;

    @NotNull
    @NotEmpty
    private String abbreviation;

    private String descritpion;

    private String groupName;

    @NotNull
    private CategoryType categoryType;

}
