package other_classes.parser;

/**
 * An exception that indicates that a file that should be parsed is not an XML file.
 */
class InvalidXmlException extends RuntimeException {
    /**
     * Create a new InvalidXmlException with a message.
     * @param message    The message of the exception.
     */
    public InvalidXmlException(final String message) {
        super(message);
    }

    /**
     * Create a new InvalidXmlException with a message and a cause.
     * @param message    The message of the exception.
     * @param cause      The cause of the exception.
     */
    public InvalidXmlException(final String message, final Exception cause) {
        super(message, cause);
    }

    /**
     * Create a new InvalidXmlException with a cause.
     * @param cause    The cause of the exception.
     */
    public InvalidXmlException(final Exception cause) {
        super(cause);
    }
}
