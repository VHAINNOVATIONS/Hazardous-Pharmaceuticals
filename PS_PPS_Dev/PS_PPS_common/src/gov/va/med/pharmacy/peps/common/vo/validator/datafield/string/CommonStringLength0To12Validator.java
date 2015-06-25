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
 * Validates the String data fields whose length should be between 0 to 12 characters
 */
public class CommonStringLength0To12Validator extends AbstractValidator<DataField<String>> {

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
 
        // the length is between 0 to 12 characters, inclusive
        if (target != null && target.getValue() != null) {
            rejectIfLongerThanMax(target.getValue(), PPSConstants.I12, errors, target.getKey());
        }
    }
}
