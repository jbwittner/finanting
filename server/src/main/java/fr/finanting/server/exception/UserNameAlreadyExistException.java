package fr.finanting.server.exception;

public class UserNameAlreadyExistException extends FunctionalException {

    public UserNameAlreadyExistException(final String userName) {
        super("The username " + userName + " are already exist");
    }

}