package fr.finanting.server.service.thirdservice;

import java.util.List;

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
import fr.finanting.server.parameter.UpdateThirdParameter;
import fr.finanting.server.parameter.subpart.AddressParameter;
import fr.finanting.server.parameter.subpart.BankDetailsParameter;
import fr.finanting.server.parameter.subpart.ContactParameter;
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

        this.updateThirdParameter = new UpdateThirdParameter();
        this.updateThirdParameter.setAbbreviation(this.factory.getRandomAlphanumericString(5).toLowerCase());
        this.updateThirdParameter.setDescritpion(this.faker.superhero().descriptor());
        this.updateThirdParameter.setLabel(this.faker.company().name());

        final ContactParameter contactParameter = new ContactParameter();
        contactParameter.setEmail(this.faker.internet().emailAddress());
        contactParameter.setHomePhone(this.faker.phoneNumber().phoneNumber());
        contactParameter.setPortablePhone(this.faker.phoneNumber().cellPhone());
        contactParameter.setWebsite(this.faker.internet().url());
        this.updateThirdParameter.setContactParameter(contactParameter);

        final BankDetailsParameter bankDetailsParameter = new BankDetailsParameter();
        bankDetailsParameter.setBankName(this.faker.dragonBall().character());
        bankDetailsParameter.setAccountNumber(this.factory.getRandomAlphanumericString());
        bankDetailsParameter.setIban(this.factory.getRandomAlphanumericString());
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
        Assertions.assertEquals(this.updateThirdParameter.getDescritpion(), third.getDescritpion());
        Assertions.assertEquals(this.updateThirdParameter.getLabel(), third.getLabel());

        final BankDetails bankDetails = third.getBankDetails();
        final Address address = third.getAddress();
        final Contact contact = third.getContact();

        
        if(this.updateThirdParameter.getBankDetailsParameter() != null){
            Assertions.assertNotNull(bankDetails);
            BankDetailsParameter bankDetailsParameter = this.updateThirdParameter.getBankDetailsParameter();
            Assertions.assertEquals(bankDetailsParameter.getIban(), bankDetails.getIban());
            Assertions.assertEquals(bankDetailsParameter.getAccountNumber(), bankDetails.getAccountNumber());
            Assertions.assertEquals(bankDetailsParameter.getBankName(), bankDetails.getBankName());
        } else {
            Assertions.assertNull(bankDetails);
        }

        if(this.updateThirdParameter.getAddressParameter() != null){
            Assertions.assertNotNull(address);
            AddressParameter addressParameter = this.updateThirdParameter.getAddressParameter();
            Assertions.assertEquals(addressParameter.getAddress(), address.getAddress());
            Assertions.assertEquals(addressParameter.getStreet(), address.getStreet());
            Assertions.assertEquals(addressParameter.getCity(), address.getCity());
            Assertions.assertEquals(addressParameter.getZipCode(), address.getZipCode());
        } else {
            Assertions.assertNull(address);
        }

        if(this.updateThirdParameter.getContactParameter() != null){
            Assertions.assertNotNull(contact);
            ContactParameter contactParameter = this.updateThirdParameter.getContactParameter();
            Assertions.assertEquals(contactParameter.getHomePhone(), contact.getHomePhone());
            Assertions.assertEquals(contactParameter.getPortablePhone(), contact.getPortablePhone());
            Assertions.assertEquals(contactParameter.getEmail(), contact.getEmail());
            Assertions.assertEquals(contactParameter.getWebsite(), contact.getWebsite());
        } else {
            Assertions.assertNull(contact);
        }

    }

    @Test
    public void testUpdateUserThird()
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException {

        Third third = this.thirdRepository.save(this.factory.getThird(this.user));
        this.updateThirdParameter.setId(third.getId());
        
        this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName());

        third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testUpdateGroupThird()
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException {
            
        Third third = this.thirdRepository.save(this.factory.getThird(this.group));
        this.updateThirdParameter.setId(third.getId());
        
        this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName());

        third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getGroup().getGroupName(), this.group.getGroupName());
        Assertions.assertNull(third.getUser());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    
    @Test
    public void testUpdateUserThirdWithoutAddressParameter()
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException {

        Third third = this.thirdRepository.save(this.factory.getThird(this.user));
        this.updateThirdParameter.setId(third.getId());

        this.updateThirdParameter.setAddressParameter(null);
        this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName());

        third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }
    
    @Test
    public void testUpdateUserThirdWithoutBankDetailsParameter()
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException {
            
        Third third = this.thirdRepository.save(this.factory.getThird(this.user));
        this.updateThirdParameter.setId(third.getId());
        
        this.updateThirdParameter.setBankDetailsParameter(null);
        this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName());

        third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testUpdateUserThirdWithoutContactParameter()
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException {
            
        Third third = this.thirdRepository.save(this.factory.getThird(this.user));
        this.updateThirdParameter.setId(third.getId());
        
        this.updateThirdParameter.setContactParameter(null);
        this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName());

        third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getUser().getUserName(), this.user.getUserName());
        Assertions.assertNull(third.getGroup());
        Assertions.assertNull(third.getDefaultCategory());
        
    }

    @Test
    public void testUpdateUserThirdAddingDefaultUserCategory()
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException {
            
        Third third = this.thirdRepository.save(this.factory.getThird(this.user));
        this.updateThirdParameter.setId(third.getId());
        
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        this.updateThirdParameter.setDefaultCategoryId(category.getId());
        this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName());

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
            
        Third third = this.thirdRepository.save(this.factory.getThird(this.group));
        this.updateThirdParameter.setId(third.getId());
        
        final Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        this.updateThirdParameter.setDefaultCategoryId(category.getId());
        this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName());

        third = this.thirdRepository.findAll().get(0);

        this.checkData(third);

        Assertions.assertEquals(third.getGroup().getGroupName(), this.group.getGroupName());
        Assertions.assertNull(third.getUser());
        Assertions.assertNotNull(third.getDefaultCategory());

        Assertions.assertEquals(this.updateThirdParameter.getDefaultCategoryId(), third.getDefaultCategory().getId());
        
    }

    @Test
    public void testUpdateOtherUserThird() {
        User otherUser = this.userRepository.save(this.factory.getUser());

        Third third = this.thirdRepository.save(this.factory.getThird(otherUser));
        this.updateThirdParameter.setId(third.getId());

        final Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        this.updateThirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(ThirdNoUserException.class,
            () -> this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testUpdateUserThirdWithGroupCategory() {
        Third third = this.thirdRepository.save(this.factory.getThird(this.user));
        this.updateThirdParameter.setId(third.getId());

        final Category category = this.categoryRepository.save(this.factory.getCategory(this.group, true));

        this.updateThirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testUpdateUserThirdWithOtherUserCategory() {
        Third third = this.thirdRepository.save(this.factory.getThird(this.user));
        this.updateThirdParameter.setId(third.getId());

        final User otherUser = this.userRepository.save(this.factory.getUser());
        final Category category = this.categoryRepository.save(this.factory.getCategory(otherUser, true));

        this.updateThirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testUpdateGroupThirdWithOtherGroupCategory() {
        Third third = this.thirdRepository.save(this.factory.getThird(this.group));
        this.updateThirdParameter.setId(third.getId());

        Group otherGroup = this.factory.getGroup();
        this.userRepository.save(otherGroup.getUserAdmin());
        this.groupRepository.save(otherGroup);

        final Category category = this.categoryRepository.save(this.factory.getCategory(otherGroup, true));

        this.updateThirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testUpdateGroupThirdWithUserCategory() {
        Third third = this.thirdRepository.save(this.factory.getThird(this.group));
        this.updateThirdParameter.setId(third.getId());

        final Category category = this.categoryRepository.save(this.factory.getCategory(this.user, true));

        this.updateThirdParameter.setDefaultCategoryId(category.getId());

        Assertions.assertThrows(BadAssociationThirdException.class,
            () -> this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testUpdateUserThirdWithNonExistentCategory() {
        Third third = this.thirdRepository.save(this.factory.getThird(this.user));
        this.updateThirdParameter.setId(third.getId());
        
        this.updateThirdParameter.setDefaultCategoryId(this.factory.getRandomInteger());

        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testUpdateNonExistentThird() {
        this.updateThirdParameter.setId(this.factory.getRandomInteger());

        Assertions.assertThrows(ThirdNotExistException.class,
            () -> this.thirdServiceImpl.updateThrid(this.updateThirdParameter, this.user.getUserName()));       
    }

    @Test
    public void testUpdateGroupThirdWithUserNotInGroup() {
        Third third = this.thirdRepository.save(this.factory.getThird(this.group));
        this.updateThirdParameter.setId(third.getId());

        final User otherUser = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.thirdServiceImpl.updateThrid(this.updateThirdParameter, otherUser.getUserName()));       
    }
    
}
