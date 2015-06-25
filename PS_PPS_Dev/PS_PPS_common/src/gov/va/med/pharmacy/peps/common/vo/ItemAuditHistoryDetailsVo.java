/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Locale;

import org.apache.commons.lang.StringUtils;



/**
 * Data representing sub data for a specific item audit history event, usually consists of a specific field level change
 */
public class ItemAuditHistoryDetailsVo extends ValueObject {

    private static final long serialVersionUID = 1L;

    private String id;

    private String oldValue;
    private String newValue;
    private String columnName;
    private String detailReason;
    private EventCategory eventCategory;

    /**
     * Default constructor
     */
    public ItemAuditHistoryDetailsVo() {
        super();
    }

    /**
     * Creates an ItemAuditHistoryVo with modification details.
     * 
     * @param managedItemVo The managed item that has been modified
     * @param modDiffVo The attribute that has changed for the item
     * @param siteName site where item is being modified
     * @param eventCategory type of event occurring
     * @param username user creating IAH record
     */
    public ItemAuditHistoryDetailsVo(ManagedItemVo managedItemVo, ModDifferenceVo modDiffVo, String siteName,
                                     EventCategory eventCategory, String username) {
        columnName = trimToEmpty(modDiffVo.getDifference().getFieldKey().getKey());
        newValue = trimToEmpty(modDiffVo.getDifference().getNewValueShortString());
        oldValue = trimToEmpty(modDiffVo.getDifference().getOldValueShortString());
        detailReason = StringUtils.trimToEmpty(modDiffVo.getModificationReason());
        this.eventCategory = eventCategory;

    }

    /**
     * Creates and ItemAuditHistoryVo with all the argument values passed in.
     * 
     * @param category the EventCategory for the IAH
     * @param reason reason for the modification
     * @param columnName name of the field that is changing
     * @param oldValue previous value for the field
     * @param newValue new value for the field
     */
    public ItemAuditHistoryDetailsVo(EventCategory category, String reason, String columnName, String oldValue,
                                     String newValue) {
        this.columnName = columnName;
        this.newValue = newValue;
        this.oldValue = oldValue;
        this.detailReason = reason;
        this.eventCategory = category;
    }

    /**
     * getId.
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * set id
     * 
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get column name
     * 
     * @return columnName property
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * 
     * Method to get the IAH page up and running quickly, can be removed
     * 
     * @return columnName property
     */
    public String getFieldName() {
        return columnName;
    }

    /**
     * setColumnName.
     * @param columnName columnName property
     */
    public void setColumnName(String columnName) {
        this.columnName = trimToEmpty(columnName);
    }

    /**
     * getOldValue.
     * @return oldValue property
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * setOldValue.
     * @param oldValue oldValue property
     */
    public void setOldValue(String oldValue) {
        this.oldValue = trimToEmpty(oldValue);
    }

    /**
     * getNewValue.
     * @return newValue property
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * setNewValue.
     * @param newValue newValue property
     */
    public void setNewValue(String newValue) {
        this.newValue = trimToEmpty(newValue);
    }

    /**
     * getDetailReason.
     * @return detailReason property
     */
    public String getDetailReason() {
        return detailReason;
    }

    /**
     * setDetailReason.
     * @param detailReason detailReason property
     */
    public void setDetailReason(String detailReason) {

        this.detailReason = StringUtils.trimToEmpty(detailReason);
    }

    /**
     * getEventCategory.
     * @return eventCategory property
     */
    public EventCategory getEventCategory() {
        return eventCategory;
    }

    /**
     * setEventCategory.
     * @param eventCategory eventCategory property
     */
    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    /**
     * Converts this detail into an IAH entry for display
     * 
     * @return an IAH entry for the same data as the detail
     */
    public ItemAuditHistoryVo convertToItemAuditHistory() {
        ItemAuditHistoryVo iah = new ItemAuditHistoryVo();

        // This should never be saved to the database, and only handled locally, so we'll localize this
        FieldKey fieldKey = FieldKey.getKey(getColumnName());


        if (fieldKey == null) {
            iah.setFieldName(getColumnName());
        } else {
            iah.setFieldName(fieldKey.getLocalizedName(Locale.getDefault()));
        }

        iah.setNewValue(getNewValue());
        iah.setOldValue(getOldValue());
        iah.setDetailCategory(getEventCategory());
        iah.setReason(getDetailReason());

        return iah;
    }
}
