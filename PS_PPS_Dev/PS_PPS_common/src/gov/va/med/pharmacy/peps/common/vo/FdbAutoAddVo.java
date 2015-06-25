/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing an Ingredient
 */
public class FdbAutoAddVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private String ndc;
    private Double packageSize;
    private String packageType;
    private String labelName;
    private Long gcnSeqno;
    private String vaProductName;
    private String fdbProductName;
    private String addDesc;
    private Long ndcIdFk;
    private String addDate;

    /**
     * the constructor
     */
    public FdbAutoAddVo() {

        super();
    }

    /**
     * getNdc.
     * @return ndc
     */
    public String getNdc() {
    
        return ndc;
    }

    /**
     * setLabelName for FdbAutoAddVo
     * @param labelName labelName
     */
    public void setLabelName(String labelName) {
    
        this.labelName = labelName;
    }
    
    /**
     * getLabelName for FdbAutoAddVo
     * @return labelName
     */
    public String getLabelName() {
    
        return labelName;
    }

    /**
     * setNdc for FdbAutoAddVo
     * @param ndc ndc
     */
    public void setNdc(String ndc) {
    
        this.ndc = ndc;
    }
    
    /**
     * getAddDate.
     * @return addDate
     */
    public String getAddDate() {
    
        return addDate;
    }

    /**
     * setAddDate.
     * @param addDate addDate
     */
    public void setAddDate(String addDate) {
    
        this.addDate = addDate;
    }

    /**
     * getPackageSize.
     * @return packageSize packageSize
     */
    public Double getPackageSize() {
    
        return packageSize;
    }

    /**
     * setPackageSize.
     * @param packageSize packageSize
     */
    public void setPackageSize(Double packageSize) {
    
        this.packageSize = packageSize;
    }

    /**
     * getPackageType for the FdbAutoAddVo.
     * @return packageType
     */
    public String getPackageType() {
    
        return packageType;
    }


    /**
     * setPackageType.
     * @param packageType packageType
     */
    public void setPackageType(String packageType) {
    
        this.packageType = packageType;
    }

    /**
     * getGcnSeqno.
     * @return gcnSeqno
     */
    public Long getGcnSeqno() {
    
        return gcnSeqno;
    }

    /**
     * setGcnSeqno.
     * @param gcnSeqno gcnSeqno
     */
    public void setGcnSeqno(Long gcnSeqno) {
    
        if (gcnSeqno != null) {
            this.setGcnSequenceNumber(String.valueOf(gcnSeqno));
        }
        
        this.gcnSeqno = gcnSeqno;
    }

    /**
     * getAddDesc.
     * @return addDesc
     */
    public String getAddDesc() {
        return addDesc;
    }

    /**
     * setAddDesc.
     * @param addDesc addDesc
     */
    public void setAddDesc(String addDesc) {
        this.addDesc = addDesc;
    }


    /**
     * getVaProductName.
     * @return vaProductName
     */
    public String getVaProductName() {
    
        return vaProductName;
    }

   /**
    * setVaProductName.
    * @param vaProductName vaProductName
    */
    public void setVaProductName(String vaProductName) {
    
        this.vaProductName = vaProductName;
    }

    /**
     * getFdbProductName for FdbAutoAddVo.
     * @return fdbProductName
     */
    public String getFdbProductName() {
    
        return fdbProductName;
    }

    /**
     * setFdbProductName for FdbAutoAddVo.
     * @param fdbProductName fdbProductName
     */
    public void setFdbProductName(String fdbProductName) {
    
        this.fdbProductName = fdbProductName;
    }

    /**
     * getNdcIdFk for FdbAutoAddVo.
     * @return ndcIdFk
     */
    public Long getNdcIdFk() {
    
        return ndcIdFk;
    }

    /**
     * setNdcIdFk.
     * @param ndcIdFk ndcIdFk
     */ 
    public void setNdcIdFk(Long ndcIdFk) {
    
        this.ndcIdFk = ndcIdFk;
    }

    /**
     * getDomainGroup.
     *      * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {

        return DomainGroup.GROUP_1;
    }

    /**
     * Returns true if the domain is standardized for the FdbAutoAddVo.
     * 
     * @return boolean
     */
    public boolean isStandardized() {

        return true;
    }

    /**
     * Returns true if this is a local only domain for the FdbAutoAddVo.
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for the FdbAutoAddVo.
     * 
     * @return boolean
     */
    public boolean isNdf() {

        return true;
    }
}
