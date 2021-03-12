package fr.finanting.server.repository;

import java.util.Optional;

import fr.finanting.server.model.Groupe;
import fr.finanting.server.model.User;

/**
 * Repository for user model
 */
public interface GroupeRepository extends AbstractRepository<Groupe, Integer>{

    boolean existsByGroupeName(String groupeName);
    Optional<Groupe> findByGroupeName(String groupeName);

}
