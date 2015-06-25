/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Unchecked exception thrown when exceptions from the shared common code.
 */
public class CommonException extends PharmacyRuntimeException {
    
    /**
     * BUILD_DATE_UNVAILABLE.
     */
    public static final MessageKey BUILD_DATE_UNVAILABLE = new MessageKey("BUILD_DATE_UNAVAILABLE");
    
    /**
     * DIFF_NULL_VALUES.
     */
    public static final MessageKey DIFF_NULL_VALUES = new MessageKey("DIFF_NULL_VALUES");
    
    /**
     * DIFF_SAME_CLASS_TYPE.
     */
    public static final MessageKey DIFF_SAME_CLASS_TYPE = new MessageKey("DIFF_SAME_CLASS_TYPE");
    
    /**
     * ILLEGAL_ARGUMENT.
     */
    public static final MessageKey ILLEGAL_ARGUMENT = new MessageKey("ILLEGAL_ARGUMENT");
    
    /**
     * INCORRECT_GROUP_DATA_FIELD.
     */
    public static final MessageKey INCORRECT_GROUP_DATA_FIELD = new MessageKey("INCORRECT_GROUP_DATA_FIELD");
    
    /**
     * INCORRECT_NUMBER_OF_GROUPED_FIELDS.
     */
    public static final MessageKey INCORRECT_NUMBER_OF_GROUPED_FIELDS = new MessageKey("INCORRECT_NUMBER_OF_GROUPED_FIELDS");
    
    /**
     * INVALID_DATA_FIELD_INSTANTIATION.
     */
    public static final MessageKey INVALID_DATA_FIELD_INSTANTIATION = new MessageKey("INVALID_DATA_FIELD_INSTANTIATION");
    
    /**
     * PROPERTY_FILE_UNAVAILABLE.
     */
    public static final MessageKey PROPERTY_FILE_UNAVAILABLE = new MessageKey("PROPERTY_FILE_UNAVAILABLE");
    
    /**
     * RESOURCE_KEY_UNAVAILABLE.
     */
    public static final MessageKey RESOURCE_KEY_UNAVAILABLE = new MessageKey("RESOURCE_KEY_UNAVAILABLE");
    
    /**
     * SERIALIZATION_ERROR.
     */
    public static final MessageKey SERIALIZATION_ERROR = new MessageKey("SERIALIZATION_ERROR");
    
    /**
     * UNKNOWN_DATA_FIELD.
     */
    public static final MessageKey UNKNOWN_DATA_FIELD = new MessageKey("UNKNOWN_DATA_FIELD");
    
    /**
     * UNKNOWN_GROUPED_FIELD.
     */
    public static final MessageKey UNKNOWN_GROUPED_FIELD = new MessageKey("UNKNOWN_GROUPED_FIELD");
    
    /**
     * VALIDATOR_ERROR.
     */
    public static final MessageKey VALIDATOR_ERROR = new MessageKey("VALIDATOR_ERROR");
    
    /**
     * VERSION_PROPERTIES_UNAVAILABLE.
     */
    public static final MessageKey VERSION_PROPERTIES_UNAVAILABLE = new MessageKey("VERSION_PROPERTIES_UNAVAILABLE");
    
    /**
     * UNEXPECTED_EXCEPTION_SEEN.
     */
    public static final MessageKey UNEXPECTED_EXCEPTION_SEEN = new MessageKey("UNEXPECTED_EXCEPTION_SEEN");

    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public CommonException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public CommonException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public CommonException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public CommonException(Exception e, MessageKey key) {
        super(key, e);
    }
}
