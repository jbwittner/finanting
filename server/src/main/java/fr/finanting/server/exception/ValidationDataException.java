package fr.finanting.server.exception;

/**
 * Exception to signal a violation of data validation
 */
@SuppressWarnings("serial")
public class ValidationDataException extends FunctionalException {

    /**
     * Exception to signal a violation of data validation
     */
    public ValidationDataException(final String message) {
        super(message);
    }

}