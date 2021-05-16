package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.CategoryDTO;
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

    private static final CategoryDTOBuilder CATEGORY_DTO_BUILDER = new CategoryDTOBuilder();

    public ThirdDTO(final Third third){
        this.id = third.getId();
        this.label = third.getLabel();
        this.abbreviation = third.getAbbreviation();
        this.descritpion = third.getDescritpion();
        
        if(third.getDefaultCategory() != null){
            this.categoryDTO = CATEGORY_DTO_BUILDER.transform(third.getDefaultCategory());
        }

        if(third.getBankDetails() != null){
            this.bankDetailsDTO = new BankDetailsDTO(third.getBankDetails());
        }

        if(third.getAddress() != null){
            this.addressDTO = new AddressDTO(third.getAddress());
        }

        if(third.getContact() != null){
            this.contactDTO = new ContactDTO(third.getContact());
        }
    }

}
