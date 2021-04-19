package fr.finanting.server.repository;

import java.util.List;

import fr.finanting.server.model.Classification;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;

public interface ClassificationRepository extends AbstractRepository<Classification, Integer>{
    
    List<Classification> findByUserAndGroupIsNull(User user);

    List<Classification> findByGroupAndUserIsNull(Group group);
}
