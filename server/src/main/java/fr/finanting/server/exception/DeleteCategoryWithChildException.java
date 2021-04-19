package fr.finanting.server.exception;

public class DeleteCategoryWithChildException extends FunctionalException {

    public DeleteCategoryWithChildException() {
        super("You can't delete a category with childs" );
    }

}