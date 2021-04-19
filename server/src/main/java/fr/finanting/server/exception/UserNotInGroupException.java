package fr.finanting.server.exception;

import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;

public class UserNotInGroupException extends FunctionalException {

    public UserNotInGroupException(final User user, final Group group) {
        super("The user " + user.getUserName() + " are not in group " + group.getGroupName());
    }

}