/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Unchecked exception thrown when exceptions from third party tool encountered.
 */
public class InterfaceException extends PharmacyRuntimeException {

    /**
     * INTERFACE_UNAVAILABLE
     */
    public static final MessageKey INTERFACE_UNAVAILABLE = new MessageKey("INTERFACE_UNAVAILABLE");
    
    /**
     * INTERFACE_ERROR.
     */
    public static final MessageKey INTERFACE_ERROR = new MessageKey("INTERFACE_ERROR");
    
    /**
     * INTERFACE_NULL_USER.
     */
    public static final MessageKey INTERFACE_NULL_USER = new MessageKey("INTERFACE_NULL_USER");
    
    /**
     * INTERFACE_VISTA_FAILURE.
     */
    public static final MessageKey INTERFACE_VISTA_FAILURE = new MessageKey("INTERFACE_VISTA_FAILURE");
    
    /**
     * INTERFACE_MARSHAL_FAILURE.
     */
    public static final MessageKey INTERFACE_MARSHAL_FAILURE = new MessageKey("INTERFACE_MARSHAL_FAILURE");
    
    /**
     * SEND_TO_VISTA.
     */
    public static final MessageKey SEND_TO_VISTA = new MessageKey("SEND_TO_VISTA");
    
    /**
     * UNKNOWN_STATUS.
     */
    public static final MessageKey UNKNOWN_STATUS = new MessageKey("UNKNOWN_STATUS");

    /**
     * DRUG_DATA_VENDOR.
     */
    public static final String DRUG_DATA_VENDOR = "Drug Data Vendor";
    
    /**
     * PRE_ENCAPSULATION.
     */
    public static final String PRE_ENCAPSULATION = "PRE Encapsulation";
    
    /**
     * MESSAGING_SERVICE.
     */
    public static final String MESSAGING_SERVICE = "Messaging Service";
    
    /**
     * STS_CAPABILITY.
     */
    public static final String STS_CAPABILITY = "STS Capability";

    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public InterfaceException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public InterfaceException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public InterfaceException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public InterfaceException(Exception e, MessageKey key) {
        super(e, key);
    }
}
