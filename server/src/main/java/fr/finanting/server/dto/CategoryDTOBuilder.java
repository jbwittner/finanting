package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.CategoryDTO;
import fr.finanting.server.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTOBuilder implements Transformer<Category, CategoryDTO> {

    @Override
    public CategoryDTO transform(final Category input) {
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(input.getId());
        categoryDTO.setAbbreviation(input.getAbbreviation());
        categoryDTO.setLabel(input.getLabel());
        categoryDTO.setDescription(input.getDescritpion());
        final CategoryDTO.CategoryTypeEnum categoryTypeEnum = CategoryDTO.CategoryTypeEnum.fromValue(input.getCategoryType().name());
        categoryDTO.setCategoryType(categoryTypeEnum);
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> transformAll(final List<Category> input) {
        final List<CategoryDTO> categoryDTOList = new ArrayList<>();
        input.forEach(category -> categoryDTOList.add(this.transform(category)));
        return categoryDTOList;
    }
}
