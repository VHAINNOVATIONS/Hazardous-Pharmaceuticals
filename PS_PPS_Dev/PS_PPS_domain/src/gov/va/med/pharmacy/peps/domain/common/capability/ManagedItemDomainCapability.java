/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PaginatedList;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;


/**
 * Perform CRUD operations on {@link ManagedItemVo}.
 * 
 * @param <T> type of {@link ManagedItemVo}
 * @param <DO> type of {@link DataObject}
 */
public interface ManagedItemDomainCapability<T extends ManagedItemVo, DO extends DataObject> {

    /**
     * Insert the given ManagedItemVo.
     * 
     * @param managedItem ManagedItemVo
     * @param user {@link UserVo} performing the action
     * @return ManagedItemVo inserted ManagedItemVo with new ID
     * @throws DuplicateItemException if item already exists by uniqueness fields
     */
    T create(T managedItem, UserVo user) throws DuplicateItemException;

    /**
     * Insert the checkForActiveDependencies.
     * 
     * @param managedItem ManagedItemVo
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    void checkForActiveDependencies(T managedItem, UserVo user) throws ValidationException;

    /**
     * retrieveDependencies.
     * 
     * @param managedItem ManagedItemVo
     * @return a List of dependent objects
     * @throws ValidationException ValidationException
     */
    List<ManagedItemVo> retrieveDependencies(T managedItem) throws ValidationException;

    /**
     * Insert the given ManagedItemVo.
     * 
     * @param managedItem ManagedItemVo
     * @param user {@link UserVo} performing the action
     * @return ManagedItemVo inserted ManagedItemVo with new ID
     */
    T createWithoutDuplicateCheck(T managedItem, UserVo user);

    /**
     * Deletes an {@link ManagedItemVo} from the database.
     * 
     * @param item {@link ManagedItemVo} to delete
     */
    void delete(T item);

    /**
     * Deletes an {@link ManagedItemVo} from the database.
     * 
     * @param item {@link ManagedItemVo} to delete
     * @throws ValidationException ValidationException
     */
    void deleteItem(T item) throws ValidationException;

    /**
     * Re-associate IAH records from the oldItem to the newItem, then deletes the old {@link ManagedItemVo} from the database.
     * 
     * @param oldItem {@link ManagedItemVo} to delete
     * @param newItem {@link ManagedItemVo} new item replacing old
     * @param user {@link UserVo} performing the action
     */
    void reassociateAndDelete(T oldItem, T newItem, UserVo user);

    /**
     * Filters out the display values in multi-value fields which do not match the search criteria
     *
     * @param searchKey - the field key searched on
     * @param searchTerm - the search term matching the field key searched on
     * @param searchType - the @SearchType
     * @param data the data coming back from criteria.list();
     */
    void filterMultiValueDisplayableFields(FieldKey searchKey, String searchTerm, SearchType searchType,
        List<DO> data); 
    
    /**
     * Retrieves revision Number
     * 
     * @param id String
     * @return revision number of the item
     * @throws ItemNotFoundException exception
     */
    long getRevisionNumber(String id) throws ItemNotFoundException;

    /**
     * Get all {@link ItemStatus#ACTIVE} {@link ManagedItemVo} of the given {@link EntityType}.
     * 
     * @return Active List<ManagedItemVo>
     */
    List<T> retrieve();

    /**
     * Retrieve the ManagedItemVo with the given ID.
     * 
     * @param id String ManagedItemVo ID
     * @return ManagedItemVo
     * @throws ItemNotFoundException if an item with the given ID does not exist
     */
    T retrieveMinimal(String id) throws ItemNotFoundException;

    /**
     * Retrieve the ManagedItemVo by its unique attributes (not by ID). If the item is not found by the unique fields, an
     * empty List is returned.
     * <p>
     * If the given item's ID is numeric, then a not equals criteria is added such that this method does not return the same
     * item as a duplicate.
     * <p>
     * Most item types will return a List with one element, but some (like NDC) may actually return more than one.
     * 
     * @param managedItem ManagedItemVo to retrieve
     * @return List of duplicate {@link ManagedItemVo}
     */
    List<T> retrieveDuplicates(T managedItem);

    /**
     * Retrieve the ManagedItemVo with the given ID. If the ManagedItemVo type has children, "minimally" populates all child
     * items.
     * 
     * @param id String ManagedItemVo ID
     * @return ManagedItemVo
     * @throws ItemNotFoundException if an item with the given ID does not exist
     */
    T retrieve(String id) throws ItemNotFoundException;

    /**
     * Retrieve the ManagedItemVo with the given ID.
     * <p>
     * Does not populate any child items, even if the ManagedItemVo type has children. If using this method and the children
     * are needed, also call {@link #retrieveChildren(String)} or
     * {@link #retrieveChildren(String, int, FieldKey, SortOrder, int)}.
     * 
     * @param id String ManagedItemVo ID
     * @return ManagedItemVo
     * @throws ItemNotFoundException exception
     */
    T retrieveParent(String id) throws ItemNotFoundException;

    /**
     * Retrieve a List of ManagedItemVo for the given parent ManagedItemVo ID.
     * 
     * @param parentId String parent ID
     * @return List<ManagedItemVo>
     */
    List<T> retrieveChildren(String parentId);

    /**
     * Retrieve a list of partially populated child {@link ManagedItemVo} for the given parent {@link ManagedItemVo} ID.
     * 
     * @param parentId String parent ID
     * @return List of ManagedItemVo
     */
    List<T> retrieveMinimalChildren(String parentId);

    /**
     * retrieve the count of children for the given parent ID
     * 
     * @param parentId String
     * @return int
     */
    int retrieveChildrenCount(String parentId);

    /**
     * Searches for managed items
     * 
     * @param search criteria
     * @return list that matches the criteria
     */
    List<T> search(SearchCriteriaVo search);
    
    /**
     * Searches for managed items (Full List returned)
     * 
     * @param search criteria
     * @return list that matches the criteria
     */
    List<T> searchFullList(SearchCriteriaVo search);

    /**
     * Update the given ManagedItemVo
     * 
     * @param managedItem ManagedItemVo
     * @param user {@link UserVo} performing the action
     * @return ManagedItemVo updated ManagedItemVo
     * @throws DuplicateItemException exception
     */
    T update(T managedItem, UserVo user) throws DuplicateItemException;

    /**
     * Retrieve a list of items for the given parent Item Id.
     * <p>
     * The returned PaginatedList contains a partial list of all the children for the given parent. The first row returned is
     * startRow with pageSize total rows returned. The list will be sorted by sortedFieldKey in sortOrder to limit the
     * potential for different result sets each call.
     * 
     * @param parentItemId String ID of parent item.
     * @param sortedFieldKey FieldKey representing field to sort by
     * @param sortOrder SortOrder representing ascending or descending sort
     * @param startRow integer index in full list of child items from which to start retrieving
     * @param pageSize integer number of rows to retrieve
     * 
     * @return PaginatedList<ManagedItemVo> child items
     */
    PaginatedList<T> retrieveChildren(String parentItemId, FieldKey sortedFieldKey, SortOrder sortOrder, int startRow,
                                      int pageSize);

    /**
     * Retrieve a list of ACTIVE items for the given parent Item Id.
     * <p>
     * The returned PaginatedList contains a partial list of all the children for the given parent. The first row returned is
     * startRow with pageSize total rows returned. The list will be sorted by sortedFieldKey in sortOrder to limit the
     * potential for different result sets each call.
     * 
     * @param parentItemId String ID of parent for which to retrieve children items
     * @param sortedFieldKey FieldKey representing field to sort by
     * @param sortOrder SortOrder representing ascending or descending sort
     * @param startRow integer index in full list of child items from which to start retrieving
     * @param pageSize integer number of rows to retrieve
     * 
     * @return PaginatedList<ManagedItemVo> child items
     */
    PaginatedList<T> retrieveActiveChildren(String parentItemId, FieldKey sortedFieldKey, SortOrder sortOrder, int startRow,
                                            int pageSize);

    /**
     * True if the item exists by uniqueness fields.
     * 
     * @param item {@link ManagedItemVo} from which to get uniqueness fields to test
     * @return boolean
     */
    boolean existsByUniquenessFields(T item);

    /**
     * True if the given ID exists.
     * <p>
     * If the given ID is null, returns false.
     * 
     * @param id EPL ID
     * @return boolean
     */
    boolean existsById(String id);

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    Converter<T, DO> getConverter();

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    DataAccessObject getDataAccessObject();

}
