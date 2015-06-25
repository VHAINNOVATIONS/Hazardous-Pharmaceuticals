/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.capability.impl;


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
 * <p>
 * Local implementation calls into National via the national instance of the
 * {@link gov.va.med.pharmacy.peps.service.common.session.RequestService}. All RequestVo are stored at National.
 */
public class RequestCapabilityImpl implements RequestCapability {
    private RequestService nationalRequestService;

    /**
     * Retrieve request detail by ID.
     * 
     * @param id RequestVo ID to retrieve
     * @return RequestVo
     * @throws ItemNotFoundException if cannot find Request with given ID
     * @throws RequestCompletedException exception
     */
    public RequestVo retrieve(String id) throws ItemNotFoundException, RequestCompletedException {
        return nationalRequestService.retrieve(id);
    }

    /**
     * Retrieve all the RequestVo for the ManagedItemVo with the given ID and {@link EntityType}.
     * 
     * @param itemId ID for the {@link ManagedItemVo}
     * @param itemType {@link EntityType} for which to retrieve {@link RequestVo}
     * @return Collection<RequestVo>
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     */
    public Collection<RequestVo> retrieve(String itemId, EntityType itemType) throws ItemNotFoundException {
        return nationalRequestService.retrieve(itemId, itemType);
    }
    
    /**
     * Retrieve request by eplId 
     *  
     * @param itemId an enterprise level id
     * @param entityType entityType
     * @return RequestVo Returns null if there are not any non-completed requests with this id.
     * 
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     */
    public RequestVo retrieveNonCompletedRequest(String itemId, EntityType entityType) throws ItemNotFoundException {
        return nationalRequestService.retrieveNonCompletedRequest(itemId, entityType);
    }

    /**
     * Search for the pending requests.
     * 
     * @param criteria for searchCriteria
     * @param user user
     * @return Collection<RequestSummaryVo>
     * 
     * @throws ValueObjectValidationException if error in criteria data
     */
    public Collection<RequestSummaryVo> search(SearchRequestCriteriaVo criteria, UserVo user) 
        throws ValueObjectValidationException {
        return nationalRequestService.search(criteria, null);
    }

    /**
     * Setter injected by Spring.
     * 
     * @param nationalRequestService nationalRequestService property
     */
    public void setNationalRequestService(RequestService nationalRequestService) {
        this.nationalRequestService = nationalRequestService;
    }
}
