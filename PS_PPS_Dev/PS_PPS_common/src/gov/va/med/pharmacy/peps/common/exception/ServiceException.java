/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Unchecked exception thrown when exceptions from the service layer.
 */
public class ServiceException extends PharmacyRuntimeException {
    
    /**
     * COTS_UPDATE_FAILURE.
     */
    public static final MessageKey COTS_UPDATE_FAILURE = new MessageKey("COTS_UPDATE_FAILURE");
    
    /**
     * ILLEGAL_ARGUMENT.
     */
    public static final MessageKey ILLEGAL_ARGUMENT = new MessageKey("ILLEGAL_ARGUMENT");
    
    /**
     * PARTIAL_SAVE_FAILURE.
     */
    public static final MessageKey PARTIAL_SAVE_FAILURE = new MessageKey("PARTIAL_SAVE_FAILURE");
    
    /**
     * PROBLEM_REPORT_FAILURE.
     */
    public static final MessageKey PROBLEM_REPORT_FAILURE = new MessageKey("PROBLEM_REPORT_FAILURE");
    
    /**
     * PROCESS_MESSAGE_FAILURE.
     */
    public static final MessageKey PROCESS_MESSAGE_FAILURE = new MessageKey("PROCESS_MESSAGE_FAILURE");
    
    /**
     * PROCESS_MESSAGE_UNKNOWN_REQUEST_TYPE.
     */
    public static final MessageKey PROCESS_MESSAGE_UNKNOWN_REQUEST_TYPE = new MessageKey(
        "PROCESS_MESSAGE_UNKNOWN_REQUEST_TYPE");

    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public ServiceException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public ServiceException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public ServiceException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public ServiceException(Exception e, MessageKey key) {
        super(e, key);
    }
}
