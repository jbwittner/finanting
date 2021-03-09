package fr.finanting.server.exception;

/**
 * Exception to signal a bad password
 */
@SuppressWarnings("serial")
public class BadPasswordException extends FunctionalException {

    /**
     * Exception to signal a bad password
     */
    public BadPasswordException() {
        super("Bad password");
    }

}