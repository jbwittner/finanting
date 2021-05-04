package fr.finanting.server.parameter;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CreateBankingTransactionParameter {

    @NotNull
    private Integer accountId;

    private Integer linkedAccountId;
    private Integer thirdId;
    private Integer categoryId;
    private Integer classificationId;
    
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date transactionDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date amountDate;

    @NotNull
    private Double amount;

    @NotNull
    private Double currencyAmount;

    @NotNull
    private Integer currencyId;

    private String description;
    
}
