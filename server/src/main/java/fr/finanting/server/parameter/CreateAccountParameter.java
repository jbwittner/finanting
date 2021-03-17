package fr.finanting.server.parameter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateAccountParameter {

    private String userName;
    private String groupeName;

    @NotNull
    @NotEmpty
    private String bankName;

    @NotNull
    @NotEmpty
    private String abbreviation;

    private String iban;
    private String accountNumber;
    private String address;
    private String street;
    private String city;
    private Integer zipCode;
    
}
