/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.bean;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.RequestCompletedException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.RequestSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.RequestService;


/**
 * Search and retrieve requests.
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class RequestServiceBean extends AbstractPepsStatelessSessionBean<RequestService> implements RequestService {
    private static final long serialVersionUID = 1L;

    /**
     * Retrieve request detail by ID.
     * 
     * @param id RequestVo ID to retrieve
     * @return RequestVo
     * 
     * @throws ItemNotFoundException if cannot find Request with given ID
     * @throws RequestCompletedException if the request has already been completed
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.RequestService#retrieve(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public RequestVo retrieve(String id) throws ItemNotFoundException, RequestCompletedException {
        return getService().retrieve(id);
    }

    /**
     * Retrieve all the RequestVo for the ManagedItemVo with the given ID and {@link EntityType}
     * 
     * @param itemId ID for the {@link ManagedItemVo}
     * @param itemType {@link EntityType} for which to retrieve {@link RequestVo}
     * @return Collection<RequestVo>
     * 
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.RequestService#retrieve(java.lang.String,
     *      gov.va.med.pharmacy.peps.common.vo.EntityType)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public Collection<RequestVo> retrieve(String itemId, EntityType itemType) throws ItemNotFoundException {
        return getService().retrieve(itemId, itemType);
    }

    /**
     * Retrieve request by eplId 
     *  
     * @param itemId an enterprise level id used to identify the request being processed.
     * @param entityType entityType
     * @return RequestVo
     * 
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability
     *      #retrieveRequestDetailById(java.lang.String)
     *           
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public RequestVo retrieveNonCompletedRequest(String itemId, EntityType entityType) throws ItemNotFoundException {
        return getService().retrieveNonCompletedRequest(itemId, entityType);
    }
    
    /**
     * Search for the pending requests
     * 
     * @param criteria for searchCriteria
     * @param user user
     * @return Collection<RequestSummaryVo>
     * 
     * @throws ValueObjectValidationException if error in criteria data
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.RequestService#search(
     *      gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo, UserVo)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public Collection<RequestSummaryVo> search(SearchRequestCriteriaVo criteria, UserVo user)
        throws ValueObjectValidationException {
        return getService().search(criteria, user);
    }
}
