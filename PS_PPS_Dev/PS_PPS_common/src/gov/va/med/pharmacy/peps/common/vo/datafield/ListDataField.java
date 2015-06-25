/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.datafield;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;

import gov.va.med.pharmacy.peps.common.utility.NumberFormatUtility;
import gov.va.med.pharmacy.peps.common.utility.ReflectionUtility;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * DataField representing a selection of zero or more items from a list
 * 
 * @param <T> Type of the element in the list
 */
public class ListDataField<T> extends DataField<List<T>> {
    private static final long serialVersionUID = 1L;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ListDataField.class);

    /**
     * Set the DataFieldKey
     * 
     * @param key DataFieldKey key for this DataField instance
     */
    protected ListDataField(FieldKey<? extends ListDataField<T>> key) {
        super(key);

        setValue(new ArrayList<T>());
        setDefaultValue(new ArrayList<T>());
    }

    /**
     * Add the given selection to the list of default selected values.
     * 
     * @param selection to add
     * @return object added to the list of default selections
     */
    public T addDefaultSelection(T selection) {
        if (super.getDefaultValue() == null) {
            setDefaultValue(new ArrayList<T>());
        }

        getDefaultValue().add(selection);

        return selection;
    }

    /**
     * Add the given selections to the list of default selected values.
     * 
     * @param selections to add
     * @return objects added to the list of default selections
     */
    public Collection<T> addDefaultSelections(Collection<T> selections) {
        if (super.getDefaultValue() == null) {
            setDefaultValue(new ArrayList<T>());
        }

        getDefaultValue().addAll(selections);

        return selections;
    }

    /**
     * Add the given selections to the list of default selected values.
     * 
     * Convert a String value into the Type, T, for this DataField. The constructor which takes a single parameter of type
     * String is called and the results are returned as the type of this DataField.
     * 
     * If the constructor could not be found or called, a CommonException (Runtime) is thrown.
     * 
     * @param selections to add
     * @return objects added to the list of default selections
     */
    public Collection<String> addStringDefaultSelections(Collection<String> selections) {
        for (String selection : selections) {
            addStringDefaultSelection(selection);
        }

        return selections;
    }

    /**
     * Add the given selection to the list of selected values.
     * 
     * @param selection to add
     * @return object added to the list of selections
     */
    public T addSelection(T selection) {
        if (getValue() == null) {
            setValue(new ArrayList<T>());
        }

        getValue().add(selection);

        return selection;
    }

    /**
     * Add the given selections to the list of selected values.
     * 
     * @param selections to add
     * @return objects added to the list of selections
     */
    public Collection<T> addSelections(Collection<T> selections) {
        if (getValue() == null) {
            setValue(new ArrayList<T>());
        }

        getValue().addAll(selections);

        return selections;
    }

    /**
     * Add a selection to the list of default selections.
     * 
     * Convert a String value into the Type, T, for this DataField. The constructor which takes a single parameter of type
     * String is called and the results are returned as the type of this DataField.
     * 
     * If the constructor could not be found or called, a CommonException (Runtime) is thrown.
     * 
     * @param selection String value for this selection
     * @return object added to the list of default selections
     */
    public T addStringDefaultSelection(String selection) {
        return addDefaultSelection((T) ReflectionUtility.convertStringValue(getKey(), selection));
    }

    /**
     * Add a selection to the list with the given values.
     * 
     * Convert a String value into the Type, T, for this DataField. The constructor which takes a single parameter of type
     * String is called and the results are returned as the type of this DataField.
     * 
     * If the constructor could not be found or called, a CommonException (Runtime) is thrown.
     * 
     * @param selection String value for this selection
     * @return object added to the list of selections
     */
    public T addStringSelection(String selection) {
        return addSelection((T) ReflectionUtility.convertStringValue(getKey(), selection));
    }

    /**
     * Add the given selections to the list of selected values.
     * 
     * Convert a String value into the Type, T, for this DataField. The constructor which takes a single parameter of type
     * String is called and the results are returned as the type of this DataField.
     * 
     * If the constructor could not be found or called, a CommonException (Runtime) is thrown.
     * 
     * @param selections to add
     * @return objects added to the list of default selections
     */
    public Collection<String> addStringSelections(Collection<String> selections) {
        for (String selection : selections) {
            addStringSelection(selection);
        }

        return selections;
    }

    /**
     * Test if the given selection is in the list of selections. The selection object must equal a selection in the list.
     * 
     * @param selection to test
     * @return true if the given selection is found
     */
    public boolean contains(T selection) {
        return isSelected() && getValue().contains(selection);
    }

    /**
     * Test if the given selections are in the list of selections. Each selection object must equal a selection in the list.
     * 
     * @param selections to test
     * @return true if the given selections are found
     */
    public boolean containsAll(Collection<T> selections) {
        return isSelected() && getValue().containsAll(selections);
    }

    /**
     * Test if the given selection is in the list of default selections. The selection object must equal a selection in the
     * list of default selections.
     * 
     * @param selection to test
     * @return true if the given selection is found
     */
    public boolean containsDefault(T selection) {
        return getDefaultValue() != null && getDefaultValue().contains(selection);
    }

    /**
     * Test if the given selections are in the list of default selections. Each selection object must equal a selection in
     * the list of default selections.
     * 
     * @param selections to test
     * @return true if the given selections are all found
     */
    public boolean containsAllDefault(Collection<T> selections) {
        return getDefaultValue() != null && getDefaultValue().containsAll(selections);
    }

    /**
     * Returns the default selections. If the current instance of the default selections is null, it returnes an empty
     * collection.
     * 
     * @return default selections
     * 
     * @see #removeSelection(ListSelection)
     * @see #unselect()
     */
    @Override
    public List<T> getDefaultValue() {
        if (super.getDefaultValue() == null) {
            return Collections.emptyList();
        } else {
            return super.getDefaultValue();
        } 
    }

    /**
     * Method used by OGNL, since it doesn't seem to call is* methods. Delegates to the is* method for the return value.
     * 
     * @return {@link #isMultiselect()}
     */
    public boolean getMultiselect() {
        return isMultiselect();
    }

    /**
     * Returns the selections.
     * 
     * @return selections
     * 
     * @see #removeSelection(ListSelection)
     * @see #unselect()
     */
    @Override
    public List<T> getValue() {
        List values = super.getValue();
        
        try {
            Collections.sort(values);
        } catch (Exception npe) {
            LOG.debug(npe.getMessage());
        }

        return values;
    }

    /**
     * isDefault.
     * @return true if any of the selected values equals the default value

     */
    @Override
    public boolean isDefault() {
        
        // return the default value of the ListDataField.
        if (getDefaultValue() == null) {
            return getValue() == null || !isSelected();
        }

        // retrun true if the value containes the entire default value
        if (getValue() != null) {
            return getValue().containsAll(getDefaultValue());
        }

        return false;
    }

    /**
     * isMultiselect.
     * @return true if {@link FieldKey#isMultiSelectListDataField()} or {@link FieldKey#isMultiSelectPrimaryKeyDataField()}
     */
    public boolean isMultiselect() {
        return getKey().isMultiSelectListDataField() || getKey().isMultiSelectPrimaryKeyDataField();
    }

    /**
     * isSelected for ListDataField.java
     * @return true if there are items in the set of selected items
     */
    @Override
    public boolean isSelected() {
        return getValue() != null && !getValue().isEmpty();
    }

    /**
     * Removes the given ListSelection from the list of selections. 
     * If this was the last remaining item in the list of selections, and the list is now empty, set the selected 
     * property to false.
     * 
     * @param selection to remove
     * @return boolean true if the given selection existed and was removed
     */
    public boolean removeDefaultSelection(T selection) {
        return getDefaultValue().remove(selection);
    }

    /**
     * Remove the given ListSelection from the list of selections. If this was the last remaining item in the list of
     * selections, and the list is now empty, set the selected property to false.
     * 
     * @param selection to remove
     * @return boolean true if the given selection existed and was removed
     */
    public boolean removeSelection(T selection) {
        return getValue().remove(selection);
    }

    /**
     * Set the list of selections and set the selected property to true, if the list of selections is not empty.
     * 
     * @param selections new list of selections
     */
    @Override
    public void selectValue(List<T> selections) {
        setValue(selections);
    }

    /**
     * toShortString.
     * @return short string value
     */
    @Override
    public String toShortString() {
        StringBuffer shortString = new StringBuffer();

        if (isSelected()) {
            for (T selection : getValue()) {
                if (shortString.length() > 0) {
                    shortString.append(", ");
                }

                if (selection instanceof ValueObject) {
                    shortString.append(((ValueObject) selection).toShortString());
                } else if (selection instanceof Number) {
                    shortString.append(NumberFormatUtility.format((Number) selection));
                } else {
                    shortString.append(selection == null ? "" : selection.toString());
                }
            }
        }

        return shortString.toString();
    }

    /**
     * Clear the list of selections and set the property selected to false.
     */
    @Override
    public void unselect() {
        getValue().clear();
    }

    /**
     * Clear the list of default selections for the ListDataField.
     */
    public void unselectDefault() {
        getDefaultValue().clear();
    }

    /**
     * ListDataFields are equal even if their lists are in different orders.
     * 
     * @param obj Object to compare
     * @return boolean true if equal
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        Set<String> ignoredFields = getIgnoreEqualsFields();
        ignoredFields.add("value");
        ignoredFields.add("defaultValue");
        
        boolean equal = EqualsBuilder.reflectionEquals(this, obj, ignoredFields);

        if (equal && obj instanceof ListDataField) {
            ListDataField listDataField = (ListDataField) obj;

            equal &= getValue().size() == listDataField.getValue().size()
                && getValue().containsAll(listDataField.getValue());
            equal &= getDefaultValue().size() == listDataField.getDefaultValue().size()
                && getDefaultValue().containsAll(listDataField.getDefaultValue());
        }

        return equal;
    }

    /**
     * Call into {@link ValueObject#hashCode()}.
     * 
     * @return int hash code
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#hashCode()
     */
    @Override
    public int hashCode() {
        int hashCode = super.hashCode();
        hashCode += serialVersionUID;
       
        return hashCode;
    }
}
