package fr.finanting.server.dto;

import fr.finanting.server.model.Category;
import fr.finanting.server.model.CategoryType;
import lombok.Data;

@Data
public class CategoryDTO {

    private Integer id;
    private Integer parentId;
    private String label;
    private String abbreviation;
    private String descritpion;
    private CategoryType categoryType;

    public CategoryDTO(Category category){

        this.id = category.getId();

        if(category.getParent() != null){
            this.parentId = category.getParent().getId();
        }

        this.label= category.getLabel();
        this.abbreviation= category.getAbbreviation();
        this.descritpion= category.getDescritpion();
        this.categoryType= category.getCategoryType();

    }
    
}
