/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Unchecked exception thrown when object/relational mapping tool exceptions encountered.
 */
public class DomainException extends PharmacyRuntimeException {
    
    /**
     * WRAPPED_EXCEPTION.
     */
    public static final MessageKey WRAPPED_EXCEPTION = new MessageKey("WRAPPED_EXCEPTION");
    
    /**
     * DATA_NOT_FOUND.
     */
    public static final MessageKey DATA_NOT_FOUND = new MessageKey("DATA_NOT_FOUND");

    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public DomainException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public DomainException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public DomainException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public DomainException(Exception e, MessageKey key) {
        super(e, key);
    }
}
