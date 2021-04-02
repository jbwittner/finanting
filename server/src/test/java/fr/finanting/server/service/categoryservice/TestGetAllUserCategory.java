package fr.finanting.server.service.categoryservice;

import fr.finanting.server.dto.GroupingCategoriesDTO;
import fr.finanting.server.dto.TreeCategoriesDTO;
import fr.finanting.server.dto.UserCategoryDTO;
import fr.finanting.server.exception.BadAssociationCategoryUserGroup;
import fr.finanting.server.exception.CategoryNoUserException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Category;
import fr.finanting.server.model.CategoryType;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.CreateCategoryParameter;
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

public class TestGetAllUserCategory extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryServiceImpl categoryServiceImpl;

    private int NUMBER_GROUP = 3;
    private int NUMBER_MOTHER_CATEGORY = 4;
    private int NUMBER_CHILD_CATEGORY = 15;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.categoryServiceImpl = new CategoryServiceImpl(this.userRepository, this.groupRepository, this.categoryRepository);
    }

    private List<Group> prepareGroupCategories(User user){

        List<Group> groupList = new ArrayList<>();

        for(int groupIndex = 0; groupIndex < NUMBER_GROUP; groupIndex++){

            Group group = this.factory.getGroup();
            this.userRepository.save(group.getUserAdmin());
            List<User> users = group.getUsers();
            users.add(user);
            group.setUsers(users);
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

            group.setCategories(categories);
            group = this.groupRepository.save(group);
            groupList.add(group);

        }

        user.setGroups(groupList);

        return groupList;

    }

    private List<Category> prepareUserCategories(User user){

        List<Category> categories = new ArrayList<>();

        for(int motherIndex = 0; motherIndex < NUMBER_MOTHER_CATEGORY; motherIndex++){

            Category motherCategory = this.categoryRepository.save(this.factory.getCategory(user, true));

            List<Category> childCategories = new ArrayList<>();

            for(int childIndex = 0; childIndex < NUMBER_CHILD_CATEGORY; childIndex++){

                final Category childCategory = this.factory.getCategory(user, true);
                childCategory.setParent(motherCategory);
                this.categoryRepository.save(childCategory);
                childCategories.add(childCategory);
            }

            motherCategory.setChild(childCategories);
            motherCategory = this.categoryRepository.save(motherCategory);
            categories.add(motherCategory);

        }

        user.setCategories(categories);

        return categories;

    }

    @Test
    public void testGetallUSerCategory(){

        User user = this.userRepository.save(this.factory.getUser());

        int userId = user.getId();

        List<Group> groupList = this.prepareGroupCategories(user);
        List<Category> userCategories = this.prepareUserCategories(user);

        user = this.userRepository.findById(userId).orElseThrow();
        
        UserCategoryDTO userCategoryDTO = this.categoryServiceImpl.getAllUserCategory(user.getUserName());

        boolean haveUserCategories = false;

        Assertions.assertEquals(NUMBER_GROUP + 1, userCategoryDTO.getGroupingCategoriesDTOs().size());

        for(GroupingCategoriesDTO groupingCategoriesDTO : userCategoryDTO.getGroupingCategoriesDTOs()){

            List<Category> categories = new ArrayList<>();

            if(groupingCategoriesDTO.getGroupName() == null){
                haveUserCategories = true;
                categories = userCategories;
            } else {
                boolean haveGroup = false;

                for(Group group : groupList){
                    if(group.getGroupName().equals(groupingCategoriesDTO.getGroupName())){
                        haveGroup = true;
                        categories = group.getCategories();
                        break;
                    }
                }
                Assertions.assertTrue(haveGroup);

            }

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

        Assertions.assertTrue(haveUserCategories);

    }
    
}
