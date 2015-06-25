/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;


/**
 * Types available to an DrugText
 */
public class VuidStatusHistoryVo extends ManagedDataVo  {
    private static final long serialVersionUID = 1L;

    private VuidItemType itemType;
    private Long vuid;
    private Date effectiveDateTime;
    private ItemStatus effectiveDtmStatus;

    /**
     * the constructor
     */
    public VuidStatusHistoryVo() {
        itemType = VuidItemType.PRODUCTS;
        effectiveDateTime = null;
        effectiveDtmStatus = ItemStatus.ACTIVE;
    }

    /**
     * Gets the itemType
     * 
     * @return itemType
     */
    public VuidItemType getItemType() {
        return itemType;
    }

    /**
     * Sets the itemType
     * @param itemType itemType
     * 
     */
    public void setItemType(VuidItemType itemType) {
        this.itemType = itemType;
    }

    /**
     * Gets the vuid
     * 
     * @return vuid
     */
    public Long getVuid() {
        return vuid;
    }

    /**
     * Sets the vuid
     * @param vuid vuid
     * 
     * 
     */
    public void setVuid(Long vuid) {
        this.vuid = vuid;
    }

    /**
     * Gets the effective date of the active effectiveDtmStatus
     * 
     * @return effective date
     */
    public Date getEffectiveDateTime() {
        return effectiveDateTime;
    }

    /**
     * Sets the effective date time
     * @param effectiveDateTime effectiveDateTime
     * 
     */
    public void setEffectiveDateTime(Date effectiveDateTime) {
        this.effectiveDateTime = effectiveDateTime;
    }

    /**
     * Gets the state of active
     * 
     * @return effectiveDtmStatus effectiveDtmStatus
     */
    public ItemStatus getEffectiveDtmStatus() {
        return effectiveDtmStatus;
    }

    /**
     * Sets the active effectiveDtmStatus
     * @param effectiveDtmStatus effectiveDtmStatus
     * 
     */
    public void setEffectiveDtmStatus(ItemStatus effectiveDtmStatus) {
        this.effectiveDtmStatus = effectiveDtmStatus;
    }

    /**
     * isStandardized
     * @return false
     */
    @Override
    public boolean isStandardized() {
        return false;
    }

    /**
     * isNnd
     * @return true;
     */
    @Override
    public boolean isNdf() {
        return true;
    }

    /**
     * getDomainGroup
     * @return null
     */
    @Override
    public DomainGroup getDomainGroup() {
        return null;
    }

    /**
     * isLocalOnlyData
     * @return false;
     */
    @Override
    public boolean isLocalOnlyData() {
        return false;
    }
}
