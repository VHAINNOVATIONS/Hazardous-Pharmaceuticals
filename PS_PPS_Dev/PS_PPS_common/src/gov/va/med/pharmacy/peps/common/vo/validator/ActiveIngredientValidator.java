/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * validate the active ingredient
 */
public class ActiveIngredientValidator extends AbstractValidator<Collection<ActiveIngredientVo>> {

    /**
     * validates the product active ingredient Name can not be null or empty Strength can not be null or empty
     * 
     * @param target the active ingredients
     * @param errors the errors collection
     * 
     */
    public void validate(Collection<ActiveIngredientVo> target, Errors errors) {
        if ((target == null) || (target.isEmpty())) {
            rejectIfNull(target, errors, FieldKey.ACTIVE_INGREDIENT);
            
            return;
        }

        // iterate through all the active ingredients,
        // if active ingredient is null, then do not save
        for (ActiveIngredientVo activeIngredient : target) {
            if (activeIngredient == null) {

                rejectIfNull(activeIngredient, errors, FieldKey.ACTIVE_INGREDIENT);
                
                return;
            } else {
                if (activeIngredient.getIngredient() == null || isNullOrEmpty(activeIngredient.getIngredient().getValue())) {
                    errors.addError(FieldKey.ACTIVE_INGREDIENT, ErrorKey.ACTIVE_INGREDIENT_NAME_EMPTY,
                            new Object[] {FieldKey.ACTIVE_INGREDIENT});

                }

                if (!isNullOrEmpty(activeIngredient.getStrength())) {
                    rejectIfLengthOutsideRangeInclusive(activeIngredient.getStrength(), 1, PPSConstants.I45, errors,
                                                        FieldKey.ACTIVE_INGREDIENT, FieldKey.STRENGTH);
                    
                    // DO NOT REMOVE ME! THIS IS NECESSARY FOR PROPER UI VALIDATION!
                 //   rejectIfNotNumeric(activeIngredient.getStrength(), errors, FieldKey.STRENGTH);

                }

            }
        }
    }
}
