/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.FdbDrugIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbDrugIngredientDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbDrugIngredientConverter;


/**
 * Perform CRUD operations on FdbDrugIngredient
 */
public interface FdbDrugIngredientDomainCapability {

    /**
     * Creates the FDB Form Association file
     * 
     * @param fdbDrugIngredientVo FdbDrugIngredientVo
     * @param user provides user information
     * @return FdbDrugIngredientVo FDB file returned
     */
    FdbDrugIngredientVo create(FdbDrugIngredientVo fdbDrugIngredientVo, UserVo user);

    /**
     * Deletes all of the FdbDrugIngredientVo's.
     */
    void deleteAll();

    /**
     * Retrieves a list of FDB files
     * 
     * @param filterDate Date
     * @return List<FdbDrugIngredientVo> list of FDB files returned
     */
    List<FdbDrugIngredientVo> retrieve(Date filterDate);

    /**
     * set the Local console info
     * 
     * @param fdbDrugIngredientDao EplFdbDrugIngredientDao
     */
    void setEplFdbDrugIngredientDao(EplFdbDrugIngredientDao fdbDrugIngredientDao);

    /**
     * setFdbDrugIngredientConverter
     * 
     * @param fdbDrugIngredientConverter fdbDrugIngredientConverter property
     */
    void setFdbDrugIngredientConverter(FdbDrugIngredientConverter fdbDrugIngredientConverter);

    /**
     * Updates the FDB file
     * 
     * @param fdbDrugIngredientVo FdbDrugIngredientVo     *            
     * @param user provides user information
     * @return FdbDrugIngredientVo FDB file returned
     */
    FdbDrugIngredientVo update(FdbDrugIngredientVo fdbDrugIngredientVo, UserVo user);
}
