package fr.finanting.server.service.categoryservice;

import fr.finanting.server.codegen.model.TreeCategoryDTO;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Category;
import fr.finanting.server.model.Group;
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

public class TestGetGroupCategory extends AbstractMotherIntegrationTest {

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
    public void testGetUserCategory() throws GroupNotExistException, UserNotInGroupException{

        final Group group = this.testFactory.getGroup();
        final User user = group.getUserAdmin();

        final List<Category> categories = new ArrayList<>();

        int NUMBER_MOTHER_CATEGORY = 4;
        for(int motherIndex = 0; motherIndex < NUMBER_MOTHER_CATEGORY; motherIndex++){

            final Category motherCategory = this.testFactory.getCategory(group, true);

            final List<Category> childCategories = new ArrayList<>();

            int NUMBER_CHILD_CATEGORY = 15;
            for(int childIndex = 0; childIndex < NUMBER_CHILD_CATEGORY; childIndex++){

                final Category childCategory = this.testFactory.getCategory(group, true);
                childCategory.setParent(motherCategory);
                childCategories.add(childCategory);
            }

            motherCategory.setChild(childCategories);
            categories.add(motherCategory);

        }

        final List<TreeCategoryDTO> treeCategoriesDTOs = this.categoryServiceImpl.getGroupCategory(group.getId(), user.getUserName());

        Assertions.assertEquals(NUMBER_MOTHER_CATEGORY, treeCategoriesDTOs.size());

        for(final TreeCategoryDTO treeCategoryDTO : treeCategoriesDTOs){

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

            for(final TreeCategoryDTO childTreeCategoriesDTO : treeCategoryDTO.getChildTreeCategoriesDTOs()){
                boolean childIsPresent = false;

                for(final Category category : childCategory.getChild()){
                    if(category.getId().equals(childTreeCategoriesDTO.getId())){
                        childIsPresent = true;
                        Assertions.assertEquals(category.getAbbreviation(), childTreeCategoriesDTO.getAbbreviation());
                        Assertions.assertEquals(category.getDescritpion(), childTreeCategoriesDTO.getDescription());
                        Assertions.assertEquals(category.getLabel(), childTreeCategoriesDTO.getLabel());
                        Assertions.assertEquals(category.getCategoryType().name(), childTreeCategoriesDTO.getCategoryType().name());
                    }
                }

                Assertions.assertTrue(childIsPresent);

            }

        }

    }

    @Test
    public void testGetUserCategoryNotExistGroup() throws GroupNotExistException, UserNotInGroupException{

        final User user = this.testFactory.getUser();

        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.categoryServiceImpl.getGroupCategory(this.testFactory.getRandomInteger(), user.getUserName()));

    }

    @Test
    public void testGetUserCategoryNoUserGroup() throws GroupNotExistException, UserNotInGroupException{

        final Group group = this.testFactory.getGroup();

        final User otherUser = this.testFactory.getUser();

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.categoryServiceImpl.getGroupCategory(group.getId(), otherUser.getUserName()));

    }
    
}
