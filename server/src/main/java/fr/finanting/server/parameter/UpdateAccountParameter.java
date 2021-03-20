package fr.finanting.server.parameter;

import javax.validation.constraints.NotNull;

import fr.finanting.server.parameter.subpart.AccountParameter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateAccountParameter extends AccountParameter {

    @NotNull
    private Integer accountId;
    
}
