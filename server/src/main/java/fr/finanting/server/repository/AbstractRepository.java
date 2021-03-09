package fr.finanting.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Abstract part of repositories
 */
@NoRepositoryBean
public interface AbstractRepository<E, K> extends JpaRepository<E, K> {

    /**
     * Method to delete a entity
     *
     * @param E Entity to delete
     */
    @Override
    void delete(E E);

}