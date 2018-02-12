package backendClasses;
/**
 * an error for wrong cell types
 * @author shichengrao
 *
 */
public class IncorrectGameTypeException extends RuntimeException {

    /**
     * set the ID to the default value
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Create an exception based on a problem in the code
     */

    public IncorrectGameTypeException(String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     * Create an exception based on a caught exception
     */

    public IncorrectGameTypeException(Throwable problem) {
        super(problem);
    }
}
