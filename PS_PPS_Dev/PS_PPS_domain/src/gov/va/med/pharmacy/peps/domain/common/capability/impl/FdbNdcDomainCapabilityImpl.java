/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.FdbNdcVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbNdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbNdcDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbNdcDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbNdcConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;





/**
 * Perform CRUD operations on FDB Drug ingredient
 */
public class FdbNdcDomainCapabilityImpl implements FdbNdcDomainCapability {  
    
    private EplFdbNdcDao eplFdbNdcDao;
    private FdbNdcConverter fdbNdcConverter;
    
    /**
     *  Default constructor
     */
    public FdbNdcDomainCapabilityImpl() {
        super();
    }

    /**
     * retrieve
     * @return a list of FDB Drug Ingredient items
     */
    public List<FdbNdcVo> retrieve() {
        List<EplFdbNdcDo> eplDos = eplFdbNdcDao.retrieve();
        List<FdbNdcVo> fdbFdbNdcs = fdbNdcConverter.convert(eplDos);
    
        return fdbFdbNdcs;
    }
    
    /**
     * retrieve with ndcIdFk.
     * @param ndcIdFk ndcIdFk
     * @return a list of FDB Drug Ingredient items
     */
    public FdbNdcVo retrieve(Long ndcIdFk) {
        FdbNdcVo fdbNdcVo = null;
        
        StringBuffer queryBuffer = new StringBuffer(HqlBuilder.create("SELECT item FROM").append(
            EplFdbNdcDo.class).append("item WHERE item.").toString());

        queryBuffer.append(EplFdbNdcDo.NDC_ID_FK).append(" = ").append(ndcIdFk);
       
        List<EplFdbNdcDo> ndcDoList = eplFdbNdcDao.executeHqlQuery(queryBuffer.toString());
        
        if (ndcDoList != null && ndcDoList.size() > 0) {
            fdbNdcVo = fdbNdcConverter.convert(ndcDoList.get(0));
        }
        
        return fdbNdcVo;
    }
    
    /**
     * create
     * @param fdbNdcVo the FDB add vo
     * @param user user
     * @return the newly created FDB
     */
    public FdbNdcVo create(FdbNdcVo fdbNdcVo, UserVo user) {
        EplFdbNdcDo eplFdbNdcDo = fdbNdcConverter.convert(fdbNdcVo);
        EplFdbNdcDo returnDo = eplFdbNdcDao.insert(eplFdbNdcDo, user);
    
        return fdbNdcConverter.convert(returnDo);
    }
    
  
    /**
     * Update the given FdbDrugIngredientVo.
     * 
     * @param fdbNdcVo fdbNdcVo
     * @param user performing the action
     * @return FdbDrugIngredientVo upddated Vo
     */
    public FdbNdcVo update(FdbNdcVo fdbNdcVo, UserVo user) {
        EplFdbNdcDo eplFdbNdcDo = fdbNdcConverter.convert(fdbNdcVo);
        eplFdbNdcDao.update(eplFdbNdcDo, user);
    
        return fdbNdcVo;
    }
    
  
    /**
     * setEplFdbNdcDao.
     * 
     * @param eplFdbNdcDao EplFdbNdcDao
     */
    public void setEplFdbNdcDao(EplFdbNdcDao eplFdbNdcDao) {
        this.eplFdbNdcDao = eplFdbNdcDao;
    }
    
    /**
     * setFdbNdcConverter Converter
     * @param fdbNdcConverter fdbNdcConverter property
     */
    public void setFdbNdcConverter(FdbNdcConverter fdbNdcConverter) {
        this.fdbNdcConverter = fdbNdcConverter;
    }

}
