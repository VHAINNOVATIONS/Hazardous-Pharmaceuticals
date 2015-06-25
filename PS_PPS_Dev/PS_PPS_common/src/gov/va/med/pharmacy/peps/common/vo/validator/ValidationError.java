/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import gov.va.med.pharmacy.peps.common.utility.ResourceBundleUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * A validation error for a particular field or ValueObject.
 */
public class ValidationError implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String FIELD_ERROR_RESOURCE_BUNDLE = "properties/validation/FieldError";
    private ErrorKey errorKey;
    private Object[] arguments;
    private FieldKey fieldKey;
    private boolean fieldError;

    /**
     * Set the key. This Error will have no arguments filled in.
     * 
     * @param key ErrorKey to localize this message
     */
    public ValidationError(ErrorKey key) {

        this(key, new Object[0]);
    }

    /**
     * Set the field key and the error key. This Error will have no arguments filled in.
     * 
     * @param fieldKey DataFieldKey for which this error applies
     * @param key ErrorKey to localize this error message
     */
    public ValidationError(FieldKey fieldKey, ErrorKey key) {

        this(fieldKey, key, new Object[0]);
    }

    /**
     * Set the key and the arguments.
     * 
     * @param key ErrorKey to localize this error message
     * @param arguments Object array to insert into localized message
     */
    public ValidationError(ErrorKey key, Object... arguments) {

        this.fieldKey = null;
        this.fieldError = false;
        this.errorKey = key;
        this.arguments = arguments;
    }

    /**
     * Set the field key, error key and the arguments.
     * 
     * @param fieldKey DataFieldKey for which this error applies
     * @param key ErrorKey to localize this error message
     * @param arguments Object array to insert into localized message
     */
    public ValidationError(FieldKey fieldKey, ErrorKey key, Object... arguments) {

        this.fieldKey = fieldKey;
        this.fieldError = true;
        this.errorKey = key;
        this.arguments = arguments;
    }

    /**
     * Localize the error message for the default Locale, inserting the arguments if there are any. Currently, the arguments
     * are not localized.
     * 
     * @return String localized error message
     */
    public String getLocalizedError() {

        return getLocalizedError(Locale.getDefault());
    }

    /**
     * Localize the error message for the given Locale, inserting the arguments if there are any.
     * 
     * @param locale the Locale for which to localize the error
     * @return String localized error message
     */
    public String getLocalizedError(Locale locale) {

        return getLocalizedError(locale, null);
    }

    /**
     * Localize the error message for the given Locale, inserting the arguments if there are any.
     * 
     * @param locale the Locale for which to localize the error
     * @param type EntityType for which to localize arguments
     * @return String localized error message
     */
    public String getLocalizedError(Locale locale, EntityType type) {

        String localizedError = errorKey.getLocalizedError(locale);

        if (arguments != null && arguments.length > 0) {
            Object[] localizedArguments = localizeArguments(locale, type);
            localizedError = MessageFormat.format(localizedError, localizedArguments);
        }

        return localizedError;
    }

    /**
     * If any of the arguments are an instance of FieldKey, get the localized name for the argument. Else, if any of the
     * arguments are found in the FieldError.properties resource bundle, localize the name for the key. Otherwise, just use
     * the argument passed in. In this case, the argument will not be localized.
     * 
     * @param locale the Locale for which to localize the arguments
     * @param type the EntityType of the error, for fieldKey localization
     * @return Object array of localized String arguments
     */
    private Object[] localizeArguments(Locale locale, EntityType type) {

        Object[] localizedArguments = new Object[arguments.length];

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof FieldKey) {
                FieldKey argument = (FieldKey) arguments[i];

                if (type == null) {
                    localizedArguments[i] = argument.getLocalizedName(locale);
                } else {
                    localizedArguments[i] = argument.getLocalizedName(locale, type);
                }
            } else if (arguments[i] instanceof String) {
                localizedArguments[i] = ResourceBundleUtility.getResourceBundleValue(FIELD_ERROR_RESOURCE_BUNDLE,
                    (String) arguments[i], locale);
            } else {
                localizedArguments[i] = arguments[i].toString();  // toString added to support doubles
            }
        }

        return localizedArguments;
    }

    /**
     * getFieldKey
     * 
     * @return DataFieldKey for which this error applies
     */
    public FieldKey getFieldKey() {

        return fieldKey;
    }

    /**
     * getErrorKey
     * 
     * @return ErrorKey for this error
     */
    public ErrorKey getErrorKey() {

        return errorKey;
    }

    /**
     * Equals returned by Jakarta Commons EqualsBuilder
     * 
     * @param obj Object to check equals against
     * @return true if equal
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * HashCode built by Jakarta Commons HasCodeBuilder
     * 
     * @return int hash
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * toString returned by Jakarta Commons ToStringBuilder for the ValidationError class
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * isFieldError
     * 
     * @return fieldError property
     */
    public boolean isFieldError() {

        return fieldError;
    }

    /**
     * setFieldError
     * 
     * @param fieldError fieldError property
     */
    public void setFieldError(boolean fieldError) {

        this.fieldError = fieldError;
    }
}
