package fr.finanting.server.service.categoryservice;

import fr.finanting.server.dto.GroupingCategoriesDTO;
import fr.finanting.server.dto.TreeCategoriesDTO;
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

    private int NUMBER_MOTHER_CATEGORY = 4;
    private int NUMBER_CHILD_CATEGORY = 15;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.categoryServiceImpl = new CategoryServiceImpl(this.userRepository, this.groupRepository, this.categoryRepository);

    }

    @Test
    public void testGetUserCategory() throws GroupNotExistException, UserNotInGroupException{

        Group group = this.factory.getGroup();
        User user = this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);

        List<Category> categories = new ArrayList<>();

        for(int motherIndex = 0; motherIndex < NUMBER_MOTHER_CATEGORY; motherIndex++){

            Category motherCategory = this.categoryRepository.save(this.factory.getCategory(group, true));

            List<Category> childCategories = new ArrayList<>();

            for(int childIndex = 0; childIndex < NUMBER_CHILD_CATEGORY; childIndex++){

                final Category childCategory = this.factory.getCategory(group, true);
                childCategory.setParent(motherCategory);
                this.categoryRepository.save(childCategory);
                childCategories.add(childCategory);
            }

            motherCategory.setChild(childCategories);
            motherCategory = this.categoryRepository.save(motherCategory);
            categories.add(motherCategory);

        }

        GroupingCategoriesDTO groupingCategoriesDTO = this.categoryServiceImpl.getGroupCategory(group.getGroupName(), user.getUserName());

        Assertions.assertEquals(NUMBER_MOTHER_CATEGORY, groupingCategoriesDTO.getTreeCategoriesDTOs().size());

        for(TreeCategoriesDTO treeCategoriesDTO : groupingCategoriesDTO.getTreeCategoriesDTOs()){

            boolean isPresent = false;

            Category childCategory = new Category();

            for(Category category : categories){
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

            for(TreeCategoriesDTO childTreeCategoriesDTO : treeCategoriesDTO.getChildTreeCategoriesDTOs()){
                boolean childIsPresent = false;

                for(Category category : childCategory.getChild()){
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

    @Test
    public void testGetUserCategoryNotExistGroup() throws GroupNotExistException, UserNotInGroupException{

        User user = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.categoryServiceImpl.getGroupCategory(this.factory.getRandomAlphanumericString(), user.getUserName()));

    }

    @Test
    public void testGetUserCategorytNoUserGroup() throws GroupNotExistException, UserNotInGroupException{

        Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());
        Group finalGroup = this.groupRepository.save(group);

        User otherUser = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.categoryServiceImpl.getGroupCategory(finalGroup.getGroupName(), otherUser.getUserName()));

    }
    
}
