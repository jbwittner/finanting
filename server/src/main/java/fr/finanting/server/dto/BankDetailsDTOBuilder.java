package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.BankDetailsDTO;
import fr.finanting.server.model.embeddable.BankDetails;

import java.util.ArrayList;
import java.util.List;

public class BankDetailsDTOBuilder extends Transformer<BankDetails, BankDetailsDTO> {

    @Override
    public BankDetailsDTO transform(BankDetails input) {
        BankDetailsDTO bankDetailsDTO = new BankDetailsDTO();
        bankDetailsDTO.setBankName(input.getBankName());
        bankDetailsDTO.setAccountNumber(input.getAccountNumber());
        bankDetailsDTO.setIban(input.getIban());
        return bankDetailsDTO;
    }

    @Override
    public List<BankDetailsDTO> transformAll(List<BankDetails> input) {
        List<BankDetailsDTO> bankDetailsDTOList = new ArrayList<>();
        input.forEach(bankDetails -> bankDetailsDTOList.add(this.transform(bankDetails)));
        return bankDetailsDTOList;
    }
}
