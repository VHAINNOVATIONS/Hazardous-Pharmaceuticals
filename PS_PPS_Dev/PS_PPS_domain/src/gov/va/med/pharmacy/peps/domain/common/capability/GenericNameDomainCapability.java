/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaGenNameDo;


/**
 * Perform CRUD operations on generic name
 */
public interface GenericNameDomainCapability extends ManagedDataDomainCapability<GenericNameVo, EplVaGenNameDo> {

    /**
     * retrieveByName
     * @param name name
     * @return IngredientVo
     */
    GenericNameVo retrieveByName(String name);
    
}
