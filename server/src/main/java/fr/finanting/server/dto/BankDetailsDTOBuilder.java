package fr.finanting.server.dto;

import fr.finanting.codegen.model.BankDetailsDTO;
import fr.finanting.server.model.embeddable.BankDetails;

import java.util.ArrayList;
import java.util.List;

public class BankDetailsDTOBuilder implements Transformer<BankDetails, BankDetailsDTO> {

    @Override
    public BankDetailsDTO transform(final BankDetails input) {
        final BankDetailsDTO bankDetailsDTO = new BankDetailsDTO();
        bankDetailsDTO.setBankName(input.getBankName());
        bankDetailsDTO.setAccountNumber(input.getAccountNumber());
        bankDetailsDTO.setIban(input.getIban());
        return bankDetailsDTO;
    }

    @Override
    public List<BankDetailsDTO> transformAll(final List<BankDetails> input) {
        final List<BankDetailsDTO> bankDetailsDTOList = new ArrayList<>();
        input.forEach(bankDetails -> bankDetailsDTOList.add(this.transform(bankDetails)));
        return bankDetailsDTOList;
    }
}
