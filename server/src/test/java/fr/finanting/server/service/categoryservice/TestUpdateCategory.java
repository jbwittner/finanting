package fr.finanting.server.service.categoryservice;

import fr.finanting.server.generated.model.UpdateCategoryParameter;
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
    protected void initDataBeforeEach() {
        this.categoryServiceImpl = new CategoryServiceImpl(this.userRepository, this.groupRepository, this.categoryRepository);

        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(user);
        
    }

    @Test
    public void testUpdateCategoryWithoutParentCategory()
        throws CategoryNotExistException, BadAssociationCategoryUserGroupException, GroupNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryTypeException {

        Category category = this.testFactory.getCategory(this.user, true);

        final Integer categoryId = category.getId();

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString(5));
        updateCategoryParameter.setCategoryType(UpdateCategoryParameter.CategoryTypeEnum.EXPENSE);
        updateCategoryParameter.setDescription(this.faker.superhero().descriptor());
        updateCategoryParameter.setLabel(this.faker.company().name());
        
        this.categoryServiceImpl.updateCategory(categoryId, updateCategoryParameter, this.user.getUserName());

        category = this.categoryRepository.findById(categoryId).orElseThrow();

        Assertions.assertEquals(updateCategoryParameter.getAbbreviation().toUpperCase(), category.getAbbreviation());
        Assertions.assertEquals(updateCategoryParameter.getCategoryType().name(), category.getCategoryType().name());
        Assertions.assertEquals(updateCategoryParameter.getDescription(), category.getDescription());
        Assertions.assertEquals(updateCategoryParameter.getLabel(), category.getLabel());
        Assertions.assertEquals(this.user.getUserName(), category.getUser().getUserName());
        Assertions.assertNull(category.getGroup());
        Assertions.assertNull(category.getParent());
    }

    @Test
    public void testUpdateSimpleGroupCategory()
        throws CategoryNotExistException, BadAssociationCategoryUserGroupException, GroupNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryTypeException {

        Category category = this.testFactory.getCategory(this.group, true);

        final Integer categoryId = category.getId();

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString(5));
        updateCategoryParameter.setCategoryType(UpdateCategoryParameter.CategoryTypeEnum.EXPENSE);
        updateCategoryParameter.setDescription(this.faker.superhero().descriptor());
        updateCategoryParameter.setLabel(this.faker.company().name());
        
        this.categoryServiceImpl.updateCategory(categoryId, updateCategoryParameter, this.user.getUserName());

        category = this.categoryRepository.findById(categoryId).orElseThrow();

        Assertions.assertEquals(updateCategoryParameter.getAbbreviation().toUpperCase(), category.getAbbreviation());
        Assertions.assertEquals(updateCategoryParameter.getCategoryType().name(), category.getCategoryType().name());
        Assertions.assertEquals(updateCategoryParameter.getDescription(), category.getDescription());
        Assertions.assertEquals(updateCategoryParameter.getLabel(), category.getLabel());
        Assertions.assertNull(category.getUser());
        Assertions.assertEquals(this.group.getGroupName(), category.getGroup().getGroupName());
        Assertions.assertNull(category.getParent());
    }

    @Test
    public void testUpdateCategoryRemoveUserParentCategory()
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryUserGroupException, BadAssociationCategoryTypeException{
        
        final Category parentCategory = this.testFactory.getCategory(this.user, true);
        Category category = this.testFactory.getCategory(this.user, true);
        category.setParent(parentCategory);

        final Integer categoryId = category.getId();

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString(5));
        updateCategoryParameter.setCategoryType(UpdateCategoryParameter.CategoryTypeEnum.EXPENSE);
        updateCategoryParameter.setDescription(this.faker.superhero().descriptor());
        updateCategoryParameter.setLabel(this.faker.company().name());

        this.categoryServiceImpl.updateCategory(categoryId, updateCategoryParameter, this.user.getUserName());

        category = this.categoryRepository.findById(categoryId).orElseThrow();

        Assertions.assertNull(category.getParent());
    }

    @Test
    public void testUpdateCategoryAddUserParentCategory()
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryUserGroupException, BadAssociationCategoryTypeException{
        
        final Category parentCategory = this.testFactory.getCategory(this.user, true);
        Category category = this.testFactory.getCategory(this.user, true);

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        final Integer categoryId = category.getId();
        updateCategoryParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString(5));
        updateCategoryParameter.setCategoryType(UpdateCategoryParameter.CategoryTypeEnum.EXPENSE);
        updateCategoryParameter.setDescription(this.faker.superhero().descriptor());
        updateCategoryParameter.setLabel(this.faker.company().name());
        updateCategoryParameter.setParentId(parentCategory.getId());

        this.categoryServiceImpl.updateCategory(categoryId, updateCategoryParameter, this.user.getUserName());

        category = this.categoryRepository.findById(categoryId).orElseThrow();

        Assertions.assertEquals(parentCategory.getId(), category.getParent().getId());

    }

    @Test
    public void testUpdateCategoryAddGroupParentCategory()
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryUserGroupException, BadAssociationCategoryTypeException{
        
        final Category parentCategory = this.testFactory.getCategory(this.group, true);
        Category category = this.testFactory.getCategory(this.group, true);

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        final Integer categoryId = category.getId();
        updateCategoryParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString(5));
        updateCategoryParameter.setCategoryType(UpdateCategoryParameter.CategoryTypeEnum.EXPENSE);
        updateCategoryParameter.setDescription(this.faker.superhero().descriptor());
        updateCategoryParameter.setLabel(this.faker.company().name());
        updateCategoryParameter.setParentId(parentCategory.getId());

        this.categoryServiceImpl.updateCategory(categoryId, updateCategoryParameter, this.user.getUserName());

        category = this.categoryRepository.findById(categoryId).orElseThrow();

        Assertions.assertEquals(parentCategory.getId(), category.getParent().getId());

    }

    @Test
    public void testUpdateCategoryWithNoUserParentCategory() {
        final User otherUser = this.testFactory.getUser();
        final Category parentCategory = this.testFactory.getCategory(otherUser, true);
        final Category category = this.testFactory.getCategory(this.user, true);

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setParentId(parentCategory.getId());
        updateCategoryParameter.setCategoryType(UpdateCategoryParameter.CategoryTypeEnum.EXPENSE);

        Assertions.assertThrows(CategoryNoUserException.class,
            () -> this.categoryServiceImpl.updateCategory(category.getId(), updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateCategoryWithNoExistParentCategory() {
        final Category category = this.testFactory.getCategory(this.user, true);

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setParentId(this.testFactory.getRandomInteger());
        updateCategoryParameter.setCategoryType(UpdateCategoryParameter.CategoryTypeEnum.EXPENSE);

        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.categoryServiceImpl.updateCategory(category.getId(), updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateCategoryWithBadAssociationCategoryType() {
        final Category parentCategory = this.testFactory.getCategory(this.user, false);
        final Category category = this.testFactory.getCategory(this.user, true);

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setParentId(parentCategory.getId());
        updateCategoryParameter.setCategoryType(UpdateCategoryParameter.CategoryTypeEnum.EXPENSE);

        Assertions.assertThrows(BadAssociationCategoryTypeException.class,
            () -> this.categoryServiceImpl.updateCategory(category.getId(), updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateCategoryWithNoExistCategory() {
        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();

        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.categoryServiceImpl.updateCategory(this.testFactory.getRandomInteger(), updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateCategoryNoUserGroupCategory() {

        final Group otherGroup = this.testFactory.getGroup();
        final Category otherCategory = this.testFactory.getCategory(otherGroup, true);

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.categoryServiceImpl.updateCategory(otherCategory.getId(), updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateCategoryNoUserCategory() {

        final User otherUser = this.testFactory.getUser();
        final Category otherCategory = this.testFactory.getCategory(otherUser, true);

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();

        Assertions.assertThrows(CategoryNoUserException.class,
            () -> this.categoryServiceImpl.updateCategory(otherCategory.getId(), updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateUserCategoryWithGroupParentCategory() {
        final Category parentCategory = this.testFactory.getCategory(this.group, true);
        final Category category = this.testFactory.getCategory(this.user, true);

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setParentId(parentCategory.getId());
        updateCategoryParameter.setCategoryType(UpdateCategoryParameter.CategoryTypeEnum.EXPENSE);

        Assertions.assertThrows(BadAssociationCategoryUserGroupException.class,
            () -> this.categoryServiceImpl.updateCategory(category.getId(), updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateGroupCategoryWithUserParentCategory() {
        final Category parentCategory = this.testFactory.getCategory(this.user, true);
        final Category category = this.testFactory.getCategory(this.group, true);

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setParentId(parentCategory.getId());
        updateCategoryParameter.setCategoryType(UpdateCategoryParameter.CategoryTypeEnum.EXPENSE);

        Assertions.assertThrows(BadAssociationCategoryUserGroupException.class,
            () -> this.categoryServiceImpl.updateCategory(category.getId(), updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateGroupCategoryWithNoUserGroupParentCategory() {
        final Group otherGroup = this.testFactory.getGroup();

        final Category parentCategory = this.testFactory.getCategory(otherGroup, true);
        final Category category = this.testFactory.getCategory(this.group, true);

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setParentId(parentCategory.getId());
        updateCategoryParameter.setCategoryType(UpdateCategoryParameter.CategoryTypeEnum.EXPENSE);

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.categoryServiceImpl.updateCategory(category.getId(), updateCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateGroupCategoryWithOtherUserGroupParentCategory() {
        final Group otherGroup = this.testFactory.getGroup(this.user);

        final Category parentCategory = this.testFactory.getCategory(otherGroup, true);
        final Category category = this.testFactory.getCategory(this.group, true);

        final UpdateCategoryParameter updateCategoryParameter = new UpdateCategoryParameter();
        updateCategoryParameter.setParentId(parentCategory.getId());
        updateCategoryParameter.setCategoryType(UpdateCategoryParameter.CategoryTypeEnum.EXPENSE);

        Assertions.assertThrows(BadAssociationCategoryUserGroupException.class,
            () -> this.categoryServiceImpl.updateCategory(category.getId(), updateCategoryParameter, this.user.getUserName()));
    }

    
}
