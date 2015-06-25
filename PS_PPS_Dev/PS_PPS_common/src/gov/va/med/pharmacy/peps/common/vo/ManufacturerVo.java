/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.Set;


/**
 * Data representing a NDC's manufacturer
 */
public class ManufacturerVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private String phone;
    private String manufacturerIen;
    private String address;

    /**
     * return the phone property
     * 
     * @return phone property
     */
    public String getPhone() {
        return phone;
    }

    /**
     * set the phone property
     * 
     * @param phone phone property
     */
    public void setPhone(String phone) {
        this.phone = trimToEmpty(phone);
    }

    /**
     * return the manufacturerIen property.
     * 
     * @return manufacturerIen property
     */
    public String getManufacturerIen() {
        return manufacturerIen;
    }

    /**
     * set the manufacturerIen property.
     * 
     * @param manufacturerIen address property
     */
    public void setManufacturerIen(String manufacturerIen) {
        this.manufacturerIen = trimToEmpty(manufacturerIen);
    }

    
    /**
     * return the address property
     * 
     * @return address property
     */
    public String getAddress() {
        return address;
    }

    /**
     * set the address property
     * 
     * @param address address property
     */
    public void setAddress(String address) {
        this.address = trimToEmpty(address);
    }

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    @Override
    public EntityType getEntityType() {

        return EntityType.MANUFACTURER;
    }

    /**
     * Returns true if the domain is standardized
     * 
     * @return boolean
     */
    @Override
    public boolean isStandardized() {
        return false;
    }

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    @Override
    public DomainGroup getDomainGroup() {
        return DomainGroup.GROUP_3;
    }

    /**
     * Returns true if this is a local only domain
     * 
     * @return boolean
     */
    @Override
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * Returns true if the domain is an NDF domain
     * 
     * @return boolean
     */
    @Override
    public boolean isNdf() {
        return true;
    }

    /**
     * Returns if this item requires two reviews before adds/modifies are approved or rejected.
     * <p>
     * Differs from {@link FieldKey#isRequiresSecondApproval()} as this method is used during adds and the FieldKey method is
     * used during mods. In addition, this method only applies to {@link ManagedItemVo}, not individual fields.
     * 
     * @return boolean True if this item requires two reviews, otherwise, only one review is needed.
     */
    @Override
    public boolean isTwoReviewItem() {
        return false;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        if (Environment.LOCAL.equals(environment)) {
            fields.add(FieldKey.ITEM_STATUS);
        }

        
        if (isNewInstance()) {
            fields.remove(FieldKey.VALUE);
        }

        return fields;

    }

}
