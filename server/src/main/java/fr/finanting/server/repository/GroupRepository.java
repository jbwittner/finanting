package fr.finanting.server.repository;

import java.util.Optional;

import fr.finanting.server.model.Group;

public interface GroupRepository extends AbstractRepository<Group, Integer>{

    boolean existsByGroupName(String groupName);

    Optional<Group> findByGroupName(String groupName);

}
