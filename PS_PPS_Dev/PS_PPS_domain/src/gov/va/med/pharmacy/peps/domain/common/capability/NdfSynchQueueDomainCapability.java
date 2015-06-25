/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.NdfSynchQueueVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplNdfSynchQueueDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdfSynchQueueDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.NdfSynchQueueConverter;


/**
 * Perform CRUD operations on FdbAutoUpdate
 */
public interface NdfSynchQueueDomainCapability extends ManagedDataDomainCapability<NdfSynchQueueVo, EplNdfSynchQueueDo> {

    
    /**
     * Retrieves a list of all Queue Messages
     * 
     * @return List<FdbAutoUpdateVo> list of FDB files returned
     */
    List<NdfSynchQueueVo> retrieve();

    /**
     * 
     * deleteItemById
     *
     * @param id id to remove
     */
    void deleteItemById(String id); 

    /**
     * setEplNdfSynchQueueDao
     *  
     * @param eplNdfSynchQueueDao eplNdfSynchQueueDao
     */
    void setEplNdfSynchQueueDao(EplNdfSynchQueueDao eplNdfSynchQueueDao);

    /**
     * setNdfSynchQueueConverter
     * @param ndfSynchQueueConverter ndfSynchQueueConverter property
     */
    void setNdfSynchQueueConverter(NdfSynchQueueConverter ndfSynchQueueConverter);
}
