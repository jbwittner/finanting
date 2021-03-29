package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class UserNameAlreadyExistException extends FunctionalException {

    public UserNameAlreadyExistException(final String userName) {
        super("The username " + userName + " are already exist");
    }

}