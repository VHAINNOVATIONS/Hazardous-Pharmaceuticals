/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.FdbAutoAddVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbAutoAddDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbAutoAddDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbAutoAddConverter;


/**
 * Perform CRUD operations on FdbAutoAdd
 */
public interface FdbAutoAddDomainCapability extends ManagedDataDomainCapability<FdbAutoAddVo, EplFdbAutoAddDo> {

    /**
     * Deletes all data from the table Greater than 90 days old. 
     */
    void deleteOver90();
    
    /**
     * Creates the FDB add file
     * 
     * @param fdbAutoAddVo
     *            migration file
     * @param user
     *            provides user information
     * @return FdbAutoAddVo FDB file returned
     */
    FdbAutoAddVo create(FdbAutoAddVo fdbAutoAddVo, UserVo user);

    /**
     * Retrieves a list of FDB files
     * 
     * @return List<FdbAutoAddVo> list of FDB files returned
     */
    List<FdbAutoAddVo> retrieve();

    /**
     * Updates the FDB file
     * 
     * @param fdbAutoAddVo
     *            migration file
     * @param user
     *            provides user information
     * @return FdbAutoAddVo FDB file returned
     */
    FdbAutoAddVo update(FdbAutoAddVo fdbAutoAddVo, UserVo user);

    /**
     * Deletes all of the FdbAutoAddVo's.
     */
    void deleteAll();
    
    
    /**
     * Deletes FdbAutoAddVo's by NDC number
     *
     * @param pNdcNo NDC number
     */
    void deleteByNdcNumber(String pNdcNo);

    /**
     * set the setEplFdbAutoAddDao
     * 
     * @param fdbAutoAddDao fdbAutoAddDao
     */
    void setEplFdbAutoAddDao(EplFdbAutoAddDao fdbAutoAddDao);

    /**
     * setFdbAutoAddConverter
     * @param fdbAutoAddConverter fdbAutoAddConverter property
     */
    void setFdbAutoAddConverter(FdbAutoAddConverter fdbAutoAddConverter);
}
