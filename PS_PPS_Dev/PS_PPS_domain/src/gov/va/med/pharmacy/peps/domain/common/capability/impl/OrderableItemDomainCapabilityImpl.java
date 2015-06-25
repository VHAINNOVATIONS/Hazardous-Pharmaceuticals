/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.DomainValidationException;
import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.DateFormatUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.PaginatedList;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderableItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ReportDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplLabDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplOiAdminSchedAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplOiDrugTextLAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplOiDrugTextNAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplOiMedRouteAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplOrderableItemDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProductDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVitalDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplAdminScheduleDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDosageFormDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugTextDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplLabDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalMedRouteDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplMultiTextDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiAdminSchedAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiDrugTextLAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiDrugTextNAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiMedRouteAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiScheduleTypeDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplStandardMedRouteDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDfDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfAssocValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfLovDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfNonlistValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVitalDo;
import gov.va.med.pharmacy.peps.domain.common.utility.SearchQueryUtility;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.OrderableItemConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * Perform CRUD operations on OrderableItemVo
 */
public class OrderableItemDomainCapabilityImpl extends ManagedItemDomainCapabilityImpl<OrderableItemVo, EplOrderableItemDo>
        implements OrderableItemDomainCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(OrderableItemDomainCapabilityImpl.class);

    private static final String SELECITEMFROM = "SELECT item FROM";
    private static final String ITEMJOIN = "item JOIN";
    private static final String ITEM = "item";
    private static final String OWNERJOIN = "owners JOIN";
    private static final String OWNER = "owners";
    private EplOrderableItemDao eplOrderableItemDao;
    private EplLabDao eplLabDao;
    private EplVitalDao eplVitalDao;
    private EplProductDao eplProductDao;
    private EplOiMedRouteAssocDao eplOiMedRouteAssocDao;
    private EplOiDrugTextLAssocDao eplOiDrugTextLAssocDao;
    private EplOiDrugTextNAssocDao eplOiDrugTextNAssocDao;
    private ProductDomainCapability productDomainCapability;
    private EplOiAdminSchedAssocDao eplOiAdminSchedAssocDao;
    private ReportDomainCapability reportDomainCapability;

    private OrderableItemConverter orderableItemConverter;

    /**
     * Insert the given {@link ManagedItemVo} with a duplicate check.
     * 
     * @param managedItem {@link ManagedItemVo}
     * @param user {@link UserVo} performing the action
     * @return {@link ManagedItemVo} inserted with new ID
     * @throws DuplicateItemException if item already exists by uniqueness fields
     */
    @Override
    public OrderableItemVo create(OrderableItemVo managedItem, UserVo user) throws DuplicateItemException {

        if (existsByUniquenessFields(managedItem)) {
            throw new DuplicateItemException(DuplicateItemException.OI_DUPLICATE_ITEM);
        }

        return createWithoutDuplicateCheck(managedItem, user);
    }

    /**
     * OIS cannot be inactivated if they have active products
     * 
     * @param orderableItemVo orderableItemVo
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    @Override
    public void checkForActiveDependencies(OrderableItemVo orderableItemVo, UserVo user) throws ValidationException {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT  item FROM ").append(EplProductDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = " WHERE EPL_ID_OI_FK = " + orderableItemVo.getId() + " AND ITEM_STATUS LIKE 'ACTIVE'";
        query.append(whereClause);

        List<EplProductDo> products = eplProductDao.executeHqlQuery(query.toString());

        if (products == null || products.size() == 0) {
            return;
        }

        throw new ValidationException(ValidationException.INACTIVATE_ORDERABLEITEM,
                                      products.size(), products.get(0).getVaProductName());

    }

    /**
     * Insert the given OrderableItemVo without duplicate check.
     * 
     * @param orderableItem OrderableItemVo
     * @param user {@link UserVo} performing the action
     * @return OrderableItemVo inserted OrderableItemVo with new ID
     */
    @Override
    public OrderableItemVo createWithoutDuplicateCheck(OrderableItemVo orderableItem, UserVo user) {
        if (orderableItem.getId() == null) {
            orderableItem.setId(getSeqNumDomainCapability().generateId(orderableItem.getEntityType(), user));

            // only assign vaDfOwnerId if dataFields exists
            if (!(orderableItem.getVaDataFields().isEmpty())) {
                orderableItem.getVaDataFields().setVaDfOwnerId(getSeqNumDomainCapability().generatedOwnerId(user));
            }
        }

        EplOrderableItemDo orderableItemDo = orderableItemConverter.convert(orderableItem);
        eplOrderableItemDao.insert(orderableItemDo, user);

        if (orderableItemDo.getEplOiMedRouteAssocs() != null && orderableItemDo.getEplOiMedRouteAssocs().size() > 0) {
            eplOiMedRouteAssocDao.insert(orderableItemDo.getEplOiMedRouteAssocs(), user);
        }

        if (orderableItemDo.getEplOiAdminSchedAssocs() != null && orderableItemDo.getEplOiAdminSchedAssocs().size() > 0) {
            eplOiAdminSchedAssocDao.insert(orderableItemDo.getEplOiAdminSchedAssocs(), user);
        }

//        if (orderableItemDo.getEplLabs() != null && orderableItemDo.getEplLabs().size() > 0) {
//            eplLabDao.insert(orderableItemDo.getEplLabs(), user);
//        }
//
//        if (orderableItemDo.getEplVitals() != null && orderableItemDo.getEplVitals().size() > 0) {
//            eplVitalDao.insert(orderableItemDo.getEplVitals(), user);
//        }
//
//        if (orderableItemDo.getEplOiDrugTextLAssocs() != null && orderableItemDo.getEplOiDrugTextLAssocs().size() > 0) {
//            eplOiDrugTextLAssocDao.insert(orderableItemDo.getEplOiDrugTextLAssocs(), user);
//        }

        if (orderableItemDo.getEplOiDrugTextNAssocs() != null && orderableItemDo.getEplOiDrugTextNAssocs().size() > 0) {
            eplOiDrugTextNAssocDao.insert(orderableItemDo.getEplOiDrugTextNAssocs(), user);
        }

        //  Set productVoCollection = new HashSet();

        // only call save if data fields exists
        if (!(orderableItem.getVaDataFields().isEmpty())) {
            getDataFieldsDomainCapability().insertEplVaDfOwners(null, null, orderableItemDo, null,
                                                                orderableItemDo.getEplVadfOwners(), user);
        }

        return orderableItem;
    }

    /**
     * Deletes an {@link OrderableItemVo} from the database, including its association tables, VA data fields, partial saves,
     * and requests.
     * <p>
     * This method does not delete associated item audit history records! It is assumed that those records would be
     * re-associated to a new Orderable Item.
     * 
     * @param item {@link OrderableItemVo} to delete
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.impl.ManagedItemDomainCapabilityImpl#delete(
     *  gov.va.med.pharmacy.peps.common.vo.ManagedItemVo)
     */
    @Override
    public void delete(OrderableItemVo item) {
        EplOrderableItemDo oi = orderableItemConverter.convert(item);

        eplOiAdminSchedAssocDao.delete(EplOiAdminSchedAssocDo.ORDERABLE_ITEM_EPL_ID, oi.getEplId());
        eplOiMedRouteAssocDao.delete(EplOiMedRouteAssocDo.ORDERABLE_ITEM_EPL_ID, oi.getEplId());
        eplLabDao.delete(EplLabDo.ORDERABLE_ITEM_EPL_ID, oi.getEplId());
        eplVitalDao.delete(EplVitalDo.ORDERABLE_ITEM_EPL_ID, oi.getEplId());
        eplOiDrugTextLAssocDao.delete(EplOiDrugTextLAssocDo.ORDERABLE_ITEM_EPL_ID, oi.getEplId());
        eplOiDrugTextNAssocDao.delete(EplOiDrugTextNAssocDo.ORDERABLE_ITEM_EPL_ID, oi.getEplId());

        super.delete(item);
    }

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link ManagedItemVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(OrderableItemVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplOrderableItemDo.OI_NAME, item.getOiName()));

        if (item.getId() != null && item.getId().trim().length() > 0) {
            criteria.add(Restrictions.ne(EplOrderableItemDo.EPL_ID, Long.valueOf(item.getId())));
        }

        return criteria;
    }

    /**
     * Retrieve the OrderableItemVo with the given ID.
     * 
     * @param nationalItemId OrderableItem ID
     * @return Collection OrderableItemVo
     */
    @Override
    public List<OrderableItemVo> retrieveChildren(String nationalItemId) {

        List<EplOrderableItemDo> localItems =
                eplOrderableItemDao.retrieve(EplOrderableItemDo.PARENT_OI_EPL_ID, Long.valueOf(nationalItemId));
        List<OrderableItemVo> returnedLocalItems = orderableItemConverter.convert(localItems);

        return returnedLocalItems;
    }

    /**
     * Retrieve the number of children this orderable item
     * 
     * @param parentId String ID
     * @return int
     */
    @Override
    public int retrieveChildrenCount(String parentId) {

        int count = 0;

        if (parentId != null) {
            String query = "select count(*) from EplOrderableItemDo order where order.eplOrderableItem = " + parentId;
            Long rowsReturned = (Long) eplOrderableItemDao.executeHqlQuery(query).iterator().next();
            count = rowsReturned.intValue();
        }

        return count;
    }

    /**
     * Search for orderable Items
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @return List<OrderableItemVo>
     */
    private List<OrderableItemVo> simpleAllSearch(SearchCriteriaVo searchCriteria) {

        List<Long> ids = reportDomainCapability.getSimpleSearchIds(searchCriteria);

        Criteria criteria = getDataAccessObject().getCriteria("ITEM");
        criteria = createSimpleAllSearchTermCriteria(searchCriteria, criteria, ids, true);

        // get one page of data
        criteria.setFirstResult(searchCriteria.getStartRow());
        criteria.setMaxResults(searchCriteria.getPageSize());

        List<EplOrderableItemDo> data = criteria.list();

        // get row count for all results for the orderableItem list of ids
        //Criteria count = createSimpleAllSearchTermCriteria(searchCriteria, criteria, ids,  false);
        //int fullSize = getDataAccessObject().getCount(count);
        int fullSize = ids.size();

        List<OrderableItemVo> valueObjects = getConverter().convertSearch(data);

        return new PaginatedList<OrderableItemVo>(valueObjects, fullSize, searchCriteria.getSortedFieldKey(),
            searchCriteria.getSortOrder(), searchCriteria.getStartRow(), searchCriteria.getPageSize());

//        List<Long> ids = reportDomainCapability.getSimpleSearchIds(searchCriteria);
//        
//        if (ids.size() < 1) {
//            return new ArrayList<OrderableItemVo>();
//        }
//        
//        StringBuffer query = new StringBuffer();
//        query.append(HqlBuilder.create("SELECT item FROM").append(EplOrderableItemDo.class).append("item WHERE "));
//                
//        query.append(" EPL_ID IN ( ");
//        
//        for (int i = 0 ; i < ids.size() && i < 1000; i++) {
//            query.append(ids.get(i).toString()).append(",");
//        }
//        
//        String submitQuery = query.substring(0, query.toString().length() - 1) + " )";
//        
//       
//        List<EplOrderableItemDo> returnedDos = eplOrderableItemDao.executeHqlQuery(submitQuery);
//
//        return this.getConverter().convert(returnedDos);
    }

    /**
     * Search for orderable Items
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @return List<OrderableItemVo>
     */
    private List<OrderableItemVo> simpleAllSearchFullList(SearchCriteriaVo searchCriteria) {

        List<Long> ids = reportDomainCapability.getSimpleSearchIds(searchCriteria);

        Criteria criteria = getDataAccessObject().getCriteria("ITEM");
        criteria = createSimpleAllSearchTermCriteria(searchCriteria, criteria, ids, true);

        List<EplOrderableItemDo> data = criteria.list();

        // get row count for all results
        //Criteria count = createSimpleAllSearchTermCriteria(searchCriteria, criteria, ids,  false);
        //int fullSize = getDataAccessObject().getCount(count);
        int fullSize = ids.size();

        List<OrderableItemVo> valueObjects = getConverter().convertSearch(data);

        return new PaginatedList<OrderableItemVo>(valueObjects, fullSize, searchCriteria.getSortedFieldKey(),
            searchCriteria.getSortOrder(), searchCriteria.getStartRow(), searchCriteria.getPageSize());

    }

    /**
     * Search for orderable items.
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @return List<OrderableItemVo>
     */
    @Override
    public List<OrderableItemVo> search(SearchCriteriaVo searchCriteria) {

        List<OrderableItemVo> orderableItems = new ArrayList<OrderableItemVo>();
        List<OrderableItemVo> results = new ArrayList(searchCriteria.getPageSize());

//        List<OrderableItemVo> orderableItems2 = new ArrayList<OrderableItemVo>();

        if (searchCriteria.isAdvanced()) {

            SearchCriteriaVo currCriteria = searchCriteria;
            List<SearchTermVo> terms = new ArrayList<SearchTermVo>();

            for (SearchTermVo term : searchCriteria.expandSearchTerms()) {
                boolean isAndSearch = term.getAdvancedAndSearch();
                terms.add(term);

                if (isAndSearch) {
                    continue;
                } else {
                    currCriteria.setSearchTerms(terms);
                    orderableItems = searchAdvanced(currCriteria);
                    terms.clear();
                }

                results.addAll(orderableItems);

//                orderableItems2.addAll(orderableItems);
            }

//            for (int i = searchCriteria.getStartRow(); i < searchCriteria.getStartRow() + searchCriteria.getPageSize()
//                && i < orderableItems2.size(); i++) {
//                results.add(orderableItems2.get(i));
//            }

        } else {

            SearchTermVo searchTermVo = searchCriteria.getSearchTerms().get(0);

            if (searchTermVo.getSearchField().getFieldKey().equals(FieldKey.SEARCH_ALL_FIELDS)) {
                results = simpleAllSearch(searchCriteria);
            } else {
                results = simpleSearch(searchCriteria);
            }
        }

        return results;
    }

    /**
     * Search for orderable items.
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @return List<OrderableItemVo>
     */
    @Override
    public List<OrderableItemVo> searchFullList(SearchCriteriaVo searchCriteria) {
        List<OrderableItemVo> results;

        if (searchCriteria.isAdvanced()) {
            results = searchAdvanced(searchCriteria);
        } else {

            SearchTermVo searchTermVo = searchCriteria.getSearchTerms().get(0);

            if (searchTermVo.getSearchField().getFieldKey().equals(FieldKey.SEARCH_ALL_FIELDS)) {
                results = simpleAllSearchFullList(searchCriteria);
            } else {
                results = simpleSearchFullList(searchCriteria);
            }
        }

        return results;
    }

    /**
     * Search for Orderable Items with the given search criteria. This method returns only those Orderable Items that match
     * all the criteria
     * 
     * @param searchCriteria criteria for matching Orderable Items
     * @return List of OrderableItemVo
     */
    @Override
    public List<OrderableItemVo> searchAllAndAdvanced(SearchCriteriaVo searchCriteria) {

        Criteria orderableItemCriteria = eplOrderableItemDao.getCriteria();
        orderableItemCriteria.setMaxResults(PPSConstants.I100);

        // only do ndc, trade name and nonlist ones

        if (LocalUseSearchType.LOCAL_USE.equals(searchCriteria.getLocalUse())) {
            orderableItemCriteria.add(Restrictions.eq(EplOrderableItemDo.LOCAL_USE, "Y"));
        } else if (LocalUseSearchType.NOT_LOCAL_USE.equals(searchCriteria.getLocalUse())) {
            orderableItemCriteria.add(Restrictions.eq(EplOrderableItemDo.LOCAL_USE, "N"));
        }

        orderableItemCriteria =
                SearchQueryUtility.createItemStatusCriteria(searchCriteria.getItemStatus(), orderableItemCriteria,
                                                            EplOrderableItemDo.ITEM_STATUS);

        orderableItemCriteria =
                SearchQueryUtility.createRequestItemStatusCriteria(searchCriteria.getRequestStatus(), orderableItemCriteria,
                                                                   EplOrderableItemDo.REQUEST_STATUS);

        //    Criteria vitals = orderableItemCriteria.createCriteria(EplOrderableItemDo.EPL_VITALS);
        //    Criteria labs = orderableItemCriteria.createCriteria(EplOrderableItemDo.EPL_LABS);
        Criteria medRouteAssoc = orderableItemCriteria.createCriteria(EplOrderableItemDo.OI_MED_ROUTE);
        Criteria medRoute = medRouteAssoc.createCriteria(EplOiMedRouteAssocDo.EPL_LOCAL_MED_ROUTE);
        Criteria dosage = orderableItemCriteria.createCriteria(EplOrderableItemDo.EPL_DOSAGE_FORM);
        Criteria drugAssocLocal = orderableItemCriteria.createCriteria(EplOrderableItemDo.DRUG_TEXT_LOCAL);
        Criteria drug = drugAssocLocal.createCriteria(EplOiDrugTextLAssocDo.EPL_DRUG_TEXT);

        String value = searchCriteria.expandSearchTerms().get(0).formatValue();
        orderableItemCriteria.add(Restrictions.ilike(EplOrderableItemDo.OI_NAME, value, MatchMode.ANYWHERE));
        orderableItemCriteria.add(Restrictions.ilike(EplOrderableItemDo.REJECT_REASON_TEXT, value, MatchMode.ANYWHERE));
        orderableItemCriteria.add(Restrictions.ilike(EplOrderableItemDo.REQUEST_REJECT_REASON, value, MatchMode.ANYWHERE));
        orderableItemCriteria.add(Restrictions.ilike(EplOrderableItemDo.VISTA_OI_NAME, value, MatchMode.ANYWHERE));
        orderableItemCriteria.add(Restrictions.ilike(EplOrderableItemDo.NATIONAL_FORMULARY_INDICATOR, value));

//        vitals.add(Restrictions.ilike(EplVitalDo.VITAL_DISPLAY_FINISH_ORDER, value, MatchMode.ANYWHERE));
//        vitals.add(Restrictions.ilike(EplVitalDo.VITAL_DISPLAY_ORDER_ENTRY, value, MatchMode.ANYWHERE));
//        vitals.add(Restrictions.ilike(EplVitalDo.VITAL_DISPLAY_ADMINISTRATION, value, MatchMode.ANYWHERE));
//        labs.add(Restrictions.ilike(EplLabDo.LAB_DISPLAY_ADMINISTRATION, value, MatchMode.ANYWHERE));
//        labs.add(Restrictions.ilike(EplLabDo.LAB_DISPLAY_ORDER_ENTRY, value, MatchMode.ANYWHERE));
//        labs.add(Restrictions.ilike(EplLabDo.LAB_DISPLAY_FINISH_AN_ORDER, value, MatchMode.ANYWHERE));
        medRoute.add(Restrictions.ilike(EplLocalMedRouteDo.NAME, value, MatchMode.ANYWHERE));
        dosage.add(Restrictions.ilike(EplDosageFormDo.DF_NAME, value, MatchMode.ANYWHERE));
        drug.add(Restrictions.ilike(EplDrugTextDo.DRUG_TEXT_NAME, value, MatchMode.ANYWHERE));

        // this collection will never be empty as there will always be active/inactive orderable items
        List criteriaCollection = orderableItemCriteria.list();

        // collection to be returned
        List<EplOrderableItemDo> resultCollection = new ArrayList<EplOrderableItemDo>();

        // need to go through result Collection to check for all lists and non-lists;
        Iterator i = criteriaCollection.iterator();

        while (i.hasNext()) {
            EplOrderableItemDo data = (EplOrderableItemDo) (i.next());
            EplVadfOwnerDo owner = null;

            for (EplVadfOwnerDo tempOwner : data.getEplVadfOwners()) {
                owner = tempOwner;
                break;
            }

            // get all the data fields values
            for (EplVadfNonlistValueDo nonListDo : owner.getEplVadfNonlistValues()) {
                if (value.equalsIgnoreCase(nonListDo.getVaDfValue().toUpperCase())) {
                    if (!(resultCollection.contains(data))) {
                        resultCollection.add(data);
                    }
                }
            } // for nonLIstDo

            // check each items in the list against the nonListSearchTerms
            for (EplVadfAssocValueDo listDo : owner.getEplVadfAssocValues()) {
                if (value.equalsIgnoreCase(listDo.getKey().getListValue())) {

                    // check to see if in existing collection if not add
                    if (!(resultCollection.contains(data))) {
                        resultCollection.add(data);
                    }
                }

            }
        } // while

        List<OrderableItemVo> items = new ArrayList<OrderableItemVo>();

        for (EplOrderableItemDo data : resultCollection) {
            OrderableItemVo vo = orderableItemConverter.convert(data);
            vo.setProductCount(productDomainCapability.retrieveChildrenCount(vo.getId()));
            items.add(vo);
        }

        // return the items for teh searchAllAndAdvanced.
        return items;

    }

    /**
     * Search for Orderable Items with the given searchAdvanced criteria.
     * 
     * @param searchCriteria criteria for matching Orderable Items
     * @return List of OrderableItemVo
     */
    private List<OrderableItemVo> searchAdvanced(SearchCriteriaVo searchCriteria) {

        boolean isAndSearch = searchCriteria.isAdvancedAndSearch();
        AbstractAdvancedSearchHelper searchHelper;

        searchHelper = new AdvancedSearchHelper(eplOrderableItemDao, searchCriteria);

        int nonListCount = 0;
        List<SearchTermVo> nonListSearchTerms = new ArrayList<SearchTermVo>();
        List<SearchTermVo> listSearchTerms = new ArrayList<SearchTermVo>();
        List<SearchTermVo> listMultiTextSearchTerms = new ArrayList<SearchTermVo>();

        for (SearchTermVo term : searchCriteria.expandSearchTerms()) {

            // Skip no-op terms as they should not be in this searchCriteria.
            if (term.getFieldKey().equals(FieldKey.SEARCH_ALL_FIELDS)
                || term.getFieldKey().equals(FieldKey.SEARCH_ALL_DESIGNATED_FIELDS)
                || term.getFieldKey().equals(FieldKey.SEARCH_NO_FIELDS)) {
                continue;
            }

            isAndSearch = term.getAdvancedAndSearch();

            if (isAndSearch) {
                searchHelper = new AndAdvancedSearchHelper(eplOrderableItemDao, searchCriteria);
            }

            MatchMode mode = MatchMode.EXACT;

            if (term.getSearchType().equals(SearchType.CONTAINS)) {
                mode = MatchMode.ANYWHERE;
            } else if (term.getSearchType().equals(SearchType.BEGINS_WITH)) {
                mode = MatchMode.START;
            }

            if (term.getFieldKey().equals(FieldKey.OI_NAME)) {
                searchHelper.add(Restrictions.ilike(EplOrderableItemDo.OI_NAME, term.formatValue(), mode),
                    isAndSearch);
            } else if (term.getFieldKey().equals(FieldKey.PREVIOUSLY_MARKED_FOR_LOCAL_USE)) {
                searchHelper.add(Restrictions.ilike(EplOrderableItemDo.PREVIOUSLY_MARKED_FOR_LOCAL_USE, term.formatValue(),
                    mode), isAndSearch);
            } else if (term.getFieldKey().equals(FieldKey.REJECTION_REASON_TEXT)) {
                searchHelper.add(Restrictions.ilike(EplOrderableItemDo.REJECT_REASON_TEXT, term.formatValue(), mode),
                    isAndSearch);
            } else if (term.getFieldKey().equals(FieldKey.REQUEST_REJECTION_REASON)) {
                searchHelper.add(Restrictions.ilike(EplOrderableItemDo.REQUEST_REJECT_REASON, term.formatValue(), mode),
                    isAndSearch);
            } else if (term.getFieldKey().equals(FieldKey.VISTA_OI_NAME)) {
                searchHelper.add(Restrictions.ilike(EplOrderableItemDo.VISTA_OI_NAME, term.formatValue(), mode), isAndSearch);
            } else if (term.getFieldKey().equals(FieldKey.NATIONAL_FORMULARY_INDICATOR)) {
                searchHelper.add(
                    Restrictions.ilike(EplOrderableItemDo.NATIONAL_FORMULARY_INDICATOR, term.formatValue(), mode), isAndSearch);
            } else if (term.getFieldKey().equals(FieldKey.NON_VA_MED)) {
                searchHelper.add(Restrictions.ilike(EplOrderableItemDo.NON_VA_MED, term.formatValue(), mode), isAndSearch);
            } else if (term.getFieldKey().equals(FieldKey.NATIONAL_DRUG_TEXT)) {
                Criteria drugTexts = searchHelper.getSubCriteria(EplOrderableItemDo.DRUG_TEXT_NATIONAL);
                Criteria drugText = searchHelper.getSubCriteria(drugTexts, EplOiDrugTextLAssocDo.EPL_DRUG_TEXT);
                drugText.add(Restrictions.ilike(EplDrugTextDo.DRUG_TEXT_NAME, term.formatValue(), mode));
            } else if (term.getFieldKey().equals(FieldKey.HIGH_ALERT)) {
                searchHelper.add(Restrictions.ilike(EplOrderableItemDo.HIGH_ALERT, term.formatValue(), mode),
                    isAndSearch);
            } else if (term.getFieldKey().equals(FieldKey.SPECIAL_INSTRUCTIONS)) {
                searchHelper.add(Restrictions.ilike(EplOrderableItemDo.SPECIAL_INSTRUCTIONS, term.formatValue(), mode),
                    isAndSearch);
            } else if (term.getFieldKey().equals(FieldKey.OI_SCHEDULE_TYPE)) {
                Criteria oiSchType = searchHelper.getSubCriteria(EplOrderableItemDo.OI_SCHEDULE_TYPE);
                oiSchType.add(Restrictions.ilike(EplOiScheduleTypeDo.CODE, term.formatValue(), mode));
            } else if (term.getFieldKey().equals(FieldKey.ADMIN_SCHEDULES)) {
                Criteria adminScheds = searchHelper.getSubCriteria(EplOrderableItemDo.OI_ADMIN_SCHEDULE_ASSOCS);
                Criteria adminSchedule = searchHelper.getSubCriteria(adminScheds, EplOiAdminSchedAssocDo.EPL_ADMIN_SCHEDULE);
                adminSchedule.add(Restrictions.ilike(EplAdminScheduleDo.NAME, term.formatValue(), mode));
            } else if (term.getFieldKey().equals(FieldKey.STANDARD_MED_ROUTE)) {
                Criteria oiRoutes = searchHelper.getSubCriteria(EplOrderableItemDo.EPL_STANDARD_MED_ROUTE);
                oiRoutes.add(Restrictions.ilike(EplStandardMedRouteDo.NAME, term.formatValue(), mode));
            } else if (term.getFieldKey().equals(FieldKey.DOSAGE_FORM)) {
                Criteria dosage = searchHelper.getSubCriteria(EplOrderableItemDo.EPL_DOSAGE_FORM);
                dosage.add(Restrictions.ilike(EplDosageFormDo.DF_NAME, term.formatValue(), mode));
            } else if (term.getFieldKey().equals(FieldKey.INACTIVATION_DATE)) {
                try {
                    searchHelper.add(
                        Restrictions.eq(EplOrderableItemDo.INACTIVATION_DATE,
                            DateFormatUtility.convertToDate(term.formatValue())), isAndSearch);
                } catch (ParseException pe) {
                    throw new DomainValidationException(pe, DomainValidationException.FIELD_FORMAT_ERROR, term.getFieldKey()
                            .getLocalizedWidth(Locale.getDefault()), term.formatValue());
                }
            } else if (term.getFieldKey().equals(FieldKey.NATIONAL_DRUG_TEXTS)) {
                Criteria nationalDrugTexts = searchHelper.getSubCriteria(EplOrderableItemDo.DRUG_TEXT_NATIONAL);
                Criteria drugText = searchHelper.getSubCriteria(nationalDrugTexts, EplOiDrugTextNAssocDo.EPL_DRUG_TEXT);
                drugText.add(Restrictions.ilike(EplDrugTextDo.DRUG_TEXT_NAME, term.formatValue(), mode));
            } else if (term.getFieldKey().isSimpleDataField()) {
                if (nonListCount == 0) { // Can only do one Hibernate query of this type, then after that must do manually

                    // (see below).
                    Criteria ownerCriteria = searchHelper.getSubCriteria(EplOrderableItemDo.EPL_VA_DF_OWNERS);
                    Criteria vaDfValueCriteria = ownerCriteria.createCriteria(EplVadfOwnerDo.EPL_VA_DF_NON_LIST_VALUES);
                    vaDfValueCriteria.add(Restrictions.ilike(EplVadfNonlistValueDo.VA_DF_VALUE, term.formatValue(), mode));

                    Criteria vaDfCriteria = vaDfValueCriteria.createCriteria(EplVadfNonlistValueDo.EPL_VA_DF);
                    vaDfCriteria.add(Restrictions.eq(EplVaDfDo.NAME, term.getFieldKey().getKey()));
                } else {
                    nonListSearchTerms.add(term);
                }

                nonListCount++;
            } else if (term.getFieldKey().isListDataField()) {

                if (term.getFieldKey().isMultiSelectListDataField()) {
                    throw new UnsupportedOperationException(
                        "No support for advanced searches on multi-select list data fields such as '"
                            + term.getFieldKey()
                            + "'.\n"
                            + "You must add a separate FieldKey entry (ending with '_SEARCHABLE' preferably)"
                            + " that is a single select list,\n"
                            + "and then add a mapping to the 'SEARCH_KEY_TO_MAIN_KEY_MAP' map's static initializer.");
                }

                listSearchTerms.add(term);
            } else if (term.getFieldKey().isPrimaryKeyDataField() || term.getFieldKey().isMultitextDataField()) {
                listMultiTextSearchTerms.add(term);
            } else {
                throw new UnsupportedOperationException("No advanced search support for field '" + term.getFieldKey() + "'");
            }

        } // end for

        // Perform the advanced search.
        Set<EplOrderableItemDo> resultCollection =
                performAdvancedSearchWithCriteria(searchCriteria, eplOrderableItemDao, searchHelper,
                    new OrderableItemDoComparator(), nonListSearchTerms, listSearchTerms, listMultiTextSearchTerms, null);

        List<OrderableItemVo> items = new ArrayList<OrderableItemVo>();

        for (EplOrderableItemDo data : resultCollection) {
            OrderableItemVo vo = orderableItemConverter.convert(data);
            vo.setProductCount(productDomainCapability.retrieveChildrenCount(vo.getId()));
            items.add(vo);
        }

        return items;
    }

    /**
     * Adds to the specified Criteria, additional 'AND'-ed criteria that is specified on the top-level panel on the advanced
     * search page. These additional criteria are always 'AND'-ed into the overall advanced search query, regardless of if
     * the mode is OR or AND.
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @param criteria The Criteria to add additional queries to
     */
    @Override
    protected void addTopPanelCriteria(SearchCriteriaVo searchCriteria, Criteria criteria) {

        if (LocalUseSearchType.LOCAL_USE.equals(searchCriteria.getLocalUse())) {
            criteria.add(Restrictions.eq(EplOrderableItemDo.LOCAL_USE, "Y"));
        } else if (LocalUseSearchType.NOT_LOCAL_USE.equals(searchCriteria.getLocalUse())) {
            criteria.add(Restrictions.eq(EplOrderableItemDo.LOCAL_USE, "N"));
        }

        SearchQueryUtility.createItemStatusCriteria(searchCriteria.getItemStatus(), criteria,
            EplOrderableItemDo.ITEM_STATUS);

        SearchQueryUtility.createRequestItemStatusCriteria(searchCriteria.getRequestStatus(), criteria,
            EplOrderableItemDo.REQUEST_STATUS);
    }

    /**
     * Tests if the VO and DO Orderable Item EPL IDs are the same.
     * 
     * @param oiVo OrderableItemVo
     * @param oiDo EplOrderableItemDo
     * @return boolean True if VO and DO have the same EPL IDs.
     */
    protected boolean isSameId(OrderableItemVo oiVo, EplOrderableItemDo oiDo) {
        return (oiVo.getId() != null && oiDo.getEplId() != null && oiVo.getId().equals(oiDo.getEplId().toString()));
    }

    /**
     * search on the epl_orderable_item table
     * 
     * @param searchType SearchType
     * @param value String
     * @param columnName String
     * @param iStatuses - List of ItemStatus
     * @param rStatuses - List of Request Item Status
     * @param localUse - Local use filter
     * @return List of EplOrderableItemDo
     */
    public List<EplOrderableItemDo> searchOnOiTable(SearchType searchType, String value, String columnName,
                                                    List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses,
                                                    LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(SELECITEMFROM).append(EplOrderableItemDo.class).append(ITEM));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));

        query.append(" AND item.");
        query.append(SearchQueryUtility.getColumnClause(searchType, columnName, value));

        List<EplOrderableItemDo> returnedDos = eplOrderableItemDao.executeHqlQuery(query.toString());

        return returnedDos;
    }

    /**
     * search by simple data field
     * 
     * @param searchType SearchType
     * @param defName String
     * @param value String
     * @param iStatuses - List of ItemStatus
     * @param rStatuses - List of Request Item Status
     * @param localUse - Local use filter
     * @return List of EplOrderableItemDo
     */
    @Override
    public List<EplOrderableItemDo> searchBySimpleDataField(SearchType searchType, String defName, String value,
                                                            List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses,
                                                            LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(SELECITEMFROM).append(EplOrderableItemDo.class).append(ITEMJOIN)
                .append(ITEM, EplOrderableItemDo.EPL_VA_DF_OWNERS).append(OWNERJOIN)
                .append(OWNER, EplVadfOwnerDo.EPL_VA_DF_NON_LIST_VALUES).append("nonlist  JOIN")
                .append(PPSConstants.NONLIST, EplVadfNonlistValueDo.EPL_VA_DF).append("def"));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));

        query.append(" AND def.vadfName = '");
        query.append(defName);
        query.append("' AND  nonlist.vaDfValue ");

        appendSearchTypeConditional(query, searchType, value);

        List<EplOrderableItemDo> returnedDos = eplOrderableItemDao.executeHqlQuery(query.toString());

        return returnedDos;
    }

    /**
     * search by list data field
     * 
     * @param searchType SearchType
     * @param defName String
     * @param value String
     * @param iStatuses - List of ItemStatus
     * @param rStatuses - List of Request Item Status
     * @param localUse - Local use filter
     * @return List of EplOrderableItemDo
     */
    @Override
    public List<EplOrderableItemDo> searchByListDataField(SearchType searchType, String defName, String value,
                                                          List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses,
                                                          LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(SELECITEMFROM).append(EplOrderableItemDo.class).append(ITEMJOIN)
                .append(ITEM, EplOrderableItemDo.EPL_VA_DF_OWNERS).append(OWNERJOIN)
                .append(OWNER, EplVadfOwnerDo.EPL_VA_DF_ASSOC_VALUES).append("list  JOIN")
                .append("list", EplVadfAssocValueDo.EPL_VA_DF_LOV).append("lovs  JOIN")
            .append("lovs", EplVadfLovDo.EPL_VA_DF)
                .append("dfName"));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));

        query.append(" AND dfName.");
        query.append(EplVaDfDo.NAME);
        query.append(PPSConstants.EQUALTICK);
        query.append(defName);
        query.append("' AND ");
        query.append(" list.key.listValue ");

        appendSearchTypeConditional(query, searchType, value);

        List<EplOrderableItemDo> returnedDos = eplOrderableItemDao.executeHqlQuery(query.toString());

        return returnedDos;
    }

    /**
     * search by multi text data field
     * 
     * @param searchType String
     * @param defName String
     * @param value String
     * @param iStatuses List<ItemStatus>
     * @param rStatuses List<RequestItemStatus>
     * @param localUse boolean
     * @return List ofEplOrderableItemDo
     * 
     */
    @Override
    protected List<EplOrderableItemDo> searchByMultiTextDataField(SearchType searchType, String defName, String value,
                                                                List<ItemStatus> iStatuses,
        List<RequestItemStatus> rStatuses,
                                                                LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(SELECITEMFROM).append(EplOrderableItemDo.class).append(ITEMJOIN)
                .append(ITEM, EplOrderableItemDo.EPL_VA_DF_OWNERS).append(OWNERJOIN)
                .append(OWNER, EplVadfOwnerDo.EPL_VA_DF_MULTI_TEXTS).append("list JOIN")
                .append("list", EplMultiTextDo.EPL_VA_DF).append("dfName"));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));

        query.append(" AND dfName.");
        query.append(EplVaDfDo.NAME);
        query.append(PPSConstants.EQUALTICK);
        query.append(defName);
        query.append("' AND ");
        query.append(" list.key.text ");

        appendSearchTypeConditional(query, searchType, value);

        List<EplOrderableItemDo> returnedDos = eplOrderableItemDao.executeHqlQuery(query.toString());

        return returnedDos;
    }

    /**
     * searchAllListDataFields
     * @param searchType SearchType
     * @param value String
     * @param iStatuses - List of ItemStatus
     * @param rStatuses - List of Request Item Status
     * @param localUse - Local use filter
     * @return List of EplOrderableItemDo
     */
    public List<EplOrderableItemDo> searchAllListDataFields(SearchType searchType, String value, List<ItemStatus> iStatuses,
                                                            List<RequestItemStatus> rStatuses, LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(SELECITEMFROM).append(EplOrderableItemDo.class).append(ITEMJOIN)
                .append(ITEM, EplOrderableItemDo.EPL_VA_DF_OWNERS).append(OWNERJOIN)
                .append(OWNER, EplVadfOwnerDo.EPL_VA_DF_ASSOC_VALUES).append("list JOIN")
                .append("list", EplVadfAssocValueDo.EPL_VA_DF_LOV).append("lovs"));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));

        query.append("' AND ");
        query.append(" list.key.listValue ");

        appendSearchTypeConditional(query, searchType, value);

        List<EplOrderableItemDo> returnedDos = eplOrderableItemDao.executeHqlQuery(query.toString());

        return returnedDos;
    }

    /**
     * searchLabs
     * @param searchType SearchType
     * @param value String
     * @param columnName String
     * @param iStatuses - List of ItemStatus
     * @param rStatuses - List of Request Item Status
     * @param localUse - Local use filter
     * @return List of EplOrderableItemDo
     */
    public List<EplOrderableItemDo> searchLabs(SearchType searchType, String value, String columnName,
                                               List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses,
                                               LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT distinct item FROM").append(EplOrderableItemDo.class).append(ITEMJOIN)
                .append(ITEM, EplOrderableItemDo.EPL_LABS).append("labs"));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));
        query.append(" AND labs.");
        query.append(SearchQueryUtility.getColumnClause(searchType, columnName, value));

        List<EplOrderableItemDo> returnedDos = eplOrderableItemDao.executeHqlQuery(query.toString());

        return returnedDos;

    }

    /**
     * searchVitals.
     * @param searchType SearchType
     * @param value String
     * @param columnName String
     * @param iStatuses - List of ItemStatus
     * @param rStatuses - List of Request Item Status
     * @param localUse - Local use filter
     * @return List of EplOrderableItemDo
     */
    public List<EplOrderableItemDo> searchVitals(SearchType searchType, String value, String columnName,
                                                 List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses,
                                                 LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT distinct item FROM").append(EplOrderableItemDo.class).append(ITEMJOIN)
                .append(ITEM, EplOrderableItemDo.EPL_VITALS).append("vitals"));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));
        query.append(" AND  vitals.");
        query.append(SearchQueryUtility.getColumnClause(searchType, columnName, value));

        List<EplOrderableItemDo> returnedDos = eplOrderableItemDao.executeHqlQuery(query.toString());

        return returnedDos;

    }

    /**
     * searchDosageForm.
     * @param searchType SearchType
     * @param value String
     * @param columnName String
     * @param iStatuses - List of ItemStatus
     * @param rStatuses - List of Request Item Status
     * @param localUse - Local use filter
     * @return List of EplOrderableItemDo
     */
    private List<EplOrderableItemDo> searchDosageForm(SearchType searchType, String value, String columnName,
                                                      List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses,
                                                      LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(SELECITEMFROM).append(EplOrderableItemDo.class).append(ITEMJOIN)
                .append(ITEM, EplOrderableItemDo.EPL_DOSAGE_FORM).append("dosages"));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));
        query.append("AND dosages.");

        query.append(SearchQueryUtility.getColumnClause(searchType, columnName, value));

        List<EplOrderableItemDo> returnedDos = eplOrderableItemDao.executeHqlQuery(query.toString());

        return returnedDos;

    }

    /**
     * searchDrugTextLocal.
     * @param searchType SearchType
     * @param value String
     * @param columnName String
     * @param iStatuses - List of ItemStatus
     * @param rStatuses - List of Request Item Status
     * @param localUse - Local use filter
     * @return List of EplOrderableItemDo
     */
    public List<EplOrderableItemDo> searchDrugTextLocal(SearchType searchType, String value, String columnName,
                                                        List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses,
                                                        LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();

        query.append(HqlBuilder.create(SELECITEMFROM).append(EplOrderableItemDo.class).append(ITEMJOIN)
                .append(ITEM, EplOrderableItemDo.DRUG_TEXT_LOCAL).append("assocs JOIN")
                .append("assocs", EplOiDrugTextLAssocDo.EPL_DRUG_TEXT).append("drugText"));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));
        query.append(" AND drugText.");
        query.append(SearchQueryUtility.getColumnClause(searchType, columnName, value));

        List<EplOrderableItemDo> returnedDos = eplOrderableItemDao.executeHqlQuery(query.toString());

        return returnedDos;

    }

    /**
     * searchLocalMedRoute.
     * @param searchType SearchType
     * @param value String
     * @param columnName String
     * @param iStatuses - List of ItemStatus
     * @param rStatuses - List of Request Item Status
     * @param localUse - Local use filter
     * @return List of EplOrderableItemDo
     */
    public List<EplOrderableItemDo> searchLocalMedRoute(SearchType searchType, String value, String columnName,
                                                        List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses,
                                                        LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();

        query.append(HqlBuilder.create(SELECITEMFROM).append(EplOrderableItemDo.class).append(ITEMJOIN)
                .append(ITEM, EplOrderableItemDo.OI_MED_ROUTE).append("assocs JOIN")
                .append("assocs", EplOiMedRouteAssocDo.EPL_LOCAL_MED_ROUTE).append("route"));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));
        query.append(" AND route.");
        query.append(SearchQueryUtility.getColumnClause(searchType, columnName, value));

        List<EplOrderableItemDo> returnedDos = eplOrderableItemDao.executeHqlQuery(query.toString());

        return returnedDos;

    }

    /**
     * setEplOiMedRouteAssocDao
     * @param eplOiMedRouteAssocDao eplOiMedRouteAssocDao property
     */
    public void setEplOiMedRouteAssocDao(EplOiMedRouteAssocDao eplOiMedRouteAssocDao) {
        this.eplOiMedRouteAssocDao = eplOiMedRouteAssocDao;
    }

    /**
     * setEplOrderableItemDao
     * @param eplOrderableItemDao eplOrderableItemDao property
     */
    public void setEplOrderableItemDao(EplOrderableItemDao eplOrderableItemDao) {
        this.eplOrderableItemDao = eplOrderableItemDao;
    }

    /**
     * setProductDomainCapability
     * @param productDomainCapability productDomainCapability property
     */
    public void setProductDomainCapability(ProductDomainCapability productDomainCapability) {
        this.productDomainCapability = productDomainCapability;
    }

    /**
     * Update the given OrderableItemVo
     * 
     * @param orderableItem OrderableItemVo
     * @param user {@link UserVo} performing the action
     * @return OrderableItemVo updated OrderableItemVo
     * @throws DuplicateItemException DuplicateItemException
     */
    @Override
    public synchronized OrderableItemVo update(OrderableItemVo orderableItem, UserVo user) throws DuplicateItemException {
        if (existsByUniquenessFields(orderableItem)) {
            throw new DuplicateItemException(DuplicateItemException.OI_DUPLICATE_ITEM);
        }

        EplOrderableItemDo oi = orderableItemConverter.convert(orderableItem);

        if (!(orderableItem.getVaDataFields().isEmpty())) {
            getDataFieldsDomainCapability().update(oi.getEplVadfOwners(), user);
        }

        // delete all existing assocs and add the ones from the new Vo LOCAL ONLY
        eplOiAdminSchedAssocDao.delete(EplOiAdminSchedAssocDo.ORDERABLE_ITEM_EPL_ID, oi.getEplId());
        oi.getEplOiAdminSchedAssocs().addAll(eplOiAdminSchedAssocDao.insert(oi.getEplOiAdminSchedAssocs(), user));

        // delete all existing assocs and add the ones from the new Vo
        eplOiMedRouteAssocDao.delete(EplOiMedRouteAssocDo.ORDERABLE_ITEM_EPL_ID, oi.getEplId());
        oi.getEplOiMedRouteAssocs().addAll(eplOiMedRouteAssocDao.insert(oi.getEplOiMedRouteAssocs(), user));

//        eplLabDao.delete(EplLabDo.ORDERABLE_ITEM_EPL_ID, oi.getEplId());
//        eplLabDao.insert(oi.getEplLabs(), user);

//        eplVitalDao.delete(EplVitalDo.ORDERABLE_ITEM_EPL_ID, oi.getEplId());
//        eplVitalDao.insert(oi.getEplVitals(), user);

//        eplOiDrugTextLAssocDao.delete(EplOiDrugTextLAssocDo.ORDERABLE_ITEM_EPL_ID, oi.getEplId());
//        eplOiDrugTextLAssocDao.insert(oi.getEplOiDrugTextLAssocs(), user);

        eplOiDrugTextNAssocDao.delete(EplOiDrugTextNAssocDo.ORDERABLE_ITEM_EPL_ID, oi.getEplId());
        eplOiDrugTextNAssocDao.insert(oi.getEplOiDrugTextNAssocs(), user);

        eplOrderableItemDao.update(oi, user);

        return orderableItem;
    }

    /**
     * searchAllSimpleDataFields.
     * @param searchType SearchType
     * @param value String
     * @param iStatuses - List of ItemStatus
     * @param rStatuses - List of Request Item Status
     * @param localUse - Local use filter
     * @return List of EplOrderableItemDo
     */
    public List<EplOrderableItemDo> searchAllSimpleDataFields(SearchType searchType, String value,
        List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses, LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(SELECITEMFROM).append(EplOrderableItemDo.class).append(ITEMJOIN)
                .append(ITEM, EplOrderableItemDo.EPL_VA_DF_OWNERS).append(OWNERJOIN)
                .append(OWNER, EplVadfOwnerDo.EPL_VA_DF_NON_LIST_VALUES).append("nonlist"));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));

        query.append("' AND ");
        query.append(" nonlist.vaDfValue ");

        appendSearchTypeConditional(query, searchType, value);

        List<EplOrderableItemDo> returnedDos = eplOrderableItemDao.executeHqlQuery(query.toString());

        return returnedDos;
    }

    /**
     * Search for Orderable Items with the given search criteria.
     * 
     * @param searchCriteria criteria for matching Orderable Items
     * @return List of OrderableItemVo
     */
    @Override
    public List<OrderableItemVo> searchAllOrAdvanced(SearchCriteriaVo searchCriteria) {

        List<EplOrderableItemDo> resultCollectionDo = new ArrayList<EplOrderableItemDo>();
        SearchType searchType = searchCriteria.expandSearchTerms().get(0).getSearchType();
        String value = searchCriteria.expandSearchTerms().get(0).formatValue();

        // will be adding to this collection only if it does not exist in collection already
        List<EplOrderableItemDo> resultOiTable =
                searchOnOiTable(searchType, value, EplOrderableItemDo.OI_NAME, searchCriteria.getItemStatus(),
                                searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
            searchOnOiTable(searchType, value, EplOrderableItemDo.REJECT_REASON_TEXT, searchCriteria.getItemStatus(),
                searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
                searchOnOiTable(searchType, value, EplOrderableItemDo.REQUEST_REJECT_REASON, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
                searchOnOiTable(searchType, value, EplOrderableItemDo.VISTA_OI_NAME, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
            searchOnOiTable(searchType, value, EplOrderableItemDo.NATIONAL_FORMULARY_INDICATOR,
                searchCriteria.getItemStatus(), searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
                searchVitals(searchType, value, EplVitalDo.VITAL_DISPLAY_FINISH_ORDER, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
                searchVitals(searchType, value, EplVitalDo.VITAL_DISPLAY_ORDER_ENTRY, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
                searchVitals(searchType, value, EplVitalDo.VITAL_DISPLAY_ADMINISTRATION, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
                searchLabs(searchType, value, EplLabDo.LAB_DISPLAY_ADMINISTRATION, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
                searchLabs(searchType, value, EplLabDo.LAB_DISPLAY_ORDER_ENTRY, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
                searchLabs(searchType, value, EplLabDo.LAB_DISPLAY_FINISH_AN_ORDER, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
                searchDosageForm(searchType, value, EplDosageFormDo.DF_NAME, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
                searchDrugTextLocal(searchType, value, EplDrugTextDo.DRUG_TEXT_NAME, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
                searchLocalMedRoute(searchType, value, EplLocalMedRouteDo.NAME, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        // need to get the simple and list data fields
        resultOiTable =
                searchAllSimpleDataFields(searchType, value, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        resultOiTable =
                searchAllListDataFields(searchType, value, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse());
        addUniqueToList(resultOiTable, resultCollectionDo);

        List<OrderableItemVo> resultCollection = new ArrayList<OrderableItemVo>();

        Iterator i = resultCollectionDo.iterator();

        while (i.hasNext()) {
            EplOrderableItemDo data = (EplOrderableItemDo) (i.next());
            OrderableItemVo req = orderableItemConverter.convert(data);
            req.setProductCount(productDomainCapability.retrieveChildrenCount(req.getId()));
            resultCollection.add(req);
        }

        return resultCollection;
    }

    /**
     * addUniqueToList
     *
     * @param fromList List<EplOrderableItemDo>
     * @param toList List<EplOrderableItemDo>
     */
    private void addUniqueToList(List<EplOrderableItemDo> fromList, List<EplOrderableItemDo> toList) {
        for (EplOrderableItemDo item : fromList) {
            if (!(toList.contains(item))) {
                toList.add(item);
            }
        }

    }

    /**
     * setEplLabDao
     * @param eplLabDao eplLabDao property
     */
    public void setEplLabDao(EplLabDao eplLabDao) {
        this.eplLabDao = eplLabDao;
    }

    /**
     * setEplVitalDao
     * @param eplVitalDao eplVitalDao property
     */
    public void setEplVitalDao(EplVitalDao eplVitalDao) {
        this.eplVitalDao = eplVitalDao;
    }

    /**
     * setEplOiDrugTextLAssocDao
     * @param eplOiDrugTextLAssocDao eplOiDrugTextAssocDao property
     */
    public void setEplOiDrugTextLAssocDao(EplOiDrugTextLAssocDao eplOiDrugTextLAssocDao) {
        this.eplOiDrugTextLAssocDao = eplOiDrugTextLAssocDao;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    @Override
    public DataAccessObject getDataAccessObject() {
        return eplOrderableItemDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    @Override
    public OrderableItemConverter getConverter() {
        return orderableItemConverter;
    }

    /**
     * setEplOiAdminSchedAssocDao
     * @param eplOiAdminSchedAssocDao eplOiAdminSchedAssocDao property
     */
    public void setEplOiAdminSchedAssocDao(EplOiAdminSchedAssocDao eplOiAdminSchedAssocDao) {
        this.eplOiAdminSchedAssocDao = eplOiAdminSchedAssocDao;
    }

    /**
     * setEplOiDrugTextNAssocDao
     * @param eplOiDrugTextNAssocDao eplOiDrugTextNAssocDao property
     */
    public void setEplOiDrugTextNAssocDao(EplOiDrugTextNAssocDao eplOiDrugTextNAssocDao) {
        this.eplOiDrugTextNAssocDao = eplOiDrugTextNAssocDao;
    }

    /**
     * setOrderableItemConverter
     * @param orderableItemConverter orderableItemConverter property
     */
    public void setOrderableItemConverter(OrderableItemConverter orderableItemConverter) {
        this.orderableItemConverter = orderableItemConverter;
    }

    /**
     * setReportDomainCapability for OrderableItemDomainCapabilityImpl
     * 
     * @param reportDomainCapability reportDomainCapability property
     */
    public void setReportDomainCapability(ReportDomainCapability reportDomainCapability) {
        this.reportDomainCapability = reportDomainCapability;
    }

    /**
     * Comparator class intended for use in sorted sets of non-new ManagedItemVo instances, such as via the TreeSet
     * structure.
     */
    private static class OrderableItemDoComparator implements Comparator<EplOrderableItemDo> {

        /**
         * Compares the two productDos using compareTo
         * 
         * @param o1 First Do
         * @param o2 Second Do
         * @return o1.compare(o2)
         * 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(EplOrderableItemDo o1, EplOrderableItemDo o2) {
            return o1.getEplId().compareTo(o2.getEplId());
        }
    }

    /**
     * setEplProductDao
     * @param eplProductDao eplProductDao property
     */
    public void setEplProductDao(EplProductDao eplProductDao) {
        this.eplProductDao = eplProductDao;
    }
}
