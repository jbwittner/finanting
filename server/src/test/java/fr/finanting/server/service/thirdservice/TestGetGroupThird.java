package fr.finanting.server.service.thirdservice;

import java.util.List;

import fr.finanting.server.generated.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

public class TestGetGroupThird extends AbstractMotherIntegrationTest {

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

    @Override
    protected void initDataBeforeEach() {
        this.thirdServiceImpl = new ThirdServiceImpl(this.thirdRepository,
                                                    this.userRepository,
                                                    this.groupRepository,
                                                    this.categoryRepository);
        
        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(user);
    }

    private void checkData(final Third expected, final ThirdDTO actual){
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getAbbreviation(), actual.getAbbreviation());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());

        final Address address = expected.getAddress();
        final AddressDTO addressDTO = actual.getAddressDTO();
        Assertions.assertEquals(address.getAddress(), addressDTO.getAddress());
        Assertions.assertEquals(address.getCity(), addressDTO.getCity());
        Assertions.assertEquals(address.getStreet(), addressDTO.getStreet());
        Assertions.assertEquals(address.getZipCode(), addressDTO.getZipCode());

        final Contact contact = expected.getContact();
        final ContactDTO contactDTO = actual.getContactDTO();
        Assertions.assertEquals(contact.getEmail(), contactDTO.getEmail());
        Assertions.assertEquals(contact.getHomePhone(), contactDTO.getHomePhone());
        Assertions.assertEquals(contact.getPortablePhone(), contactDTO.getPortablePhone());
        Assertions.assertEquals(contact.getWebsite(), contactDTO.getWebsite());

        final BankDetails bankDetails = expected.getBankDetails();
        final BankDetailsDTO bankDetailsDTO = actual.getBankDetailsDTO();
        Assertions.assertEquals(bankDetails.getAccountNumber(), bankDetailsDTO.getAccountNumber());
        Assertions.assertEquals(bankDetails.getBankName(), bankDetailsDTO.getBankName());
        Assertions.assertEquals(bankDetails.getIban(), bankDetailsDTO.getIban());

        final Category category = expected.getDefaultCategory();
        final CategoryDTO categoryDTO = actual.getCategoryDTO();
        Assertions.assertEquals(category.getId(), categoryDTO.getId());
        Assertions.assertEquals(category.getAbbreviation(), categoryDTO.getAbbreviation());
        Assertions.assertEquals(category.getCategoryType().name(), categoryDTO.getCategoryType().name());
        Assertions.assertEquals(category.getDescription(), categoryDTO.getDescription());
        Assertions.assertEquals(category.getLabel(), categoryDTO.getLabel());

    }

    @Test
    public void testGetUserThird() throws UserNotInGroupException, GroupNotExistException {
        final Category category = this.testFactory.getCategory(this.group, true);
        final Third third1 = this.testFactory.getThird(this.group, category);
        final Third third2 = this.testFactory.getThird(this.group, category);
        final Third third3 = this.testFactory.getThird(this.group, category);

        final List<ThirdDTO> thirdDTOs = this.thirdServiceImpl.getGroupThird(this.group.getId(), this.user.getUserName());

        Assertions.assertEquals(3, thirdDTOs.size());

        for(final ThirdDTO thirdDTO : thirdDTOs){

            if(thirdDTO.getId().equals(third1.getId())){
                this.checkData(third1, thirdDTO);
            } else if(thirdDTO.getId().equals(third2.getId())){
                this.checkData(third2, thirdDTO);
            } else if(thirdDTO.getId().equals(third3.getId())){
                this.checkData(third3, thirdDTO);
            } else {
                Assertions.fail();
            }
        }
    }

    @Test
    public void testGetUserThirdWithoutThird() throws UserNotInGroupException, GroupNotExistException {

        final List<ThirdDTO> thirdDTOs = this.thirdServiceImpl.getGroupThird(this.group.getId(), this.user.getUserName());

        Assertions.assertEquals(0, thirdDTOs.size());

    }

    @Test
    public void testGetUserThirdUserNotInGroup() throws GroupNotExistException, UserNotInGroupException{

        final User otherUser = this.testFactory.getUser();

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.thirdServiceImpl.getGroupThird(this.group.getId(), otherUser.getUserName()));

    }

    @Test
    public void testGetUserThirdGroupNotExist() throws GroupNotExistException, UserNotInGroupException{

        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.thirdServiceImpl.getGroupThird(this.testFactory.getRandomInteger(), this.user.getUserName()));

    }

}
