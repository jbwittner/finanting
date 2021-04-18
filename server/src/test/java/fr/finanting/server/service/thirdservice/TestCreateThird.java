package fr.finanting.server.service.thirdservice;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.BadAssociationThirdException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Category;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.Third;
import fr.finanting.server.model.User;
import fr.finanting.server.model.embeddable.Address;
import fr.finanting.server.model.embeddable.BankDetails;
import fr.finanting.server.model.embeddable.Contact;
import fr.finanting.server.parameter.CreateThirdParameter;
import fr.finanting.server.parameter.subpart.AddressParameter;
import fr.finanting.server.parameter.subpart.BankDetailsParameter;
import fr.finanting.server.parameter.subpart.ContactParameter;
import fr.finanting.server.repository.CategoryRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.ThirdRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.ThirdServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestCreateThird extends AbstractMotherIntegrationTest {

    @Autowired
    private ThirdRepository thirdRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private ThirdServiceImpl thirdServiceImpl;

    private User user;
    private Group group;

    private CreateThirdParameter createThirdParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.thirdServiceImpl = new ThirdServiceImpl(this.thirdRepository,
                                                    this.userRepository,
                                                    this.groupRepository,
                                                    this.categoryRepository);
        
        this.group = this.factory.getGroup();
        this.userRepository.save(this.group.getUserAdmin());

        this.user = this.userRepository.save(this.factory.getUser());

        final List<User> users = this.group.getUsers();
        users.add(user);
        this.group.setUsers(users);

        this.group = this.groupRepository.save(group);

        this.createThirdParameter = new CreateThirdParameter();
        this.createThirdParameter.setAbbreviation(this.factory.getRandomAlphanumericString(5).toLowerCase());
        this.createThirdParameter.setDescritpion(this.faker.superhero().descriptor());
        this.createThirdParameter.setLabel(this.faker.company().name());

        final ContactParameter contactParameter = new ContactParameter();
        contactParameter.setEmail(this.faker.internet().emailAddress());
        contactParameter.setHomePhone(this.faker.phoneNumber().phoneNumber());
        contactParameter.setPortablePhone(this.faker.phoneNumber().cellPhone());
        contactParameter.setWebsite(this.faker.internet().url());
        this.createThirdParameter.setContactParameter(contactParameter);

        final BankDetailsParameter bankDetailsParameter = new BankDetailsParameter();
        bankDetailsParameter.setBankName(this.faker.dragonBall().character());
        bankDetailsParameter.setAccountNumber(this.factory.getRandomAlphanumericString());
        bankDetailsParameter.setIban(this.factory.getRandomAlphanumericString());
        this.createThirdParameter.setBankDetailsParameter(bankDetailsParameter);

        final AddressParameter addressParameter = new AddressParameter();
        addressParameter.setAddress(this.faker.address().fullAddress());
        addressParameter.setCity(this.faker.address().city());
        addressParameter.setStreet(this.faker.address().streetAddress());
        addressParameter.setZipCode(this.faker.address().zipCode());
        this.createThirdParameter.setAddressParameter(addressParameter);

    }

    private void checkData(final Third third){

        Assertions.assertEquals(this.createThirdParameter.getAbbreviation().toUpperCase(), third.getAbbreviation());
        Assertions.assertEquals(this.createThirdParameter.getDescritpion(), third.getDescritpion());
        Assertions.assertEquals(this.createThirdParameter.getLabel(), third.getLabel());
        
        if(this.createThirdParameter.getBankDetailsParameter() != null){
            BankDetails bankDetails = third.getBankDetails();
            Assertions.assertNotNull(bankDetails);
            BankDetailsParameter bankDetailsParameter = this.createThirdParameter.getBankDetailsParameter();
            Assertions.assertEquals(bankDetailsParameter.getIban(), bankDetails.getIban());
            Assertions.assertEquals(bankDetailsParameter.getAccountNumber(), bankDetails.getAccountNumber());
            Assertions.assertEquals(bankDetailsParameter.getBankName(), bankDetails.getBankName());
        }
        

        if(this.createThirdParameter.getAddressParameter() != null){
            Address address = third.getAddress();
            Assertions.assertNotNull(address);
            AddressParameter addressParameter = this.createThirdParameter.getAddressParameter();
            Assertions.assertEquals(addressParameter.getAddress(), address.getAddress());
            Assertions.assertEquals(addressParameter.getStreet(), address.getStreet());
            Assertions.assertEquals(addressParameter.getCity(), address.getCity());
            Assertions.assertEquals(addressParameter.getZipCode(), address.getZipCode());
        }

        if(this.createThirdParameter.getContactParameter() != null){
            Contact contact = third.getContact();
            Assertions.assertNotNull(contact);
            ContactParameter contactParameter = this.createThirdParameter.getContactParameter();
            Assertions.assertEquals(contactParameter.getHomePhone(), contact.getHomePhone());
            Assertions.assertEquals(contactParameter.getPortablePhone(), contact.getPortablePhone());
            Assertions.assertEquals(contactParameter.getEmail(), contact.getEmail());
            Assertions.assertEquals(contactParameter.getWebsite(), contact.getWebsite());
        }

    }

    @Test
    public void testCreateUserThird() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        this.thirdServiceImpl.createThird(this.createThirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testCreateUserThirdWithoutAddressParameter() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        this.createThirdParameter.setAddressParameter(null);
        this.thirdServiceImpl.createThird(this.createThirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }
    
    @Test
    public void testCreateUserThirdWithoutBankDetailsParameter() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        this.createThirdParameter.setBankDetailsParameter(null);
        this.thirdServiceImpl.createThird(this.createThirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testCreateUserThirdWithoutContactParameter() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        this.createThirdParameter.setContactParameter(null);
        this.thirdServiceImpl.createThird(this.createThirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testCreateGroupThird() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        this.createThirdParameter.setGroupName(this.group.getGroupName());
        this.thirdServiceImpl.createThird(this.createThirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getGroup().getGroupName(), this.group.getGroupName());
        Assertions.assertNull(third.getUser());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testCreateUserThirdWithDefaultUserCategory() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        this.createThirdParameter.setDefaultCategoryId(category.getId());
        this.thirdServiceImpl.createThird(this.createThirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNotNull(third.getDefaultCategory());

        Assertions.assertEquals(this.createThirdParameter.getDefaultCategoryId(), third.getDefaultCategory().getId());
        
    }

    @Test
    public void testCreateGroupThirdWithDefaultGroupCategory() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        this.createThirdParameter.setDefaultCategoryId(category.getId());
        this.createThirdParameter.setGroupName(this.group.getGroupName());
        this.thirdServiceImpl.createThird(this.createThirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getGroup().getGroupName(), this.group.getGroupName());
        Assertions.assertNull(third.getUser());
        Assertions.assertNotNull(third.getDefaultCategory());

        Assertions.assertEquals(this.createThirdParameter.getDefaultCategoryId(), third.getDefaultCategory().getId());
        
    }

    @Test
    public void testCreateUserThirdWithGroupCategory() {
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        this.createThirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.createThird(this.createThirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testCreateUserThirdWithOtherUserCategory() {
        final User otherUser = this.userRepository.save(this.factory.getUser());
        final Category category = this.categoryRepository.save(this.factory.getCategory(otherUser, true));

        this.createThirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.createThird(this.createThirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testCreateGroupThirdWithOtherGroupCategory() {
        Group otherGroup = this.factory.getGroup();
        this.userRepository.save(otherGroup.getUserAdmin());
        this.groupRepository.save(otherGroup);

        final Category category = this.categoryRepository.save(this.factory.getCategory(otherGroup, true));

        this.createThirdParameter.setDefaultCategoryId(category.getId());
        this.createThirdParameter.setGroupName(this.group.getGroupName());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.createThird(this.createThirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testCreateGroupThirdWithUserCategory() {
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        this.createThirdParameter.setDefaultCategoryId(category.getId());
        this.createThirdParameter.setGroupName(this.group.getGroupName());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.createThird(this.createThirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testCreateGroupThirdWithNonExistentGroup() {
        this.createThirdParameter.setGroupName(this.factory.getRandomAlphanumericString());

        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.thirdServiceImpl.createThird(this.createThirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testCreateGroupThirdWithUserNotInGroup() {
        User otherUser = this.userRepository.save(this.factory.getUser());
        this.createThirdParameter.setGroupName(this.group.getGroupName());

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.thirdServiceImpl.createThird(this.createThirdParameter, otherUser.getUserName()));       
    }

    @Test
    public void testCreateUserThirdWithNonExistentCategory() {
        this.createThirdParameter.setDefaultCategoryId(this.factory.getRandomInteger());

        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.thirdServiceImpl.createThird(this.createThirdParameter, this.user.getUserName()));       
    }
    
}
