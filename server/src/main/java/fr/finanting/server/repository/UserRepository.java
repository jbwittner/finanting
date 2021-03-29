package fr.finanting.server.repository;

import java.util.Optional;

import fr.finanting.server.model.User;

public interface UserRepository extends AbstractRepository<User, Integer>{
    
    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

}
