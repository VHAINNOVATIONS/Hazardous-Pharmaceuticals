/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Exception thrown during validation
 */
public class ValidationException extends PharmacyException {
    
    /**
     * INVALID_MONOGRAPH.
     */
    public static final MessageKey INVALID_MONOGRAPH = new MessageKey("INVALID_MONOGRAPH");
    
    /**
     * INACTIVATE_MANUFACTURER
     */
    public static final MessageKey INACTIVATE_MANUFACTURER = new MessageKey("INACTIVATE_MANUFACTURER");
    
    /**
     * INACTIVATE_PACKAGETYPE
     */
    public static final MessageKey INACTIVATE_PACKAGETYPE = new MessageKey("INACTIVATE_PACKAGETYPE");
    
    /**
     * INACTIVATE_ORDERABLEITEM
     */
    public static final MessageKey INACTIVATE_ORDERABLEITEM = new MessageKey("INACTIVATE_ORDERABLEITEM");
    
    /**
     * INACTIVATE_DOSAGEFORM
     */
    public static final MessageKey INACTIVATE_DOSAGEFORM = new MessageKey("INACTIVATE_DOSAGEFORM");
    
    /**
     * INACTIVATE_GENERICNAME
     */
    public static final MessageKey INACTIVATE_GENERICNAME = new MessageKey("INACTIVATE_GENERICNAME");
    
    /**
     * INACTIVATE_INGREDIENT
     */
    public static final MessageKey INACTIVATE_INGREDIENT = new MessageKey("INACTIVATE_INGREDIENT");
    
    /**
     * INACTIVATE_INGREDIENT
     */
    public static final MessageKey INACTIVATE_INGREDIENT_PRIMARY = new MessageKey("INACTIVATE_INGREDIENT_PRIMARY");

    
    /**
     * INACTIVATE_DISPENSEUNIT
     */
    public static final MessageKey INACTIVATE_DISPENSEUNIT = new MessageKey("INACTIVATE_DISPENSEUNIT");
    
    /**
     * INACTIVATE_DRUGTEXT_OI
     */
    public static final MessageKey INACTIVATE_DRUGTEXT_OI = new MessageKey("INACTIVATE_DRUGTEXT_OI");
    
    /**
     * INACTIVATE_DRUGTEXT_PROD
     */
    public static final MessageKey INACTIVATE_DRUGTEXT_PROD = new MessageKey("INACTIVATE_DRUGTEXT_PROD");
    
    /**
     * INACTIVATE_ORDERUNIT_PROD
     */
    public static final MessageKey INACTIVATE_ORDERUNIT_PROD = new MessageKey("INACTIVATE_ORDERUNIT_PROD");
    
    /**
     * INACTIVATE_ORDERUNIT_NDC
     */
    public static final MessageKey INACTIVATE_ORDERUNIT_NDC = new MessageKey("INACTIVATE_ORDERUNIT_NDC");
    
    /**
     * INACTIVATE_DRUGUNIT_ASSOC
     */
    public static final MessageKey INACTIVATE_DRUGUNIT_ASSOC = new MessageKey("INACTIVATE_DRUGUNIT_ASSOC");
    
    /**
     * INACTIVATE_DRUGUNIT_PROD
     */
    public static final MessageKey INACTIVATE_DRUGUNIT_PROD = new MessageKey("INACTIVATE_DRUGUNIT_PROD");
    
    /**
     * INACTIVATE_DRUGUNIT_DF
     */
    public static final MessageKey INACTIVATE_DRUGUNIT_DF = new MessageKey("INACTIVATE_DRUGUNIT_DF");

    /**
     * SEND_ITEM_TO_VISTA
     */
    public static final MessageKey SEND_ITEM_TO_VISTA = new MessageKey("SEND_ITEM_TO_VISTA");
    
    /**
     * VISTA_COMM_TURNED_OFF
     */
    public static final MessageKey VISTA_COMM_TURNED_OFF = new MessageKey("VISTA_COMM_TURNED_OFF");

    /**
     * VISTA_XML_MARSHALLING
     */
    public static final MessageKey VISTA_XML_MARSHALLING = new MessageKey("VISTA_XML_MARSHALLING");
    
    /**
     * CANNOT_DELETE
     */
    public static final MessageKey CANNOT_DELETE = new MessageKey("CANNOT_DELETE");

    /** NO_NDCS_SELECTED */
    public static final MessageKey NO_NDCS_SELECTED = new MessageKey("NO_NDCS_SELECTED");
    
    /** INVALID HEADER */
    public static final MessageKey INVALID_HEADER = new MessageKey("INVALID_HEADER");

    
    
    private static final long serialVersionUID = 1L;
    

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public ValidationException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public ValidationException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public ValidationException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public ValidationException(Exception e, MessageKey key) {
        super(e, key);
    }
}
