package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class GroupeNotExistException extends FunctionalException {

    public GroupeNotExistException(final String groupName) {
        super("The groupe " + groupName + " not exist");
    }

}