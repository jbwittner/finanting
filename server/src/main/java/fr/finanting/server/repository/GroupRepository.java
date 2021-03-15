package fr.finanting.server.repository;

import java.util.Optional;

import fr.finanting.server.model.Group;

/**
 * Repository for group model
 */
public interface GroupRepository extends AbstractRepository<Group, Integer>{

    /**
     * Method to check if a group exist by group name
     */
    boolean existsByGroupName(String groupName);

    /**
     * Method to get group by group name
     */
    Optional<Group> findByGroupName(String groupName);

}
