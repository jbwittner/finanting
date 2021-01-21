package fr.finanting.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.finanting.server.model.AccountType;

/**
 * Repository for account type
 */
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {

    /**
     * Method to find AccountType type by type
     * @param type AccountType to find
     * @return AccountType which the type
     */
    AccountType findByType(String type);
}