/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.Set;


/**
 * Data representing a strength's unit of measure.
 */
public class DrugUnitVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;
    
    //Attributes added during migration
    private String drugUnitIen;

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    public EntityType getEntityType() {
        return EntityType.DRUG_UNIT;
    }

    /**
     * Returns the VA drug Unit IEN
     * 
     * @return drug Unit IEN
     */
    public String getDrugUnitIen() {
        return drugUnitIen;
    }

    /**
     * Set the VA IEN
     * @param drugUnitIen drugUnitIen
     * 
     */
    public void setDrugUnitIen(String drugUnitIen) {
        this.drugUnitIen = drugUnitIen;
    }

    /**
     * Returns true if the domain is standardized.
     * 
     * @return boolean
     */
    public boolean isStandardized() {
        return false;
    }

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {
        return DomainGroup.GROUP_2;
    }

    /**
     * Returns true if isLocalOnlyData for DrugUnitVo.
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for DrugUnitVo.
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return true;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one for DrugUnitVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        // add ItemStatsu at local for DrugUnitVo.
        if (Environment.LOCAL.equals(environment)) {
            fields.add(FieldKey.ITEM_STATUS);
        }

        // add RequestItemStatus at local for DrugUnitVo.
        if (!getRequestItemStatus().isPending()) {
            fields.add(FieldKey.VALUE);
        }

        return fields;
    }

    /**
     * List all second review fields for this DrugUnitVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} for the current user
     * @return Set<FieldKey> all second review fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListSecondReviewFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListSecondReviewFields(environment, roles);
        
        // for DrugUnitVo.
        fields.add(FieldKey.ITEM_STATUS);
        fields.add(FieldKey.INACTIVATION_DATE);

        return fields;
    }
}
