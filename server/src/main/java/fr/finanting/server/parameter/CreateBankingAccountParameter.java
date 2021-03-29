package fr.finanting.server.parameter;

import fr.finanting.server.parameter.subpart.BankingAccountParameter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CreateBankingAccountParameter extends BankingAccountParameter {

    private String groupName;

}
