/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbAddDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbAddConverter;




/**
 * Perform CRUD operations on FdbAdd
 */
public interface FdbAddDomainCapability extends ManagedDataDomainCapability<FdbAddVo, EplFdbAddDo> {

    
    /**
     * Deletes all data from the table Greater than 90 days old. 
     */
    void deleteOver90();
    
    /**
     * Creates the FDB add file
     * 
     * @param fdbAddVo  migration file
     * @param user  provides user information
     * @return FdbAddVo FDB file returned
     */
    FdbAddVo create(FdbAddVo fdbAddVo, UserVo user);

    /**
     * Retrieves a list of FDB files
     * 
     * @return List<FdbAddVo> list of FDB files returned
     */
    List<FdbAddVo> retrieve();

    /**
     * Updates the FDB file
     * 
     * @param fdbAddVo   migration file
     * @param user      provides user information
     * @return FdbAddVo FDB file returned
     */
    FdbAddVo update(FdbAddVo fdbAddVo, UserVo user);

    /**
     * Deletes all of the FdbAddVo's.
     */
    void deleteAll();

    /**
     * Setter for the FdbAddConverter 
     * @param fdbAddConverter  fdbAddConverter property
     */
    void setFdbAddConverter(FdbAddConverter fdbAddConverter);

    
    /**
     * Deletes FdbAddVos by NDC number
     *
     * @param ndcNo NDC number
     */
    void deleteByNdcNumber(String ndcNo);
}
