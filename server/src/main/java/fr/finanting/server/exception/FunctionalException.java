package fr.finanting.server.exception;

public abstract class FunctionalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FunctionalException(final String message){
        super(message);
    }
    
}
