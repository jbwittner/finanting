package fr.finanting.server.service;

import fr.finanting.server.dto.AccountDTO;
import fr.finanting.server.dto.AccountsDTO;
import fr.finanting.server.exception.*;
import fr.finanting.server.parameter.CreateAccountParameter;
import fr.finanting.server.parameter.DeleteAccountParameter;
import fr.finanting.server.parameter.UpdateAccountParameter;

public interface AccountService {

    public AccountDTO updateAccount(final UpdateAccountParameter updateAccountParameter, final String userName)
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException;

    public void deleteAccount(final DeleteAccountParameter deleteAccountParameter, final String userName)
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException;

    public AccountDTO createAccount(final CreateAccountParameter createAccountParameter, final String userName)
            throws UserNotExistException, GroupNotExistException;

    public AccountsDTO getUserAccounts(final String userName);

    public AccountDTO getAccount(final Integer accountId, final String userName)
            throws AccountNotExistException, NotUserAccountException, UserNotInGroupException;
}
