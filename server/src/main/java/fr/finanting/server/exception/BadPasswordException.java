package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class BadPasswordException extends FunctionalException {

    public BadPasswordException() {
        super("Bad password");
    }

}