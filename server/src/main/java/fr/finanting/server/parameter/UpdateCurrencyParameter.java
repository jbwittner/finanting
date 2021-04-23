package fr.finanting.server.parameter;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateCurrencyParameter extends CreateCurrencyParameter {

    @NotNull
    private Integer id;
    
}
