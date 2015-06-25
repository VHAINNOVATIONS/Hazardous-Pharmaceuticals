/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;




/**
 * Data representing an Ingredient
 */
public class FdbUpdateVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private Long eplId;
    private Long gcnSeqno;
    private String ndc;
    private String message;
    private String vaProductName;
    private String fdbProductName;
    private Long ndcIdFk;
    private Long productFk;

    /**
     * the constructor.
     */
    public FdbUpdateVo() {
     
        // call FdbUpdateVo class
        super();
    }

    /**
     * getNdc for FdbUpdateVo.
     * @return ndc
     */
    public String getNdc() {
    
        return ndc;
    }

    /**
     * setNdc for FdbUpdateVo.
     * @param ndc ndc
     */
    public void setNdc(String ndc) {
    
        this.ndc = ndc;
    }

    /**
     * getGcnSeqno for FdbUpdateVo.
     * @return gcnSeqno
     */
    public Long getGcnSeqno() {
    
        return gcnSeqno;
    }

    /**
     * setGcnSeqno for FdbUpdateVo.
     * @param gcnSeqno gcnSeqno
     */
    public void setGcnSeqno(Long gcnSeqno) {
        if (gcnSeqno != null) {
            this.setGcnSequenceNumber(String.valueOf(gcnSeqno));
        }
        
        this.gcnSeqno = gcnSeqno;
    }
    
    /**
     * getEplId for FdbUpdateVo.
     * @return eplId
     */
    public Long getEplId() {
    
        return eplId;
    }

    /**
     * setEplId for FdbUpdateVo.
     * @param  eplId  eplId
     */
    public void setEplId(Long eplId) {
    
        this.eplId = eplId;
    }

    /**
     * getMessage.
     * @return message
     */
    public String getMessage() {
    
        return message;
    }

    /**
     * setMessage.
     * @param messageIn message
     */
    public void setMessage(String messageIn) {
    
        this.message = messageIn;
    }


    /**
     * This method is the FDbUpdateVo method to return the VaProductName.
     * @return vaProductName
     */
    public String getVaProductName() {
    
        return vaProductName;
    }

   /**
    * setVaProductName.
    * @param productName productName
    */
    public void setVaProductName(String productName) {
    
        this.vaProductName = productName;
    }

    /**
     * getFdbProductName for the FdbUpdateVo.
     * @return fdbProductName
     */
    public String getFdbProductName() {
    
        return fdbProductName;
    }

    /**
     * setFdbProductName for the FdbUpdateVo.
     * @param productName productName
     */
    public void setFdbProductName(String productName) {
    
        this.fdbProductName = productName;
    }

    /**
     * getNdcIdFk.
     * @return ndcIdFk
     */
    public Long getNdcIdFk() {
    
        return ndcIdFk;
    }

    /**
     * setNdcIdFk.
     * @param ndcId ndcId
     */
    public void setNdcIdFk(Long ndcId) {
    
        this.ndcIdFk = ndcId;
    }

    /**
     * getProductFk.
     * @return productFk
     */
    public Long getProductFk() {
    
        return productFk;
    }

    /**
     * setProductFk.
     * @param product product
     */
    public void setProductFk(Long product) {
    
        this.productFk = product;
    }


 
    /**
     * returns the domain group of the managed Data for this FdbUpdateVo
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {
        return DomainGroup.GROUP_1;
    }

    /**
     * Returns true if the domain is standardized  for this FdbUpdateVo
     * 
     * @return boolean
     */
    public boolean isStandardized() {
        return true;
    }

    /**
     * Returns true if this is a local only domain  for this FdbUpdateVo
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * Returns true if the domain is an NDF domain  for this FdbUpdateVo
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return true;
    }
}
