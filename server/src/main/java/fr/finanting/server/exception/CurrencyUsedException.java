package fr.finanting.server.exception;

public class CurrencyUsedException extends FunctionalException {

    public CurrencyUsedException(final String ISOCode) {
        super("The currency with the code " + ISOCode + " are used");
    }

}