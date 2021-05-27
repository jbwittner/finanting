package fr.finanting.server.service;

import fr.finanting.codegen.model.BankingTransactionDTO;
import fr.finanting.codegen.model.BankingTransactionParameter;
import fr.finanting.server.exception.BadAssociationElementException;
import fr.finanting.server.exception.BankingAccountNotExistException;
import fr.finanting.server.exception.BankingTransactionNotExistException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.NotUserElementException;
import fr.finanting.server.exception.ThirdNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;

import java.util.List;

public interface BankingTransactionService {

    BankingTransactionDTO createBankingTransaction(final BankingTransactionParameter bankingTransactionParameter, String userName)
        throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException,
        CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException;

    BankingTransactionDTO updateBankingTransaction(Integer bankingTransactionId,
                                                          final BankingTransactionParameter bankingTransactionParameter, final String userName)
        throws BankingTransactionNotExistException, BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException,
        ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException;

    BankingTransactionDTO getBankingTransaction(final Integer bankingTransactionId, final String userName)
        throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException;

    List<BankingTransactionDTO> getBankingAccountTransaction(final Integer bankingAccountId, final String userName)
        throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException, BankingAccountNotExistException;

    void deleteAccountBankingTransaction(final Integer bankingTransactionId, final String userName)
        throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException;
    
}
