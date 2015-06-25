/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.bean;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 
 * FdbUpdateBean
 */
@Component("fdbUpdateBean")
@Scope("prototype")
public class FdbUpdateBean {

    private String[] itemId;
    private String[] itemType;
    private String[] gncSeqNo;
    private String[] message;
    private String[] itemIds;
    private String[] eplId;

    /**
     * getItemId
     * @return the itemId
     */
    public String[] getItemId() {
        return itemId;
    }

    /**
     * setItemId
     * @param itemId the itemId to set
     */
    public void setItemId(String[] itemId) {
        this.itemId = itemId;
    }

    /**
     * getItemType
     * @return the itemType
     */
    public String[] getItemType() {
        return itemType;
    }

    /**
     * setItemType
     * @param itemType the itemType to set
     */
    public void setItemType(String[] itemType) {
        this.itemType = itemType;
    }

    /**
     * getGncSeqNo
     * @return the gncSeqNo
     */
    public String[] getGncSeqNo() {
        return gncSeqNo;
    }

    /**
     * setGncSeqNo
     * @param gncSeqNo the gncSeqNo to set
     */
    public void setGncSeqNo(String[] gncSeqNo) {
        this.gncSeqNo = gncSeqNo;
    }

    /**
     * getMessage
     * @return the message
     */
    public String[] getMessage() {
        return message;
    }

    /**
     * setMessage
     * @param message the message to set
     */
    public void setMessage(String[] message) {
        this.message = message;
    }

    /**
     * getItemIds
     * @return the itemIds
     */
    public String[] getItemIds() {
        return itemIds;
    }

    /**
     * setItemIds
     * @param itemIds the itemIds to set
     */
    public void setItemIds(String[] itemIds) {
        this.itemIds = itemIds;
    }

    /**getEplId
     * @return the eplId
     */
    public String[] getEplId() {
        return eplId;
    }

    /**setEplId
     * @param eplId the eplId to set
     */
    public void setEplId(String[] eplId) {
        this.eplId = eplId;
    }
}
