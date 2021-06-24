package fr.finanting.server.service.thirdservice;

import fr.finanting.server.generated.model.AddressParameter;
import fr.finanting.server.generated.model.BankDetailsParameter;
import fr.finanting.server.generated.model.ContactParameter;
import fr.finanting.server.generated.model.ThirdParameter;
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

    private ThirdParameter thirdParameter;

    @Override
    protected void initDataBeforeEach() {
        this.thirdServiceImpl = new ThirdServiceImpl(this.thirdRepository,
                                                    this.userRepository,
                                                    this.groupRepository,
                                                    this.categoryRepository);
        
        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(user);


        this.thirdParameter = new ThirdParameter();
        this.thirdParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString(5).toLowerCase());
        this.thirdParameter.setDescription(this.faker.superhero().descriptor());
        this.thirdParameter.setLabel(this.faker.company().name());

        final ContactParameter contactParameter = new ContactParameter();
        contactParameter.setEmail(this.faker.internet().emailAddress());
        contactParameter.setHomePhone(this.faker.phoneNumber().phoneNumber());
        contactParameter.setPortablePhone(this.faker.phoneNumber().cellPhone());
        contactParameter.setWebsite(this.faker.internet().url());
        this.thirdParameter.setContactParameter(contactParameter);

        final BankDetailsParameter bankDetailsParameter = new BankDetailsParameter();
        bankDetailsParameter.setBankName(this.faker.dragonBall().character());
        bankDetailsParameter.setAccountNumber(this.testFactory.getRandomAlphanumericString());
        bankDetailsParameter.setIban(this.testFactory.getRandomAlphanumericString());
        this.thirdParameter.setBankDetailsParameter(bankDetailsParameter);

        final AddressParameter addressParameter = new AddressParameter();
        addressParameter.setAddress(this.faker.address().fullAddress());
        addressParameter.setCity(this.faker.address().city());
        addressParameter.setStreet(this.faker.address().streetAddress());
        addressParameter.setZipCode(this.faker.address().zipCode());
        this.thirdParameter.setAddressParameter(addressParameter);

    }

    private void checkData(final Third third){

        Assertions.assertEquals(this.thirdParameter.getAbbreviation().toUpperCase(), third.getAbbreviation());
        Assertions.assertEquals(this.thirdParameter.getDescription(), third.getDescription());
        Assertions.assertEquals(this.thirdParameter.getLabel(), third.getLabel());
        
        if(this.thirdParameter.getBankDetailsParameter() != null){
            final BankDetails bankDetails = third.getBankDetails();
            Assertions.assertNotNull(bankDetails);
            final BankDetailsParameter bankDetailsParameter = this.thirdParameter.getBankDetailsParameter();
            Assertions.assertEquals(bankDetailsParameter.getIban(), bankDetails.getIban());
            Assertions.assertEquals(bankDetailsParameter.getAccountNumber(), bankDetails.getAccountNumber());
            Assertions.assertEquals(bankDetailsParameter.getBankName(), bankDetails.getBankName());
        }
        

        if(this.thirdParameter.getAddressParameter() != null){
            final Address address = third.getAddress();
            Assertions.assertNotNull(address);
            final AddressParameter addressParameter = this.thirdParameter.getAddressParameter();
            Assertions.assertEquals(addressParameter.getAddress(), address.getAddress());
            Assertions.assertEquals(addressParameter.getStreet(), address.getStreet());
            Assertions.assertEquals(addressParameter.getCity(), address.getCity());
            Assertions.assertEquals(addressParameter.getZipCode(), address.getZipCode());
        }

        if(this.thirdParameter.getContactParameter() != null){
            final Contact contact = third.getContact();
            Assertions.assertNotNull(contact);
            final ContactParameter contactParameter = this.thirdParameter.getContactParameter();
            Assertions.assertEquals(contactParameter.getHomePhone(), contact.getHomePhone());
            Assertions.assertEquals(contactParameter.getPortablePhone(), contact.getPortablePhone());
            Assertions.assertEquals(contactParameter.getEmail(), contact.getEmail());
            Assertions.assertEquals(contactParameter.getWebsite(), contact.getWebsite());
        }

    }

    @Test
    public void testCreateUserThird() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        this.thirdServiceImpl.createThird(this.thirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testCreateUserThirdWithoutAddressParameter() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        this.thirdParameter.setAddressParameter(null);
        this.thirdServiceImpl.createThird(this.thirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }
    
    @Test
    public void testCreateUserThirdWithoutBankDetailsParameter() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        this.thirdParameter.setBankDetailsParameter(null);
        this.thirdServiceImpl.createThird(this.thirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testCreateUserThirdWithoutContactParameter() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        this.thirdParameter.setContactParameter(null);
        this.thirdServiceImpl.createThird(this.thirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testCreateGroupThird() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        this.thirdParameter.setGroupName(this.group.getGroupName());
        this.thirdServiceImpl.createThird(this.thirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getGroup().getGroupName(), this.group.getGroupName());
        Assertions.assertNull(third.getUser());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testCreateUserThirdWithDefaultUserCategory() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        final Category category = this.testFactory.getCategory(this.user, true);

        this.thirdParameter.setDefaultCategoryId(category.getId());
        this.thirdServiceImpl.createThird(this.thirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNotNull(third.getDefaultCategory());

        Assertions.assertEquals(this.thirdParameter.getDefaultCategoryId(), third.getDefaultCategory().getId());
        
    }

    @Test
    public void testCreateGroupThirdWithDefaultGroupCategory() throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        final Category category = this.testFactory.getCategory(this.group, true);

        this.thirdParameter.setDefaultCategoryId(category.getId());
        this.thirdParameter.setGroupName(this.group.getGroupName());
        this.thirdServiceImpl.createThird(this.thirdParameter, this.user.getUserName());

        final Third third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getGroup().getGroupName(), this.group.getGroupName());
        Assertions.assertNull(third.getUser());
        Assertions.assertNotNull(third.getDefaultCategory());

        Assertions.assertEquals(this.thirdParameter.getDefaultCategoryId(), third.getDefaultCategory().getId());
        
    }

    @Test
    public void testCreateUserThirdWithGroupCategory() {
        final Category category = this.testFactory.getCategory(this.group, true);

        this.thirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.createThird(this.thirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testCreateUserThirdWithOtherUserCategory() {
        final User otherUser = this.testFactory.getUser();
        final Category category = this.testFactory.getCategory(otherUser, true);

        this.thirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.createThird(this.thirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testCreateGroupThirdWithOtherGroupCategory() {
        final Group otherGroup = this.testFactory.getGroup();
        final Category category = this.testFactory.getCategory(otherGroup, true);

        this.thirdParameter.setDefaultCategoryId(category.getId());
        this.thirdParameter.setGroupName(this.group.getGroupName());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.createThird(this.thirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testCreateGroupThirdWithUserCategory() {
        final Category category = this.testFactory.getCategory(this.user, true);

        this.thirdParameter.setDefaultCategoryId(category.getId());
        this.thirdParameter.setGroupName(this.group.getGroupName());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.createThird(this.thirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testCreateGroupThirdWithNonExistentGroup() {
        this.thirdParameter.setGroupName(this.testFactory.getRandomAlphanumericString());

        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.thirdServiceImpl.createThird(this.thirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testCreateGroupThirdWithUserNotInGroup() {
        final User otherUser = this.testFactory.getUser();
        this.thirdParameter.setGroupName(this.group.getGroupName());

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.thirdServiceImpl.createThird(this.thirdParameter, otherUser.getUserName()));       
    }

    @Test
    public void testCreateUserThirdWithNonExistentCategory() {
        this.thirdParameter.setDefaultCategoryId(this.testFactory.getRandomInteger());

        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.thirdServiceImpl.createThird(this.thirdParameter, this.user.getUserName()));       
    }
    
}
