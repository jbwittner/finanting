package fr.finanting.server.exception;

public class BankingTransactionNotExistException extends FunctionalException {

    public BankingTransactionNotExistException(final Integer id) {
        super("Banking Transaction with the id '" + id + "' doesn't exist" );
    }

}