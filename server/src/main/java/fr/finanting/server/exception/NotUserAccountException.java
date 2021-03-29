package fr.finanting.server.exception;

import fr.finanting.server.model.BankingAccount;

@SuppressWarnings("serial")
public class NotUserAccountException extends FunctionalException {

    public NotUserAccountException(final String userName, final BankingAccount account) {
        super("The user '" + userName + "' are not the proprietor of the account '" + account.getLabel() + "'");
    }

}
