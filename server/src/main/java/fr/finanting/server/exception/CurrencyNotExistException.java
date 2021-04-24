package fr.finanting.server.exception;

public class CurrencyNotExistException extends FunctionalException {

    public CurrencyNotExistException(final Integer id) {
        super("The currency with the id " + id + " not exist");
    }

    public CurrencyNotExistException(final String ISOCode) {
        super("The currency with the code " + ISOCode + " not exist");
    }


}
