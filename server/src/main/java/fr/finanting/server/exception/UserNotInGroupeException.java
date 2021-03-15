package fr.finanting.server.exception;

import fr.finanting.server.model.Groupe;
import fr.finanting.server.model.User;

/**
 * Exception to signal that the user is not in groupe
 */
@SuppressWarnings("serial")
public class UserNotInGroupeException extends FunctionalException {

    /**
     * Exception to signal that the user is not in groupe
     */
    public UserNotInGroupeException(final User user, final Groupe groupe) {
        super("The user " + user.getUserName() + " are not in groupe " + groupe.getGroupeName());
    }

}