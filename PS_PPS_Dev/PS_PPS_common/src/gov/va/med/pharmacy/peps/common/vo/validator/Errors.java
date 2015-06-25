/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * A List of Error objects for a particular ValueObject this is being (or was)
 * validated.
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "request")
public class Errors implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<ValidationError> errors = new ArrayList<ValidationError>();

    /**
     * Add the given error to the List of errors.
     * 
     * @param error
     *            Error to add
     */
    public void addError(ValidationError error) {
        errors.add(error);
    }

    /**
     * Add all of the Error object from the given Errors.
     * 
     * @param myErrors
     *            Errors to add to this Errors' List
     */
    public void addErrors(Errors myErrors) {
        this.errors.addAll(myErrors.getErrors());
    }

    /**
     * Add a new error with the given key and arguments.
     * 
     * @param errorKey
     *            ErrorKey for the new Error
     * @param arguments
     *            Object array as the arguments for the new Error
     */
    public void addError(ErrorKey errorKey, Object... arguments) {
        addError(new ValidationError(errorKey, arguments));
    }

    /**
     * Add a new error with the given key.
     * 
     * @param errorKey
     *            ErrorKey for the new Error
     */
    public void addError(ErrorKey errorKey) {
        addError(new ValidationError(errorKey));
    }

    /**
     * Add a new error with the given field key and error key.
     * 
     * @param fieldKey
     *            FieldKey for which the new Error applies
     * @param errorKey
     *            ErrorKey for the new Error
     */
    public void addError(FieldKey fieldKey, ErrorKey errorKey) {
        addError(new ValidationError(fieldKey, errorKey));
    }

    /**
     * Add a new error with the given field key, error key, and arguments.
     * 
     * @param fieldKey
     *            FieldKey for which the new Error applies
     * @param errorKey
     *            ErrorKey for the new Error
     * @param arguments
     *            Object array as the arguments for the new Error
     */
    public void addError(FieldKey fieldKey, ErrorKey errorKey, Object... arguments) {
        addError(new ValidationError(fieldKey, errorKey, arguments));
    }

    /**
     * Localize each of the Error objects for the default Locale, returning a
     * List of the localized String error messages.
     * 
     * @return List of localized String error messages
     */
    public List<String> getLocalizedErrors() {
        return getLocalizedErrors(Locale.getDefault());
    }

    /**
     * Localize each of the Error objects for the given Locale, returning a List
     * of the localized String error messages.
     * 
     * @param locale
     *            Locale for which to localize the messages
     * @return List of localized String error messages
     */
    public List<String> getLocalizedErrors(Locale locale) {
        List<String> localizedErrors = new ArrayList<String>(errors.size());

        for (ValidationError validationError : errors) {
            localizedErrors.add(validationError.getLocalizedError(locale));
        }

        return localizedErrors;
    }

    /**
     * Localize each of the Error objects for the default Locale, returning a
     * String containing each of the localized String
     * error messages, separated by a the system's default line separator.
     * 
     * @return String of all localized error messages, separated by the system's
     *         default line separator
     */
    public String getLocalizedErrorsAsString() {
        return getLocalizedErrorsAsString(Locale.getDefault());
    }

    /**
     * Localize each of the Error objects for the given Locale, returning a
     * String containing each of the localized String
     * error messages, separated by a the system's default line separator.
     * 
     * @param locale
     *            the Locale for which to localize the errors
     * @return String of all localized error messages, separated by the system's
     *         default line separator
     */
    public String getLocalizedErrorsAsString(Locale locale) {
        StringBuffer message = new StringBuffer();

        for (String localizedError : getLocalizedErrors(locale)) {
            message.append(localizedError + System.getProperty("line.separator"));
        }

        return message.toString();
    }

    /**
     * Get the errors attributed to the given FieldKey.
     * 
     * @param fieldKey
     *            FieldKey to get errors for
     * @return Errors for given FieldKey, may be an empty list if there were no
     *         errors for given FieldKey
     */
    public Errors getErrors(FieldKey fieldKey) {
        Errors fieldErrors = new Errors();

        for (ValidationError error : errors) {
            if (fieldKey.equals(error.getFieldKey())) {
                fieldErrors.addError(error);
            }
        }

        return fieldErrors;
    }

    /**
     * Get the errors with the given ErrorKey.
     * 
     * @param errorKey
     *            ErrorKey to get errors for
     * @return Errors for given ErrorKey, may be an empty list if there were no
     *         errors for given ErrorKey
     */
    public Errors getErrors(ErrorKey errorKey) {
        Errors fieldErrors = new Errors();

        for (ValidationError error : errors) {
            if (errorKey.equals(error.getErrorKey())) {
                fieldErrors.addError(error);
            }
        }

        return fieldErrors;
    }

    /**
     * Get the errors attributed to the given FieldKey with the given ErrorKey.
     * 
     * @param errorKey
     *            ErrorKey to get errors for
     * @param fieldKey
     *            FieldKey to get errors for
     * @return Errors for given ErrorKey, may be an empty list if there were no
     *         errors for given ErrorKey
     */
    public Errors getErrors(ErrorKey errorKey, FieldKey fieldKey) {
        Errors fieldErrors = new Errors();

        for (ValidationError error : errors) {
            if (errorKey.equals(error.getErrorKey()) && fieldKey.equals(error.getFieldKey())) {
                fieldErrors.addError(error);
            }
        }

        return fieldErrors;
    }

    /**
     * Check if this Errors object has any Error instances within its List.
     * 
     * @return boolean true if the List of errors is not empty, else return
     *         false
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Check if this {@link Errors} object has any {@link ErrorKey} instances
     * within its list.
     * 
     * @param errorKey
     *            {@link ErrorKey} to look for
     * @return boolean true if this {@link Errors} object contains an error for
     *         {@link ErrorKey}
     */
    public boolean hasError(ErrorKey errorKey) {
        Errors myErrors = getErrors(errorKey);

        return myErrors.hasErrors();
    }

    /**
     * Get the size of the List of Errors. If the Errors List is null, return
     * zero (0).
     * 
     * @return int the size of the errors List.
     */
    public int size() {
        if (errors == null) {
            return 0;
        } else {
            return errors.size();
        }
    }

    /**
     * getErrors
     * @return errors List property
     */
    public List<ValidationError> getErrors() {
        return errors;
    }

    /**
     * Equals returned by Jakarta Commons EqualsBuilder
     * 
     * @param obj used in the equals comaprison
     * @return true if equal
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
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
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * toString returned by Jakarta Commons ToStringBuilder for Errors
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
