package fr.finanting.server.parameter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CreateCurrencyParameter {

    @NotNull
    private Boolean defaultCurrency = false;

    @NotNull
    @NotEmpty
    private String label;

    @NotNull
    @NotEmpty
    @Size(max=3)
    private String symbol;

    @NotNull
    @NotEmpty
    @Size(min=3, max=3)
    private String isoCode;

    @NotNull
    private Integer rate;

    @NotNull
    private Integer decimalPlaces;
    
}
