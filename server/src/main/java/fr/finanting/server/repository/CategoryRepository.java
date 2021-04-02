package fr.finanting.server.repository;

import java.util.List;

import fr.finanting.server.model.Category;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;

public interface CategoryRepository extends AbstractRepository<Category, Integer>{

    List<Category> findByUserAndGroupIsNullAndParentIsNull(User user);

    List<Category> findByGroupAndParentIsNull(Group group);
    
}
