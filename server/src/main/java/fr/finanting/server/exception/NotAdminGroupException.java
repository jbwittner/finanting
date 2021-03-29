package fr.finanting.server.exception;

import fr.finanting.server.model.Group;

@SuppressWarnings("serial")
public class NotAdminGroupException extends FunctionalException {

    public NotAdminGroupException(final Group group) {
        super("You are not the admin of the group : " + group.getGroupName());
    }

}