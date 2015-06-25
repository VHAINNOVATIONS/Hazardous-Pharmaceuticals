/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;


/**
 * Data representing a summary of a request used for displaying pending requests.
 * 
 */
public class RequestSummaryVo extends ValueObject {

    private static final long serialVersionUID = 1L;

    private static final String QUESTION_MARKS = "???";

    private String requestId; // this is an enterprise id
    private String requestItemId; // id of the item (Product/NDC/OI)
    private EntityType entityType; // type of the item(Product/NDC/OI)
    private RequestType requestType; // Add or mod
    private String itemName;
    private String oiName;
    private String vaProductName;
    private GenericNameVo genericName;
    private DispenseUnitVo dispenseUnit;
    private String ndcNumber;
    private String productStrength;
    private DrugUnitVo productUnit;
    private List<Category> categories;
    private List<SubCategory> subCategories;

    // private String displayableIngredientStrength;
    // private String displayableIngredientUnit;
    private DosageFormVo dosageForm;
    private Collection<ProductType> productType = new ArrayList<ProductType>(0); // medication, supply, etc.
    private DataFields<DataField> dataFields = new DataFields<DataField>();
    private boolean localUse;
    private FormularyStatus formulary;
    private ItemStatus itemStatus; // active or inactive
    private RequestItemStatus requestItemStatus; // pending, approved,rejected
    private RequestState requestState; // state of the request (pending 1st review,pending 2nd review,completed)
    private UserVo requestedBy;
    private UserVo reviewedBy;
    private boolean underReview;
    private String psrName;

    /**
     * Getters and Setters
     */

    /**
     * getRequestId
     * 
     * @return requestId
     */
    public String getRequestId() {

        return requestId;
    }

    /**
     * setRequestId
     * 
     * @param requestId for request
     */
    public void setRequestId(String requestId) {

        this.requestId = requestId;
    }

    /**
     * getRequestItemId
     * @return requestItemId
     */
    public String getRequestItemId() {

        return requestItemId;
    }

    /**
     * setRequestItemId
     * @param requestItemId item id for which the request is being made.
     */
    public void setRequestItemId(String requestItemId) {

        this.requestItemId = requestItemId;
    }

    /**
     * getEntityType for RequestSummaryVo.
     * @return entityType
     */
    public EntityType getEntityType() {

        return entityType;
    }

    /**
     * setEntityType
     * @param requestItemType item type of the item for which the requests is being made.
     */
    public void setEntityType(EntityType requestItemType) {

        this.entityType = requestItemType;
    }

    /**
     * getRequestType
     * @return requestType
     */
    public RequestType getRequestType() {

        return requestType;
    }

    /**
     * setRequestType
     * @param requestType request for modification or addition.
     */
    public void setRequestType(RequestType requestType) {

        this.requestType = requestType;
    }

    /**
     * getGenericName
     * @return genericName
     */
    public GenericNameVo getGenericName() {

        return genericName;
    }

    /**
     * setGenericName
     * @param genericName generic name of the item.
     */
    public void setGenericName(GenericNameVo genericName) {

        this.genericName = genericName;
    }

    /**
     * getDosageForm
     * @return dosageForm
     */
    public DosageFormVo getDosageForm() {

        return dosageForm;
    }

    /**
     * setDosageForm
     * @param dosageForm dosage form of the item
     */
    public void setDosageForm(DosageFormVo dosageForm) {

        this.dosageForm = dosageForm;
    }

    /**
     * isLocalUse
     * @return localUse
     */
    public boolean isLocalUse() {

        return localUse;
    }

    /**
     * setLocalUse
     * @param localUse flag for marked for local use.
     */
    public void setLocalUse(boolean localUse) {

        this.localUse = localUse;
    }

    /**
     * getFormulary
     * 
     * @return formulary
     */
    public FormularyStatus getFormulary() {

        return formulary;
    }

    /**
     * setFormulary
     * 
     * @param formulary formulary value for the item
     */
    public void setFormulary(FormularyStatus formulary) {

        this.formulary = formulary;
    }

    /**
     * getItemStatus
     * @return itemStatus
     */
    public ItemStatus getItemStatus() {

        return itemStatus;
    }

    /**
     * setItemStatus
     * @param itemStatus status of the item as active or inactive
     */
    public void setItemStatus(ItemStatus itemStatus) {

        this.itemStatus = itemStatus;
    }

    /**
     * getRequestItemStatus
     * @return requestItemStatus
     */
    public RequestItemStatus getRequestItemStatus() {

        return requestItemStatus;
    }

    /**
     * setRequestItemStatus
     * @param requestItemStatus status of the item as approved, pending or rejected.
     */
    public void setRequestItemStatus(RequestItemStatus requestItemStatus) {

        this.requestItemStatus = requestItemStatus;
    }

    /**
     * getRequestState
     * @return requestState
     */
    public RequestState getRequestState() {

        return requestState;
    }

    /**
     * setRequestState
     * @param requestState the state of the request
     */
    public void setRequestState(RequestState requestState) {

        this.requestState = requestState;
    }

    /**
     * getRequestedBy
     * @return reviewedBy
     */
    public String getRequestedBy() {

        return (requestedBy == null ? QUESTION_MARKS : requestedBy.toDisplayName());
    }

    /**
     * setRequestedBy
     * @param requestedBy user who made the request
     */
    public void setRequestedBy(UserVo requestedBy) {

        this.requestedBy = requestedBy;
    }

    /**
     * getProductType
     * 
     * @return productType
     */
    public Collection<ProductType> getProductType() {

        return productType;
    }

    /**
     * setProductType
     * 
     * @param productType type of product(Medication, Investigational,etc...)
     */
    public void setProductType(Collection<ProductType> productType) {

        if (productType == null) {
            this.productType = new ArrayList<ProductType>();
        } else {
            this.productType = productType;
        }
    }

    /**
     * getOiName
     * 
     * @return oiName
     */
    public String getOiName() {

        return oiName;
    }

    /**
     * setOiName
     * 
     * @param oiName name for an Orderable Item
     */
    public void setOiName(String oiName) {

        this.oiName = oiName;
    }

    /**
     * getVaProductName
     * @return vaProductName
     */
    public String getVaProductName() {

        return vaProductName;
    }

    /**
     * setVaProductName
     * @param vaProductName VA product name for item
     */
    public void setVaProductName(String vaProductName) {

        this.vaProductName = vaProductName;
    }

    /**
     * getReviewedBy
     * @return reviewedBy
     */
    public String getReviewedBy() {

        return (reviewedBy == null ? QUESTION_MARKS : reviewedBy.toDisplayName());
    }

    /**
     * setReviewedBy
     * @param reviewedBy user who performed review
     */
    public void setReviewedBy(UserVo reviewedBy) {

        this.reviewedBy = reviewedBy;
    }

    /**
     * getNdcNumber
     * 
     * @return ndcNumber
     */
    public String getNdcNumber() {

        return ndcNumber;
    }

    /**
     * setNdcNumber
     * 
     * @param ndcNumber NDC number for item
     */
    public void setNdcNumber(String ndcNumber) {

        this.ndcNumber = ndcNumber;
    }

    /**
     * getItemName
     * @return itemName
     */
    public String getItemName() {

        return itemName;
    }

    /** 
     * setItemName
     * @param itemName Name to be used for all item types
     */
    public void setItemName(String itemName) {

        this.itemName = itemName;
    }

    /**
     * Getter to get around a localization issue on the domain summary page
     * @return itemName
     */
    public String getDomainName() {

        return itemName;
    }

    /**
     * getProductStrength for RequestSummaryVo.
     * @return productStrength
     */
    public String getProductStrength() {

        return productStrength;
    }

    /**
     * setProductStrength
     * @param productStrength strength for the item
     */
    public void setProductStrength(String productStrength) {

        this.productStrength = productStrength;
    }

    /**
     * getProductUnit
     * @return productUnit;
     */
    public DrugUnitVo getProductUnit() {
        
        return productUnit;
    }

    /**
     * setProductUnit
     * @param productUnit unit for the item
     */
    public void setProductUnit(DrugUnitVo productUnit) {

        this.productUnit = productUnit;
    }

    /**
     * getDataFields
     * @return dataFields property
     */
    public DataFields<DataField> getDataFields() {

        return dataFields;
    }

    /**
     * setDataFields
     * @param dataFields dataFields property
     */
    public void setDataFields(DataFields<DataField> dataFields) {

        this.dataFields = dataFields;
    }

    /**
     * isUnderReview
     * @return underReview property
     */
    public boolean isUnderReview() {

        return underReview;
    }

    /**
     * setUnderReview
     * @param underReview underReview property
     */
    public void setUnderReview(boolean underReview) {

        this.underReview = underReview;
    }

    /**
     * getPsrName for ReqeustSummaryVo.
     * @return psrName property
     */
    public String getPsrName() {

        return psrName;
    }

    /**
     * setPsrName for RequestSeummaryVo.
     * @param psrName psrName property
     */
    public void setPsrName(String psrName) {

        this.psrName = psrName;
    }

    /**
     * getDispenseUnit
     * @return dispenseUnit property
     */
    public DispenseUnitVo getDispenseUnit() {

        return dispenseUnit;
    }

    /**
     * setDispenseUnit
     * @param dispenseUnit dispenseUnit property
     */
    public void setDispenseUnit(DispenseUnitVo dispenseUnit) {

        this.dispenseUnit = dispenseUnit;
    }

    /**
     * setCategories
     * @param categories the categories to set
     */
    public void setCategories(List<Category> categories) {

        this.categories = categories;
    }

    /**
     * getCategories
     * @return the categories
     */
    public List<Category> getCategories() {

        return categories;
    }

    /**
     * setSubCategories
     * @param subCategories the subCategories to set
     */
    public void setSubCategories(List<SubCategory> subCategories) {

        this.subCategories = subCategories;
    }

    /**
     * getCategories
     * @return the subCategories
     */
    public List<SubCategory> getSubCategories() {

        return subCategories;
    }

}
