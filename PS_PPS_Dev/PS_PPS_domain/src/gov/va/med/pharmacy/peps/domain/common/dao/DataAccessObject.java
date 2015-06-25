/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;


/**
 * Common methods for all data access objects
 * 
 * @param <DO> Type of DataObject this DAO works with
 * @param <ID> Type of ID the DataObject uses
 */
public interface DataAccessObject<DO extends DataObject, ID extends Serializable> {

    /**
     * Delete the given Collection of DataObjects
     * 
     * @param list Collection of DataObject instances to delete
     */
    void delete(Collection<DO> list);

    /**
     * Delete the given DataObject
     * 
     * @param data DataObject instance to delete
     */
    void delete(DO data);

    /**
     * Retrieves DOs for rows where the given fieldName matches the given value. The retrieved DOs are then deleted.
     * 
     * @param fieldName the field to retrieve by
     * @param value the value of the field
     */
    void delete(String fieldName, Object value);

    /**
     * Determine if a DataObject with the given ID exists.
     * 
     * @param id Serializable ID
     * @return true if ID for DataObject exists
     */
    boolean exists(ID id);

    /**
     * Update the created by and modified by date/times, validate the data and save the given Collection of DataObjects
     * 
     * @param list Collection of DataObjects to validate and save
     * @param user {@link UserVo} performing action
     * @return CollectionDataObject with new ID set
     */
    Collection<DO> insert(Collection<DO> list, UserVo user);

    /**
     * Update the created by and modified by date/times, validate the data and save
     * 
     * @param data DataObject to validate and save
     * @param user {@link UserVo} performing action
     * @return DataObject with new ID set
     */
    DO insert(DO data, UserVo user);

    /**
     * Find the max value of the given field.
     * 
     * @param fieldName String name of the field/column to get the max of
     * @return long max value
     */
    long max(String fieldName);

    /**
     * Find the max value of the ID.
     * 
     * @return long max value of the ID
     */
    ID maxId();

    /**
     * Get all instances of the given DataObject without any particular order
     * 
     * @return List of DataObjects
     */
    List<DO> retrieve();

    /**
     * Find an instance of the given class by the ID. If the ID is not found, a HibernateException will be thrown.
     * 
     * @param id Serializable ID
     * @return DataObject found
     */
    DO retrieve(ID id);

    /**
     * Return all DataObjects that match the given Restrictions (Criterion).
     * 
     * @param criteria List of Criterion to match
     * @return List of Criterion
     */
    List<DO> retrieve(List<Criterion> criteria);

    /**
     * Return all DataObjects that have fields equal to the given values in the nameToValues map.
     * 
     * @param nameToValues Map of String field name as keys and Object as values
     * @return List of DataObjects
     */
    List<DO> retrieve(Map<String, Object> nameToValues);

    /**
     * Return all DataObjects that have a field with a value equal to one in the given Collection.
     * 
     * @param fieldName String name of the field to find by
     * @param list Collection of values of the field results may be equal to
     * @return List of DataObjects
     */
    List<DO> retrieve(String fieldName, Collection list);

    /**
     * Return all DataObjects that have a field equal to the given value.
     * 
     * @param fieldName String name of the field to find by
     * @param value Object value of the field results must be equal to
     * @return List of DataObjects
     */
    List<DO> retrieve(String fieldName, Object value);

    /**
     * Get all instances of the given DataObject in ascending order by the given field name.
     * 
     * @param orderBy String field name to order by
     * @return List of DataObjects
     */
    List<DO> retrieveAscending(String orderBy);

    /**
     * Get a specific number of records beginning at a specific index.
     * 
     * @param orderBy String field name to order by
     * @param beginInx the beginning index
     * @param numRecords the number of records requested
     * @return List of DataObjects
     */
    List<DO> retrieveAscending(String orderBy, int beginInx, int numRecords);

    /**
     * Get all instances of the given DataObject in descending order by the given field name.
     * 
     * @param orderBy String field name to order by
     * @return List of DataObjects
     */
    List<DO> retrieveDescending(String orderBy);

    /**
     * Retrieve a "page" of DataObjects that have a field equal to the given value.
     * 
     * @param fieldName String name of the field to find by
     * @param value Object value of the field results must be equal to
     * @param startRow integer row in result set where "page" starts
     * @param pageSize integer number of rows to retrieve per page
     * @param orderBy {@link FieldKey} to order by
     * @param sortOrder {@link SortOrder} ascending or descending
     * @return paged List of DataObjects
     */
    List<DO> retrievePage(String fieldName, Object value, int startRow, int pageSize, FieldKey orderBy, SortOrder sortOrder);

    /**
     * Retrieve a "page" of DataObjects that have a field equal to the given value and status of ACTIVE
     * 
     * @param fieldName String name of the field to find by
     * @param value Object value of the field results must be equal to
     * @param fieldNameActive String name of the field that should be {@link ItemStatus#ACTIVE}
     * @param startRow integer row in result set where "page" starts
     * @param pageSize integer number of rows to retrieve per page
     * @param orderBy {@link FieldKey} to order by
     * @param sortOrder {@link SortOrder} ascending or descending
     * @return paged List of DataObjects
     */
    List<DO> retrieveActivePage(String fieldName, Object value, String fieldNameActive, int startRow, int pageSize,
                                FieldKey orderBy, SortOrder sortOrder);

    /**
     * Update the created by and modified by date/times, validate, merge, and update the Collection of DataObjects.
     * 
     * @param list Collection of DataObjects
     * @param user {@link UserVo} performing action
     * @return Collection of merged and updated DataObjects
     */
    Collection<DO> update(Collection<DO> list, UserVo user);

    /**
     * Update the created by and modified by date/times, validate, merge, and update the given DataObject.
     * 
     * @param data DataObject to merge and update
     * @param user {@link UserVo} performing action
     * @return DataObject with updated data
     */
    DO update(DO data, UserVo user);

    /**
     * Update the Created & Last Modified by and date/time info
     * 
     * @param list Collection of DataObjects
     * @param user {@link UserVo} performing action
     */
    void updateCreatedAndLastModified(Collection<DO> list, UserVo user);

    /**
     * Update the Created & Last Modified by and date/time info
     * 
     * @param data DataObject
     * @param user {@link UserVo} performing action
     */
    void updateCreatedAndLastModified(DO data, UserVo user);

    /**
     * Create a {@link Criteria} without an alias.
     * 
     * @return {@link Criteria}
     */
    Criteria getCriteria();

    /**
     * Create a {@link Criteria} with the given alias.
     * 
     * @param alias {@link String} alias for table
     * @return {@link Criteria}
     */
    Criteria getCriteria(String alias);

    /**
     * Execute Hql query
     * 
     * @param hql hibernate HQL query
     * @return List<DO>
     */
    List executeHqlQuery(String hql);

    /**
     * Deletes all rows for table
     * 
     * @param sql Query of rows to be deleted
     */
    void executeQuery(String sql);

    /**
     * reattachToSession
     * @param data object to re-attach to session (must not be transient)
     */
    void reattachToSession(DO data);

    /**
     * Uses a Criteria object to execute Count(*)
     * 
     * @param criteria hibernate criteria class
     * @return count of matching rows
     */
    int getCount(Criteria criteria);

    /**
     * Clear the current session.
     */
    void clearSession();

    /**
     * Force Hibernate to retrieve the data from the database.
     * 
     * @param data {@link DataObject}
     */
    void refresh(DO data);

    /**
     * Force Hibernate to retrieve the data from the database.
     * 
     * @param data Collection of {@link DataObject}
     */
    void refresh(Collection<DO> data);

    /**
     * Get the Class type of the DataObject this DAO deals with
     * 
     * @return Class of DataObject
     */
    Class<DO> getDataObjectClass();

    /**
     * Get the Class type of the ID this DAO deals with
     * 
     * @return Class of ID
     */
    Class<ID> getIdClass();

    /**
     * Convert the String into the appropriate class for the Serializable ID.
     * <p>
     * Assumes the ID class type has a single String argument constructor!
     * <p>
     * If the instantiation fails, null will be returned.
     * 
     * @param id String ID to convert
     * @return ID
     */
    ID convertId(String id);

    /**
     * Adds an order to the given {@link Criteria} for the given {@link FieldKey} in {@link SortOrder}. Includes determining
     * if a join is required.
     * 
     * @param criteria {@link Criteria} on which to add the order
     * @param fieldKey {@link FieldKey} to sort
     * @param sortOrder {@link SortOrder} either ascending or descending.
     * 
     * @return {@link Criteria} result of calling {@link Criteria#addOrder(org.hibernate.criterion.Order)}
     */
    Criteria addOrder(Criteria criteria, FieldKey fieldKey, SortOrder sortOrder);

    /**
     * Join to the {@link Criteria} object to enable a sort by the given {@link FieldKey}.
     * 
     * @param criteria {@link Criteria} on which to join
     * @param fieldKey {@link FieldKey} to sort
     * @return joined {@link Criteria}, without the sort restriction set
     */
    Criteria join(Criteria criteria, FieldKey fieldKey);

    /**
     * Join to the {@link Criteria} object to enable a sort by the given {@link FieldKey}.
     * 
     * @param criteria {@link Criteria} on which to join
     * @param fieldKey {@link FieldKey} to sort
     * @param alias String alias to give joined table
     * @return joined {@link Criteria}, without the sort restriction set
     */
    Criteria join(Criteria criteria, FieldKey fieldKey, String alias);

    /**
     * Create a {@link DetachedCriteria} to the appropriate tables, correlating to the given EntityType's ID for use as a
     * subquery (in an EXISTS criteria, for example).
     * <p>
     * Can be used with an EXISTS criteria as a work-around to a Hibernate error that does not allow joining to a table more
     * than once in a {@link Criteria}.
     * <p>
     * This method assumes that the given {@link FieldKey} for the DataObject represented by this DataAccessObject requires a
     * join.
     * 
     * @param fieldKey {@link FieldKey} for which to create {@link DetachedCriteria}
     * @param itemType {@link EntityType} on which the search is being performed
     * @param itemAlias {@link String} alias to item table
     * @return {@link DetachedCriteria}
     * 
     * @see <a href="http://opensource.atlassian.com/projects/hibernate/browse/HHH-879">Hibernate issue HHH-879</a>
     * @see SchemaUtility#isJoinRequired(Class, FieldKey)
     */
    DetachedCriteria subquery(FieldKey fieldKey, EntityType itemType, String itemAlias);

    /**
     * Return the current Hibernate {@link Session} from the {@link SessionFactory}.
     * 
     * @return current {@link Session}
     */
    Session getCurrentSession();
}
