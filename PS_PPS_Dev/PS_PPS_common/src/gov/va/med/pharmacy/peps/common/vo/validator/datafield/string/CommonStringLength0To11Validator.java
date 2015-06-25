/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.string;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.datafield.MultitextDataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * 
 * 
 * Validates the String data fields whose length should be between 0 to 11 characters
 */
public class CommonStringLength0To11Validator extends AbstractValidator<MultitextDataField<String>> {

    /**
     * validate
     * 
     * @param target the String Data Field
     * @param errors the errors collection
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator #validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    public void validate(MultitextDataField<String> target, Errors errors) {

        if (target != null && !(target.isEmpty())) {

            // the length is between 0 to 11 characters, inclusive
            for (String selection : target.getValue()) {
                if (selection != null && selection != null) {
                    rejectIfLongerThanMax(selection, PPSConstants.I11, errors, target.getKey());
                }
            }
        }
    }
}
