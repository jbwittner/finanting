package fr.finanting.server.model;

import javax.persistence.*;

/**
 * Class of the Account
 */
@Entity
@Table(name = "ACCOUNT", uniqueConstraints = @UniqueConstraint(columnNames = "NAME", name = "UK_NAME"))
public class Account extends AbstractPersistant{

    @Column(name = "NAME", nullable = false, length = 20)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_TYPE", nullable = false, foreignKey = @ForeignKey(name = "FK_ACCOUNT_TYPE"))
    private AccountType accountType;

    /**
     * Default constructor
     */
    public Account() {
        super();
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Constructor of Account
     * @param name Name of the account
     * @param accountType Type of the account
     */
    public Account(final String name, final AccountType accountType) {
        super();
        this.name = name;
        this.accountType = accountType;
    }

    /**
     * Getter of name
     * @return Account name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of name
     * @param name Account name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Getter of type
     * @return Account type
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Setter of type
     * @param accountType Account type
     */
    public void setAccountType(final AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Account [id=" + this.id + ", accountType=" + accountType + ", name=" + name + "]";
    }

    public boolean equals(Account account){
        Boolean idIsEqual = this.id.equals(account.getId());
        Boolean nameIsEqual = this.name.equals(account.getName());
        Boolean accountTypeIsEquals = this.accountType.equals(account.getAccountType());

        Boolean isEquals = idIsEqual && nameIsEqual && accountTypeIsEquals;
        return isEquals;
    }

}