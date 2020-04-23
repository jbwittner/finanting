package fr.finanting.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class of the Account type
 */
@Entity
@Table(name = "ACCOUNT_TYPE")
public class AccountType {

    @Id
    @Column(name = "ID_ACCOUNT_TYPE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TYPE", nullable = false, unique = true)
    private String type;

    /**
     * Default constructor
     */
    protected AccountType() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Constructor of Account type
     * @param type String of type
     */
    public AccountType(final String type) {
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
     * Override of toString method
     * @return String representation of the model
     */
    @Override
    public String toString() {
        return "AccountType{" +
                "id=" + this.id +
                ", type='" + this.type + '\'' +
                '}';
    }

}