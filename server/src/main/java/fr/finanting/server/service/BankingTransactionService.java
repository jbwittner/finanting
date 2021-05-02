package fr.finanting.server.service;

import java.util.List;

import fr.finanting.server.dto.BankingTransactionDTO;
import fr.finanting.server.exception.BadAssociationElementException;
import fr.finanting.server.exception.BankingAccountNotExistException;
import fr.finanting.server.exception.BankingTransactionNotExistException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.NotUserElementException;
import fr.finanting.server.exception.ThirdNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.parameter.CreateBankingTransactionParameter;
import fr.finanting.server.parameter.UpdateBankingTransactionParameter;

public interface BankingTransactionService {

    public BankingTransactionDTO createBankingTransaction(final CreateBankingTransactionParameter createBankingTransactionParameter, String userName)
        throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException,
        CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException;

    public BankingTransactionDTO updateBankingTransaction(final UpdateBankingTransactionParameter updateBankingTransactionParameter, final String userName)
        throws BankingTransactionNotExistException, BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException,
        ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException;

    public BankingTransactionDTO getBankingTransaction(final Integer id, final String userName) 
        throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException;

    public List<BankingTransactionDTO> getAccountBankingTransaction(final Integer id, final String userName) 
        throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException, BankingAccountNotExistException;
    
}
