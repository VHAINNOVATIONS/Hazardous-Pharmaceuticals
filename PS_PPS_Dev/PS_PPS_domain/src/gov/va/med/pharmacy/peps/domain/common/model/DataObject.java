/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.model;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import gov.va.med.pharmacy.peps.common.exception.DomainException;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * DataObjects common implementation
 */
public abstract class DataObject implements Serializable, Cloneable {
    
    /** CREATED_BY */
    public static final String CREATED_BY = "createdBy";
    
    /** CREATED_DATE_TIME */
    public static final String CREATED_DATE_TIME = "createdDtm";
    
    /** LAST_MODIFIED_BY */
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";
    
    /** LAST_MODIFIED_DATE_TIME */
    public static final String LAST_MODIFIED_DATE_TIME = "lastModifiedDtm";

    /** LEN_100 */
    protected static final int LEN_100 = 100;
    
    /** LEN_LONG */
    protected static final int LEN_LONG = 30;
    
    /** LEN_DATE */
    protected static final int LEN_DATE = 26;
    
    /** LEN_STR_ENUM */
    protected static final int LEN_STR_ENUM = 30;
    
    /** LEN_STR_SM */
    protected static final int LEN_STR_SM = 50;
    
    /** LEN_STR_MED */
    protected static final int LEN_STR_MED = 100;
    
    /** LEN_STR_LRG */
    protected static final int LEN_STR_LRG = 256;
    
    /** LEN_STR_MAX */
    protected static final int LEN_STR_MAX = 2000;

    /** LEN_BOOL_STR */
    protected static final int LEN_BOOL_STR = 1;

    private static final long serialVersionUID = 1L;

    /** createdBy */
    protected String createdBy;
    
    /** createdDtm */
    protected Date createdDtm;
    
    /** lastModifiedBy */
    protected String lastModifiedBy;
    
    /** lastModifiedDtm */
    protected Date lastModifiedDtm;
    
    /** minimallyPopulated */
    protected boolean minimallyPopulated = false;

    /**
     * Equals returned by Jakarta Commons EqualsBuilder
     * 
     * @param obj Object to check equals against
     * @return true if equal
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, getIgnoredCollections());
    }

    /**
     * HashCode built by Jakarta Commons HasCodeBuilder
     * 
     * @return int hash
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, getIgnoredCollections());
    }

    /**
     * toString returned by Jakarta Commons ToStringBuilder
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * Deep copy clone this DataObject through a serialize-deserialize process using Apache Common's
     * {@link SerializationUtils} class.
     * 
     * Serialization could be slow, but with the complex structure in some tables, this is a much simpler and more
     * maintainable solution.
     * 
     * @return deep copy clone of this DataObject
     * @throws CloneNotSupportedException if this object does not support cloning. Since DataObjects support cloning, this
     *             should never get thrown.
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    public DataObject clone() throws CloneNotSupportedException {
        return (DataObject) SerializationUtils.clone(this);
    }

    /**
     * Create a copy of this DataObject. This method wraps the clone() call in try/catch block to turn the checked exception,
     * CloneNotSupportedException, into an unchecked exception, DomainException.
     * 
     * @param <T> Type of DataObject to return (must be the same type as this DataObject)
     * @return A copy of this instance.
     */
    @SuppressWarnings("unchecked")
    public <T extends DataObject> T copy() {
        try {
            return (T) clone();
        } catch (CloneNotSupportedException e) {
            throw new DomainException(e, DomainException.WRAPPED_EXCEPTION);
        }
    }

    /**
     * Find, via reflection, the member variables (declared fields) that implement Collection and return the field names in a
     * Collection. Used in equals() and hashCode() to prevent checking the Collections, as Hibernate throws a fit when this
     * is done. (e.g., org.hibernate.LazyInitializationException: illegal access to loading collection)
     * 
     * @return Collection of String field names
     */
    private Collection<String> getIgnoredCollections() {
        Collection<String> fieldNames = new ArrayList<String>();
        Field[] fields = getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            if (Collection.class.isAssignableFrom(fields[i].getType())) {
                fieldNames.add(fields[i].getName());
            }
        }

        return fieldNames;
    }

    /**
     * getCreatedBy
     * @return createdBy property
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * setCreatedBy
     * @param createdBy createdBy property
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * getCreatedDtm
     * @return createdDtm property
     */
    public Date getCreatedDtm() {
        return createdDtm;
    }

    /**
     * setCreatedDtm
     * @param createdDtm createdDtm property
     */
    public void setCreatedDtm(Date createdDtm) {
        this.createdDtm = createdDtm;
    }

    /**
     * getLastModifiedBy
     * @return lastModifiedBy property
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * setLastModifiedBy
     * @param lastModifiedBy lastModifiedBy property
     */
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * getLastModifiedDtm
     * @return lastModifiedDtm property
     */
    public Date getLastModifiedDtm() {
        return lastModifiedDtm;
    }

    /**
     * setLastModifiedDtm
     * @param lastModifiedDtm lastModifiedDtm property
     */
    public void setLastModifiedDtm(Date lastModifiedDtm) {
        this.lastModifiedDtm = lastModifiedDtm;
    }

    /**
     * Return true if this {@link DataObject} is minimally populated.
     * <p>
     * A minimally populated {@link DataObject} has only the primary key set and the values required to display to the user.
     * All other values are likely to be null.
     * 
     * @return minimallyPopulated property
     */
    public boolean isMinimallyPopulated() {
        return minimallyPopulated;
    }

    /**
     * Return true if this {@link ValueObject} is not minimally populated.
     * 
     * @return boolean
     */
    public boolean isFullyPopulated() {
        return !isMinimallyPopulated();
    }

    /**
     * setMinimallyPopulated
     * @param minimallyPopulated minimallyPopulated property
     */
    public void setMinimallyPopulated(boolean minimallyPopulated) {
        this.minimallyPopulated = minimallyPopulated;
    }

    /**
     * Return a set of EplVadfOwners
     * 
     * @return EplVadfOwnerDo Set
     */
    public Set<EplVadfOwnerDo> getEplVadfOwners() {
        throw new UnsupportedOperationException();
    }
}
