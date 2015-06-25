/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Checked exception thrown when trying to add an item that already exists
 */
public class DuplicateItemException extends ValidationException {
    
    /**
     * DUPLICATE_ITEM.
     */
    public static final MessageKey DUPLICATE_ITEM = new MessageKey("DUPLICATE_ITEM");
    
    /**
     * OI_DUPLICATE_ITEM.
     */
    public static final MessageKey OI_DUPLICATE_ITEM = new MessageKey("OI_DUPLICATE_ITEM");
    
    /**
     * NDC_DUPLICATE_ITEM.
     */
    public static final MessageKey NDC_DUPLICATE_ITEM = new MessageKey("NDC_DUPLICATE_ITEM");
    
    /**
     * PRODUCT_DUPLICATE_ITEM.
     */
    public static final MessageKey PRODUCT_DUPLICATE_ITEM = new MessageKey("PRODUCT_DUPLICATE_ITEM");
    
    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public DuplicateItemException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public DuplicateItemException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public DuplicateItemException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public DuplicateItemException(Exception e, MessageKey key) {
        super(e, key);
    }
}
