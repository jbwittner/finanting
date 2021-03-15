package fr.finanting.server.exception;

/**
* Exception to signal that the group doesn't exist
*/
@SuppressWarnings("serial")
public class GroupeNotExistException extends FunctionalException {

    /**
    * Exception to signal that the group doesn't exist
    */
    public GroupeNotExistException(final String groupName) {
        super("The groupe " + groupName + " not exist");
    }

}