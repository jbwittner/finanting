package fr.finanting.server.repository;

import java.util.List;

import fr.finanting.server.model.Group;
import fr.finanting.server.model.Third;
import fr.finanting.server.model.User;

public interface ThirdRepository extends AbstractRepository<Third, Integer>{

    List<Third> findByUser(final User user);
    List<Third> findByGroup(final Group user);

    
}
