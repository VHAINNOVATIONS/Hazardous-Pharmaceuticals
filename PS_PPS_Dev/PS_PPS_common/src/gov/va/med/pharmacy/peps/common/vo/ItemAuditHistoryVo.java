/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 * Data representing an Item Audit History entry. Contains data both for specific entry level and overall event level actions
 * 
 */
public class ItemAuditHistoryVo extends ValueObject {

    private static final long serialVersionUID = 1L;

    private String id;
    private EntityType auditItemType;
    private String auditItemId;
    private String fieldName;
    private String siteName;
    private EventCategory eventCategory;
    private EventCategory detailCategory;
    private String reason;
    private String username;
    private Date dtModified;
    private Collection<ItemAuditHistoryDetailsVo> details = new ArrayList<ItemAuditHistoryDetailsVo>();

    private String oldValue;
    private String newValue;

    /**
     * Default constructor
     */
    public ItemAuditHistoryVo() {
        super();
    }

    /**
     * Creates an ItemAuditHistoryVo with modification details but still needs the siteName and eventCategory to be set
     * 
     * @param managedItemVo The managed item that has been modified
     * @param modDiffVo The attribute that has changed for the item
     * @param siteName site where item is being modified
     * @param eventCategory type of event occurring
     * @param username user creating IAH record
     */
    public ItemAuditHistoryVo(ManagedItemVo managedItemVo, ModDifferenceVo modDiffVo, String siteName,
                              EventCategory eventCategory, String username) {

        this.eventCategory = eventCategory;
        this.auditItemType = managedItemVo.getEntityType();
        this.auditItemId = managedItemVo.getId();
        this.siteName = siteName;
        this.username = username;
    }


    /**
     * getId for ItemAuditHistoryVo.
     * 
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * setId for ItemAuditHistoryVo.
     * 
     * 
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * getFieldName for ItemAuditHistoryVo.
     * 
     * @return fieldName property
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * setFieldName
     * 
     * @param fieldName fieldName property
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    /**
     * getAuditItemType
     * 
     * @return itemType property
     */
    public EntityType getAuditItemType() {
        return auditItemType;
    }

    /**
     * setAuditItemType
     * 
     * @param itemType itemType property
     */
    public void setAuditItemType(EntityType itemType) {
        this.auditItemType = itemType;
    }

    /**
     * getSiteName
     * 
     * @return siteName property
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * setSiteName
     * 
     * @param siteName siteName property
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * getEventCategory
     * 
     * @return eventCategory property
     */
    public EventCategory getEventCategory() {
        return eventCategory;
    }

    /**
     * setEventCategory
     * 
     * @param eventCategory eventCategory property
     */
    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    /**
     * getReason
     * 
     * @return reason property
     */
    public String getReason() {
        return reason;
    }

    /**
     * setReason
     * 
     * @param reason reason property
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * getAuditItemId
     * 
     * @return auditItemId property
     */
    public String getAuditItemId() {
        return auditItemId;
    }

    /**
     * setAuditItemId
     * 
     * @param auditItemId auditItemId property
     */
    public void setAuditItemId(String auditItemId) {
        this.auditItemId = auditItemId;
    }

    /**
     * getUsername
     * 
     * @return username property
     */
    public String getUsername() {
        return username;
    }

    /**
     * setUsername
     * 
     * @param username userName property
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getDtModified
     * 
     * @return dtModified property
     */
    public Date getDtModified() {
        return dtModified;
    }

    /**
     * setDtModified
     * 
     * @param dtModified dtModified property
     */
    public void setDtModified(Date dtModified) {
        this.dtModified = dtModified;
    }

    /**
     * getDetails
     * 
     * @return details property
     */
    public Collection<ItemAuditHistoryDetailsVo> getDetails() {
        return details;
    }

    /**
     * setDetails
     * 
     * @param details details property
     */
    public void setDetails(Collection<ItemAuditHistoryDetailsVo> details) {
        this.details = details;
    }

    /**
     * Adds the given detail to the current Item Audit History
     * 
     * @param detail the details of this IAH entry
     */
    public void addDetail(ItemAuditHistoryDetailsVo detail) {
        if (details == null) {
            this.details = new ArrayList<ItemAuditHistoryDetailsVo>();
        }

        details.add(detail);
    }

    /**
     * getOldValue
     * 
     * @return oldValue property
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * setOldValue
     * 
     * @param oldValue oldValue property
     */
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * getNewValue
     * 
     * @return newValue property
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * setNewValue
     * 
     * @param newValue newValue property
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    /**
     * getDetailCategory
     * 
     * @return detailCategory property
     */
    public EventCategory getDetailCategory() {
        return detailCategory;
    }

    /**
     * setDetailCategory
     * 
     * @param detailCategory detailCategory property
     */
    public void setDetailCategory(EventCategory detailCategory) {
        this.detailCategory = detailCategory;
    }

}
