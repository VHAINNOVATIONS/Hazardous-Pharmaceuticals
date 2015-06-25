/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


import java.text.MessageFormat;
import java.util.Locale;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.ResourceBundleUtility;


/**
 * Pharmacy checked exception base class. Intended for use where cause of exception is recoverable.
 */
public class PharmacyException extends Exception {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(PharmacyException.class);

    private MessageKey key;
    private Object[] arguments;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    protected PharmacyException(MessageKey key) {
        this.key = key;
        this.arguments = new Object[] {};

        LOG.error(key);
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    protected PharmacyException(MessageKey key, Object... arguments) {
        this.key = key;
        this.arguments = arguments;

        LOG.error(key);
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param cause Exception that caused this exception
     * @param key String key to the exception message
     */
    protected PharmacyException(Exception cause, MessageKey key) {
        super(cause);

        this.key = key;
        this.arguments = new Object[] {};

    
        LOG.error(key);
        
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param cause Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    protected PharmacyException(Exception cause, MessageKey key, Object... arguments) {
        super(cause);

        this.key = key;
        this.arguments = arguments;

        LOG.error(key);
    }

    /**
     * Return the message localized for the default locale as instructed. 
     * The localized message is derived from the ExceptionMessage provided in the constructor.
     * 
     * @return String localized message
     */
    public String getLocalizedMessage() {
        
        // return the localized mesage
        return getLocalizedMessage(Locale.getDefault());
    }

    /**
     * Return the message localized for the the Locale that is in question. 
     * The localized message is derived from the ExceptionMessage provided
     * in the constructor.
     * 
     * @param locale the Locale for which to localize the message
     * @return String localized message
     * 
     */
    public String getLocalizedMessage(Locale locale) {
        String message = ResourceBundleUtility.getResourceBundleValue(getClass(), key.getKey(), locale);

        // ResourceBundleUtility returns the key if the localized message could not be found if necessary
        if (!key.getKey().equals(message)) {
            message = MessageFormat.format(message, arguments);
        }

        return message;
    }

    /**
     * The getMessage method return the message localized for the default locale. The localized message is derived 
     * from the ExceptionMessage provided in the constructor.
     * 
     * @return String localized message
     * 
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {
        return getLocalizedMessage();
    }

}
