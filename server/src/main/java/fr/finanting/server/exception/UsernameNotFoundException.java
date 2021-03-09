package fr.finanting.server.exception;

/**
 * Exception to signal a user name are not used
 */
@SuppressWarnings("serial")
public class UsernameNotFoundException extends FunctionalException {

    /**
     * Exception to signal a user name are not used
     */
    public UsernameNotFoundException(final String username) {
        super("The username " + username + " are not used by a account");
    }

}