package fr.finanting.server.exception;

public class CategoryNoUserException extends FunctionalException {

    public CategoryNoUserException(final Integer id) {
        super("You are not the owner of the category with the id '" + id + "'" );
    }

}