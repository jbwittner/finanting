package fr.finanting.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepository<E, K> extends JpaRepository<E, K> {

    @Override
    void delete(E E);

}