/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.DateFormatUtility;
import gov.va.med.pharmacy.peps.common.utility.NumberFormatUtility;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Class that validates a given ValueObject for correct data values. The AbstractValidator class provides common
 * implementations for the supports() and isEmptyString() methods used by many or all Validators.
 * 
 * @param <T> Type of object being validated
 */
public abstract class AbstractValidator<T> {

    /**
     * length arguments used in min/max range function calls
     */

    /** NUM_0 */
    protected static final int NUM_0 = 0;

    /** NUM_1 */
    protected static final int NUM_1 = 1;

    /** NUM_2 */
    protected static final int NUM_2 = 2;

    /** NUM_3 */
    protected static final int NUM_3 = 3;

    /** NUM_4 */
    protected static final int NUM_4 = 4;

    /** NUM_5 */
    protected static final int NUM_5 = 5;

    /** NUM_6 */
    protected static final int NUM_6 = 6;

    /** NUM_7 */
    protected static final int NUM_7 = 7;

    /** NUM_8 */
    protected static final int NUM_8 = 8;

    /** NUM_9 */
    protected static final int NUM_9 = 9;

    /** NUM_10 */
    protected static final int NUM_10 = 10;

    /** NUM_15 */
    protected static final int NUM_15 = 15;

    /** NUM_25 */
    protected static final int NUM_25 = 25;

    /** NUM_20 */
    protected static final int NUM_20 = 20;

    /** NUM_30 */
    protected static final int NUM_30 = 30;

    /** NUM_40 */
    protected static final int NUM_40 = 40;

    /** NUM_50 */
    protected static final int NUM_50 = 50;

    /** NUM_100 */
    protected static final int NUM_100 = 100;

    /** NUM_119 */
    protected static final int NUM_119 = 119;

    /** NUM_250 */
    protected static final int NUM_250 = 250;

    /** NUM_255 */
    protected static final int NUM_255 = 255;

    /** NUM_280 */
    protected static final int NUM_280 = 280;

    /** NUM_800 */
    protected static final int NUM_800 = 800;

    /** NUM_9999 */
    protected static final int NUM_9999 = 9999;

    /** NUM_999999 */
    protected static final int NUM_999999 = 999999;

    /** NUM_999999999 */
    protected static final int NUM_999999999 = 999999999;


    private static final String PATTERN1 = "^\\d+(\\.\\d{1,";
    private static final String PATTERN2 = "})?$";

    /**
     * Add an error for the given {@link FieldKey} to the given {@link Errors} if the given double value has more decimal
     * digits than the given maxDecimals.
     * 
     * @param value double value to test
     * @param maxDecimals maximum number of decimal digits
     * @param errors Errors to add new error to
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfMoreDecimals(Double value, int maxDecimals, Errors errors, FieldKey fieldKey) {
        Pattern pattern = Pattern.compile(PATTERN1 + maxDecimals + PATTERN2);
        Matcher matcher = pattern.matcher(NumberFormatUtility.format(value));

        if (!matcher.matches()) {
            errors.addError(ErrorKey.COMMON_INCORRECT_DECIMAL_DIGITS, fieldKey, maxDecimals);
        }
    }

    /**
     * Add an error for the given {@link FieldKey} contained within the given group {@link FieldKey} to the given
     * {@link Errors} if the given double value has more decimal digits than the given maxDecimals.
     * 
     * @param value double value to test
     * @param maxDecimals maximum number of decimal digits
     * @param errors Errors to add new error to
     * @param fieldKey FieldKey for the field being checked
     * @param groupFieldKey FieldKey for the group which contains the given fieldKey
     */
    protected final void rejectIfMoreDecimals(Double value, int maxDecimals, Errors errors, FieldKey fieldKey,
                                              FieldKey groupFieldKey) {
        Pattern pattern = Pattern.compile(PATTERN1 + maxDecimals + PATTERN2);
        Matcher matcher = pattern.matcher(NumberFormatUtility.format(value));

        if (!matcher.matches()) {
            errors.addError(ErrorKey.COMMON_GROUPLIST_INCORRECT_DECIMAL_DIGITS, fieldKey, groupFieldKey, maxDecimals);
        }
    }

    /**
     * Add an error for the given fieldName to the given errors instance if the given boolean value is true.
     * 
     * @param value boolean true/false
     * @param errorKey ErrorKey to add
     * @param errors Errors to add new error to
     */
    protected final void rejectIfTrue(boolean value, ErrorKey errorKey, Errors errors) {
        if (value) {
            errors.addError(errorKey);
        }
    }

    /**
     * Add an error for the given fieldName to the given errors instance if the given boolean value is false.
     * 
     * @param value boolean true/false
     * @param errorKey ErrorKey to add
     * @param errors Errors to add new error to
     */
    protected final void rejectIfFalse(boolean value, ErrorKey errorKey, Errors errors) {
        rejectIfTrue(!value, errorKey, errors);
    }

    /**
     * Add an error for the given fieldName to the given errors instance if the given boolean value is true.
     * 
     * @param value boolean true/false
     * @param errorKey ErrorKey to add
     * @param errors Errors to add new error to
     * @param arguments Object array to use as arguments into the message (or null)
     */
    protected final void rejectIfTrue(boolean value, ErrorKey errorKey, Errors errors, Object... arguments) {
        if (value) {
            if (arguments != null && arguments.length > 0) {
                errors.addError(errorKey, arguments);
            } else {
                errors.addError(errorKey);
            }
        }
    }

    /**
     * Add an error for the given fieldName to the given errors instance if the given boolean value is false.
     * 
     * @param value boolean true/false
     * @param errorKey ErrorKey to add
     * @param errors Errors to add new error to
     * @param arguments Object array to use as arguments into the message (or null)
     */
    protected final void rejectIfFalse(boolean value, ErrorKey errorKey, Errors errors, Object... arguments) {
        rejectIfTrue(!value, errorKey, errors, arguments);
    }

    /**
     * Add an error for the given fieldName to the given errors instance if the given boolean value is true.
     * 
     * @param value boolean true/false
     * @param errorKey ErrorKey to add
     * @param errors Errors to add new error to
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfTrue(boolean value, ErrorKey errorKey, Errors errors, FieldKey fieldKey) {
        rejectIfTrue(value, errorKey, errors, fieldKey, new Object[] {});
    }

    /**
     * Add an error for the given fieldName to the given errors instance if the given boolean value is false.
     * 
     * @param value boolean true/false
     * @param errorKey ErrorKey to add
     * @param errors Errors to add new error to
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfFalse(boolean value, ErrorKey errorKey, Errors errors, FieldKey fieldKey) {
        rejectIfFalse(value, errorKey, errors, fieldKey, new Object[] {});
    }

    /**
     * Add an error for the given fieldName to the given errors instance if the given boolean value is false.
     * 
     * @param value boolean true/false
     * @param errorKey ErrorKey to add
     * @param errors Errors to add new error to
     * @param groupKey FieldKey of the group the given fieldKey is in
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfFalse(boolean value, ErrorKey errorKey, Errors errors, FieldKey groupKey, FieldKey fieldKey) {
        rejectIfFalse(value, errorKey, errors, fieldKey, new Object[] {groupKey, fieldKey});
    }

    /**
     * Add an error for the given fieldName to the given errors instance if the given boolean value is true.
     * 
     * @param value boolean true/false
     * @param errorKey ErrorKey to add
     * @param errors Errors to add new error to
     * @param fieldKey FieldKey for the field being checked
     * @param arguments Object array to use as arguments into the message (or null)
     */
    protected final void rejectIfTrue(boolean value, ErrorKey errorKey, Errors errors, FieldKey fieldKey,
                                      Object... arguments) {
        if (value) {
            int argSize = 1;

            if (arguments != null) {
                argSize += arguments.length;
            }

            Object[] args = new Object[argSize];
            args[0] = fieldKey;

            if (arguments != null) {
                System.arraycopy(arguments, 0, args, 1, arguments.length);
                
            //    for (int i = 0; i < arguments.length; i++) {
            //        args[i + 1] = arguments[i];
            //    }
            }

            errors.addError(fieldKey, errorKey, args);
        }
    }

    /**
     * Add an error for the given fieldName to the given errors instance if the given boolean value is false.
     * 
     * @param value boolean true/false
     * @param errorKey ErrorKey to add
     * @param errors Errors to add new error to
     * @param fieldKey FieldKey for the field being checked
     * @param arguments Object array to use as arguments into the message (or null)
     */
    protected final void rejectIfFalse(boolean value, ErrorKey errorKey, Errors errors, FieldKey fieldKey,
                                       Object... arguments) {
        rejectIfTrue(!value, errorKey, errors, fieldKey, arguments);
    }

    /**
     * Add an error for the given fieldName to the given errors instance if the given boolean value is true.
     * 
     * @param value boolean true/false
     * @param errorKey ErrorKey to add
     * @param errors Errors to add new error to
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfTrue(boolean value, ErrorKey errorKey, Errors errors, String fieldName) {
        rejectIfTrue(value, errorKey, errors, fieldName, new Object[] {});
    }

    /**
     * Add an error for the given fieldName to the given errors instance if the given boolean value is false.
     * 
     * @param value boolean true/false
     * @param errorKey ErrorKey to add
     * @param errors Errors to add new error to
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfFalse(boolean value, ErrorKey errorKey, Errors errors, String fieldName) {
        rejectIfFalse(value, errorKey, errors, fieldName, new Object[] {});
    }

    /**
     * Add an error for the given fieldName to the given errors instance if the given boolean value is true.
     * 
     * @param value boolean true/false
     * @param errorKey ErrorKey to add
     * @param errors Errors to add new error to
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     * @param arguments Object array to use as arguments into the message (or null)
     */
    protected final void rejectIfTrue(boolean value, ErrorKey errorKey, Errors errors, String fieldName, Object... arguments) {
        if (value) {
            int argSize = 1;

            if (arguments != null) {
                argSize += arguments.length;
            }

            Object[] args = new Object[argSize];
            args[0] = fieldName;

            if (arguments != null) {
                System.arraycopy(arguments, 0, args, 1, arguments.length);
                
                //for (int i = 0; i < arguments.length; i++) {
                //    args[i + 1] = arguments[i];
               // }
            }

            errors.addError(errorKey, args);
        }
    }

    /**
     * Add an error for the given fieldName to the given errors instance if the given boolean value is false.
     * 
     * @param value boolean true/false
     * @param errorKey ErrorKey to add
     * @param errors Errors to add new error to
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     * @param arguments Object array to use as arguments into the message (or null)
     */
    protected final void rejectIfFalse(boolean value, ErrorKey errorKey, Errors errors, String fieldName,
                                       Object... arguments) {
        rejectIfTrue(!value, errorKey, errors, fieldName, arguments);
    }

    /**
     * Returns true if the given string is null, or if its trimmed value has a zero (0) length.
     * 
     * @param value String to check
     * @return boolean true if the given string is null or its trimmed value has a zero (0) length
     */
    protected final boolean isNullOrEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    /**
     * If the given String value is null or if its trimmed value has a zero (0) length, add an error to the given Errors.
     * 
     * @param value String to check
     * @param errors Errors to add to if the given value is null or empty
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfNullOrEmpty(String value, Errors errors, String fieldName) {
        rejectIfTrue(isNullOrEmpty(value), ErrorKey.COMMON_EMPTY, errors, fieldName);
    }

    /**
     * If the given String value is null or if its trimmed value has a zero (0) length, add an error to the given Errors.
     * 
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value String to check
     * @param errors Errors to add to if the given value is null or empty
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfNullOrEmpty(String value, Errors errors, FieldKey fieldKey) {
        rejectIfTrue(isNullOrEmpty(value), ErrorKey.COMMON_EMPTY, errors, fieldKey);
    }

    /**
     * Returns true if the given Collection is null, or if its size is zero (0).
     * 
     * @param value Collection to check
     * @return boolean true if the given Collection is null or its size is zero (0)
     */
    protected final boolean isNullOrEmpty(Collection value) {
        return value == null || value.isEmpty();
    }

    /**
     * If the given Collection value is null or if its size is zero (0), add an error to the given Errors.
     * 
     * @param value Collection to check
     * @param errors Errors to add to if the given value is null or empty
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfNullOrEmpty(Collection value, Errors errors, String fieldName) {
        rejectIfTrue(isNullOrEmpty(value), ErrorKey.COMMON_EMPTY, errors, fieldName);
    }

    /**
     * If the given Collection value is null or if its size is zero (0), add an error to the given Errors.
     * 
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value Collection to check
     * @param errors Errors to add to if the given value is null or empty
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfNullOrEmpty(Collection value, Errors errors, FieldKey fieldKey) {
        rejectIfTrue(isNullOrEmpty(value), ErrorKey.COMMON_EMPTY, errors, fieldKey);
    }

    /**
     * Returns true if the given Object is null.
     * 
     * @param value Object to check
     * @return boolean true if the given Object is null
     */
    protected final boolean isNull(Object value) {
        return value == null;
    }

    /**
     * If the given Object value is null, add an error to the given Errors.
     * 
     * @param value Object to check
     * @param errors Errors to add to if the given value is null or empty
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfNull(Object value, Errors errors, String fieldName) {
        rejectIfTrue(isNull(value), ErrorKey.COMMON_EMPTY, errors, fieldName);
    }

    /**
     * If the given Object value is null, add an error to the given Errors.
     * 
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value Object to check
     * @param errors Errors to add to if the given value is null or empty
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfNull(Object value, Errors errors, FieldKey fieldKey) {
        rejectIfTrue(isNull(value), ErrorKey.COMMON_EMPTY, errors, fieldKey);
    }

    /**
     * Check if the given String starts with punctuation.
     * 
     * @param value String to check
     * @return boolean true if the value starts with punctuation.
     */
    protected final boolean startsWithPunctuation(String value) {
        if (value == null || value.equals("")) {
            return false;
        }

        return !Character.isLetterOrDigit(value.charAt(0));
    }

    /**
     * If the given String value is null or if its trimmed value has a zero (0) length, add an error to the given Errors.
     * 
     * @param value String to check
     * @param errors Errors to add to if the given value is null or empty
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfStartsWithPunctuation(String value, Errors errors, FieldKey fieldName) {
        rejectIfTrue(startsWithPunctuation(value),
                     ErrorKey.COMMON_STARTS_WITH_PUNCTUATION, errors, fieldName);
    }

    /**
     * Check if the given String value is not null and if its trimmed value is longer than the given maximum length.
     * 
     * @param value String to check
     * @param maxLength int maximum length for the given String
     * @return boolean true if the value is not null and the trimmed length is greater than the maxLength, else false
     */
    protected final boolean isLongerThanMax(String value, int maxLength) {
        return value != null && value.trim().length() > maxLength;
    }

    /**
     * If the given String value is null or is its trimmed value is longer than the given maximum length, add an error to the
     * given Errors.
     * 
     * @param value String to check
     * @param maxLength int maximum length for the given String
     * @param errors Errors to add to if the given value is null or longer than maxLength
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfLongerThanMax(String value, int maxLength, Errors errors, String fieldName) {
        rejectIfTrue(isLongerThanMax(value, maxLength), ErrorKey.COMMON_EQUAL_LENGTH, errors, fieldName, maxLength);
    }

    /**
     * If the given String value is null or is its trimmed value is longer than the given maximum length, add an error to the
     * given Errors.
     * 
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value String to check
     * @param maxLength int maximum length for the given String
     * @param errors Errors to add to if the given value is null or longer than maxLength
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfLongerThanMax(String value, int maxLength, Errors errors, FieldKey fieldKey) {
        rejectIfTrue(isLongerThanMax(value, maxLength), ErrorKey.COMMON_MAX_LENGTH, errors, fieldKey, maxLength);
    }

    /**
     * Check if the given String value is not null and if its trimmed value is equal
     * 
     * @param value String to check
     * @param length int length for the given String
     * @return boolean true if the value is not null and the trimmed length not equal to the given length, else false
     */
    protected final boolean isLengthEqual(String value, int length) {
        return value != null && value.trim().length() == length;
    }

    /**
     * If the given String value is null or is its trimmed value is not equal than the given length, add an error to the
     * given Errors.
     * 
     * @param value String to check
     * @param length int length for the given String
     * @param errors Errors to add to if the given value is null or not equal to the given length
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfNotLengthEqual(String value, int length, Errors errors, String fieldName) {
        rejectIfFalse(isLengthEqual(value, length), ErrorKey.COMMON_EQUAL_LENGTH, errors, fieldName, length);
    }

    /**
     * If the given String value is null or is its trimmed value is not equal than the given length, add an error to the
     * given Errors.
     * 
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value String to check
     * @param length int length for the given String
     * @param errors Errors to add to if the given value is null or not equal to the given length
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfNotLengthEqual(String value, int length, Errors errors, FieldKey fieldKey) {
        rejectIfFalse(isLengthEqual(value, length), ErrorKey.COMMON_EQUAL_LENGTH, errors, fieldKey, length);
    }

    /**
     * Check if the given value is outside the given inclusive range.
     * 
     * @param value double value to test
     * @param min Minimum double value
     * @param max Maximum double value
     * @return boolean true if the value is not between the min and max, inclusive
     */
    protected final boolean isOutsideRangeInclusive(double value, double min, double max) {
        return value < min || value > max;
    }

    /**
     * If the given value is not between the minimum and maximum, inclusive, then add an error to the given Errors.
     * 
     * @param value double value to test
     * @param min Minimum double value
     * @param max Maximum double value
     * @param errors Errors to add to if the given value is not between the given values
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfOutsideRangeInclusive(double value, double min, double max, Errors errors, String fieldName) {
        rejectIfTrue(isOutsideRangeInclusive(value, min, max), ErrorKey.COMMON_MAX_MIN_VALUE_INCLUSIVE, errors, fieldName,
            min, max);
    }

    /**
     * If the given value is not between the minimum and maximum, inclusive, then add an error to the given Errors.
     * This is to rejectIfOutsideRangeInclusive for the value.
     *  
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value double value to test
     * @param min Minimum double value
     * @param max Maximum double value
     * @param errors Errors to add to if the given value is not between the given values
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfOutsideRangeInclusive(double value, double min, double max, Errors errors, FieldKey fieldKey) {
        rejectIfTrue(isOutsideRangeInclusive(value, min, max), ErrorKey.COMMON_MAX_MIN_VALUE_INCLUSIVE, errors, fieldKey,
            min, max);
    }

    /**
     * If the given value is not between the minimum and maximum, inclusive, then add an error to the given Errors.
     * 
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value double value to test
     * @param min Minimum double value
     * @param max Maximum double value
     * @param errors Errors to add to if the given value is not between the given values
     * @param groupFieldKey FieldKey of the group the given fieldKey is in
     * @param componentKey FieldKey for the field being checked
     */
    protected final void rejectIfOutsideRangeInclusive(double value, double min, double max, Errors errors,
                                                       FieldKey groupFieldKey, FieldKey componentKey) {
        rejectIfTrue(isOutsideRangeInclusive(value, min, max), ErrorKey.COMMON_GROUPLIST_MAX_MIN_VALUE_INCLUSIVE, errors,
            groupFieldKey, componentKey, min, max);
    }

    /**
     * If the given value's length is not between the minimum and maximum, inclusive, then add an error to the given Errors.
     * 
     * @param value String to test
     * @param min minimum String length
     * @param max maximum String length
     * @param errors Errors to add to if the length of the string is not between the given values
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfLengthOutsideRangeInclusive(String value, int min, int max, Errors errors, FieldKey fieldKey) {
        rejectIfTrue(isLengthOutsideRangeInclusive(value, min, max), ErrorKey.COMMON_MAX_MIN_LENGTH, errors, fieldKey, min,
            max);
    }

    /**
     * If the given value's length is not between the minimum and maximum, inclusive, then add an error to the given Errors.
     * 
     * @param value String to test
     * @param min minimum String length
     * @param max maximum String length
     * @param errors Errors to add to if the length of the string is not between the given values
     * @param groupKey FieldKey of the group the given fieldKey is in
     * @param componentKey FieldKey for the field being checked
     */
    protected final void rejectIfLengthOutsideRangeInclusive(String value, int min, int max, Errors errors,
                                                             FieldKey groupKey, FieldKey componentKey) {
        rejectIfTrue(isLengthOutsideRangeInclusive(value, min, max), ErrorKey.COMMON_GROUPLIST_MAX_MIN_LENGTH, errors,
            groupKey, componentKey, min, max);
    }

    /**
     * Check if the given value's length is outside given inclusive range.
     * 
     * @param value the String to test the length
     * @param min minimum allowed length of the string
     * @param max maximum allowed length of the string
     * @return true if the length is between minimum and maximum value, inclusive
     */
    protected final boolean isLengthOutsideRangeInclusive(String value, int min, int max) {
        int length = 0;

        if (value != null) {
            length = value.trim().length();
        }

        return length < min || length > max;
    }

    /**
     * Check if the given value is outside given exclusive range.
     * 
     * @param value double value to test
     * @param min Minimum double value
     * @param max Maximum double value
     * @return boolean true if the value is not between the min and max, exclusive
     */
    protected final boolean isOutsideRangeExclusive(double value, double min, double max) {
        return value <= min || value >= max;
    }

    /**
     * If the given value is not between the minimum and maximum, exclusive, then add an error to the given Errors.
     * 
     * @param value double value to test
     * @param min Minimum double value
     * @param max Maximum double value
     * @param errors Errors to add to if the given value is not between the given values
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfOutsideRangeExclusive(double value, double min, double max, Errors errors, String fieldName) {
        rejectIfTrue(isOutsideRangeExclusive(value, min, max), ErrorKey.COMMON_MAX_MIN_VALUE_EXCLUSIVE, errors, fieldName,
            min, max);
    }

    /**
     * If the given value is not between the minimum and maximum, exclusive, then add an error to the given Errors.
     * 
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value double value to test
     * @param min Minimum double value
     * @param max Maximum double value
     * @param errors Errors to add to if the given value is not between the given values
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfOutsideRangeExclusive(double value, double min, double max, Errors errors, FieldKey fieldKey) {
        rejectIfTrue(isOutsideRangeExclusive(value, min, max), ErrorKey.COMMON_MAX_MIN_VALUE_EXCLUSIVE, errors, fieldKey,
            min, max);
    }

    /**
     * Check if the given String value is numeric.
     * 
     * @param value String value to test
     * @return boolean true if the value is numeric
     */
    protected final boolean isNumeric(String value) {
        try {
            Double.parseDouble(value);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check if the given String value is an Integer.
     * 
     * @param value String value to test
     * @return boolean true if the value is  an Integer
     */
    protected final boolean isInteger(String value) {
        try {
            Integer.parseInt(value);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * If the given String value is not numeric, then add an error to the given Errors. If the given String is not an
     * Integer, then add an error to the given Errors.
     * 
     * @param value String value to test
     * @param errors Errors to add to if the given value is not an integer
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfNotInteger(String value, Errors errors, String fieldName) {
        rejectIfFalse(isInteger(value), ErrorKey.COMMON_NOT_INTEGER, errors, fieldName);
    }

    /**
     * If the given String value is not numeric, then add an error to the given Errors. If the given String is not an
     * Integer, then add an error to the given Errors.
     * 
     * @param value String value to test
     * @param errors Errors to add to if the given value is not an integer
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfNotInteger(String value, Errors errors, FieldKey fieldKey) {
        rejectIfFalse(isInteger(value), ErrorKey.COMMON_NOT_INTEGER, errors, fieldKey);
    }

    /**
     * If the given String value is not numeric, then add an error to the given Errors. If the given String is not an
     * Integer, then add an error to the given Errors.
     * 
     * @param value String value to test
     * @param errors Errors to add to if the given value is not an integer
     * @param groupKey FieldKey of the group the given fieldKey is in
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfNotInteger(String value, Errors errors, FieldKey groupKey, FieldKey fieldKey) {
        rejectIfFalse(isInteger(value), ErrorKey.COMMON_NOT_INTEGER, errors, groupKey, fieldKey);
    }

    /**
     * If the given String value is not numeric, then add an error to the given Errors.
     * 
     * @param value String value to test
     * @param errors Errors to add to if the given value is not numeric
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfNotNumeric(String value, Errors errors, String fieldName) {
        rejectIfFalse(isNumeric(value), ErrorKey.COMMON_NOT_NUMERIC, errors, fieldName);
    }

    /**
     * If the given String value is not numeric, then add an error to the given Errors.
     * 
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value String value to test
     * @param errors Errors to add to if the given value is not numeric
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfNotNumeric(String value, Errors errors, FieldKey fieldKey) {
        rejectIfFalse(isNumeric(value), ErrorKey.COMMON_NOT_NUMERIC, errors, fieldKey);
    }

    /**
     * If the given String value is not numeric, then add an error to the given Errors.
     * 
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value String value to test
     * @param errors Errors to add to if the given value is not numeric
     * @param groupKey FieldKey of the group the given fieldKey is in
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfNotNumeric(String value, Errors errors, FieldKey groupKey, FieldKey fieldKey) {
        rejectIfFalse(isNumeric(value), ErrorKey.COMMON_GROUP_NOT_NUMERIC, errors, groupKey, fieldKey);

    }

    /**
     * Check if the given Object value is not in the Collection of possible options.
     * 
     * @param value Object value to test
     * @param options Collection of possible valid options
     * @return boolean true if the value is not in the Collection of possible options, else false
     */
    protected final boolean isOption(Object value, Collection options) {
        return options.contains(value);
    }

    /**
     * If the given Object value is not in the Collection of possible options, then add an error to the given Errors.
     * 
     * @param value Object value to test
     * @param options Collection of possible valid options
     * @param errors Errors to add to if the given value is not in the Collection of possible options
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfNotOption(Object value, Collection options, Errors errors, String fieldName) {
        rejectIfFalse(isOption(value, options), ErrorKey.COMMON_NOT_OPTION, errors, fieldName, value);
    }

    /**
     * If the given Object value is not in the Collection of possible options, then add an error to the given Errors.
     * 
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value Object value to test
     * @param options Collection of possible valid options
     * @param errors Errors to add to if the given value is not in the Collection of possible options
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfNotOption(Object value, Collection options, Errors errors, FieldKey fieldKey) {
        rejectIfFalse(isOption(value, options), ErrorKey.COMMON_NOT_OPTION, errors, fieldKey, value);
    }

    /**
     * Check if the given String value is valid to insert or update in the database.
     * 
     * Note that this logic was present in the Domain validation code, but doesn't seem to do much more than validate that
     * there aren't a handful of symbols in the string. Our test cases passed without the validation there (in other words,
     * we never tested that the validation worked), so for now, we are not using this method.
     * 
     * @param value String value to test
     * @return boolean true if the value is valid for inserting or updating in the database
     */
    protected final boolean isValidInsertString(String value) {
        return value != null && value.matches("[ a-zA-Z0-9\\#\\(\\)\\.\\,\\-\\_\\%\\/]+");
    }

    /**
     * If the given String value is not valid to insert or update in the database, then add an error to the given Errors.
     * 
     * Note that this logic was present in the Domain validation code, but doesn't seem to do much more than validate that
     * there aren't a handful of symbols in the string. Our test cases passed without the validation there (in other words,
     * we never tested that the validation worked), so for now, we are not using this method.
     * 
     * @param value String value to test
     * @param errors Errors to add to if the given value is not valid to insert or update in the database
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfNotValidInsertString(String value, Errors errors, String fieldName) {
        rejectIfFalse(isValidInsertString(value), ErrorKey.COMMON_INSERT_STRING, errors, fieldName, value);
    }

    /**
     * If the given String value is not valid to insert or update in the database, then add an error to the given Errors.
     * 
     * Note that this logic was present in the Domain validation code, but doesn't seem to do much more than validate that
     * there aren't a handful of symbols in the string. Our test cases passed without the validation there (in other words,
     * we never tested that the validation worked), so for now, we are not using this method.
     * 
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value String value to test
     * @param errors Errors to add to if the given value is not valid to insert or update in the database
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfNotValidInsertString(String value, Errors errors, FieldKey fieldKey) {
        rejectIfFalse(isValidInsertString(value), ErrorKey.COMMON_INSERT_STRING, errors, fieldKey, value);
    }

    /**
     * Check if the given String value is valid to use as a search criteria.
     * 
     * Note that this logic was present in the Domain validation code, but doesn't seem to do much more than validate that
     * there aren't a handful of symbols in the string. Our test cases passed without the validation there (in other words,
     * we never tested that the validation worked), so for now, we are not using this method.
     * 
     * @param value String value to test
     * @return boolean true if the value is valid for use as a search criteria
     */
    protected final boolean isValidCriteriaString(String value) {
        return value != null && value.matches("[ a-zA-Z0-9\\#\\(\\)\\.\\-\\_\\%\\/\\*]+");
    }

    /**
     * If the given String value is not valid to use as a search criteria, then add an error to the given Errors.
     * 
     * Note that this logic was present in the Domain validation code, but doesn't seem to do much more than validate that
     * there aren't a handful of symbols in the string. Our test cases passed without the validation there (in other words,
     * we never tested that the validation worked), so for now, we are not using this method.
     * 
     * @param value String value to test
     * @param errors Errors to add to if the given value is not valid to use as a search criteria
     * @param fieldName String for the field being checked, either a key into the FieldError.properties or a String literal
     */
    protected final void rejectIfNotValidCriteriaString(String value, Errors errors, String fieldName) {
        rejectIfFalse(isValidCriteriaString(value), ErrorKey.COMMON_CRITERIA_STRING, errors, fieldName, value);
    }

    /**
     * If the given String value is not valid to use as a search criteria, then add an error to the given Errors.
     * 
     * Note that this logic was present in the Domain validation code, but doesn't seem to do much more than validate that
     * there aren't a handful of symbols in the string. Our test cases passed without the validation there (in other words,
     * we never tested that the validation worked), so for now, we are not using this method.
     * 
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value String value to test
     * @param errors Errors to add to if the given value is not valid to use as a search criteria
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfNotValidCriteriaString(String value, Errors errors, FieldKey fieldKey) {
        rejectIfFalse(isValidCriteriaString(value), ErrorKey.COMMON_CRITERIA_STRING, errors, fieldKey, value);
    }

    /**
     * If the given String value doesn't have at least alphabetic character, then add an error to the given Errors.
     * 
     * The new Error added will be attributed to the given field identified by the fieldKey parameter.
     * 
     * @param value String value to test
     * @param errors Errors to add to if the given value is not valid to use as a search criteria
     * @param fieldKey FieldKey for the field being checked
     */
    protected final void rejectIfNotAtLeastOneAlpha(String value, Errors errors, FieldKey fieldKey) {
        rejectIfFalse(isAtLeastOneAlpha(value), ErrorKey.COMMON_NEED_AT_LEAST_ONE_ALPHA_STRING, errors, fieldKey, value);
    }

    /**
     * Check if the given String value has at least one alphabetic character specified, with empty strings ignored.
     * 
     * @param value String to check
     * 
     * @return boolean true if the value is not null and the trimmed length not equal to the given length, else false
     */
    protected final boolean isAtLeastOneAlpha(String value) {
        if (value == null || value.trim().length() <= 0) {
            return true;
        }

        return (!value.matches("[^a-zA-Z]+")); // If any alphabetic character found, return true.
    }

    /**
     * If the given String value has at least one numeric character, then add an
     * error to the given Errors.
     * 
     * The new Error added will be attributed to the given field identified by
     * the fieldKey parameter.
     * 
     * @param value String value to test
     * @param errors Errors to add to if the given value is not valid to use as a
     *            search criteria
     * @param fieldName FieldName for the field being checked
     */
    protected final void rejectIfStartsWithNumeric(String value, Errors errors,
                                                   FieldKey fieldName) {
        rejectIfTrue(startsWithNumeric(value),
                     ErrorKey.COMMON_STARTS_WITH_NUMERIC, errors, fieldName);
    }

    /**
     * Check if the given String value has at least one numeric character
     * specified, with empty strings ignored.
     * 
     * @param value String to check
     * 
     * @return boolean true if the value is not null and the trimmed length not
     *         equal to the given length, else false
     */
    protected final boolean startsWithNumeric(String value) {
        if (value == null || value.equals("")) {
            return false;
        }

        return Character.isDigit(value.charAt(0));
    }

    /**
     * If the given Date value is in the future, then add an
     * error to the given Errors.
     * 
     * The new Error added will be attributed to the given field identified by
     * the fieldKey parameter.
     * 
     * @param value Date value to test
     * @param errors  Errors to add to if the given value is not valid to use as a
     *            search criteria
     * @param fieldKey  FieldKey for the field being checked
     */
    protected final void rejectIfInTheFuture(Date value, Errors errors,
                                             FieldKey fieldKey) {
        rejectIfTrue(isInTheFuture(value), ErrorKey.COMMON_CANNOT_BE_IN_THE_FUTURE,
                     errors, fieldKey, value);
    }

    /**
     * Check if the given Date value is in the future.
     * 
     * @param value Date to check
     * 
     * @return boolean true if the date value is in the future, else false
     */
    protected final boolean isInTheFuture(Date value) {
     
        //Set calendar to right now
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        if (value == null) {
            return false;
        }

        return (cal.getTime().before(value)); // If the current time is before the value, return true.
    }

    /**
     * 
     * Reject the value if it does not properly parse out to a Date
     *
     * @param value String value of date
     * @return true if valid date
     */
    protected final boolean rejectIfNotADate(String value) {

        try {
            DateFormatUtility.convertToDateStrictly(value);
        } catch (ParseException e1) {
            return false;
        }

        return true;
    }

    /**
     * Invoke the validator on an Object that is not a ValueObject. This method
     * is only intended if there is a validator that
     * does not validate a specific ValueObject, instead it validates a common
     * value. For instance, the NdcValidator
     * validates the NDC String, not a ValueObject.
     * 
     * @param validator AbstractValidator subclass to use for validation
     * @param value Object to validate with the given validator
     * @param errors Errors to add new Error objects to
     */
    protected final void invokeValidator(AbstractValidator<T> validator, T value, Errors errors) {
        validator.validate(value, errors);
    }

    /**
     * Validate the given ValueObject.
     * 
     * @param value the object that is to be validated (can be <code>null</code>)
     * @param errors contextual state about the validation process (never <code>null</code>)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public abstract void validate(T value, Errors errors);

    /**
     * Validate the given ValueObject. Validates data values and user permissions. The default implementation, if not
     * overridden by sub classes, is to ignore the provided user and call {@link #validate(Object, Errors)}.
     * 
     * @param value the object that is to be validated (can be <code>null</code>)
     * @param user current UserVo for the user executing the code
     * @param errors contextual state about the validation process (never <code>null</code>)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(T value, UserVo user, Errors errors) {
        validate(value, errors);
    }

    /**
     * Validate the given ValueObject. Validates data values and user permissions. The default implementation, if not
     * overridden by sub classes, is to ignore the provided environment and instead call
     * {@link #validate(Object, UserVo, Errors)}.
     * 
     * @param value the object that is to be validated (can be <code>null</code>)
     * @param user current UserVo for the user executing the code
     * @param environment the local/national environment
     * @param errors contextual state about the validation process (never <code>null</code>)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(T value, UserVo user, Environment environment, Errors errors) {
        validate(value, user, errors);
    }

    /**
     * Validate the given ValueObject. Validates data values and user permissions. The default implementation, if not
     * overridden by sub classes, is to ignore the provided page number and call
     * {@link #validate(Object, UserVo, Environment, Errors)}.
     * 
     * @param value the object that is to be validated (can be <code>null</code>)
     * @param user current UserVo for the user executing the code
     * @param environment the local/national environment
     * @param pageNum wizard page to be validated
     * @param errors contextual state about the validation process (never <code>null</code>)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(T value, UserVo user, Environment environment, int pageNum, Errors errors) {
        validate(value, user, environment, errors);
    }

    /**
     * Ensure this ValudObject instance contains valid data values.
     * 
     * @param value Value to validate
     * 
     * @return Errors containing messages concerning invalid values, if any
     */
    public final Errors checkForErrors(T value) {
        Errors errors = new Errors();
        validate(value, errors);

        return errors;
    }

    /**
     * Ensure this ValudObject instance contains valid data values. Validates data values and user permissions.
     * 
     * @param value Value to validate
     * @param user current UserVo executing code
     * @return Errors containing messages concerning invalid values, if any
     */
    public final Errors checkForErrors(T value, UserVo user) {
        Errors errors = new Errors();
        validate(value, user, errors);

        return errors;
    }

    /**
     * Ensure this ValudObject instance contains valid data values. Validates data values and user permissions.
     * 
     * @param value Value to validate
     * @param user current UserVo executing code
     * @param environment the local/national environment
     * @return Errors containing messages concerning invalid values, if any
     */
    public final Errors checkForErrors(T value, UserVo user, Environment environment) {
        Errors errors = new Errors();
        validate(value, user, environment, errors);

        return errors;
    }

    /**
     * Ensure this ValueObject instance contains valid data values. Validates data values and user permissions.
     * 
     * @param value Value to validate
     * @param user current UserVo executing code
     * @param environment the local/national environment
     * @param pageNum is the page number in the Add product wizard
     * @return Errors containing messages concerning invalid values, if any
     */
    public final Errors checkForErrors(T value, UserVo user, Environment environment, int pageNum) {
        Errors errors = new Errors();

        validate(value, user, environment, pageNum, errors);

        return errors;
    }

    /**
     * Ensure this ValueObject instance contains valid data values. Throw a ValuObjectValidationException if any field values
     * are invalid.
     * 
     * @param value Value to validate
     * 
     * @throws ValueObjectValidationException if this ValueObject is invalid
     */
    public final void validate(T value) throws ValueObjectValidationException {
        Errors errors = checkForErrors(value);

        if (errors.hasErrors()) {
            throw new ValueObjectValidationException(errors);
        }
    }

    /**
     * Ensure this AstractValidator instance contains valid data values. 
     * Throw a ValuObjectValidationException if any field values
     * are invalid. Validates data values and user permissions.
     * 
     * @param value Value to validate
     * @param user current UserVo executing code
     * 
     * @throws ValueObjectValidationException if this ValueObject is invalid
     */
    public final void validate(T value, UserVo user) throws ValueObjectValidationException {
        Errors errors = checkForErrors(value, user);

        if (errors.hasErrors()) {
            throw new ValueObjectValidationException(errors);
        }
    }

    /**
     * Ensure this AbstractValidator instance contains valid data values. 
     * Throw a ValuObjectValidationException if any field values
     * are invalid. Validates data values and user permissions.
     * 
     * @param value Value to validate
     * @param user current UserVo executing code
     * @param environment is the local/national environment
     * 
     * @throws ValueObjectValidationException if this ValueObject is invalid
     */
    public final void validate(T value, UserVo user, Environment environment) throws ValueObjectValidationException {
        Errors errors = checkForErrors(value, user, environment);

        if (errors.hasErrors()) {
            throw new ValueObjectValidationException(errors);
        }
    }

    /**
     * Ensure this ValueObject instance contains valid data values. Throw a ValuObjectValidationException if any field values
     * are invalid. Validates data values and user permissions.
     * 
     * @param value Value to validate
     * @param user current UserVo executing code
     * @param environment is the local/national environment
     * @param pageNum is the page number for in add product wizard
     * 
     * @throws ValueObjectValidationException if this ValueObject is invalid
     */
    public final void validate(T value, UserVo user, Environment environment, int pageNum)
        throws ValueObjectValidationException {
        Errors errors = checkForErrors(value, user, environment, pageNum);

        if (errors.hasErrors()) {
            throw new ValueObjectValidationException(errors);
        }
    }
}
