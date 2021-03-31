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
    private List<TreeCategoriesDTO> treeCategoriesDTOs;

    public TreeCategoriesDTO(final Category category){
        this.id = category.getId();
        this.label= category.getLabel();
        this.abbreviation= category.getAbbreviation();
        this.descritpion= category.getDescritpion();
        this.categoryType= category.getCategoryType();

        this.treeCategoriesDTOs = new ArrayList<>();

        for(final Category childCategory : category.getChild()){
            TreeCategoriesDTO childTreeCategoriesDTO = new TreeCategoriesDTO(childCategory);
            treeCategoriesDTOs.add(childTreeCategoriesDTO);
        }

    }
    
}
