package fr.finanting.server.parameter;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class UpdateBankingTransactionParameter extends CreateBankingTransactionParameter {

    @NotNull
    private Integer id;
    
}
