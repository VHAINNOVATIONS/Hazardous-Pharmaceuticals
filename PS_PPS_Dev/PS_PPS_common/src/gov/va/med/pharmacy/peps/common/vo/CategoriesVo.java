/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing a Drug Text Domain.
 */
public class CategoriesVo extends ValueObject  {
    private static final long serialVersionUID = 1L;
    private String category;
    private Long eplId;
    private Long productFk;
    private Long ndcFk;
    private Long oiFk;
    private Long catType;

    /**
     * getCategory.
     * @return drugText property
     */
    public String getCategory() {
        return category;
    }

    /**
     * setCategory.
     * @param category drugText property
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * getProductFk
     * @return productFk productFk
     */
    public Long getProductFk() {
        return productFk;
    }
    
    /**
     * setProductFk
     * @param productFk productFk property
     */
    public void setProductFk(Long productFk) {
        this.productFk = productFk;
    }

    /**
     * getEplId
     * @return eplId eplId
     */
    public Long getEplId() {
        return eplId;
    }
    
    /**
     * setEplId
     * @param eplId eplId property
     */
    public void setEplId(Long eplId) {
        this.eplId = eplId;
    }

    
    /**
     * setCatType
     * @param catType drugText property
     */
    public void setCatType(Long catType) {
        this.catType = catType;
    }
    
    /**
     * getCatType
     * @return catType property
     */
    public Long getCatType() {
        return catType;
    }

    /**
     * setNdcFk
     * @param ndcFk ndcFk property
     */
    public void setNdcFk(Long ndcFk) {
        this.ndcFk = ndcFk;
    }
    
    /**
     * getNdcFk
     * @return ndcFk property
     */
    public Long getNdcFk() {
        return ndcFk;
    }
    
    /**
     * setOiFk
     * @param oiFk oiFk property
     */
    public void setOiFk(Long oiFk) {
        this.oiFk = oiFk;
    }
    
    /**
     * getOiFk
     * @return oiFk property
     */
    public Long getOiFk() {
        return oiFk;
    }
 
}
