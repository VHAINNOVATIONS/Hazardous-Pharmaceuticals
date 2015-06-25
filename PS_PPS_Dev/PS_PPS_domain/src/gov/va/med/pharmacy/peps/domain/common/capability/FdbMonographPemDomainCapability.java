/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;



import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DrugMonographVo;
import gov.va.med.pharmacy.peps.common.vo.FdbGenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbMonographPemDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbMonographPemConverter;


/**
 * Perform CRUD operations on FdbGenericName
 */
public interface FdbMonographPemDomainCapability {

    /**
     * Creates the FDB Form Association file
     * 
     * @param monographVo DrugMonographVo
     * @param user provides user information
     * @return DrugMonographVo FDB file returned
     */
    DrugMonographVo create(DrugMonographVo monographVo, UserVo user);

    /**
     * Deletes all of the FdbGenericNameVo's.
     */
    void deleteAll();
    
    /**
     * Deletes all of the FdbGenericNameVo's.
     */
    void delete(Long monographId);
    
    /**
     * Retrieves a list of FDB files
     * 
     * @param filterDate Date
     * @return List<DrugMonographVo> list of FDB files returned
     */
    List<DrugMonographVo> retrieve(Long monographId);
    
    /**
     * Retrieves a list of FDB files
     * 
     * @param filterDate Date
     * @return List<DrugMonographVo> list of FDB files returned
     */
    List<DrugMonographVo> retrieveAll();

    /**
     * set the Local console info
     * 
     * @param fdbMonographPemDao
     *            fdbMonographPemDao
     */
    void setEplFdbMonographPemDao(EplFdbMonographPemDao fdbMonographPemDao);

    /**
     * setFdbGcnseqnoPemConverter
     * 
     * @param fdbMonographPemConverter
     *            fdbMonographPemConverter property
     */
    void setFdbMonographPemConverter(FdbMonographPemConverter fdbMonographPemConverter);

    /**
     * Updates the FDB file
     * 
     * @param drugMonographVo drugMonographVo
     * @param user provides user information
     * @return drugMonographVo FDB file returned
     */
    DrugMonographVo update(DrugMonographVo drugMonographVo, UserVo user);
}

