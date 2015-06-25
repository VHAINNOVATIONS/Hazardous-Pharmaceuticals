/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * validator for dispense unit
 */
public class DispenseUnitValidator extends AbstractValidator<DispenseUnitVo> {
    
    /**
     * validates Dispense Unit Vo
     * 
     * @param target the DispenseUnitVo
     * @param errors the errors collection
     */
    public void validate(DispenseUnitVo target, Errors errors) {
        if (target == null) {
            rejectIfNull(target, errors, FieldKey.DISPENSE_UNIT);

            return;
        }

        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.DISPENSE_UNIT);
        
        if (target.getValue() != null) {
            rejectIfLengthOutsideRangeInclusive(target.getValue(), 1, PPSConstants.I10, errors, FieldKey.VALUE);
        }
    }
}
