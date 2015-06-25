/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * Checked exception thrown when trying to add an item that already exists
 */
public class OptimisticLockingException extends ValidationException {
    
    /**
     * OPTIMISTIC_LOCKING_FAILED.
     */
    public static final MessageKey OPTIMISTIC_LOCKING_FAILED = new MessageKey("OPTIMISTIC_LOCKING_FAILED");

    /** ITEM_MODIFIED_BY_ANOTHER_USER */
    public static final MessageKey ITEM_MODIFIED_BY_ANOTHER_USER = new MessageKey("ITEM_MODIFIED_BY_ANOTHER_USER");

    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    public OptimisticLockingException(MessageKey key) {
        super(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public OptimisticLockingException(MessageKey key, Object... arguments) {
        super(key, arguments);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    public OptimisticLockingException(Exception e, MessageKey key, Object... arguments) {
        super(e, key, arguments);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    public OptimisticLockingException(Exception e, MessageKey key) {
        super(e, key);
    }
}
