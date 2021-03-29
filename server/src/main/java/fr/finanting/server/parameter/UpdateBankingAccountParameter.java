package fr.finanting.server.parameter;

import javax.validation.constraints.NotNull;

import fr.finanting.server.parameter.subpart.BankingAccountParameter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateBankingAccountParameter extends BankingAccountParameter {

    @NotNull
    private Integer accountId;
    
}
