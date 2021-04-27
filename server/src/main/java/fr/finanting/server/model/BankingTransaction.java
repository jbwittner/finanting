package fr.finanting.server.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "BANKING_TRANSACTIONS")
@Data
public class BankingTransaction extends MotherPersistant {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private BankingAccount account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LINKED_ACCOUNT_ID")
    private BankingAccount linkedAccount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MIRROR_TRANSACTION")
    private BankingTransaction mirrorTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "THIRD_ID")
    private Third third;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLASSIFICATION_ID")
    private Classification classification;

    @Column(name = "TRANSACTION_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    @Column(name = "AMOUNT_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date amountDate;

    @Column(name = "AMOUNT", nullable = false)
    private Double amount;

    @Column(name = "CURRENCY_AMOUNT", nullable = false)
    private Double currencyAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_ID")
    private Currency currency;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "ASSOCIATIONS_TRANSACTIONS_FILES",
                joinColumns = { 
                    @JoinColumn(name = "TRANSACTIONS_ID_JOIN") }, inverseJoinColumns = { 
                    @JoinColumn(name = "FILES_ID_INVERSE") })
    private List<Files> files = new ArrayList<>();
    
}
