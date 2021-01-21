package fr.finanting.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class of the Account type
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "ACCOUNT_TYPE")
public class AccountType extends AbstractPersistant{

    @Column(name = "TYPE", nullable = false, unique = true, length = 20)
    private String type;

    /**
     * Default constructor
     */
    public AccountType() {
        super();
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Constructor of Account type
     * @param type String of type
     */
    public AccountType(final String type) {
        super();
        this.type = type;
    }

}