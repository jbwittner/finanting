package fr.finanting.server.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.finanting.server.model.AccountType;

/**
 * Repository for account type
 */
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    /**
     * Method to find AccountType type by type
     * @param type AccountType to find
     * @return AccountType which the type
     */
    AccountType findByType(String type);
}