/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.datafield;


import java.util.Locale;

import gov.va.med.pharmacy.peps.common.exception.CommonException;
import gov.va.med.pharmacy.peps.common.utility.NumberFormatUtility;
import gov.va.med.pharmacy.peps.common.utility.ReflectionUtility;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;


/**
 * A DataField represents a single value for a ValueObject
 * 
 * @param <T> Type of the value for this DataField
 */
public class DataField<T> extends ValueObject {

    private static final long serialVersionUID = 1L;

    private final FieldKey<? extends DataField> key;
    private Long dataFieldId = null;
    private boolean editable = true;
    private boolean requestToEdit = false;

    private T value = null;
    private T defaultValue = null;

    /**
     * Set the FieldKey
     * 
     * @param <K> Type of DataField the FieldKey represents
     * @param key DataFieldKey for this DataField instance
     */
    protected <K extends DataField> DataField(FieldKey<K> key) {

        this.key = key;
    }

    /**
     * getKey.
     * @return key property
     */
    public FieldKey<? extends DataField> getKey() {

        return key;
    }

    /**
     * Create a new instance of a DataField using the given key. The key will be mapped to the correct subclass of DataField.
     * If the key does not exist or there is an error in instantiating the DataField, an unchecked CommonException is thrown.
     * 
     * @param <K> type of the DataField
     * @param key DataField key for the new DataField instance
     * @return DataField instance for the given key
     */
    public static <K extends DataField> K newInstance(FieldKey<K> key) {
        if(key==null){
            System.out.println("NULL Field key passed to newInstance !!!!");
        }else{
            System.out.println("NON NULL Field key passed to newInstance");
        }
        if (key.isSimpleDataField()) {
            return (K) new DataField(key);
        } else if (key.isListDataField() || key.isPrimaryKeyDataField()) {
            return (K) new ListDataField(key);
        } else if (key.isMultitextDataField()) {
            return (K) new MultitextDataField(key);
        } else if (key.isGroupDataField()) {
            return (K) new GroupDataField((FieldKey<GroupDataField>) key);
        } else if (key.isGroupListDataField()) {
            return (K) new GroupListDataField((FieldKey) key);
        } else {
            throw new CommonException(CommonException.UNKNOWN_DATA_FIELD, key.getKey());
        }
    }

    /**
     * getDataFieldId.
     * @return dataFieldId property
     */
    public Long getDataFieldId() {

        return dataFieldId;
    }

    /**
     * getDefaultValue.
     * @return defaultValue property
     */
    public T getDefaultValue() {

        return defaultValue;
    }

    /**
     * If this DataField is grouped by a GroupDataField (determined by calling {@link #isGroupedDataField()}), then return
     * the {@link FieldKey} of the GroupDataField which groups it. If this field is not grouped by a GroupDataField, return
     * null.
     * 
     * @return the FieldKey for the GroupDataField if this DataField is grouped by a GroupDataField, otherwise null
     */
    public FieldKey<? extends GroupDataField> getGroupKey() {

        if (isGroupedDataField()) {
            return getKey().getGroupKey();
        } else if (isGroupDataField() || isGroupListDataField()) {
            return (FieldKey<GroupDataField>) getKey();
        } else {
            return null;
        }

    }

    /**
     * getLocalizedAbbreviation.
     * @param locale current Locale
     * @return String of the localized abbreviation for this data field
     */
    public String getLocalizedAbbreviation(Locale locale) {

        return getKey().getLocalizedAbbreviation(locale);
    }

    /**
     * getLocalizedDescription.
     * @param locale current Locale
     * @return String of the localized description for this data field
     */
    public String getLocalizedDescription(Locale locale) {

        return getKey().getLocalizedDescription(locale);
    }

    /**
     * getLocalizedName.
     * @param locale current Locale
     * @return String of the localized name for this data field
     */
    public String getLocalizedName(Locale locale) {

        return getKey().getLocalizedName(locale);
    }

    /**
     * getLocalizedWidth.
     * @param locale current Locale
     * @return String of the localized width for this data field
     */
    public String getLocalizedWidth(Locale locale) {

        return getKey().getLocalizedWidth(locale);
    }

    /**
     * Get the Validator for this DataField from the FieldKey
     * 
     * @return Validator instance from this DataField's FieldKey
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#getValidatorInstance()
     */
    @Override
    protected AbstractValidator getValidatorInstance() {

        return key.newValidatorInstance();
    }

    /**
     * getValue.
     * @return value property
     */
    public T getValue() {

        return value;
    }

    /**
     * isBooleanSimpleDataField.
     * @return true if this is a DataField<Boolean>
     */
    public boolean isBooleanSimpleDataField() {

        return key.isBooleanSimpleDataField();
    }

    /**
     * True if this FieldKey represents a data field which is editable at Local and National.
     * 
     * @return boolean true if FieldEnvironment#BOTH is equal to this FieldKey's field environment.
     */
    public boolean isBothLocalAndNationalDataField() {

        return key.isBothLocalAndNationalDataField();
    }

    /**
     * True if this FieldKey represents a generic Data Field. This classification is a "catch all" for any FieldKey that is
     * not a VA Data Field or a Linked Data Field.
     * 
     * @return boolean true if FieldType#DATA_FIELD is equal to this FieldKey's field type.
     */
    public boolean isDataField() {

        return key.isDataField();
    }

    /**
     * isDateSimpleDataField.
     * @return true if this is a DataField<Date>
     */
    public boolean isDateSimpleDataField() {

        return key.isDateSimpleDataField();
    }

    /**
     * isDefault.
     * @return true if the default value is equal to the value
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.datafield.DataField#isDefault()
     */
    public boolean isDefault() {

        if (defaultValue == null) {
            return value == null;
        } else {
            return defaultValue.equals(value);
        }
    }

    /**
     * isDefault.
     * @param testValue T value to test if it is equal to the default value
     * @return true if the default value is equal to the given test value
     */
    public boolean isDefault(T testValue) {

        if (defaultValue == null) {
            return testValue == null;
        } else {
            return defaultValue.equals(testValue);
        }
    }

    /**
     * isDoubleListDataField.
     * @return true if this is a ListDataField<Double>
     */
    public boolean isDoubleListDataField() {

        return key.isDoubleListDataField();
    }

    /**
     * isDoubleMultitextDataField.
     * @return true if this is a MultitextDataField<Double>
     */
    public boolean isDoubleMultitextDataField() {

        return key.isDoubleMultitextDataField();
    }

    /**
     * isDoubleSimpleDataField.
     * @return true if this is a DataField<Double>
     */
    public boolean isDoubleSimpleDataField() {

        return key.isDoubleSimpleDataField();
    }

    /**
     * isEditable.
     * @return editable property
     */
    public boolean isEditable() {

        return editable;
    }

    /**
     * isEmpty.
     * @return true if the DataField is not selected
     * 
     * @see #isSelected()
     */
    public boolean isEmpty() {

        return !isSelected();
    }

    /**
     * True if this FieldKey represents a FDB Direct Linked DataField.
     * 
     * @return boolean true if FieldType#FDB_DIRECT_LINKED_DATA_FIELD is equal to this FieldKey's field type.
     */
    public boolean isFdbDirectLinkedDataField() {

        return key.isFdbDirectLinkedDataField();
    }

    /**
     * True if this FieldKey represents a FDB Mapped Linked DataField.
     * 
     * @return boolean true if FieldType#FDB_MAPPED_LINKED_DATA_FIELD is equal to this FieldKey's field type.
     */
    public boolean isFdbMappedLinkedDataField() {

        return key.isFdbMappedLinkedDataField();
    }

    /**
     * True if this FieldKey represents a GroupDataField.
     * 
     * @return true if this is a GroupDataField
     */
    public boolean isGroupDataField() {

        return key.isGroupDataField();
    }

    /**
     * True if this FieldKey represents a DataField grouped by a GroupDataField.
     * 
     * @return true if this is a DataField grouped by a GroupDataField
     */
    public boolean isGroupedDataField() {

        return key.isGroupedDataField();
    }

    /**
     * True if this FieldKey represents a GroupListDataField.
     * 
     * @return true if this is a GroupListDataField
     */
    public boolean isGroupListDataField() {

        return key.isGroupListDataField();
    }

    /**
     * True if this FieldKey represents a Linked DataField.
     * 
     * @return boolean true if FieldType#FDB_DIRECT_LINKED_DATA_FIELD, FieldType#FDB_MAPPED_LINKED_DATA_FIELD, or
     *         FieldType#VISTA_LINKED_DATA_FIELD is equal to this FieldKey's field type.
     */
    public boolean isLinkedDataField() {

        return key.isLinkedDataField();
    }

    /**
     * isPrimaryKeyDataField.
     * @return true if this is a PrimaryKeytDataField
     */
    public boolean isPrimaryKeyDataField() {

        return key.isPrimaryKeyDataField();
    }

    /**
     * isListDataField.
     * @return true if this is a ListDataField
     */
    public boolean isListDataField() {

        return key.isListDataField();
    }

    /**
     * True if this FieldKey represents a Local Only data field.
     * 
     * @return boolean true if FieldEnvironment#LOCAL is equal to this FieldKey's field environment.
     */
    public boolean isLocalOnlyDataField() {

        return key.isLocalOnlyDataField();
    }

    /**
     * isLongListDataField.
     * @return true if this is a ListDataField<Long>
     */
    public boolean isLongListDataField() {

        return key.isLongListDataField();
    }

    /**
     * isLongMultitextDataField.
     * @return true if this is a MultitextDataField<Integer>
     */
    public boolean isLongMultitextDataField() {

        return key.isLongMultitextDataField();
    }

    /**
     * isLongSimpleDataField.
     * @return true if this is a DataField<Long>
     */
    public boolean isLongSimpleDataField() {

        return key.isLongSimpleDataField();
    }

    /**
     * True if this FieldKey represents a MultitextDataField.
     * 
     * @return true if this is a MultitextDataField
     */
    public boolean isMultitextDataField() {

        return key.isMultitextDataField();
    }

    /**
     * True if this FieldKey represents a National data field.
     * 
     * @return boolean true if FieldEnvironment#NATIONAL is equal to this FieldKey's field environment.
     */
    public boolean isNationalDataField() {

        return key.isNationalDataField();
    }

    /**
     * Return true if this FieldKey is stored at the NDC level. This is not mutually exclusive with
     * {@link #isOrderableItemLevelField()} and {@link #isProductLevelField()}, as fields can be stored at multiple levels.
     * 
     * @return boolean
     */
    public boolean isNdcLevelField() {

        return key.isNdcLevelField();
    }

    /**
     * isNumberListDataField.
     * @return true if this is a ListDataField<Number>
     */
    public boolean isNumberListDataField() {

        return key.isNumberListDataField();
    }

    /**
     * isNumberMultitextDataField.
     * @return true if this is a MultitextDataField<Number>
     */
    public boolean isNumberMultitextDataField() {

        return key.isNumberMultitextDataField();
    }

    /**
     * isNumberSimpleDataField.
     * @return true if this is a DataField<Number>
     */
    public boolean isNumberSimpleDataField() {

        return key.isNumberSimpleDataField();
    }

    /**
     * Return true if this FieldKey is stored at the Orderable Item level. This is not mutually exclusive with
     * {@link #isProductLevelField()} and {@link #isNdcLevelField()}, as fields can be stored at multiple levels.
     * 
     * @return boolean
     */
    public boolean isOrderableItemLevelField() {

        return key.isOrderableItemLevelField();
    }

    /**
     * Return true if this FieldKey is stored at the Product level. This is not mutually exclusive with
     * {@link #isOrderableItemLevelField()} and {@link #isNdcLevelField()}, as fields can be stored at multiple levels.
     * 
     * @return boolean
     */
    public boolean isProductLevelField() {

        return key.isProductLevelField();
    }

    /**
     * NOTE: This is not a persisted field. It is used by the Presentation to support the edit links and by Service to during
     * the modification summary request process.
     * 
     * @return requestToEdit property
     */
    public boolean isRequestToEdit() {

        return requestToEdit;
    }

    /**
     * isRequiresSecondApproval.
     * @return requiresSecondApproval property
     */
    public boolean isRequiresSecondApproval() {

        return key.isRequiresSecondApproval();
    }

    /**
     * isSelected.
     * @return true if the DataField has a selection or value set
     */
    public boolean isSelected() {

        return value != null;
    }

    /**
     * True if this FieldKey represents a DataField, but not a ListDataField or CompositeDataField.
     * 
     * @see #isListDataFieldType()
     * @see #isCompositeDataFieldType()
     * @return boolean
     */
    public boolean isSimpleDataField() {

        return key.isSimpleDataField();
    }

    /**
     * isStringListDataField.
     * @return true if this is a ListDataField<String>
     */
    public boolean isStringListDataField() {

        return key.isStringListDataField();
    }

    /**
     * isStringMultitextDataField.
     * @return true if this is a MultitextDataField<String>
     */
    public boolean isStringMultitextDataField() {

        return key.isStringMultitextDataField();
    }

    /**
     * isStringSimpleDataField.
     * @return true if this is a DataField<String>
     */
    public boolean isStringSimpleDataField() {

        return key.isStringSimpleDataField();
    }

    /**
     * True if this FieldKey represents a VA Data Field.
     * 
     * @return boolean true if FieldType#VA_DATA_FIELD is equal to this FieldKey's field type.
     */
    public boolean isVaDataField() {

        return key.isVaDataField();
    }

    /**
     * True if this FieldKey represents a VistA Linked DataField.
     * 
     * @return boolean true if FieldType#VISTA_LINKED_DATA_FIELD is equal to this FieldKey's field type.
     */
    public boolean isVistaLinkedDataField() {

        return key.isVistaLinkedDataField();
    }

    /**
     * If the type for this DataField has a constructor which takes a single parameter of type String, that constructor is
     * called and the results are set as the value of this DataField.
     * 
     * If the constructor could not be found or called, a CommonException (Runtime) is thrown.
     * 
     * This method should work for any {@link String}, {@link Number}, or {@link Boolean} DataField as all of these Java
     * classes all have a constructor which takes a single String as a parameter. This method will not work on ListDataFields
     * and CompositeDataFields; these data fields must be handled separately.
     * 
     * @param values String value for this DataField
     */
    public void selectStringValue(String values) {

        selectValue((T) ReflectionUtility.convertStringValue(getKey(), values));
    }

    /**
     * Select the default value as this DataField's current value.
     * 
     * @see #getDefaultValue()
     * @see #selectValue(Object)
     */
    public void selectDefaultValue() {

        selectValue(getDefaultValue());
    }

    /**
     * Set the current value to the given value and set the selected property to true.
     * 
     * @param values new current value
     */
    public void selectValue(T values) {

        setValue(values);
    }

    /**
     * setDataFieldId.
     * @param dataFieldId dataFieldId property
     */
    public void setDataFieldId(Long dataFieldId) {

        this.dataFieldId = dataFieldId;
    }

    /**
     * setDefaultValue.
     * @param defaultValue defaultValue property
     */
    public void setDefaultValue(T defaultValue) {

        if (defaultValue instanceof String) {
            this.defaultValue = (T) trimToEmpty((String) defaultValue);
        } else {
            this.defaultValue = defaultValue;
        }
    }

    /**
     * setEditable.
     * @param editable editable property
     */
    public void setEditable(boolean editable) {

        this.editable = editable;
    }

    /**
     * NOTE: This is not a persisted field. It is used by the Presentation to support the edit links and by Service to during
     * the modification summary request process.
     * 
     * @param requestToEdit requestToEdit property
     */
    public void setRequestToEdit(boolean requestToEdit) {

        this.requestToEdit = requestToEdit;
    }

    /**
     * 
     * If the type for this DataField has a constructor which takes a single parameter of type String, that constructor is
     * called and the results are set as the default value of this DataField.
     * 
     * If the constructor could not be found or called, a CommonException (Runtime) is thrown.
     * 
     * This method should work for any {@link String}, {@link Number}, or {@link Boolean} DataField as all of these Java
     * classes all have a constructor which takes a single String as a parameter. This method will not work on ListDataFields
     * and CompositeDataFields; these data fields must be handled separately.
     * 
     * @param defaultValue1 String value for this DataField
     */
    public void setStringDefaultValue(String defaultValue1) {

        setDefaultValue((T) ReflectionUtility.convertStringValue(getKey(), defaultValue1));
    }

    /**
     * setValue.
     * @param value value property
     */
    protected void setValue(T value) {

        if (value instanceof String) {
            this.value = (T) trimToEmpty((String) value);
        } else {
            this.value = value;
        }
    }

    /**
     * toShortString.
     * @return short string value
     */
    @Override
    public String toShortString() {

        String shortString = null;

        if (value instanceof Number) {
            shortString = NumberFormatUtility.format((Number) value);
        } else {
            shortString = value == null ? null : value.toString();
        }

        return shortString;
    }

    /**
     * Set the current value to false and the selected property to false.
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.datafield.DataField#unselect()
     */
    public void unselect() {

        setValue(null);
    }
}
