/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.RequestCompletedException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.RequestSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Search and retrieve requests.
 * <p>
 * Local/National implement this differently. There is not a common implementation, only a common interface!
 */
public interface RequestCapability {

    /**
     * Retrieve request detail by ID.
     * 
     * @param id RequestVo ID to retrieve
     * @return RequestVo
     * @throws ItemNotFoundException if cannot find Request with given ID
     * @throws RequestCompletedException if the request has already been completed
     */
    RequestVo retrieve(String id) throws ItemNotFoundException, RequestCompletedException;

    /**
     * Retrieve all the RequestVo for the ManagedItemVo with the given ID and {@link EntityType}
     * 
     * @param itemId ID for the <ManagedItemVo>
     * @param itemType {@link EntityType} for which to retrieve {@link RequestVo}
     * @return Collection<RequestVo>
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     */
    Collection<RequestVo> retrieve(String itemId, EntityType itemType) throws ItemNotFoundException;

    /**
     * RequestCapability. Retrieve request by eplId 
     *  
     * @param itemId an enterprise level id
     * @param entityType entityType
     * @return RequestVo
     * 
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability
     *      #retrieveRequestDetailById(java.lang.String)
     */
    RequestVo retrieveNonCompletedRequest(String itemId, EntityType entityType) throws ItemNotFoundException; 
    
    /**
     * Search for the pending requests
     * 
     * @param criteria for searchCriteria
     * @param user the user performing the search
     * @return Collection<RequestSummaryVo>
     * 
     * @throws ValueObjectValidationException if error in criteria data
     */
    Collection<RequestSummaryVo> search(SearchRequestCriteriaVo criteria, UserVo user) throws ValueObjectValidationException;
}
