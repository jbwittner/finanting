package fr.finanting.server.parameter.subpart;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fr.finanting.server.codegen.model.AddressParameter;
import fr.finanting.server.codegen.model.BankDetailsParameter;
import lombok.Data;

@Data
public class BankingAccountParameter {

    @NotNull
    @NotEmpty
    private String label;

    @NotNull
    @NotEmpty
    @Size(min=3, max=6)
    private String abbreviation;

    @NotNull
    @Size(min=3, max=3)
    private String defaultCurrencyISOCode; 

    @NotNull
    private Integer initialBalance;

    private AddressParameter addressParameter;
    private BankDetailsParameter bankDetailsParameter;
    
}
