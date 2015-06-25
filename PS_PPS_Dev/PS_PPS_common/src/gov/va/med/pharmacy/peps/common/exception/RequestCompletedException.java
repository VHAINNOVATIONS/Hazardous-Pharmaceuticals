/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Checked exception thrown when trying to add an item that already exists
 */
public class RequestCompletedException extends ValidationException {

    /**
     * REQUEST_COMPLETE
     */
    public static final MessageKey REQUEST_COMPLETE = new MessageKey("REQUEST_COMPLETE");

    /**
     * REQUEST_COMPLETE_MULTIPLE
     */
    public static final MessageKey REQUEST_COMPLETE_MULTIPLE = new MessageKey("REQUEST_COMPLETE_MULTIPLE");

    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public RequestCompletedException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public RequestCompletedException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public RequestCompletedException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public RequestCompletedException(Exception e, MessageKey key) {
        super(e, key);
    }
}
