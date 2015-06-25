/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.FdbDosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbDosageFormDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbDosageFormConverter;


/**
 * Perform CRUD operations on FdbDosageForm
 */
public interface FdbDosageFormDomainCapability {

    /**
     * Creates the FDB Form Association file
     * 
     * @param fdbDosageFormVo FdbDosageFormVo
     * @param user provides user information
     * @return FdbDosageFormVo FDB file returned
     */
    FdbDosageFormVo create(FdbDosageFormVo fdbDosageFormVo, UserVo user);

    /**
     * Deletes all of the FdbDosageFormVo's.
     */
    void deleteAll();

    /**
     * Retrieves a list of FDB files
     * 
     * @param filterDate Date
     * @return List<FdbDosageFormVo> list of FDB files returned
     */
    List<FdbDosageFormVo> retrieve(Date filterDate);

    /**
     * set the Local console info
     * 
     * @param fdbDosageFormDao EplFdbDosageFormDao
     */
    void setEplFdbDosageFormDao(EplFdbDosageFormDao fdbDosageFormDao);

    /**
     * setFdbDosageFormConverter
     * 
     * @param fdbDosageFormConverter fdbDosageFormConverter property
     */
    void setFdbDosageFormConverter(FdbDosageFormConverter fdbDosageFormConverter);

    /**
     * Updates the FDB file
     * 
     * @param fdbDosageFormVo FdbDosageFormVo
     * @param user provides user information
     * @return FdbDosageFormVo FDB file returned
     */
    FdbDosageFormVo update(FdbDosageFormVo fdbDosageFormVo, UserVo user);
}
