package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class BadAssociationCategoryUserGroup extends FunctionalException {

    public BadAssociationCategoryUserGroup() {
        super("You can't associate a user category with a group category");
    }

}