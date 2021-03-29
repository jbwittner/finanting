package fr.finanting.server.exception;

import fr.finanting.server.model.BankingAccount;

@SuppressWarnings("serial")
public class NotUserBankingAccountException extends FunctionalException {

    public NotUserBankingAccountException(final String userName, final BankingAccount bankingAccount) {
        super("The user '" + userName + "' are not the proprietor of the banking account '" + bankingAccount.getLabel() + "'");
    }

}
