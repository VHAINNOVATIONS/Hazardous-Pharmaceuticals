/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Validate the NDC String
 */
public class NdcFieldValidator extends AbstractValidator<String> {

    /**
     * The system shall contain a field for NDC.
     * <p>
     * Type: Linked DF
     * <p>
     * Data Type: Text
     * <p>
     * Data Range: {min/max: 11}
     * <p>
     * Default: Empty
     * <p>
     * Local Only: N
     * <p>
     * Editable after Creation: Y
     * <p>
     * <p>
     * Rule 1: This field is mandatory for an NDC item.
     * <p>
     * Rule 2: Validate that an NDC has 3 segments in the order of 5 digits, 4 digits, and 2 digits respectively, and each
     * segment consists only of numeric digits.
     * <p>
     * Rule 3: Provide leading zeros in each segment of the NDC when a segment does not have the correct number of digits.
     * 
     * @param ndc for Object
     * @param errors for Errors
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(String ndc, Errors errors) {
        rejectIfNullOrEmpty(ndc, errors, FieldKey.NDC);

        if (!isNullOrEmpty(ndc)) {
            String strippedNdc = ndc.replaceAll("-", "");

            if (strippedNdc.length() > PPSConstants.I11) {
                errors.addError(FieldKey.NDC, ErrorKey.NDC_LENGTH, new Object[] {FieldKey.NDC});
            }

            rejectIfNotNumeric(strippedNdc, errors, FieldKey.NDC);

            checkSplit(ndc, errors);
            
        }
    }
    
    /**
     * checkSplit
     * @param ndc ndc 
     * @param errors errors
     */
    private void checkSplit(String ndc, Errors errors) {
        String[] split = ndc.split("-", -1);
        
        if (split.length != PPSConstants.I3 || split[0].trim().length() > PPSConstants.I5 
            || split[1].trim().length() > PPSConstants.I4 || split[2].trim().length() > 2) {
            
            //If its just one block of text that's fine we'll put the delimeters in.
            if (!(split.length == 1 && ndc.length() == PPSConstants.I11)) {
                errors.addError(FieldKey.NDC, ErrorKey.NDC_FORMAT, new Object[] {FieldKey.NDC});
            }               
           
        }
    }
}
