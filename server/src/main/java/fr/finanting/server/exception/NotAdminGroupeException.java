package fr.finanting.server.exception;

import fr.finanting.server.model.Groupe;

/**
* Exception to signal that the user are not the admin of the groupe
*/
@SuppressWarnings("serial")
public class NotAdminGroupeException extends FunctionalException {

    /**
    * Exception to signal that the user are not the admin of the groupe
    */
    public NotAdminGroupeException(final Groupe groupe) {
        super("You are not the admin of the groupe : " + groupe.getGroupeName());
    }

}