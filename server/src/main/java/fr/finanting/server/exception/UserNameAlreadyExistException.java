package fr.finanting.server.exception;

/**
 * Exception to signal a user name are already used
 */
@SuppressWarnings("serial")
public class UserNameAlreadyExistException extends FunctionalException {

    /**
     * Exception to signal a user name are already used
     */
    public UserNameAlreadyExistException(final String userName) {
        super("The username " + userName + " are already exist");
    }

}