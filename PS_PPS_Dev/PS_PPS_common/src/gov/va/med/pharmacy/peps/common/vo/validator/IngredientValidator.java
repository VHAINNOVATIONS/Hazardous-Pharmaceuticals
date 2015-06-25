/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;


/**
 * Validator for ingredient
 */
public class IngredientValidator extends AbstractValidator<IngredientVo> {

    /**
     * validates object
     * 
     * @param target the object
     * @param errors the errors collection
     */
    public void validate(IngredientVo target, Errors errors) {
        if (target == null) {
            rejectIfNull(target, errors, FieldKey.INGREDIENT);

            return;
        }

        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.VALUE);
        rejectIfLengthOutsideRangeInclusive(target.getValue(), PPSConstants.I3, 
            PPSConstants.I64, errors, FieldKey.VALUE);
        
        
        // VUID and Effective Date Time are mandatory when the request item status is approved
        if ((target.getRequestItemStatus().equals(RequestItemStatus.APPROVED))) {

            // VUID
            FieldKey.VUID.newValidatorInstance().validate(target.getVuid(),
                                                          errors);

            // Effective Date Time for the Ingredient Test
            FieldKey.EFFECTIVE_DATES.newValidatorInstance().validate(target.getEffectiveDates(), errors);
        }
    }
}
