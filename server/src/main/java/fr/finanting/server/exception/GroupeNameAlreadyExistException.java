package fr.finanting.server.exception;

/**
* Exception to signal that the group name are already used
*/
@SuppressWarnings("serial")
public class GroupeNameAlreadyExistException extends FunctionalException {

    /**
    * Exception to signal that the group name are already used
    */
    public GroupeNameAlreadyExistException(final String groupeName) {
        super("The groupe " + groupeName + " are already exist");
    }

}