package fr.finanting.server.dto;

import fr.finanting.server.model.BankingAccount;
import lombok.Data;

@Data
public class BankingAccountDTO {

    private Integer id;
    private String label;
    private String bankName;
    private String abbreviation;
    private Integer balance;
    private BankDetailsDTO bankDetailsDTO;
    

    AddressDTO addressDTO;

    public BankingAccountDTO(final BankingAccount bankingAccount){

        this.id = bankingAccount.getId();
        this.label = bankingAccount.getLabel();
        this.abbreviation = bankingAccount.getAbbreviation();

        if(bankingAccount.getAddress() != null){
            this.addressDTO = new AddressDTO(bankingAccount.getAddress());
        }

        if(bankingAccount.getBankDetails() != null){
            this.bankDetailsDTO = new BankDetailsDTO(bankingAccount.getBankDetails());
        }

    }
    
}
