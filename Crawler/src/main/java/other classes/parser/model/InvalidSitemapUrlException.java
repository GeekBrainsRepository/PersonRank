package geekbrains.internship.project_full.Crawler.src.main.java.other_classes.parser.model;

/**
 * An exception that indicates that a supplied sitemap URL was invalid. This could mean that the URL itself was invalid
 * or that no sitemap could be found at this URL.
 */
public class InvalidSitemapUrlException extends RuntimeException {
    /**
     * Create a new InvalidSitemapUrlException with a message.
     * @param message    The message of the exception.
     */
    public InvalidSitemapUrlException(final String message) {
        super(message);
    }

    /**
     * Create a new InvalidSitemapUrlException with a message and a cause.
     * @param message    The message of the exception.
     * @param cause      The cause of the exception.
     */
    public InvalidSitemapUrlException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new InvalidSitemapUrlException with a cause.
     * @param cause    The cause of the exception.
     */
    public InvalidSitemapUrlException(final Throwable cause) {
        super(cause);
    }
}
