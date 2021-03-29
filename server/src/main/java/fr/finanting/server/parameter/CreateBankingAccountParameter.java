package fr.finanting.server.parameter;

import fr.finanting.server.parameter.subpart.AccountParameter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CreateBankingAccountParameter extends AccountParameter {

    private String groupName;

}
