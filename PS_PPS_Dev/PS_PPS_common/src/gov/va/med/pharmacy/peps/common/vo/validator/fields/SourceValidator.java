/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Source Validator
 */
public class SourceValidator extends AbstractValidator<String> {

    /**
     * The value cannot be null or empty
     * 
     * @param value DataField
     * @param errors object to add errors to
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(String value, Errors errors) {
        if (isNull(value)) {
            rejectIfNullOrEmpty(value, errors, FieldKey.SOURCE);
            
            return;
        }
        
        // the length is between 1 to 120 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(value, 1, PPSConstants.I120, errors, FieldKey.SOURCE);
    }
}
