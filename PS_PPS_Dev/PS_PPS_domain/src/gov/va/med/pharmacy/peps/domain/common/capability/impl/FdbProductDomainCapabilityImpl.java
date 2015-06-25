/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.FdbProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbProductDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbProductDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbProductConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;





/**
 * Perform CRUD operations on FDB Drug ingredient
 */
public class FdbProductDomainCapabilityImpl implements FdbProductDomainCapability {  
    
    private EplFdbProductDao eplFdbProductDao;
    private FdbProductConverter fdbProductConverter;
    
    /**
     *  Default constructor
     */
    public FdbProductDomainCapabilityImpl() {
        super();
    }

    /**
     * retrieve
     * @return a list of FDB Drug Ingredient items
     */
    public List<FdbProductVo> retrieve() {
        List<EplFdbProductDo> eplDos = eplFdbProductDao.retrieve();
        List<FdbProductVo> fdbFdbProducts = fdbProductConverter.convert(eplDos);
    
        return fdbFdbProducts;
    }
    
    /**
     * retrieve with ProductIdFk.
     * @param productIdFk ProductIdFk
     * @return a list of FDB Drug Ingredient items
     */
    public FdbProductVo retrieve(Long productIdFk) {
        FdbProductVo fdbProductVo = null;
        
        StringBuffer queryBuffer = new StringBuffer(HqlBuilder.create("SELECT item FROM").append(
            EplFdbProductDo.class).append("item WHERE item.").toString());

        queryBuffer.append(EplFdbProductDo.PRODUCT_ID_FK).append(" = ").append(productIdFk);
       
        List<EplFdbProductDo> productDoList = eplFdbProductDao.executeHqlQuery(queryBuffer.toString());
        
        if (productDoList != null && productDoList.size() > 0) {
            fdbProductVo = fdbProductConverter.convert(productDoList.get(0));
        }
        
        return fdbProductVo;
    }
    
    /**
     * create
     * @param fdbProductVo the FDB add vo
     * @param user user
     * @return the newly created FDB
     */
    public FdbProductVo create(FdbProductVo fdbProductVo, UserVo user) {
        EplFdbProductDo eplFdbProductDo = fdbProductConverter.convert(fdbProductVo);
        EplFdbProductDo returnDo = eplFdbProductDao.insert(eplFdbProductDo, user);
    
        return fdbProductConverter.convert(returnDo);
    }
    
  
    /**
     * Update the given FdbDrugIngredientVo.
     * 
     * @param fdbProductVo fdbProductVo
     * @param user performing the action
     * @return FdbDrugIngredientVo upddated Vo
     */
    public FdbProductVo update(FdbProductVo fdbProductVo, UserVo user) {
        EplFdbProductDo eplFdbProductDo = fdbProductConverter.convert(fdbProductVo);
        eplFdbProductDao.update(eplFdbProductDo, user);
    
        return fdbProductVo;
    }
    
  
    /**
     * setEplFdbProductDao.
     * 
     * @param eplFdbProductDao EplFdbProductDao
     */
    public void setEplFdbProductDao(EplFdbProductDao eplFdbProductDao) {
        this.eplFdbProductDao = eplFdbProductDao;
    }
    
    /**
     * setFdbProductConverter Converter
     * @param fdbProductConverter fdbProductConverter property
     */
    public void setFdbProductConverter(FdbProductConverter fdbProductConverter) {
        this.fdbProductConverter = fdbProductConverter;
    }

}
