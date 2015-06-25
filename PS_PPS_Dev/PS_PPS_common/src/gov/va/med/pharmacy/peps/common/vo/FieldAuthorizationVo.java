/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * ValueObject representing fields that are disabled/enabled and (non) editable.
 */
public class FieldAuthorizationVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Set<FieldKey> disabled = new HashSet<FieldKey>();
    private Set<FieldKey> nonEditable = new HashSet<FieldKey>();
    private Set<FieldKey> required = new HashSet<FieldKey>();
    private Set<FieldKey> secondReview = new HashSet<FieldKey>();
    private Set<FieldKey> disabledAddMultipleDataFields = new HashSet<FieldKey>();
    private Set<FieldKey> disabledModifyMultipleDataFields = new HashSet<FieldKey>();
    private Set<FieldKey> disabledRemoveMultipleDataFields = new HashSet<FieldKey>();

    private boolean newInstance;

    
    /**
     * Creates a default FieldAuthorizationVo instance, with no required or disabled fields.
     */
    public FieldAuthorizationVo() {
        super();
    }
    
    /**
     * Creates a default FieldAuthorizationVo instance, with no required or disabled fields.
     * 
     * @param disabledFields Set of disabled fields.
     * @param nonEditableFields Set of non-editable fields.
     * @param requiredFields Set of required fields.
     * @param secondReviewFields Set of second review fields.
     * @param disabledAddMultipleDataFields Set of Multiple Data Fields whose Add functionality should be disabled
     * @param disabledModifyMultipleDataFields Set of Multiple Data Fields whose Modify functionality should be disabled
     * @param disabledRemoveMultipleDataFields Set of Multiple Data Fields whose Remove functionality should be disabled
     * @param newInstance True if the item authorized is a new instance, which can affect other authorization rules.
     */
    public FieldAuthorizationVo(Set<FieldKey> disabledFields, Set<FieldKey> nonEditableFields, Set<FieldKey> requiredFields,
        Set<FieldKey> secondReviewFields, Set<FieldKey> disabledAddMultipleDataFields,
        Set<FieldKey> disabledModifyMultipleDataFields, Set<FieldKey> disabledRemoveMultipleDataFields, boolean newInstance) {

        disabled.addAll(disabledFields);
        nonEditable.addAll(nonEditableFields);
        required.addAll(requiredFields);
        secondReview.addAll(secondReviewFields);
        this.disabledAddMultipleDataFields.addAll(disabledAddMultipleDataFields);
        this.disabledModifyMultipleDataFields.addAll(disabledModifyMultipleDataFields);
        this.disabledRemoveMultipleDataFields.addAll(disabledRemoveMultipleDataFields);

        this.newInstance = newInstance;
    }
    
    /**
     * Merge two FieldAuthorizationVo objects into one by adding all disabled, non-editable, and required fields from both.
     * <p>
     * The merged VO takes the newInstance value from the first parameter.
     * 
     * @param one FieldAuthorizationVo
     * @param two FieldAuthorizationVo
     * @return FieldAuthorizationVo
     */
    public static FieldAuthorizationVo merge(FieldAuthorizationVo one, FieldAuthorizationVo two) {
        FieldAuthorizationVo fieldAuthorization = new FieldAuthorizationVo();

        fieldAuthorization.addDisabled(one.getDisabled());
        fieldAuthorization.addDisabled(two.getDisabled());

        fieldAuthorization.addNonEditable(one.getNonEditable());
        fieldAuthorization.addNonEditable(two.getNonEditable());

        fieldAuthorization.addRequired(one.getRequired());
        fieldAuthorization.addRequired(two.getRequired());
        
        fieldAuthorization.addSecondReview(one.getSecondReview());
        fieldAuthorization.addSecondReview(two.getSecondReview());

        fieldAuthorization.addDisabledAddMultipleDataFields(one.getDisabledAddMultipleDataFields());
        fieldAuthorization.addDisabledAddMultipleDataFields(two.getDisabledAddMultipleDataFields());

        fieldAuthorization.addDisabledModifyMultipleDataFields(one.getDisabledModifyMultipleDataFields());
        fieldAuthorization.addDisabledModifyMultipleDataFields(two.getDisabledModifyMultipleDataFields());

        fieldAuthorization.addDisabledRemoveMultipleDataFields(one.getDisabledRemoveMultipleDataFields());
        fieldAuthorization.addDisabledRemoveMultipleDataFields(two.getDisabledRemoveMultipleDataFields());

        fieldAuthorization.setNewInstance(one.isNewInstance());

        return fieldAuthorization;
    }





    /**
     * Add all of the given FieldKeys to the set of disabled FieldKeys
     * 
     * @param keys Set of FieldKey to add
     */
    public void addDisabled(Set<FieldKey> keys) {
        disabled.addAll(keys);
    }

    /**
     * Add a FieldKey to the set of disabled FieldKeys
     * 
     * @param key FieldKey to add
     */
    public void addDisabled(FieldKey key) {
        disabled.add(key);
    }

    /**
     * Add a the FieldKey represented by the given String key value to the set of disabled FieldKeys
     * 
     * @param fieldKey String value of the FieldKey's key to add
     */
    public void addDisabled(String fieldKey) {
        addDisabled(FieldKey.getKey(fieldKey));
    }

    /**
     * Add all of the given FieldKeys to the set of non-editable FieldKeys
     * 
     * @param keys Set of FieldKey to add
     */
    public void addNonEditable(Set<FieldKey> keys) {
        nonEditable.addAll(keys);
    }

    /**
     * Add a FieldKey to the set of non-editable FieldKeys
     * 
     * @param key FieldKey to add
     */
    public void addNonEditable(FieldKey key) {
        nonEditable.add(key);
    }

    /**
     * Add a the FieldKey represented by the given String key value to the set of non-editable FieldKeys
     * 
     * @param fieldKey String value of the FieldKey's key to add
     */
    public void addNonEditable(String fieldKey) {
        addNonEditable(FieldKey.getKey(fieldKey));
    }

    /**
     * Add all of the given FieldKeys to the set of required FieldKeys
     * 
     * @param keys Set of FieldKey to add
     */
    public void addRequired(Set<FieldKey> keys) {
        required.addAll(keys);
    }

    /**
     * Add a FieldKey to the set of required FieldKeys
     * 
     * @param key FieldKey to add
     */
    public void addRequired(FieldKey key) {
        required.add(key);
    }

    /**
     * Add a the FieldKey represented by the given String key value to the set of required FieldKeys
     * 
     * @param fieldKey String value of the FieldKey's key to add
     */
    public void addRequired(String fieldKey) {
        addRequired(FieldKey.getKey(fieldKey));
    }
    
    /**
     * Add all of the given FieldKeys to the set of required FieldKeys
     * 
     * @param keys Set of FieldKey to add
     */
    public void addSecondReview(Set<FieldKey> keys) {
        secondReview.addAll(keys);
    }

    /**
     * Add a FieldKey to the set of required FieldKeys
     * 
     * @param key FieldKey to add
     */
    public void addSecondReview(FieldKey key) {
        secondReview.add(key);
    }

    /**
     * Add a the FieldKey represented by the given String key value to the set of required FieldKeys
     * 
     * @param fieldKey String value of the FieldKey's key to add
     */
    public void addSecondReview(String fieldKey) {
        addSecondReview(FieldKey.getKey(fieldKey));
    }

    /**
     * Add all of the given FieldKeys to the set of disabled Add functionality in a Multiple Data Field.
     * 
     * @param keys Set of FieldKey to add
     */
    public void addDisabledAddMultipleDataFields(Set<FieldKey> keys) {
        disabledAddMultipleDataFields.addAll(keys);
    }

    /**
     * Add a FieldKey to the set of disabled Add functionality in a Multiple Data Field.
     * 
     * @param key FieldKey to add
     */
    public void addDisabledAddMultipleDataField(FieldKey key) {
        disabledAddMultipleDataFields.add(key);
    }

    /**
     * Add a the FieldKey represented by the given String key value to the set of disabled Add functionality in a Multiple
     * Data Field.
     * 
     * @param fieldKey String value of the FieldKey's key to add
     */
    public void addDisabledAddMultipleDataField(String fieldKey) {
        addDisabledAddMultipleDataField(FieldKey.getKey(fieldKey));
    }

    /**
     * Add all of the given FieldKeys to the set of disabled Modify functionality in a Multiple Data Field.
     * 
     * @param keys Set of FieldKey to add
     */
    public void addDisabledModifyMultipleDataFields(Set<FieldKey> keys) {
        disabledModifyMultipleDataFields.addAll(keys);
    }

    /**
     * Add a FieldKey to the set of disabled Modify functionality in a Multiple Data Field.
     * 
     * @param key FieldKey to add
     */
    public void addDisabledModifyMultipleDataField(FieldKey key) {
        disabledModifyMultipleDataFields.add(key);
    }

    /**
     * Add a the FieldKey represented by the given String key value to the set of disabled Modify functionality in a Multiple
     * Data Field.
     * 
     * @param fieldKey String value of the FieldKey's key to add
     */
    public void addDisabledModifyMultipleDataField(String fieldKey) {
        addDisabledModifyMultipleDataField(FieldKey.getKey(fieldKey));
    }

    /**
     * Add all of the given FieldKeys to the set of disabled Remove functionality in a Multiple Data Field.
     * 
     * @param keys Set of FieldKey to add
     */
    public void addDisabledRemoveMultipleDataFields(Set<FieldKey> keys) {
        disabledRemoveMultipleDataFields.addAll(keys);
    }

    /**
     * Add a FieldKey to the set of disabled Remove functionality in a Multiple Data Field.
     * 
     * @param key FieldKey to add
     */
    public void addDisabledRemoveMultipleDataField(FieldKey key) {
        disabledRemoveMultipleDataFields.add(key);
    }

    /**
     * Add a the FieldKey represented by the given String key value to the set of disabled Remove functionality in a Multiple
     * Data Field.
     * 
     * @param fieldKey String value of the FieldKey's key to add
     */
    public void addDisabledRemoveMultipleDataField(String fieldKey) {
        addDisabledRemoveMultipleDataField(FieldKey.getKey(fieldKey));
    }

    /**
     * Return an unmodifiable Set of disabled FieldKeys
     * 
     * @return disabled property
     */
    public Set<FieldKey> getDisabled() {
        return Collections.unmodifiableSet(disabled);
    }

    /**
     * Return an unmodifiable Set of non-editable FieldKeys
     * 
     * @return nonEditable property
     */
    public Set<FieldKey> getNonEditable() {
        return Collections.unmodifiableSet(nonEditable);
    }

    /**
     * Return an unmodifiable Set of required FieldKeys
     * 
     * @return required property
     */
    public Set<FieldKey> getRequired() {
        return Collections.unmodifiableSet(required);
    }

    /**
     * Return an unmodifiable Set of required FieldKeys
     * 
     * @return required property
     */
    public Set<FieldKey> getSecondReview() {
        return Collections.unmodifiableSet(secondReview);
    }

    /**
     * Return an unmodifiable Set of disabled Add functionality in Multiple Data Fields
     * 
     * @return disabledAddMultipleDataFields property
     */
    public Set<FieldKey> getDisabledAddMultipleDataFields() {
        return Collections.unmodifiableSet(disabledAddMultipleDataFields);
    }
    
    /**
     * Return an unmodifiable Set of disabled Modify functionality in Multiple Data Fields
     * 
     * @return disabledModifyMultipleDataFields property
     */
    public Set<FieldKey> getDisabledModifyMultipleDataFields() {
        return Collections.unmodifiableSet(disabledModifyMultipleDataFields);
    }

    /**
     * Return an unmodifiable Set of disabled Remove functionality in Multiple Data Fields
     * 
     * @return disabledRemoveMultipleDataFields property
     */
    public Set<FieldKey> getDisabledRemoveMultipleDataFields() {
        return Collections.unmodifiableSet(disabledRemoveMultipleDataFields);
    }

    /**
     * isDisabled.
     * @param key FieldKey
     * @return boolean true if the field for the given FieldKey is disabled
     */
    public boolean isDisabled(FieldKey key) {
        return disabled.contains(key);
    }

    /**
     * isDisabled.
     * @param fieldKey String value of FieldKey's key
     * @return true if the field for the given String value of a FieldKey's key is disabled
     */
    public boolean isDisabled(String fieldKey) {
        return isDisabled(FieldKey.getKey(fieldKey));
    }

    /**
     * isEditable.
     * @param key FieldKey
     * @return boolean true if the field for the given FieldKey is editable
     */
    public boolean isEditable(FieldKey key) {
        return !isNonEditable(key);
    }

    /**
     * isEditable.
     * @param fieldKey String value of FieldKey's key
     * @return true if the field for the given String value of a FieldKey's key is editable
     */
    public boolean isEditable(String fieldKey) {
        return isEditable(FieldKey.getKey(fieldKey));
    }

    /**
     * isEnabled.
     * @param key FieldKey
     * @return boolean true if the field for the given FieldKey is enabled
     */
    public boolean isEnabled(FieldKey key) {
        return !isDisabled(key);
    }

    /**
     * isEnabled.
     * @param fieldKey String value of FieldKey's key
     * @return true if the field for the given String value of a FieldKey's key is enabled
     */
    public boolean isEnabled(String fieldKey) {
        return !isDisabled(fieldKey);
    }

    /**
     * Check if the given FieldKey is local only.
     * 
     * @param key FieldKey
     * @return boolean true if the field is local only
     */
    public boolean isLocalOnly(FieldKey key) {
        return key.isLocalOnlyDataField();
    }

    /**
     * Check if the given FieldKey is local only.
     * 
     * @param fieldKey String value of FieldKey's key
     * @return boolean true if the field is local only
     */
    public boolean isLocalOnly(String fieldKey) {
        return isLocalOnly(FieldKey.getKey(fieldKey));
    }

    /**
     * Check if the given FieldKey is a national data field.
     * 
     * @param key FieldKey
     * @return boolean true if the field is a national data fields
     */
    public boolean isNationalDataField(FieldKey key) {
        return key.isNationalDataField();
    }

    /**
     * Check if the given FieldKey is a national data field.
     * 
     * @param fieldKey String value of FieldKey's key
     * @return boolean true if the field is a national data field
     */
    public boolean isNationalDataField(String fieldKey) {
        return isNationalDataField(FieldKey.getKey(fieldKey));
    }

    /**
     * isNonEditable.
     * @param key FieldKey
     * @return boolean true if the field for the given FieldKey is non-editable
     */
    public boolean isNonEditable(FieldKey key) {
        return nonEditable.contains(key);
    }

    /**
     * isNonEditable(String fieldKey.
     * @param fieldKey String value of FieldKey's key
     * @return true if the field for the given String value of a FieldKey's key is non-editable
     */
    public boolean isNonEditable(String fieldKey) {
        return isNonEditable(FieldKey.getKey(fieldKey));
    }

    /**
     * boolean isOptional(FieldKey key).
     * @param key FieldKey
     * @return boolean true if the field for the given FieldKey is not required to have a value
     */
    public boolean isOptional(FieldKey key) {
        return !isRequired(key);
    }

    /**
     *  boolean isOptional(String fieldKey).
     * @param fieldKey String value of FieldKey's key
     * @return boolean true if the field for the given String value of a FieldKey is not required to have a value
     */
    public boolean isOptional(String fieldKey) {
        return !isRequired(fieldKey);
    }

    /**
     * n isRequired(FieldKey key).
     * @param key FieldKey
     * @return boolean true if the field for the given FieldKey is required to have a value
     */
    public boolean isRequired(FieldKey key) {
        return required.contains(key);
    }

    /**
     * isRequired(String fieldKey).
     * @param fieldKey String value of FieldKey's key
     * @return boolean true if the field for the given String value of a FieldKey is required to have a value
     */
    public boolean isRequired(String fieldKey) {
        return isRequired(FieldKey.getKey(fieldKey));
    }

    /**
     * Check if the given FieldKey requires second approval.
     * 
     * @param key FieldKey
     * @return boolean true if the field requires second approval
     */
    public boolean isRequiresSecondApproval(FieldKey key) {
        return secondReview.contains(key);

        
    }

    /**
     * Check if the given FieldKey requires second approval.
     * 
     * @param fieldKey String value of FieldKey's key
     * @return boolean true if the field requires second approval
     */
    public boolean isRequiresSecondApproval(String fieldKey) {
        return isRequiresSecondApproval(FieldKey.getKey(fieldKey));
    }

    /**
     * isDisabledAddMultipleDataField(FieldKey key).
     * @param key FieldKey
     * @return boolean true if the field for the given FieldKey is disabled for Add Multiple Data Field functionality
     */
    public boolean isDisabledAddMultipleDataField(FieldKey key) {
        return disabledAddMultipleDataFields.contains(key);
    }

    /**
     * isDisabledAddMultipleDataField(String fieldKey).
     * @param fieldKey String value of FieldKey's key
     * @return boolean true if the field for the given FieldKey is disabled for Add Multiple Data Field functionality
     */
    public boolean isDisabledAddMultipleDataField(String fieldKey) {
        return isDisabledAddMultipleDataField(FieldKey.getKey(fieldKey));
    }

    /**
     * isDisabledModifyMultipleDataField(FieldKey key) .
     * @param key FieldKey
     * @return boolean true if the field for the given FieldKey is disabled for Modify Multiple Data Field functionality
     */
    public boolean isDisabledModifyMultipleDataField(FieldKey key) {
        return disabledModifyMultipleDataFields.contains(key);
    }

    /**
     * isDisabledModifyMultipleDataField.
     * @param fieldKey String value of FieldKey's key
     * @return boolean true if the field for the given FieldKey is disabled for Modify Multiple Data Field functionality
     */
    public boolean isDisabledModifyMultipleDataField(String fieldKey) {
        return isDisabledModifyMultipleDataField(FieldKey.getKey(fieldKey));
    }
    
    /**
     * isDisabledRemoveMultipleDataField.
     * @param key FieldKey
     * @return boolean true if the field for the given FieldKey is disabled for Remove Multiple Data Field functionality
     */
    public boolean isDisabledRemoveMultipleDataField(FieldKey key) {
        return disabledRemoveMultipleDataFields.contains(key);
    }

    /**
     * isDisabledRemoveMultipleDataField.
     * @param fieldKey String value of FieldKey's key
     * @return boolean true if the field for the given FieldKey is disabled for Remove Multiple Data Field functionality
     */
    public boolean isDisabledRemoveMultipleDataField(String fieldKey) {
        return isDisabledRemoveMultipleDataField(FieldKey.getKey(fieldKey));
    }

    /**
     * True if the item authorized is a new instance, which can affect other authorization rules.
     * 
     * @return newInstance property
     */
    public boolean isNewInstance() {
        return newInstance;
    }

    /**
     * True if the item authorized is a new instance, which can affect other authorization rules.
     * 
     * @param newInstance newInstance property
     */
    public void setNewInstance(boolean newInstance) {
        this.newInstance = newInstance;
    }
}
