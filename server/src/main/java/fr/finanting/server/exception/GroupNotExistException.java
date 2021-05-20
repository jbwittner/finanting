package fr.finanting.server.exception;

public class GroupNotExistException extends FunctionalException {

    public GroupNotExistException(final String groupName) {
        super("The group " + groupName + " not exist");
    }

    public GroupNotExistException(final Integer groupId) {
        super("The group with the id " + groupId + " not exist");
    }

}