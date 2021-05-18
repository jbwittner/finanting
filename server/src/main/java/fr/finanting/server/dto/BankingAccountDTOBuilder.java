package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.BankingAccountDTO;
import fr.finanting.server.model.BankingAccount;

import java.util.ArrayList;
import java.util.List;

public class BankingAccountDTOBuilder extends Transformer<BankingAccount, BankingAccountDTO> {

    private static final CurrencyDTOBuilder CURRENCY_DTO_BUILDER = new CurrencyDTOBuilder();
    private static final BankDetailsDTOBuilder BANK_DETAILS_DTO_BUILDER = new BankDetailsDTOBuilder();
    private static final AddressDTOBuilder ADDRESS_DTO_BUILDER = new AddressDTOBuilder();

    @Override
    public BankingAccountDTO transform(BankingAccount input) {
        BankingAccountDTO bankDetailsDTO = new BankingAccountDTO();
        bankDetailsDTO.setId(input.getId());
        bankDetailsDTO.setLabel(input.getLabel());
        bankDetailsDTO.setAbbreviation(input.getAbbreviation());
        bankDetailsDTO.setCurrencyDTO(CURRENCY_DTO_BUILDER.transform(input.getDefaultCurrency()));

        if(input.getAddress() != null){
            bankDetailsDTO.setAddressDTO(ADDRESS_DTO_BUILDER.transform(input.getAddress()));
        }

        if(input.getBankDetails() != null){
            bankDetailsDTO.setBankDetailsDTO(BANK_DETAILS_DTO_BUILDER.transform(input.getBankDetails()));
        }

        return bankDetailsDTO;
    }

    @Override
    public List<BankingAccountDTO> transformAll(List<BankingAccount> input) {
        List<BankingAccountDTO> bankingAccountDTOList = new ArrayList<>();
        input.forEach(bankingAccount -> bankingAccountDTOList.add(this.transform(bankingAccount)));
        return bankingAccountDTOList;
    }
}
