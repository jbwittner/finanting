package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class GroupeNameAlreadyExistException extends FunctionalException {

    public GroupeNameAlreadyExistException(final String groupeName) {
        super("The groupe " + groupeName + " are already exist");
    }

}