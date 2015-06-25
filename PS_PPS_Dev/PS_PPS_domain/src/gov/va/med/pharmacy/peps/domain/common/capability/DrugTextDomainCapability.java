/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugTextDo;


/**
 * Perform CRUD operations on drug texts
 */
public interface DrugTextDomainCapability extends ManagedDataDomainCapability<DrugTextVo, EplDrugTextDo> {

    /**
     * Re-associates existing drug text associations for a product to the 
     * new incoming drug texts denoted by nationalItem
     * 
     * @param existingItem existing Local {@link DrugTextVo} to associate Products with
     * @param nationalItem new {@link DrugTextVo} to associate Products with
     * @param user {@link UserVo} performing the action
     */
    void reassociateExistingItems(DrugTextVo existingItem, DrugTextVo nationalItem, UserVo user);
    
    /**
     * Get all active drug texts, LOCAL only
     * 
     * @return List<DrugTextVo>
     */
    List<DrugTextVo> getLocalDrugText();

    /**
     * Get all active drug texts, NATIONAL only
     * 
     * @return List<DrugTextVo>
     */
    List<DrugTextVo> getNationalDrugText();
    
    /**
     * Get drug text that matches name
     * 
     * @param drugText DrugTextVo
     * @return DrugTextVo
     */
    DrugTextVo getUniqueItem(DrugTextVo drugText);

}
