package fr.finanting.server.exception;

/**
* Exception to signal that the group doesn't exist
*/
@SuppressWarnings("serial")
public class GroupNotExistException extends FunctionalException {

    /**
    * Exception to signal that the group doesn't exist
    */
    public GroupNotExistException(final String groupName) {
        super("The group " + groupName + " not exist");
    }

}