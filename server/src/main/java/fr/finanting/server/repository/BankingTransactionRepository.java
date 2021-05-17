package fr.finanting.server.repository;

import java.util.List;

import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.BankingTransaction;
import fr.finanting.server.model.Currency;

public interface BankingTransactionRepository extends AbstractRepository<BankingTransaction, Integer>{

    List<BankingTransaction> findByAccount(BankingAccount account);

    Boolean existsByCurrency(final Currency currency);

}