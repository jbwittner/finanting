package fr.finanting.server.exception;

public class BadPasswordException extends FunctionalException {

    public BadPasswordException() {
        super("Bad password");
    }

}