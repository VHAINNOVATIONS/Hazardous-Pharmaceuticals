/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.FdbDrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbDrugClassDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbDrugClassConverter;


/**
 * Perform CRUD operations on FdbDrugClass
 */
public interface FdbDrugClassDomainCapability {

    /**
     * Creates the FDB Form Association file
     * 
     * @param fdbDrugClassVo fdbDrugClassVo
     *            
     * @param user provides user information
     * @return FdbDrugClassVo FDB file returned
     */
    FdbDrugClassVo create(FdbDrugClassVo fdbDrugClassVo, UserVo user);

    /**
     * Deletes all of the FdbDrugClassVo's.
     */
    void deleteAll();

    /**
     * Retrieves a list of FDB files
     * 
     * @param filterDate Date
     * @return List<FdbDrugClassVo> list of FDB files returned
     */
    List<FdbDrugClassVo> retrieve(Date filterDate);

    /**
     * setEplFdbDrugClassDao
     *      * 
     * @param fdbDrugClassDao fdbDrugClassDao
     *            
     */
    void setEplFdbDrugClassDao(EplFdbDrugClassDao fdbDrugClassDao);

    /**
     * setFdbDrugClassConverter
     * @param fdbDrugClassConverter fdbDrugClassConverter property
     */
    void setFdbDrugClassConverter(FdbDrugClassConverter fdbDrugClassConverter);

    /**
     * Updates the FDB file
     * 
     * @param fdbDrugClassVo fdbDrugClassVo
     *            
     * @param user provides user information
     * @return FdbDrugClassVo FDB file returned
     */
    FdbDrugClassVo update(FdbDrugClassVo fdbDrugClassVo, UserVo user);
}
