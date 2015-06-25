/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * A VUID must be a length of 1 to 20.
 */
public class VuidValidator extends AbstractValidator<String> {

    /**
     * A VUID must be a length of 1 to 20.
     * 
     * @param value VUID
     * @param errors Errors
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(String value, Errors errors) {

        if (isNullOrEmpty(value)) {
            rejectIfNullOrEmpty(value, errors, FieldKey.VUID);

            return;

        }

        rejectIfLengthOutsideRangeInclusive(value, 1, PPSConstants.I20, errors, FieldKey.VUID);
    }
}
