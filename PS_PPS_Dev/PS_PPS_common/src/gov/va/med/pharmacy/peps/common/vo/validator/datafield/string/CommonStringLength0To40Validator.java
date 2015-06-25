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
 * Validates the String data fields whose length should be between 0 to 40 characters
 */
public class CommonStringLength0To40Validator extends AbstractValidator<DataField<String>> {

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
        if (target.getValue() != null && !(target.getValue().isEmpty())) {

            // the length is between 0 to 40 characters, inclusive
            rejectIfLongerThanMax(target.getValue(), PPSConstants.I40, errors, target.getKey());

        }
        
    }
}
