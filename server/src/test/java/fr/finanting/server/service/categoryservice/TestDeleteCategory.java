package fr.finanting.server.service.categoryservice;

import fr.finanting.server.exception.CategoryNoUserException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.DeleteCategoryWithChildException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Category;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.DeleteCategoryParameter;
import fr.finanting.server.repository.CategoryRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.CategoryServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestDeleteCategory extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryServiceImpl categoryServiceImpl;

    private Group group;
    private User user;

    @Override
    protected void initDataBeforeEach()
        throws Exception {

        this.categoryServiceImpl = new CategoryServiceImpl(this.userRepository, this.groupRepository, this.categoryRepository);

        this.group = this.factory.getGroup();
        this.userRepository.save(this.group.getUserAdmin());

        this.user = this.userRepository.save(this.factory.getUser());

        final List<User> users = this.group.getUsers();
        users.add(user);
        this.group.setUsers(users);

        this.group = this.groupRepository.save(group);
        
    }

    @Test
    public void testDeleteParentUserCategory()
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, DeleteCategoryWithChildException {

        final Category category = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        final DeleteCategoryParameter deleteCategoryParameter = new DeleteCategoryParameter();
        deleteCategoryParameter.setId(category.getId());

        this.categoryServiceImpl.deleteCategory(deleteCategoryParameter, this.user.getUserName());

        final Optional<Category> optionalCategory = this.categoryRepository.findById(category.getId());

        Assertions.assertFalse(optionalCategory.isPresent());

    }

    @Test
    public void testDeleteParentUserCategoryWithChildCategories()
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, DeleteCategoryWithChildException {

        final List<Category> categories = new ArrayList<>();

        final Category childCategoryOne = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        categories.add(childCategoryOne);

        Category category = this.factory.getCategory(this.user, true);
        category.setChild(categories);

        category = this.categoryRepository.save(category);

        final DeleteCategoryParameter deleteCategoryParameter = new DeleteCategoryParameter();
        deleteCategoryParameter.setId(category.getId());

        Assertions.assertThrows(DeleteCategoryWithChildException.class,
            () -> this.categoryServiceImpl.deleteCategory(deleteCategoryParameter, this.user.getUserName()));

    }

    @Test
    public void testDeleteParentGroupCategory()
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, DeleteCategoryWithChildException {

        final Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        final DeleteCategoryParameter deleteCategoryParameter = new DeleteCategoryParameter();
        deleteCategoryParameter.setId(category.getId());

        this.categoryServiceImpl.deleteCategory(deleteCategoryParameter, this.user.getUserName());

        final Optional<Category> optionalCategory = this.categoryRepository.findById(category.getId());

        Assertions.assertFalse(optionalCategory.isPresent());

    }

    @Test
    public void testDeleteParentNotUserCategory() {

        final User otherUser = this.userRepository.save(this.factory.getUser());

        final Category category = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        final DeleteCategoryParameter deleteCategoryParameter = new DeleteCategoryParameter();
        deleteCategoryParameter.setId(category.getId());

        Assertions.assertThrows(CategoryNoUserException.class,
            () -> this.categoryServiceImpl.deleteCategory(deleteCategoryParameter, otherUser.getUserName()));
        
    }

    @Test
    public void testDeleteParentGroupCategoryUserNotInGroup() {

        final User otherUser = this.userRepository.save(this.factory.getUser());

        final Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        final DeleteCategoryParameter deleteCategoryParameter = new DeleteCategoryParameter();
        deleteCategoryParameter.setId(category.getId());

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.categoryServiceImpl.deleteCategory(deleteCategoryParameter, otherUser.getUserName()));

    }

    @Test
    public void testDeleteParentNotExistCategory() {

        final DeleteCategoryParameter deleteCategoryParameter = new DeleteCategoryParameter();
        deleteCategoryParameter.setId(this.factory.getRandomInteger());

        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.categoryServiceImpl.deleteCategory(deleteCategoryParameter, this.user.getUserName()));

    }
    
}
