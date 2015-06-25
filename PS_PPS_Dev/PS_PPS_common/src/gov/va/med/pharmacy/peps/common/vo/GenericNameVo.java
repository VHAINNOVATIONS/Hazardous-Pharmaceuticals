/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Data representing a generic name
 */
public class GenericNameVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private String vuid = "";
    private Set<OrderableItemVo> orderableItems = new HashSet<OrderableItemVo>(0);
    private boolean masterEntryForVuid = true;
    
    //Attributes added during migration
    private List<VuidStatusHistoryVo> effectiveDates;
    private String genericIen;
    private String genericNameStatus = "Active";

    /**
     * Gets the Generic Name IEN
     * 
     * @return IEN
     */
    public String getGenericIen() {
        return genericIen;
    }

    /**
     * Sets the Generic Name IEN
     * @param genericIen genericNameIen
     */
    public void setGenericIen(String genericIen) {
        this.genericIen = genericIen;
    }

    /**
     * getGenericNameStatus
     * @return genericNameStatus
     */
    public String getGenericNameStatus() {
        return genericNameStatus;
    }

    /**
     * setGenericNameStatus
     * @param genericNameStatus genericNameStatus
     */
    public void setGenericNameStatus(String genericNameStatus) {
        this.genericNameStatus = genericNameStatus;
    }

    /**
     * Gets a list of the effective dates and the status on those dates
     * 
     * @return the list of effective date objects
     */
    public List<VuidStatusHistoryVo> getEffectiveDates() {
        return effectiveDates;
    }

    /**
     * Sets a list of the effective dates and the status on those dates
     * @param effectiveDates effectiveDates
     * 
     */
    public void setEffectiveDates(List<VuidStatusHistoryVo> effectiveDates) {
        this.effectiveDates = effectiveDates;
    }

    /**
     * Gets the Master Entry for VUID
     * 
     * @return Master Entry for VUID status
     */
    public boolean getMasterEntryForVuid() {
        return masterEntryForVuid;
    }

    /**
     * Sets the Master VUID for entry status
     * @param masterEntryForVuid masterEntryForVuid
     */
    public void setMasterEntryForVuid(boolean masterEntryForVuid) {
        this.masterEntryForVuid = masterEntryForVuid;
    }
 
    /**
     * getVuid for GenericNameVo.
     * @return vuid property
     */
    public String getVuid() {
        return vuid;
    }

    /**
     * setVuid for GenericNameVo.
     * @param vuid vuid property
     */
    public void setVuid(String vuid) {
        this.vuid = trimToEmpty(vuid);
    }

    /**
     * getOrderableItems.
     * @return orderableItems property
     */
    public Set<OrderableItemVo> getOrderableItems() {
        return orderableItems;
    }

    /**
     * setOrderableItems.
     * @param orderableItems orderableItems property
     */
    public void setOrderableItems(Set<OrderableItemVo> orderableItems) {
        if (orderableItems == null) {
            this.orderableItems = new HashSet<OrderableItemVo>();
        } else {
            this.orderableItems = orderableItems;
        }
    }

    /**
     * getEntityType.
     * @return entity type
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedDataVo#getEntityType()
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.GENERIC_NAME;

    }

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {
        return DomainGroup.GROUP_1;
    }

    /**
     * isStandardized.
     * @return true if domain is standardized
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedDataVo#isStandardized()
     */
    @Override
    public boolean isStandardized() {
        return true;
    }

    /**
     * Returns true if this is a local only domain for GenericNameVo
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * Returns true if the domain is an NDF domain  for GenericNameVo
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return true;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one for GenericNameVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        fields.add(FieldKey.EFFECTIVE_DATES);

        // add itemstatus at Local for GenericNameVo.
        if (Environment.LOCAL.equals(environment)) {
            fields.add(FieldKey.ITEM_STATUS);
        }

        return fields;
    }

    /**
     * List all second review fields for this GenericNameVo
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} for the current user
     * @return Set<FieldKey> all second review fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListSecondReviewFields(Environment environment, Collection<Role> roles) {
        
        // for GenericNameVo
        Set<FieldKey> fields = super.handleListSecondReviewFields(environment, roles);
        fields.add(FieldKey.ITEM_STATUS);
        fields.add(FieldKey.INACTIVATION_DATE);

        return fields;
    }
}
