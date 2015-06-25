/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validate National Formulary Name
 */
public class NationalFormularyNameValidator extends AbstractValidator<String> {
    
    /**
     * validate
     * 
     * @param target the National Formulary Name field
     * @param errors the errors collection
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator# validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    public void validate(String target, Errors errors) {
        if (isNullOrEmpty(target)) {
            rejectIfNullOrEmpty(target, errors, FieldKey.NATIONAL_FORMULARY_NAME);

            return;
        }// end if

     // the length is between 1 to 94 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(target, 1, PPSConstants.I94, errors, FieldKey.NATIONAL_FORMULARY_NAME);
    }
}
