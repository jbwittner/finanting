package fr.finanting.server.repository;

import java.util.Optional;

import fr.finanting.server.model.User;

/**
 * Repository for user model
 */
public interface UserRepository extends AbstractRepository<User, Integer>{
    
    /**
     * Method to get user by user name
     */
    Optional<User> findByUserName(String userName);

    /**
     * Method to get user by email
     */
    Optional<User> findByEmail(String email);

    /**
     * Method to check if user exist by user name
     */
    boolean existsByUserName(String userName);

    /**
     * Method to check if user exist by email
     */
    boolean existsByEmail(String email);

}
