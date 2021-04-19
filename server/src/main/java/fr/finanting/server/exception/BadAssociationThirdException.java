package fr.finanting.server.exception;

public class BadAssociationThirdException extends FunctionalException {

    public BadAssociationThirdException() {
        super("You can only associate a third party with a category of which you are the owner or that you are in the group and you can't associate a user third with a group category and vice versa");
    }

}