/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Exception thrown when the credentials (username/password) given during authentication fail.
 */
public class AuthenticationException extends ValidationException {
    
    /**
     * INVALID_CREDENTIALS.
     */
    public static final MessageKey INVALID_CREDENTIALS = new MessageKey("INVALID_CREDENTIALS");
    
    /**
     * USER_NOT_FOUND.
     */
    public static final MessageKey USER_NOT_FOUND = new MessageKey("USER_NOT_FOUND");

    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public AuthenticationException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public AuthenticationException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public AuthenticationException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public AuthenticationException(Exception e, MessageKey key) {
        super(e, key);
    }
}
