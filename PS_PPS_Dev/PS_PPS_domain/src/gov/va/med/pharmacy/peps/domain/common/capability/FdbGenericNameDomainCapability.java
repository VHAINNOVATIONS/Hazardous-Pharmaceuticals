/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.FdbGenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbGenericNameDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbGenericNameConverter;


/**
 * Perform CRUD operations on FdbGenericName
 */
public interface FdbGenericNameDomainCapability {

    /**
     * Creates the FDB Form Association file
     * 
     * @param fdbGenericNameVo FdbGenericNameVo
     * @param user provides user information
     * @return FdbGenericNameVo FDB file returned
     */
    FdbGenericNameVo create(FdbGenericNameVo fdbGenericNameVo, UserVo user);

    /**
     * Deletes all of the FdbGenericNameVo's.
     */
    void deleteAll();

    /**
     * Retrieves a list of FDB files
     * 
     * @param filterDate Date
     * @return List<FdbGenericNameVo> list of FDB files returned
     */
    List<FdbGenericNameVo> retrieve(Date filterDate);

    /**
     * set the Local console info
     * 
     * @param fdbGenericNameDao
     *            EplFdbGenericNameDao
     */
    void setEplFdbGenericNameDao(EplFdbGenericNameDao fdbGenericNameDao);

    /**
     * setFdbGenericNameConverter
     * 
     * @param fdbGenericNameConverter
     *            fdbGenericNameConverter property
     */
    void setFdbGenericNameConverter(FdbGenericNameConverter fdbGenericNameConverter);

    /**
     * Updates the FDB file
     * 
     * @param fdbGenericNameVo FdbGenericNameVo
     * @param user provides user information
     * @return FdbGenericNameVo FDB file returned
     */
    FdbGenericNameVo update(FdbGenericNameVo fdbGenericNameVo, UserVo user);
}
