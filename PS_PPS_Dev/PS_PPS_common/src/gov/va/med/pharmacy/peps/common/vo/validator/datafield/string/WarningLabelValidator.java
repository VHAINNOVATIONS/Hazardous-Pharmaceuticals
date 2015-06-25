/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.string;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates warning label
 */
public class WarningLabelValidator extends AbstractValidator<DataField<String>> {
    
    /**
     * validates warning label
     * 
     * @param target the string data field
     * @param errors the errors collection
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    public void validate(DataField<String> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, target.getKey());

            return;
        }

        if (isNull(target.getValue())) {

            // check if the string is not empty string
            rejectIfNullOrEmpty(target.getValue(), errors, target.getKey());

            return;
        }

        // the length is between 1 to 25 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(target.getValue(), 1, PPSConstants.I25, errors, target.getKey());
    }
}
