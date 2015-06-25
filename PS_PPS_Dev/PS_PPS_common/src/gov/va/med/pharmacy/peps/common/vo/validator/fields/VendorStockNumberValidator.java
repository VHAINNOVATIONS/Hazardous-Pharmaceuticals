/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Vendor Stock Number Validator
 */
public class VendorStockNumberValidator extends AbstractValidator<String> {

    /**
     * The value is optional. If entered, it must be between 1 and 30 characters long.
     * 
     * @param vendorStockNumber String vendor stock number
     * @param errors object to add errors to
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(String vendorStockNumber, Errors errors) {
        if (!isNull(vendorStockNumber)) {
            rejectIfLengthOutsideRangeInclusive(vendorStockNumber, 1, PPSConstants.I30, errors, FieldKey.VENDOR_STOCK_NUMBER);
        }
    }
}
