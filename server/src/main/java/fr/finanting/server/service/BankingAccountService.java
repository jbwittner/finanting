package fr.finanting.server.service;

import java.util.List;

import fr.finanting.server.dto.BankingAccountDTO;
import fr.finanting.server.exception.*;
import fr.finanting.server.parameter.CreateBankingAccountParameter;
import fr.finanting.server.parameter.DeleteBankingAccountParameter;
import fr.finanting.server.parameter.UpdateBankingAccountParameter;

public interface BankingAccountService {

    public BankingAccountDTO updateAccount(final UpdateBankingAccountParameter updateBankingAccountParameter, final String userName)
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException, CurrencyNotExistException;

    public void deleteAccount(final DeleteBankingAccountParameter deleteBankingAccountParameter, final String userName)
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException;

    public BankingAccountDTO createAccount(final CreateBankingAccountParameter createBankingAccountParameter, final String userName)
            throws UserNotExistException, GroupNotExistException, CurrencyNotExistException;

    public List<BankingAccountDTO> getUserBankingAccounts(final String userName);

    public List<BankingAccountDTO> getGroupBankingAccounts(final String groupName, final String userName) throws UserNotInGroupException, GroupNotExistException;

    public BankingAccountDTO getBankingAccount(final Integer accountId, final String userName)
            throws BankingAccountNotExistException, NotUserBankingAccountException, UserNotInGroupException;
}
