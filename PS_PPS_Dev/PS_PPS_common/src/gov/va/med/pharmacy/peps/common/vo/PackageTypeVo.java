/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.Set;


/**
 * Data representing a NDC's package type.
 */
public class PackageTypeVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private String packagetypeIen;
    
    /**
     * return the packagetypeIen property.
     * 
     * @return packagetypeIen property
     */
    public String getPackagetypeIen() {
        return packagetypeIen;
    }

    /**
     * set the packagetypeIen property.
     * 
     * @param packagetypeIen address property
     */
    public void setPackagetypeIen(String packagetypeIen) {
        this.packagetypeIen = trimToEmpty(packagetypeIen);
    }
    
    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.PACKAGE_TYPE;
    }

    /**
     * returns the domain group of the managed Data 
     * for the PackageTypeVo.
     * 
     * @return domainGroup
     */
    @Override
    public DomainGroup getDomainGroup() {
        return DomainGroup.GROUP_3;
    }

    /**
     * Returns true if the domain is standardized for PackageTypeVo
     * 
     * @return boolean
     */
    @Override
    public boolean isStandardized() {
        return false;
    }

    /**
     * Returns true if this is a local only domain  for PackageTypeVo
     * 
     * @return boolean
     */
    @Override
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * Returns true if the domain is an NDF domain  for PackageTypeVo
     *  
     * @return boolean
     */
    @Override
    public boolean isNdf() {
        return false;
    }

    /**
     * for PackageTypeVo
     * Returns if this item requires two reviews before adds/modifies are approved or rejected.
     * <p>
     * Differs from {@link FieldKey#isRequiresSecondApproval()} as this method is used during adds and the FieldKey method is
     * used during mods. In addition, this method only applies to {@link ManagedItemVo}, not individual fields.
     * 
     *  PackageTypeVo
     * @return boolean True if this item requires two reviews, otherwise, only one review is needed.
     */
    @Override
    public boolean isTwoReviewItem() {
        return false;
    }

    /**
     *  for PackageTypeVo
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this PackageTypeVo.
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
