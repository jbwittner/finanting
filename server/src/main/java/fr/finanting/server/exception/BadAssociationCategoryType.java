package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class BadAssociationCategoryType extends FunctionalException {

    public BadAssociationCategoryType() {
        super("You can't associate a expense category with a revenue category");
    }

}
