/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplRequestDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplRequestDetailDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplRequestDetailDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplRequestDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.RequestConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.RequestSummaryConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * handles add/mod requests
 */
public class RequestDomainCapabilityImpl implements RequestDomainCapability {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RequestDomainCapabilityImpl.class);
    private static final String YES = "Y";
    private static final String NO = "N";

    private EplRequestDao eplRequestDao;
    private EplRequestDetailDao eplRequestDetailDao;

    private RequestConverter requestConverter;
    private RequestSummaryConverter requestSummaryConverter;

    /**
     * Method to search for pending requests and filtering results based on given search criteria.
     * @param searchCriteria SearchRequestCriteriaVo
     * @param user UserVo - the user performing the search, used to filter out requests they can't take action on
     * 
     * @return collection of RequestSummaryVO
     */
    public List<RequestSummaryVo> nationalSearch(SearchRequestCriteriaVo searchCriteria, UserVo user) {
        Criteria criteria = createRequestCriteria(searchCriteria);

        if (!searchCriteria.isAllRequests()) {
            Criterion pendingFirstReview = Restrictions.eq(EplRequestDo.REQUEST_STATUS, RequestState.PENDING_FIRST_REVIEW
                .toString());
            Criterion pendingSecondReview = Restrictions.eq(EplRequestDo.REQUEST_STATUS, RequestState.PENDING_SECOND_REVIEW
                .toString());

            Criterion addition = Restrictions.eq(EplRequestDo.REQUEST_TYPE, RequestType.ADDITION.toString());
            Criterion modification = Restrictions.eq(EplRequestDo.REQUEST_TYPE, RequestType.MODIFICATION.toString());

            Criterion notMarkedForPsrOrUnderReview = Restrictions.and(Restrictions.eq(EplRequestDo.MARKED_FOR_PSR, NO),
                Restrictions.eq(EplRequestDo.UNDER_REVIEW_FLAG, NO));

            Disjunction or = Restrictions.disjunction();

            if (searchCriteria.isMarkedForPepsSecondReview()) {
                Criterion markedForPsr = Restrictions.eq(EplRequestDo.MARKED_FOR_PSR, YES);
                or.add(Restrictions.and(markedForPsr, pendingSecondReview));
            }

            if (searchCriteria.isPendingAddition()) {
                Criterion criterion = Restrictions.and(pendingFirstReview, addition);
                or.add(Restrictions.and(criterion, notMarkedForPsrOrUnderReview));
            }

            if (searchCriteria.isPendingModification()) {
                Criterion criterion = Restrictions.and(pendingFirstReview, modification);
                or.add(Restrictions.and(criterion, notMarkedForPsrOrUnderReview));
            }

            if (searchCriteria.isPendingSecondApprovalAddition()) {
                Criterion criterion = Restrictions.and(pendingSecondReview, addition);
                or.add(Restrictions.and(criterion, notMarkedForPsrOrUnderReview));
            }

            if (searchCriteria.isPendingSecondApprovalModification()) {
                Criterion criterion = Restrictions.and(pendingSecondReview, modification);
                or.add(Restrictions.and(criterion, notMarkedForPsrOrUnderReview));
            }

            if (searchCriteria.isProblemReports()) {
                or.add(Restrictions.and(Restrictions.eq(EplRequestDo.REQUEST_TYPE, RequestType.PROBLEM_REPORT.toString()),
                    notMarkedForPsrOrUnderReview));
            }

            if (searchCriteria.isUnderReview()) {
                or.add(Restrictions.eq(EplRequestDo.UNDER_REVIEW_FLAG, YES));
            }
            
            if (searchCriteria.isNotLastReviewer()) {
                or.add(Restrictions.ne(EplRequestDo.LAST_REVIEWER_NAME, 
                    user.getFirstName() + "_" + user.getLastName() + "_" 
                    + user.getUsername() + "_" + user.getLocation()));
            }
            
            criteria.add(or);
        }

        List<EplRequestDo> requests = criteria.list();

        return requestSummaryConverter.convert(requests);
    }

    /**
     * Perform local request search.
     * 
     * @param searchCriteria SearchRequestCriteriaVo
     * @return collection of RequestSummaryVO
     */
    public List<RequestSummaryVo> localSearch(SearchRequestCriteriaVo searchCriteria) {
        Criteria criteria = createRequestCriteria(searchCriteria);
        
        if (searchCriteria.getLocalRequest().isOnlyLocal()) {
            criteria.add(Restrictions.eq(EplRequestDo.SITE_NUMBER, searchCriteria.getSiteNumber()));
        } else if (!searchCriteria.isRequestDateUse()) {
            java.util.Date endDate = new java.util.Date();
            java.util.Date startDate = 
                new java.util.Date(endDate.getTime() - (SearchRequestCriteriaVo.ONE_DAY * PPSConstants.I180));
            criteria.add(Restrictions.ge(EplRequestDo.CREATED_DTM, startDate));
            criteria.add(Restrictions.le(EplRequestDo.CREATED_DTM, endDate));
        } else {
            criteria.add(Restrictions.ge(EplRequestDo.CREATED_DTM, searchCriteria.getSearchRequestStartDate()));
            criteria.add(Restrictions.le(EplRequestDo.CREATED_DTM, searchCriteria.getSearchRequestEndDate()));
        }
        

        List<EplRequestDo> data = criteria.list();

        return requestSummaryConverter.convert(data);
    }

    /**
     * Create the base request Criteria object used at both local and national.
     * 
     * @param searchCriteria {@link SearchRequestCriteriaVo}
     * @return {@link Criteria}
     */
    private Criteria createRequestCriteria(SearchRequestCriteriaVo searchCriteria) {
        Criteria criteria = eplRequestDao.getCriteria();
        criteria.add(Restrictions.ne(EplRequestDo.REQUEST_STATUS, RequestState.COMPLETED.toString()));

        if (searchCriteria.getSearchDomain() != null && searchCriteria.getSearchDomain().isDomainSearch()) {
            List<EntityType> domains = EntityType.domains();
            List<String> domainStrings = new ArrayList<String>(domains.size());

            for (EntityType entityType : domains) {
                domainStrings.add(entityType.toString());
            }

            criteria.add(Restrictions.in(EplRequestDo.ITEM_TYPE, domainStrings));
        } else {
            List<EntityType> entities = EntityType.entities();
            List<String> entityStrings = new ArrayList<String>(entities.size());

            for (EntityType entityType : entities) {
                entityStrings.add(entityType.toString());
            }

            criteria.add(Restrictions.in(EplRequestDo.ITEM_TYPE, entityStrings));
        }

        return criteria;
    }

    /**
     * Retrieve request by request id
     * 
     * @param requestId an enterprise level id
     * @return RequestVo
     * 
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability
     *      #retrieveRequestDetailById(java.lang.String)
     */
    public RequestVo retrieve(String requestId) throws ItemNotFoundException {
        try {
            EplRequestDo reqDo = eplRequestDao.retrieve(new Long(requestId));
            reqDo.setEplRequestDetails(retrieveDetails(reqDo));
            RequestVo requestVo = requestConverter.convert(reqDo);

            return requestVo;
        } catch (ObjectNotFoundException e) {
            throw new ItemNotFoundException(ItemNotFoundException.ITEM_NOT_FOUND, new Object[] { requestId });

        }

    }
    
    /**
     * Retrieve request by eplId 
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
    public RequestVo retrieveNonCompletedRequest(String itemId, EntityType entityType) throws ItemNotFoundException {

        StringBuffer queryBuffer =
            new StringBuffer(HqlBuilder.create(PPSConstants.SELECT_ITEM_FROM).append(EplRequestDo.class)
                .append("item WHERE item.").append(EplRequestDo.REQUEST_STATUS)
                .append("  LIKE 'PENDING%' AND ").toString());
            

        if (entityType.equals(EntityType.NDC)) {
            queryBuffer.append(EplRequestDo.ITEM_TYPE).append(" LIKE 'NDC' AND EPL_ID_NDC_FK = ").append(itemId);
        } else if (entityType.equals(EntityType.PRODUCT)) {
            queryBuffer.append(EplRequestDo.ITEM_TYPE).append(" LIKE 'PRODUCT' AND EPL_ID_PRODUCT_FK = ")
                .append(itemId);
        } else if (entityType.equals(EntityType.ORDERABLE_ITEM)) {
            queryBuffer.append(EplRequestDo.ITEM_TYPE).append(" LIKE 'ORDERABLE_ITEM' AND EPL_ID_OI_FK = ")
                .append(itemId);
        } else  {
            queryBuffer.append(EplRequestDo.ITEM_TYPE).append(" LIKE '").append(entityType.toString()).
                append("' AND DOMAIN_ID = ").append(itemId);
        }
        
        queryBuffer.append(" Order by ID DESC");
           
        RequestVo requestVo = null;
        
        try {
            List<EplRequestDo> requestData = eplRequestDao.executeHqlQuery(queryBuffer.toString());
            
            if (requestData.size() == 1) {
                requestVo = requestConverter.convert(requestData.get(0));
            } else if (requestData.size() > 1) {
                LOG.error("There were " + requestData.size() + " requests of type " 
                    + entityType.toString() + " with the id of " + itemId);
                requestVo = requestConverter.convert(requestData.get(0));
            }
        } catch (ObjectNotFoundException e) {
            throw new ItemNotFoundException(ItemNotFoundException.ITEM_NOT_FOUND, new Object[] { itemId });
        }
        
        return requestVo;
    }
    
    /**
     * Deletes all completed requests 
     *  
     */
    public void deleteCompletedRequest() {

        
        StringBuffer queryBuffer =
            new StringBuffer(HqlBuilder.create(PPSConstants.SELECT_ITEM_FROM).append(EplRequestDo.class)
                .append("item WHERE item.").append(EplRequestDo.REQUEST_STATUS)
                .append(" LIKE 'COMPLETED'").toString());

        List<EplRequestDo> requestData = eplRequestDao.executeHqlQuery(queryBuffer.toString());
            
        if (requestData != null && requestData.size() > 0) {
            for (EplRequestDo request : requestData) {
                String detailsQuery = "delete from EplRequestDetailDo where REQUEST_ID_FK = " + request.getId();
                eplRequestDetailDao.executeQuery(detailsQuery);
            }
        }

        StringBuffer query = new StringBuffer();
        query.append("delete from EplRequestDo where REQUEST_STATUS LIKE 'COMPLETED'");

        eplRequestDao.executeQuery(query.toString());
       
    }

    /**
     * gets all the requests associated with the item
     * 
     * @param itemId the unique enterprise level id for item
     * @param entityType Product/NDC/OI/DRUG_CLASS or any other managed domain
     * @return Collection of RequestVo
     * 
     * @throws ItemNotFoundException if ManagedItemVo with given ID cannot be found
     */
    public Collection<RequestVo> retrieve(String itemId, EntityType entityType) throws ItemNotFoundException {
        Collection<RequestVo> reqeusts = new ArrayList<RequestVo>();
        List<EplRequestDo> requestData;

        try {
            if (entityType.isNdc()) {
                EplNdcDo eplNdc = new EplNdcDo();
                eplNdc.setEplId(new Long(itemId));
                requestData = eplRequestDao.retrieve(EplRequestDo.EPL_NDC, eplNdc);
            } else if (entityType.isProduct()) {
                EplProductDo eplProduct = new EplProductDo();
                eplProduct.setEplId(new Long(itemId));
                requestData = eplRequestDao.retrieve(EplRequestDo.EPL_PRODUCT, eplProduct);
            } else if (entityType.isOrderableItem()) {
                EplOrderableItemDo eplOrderableItem = new EplOrderableItemDo();
                eplOrderableItem.setEplId(new Long(itemId));
                requestData = eplRequestDao.retrieve(EplRequestDo.EPL_ORDERABLE_ITEM, eplOrderableItem);
            } else { // entity type is a managed domain like Drug_CLASS, Manufactureres etc
                requestData = eplRequestDao.retrieve(EplRequestDo.DOMAIN_ID, new Long(itemId));
            }
        } catch (ObjectNotFoundException e) {
            throw new ItemNotFoundException(ItemNotFoundException.ITEM_NOT_FOUND, new Object[] { itemId });

        }

        for (EplRequestDo requestDo : requestData) {
            requestDo.setEplRequestDetails(retrieveDetails(requestDo));
            RequestVo requestVo = requestConverter.convert(requestDo);
            reqeusts.add(requestVo);
        }

        return reqeusts;
    }

    /**
     * gets the first request id for an item, given id and entity type. Returns null string if no id found
     * 
     * @param itemId the unique enterprise level id for item
     * @param entityType Product/NDC/OI/DRUG_CLASS or any other managed domain
     * @return String
     * 
     */
    public String getModificationRequestId(String itemId, EntityType entityType) {
        Criteria criteria = eplRequestDao.getCriteria();
        criteria.add(Restrictions.eq(EplRequestDo.REQUEST_TYPE, RequestType.MODIFICATION.name()));
        criteria.add(Restrictions.ne(EplRequestDo.REQUEST_STATUS, RequestState.COMPLETED.name()));

        if (EntityType.NDC.equals(entityType)) {
            EplNdcDo ndc = new EplNdcDo();
            ndc.setEplId(Long.valueOf(itemId));
            criteria.add(Restrictions.eq(EplRequestDo.EPL_NDC, ndc));
        } else if (EntityType.PRODUCT.equals(entityType)) {
            EplProductDo prod = new EplProductDo();
            prod.setEplId(Long.valueOf(itemId));
            criteria.add(Restrictions.eq(EplRequestDo.EPL_PRODUCT, prod));
        } else if (EntityType.ORDERABLE_ITEM.equals(entityType)) {
            EplOrderableItemDo order = new EplOrderableItemDo();
            order.setEplId(Long.valueOf(itemId));
            criteria.add(Restrictions.eq(EplRequestDo.EPL_ORDERABLE_ITEM, order));
        } else { // entity type is a managed domain like Drug_CLASS, Manufacturers etc
            criteria.add(Restrictions.eq(EplRequestDo.DOMAIN_ID, Long.valueOf(itemId)));
        }

        String requestId = null;

        try {
            EplRequestDo request = (EplRequestDo) criteria.uniqueResult();

            if (request != null) {
                requestId = request.getId().toString();
            }
        } catch (ObjectNotFoundException e) {
            requestId = null;
            LOG.warn("Object not found, returning null!", e);
        }

        return requestId;
    }

    /**
     * gets all the reports for the item id
     * 
     * @param itemId the unique enterprise level id for item
     * @param entityType Product/NDC/OI
     * @return Collection of RequestVo
     * 
     */
    public Collection<RequestVo> retrieveReportsByItemId(String itemId, EntityType entityType) {

        List<Criterion> allCriterias = new ArrayList<Criterion>();
        Criterion crit = null;
        crit = Restrictions.eq(EplRequestDo.REQUEST_TYPE, "REPORT");
        allCriterias.add(crit);

        if (entityType.isNdc()) {
            EplNdcDo ndc = new EplNdcDo();
            ndc.setEplId(new Long(itemId));
            crit = Restrictions.eq(EplRequestDo.EPL_NDC, ndc);
            allCriterias.add(crit);
        } else if (entityType.isProduct()) {
            EplProductDo prod = new EplProductDo();
            prod.setEplId(new Long(itemId));
            crit = Restrictions.eq(EplRequestDo.EPL_PRODUCT, prod);
            allCriterias.add(crit);
        } else if (entityType.isOrderableItem()) {
            EplOrderableItemDo order = new EplOrderableItemDo();
            order.setEplId(new Long(itemId));
            crit = Restrictions.eq(EplRequestDo.EPL_ORDERABLE_ITEM, order);
            allCriterias.add(crit);
        }

        List<EplRequestDo> requests = eplRequestDao.retrieve(allCriterias);
        Collection<RequestVo> requestVos = new ArrayList<RequestVo>();

        for (EplRequestDo requestDo : requests) {
            RequestVo requestVo = requestConverter.convert(requestDo);
            requestVos.add(requestVo);
        }

        return requestVos;

    }

    /**
     * gets all the requestDetails with the request
     * 
     * @param requestDo EplRequestDo
     * 
     * @return Set EplRequestDetailDo
     * 
     */
    private Set<EplRequestDetailDo> retrieveDetails(EplRequestDo requestDo) {

        java.util.List<EplRequestDetailDo> reqDetails = eplRequestDetailDao.retrieve(EplRequestDetailDo.EPL_REQUEST,
            requestDo);

        Set setDetails = new HashSet();
        setDetails.addAll(reqDetails);

        return setDetails;
    }

    /**
     * Saves the request to the database
     * 
     * @param request RequestVo
     * @param user {@link UserVo} performing the action
     * @return RequestVo
     * 
     */
    public RequestVo create(RequestVo request, UserVo user) {

        EplRequestDo requestDo = requestConverter.convert(request);

        EplRequestDo newRequestDo = createRequestDo(requestDo, true, user);

        // convert do to vo
        // send in itemid
        RequestVo newRequestVo = requestConverter.convert(newRequestDo);

        return newRequestVo;
    }

    /**
     * Updates the request to the database
     * 
     * @param request RequestVo
     * @param user {@link UserVo} performing the action
     * @return RequestVo updatedRequest
     */
    public RequestVo updateRequest(RequestVo request, UserVo user) {
        EplRequestDo requestDo = requestConverter.convert(request);

        if (requestDo.getEplRequestDetails() != null) {
            eplRequestDetailDao.update(requestDo.getEplRequestDetails(), user);
        }

        eplRequestDao.update(requestDo, user);

        return request;
    }

    /**
     * Insert the given DO. For testing purposes only needs to be cleaned up the ids are set to increment in all of the
     * hbm.xml files for request
     * 
     * @param request EplRequestDo
     * @param insertAsNewRequest boolean indicating a new request
     * @param user {@link UserVo} performing the action
     * @return DO inserted DO with new ID
     */
    public EplRequestDo createRequestDo(EplRequestDo request, boolean insertAsNewRequest, UserVo user) {

        EplRequestDo newRequest = null;

        if (insertAsNewRequest) {

            newRequest = eplRequestDao.insert(request, user);
        } else {
            newRequest = request;
        }

        // have to do this as the eplndc, eplOrderableItem and eplProduct is null;
        // the dao.insert returns the saved instance with no associations
        // check to see if request had details etc
        Set requestDetails = request.getEplRequestDetails();

        if (requestDetails.size() > 0) {

            Set insertedDetailCollection = new HashSet();

            Iterator i = requestDetails.iterator();

            while (i.hasNext()) {
                EplRequestDetailDo detail = (EplRequestDetailDo) (i.next());

                // If detail has an id then it already exists in the database
                if (detail.getId() != null) {
                    insertedDetailCollection.add(detail);
                    continue;
                }

                EplRequestDetailDo insertDetail = eplRequestDetailDao.insert(detail, user);

                insertedDetailCollection.add(insertDetail);
            }

            newRequest.setEplRequestDetails(insertedDetailCollection);
        }

        return newRequest;

    }

    /**
     * this method inserts into request details , old and new values it assumes a request already exists
     * 
     * @param request The request to insert details into
     * @param user {@link UserVo} performing the action
     * @return newRequestVo
     */
    public RequestVo createRequestDetails(RequestVo request, UserVo user) {

        EplRequestDo requestDo = requestConverter.convert(request);

        EplRequestDo newRequestDo = createRequestDo(requestDo, false, user);

        RequestVo newRequestVo = requestConverter.convert(newRequestDo);

        return newRequestVo;

    }

    /**
     * Deletes all requests and request details for the given request
     * 
     * @param request {@link RequestVo} to delete
     */
    public void delete(RequestVo request) {
        deleteRequestDetails(request.getId());
        eplRequestDao.delete(EplRequestDo.ID, Long.valueOf(request.getId()));
    }

    /**
     * Deletes all requests and request details for the given {@link ManagedItemVo}
     * 
     * @param item {@link ManagedItemVo} for which to delete requests
     */
    public void delete(ManagedItemVo item) {
        deleteRequestDetails(item.getId());

        StringBuffer query = new StringBuffer();
        query.append("delete from EplRequestDo where ");

        if (item instanceof OrderableItemVo) {
            query.append("EPL_ID_OI_FK  = ");
        } else if (item instanceof ProductVo) {
            query.append("EPL_ID_PRODUCT_FK  = "); 
        } else if (item instanceof NdcVo) {
            query.append("EPL_ID_NDC_FK  = ");
        } else {
            query.append("DOMAIN_ID = ");
        }
        
        query.append(item.getId() + " AND ITEM_TYPE = '" + item.getEntityType().toString() + "'");
        eplRequestDao.executeQuery(query.toString());

    }

    /**
     * Remove request details by deleting epl_request_details with the request's id
     * 
     * @param id identifier for request details
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability#deleteRequestDetails(java.lang.String)
     */
    public void deleteRequestDetails(String id) {
        eplRequestDetailDao.delete(EplRequestDetailDo.EPL_REQUEST_ID, Long.valueOf(id));
    }

    /**
     * setEplRequestDao
     * 
     * @param eplRequestDao eplRequestDao property
     */
    public void setEplRequestDao(EplRequestDao eplRequestDao) {
        this.eplRequestDao = eplRequestDao;
    }

    /**
     * setEplRequestDetailDao
     * 
     * @param eplRequestDetailDao eplRequestDetailDao property
     */
    public void setEplRequestDetailDao(EplRequestDetailDao eplRequestDetailDao) {
        this.eplRequestDetailDao = eplRequestDetailDao;
    }

    /**
     * setRequestConverter
     * 
     * @param requestConverter requestConverter property
     */
    public void setRequestConverter(RequestConverter requestConverter) {
        this.requestConverter = requestConverter;
    }

    /**
     * setRequestSummaryConverter
     * 
     * @param requestSummaryConverter requestSummaryConverter property
     */
    public void setRequestSummaryConverter(RequestSummaryConverter requestSummaryConverter) {
        this.requestSummaryConverter = requestSummaryConverter;
    }

}
