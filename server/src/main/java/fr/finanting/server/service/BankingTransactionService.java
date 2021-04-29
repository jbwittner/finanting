package fr.finanting.server.service;

import fr.finanting.server.dto.BankingTransactionDTO;
import fr.finanting.server.exception.BadAssociationElementException;
import fr.finanting.server.exception.BankingAccountNotExistException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.ThirdNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.parameter.CreateBankingTransactionParameter;

public interface BankingTransactionService {

    public BankingTransactionDTO createBankingTransaction(final CreateBankingTransactionParameter createBankingTransactionParameter, String userName)
        throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException,
        CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException;
    
}
