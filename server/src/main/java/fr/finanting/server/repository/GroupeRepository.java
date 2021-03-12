package fr.finanting.server.repository;

import java.util.Optional;

import fr.finanting.server.model.Groupe;

/**
 * Repository for groupe model
 */
public interface GroupeRepository extends AbstractRepository<Groupe, Integer>{

    boolean existsByGroupeName(String groupeName);
    Optional<Groupe> findByGroupeName(String groupeName);

}
