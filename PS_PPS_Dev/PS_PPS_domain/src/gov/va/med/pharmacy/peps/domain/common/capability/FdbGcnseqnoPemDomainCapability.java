/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;



import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DrugMonographGcnseqnoAssocsVo;
import gov.va.med.pharmacy.peps.common.vo.DrugMonographVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbGcnseqnoPemDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbGcnseqnoPemConverter;


/**
 * Perform CRUD operations on FdbGenericName
 */
public interface FdbGcnseqnoPemDomainCapability {

    /**
     * Creates the FDB Form Association file
     * 
     * @param fdbgcnseqnoPemVo fdbgcnseqnoPemVo
     * @param user provides user information
     * @return DrugMonographGcnseqnoAssocsVo FDB file returned
     */
    DrugMonographGcnseqnoAssocsVo create(DrugMonographGcnseqnoAssocsVo fdbgcnseqnoPemVo, UserVo user);

    /**
     * Deletes all of the DrugMonographGcnseqnoAssocsVo.
     */
    void deleteAll();
    
    /**
     * Deletes all of the DrugMonographGcnseqnoAssocsVo.
     */
    void delete(Long monographId, Long gcnseqno);

    
    /**
     * Retrieves a list of FDB files
     * 
     * @param filterDate Date
     * @return List<DrugMonographGcnseqnoAssocsVo> list of FDB files returned
     */
    List<DrugMonographGcnseqnoAssocsVo> retrieveByMonograph(Long monographId);
    
    /**
     * Retrieves a list of FDB files
     * 
     * @param filterDate Date
     * @return List<DrugMonographGcnseqnoAssocsVo> list of FDB files returned
     */
    List<DrugMonographGcnseqnoAssocsVo> retrieveByGcnseqno(Long gcnseqno);

    
    /**
     * Retrieves a list of FDB files
     * 
     * @param filterDate Date
     * @return List<DrugMonographGcnseqnoAssocsVo> list of FDB files returned
     */
    List<DrugMonographGcnseqnoAssocsVo> retrieveAll();

    

    /**
     * set the setEplFdbGcnseqnoPemDao
     * 
     * @param fdbGcnseqnoPemDao
     *            EplFdbGcnseqnoPemDao
     */
    void setEplFdbGcnseqnoPemDao(EplFdbGcnseqnoPemDao fdbGcnseqnoPemDao);

    /**
     * setFdbGcnseqnoPemConverter
     * 
     * @param fdbGcnseqnoPemConverter
     *            fdbGcnseqnoPemConverter property
     */
    void setFdbGcnseqnoPemConverter(FdbGcnseqnoPemConverter fdbGcnseqnoPemConverter);

    /**
     * Updates the FDB file
     * 
     * @param fdbgcnseqnoPemVo fdbgcnseqnoPemVo
     * @param user provides user information
     * @return FdbGenericNameVo FDB file returned
     */
    DrugMonographGcnseqnoAssocsVo update(DrugMonographGcnseqnoAssocsVo fdbgcnseqnoPemVo, UserVo user);
}
