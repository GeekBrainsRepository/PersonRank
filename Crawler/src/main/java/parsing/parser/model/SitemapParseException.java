package parsing.parser.model;

/**
 * An exception that indicates that there was a problem parsing the sitemap.
 */
public class SitemapParseException extends RuntimeException {
    /**
     * Create a new SitemapParseException with a message.
     * @param message    The message of the exception.
     */
    public SitemapParseException(final String message) {
        super(message);
    }

    /**
     * Create a new SitemapParseException with a message and a cause.
     * @param message    The message of the exception.
     * @param cause      The cause of the exception.
     */
    public SitemapParseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new SitemapParseException with a cause.
     * @param cause    The cause of the exception.
     */
    public SitemapParseException(final Throwable cause) {
        super(cause);
    }
}
