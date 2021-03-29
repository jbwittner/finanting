package fr.finanting.server.parameter.subpart;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AccountParameter {

    @NotNull
    @NotEmpty
    private String label;

    @NotNull
    @NotEmpty
    @Size(min=3, max=6)
    private String abbreviation;

    @NotNull
    private Integer initialBalance;

    private AddressParameter addressParameter;
    private BankDetailsParameter bankDetailsParameter;
    
}
