package parsing.parser.model;

/**
 * An exception that indicates that there was a problem creating the URL connection. This could also mean that the URL
 * itself was invalid.
 */
public class UrlConnectionException extends RuntimeException {
    /**
     * Create a new UrlConnectionException with a message.
     * @param message    The message of the exception.
     */
    public UrlConnectionException(final String message) {
        super(message);
    }

    /**
     * Create a new UrlConnectionException with a message and a cause.
     * @param message    The message of the exception.
     * @param cause      The cause of the exception.
     */
    public UrlConnectionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new UrlConnectionException with a cause.
     * @param cause    The cause of the exception.
     */
    public UrlConnectionException(final Throwable cause) {
        super(cause);
    }
}
