package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class BankingAccountNotExistException extends FunctionalException {

    public BankingAccountNotExistException(final Integer id) {
        super("Account with the id '" + id + "' doesn't exist" );
    }

}