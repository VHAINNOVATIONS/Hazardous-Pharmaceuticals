/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


import java.text.MessageFormat;
import java.util.Locale;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.ResourceBundleUtility;


/**
 * Pharmacy unchecked exception base class. Intended for use where cause of exception is unrecoverable.
 */
public class PharmacyRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(PharmacyRuntimeException.class);

    private MessageKey key;
    private Object[] arguments;

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param key String key to the exception message
     */
    protected PharmacyRuntimeException(MessageKey key) {
        this.key = key;
        this.arguments = new Object[] {};

        log();
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    protected PharmacyRuntimeException(MessageKey key, Object... arguments) {
        this.key = key;
        this.arguments = arguments;

        log();
    }

    /**
     * Create a new exception with a no-argument message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     */
    protected PharmacyRuntimeException(Exception e, MessageKey key) {
        super(e);

        this.key = key;
        this.arguments = new Object[] {};

        log();
    }

    /**
     * Create a new exception with a parameterized message.
     * 
     * @param e Exception that caused this exception
     * @param key String key to the exception message
     * @param arguments Arguments to insert into the message
     */
    protected PharmacyRuntimeException(Exception e, MessageKey key, Object... arguments) {
        super(e);

        this.key = key;
        this.arguments = arguments;

        log();
    }

    /**
     * Return the message localized for the default locale. The localized message is derived from the ExceptionMessage
     * provided in the constructor.
     * 
     * @return String localized message
     * 
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    public String getLocalizedMessage() {
        
        // This is the defaults local message
        return getLocalizedMessage(Locale.getDefault());
    }

    /**
     * Return the message localized for this Locale. The localized message is 
     * derived from the ExceptionMessage provided in the constructor.
     * 
     * @param locale the Locale for which to localize the message
     * @return String localized message
     * 
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    public String getLocalizedMessage(Locale locale) {
        String message = ResourceBundleUtility.getResourceBundleValue(getClass(), key.getKey(), locale);

        // ResourceBundleUtility returns the key if the localized message could not be found
        if (!key.getKey().equals(message)) {
            message = MessageFormat.format(message, arguments);
        }

        return message;
    }

    /**
     * Return the message localized for the default locale. The localized message is derived from the ExceptionMessage
     * provided in the constructor.
     * 
     * @return String localized message
     * 
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {
        return getLocalizedMessage();
    }

    /**
     * Log the message via Log4j
     */
    private void log() {
        LOG.error("", this);
    }
}
