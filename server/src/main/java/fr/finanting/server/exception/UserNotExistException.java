package fr.finanting.server.exception;

public class UserNotExistException extends FunctionalException {
    
    public UserNotExistException(final String userName) {
        super("The user with " + userName + " not exist");
    }

}