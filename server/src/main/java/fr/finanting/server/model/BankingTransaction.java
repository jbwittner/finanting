package fr.finanting.server.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "BANKING_TRANSACTIONS")
@Data
public class BankingTransaction extends MotherPersistant {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANKING_ACCOUNT_ID")
    private BankingAccount bankingAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "THIRD_ID")
    private Third third;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLASSIFICATION_ID")
    private Classification classification;

    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;

    @Column(name = "VALUE_DATE")
    private Date valueDate;

    @Column(name = "VALUE")
    private Integer value;
    
}
