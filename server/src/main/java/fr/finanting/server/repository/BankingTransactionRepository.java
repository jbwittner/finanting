package fr.finanting.server.repository;

import java.util.List;

import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.BankingTransaction;
import fr.finanting.server.model.Currency;
import org.springframework.data.jpa.repository.Query;

public interface BankingTransactionRepository extends AbstractRepository<BankingTransaction, Integer>{

    List<BankingTransaction> findByAccount(BankingAccount account);

    Boolean existsByCurrency(final Currency currency);

    @Query(value = "select sum(b.AMOUNT) from BANKING_TRANSACTIONS b where b.ACCOUNT_ID = ?1", nativeQuery = true)
    Double sumAmountByAccountId(Integer accountId);

}