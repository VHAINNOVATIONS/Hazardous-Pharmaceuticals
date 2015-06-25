/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedDataVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PaginatedList;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedDataDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.utility.SchemaUtility;
import gov.va.med.pharmacy.peps.domain.common.utility.SearchQueryUtility;


/**
 * Perform CRUD operations on managed data domains
 * 
 * @param <T> type of ManagedDataVo
 * @param <DO> type of {@link DataObject}
 */
public abstract class ManagedDataDomainCapabilityImpl<T extends ManagedDataVo, DO extends DataObject> extends
    ManagedItemDomainCapabilityImpl<T, DO> implements ManagedDataDomainCapability<T, DO> {

    private static final Logger LOG = Logger.getLogger(ManagedDataDomainCapabilityImpl.class);

    /**
     * Get all {@link ItemStatus#ACTIVE} {@link ManagedItemVo} of the given {@link EntityType}.
     * 
     * @return Active List<ManagedItemVo>
     */
    public List<T> retrieve() {
        Criteria criteria = getDataAccessObject().getCriteria();
        String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.ITEM_STATUS);
        criteria.add(Restrictions.eq(propertyName, ItemStatus.ACTIVE.name()));

        String sortPropertyName = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.VALUE);

        if (sortPropertyName != null && sortPropertyName.trim().length() > 0) {
            Order order = Order.asc(sortPropertyName);
            order.ignoreCase();
            criteria.addOrder(order);
        }

        configureCaching(criteria);
        List<DO> results = criteria.list();

        return getConverter().convertMinimal(results);
    }

    /**
     * ManagedDataVo don't have children; return an empty list.
     * 
     * @param parentId ID
     * @return Empty List
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability#retrieveChildren(java.lang.String)
     */
    public List<T> retrieveChildren(String parentId) {
        return Collections.EMPTY_LIST;
    }

    /**
     * ManagedDataVo don't have children; return zero.
     * 
     * @param parentId ID
     * @return zero
     */
    public int retrieveChildrenCount(String parentId) {
        return 0;
    }

    /**
     * Search for managed domains.
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * 
     * @return List of {@link DataObject}
     */
    public final List<T> search(SearchCriteriaVo searchCriteria) {
        Criteria count = createSearchCriteria(searchCriteria);
        int fullSize = getDataAccessObject().getCount(count);

        Criteria criteria = createSearchCriteria(searchCriteria);
        criteria.setFirstResult(searchCriteria.getStartRow());
        criteria.setMaxResults(searchCriteria.getPageSize());

        if (FieldKey.VALUE.equals(searchCriteria.getSortedFieldKey())) {
            String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.VALUE);
            Order order;

            if (SortOrder.ASCENDING.equals(searchCriteria.getSortOrder())) {
                order = Order.asc(propertyName);
            } else {
                order = Order.desc(propertyName);
            }

            order.ignoreCase();
            criteria.addOrder(order);
        } else {
            criteria = getDataAccessObject().addOrder(criteria, searchCriteria.getSortedFieldKey(),
                searchCriteria.getSortOrder());
        }

        configureCaching(criteria);

        // run the criteria object to retrieve the list of data objects.
        List<DO> data = criteria.list();
        List<T> valueObjects = getConverter().convertSearch(data);

        return new PaginatedList<T>(valueObjects, fullSize, searchCriteria.getSortedFieldKey(), searchCriteria
            .getSortOrder(), searchCriteria.getStartRow(), searchCriteria.getPageSize());
    }
    
    /**
     * Search for managed domains.
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * 
     * @return List of {@link DataObject}
     */
    public final List<T> searchFullList(SearchCriteriaVo searchCriteria) {
        Criteria count = createSearchCriteria(searchCriteria);
        int fullSize = getDataAccessObject().getCount(count);

        Criteria criteria = createSearchCriteria(searchCriteria);
        criteria.setFirstResult(searchCriteria.getStartRow());
        criteria.setMaxResults(searchCriteria.getPageSize());

        if (FieldKey.VALUE.equals(searchCriteria.getSortedFieldKey())) {
            String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.VALUE);
            Order order;

            if (SortOrder.DESCENDING.equals(searchCriteria.getSortOrder())) {
                order = Order.desc(propertyName);
            } else {
                order = Order.asc(propertyName);
            }

            order.ignoreCase();
            criteria.addOrder(order);
        } else {
            criteria = getDataAccessObject().addOrder(criteria, searchCriteria.getSortedFieldKey(),
                searchCriteria.getSortOrder());
        }

        configureCaching(criteria);

        List<DO> data = criteria.list();
        List<T> valueObjects = getConverter().convertSearch(data);

        return new PaginatedList<T>(valueObjects, fullSize, searchCriteria.getSortedFieldKey(), searchCriteria
            .getSortOrder(), searchCriteria.getStartRow(), searchCriteria.getPageSize());
    }

    /**
     * Create the criteria to perform a search based upon the given {@link SearchCriteriaVo}. The resulting {@link Criteria}
     * would return an unsorted entire result (not paged).
     * <p>
     * This method is used twice by the {@link #search(SearchCriteriaVo)} method. Once to get the count of the total number
     * of results, and a second time to perform the paged search.
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @return {@link Criteria} for a search using the given {@link SearchCriteriaVo}
     */
    protected Criteria createSearchCriteria(SearchCriteriaVo searchCriteria) {
        Criteria criteria = getDataAccessObject().getCriteria();

        criteria = SearchQueryUtility.createItemStatusCriteria(searchCriteria.getItemStatus(), criteria, SchemaUtility
            .getPropertyName(getDataObjectClass(), FieldKey.ITEM_STATUS));

        LOG.debug("searchCriteria null?: " + searchCriteria == null);
        LOG.debug("searchCriteria.getEntityType() null?: " + searchCriteria.getEntityType() == null);

        // Special handling does not have a request item status
        // Request Item status should be set to null for Special handling
        if (!(EntityType.SPECIAL_HANDLING.equals(searchCriteria.getEntityType()))) {
            criteria = SearchQueryUtility.createRequestItemStatusCriteria(searchCriteria.getRequestStatus(), criteria,
                SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.REQUEST_ITEM_STATUS));
        }

        for (SearchTermVo searchTerm : searchCriteria.expandSearchTerms()) {
            criteria = createSearchTermCriteria(searchTerm, criteria);
        }

        return criteria;
    }

    /**
     * Add the search term {@link SearchTermVo} to the given {@link Criteria}.
     * 
     * @param searchTerm {@link SearchTermVo} for which to add {@link Criteria}
     * @param criteria {@link Criteria} on which to add the search terms
     * @return resulting {@link Criteria}
     */
    protected Criteria createSearchTermCriteria(SearchTermVo searchTerm, Criteria criteria) {
        String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), searchTerm.getFieldKey());

        if (SearchType.CONTAINS.equals(searchTerm.getSearchType())) {
            return criteria.add(Restrictions.ilike(propertyName, searchTerm.formatValue(), MatchMode.ANYWHERE));
        } else if (SearchType.BEGINS_WITH.equals(searchTerm.getSearchType())) {
            return criteria.add(Restrictions.ilike(propertyName, searchTerm.formatValue(), MatchMode.START));
        } else {
            return criteria.add(Restrictions.ilike(propertyName, searchTerm.formatValue(), MatchMode.EXACT));
        }
    }

    /**
     * Insert the given {@link ManagedItemVo} with a duplicate check.
     * 
     * @param managedItem {@link ManagedItemVo}
     * @param user {@link UserVo} performing the action
     * @return {@link ManagedItemVo} inserted with new ID
     * @throws DuplicateItemException if item already exists by uniqueness fields
     */
    public T create(T managedItem, UserVo user) throws DuplicateItemException {

        if (existsByUniquenessFields(managedItem)) {
            throw new DuplicateItemException(DuplicateItemException.DUPLICATE_ITEM, managedItem.toShortString());
        }

        return createWithoutDuplicateCheck(managedItem, user);
    }

    /**
     * Delete associated domain from data base if IEN is not assigned and other objects are not dependent on this object.
     * 
     * @param managedDataVo to delete
     * @param user requesting the delete
     * 
     * @throws ValidationException to validate the IEN and dependencies
     * 
     */
    public void deletePending(T managedDataVo, UserVo user) throws ValidationException {

    }

    /**
     * a common caching configuration 
     *
     * @param criteria Criteria
     */
    protected void configureCaching(Criteria criteria) {
        criteria.setCacheable(true).setCacheRegion("nationalData");
    }
}
