package fr.finanting.server.model.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class BankDetails {

    @Column(name = "IBAN")
    private String iban;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
   
}