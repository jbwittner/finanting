package fr.finanting.server.exception;

public class BadAssociationCategoryTypeException extends FunctionalException {

    public BadAssociationCategoryTypeException() {
        super("You can't associate a expense category with a revenue category");
    }

}
