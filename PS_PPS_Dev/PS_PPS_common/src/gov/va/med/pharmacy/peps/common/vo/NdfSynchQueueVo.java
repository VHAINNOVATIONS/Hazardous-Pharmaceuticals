/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing the Migration variables.
 */
public class NdfSynchQueueVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;
 
    private String idFk;
    private String itemType;
    private String actionType;
    
    /**
     * getIdFk
     * @return idFk
     */
    public String getIdFk() {
        return idFk;
    }

    /**
     * setIdFk
     * @param idFk idFk
     */
    public void setIdFk(String idFk) {
        this.idFk = idFk;
    }
    
    /** 
     * getItemType
     * @return itemType
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * setItemType
     * @param itemType itemType
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    
    /** 
     * getActionType
     * @return actionType
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * setActionType
     * @param actionType actionType
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    public EntityType getEntityType() {

        return EntityType.NDF_SYNCH_QUEUE;
    }

    @Override
    public boolean isStandardized() {

        return false;
    }

    @Override
    public boolean isNdf() {
        return false;
    }

    @Override
    public DomainGroup getDomainGroup() {
        return null;
    }

    @Override
    public boolean isLocalOnlyData() {
        return false;
    }

}
