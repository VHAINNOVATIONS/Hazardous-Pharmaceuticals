/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Exception thrown when the user is not authorized to perform an action.
 */
public class AuthorizationException extends ValidationException {
    
    /**
     * NOT_AUTHORIZED.
     */
    public static final MessageKey NOT_AUTHORIZED = new MessageKey("NOT_AUTHORIZED");

    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public AuthorizationException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public AuthorizationException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public AuthorizationException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public AuthorizationException(Exception e, MessageKey key) {
        super(e, key);
    }
}
