/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.string;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates Message vadf
 */
public class MessageValidator extends AbstractValidator<DataField<String>> {
    
    /**
     *  validates the message field
     *  
     * this is an optional field for product item
     * message should be between 1-68 characters
     * @param target Message
     * @param errors the errors collection
     * 
     */
    public void validate(DataField<String> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.MESSAGE);

            return;
        }// end if

        if (isNull(target.getValue())) {

            // check if the string is not empty string
            rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.MESSAGE);

            return;
        }

        // the length is between 1 to 68 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(target.getValue(), 1, PPSConstants.SIXTYEIGHT, errors, FieldKey.MESSAGE);
    }
}
