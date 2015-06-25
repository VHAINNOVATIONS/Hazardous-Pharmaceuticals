/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing a PartialSaveVo.
 * 
 */
public class PartialSaveVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private String id;
    private EntityType entityType; // OI, PRODUCT, NDC
    private String fileName; // filename of the serialized VO
    private RequestType requestType; // Add or mod
    private Long itemRevisionNumber;
    private String comment;
    private NdcVo ndcVo;
    private ProductVo productVo;
    private OrderableItemVo orderableItemVo;

    /**
     * getId for the PartialSaveVo.
     * 
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * setId for the PartialSaveVo.
     * 
     * @param id String property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getEntityType
     * 
     * @return entityType EntityType property
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * setEntityType
     * 
     * @param entityType property
     */
    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    /**
     * getRequestType
     * 
     * @return requestType RequestType
     */
    public RequestType getRequestType() {
        return requestType;
    }

    /**
     * setRequestType
     * 
     * @param requestType requestType property
     */
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    /**
     * getFileName
     * 
     * @return fileName property
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * setFileName
     * 
     * @param fileName fileName property
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * getItemRevisionNumber
     * 
     * @return itemRevisionNumber property
     */
    public Long getItemRevisionNumber() {
        return itemRevisionNumber;
    }

    /**
     * setItemRevisionNumber
     * 
     * @param itemRevisionNumber itemRevisionNumber property
     */
    public void setItemRevisionNumber(Long itemRevisionNumber) {
        this.itemRevisionNumber = itemRevisionNumber;
    }

    /**
     * getComment
     * 
     * @return comment property
     */
    public String getComment() {
        return comment;
    }

    /**
     * setComment
     * 
     * @param comment String property
     */
    public void setComment(String comment) {
        this.comment = trimToEmpty(comment);
    }

    /**
     * set the NDC vo
     * 
     * @param ndcVo NdcVo
     */
    public void setNdcVo(NdcVo ndcVo) {
        this.ndcVo = ndcVo;
    }

    /**
     * get the NDC VO
     * 
     * @return NdcVo
     */
    public NdcVo getNdcVo() {
        return ndcVo;
    }

    /**
     * setProductVo
     * 
     * @param productVo ProductVo property
     */
    public void setProductVo(ProductVo productVo) {
        this.productVo = productVo;
    }

    /**
     * getProductVo
     * 
     * @return productVo property
     */
    public ProductVo getProductVo() {
        return productVo;
    }

    /**
     * setOrderableItemVo
     * 
     * @param orderableItemVo OrderableItemVo property
     */
    public void setOrderableItemVo(OrderableItemVo orderableItemVo) {
        this.orderableItemVo = orderableItemVo;
    }

    /**
     * getOrderableItemVo
     * 
     * @return orderableItemVo property
     */
    public OrderableItemVo getOrderableItemVo() {
        return orderableItemVo;
    }

    /**
     * returns the partial save category composed of entity type and request type
     * 
     * @return string the partial save category
     */
    public String getPartialCategory() {
        return entityType + " " + requestType;
    }
}
