package fr.finanting.server.dto;

import fr.finanting.server.model.embeddable.BankDetails;
import lombok.Data;

@Data
public class BankDetailsDTO {

    private String iban;
    private String accountNumber;

    public BankDetailsDTO(BankDetails bankDetails){
        this.iban = bankDetails.getIban();
        this.accountNumber = bankDetails.getAccountNumber();
    }
    
}
