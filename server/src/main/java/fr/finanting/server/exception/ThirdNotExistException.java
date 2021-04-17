package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class ThirdNotExistException extends FunctionalException {

    public ThirdNotExistException(final Integer id) {
        super("Third with the id '" + id + "' doesn't exist");
    }

}