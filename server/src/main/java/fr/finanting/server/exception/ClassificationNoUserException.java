package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class ClassificationNoUserException extends FunctionalException {

    public ClassificationNoUserException(final Integer id) {
        super("You are not the owner of the classification with the id '" + id + "'" );
    }

}