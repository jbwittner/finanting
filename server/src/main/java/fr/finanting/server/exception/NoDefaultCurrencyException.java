package fr.finanting.server.exception;

public class NoDefaultCurrencyException extends FunctionalException {

    public NoDefaultCurrencyException() {
        super("The application need a default currency");
    }

}