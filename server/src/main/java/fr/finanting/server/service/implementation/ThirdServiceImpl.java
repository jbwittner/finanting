package fr.finanting.server.service.implementation;

import java.util.ArrayList;
import java.util.List;

import fr.finanting.server.codegen.model.*;
import fr.finanting.server.dto.ThirdDTOBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import fr.finanting.server.repository.CategoryRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.ThirdRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.ThirdService;

@Service
public class ThirdServiceImpl implements ThirdService{

    private final ThirdRepository thirdRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final CategoryRepository categoryRepository;

    private static final ThirdDTOBuilder THIRD_DTO_BUILDER = new ThirdDTOBuilder();

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
    public void createThird(final ThirdParameter thirdParameter, final String userName) {
        
        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        final Third third = new Third();

        if(thirdParameter.getGroupName() == null){
            third.setUser(user);
        } else {
            final Group group = this.groupRepository.findByGroupName(thirdParameter.getGroupName())
                .orElseThrow(() -> new GroupNotExistException(thirdParameter.getGroupName()));

            group.checkAreInGroup(user);

            third.setGroup(group);
        }

        if(thirdParameter.getAddressParameter() != null){
            final AddressParameter addressParameter = thirdParameter.getAddressParameter();

            final Address address = new Address();
            address.setAddress(addressParameter.getAddress());
            address.setCity(addressParameter.getCity());
            address.setStreet(addressParameter.getStreet());
            address.setZipCode(addressParameter.getZipCode());

            third.setAddress(address);
        }

        if(thirdParameter.getBankDetailsParameter() != null){
            final BankDetailsParameter bankDetailsParameter = thirdParameter.getBankDetailsParameter();
            
            final BankDetails bankDetails = new BankDetails();
            bankDetails.setBankName(bankDetailsParameter.getBankName());
            bankDetails.setIban(bankDetailsParameter.getIban());
            bankDetails.setAccountNumber(bankDetailsParameter.getAccountNumber());

            third.setBankDetails(bankDetails);
        }

        if(thirdParameter.getContactParameter() != null){
            final ContactParameter contactParameter = thirdParameter.getContactParameter();

            final Contact contact = new Contact();
            contact.setHomePhone(contactParameter.getHomePhone());
            contact.setPortablePhone(contactParameter.getPortablePhone());
            contact.setEmail(contactParameter.getEmail());
            contact.setWebsite(contactParameter.getWebsite());

            third.setContact(contact);
        }

        third.setAbbreviation(thirdParameter.getAbbreviation().toUpperCase());
        third.setLabel(thirdParameter.getLabel());
        third.setDescritpion(thirdParameter.getDescription());

        if(thirdParameter.getDefaultCategoryId() != null){
            final Category defaultCategory = this.categoryRepository.findById(thirdParameter.getDefaultCategoryId())
                .orElseThrow(() -> new CategoryNotExistException(thirdParameter.getDefaultCategoryId()));

            if(thirdParameter.getGroupName() == null){
                if(defaultCategory.getUser() == null){
                    throw new BadAssociationThirdException();
                } else if(!defaultCategory.getUser().getUserName().equals(userName)){
                    throw new BadAssociationThirdException();
                }
            } else {
                if(defaultCategory.getGroup() == null){
                    throw new BadAssociationThirdException();
                } else if(!defaultCategory.getGroup().getGroupName().equals(thirdParameter.getGroupName())){
                    throw new BadAssociationThirdException();
                }
            }

            third.setDefaultCategory(defaultCategory);
        }

        this.thirdRepository.save(third);

    }

    @Override
    public void updateThird(final Integer thirdId,
                            final UpdateThirdParameter updateThirdParameter,
                            final String userName) {

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        final Third third = this.thirdRepository.findById(thirdId)
            .orElseThrow(() -> new ThirdNotExistException(thirdId));

        if(third.getGroup() == null){
            if(!third.getUser().getUserName().equals(userName)){
                throw new ThirdNoUserException(thirdId);
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
        third.setDescritpion(updateThirdParameter.getDescription());

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
    public void deleteThird(final Integer thirdId, final String userName) {

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        final Third third = this.thirdRepository.findById(thirdId)
            .orElseThrow(() -> new ThirdNotExistException(thirdId));

        if(third.getGroup() == null){
            if(!third.getUser().getUserName().equals(userName)){
                throw new ThirdNoUserException(thirdId);
            }
        } else {
            third.getGroup().checkAreInGroup(user);
        }

        this.thirdRepository.delete(third);

    }

    @Override
    public List<ThirdDTO> getUserThird(final String userName) {
        final User user = this.userRepository.findByUserName(userName).orElseThrow();
        final List<Third> thirds = this.thirdRepository.findByUser(user);
        return THIRD_DTO_BUILDER.transformAll(thirds);
    }

    @Override
    public List<ThirdDTO> getGroupThird(final Integer groupId, final String userName) {

        final User user = this.userRepository.findByUserName(userName).orElseThrow();
        final Group group = this.groupRepository.findById(groupId)
            .orElseThrow(() -> new GroupNotExistException(groupId));

        group.checkAreInGroup(user);
        
        final List<Third> thirds = this.thirdRepository.findByGroup(group);
        return THIRD_DTO_BUILDER.transformAll(thirds);
    }
    
}
