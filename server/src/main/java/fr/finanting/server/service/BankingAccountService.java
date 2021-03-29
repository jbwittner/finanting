package fr.finanting.server.service;

import fr.finanting.server.dto.BankingAccountDTO;
import fr.finanting.server.dto.BankingAccountsDTO;
import fr.finanting.server.exception.*;
import fr.finanting.server.parameter.CreateAccountParameter;
import fr.finanting.server.parameter.DeleteAccountParameter;
import fr.finanting.server.parameter.UpdateAccountParameter;

public interface BankingAccountService {

    public BankingAccountDTO updateAccount(final UpdateAccountParameter updateAccountParameter, final String userName)
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException;

    public void deleteAccount(final DeleteAccountParameter deleteAccountParameter, final String userName)
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException;

    public BankingAccountDTO createAccount(final CreateAccountParameter createAccountParameter, final String userName)
            throws UserNotExistException, GroupNotExistException;

    public BankingAccountsDTO getUserAccounts(final String userName);

    public BankingAccountDTO getAccount(final Integer accountId, final String userName)
            throws AccountNotExistException, NotUserAccountException, UserNotInGroupException;
}
