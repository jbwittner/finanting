package fr.finanting.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Class of the Account type
 */
@Entity
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

    /**
     * Getter of type
     * @return String of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter of type
     * @param type String of type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "AccountType [id=" + this.id + ", type=" + type + "]";
    }

    public boolean equals(AccountType accountType){
        boolean idIsEqual = this.id.equals(accountType.getId());
        boolean typeIsEquals = this.type.equals(accountType.getType());

        boolean isEquals = idIsEqual && typeIsEquals;
        return isEquals;
    }

}