/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates the VA Product Name
 */
public class VAProductNameValidator extends AbstractValidator<String> {
    
    /**
     * validate
     * 
     * @param target the CMOP Id va data field
     * @param errors the errors collection
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator# validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    public void validate(String target, Errors errors) {
        if (isNullOrEmpty(target)) {
            rejectIfNullOrEmpty(target, errors, FieldKey.VA_PRODUCT_NAME);

            return;
        }// end if

        // the length is between 3 to 64 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(target, PPSConstants.I3, PPSConstants.I64, errors, FieldKey.VA_PRODUCT_NAME);
    }

}
