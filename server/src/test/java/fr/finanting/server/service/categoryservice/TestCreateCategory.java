package fr.finanting.server.service.categoryservice;

import fr.finanting.server.dto.CategoryDTO;
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

import java.util.List;

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

    private CreateCategoryParameter createCategoryParameter;
    private Group group;
    private User user;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.categoryServiceImpl = new CategoryServiceImpl(this.userRepository, this.groupRepository, this.categoryRepository);
        this.createCategoryParameter = new CreateCategoryParameter();
        this.createCategoryParameter.setAbbreviation(this.factory.getRandomAlphanumericString(5));
        this.createCategoryParameter.setCategoryType(CategoryType.EXPENSE);
        this.createCategoryParameter.setDescritpion(this.faker.superhero().descriptor());
        this.createCategoryParameter.setLabel(this.faker.company().name());

        this.group = this.factory.getGroup();
        this.userRepository.save(this.group.getUserAdmin());

        this.user = this.userRepository.save(this.factory.getUser());

        final List<User> users = this.group.getUsers();
        users.add(user);
        this.group.setUsers(users);

        this.group = this.groupRepository.save(group);
        
    }

    @Test
    public void testCreateSimpleUserCategory()
        throws CategoryNotExistException, BadAssociationCategoryUserGroup, GroupNotExistException, CategoryNoUserException, UserNotInGroupException {
        final CategoryDTO categoryDTO = this.categoryServiceImpl.createCategory(this.createCategoryParameter, this.user.getUserName());

        final Category category = this.categoryRepository.findById(categoryDTO.getId()).orElseThrow();

        Assertions.assertEquals(this.createCategoryParameter.getAbbreviation().toUpperCase(), categoryDTO.getAbbreviation());
        Assertions.assertEquals(this.createCategoryParameter.getCategoryType(), categoryDTO.getCategoryType());
        Assertions.assertEquals(this.createCategoryParameter.getDescritpion(), categoryDTO.getDescritpion());
        Assertions.assertEquals(this.createCategoryParameter.getLabel(), categoryDTO.getLabel());
        Assertions.assertNull(categoryDTO.getParentId());

        Assertions.assertEquals(this.createCategoryParameter.getAbbreviation().toUpperCase(), category.getAbbreviation());
        Assertions.assertEquals(this.createCategoryParameter.getCategoryType(), category.getCategoryType());
        Assertions.assertEquals(this.createCategoryParameter.getDescritpion(), category.getDescritpion());
        Assertions.assertEquals(this.createCategoryParameter.getLabel(), category.getLabel());
        Assertions.assertEquals(this.user.getUserName(), category.getUser().getUserName());
        Assertions.assertNull(category.getGroup());
        Assertions.assertNull(category.getParent());
    }

    @Test
    public void testCreateSimpleGroupCategory()
        throws CategoryNotExistException, BadAssociationCategoryUserGroup, GroupNotExistException, CategoryNoUserException, UserNotInGroupException {
        this.createCategoryParameter.setGroupName(this.group.getGroupName());
        final CategoryDTO categoryDTO = this.categoryServiceImpl.createCategory(this.createCategoryParameter, this.user.getUserName());

        final Category category = this.categoryRepository.findById(categoryDTO.getId()).orElseThrow();

        Assertions.assertEquals(this.createCategoryParameter.getAbbreviation().toUpperCase(), categoryDTO.getAbbreviation());
        Assertions.assertEquals(this.createCategoryParameter.getCategoryType(), categoryDTO.getCategoryType());
        Assertions.assertEquals(this.createCategoryParameter.getDescritpion(), categoryDTO.getDescritpion());
        Assertions.assertEquals(this.createCategoryParameter.getLabel(), categoryDTO.getLabel());
        Assertions.assertNull(categoryDTO.getParentId());

        Assertions.assertEquals(this.createCategoryParameter.getAbbreviation().toUpperCase(), category.getAbbreviation());
        Assertions.assertEquals(this.createCategoryParameter.getCategoryType(), category.getCategoryType());
        Assertions.assertEquals(this.createCategoryParameter.getDescritpion(), category.getDescritpion());
        Assertions.assertEquals(this.createCategoryParameter.getLabel(), category.getLabel());
        Assertions.assertNull(category.getUser());
        Assertions.assertEquals(this.group.getGroupName(), category.getGroup().getGroupName());
        Assertions.assertNull(category.getParent());
    }

    @Test
    public void testCreateUserCategoryWithUserParentCategory()
        throws CategoryNotExistException, BadAssociationCategoryUserGroup, GroupNotExistException, CategoryNoUserException, UserNotInGroupException {
        
        final Category parentCategory = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        this.createCategoryParameter.setParentId(parentCategory.getId());

        final CategoryDTO categoryDTO = this.categoryServiceImpl.createCategory(this.createCategoryParameter, this.user.getUserName());

        final Category category = this.categoryRepository.findById(categoryDTO.getId()).orElseThrow();

        Assertions.assertEquals(this.createCategoryParameter.getAbbreviation().toUpperCase(), categoryDTO.getAbbreviation());
        Assertions.assertEquals(this.createCategoryParameter.getCategoryType(), categoryDTO.getCategoryType());
        Assertions.assertEquals(this.createCategoryParameter.getDescritpion(), categoryDTO.getDescritpion());
        Assertions.assertEquals(this.createCategoryParameter.getLabel(), categoryDTO.getLabel());
        Assertions.assertEquals(this.createCategoryParameter.getParentId(),categoryDTO.getParentId());

        Assertions.assertEquals(this.createCategoryParameter.getAbbreviation().toUpperCase(), category.getAbbreviation());
        Assertions.assertEquals(this.createCategoryParameter.getCategoryType(), category.getCategoryType());
        Assertions.assertEquals(this.createCategoryParameter.getDescritpion(), category.getDescritpion());
        Assertions.assertEquals(this.createCategoryParameter.getLabel(), category.getLabel());
        Assertions.assertEquals(this.user.getUserName(), category.getUser().getUserName());
        Assertions.assertNull(category.getGroup());
        Assertions.assertEquals(this.createCategoryParameter.getParentId(),category.getParent().getId());
    }

    @Test
    public void testCreateGroupCategoryWithGroupParentCategory()
        throws CategoryNotExistException, BadAssociationCategoryUserGroup, GroupNotExistException, CategoryNoUserException, UserNotInGroupException {
        final Category parentCategory = this.categoryRepository.save(this.factory.getCategory(this.group, true));
        
        this.createCategoryParameter.setParentId(parentCategory.getId());
        this.createCategoryParameter.setGroupName(this.group.getGroupName());

        final CategoryDTO categoryDTO = this.categoryServiceImpl.createCategory(this.createCategoryParameter, this.user.getUserName());

        final Category category = this.categoryRepository.findById(categoryDTO.getId()).orElseThrow();

        Assertions.assertEquals(this.createCategoryParameter.getAbbreviation().toUpperCase(), categoryDTO.getAbbreviation());
        Assertions.assertEquals(this.createCategoryParameter.getCategoryType(), categoryDTO.getCategoryType());
        Assertions.assertEquals(this.createCategoryParameter.getDescritpion(), categoryDTO.getDescritpion());
        Assertions.assertEquals(this.createCategoryParameter.getLabel(), categoryDTO.getLabel());
        Assertions.assertEquals(this.createCategoryParameter.getParentId(),categoryDTO.getParentId());

        Assertions.assertEquals(this.createCategoryParameter.getAbbreviation().toUpperCase(), category.getAbbreviation());
        Assertions.assertEquals(this.createCategoryParameter.getCategoryType(), category.getCategoryType());
        Assertions.assertEquals(this.createCategoryParameter.getDescritpion(), category.getDescritpion());
        Assertions.assertEquals(this.createCategoryParameter.getLabel(), category.getLabel());
        Assertions.assertNull(category.getUser());
        Assertions.assertEquals(this.group.getGroupName(), category.getGroup().getGroupName());
        Assertions.assertEquals(this.createCategoryParameter.getParentId(),category.getParent().getId());
    }

    @Test
    public void testCreateCategoryWithNoExistGroup() {
        this.createCategoryParameter.setGroupName(this.factory.getRandomAlphanumericString());

        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.categoryServiceImpl.createCategory(this.createCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateCategoryWithNoExistParentCategory() {
        this.createCategoryParameter.setParentId(this.factory.getRandomInteger());

        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.categoryServiceImpl.createCategory(this.createCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateCategoryWithBadAssociationtUserParentCategory() {
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        this.createCategoryParameter.setParentId(category.getId());

        Assertions.assertThrows(BadAssociationCategoryUserGroup.class,
            () -> this.categoryServiceImpl.createCategory(this.createCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateCategoryWithBadAssociationtNonExistGroupParentCategory() {
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        this.createCategoryParameter.setParentId(category.getId());
        this.createCategoryParameter.setGroupName(this.factory.getRandomAlphanumericString());

        Assertions.assertThrows(BadAssociationCategoryUserGroup.class,
            () -> this.categoryServiceImpl.createCategory(this.createCategoryParameter, this.user.getUserName()));
    }

    
    @Test
    public void testCreateCategoryWithBadAssociationtGroupParentCategory() {
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        this.createCategoryParameter.setGroupName(this.group.getGroupName());
        this.createCategoryParameter.setParentId(category.getId());

        Assertions.assertThrows(BadAssociationCategoryUserGroup.class,
            () -> this.categoryServiceImpl.createCategory(this.createCategoryParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateCategoryWithNoUserParentCategory() {
        final User otherUser = this.userRepository.save(this.factory.getUser());
        final Category category = this.categoryRepository.save(this.factory.getCategory(otherUser, true));

        this.createCategoryParameter.setParentId(category.getId());

        Assertions.assertThrows(CategoryNoUserException.class,
            () -> this.categoryServiceImpl.createCategory(this.createCategoryParameter, this.user.getUserName()));
    }

    
    
}
