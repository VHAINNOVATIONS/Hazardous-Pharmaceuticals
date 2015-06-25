/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.utility.ClassUtility;
import gov.va.med.pharmacy.peps.common.utility.ReflectionUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfValuesViewDo;
import gov.va.med.pharmacy.peps.domain.common.utility.SchemaUtility;


/**
 * Generic helper methods used by subclasses
 * 
 * @param <DO> Type of DataObject this DAO deals with
 * @param <ID> Type of ID the DataObject uses
 */
public abstract class DataAccessObjectImpl<DO extends DataObject, ID extends Serializable> implements 
    DataAccessObject<DO, ID> {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DataAccessObjectImpl.class);

    private static final String CREATOR_NAME = "SYSTEM";
    private static final String ID_FIELD = "id";
    private static final String SUBQUERY_ALIAS = "SUBQUERY";
    private static final String DOT = ".";

    private SessionFactory sessionFactory;
    private Class<DO> dataObjectClass;
    private Class<ID> idClass;

    /**
     * Delete the given Collection of DataObjects
     * 
     * @param list Collection of DataObject instances to delete
     */
    public void delete(Collection<DO> list) {
        if (list != null && !list.isEmpty()) {
            for (DO data : list) {
                delete(data);
            }
        }
    }

    /**
     * Delete the given DataObject
     * 
     * @param data DataObject instance to delete
     */
    public void delete(DO data) {
        if (data != null) {
            Session session = getCurrentSession();
            Object merged;

            try {
                merged = session.merge(data);
            } catch (ObjectNotFoundException e) {
                LOG.trace("Unable to merge object, will try to delete it anyway.");
                merged = data;
            }

            session.delete(merged);
            session.flush();
            session.clear();
        }
    }

    /**
     * Retrieves DOs for rows where the given fieldName matches the given value. The retrieved DOs are then deleted.
     * 
     * @param fieldName the field to retrieve by
     * @param value the value of the field
     * 
     * @see #retrieve(String, Object)
     * @see #delete(Collection)
     */
    public void delete(String fieldName, Object value) {
        List<DO> matches = retrieve(fieldName, value);
        delete(matches);
    }

    /**
     * Determine if a DataObject with the given ID exists.
     * 
     * @param id Serializable ID
     * @return true if ID for DataObject exists
     */
    public boolean exists(ID id) {
        return getCurrentSession().get(getDataObjectClass(), id) != null;
    }

    /**
     * Return the current Hibernate {@link Session} from the {@link SessionFactory}.
     * 
     * @return current {@link Session}
     */
    public Session getCurrentSession() {
        Session session = sessionFactory.getCurrentSession();
        session.setCacheMode(CacheMode.REFRESH);
        session.setFlushMode(FlushMode.ALWAYS);

        return session;
    }

    /**
     * Get the Class type of the DataObject this DAO deals with
     * 
     * @return Class of DataObject
     */
    public final Class<DO> getDataObjectClass() {
        if (dataObjectClass == null) {
            this.dataObjectClass = (Class<DO>) ClassUtility.getGenericType(getClass(), 0);
        }

        return dataObjectClass;
    }

    /**
     * Get the Class type of the ID this DAO deals with
     * 
     * @return Class of ID
     */
    public final Class<ID> getIdClass() {
        if (idClass == null) {
            this.idClass = (Class<ID>) ClassUtility.getGenericType(getClass(), 1);
        }

        return idClass;
    }

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
    public final ID convertId(String id) {
        ID result = null;

        try {
            result = (ID) getIdClass().getConstructor(String.class).newInstance(id);
        } catch (Exception e) {
            LOG.error("Unable to convert DataObject ID '" + id + "' to a serializable object!", e);
        }

        return result;
    }

    /**
     * Validate the data and save the Collection of DataObjects
     * 
     * @param list Collection of DataObjects to validate and save
     * @param user {@link UserVo} performing action
     * @return Collection of DataObjects with new ID set
     */
    public Collection<DO> insert(Collection<DO> list, UserVo user) {

        Collection<DO> results = new ArrayList<DO>();

        if (list != null && !list.isEmpty()) {
            for (DO data : list) {
                results.add(insert(data, user));
            }
        }

        return results;
    }

    /**
     * Validate the data and save
     * 
     * @param data DataObject to validate and save
     * @param user {@link UserVo} performing action
     * @return DataObject with new ID set
     */
    public DO insert(DO data, UserVo user) {
        if (data != null) {
            updateCreatedAndLastModified(data, user);

            Session session = getCurrentSession();
            session.save(data);
            session.flush();
            session.clear();
        }

        return data;
    }

    /**
     * Find the max value of the given field.
     * 
     * @param fieldName String name of the field/column to get the max of
     * @return long max value
     */
    public long max(String fieldName) {
        Criteria criteria = getCurrentSession().createCriteria(getDataObjectClass());
        criteria.setProjection(Projections.max(fieldName));

        return ((Long) criteria.uniqueResult()).longValue();
    }

    /**
     * Find the max value of the ID. This assumes that the ID is numeric, making max() a valid SQL query.
     * 
     * @return ID max value
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject#maxId()
     */
    public ID maxId() {
        Criteria criteria = getCurrentSession().createCriteria(getDataObjectClass());
        criteria.setProjection(Projections.max(ID_FIELD));

        return (ID) criteria.uniqueResult();
    }

    /**
     * Get all instances of the given DataObject without any particular order
     * 
     * @return List of DataObjects
     */
    public List<DO> retrieve() {
        return getCurrentSession().createCriteria(getDataObjectClass()).list();
    }

    /**
     * Find an instance of the given class by the ID. If the ID is not found, a HibernateException will be thrown.
     * 
     * @param id Serializable ID
     * @return DataObject found
     */
    public DO retrieve(ID id) {
        Session s = getCurrentSession();

        return (DO) s.load(getDataObjectClass(), id);
    }

    /**
     * Clear the current session.
     */
    public void clearSession() {
        Session s = getCurrentSession();
        s.clear();
    }

    /**
     * Force Hibernate to retrieve the data from the database.
     * 
     * @param data Collection of {@link DataObject}
     */
    public void refresh(Collection<DO> data) {
        if (data != null) {
            for (DO dataObject : data) {
                refresh(dataObject);
            }
        }
    }

    /**
     * Force Hibernate to retrieve the data from the database.
     * 
     * @param data {@link DataObject}
     */
    public void refresh(DO data) {
        try {
            getCurrentSession().refresh(data);
        } catch (Exception e) {
            LOG.debug("Unable to refresh a DataObject due to exception from Hibernate! Object may not match database.", e);
        }
    }

    /**
     * Return all DataObjects that match the given Restrictions (Criterion).
     * 
     * @param criteria List of Criterion to match
     * @return List of Criterion
     */
    public List<DO> retrieve(List<Criterion> criteria) {
        Criteria find = getCriteria();

        for (Criterion criterion : criteria) {
            find.add(criterion);
        }

        return find.list();
    }

    /**
     * Return list of data items matching query.
     * 
     * @param hql Query to execute
     * @return List of matching data items
     */
    public List executeHqlQuery(String hql) {
        Query query = getCurrentSession().createQuery(hql);

        return query.list();
    }

    /**
     * Deletes all rows for table
     * 
     * @param sql Query of rows to be deleted
     */
    public void executeQuery(String sql) {
        Session session = getCurrentSession();
        Query query = session.createQuery(sql);
        query.executeUpdate();
        session.flush();
        session.clear();
    }

    /**
     * Create a {@link Criteria} without an alias.
     * 
     * @return {@link Criteria}
     */
    public Criteria getCriteria() {
        return getCurrentSession().createCriteria(getDataObjectClass());
    }

    /**
     * Create a {@link Criteria} with the given alias.
     * 
     * @param alias {@link String} alias for table
     * @return {@link Criteria}
     */
    public Criteria getCriteria(String alias) {
        return getCurrentSession().createCriteria(getDataObjectClass(), alias);
    }

    /**
     * Return all DataObjects that have fields equal to the given values in the nameToValues map.
     * 
     * @param nameToValues Map of String field name as keys and Object as values
     * @return List of DataObjects
     */
    public List<DO> retrieve(Map<String, Object> nameToValues) {
        return getCurrentSession().createCriteria(getDataObjectClass()).add(Restrictions.allEq(nameToValues)).list();
    }

    /**
     * Return all DataObjects that have a field equal to the given value.
     * 
     * @param fieldName String name of the field to find by
     * @param list Collection of values of the field results may be equal to
     * @return List of DataObjects
     */
    public List<DO> retrieve(String fieldName, Collection list) {
        return getCurrentSession().createCriteria(getDataObjectClass()).add(Restrictions.in(fieldName, list)).list();
    }

    /**
     * Return all DataObjects that have a field equal to the given value.
     * 
     * @param fieldName String name of the field to find by
     * @param value Object value of the field results must be equal to
     * @return List of DataObjects
     */
    public List<DO> retrieve(String fieldName, Object value) {
        Criteria criteria = getCurrentSession().createCriteria(getDataObjectClass());

        criteria.add(Restrictions.eq(fieldName, value));

        return criteria.list();
    }

 
    /**
     * retrievePage. Retrieves a "page" of DataObjects that have a field equal to the given value.
     * 
     * @param fieldName String name of the field to find by
     * @param value Object value of the field results must be equal to
     * @param startRow integer row in result set where "page" starts
     * @param pageSize integer number of rows to retrieve per page
     * @param orderBy Link to order by
     * @param sortOrder {@link SortOrder} ascending or descending
     * @return paged List of DataObjects
     */
    public List<DO> retrievePage(String fieldName, Object value, int startRow, int pageSize, FieldKey orderBy,
                                 SortOrder sortOrder) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(getDataObjectClass());
        criteria.add(Restrictions.eq(fieldName, value));
        criteria.setFirstResult(startRow);
        criteria.setMaxResults(pageSize);
        criteria = addOrder(criteria, orderBy, sortOrder);

        return criteria.list();
    }

    /**
     * Retrieve a "page" of DataObjects that have a field equal to the given value and status of ACTIVE
     * 
     * @param fieldName This is the field used to retreive the page.
     * @param value Object value of the field results must be equal to
     * @param fieldNameActive String name of the field that should be {@link ItemStatus#ACTIVE}
     * @param startRow integer row in result set where "page" starts
     * @param pageSize integer number of rows to retrieve per page
     * @param orderBy {@link FieldKey} to order by
     * @param sortOrder {@link SortOrder} ascending or descending
     * @return paged List of DataObjects
     */
    public List<DO> retrieveActivePage(String fieldName, Object value, String fieldNameActive, int startRow, int pageSize,
                                       FieldKey orderBy, SortOrder sortOrder) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(getDataObjectClass());
        criteria.add(Restrictions.eq(fieldName, value));
        criteria.add(Restrictions.eq(fieldNameActive, ItemStatus.ACTIVE.name()));
        criteria.setFirstResult(startRow);
        criteria.setMaxResults(pageSize);
        criteria = addOrder(criteria, orderBy, sortOrder);

        return criteria.list();
    }

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
    public Criteria addOrder(Criteria criteria, FieldKey fieldKey, SortOrder sortOrder) {
        Criteria orderedCritiera = join(criteria, fieldKey);

        String propertyName = SchemaUtility.getPropertyName(getDataObjectClass(), fieldKey);
        Order order;

        if (SortOrder.DESCENDING.equals(sortOrder)) {
            order = Order.desc(propertyName);
        } else {
            order = Order.asc(propertyName);
        }

        order.ignoreCase();
        orderedCritiera.addOrder(order);

        return orderedCritiera;
    }

    /**
     * Join to the {@link Criteria} object to enable a sort by the given {@link FieldKey}.
     * 
     * @param criteria {@link Criteria} on which to join
     * @param fieldKey {@link FieldKey} to sort
     * @return joined {@link Criteria}, without the sort restriction set
     */
    public Criteria join(Criteria criteria, FieldKey fieldKey) {
        return join(criteria, fieldKey, null);
    }

    /**
     * Join to the {@link Criteria} object to enable a sort by the given {@link FieldKey}.
     * 
     * @param criteria {@link Criteria} on which to join
     * @param fieldKey {@link FieldKey} to sort
     * @param alias String alias to give joined table
     * @return joined {@link Criteria}, without the sort restriction set
     */
    public Criteria join(Criteria criteria, FieldKey fieldKey, String alias) {
        Criteria join = criteria;

        if (SchemaUtility.isJoinRequired(getDataObjectClass(), fieldKey)) {
            String[] joinPropertyNames = SchemaUtility.getJoinPropertyNames(getDataObjectClass(), fieldKey);

            for (int i = 0; i < joinPropertyNames.length; i++) {
                if (alias != null && i == joinPropertyNames.length - 1) {
                    join = join.createCriteria(joinPropertyNames[i], alias, Criteria.LEFT_JOIN);
                } else {
                    join = join.createCriteria(joinPropertyNames[i], Criteria.LEFT_JOIN);
                }
            }

            if (fieldKey.isVaDataField()) {
                join.add(Restrictions.eq(EplVadfValuesViewDo.VADF_NAME, fieldKey.getKey()));
            }
        }

        return join;
    }

    /**
     * Create a {@link DetachedCriteria} to the appropriate tables, correlating to the given EntityType's ID for use as a
     * subquery (in an EXISTS criteria, for example).
     * <p>
     * Can be used with an EXISTS criteria as a work-around to a Hibernate error that does not allow joining to a table more
     * than once in a {@link Criteria}. {@link DetachedCriteria} used in an EXISTS subquery must have a {@link Projection}
     * set on them, therefore the returned DetachedCriteria has an SQL projection of "1" set on it (all rows that match will
     * simply return a column with the value "1" in them). This differs from the Hibernate documentation that recommends a
     * {@link Projections#id()} because Hibernate seemed to get confused with composite keys with the ID projection, besides
     * the "1" will probably be faster since no data is really returned.
     * <p><p>
     * This method assumes that the given FieldKey for the DataObject represented by this DataAccessObject requires a
     * join.
     * 
     * @param fieldKey {@link FieldKey} for which to create {@link DetachedCriteria}
     * @param itemType {@link EntityType} on which the search is being performed
     * @param itemAlias {@link String} alias to item table
     * @return {@link DetachedCriteria}
     */
    public DetachedCriteria subquery(FieldKey fieldKey, EntityType itemType, String itemAlias) {
        String[] tables = SchemaUtility.getJoinPropertyNames(getDataObjectClass(), fieldKey);

        Class dataObject = ReflectionUtility.getDeclaredFieldType(tables[0], getDataObjectClass());
        DetachedCriteria criteria = DetachedCriteria.forClass(dataObject, SUBQUERY_ALIAS);
        criteria.setProjection(Projections.sqlProjection("1", null, null));

        String outer = SchemaUtility.getOuterCorrelatedAttribute(getDataObjectClass(), dataObject);
        String inner = SchemaUtility.getInnerCorrelatedAttribute(getDataObjectClass(), dataObject);

        criteria.add(Restrictions.eqProperty(criteria.getAlias() + DOT + inner, itemAlias + DOT + outer));

        for (int i = 1; i < tables.length; i++) {
            criteria = criteria.createCriteria(tables[i]);
        }

        if (fieldKey.isVaDataField()) {
            criteria.add(Restrictions.eq(EplVadfValuesViewDo.VADF_NAME, fieldKey.getKey()));
        }

        return criteria;
    }

    /**
     * Get all instances of the given DataObject in ascending order by the given field name.
     * 
     * @param orderBy String field name to order by
     * @return List of DataObjects
     */
    public List<DO> retrieveAscending(String orderBy) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(getDataObjectClass());
        criteria.addOrder(Order.asc(orderBy));
        List<DO> list = criteria.list();

        return list;
    }

    /**
     * Get a specific number of records beginning at a specific index.
     * 
     * @param orderBy String field name to order by
     * @param beginInx the beginning index
     * @param numRecords the number of records requested
     * @return List of DataObjects
     */
    public List<DO> retrieveAscending(String orderBy, int beginInx, int numRecords) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(getDataObjectClass());
        criteria.setFirstResult(beginInx);
        criteria.setMaxResults(numRecords);
        criteria.addOrder(Order.asc(orderBy));
        List<DO> list = criteria.list();

        return list;
    }

    /**
     * Get all instances of the given DataObject in descending order by the given field name.
     * 
     * @param orderBy String field name to order by
     * @return List of DataObjects
     */
    public List<DO> retrieveDescending(String orderBy) {
        return getCurrentSession().createCriteria(getDataObjectClass()).addOrder(Order.desc(orderBy)).list();
    }

    /**
     * setSessionFactory.
     * @param sessionFactory sessionFactory property
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * reattachToSession.
     * @param data object to re-attach to session (must not be transient)
     */
    public void reattachToSession(DO data) {
        getCurrentSession().replicate(data, ReplicationMode.OVERWRITE);
    }

    /**
     * Validate, merge, and update the Collection of DataObjects.
     * 
     * @param list Collection of DataObjects
     * @param user {@link UserVo} performing action
     * @return Collection of merged and updated DataObjects
     */
    public Collection<DO> update(Collection<DO> list, UserVo user) {
        Collection<DO> results = new ArrayList<DO>();

        if (list != null && !list.isEmpty()) {
            for (DO data : list) {
                results.add(update(data, user));
            }
        }

        return results;
    }

    /**
     * Validate, merge, and update the given DataObject.
     * 
     * @param data DataObject to merge and update
     * @param user {@link UserVo} performing action
     * @return DataObject with updated data
     */
    public DO update(DO data, UserVo user) {
        if (data != null) {
            updateCreatedAndLastModified(data, user);

            Session session = getCurrentSession();
            Object merged = session.merge(data);
            session.update(merged);
            session.flush();
            session.clear();
        }

        return data;
    }

    /**
     * Update the Created & Last Modified by and date/time info
     * 
     * @param list Collection of DataObjects
     * @param user {@link UserVo} performing action
     */
    public void updateCreatedAndLastModified(Collection<DO> list, UserVo user) {
        for (DO data : list) {
            updateCreatedAndLastModified(data, user);
        }
    }

    /**
     * Update the Created & Last Modified by and date/time info
     * 
     * @param data DataObject
     * @param user {@link UserVo} performing action
     */
    public void updateCreatedAndLastModified(DO data, UserVo user) {
        String username = CREATOR_NAME;

        if (user != null && user.getUsername() != null && user.getUsername().trim().length() > 0) {
            username = user.getUsername();
        }

        if (data.getCreatedBy() == null || data.getCreatedBy().trim().length() == 0) {
            data.setCreatedBy(username);
        }

        if (data.getCreatedDtm() == null) {
            data.setCreatedDtm(new Date());
        }

        if (data.getLastModifiedBy() == null || data.getLastModifiedBy().trim().length() == 0) {
            data.setLastModifiedBy(username);
        }

        if (data.getLastModifiedDtm() == null) {
            data.setLastModifiedDtm(new Date());
        }
    }

    /**
     * Uses a Criteria object to execute Count(*)
     * 
     * @param criteria hibernate criteria class
     * @return count of matching rows
     */
    public int getCount(Criteria criteria) {
        criteria.setProjection(Projections.rowCount());

        return ((Long) criteria.uniqueResult()).intValue();
    }
}
