package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class GroupNotExistException extends FunctionalException {

    public GroupNotExistException(final String groupName) {
        super("The group " + groupName + " not exist");
    }

}