/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.DomainValidationException;
import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.DateFormatUtility;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.PaginatedList;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ReportDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.impl.ManagedItemDomainCapabilityImpl.AndAdvancedSearchHelper;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplNdcDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplManufacturerDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplMultiTextDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPackageTypeDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDfDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfAssocValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfLovDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfNonlistValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;
import gov.va.med.pharmacy.peps.domain.common.utility.SearchQueryUtility;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.NdcConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.NdcImageCapability;


/**
 * Perform CRUD operations on NdcVo
 */
public class NdcDomainCapabilityImpl extends ManagedItemDomainCapabilityImpl<NdcVo, EplNdcDo> implements NdcDomainCapability {

    private static final String ITEM = "item";
    private static final String OWNERS = "owners";
    private static final String ITEM_JOIN = "item JOIN";
    private static final String OWNERS_JOIN = "owners JOIN";
    private static final String EQ_SQUOTE = " = '";
    private static final String SELECT_ITEM_FROM = "SELECT item FROM";
    private static final String LIKE_SQUOTE = " like '";
    private static final String LIKE_SQUOTE_WC = " like '%";
    private static final String AND_DFNAME = " AND dfName.";
    private static final String DFNAME = "dfName";
    private static final String LIST = "list";
    private static final String LIST_JOIN = "list JOIN";
    private static final String SQUOTE = "'";
    private static final String WC_SQUOTE = "%'";

    private EplNdcDao eplNdcDao;
    private NdcImageCapability ndcImageCapability;
    private ReportDomainCapability reportDomainCapability;

    private NdcConverter ndcConverter;

    /**
     * Insert the given {@link ManagedItemVo} with a duplicate check.
     * 
     * @param managedItem {@link ManagedItemVo}
     * @param user {@link UserVo} performing the action
     * @return {@link ManagedItemVo} inserted with new ID
     * @throws DuplicateItemException if item already exists by uniqueness fields
     */
    @Override
    public NdcVo create(NdcVo managedItem, UserVo user) throws DuplicateItemException {

        if (existsByUniquenessFields(managedItem)) {
            throw new DuplicateItemException(DuplicateItemException.NDC_DUPLICATE_ITEM);
        }

        return createWithoutDuplicateCheck(managedItem, user);
    }

    /**
     * Insert the given NdcVo.
     * 
     * @param ndc NdcVo
     * @param user {@link UserVo} performing the action
     * @return NdcVo inserted NdcVo with new ID
     */
    @Override
    public NdcVo createWithoutDuplicateCheck(NdcVo ndc, UserVo user) {
        if (ndc.getId() == null) {
            ndc.setId(getSeqNumDomainCapability().generateId(ndc.getEntityType(), user));

            if (!(ndc.getVaDataFields().isEmpty())) {
                ndc.getVaDataFields().setVaDfOwnerId(getSeqNumDomainCapability().generatedOwnerId(user));
            }
        }

        EplNdcDo ndcDo = ndcConverter.convert(ndc);

        eplNdcDao.insert(ndcDo, user);

        if (!(ndc.getVaDataFields().isEmpty())) {
            ndcDo.setEplVadfOwners(getDataFieldsDomainCapability().insertEplVaDfOwners(ndcDo, null, null, null,
                                                                                       ndcDo.getEplVadfOwners(), user));
        }

        return ndc;
    }

    /**
     * The NDC Check does't have dependencies
     * 
     * @param ndcVo ndcVo
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    @Override
    public void checkForActiveDependencies(NdcVo ndcVo, UserVo user) throws ValidationException {

    }

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link ManagedItemVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(NdcVo item) {
        Disjunction or = Restrictions.disjunction();

        // NDC must be unique if set
        String ndcNumber = item.getNdc() == null ? null : ndcConverter.createNdcNoDashes(item.getNdc());

        if (ndcNumber != null && !ndcNumber.trim().isEmpty()) {
            Criterion ndcCriterion = Restrictions.eq(EplNdcDo.NDC_NO_DASHES, ndcNumber);
            or.add(ndcCriterion);
        }

        // UPC/UPN must be unique if set
        String upcUpn = item.getUpcUpn();

        if (upcUpn != null && !upcUpn.trim().isEmpty()) {
            Criterion upcUpnCriterion = Restrictions.eq(EplNdcDo.UPC_UPN, upcUpn);
            or.add(upcUpnCriterion);
        }

        // Vendor/VSN combination must be unique if set
        String vendor = item.getVendor();
        String vsn = item.getVendorStockNumber();

        if (vendor != null && !vendor.trim().isEmpty() && vsn != null && !vsn.trim().isEmpty()) {
            Criterion vendorVsnCriterion =
                    Restrictions.and(Restrictions.eq(EplNdcDo.VENDOR, vendor), Restrictions.eq(EplNdcDo.VSN, vsn));
            or.add(vendorVsnCriterion);
        }

        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(or);

        // Do not include self duplicates
        if (item.getId() != null && !item.getId().trim().isEmpty()) {
            criteria.add(Restrictions.ne(EplNdcDo.EPL_ID, Long.valueOf(item.getId())));
        }

        return criteria;
    }

    /**
     * Retrieve the NdcVo with the given ID.
     * 
     * @param id String NDC ID
     * @return NdcVo
     * @throws ItemNotFoundException ItemNotFoundException
     */
    @Override
    public NdcVo retrieveParent(String id) throws ItemNotFoundException {
        NdcVo value = super.retrieveParent(id);

        value.setImage(ndcImageCapability.getImageFileName(value.getNdc()));

        return value;
    }

    /**
     * Retrieve a list of partially populated NDCs for the given Product ID.
     * 
     * @param productId String product ID
     * @return List of NdcVo
     */
    @Override
    public List<NdcVo> retrieveMinimalChildren(String productId) {
        EplProductDo prod = new EplProductDo();
        prod.setEplId(Long.valueOf(productId));

        List<EplNdcDo> ndcDos = eplNdcDao.retrieve(EplNdcDo.EPL_PRODUCT, prod);
        List<NdcVo> ndcs = ndcConverter.convertChild(ndcDos);

        return ndcs;
    }

    /**
     * Retrieve a list of fully populated NDCs for the given Product ID
     * 
     * @param productId String product ID
     * @return List of NdcVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.capability.NdcCapability#retrieveChildren(java.lang.String)
     */
    @Override
    public List<NdcVo> retrieveChildren(String productId) {
        List<EplNdcDo> ndcDos = eplNdcDao.retrieve(EplNdcDo.EPL_PRODUCT_EPL_ID, Long.valueOf(productId));
        List<NdcVo> ndcs = ndcConverter.convert(ndcDos);

        return ndcs;
    }

    /**
     * Retrieve a partial list of NDCs for the given Product Item ID. The list will begin at index startRow and will have
     * pageSize number of rows in it. The list will be sorted by sortedFieldKey in the given sortOrder.
     * 
     * @param parentItemId String ID of parent for which to retrieve children items
     * @param sortedFieldKey FieldKey representing field to sort by
     * @param sortOrder SortOrder representing ascending or descending sort
     * @param startRow integer index in full list of child items from which to start retrieving
     * @param pageSize integer number of rows to retrieve
     * 
     * @return PaginatedList<ManagedItemVo> child items
     */
    @Override
    public PaginatedList<NdcVo> retrieveChildren(String parentItemId, FieldKey sortedFieldKey, SortOrder sortOrder,
        int startRow, int pageSize) {

        List<EplNdcDo> ndcDos =
            eplNdcDao.retrievePage(EplNdcDo.EPL_PRODUCT_EPL_ID, Long.valueOf(parentItemId), startRow, pageSize,
                sortedFieldKey, sortOrder);
        List<NdcVo> ndcs = ndcConverter.convertChild(ndcDos);

        return new PaginatedList<NdcVo>(ndcs, retrieveChildrenCount(parentItemId), sortedFieldKey, sortOrder, startRow,
            pageSize);
    }

    /**
     * Retrieve a partial list of Active NDCs for the given Product Item ID. The list will begin at index startRow and will
     * have pageSize number of rows in it. The list will be sorted by sortedFieldKey in the given sortOrder.
     * 
     * @param parentItemId String ID of parent for which to retrieve children items
     * @param sortedFieldKey FieldKey representing field to sort by
     * @param sortOrder SortOrder representing ascending or descending sort
     * @param startRow integer index in full list of child items from which to start retrieving
     * @param pageSize integer number of rows to retrieve
     * 
     * @return PaginatedList<ManagedItemVo> child items
     */
    @Override
    public PaginatedList<NdcVo> retrieveActiveChildren(String parentItemId, FieldKey sortedFieldKey, SortOrder sortOrder,
        int startRow, int pageSize) {

        List<EplNdcDo> ndcDos =
            eplNdcDao.retrieveActivePage(EplNdcDo.EPL_PRODUCT_EPL_ID, Long.valueOf(parentItemId), EplNdcDo.ITEM_STATUS,
                startRow, pageSize, sortedFieldKey, sortOrder);
        List<NdcVo> ndcs = ndcConverter.convert(ndcDos);

        return new PaginatedList<NdcVo>(ndcs, retrieveChildrenCount(parentItemId), sortedFieldKey, sortOrder, startRow,
            pageSize);
    }

    /**
     * retrieve the ndc count for the product by product id
     * 
     * @param productId String
     * @return int
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability#retrieveChildrenCount(java.lang.String)
     */
    @Override
    public int retrieveChildrenCount(String productId) {
        if (productId == null) {
            return 0;
        }

        String query = "select count(*) from EplNdcDo ndc where ndc.eplProduct = " + productId;
        Long rowsReturned = (Long) eplNdcDao.executeHqlQuery(query).iterator().next();

        return rowsReturned.intValue();
    }

    /**
     * Update the given NdcVo
     * 
     * @param ndc NdcVo
     * @param user {@link UserVo} performing the action
     * @return NdcVo updated NdcVo
     * @throws DuplicateItemException 
     */
    @Override
    public synchronized NdcVo update(NdcVo ndc, UserVo user) throws DuplicateItemException {
        if (existsByUniquenessFields(ndc)) {
            throw new DuplicateItemException(DuplicateItemException.NDC_DUPLICATE_ITEM);
        }

        // convert vo to do
        EplNdcDo updateDo = ndcConverter.convert(ndc);

        if (!(ndc.getVaDataFields().isEmpty())) {
            getDataFieldsDomainCapability().update(updateDo.getEplVadfOwners(), user);
        }

        eplNdcDao.update(updateDo, user);

        return ndc;
    }

    /**
     * searches by simple data field
     * 
     * @param searchType SearchType
     * @param defName definition name
     * @param value value
     * @param iStatuses statuses
     * @param rStatuses statuses
     * @param localUse local use
     * @return list
     */
    @Override
    protected List<EplNdcDo> searchBySimpleDataField(SearchType searchType, String defName, String value,
                                                     List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses,
                                                     LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(SELECT_ITEM_FROM).append(EplNdcDo.class).append(ITEM_JOIN)
            .append(ITEM, EplNdcDo.EPL_VADF_OWNERS).append(OWNERS_JOIN)
            .append(OWNERS, EplVadfOwnerDo.EPL_VA_DF_NON_LIST_VALUES).append("nonlist JOIN")
            .append("nonlist", EplVadfNonlistValueDo.EPL_VA_DF).append("def"));

        query.append(SearchQueryUtility.createNdcStatusLocaDispenseQuery(localUse, iStatuses, rStatuses));

        query.append(" AND def.vadfName = '");
        query.append(defName);
        query.append("' AND  nonlist.vaDfValue ");

        if (searchType.equals(SearchType.CONTAINS)) {
            query.append(LIKE_SQUOTE_WC);
            query.append(value);
            query.append(WC_SQUOTE);

            // NdcDomainCapability: searchBySimpleDataField Begins With search
        } else if (searchType.equals(SearchType.BEGINS_WITH)) {
            query.append(LIKE_SQUOTE);
            query.append(value);
            query.append(WC_SQUOTE);

            // NdcDomainCapability: searchBySimpleDataField Equals search
        } else {
            query.append(EQ_SQUOTE);
            query.append(value);
            query.append(SQUOTE);
        }

        List<EplNdcDo> returnedDos = eplNdcDao.executeHqlQuery(query.toString());

        // return list of NdcDo data objects
        return returnedDos;
    }

    /**
     * This method searches the epl_va_df_assoc_values table and returns the corresponding collection of ndc's
     * 
     * @param searchType this is the type field that will be searched.
     * @param defName definition name
     * @param value value
     * @param iStatuses statuses
     * @param rStatuses statuses
     * @param localUse local use
     * @return list
     */
    @Override
    protected List<EplNdcDo> searchByListDataField(SearchType searchType, String defName, String value,
        List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses, LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(SELECT_ITEM_FROM).append(EplNdcDo.class).append(ITEM_JOIN)
            .append(ITEM, EplNdcDo.EPL_VADF_OWNERS).append(OWNERS_JOIN)
            .append(OWNERS, EplVadfOwnerDo.EPL_VA_DF_ASSOC_VALUES).append(LIST_JOIN)
            .append(LIST, EplVadfAssocValueDo.EPL_VA_DF_LOV).append("lovs JOIN")
            .append("lovs", EplVadfLovDo.EPL_VA_DF).append(DFNAME));

        query.append(SearchQueryUtility.createNdcStatusLocaDispenseQuery(localUse, iStatuses, rStatuses));

        query.append(AND_DFNAME);
        query.append(EplVaDfDo.NAME);
        query.append(EQ_SQUOTE);
        query.append(defName);
        query.append("' AND  list.key.listValue ");

        if (searchType.equals(SearchType.CONTAINS)) {
            query.append(LIKE_SQUOTE_WC);
            query.append(value);
            query.append(WC_SQUOTE);
        } else if (searchType.equals(SearchType.BEGINS_WITH)) {
            query.append(LIKE_SQUOTE);
            query.append(value);
            query.append(WC_SQUOTE);

            // Else if Equals in NdcDomainCapability searchByListDataField
        } else {
            query.append(EQ_SQUOTE);
            query.append(value);
            query.append(SQUOTE);
        }

        List<EplNdcDo> returnedDos = eplNdcDao.executeHqlQuery(query.toString());

        // return the dataobjects for searchByListDataField
        return returnedDos;
    }

    /**
     * searches by multi text data field
     * 
     * @param searchType String
     * @param defName String
     * @param value String
     * @param iStatuses List<ItemStatus>
     * @param rStatuses List<RequestItemStatus>
     * @param localUse boolean
     * @return List<EplProductDo>
     * 
     */
    @Override
    protected List<EplNdcDo> searchByMultiTextDataField(SearchType searchType, String defName, String value,
                                                        List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses,
                                                        LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(SELECT_ITEM_FROM).append(EplNdcDo.class).append(ITEM_JOIN)
                .append(ITEM, EplNdcDo.EPL_VADF_OWNERS).append(OWNERS_JOIN)
                .append(OWNERS, EplVadfOwnerDo.EPL_VA_DF_MULTI_TEXTS).append(LIST_JOIN)
                .append(LIST, EplMultiTextDo.EPL_VA_DF).append(DFNAME));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));

        query.append(AND_DFNAME);
        query.append(EplVaDfDo.NAME);
        query.append(EQ_SQUOTE);
        query.append(defName);
        query.append("' AND  list.key.text ");

        if (searchType.equals(SearchType.CONTAINS)) {
            query.append(LIKE_SQUOTE_WC);
            query.append(value);
            query.append(WC_SQUOTE);
        } else if (searchType.equals(SearchType.BEGINS_WITH)) {
            query.append(LIKE_SQUOTE);
            query.append(value);
            query.append(WC_SQUOTE);
        } else {
            query.append(EQ_SQUOTE);
            query.append(value);
            query.append(SQUOTE);
        }

        List<EplNdcDo> returnedDos = eplNdcDao.executeHqlQuery(query.toString());

        return returnedDos;
    }

    /**
     * Adds to the specified Criteria, additional 'AND'-ed criteria that is specified on the top-level panel on the advanced
     * search page. These additional criteria are always 'AND'-ed into the overall advanced search query, regardless of if
     * the mode is OR or AND for the NdcDomainCapabilityImpl.
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @param criteria The Criteria to add additional queries to
     */
    @Override
    protected void addTopPanelCriteria(SearchCriteriaVo searchCriteria, Criteria criteria) {

        if (LocalUseSearchType.LOCAL_USE.equals(searchCriteria.getLocalUse())) {
            criteria.add(Restrictions.eq(EplNdcDo.LOCAL_DISPENSE, "Y"));
        } else if (LocalUseSearchType.NOT_LOCAL_USE.equals(searchCriteria.getLocalUse())) {
            criteria.add(Restrictions.eq(EplNdcDo.LOCAL_DISPENSE, "N"));
        }

        SearchQueryUtility.createItemStatusCriteria(searchCriteria.getItemStatus(), criteria, EplNdcDo.ITEM_STATUS);

        SearchQueryUtility.createRequestItemStatusCriteria(searchCriteria.getRequestStatus(), criteria,
            EplNdcDo.REQUEST_STATUS);
    }

    /**
     * set the NDC Dao
     * 
     * @param eplNdcDao eplNdcDao property
     */
    public void setEplNdcDao(EplNdcDao eplNdcDao) {
        this.eplNdcDao = eplNdcDao;
    }

    /**
     * Search for NDCs
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @return List<NdcVo>
     */
    @Override
    public List<NdcVo> search(SearchCriteriaVo searchCriteria) {

        List<NdcVo> results = new ArrayList<NdcVo>();
        List<NdcVo> ndcs = new ArrayList<NdcVo>();

        if (searchCriteria.isAdvanced()) {

            SearchCriteriaVo currCriteria = searchCriteria;
            List<SearchTermVo> terms = new ArrayList<SearchTermVo>();

            // set the search terms for the NDC Search in the NDCDomainCapabilityIMPl
            for (SearchTermVo term : searchCriteria.expandSearchTerms()) {
                boolean isAndSearch = term.getAdvancedAndSearch();

                terms.add(term);

                if (isAndSearch) {
                    continue;

                } else {
                    currCriteria.setSearchTerms(terms);
                    ndcs = searchAdvanced(currCriteria);
                    terms.clear();
                }

                results.addAll(ndcs);
            }

        } else {
            SearchTermVo searchTermVo = searchCriteria.getSearchTerms().get(0);

            // set the NDCSearch Terms.
            if (searchTermVo.getSearchField().getFieldKey().equals(FieldKey.SEARCH_ALL_FIELDS)) {
                results = simpleAllSearch(searchCriteria);
            } else {
                results = simpleSearch(searchCriteria);
            }
        }

        return results;
    }

    /** 
     * ndcExists
     * @param ndcNoDashes ndcNoDashes
     * @return True if the NDC String exists in the database
     */
    public boolean ndcExists(String ndcNoDashes) {

        String query = "select count(*) from EplNdcDo ndc where ndc.ndcNoDashes LIKE '%" + ndcNoDashes + "%'";

        List<Long> returnedDos = eplNdcDao.executeHqlQuery(query);

        if (returnedDos.size() > 0) {
            if (returnedDos.get(0).equals(Long.valueOf(0))) {
                return false;
            }
        }

        return true;

    }
    
    /** 
     * getIdFromNDC
     * @param ndcNoDashes ndcNoDashes
     * @return True if the NDC String exists in the database
     */
    public long getIdFromNDC(String ndcNoDashes) {

        String query = "select ndc.eplId from EplNdcDo ndc where ndc.ndcNoDashes LIKE '%" + ndcNoDashes + "%'";

        List<Long> returnedDos = eplNdcDao.executeHqlQuery(query);

        if (returnedDos.size() == 1) {
            return returnedDos.get(0);
        } else {
            return 0;
        }

    }

    /**
     * Search for NDCs
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @return List<NdcVo>
     */
    @Override
    public List<NdcVo> searchFullList(SearchCriteriaVo searchCriteria) {

        List<NdcVo> results;

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

        // return the results for the NDCVo full list search
        return results;
    }

    /**
     * Search for NDCs
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @return List<NdcVo>
     */
    private List<NdcVo> simpleAllSearch(SearchCriteriaVo searchCriteria) {

        List<Long> ids = reportDomainCapability.getSimpleSearchIds(searchCriteria);

        Criteria criteria = getDataAccessObject().getCriteria("ITEM");
        criteria = createSimpleAllSearchTermCriteria(searchCriteria, criteria, ids, true);

        // get one page of data
        criteria.setFirstResult(searchCriteria.getStartRow());
        criteria.setMaxResults(searchCriteria.getPageSize());

        List<EplNdcDo> data = criteria.list();

        // get row count for all results for the list of ids
        //Criteria count = createSimpleAllSearchTermCriteria(searchCriteria, criteria, ids,  false);
        //int fullSize = getDataAccessObject().getCount(count);
        int fullSize = ids.size();

        List<NdcVo> valueObjects = getConverter().convertSearch(data);

        return new PaginatedList<NdcVo>(valueObjects, fullSize, searchCriteria.getSortedFieldKey(),
            searchCriteria.getSortOrder(), searchCriteria.getStartRow(), searchCriteria.getPageSize());

//        
//        
//        long time1 = System.currentTimeMillis();
//        
//        
//        
//        
//        List<Long> ids = reportDomainCapability.getSimpleSearchIds(searchCriteria);
//        long time2 = System.currentTimeMillis();
//      
//        if (ids.size() < 1) {
//            return new ArrayList<NdcVo>();
//        }
//        
//        StringBuffer query = new StringBuffer();
//        query.append(HqlBuilder.create(SELECT_ITEM_FROM).append(EplNdcDo.class).append("item WHERE "));
//                
//        query.append(" EPL_ID IN ( ");
//        
//        for (int i = 0 ; i < ids.size() && i < 1000; i++) {
//            query.append(ids.get(i).toString()).append(",");
//        }
//        
//        String submitQuery = query.substring(0, query.toString().length() - 1) + " )";
//        
//        long time3 = System.currentTimeMillis();
//        List<EplNdcDo> returnedDos = eplNdcDao.executeHqlQuery(submitQuery);
//      
//        long time4 = System.currentTimeMillis();
//        List<NdcVo> list = getConverter().convert(returnedDos);
//        long time5 = System.currentTimeMillis();
//        
//        LOG.debug("TIME1 : " + (time2 - time1));
//        LOG.debug("TIME2 : " + (time3 - time2));
//        LOG.debug("TIME3 : " + (time4 - time3));
//        LOG.debug("TIME4 : " + (time5 - time4));
//        
//        
//        return list;

    }

    /**
     * Search for NDCs
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @return List<NdcVo>
     */
    private List<NdcVo> simpleAllSearchFullList(SearchCriteriaVo searchCriteria) {

        List<Long> ids = reportDomainCapability.getSimpleSearchIds(searchCriteria);

        Criteria criteria = getDataAccessObject().getCriteria("ITEM");
        criteria = createSimpleAllSearchTermCriteria(searchCriteria, criteria, ids, true);

        List<EplNdcDo> data = criteria.list();

        // get row count for all results
        //Criteria count = createSimpleAllSearchTermCriteria(searchCriteria, criteria, ids,  false);
        //int fullSize = getDataAccessObject().getCount(count);
        int fullSize = ids.size();

        List<NdcVo> valueObjects = getConverter().convertSearch(data);

        return new PaginatedList<NdcVo>(valueObjects, fullSize, searchCriteria.getSortedFieldKey(),
            searchCriteria.getSortOrder(), searchCriteria.getStartRow(), searchCriteria.getPageSize());

    }

    /**
     * Tests if the VO and DO NDC EPL IDs are the same.
     * 
     * @param ndcVo NdcVo
     * @param ndcDo EplNdcDo
     * @return boolean True if VO and DO have the same EPL IDs.
     */
    protected boolean isSameId(NdcVo ndcVo, EplNdcDo ndcDo) {
        return (ndcVo.getId() != null && ndcDo.getEplId() != null && ndcVo.getId().equals(ndcDo.getEplId().toString()));
    }

    /**
     * Search by Manufacturer
     * 
     * @param searchType - SearchType
     * @param value String
     * @param columnName String
     * @param iStatuses - List of ItemStatus
     * @param rStatuses - List of Request Item Status
     * @param localUse - Local use filter
     * @return List of EPlNdcDo
     */
    @SuppressWarnings("unused")
    private List<EplNdcDo> searchByManuf(SearchType searchType, String value, String columnName, List<ItemStatus> iStatuses,
                                         List<RequestItemStatus> rStatuses, LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(SELECT_ITEM_FROM).append(EplNdcDo.class).append(ITEM_JOIN)
                .append(ITEM, EplNdcDo.MANUFACTURER).append("manu"));

        query.append(SearchQueryUtility.createNdcStatusLocaDispenseQuery(localUse, iStatuses, rStatuses));
        query.append("AND manu.");
        query.append(SearchQueryUtility.getColumnClause(searchType, columnName, value));

        List<EplNdcDo> returnedDos = eplNdcDao.executeHqlQuery(query.toString());

        return returnedDos;

    }

    /**
     * searchOnNdcTable
     * 
     * @param searchType - SearchType
     * @param value String
     * @param columnName String
     * @param iStatuses - List of ItemStatus
     * @param rStatuses - List of Request Item Status
     * @param localUse - Local use filter
     * @return List of EPlNdcDo
     */
    public List<EplNdcDo> searchOnNdcTable(SearchType searchType, String value, String columnName,
        List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses, LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(SELECT_ITEM_FROM).append(EplNdcDo.class).append(ITEM));

        query.append(SearchQueryUtility.createNdcStatusLocaDispenseQuery(localUse, iStatuses, rStatuses));
        query.append("AND item.");
        query.append(SearchQueryUtility.getColumnClause(searchType, columnName, value));

        List<EplNdcDo> returnedDos = eplNdcDao.executeHqlQuery(query.toString());

        return returnedDos;
    }

    /**
     * Search ndc advanced.
     * 
     * @param searchCriteria SearchCriteriaVo
     * @return List of NdcVo
     */
    private List<NdcVo> searchAdvanced(SearchCriteriaVo searchCriteria) {

        boolean isAndSearch = searchCriteria.isAdvancedAndSearch();
        AbstractAdvancedSearchHelper searchHelper;

        searchHelper = new AdvancedSearchHelper(eplNdcDao, searchCriteria);

        int nonListCount = 0;
        List<SearchTermVo> nonListSearchTerms = new ArrayList<SearchTermVo>();
        List<SearchTermVo> listSearchTerms = new ArrayList<SearchTermVo>();
        List<SearchTermVo> listMultiTextSearchTerms = new ArrayList<SearchTermVo>();

        // only do ndc, trade name and nonlist ones
        for (SearchTermVo term : searchCriteria.expandSearchTerms()) {

            // Skip no-op terms.            
            if (FieldKey.SEARCH_ALL_FIELDS.equals(term.getFieldKey())
                || FieldKey.SEARCH_ALL_DESIGNATED_FIELDS.equals(term.getFieldKey())
                || FieldKey.SEARCH_NO_FIELDS.equals(term.getFieldKey())) {
                continue;
            }

            isAndSearch = term.getAdvancedAndSearch();

            if (isAndSearch) {
                searchHelper = new AndAdvancedSearchHelper(eplNdcDao, searchCriteria);
            }

            MatchMode mode = MatchMode.EXACT;

            if (SearchType.CONTAINS.equals(term.getSearchType())) {
                mode = MatchMode.ANYWHERE;
            } else if (SearchType.BEGINS_WITH.equals(term.getSearchType())) {
                mode = MatchMode.START;
            }

            if (FieldKey.NDC.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.NDC_NO_DASHES, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.COLOR.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.COLOR, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.IMPRINT.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.IMPRINT, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.IMPRINT2.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.IMPRINT2, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.SINGLE_MULTISOURCE_NDC.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.SINGLE_MULTI_SOURCE, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.MANUFACTURER.equals(term.getFieldKey())) {
                Criteria manuf = searchHelper.getSubCriteria(EplNdcDo.MANUFACTURER);
                manuf.add(Restrictions.ilike(EplManufacturerDo.NAME, term.formatValue(), mode));
            } else if (FieldKey.LOCAL_DISPENSE.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.LOCAL_DISPENSE, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.NDC_DISP_UNITS_PER_ORD_UNIT.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.eq(EplNdcDo.NDC_DISP_UNIT_PER_ORD_UNIT, new Double(term.formatValue())),
                    isAndSearch);
            } else if (FieldKey.OTC_RX_INDICATOR.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.OTC_RX, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.SOURCE.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.SOURCE, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.SHAPE.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.SHAPE, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.REJECTION_REASON_TEXT.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.REJECT_REASON_TEXT, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.REQUEST_REJECTION_REASON.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.REQUEST_REJECT_REASON, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.UPC_UPN.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.UPC_UPN, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.TRADE_NAME.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.TRADE_NAME, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.TEN_DIGIT_FORMAT_INDICATION.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.TEN_DIGIT_FORMAT_INDICATION, term.formatValue(), mode),
                    isAndSearch);
            } else if (FieldKey.TEN_DIGIT_NDC.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.TEN_DIGIT_NDC, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.VENDOR_STOCK_NUMBER.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.VSN, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.VENDOR.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.ilike(EplNdcDo.VENDOR, term.formatValue(), mode), isAndSearch);
            } else if (FieldKey.PACKAGE_SIZE.equals(term.getFieldKey())) {
                searchHelper.add(Restrictions.eq(EplNdcDo.PACKAGE_SIZE, new Double(term.formatValue())), isAndSearch);
            } else if (FieldKey.INACTIVATION_DATE.equals(term.getFieldKey())) {
                try {
                    searchHelper.add(
                        Restrictions.eq(EplNdcDo.INACTIVATION_DATE, DateFormatUtility.convertToDate(term.formatValue())),
                        isAndSearch);
                } catch (ParseException pe) {
                    throw new DomainValidationException(pe, DomainValidationException.FIELD_FORMAT_ERROR, term.getFieldKey()
                        .getLocalizedWidth(Locale.getDefault()), term.formatValue());
                }
            } else if (FieldKey.PACKAGE_TYPE.equals(term.getFieldKey())) {
                Criteria packT = searchHelper.getSubCriteria(EplNdcDo.PACKAGE_TYPE);
                packT.add(Restrictions.ilike(EplPackageTypeDo.NAME, term.formatValue(), mode));
            } else if (FieldKey.ORDER_UNIT.equals(term.getFieldKey())) {
                Criteria orderUnit = searchHelper.getSubCriteria(EplNdcDo.ORDER_UNIT);
                orderUnit.add(Restrictions.ilike(EplOrderUnitDo.ABBREV, term.formatValue(), mode));
            } else if (term.getFieldKey().isSimpleDataField()) {
                if (nonListCount == 0) { // Can only do one Hibernate query of this type, then after that must do manually

                    // (see below).
                    Criteria ownerCriteria = searchHelper.getSubCriteria(EplNdcDo.EPL_VADF_OWNERS);
                    Criteria vaDfValueCriteria = ownerCriteria.createCriteria(EplVadfOwnerDo.EPL_VA_DF_NON_LIST_VALUES);
                    vaDfValueCriteria.add(Restrictions.ilike(EplVadfNonlistValueDo.VA_DF_VALUE, term.formatValue(), mode));

                    Criteria vaDfCriteria = vaDfValueCriteria.createCriteria(EplVadfNonlistValueDo.EPL_VA_DF);
                    vaDfCriteria.add(Restrictions.eq(EplVaDfDo.NAME, term.getFieldKey().getKey()));
                } else {
                    nonListSearchTerms.add(term);
                }

                // increment counter of non_list VA data fields.
                nonListCount++;
            } else if (term.getFieldKey().isListDataField()) {

                if (term.getFieldKey().isMultiSelectListDataField()) {
                    throw new UnsupportedOperationException(
                        "There is no support for advanced searches on multi-select list data fields such as '"
                            + term.getFieldKey()
                            + "'.\nYou must add a separate FieldKey entry (ending with '_SEARCHABLE' preferably) that is a "
                            + "single select list,\nand then add a mapping to the 'SEARCH_KEY_TO_MAIN_KEY_MAP' map's static "
                            + "initializer.");
                }

                listSearchTerms.add(term);
            } else if (term.getFieldKey().isPrimaryKeyDataField() || term.getFieldKey().isMultitextDataField()) {
                listMultiTextSearchTerms.add(term);
            } else {
                throw new UnsupportedOperationException("No advanced search support for field '" + term.getFieldKey()
                    + SQUOTE);
            }

        } // end for

        // Perform the advanced search.
        Set<EplNdcDo> resultCollection =
            performAdvancedSearchWithCriteria(searchCriteria, eplNdcDao, searchHelper, new NdcDoComparator(),
                nonListSearchTerms, listSearchTerms, listMultiTextSearchTerms, null);

        List<NdcVo> ndcs = new ArrayList<NdcVo>();

        for (EplNdcDo data : resultCollection) {
            NdcVo vo = ndcConverter.convert(data);
            ndcs.add(vo);
        }

        return ndcs;
    }
    
   

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    @Override
    public DataAccessObject getDataAccessObject() {
        return eplNdcDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    @Override
    public NdcConverter getConverter() {
        return ndcConverter;
    }

    /**
     * setNdcImageCapability
     * 
     * @param ndcImageCapability ndcImageCapability property
     */
    public void setNdcImageCapability(NdcImageCapability ndcImageCapability) {
        this.ndcImageCapability = ndcImageCapability;
    }

    /**
     * setNdcConverter
     * 
     * @param ndcConverter ndcConverter property
     */
    public void setNdcConverter(NdcConverter ndcConverter) {
        this.ndcConverter = ndcConverter;
    }

    /**
     * setReportDomainCapability for NdcDomainCapabilityImpl
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
    private class NdcDoComparator implements Comparator<EplNdcDo> {

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
        public int compare(EplNdcDo o1, EplNdcDo o2) {
            return o1.getEplId().compareTo(o2.getEplId());
        }
    }
}
