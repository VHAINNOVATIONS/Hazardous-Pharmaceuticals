/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates TallMan Lettering
 */
public class TallManLetteringValidator extends AbstractValidator<String> {

    /**
     * validate
     * 
     * @param target tall man lettering field
     * @param errors the errors collection
     * 
     */
    public void validate(String target, Errors errors) {
        if (isNullOrEmpty(target)) {
            rejectIfNullOrEmpty(target, errors, FieldKey.TALL_MAN_LETTERING);

            return;
        }// end if

        // the length is between 1 to 120 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(target.trim(), 1, PPSConstants.I120, errors, FieldKey.TALL_MAN_LETTERING);

    }// end method
}
