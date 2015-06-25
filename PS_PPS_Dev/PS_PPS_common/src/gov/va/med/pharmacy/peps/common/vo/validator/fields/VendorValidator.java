/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Validate vendor field
 */
public class VendorValidator extends AbstractValidator<String> {

    /**
     * The value is optional. If entered, it must be between 1 and 35 characters long.
     * 
     * @param vendor vendor field
     * @param errors Errors object to add errors to
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(String vendor, Errors errors) {
        if (!isNull(vendor)) {
            rejectIfLengthOutsideRangeInclusive(vendor, 1, PPSConstants.I35, errors, FieldKey.VENDOR);
        }
    }
}
