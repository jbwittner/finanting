package fr.finanting.server.service;

import fr.finanting.server.dto.BankingAccountDTO;
import fr.finanting.server.dto.BankingAccountsDTO;
import fr.finanting.server.exception.*;
import fr.finanting.server.parameter.CreateBankingAccountParameter;
import fr.finanting.server.parameter.DeleteBankingAccountParameter;
import fr.finanting.server.parameter.UpdateBankingAccountParameter;

public interface BankingAccountService {

    public BankingAccountDTO updateAccount(final UpdateBankingAccountParameter updateBankingAccountParameter, final String userName)
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException;

    public void deleteAccount(final DeleteBankingAccountParameter deleteBankingAccountParameter, final String userName)
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException;

    public BankingAccountDTO createAccount(final CreateBankingAccountParameter createBankingAccountParameter, final String userName)
            throws UserNotExistException, GroupNotExistException;

    public BankingAccountsDTO getUserBankingAccounts(final String userName);

    public BankingAccountDTO getBankingAccount(final Integer accountId, final String userName)
            throws BankingAccountNotExistException, NotUserBankingAccountException, UserNotInGroupException;
}
