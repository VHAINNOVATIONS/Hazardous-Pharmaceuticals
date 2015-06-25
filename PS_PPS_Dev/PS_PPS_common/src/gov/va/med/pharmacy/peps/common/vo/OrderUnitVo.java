/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.Set;


/**
 * Data representing an Order Unit Domain
 */
public class OrderUnitVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private String expansion;

    /**
     * getAbbrev
     * 
     * @return abbrev property
     */
    public String getAbbrev() {
        return getValue();
    }

    /**
     * setAbbrev
     * 
     * @param abbrev abbrev property
     */
    public void setAbbrev(String abbrev) {
        setValue(abbrev);
    }

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.ORDER_UNIT;
    }

    /**
     * returns the domain group of the managed Data for the OrderUnitVo.
     * 
     * @return domainGroup
     */
    @Override
    public DomainGroup getDomainGroup() {
        return DomainGroup.GROUP_3;
    }

    /**
     * Returns true if the domain is standardized for the OrderUnitVo.
     * 
     * @return boolean
     */
    @Override
    public boolean isStandardized() {
        return false;
    }

    /**
     * getExpansion
     * 
     * @return expansion property
     */
    public String getExpansion() {
        return expansion;
    }

    /**
     * setExpansion
     * 
     * @param expansion expansion property
     */
    public void setExpansion(String expansion) {
        this.expansion = trimToEmpty(expansion);
    }

    /**
     * Returns true if this is a local only domain for the OrderUnitVo.
     * 
     * @return boolean
     */
    @Override
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for the OrderUnitVo.
     * 
     * @return boolean
     */
    @Override
    public boolean isNdf() {
        return false;
    }

    /**
     * Returns if this item requires two reviews before adds/modifies are approved or rejected for the OrderUnitVo
     * <p>
     * Differs from {@link FieldKey#isRequiresSecondApproval()} as this method is used during adds and the FieldKey method is
     * used during mods. In addition, this method only applies to {@link ManagedItemVo}, not individual fields.
     * 
     * @return boolean True if this OrderUnitVo requires two reviews, otherwise, only one review is needed.
     */
    @Override
    public boolean isTwoReviewItem() {
        return false;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one for the OrderUnitVo
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        // add ItemStatus field to the OrderUnitVo
        fields.add(FieldKey.ITEM_STATUS);

        if (isNewInstance()) {
            fields.remove(FieldKey.VALUE);
        }

        return fields;
    }
}
