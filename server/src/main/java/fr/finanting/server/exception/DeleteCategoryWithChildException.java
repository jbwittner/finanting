package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class DeleteCategoryWithChildException extends FunctionalException {

    public DeleteCategoryWithChildException() {
        super("You can't delete a category with childs" );
    }

}