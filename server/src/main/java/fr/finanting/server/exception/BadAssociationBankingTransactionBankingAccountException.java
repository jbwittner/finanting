package fr.finanting.server.exception;

public class BadAssociationBankingTransactionBankingAccountException extends FunctionalException {

    public BadAssociationBankingTransactionBankingAccountException() {
        super("You can only associate a transaction with your banking account");
    }

}