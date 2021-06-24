package fr.finanting.server.dto;

import fr.finanting.server.generated.model.BankingTransactionDTO;
import fr.finanting.server.model.BankingTransaction;

import java.util.ArrayList;
import java.util.List;

public class BankingTransactionDTOBuilder implements Transformer<BankingTransaction, BankingTransactionDTO> {

    private static final CategoryDTOBuilder CATEGORY_DTO_BUILDER = new CategoryDTOBuilder();
    private static final ClassificationDTOBuilder CLASSIFICATION_DTO_BUILDER = new ClassificationDTOBuilder();
    private static final CurrencyDTOBuilder CURRENCY_DTO_BUILDER = new CurrencyDTOBuilder();
    private static final ThirdDTOBuilder THIRD_DTO_BUILDER = new ThirdDTOBuilder();
    private static final BankingAccountDTOBuilder BANKING_ACCOUNT_DTO_BUILDER = new BankingAccountDTOBuilder();

    @Override
    public BankingTransactionDTO transform(final BankingTransaction input) {
        final BankingTransactionDTO bankingTransactionDTO = new BankingTransactionDTO();
        bankingTransactionDTO.setId(input.getId());
        bankingTransactionDTO.setBankingAccountDTO(BANKING_ACCOUNT_DTO_BUILDER.transform(input.getAccount()));

        if(input.getLinkedAccount() != null){
            bankingTransactionDTO.setLinkedBankingAccountDTO(BANKING_ACCOUNT_DTO_BUILDER.transform(input.getLinkedAccount()));
        }

        if(input.getThird() != null){
            bankingTransactionDTO.setThirdDTO(THIRD_DTO_BUILDER.transform(input.getThird()));
        }

        if(input.getCategory() != null){
            bankingTransactionDTO.setCategoryDTO(CATEGORY_DTO_BUILDER.transform(input.getCategory()));
        }

        if(input.getClassification() != null){
            bankingTransactionDTO.setClassificationDTO(CLASSIFICATION_DTO_BUILDER.transform(input.getClassification()));
        }

        bankingTransactionDTO.setTransactionDate(input.getTransactionDate());
        bankingTransactionDTO.setAmountDate(input.getAmountDate());
        bankingTransactionDTO.setAmount(input.getAmount());
        bankingTransactionDTO.setCurrencyAmount(input.getCurrencyAmount());

        bankingTransactionDTO.setCurrencyDTO(CURRENCY_DTO_BUILDER.transform(input.getCurrency()));

        bankingTransactionDTO.setDescription(input.getDescription());
        bankingTransactionDTO.setCreateTimestamp(input.getCreateTimestamp());
        bankingTransactionDTO.setUpdateTimestamp(input.getUpdateTimestamp());

        return bankingTransactionDTO;
    }

    @Override
    public List<BankingTransactionDTO> transformAll(final List<BankingTransaction> input) {
        final List<BankingTransactionDTO> bankingTransactionDTOList = new ArrayList<>();
        input.forEach(bankingTransaction -> bankingTransactionDTOList.add(this.transform(bankingTransaction)));
        return bankingTransactionDTOList;
    }
}
