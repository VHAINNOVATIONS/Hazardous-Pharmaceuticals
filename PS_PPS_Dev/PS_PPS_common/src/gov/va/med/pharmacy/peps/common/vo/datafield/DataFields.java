/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.datafield;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.FieldKey.FieldSubType;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Helper class wrapping a Map of DataFields allowing easy access to the fields by DataFieldKey.
 * 
 * @param <T> Type of DataField stored in this class
 */
public class DataFields<T extends DataField> extends ValueObject {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(DataFields.class);
    private String vaDfOwnerId;
    private Map<FieldKey<? extends T>, T> dataFields = new HashMap<FieldKey<? extends T>, T>();

    /**
     * Empty constructor
     */
    public DataFields() {
        super();
    }

    /**
     * Check to see if a DataField with the given key exists in the DataField instances held by this DataFields class.
     * 
     * @param key FieldKey to search for
     * @return true if the DataField instances held by this DataFields class includes one with the given key
     */
    public boolean containsFieldKey(FieldKey key) {
        return getDataFields().containsKey(key);
    }

    /**
     * Check to see if a DataField with the given value exists in the DataField instances held by this DataFields class.
     * 
     * @param dataField DataField value to search for
     * @return true if the DataField instances held by this DataFields class includes one with the given value
     */
    public boolean containsDataField(DataField dataField) {
        return getDataFields().containsValue(dataField);
    }

    /**
     * Check to see if DataFields with the given keys exists in the DataField instances held by this DataFields class. The
     * result is true only if all the given FieldKey instances are found.
     * 
     * @param keys FieldKeys to search for
     * @return true if the DataField instances held by this DataFields class includes any with the given keys
     */
    public boolean containsFieldKeys(Collection<FieldKey> keys) {
        boolean allFound = true;

        for (FieldKey fieldKey : keys) {
            allFound = allFound && containsFieldKey(fieldKey);
        }

        if (keys.isEmpty()) {
            return false;
        } else {
            return allFound;
        }
    }

    /**
     * Check to see if DataFields with the given values exists in the DataField instances held by this DataFields class. The
     * result is true only if all given DataField instances are found.
     * 
     * @param dataFields1 DataField values to search for
     * @return true if the DataField instances held by this DataFields class includes any with the given values
     */
    public boolean containsDataFields(Collection<DataField> dataFields1) {
        boolean allFound = true;

        for (DataField dataField : dataFields1) {
            allFound = allFound && containsDataField(dataField);
        }

        return allFound;
    }

    /**
     * Add the given DataField. If the DataField, field, already exists in this ItemDetailVo, then the previous is replaced
     * and returned. If no value was previously set, null is returned.
     * 
     * @param <F> type of DataField to set
     * @param field instance of DataField to set
     * 
     * @return DataField that used to be set on this ItemDetailVo, if none existed previously this value is null
     */
    public <F extends T> F setDataField(F field) {
        return (F) dataFields.put(field.getKey(), field);
    }

    /**
     * Remove the given field from the current DataFields The data field with the same key as the given field will be
     * removed. The DataField instance that was previously in the Map will be returned. If the field was not found in the
     * map, null will be returned.
     * 
     * @param <F> Type of DataField to remove
     * @param field DataField instance to remove.
     * @return DataField instance that was previously in the map for the key of the given field
     */
    public <F extends T> F removeDataField(F field) {
        return (F) removeDataField(field.getKey());
    }

    /**
     * Remove the field for the given key from the current DataFields The DataField instance that was previously in the Map
     * will be returned. If the field was not found in the map, null will be returned.
     * 
     * @param <F> Type of DataField the FieldKey represents
     * @param key DataFieldKey of the DataField to remove
     * @return DataField instance that was previously in the map for the key of the given field
     */
    public <F extends T> F removeDataField(FieldKey<F> key) {
        return (F) dataFields.remove(key);
    }

    /**
     * Get the specified data field. In order to retrieve a data field, a specific DataFieldKey must be used. You cannot
     * retrieve the generic data fields with this method (i.e., BooleanDataField, StringDataField, etc.).
     * 
     * @param <F> Type of DataFiled the FieldKey represents
     * @param key DataFieldKey of data field to retrieve
     * 
     * @return DataField instance, or null if the given key is not found
     * @see #getDataFields(FieldKey) to get all DataField instances of a generic type (i.e., DataField<Boolean>, 
     *      DataField<String>)
     */
    public <F extends T> F getDataField(FieldKey<F> key) {
        F field = (F) dataFields.get(key);

        if (field == null) {
            LOG.trace("Data field for key not found: " + key.getKey());
        }

        return field;
    }

    /**
     * Get the DataFields that implement the given class the key represents. For instance, passing
     * {@link FieldSubType#GROUP_DATA_FIELD} will return all GroupDataFields.
     * 
     * @param <F> Type of DataField the given FieldKey represents
     * @param fieldSubType FieldSubType of type to return
     * 
     * @return DataFields wrapper class of data fields matching key criteria
     */
    public <F extends T> DataFields<F> getDataFields(FieldSubType fieldSubType) {
        DataFields<F> fields = new DataFields<F>();

        for (FieldKey dataFieldKey : dataFields.keySet()) {
            if (fieldSubType.equals(dataFieldKey.getFieldSubType())) {
                fields.setDataField((F) getDataField(dataFieldKey));
            }
        }

        return fields;
    }

    /**
     * Get the DataFields that implement the given class the key represents. For instance, passing
     * {@link FieldSubType#SIMPLE_DATA_FIELD}, Boolean.class will return all DataField<Boolean>.
     * 
     * @param <F> Type of DataField the given FieldKey represents
     * @param fieldSubType FieldSubType of type to return
     * @param clazz Class for the FieldKey
     * 
     * @return DataFields wrapper class of data fields matching key criteria
     */
    public <F extends T> DataFields<F> getDataFields(FieldSubType fieldSubType, Class clazz) {
        DataFields<F> fields = new DataFields<F>();

        for (FieldKey dataFieldKey : dataFields.keySet()) {
            if (fieldSubType.equals(dataFieldKey.getFieldSubType()) && clazz.isAssignableFrom(dataFieldKey.getFieldClass())) {
                fields.setDataField((F) getDataField(dataFieldKey));
            }
        }

        return fields;
    }

    /**
     * Get all DataFields The Map returned has a key of the concrete class for each DataField and a value of the DataField
     * instance.
     * 
     * @return Map of DataFields, the concrete class is the key to retrieve an individual value
     */
    public Map<FieldKey<? extends T>, T> getDataFields() {
        return dataFields;
    }

    /**
     * The DataFields instance is "empty" if no FieldKey/DataField key/value pairs exist in the internal Map. In other words,
     * dataFields.size() == 0
     * 
     * @return boolean true if there are no FieldKey/DataField key/value pairs in the internal Map
     */
    public boolean isEmpty() {
        return dataFields != null && dataFields.size() == 0;
    }

    /**
     * Set all of the data fields at once in the Map. This assumes that the Map passed in uses the class as the key and the
     * DataField as the value.
     * 
     * @param dataFields property
     */
    public void setDataFields(Map<FieldKey<? extends T>, T> dataFields) {
        this.dataFields = dataFields;
    }

    /**
     * getVaDfOwnerId.
     * @return vaDfOwnerId property used only by the
     */
    public String getVaDfOwnerId() {
        return vaDfOwnerId;
    }

    /**
     * setVaDfOwnerId.
     * @param vaDfOwnerId vaDfOwnerId property
     */
    public void setVaDfOwnerId(String vaDfOwnerId) {
        this.vaDfOwnerId = vaDfOwnerId;
    }
}
