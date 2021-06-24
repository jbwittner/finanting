package fr.finanting.server.service;

import java.util.List;

import fr.finanting.server.generated.model.BankingAccountDTO;
import fr.finanting.server.generated.model.BankingAccountParameter;
import fr.finanting.server.generated.model.UpdateBankingAccountParameter;
import fr.finanting.server.exception.*;

public interface BankingAccountService {

    BankingAccountDTO updateAccount(final Integer bankingAccountId, final UpdateBankingAccountParameter updateBankingAccountParameter, final String userName)
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException, CurrencyNotExistException;

    void deleteAccount(final Integer bankingAccountId, final String userName)
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException;

    BankingAccountDTO createAccount(final BankingAccountParameter bankingAccountParameter, final String userName)
            throws UserNotExistException, GroupNotExistException, CurrencyNotExistException;

    List<BankingAccountDTO> getUserBankingAccounts(final String userName);

    List<BankingAccountDTO> getGroupBankingAccounts(final Integer groupId, final String userName) throws UserNotInGroupException, GroupNotExistException;

    BankingAccountDTO getBankingAccount(final Integer accountId, final String userName)
            throws BankingAccountNotExistException, NotUserBankingAccountException, UserNotInGroupException;
}
