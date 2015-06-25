/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplIngredientDo;


/**
 * Perform CRUD operations on Ingredients
 */
public interface IngredientDomainCapability extends ManagedDataDomainCapability<IngredientVo, EplIngredientDo> {

    
    /**
     * retrieveByName
     * @param name name
     * @return IngredientVo
     */
    IngredientVo retrieveByName(String name);
    
}
