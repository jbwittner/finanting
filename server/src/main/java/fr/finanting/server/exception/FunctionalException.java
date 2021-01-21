package fr.finanting.server.exception;

/**
 * Abstract functional exception used to pass the exception to the frontend
 */
public abstract class FunctionalException extends Exception{

    private static final long serialVersionUID = 1L;

    /**
     * Default Constructeur
     */
    public FunctionalException() {
        super();
    }

    /**
     * Constructeur
     * @param message the message parameter
     */
    public FunctionalException(final String message){
        super(message);
    }

    /**
     * Constructeur
     * @param message the message parameter
     * @param cause the cause parameter
     */
    public FunctionalException(final String message, final Throwable cause){
        super(message, cause);
    }
    
}
