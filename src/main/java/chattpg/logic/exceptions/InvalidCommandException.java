package chattpg.logic.exceptions;

/**
 * Thrown to indicate that a user has entered an invalid or unrecognized command
 * in the ChatTPG CLI. Examples include unknown command keywords or malformed
 * command arguments (e.g., missing required segments like "/by" or "/from"/"/to").
 */
public class InvalidCommandException extends Exception {

    /**
     * Creates a new InvalidCommandException with a human-readable reason.
     * The message is typically surfaced directly to the user.
     *
     * @param message explanation of why the command is invalid
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
