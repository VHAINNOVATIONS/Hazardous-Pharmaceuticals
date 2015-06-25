/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.bean;


import gov.va.med.pharmacy.peps.common.vo.EntityType;


/**
 * MultipleSelectItemBean
 * 
 *
 */
public class MultipleSelectItemBean {

    /** currentIndex */
    private int currentIndex;
    
    /** itemIds */
    private String[] itemIds;
    
    /** requestIds */
    private String[] requestIds;
    
    /** itemEntityTypes */
    private EntityType[] itemEntityTypes;

    /**
     * getItemIds
     * @return itemIds
     */
    public String[] getItemIds() {
        return itemIds;
    }

    /**
     * setItemIds
     * @param itemIds itemIds
     */
    public void setItemIds(String[] itemIds) {
        this.itemIds = itemIds;
    }

    /**
     * getItemEntityTypes
     * @return itemEntityTypes
     */
    public EntityType[] getItemEntityTypes() {
        return itemEntityTypes;
    }

    /**
     * setItemEntityTypes
     * @param itemEntityTypes itemEntityTypes
     */
    public void setItemEntityTypes(EntityType[] itemEntityTypes) {
        this.itemEntityTypes = itemEntityTypes;
    }

    /**
     * getCurrentIndex
     * @return currentIndex
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * setCurrentIndex
     * @param currentIndex currentIndex
     */
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    
    /**
     * getRequestIds
     * @return the requestIds
     */
    public String[] getRequestIds() {
        return requestIds;
    }

    
    /**
     * setRequestIds
     * @param requestIds the requestIds to set
     */
    public void setRequestIds(String[] requestIds) {
        this.requestIds = requestIds;
    }

    
}
