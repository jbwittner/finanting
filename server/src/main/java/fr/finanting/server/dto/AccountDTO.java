package fr.finanting.server.dto;

import fr.finanting.server.model.Account;
import lombok.Data;

@Data
public class AccountDTO {

    private Integer id;
    private String label;
    private GroupDTO groupDTO;
    private UserDTO userDTO;
    private String bankName;
    private String abbreviation;
    private Integer balance;
    private BankDetailsDTO bankDetailsDTO;
    

    AddressDTO addressDTO;

    public AccountDTO(Account account){

        this.id = account.getId();
        this.label = account.getLabel();
        this.bankName = account.getBankName();
        this.abbreviation = account.getAbbreviation();

        if(account.getAddress() != null){
            this.addressDTO = new AddressDTO(account.getAddress());
        }

        if(account.getGroup() != null){
            this.groupDTO = new GroupDTO(account.getGroup());
        }

        if(account.getUser() != null){
            this.userDTO = new UserDTO(account.getUser());
        }

        if(account.getBankDetails() != null){
            this.bankDetailsDTO = new BankDetailsDTO(account.getBankDetails());
        }

    }
    
}
