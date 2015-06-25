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
 * Validates the String data fields whose length should be between 1 to 120 characters
 */
public class CommonStringLength1To120Validator extends AbstractValidator<DataField<String>> {

/**
 * validate
 * 
 * @param target the String Data Field
 * @param errors the errors collection
 * 
 * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator
 * #validate(java.lang.Object, gov.va.med.pharmacy.peps.common.vo.validator.Errors)
 */
    public void validate(DataField<String> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, target.getKey());

            return;
        }// end if

        if (isNull(target.getValue())) {

            // check if the string is not empty string
            rejectIfNullOrEmpty(target.getValue(), errors, target.getKey());

            return;
        }

        // the length is between 1 to 120 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(target.getValue(), 1, PPSConstants.I120, errors, target.getKey());
    }
}
