package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class UserEmailAlreadyExistException extends FunctionalException {

    public UserEmailAlreadyExistException(final String email) {
        super("The email " + email + " are already exist");
    }

}