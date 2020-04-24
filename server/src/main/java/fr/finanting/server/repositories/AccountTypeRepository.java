package fr.finanting.server.repositories;

import org.springframework.data.repository.CrudRepository;

import fr.finanting.server.model.AccountType;

/**
 * Repository for Account type
 */
public interface AccountTypeRepository extends CrudRepository<AccountType, Long> {

    /**
     * Method to find device type by serial
     * @param type Type to find
     * @return Type which the serial match
     */
    AccountType findByType(String type);

    /**
     * Method to find type by id
     * @param id Id to find
     * @return Type which the id match
     */
    AccountType findById(long id);
}