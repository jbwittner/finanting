package fr.finanting.server.service.thirdservice;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.dto.AddressDTO;
import fr.finanting.server.dto.BankDetailsDTO;
import fr.finanting.server.dto.CategoryDTO;
import fr.finanting.server.dto.ContactDTO;
import fr.finanting.server.dto.ThirdDTO;
import fr.finanting.server.model.Category;
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

public class TestGetUserThird extends AbstractMotherIntegrationTest {

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

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.thirdServiceImpl = new ThirdServiceImpl(this.thirdRepository,
                                                    this.userRepository,
                                                    this.groupRepository,
                                                    this.categoryRepository);
        
        this.user = this.userRepository.save(this.factory.getUser());
    }

    private void checkData(final Third expected, final ThirdDTO actual){
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getAbbreviation(), actual.getAbbreviation());
        Assertions.assertEquals(expected.getDescritpion(), actual.getDescritpion());

        Address address = expected.getAddress();
        AddressDTO addressDTO = actual.getAddressDTO();
        Assertions.assertEquals(address.getAddress(), addressDTO.getAddress());
        Assertions.assertEquals(address.getCity(), addressDTO.getCity());
        Assertions.assertEquals(address.getStreet(), addressDTO.getStreet());
        Assertions.assertEquals(address.getZipCode(), addressDTO.getZipCode());

        Contact contact = expected.getContact();
        ContactDTO contactDTO = actual.getContactDTO();
        Assertions.assertEquals(contact.getEmail(), contactDTO.getEmail());
        Assertions.assertEquals(contact.getHomePhone(), contactDTO.getHomePhone());
        Assertions.assertEquals(contact.getPortablePhone(), contactDTO.getPortablePhone());
        Assertions.assertEquals(contact.getWebsite(), contactDTO.getWebsite());

        BankDetails bankDetails = expected.getBankDetails();
        BankDetailsDTO bankDetailsDTO = actual.getBankDetailsDTO();
        Assertions.assertEquals(bankDetails.getAccountNumber(), bankDetailsDTO.getAccountNumber());
        Assertions.assertEquals(bankDetails.getBankName(), bankDetailsDTO.getBankName());
        Assertions.assertEquals(bankDetails.getIban(), bankDetailsDTO.getIban());

        Category category = expected.getDefaultCategory();
        CategoryDTO categoryDTO = actual.getCategoryDTO();
        Assertions.assertEquals(category.getId(), categoryDTO.getId());
        Assertions.assertEquals(category.getAbbreviation(), categoryDTO.getAbbreviation());
        Assertions.assertEquals(category.getCategoryType(), categoryDTO.getCategoryType());
        Assertions.assertEquals(category.getDescritpion(), categoryDTO.getDescritpion());
        Assertions.assertEquals(category.getLabel(), categoryDTO.getLabel());

    }

    @Test
    public void testGetUserThird() {
        Category category = this.categoryRepository.save(this.factory.getCategory(this.user, true));
        Third third1 = this.thirdRepository.save(this.factory.getThird(this.user, category));
        Third third2 = this.thirdRepository.save(this.factory.getThird(this.user, category));
        Third third3 = this.thirdRepository.save(this.factory.getThird(this.user, category));

        List<ThirdDTO> thirdDTOs = this.thirdServiceImpl.getUserThird(this.user.getUserName());

        Assertions.assertEquals(3, thirdDTOs.size());

        for(ThirdDTO thirdDTO : thirdDTOs){

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
    public void testGetUserThirdWithoutThird() {

        List<ThirdDTO> thirdDTOs = this.thirdServiceImpl.getUserThird(this.user.getUserName());

        Assertions.assertEquals(0, thirdDTOs.size());

    }

}
