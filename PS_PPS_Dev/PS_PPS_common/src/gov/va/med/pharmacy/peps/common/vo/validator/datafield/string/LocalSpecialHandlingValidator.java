/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.string;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 *LocalSpecialHandlingValidator
 */
public class LocalSpecialHandlingValidator extends AbstractValidator<DataField<String>> {

    /**
     * validate
     * @param value Local Special Handling
     * @param errors Errors
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object, 
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(DataField<String> value, Errors errors) {
        
        if (value != null) {
            rejectIfLengthOutsideRangeInclusive(value.getValue(), 0, PPSConstants.I40, errors, 
                FieldKey.LOCAL_SPECIAL_HANDLING);
        }
    }

}
