package fr.finanting.server.repository;

import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;

import java.util.List;

public interface BankingAccountRepository extends AbstractRepository<BankingAccount, Integer>{

    List<BankingAccount> findByUser(User user);
    List<BankingAccount> findByGroup(Group group);

}