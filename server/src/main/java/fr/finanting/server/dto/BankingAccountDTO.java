package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.AddressDTO;
import fr.finanting.server.codegen.model.BankDetailsDTO;
import fr.finanting.server.codegen.model.CurrencyDTO;
import fr.finanting.server.model.BankingAccount;
import lombok.Data;

@Data
public class BankingAccountDTO {

    private Integer id;
    private String label;
    private String abbreviation;
    private Integer balance;
    private BankDetailsDTO bankDetailsDTO;
    private CurrencyDTO defaultCurrencyDTO;
    private AddressDTO addressDTO;

    private static final CurrencyDTOBuilder CURRENCY_DTO_BUILDER = new CurrencyDTOBuilder();
    private static final BankDetailsDTOBuilder BANK_DETAILS_DTO_BUILDER = new BankDetailsDTOBuilder();
    private static final AddressDTOBuilder ADDRESS_DTO_BUILDER = new AddressDTOBuilder();

    public BankingAccountDTO(final BankingAccount bankingAccount){

        this.id = bankingAccount.getId();
        this.label = bankingAccount.getLabel();
        this.abbreviation = bankingAccount.getAbbreviation();
        this.defaultCurrencyDTO = CURRENCY_DTO_BUILDER.transform(bankingAccount.getDefaultCurrency());

        if(bankingAccount.getAddress() != null){
            this.addressDTO = ADDRESS_DTO_BUILDER.transform(bankingAccount.getAddress());
        }

        if(bankingAccount.getBankDetails() != null){
            this.bankDetailsDTO = BANK_DETAILS_DTO_BUILDER.transform(bankingAccount.getBankDetails());
        }

    }
    
}
