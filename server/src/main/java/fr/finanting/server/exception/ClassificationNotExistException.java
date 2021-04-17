package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class ClassificationNotExistException extends FunctionalException {

    public ClassificationNotExistException(final Integer id) {
        super("Classification with the id '" + id + "' doesn't exist" );
    }

}