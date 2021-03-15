package fr.finanting.server.repository;

import java.util.Optional;

import fr.finanting.server.model.Groupe;

/**
 * Repository for groupe model
 */
public interface GroupeRepository extends AbstractRepository<Groupe, Integer>{

    /**
     * Method to check if a group exist by group name
     */
    boolean existsByGroupeName(String groupeName);

    /**
     * Method to get group by group name
     */
    Optional<Groupe> findByGroupeName(String groupeName);

}
