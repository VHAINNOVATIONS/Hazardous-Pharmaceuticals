/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.datafield;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.CommonException;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * DataField representing a list of data fields grouped together.
 */
public class GroupDataField extends DataField<List<DataField>> {
    private static final long serialVersionUID = 1L;

    /**
     * Set the DataFieldKey
     * 
     * @param key DataFieldKey key for this DataField instance
     */
    protected GroupDataField(FieldKey<? extends GroupDataField> key) {
        super(key);

        int size = key.getGroupedFields().size();
        setValue(new ArrayList<DataField>(size));
        setDefaultValue(new ArrayList<DataField>(size));
    }

    /**
     * Select a value for the given DataField. The DataField's FieldKey must be a field grouped by this GroupDataField,
     * otherwise a {@link CommonException#UNKNOWN_GROUPED_FIELD} (runtime) is thrown.
     * 
     * @param <T> Type of DataField to select
     * @param dataField selection
     * @return DataField selected
     */
    public <T> DataField<T> selectGroupedFieldValue(DataField<T> dataField) {
        FieldKey fieldKey = dataField.getKey();
        int index = getKey().getGroupedFields().indexOf(fieldKey);

        if (index < 0) {
            throw new CommonException(CommonException.UNKNOWN_GROUPED_FIELD, fieldKey.getKey(), getKey().getKey());
        }

        if (super.getValue() == null) {
            int size = getKey().getGroupedFields().size();
            super.setValue(new ArrayList<DataField>(size));
        }
        
        while (super.getValue().size() <= index) {
            super.getValue().add(null);
        }

        return super.getValue().set(index, dataField);
    }

    /**
     * Select a default value for the given DataField. The DataField's FieldKey must be a field grouped by this
     * GroupDataField, otherwise a {@link CommonException#UNKNOWN_GROUPED_FIELD} (runtime) is thrown.
     * 
     * @param <T> Type of DataField to select
     * @param dataField default selection
     * @return DataField selection
     */
    public <T> DataField<T> selectDefaultGroupedFieldValue(DataField<T> dataField) {
        FieldKey fieldKey = dataField.getKey();
        int index = fieldKey.getGroupedFields().indexOf(fieldKey);

        if (index < 0) {
            throw new CommonException(CommonException.UNKNOWN_GROUPED_FIELD, fieldKey.getKey(), getKey().getKey());
        }

        if (super.getDefaultValue() == null) {
            int size = getKey().getGroupedFields().size();
            super.setDefaultValue(new ArrayList<DataField>(size));
        }
        
        while (super.getDefaultValue().size() <= index) {
            super.getDefaultValue().add(null);
        }

        return super.getDefaultValue().set(index, dataField);
    }

    /**
     * Get the DataField grouped by this GroupDataField with the given FieldKey.
     * 
     * If the given FieldKey is not for a DataField grouped by this GroupDataField, a
     * {@link CommonException#UNKNOWN_GROUPED_FIELD} (runtime) is thrown.
     * 
     * If the List of grouped data fields is somehow the wrong size (for instance some other code called
     * {@link #setValue(List)} with a List of the wrong size), and the index for the given FieldKey is out of bounds, then
     * null is returned (rather than throwing an {@link IndexOutOfBoundsException}).
     * 
     * @param <T> Type of DataField to get
     * @param fieldKey FieldKey of grouped data field to get
     * @return DataField
     */
    public <T> T getGroupedField(FieldKey<T> fieldKey) {
        int index = getKey().getGroupedFields().indexOf(fieldKey);

        if (index < 0) {
            throw new CommonException(CommonException.UNKNOWN_GROUPED_FIELD, fieldKey.getKey(), getKey().getKey());
        }

        if (index >= super.getValue().size()) {
            return null;
        } else {
            return (T) super.getValue().get(index);
        }
    }

    /**
     * A GroupDataField is selected if the fields grouped by this field are in the correct order as defined by its FieldKey
     * and are all selected.
     * 
     * @return true if the field is selected
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.datafield.DataField#isSelected()
     */
    @Override
    public boolean isSelected() {
        boolean selected = true;
        int size = getKey().getGroupedFields().size();
        List<DataField> value = super.getValue();

        if (value != null && value.size() == size) {
            for (int i = 0; i < size; i++) {
                DataField groupedField = value.get(i);

                selected &= groupedField != null && groupedField.getKey().equals(getKey().getGroupedFields().get(i))
                    && groupedField.isSelected();
            }
        } else {
            selected = false;
        }

        return selected;
    }

    /**
     * A GroupDataField is equal to the default value if the fields groped by this field are all equal to the default value.
     * 
     * @return true if this field is default
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.datafield.DataField#isDefault()
     */
    @Override
    public boolean isDefault() {
        boolean result = false;

        if (super.getValue().size() == super.getDefaultValue().size()) {
            result = super.isDefault(super.getValue());
        }

        return result;
    }

    /**
     * Returns an immutable list of selections.
     * 
     * If new selections must be set, use the {@link #selectGroupedFieldValue(DataField)} method.
     * 
     * @return selected property
     * 
     * @see #selectGroupedFieldValue(DataField)
     */
    @Override
    public List<DataField> getValue() {
        return Collections.unmodifiableList(super.getValue());
    }

    /**
     * Returns an immutable list of default selections.
     * 
     * If new default selections must be set, use the {@link #selectDefaultGroupedFieldValue(DataField)} method.
     * 
     * @return selected property
     * 
     * @see #
     */
    @Override
    public List<DataField> getDefaultValue() {
        return Collections.unmodifiableList(super.getDefaultValue());
    }

    /**
     * A GroupDataField is editable if all of its grouped DataFields are editable.
     * 
     * @return true if editable, else false
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.datafield.DataField#isEditable()
     */
    @Override
    public boolean isEditable() {
        boolean result = false;
        int size = getKey().getGroupedFields().size();
        List<DataField> value = super.getValue();

        if (value != null && value.size() == size) {
            for (int i = 0; i < size; i++) {
                DataField groupedField = value.get(i);

                result = groupedField != null && groupedField.getKey() == getKey().getGroupedFields().get(i)
                    && groupedField.isEditable();
            }
        }

        return result;
    }

    /**
     * Setting a GroupDataField as editable set all of its grouped DataFields as editable.
     * 
     * @param editable true if editable, else false
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.datafield.DataField#setEditable(boolean)
     */
    @Override
    public void setEditable(boolean editable) {
        int size = getKey().getGroupedFields().size();

        if (super.getValue() != null) {
            for (int i = 0; i < size; i++) {
                super.getValue().get(i).setEditable(editable);
            }
        }
    }

    /**
     * Concatenate all of the grouped DataField's toShortString().
     * 
     * @return short toString()
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.datafield.DataField#toShortString()
     */
    @Override
    public String toShortString() {
        StringBuffer shortString = new StringBuffer();
        int size = getKey().getGroupedFields().size();
        int sizeValues = (super.getValue() == null ? 0 : super.getValue().size());

        if (super.getValue() != null) {
            for (int i = 0; i < size; i++) {
                if (i != 0) {
                    shortString.append(", ");
                }
                
                if (i < sizeValues) {
                    shortString.append(super.getValue().get(i).toShortString());                    
                } else {
                    shortString.append("N/A");
                }
            }
        }
        
        return shortString.toString();
    }

    /**
     * Unselected a GroupDataField unselects all of its grouped DataFields.
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.datafield.DataField#unselect()
     */
    @Override
    public void unselect() {
        int size = getKey().getGroupedFields().size();

        if (getValue() != null) {
            for (int i = 0; i < size; i++) {
                getValue().get(i).unselect();
            }
        }
    }
}
