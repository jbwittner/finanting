package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class AccountNotExistException extends FunctionalException {

    public AccountNotExistException(final Integer id) {
        super("Account with the id '" + id + "' doesn't exist" );
    }

}