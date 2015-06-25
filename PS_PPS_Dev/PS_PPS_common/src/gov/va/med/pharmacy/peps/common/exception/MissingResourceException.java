/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Exception thrown when a resource (for instance, file) could not be found as the name/id is not valid.
 */
public class MissingResourceException extends ValidationException {
    
    /**
     * MISSING_RESOURCE.
     */
    public static final MessageKey MISSING_RESOURCE = new MessageKey("MISSING_RESOURCE");
    
    /**
     * SEARCH_TEMPLATE_NOT_FOUND.
     */
    public static final MessageKey SEARCH_TEMPLATE_NOT_FOUND = new MessageKey("SEARCH_TEMPLATE_NOT_FOUND");
    
    /**
     * UNABLE_TO_SAVE_SEARCH_TEMPLATE.
     */
    public static final MessageKey UNABLE_TO_SAVE_SEARCH_TEMPLATE = new MessageKey("UNABLE_TO_SAVE_SEARCH_TEMPLATE");
    
    /**
     * USER_SEARCH_TEMPLATES_NOT_FOUND.
     */
    public static final MessageKey USER_SEARCH_TEMPLATES_NOT_FOUND = new MessageKey("USER_SEARCH_TEMPLATES_NOT_FOUND");
    
    /**
     * NO_PERMISSIONS.
     */
    public static final MessageKey NO_PERMISSIONS = new MessageKey("NO_PERMISSIONS");
    
    /**
     * SEARCH_TEMPLATE_UNNAMED.
     */
    public static final MessageKey SEARCH_TEMPLATE_UNNAMED = new MessageKey("SEARCH_TEMPLATE_UNNAMED");
    
    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public MissingResourceException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public MissingResourceException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public MissingResourceException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public MissingResourceException(Exception e, MessageKey key) {
        super(e, key);
    }
}
