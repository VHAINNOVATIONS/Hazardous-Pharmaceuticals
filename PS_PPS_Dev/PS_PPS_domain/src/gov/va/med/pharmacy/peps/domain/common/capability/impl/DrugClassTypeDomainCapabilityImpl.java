/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugClassTypeDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplClassTypeDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DrugClassificationTypeConverter;


/**
 * Perform CRUD operations with drug classification types
 */
public class DrugClassTypeDomainCapabilityImpl implements DrugClassTypeDomainCapability {
    private EplClassTypeDao eplClassTypeDao;
    private DrugClassificationTypeConverter drugClassificationTypeConverter;

    /**
     * Retrieve a List of drug classification types
     * 
     * @return List of DrugClassificationTypeVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.DrugClassTypeDomainCapability#retrieveDomain()
     */
    public List<DrugClassificationTypeVo> retrieveDomain() {
        return drugClassificationTypeConverter.convertMinimal(eplClassTypeDao.retrieve());
    }

    /**
     *  Retrieve a DrugClassificationTypeVo
     *  @param id id
     *  @return drugClass
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.DrugClassTypeDomainCapability#retrieve(java.lang.String)
     */
    public DrugClassificationTypeVo retrieve(String id) {
        return drugClassificationTypeConverter.convertMinimal(eplClassTypeDao.retrieve(Long.valueOf(id)));
    }

    /**
     * setEplClassTypeDao
     * @param eplClassTypeDao eplClassTypeDao property
     */
    public void setEplClassTypeDao(EplClassTypeDao eplClassTypeDao) {
        this.eplClassTypeDao = eplClassTypeDao;
    }

    /**
     * setDrugClassificationTypeConverter
     * @param drugClassificationTypeConverter drugClassificationTypeConverter property
     */
    public void setDrugClassificationTypeConverter(DrugClassificationTypeConverter drugClassificationTypeConverter) {
        this.drugClassificationTypeConverter = drugClassificationTypeConverter;
    }
}
