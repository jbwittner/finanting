package fr.finanting.server.service.categoryservice;

import fr.finanting.codegen.model.CategoryParameter;
import fr.finanting.server.exception.BadAssociationCategoryTypeException;
import fr.finanting.server.exception.BadAssociationCategoryUserGroupException;
import fr.finanting.server.exception.CategoryNoUserException;
import fr.finanting.server.exception.CategoryNotExistException;
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestCreateCategory extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryServiceImpl categoryServiceImpl;

    private CategoryParameter categoryParameter;
    private Group group;
    private User user;

    @Override
    protected void initDataBeforeEach() {
        this.categoryServiceImpl = new CategoryServiceImpl(this.userRepository, this.groupRepository, this.categoryRepository);
        this.categoryParameter = new CategoryParameter();
        this.categoryParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString(5));
        this.categoryParameter.setCategoryType(CategoryParameter.CategoryTypeEnum.EXPENSE);
        this.categoryParameter.setDescription(this.faker.superhero().descriptor());
        this.categoryParameter.setLabel(this.faker.company().name());

        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(user);
        
    }

    @Test
    public void testCreateSimpleUserCategory()
        throws CategoryNotExistException, BadAssociationCategoryUserGroupException, GroupNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryTypeException {
        this.categoryServiceImpl.createCategory(this.categoryParameter, this.user.getUserName());
        final Category category = this.categoryRepository.findAll().get(0);

        Assertions.assertEquals(this.categoryParameter.getAbbreviation().toUpperCase(), category.getAbbreviation());
        Assertions.assertEquals(this.categoryParameter.getCategoryType().name(), category.getCategoryType().name());
        Assertions.assertEquals(this.categoryParameter.getDescription(), category.getDescription());
        Assertions.assertEquals(this.categoryParameter.getLabel(), category.getLabel());
        Assertions.assertEquals(this.user.getUserName(), category.getUser().getUserName());
        Assertions.assertNull(category.getGroup());
        Assertions.assertNull(category.getParent());
    }

    @Test
    public void testCreateSimpleGroupCategory()
        throws CategoryNotExistException, BadAssociationCategoryUserGroupException, GroupNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryTypeException {
        this.categoryParameter.setGroupName(this.group.getGroupName());
        
        this.categoryServiceImpl.createCategory(this.categoryParameter, this.user.getUserName());

        final Category category = this.categoryRepository.findAll().get(0);

        Assertions.assertEquals(this.categoryParameter.getAbbreviation().toUpperCase(), category.getAbbreviation());
        Assertions.assertEquals(this.categoryParameter.getCategoryType().name(), category.getCategoryType().name());
        Assertions.assertEquals(this.categoryParameter.getDescription(), category.getDescription());
        Assertions.assertEquals(this.categoryParameter.getLabel(), category.getLabel());
        Assertions.assertNull(category.getUser());
        Assertions.assertEquals(this.group.getGroupName(), category.getGroup().getGroupName());
        Assertions.assertNull(category.getParent());
    }

    @Test
    public void testCreateUserCategoryWithUserParentCategory()
        throws CategoryNotExistException, BadAssociationCategoryUserGroupException, GroupNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryTypeException {
        
        final Category parentCategory = this.testFactory.getCategory(this.user, true);

        this.categoryParameter.setParentId(parentCategory.getId());

        this.categoryServiceImpl.createCategory(this.categoryParameter, this.user.getUserName());

        final Category category = this.categoryRepository.findById(parentCategory.getId() + 1).orElseThrow();

        Assertions.assertEquals(this.categoryParameter.getAbbreviation().toUpperCase(), category.getAbbreviation());
        Assertions.assertEquals(this.categoryParameter.getCategoryType().name(), category.getCategoryType().name());
        Assertions.assertEquals(this.categoryParameter.getDescription(), category.getDescription());
        Assertions.assertEquals(this.categoryParameter.getLabel(), category.getLabel());
        Assertions.assertEquals(this.user.getUserName(), category.getUser().getUserName());
        Assertions.assertNull(category.getGroup());
        Assertions.assertEquals(this.categoryParameter.getParentId(),category.getParent().getId());
    }

    @Test
    public void testCreateGroupCategoryWithGroupParentCategory()
        throws CategoryNotExistException, BadAssociationCategoryUserGroupException, GroupNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryTypeException {
        final Category parentCategory = this.testFactory.getCategory(this.group, true);
        
        this.categoryParameter.setParentId(parentCategory.getId());
        this.categoryParameter.setGroupName(this.group.getGroupName());

        this.categoryServiceImpl.createCategory(this.categoryParameter, this.user.getUserName());

        final Category category = this.categoryRepository.findById(parentCategory.getId() + 1).orElseThrow();

        Assertions.assertEquals(this.categoryParameter.getAbbreviation().toUpperCase(), category.getAbbreviation());
        Assertions.assertEquals(this.categoryParameter.getCategoryType().name(), category.getCategoryType().name());
        Assertions.assertEquals(this.categoryParameter.getDescription(), category.getDescription());
        Assertions.assertEquals(this.categoryParameter.getDescription(), category.getDescription());
        Assertions.assertEquals(this.categoryParameter.getLabel(), category.getLabel());
        Assertions.assertNull(category.getUser());
        Assertions.assertEquals(this.group.getGroupName(), category.getGroup().getGroupName());
        Assertions.assertEquals(this.categoryParameter.getParentId(),category.getParent().getId());
    }

    @Test
    public void testCreateCategoryWithNoExistGroup() {
        this.categoryParameter.setGroupName(this.testFactory.getRandomAlphanumericString());

        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.categoryServiceImpl.createCategory(this.categoryParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateCategoryWithNoExistParentCategory() {
        this.categoryParameter.setParentId(this.testFactory.getRandomInteger());

        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.categoryServiceImpl.createCategory(this.categoryParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateCategoryWithBadAssociationCategoryType() {
        final Category category = this.testFactory.getCategory(this.user, false);

        this.categoryParameter.setParentId(category.getId());

        Assertions.assertThrows(BadAssociationCategoryTypeException.class,
            () -> this.categoryServiceImpl.createCategory(this.categoryParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateCategoryWithBadAssociationUserParentCategory() {
        final Category category = this.testFactory.getCategory(this.group, true);

        this.categoryParameter.setParentId(category.getId());

        Assertions.assertThrows(BadAssociationCategoryUserGroupException.class,
            () -> this.categoryServiceImpl.createCategory(this.categoryParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateCategoryWithBadAssociationNonExistGroupParentCategory() {
        final Category category = this.testFactory.getCategory(this.group, true);

        this.categoryParameter.setParentId(category.getId());
        this.categoryParameter.setGroupName(this.testFactory.getRandomAlphanumericString());

        Assertions.assertThrows(BadAssociationCategoryUserGroupException.class,
            () -> this.categoryServiceImpl.createCategory(this.categoryParameter, this.user.getUserName()));
    }

    
    @Test
    public void testCreateCategoryWithBadAssociationGroupParentCategory() {
        final Category category = this.testFactory.getCategory(this.user, true);

        this.categoryParameter.setGroupName(this.group.getGroupName());
        this.categoryParameter.setParentId(category.getId());

        Assertions.assertThrows(BadAssociationCategoryUserGroupException.class,
            () -> this.categoryServiceImpl.createCategory(this.categoryParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateCategoryWithNoUserParentCategory() {
        final User otherUser = this.testFactory.getUser();
        final Category category = this.testFactory.getCategory(otherUser, true);

        this.categoryParameter.setParentId(category.getId());

        Assertions.assertThrows(CategoryNoUserException.class,
            () -> this.categoryServiceImpl.createCategory(this.categoryParameter, this.user.getUserName()));
    }

    
    
}
