package fr.finanting.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import fr.finanting.server.model.mother.MotherPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CURRENCIES")
@Data
public class Currency extends MotherPersistent {

    @Column(name = "DEFAULT_CURRENCY", nullable = false)
    private Boolean defaultCurrency = false;

    @Column(name = "LABEL", nullable = false)
    private String label;

    @Column(name = "SYMBOL", nullable = false, length = 3)
    private String symbol;

    @Column(name = "ISO_CODE", unique = true, nullable = false, length = 3)
    private String isoCode;

    @Column(name = "RATE", nullable = false)
    private Integer rate;

    @Column(name = "DECIMAL_PLACES", nullable = false)
    private Integer decimalPlaces;

}
