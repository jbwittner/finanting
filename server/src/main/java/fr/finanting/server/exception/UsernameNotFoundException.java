package fr.finanting.server.exception;

@SuppressWarnings("serial")

public class UsernameNotFoundException extends FunctionalException {

    /**
     * Exception to signal a violation of data validation
     */
    public UsernameNotFoundException(final String username) {
        super("The username " + username + " are not used by a account");
    }

}