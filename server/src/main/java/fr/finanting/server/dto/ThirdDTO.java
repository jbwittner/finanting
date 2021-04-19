package fr.finanting.server.dto;

import fr.finanting.server.model.Third;
import lombok.Data;

@Data
public class ThirdDTO {
    
    private Integer id;
    private String label;
    private String abbreviation;
    private String descritpion;
    private CategoryDTO categoryDTO;
    private BankDetailsDTO bankDetailsDTO;
    private AddressDTO addressDTO;
    private ContactDTO contactDTO;

    public ThirdDTO(Third third){
        this.id = third.getId();
        this.label = third.getLabel();
        this.abbreviation = third.getAbbreviation();
        this.descritpion = third.getDescritpion();
        
        if(third.getDefaultCategory() != null){
            CategoryDTO categoryDTO = new CategoryDTO(third.getDefaultCategory());
            this.categoryDTO = categoryDTO;
        }

        if(third.getBankDetails() != null){
            BankDetailsDTO bankDetailsDTO = new BankDetailsDTO(third.getBankDetails());
            this.bankDetailsDTO = bankDetailsDTO;
        }

        if(third.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO(third.getAddress());
            this.addressDTO = addressDTO;
        }

        if(third.getContact() != null){
            ContactDTO contactDTO = new ContactDTO(third.getContact());
            this.contactDTO = contactDTO;
        }
    }

}
