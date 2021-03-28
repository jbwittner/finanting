package fr.finanting.server.dto;

import fr.finanting.server.model.embeddable.BankDetails;
import lombok.Data;

@Data
public class BankDetailsDTO {

    private String iban;
    private String accountNumber;
    private String bankName;

    public BankDetailsDTO(BankDetails bankDetails){
        this.iban = bankDetails.getIban();
        this.accountNumber = bankDetails.getAccountNumber();
        this.bankName = bankDetails.getBankName();
    }
    
}
