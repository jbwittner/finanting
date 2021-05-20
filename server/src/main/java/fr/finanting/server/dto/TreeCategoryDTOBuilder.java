package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.TreeCategoryDTO;
import fr.finanting.server.model.Category;

import java.util.ArrayList;
import java.util.List;

public class TreeCategoryDTOBuilder extends Transformer<Category, TreeCategoryDTO> {

    @Override
    public TreeCategoryDTO transform(final Category input) {
        final TreeCategoryDTO treeCategoryDTO = new TreeCategoryDTO();
        treeCategoryDTO.setCategoryType(TreeCategoryDTO.CategoryTypeEnum.fromValue(input.getCategoryType().name()));
        treeCategoryDTO.setAbbreviation(input.getAbbreviation());
        treeCategoryDTO.setId(input.getId());
        treeCategoryDTO.setLabel(input.getLabel());
        treeCategoryDTO.setDescription(input.getDescritpion());
        treeCategoryDTO.setChildTreeCategoriesDTOs(this.transformAll(input.getChild()));
        return treeCategoryDTO;
    }

    @Override
    public List<TreeCategoryDTO> transformAll(final List<Category> input) {
        final List<TreeCategoryDTO> treeCategoryDTOList = new ArrayList<>();
        input.forEach(category -> treeCategoryDTOList.add(this.transform(category)));
        return treeCategoryDTOList;
    }
}
