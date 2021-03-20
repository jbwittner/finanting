package fr.finanting.server.parameter.subpart;

import lombok.Data;

@Data
public class BankDetailsParameter {
    
    private String iban;
    private String accountNumber;
}
