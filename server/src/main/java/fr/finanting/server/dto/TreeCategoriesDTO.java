package fr.finanting.server.dto;

import java.util.ArrayList;
import java.util.List;

import fr.finanting.server.model.Category;
import fr.finanting.server.model.CategoryType;
import lombok.Data;

@Data
public class TreeCategoriesDTO {

    private Integer id;
    private String label;
    private String abbreviation;
    private String descritpion;
    private CategoryType categoryType;
    private List<TreeCategoriesDTO> childTreeCategoriesDTOs;

    public TreeCategoriesDTO(final Category category){
        this.id = category.getId();
        this.label= category.getLabel();
        this.abbreviation= category.getAbbreviation();
        this.descritpion= category.getDescritpion();
        this.categoryType= category.getCategoryType();

        this.childTreeCategoriesDTOs = new ArrayList<>();

        if(category.getChild() != null) {
            for(final Category childCategory : category.getChild()){
                final TreeCategoriesDTO childTreeCategoriesDTO = new TreeCategoriesDTO(childCategory);
                childTreeCategoriesDTOs.add(childTreeCategoriesDTO);
            } 
        }

    }
    
}
