/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * IenValidator validates IEN value, IEN is an optional field that must be
 * between 0 and 1000000.
 * 
 */
public class IenValidator extends AbstractValidator<Long> {

    
    /**
     * validates the IEN
     * 
     * @param target is the IEN
     * @param errors is the errors collection
     */
    public void validate(Long target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.IEN);

            return;
        }

        rejectIfOutsideRangeInclusive(target, 0, PPSConstants.ONEMILLION, errors, FieldKey.IEN);
    }
}
