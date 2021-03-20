package fr.finanting.server.exception;

public class AccountNotExistException extends FunctionalException {

    public AccountNotExistException(Integer id) {
        super("Account with the id '" + id + "' doesn't exist" );
    }

}