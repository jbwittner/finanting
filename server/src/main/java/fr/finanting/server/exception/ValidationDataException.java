package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class ValidationDataException extends FunctionalException {

    public ValidationDataException(final String message) {
        super(message);
    }

}