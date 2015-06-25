/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Checked exception thrown when trying to add an item that already exists
 */
public class PresentationSecurityException extends ValidationException {

    /**
     * ROLE_REQUIRED
     */
    public static final MessageKey ROLE_REQUIRED = new MessageKey("ROLE_REQUIRED");

    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public PresentationSecurityException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public PresentationSecurityException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public PresentationSecurityException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public PresentationSecurityException(Exception e, MessageKey key) {
        super(e, key);
    }
}
