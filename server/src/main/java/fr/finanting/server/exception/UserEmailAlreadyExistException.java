package fr.finanting.server.exception;

/**
 * Exception to signal a email are already used
 */
@SuppressWarnings("serial")
public class UserEmailAlreadyExistException extends FunctionalException {

    /**
     * Exception to signal a email are already used
     */
    public UserEmailAlreadyExistException(final String email) {
        super("The email " + email + " are already exist");
    }

}