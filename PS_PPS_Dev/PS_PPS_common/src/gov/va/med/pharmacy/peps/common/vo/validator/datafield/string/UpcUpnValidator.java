/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.string;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Source file created in 2007 by Southwest Research Institute
 */
public class UpcUpnValidator extends AbstractValidator<String> {

    /**
     * 
     * this is an optional field
     * 
     * @param target UpcUpn
     * @param errors the errors collection
     * 
     */
    public void validate(String target, Errors errors) {

        if (isNullOrEmpty(target)) {

            return;
        }
        
     // the length is between 1 to 20 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(target, 1, PPSConstants.I20, errors, FieldKey.UPC_UPN);
    }
}
