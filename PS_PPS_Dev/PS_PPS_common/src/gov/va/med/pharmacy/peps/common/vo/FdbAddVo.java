/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Data representing an Ingredient
 */
public class FdbAddVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private String ndc;
    private String packageSize;
    private String packageType;
    private String manufacturer;
    private String labelName;
    private String addDesc;
    private String tradeName;
    private String fdbProductName;
    private Long gcnSeqno;
    private Date fdbCreationDate;
    private Date obsoleteDate;
    private boolean ndcExist;
    private String obsoleteDateStr;

    private List<ProductVo> productVos = new ArrayList<ProductVo>();

    /**
     * the constructor
     */
    public FdbAddVo() {
        super();
    }

    /**
     * get Ndc
     * @return ndc
     */
    public String getNdc() {
    
        return ndc;
    }
    
    @Override
    public String getId() {
        return ndc;
    }
  
    /**
     * setNdc.
     * @param ndc ndc
     */
    public void setNdc(String ndc) {
    
        this.ndc = ndc;
    }

    /**
     * getPackageSize.
     * @return packageSize
     */
    public String getPackageSize() {
    
        return packageSize;
    }

    /**
     * setPackageSize.
     * @param packageSize packageSize
     */
    public void setPackageSize(String packageSize) {
    
        this.packageSize = packageSize;
    }

    /**
     * getPackageType.
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
     * getManufacturer.
     * @return manufacturer
     */
    public String getManufacturer() {
    
        return manufacturer;
    }

    /**
     * setManufacturer.
     * @param manufacturer manufacturer
     */
    public void setManufacturer(String manufacturer) {
    
        this.manufacturer = manufacturer;
    }

    /**
     * getLabelName.
     * @return labelName
     */
    public String getLabelName() {
    
        return labelName;
    }

    /**
     * setLabelName.
     * @param labelName labelName
     */
    public void setLabelName(String labelName) {
    
        this.labelName = labelName;
    }

    /**
     * getAddDesc.
     * @return addDesc addDesc
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
     * getTradeName.
     * @return tradeName tradeName
     */
    public String getTradeName() {
    
        return tradeName;
    }

    /**
     * setTradeName.
     * @param tradeName tradeName
     */
    public void setTradeName(String tradeName) {
    
        this.tradeName = tradeName;
    }

    /**
     * getFdbProductName.
     * @return fdbProductName fdbProductName
     */
    public String getFdbProductName() {
    
        return fdbProductName;
    }

    /**
     * setFdbProductName
     * @param fdbProductName fdbProductName
     */
    public void setFdbProductName(String fdbProductName) {
    
        this.fdbProductName = fdbProductName;
    }

    /**
     * getGcnSeqno
     * @return gcnSeqno
     */
    public Long getGcnSeqno() {
    
        return gcnSeqno;
    }

    /**
     * setGcnSeqno
     * @param gcnSeqno gcnSeqNo
     */
    public void setGcnSeqno(Long gcnSeqno) {
        if (gcnSeqno != null) {
            this.setGcnSequenceNumber(String.valueOf(gcnSeqno));
        }
        
        this.gcnSeqno = gcnSeqno;
    }


    /**
     * returns the domain group of the managed Data  for FdbAddVo
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {
        return DomainGroup.GROUP_1;
    }

    /**
     * Returns true if the domain is standardized for FdbAddVo
     * 
     * @return boolean
     */
    public boolean isStandardized() {
        return true;
    }

    /**
     * Returns true if this is a local only domain  for FdbAddVo
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * Returns true if the domain is an NDF domain  for FdbAddVo
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return true;
    }

    /**
     * Returns the fdb creation date  for FdbAddVo
     * 
     * @return the fdbCreationDate
     */
    public Date getFdbCreationDate() {
        return fdbCreationDate;
    }

    /**
     * Setter for teh FDB creation date
     * 
     * @param fdbCreationDate the fdbCreationDate to set
     */
    public void setFdbCreationDate(Date fdbCreationDate) {
        this.fdbCreationDate = fdbCreationDate;
    }

    /** 
     * get ProductVos 
     * @return the productVos
     */
    public List<ProductVo> getProductVos() {
        return productVos;
    }

    /**
     * setProductVos.
     * @param productVos the productVos to set
     */
    public void setProductVos(List<ProductVo> productVos) {
        this.productVos = productVos;
    }

    /**
     * Adds a productVo to list
     *
     * @param pProductVo pointer to a Product Vo
     */
    public void addProductVo(ProductVo pProductVo) {
        productVos.add(pProductVo);
    }

    
    /**
     * get ObsoleteDate
     * @return the obsoleteDate
     */
    public Date getObsoleteDate() {
    
        return obsoleteDate;
    }

    
    /**
     * set ObsoleteDate
     * @param obsoleteDate the obsoleteDate to set
     */
    public void setObsoleteDate(Date obsoleteDate) {
    
        this.obsoleteDate = obsoleteDate;
    }

    /**
     * isNdcExist
     * 
     * @return the ndcExist
     */
    public boolean isNdcExist() {
    
        return ndcExist;
    }

    /**
     * setNdcExist
     * 
     * @param ndcExist the ndcExist to set
     */
    public void setNdcExist(boolean ndcExist) {
    
        this.ndcExist = ndcExist;
    }

    
    /**
     * obsoleteDateStr
     * @return the obsoleteDateStr
     */
    public String getObsoleteDateStr() {
    
        return obsoleteDateStr;
    }

    
    /**
     * setObsoleteDateStr
     * @param obsoleteDateStr the obsoleteDateStr to set
     */
    public void setObsoleteDateStr(String obsoleteDateStr) {
    
        this.obsoleteDateStr = obsoleteDateStr;
    }
}
