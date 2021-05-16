package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.CategoryDTO;
import fr.finanting.server.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTOBuilder extends Transformer<Category, CategoryDTO> {

    @Override
    public CategoryDTO transform(Category input) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(input.getId());
        categoryDTO.setAbbreviation(input.getAbbreviation());
        categoryDTO.setLabel(input.getLabel());
        categoryDTO.setDescription(input.getDescritpion());
        CategoryDTO.CategoryTypeEnum categoryTypeEnum = CategoryDTO.CategoryTypeEnum.fromValue(input.getCategoryType().name());
        categoryDTO.setCategoryType(categoryTypeEnum);
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> transformAll(List<Category> input) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        input.forEach(category -> categoryDTOList.add(this.transform(category)));
        return categoryDTOList;
    }
}
