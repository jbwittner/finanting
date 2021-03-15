package fr.finanting.server.exception;

/**
* Exception to signal that the user doesn't exist
*/
@SuppressWarnings("serial")
public class UserNotExistException extends FunctionalException {
    
    /**
    * Exception to signal that the user doesn't exist
    */
    public UserNotExistException(final String userName) {
        super("The user with " + userName + " not exist");
    }

}