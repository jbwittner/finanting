package fr.finanting.server.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import com.sun.xml.bind.v2.TODO;
import fr.finanting.server.model.embeddable.Address;
import fr.finanting.server.model.embeddable.BankDetails;
import fr.finanting.server.model.mother.MotherGroupUserElement;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Account model
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "BANKING_ACCOUNTS")
@Data
public class BankingAccount extends MotherGroupUserElement {
    
	@Column(name = "LABEL", nullable = false)
    private String label;

	@Column(name = "ABBREVIATION", nullable = false, length = 6)
    private String abbreviation;

    @Column(name = "INITIAL_BALANCE", nullable = false)
    private Integer initialBalance = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFAULT_CURRENCY_ID", nullable = false)
    private Currency defaultCurrency;

    @Embedded
    private BankDetails bankDetails;
    
    @Embedded
    private Address address;

    public Integer getBalance(){
        //TODO
        return 0;
    }

    @Override
    public String toString() {
        return "BankingAccount [id= " + this.id + ", abbreviation=" + abbreviation + ", label=" + label + ", user=" + user + "]";
    }

}
