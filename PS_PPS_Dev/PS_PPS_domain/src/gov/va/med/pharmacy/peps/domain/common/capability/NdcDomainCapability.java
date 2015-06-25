/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;


/**
 * Perform CRUD operations on NdcVo
 */
public interface NdcDomainCapability extends ManagedItemDomainCapability<NdcVo, EplNdcDo> {

    /** 
     * ndcExists
     * @param ndcNoDashes ndcNoDashes
     * @return True if the NDC String exists in the database
     */
    boolean ndcExists(String ndcNoDashes);
    
    /** 
     * ndcExists
     * @param ndcNoDashes ndcNoDashes
     * @return True if the NDC String exists in the database
     */
    long getIdFromNDC(String ndcNoDashes);
}
