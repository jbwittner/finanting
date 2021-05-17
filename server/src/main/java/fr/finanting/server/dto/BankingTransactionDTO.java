package fr.finanting.server.dto;

import java.util.Date;

import fr.finanting.server.codegen.model.CategoryDTO;
import fr.finanting.server.codegen.model.ClassificationDTO;
import fr.finanting.server.codegen.model.CurrencyDTO;
import fr.finanting.server.codegen.model.ThirdDTO;
import fr.finanting.server.model.BankingTransaction;
import lombok.Data;

@Data
public class BankingTransactionDTO {

    Integer id;
    BankingAccountDTO bankingAccountDTO;
    BankingAccountDTO linkedBankingAccountDTO;
    ThirdDTO thirdDTO;
    CategoryDTO categoryDTO;
    ClassificationDTO classificationDTO;
    Date transactionDate;
    Date amountDate;
    Double amount;
    Double currencyAmount;
    CurrencyDTO currencyDTO;
    String description;
    Date createTimestamp;
    Date updateTimestamp;

    private static final CategoryDTOBuilder CATEGORY_DTO_BUILDER = new CategoryDTOBuilder();
    private static final ClassificationDTOBuilder CLASSIFICATION_DTO_BUILDER = new ClassificationDTOBuilder();
    private static final CurrencyDTOBuilder CURRENCY_DTO_BUILDER = new CurrencyDTOBuilder();
    private static final ThirdDTOBuilder THIRD_DTO_BUILDER = new ThirdDTOBuilder();

    public BankingTransactionDTO(final BankingTransaction bankingTransaction){

        this.id = bankingTransaction.getId();

        this.bankingAccountDTO = new BankingAccountDTO(bankingTransaction.getAccount());

        this.transactionDate = bankingTransaction.getTransactionDate();
        this.amountDate = bankingTransaction.getAmountDate();

        this.amount = bankingTransaction.getAmount();
        this.currencyAmount = bankingTransaction.getCurrencyAmount();

        this.currencyDTO = CURRENCY_DTO_BUILDER.transform(bankingTransaction.getCurrency());

        this.description = bankingTransaction.getDescription();

        if(bankingTransaction.getLinkedAccount() != null){
            this.linkedBankingAccountDTO = new BankingAccountDTO(bankingTransaction.getLinkedAccount());
        }

        if(bankingTransaction.getThird() != null){
            this.thirdDTO = THIRD_DTO_BUILDER.transform(bankingTransaction.getThird());
        }

        if(bankingTransaction.getCategory() != null){
            this.categoryDTO = CATEGORY_DTO_BUILDER.transform(bankingTransaction.getCategory());
        }

        if(bankingTransaction.getClassification()!= null){
            this.classificationDTO = CLASSIFICATION_DTO_BUILDER.transform(bankingTransaction.getClassification());
        }

        this.createTimestamp = bankingTransaction.getCreateTimestamp();
        this.updateTimestamp = bankingTransaction.getUpdateTimestamp();
    }
    
}
