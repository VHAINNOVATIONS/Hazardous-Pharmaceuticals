/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.FdbUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbUpdateDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbUpdateDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbUpdateConverter;


/**
 * Perform CRUD operations on FdbUpdate
 */
public interface FdbUpdateDomainCapability extends ManagedDataDomainCapability<FdbUpdateVo, EplFdbUpdateDo> {

    /**
     * Deletes all data from the table Greater than 90 days old. 
     */
    void deleteOver90();
    

//    /**
//     * Creates the FDB add file
//     * 
//     * @param fdbUpdateVo
//     *            migration file
//     * @param user
//     *            provides user information
//     * @return FdbUpdateVo FDB file returned
//     */
  //  FdbUpdateVo create(FdbUpdateVo fdbUpdateVo, UserVo user);

    /**
     * Retrieves a list of FDB files
     * 
     * @return List<FdbUpdateVo> list of FDB files returned
     */
    List<FdbUpdateVo> retrieve();

    /**
     * Updates the FDB file
     * 
     * @param fdbUpdateVo
     *            migration file
     * @param user
     *            provides user information
     * @return FdbUpdateVo FDB file returned
     */
    FdbUpdateVo update(FdbUpdateVo fdbUpdateVo, UserVo user);

    /**
     * Deletes all of the FdbUpdateVo's.
     */
    void deleteAll();

    /**
     * deleteItemById
     * @param identification identification
     */
    void deleteItemById(String identification);
    
    
    /**
     * set the Local console info
     * 
     * @param fdbUpdateDao   EplLocalConsoleInfoDao
     */
    void setEplFdbUpdateDao(EplFdbUpdateDao fdbUpdateDao);

    /**
     * setFdbUpdateConverter
     * @param fdbUpdateConverter
     *            fdbUpdateConverter property
     */
    void setFdbUpdateConverter(FdbUpdateConverter fdbUpdateConverter);
}
