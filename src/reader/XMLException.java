package reader;

/**
 * @author Alexi Kontos
 */

/**
 * Class to be able to throw exceptions based on the format of our XML files
 */

public class XMLException extends RuntimeException {

    /**
     * set the ID to the default value
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Create an exception based on a problem in the code
     */

    public XMLException(String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     * Create an exception based on a caught exception
     */

    public XMLException(Throwable problem) {
        super(problem);
    }
}
