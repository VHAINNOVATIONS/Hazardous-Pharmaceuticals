/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.ClassUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PaginatedList;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermConjunction;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.domain.common.capability.DataFieldsDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.PartialSaveMgtDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.SeqNumDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplMultiTextDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdIngredientAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSynonymDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfAssocValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfNonlistValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;
import gov.va.med.pharmacy.peps.domain.common.utility.ManagedItemDomainCapabilityFactory;
import gov.va.med.pharmacy.peps.domain.common.utility.SchemaUtility;
import gov.va.med.pharmacy.peps.domain.common.utility.SearchQueryUtility;


/**
 * Perform common CRUD operations on ManagedItemVo.
 * 
 * @param <T> type of ManagedItemVo
 * @param <DO> type of DataObject
 */
public abstract class ManagedItemDomainCapabilityImpl<T extends ManagedItemVo, DO extends DataObject> implements
        ManagedItemDomainCapability<T, DO> {

    /** EPL_ID */
    protected static final String EPL_ID = "eplId";
    
    private static final String SUBCLASS_IMPL = "Sub-classes need to provide implementation";
    private static final String NO_CACHED_CRIT_OR = "No support for cached Criteria for the OR search.";
    private static final String DASH = "-";

    private Class<DO> dataObjectClass;
    private Class<T> managedItemClass;

    private ManagedItemDomainCapabilityFactory managedItemDomainCapabilityFactory;

    private DataFieldsDomainCapability dataFieldsDomainCapability;
    private PartialSaveMgtDomainCapability partialSaveMgtDomainCapability;
    private RequestDomainCapability requestDomainCapability;
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private SeqNumDomainCapability seqNumDomainCapability;

    /**
     * Update the given {@link ManagedItemVo}.
     * 
     * @param data {@link ManagedItemVo} to update
     * @param user {@link UserVo} performing the action
     * @return updated {@link ManagedItemVo}
     * @throws DuplicateItemException if the item being updated causes it to be a duplicate
     */
    @Override
    public T update(T data, UserVo user) throws DuplicateItemException {
        DO dataDo = getConverter().convert(data);
        DO returnedDo = (DO) getDataAccessObject().update(dataDo, user);
        T updatedData = getConverter().convert(returnedDo);

        return updatedData;
    }

    /**
     * Insert the given {@link ManagedItemVo}.
     * 
     * @param data {@link ManagedItemVo} to create
     * @param user {@link UserVo} performing the action
     * @return {@link ManagedItemVo} with new ID
     */
    @Override
    public T createWithoutDuplicateCheck(T data, UserVo user) {
        if (data.getId() == null) {
            String id = getSeqNumDomainCapability().generateId(data.getEntityType(), user);
            data.setId(id);
        }

        DO dataDo = getConverter().convert(data);
        getDataAccessObject().insert(dataDo, user);

        return data;
    }

    /**
     * Retrieves revision Number
     * 
     * @param id of item
     * @return long revision number of the item
     * @throws ItemNotFoundException ItemNotFoundException
     */
    @Override
    public long getRevisionNumber(String id) throws ItemNotFoundException {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.setCacheable(true).setCacheRegion(PPSConstants.NATIONAL_DATA_CACHE);
        String idProperty = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.ID);
        criteria.add(Restrictions.eq(idProperty, getDataAccessObject().convertId(id)));
        String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.REVISION_NUMBER);
        criteria.setProjection(Projections.property(propertyName));

        Long revisionNumber = (Long) criteria.uniqueResult();

        if (revisionNumber == null) {
            throw new ItemNotFoundException(ItemNotFoundException.ITEM_NOT_FOUND, id);
        }

        return revisionNumber.longValue();
    }

    /**
     * Prevent retrieving all ManagedItemVo since that could be a large list.
     * 
     * @return Empty list 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability#retrieve()
     */
    @Override
    public List<T> retrieve() {
        return Collections.EMPTY_LIST;
    }

    /**
     * Retrieve a partial list of items for the given parent Item ID. The list will begin at index startRow and will have
     * pageSize number of rows in it. The list will be sorted by sortedFieldKey in the given sortOrder.
     * <p>Default implementation returns {@link ManagedItemDomainCapability#retrieveChildren(String)} as a {@link PaginatedList}
     * resulting in the full list returned as "paged" even though it is not.
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
    public PaginatedList<T> retrieveChildren(String parentItemId, FieldKey sortedFieldKey, SortOrder sortOrder,
        int startRow, int pageSize) {
        PaginatedList<T> paged =
            new PaginatedList<T>(retrieveChildren(parentItemId), retrieveChildrenCount(parentItemId), sortedFieldKey,
                sortOrder, startRow, pageSize);

        return paged;
    }

    /**
     * Retrieve a partial list of ACTIVE items for the given parent Item ID. The list will begin at index startRow and will
     * have pageSize number of rows in it. The list will be sorted by sortedFieldKey in the given sortOrder.
     * <p>Default implementation returns {@link ManagedItemDomainCapability#retrieveChildren(String)} as a {@link PaginatedList}
     * resulting in the full list returned as "paged" even though it is not.
     * 
     * @param parentItemId String ID of parent for which to retrieve active children items
     * @param sortedFieldKey FieldKey representing field to sort by
     * @param sortOrder SortOrder representing ascending or descending sort
     * @param startRow integer index in full list of active child items from which to start retrieving
     * @param pageSize integer number of rows to retrieve
     * 
     * @return PaginatedList<ManagedItemVo> child items
     */
    @Override
    public PaginatedList<T> retrieveActiveChildren(String parentItemId, FieldKey sortedFieldKey, SortOrder sortOrder,
        int startRow, int pageSize) {
        PaginatedList<T> paged =
            new PaginatedList<T>(retrieveChildren(parentItemId), retrieveChildrenCount(parentItemId), sortedFieldKey,
                sortOrder, startRow, pageSize);

        return paged;
    }

    /**
     * Retrieve a list of partially populated child {@link ManagedItemVo} for the given parent {@link ManagedItemVo} ID.
     * <p>Default implementation calls {@link #retrieveChildren(String)}. Note that this means that this method may fully
     * populate the value objects, unless a sub class overrides the method!
     * 
     * @param parentId String parent ID
     * @return List of ManagedItemVo
     */
    @Override
    public List<T> retrieveMinimalChildren(String parentId) {
        return retrieveChildren(parentId);
    }

    /**
     * Retrieve the {@link ManagedItemVo} with the given ID.
     * <p>If the item type retrieved has children, populates only partial child items! So the NDCs on the returned Product are
     * not fully populated. To get a fully populated list of children, call {@link #retrieveChildren(String)}.
     * 
     * @param id String item ID
     * @return ProductVo
     * @throws ItemNotFoundException ItemNotFoundException
     */
    @Override
    public T retrieve(String id) throws ItemNotFoundException {
        T parent = retrieveParent(id);

        if (parent.hasChildren()) {
            ManagedItemDomainCapability childCapability =
                getManagedItemDomainCapabilityFactory().getDomainCapability(parent.getEntityType().getChild());
            List<ManagedItemVo> children = childCapability.retrieveMinimalChildren(id);
            parent.setChildren(children);
        }

        return parent;
    }

    /**
     * Retrieves minimal of the {@link ManagedItemVo}
     * 
     * @param id String item ID
     * @return the minimal {@link ManagedItemVo}
     */
    @Override
    public T retrieveMinimal(String id) {
        DO objectDo = (DO) getDataAccessObject().retrieve(Long.valueOf(id));

        return getConverter().convertMinimal(objectDo);
    }

    /**
     * Retrieve the ManagedItemVo with the given ID.
     * <p>Does not populate any child items, even if the ManagedItemVo type has children. If using this method and the children
     * are needed, also call {@link #retrieveChildren(String)} or 
     * {@link #retrieveChildren(String, int, FieldKey, SortOrder, int)}.
     * 
     * @param id String ManagedItemVo ID
     * @return ManagedItemVo
     * @throws ItemNotFoundException ItemNotFoundException
     */
    @Override
    public T retrieveParent(String id) throws ItemNotFoundException {
        try {
            Serializable idObject = getDataAccessObject().convertId(id);
            DO data = (DO) getDataAccessObject().retrieve(idObject);
            getDataAccessObject().refresh(data);

            T value = getConverter().convert(data);
            Collection<ItemAuditHistoryVo> audits =
                getItemAuditHistoryDomainCapability().retreiveByAuditItemId(value.getId(), value.getEntityType().name());
            value.setItemAuditHistory(audits);

            if (FieldKey.VA_DATA_FIELDS.getEntityTypes().contains(value.getEntityType())) {
                DataFields dataFields = dataFieldsDomainCapability.retrieve(Long.valueOf(id), value.getEntityType());
                value.setVaDataFields(dataFields);
            }

            return value;
        } catch (ObjectNotFoundException e) {
            throw new ItemNotFoundException(e, ItemNotFoundException.ITEM_NOT_FOUND, id);
        }
    }

    /**
     * Retrieve the ManagedItemVo by its unique attributes (not by ID). If the item is not found by the unique fields, an
     * empty List is returned.
     * <p>If the given item's ID is numeric, then a not equals criteria is added such that this method does not return the same
     * item as a duplicate.
     * <p>Most item types will return a List with one element, but some (like NDC) may actually return more than one.
     * 
     * @param data {@link ManagedItemVo}
     * @return duplicate {@link ManagedItemVo}
     */
    @Override
    public List<T> retrieveDuplicates(T data) {
        Criteria criteria = createUniquenessCriteria(data);
        criteria.setCacheable(true).setCacheRegion(PPSConstants.NATIONAL_DATA_CACHE);

        String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.ITEM_STATUS);
        criteria.add(Restrictions.ne(propertyName, ItemStatus.ARCHIVED.name()));

        if (NumberUtils.isNumber(data.getId())) {
            criteria.add(Restrictions.ne(EPL_ID, Long.valueOf(data.getId())));
        }

        criteria.setCacheable(true).setCacheRegion(PPSConstants.NATIONAL_DATA_CACHE);
        List<DO> items = criteria.list();

        return getConverter().convert(items);
    }

    /**
     * True if the item exists by uniqueness fields.
     * 
     * @param item {@link ManagedItemVo} from which to get uniqueness fields to test
     * @return boolean
     */
    @Override
    public boolean existsByUniquenessFields(T item) {
        Criteria criteria = createUniquenessCriteria(item);
        String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.ITEM_STATUS);
        criteria.add(Restrictions.ne(propertyName, ItemStatus.ARCHIVED.name()));
        criteria.setCacheable(true).setCacheRegion(PPSConstants.NATIONAL_DATA_CACHE);

        return getDataAccessObject().getCount(criteria) != 0;
    }

    /**
     * True if the given ID exists.
     * <p>
     * If the given ID is null, returns false.
     * 
     * @param id EPL ID
     * @return boolean
     */
    @Override
    public boolean existsById(String id) {
        boolean exists = false;

        if (id != null) {
            exists = getDataAccessObject().exists(Long.valueOf(id));
        }

        return exists;
    }

//    /**
//     * Perform all items simple search.
//     * @param searchCriteria {@link SearchCriteriaVo}
//     * @return List of {@link ManagedItemVo}
//     */
//    public List<T> searchAllItemsSimpleDataField(SearchCriteriaVo searchCriteria) {
//        return null;
//    }

    /**
    * Perform a simple search.
    * 
    * @param searchCriteria {@link SearchCriteriaVo}
    * @return List of {@link ManagedItemVo}
    */
    protected List<T> simpleSearch(SearchCriteriaVo searchCriteria) {

        Criteria criteria = createSimpleSearchCriteria(searchCriteria, true);

        // get one page of data
        criteria.setFirstResult(searchCriteria.getStartRow());
        criteria.setMaxResults(searchCriteria.getPageSize());

//        if (searchCriteria.getLocalUse().equals(LocalUseSearchType.ALL_ITEMS)) {
//            
//            if (searchCriteria.getEntityType().equals(EntityType.PRODUCT)) {
//                return this.searchAllItemsSimpleDataField(searchCriteria);
//            } else if (searchCriteria.getEntityType().equals(EntityType.NDC)) {
//                return this.searchAllItemsSimpleDataField(searchCriteria);
//            } else if (searchCriteria.getEntityType().equals(EntityType.ORDERABLE_ITEM)) {
//                return this.searchAllItemsSimpleDataField(searchCriteria);
//            }
//        }

        List<DO> data = criteria.list();

        // get row count for all results
        Criteria count = createSimpleSearchCriteria(searchCriteria, false);
        int fullSize = getDataAccessObject().getCount(count);

        filterMultiValueDisplayableFields(searchCriteria.getSearchTerms().get(0).getFieldKey(), searchCriteria
            .getSearchTerms().get(0).getValue(), searchCriteria.getSearchTerms().get(0).getSearchType(), data);

        List<T> valueObjects = getConverter().convertSearch(data);

        return new PaginatedList<T>(valueObjects, fullSize, searchCriteria.getSortedFieldKey(),
            searchCriteria.getSortOrder(), searchCriteria.getStartRow(), searchCriteria.getPageSize());
    }

    /**
     * Filters out the display values in multi-value fields which do not match the search criteria
     *
     * @param searchKey - the field key searched on
     * @param searchTerm - the search term matching the field key searched on
     * @param searchType - the @SearchType
     * @param data the data coming back from criteria.list();
     */
    public void filterMultiValueDisplayableFields(FieldKey searchKey, String searchTerm, SearchType searchType,
        List<DO> data) {
        
        String searchT = searchTerm;
        Pattern pattern = null;

        if (StringUtils.contains(searchT, PPSConstants.PERCENT)) {
            searchT = StringUtils.remove(searchT, PPSConstants.PERCENT);
        } else if (StringUtils.contains(searchT, PPSConstants.UNDERSCORE)) {
            String[] splitValue = StringUtils.split(searchT, PPSConstants.UNDERSCORE);
            int underScoreCount = StringUtils.countMatches(searchT, PPSConstants.UNDERSCORE);
            StringBuffer regex = new StringBuffer();
            int i = 1;

            for (String value : splitValue) {
                regex.append(value);

                if (underScoreCount >= i) {
                    regex.append("[a-zA-Z0-9]");
                }

                i++;
            }
            
            pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE);
        }

        if (!searchT.isEmpty()) {
            for (DO item : data) {
                if (item.getClass().equals(EplProductDo.class)) {
                    if (searchKey.equals(FieldKey.SYNONYM_NAME)) {
                        Set<EplSynonymDo> multiValueFields = new LinkedHashSet<EplSynonymDo>();

                        for (EplSynonymDo multiValueField : ((EplProductDo) item).getEplSynonyms()) {
                            String value = multiValueField.getSynonymName();

                            filterMultiValueDisplayableFieldsHelper(searchT, searchType, pattern, multiValueFields,
                                multiValueField, value);
                        }

                        ((EplProductDo) item).setEplSynonyms(multiValueFields);
                    } else if (searchKey.equals(FieldKey.ACTIVE_INGREDIENT)) {
                        Set<EplProdIngredientAssocDo> multiValueFields = new HashSet<EplProdIngredientAssocDo>();

                        for (EplProdIngredientAssocDo multiValueField : ((EplProductDo) item).getEplProdIngredientAssocs()) {
                            String value = multiValueField.getEplIngredient().getName();

                            filterMultiValueDisplayableFieldsHelper(searchT, searchType, pattern, multiValueFields,
                                multiValueField, value);
                        }

                        ((EplProductDo) item).setEplProdIngredientAssocs(multiValueFields);
                    }
                } else if (item.getClass().equals(EplOrderableItemDo.class)) {
                    if (searchKey.equals(FieldKey.ORDERABLE_ITEM_SYNONYM)) {
                        Set<EplMultiTextDo> multiValueFields = new LinkedHashSet<EplMultiTextDo>();

                        for (EplVadfOwnerDo eplVadfOwnerDo : ((EplOrderableItemDo) item).getEplVadfOwners()) {
                            for (EplMultiTextDo multiValueField : eplVadfOwnerDo.getEplMultiTexts()) {
                                if (multiValueField.getEplVaDf().getVadfName()
                                    .equals(FieldKey.ORDERABLE_ITEM_SYNONYM.getKey())) {
                                    filterMultiValueDisplayableFieldsHelper(searchT, searchType, pattern,
                                        multiValueFields,
                                        multiValueField, multiValueField.getKey().getText());
                                } else {
                                    multiValueFields.add(multiValueField);
                                }
                            }

                            eplVadfOwnerDo.setEplMultiTexts(multiValueFields);
                        }
                    }
                }
            }
        }
    }

    /**
     * A helper function which performs the filtering for the filterMultiValueDisplayableFields method
     *
     * @param <E> - the type which extends DataObject
     * @param searchTerm - the search term matching the field key searched on
     * @param searchType - the @SearchType
     * @param pattern - the @Pattern to use for the regex
     * @param multiValueFields - the list of DataObjects to be set on the DO after filtering
     * @param multiValueField - the DataObject to filter
     * @param value - the value to filter on
     */
    private <E extends DataObject> void filterMultiValueDisplayableFieldsHelper(String searchTerm, SearchType searchType,
        Pattern pattern, Set<E> multiValueFields, E multiValueField, String value) {
        if (pattern != null) {
            Matcher matcher = pattern.matcher(value);

            if (matcher.find()) {
                multiValueFields.add(multiValueField);
            }
        } else if (searchType.equals(SearchType.CONTAINS) && StringUtils.containsIgnoreCase(value, searchTerm)) {
            multiValueFields.add(multiValueField);
        } else if (searchType.equals(SearchType.BEGINS_WITH) && StringUtils.startsWithIgnoreCase(value, searchTerm)) {
            multiValueFields.add(multiValueField);
        } else if (searchType.equals(SearchType.EQUALS) && StringUtils.equalsIgnoreCase(value, searchTerm)) {
            multiValueFields.add(multiValueField);
        }
    }

    /** 
     * Description
     *
     * @param searchCriteria SearchCriteriaVo
     * @return List of results
     */
    protected List<T> simpleSearchFullList(SearchCriteriaVo searchCriteria) {

        Criteria criteria = createSimpleSearchCriteria(searchCriteria, true);
        List<DO> data = criteria.list();

        // get row count for all results
        Criteria count = createSimpleSearchCriteria(searchCriteria, false);
        int fullSize = getDataAccessObject().getCount(count);

        List<T> valueObjects = getConverter().convertSearch(data);

        return new PaginatedList<T>(valueObjects, fullSize, searchCriteria.getSortedFieldKey(),
            searchCriteria.getSortOrder(), searchCriteria.getStartRow(), searchCriteria.getPageSize());
    }

    /**
     * Create the criteria to perform a simple search based upon the given {@link SearchCriteriaVo}. The resulting
     * {@link Criteria} would return an unsorted entire result (not paged).
     * <p>
     * This method is used twice by the {@link #simpleSearch(SearchCriteriaVo)} method. Once to get the count of the total
     * number of results, and a second time to perform the paged search.
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @param addOrder boolean true if "ORDER BY" clause should be added, false if performing a COUNT(*) operation
     * @return {@link Criteria} for a search using the given {@link SearchCriteriaVo}
     */
    private Criteria createSimpleSearchCriteria(SearchCriteriaVo searchCriteria, boolean addOrder) {
        Criteria criteria = createSimpleSearchItemCriteria(searchCriteria);
        criteria.setCacheable(true).setCacheRegion(PPSConstants.NATIONAL_DATA_CACHE);

        return createSimpleSearchTermCriteria(searchCriteria, criteria, addOrder);
    }

    /**
     * Most methods will not implment this. Only the ones used for Items.
     * 
     * @param managedItem ManagedItemVo
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    @Override
    public void checkForActiveDependencies(ManagedItemVo managedItem, UserVo user) throws ValidationException {

    }

    /**
     * retrieveDependencies.
     * 
     * @param managedItem ManagedItemVo
     * @return a List of dependent objects
     * @throws ValidationException ValidationException
     */
    @Override
    public List<ManagedItemVo> retrieveDependencies(T managedItem) throws ValidationException {
        return new ArrayList<ManagedItemVo>();
    }

    /**
     * Create the simple search {@link Criteria} that only represents the table for the {@link ManagedItemVo}. Using this
     * method allows for later joins against the {@link ManagedItemVo} table.
     * <p>
     * This method should be used in conjunction with {@link #createSimpleSearchTermCriteria(SearchCriteriaVo, Criteria)}.
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @return resulting {@link Criteria}
     */
    private Criteria createSimpleSearchItemCriteria(SearchCriteriaVo searchCriteria) {
        Criteria criteria = getDataAccessObject().getCriteria(PPSConstants.ITEM);

        criteria =
            SearchQueryUtility.createItemStatusCriteria(searchCriteria.getItemStatus(), criteria,
                SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.ITEM_STATUS));

        criteria =
            SearchQueryUtility.createRequestItemStatusCriteria(searchCriteria.getRequestStatus(), criteria,
                SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.REQUEST_ITEM_STATUS));

        criteria =
            SearchQueryUtility.createMedicationCategoriesCriteria(searchCriteria.getCategories(), criteria,
                SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.MEDICATION));

        criteria =
            SearchQueryUtility.createInvestigationalCategoriesCriteria(searchCriteria.getCategories(), criteria,
                SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.INVESTIGATIONAL));

        criteria =
            SearchQueryUtility.createCompoundCategoriesCriteria(searchCriteria.getCategories(), criteria, SchemaUtility
                    .getPropertyName(getDataObjectClass(), FieldKey.COMPOUND));

        criteria =
            SearchQueryUtility.createSupplyCategoriesCriteria(searchCriteria.getCategories(), criteria, SchemaUtility
                    .getPropertyName(getDataObjectClass(), FieldKey.SUPPLY));

        criteria =
            SearchQueryUtility.createHerbalSubCategoriesCriteria(searchCriteria.getSubCategories(),
                criteria, SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.HERBAL));

        criteria =
            SearchQueryUtility.createChemotherapySubCategoriesCriteria(searchCriteria.getSubCategories(), criteria,
                SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.CHEMOTHERAPY));

        criteria =
            SearchQueryUtility.createOtcSubCategoriesCriteria(searchCriteria.getSubCategories(), criteria,
                SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.OTC));

        criteria =
            SearchQueryUtility.createVeterinarySubCategoriesCriteria(searchCriteria.getSubCategories(), criteria,
                SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.VETERINARY));

        if (EntityType.ORDERABLE_ITEM.equals(searchCriteria.getEntityType())) {
            if (searchCriteria.getDosageForm() != null && searchCriteria.getDosageForm().trim().length() > 0) {
                DetachedCriteria detachedCriteria =
                    getDataAccessObject()
                        .subquery(FieldKey.DOSAGE_FORM, searchCriteria.getEntityType(), criteria.getAlias());
                String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.DOSAGE_FORM);
                
                if (searchCriteria.getSearchTerms() != null && searchCriteria.getSearchTerms().size() > 0) {
                    for (SearchTermVo st : searchCriteria.getSearchTerms()) {
                        if (SearchType.BEGINS_WITH.equals(st.getSearchType())) { // BEGINS WITH
                            detachedCriteria.add(Restrictions.ilike(propertyName, searchCriteria.getDosageForm(), 
                                MatchMode.START));        
                        } else if (SearchType.CONTAINS.equals(st.getSearchType())) { // CONTAINS
                            detachedCriteria.add(Restrictions.ilike(propertyName, searchCriteria.getDosageForm(), 
                                MatchMode.ANYWHERE));
                        } else { // EXACT
                            detachedCriteria.add(Restrictions.ilike(propertyName, searchCriteria.getDosageForm(), 
                                MatchMode.EXACT));
                        }
                        
                        break;
                    }
                }
                
                criteria = criteria.add(Subqueries.exists(detachedCriteria));
            }
        }

        if (EntityType.PRODUCT.equals(searchCriteria.getEntityType()) && searchCriteria.getStrength() != null
            && searchCriteria.getStrength().trim().length() > 0) {
            String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.PRODUCT_STRENGTH);
            
            if (searchCriteria.getSearchTerms() != null && searchCriteria.getSearchTerms().size() > 0) {
                for (SearchTermVo st : searchCriteria.getSearchTerms()) {
                    if (SearchType.BEGINS_WITH.equals(st.getSearchType())) {
                        criteria = criteria.add(Restrictions.ilike(propertyName, searchCriteria.getStrength(), 
                            MatchMode.START));
                    } else if (SearchType.CONTAINS.equals(st.getSearchType())) {
                        criteria = criteria.add(Restrictions.ilike(propertyName, searchCriteria.getStrength(), 
                            MatchMode.ANYWHERE));
                    } else {
                        criteria = criteria.add(Restrictions.ilike(propertyName, searchCriteria.getStrength(), 
                            MatchMode.EXACT));
                    }
                    
                    break;
                }
            }
        }

//        if (EntityType.PRODUCT.equals(searchCriteria.getEntityType())) {
//            if (searchCriteria.getStrength() != null && searchCriteria.getStrength().trim().length() > 0) {
//                DetachedCriteria detachedCriteria =
//                        getDataAccessObject().subquery(FieldKey.PRODUCT_STRENGTH, searchCriteria.getEntityType(),
//                            criteria.getAlias());
//                String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.PRODUCT_STRENGTH);
//                detachedCriteria.add(Restrictions.ilike(propertyName, searchCriteria.getStrength(), MatchMode.EXACT));
//                criteria = criteria.add(Subqueries.exists(detachedCriteria));
//            }
//        }

        return criteria;
    }

    /**
     * Add the search term {@link Criteria} to the given {@link Criteria} based upon the search terms in the given
     * {@link SearchCriteriaVo}.
     * 
     * @param searchCriteria {@link SearchCriteriaVo} with search terms
     * @param criteria {@link Criteria} on which to add the search terms
     * @param addOrder boolean true if "ORDER BY" clause should be added, false if performing a COUNT(*) operation
     * @return resulting {@link Criteria}
     */
    private Criteria createSimpleSearchTermCriteria(SearchCriteriaVo searchCriteria, Criteria criteria, boolean addOrder) {
        Criteria terms = criteria;
        boolean ndcSearch = searchCriteria.getEntityType().equals(EntityType.NDC);
        boolean ndcHasOneDash = false;

        Disjunction or = Restrictions.disjunction();

        for (SearchTermVo searchTerm : searchCriteria.expandSearchTerms()) {
            String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), searchTerm.getFieldKey());
            Criterion criterion;

            if (SchemaUtility.isJoinRequired(getDataObjectClass(), searchTerm.getFieldKey())) {
                DetachedCriteria detachedCriteria =
                    getDataAccessObject().subquery(searchTerm.getFieldKey(), searchCriteria.getEntityType(),
                        terms.getAlias());
                detachedCriteria.add(createRestriction(searchTerm, null, propertyName));
                criterion = Subqueries.exists(detachedCriteria);
            } else {

                if (searchTerm.getValue().contains(DASH)) {
                    int dashOneAt = searchTerm.getValue().indexOf(DASH);
                    int dashTwoAt = searchTerm.getValue().lastIndexOf(DASH);
                    ndcHasOneDash = (dashOneAt != -1);

                    if (dashOneAt != dashTwoAt) {
                        ndcHasOneDash = false;
                    }
                }

                if (ndcSearch && ndcHasOneDash) {
                    criterion = Restrictions.ilike(EplNdcDo.NDC_NUMBER, searchTerm.getValue(), MatchMode.ANYWHERE);
                } else {
                    criterion = createRestriction(searchTerm, null, propertyName);
                }
            }

            if (SearchTermConjunction.OR.equals(searchTerm.getSearchTermConjunction())) {
                or.add(criterion);
            } else {
                terms.add(criterion);
            }
        }

        terms = terms.add(or);

        if (addOrder) {
            terms = getDataAccessObject().addOrder(terms, searchCriteria.getSortedFieldKey(), searchCriteria.getSortOrder());
        }

        return terms;
    }

    /**
     * Add the search term {@link Criteria} to the given {@link Criteria} based upon the search terms in the given
     * {@link SearchCriteriaVo}.
     * 
     * @param searchCriteria {@link SearchCriteriaVo} with search terms
     * @param criteria {@link Criteria} on which to add the search terms
     * @param ids The list of eplIds
     * @param addOrder boolean true if "ORDER BY" clause should be added, false if performing a COUNT(*) operation
     * @return resulting {@link Criteria}
     */
    protected Criteria createSimpleAllSearchTermCriteria(SearchCriteriaVo searchCriteria, Criteria criteria, List<Long> ids,
        boolean addOrder) {

        Criteria terms = criteria;
        String fullName = "ITEM.eplId";
        Criterion restriction;

        // This is silly but if the ids is null then we need a fake ID or grammar exception is thrown.
        if (ids.size() == 0) {
            List<Long> fakeId = new ArrayList<Long>();
            fakeId.add(new Long("1"));
            restriction = Restrictions.in(fullName, fakeId);
        } else {
            restriction = Restrictions.in(fullName, ids);
        }
        
        terms.add(restriction);
        
        if (addOrder) {
            terms = getDataAccessObject().addOrder(terms, searchCriteria.getSortedFieldKey(), searchCriteria.getSortOrder());
        }
        
        //added the following to include OI dosage form as a filter
        if (EntityType.ORDERABLE_ITEM.equals(searchCriteria.getEntityType())) {
            if (searchCriteria.getDosageForm() != null && searchCriteria.getDosageForm().trim().length() > 0) {
                DetachedCriteria detachedCriteria =
                    getDataAccessObject()
                        .subquery(FieldKey.DOSAGE_FORM, searchCriteria.getEntityType(), terms.getAlias());
                String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.DOSAGE_FORM);
                
                if (searchCriteria.getSearchTerms() != null && searchCriteria.getSearchTerms().size() > 0) {
                    for (SearchTermVo st : searchCriteria.getSearchTerms()) {
                        if (SearchType.BEGINS_WITH.equals(st.getSearchType())) {
                            detachedCriteria.add(Restrictions.ilike(propertyName, searchCriteria.getDosageForm(), 
                                MatchMode.START));        
                        } else if (SearchType.CONTAINS.equals(st.getSearchType())) {
                            detachedCriteria.add(Restrictions.ilike(propertyName, searchCriteria.getDosageForm(), 
                                MatchMode.ANYWHERE));
                        } else {
                            detachedCriteria.add(Restrictions.ilike(propertyName, searchCriteria.getDosageForm(), 
                                MatchMode.EXACT));
                        }
                        
                        break;
                    }
                }
                
                terms = terms.add(Subqueries.exists(detachedCriteria));
            }
        }

        //added the following to include product strength as a filter
        if (EntityType.PRODUCT.equals(searchCriteria.getEntityType()) && searchCriteria.getStrength() != null
            && searchCriteria.getStrength().trim().length() > 0) {
            String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.PRODUCT_STRENGTH);
            
            if (searchCriteria.getSearchTerms() != null && searchCriteria.getSearchTerms().size() > 0) {
                for (SearchTermVo st : searchCriteria.getSearchTerms()) {
                    if (SearchType.BEGINS_WITH.equals(st.getSearchType())) {
                        terms = terms.add(Restrictions.ilike(propertyName, searchCriteria.getStrength(), MatchMode.START)); 
                    } else if (SearchType.CONTAINS.equals(st.getSearchType())) {
                        terms = terms.add(Restrictions.ilike(propertyName, searchCriteria.getStrength(), MatchMode.ANYWHERE));
                    } else {
                        terms = terms.add(Restrictions.ilike(propertyName, searchCriteria.getStrength(), MatchMode.EXACT));
                    }
                    
                    break;
                }
            }
        }

        terms.setCacheable(true).setCacheRegion(PPSConstants.NATIONAL_DATA_CACHE);

        return terms;
    }

    /**
     * Create a Hibernate {@link Criterion} restriction for the given {@link SearchTermVo}.
     * 
     * @param searchTerm {@link SearchTermVo} for which to create restriction
     * @param termAlias optional String alias on the search term's criteria, if null the alias will be ignored
     * @param propertyName String property name in Hibernate on which to add restriction
     * @return {@link Criterion} restriction
     */
    private Criterion createRestriction(SearchTermVo searchTerm, String termAlias, String propertyName) {

//      String fullName = termAlias;
        StringBuffer fullName = new StringBuffer();
        Criterion restriction;

        if (termAlias != null) {
            fullName.append(termAlias).append(".");
        }

        fullName.append(propertyName);

        if (SearchType.EQUALS.equals(searchTerm.getSearchType())) {
            Object value = SchemaUtility.toValue(searchTerm.getFieldKey(), searchTerm.formatValue());
            restriction = Restrictions.eq(fullName.toString(), value);
        } else {

            MatchMode matchMode = MatchMode.EXACT;

            if (SearchType.CONTAINS.equals(searchTerm.getSearchType())) {
                matchMode = MatchMode.ANYWHERE;
            } else if (SearchType.BEGINS_WITH.equals(searchTerm.getSearchType())) {
                matchMode = MatchMode.START;
            }

            restriction = Restrictions.ilike(fullName.toString(), searchTerm.formatValue(), matchMode);
        }

        return restriction;
    }

    /**
     * @param listSearchTerms {@link SearchTermVo} list of search terms
     * @param owner EplVadfOwnerDo
     * @return boolean
     */
    private boolean listAndSearchfiltersOutItem(List<SearchTermVo> listSearchTerms, EplVadfOwnerDo owner) {

        // If no search terms specified, then can't filter out item.
        if (listSearchTerms.isEmpty()) {
            return false;
        }

        // Build up a map of key name to list VADF values:
        Map<String, List<EplVadfAssocValueDo>> mapFieldNameToListValue = new HashMap<String, List<EplVadfAssocValueDo>>();

        for (EplVadfAssocValueDo listDo : owner.getEplVadfAssocValues()) {

            String name = listDo.getEplVadfLov().getEplVaDf().getVadfName();
            List<EplVadfAssocValueDo> listValues = mapFieldNameToListValue.get(name);

            if (listValues == null) {
                listValues = new ArrayList<EplVadfAssocValueDo>(1);
                mapFieldNameToListValue.put(name, listValues);
            }

            listValues.add(listDo);
        }

        // If the item doesn't have selections, it has to be filtered out.
        if (mapFieldNameToListValue.isEmpty()) {
            return true;
        }

        // Search based on the specified terms.
        for (SearchTermVo listBasedSearchTerms : listSearchTerms) {

            // Get the main key for the specified key, that might be a searchable one only for advanced search
            // support, in which case we need to get the main key to perform the database search.
            FieldKey mainKey = FieldKey.findMainKeyFor(listBasedSearchTerms.getFieldKey());
            List<EplVadfAssocValueDo> listDos = mapFieldNameToListValue.get(mainKey.getKey());

            if (listDos == null) {
                return true;
            }

            String[] parsedTerms = listBasedSearchTerms.formatValue().split(", ");

            for (String nextTerm : parsedTerms) {

                boolean isMatchToExistingSelectionSeen = false;

                for (EplVadfAssocValueDo listDo : listDos) {

                    if (SearchType.BEGINS_WITH.equals(listBasedSearchTerms.getSearchType())) {
                        if (nextTerm.toUpperCase(Locale.getDefault()).startsWith(
                            listDo.getKey().getListValue().toUpperCase(Locale.getDefault()))) {
                            isMatchToExistingSelectionSeen = true;
                            break;
                        }
                    } else if (SearchType.EQUALS.equals(listBasedSearchTerms.getSearchType())) {
                        if (nextTerm.equalsIgnoreCase(listDo.getKey().getListValue())) {
                            isMatchToExistingSelectionSeen = true;
                            break;
                        }
                    } else if (SearchType.CONTAINS.equals(listBasedSearchTerms.getSearchType())) {
                        String listDoValue = listDo.getKey().getListValue().toUpperCase(Locale.getDefault());
                        String listSearchValue = nextTerm.toUpperCase(Locale.getDefault());

                        if (listDoValue.indexOf(listSearchValue) >= 0) {
                            isMatchToExistingSelectionSeen = true;
                            break;
                        }
                    } 
                } // for list values

                // Filter out the items that do not match
                if (!isMatchToExistingSelectionSeen) {
                    return true;
                }
            } // for all parsed sub-terms
        } // for search terms

        return false;
    }

    /**
     * 
     * 
     * @param listMultiTextSearchTerms {@link SearchTermVo} list of search terms
     * @param owner EplVadfOwnerDo
     * @return boolean
     */
    private boolean multiTextAndSearchfiltersOutItem(List<SearchTermVo> listMultiTextSearchTerms, EplVadfOwnerDo owner) {

        // If no search terms specified, then can't filter out item.
        if (listMultiTextSearchTerms.isEmpty()) {
            return false;
        }

        // Build up a map of key name to list of list selection values:
        Map<String, List<String>> mapFieldNameToListSelections = new HashMap<String, List<String>>();

        for (EplMultiTextDo multiDo : owner.getEplMultiTexts()) {

            String name = multiDo.getEplVaDf().getVadfName();
            List<String> listSelections = mapFieldNameToListSelections.get(name);

            if (listSelections == null) {
                listSelections = new ArrayList<String>(1);
                mapFieldNameToListSelections.put(name, listSelections);
            }

            listSelections.add(multiDo.getKey().getText());
        }

        // If the item doesn't have selections, it has to be filtered out.
        if (mapFieldNameToListSelections.isEmpty()) {
            return true;
        }

        // Search based on the specified terms.
        for (SearchTermVo multiSearchTerms : listMultiTextSearchTerms) {

            List<String> listSelections = mapFieldNameToListSelections.get(multiSearchTerms.getFieldKey().getKey());

            if (listSelections == null) {
                return true;
            }

            String[] parsedTerms = multiSearchTerms.formatValue().split(", ");

            for (String nextTerm : parsedTerms) {

                boolean isMatchToExistingSelectionSeen = false;

                for (String selection : listSelections) {

                    if (SearchType.BEGINS_WITH.equals(multiSearchTerms.getSearchType())) {
                        if (nextTerm.toUpperCase(Locale.getDefault()).startsWith(selection.toUpperCase(Locale.getDefault()))) {
                            isMatchToExistingSelectionSeen = true;
                            break;
                        }
                    } else if (SearchType.EQUALS.equals(multiSearchTerms.getSearchType())) {
                        if (nextTerm.equalsIgnoreCase(selection)) {
                            isMatchToExistingSelectionSeen = true;
                            break;
                        }
                    } else if (SearchType.CONTAINS.equals(multiSearchTerms.getSearchType())) {
                        String listDoValue = selection.toUpperCase(Locale.getDefault());
                        String listSearchValue = nextTerm.toUpperCase(Locale.getDefault());

                        if (listDoValue.indexOf(listSearchValue) >= 0) {
                            isMatchToExistingSelectionSeen = true;
                            break;
                        }
                    } // CONTAINS
                } // for list values

                // If no match to the search terms, then filter out.
                if (!isMatchToExistingSelectionSeen) {
                    return true;
                }
            } // for all parsed sub-terms
        } // for search terms

        return false;
    }

    /**
     * Get the Class type of the DataObject this ManagedItemDomainCapability deals with
     * 
     * @return Class of DataObject
     */
    protected final Class<DO> getDataObjectClass() {
        if (dataObjectClass == null) {
            this.dataObjectClass = ClassUtility.getGenericType(getClass(), 1);
        }

        return dataObjectClass;
    }

    /**
     * Get the Class type of the DataObject this ManagedItemDomainCapability deals with
     * 
     * @return Class of DataObject
     */
    protected final Class<T> getManagedItemClass() {
        if (managedItemClass == null) {
            this.managedItemClass = ClassUtility.getGenericType(getClass(), 0);
        }

        return managedItemClass;
    }

    /**
     * Deletes an {@link ManagedItemVo} from the database.
     * <p>
     * Default implementation deletes partial saves, VA data fields, requests, and finally the item itself. Sub classes
     * should override this method to first delete other associated records.
     * 
     * @param item {@link ManagedItemVo} to delete
     */
    @Override
    public void delete(T item) {
        getPartialSaveMgtDomainCapability().delete(item);
        getDataFieldsDomainCapability().delete(item.getVaDataFields());
        item.setVaDataFields(null);

        getRequestDomainCapability().delete(item);

        getDataAccessObject().delete(EPL_ID, Long.valueOf(item.getId()));
    }

    /**
     * Deletes an  from the database.
     * <p>
     * Default implementation to delete a domain item.  Throws a Validation excpetion if not implemented  by a super class.
     * 
     * @param item {@link ManagedItemVo} to delete
     * @throws ValidationException Validation Error
     */
    @Override
    public void deleteItem(T item) throws ValidationException {
        throw new ValidationException(ValidationException.CANNOT_DELETE,
            item.getEntityType().toString());
    }

    /**
     * Reassociate records from the oldItem to the newItem, then deletes an {@link ManagedItemVo} from the database.
     * <p>
     * Default implementation reassociates IAH records and then calls {@link #delete(ManagedItemVo)} with the oldItem.
     * 
     * @param oldItem {@link ManagedItemVo} to delete
     * @param newItem {@link ManagedItemVo} new item replacing old
     * @param user {@link UserVo} performing the action
     */
    @Override
    public void reassociateAndDelete(T oldItem, T newItem, UserVo user) {
        getItemAuditHistoryDomainCapability().reassociate(oldItem, newItem, user);
        delete(oldItem);
    }

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link ManagedItemVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    protected abstract Criteria createUniquenessCriteria(T item);

    /**
     * getDataFieldsDomainCapability 
     * @return dataFieldsDomainCapability property
     */
    public final DataFieldsDomainCapability getDataFieldsDomainCapability() {
        return dataFieldsDomainCapability;
    }

    /**
     * setDataFieldsDomainCapability
     * @param dataFieldsDomainCapability dataFieldsDomainCapability property
     */
    public final void setDataFieldsDomainCapability(DataFieldsDomainCapability dataFieldsDomainCapability) {
        this.dataFieldsDomainCapability = dataFieldsDomainCapability;
    }

    /**
     * getPartialSaveMgtDomainCapability
     * @return partialSaveMgtDomainCapability property
     */
    public final PartialSaveMgtDomainCapability getPartialSaveMgtDomainCapability() {
        return partialSaveMgtDomainCapability;
    }

    /**
     * setPartialSaveMgtDomainCapability
     * @param partialSaveMgtDomainCapability partialSaveMgtDomainCapability property
     */
    public final void setPartialSaveMgtDomainCapability(PartialSaveMgtDomainCapability partialSaveMgtDomainCapability) {
        this.partialSaveMgtDomainCapability = partialSaveMgtDomainCapability;
    }

    /**
     * getRequestDomainCapability
     * @return requestDomainCapability property
     */
    public final RequestDomainCapability getRequestDomainCapability() {
        return requestDomainCapability;
    }

    /**
     * setRequestDomainCapability
     * @param requestDomainCapability requestDomainCapability property
     */
    public final void setRequestDomainCapability(RequestDomainCapability requestDomainCapability) {
        this.requestDomainCapability = requestDomainCapability;
    }

    /**
     * getItemAuditHistoryDomainCapability
     * @return itemAuditHistoryDomainCapability property
     */
    public final ItemAuditHistoryDomainCapability getItemAuditHistoryDomainCapability() {
        return itemAuditHistoryDomainCapability;
    }

    /**
     * setItemAuditHistoryDomainCapability
     * @param itemAuditHistoryDomainCapability itemAuditHistoryDomainCapability property
     */
    public final void setItemAuditHistoryDomainCapability(ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability) {
        this.itemAuditHistoryDomainCapability = itemAuditHistoryDomainCapability;
    }

    /**
     * getSeqNumDomainCapability
     * @return seqNumDomainCapability property
     */
    public final SeqNumDomainCapability getSeqNumDomainCapability() {
        return seqNumDomainCapability;
    }

    /**
     * setSeqNumDomainCapability
     * @param seqNumDomainCapability seqNumDomainCapability property
     */
    public final void setSeqNumDomainCapability(SeqNumDomainCapability seqNumDomainCapability) {
        this.seqNumDomainCapability = seqNumDomainCapability;
    }

    /**
     * getManagedItemDomainCapabilityFactory
     * @return managedItemDomainCapabilityFactory property
     */
    public ManagedItemDomainCapabilityFactory getManagedItemDomainCapabilityFactory() {
        return managedItemDomainCapabilityFactory;
    }

    /**
     * setManagedItemDomainCapabilityFactory
     * @param managedItemDomainCapabilityFactory managedItemDomainCapabilityFactory property
     */
    public void setManagedItemDomainCapabilityFactory(ManagedItemDomainCapabilityFactory managedItemDomainCapabilityFactory) {
        this.managedItemDomainCapabilityFactory = managedItemDomainCapabilityFactory;
    }

    /**
     * searchBySimpleDataField
     * 
     * @param searchType SearchType
     * @param defName definition name
     * @param value value
     * @param iStatuses statuses
     * @param rStatuses statuses
     * @param localUse local use
     * @return list
     */
    protected List<DO> searchBySimpleDataField(SearchType searchType, String defName, String value,
        List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses, LocalUseSearchType localUse) {
        throw new UnsupportedOperationException(SUBCLASS_IMPL);
    }

    /**
     * This method searches the epl_va_df_assoc_values table and returns the corresponding collection of ndc's
     * 
     * @param searchType SearchType
     * @param defName definition name
     * @param value value
     * @param iStatuses statuses
     * @param rStatuses statuses
     * @param localUse local use
     * @return list
     */
    protected List<DO> searchByListDataField(SearchType searchType, String defName, String value,
        List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses, LocalUseSearchType localUse) {
        throw new UnsupportedOperationException(SUBCLASS_IMPL);
    }

    /** 
     * searchByMultiTextDataField
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
    protected List<DO> searchByMultiTextDataField(SearchType searchType, String defName, String value,
        List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses, LocalUseSearchType localUse) {
        throw new UnsupportedOperationException(SUBCLASS_IMPL);
    }

    /**
     * Adds to the specified Criteria, additional 'AND'-ed criteria that is specified on the top-level panel on the advanced
     * search page. These additional criteria are always 'AND'-ed into the overall advanced search query, regardless of if
     * the mode is OR or AND.
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @param criteria The Criteria to add additional queries to
     */
    protected void addTopPanelCriteria(SearchCriteriaVo searchCriteria, Criteria criteria) {
        throw new UnsupportedOperationException(SUBCLASS_IMPL);
    }

    /**
     * Performs an advanced search using the specified information. <BR>
     * Note that this is a lower-level method, with advanced search terms presumed to already be processed.
     * 
     * @param searchCriteria (@link SearchCriteriaVo)
     * @param dao (@link DataAccessObject)
     * @param searchHelper (@link AbstractAdvancedSearchHelper)
     * @param comparator (@link Comparator)
     * @param nonListSearchTerms (@link SearchTermVo)
     * @param listSearchTerms (@link SearchTermVo)
     * @param listMultiTextSearchTerms (@link SearchTermVo)
     * @param manualQueries (@link AndExpressionRunnable)
     * @return Set
     */
    protected Set<DO> performAdvancedSearchWithCriteria(SearchCriteriaVo searchCriteria, DataAccessObject dao,
        AbstractAdvancedSearchHelper searchHelper, Comparator<DO> comparator, List<SearchTermVo> nonListSearchTerms,
        List<SearchTermVo> listSearchTerms, List<SearchTermVo> listMultiTextSearchTerms,
        List<AndExpressionRunnable<DO>> manualQueries) {

        boolean isAndSearch = searchCriteria.isAdvancedAndSearch();
        List criteriaCollection = searchHelper.results(); // Perform the advanced search.
        Set<DO> resultCollection = new TreeSet<DO>(comparator); // collection to be returned
        
        resultCollection.addAll(criteriaCollection); // Start with all automatic queries performed first.
        
        // Perform manual queries for AND advanced searches. Manual queries need to be performed either to avoid the 
        // Hibernate one-criteria-per-join restriction, or else because a sub-OR query was required that couldn't use a 
        // Disjunction because it involved sub-Criteria objects (i.e. joins) instead of simple Criterion objects.
        // If even one manual test fails, this product is removed from further consideration.
        if (isAndSearch) {

            // need to go through result Collection to check for all lists and nonlists;
            Iterator<DO> i = resultCollection.iterator();

            while (i.hasNext()) {

                DO data = i.next();
                EplVadfOwnerDo owner = null;
                boolean isRemoveNeeded = false;

                for (EplVadfOwnerDo tempOwner : data.getEplVadfOwners()) {
                    owner = tempOwner;
                    break;
                }
                
                if (!nonListSearchTerms.isEmpty()) {
                    
                    // Build up a map of key name to non-list VADF values:
                    Map<String, EplVadfNonlistValueDo> mapFieldNameToNonListValue =
                            new HashMap<String, EplVadfNonlistValueDo>();
                    
                    checkEplVadfNonlistValues(owner, mapFieldNameToNonListValue);

                    if (!mapFieldNameToNonListValue.isEmpty()) {

                        // Filter out Products that fail the non-list VADF search terms.
                        for (SearchTermVo nonListSearches : nonListSearchTerms) {
                            EplVadfNonlistValueDo nonListDo =
                                    mapFieldNameToNonListValue.get(nonListSearches.getFieldKey().getKey());

                            if (nonListDo == null) {
                                isRemoveNeeded = true;
                                break;
                            }

                            if (SearchType.BEGINS_WITH.equals(nonListSearches.getSearchType())) {
                                if (!nonListSearches.formatValue().toUpperCase(Locale.getDefault())
                                        .startsWith(nonListDo.getVaDfValue().toUpperCase(Locale.getDefault()))) {
                                    isRemoveNeeded = true;
                                    break;
                                }
                            } else if (SearchType.EQUALS.equals(nonListSearches.getSearchType())) {
                                if (!nonListSearches.formatValue().equalsIgnoreCase(nonListDo.getVaDfValue())) {
                                    isRemoveNeeded = true;
                                    break;
                                }
                            } else if (SearchType.CONTAINS.equals(nonListSearches.getSearchType())) {
                                String nonListDoValue = nonListDo.getVaDfValue().toUpperCase(Locale.getDefault());
                                String nonListSearchValue = nonListSearches.formatValue().toUpperCase(Locale.getDefault());

                                if (nonListDoValue.indexOf(nonListSearchValue) < 0) {
                                    isRemoveNeeded = true;
                                    break;
                                }
                            } // CONTAINS
                        } // for all non-list search terms.

                        if (isRemoveNeeded) {
                            i.remove();
                            continue;
                        }
                    }
                } // if there are non-list-based VADF search terms.

                if (listAndSearchfiltersOutItem(listSearchTerms, owner)) {
                    i.remove();
                    continue;
                }

                if (multiTextAndSearchfiltersOutItem(listMultiTextSearchTerms, owner)) {
                    i.remove();
                    continue;
                }

                
                if (manualQueries != null) { // Perform other manual queries.
                    for (AndExpressionRunnable nxt : manualQueries) {
                        if (!nxt.performAndExpression(data)) {
                            isRemoveNeeded = true;
                            break;
                        }
                    }

                    if (isRemoveNeeded) {
                        i.remove();
                        continue;
                    }
                }
            } // for all data objects while loop
        } else {
            
//            LOG.debug("Timer - For loop_resultCollection.addAll (base): " + System.currentTimeMillis());            
            for (SearchTermVo nonListSearches : nonListSearchTerms) {
                resultCollection.addAll(searchBySimpleDataField(nonListSearches.getSearchType(), nonListSearches
                    .getFieldKey().getKey(), nonListSearches.formatValue(), searchCriteria.getItemStatus(), searchCriteria
                    .getRequestStatus(), searchCriteria.getLocalUse()));           

            } // for searchTerms
            
//            LOG.debug("Timer - For loop_resultCollection.addAll: " + System.currentTimeMillis());
            for (SearchTermVo listSearches : listSearchTerms) {
                String[] terms = listSearches.formatValue().split(", ");

                for (String nextTerm : terms) {

                    // Get the main key for the specified key, that might be a searchable one only for advanced search
                    // support, in which case we need to get the main key to perform the database search.
                    FieldKey mainKey = FieldKey.findMainKeyFor(listSearches.getFieldKey());

                    resultCollection.addAll(searchByListDataField(listSearches.getSearchType(), mainKey.getKey(), nextTerm,
                        searchCriteria.getItemStatus(), searchCriteria.getRequestStatus(), searchCriteria.getLocalUse()));
                }
            }
            
//            LOG.debug("Timer - For loop_format value: " + System.currentTimeMillis());
            processTerms(searchCriteria, listMultiTextSearchTerms, resultCollection);
            
//            LOG.debug("Timer - For loop_multiSearchTerm: " + System.currentTimeMillis());
        }

        return resultCollection;
    }
    
    /**
     * checkEplVadfNonlistValues
     *
     * @param owner EplVadfOwnerDo
     * @param mapFieldNameToNonListValue Map<String, EplVadfNonlistValueDo>
     */
    private void checkEplVadfNonlistValues(EplVadfOwnerDo owner, 
        Map<String, EplVadfNonlistValueDo> mapFieldNameToNonListValue) {
        
        for (EplVadfNonlistValueDo nonListDo : owner.getEplVadfNonlistValues()) {
            if (mapFieldNameToNonListValue.put(nonListDo.getEplVaDf().getVadfName(), nonListDo) != null) {
                throw new UnsupportedOperationException();
            }
        }
    }
    
    /**
     * processTerms
     *
     * @param searchCriteria SearchCriteriaVo
     * @param listMultiTextSearchTerms List<SearchTermVo>
     * @param resultCollection Set<DO>
     */
    private void processTerms(SearchCriteriaVo searchCriteria, List<SearchTermVo> listMultiTextSearchTerms, 
        Set<DO> resultCollection) {
        
        for (SearchTermVo multiSearchTerm : listMultiTextSearchTerms) {
            String[] terms = multiSearchTerm.formatValue().split(", ");

            for (String nextTerm : terms) {
                resultCollection.addAll(searchByMultiTextDataField(multiSearchTerm.getSearchType(), multiSearchTerm
                    .getFieldKey().getKey(), nextTerm, searchCriteria.getItemStatus(),
                    searchCriteria.getRequestStatus(), searchCriteria.getLocalUse()));
            }
        }
    }

    /**
     * Identifies objects capable of performing an AND expression.
     * 
     * @param <DO>
     */
    protected interface AndExpressionRunnable<DO extends DataObject> {

        /**
         * Perform an AND expression based on the input data object.
         * 
         * @param data The data to perform AND expression on.
         * @return boolean True if this data passes the AND expression, false otherwise.
         */
        boolean performAndExpression(DO data);
    }

    /**
     * Instances of this class are intended to be used as keys in a HashMap data structure.
     */
    protected static class CategoryJoinStringKey {

        private final Criteria criteria;
        private final String joinString;

        /**
         * constructor
         * 
         * @param criteria (@link Criteria)
         * @param joinString the join string statement
         */
        public CategoryJoinStringKey(Criteria criteria, String joinString) {
            this.criteria = criteria;
            this.joinString = joinString;
        }

        /**
         * Method returns true if objects are equal
         * 
         * @param o Object
         * @return boolean
         */
        @Override
        public boolean equals(Object o) {
            boolean isEq = false;

            if (o instanceof CategoryJoinStringKey) {
                CategoryJoinStringKey cjsk = (CategoryJoinStringKey) o;
                isEq = (cjsk.criteria == criteria && cjsk.joinString.equals(joinString));
            }

            return isEq;
        }

        /**
         * Calculates hash code
         * 
         * @return int
         */
        @Override
        public int hashCode() {
            return ((criteria == null ? 0 : criteria.hashCode()) ^ joinString.hashCode());
        }
    }

    /**
     * Base class for an advanced search helper used to support building Hibernate Criteria(s) for alternate advanced search
     * modes (such as OR or AND searches).
     */
    protected abstract class AbstractAdvancedSearchHelper {

        /** dao */
        protected DataAccessObject dao;

        /** searchCriteria */
        protected SearchCriteriaVo searchCriteria;

        /**
         * Create an instance of this base class.
         * 
         * @param dao The item/domain DAO object used to create Hibernate Criteria instances.
         * @param searchCriteria The SearchCriteriaVo instance that is directing the advanced search.
         */
        protected AbstractAdvancedSearchHelper(DataAccessObject dao, SearchCriteriaVo searchCriteria) {
            this.dao = dao;
            this.searchCriteria = searchCriteria;
        }

        /**
         * Add the specified Criterion to the overall advanced search.
         * 
         * @param c The Criterion to add.
         * @param b Boolean
         */
        public abstract void add(Criterion c, Boolean b);

        /**
         * Return/create a top-level Criteria (rooted in the specified DAO's database table).
         * 
         * @return Criteria The top-level Criteria instance.
         */
        public abstract Criteria getTopCriteria();

        /**
         * Return/create a sub-Criteria joined to the top-level Criteria via the specified joinPath.
         * 
         * @param joinPath The Hibernate join path.
         * @return Criteria The sub-Criteria instance.
         */
        public abstract Criteria getSubCriteria(String joinPath);

        /**
         * Return/create a sub-Criteria joined to the specified Criteria via the specified joinPath.
         * 
         * @param c The Criteria to create a sub-Criteria from.
         * @param joinPath The Hibernate join path.
         * @return Criteria The sub-Criteria instance.
         */
        public abstract Criteria getSubCriteria(Criteria c, String joinPath);

        /**
         * Produce the results of the advanced search query(s) build up via the methods above.
         * 
         * @return List The list of items/domains per the advanced search query.
         */
        public abstract List results();

    }

    /**
     * Sub-class for an AND-mode advanced search helper..
     */
    protected class AndAdvancedSearchHelper extends AbstractAdvancedSearchHelper {

        private final Criteria baseCriteria;
        private final Map<CategoryJoinStringKey, Criteria> mapCatJoinKeyToSubCriteria =
                new HashMap<CategoryJoinStringKey, Criteria>();

        /**
         * Create an instance of this AND-helper class.
         * 
         * @param dao The item/domain DAO object used to create Hibernate Criteria instances.
         * @param aSearchCriteria The SearchCriteriaVo instance that is directing the advanced search.
         */
        public AndAdvancedSearchHelper(DataAccessObject dao, SearchCriteriaVo aSearchCriteria) {
            super(dao, aSearchCriteria);

            baseCriteria = dao.getCriteria();
            baseCriteria.setMaxResults(PPSConstants.I100);
            addTopPanelCriteria(aSearchCriteria, baseCriteria);

            // if (LocalUseSearchType.LOCAL_USE.equals(searchCriteria.getLocalUse())) {
            // add(Restrictions.eq(EplProductDo.LOCAL_USE, "Y"));
            // } else if (LocalUseSearchType.NOT_LOCAL_USE.equals(searchCriteria.getLocalUse())) {
            // add(Restrictions.eq(EplProductDo.LOCAL_USE, "N"));
            // }
            //
            // if (searchCriteria.getItemStatus() != null && searchCriteria.getItemStatus().size() > 0) {
            // SearchQueryUtility.createItemStatusCriteria(searchCriteria.getItemStatus(), getTopCriteria(),
            // EplProductDo.ITEM_STATUS);
            // }
            //
            // if (searchCriteria.getRequestStatus() != null && searchCriteria.getRequestStatus().size() > 0) {
            // SearchQueryUtility.createRequestItemStatusCriteria(searchCriteria.getRequestStatus(), getTopCriteria(),
            // EplProductDo.REQUEST_STATUS);
            // }

        }

        /**
         * Add the specified Criterion to the overall advanced search.
         * 
         * @param c The Criterion to add.
         * @param b Boolean
         */
        @Override
        public void add(Criterion c, Boolean b) {
            baseCriteria.add(c);
        }

        /**
         * Return/create a top-level Criteria (rooted in the specified DAO's database table).
         * 
         * @return Criteria The top-level Criteria instance.
         */
        @Override
        public Criteria getTopCriteria() {
            return baseCriteria;
        }

        /**
         * Return/create a sub-Criteria joined to the top-level Criteria via the specified joinPath.
         * 
         * @param joinPath The Hibernate join path.
         * @return Criteria The sub-Criteria instance.
         */
        @Override
        public Criteria getSubCriteria(String joinPath) {

            return getSubCriteria(null, joinPath);
        }

        /**
         * Return/create a sub-Criteria joined to the specified Criteria via the specified joinPath.
         * 
         * @param c The Criteria to create a sub-Criteria from.
         * @param joinPath The Hibernate join path.
         * @return Criteria The sub-Criteria instance.
         */
        @Override
        public Criteria getSubCriteria(Criteria c, String joinPath) {

            CategoryJoinStringKey cjsk = new CategoryJoinStringKey(c, joinPath);
            Criteria subCriteria = mapCatJoinKeyToSubCriteria.get(cjsk);

            if (subCriteria == null) {
                subCriteria = (c == null ? baseCriteria : c).createCriteria(joinPath);
                mapCatJoinKeyToSubCriteria.put(cjsk, subCriteria);
            }

            return subCriteria;
        }

        /**
         * Produce the results of the advanced search query(s) build up via the methods above.
         * 
         * @return List The list of items/domains per the advanced search query.
         */
        @Override
        public List results() {
            return baseCriteria.list();
        }

    }

    /**
     * Sub-class for an OR-mode advanced search helper..
     */
    protected class OrAdvancedSearchHelper extends AbstractAdvancedSearchHelper {

        private final Collection<Criteria> criterias = new ArrayList<Criteria>();
        private boolean isOneOrMoreDisjunctionsSeen = false;
        private final Disjunction disjunction = Restrictions.disjunction();
        private Criteria lastCriteriaUsed;

        /**
         * Create an instance of this OR-helper class.
         * 
         * @param dao The item/domain DAO object used to create Hibernate Criteria instances.
         * @param searchCriteria The SearchCriteriaVo instance that is directing the advanced search.
         */
        public OrAdvancedSearchHelper(DataAccessObject dao, SearchCriteriaVo searchCriteria) {
            super(dao, searchCriteria);
        }

        /**
         * Add the specified Criterion to the overall advanced search.
         * 
         * @param c The Criterion to add.
         * @param b Boolean
         */
        @Override
        public void add(Criterion c, Boolean b) {
            disjunction.add(c);
            isOneOrMoreDisjunctionsSeen = true;
        }

        /**
         * ManagedItemDomainCapability
         * Return/create a top-level Criteria (rooted in the specified DAO's database table).
         * 
         * @return Criteria The top-level Criteria instance.
         */
        @Override
        public Criteria getTopCriteria() {
            Criteria crit = dao.getCriteria();

            if (lastCriteriaUsed == crit) {
                throw new UnsupportedOperationException(NO_CACHED_CRIT_OR);
            }

            lastCriteriaUsed = crit;
            criterias.add(crit);
            addTopPanelCriteria(searchCriteria, crit);

            return crit;
        }

        /**
         * MangedItemDomainCapability
         * Return/create a sub-Criteria joined to the top-level Criteria via the specified joinPath.
         * 
         * @param joinPath The Hibernate join path.
         * @return Criteria The sub-Criteria instance.
         */
        @Override
        public Criteria getSubCriteria(String joinPath) {
            
            // get the subCriteria by adding the joinPath
            Criteria cTop = getTopCriteria();
            Criteria cSub = cTop.createCriteria(joinPath);

            return cSub;
        }

        /**
         * ManagedItemDomainCapability
         * Return/create a sub-Criteria joined to the specified Criteria via the specified joinPath.
         * 
         * @param c The Criteria to create a sub-Criteria from.
         * @param joinPath The Hibernate join path.
         * @return Criteria The sub-Criteria instance.
         */
        @Override
        public Criteria getSubCriteria(Criteria c, String joinPath) {
            
            // return the creation of the joinPath
            return c.createCriteria(joinPath);
        }

        /**
         * Produce the results of the advanced search query(s) build up via the methods above.
         * 
         * @return List The list of items/domains per the advanced search query.
         */
        @Override
        public List results() {
            
            // create the hashset
            Set dataObjects = new HashSet();

            // Produce the basic 'OR' list.
            if (isOneOrMoreDisjunctionsSeen) {
                Criteria c = dao.getCriteria();
                c.add(disjunction);
                addTopPanelCriteria(searchCriteria, c);
                dataObjects.addAll(c.list());
            }

            // Loop thru the other OR-ed criterias, and merge the lists that are returned.
            for (Criteria cNxt : criterias) {
                dataObjects.addAll(cNxt.list());
            }

            return new ArrayList(dataObjects);
        }
    }

    /**
     * Sub-class for an advanced search helper..
     */
    protected class AdvancedSearchHelper extends AbstractAdvancedSearchHelper {

        private final Collection<Criteria> criterias = new ArrayList<Criteria>();
        private boolean isOneOrMoreDisjunctionsSeen = false;
        private final Disjunction disjunction = Restrictions.disjunction();
        private Criteria lastCriteriaUsed;

        private Conjunction conjunction = Restrictions.conjunction();
        private Boolean islastConjunction = false;
        private final Criteria baseCriteria;

        @SuppressWarnings("unused")
        private final Map<CategoryJoinStringKey, Criteria> mapCatJoinKeyToSubCriteria =
                new HashMap<CategoryJoinStringKey, Criteria>();

        /**
         * Create an instance of this OR-helper class.
         * 
         * @param dao The item/domain DAO object used to create Hibernate Criteria instances.
         * @param searchCriteria The SearchCriteriaVo instance that is directing the advanced search.
         */
        public AdvancedSearchHelper(DataAccessObject dao, SearchCriteriaVo searchCriteria) {
            super(dao, searchCriteria);

            baseCriteria = dao.getCriteria();
            baseCriteria.setMaxResults(PPSConstants.I100);

            //addTopPanelCriteria(searchCriteria, baseCriteria);
        }

        /**
         * Add the specified Criterion to the overall advanced search.
         * 
         * @param c The Criterion to add.
         * @param b Flag for whether to build a conjunction.
         */
        @Override
        public void add(Criterion c, Boolean b) {

            if (b || islastConjunction) {
                baseCriteria.add(c);

                if (b) {
                    conjunction.add(c);
                } else {
                    conjunction.add(c);
                    disjunction.add(conjunction);
                    conjunction = Restrictions.conjunction();
                }
            } else {
                disjunction.add(c);
            }

            islastConjunction = b;
            isOneOrMoreDisjunctionsSeen = true;
        }

        /**
         * Return/create a top-level Criteria (rooted in the specified DAO's database table).
         * 
         * @return Criteria The top-level Criteria instance.
         */
        @Override
        public Criteria getTopCriteria() {
            Criteria c = dao.getCriteria();

            if (lastCriteriaUsed == c) {
                throw new UnsupportedOperationException(NO_CACHED_CRIT_OR);
            }

            lastCriteriaUsed = c;
            criterias.add(c);
            addTopPanelCriteria(searchCriteria, c);

            return c;
        }

        /**
         * Return/create a sub-Criteria joined to the top-level Criteria via the specified joinPath.
         * 
         * @param joinPath The Hibernate join path.
         * @return Criteria The sub-Criteria instance.
         */
        @Override
        public Criteria getSubCriteria(String joinPath) {
            Criteria cTop = getTopCriteria();
            Criteria cSub = cTop.createCriteria(joinPath);

            return cSub;
        }

        /**
         * Return/create a sub-Criteria joined to the specified Criteria via the specified joinPath.
         * 
         * @param c The Criteria to create a sub-Criteria from.
         * @param joinPath The Hibernate join path.
         * @return Criteria The sub-Criteria instance.
         */
        @Override
        public Criteria getSubCriteria(Criteria c, String joinPath) {
            return c.createCriteria(joinPath);
        }

        /**
         * Produce the results of the advanced search query(s) build up via the methods above.
         * 
         * @return List The list of items/domains per the advanced search query.
         */
        @Override
        public List results() {
            Set dataObjects = new HashSet();

            // Produce the basic 'OR' list.
            if (isOneOrMoreDisjunctionsSeen) {
                Criteria c = dao.getCriteria();
                c.add(disjunction);
                addTopPanelCriteria(searchCriteria, c);
                dataObjects.addAll(c.list());
            }

            // Loop thru the other OR-ed criterias, and merge their lists.
            for (Criteria cNxt : criterias) {
                dataObjects.addAll(cNxt.list());
            }

            return new ArrayList(dataObjects);
        }
    }
    
    /**
     * Creates and appends the search type condition SQL
     *
     * @param query StringBuffer
     * @param searchType SearchType
     * @param value String
     */
    protected static void appendSearchTypeConditional(StringBuffer query, SearchType searchType, String value) {
        if (searchType.equals(SearchType.CONTAINS)) {
            query.append(PPSConstants.LIKE_PERCENT);
            query.append(value);
            query.append("%'");
        } else if (searchType.equals(SearchType.BEGINS_WITH)) {
            query.append(PPSConstants.LIKE);
            query.append(value);
            query.append("%'");
        } else {
            query.append(PPSConstants.EQUALTICK);
            query.append(value);
            query.append("'");
        }
    }
}
