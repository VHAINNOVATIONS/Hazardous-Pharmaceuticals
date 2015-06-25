/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;


import gov.va.med.pharmacy.peps.common.vo.FdbNdcVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbNdcDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbNdcConverter;


/**
 * Perform CRUD operations on FdbNdc
 */
public interface FdbNdcDomainCapability {

    /**
     * Creates the FDB add file
     * 
     * @param fdbNdcVo
     *            migration file
     * @param user
     *            provides user information
     * @return FdbNdcVo FDB file returned
     */
    FdbNdcVo create(FdbNdcVo fdbNdcVo, UserVo user);

    /**
     * Retrieves a list of FDB files
     * 
     * @return List<FdbNdcVo> list of FDB files returned
     */
    List<FdbNdcVo> retrieve();
    
    /**
     * Retrieves a single row
     * @param ndcIdFk ndcIdFk
     * @return List<FdbNdcVo> list of FDB files returned
     */
    FdbNdcVo retrieve(Long ndcIdFk);


    /**
     * Updates the FDB file
     * 
     * @param fdbNdcVo
     *            migration file
     * @param user
     *            provides user information
     * @return FdbNdcVo FDB file returned
     */
    FdbNdcVo update(FdbNdcVo fdbNdcVo, UserVo user);

    /**
     * set the Local console info.
     * 
     * @param fdbNdcDao fdbNdcDao
     */
    void setEplFdbNdcDao(EplFdbNdcDao fdbNdcDao);

    /**
     * setFdbNdcConverter.
     * @param fdbNdcConverter fdbNdcConverter property
     */
    void setFdbNdcConverter(FdbNdcConverter fdbNdcConverter);
}
