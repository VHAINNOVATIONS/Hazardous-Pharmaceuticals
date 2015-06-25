/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


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
 */
public interface RequestService {

    /**
     * Retrieve request detail by ID.
     * 
     * @param id RequestVo ID to retrieve
     * @return RequestVo
     * 
     * @throws ItemNotFoundException if cannot find Request with given ID
     * @throws RequestCompletedException if the request has already been completed
     */
    RequestVo retrieve(String id) throws ItemNotFoundException, RequestCompletedException;

    /**
     * Retrieve all the RequestVo for the ManagedItemVo with the given ID and {@link EntityType}
     * 
     * @param itemId ID for the <ManagedItemVo>
     * @param itemType {@link EntityType} for which to retrieve {@link RequestVo}
     * @return Map<String,RequestVo>
     * 
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found     *  
     */
    Collection<RequestVo> retrieve(String itemId, EntityType itemType) throws ItemNotFoundException;

    /**
     * Retrieve request by eplId for RequestService
     *  
     * @param itemId an enterprise level id
     * @param entityType entityType
     * @return RequestVo
     * 
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     */
    RequestVo retrieveNonCompletedRequest(String itemId, EntityType entityType) throws ItemNotFoundException; 
    
    
    /**
     * Search for the pending requests
     * 
     * @param criteria for searchCriteria
     * @param user The User
     * @return Collection<RequestSummaryVo>
     * 
     * @throws ValueObjectValidationException if error in criteria data
     */
    Collection<RequestSummaryVo> search(SearchRequestCriteriaVo criteria, UserVo user) throws ValueObjectValidationException;
}
