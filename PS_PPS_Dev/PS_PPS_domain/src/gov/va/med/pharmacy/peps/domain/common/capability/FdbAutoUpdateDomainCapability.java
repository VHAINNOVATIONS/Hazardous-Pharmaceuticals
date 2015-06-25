/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.FdbAutoUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbAutoUpdateDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbAutoUpdateDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbAutoUpdateConverter;


/**
 * Perform CRUD operations on FdbAutoUpdate
 */
public interface FdbAutoUpdateDomainCapability extends ManagedDataDomainCapability<FdbAutoUpdateVo, EplFdbAutoUpdateDo> {

    
    /**
     * Deletes all data from the table Greater than 90 days old. 
     */
    void deleteOver90();
    

//    /**
//     * Creates the FDB add file
//     * 
//     * @param fdbAutoUpdateVo
//     *            migration file
//     * @param user
//     *            provides user information
//     * @return FdbAutoUpdateVo FDB file returned
//     */
  //  FdbAutoUpdateVo create(FdbAutoUpdateVo fdbAutoUpdateVo, UserVo user);

    /**
     * Retrieves a list of FDB files
     * 
     * @return List<FdbAutoUpdateVo> list of FDB files returned
     */
    List<FdbAutoUpdateVo> retrieve();

    /**
     * Updates the FDB file
     * 
     * @param fdbAutoUpdateVo
     *            migration file
     * @param user
     *            provides user information
     * @return FdbAutoUpdateVo FDB file returned
     */
    FdbAutoUpdateVo update(FdbAutoUpdateVo fdbAutoUpdateVo, UserVo user);

    /**
     * Deletes all of the FdbAutoUpdateVo's.
     */
    void deleteAll();
    
    
    /**
     * 
     * deleteItemById
     *
     * @param id id to remove
     */
    void deleteItemById(String id); 

    /**
     * set the Local console info
     * 
     * @param fdbAutoUpdateDao fdbAutoUpdateDao
     */
    void setEplFdbAutoUpdateDao(EplFdbAutoUpdateDao fdbAutoUpdateDao);

    /**
     * setFdbAutoUpdateConverter
     * @param fdbAutoUpdateConverter fdbAutoUpdateConverter property
     */
    void setFdbAutoUpdateConverter(FdbAutoUpdateConverter fdbAutoUpdateConverter);
}
