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
 * validates the Federal Stock Number
 */
public class FsnValidator extends AbstractValidator<DataField<String>> {
   
    /**
     * 
     * this is an optional field for product item
     * federal stock number should be between 5-20 characters
     * @param target Federal Stock Number
     * @param errors the errors collection
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object, gov.va.med.pharmacy.
     * peps.common.vo.validator.Errors)
     */
    public void validate(DataField<String> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.FSN);

            return;
        }// end if

        if (isNull(target.getValue())) {

            // check if the string is not empty string
            rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.FSN);

            return;
        }

        // the length is between 5 to 20 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(target.getValue(), PPSConstants.I5, PPSConstants.I20, errors, FieldKey.FSN);
    }
}
