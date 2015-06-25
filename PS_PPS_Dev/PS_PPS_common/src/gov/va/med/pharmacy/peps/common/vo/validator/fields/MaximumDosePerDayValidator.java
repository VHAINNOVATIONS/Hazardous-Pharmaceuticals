/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validated maximum dose per day
 */
public class MaximumDosePerDayValidator extends AbstractValidator<Long> {

    /**
     * validates the Maximum dose per day
     * 
     * @param target is the Maximum dose per day
     * @param errors is the errors collection
     */
    public void validate(Long target, Errors errors) {

        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.MAX_DOSE_PER_DAY);

            return;
        }

        rejectIfOutsideRangeInclusive(target, 0, PPSConstants.I999, errors, FieldKey.MAX_DOSE_PER_DAY);

//        rejectIfMoreDecimals(target.doubleValue(), NUM_0, errors, FieldKey.MAX_DOSE_PER_DAY);
        
        rejectIfNotInteger(target.toString(), errors, FieldKey.MAX_DOSE_PER_DAY);
    }
}
