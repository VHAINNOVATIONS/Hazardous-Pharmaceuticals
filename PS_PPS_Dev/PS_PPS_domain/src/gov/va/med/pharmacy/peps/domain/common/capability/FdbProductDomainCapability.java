/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;


import gov.va.med.pharmacy.peps.common.vo.FdbProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbProductDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbProductConverter;


/**
 * Perform CRUD operations on FdbProduct
 */
public interface FdbProductDomainCapability {

    /**
     * Creates the FDB add file
     * 
     * @param fdbProductVo migration file
     * @param user provides user information
     * @return FdbProductVo FDB file returned
     */
    FdbProductVo create(FdbProductVo fdbProductVo, UserVo user);

    /**
     * Retrieves a list of FDB files
     * 
     * @return List<FdbProductVo> list of FDB files returned
     */
    List<FdbProductVo> retrieve();
    
    /**
     * Retrieves a single row
     * @param productIdFk ProductIdFk
     * @return List<FdbProductVo> list of FDB files returned
     */
    FdbProductVo retrieve(Long productIdFk);


    /**
     * Updates the FDB file
     * 
     * @param fdbProductVo migration file
     * @param user provides user information
     * @return FdbProductVo FDB file returned
     */
    FdbProductVo update(FdbProductVo fdbProductVo, UserVo user);

    /**
     * set the Local console info.
     * 
     * @param fdbProductDao fdbProductDao
     */
    void setEplFdbProductDao(EplFdbProductDao fdbProductDao);

    /**
     * setFdbProductConverter.
     * @param fdbProductConverter fdbProductConverter property
     */
    void setFdbProductConverter(FdbProductConverter fdbProductConverter);
}
