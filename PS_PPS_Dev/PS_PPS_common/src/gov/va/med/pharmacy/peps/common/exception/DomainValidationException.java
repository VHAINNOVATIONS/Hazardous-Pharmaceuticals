/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Unchecked exception when validation fails in Domain layer
 */
public class DomainValidationException extends DomainException {
    
    /**
     * VALIDATION_ERROR.
     */
    public static final MessageKey VALIDATION_ERROR = new MessageKey("VALIDATION_ERROR");
    
    /**
     * FIELD_VALIDATION_ERROR.
     */
    public static final MessageKey FIELD_VALIDATION_ERROR = new MessageKey("FIELD_VALIDATION_ERROR");
    
    /**
     * FIELD_FORMAT_ERROR.
     */
    public static final MessageKey FIELD_FORMAT_ERROR = new MessageKey("FIELD_FORMAT_ERROR");

    /**
     * NUMERIC_ERROR.
     */
    public static final MessageKey NUMERIC_ERROR = new MessageKey("NUMERIC_ERROR");

    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public DomainValidationException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public DomainValidationException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public DomainValidationException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public DomainValidationException(Exception e, MessageKey key) {
        super(e, key);
    }
}
