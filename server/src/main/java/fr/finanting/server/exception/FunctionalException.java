package fr.finanting.server.exception;

/**
 * Abstract functional exception used to pass the exception to the frontend
 */
public abstract class FunctionalException extends Exception{

    private static final long serialVersionUID = 1L;

    /**
     * Constructeur
     * @param message the message parameter
     */
    public FunctionalException(final String message){
        super(message);
    }
    
}
