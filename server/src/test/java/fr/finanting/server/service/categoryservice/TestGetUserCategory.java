package fr.finanting.server.service.categoryservice;

import fr.finanting.server.codegen.model.TreeCategoryDTO;
import fr.finanting.server.model.Category;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.CategoryRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.CategoryServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestGetUserCategory extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryServiceImpl categoryServiceImpl;

    @Override
    protected void initDataBeforeEach() {
        this.categoryServiceImpl = new CategoryServiceImpl(this.userRepository, this.groupRepository, this.categoryRepository);
    }

    @Test
    public void testGetUserCategory(){

        final User user = this.testFactory.getUser();

        final List<Category> categories = new ArrayList<>();

        int NUMBER_MOTHER_CATEGORY = 4;
        for(int motherIndex = 0; motherIndex < NUMBER_MOTHER_CATEGORY; motherIndex++){

            final Category motherCategory = this.testFactory.getCategory(user, true);

            final List<Category> childCategories = new ArrayList<>();

            int NUMBER_CHILD_CATEGORY = 15;
            for(int childIndex = 0; childIndex < NUMBER_CHILD_CATEGORY; childIndex++){

                final Category childCategory = this.testFactory.getCategory(user, true);
                childCategory.setParent(motherCategory);
                childCategories.add(childCategory);
            }

            motherCategory.setChild(childCategories);
            categories.add(motherCategory);

        }

        final List<TreeCategoryDTO> treeCategoryDTOList = this.categoryServiceImpl.getUserCategory(user.getUserName());

        Assertions.assertEquals(NUMBER_MOTHER_CATEGORY, treeCategoryDTOList.size());

        for(final TreeCategoryDTO treeCategoryDTO : treeCategoryDTOList){

            boolean isPresent = false;

            Category childCategory = new Category();

            for(final Category category : categories){
                if(category.getId().equals(treeCategoryDTO.getId())){
                    isPresent = true;
                    childCategory = category;
                    Assertions.assertEquals(category.getChild().size(), treeCategoryDTO.getChildTreeCategoriesDTOs().size());
                    Assertions.assertEquals(category.getAbbreviation(), treeCategoryDTO.getAbbreviation());
                    Assertions.assertEquals(category.getDescritpion(), treeCategoryDTO.getDescription());
                    Assertions.assertEquals(category.getLabel(), treeCategoryDTO.getLabel());
                    Assertions.assertEquals(category.getCategoryType().name(), treeCategoryDTO.getCategoryType().name());
                }
            }

            Assertions.assertTrue(isPresent);

            for(final TreeCategoryDTO childTreeCategoryDTO : treeCategoryDTO.getChildTreeCategoriesDTOs()){
                boolean childIsPresent = false;

                for(final Category category : childCategory.getChild()){
                    if(category.getId().equals(childTreeCategoryDTO.getId())){
                        childIsPresent = true;
                        Assertions.assertEquals(category.getAbbreviation(), childTreeCategoryDTO.getAbbreviation());
                        Assertions.assertEquals(category.getDescritpion(), childTreeCategoryDTO.getDescription());
                        Assertions.assertEquals(category.getLabel(), childTreeCategoryDTO.getLabel());
                        Assertions.assertEquals(category.getCategoryType().name(), childTreeCategoryDTO.getCategoryType().name());
                    }
                }

                Assertions.assertTrue(childIsPresent);

            }

        }

    }
    
}
