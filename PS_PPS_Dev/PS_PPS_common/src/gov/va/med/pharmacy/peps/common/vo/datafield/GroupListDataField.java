/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.datafield;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.CommonException;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Contain a List of GroupDataFields
 */
public class GroupListDataField extends DataField<List<GroupDataField>> {
    private static final long serialVersionUID = 1L;

    /**
     * Set the DataFieldKey
     * 
     * @param key DataFieldKey key for this DataField instance
     */
    protected GroupListDataField(FieldKey<? extends GroupDataField> key) {
        super(key);

        setValue(new ArrayList<GroupDataField>());
        setDefaultValue(new ArrayList<GroupDataField>());
    }

    /**
     * Add the given selection to the list of default selected values.
     * 
     * @param selection to add
     * @return object added to the list of default selections
     */
    public GroupDataField addDefaultSelection(GroupDataField selection) {
        if (getDefaultValue() == null) {
            setDefaultValue(new ArrayList<GroupDataField>());
        }

        if (selection.getGroupKey().equals(getKey())) {
            getDefaultValue().add(selection);
        } else {
            throw new CommonException(CommonException.INCORRECT_GROUP_DATA_FIELD, selection.getGroupKey().getKey(), getKey()
                .getKey());
        }

        return selection;
    }

    /**
     * Add a new GroupDataField default value by providing the grouped DataFields as an array (via var args).
     * 
     * @param dataFields Array/var args of DataFields in the new GroupDataField
     * @return GroupDataField added
     */
    public GroupDataField addDefaultGroupedDefaultSelection(DataField... dataFields) {
        return addDefaultSelection(createGroup(dataFields));
    }

    /**
     * Create a new GroupDataField with the given array of DataFields.
     * 
     * If the array is of the incorrect size or if any of the DataFields do not belong to this GroupDataField, throw a
     * CommonException (runtime).
     * 
     * @param dataFields Array of DataFields
     * @return GroupDataField containing given DataFields
     */
    private GroupDataField createGroup(DataField... dataFields) {
        GroupDataField group = new GroupDataField((FieldKey<GroupDataField>) getKey());

        if (dataFields.length != getKey().getGroupedFields().size()) {
            throw new CommonException(CommonException.INCORRECT_NUMBER_OF_GROUPED_FIELDS, dataFields.length, getKey()
                .getKey(), getKey().getGroupedFields().size());
        }

        for (DataField dataField : dataFields) {
            group.selectGroupedFieldValue(dataField);
        }

        return group;
    }

    /**
     * Add the given selections to the list of default selected values.
     * 
     * @param selections to add
     * @return objects added to the list of default selections
     * 
     * @see #addDefaultSelection(GroupDataField)
     */
    public Collection<GroupDataField> addDefaultSelections(Collection<GroupDataField> selections) {
        if (getValue() == null) {
            setDefaultValue(new ArrayList<GroupDataField>());
        }

        // call addDefaultSelection() to verify the FieldKeys are correct
        for (GroupDataField selection : selections) {
            addDefaultSelection(selection);
        }

        return selections;
    }

    /**
     * Add an empty GroupDataField instance to this GroupListDataField's list of selections.
     * 
     * @return GroupDataField added
     */
    private GroupDataField addEmptySelection() {
        if (getValue() == null) {
            setValue(new ArrayList<GroupDataField>());
        }

        GroupDataField group = new GroupDataField((FieldKey<GroupDataField>) getKey());
        getValue().add(group);

        return group;
    }

    /**
     * Add the given selection to the list of selected values.
     * 
     * @param selection to add
     * @return object added to the list of selections
     */
    public GroupDataField addSelection(GroupDataField selection) {
        if (getValue() == null) {
            setValue(new ArrayList<GroupDataField>());
        }

        if (selection.getGroupKey().equals(getKey())) {
            getValue().add(selection);
        } else {
            throw new CommonException(CommonException.INCORRECT_GROUP_DATA_FIELD, selection.getGroupKey().getKey(), getKey()
                .getKey());
        }

        return selection;
    }

    /**
     * Add a new GroupDataField value by providing the grouped DataFields as an array (via var args).
     * 
     * @param dataFields Array/var args of DataFields in the new GroupDataField
     * @return GroupDataField added
     */
    public GroupDataField addGroupedSelection(DataField... dataFields) {
        return addSelection(createGroup(dataFields));
    }

    /**
     * Add the given selections to the list of selected values.
     * 
     * @param selections to add
     * @return objects added to the list of selections
     * 
     * @see #addSelection(GroupDataField)
     */
    public Collection<GroupDataField> addSelections(Collection<GroupDataField> selections) {
        if (getValue() == null) {
            setValue(new ArrayList<GroupDataField>());
        }

        // call addSelection() for each to verify the FieldKey is correct
        for (GroupDataField selection : selections) {
            addSelection(selection);
        }

        return selections;
    }

    /**
     * Test if the given selection is in the list of selections. The selection object must equal a selection in the list.
     * 
     * @param selection to test
     * @return true if the given selection is found
     */
    public boolean contains(GroupDataField selection) {
        return isSelected() && getValue().contains(selection);
    }

    /**
     * Test if the given selection is in the list of default selections. The selection object must equal a selection in the
     * list of default selections.
     * 
     * @param selection to test
     * @return true if the given selection is found
     */
    public boolean containsDefault(GroupDataField selection) {
        return getDefaultValue() != null && getDefaultValue().contains(selection);
    }

    /**
     * This method should only be called by GroupListDataFieldPropertyAccessor to get an instance of a Group. If the given
     * index is out of bounds, call {@link #addEmptySelection()} until the internal List is the correct size.
     * 
     * @param index row to retrieve
     * @return GroupDataField at the given index
     */
    public GroupDataField getGroup(int index) {
        if (getValue() == null) {
            setValue(new ArrayList<GroupDataField>());
        }

        int numToAdd = (index + 1) - getValue().size();

        for (int i = 0; i < numToAdd; i++) {
            addEmptySelection();
        }

        return getValue().get(index);
    }

    /**
     * isDefault.
     * @return true if any of the selected values equals the default value
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.datafield.DataField#isDefault()
     */
    @Override
    public boolean isDefault() {
        if (getDefaultValue() == null) {
            return getValue() == null || !isSelected();
        }

        if (getValue() != null) {
            return getValue().containsAll(getDefaultValue());
        }

        return false;
    }

    /**
     * isSelected.
     * @return true if there are items in the set of selected items
     */
    @Override
    public boolean isSelected() {
        return getValue() != null && !getValue().isEmpty();
    }

    /**
     * Remove the given ListSelection from the list of selections. If this was the last remaining item in the list of
     * selections, and the list is now empty, set the selected property to false.
     * 
     * @param selection to remove
     * @return boolean true if the given selection existed and was removed
     */
    public boolean removeDefaultSelection(GroupDataField selection) {
        return getDefaultValue().remove(selection);
    }

    /**
     * Remove the given ListSelection from the list of selections. If this was the last remaining item in the list of
     * selections, and the list is now empty, set the selected property to false.
     * 
     * @param selection to remove
     * @return boolean true if the given selection existed and was removed
     */
    public boolean removeSelection(GroupDataField selection) {
        return getValue().remove(selection);
    }

    /**
     * Set the list of selections and set the selected property to true, if the list of selections is not empty.
     * 
     * @param selections new list of selections
     * 
     * @see #addSelection(GroupDataField)
     */
    @Override
    public void selectValue(List<GroupDataField> selections) {
        if (getValue() == null) {
            setValue(new ArrayList<GroupDataField>());
        }

        // call addSelection() for each in the given list to verify the FieldKey is correct
        for (GroupDataField selection : selections) {
            addSelection(selection);
        }
    }

    /**
     * toShortString.
     * @return short string value
     */
    @Override
    public String toShortString() {
        StringBuffer shortString = new StringBuffer();

        if (isSelected()) {
            for (GroupDataField selection : getValue()) {
                if (shortString.length() > 0) {
                    shortString = shortString.append(", ");
                }

                shortString = shortString.append(selection.toShortString());
            }
        }

        return shortString.toString();
    }

    /**
     * Clear the list of selections and set the selected property to false.
     */
    @Override
    public void unselect() {
        getValue().clear();
    }

    /**
     * Clear the list of default selections.
     */
    public void unselectDefault() {
        getDefaultValue().clear();
    }
}
