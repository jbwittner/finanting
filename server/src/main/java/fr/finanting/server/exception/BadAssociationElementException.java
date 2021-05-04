package fr.finanting.server.exception;

public class BadAssociationElementException extends FunctionalException {

    public BadAssociationElementException() {
        super("You can only associate user element with a other user element (same user) (same for group)");
    }

}