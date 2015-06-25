/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing an Ingredient
 */
public class FdbAutoUpdateVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private Long eplId;
    private String ndc;
    private String itemType;
    private Long gcnSeqno;
    private String message;
    private String vaProductName;
    private String fdbProductName;
    private String formatIndicator;
    private String updateDate;
    private Long ndcIdFk;
    private Long productFk;

    /**
     * the constructor
     */
    public FdbAutoUpdateVo() {
        super();
    }
    
    /**
     * getNdc for FdbAutoUpdateVo.
     * @return ndc
     */
    public String getNdc() {
    
        return ndc;
    }

    /**
     * setNdc for AutoUpdateVo.
     * @param ndc ndc
     */
    public void setNdc(String ndc) {
    
        this.ndc = ndc;
    }
    
    /**
     * getUpdateDate.
     * @return updateDate
     */
    public String getUpdateDate() {
    
        return updateDate;
    }

    /**
     * setUpdateDate.
     * @param updateDate updateDate
     */
    public void setUpdateDate(String updateDate) {
    
        this.updateDate = updateDate;
    }

    
    /**
     * getEplId for FdbAutoUpdateVo.
     * @return eplId
     */
    public Long getEplId() {
    
        return eplId;
    }

    /**
     * setEplId  for FdbAutoUpdateVo.
     * @param  eplId  eplId
     */
    public void setEplId(Long eplId) {
    
        this.eplId = eplId;
    }
    
    /**
     * getItemType for FdbAutoUpdateVo.
     * @return itemType
     */
    public String getItemType() {
    
        return itemType;
    }

    /**
     * setItemType for FdbAutoUpdateVo.
     * @param itemType itemType
     */
    public void setItemType(String itemType) {
    
        this.itemType = itemType;
    }

    /**
     * getGcnSeqno.
     * @return gcnSeqno gcnSeqNo
     */
    public Long getGcnSeqno() {
    
        return gcnSeqno;
    }

    /**
     * setGcnSeqno.
     * @param gcnSeqno gcnSeqNo
     */
    public void setGcnSeqno(Long gcnSeqno) {
        if (gcnSeqno != null) {
            this.setGcnSequenceNumber(String.valueOf(gcnSeqno));
        }
        this.gcnSeqno = gcnSeqno;
    }

    /**
     * getMessage for FdbAutoUpdateVo.
     * @return message
     */
    public String getMessage() {
    
        return message;
    }

    /**
     * setMessage
     * @param message message
     */
    public void setMessage(String message) {
    
        this.message = message;
    }


    /**
     * getVaProductName
     * @return vaProductName vaProductName
     */
    public String getVaProductName() {
    
        return vaProductName;
    }

   /**
    * setVaProductName for FdbAutoUpdateVo.
    * @param vaProductName vaProductName
    */
    public void setVaProductName(String vaProductName) {
    
        this.vaProductName = vaProductName;
    }

    /**
     * getFdbProductName for FdbAutoUpdateVo.
     * @return fdbProductName fdbProductName
     */
    public String getFdbProductName() {
    
        return fdbProductName;
    }

    /**
     * setFdbProductName for FdbAutoUpdateVo..
     * @param fdbProductName fdbProductName
     */
    public void setFdbProductName(String fdbProductName) {
    
        this.fdbProductName = fdbProductName;
    }

    /**
     * getFormatIndicator for FdbAutoUpdateVo.
     * @return formatIndicator formatIndicator
     */
    public String getFormatIndicator() {
    
        return formatIndicator;
    }

    /**
     * setFormatIndicator.
     * @param formatIndicator formatIndicator
     */
    public void setFormatIndicator(String formatIndicator) {
    
        this.formatIndicator = formatIndicator;
    }

    /**
     * getNdcIdFk for FdbAutoUpdateVo.
     * @return ndcIdFk ndcIdFk
     */
    public Long getNdcIdFk() {
    
        return ndcIdFk;
    }

    /**
     * setNdcIdFkfor FdbAutoUpdateVo.
     * @param ndcIdFk ndcIdFk
     */
    public void setNdcIdFk(Long ndcIdFk) {
    
        this.ndcIdFk = ndcIdFk;
    }

    /**
     * getProductFk for FdbAutoUpdateVo.
     * @return productFk productFk
     */
    public Long getProductFk() {
    
        return productFk;
    }

    /**
     * setProductFk for FdbAutoUpdateVo.
     * @param productFk productFk
     */
    public void setProductFk(Long productFk) {
    
        this.productFk = productFk;
    }

    /**
     * Returns true if the domain is standardized for the FdbAutoUpdateVo
     * 
     * @return boolean
     */
    public boolean isStandardized() {
        return true;
    }

    /**
     * Returns true if this is a local only domain for the FdbAutoUpdateVo
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for the FdbAutoUpdateVo
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return true;
    }

    /**
     * Returns the entity for the managed item for the FdbAutoUpdateVo
     * 
     * @return EntityType
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.FDB_AUTO_UPDATE;
    }
    
    /**
     * getDomainGroup for the FdbAutoUpdateVo
     * @return DomainGroup
     */
    @Override
    public DomainGroup getDomainGroup() {
        return null;
    }
}
