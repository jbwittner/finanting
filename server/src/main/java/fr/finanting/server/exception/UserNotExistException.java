package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class UserNotExistException extends FunctionalException {

    public UserNotExistException(final String userName) {
        super("The user with " + userName + " not exist");
    }

}