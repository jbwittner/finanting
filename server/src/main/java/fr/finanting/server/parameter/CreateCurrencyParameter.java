package fr.finanting.server.parameter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @Max(3)
    private String symbol;

    @NotNull
    @NotEmpty
    @Max(3)
    private String isoCode;

    @NotNull
    private Integer rate;

    @NotNull
    private Integer decimalPlaces;
    
}
