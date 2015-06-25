/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplRequestDo;


/**
 * Interface for handling requests
 */
public interface RequestDomainCapability {

    /**
     * retrieve request detail by requestId
     * 
     * @param requestId for retrieving request
     * @return RequestVo
     * 
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     */
    RequestVo retrieve(String requestId) throws ItemNotFoundException;

    /**
     * retrieve all the requests for the item
     * 
     * @param itemId for the item
     * @param itemType {@link EntityType}
     * @return Collection<RequestVo>
     * 
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     */
    Collection<RequestVo> retrieve(String itemId, EntityType itemType) throws ItemNotFoundException;

    /**
     * RequestDomainCapability
     * Retrieve request by eplId 
     *  
     * @param itemId an enterprise level id
     * @param entityType entityType
     * @return RequestVo the RequestVo.
     * 
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability
     *      #retrieveRequestDetailById(java.lang.String)
     */
    RequestVo retrieveNonCompletedRequest(String itemId, EntityType entityType) throws ItemNotFoundException;
    
    /**
     * Deletes all completed requests 
     *  
     */
    void deleteCompletedRequest();
    
    /**
     * searches for pending requests by criteria
     * 
     * @param criteria the search criteria for pending requests
     * @param user the user performing the search
     * @return Collection<RequestSummaryVo>
     */
    Collection<RequestSummaryVo> nationalSearch(SearchRequestCriteriaVo criteria, UserVo user);

    /**
     * saves the new request
     * 
     * @param request to save
     * @param user {@link UserVo} performing the action
     * @return RequestVo
     */
    RequestVo create(RequestVo request, UserVo user);

    /**
     * saves the new request DO for testing only
     * 
     * @param request to save
     * @param insertAsNewRequest boolean
     * @param user {@link UserVo} performing the action
     * @return RequestDo
     */
    EplRequestDo createRequestDo(EplRequestDo request, boolean insertAsNewRequest, UserVo user);

    /**
     * saves the RequestDetail for existing Request
     * 
     * @param request to save
     * @param user {@link UserVo} performing the action
     * @return RequestVo
     */
    RequestVo createRequestDetails(RequestVo request, UserVo user);

    /**
     * Deletes all requests and request details for the given {@link ManagedItemVo}
     * 
     * @param item {@link ManagedItemVo} for which to delete requests
     */
    void delete(ManagedItemVo item);
    
    /**
     * deletes the RequestDetail for existing Request
     * 
     * @param requestId String
     */
    void deleteRequestDetails(String requestId);
    
    /**
     * Deletes all requests and request details for the given request
     * 
     * @param request {@link RequestVo} to delete
     */
    void delete(RequestVo request);

    /**
     * update the new Request do update all the requestDetails this method must take in a request
     * 
     * @param request to save
     * @param user {@link UserVo} performing the action
     * @return updated RequestVo
     */
    RequestVo updateRequest(RequestVo request, UserVo user);

    /**
     * Method to support local searches for pending requests. Local searches are performed using the date the request was
     * made.
     * 
     * @param criteria SearchRequestCriteriaVo
     * @return collection of RequestSummaryVO
     */
    List<RequestSummaryVo> localSearch(SearchRequestCriteriaVo criteria);

    /**
     * gets all the reports for the item id
     * 
     * @param itemId the unique enterprise level id for item
     * @param entityType Product/NDC/OI
     * @return Collection of RequestVo
     * 
     */
    Collection<RequestVo> retrieveReportsByItemId(String itemId, EntityType entityType);

    /**
     * gets the first request id for an item, given id and entity type. Returns null string if no id found
     * 
     * @param itemId the unique enterprise level id for item
     * @param entityType Product/NDC/OI/DRUG_CLASS or any other managed domain
     * @return String
     * 
     */
    String getModificationRequestId(String itemId, EntityType entityType);

}
