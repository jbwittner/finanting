package fr.finanting.server.repository;

import fr.finanting.server.model.Account;
import fr.finanting.server.model.User;

import java.util.List;

public interface AccountRepository extends AbstractRepository<Account, Integer>{

    List<Account> findByUser(User user);

}