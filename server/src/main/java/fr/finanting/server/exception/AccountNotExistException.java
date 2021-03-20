package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class AccountNotExistException extends FunctionalException {

    public AccountNotExistException(Integer id) {
        super("Account with the id '" + id + "' doesn't exist" );
    }

}