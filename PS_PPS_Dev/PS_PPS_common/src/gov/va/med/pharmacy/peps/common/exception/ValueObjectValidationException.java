/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


import java.util.Locale;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Exception thrown during value object data field validation
 */
public class ValueObjectValidationException extends ValidationException {
    
    /**
     * VALIDATION_ERROR.
     */
    public static final MessageKey VALIDATION_ERROR = new MessageKey("VALIDATION_ERROR");

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ValueObjectValidationException.class);
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private Errors errors;

    /**
     * Provide the errors with the validation
     * 
     * @param errors Errors object detailing the validation errors
     */
    public ValueObjectValidationException(Errors errors) {
        super(VALIDATION_ERROR);

        this.errors = errors;

        LOG.info("", this);
    }

    /**
     * Return the message localized for the default locale. The localized message is derived from the ExceptionMessage
     * provided in the constructor.
     * 
     * @return String localized message
     * 
     * @see gov.va.med.pharmacy.peps.common.exception.PharmacyException#getLocalizedMessage()
     */
    public String getLocalizedMessage() {
        return getLocalizedMessage(Locale.getDefault());
    }

    /**
     * Return the message localized for the given Locale. The localized message is derived from the ExceptionMessage provided
     * in the constructor.
     * 
     * @param locale the Locale for which to localize the message
     * @return String localized message
     * 
     * @see gov.va.med.pharmacy.peps.common.exception.PharmacyException#getLocalizedMessage(Locale)
     */
    public String getLocalizedMessage(Locale locale) {
        if (errors == null) {        
            return super.getLocalizedMessage(locale);
        } else {
            return super.getLocalizedMessage(locale) 
                + LINE_SEPARATOR 
                + errors.getLocalizedErrorsAsString(locale);
        }
    }

    /**
     * Override super's log method to not log during construction
     * 
     * @see gov.va.med.pharmacy.peps.common.exception.PharmacyException#log()
     */
    protected void log() {

        // called in super's constructor, but cannot log there because errors object has not yet been set
    }

    /**
     * The errors which caused this exception to be thrown.
     * 
     * @return Errors
     */
    public Errors getErrors() {
        return errors;
    }
}
