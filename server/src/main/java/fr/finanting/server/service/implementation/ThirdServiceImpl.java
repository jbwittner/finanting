package fr.finanting.server.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.finanting.server.dto.ThirdDTO;
import fr.finanting.server.exception.BadAssociationThirdException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
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
import fr.finanting.server.parameter.CreateThirdParameter;
import fr.finanting.server.parameter.DeleteThirdParameter;
import fr.finanting.server.parameter.UpdateThirdParameter;
import fr.finanting.server.parameter.subpart.AddressParameter;
import fr.finanting.server.parameter.subpart.BankDetailsParameter;
import fr.finanting.server.parameter.subpart.ContactParameter;
import fr.finanting.server.repository.CategoryRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.ThirdRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.ThirdService;

@Service
public class ThirdServiceImpl implements ThirdService{

    private ThirdRepository thirdRepository;
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ThirdServiceImpl(final ThirdRepository thirdRepository,
                            final UserRepository userRepository,
                            final GroupRepository groupRepository,
                            final CategoryRepository categoryRepository){

            this.thirdRepository = thirdRepository;
            this.userRepository = userRepository;
            this.groupRepository = groupRepository;
            this.categoryRepository = categoryRepository;
        }
    
    @Override
    public void createThird(final CreateThirdParameter createThirdParameter, final String userName)
        throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException{
        
        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        final Third third = new Third();

        if(createThirdParameter.getGroupName() == null){
            third.setUser(user);
        } else {
            final Group group = this.groupRepository.findByGroupName(createThirdParameter.getGroupName())
                .orElseThrow(() -> new GroupNotExistException(createThirdParameter.getGroupName()));

            group.checkAreInGroup(user);

            third.setGroup(group);
        }

        if(createThirdParameter.getAddressParameter() != null){
            final AddressParameter addressParameter = createThirdParameter.getAddressParameter();

            final Address address = new Address();
            address.setAddress(addressParameter.getAddress());
            address.setCity(addressParameter.getCity());
            address.setStreet(addressParameter.getStreet());
            address.setZipCode(addressParameter.getZipCode());

            third.setAddress(address);
        }

        if(createThirdParameter.getBankDetailsParameter() != null){
            final BankDetailsParameter bankDetailsParameter = createThirdParameter.getBankDetailsParameter();
            
            final BankDetails bankDetails = new BankDetails();
            bankDetails.setBankName(bankDetailsParameter.getBankName());
            bankDetails.setIban(bankDetailsParameter.getIban());
            bankDetails.setAccountNumber(bankDetailsParameter.getAccountNumber());

            third.setBankDetails(bankDetails);
        }

        if(createThirdParameter.getContactParameter() != null){
            final ContactParameter contactParameter = createThirdParameter.getContactParameter();

            final Contact contact = new Contact();
            contact.setHomePhone(contactParameter.getHomePhone());
            contact.setPortablePhone(contactParameter.getPortablePhone());
            contact.setEmail(contactParameter.getEmail());
            contact.setWebsite(contactParameter.getWebsite());

            third.setContact(contact);
        }

        third.setAbbreviation(createThirdParameter.getAbbreviation().toUpperCase());
        third.setLabel(createThirdParameter.getLabel());
        third.setDescritpion(createThirdParameter.getDescritpion());

        if(createThirdParameter.getDefaultCategoryId() != null){
            final Category defaultCategory = this.categoryRepository.findById(createThirdParameter.getDefaultCategoryId())
                .orElseThrow(() -> new CategoryNotExistException(createThirdParameter.getDefaultCategoryId()));

            if(createThirdParameter.getGroupName() == null){
                if(defaultCategory.getUser() == null){
                    throw new BadAssociationThirdException();
                } else if(!defaultCategory.getUser().getUserName().equals(userName)){
                    throw new BadAssociationThirdException();
                }
            } else {
                if(defaultCategory.getGroup() == null){
                    throw new BadAssociationThirdException();
                } else if(!defaultCategory.getGroup().getGroupName().equals(createThirdParameter.getGroupName())){
                    throw new BadAssociationThirdException();
                }
            }

            third.setDefaultCategory(defaultCategory);
        }

        this.thirdRepository.save(third);

    }

    @Override
    public void updateThrid(final UpdateThirdParameter updateThirdParameter, final String userName)
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException{

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        final Third third = this.thirdRepository.findById(updateThirdParameter.getId())
            .orElseThrow(() -> new ThirdNotExistException(updateThirdParameter.getId()));

        if(third.getGroup() == null){
            if(!third.getUser().getUserName().equals(userName)){
                throw new ThirdNoUserException(updateThirdParameter.getId());
            }
        } else {
            third.getGroup().checkAreInGroup(user);
        }

        if(updateThirdParameter.getAddressParameter() == null){
            third.setAddress(null);
        } else {
            final AddressParameter addressParameter = updateThirdParameter.getAddressParameter();

            final Address address = new Address();
            address.setAddress(addressParameter.getAddress());
            address.setCity(addressParameter.getCity());
            address.setStreet(addressParameter.getStreet());
            address.setZipCode(addressParameter.getZipCode());

            third.setAddress(address);
        }

        if(updateThirdParameter.getBankDetailsParameter() == null){
            third.setBankDetails(null);
        } else {
            final BankDetailsParameter bankDetailsParameter = updateThirdParameter.getBankDetailsParameter();
            
            final BankDetails bankDetails = new BankDetails();
            bankDetails.setBankName(bankDetailsParameter.getBankName());
            bankDetails.setIban(bankDetailsParameter.getIban());
            bankDetails.setAccountNumber(bankDetailsParameter.getAccountNumber());

            third.setBankDetails(bankDetails);
        }

        if(updateThirdParameter.getContactParameter() == null){
            third.setContact(null);
        } else {
            final ContactParameter contactParameter = updateThirdParameter.getContactParameter();

            final Contact contact = new Contact();
            contact.setHomePhone(contactParameter.getHomePhone());
            contact.setPortablePhone(contactParameter.getPortablePhone());
            contact.setEmail(contactParameter.getEmail());
            contact.setWebsite(contactParameter.getWebsite());

            third.setContact(contact);
        }

        third.setAbbreviation(updateThirdParameter.getAbbreviation().toUpperCase());
        third.setLabel(updateThirdParameter.getLabel());
        third.setDescritpion(updateThirdParameter.getDescritpion());

        if(updateThirdParameter.getDefaultCategoryId() != null){
            final Category defaultCategory = this.categoryRepository.findById(updateThirdParameter.getDefaultCategoryId())
                .orElseThrow(() -> new CategoryNotExistException(updateThirdParameter.getDefaultCategoryId()));

            if(third.getGroup() == null){
                if(defaultCategory.getUser() == null){
                    throw new BadAssociationThirdException();
                } else if(!defaultCategory.getUser().getUserName().equals(userName)){
                    throw new BadAssociationThirdException();
                }
            } else {
                if(defaultCategory.getGroup() == null){
                    throw new BadAssociationThirdException();
                } else if(!defaultCategory.getGroup().getGroupName().equals(third.getGroup().getGroupName())){
                    throw new BadAssociationThirdException();
                }
            }

            third.setDefaultCategory(defaultCategory);
        }

        this.thirdRepository.save(third);

    }

    @Override
    public void deleteThird(final DeleteThirdParameter deleteThirdParameter, final String userName)
        throws ThirdNotExistException, UserNotInGroupException, ThirdNoUserException{

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        final Third third = this.thirdRepository.findById(deleteThirdParameter.getId())
            .orElseThrow(() -> new ThirdNotExistException(deleteThirdParameter.getId()));

        if(third.getGroup() == null){
            if(!third.getUser().getUserName().equals(userName)){
                throw new ThirdNoUserException(deleteThirdParameter.getId());
            }
        } else {
            third.getGroup().checkAreInGroup(user);
        }

        this.thirdRepository.delete(third);

    }

    @Override
    public List<ThirdDTO> getUserThird(final String userName) {

        final List<ThirdDTO> thirdDTOs = new ArrayList<>();

        final User user = this.userRepository.findByUserName(userName).orElseThrow();
        
        final List<Third> thirds = this.thirdRepository.findByUser(user);

        for(final Third third : thirds){
            final ThirdDTO thirdDTO = new ThirdDTO(third);
            thirdDTOs.add(thirdDTO);
        }

        return thirdDTOs;
    }

    @Override
    public List<ThirdDTO> getGroupThird(final String groupName, final String userName) throws UserNotInGroupException, GroupNotExistException {

        final List<ThirdDTO> thirdDTOs = new ArrayList<>();

        final User user = this.userRepository.findByUserName(userName).orElseThrow();
        final Group group = this.groupRepository.findByGroupName(groupName)
            .orElseThrow(() -> new GroupNotExistException(groupName));

        group.checkAreInGroup(user);
        
        final List<Third> thirds = this.thirdRepository.findByGroup(group);

        for(final Third third : thirds){
            final ThirdDTO thirdDTO = new ThirdDTO(third);
            thirdDTOs.add(thirdDTO);
        }

        return thirdDTOs;
    }
    
}
