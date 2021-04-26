package fr.finanting.server.exception;

public class SourceAndTargetBankingAccountNullException extends FunctionalException {

    public SourceAndTargetBankingAccountNullException() {
        super("The source and the target can't be null");
    }

}