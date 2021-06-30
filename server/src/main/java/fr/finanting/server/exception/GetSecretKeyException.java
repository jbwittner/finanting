package fr.finanting.server.exception;

public class GetSecretKeyException extends FunctionalException {

    public GetSecretKeyException(Exception e) {
        super("Error generating JWT Secret Key : " + e.getMessage());
    }

}