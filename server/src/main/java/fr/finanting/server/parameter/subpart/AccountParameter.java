package fr.finanting.server.parameter.subpart;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AccountParameter {

    private String groupeName;

    @NotNull
    @NotEmpty
    private String label;

    @NotNull
    @NotEmpty
    private String abbreviation;

    @NotNull
    private Integer initialBalance;

    private AddressParameter addressParameter;
    private BankDetailsParameter bankDetailsParameter;
    
}
