/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.external.common.callback.IntendedUseDomainCapabilityCallback;


/**
 * Perform CRUD operations on Intended Use VOs
 */
public interface IntendedUseDomainCapability extends
        IntendedUseDomainCapabilityCallback {

    /**
     * Retrieve all possible Intended Use
     * 
     * @return List of Intended Use
     */
    @Override
    List<IntendedUseVo> retrieveDomain();

    /**
     * Retrieve specified IntendedUseVo
     * 
     * @param id
     *            of intendedUseVo to retrieve
     * @return IntendedUseVo
     * @throws ItemNotFoundException exception
     * 
     */
    IntendedUseVo retrieve(String id) throws ItemNotFoundException;

}
