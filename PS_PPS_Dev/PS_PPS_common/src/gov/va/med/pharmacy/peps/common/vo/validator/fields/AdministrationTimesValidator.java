/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import org.apache.commons.lang.math.NumberUtils;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Validate common rules for various administration times (all but field length).
 */
public class AdministrationTimesValidator extends AbstractValidator<String> {
    private static final String HYPHEN = "-";

    /**
     * Calls {@link #validate(String, Errors, FieldKey)} with a null FieldKey.
     * <p>
     * Should never really call this method. Use {@link #validate(String, Errors, FieldKey)} instead.
     * 
     * @param value String administration times
     * @param errors Errors to add validation errors
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(String value, Errors errors) {
        validate(value, errors, null);
    }

    /**
     * The String administration times value is assumed to be non-null with at least one time value in it.
     * <p>
     * All time be in military time (24 hour clock).
     * <p>
     * All times must be in ascending order (i.e., 06-14-22 or 0600-1400-2200. )
     * <p>
     * All times must be the same length, either 2 or 4 digits.
     * 
     * @param value String administration times
     * @param errors Errors to add validation errors
     * @param fieldKey FieldKey being validated
     */
    public void validate(String value, Errors errors, FieldKey fieldKey) {
        String[] times = value.split(HYPHEN, -1);

        int firstLength = times[0].trim().length();
        boolean all24HourClock = true;
        boolean allSameLength = true;
        boolean ascendingOrder = true;
        int previousTime = -1;

        for (String time : times) {
            all24HourClock &= time.matches("([0-1][0-9]|2[0-3])") || time.matches("(([0-1][0-9]|2[0-3])[0-5][0-9])");
            allSameLength &= time.trim().length() == firstLength;

            if (NumberUtils.isNumber(time)) {
                int currentTime = Integer.parseInt(time);

                // Just in case the user mixed 2 digits with 4, change them all to 4.
                // This won't fix incorrect lengths like 3, though.
                if (currentTime < PPSConstants.I100) {
                    currentTime *= PPSConstants.I100;
                }

                ascendingOrder &= previousTime < currentTime;
                previousTime = currentTime;
            }
        }

        rejectIfFalse(all24HourClock, ErrorKey.ADMINISTRATION_TIMES_24_CLOCK, errors, fieldKey, fieldKey);
        rejectIfFalse(ascendingOrder, ErrorKey.ADMINISTRATION_TIMES_ASCENDING_ORDER, errors, fieldKey, fieldKey);
        rejectIfFalse(allSameLength && (firstLength == 2 || firstLength == PPSConstants.I4), 
            ErrorKey.ADMINISTRATION_TIMES_EQUAL_LENGTH, errors, fieldKey, fieldKey);
    }
}
