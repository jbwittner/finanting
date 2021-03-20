package fr.finanting.server.exception;

import fr.finanting.server.model.Account;

@SuppressWarnings("serial")
public class NotUserAccountException extends FunctionalException {

    public NotUserAccountException(String userName, Account account) {
        super("The user '" + userName + "' are not the proprietor of the account '" + account.getLabel() + "'");
    }

}
