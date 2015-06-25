/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.string;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates the previous ndc
 */
public class PreviousNdcValidator extends AbstractValidator<DataField<String>> {

    /**
     * validate
     * 
     * @param target the previous ndcs
     * @param errors the errors object
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    public void validate(DataField<String> target, Errors errors) {

        if ((target != null) && (target.getValue() != null) && !(target.getValue().isEmpty())) {
            validatePreviousNdc(target.getValue(), errors);

        }// end if
    }// end method

    /**
     * 
     * 
     * @param previousNdc the previous ndc
     * @param errors errors object
     */
    private void validatePreviousNdc(String previousNdc, Errors errors) {
        String strippedPreviousNdc = previousNdc.replaceAll("-", "");

        if (strippedPreviousNdc.length() > PPSConstants.I11) {
            errors.addError(FieldKey.PREVIOUS_NDC, ErrorKey.NDC_LENGTH, new Object[] {FieldKey.PREVIOUS_NDC});
        }

        rejectIfNotNumeric(strippedPreviousNdc, errors, FieldKey.PREVIOUS_NDC);
        String[] split = previousNdc.split("-", -1);

        if (split.length != PPSConstants.I3 || split[0].trim().length() > PPSConstants.I5 
            || split[1].trim().length() > PPSConstants.I4 || split[2].trim().length() > 2) {
            
            //If its just one block of text that's fine we'll put the delimeters in.
            if (!(split.length == 1 && previousNdc.length() == PPSConstants.I11)) {
                errors.addError(FieldKey.PREVIOUS_NDC, ErrorKey.NDC_FORMAT, new Object[] {FieldKey.PREVIOUS_NDC});
            }
        }

    }

}// end class
