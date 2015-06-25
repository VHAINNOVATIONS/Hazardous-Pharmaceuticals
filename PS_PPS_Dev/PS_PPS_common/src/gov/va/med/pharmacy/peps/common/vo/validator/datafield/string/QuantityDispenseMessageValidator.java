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
 * validates quantity dispense message
 */
public class QuantityDispenseMessageValidator extends AbstractValidator<DataField<String>> {
    
    /**
     * 
     * this is an optional field for product item
     * quantity dispense message should be between 1-68 characters
     * @param target quantity dispense message
     * @param errors the errors collection
     *
     */
    public void validate(DataField<String> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.QUANTITY_DISPENSE_MESSAGE);

            return;
        }// end if

        if (isNull(target.getValue())) {

            // check if the string is not empty string
            rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.QUANTITY_DISPENSE_MESSAGE);

            return;
        }

        // the length is between 1 to 68 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(target.getValue(), 1, PPSConstants.SIXTYEIGHT, errors, 
            FieldKey.QUANTITY_DISPENSE_MESSAGE);
    }
}
