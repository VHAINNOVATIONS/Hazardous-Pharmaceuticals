/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Unchecked exception thrown when exceptions from the presentation layer.
 */
public class PresentationException extends PharmacyRuntimeException {
    
    /**
     * SPRING_ERROR.
     */
    public static final MessageKey SPRING_ERROR = new MessageKey("SPRING_ERROR");
    
    /**
     * STRUTS_ERROR.
     */
    public static final MessageKey STRUTS_ERROR = new MessageKey("STRUTS_ERROR");
    
    /**
     * NO_CONTEXT_AVAILABLE.
     */
    public static final MessageKey NO_CONTEXT_AVAILABLE = new MessageKey("NO_CONTEXT_AVAILABLE");
    
    /**
     * NO_GUI_CONTEXT_AVAILABLE.
     */
    public static final MessageKey NO_GUI_CONTEXT_AVAILABLE = new MessageKey("NO_GUI_CONTEXT_AVAILABLE");
    
    /**
     * NO_GUI_SESSION_AVAILABLE.
     */
    public static final MessageKey NO_GUI_SESSION_AVAILABLE = new MessageKey("NO_GUI_SESSION_AVAILABLE");
    
    /**
     * TILES_ERROR.
     */
    public static final MessageKey TILES_ERROR = new MessageKey("TILES_ERROR");
    
    /**
     * SPRING_WEB_FLOW_ERROR.
     */
    public static final MessageKey SPRING_WEB_FLOW_ERROR = new MessageKey("SPRING_WEB_FLOW_ERROR");
    
    /**
     * AUTOMATION_TESTING_ERROR.
     */
    public static final MessageKey AUTOMATION_TESTING_ERROR = new MessageKey("AUTOMATION_TESTING_ERROR");
    
    /**
     * Automation testing error condition strings.
     * Note: Need to fill out text below to complete this sentence:
     *   "An automated testing error occurred while {0}."
     */
    public static final String AUTOMATION_TESTING_LOGIN_FAILED = "attempting log in to server";
    
    /**
     * AUTOMATION_TESTING_ERROR_PAGE_SEEN
     */
    public static final String AUTOMATION_TESTING_ERROR_PAGE_SEEN = "processing server action, resulting in error page";
    
    /**
     * AUTOMATION_TESTING_LOGOUT_FAILED.
     */
    public static final String AUTOMATION_TESTING_LOGOUT_FAILED = "attempting to logout from server";
    
    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public PresentationException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public PresentationException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public PresentationException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public PresentationException(Exception e, MessageKey key) {
        super(e, key);
    }
}
