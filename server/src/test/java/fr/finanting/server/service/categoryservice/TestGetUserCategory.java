package fr.finanting.server.service.categoryservice;

import fr.finanting.server.dto.TreeCategoriesDTO;
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

    private int NUMBER_MOTHER_CATEGORY = 4;
    private int NUMBER_CHILD_CATEGORY = 15;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.categoryServiceImpl = new CategoryServiceImpl(this.userRepository, this.groupRepository, this.categoryRepository);

    }

    @Test
    public void testGetUserCategory(){

        final User user = this.testFactory.getUser();

        final List<Category> categories = new ArrayList<>();

        for(int motherIndex = 0; motherIndex < NUMBER_MOTHER_CATEGORY; motherIndex++){

            final Category motherCategory = this.testFactory.getCategory(user, true);

            final List<Category> childCategories = new ArrayList<>();

            for(int childIndex = 0; childIndex < NUMBER_CHILD_CATEGORY; childIndex++){

                final Category childCategory = this.testFactory.getCategory(user, true);
                childCategory.setParent(motherCategory);
                childCategories.add(childCategory);
            }

            motherCategory.setChild(childCategories);
            categories.add(motherCategory);

        }

        final List<TreeCategoriesDTO> treeCategoriesDTOs = this.categoryServiceImpl.getUserCategory(user.getUserName());

        Assertions.assertEquals(NUMBER_MOTHER_CATEGORY, treeCategoriesDTOs.size());

        for(final TreeCategoriesDTO treeCategoriesDTO : treeCategoriesDTOs){

            boolean isPresent = false;

            Category childCategory = new Category();

            for(final Category category : categories){
                if(category.getId().equals(treeCategoriesDTO.getId())){
                    isPresent = true;
                    childCategory = category;
                    Assertions.assertEquals(category.getChild().size(), treeCategoriesDTO.getChildTreeCategoriesDTOs().size());
                    Assertions.assertEquals(category.getAbbreviation(), treeCategoriesDTO.getAbbreviation());
                    Assertions.assertEquals(category.getDescritpion(), treeCategoriesDTO.getDescritpion());
                    Assertions.assertEquals(category.getLabel(), treeCategoriesDTO.getLabel());
                    Assertions.assertEquals(category.getCategoryType(), treeCategoriesDTO.getCategoryType());
                }
            }

            Assertions.assertTrue(isPresent);

            for(final TreeCategoriesDTO childTreeCategoriesDTO : treeCategoriesDTO.getChildTreeCategoriesDTOs()){
                boolean childIsPresent = false;

                for(final Category category : childCategory.getChild()){
                    if(category.getId().equals(childTreeCategoriesDTO.getId())){
                        childIsPresent = true;
                        Assertions.assertEquals(category.getAbbreviation(), childTreeCategoriesDTO.getAbbreviation());
                        Assertions.assertEquals(category.getDescritpion(), childTreeCategoriesDTO.getDescritpion());
                        Assertions.assertEquals(category.getLabel(), childTreeCategoriesDTO.getLabel());
                        Assertions.assertEquals(category.getCategoryType(), childTreeCategoriesDTO.getCategoryType());
                    }
                }


                Assertions.assertTrue(childIsPresent);

            }

        }

    }
    
}
