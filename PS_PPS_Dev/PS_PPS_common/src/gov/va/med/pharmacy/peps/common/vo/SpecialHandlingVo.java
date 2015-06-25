/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.Set;


/**
 * Data representing an Order Unit Domain
 */
public class SpecialHandlingVo extends ManagedDataVo {

    private static final long serialVersionUID = 1L;

    private String description;

    /**
     * Description
     * 
     * @return abbrev property
     */
    public String getCode() {

        return getValue();
    }

    /**
     * Description
     * 
     * @param code abbrev property
     */
    public void setCode(String code) {

        setValue(code);
    }

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    @Override
    public EntityType getEntityType() {

        return EntityType.SPECIAL_HANDLING;
    }

    /**
     * returns the domain group of the managed Data for SpecialHandlingVo.
     * 
     * @return domainGroup
     */
    @Override
    public DomainGroup getDomainGroup() {

        return DomainGroup.GROUP_3;
    }

    /**
     * Returns true if the domain is standardized for SpecialHandlingVo.
     * 
     * @return boolean
     */
    @Override
    public boolean isStandardized() {

        return false;
    }

    /**
     * getDescription for SpecialHandlingVo.
     * 
     * @return expansion property
     */
    public String getDescription() {

        return description;
    }

    /**
     * Description
     * 
     * @param description expansion property
     */
    public void setDescription(String description) {

        this.description = trimToEmpty(description);
    }

    /**
     * Returns true if this is a local only domain for the SpecialHandlingVo.
     * 
     * @return boolean
     */
    @Override
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for the SpecialHandlingVo.
     * 
     * @return boolean
     */
    @Override
    public boolean isNdf() {

        return false;
    }

    /**
     * This is the SpecialHandlingVo method to return if this item requires two reviews 
     * before adds/modifies are approved or rejected  for the SpecialHandlingVo.
     * <p>
     * Differs from {@link FieldKey#isRequiresSecondApproval()} as this method is used during adds and the FieldKey method is
     * used during mods. In addition, this method only applies to the SpecialHandlingVo, not individual fields.
     * 
     * @return boolean True if this item requires two reviews, otherwise, only one review is needed.
     */
    @Override
    public boolean isTwoReviewItem() {

        return false;
    }

    /**
     * This is the SpecialHandlingVo method to list disabled fields for 
     * this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one.
     * 
     * @param environment the current Environment where the SpecialHandlingVo is being set.
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        fields.add(FieldKey.ITEM_STATUS);

        // this is for the SpecialHandlingVo 
        if (!getRequestItemStatus().isPending()) {
            fields.add(FieldKey.VALUE);
        }

        return fields;
    }

    /**
     * List all required fields for SpecialHandlingVo, with the pre-condition that the current instance is not a read-only
     * one.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.handleListRequiredFields(environment, roles);

        fields.add(FieldKey.DESCRIPTION);

        return fields;
    }

    /**
     * Return a Set of field names that should be ignored by the {@link #equals(Object)} and {@link #hashCode()} methods.
     * <p>
     * Default implementation returns a Set of all Fields annotated with {@link IgnoreEquals}. Adds the ID and data fields
     * attributes.
     * 
     * @return Set of field names to be ignored by {@link #equals(Object)} and {@link #hashCode()}
     */
    @Override
    protected Set<String> getIgnoreEqualsFields() {

        Set<String> ignoredFields = super.getIgnoreEqualsFields();
        ignoredFields.add("id");
        ignoredFields.add("dataFields");

        return ignoredFields;
    }

    /**
     * Concatenate the Code and Description properties, separated by a hyphen.
     * 
     * @return short string value
     */
    @Override
    public String toShortString() {

        final int extraBuffer = 3;
        StringBuffer shortString = new StringBuffer(getCode().length() + getDescription().length() + extraBuffer);
        shortString.append(getCode());
        shortString.append(" - ");
        shortString.append(getDescription());

        return shortString.toString();
    }
}
