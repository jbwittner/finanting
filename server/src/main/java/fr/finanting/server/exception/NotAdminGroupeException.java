package fr.finanting.server.exception;

import fr.finanting.server.model.Group;

/**
* Exception to signal that the user are not the admin of the group
*/
@SuppressWarnings("serial")
public class NotAdminGroupException extends FunctionalException {

    /**
    * Exception to signal that the user are not the admin of the group
    */
    public NotAdminGroupException(final Group group) {
        super("You are not the admin of the group : " + group.getGroupName());
    }

}