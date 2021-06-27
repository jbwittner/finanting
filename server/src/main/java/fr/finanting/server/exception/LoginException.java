package fr.finanting.server.exception;

public class LoginException extends FunctionalException {

    public LoginException(final Exception exception) {
        super(exception.getMessage());
    }

}