package fr.finanting.server.service.thirdservice;

import fr.finanting.server.codegen.model.AddressParameter;
import fr.finanting.server.codegen.model.BankDetailsParameter;
import fr.finanting.server.codegen.model.ContactParameter;
import fr.finanting.server.codegen.model.UpdateThirdParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.BadAssociationThirdException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.ThirdNoUserException;
import fr.finanting.server.exception.ThirdNotExistException;
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

public class TestUpdateThird extends AbstractMotherIntegrationTest {

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

    private UpdateThirdParameter updateThirdParameter;

    @Override
    protected void initDataBeforeEach() {
        this.thirdServiceImpl = new ThirdServiceImpl(this.thirdRepository,
                                                    this.userRepository,
                                                    this.groupRepository,
                                                    this.categoryRepository);
        
        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(user);

        this.updateThirdParameter = new UpdateThirdParameter();
        this.updateThirdParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString(5).toLowerCase());
        this.updateThirdParameter.setDescription(this.faker.superhero().descriptor());
        this.updateThirdParameter.setLabel(this.faker.company().name());

        final ContactParameter contactParameter = new ContactParameter();
        contactParameter.setEmail(this.faker.internet().emailAddress());
        contactParameter.setHomePhone(this.faker.phoneNumber().phoneNumber());
        contactParameter.setPortablePhone(this.faker.phoneNumber().cellPhone());
        contactParameter.setWebsite(this.faker.internet().url());
        this.updateThirdParameter.setContactParameter(contactParameter);

        final BankDetailsParameter bankDetailsParameter = new BankDetailsParameter();
        bankDetailsParameter.setBankName(this.faker.dragonBall().character());
        bankDetailsParameter.setAccountNumber(this.testFactory.getRandomAlphanumericString());
        bankDetailsParameter.setIban(this.testFactory.getRandomAlphanumericString());
        this.updateThirdParameter.setBankDetailsParameter(bankDetailsParameter);

        final AddressParameter addressParameter = new AddressParameter();
        addressParameter.setAddress(this.faker.address().fullAddress());
        addressParameter.setCity(this.faker.address().city());
        addressParameter.setStreet(this.faker.address().streetAddress());
        addressParameter.setZipCode(this.faker.address().zipCode());
        this.updateThirdParameter.setAddressParameter(addressParameter);


    }

    private void checkData(final Third third){

        Assertions.assertEquals(this.updateThirdParameter.getAbbreviation().toUpperCase(), third.getAbbreviation());
        Assertions.assertEquals(this.updateThirdParameter.getDescription(), third.getDescription());
        Assertions.assertEquals(this.updateThirdParameter.getLabel(), third.getLabel());

        final BankDetails bankDetails = third.getBankDetails();
        final Address address = third.getAddress();
        final Contact contact = third.getContact();

        
        if(this.updateThirdParameter.getBankDetailsParameter() == null){
            Assertions.assertNull(bankDetails);
        } else {
            Assertions.assertNotNull(bankDetails);
            final BankDetailsParameter bankDetailsParameter = this.updateThirdParameter.getBankDetailsParameter();
            Assertions.assertEquals(bankDetailsParameter.getIban(), bankDetails.getIban());
            Assertions.assertEquals(bankDetailsParameter.getAccountNumber(), bankDetails.getAccountNumber());
            Assertions.assertEquals(bankDetailsParameter.getBankName(), bankDetails.getBankName());
        }

        if(this.updateThirdParameter.getAddressParameter() == null){
            Assertions.assertNull(address);
        } else {
            Assertions.assertNotNull(address);
            final AddressParameter addressParameter = this.updateThirdParameter.getAddressParameter();
            Assertions.assertEquals(addressParameter.getAddress(), address.getAddress());
            Assertions.assertEquals(addressParameter.getStreet(), address.getStreet());
            Assertions.assertEquals(addressParameter.getCity(), address.getCity());
            Assertions.assertEquals(addressParameter.getZipCode(), address.getZipCode());
        }

        if(this.updateThirdParameter.getContactParameter() == null){
            Assertions.assertNull(contact);
        } else {
            Assertions.assertNotNull(contact);
            final ContactParameter contactParameter = this.updateThirdParameter.getContactParameter();
            Assertions.assertEquals(contactParameter.getHomePhone(), contact.getHomePhone());
            Assertions.assertEquals(contactParameter.getPortablePhone(), contact.getPortablePhone());
            Assertions.assertEquals(contactParameter.getEmail(), contact.getEmail());
            Assertions.assertEquals(contactParameter.getWebsite(), contact.getWebsite());
        }

    }

    @Test
    public void testUpdateUserThird()
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException {

        Third third = this.testFactory.getThird(this.user);
        
        this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, this.user.getUserName());

        third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testUpdateGroupThird()
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException {
            
        Third third = this.testFactory.getThird(this.group);

        this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, this.user.getUserName());

        third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getGroup().getGroupName(), this.group.getGroupName());
        Assertions.assertNull(third.getUser());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    
    @Test
    public void testUpdateUserThirdWithoutAddressParameter()
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException {

        Third third = this.testFactory.getThird(this.user);

        this.updateThirdParameter.setAddressParameter(null);
        this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, this.user.getUserName());

        third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }
    
    @Test
    public void testUpdateUserThirdWithoutBankDetailsParameter()
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException {
            
        Third third = this.testFactory.getThird(this.user);

        this.updateThirdParameter.setBankDetailsParameter(null);
        this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, this.user.getUserName());

        third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testUpdateUserThirdWithoutContactParameter()
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException {
            
        Third third = this.testFactory.getThird(this.user);

        this.updateThirdParameter.setContactParameter(null);
        this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, this.user.getUserName());

        third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testUpdateUserThirdAddingDefaultUserCategory()
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException {
            
        Third third = this.testFactory.getThird(this.user);

        final Category category = this.testFactory.getCategory(this.user, true);

        this.updateThirdParameter.setDefaultCategoryId(category.getId());
        this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, this.user.getUserName());

        third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNotNull(third.getDefaultCategory());

        Assertions.assertEquals(this.updateThirdParameter.getDefaultCategoryId(), third.getDefaultCategory().getId());
        
    }

    @Test
    public void testUpdateGroupThirdAddingDefaultGroupCategory()
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException {
            
        Third third = this.testFactory.getThird(this.group);

        final Category category = this.testFactory.getCategory(this.group, true);

        this.updateThirdParameter.setDefaultCategoryId(category.getId());
        this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, this.user.getUserName());

        third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getGroup().getGroupName(), this.group.getGroupName());
        Assertions.assertNull(third.getUser());
        Assertions.assertNotNull(third.getDefaultCategory());

        Assertions.assertEquals(this.updateThirdParameter.getDefaultCategoryId(), third.getDefaultCategory().getId());
        
    }

    @Test
    public void testUpdateOtherUserThird() {
        final User otherUser = this.testFactory.getUser();

        final Third third = this.testFactory.getThird(otherUser);

        final Category category = this.testFactory.getCategory(this.group, true);

        this.updateThirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(ThirdNoUserException.class,
            () -> this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateUserThirdWithGroupCategory() {
        final Third third = this.testFactory.getThird(this.user);

        final Category category = this.testFactory.getCategory(this.group, true);

        this.updateThirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateUserThirdWithOtherUserCategory() {
        final Third third = this.testFactory.getThird(this.user);

        final User otherUser = this.testFactory.getUser();
        final Category category = this.testFactory.getCategory(otherUser, true);

        this.updateThirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateGroupThirdWithOtherGroupCategory() {
        final Third third = this.testFactory.getThird(this.group);

        final Group otherGroup = this.testFactory.getGroup();
        final Category category = this.testFactory.getCategory(otherGroup, true);

        this.updateThirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateGroupThirdWithUserCategory() {
        final Third third = this.testFactory.getThird(this.group);

        final Category category = this.testFactory.getCategory(this.user, true);

        this.updateThirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateUserThirdWithNonExistentCategory() {
        final Third third = this.testFactory.getThird(this.user);

        this.updateThirdParameter.setDefaultCategoryId(this.testFactory.getRandomInteger());

        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateNonExistentThird() {
        Assertions.assertThrows(ThirdNotExistException.class,
            () -> this.thirdServiceImpl.updateThird(this.testFactory.getRandomInteger(), this.updateThirdParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateGroupThirdWithUserNotInGroup() {
        final Third third = this.testFactory.getThird(this.group);

        final User otherUser = this.testFactory.getUser();

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.thirdServiceImpl.updateThird(third.getId(), this.updateThirdParameter, otherUser.getUserName()));
    }
    
}
