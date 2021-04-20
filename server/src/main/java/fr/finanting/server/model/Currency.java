package fr.finanting.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CURRENCIES")
@Data
public class Currency extends MotherPersistant {

    @Column(name = "DEFAULT_CURRENCY")
    private Boolean defaultCurrency;

    @Column(name = "LABEL")
    private String label;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "CODE")
    private String code;

    @Column(name = "EURO_RATE")
    private Integer euroRate;

    @Column(name = "DECIMAL_PLACES")
    private Integer decimalPlaces;

    @Column(name = "UPDATE_AUTOMATICALLY")
    private Boolean updateAutomatically;

}
