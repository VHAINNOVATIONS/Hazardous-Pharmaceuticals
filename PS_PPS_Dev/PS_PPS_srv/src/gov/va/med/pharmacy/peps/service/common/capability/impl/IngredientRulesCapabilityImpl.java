/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.domain.common.capability.IngredientDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.IntendedUseDomainCapability;


/**
 * IngredientRulesCapabilityImpl
 *
 */
public class IngredientRulesCapabilityImpl extends DefaultRulesCapabilityImpl<IngredientVo> {

    private IngredientDomainCapability ingredientDomainCapability;


    /**
     * Enforce additional Ingredient rules beyond simple ValueObject validation.
     * 
     * @param ingredient {@link IngredientVo} to validate
     * @param user UserVo for which to validate
     * @param canPerformUpdate boolean indicating if the call is allowed to perform updates on other items     
     * @throws ValidationException if general validation error
     */
    @Override
    protected void handleEnforceRules(IngredientVo ingredient, UserVo user, boolean canPerformUpdate)
        throws ValidationException {

        Errors errors = new Errors();
        IngredientVo primary = null;

        if (ItemStatus.ACTIVE.equals(ingredient.getItemStatus())) {
            if (ingredient.getPrimaryIngredient() != null) {
                
                // need to retrieve the primary again as it may have been updated in the database since this
                // underlying ingredient was retrieved.
                primary = ingredientDomainCapability.retrieve(ingredient.getPrimaryIngredient().getId());
            }
        }
        
        if (primary != null && ItemStatus.INACTIVE.equals(primary.getItemStatus())) {
            errors.addError(ErrorKey.PRIMARY_ING_MUST_BE_ACTIVE, primary.getValue(), ingredient.getValue());
            throw new ValueObjectValidationException(errors);
        }
    }
    
    
    /**
     * SetIngredientDomainCapability
     * @param ingredientDomainCapability intendedUseDomainCapability property
     */
    public void setIngredientDomainCapability(IngredientDomainCapability ingredientDomainCapability) {
        this.ingredientDomainCapability = ingredientDomainCapability;
    }
}
