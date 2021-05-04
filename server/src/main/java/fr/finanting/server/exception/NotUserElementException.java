package fr.finanting.server.exception;

public class NotUserElementException extends FunctionalException {

    public NotUserElementException() {
        super("You are not the owner of the element");
    }

}
