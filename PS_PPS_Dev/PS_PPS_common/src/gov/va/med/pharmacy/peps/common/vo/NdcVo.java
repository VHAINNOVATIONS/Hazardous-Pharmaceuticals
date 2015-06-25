/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.utility.ConfigFileUtility;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.diff.IgnoreDifference;


/**
 * Data representing a VA NDC
 */
public class NdcVo extends ManagedItemVo {

    /** IMAGE_FILE_TYPE */
    public static final String IMAGE_FILE_TYPE = ".JPG";

    /** DEFAULT_IMAGE_FILE_NAME */
    public static final String DEFAULT_IMAGE_FILE_NAME = "imageNotFound";

    private static final long serialVersionUID = 1L;

    private ProductVo product;
    private ProductVo previousProduct; // Previously set product item.
    private String ndc;
    private String ndcPart1;
    private String ndcPart2;
    private String ndcPart3;
    private long sequenceNumber;
    private String ndcIen;
    private String tenDigitNdc;
    private String tenDigitFormatIndication;
    private String vendor;
    private String vendorStockNumber;
    private String upcUpn;
    private OrderUnitVo orderUnit;
    private ManufacturerVo manufacturer;
    private PackageTypeVo packageType;
    private String tradeName;
    private Double ndcDispUnitsPerOrdUnit;
    private boolean localDispense;

    private Double packageSize;
    private ShapeVo shape;
    private ColorVo color;
    private String imprint;
    private String imprint2;
    @IgnoreDifference
    private String image = ConfigFileUtility.DEFAULT_FDB_IMAGE_PATH + DEFAULT_IMAGE_FILE_NAME + IMAGE_FILE_TYPE;
    private OtcRxVo otcRxIndicator;
    private NdcSourceType source;
    private SingleMultiSourceProductVo singleMultiSourceNdc;

    private transient ConfigFileUtility configFile = new ConfigFileUtility();

    //Added for migration
    private String fssI;
    private boolean fssPv;
    private String fssCntNo;
    private Date fssFssEnd;
    private Double fssFssPrice;
    private Double fssVaPrice;
    private Double fssBig4Price;
    private Double fssBpaPrice;
    private boolean fssBpaAvail;
    private Double fssNcPrice;
    private String fdbProductName;
    private List<Category> categories = new ArrayList<Category>();
    private List<SubCategory> subCategories = new ArrayList<SubCategory>();
    private FdbNdcVo fdbNdcVo;

    /**
     * getFdbNdcVo.
     * @return fdbNdcVo
     */
    public FdbNdcVo getFdbNdcVo() {

        return fdbNdcVo;
    }

    /**
     * setFdbNdcVo.
     * @param fdbNdcVo fdbNdcVo
     */
    public void setFdbNdcVo(FdbNdcVo fdbNdcVo) {

        this.fdbNdcVo = fdbNdcVo;
    }

    /**
     * getCategories
     * @return categories
     */
    public List<Category> getCategories() {

        return categories;
    }

    /**
     * setCategories
     * @param categories categories
     */
    public void setCategories(List<Category> categories) {

        this.categories = categories;
    }

    /**
     * getSubCategories
     * @return subCategories
     */
    public List<SubCategory> getSubCategories() {

        return subCategories;
    }

    /**
     * setSubCategories
     * @param subCategories subCategories
     */
    public void setSubCategories(List<SubCategory> subCategories) {

        this.subCategories = subCategories;
    }

    /**
     * ndcIen.
     * @return ndcIen
     */
    public String getNdcIen() {

        return ndcIen;
    }

    /**
     * ndcIen.
     * @param ndcIen ndcIen
     */
    public void setNdcIen(String ndcIen) {

        this.ndcIen = ndcIen;
    }
    
    /**
     * getFdbProductName.
     * @return fdbProductName
     */
    public String getFdbProductName() {

        return fdbProductName;
    }

    /**
     * setFdbProductName.
     * @param fdbProductName fdbProductName
     */
    public void setFdbProductName(String fdbProductName) {

        this.fdbProductName = fdbProductName;
    }


    /**
     * getFssI
     * @return fssI
     */
    public String getFssI() {

        return fssI;
    }

    /**
     * setFssI
     * @param fssI fssI
     */
    public void setFssI(String fssI) {

        this.fssI = fssI;
    }

    /**
     * isFssPv
     * @return fssPv
     */
    public boolean isFssPv() {

        return fssPv;
    }

    /**
     * setFssPv
     * @param fssPv fssPv property
     */
    public void setFssPv(boolean fssPv) {

        this.fssPv = fssPv;
    }

    /**
     * getFssCntNo
     * @return fssCntNo
     */
    public String getFssCntNo() {

        return fssCntNo;
    }

    /**
     * setFssCntNo
     * @param fssCntNo fssCntNo
     */
    public void setFssCntNo(String fssCntNo) {

        this.fssCntNo = fssCntNo;
    }

    /**
     * getFssFssEnd
     * @return fssFssEnd
     */
    public Date getFssFssEnd() {

        return fssFssEnd;
    }

    /**
     * setFssFssEnd
     * @param fssFssEnd fssFssEnd
     */
    public void setFssFssEnd(Date fssFssEnd) {

        this.fssFssEnd = fssFssEnd;
    }

    /**
     * getFssFssPrice
     * @return fssFssPrice
     */
    public Double getFssFssPrice() {

        return fssFssPrice;
    }

    /**
     * setFssFssPrice
     * @param fssFssPrice fssFssPrice
     */
    public void setFssFssPrice(Double fssFssPrice) {

        this.fssFssPrice = fssFssPrice;
    }

    /**
     * getFssVaPrice
     * @return fssVaPrice
     */
    public Double getFssVaPrice() {

        return fssVaPrice;
    }

    /**
     * setFssVaPrice
     * @param fssVaPrice fssVaPrice
     */
    public void setFssVaPrice(Double fssVaPrice) {

        this.fssVaPrice = fssVaPrice;
    }

    /**
     * getFssBig4Price
     * @return fssBig4Price
     */
    public Double getFssBig4Price() {

        return fssBig4Price;
    }

    /**
     * setFssBig4Price
     * @param fssBig4Price fssBig4Price
     */
    public void setFssBig4Price(Double fssBig4Price) {

        this.fssBig4Price = fssBig4Price;
    }

    /**
     * getFssBpaPrice
     * @return fssBpaPrice
     */
    public Double getFssBpaPrice() {

        return fssBpaPrice;
    }

    /**
     * setFssBpaPrice
     * @param fssBpaPrice fssBpaPrice
     */
    public void setFssBpaPrice(Double fssBpaPrice) {

        this.fssBpaPrice = fssBpaPrice;
    }

    /**
     * isFssBpaAvail
     * @return fssBpaAvail
     */
    public boolean isFssBpaAvail() {

        return fssBpaAvail;
    }

    /**
     * setFssBpaAvail
     * @param fssBpaAvail fsaBpAvail
     */
    public void setFssBpaAvail(boolean fssBpaAvail) {

        this.fssBpaAvail = fssBpaAvail;
    }

    /**
     * getFssNcPrice
     * @return fssNcPrice
     */
    public Double getFssNcPrice() {

        return fssNcPrice;
    }

    /**
     * setFssNcPrice
     * @param fssNcPrice fssNcPrice
     */
    public void setFssNcPrice(Double fssNcPrice) {

        this.fssNcPrice = fssNcPrice;
    }

    /**
     * return the product vo
     * 
     * @return product property
     */
    public ProductVo getProduct() {

        return product;
    }

    /**
     * getSingleMultiSourceProduct.
     * @return singleMultiSourceProduct property
     */
    public SingleMultiSourceProductVo getSingleMultiSourceProduct() {

        return singleMultiSourceNdc;
    }

    /**
     * setSingleMultiSource.
     * @param singleMultiSourceProduct singleMultiSourceProduct property
     */
    public void setSingleMultiSourceProduct(SingleMultiSourceProductVo singleMultiSourceProduct) {

        this.singleMultiSourceNdc = singleMultiSourceProduct;
    }

    /**
     * set the product vo
     * 
     * @param product product property
     */
    public void setProduct(ProductVo product) {

        if (this.product != null) {
            if ((product == null) || (product.getId() != this.product.getId())) {
                setPreviousProduct(this.product);
            }
        }

        this.product = product;
    }

    /**
     * return the previously selected product
     * 
     * @return previousOrderableItem property
     */
    public ProductVo getPreviousProduct() {

        return previousProduct;
    }

    /**
     * set the previously selected product
     * 
     * @param previousProduct Product property
     */
    public void setPreviousProduct(ProductVo previousProduct) {

        this.previousProduct = previousProduct;
    }

    /**
     * return the NDC
     * 
     * @return ndc property
     */
    public String getNdc() {

        return ndc;
    }

    /**
     * set the NDC
     * 
     * @param ndc ndc property
     */
    public void setNdc(String ndc) {

        this.ndc = trimToEmpty(ndc);
    }

    /**
     * return the NDC manufacture
     * 
     * @return manufacturer property
     */
    public ManufacturerVo getManufacturer() {

        return manufacturer;
    }

    /**
     * set the NDC manufacturer
     * 
     * @param manufacturer manufacturer property
     */
    public void setManufacturer(ManufacturerVo manufacturer) {

        this.manufacturer = manufacturer;
    }

    /**
     * return the packet type
     * 
     * @return packageType property
     */
    public PackageTypeVo getPackageType() {

        return packageType;
    }

    /**
     * set the package type
     * 
     * @param packageType packageType property
     */
    public void setPackageType(PackageTypeVo packageType) {

        this.packageType = packageType;
    }

    /**
     * return the package size
     * 
     * @return packageSize property
     */
    public Double getPackageSize() {

        return packageSize;
    }

    /**
     * set the package size
     * 
     * @param packageSize packageSize property
     */
    public void setPackageSize(Double packageSize) {

        this.packageSize = packageSize;
    }

    /**
     * return the NDC shape
     * 
     * @return shape property
     */
    public ShapeVo getShape() {

        return shape;
    }

    /**
     * set the NDC shape
     * 
     * @param shape shape property
     */
    public void setShape(ShapeVo shape) {

        this.shape = shape;
    }

    /**
     * return the NDC color
     * 
     * @return color property
     */
    public ColorVo getColor() {

        return color;
    }

    /**
     * set the NDC color
     * 
     * @param color color property
     */
    public void setColor(ColorVo color) {

        this.color = color;
    }

    /**
     * return the NDC imprint
     * 
     * @return imprint property
     */
    public String getImprint() {
        return imprint;
    }

    /**
     * return the NDC imprint
     * 
     * @return imprint property
     */
    public String getImprint2() {
        return imprint2;
    }

    /**
     * set the NDC imprint
     * 
     * @param imprint imprint property
     */
    public void setImprint(String imprint) {
        this.imprint = imprint;
    }
    
    /**
     * set the NDC imprint
     * 
     * @param imprint2 imprint property
     */
    public void setImprint2(String imprint2) {
        this.imprint2 = imprint2;
    }

    /**
     * return the NDC image filename
     * 
     * @return imageFileName property
     */
    public String getImage() {

        return image;
    }

    /**
     * Set the NDC image file name.
     * <p>
     * If null or empty string is provided, use the default "no image found".
     * 
     * @param imageFileName String file name without path or type extension
     */
    public void setImage(String imageFileName) {

        StringBuffer path = new StringBuffer(ConfigFileUtility.DEFAULT_FDB_IMAGE_PATH);

        if (configFile != null && configFile.getFdbImageLocation() != null && !configFile.getFdbImageLocation().isEmpty()) {
            path = new StringBuffer(configFile.getFdbImageLocation());
        }

        if (!path.toString().endsWith("/")) {
            path.append("/");
        }

        if (imageFileName == null || imageFileName.trim().length() == 0) {
            this.image = path.toString() + DEFAULT_IMAGE_FILE_NAME + IMAGE_FILE_TYPE;
        } else {
            this.image = path.toString() + imageFileName.trim() + IMAGE_FILE_TYPE;
        }
    }

    /**
     * Used in the header.jsp for the request status. Encounter issues when trying to use the Request Status field
     * 
     * @return requestStatus property
     */
    public RequestItemStatus getRequestState() {

        return getRequestItemStatus();
    }

    /**
     * RequestItemStatus
     * @return requestItemStatus property
     * @deprecated Remove this method, and use super getRequestItemStatus() method.
     */
    @Deprecated
    public RequestItemStatus getRequestStatus() {

        return getRequestItemStatus();
    }

    /**
     * setRequestStatus
     * @param requestStatus requestStatus property
     * @deprecated Remove this method, and use super setRequestItemStatus() method.
     */
    @Deprecated
    public void setRequestStatus(RequestItemStatus requestStatus) {

        setRequestItemStatus(requestStatus);
    }

    /**
     * getUpcUpn
     * 
     * @return String Upc/Upn property
     */
    public String getUpcUpn() {

        return upcUpn;
    }

    /**
     * set the UPC/UPN
     * 
     * @param upcUpn upcUpn property
     */
    public void setUpcUpn(String upcUpn) {

        this.upcUpn = trimToEmpty(upcUpn);
    }

    /**
     * returns the name of the Product and NDC
     * 
     * @return String
     */
    public String getNdcLongName() {

        if (getProduct() == null) {
            return getNdc();
        } else {
            return getProduct().getProductLongName() + " " + getNdc();
        }
    }

    /**
     * Returns if this item requires two reviews before adds/modifies are approved or rejected.
     * <p>
     * Differs from {@link FieldKey#isRequiresSecondApproval()} as this method is used during adds and the FieldKey method is
     * used during mods. In addition, this method only applies to {@link ManagedItemVo}, not individual fields.
     * <p>
     * NdcVo are not two review items.
     * 
     * @return boolean True if this item requires two reviews, otherwise, only one review is needed.
     */
    @Override
    public boolean isTwoReviewItem() {

        // If the Product this NDC is associated with is Pending, do not automatically approve it
        // so it doesn't get sent off to Vista
        return getProduct().getRequestItemStatus().equals(RequestItemStatus.PENDING);
    }

    /**
     * Returns if this ValueObject instance should be non-modifiable.
     * 
     * @return boolean True if this instance is should be non-modifiable.
     */
    @Override
    public boolean isReadOnlyInstance() {

        if (getRequestItemStatus() == null) {
            setRequestItemStatus(RequestItemStatus.PENDING);
        }

        return (getRequestItemStatus().isRejected() || super.isReadOnlyInstance());
    }

    /**
     * Returns true if this is a local only domain for the NDcVo.
     * 
     * @return boolean
     */
    @Override
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * List all uniqueness criteria for this ValueObject.
     * 
     * @return Set<FieldKey> All uniqueness fields for this object.
     */
    @Override
    public Set<FieldKey> listUniquenessCriteria() {

        Set<FieldKey> uniqueness = new HashSet<FieldKey>();
        uniqueness.add(FieldKey.NDC);
        uniqueness.add(FieldKey.UPC_UPN);

        return uniqueness;
    }

    /**
     * This is the NDCVo short string method.
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toShortString() {

        String msgret = "";

        if (StringUtils.isNotBlank(getNdc())) {
            msgret = "NDC:" + this.getNdc();
        } else if (StringUtils.isNotBlank(getUpcUpn())) {
            msgret = "UPC_UPN:" + this.getUpcUpn();
        }

        return msgret;
    }

    /**
     * Sub class handler method for merging the current National instance of a {@link ManagedItemVo} with the given Local
     * {@link ManagedItemVo}.
     * <p>
     * Returns a Collection of {@link FieldKey} which have been merged by this method. This will prevent the fields
     * represented by these FieldKeys from being overwritten again by future merging.
     * <p>
     * Handles merging the Product parent.
     * 
     * @param localItem the Local instance of {@link ManagedItemVo} to merge with National instance of {@link ManagedItemVo}
     * @return Collection of FieldKeys merged within this method
     */
    @Override
    protected Collection<FieldKey> handleMergeLocalFields(ManagedItemVo localItem) {

        Collection<FieldKey> handledFields = new ArrayList<FieldKey>();

        NdcVo localNdc = (NdcVo) localItem;
        handledFields.add(mergeProductParent(localNdc));

        return handledFields;
    }

    /**
     * Merge the NDC's parent Product.
     * <p>
     * If this National NDC parent Product's ID is not the same as the local's then set the previous product parent field as
     * an indication of a change, to be handled with parent swapping business rules during the update process.
     * 
     * @param localNdc Local instance of this National NDC
     * @return {@link FieldKey#PRODUCT}
     */
    private FieldKey mergeProductParent(NdcVo localNdc) {

        ProductVo localProductParent = localNdc.getProduct();
        ProductVo nationalProductParent = getProduct();

        if (!nationalProductParent.getId().equals(localProductParent.getId())) {
            setPreviousProduct(localProductParent);
        }

        return FieldKey.PRODUCT;
    }

    /**
     * Strips out local only data from the NDC
     * 
     * @return A NDC minus any local only data
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#removeLocalData()
     */
    @Override
    public NdcVo removeLocalData() {

        NdcVo copy = this.copy();

        copy.setLocalUse(false);
        copy.setVendor(null);
        copy.setVendorStockNumber(null);
        copy.setLocalDispense(false);

        copy.getVaDataFields().removeDataField(FieldKey.APPLICATION_PACKAGE_USE);

        return copy;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one for NdcAddVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        fields.add(FieldKey.CATEGORIES);
        fields.add(FieldKey.FSS_BIG4_PRICE);
        fields.add(FieldKey.FSS_BPA_AVAIL);
        fields.add(FieldKey.FSS_BPA_PRICE);
        fields.add(FieldKey.FSS_CNT_NO);
        fields.add(FieldKey.FSS_FSS_END);
        fields.add(FieldKey.FSS_FSS_PRICE);
        fields.add(FieldKey.FSS_I);
        fields.add(FieldKey.FSS_NC_PRICE);
        fields.add(FieldKey.FSS_PV);
        fields.add(FieldKey.FSS_VA_PRICE);
        fields.add(FieldKey.TEN_DIGIT_FORMAT_INDICATION);
        fields.add(FieldKey.TEN_DIGIT_NDC);

        if (Environment.NATIONAL.equals(environment)) {
            fields.addAll(listNationalDisabledFields(roles));
        } else {
            fields.addAll(listLocalDisabledFields(roles));
        }

        return fields;
    }

    /**
     * Return a list of the fields disabled at National.
     * <p>
     * Request Item status is always disabled. NDC Item Inactivation date is always disabled. At National all the Local only
     * fields and Uniqueness fields field are disabled for both pending and approved item
     * 
     * @param roles Collection of {@link Role} for the current user
     * @return disabled fields represented as a Set of {@link FieldKey}
     */
    private Set<FieldKey> listNationalDisabledFields(Collection<Role> roles) {

        Set<FieldKey> fields = new HashSet<FieldKey>();

        //fields.add(FieldKey.SOURCE);

        fields.add(FieldKey.TEN_DIGIT_NDC);

        if (getRequestItemStatus().isApproved() || getRequestItemStatus().isPending()) {
            fields.addAll(FieldKey.getNdcLocalOnlyDataFields());
        }

        // if pending get list of field keys and remove from disabled field list from national
        if (getRequestItemStatus().isPending()) {
            fields.removeAll(listUniquenessCriteria());
        }

        if (getItemStatus().equals(ItemStatus.ACTIVE)) {

            // UPC/UPN is a uniqueness field, but it is always editable
            fields.remove(FieldKey.UPC_UPN);
        }

        return fields;
    }

    /**
     * Return a list of the fields disabled at Local.
     * <p>
     * At Local National Data Fields are disabled for approved and pending items. Request Item Status is always disabled. NDC
     * Item Status is always disabled.
     * 
     * @param roles Collection of {@link Role} for the current user
     * @return disabled fields represented as a Set of {@link FieldKey}
     */
    private Set<FieldKey> listLocalDisabledFields(Collection<Role> roles) {

        Set<FieldKey> fields = new HashSet<FieldKey>();
        fields.add(FieldKey.SOURCE);

        //DF376
        //fields.add(FieldKey.TEN_DIGIT_FORMAT_INDICATION);

        fields.add(FieldKey.TEN_DIGIT_NDC);

        // both national data fields and Non VA Data Fields are disabled
        if (getRequestItemStatus().isApproved() || getRequestItemStatus().isPending()) {

            // add national data fields to the collection
            fields.addAll(FieldKey.getNdcNationalOnlyDataFields());

            // add non va data fields to the collection
            fields.addAll(FieldKey.getNdcNonVaDataFields());

            // make all the local only fields editable
            fields.removeAll(FieldKey.getNdcLocalOnlyDataFields());

        }

        fields.add(FieldKey.SHAPE);
        fields.add(FieldKey.ITEM_STATUS);

        // get list of uniqueness fields and disable at local
        fields.addAll(listUniquenessCriteria());

        if (getItemStatus().equals(ItemStatus.ACTIVE)) {

            // UPC/UPN is a uniqueness field, but it is always editable
            fields.remove(FieldKey.UPC_UPN);
        }

        return fields;
    }

    /**
     * List all non-editable fields for this ValueObject.
     * 
     * Note: Though the VO's isEditable() could be used directly, using the authorization framework (via the returned list)
     * eases the implementation of the custom JSP tag files that render the fields.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All non-editable fields for this object.
     */
    @Override
    public Set<FieldKey> listNonEditableFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = new HashSet<FieldKey>();

        for (DataField dataField : getVaDataFields().getDataFields().values()) {
            if (!dataField.isEditable()) {
                fields.add(dataField.getKey());
            }
        }
        
        return fields;
    }

    /**
     * Lists the NDCVo required fields for this ValueObject, with the pre-condition that the current instance is not a read-only
     * one.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.handleListRequiredFields(environment, roles);
        fields.add(FieldKey.NDC);

        //fields.add(FieldKey.ORDER_UNIT);
        fields.add(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);
        fields.add(FieldKey.CATEGORIES);
        fields.add(FieldKey.OTC_RX_INDICATOR);

        if (environment.isNational()) {

            fields.add(FieldKey.MANUFACTURER);
            fields.add(FieldKey.PACKAGE_SIZE);
            fields.add(FieldKey.PACKAGE_TYPE);
            fields.add(FieldKey.TRADE_NAME);
            fields.add(FieldKey.PRODUCT);
        } else {
            fields.add(FieldKey.NDC_PRICE_PER_ORDER_UNIT);
            fields.add(FieldKey.UNIT_PRICE);

            if (RequestItemStatus.APPROVED.equals(getRequestItemStatus())) {

                fields.add(FieldKey.MANUFACTURER);
                fields.add(FieldKey.PACKAGE_SIZE);
                fields.add(FieldKey.PACKAGE_TYPE);
                fields.add(FieldKey.TRADE_NAME);
            }
        }

        return fields;
    }

    /**
     * List all second review fields for this ValueObject
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} for the current user
     * @return Set<FieldKey> all second review fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListSecondReviewFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = new HashSet<FieldKey>();
        fields.addAll(FieldKey.getSecondApprovalDataFields(EntityType.NDC));

        return fields;
    }

    /**
     * return the NDC part 1 number
     * 
     * @return ndcPart1 property
     */
    public String getNdcPart1() {

        return ndcPart1;
    }

    /**
     * set the NDC part 1 number
     * 
     * @param ndcPart1 ndcPart1 property
     */
    public void setNdcPart1(String ndcPart1) {

        this.ndcPart1 = trimToEmpty(ndcPart1);
    }

    /**
     * return the NDC part2 number
     * 
     * @return ndcPart2 property
     */
    public String getNdcPart2() {

        return ndcPart2;
    }

    /**
     * set the NDC part2 number
     * 
     * @param ndcPart2 ndcPart2 property
     */
    public void setNdcPart2(String ndcPart2) {

        this.ndcPart2 = trimToEmpty(ndcPart2);
    }

    /**
     * return the NDC part3 number
     * 
     * @return ndcPart3 property
     */
    public String getNdcPart3() {

        return ndcPart3;
    }

    /**
     * set the NDC part3 number
     * 
     * @param ndcPart3 ndcPart3 property
     */
    public void setNdcPart3(String ndcPart3) {

        this.ndcPart3 = trimToEmpty(ndcPart3);
    }

    /**
     * return the NDC sequence number
     * 
     * @return sequenceNumber property
     */
    public long getSequenceNumber() {

        return sequenceNumber;
    }

    /**
     * set the NDC sequence number
     * 
     * @param sequenceNumber sequenceNumber property
     */
    public void setSequenceNumber(long sequenceNumber) {

        this.sequenceNumber = sequenceNumber;
    }

    /**
     * return the NDC tradename
     * 
     * @return tradeName property
     */
    public String getTradeName() {

        return tradeName;
    }

    /**
     * set the NDC trade name
     * 
     * @param tradeName tradeName property
     */
    public void setTradeName(String tradeName) {

        this.tradeName = trimToEmpty(tradeName);
    }

    /**
     * return the OTC prescription indicator
     * 
     * @return otc_rx property
     */
    public OtcRxVo getOtcRxIndicator() {

        return otcRxIndicator;

    }

    /**
     * set the OTC prescription indicator
     * 
     * @param otcRx OtcRxVo property
     */
    public void setOtcRxIndicator(OtcRxVo otcRx) {

        this.otcRxIndicator = otcRx;
    }

    /**
     * return the order unit
     * 
     * @return orderUnit property
     */
    public OrderUnitVo getOrderUnit() {

        return orderUnit;
    }

    /**
     * set the order unit
     * 
     * @param orderUnit orderUnit property
     */
    public void setOrderUnit(OrderUnitVo orderUnit) {

        this.orderUnit = orderUnit;
    }

    /**
     * Set the given ManagedItemVo as this NDC's parent product.
     * 
     * @param parent ManagedItemVo
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#selectParent(gov.va.med.pharmacy.peps.common.vo.ManagedItemVo)
     */
    @Override
    public void selectParent(ManagedItemVo parent) {

        if (parent instanceof ProductVo) {
            setProduct((ProductVo) parent);

        }

    }

    /**
     * getNdcDispUnitsPerOrdUnit
     * @return ndcDispUnitsPerOrdUnit property
     */
    public Double getNdcDispUnitsPerOrdUnit() {

        return ndcDispUnitsPerOrdUnit;
    }

    /**
     * tempNdc
     * @param ndcDispUnitsPerOrdUnit ndcDispoubleUnitsPerOrdUnit property
     */
    public void setNdcDispUnitsPerOrdUnit(Double ndcDispUnitsPerOrdUnit) {

        this.ndcDispUnitsPerOrdUnit = ndcDispUnitsPerOrdUnit;
    }

    /**
     * Create a blank NDC template.
     * 
     * @param environment {@link Environment} in which the template is being made
     * @return NdcVo
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#blankTemplate()
     */
    @Override
    public ManagedItemVo blankTemplate(Environment environment) {

        NdcVo tNDC = new NdcVo();

        ColorVo clr = new ColorVo();
        clr.setId(null);
        clr.setValue("");
        tNDC.setColor(clr);
        tNDC.setImage("");


        ManufacturerVo manu = new ManufacturerVo();
        manu.setId(null);
        manu.setValue("");
        tNDC.setManufacturer(manu);

        ShapeVo shp = new ShapeVo();
        shp.setId(null);
        shp.setValue("");
        tNDC.setShape(shp);

        tNDC.setId(null);
        tNDC.setItemStatus(ItemStatus.ACTIVE);
        tNDC.setRequestItemStatus(RequestItemStatus.PENDING);

        tNDC.setSource(NdcSourceType.VA);

        tNDC.setFssVaPrice(0.0);
        tNDC.setFssBig4Price(0.0);
        tNDC.setFssBpaPrice(0.0);
        tNDC.setFssNcPrice(0.0);
        tNDC.setFssFssPrice(0.0);
        
        return tNDC;
    }

    /**
     * Set the ID to null and reset the UPC to an empty string.
     * 
     * @param managedItemVo ManagedItemVo template
     * @param environment {@link Environment} in which the template is being made
     * @return NdcVo
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#makeTemplate(gov.va.med.pharmacy.peps.common.vo.ManagedItemVo)
     */
    @Override
    protected ManagedItemVo makeTemplate(ManagedItemVo managedItemVo, Environment environment) {

        NdcVo tNDC = (NdcVo) managedItemVo;
        tNDC.setCreatedBy(null);
        tNDC.setModifiedBy(null);
        tNDC.setCreatedDate(null);
        tNDC.setModifiedDate(null);
        tNDC.setId(null);
        tNDC.setNdcIen(null);
        tNDC.setSource(NdcSourceType.VA);
        tNDC.setVendor("");
        tNDC.setVendorStockNumber("");
        tNDC.getVaDataFields().getDataField(FieldKey.NDC_PRICE_PER_ORDER_UNIT).unselect();

        return tNDC;
    }

    /**
     * Indicates originating source of Product/NDC data.
     * 
     * @return NdcSourceType (VA, FDA or COTS)
     */
    public NdcSourceType getSource() {

        return source;
    }

    /**
     * Indicates originating source of Product/NDC data.
     * 
     * @param source productDataSource
     */
    public void setSource(NdcSourceType source) {

        this.source = source;
    }

    /**
     * getDefaultSortedFieldKey
     * @return {@link FieldKey#NDC}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#getDefaultSortedFieldKey()
     */
    @Override
    public FieldKey getDefaultSortedFieldKey() {

        return FieldKey.NDC;
    }

    /**
     * isLocalDispense
     * @return localDispense property
     */
    public boolean isLocalDispense() {

        return localDispense;
    }

    /**
     * setLocalDispense
     * @param localDispense localDispense property
     */
    public void setLocalDispense(boolean localDispense) {

        this.localDispense = localDispense;
    }

    /**
     * getVendor
     * @return vendor property
     */
    public String getVendor() {

        return vendor;
    }

    /**
     * setVendor
     * @param vendor vendor property
     */
    public void setVendor(String vendor) {

        this.vendor = trimToEmpty(vendor);
    }

    /**
     * getVendorStockNumber
     * @return vendorStockNumber property
     */
    public String getVendorStockNumber() {

        return vendorStockNumber;
    }

    /**
     * setVendorStockNumber
     * @param vendorStockNumber vendorStockNumber property
     */
    public void setVendorStockNumber(String vendorStockNumber) {

        this.vendorStockNumber = trimToEmpty(vendorStockNumber);
    }

    /**
     * getTenDigitNdc
     * @return tenDigitNdc property
     */
    public String getTenDigitNdc() {

        return tenDigitNdc;
    }

    /**
     * setTenDigitNdc
     * @param tenDigitNdc tenDigitNdc property
     */
    public void setTenDigitNdc(String tenDigitNdc) {

        this.tenDigitNdc = tenDigitNdc;
    }

    /**
     * getTenDigitFormatIndication
     * @return tenDigitFormatIndication property
     */
    public String getTenDigitFormatIndication() {

        return tenDigitFormatIndication;
    }

    /**
     * setTenDigitFormatIndication
     * @param tenDigitFormatIndication tenDigitFormatIndication property
     */
    public void setTenDigitFormatIndication(String tenDigitFormatIndication) {

        this.tenDigitFormatIndication = tenDigitFormatIndication;
    }

}

