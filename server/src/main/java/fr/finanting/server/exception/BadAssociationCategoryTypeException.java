package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class BadAssociationCategoryTypeException extends FunctionalException {

    public BadAssociationCategoryTypeException() {
        super("You can't associate a expense category with a revenue category");
    }

}
