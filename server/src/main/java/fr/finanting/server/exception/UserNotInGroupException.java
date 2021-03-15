package fr.finanting.server.exception;

import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;

/**
 * Exception to signal that the user is not in group
 */
@SuppressWarnings("serial")
public class UserNotInGroupException extends FunctionalException {

    /**
     * Exception to signal that the user is not in group
     */
    public UserNotInGroupException(final User user, final Group group) {
        super("The user " + user.getUserName() + " are not in group " + group.getGroupName());
    }

}