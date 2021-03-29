package fr.finanting.server.exception;

@SuppressWarnings("serial")
public class GroupNameAlreadyExistException extends FunctionalException {

    public GroupNameAlreadyExistException(final String groupName) {
        super("The group " + groupName + " are already exist");
    }

}