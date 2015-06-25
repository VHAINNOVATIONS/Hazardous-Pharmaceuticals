/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Exception for validation errors due to business rules
 */
public class BusinessRuleException extends ValidationException {
    
    /**
     * FIELD_VALIDATION_ERROR.
     */
    public static final MessageKey FIELD_VALIDATION_ERROR = new MessageKey("FIELD_VALIDATION_ERROR");
    
    /**
     * VALIDATION_ERROR.
     */
    public static final MessageKey VALIDATION_ERROR = new MessageKey("VALIDATION_ERROR");
    
    /**
     * COULD_NOT_PROCEED_ERROR.
     */
    public static final MessageKey COULD_NOT_PROCEED_ERROR = new MessageKey("COULD_NOT_PROCEED_ERROR");
    
    /**
     * REQUEST_CONFLICTS.
     */
    public static final MessageKey REQUEST_CONFLICTS = new MessageKey("REQUEST_CONFLICTS");
    
    /**
     * NO_PENDING_REQUESTS.
     */
    public static final MessageKey NO_PENDING_REQUESTS = new MessageKey("NO_PENDING_REQUESTS");
    
    /**
     * REJECT_REASON_REQUIRED.
     */
    public static final MessageKey REJECT_REASON_REQUIRED = new MessageKey("REJECT_REASON_REQUIRED");
    
    /**
     * NO_NEW_DIFFERENCES.
     */
    public static final MessageKey NO_NEW_DIFFERENCES = new MessageKey("NO_NEW_DIFFERENCES");
    
    /**
     * MOD_MUST_BE_APPROVED_REJECTED.
     */
    public static final MessageKey MOD_MUST_BE_APPROVED_REJECTED = new MessageKey("MOD_MUST_BE_APPROVED_REJECTED");
    
    /**
     * CANNOT_ADD.
     */
    public static final MessageKey CANNOT_ADD = new MessageKey("CANNOT_ADD");
    
    /**
     * CANNOT_EDIT.
     */
    public static final MessageKey CANNOT_EDIT = new MessageKey("CANNOT_EDIT");

    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public BusinessRuleException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public BusinessRuleException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public BusinessRuleException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public BusinessRuleException(Exception e, MessageKey key) {
        super(e, key);
    }
}
