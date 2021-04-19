package fr.finanting.server.exception;

public class BadAssociationCategoryUserGroupException extends FunctionalException {

    public BadAssociationCategoryUserGroupException() {
        super("You can't associate a user category with a group category");
    }

}