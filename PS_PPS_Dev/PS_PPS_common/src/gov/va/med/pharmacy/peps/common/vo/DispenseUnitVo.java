/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.Set;


/**
 * Data representing a Dispense Unit
 */
public class DispenseUnitVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;
    
    //Attributes added during migration
    private String dispenseUnitIen;

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    public EntityType getEntityType() {
        return EntityType.DISPENSE_UNIT;
    }

    /**
     * Returns the IEN for Dispense Unit
     * 
     * @return IEN
     */
    public String getDispenseUnitIen() {
        return dispenseUnitIen;
    }

    /**
     * Sets the IEN for Dispense Unit.
     * 
     * @param dispenseUnitIen dispenseUnitIen
     */
    public void setDispenseUnitIen(String dispenseUnitIen) {
        this.dispenseUnitIen = dispenseUnitIen;
    }

    /**
     * getDomainGroup.
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {
        return DomainGroup.GROUP_2;
    }

    /**
     * isStandardized for the DispenseUnitVo.
     * 
     * @return boolean
     */
    public boolean isStandardized() {
        return true;
    }

    /**
     * Returns true if this is a local only domain for the DispenseUnitVo.
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for the DispenseUnitVo.
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return true;
    }

    /**
     * List all required fields for this ValueObject, with the pre-condition that the current instance is not a read-only
     * one for the DispenseUnitVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> requiredFields = super.handleListRequiredFields(environment, roles);

        //    requiredFields.add(FieldKey.DISPENSE_UNIT);

        return requiredFields;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one for the DispenseUnitVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        // add the item status for DispenseUnitVo if at Local
        if (Environment.LOCAL.equals(environment)) {
            fields.add(FieldKey.ITEM_STATUS);
        }

        // Check the RequestItemStatus Value
        if (!getRequestItemStatus().isPending()) {
            fields.add(FieldKey.VALUE);
        }

        return fields;
    }

    /**
     * List all second review fields for this ValueObject for the DispenseUnitVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} for the current user
     * @return Set<FieldKey> all second review fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListSecondReviewFields(Environment environment, Collection<Role> roles) {
        
        // add these fields for DispenseUnitVo.
        Set<FieldKey> fields = super.handleListSecondReviewFields(environment, roles);
        fields.add(FieldKey.ITEM_STATUS);
        fields.add(FieldKey.INACTIVATION_DATE);

        return fields;
    }
}
