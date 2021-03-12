package fr.finanting.server.exception;

import fr.finanting.server.model.Groupe;
import fr.finanting.server.model.User;

@SuppressWarnings("serial")
public class UserNotInGroupeException extends FunctionalException {

    public UserNotInGroupeException(final User user, final Groupe groupe) {
        super("The user " + user.getUserName() + " are not in groupe " + groupe.getGroupeName());
    }

}