package fr.finanting.server.repositorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.finanting.server.model.Account;
import fr.finanting.server.model.AccountType;

/**
 * Repository for Account
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Method to find Account by name
     * @param name Name to find
     * @return Account which the name match
     */
    Account findByName(String name);

    /**
     * Method to find Accounts by name
     * @param name Name to find
     * @return Account which the name match
     */
    List<Account> findByAccountType(AccountType accountType);
}