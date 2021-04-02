package fr.finanting.server.service.categoryservice;

import fr.finanting.server.exception.BadAssociationCategoryUserGroup;
import fr.finanting.server.exception.CategoryNoUserException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Category;
import fr.finanting.server.model.CategoryType;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.UpdateCategoryParameter;
import fr.finanting.server.repository.CategoryRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.CategoryServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class TestUpdateCategory extends AbstractMotherIntegrationTest {

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
    protected void initDataBeforeEach() throws Exception {
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
    public void testUpdateCategoryWithoutParentCategory()
        throws CategoryNotExistException, BadAssociationCategoryUserGroup, GroupNotExistException, CategoryNoUserException, UserNotInGroupException {

        Category category = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        final Integer categoryId = category.getId();

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setId(categoryId);
        updateCategoryParameter.setAbbreviation(this.factory.getRandomAlphanumericString(5));
        updateCategoryParameter.setCategoryType(CategoryType.EXPENSE);
        updateCategoryParameter.setDescritpion(this.faker.superhero().descriptor());
        updateCategoryParameter.setLabel(this.faker.company().name());
        
        this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName());

        category = this.categoryRepository.findById(categoryId).orElseThrow();

        Assertions.assertEquals(updateCategoryParameter.getAbbreviation().toUpperCase(), category.getAbbreviation());
        Assertions.assertEquals(updateCategoryParameter.getCategoryType(), category.getCategoryType());
        Assertions.assertEquals(updateCategoryParameter.getDescritpion(), category.getDescritpion());
        Assertions.assertEquals(updateCategoryParameter.getLabel(), category.getLabel());
        Assertions.assertEquals(this.user.getUserName(), category.getUser().getUserName());
        Assertions.assertNull(category.getGroup());
        Assertions.assertNull(category.getParent());
    }

    @Test
    public void testCreateSimpleGroupCategory()
        throws CategoryNotExistException, BadAssociationCategoryUserGroup, GroupNotExistException, CategoryNoUserException, UserNotInGroupException {

        Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        final Integer categoryId = category.getId();

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setId(categoryId);
        updateCategoryParameter.setAbbreviation(this.factory.getRandomAlphanumericString(5));
        updateCategoryParameter.setCategoryType(CategoryType.EXPENSE);
        updateCategoryParameter.setDescritpion(this.faker.superhero().descriptor());
        updateCategoryParameter.setLabel(this.faker.company().name());
        
        this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName());

        category = this.categoryRepository.findById(categoryId).orElseThrow();

        Assertions.assertEquals(updateCategoryParameter.getAbbreviation().toUpperCase(), category.getAbbreviation());
        Assertions.assertEquals(updateCategoryParameter.getCategoryType(), category.getCategoryType());
        Assertions.assertEquals(updateCategoryParameter.getDescritpion(), category.getDescritpion());
        Assertions.assertEquals(updateCategoryParameter.getLabel(), category.getLabel());
        Assertions.assertNull(category.getUser());
        Assertions.assertEquals(this.group.getGroupName(), category.getGroup().getGroupName());
        Assertions.assertNull(category.getParent());
    }

    @Test
    public void testUpdateCategoryRemoveUserParentCategory()
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryUserGroup{
        
        final Category parentCategory = this.categoryRepository.save(this.factory.getCategory(this.user, true));
        Category category = this.factory.getCategory(this.user, true);
        category.setParent(parentCategory);
        category = this.categoryRepository.save(category);

        final Integer categoryId = category.getId();

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setId(categoryId);
        updateCategoryParameter.setAbbreviation(this.factory.getRandomAlphanumericString(5));
        updateCategoryParameter.setCategoryType(CategoryType.EXPENSE);
        updateCategoryParameter.setDescritpion(this.faker.superhero().descriptor());
        updateCategoryParameter.setLabel(this.faker.company().name());

        this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName());

        category = this.categoryRepository.findById(categoryId).orElseThrow();

        Assertions.assertNull(category.getParent());
    }

    @Test
    public void testUpdateCategoryAddUserParentCategory()
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryUserGroup{
        
        final Category parentCategory = this.categoryRepository.save(this.factory.getCategory(this.user, true));
        Category category = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        final Integer categoryId = category.getId();
        updateCategoryParameter.setId(categoryId);
        updateCategoryParameter.setAbbreviation(this.factory.getRandomAlphanumericString(5));
        updateCategoryParameter.setCategoryType(CategoryType.EXPENSE);
        updateCategoryParameter.setDescritpion(this.faker.superhero().descriptor());
        updateCategoryParameter.setLabel(this.faker.company().name());
        updateCategoryParameter.setParentId(parentCategory.getId());

        this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName());

        category = this.categoryRepository.findById(categoryId).orElseThrow();

        Assertions.assertEquals(parentCategory.getId(), category.getParent().getId());

    }

    @Test
    public void testUpdateCategoryAddGroupParentCategory()
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryUserGroup{
        
        final Category parentCategory = this.categoryRepository.save(this.factory.getCategory(this.group, true));
        Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        final Integer categoryId = category.getId();
        updateCategoryParameter.setId(categoryId);
        updateCategoryParameter.setAbbreviation(this.factory.getRandomAlphanumericString(5));
        updateCategoryParameter.setCategoryType(CategoryType.EXPENSE);
        updateCategoryParameter.setDescritpion(this.faker.superhero().descriptor());
        updateCategoryParameter.setLabel(this.faker.company().name());
        updateCategoryParameter.setParentId(parentCategory.getId());

        this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName());

        category = this.categoryRepository.findById(categoryId).orElseThrow();

        Assertions.assertEquals(parentCategory.getId(), category.getParent().getId());

    }

    @Test
    public void testUpdateCategoryWithNoUserParentCategory() {
        final User otherUser = this.userRepository.save(this.factory.getUser());
        final Category parentCategory = this.categoryRepository.save(this.factory.getCategory(otherUser, true));
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setId(category.getId());
        updateCategoryParameter.setParentId(parentCategory.getId());

        Assertions.assertThrows(CategoryNoUserException.class,
            () -> this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateCategoryWithNoExistParentCategory() {
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setId(category.getId());
        updateCategoryParameter.setParentId(this.factory.getRandomInteger());

        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateCategoryWithNoExistCategory() {
        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setId(this.factory.getRandomInteger());

        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateCategoryNoUserGroupCategory() {

        Group otherGroup = this.factory.getGroup();
        this.userRepository.save(otherGroup.getUserAdmin());
        otherGroup = this.groupRepository.save(otherGroup);
        final Category otherCathegory = this.categoryRepository.save(this.factory.getCategory(otherGroup, true));

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setId(otherCathegory.getId());

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateCategoryNoUserCategory() {

        final User otherUser = this.userRepository.save(this.factory.getUser());
        final Category otherCathegory = this.categoryRepository.save(this.factory.getCategory(otherUser, true));

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setId(otherCathegory.getId());

        Assertions.assertThrows(CategoryNoUserException.class,
            () -> this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateUserCategoryWithGroupParentCategory() {
        final Category parentCategory = this.categoryRepository.save(this.factory.getCategory(this.group, true));
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setId(category.getId());
        updateCategoryParameter.setParentId(parentCategory.getId());

        Assertions.assertThrows(BadAssociationCategoryUserGroup.class,
            () -> this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateGroupCategoryWithUserParentCategory() {
        final Category parentCategory = this.categoryRepository.save(this.factory.getCategory(this.user, true));
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setId(category.getId());
        updateCategoryParameter.setParentId(parentCategory.getId());

        Assertions.assertThrows(BadAssociationCategoryUserGroup.class,
            () -> this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateGroupCategoryWithNoUserGroupParentCategory() {
        Group otherGroup = this.factory.getGroup();
        this.userRepository.save(otherGroup.getUserAdmin());
        otherGroup = this.groupRepository.save(otherGroup);

        final Category parentCategory = this.categoryRepository.save(this.factory.getCategory(otherGroup, true));
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setId(category.getId());
        updateCategoryParameter.setParentId(parentCategory.getId());

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateGroupCategoryWithOtherUserGroupParentCategory() {
        Group otherGroup = this.factory.getGroup();
        this.userRepository.save(otherGroup.getUserAdmin());

        final List<User> users = otherGroup.getUsers();
        users.add(this.user);
        otherGroup.setUsers(users);

        otherGroup = this.groupRepository.save(otherGroup);

        final Category parentCategory = this.categoryRepository.save(this.factory.getCategory(otherGroup, true));
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setId(category.getId());
        updateCategoryParameter.setParentId(parentCategory.getId());

        Assertions.assertThrows(BadAssociationCategoryUserGroup.class,
            () -> this.categoryServiceImpl.updateCategory(updateCategoryParameter, this.user.getUserName()));
    }

    
}
