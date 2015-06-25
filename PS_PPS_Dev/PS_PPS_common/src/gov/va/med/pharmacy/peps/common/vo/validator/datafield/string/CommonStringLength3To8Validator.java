/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.string;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * 
 * 
 * Validates the String data fields whose length should be between 3 to 8 characters
 */
public class CommonStringLength3To8Validator extends AbstractValidator<DataField<String>> {

/**
 * validate the CommonStringLength3To8Validator
 * 
 * @param target the String Data Field
 * @param errors the errors collection
 * 
 * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator
 * #validate(java.lang.Object, gov.va.med.pharmacy.peps.common.vo.validator.Errors)
 */
    public void validate(DataField<String> target, Errors errors) {
        if (isNull(target)) {
            return;
        }// end if

        if (isNull(target.getValue())) {
            return;
        }

        // the length is between 3 to 8 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(target.getValue(), PPSConstants.I3, PPSConstants.I8, errors, target.getKey());
    }
}
