/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Validate DF71: Frequency (in minutes).
 */
public class FrequencyValidator extends AbstractValidator<Long> {

    /**
     * Data Type: Numeric
     * <p>
     * Data Range: {min: 1; max: 129600}
     * <p>
     * Rule 1: This field is optional for Medication Instruction domain.
     * <p>
     * Rule 2: This field is optional for Administration Schedule domain.
     * <p>
     * Rule 3: Only a numeric value that is divisor of 1440 (minutes) can be added to this field.
     * 
     * @param value Long value of Frequency to validate
     * @param errors Current list of Errors
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(Long value, Errors errors) {
        if (value != null) {
            rejectIfOutsideRangeInclusive(value, 1, PPSConstants.I129600, errors, FieldKey.FREQUENCY);

            if (value > 0) {
                if (value < PPSConstants.I1440) {
                    rejectIfFalse(PPSConstants.I1440 % value == 0, ErrorKey.FREQUENCY_DIVISIBLE, errors, FieldKey.FREQUENCY);
                } else {
                    rejectIfFalse(value % PPSConstants.I1440 == 0, ErrorKey.FREQUENCY_DIVISIBLE, errors, FieldKey.FREQUENCY);
                }
            }
        }
    }
}
