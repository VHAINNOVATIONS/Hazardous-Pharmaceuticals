/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.FdbDrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbDrugUnitDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbDrugUnitConverter;


/**
 * Perform CRUD operations on FdbDrugUnit
 */
public interface FdbDrugUnitDomainCapability {

    /**
     * Creates the FDB Form Association file
     * 
     * @param fdbDrugUnitVo FdbDrugUnitVo
     * @param user provides user information
     * @return FdbDrugUnitVo FDB file returned
     */
    FdbDrugUnitVo create(FdbDrugUnitVo fdbDrugUnitVo, UserVo user);

    /**
     * Deletes all of the FdbDrugUnitVo's.
     */
    void deleteAll();

    /**
     * Retrieves a list of FDB files
     * 
     * @param filterDate Date
     * @return List<FdbDrugUnitVo> list of FDB files returned
     */
    List<FdbDrugUnitVo> retrieve(Date filterDate);

    /**
     * set the Local console info
     * 
     * @param fdbDrugUnitDao EplFdbDrugUnitDao
     */
    void setEplFdbDrugUnitDao(EplFdbDrugUnitDao fdbDrugUnitDao);

    /**
     * setFdbDrugUnitConverter
     * 
     * @param fdbDrugUnitConverter fdbDrugUnitConverter property
     */
    void setFdbDrugUnitConverter(FdbDrugUnitConverter fdbDrugUnitConverter);

    /**
     * Updates the FDB file
     * 
     * @param fdbDrugUnitVo FdbDrugUnitVo
     * @param user provides user information
     * @return FdbDrugUnitVo FDB file returned
     */
    FdbDrugUnitVo update(FdbDrugUnitVo fdbDrugUnitVo, UserVo user);
}
