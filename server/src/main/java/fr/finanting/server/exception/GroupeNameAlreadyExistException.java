package fr.finanting.server.exception;

/**
* Exception to signal that the group name are already used
*/
@SuppressWarnings("serial")
public class GroupNameAlreadyExistException extends FunctionalException {

    /**
    * Exception to signal that the group name are already used
    */
    public GroupNameAlreadyExistException(final String groupName) {
        super("The group " + groupName + " are already exist");
    }

}