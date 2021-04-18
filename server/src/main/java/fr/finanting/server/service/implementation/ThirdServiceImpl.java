package fr.finanting.server.service.implementation;

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
            AddressParameter addressParameter = createThirdParameter.getAddressParameter();

            Address address = new Address();
            address.setAddress(addressParameter.getAddress());
            address.setCity(addressParameter.getCity());
            address.setStreet(addressParameter.getStreet());
            address.setZipCode(addressParameter.getZipCode());

            third.setAddress(address);
        }

        if(createThirdParameter.getBankDetailsParameter() != null){
            BankDetailsParameter bankDetailsParameter = createThirdParameter.getBankDetailsParameter();
            
            BankDetails bankDetails = new BankDetails();
            bankDetails.setBankName(bankDetailsParameter.getBankName());
            bankDetails.setIban(bankDetailsParameter.getIban());
            bankDetails.setAccountNumber(bankDetailsParameter.getAccountNumber());

            third.setBankDetails(bankDetails);
        }

        if(createThirdParameter.getContactParameter() != null){
            ContactParameter contactParameter = createThirdParameter.getContactParameter();

            Contact contact = new Contact();
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

        if(updateThirdParameter.getAddressParameter() != null){
            AddressParameter addressParameter = updateThirdParameter.getAddressParameter();

            Address address = new Address();
            address.setAddress(addressParameter.getAddress());
            address.setCity(addressParameter.getCity());
            address.setStreet(addressParameter.getStreet());
            address.setZipCode(addressParameter.getZipCode());

            third.setAddress(address);
        } else {
            third.setAddress(null);
        }

        if(updateThirdParameter.getBankDetailsParameter() != null){
            BankDetailsParameter bankDetailsParameter = updateThirdParameter.getBankDetailsParameter();
            
            BankDetails bankDetails = new BankDetails();
            bankDetails.setBankName(bankDetailsParameter.getBankName());
            bankDetails.setIban(bankDetailsParameter.getIban());
            bankDetails.setAccountNumber(bankDetailsParameter.getAccountNumber());

            third.setBankDetails(bankDetails);
        } else {
            third.setBankDetails(null);
        }

        if(updateThirdParameter.getContactParameter() != null){
            ContactParameter contactParameter = updateThirdParameter.getContactParameter();

            Contact contact = new Contact();
            contact.setHomePhone(contactParameter.getHomePhone());
            contact.setPortablePhone(contactParameter.getPortablePhone());
            contact.setEmail(contactParameter.getEmail());
            contact.setWebsite(contactParameter.getWebsite());

            third.setContact(contact);
        } else {
            third.setContact(null);
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
        throws ThirdNotExistException, UserNotInGroupException{

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        final Third third = this.thirdRepository.findById(deleteThirdParameter.getId())
            .orElseThrow(() -> new ThirdNotExistException(deleteThirdParameter.getId()));

        if(third.getGroup() != null){
            third.getGroup().checkAreInGroup(user);
        }

    }
    
}
