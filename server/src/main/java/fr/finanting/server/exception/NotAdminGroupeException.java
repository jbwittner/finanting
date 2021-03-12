package fr.finanting.server.exception;

import fr.finanting.server.model.Groupe;

@SuppressWarnings("serial")
public class NotAdminGroupeException extends FunctionalException {

    public NotAdminGroupeException(final Groupe groupe) {
        super("You are not the admin of the groupe : " + groupe.getGroupeName());
    }

}