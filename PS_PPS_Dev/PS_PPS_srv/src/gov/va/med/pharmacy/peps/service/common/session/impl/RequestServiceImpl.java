/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.RequestCompletedException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.RequestSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.capability.RequestCapability;
import gov.va.med.pharmacy.peps.service.common.session.RequestService;


/**
 * Search and retrieve requests.
 */
public class RequestServiceImpl implements RequestService {
    private RequestCapability requestCapability;

    /**
     * Retrieve request detail by ID.
     * 
     * @param id RequestVo ID to retrieve
     * @return RequestVo
     * 
     * @throws ItemNotFoundException if cannot find Request with given ID
     * @throws RequestCompletedException If the request has already been completed 
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.RequestService#retrieve(java.lang.String)
     */
    public RequestVo retrieve(String id) throws ItemNotFoundException, RequestCompletedException {
        return requestCapability.retrieve(id);
    }

    /**
     * Retrieve all the RequestVo for the ManagedItemVo with the given ID and {@link EntityType}
     * 
     * @param itemId ID for the <ManagedItemVo>
     * @param itemType {@link EntityType} for which to retrieve {@link RequestVo}
     * @return Collection<RequestVo>
     * 
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found  
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.RequestService#retrieve(java.lang.String,
     *      gov.va.med.pharmacy.peps.common.vo.EntityType)
     */
    public Collection<RequestVo> retrieve(String itemId, EntityType itemType) throws ItemNotFoundException {
        return requestCapability.retrieve(itemId, itemType);
    }
    
    /**
     * Retrieve request by eplId
     *  
     * @param itemId The EPL (Enterprise Level) id
     * @param entityType entityType
     * @return RequestVo
     * 
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability
     *      #retrieveRequestDetailById(java.lang.String)
     */
    public RequestVo retrieveNonCompletedRequest(String itemId, EntityType entityType) throws ItemNotFoundException {
        return requestCapability.retrieveNonCompletedRequest(itemId, entityType);
    }

    /**
     * Search for the pending requests
     * 
     * @param criteria for searchCriteria
     * @param user the user performing the search
     * @return Collection<RequestSummaryVo>
     * 
     * @throws ValueObjectValidationException if error in criteria data
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.RequestService
     * #search(gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo, UserVo)
     */
    public Collection<RequestSummaryVo> search(SearchRequestCriteriaVo criteria, UserVo user)
        throws ValueObjectValidationException {
        return requestCapability.search(criteria, user);
    }

    /**
     * Setter injected by Spring.
     * 
     * @param requestCapability requestCapability property
     */
    public void setRequestCapability(RequestCapability requestCapability) {
        this.requestCapability = requestCapability;
    }
}
