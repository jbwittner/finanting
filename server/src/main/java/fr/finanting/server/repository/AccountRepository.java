package fr.finanting.server.repository;

import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.User;

import java.util.List;

public interface AccountRepository extends AbstractRepository<BankingAccount, Integer>{

    List<BankingAccount> findByUser(User user);

}