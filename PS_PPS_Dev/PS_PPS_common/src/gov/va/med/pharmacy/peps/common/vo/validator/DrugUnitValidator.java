/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Validator for drug unit
 */
public class DrugUnitValidator extends AbstractValidator<DrugUnitVo> {
    
    /**
     * validates drug unit
     * 
     * @param target the DrugUnitVo
     * @param errors the errors collection
     */
    public void validate(DrugUnitVo target, Errors errors) {
        if (target == null) {
            rejectIfNull(target, errors, FieldKey.DRUG_UNIT);

            return;
        }
        
        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.VALUE);
        rejectIfLengthOutsideRangeInclusive(target.getValue(), 1, PPSConstants.I75, errors, FieldKey.VALUE);

    }
}
