/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates strength field
 */
public class ProductStrengthValidator extends AbstractValidator<String> {

    /**
     * validate
     * 
     * @param target the Strength field
     * @param errors the errors collection
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator# validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    public void validate(String target, Errors errors) {

        if (!isNull(target)) {

            // string length
            rejectIfLengthOutsideRangeInclusive(target, 1, PPSConstants.I45, errors, FieldKey.PRODUCT_STRENGTH);
        }

    }
}
