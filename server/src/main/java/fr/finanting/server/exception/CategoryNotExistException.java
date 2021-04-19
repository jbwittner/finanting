package fr.finanting.server.exception;

public class CategoryNotExistException extends FunctionalException {

    public CategoryNotExistException(final Integer id) {
        super("Category with the id '" + id + "' doesn't exist" );
    }

}