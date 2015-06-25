/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.diff.IgnoreDifference;


/**
 * Data representing a VA Orderable Item.
 */
public class OrderableItemVo extends ManagedItemVo {

    private static final long serialVersionUID = 1L;

    private String oiName;
    private Collection<RequestVo> requests = new ArrayList<RequestVo>(0);
    private Collection<OiRouteVo> oiRoute = new ArrayList<OiRouteVo>(0);
    private OiScheduleTypeVo oiScheduleType;
    private DosageFormVo dosageForm;
    private StandardMedRouteVo standardMedRoute;
    private Collection<AdministrationScheduleAssocVo> adminSchedules = new ArrayList<AdministrationScheduleAssocVo>();
    private Boolean nonVaMed;
    @IgnoreDifference
    private Collection<ProductVo> products = new ArrayList<ProductVo>(0);

    @IgnoreDifference
    private Integer productCount;

    private OrderableItemType orderableItemType;
    private OrderableItemVo orderableItemParent;

    @IgnoreDifference
    private OrderableItemVo previousOrderableItemParent;

    // "lab.display.administration"
    // "lab.display.order.entry"
    // "lab.display.finishing.an.order"
    private Collection<LabDisplayAdministrationVo> labDisplayAdministration = new ArrayList<LabDisplayAdministrationVo>();
    private Collection<LabDisplayOrderEntryVo> labDisplayOrderEntry = new ArrayList<LabDisplayOrderEntryVo>();
    private Collection<LabDisplayFinishingAnOrderVo> labDisplayFinishingAnOrder = new ArrayList<LabDisplayFinishingAnOrderVo>();
    private List<DrugTextVo> localDrugTexts = new ArrayList<DrugTextVo>();
    private List<DrugTextVo> nationalDrugTexts = new ArrayList<DrugTextVo>();

    // "vitals.display.administration"
    // "vitals.display.order.entry"
    // "vitals.display.finish.an.order"
    private Collection<VitalsDisplayAdministrationVo> vitalsDisplayAdministration =
            new ArrayList<VitalsDisplayAdministrationVo>();
    private Collection<VitalsDisplayOrderEntryVo> vitalsDisplayOrderEntry = new ArrayList<VitalsDisplayOrderEntryVo>();
    private Collection<VitalsDisplayFinishAnOrderVo> vitalsDisplayFinishAnOrder = new ArrayList<VitalsDisplayFinishAnOrderVo>();

    private boolean nationalFormularyIndicator;
    private String vistaOiName;

    private GenericNameVo genericName;

    //Added for migration
    private String highAlert;
    private String specialInstructions;
    

    private List<Category> categories = new ArrayList<Category>();
    private List<SubCategory> subCategories = new ArrayList<SubCategory>();

    /**
     * getCategories for OrderableItemVo.
     * @return categories
     */
    public List<Category> getCategories() {

        return categories;
    }

    /**
     * setCategories for OrderableItemVo.
     * @param categories categories
     */
    public void setCategories(List<Category> categories) {

        this.categories = categories;
    }

    /**
     * getSubCategories for OrderableItemVo.
     * @return subCategories
     */
    public List<SubCategory> getSubCategories() {

        return subCategories;
    }

    /**
     * setSubCategories for OrderableItemVo.
     * @param subCategories subCategories
     */
    public void setSubCategories(List<SubCategory> subCategories) {

        this.subCategories = subCategories;
    }

    /**
     * Return true if this OrderableitemVo has a parent..
     * 
     * @return boolean
     */
    @Override
    public boolean hasParent() {

        return super.hasParent() && isLocal();
    }

    /**
     * getHighAlert
     * @return highAlert
     */
    public String getHighAlert() {

        return highAlert;
    }

    /** 
     * setHighAlert
     * @param highAlert highAlert
     */
    public void setHighAlert(String highAlert) {

        this.highAlert = highAlert;
    }

    /**
     * getSpecialInstructions
     * @return specialInstructions
     */
    public String getSpecialInstructions() {

        return specialInstructions;
    }

    /**
     * setSpecialInstructions
     * @param specialInstructions specialInstructions
     */
    public void setSpecialInstructions(String specialInstructions) {

        this.specialInstructions = specialInstructions;
    }

    /**
     * getStandardMedRoute
     * @return standardMedRoute
     */
    public StandardMedRouteVo getStandardMedRoute() {

        return standardMedRoute;
    }

    /**
     * setStandardMedRoute
     * @param standardMedRoute standardMedRoute
     */
    public void setStandardMedRoute(StandardMedRouteVo standardMedRoute) {

        this.standardMedRoute = standardMedRoute;
    }

    /**
     * getOiName
     * 
     * @return oiName property
     */
    public String getOiName() {

        return oiName;
    }

    /**
     * setOiName
     * 
     * @param oiName oiName property
     */
    public void setOiName(String oiName) {
        if (StringUtils.isNotBlank(oiName)) {
            this.oiName = trimToEmpty(oiName.toUpperCase(Locale.US));
        } else {
            this.oiName = "";
        }

    }

    /**
     * The PEPS Orderable Item Name is a system data field derived from VistA Orderable Item Name and Dosage Form (DF138 Rule
     * 2).
     */
    public void updateOiName() {

        // Both Vista OI Name and Dosage Form may not be populated if this is a minimally populated VO!
        if (getVistaOiName() != null && getDosageForm() != null && getDosageForm().getDosageFormName() != null) {
            StringBuffer updatedOiName = new StringBuffer();
            updatedOiName.append(trimToEmpty(getVistaOiName()));
            updatedOiName.append(" ");
            updatedOiName.append(trimToEmpty(getDosageForm().getDosageFormName()));

            setOiName(updatedOiName.toString());
        }
    }

    /**
     * getProducts
     * 
     * @return products property
     */
    public Collection<ProductVo> getProducts() {

        return products;
    }

    /**
     * setProducts
     * 
     * @param products products property
     */
    public void setProducts(Collection<ProductVo> products) {

        this.products = new ArrayList<ProductVo>();

        if (products != null && !products.isEmpty()) {
            this.products.addAll(products);
        }
    }

    /**
     * If the List of Products, {@link #getProducts()}, is null or empty, set the count of Products set when the
     * OrderableItem was instantiated.
     * 
     * The value of the count may not reflect the size of the Product list if Products have been added to the List!
     * 
     * @param productCount productCount property
     */
    public void setProductCount(Integer productCount) {

        this.productCount = productCount;
    }

    /**
     * If the List of Products, {@link #getProducts()}, is null or empty, return the count of Products set when the
     * OrderableItem was instantiated.
     * 
     * The value of the count may not reflect the size of the Product list if Products have been added to the List!
     * 
     * @return int productCount - was revised to Integer
     */
    public Integer getProductCount() {

        if (products == null || products.isEmpty()) {
            return productCount;
        } else {
            return products.size();
        }
    }

    /**
     * getRequests
     * 
     * @return requests
     */
    public Collection<RequestVo> getRequests() {

        return requests;
    }

    /**
     * setRequests
     * 
     * @param requests requests property
     */
    public void setRequests(Collection<RequestVo> requests) {

        this.requests = new ArrayList<RequestVo>();

        if (requests != null && !requests.isEmpty()) {
            this.requests.addAll(requests);
        }
    }

    /**
     * getOiRoute
     * 
     * @return oiRoute
     */
    public Collection<OiRouteVo> getOiRoute() {

        return oiRoute;
    }

    /**
     * setOiRoute
     * 
     * @param oiRoute oiRoute property
     */
    public void setOiRoute(Collection<OiRouteVo> oiRoute) {

        this.oiRoute = new ArrayList<OiRouteVo>();

        if (oiRoute != null && !oiRoute.isEmpty()) {
            this.oiRoute.addAll(oiRoute);
        }
    }

    /**
     * getDosageForm
     * 
     * @return DosageFormVo
     */
    public DosageFormVo getDosageForm() {

        return dosageForm;
    }

    /**
     * setDosageForm
     * 
     * @param dosageForm dosageForm property
     */
    public void setDosageForm(DosageFormVo dosageForm) {

        this.dosageForm = dosageForm;
    }

    /**
     * Returns true if this is a local only domain.
     * <p>
     * An OI is a local-only domain if {@link #isLocal()} returns true (i.e., if it is an LOI).
     * 
     * @return boolean
     */
    @Override
    public boolean isLocalOnlyData() {

        return isLocal();
    }

    /**
     * isNational
     * 
     * @return boolean
     */
    public boolean isNational() {

        return OrderableItemType.NATIONAL.equals(getOrderableItemType());
    }

    /**
     * isLocal
     * 
     * @return boolean
     */
    public boolean isLocal() {

        return OrderableItemType.LOCAL.equals(getOrderableItemType());
    }

    /**
     * getOrderableItemType
     * 
     * @return orderableItemType property
     */
    public OrderableItemType getOrderableItemType() {

        return orderableItemType;
    }

    /**
     * setOrderableItemType
     * 
     * @param orderableItemType orderableItemType property
     */
    public void setOrderableItemType(OrderableItemType orderableItemType) {

        this.orderableItemType = orderableItemType;
    }

    /**
     * getOrderableItemParent
     * 
     * @return orderableItemParent property
     */
    public OrderableItemVo getOrderableItemParent() {

        return orderableItemParent;
    }

    /**
     * setOrderableItemParent
     * 
     * @param orderableItemParent orderableItemParent property
     */
    public void setOrderableItemParent(OrderableItemVo orderableItemParent) {

        if (this.orderableItemParent != null) {
            if ((orderableItemParent == null) || (orderableItemParent.getId() != this.orderableItemParent.getId())) {
                setPreviousOrderableItemParent(this.orderableItemParent);
            }
        }

        this.orderableItemParent = orderableItemParent;
    }

    /**
     * getPreviousOrderableItemParent
     * 
     * @return previousOrderableItemParent property
     */
    public OrderableItemVo getPreviousOrderableItemParent() {

        return getPreviousOrderableItem();
    }

    /**
     * setPreviousOrderableItemParent
     * 
     * @param previousOrderableItemParent previousOrderableItemParent property
     */
    public void setPreviousOrderableItemParent(OrderableItemVo previousOrderableItemParent) {

        setPreviousOrderableItem(previousOrderableItemParent);
    }

    /**
     * Gets the previous orderable item parent of this VO. Note that the 'parent' suffix has been removed from the methods
     * above, to be in line with the naming convention expected by ManagedItemVo.java:setPreviousParent() method.
     * 
     * @return previousOrderableItem property
     */
    public OrderableItemVo getPreviousOrderableItem() {

        return previousOrderableItemParent;
    }

    /**
     * Sets the previous orderable item parent of this VO. Note that the 'parent' suffix has been removed from the methods
     * above, to be in line with the naming convention expected by ManagedItemVo.java:setPreviousParent() method.
     * 
     * @param previousOrderableItem previousOrderableItem property
     */
    public void setPreviousOrderableItem(OrderableItemVo previousOrderableItem) {

        this.previousOrderableItemParent = previousOrderableItem;
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
     * List all uniqueness criteria for this ValueObject.
     * 
     * @return Set<FieldKey> All uniqueness fields for this object.
     */
    @Override
    public Set<FieldKey> listUniquenessCriteria() {

        Set<FieldKey> uniqueness = new HashSet<FieldKey>();
        uniqueness.add(FieldKey.OI_NAME);

        return uniqueness;
    }

    /**
     * This is the orderable Item short string method
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toShortString() {

        return this.getOiName();
    }

    /**
     * Finds any drug texts originally placed on the Orderable Item national list that the National manager removed and moves
     * them to the beginning of the Orderable Item local list.
     * <p>
     * Then finds duplicates on the National and Local lists, and removes those from the Local list.
     * 
     * @param localItem Local instance of Orderable Item to merge
     * @return FieldKey.LOCAL_DRUG_TEXTS
     */
    private FieldKey mergeDrugTexts(OrderableItemVo localItem) {

        List<DrugTextVo> newNationalLabels = getNationalDrugTexts();
        List<DrugTextVo> oldNationalLabels = localItem.getNationalDrugTexts();
        List<DrugTextVo> newLocalLabels = new ArrayList<DrugTextVo>(oldNationalLabels);
        newLocalLabels.removeAll(newNationalLabels);
        List<DrugTextVo> localLabels = localItem.getLocalDrugTexts();
        localLabels.addAll(0, newLocalLabels);
        localLabels.removeAll(newNationalLabels);
        setLocalDrugTexts(localLabels);

        return FieldKey.LOCAL_DRUG_TEXTS;
    }

//    /**
//     * Merge Labs fields from the given local OI.
//     * 
//     * @param localItem Local OI
//     * @return Collection of Labs FieldKeys
//     */
//    private Collection<FieldKey> mergeLabs(OrderableItemVo localItem) {
//
//        List<FieldKey> handledFields = new ArrayList<FieldKey>();
//
//        handledFields.add(FieldKey.LABS_ADMINISTRATION);
//        setLabDisplayAdministration(localItem.getLabDisplayAdministration());
//
//        handledFields.add(FieldKey.LABS_ORDER_FINISH);
//        setLabDisplayFinishingAnOrder(localItem.getLabDisplayFinishingAnOrder());
//
//        handledFields.add(FieldKey.LABS_ORDER_ENTRY);
//        setLabDisplayOrderEntry(localItem.getLabDisplayOrderEntry());
//
//        return handledFields;
//    }

//    /**
//     * Merge Vitals fields from the given local OI.
//     * 
//     * @param localItem Local OI
//     * @return Collection of Vitals FieldKeys
//     */
//    @SuppressWarnings("unused")
//    private Collection<FieldKey> mergeVitals(OrderableItemVo localItem) {
//
//        List<FieldKey> handledFields = new ArrayList<FieldKey>();
//
//        handledFields.add(FieldKey.VITALS_ADMINISTRATION);
//        setVitalsDisplayAdministration(localItem.getVitalsDisplayAdministration());
//
//        handledFields.add(FieldKey.VITALS_ORDER_FINISH);
//        setVitalsDisplayFinishAnOrder(localItem.getVitalsDisplayFinishAnOrder());
//
//        handledFields.add(FieldKey.VITALS_ORDER_ENTRY);
//        setVitalsDisplayOrderEntry(localItem.getVitalsDisplayOrderEntry());
//
//        return handledFields;
//    }

    /**
     * Strips out local only data from the OI
     * 
     * @return An Orderable Item minus any local only data
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#removeLocalData()
     */
    @Override
    public OrderableItemVo removeLocalData() {

        OrderableItemVo copy = this.copy();

        copy.setAdminSchedules(Collections.EMPTY_LIST);
        copy.setOiRoute(Collections.EMPTY_LIST);

        copy.setVitalsDisplayAdministration(Collections.EMPTY_LIST);
        copy.setVitalsDisplayFinishAnOrder(Collections.EMPTY_LIST);
        copy.setVitalsDisplayOrderEntry(Collections.EMPTY_LIST);

        copy.setLabDisplayAdministration(Collections.EMPTY_LIST);
        copy.setLabDisplayFinishingAnOrder(Collections.EMPTY_LIST);
        copy.setLabDisplayOrderEntry(Collections.EMPTY_LIST);

        copy.setOiRoute(Collections.EMPTY_LIST);
        copy.setLocalDrugTexts(Collections.EMPTY_LIST);

        copy.setLocalUse(false);
        copy.setPreviouslyMarkedForLocalUse(false);

        copy.getVaDataFields().removeDataField(FieldKey.APPLICATION_PACKAGE_USE);

        return copy;
    }

    /**
     * OrderableItemVo.
     * List all non-editable fields for this ValueObject. <BR>
     * Note: Though the VO's isEditable() could be used directly, using the authorization framework (via the returned list)
     * eases the implementation of the custom JSP tag files that render the fields.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All non-editable fields for this object.
     */
    @Override
    public Set<FieldKey> listNonEditableFields(Environment environment, Collection<Role> roles) {

        // OrderableItemVo
        Set<FieldKey> fields = new HashSet<FieldKey>();

        for (DataField dataField : getVaDataFields().getDataFields().values()) {
            if (!dataField.isEditable()) {
                fields.add(dataField.getKey());
            }
        }

        // return the orderablieItem Non editable fields.
        return fields;
    }

    /**
     * OrderableItemVo
     * List all required fields for this ValueObject, with the pre-condition that the current instance is not a read-only
     * one.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {

        // verified these fields and found that PEPS OI name, Formulary Status, and NonVA med are system data fields. we may
        // need to remove those
        // from required field list.Also Schedule Type is local only field. Therefore, need to be in an if block. All of
        // these fixes will be done in M5 Part II
        Set<FieldKey> fields = super.handleListRequiredFields(environment, roles);

        fields.add(FieldKey.DOSAGE_FORM);
        fields.add(FieldKey.OI_NAME);
        fields.add(FieldKey.VISTA_OI_NAME);
        fields.add(FieldKey.CATEGORIES);
        fields.add(FieldKey.OI_SCHEDULE_TYPE);

        if (environment.isLocal()) {
            fields.add(FieldKey.ORDERABLE_ITEM_TYPE);
        }

        return fields;
    }

    /**
     * Sub class handler method for merging the current National instance of a OrderableItemVo with the given Local
     * OrderableItemVo.
     * <p>
     * Returns a Collection of {@link FieldKey} which have been merged by this method. This will prevent the fields
     * represented by these FieldKeys from being overwritten again by future merging.
     * 
     * @param localItem the Local instance of {@link ManagedItemVo} to merge with National instance of {@link ManagedItemVo}
     * @return Collection of FieldKeys merged within this method
     */
    @Override
    protected Collection<FieldKey> handleMergeLocalFields(ManagedItemVo localItem) {

        OrderableItemVo localOrderableItem = (OrderableItemVo) localItem;

        List<FieldKey> handledFields = new ArrayList<FieldKey>();
        handledFields.add(mergeDrugTexts(localOrderableItem));

        return handledFields;
    }

    /**
     * List all second review fields for this ValueObject for the OrderableItemVo
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} for the current user
     * @return Set<FieldKey> all second review fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListSecondReviewFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = new HashSet<FieldKey>();
        fields.addAll(FieldKey.getSecondApprovalDataFields(EntityType.ORDERABLE_ITEM));
        fields.add(FieldKey.ITEM_STATUS);
        fields.add(FieldKey.INACTIVATION_DATE);

        return fields;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one for the OrderableItemVo
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        //DF67 - Now editable after creation
        //fields.add(FieldKey.FORMULARY_STATUS);

        //DF149
        //fields.add(FieldKey.NON_VA_MED);

        if (Environment.NATIONAL.equals(environment)) {
            fields.addAll(listNationalDisabledFields(roles));
        } else {
            fields.addAll(listLocalDisabledFields(roles));
        }

        return fields;
    }

    /**
     * Return a list of the fields disabled at Local.
     * 
     * @param roles Collection of {@link Role} for the current user
     * @return disabled fields represented as a Set of {@link FieldKey}
     */
    private Collection<? extends FieldKey> listLocalDisabledFields(Collection<Role> roles) {

        Set<FieldKey> fields = new HashSet<FieldKey>();
        fields.add(FieldKey.DOSAGE_FORM);
        fields.add(FieldKey.VISTA_OI_NAME);
        fields.add(FieldKey.NATIONAL_DRUG_TEXTS);
        fields.remove(FieldKey.OI_ROUTE);

        return fields;
    }

    /**
     * Return a list of the fields disabled at National.
     * 
     * @param roles Collection of {@link Role} for the current user
     * @return disabled fields represented as a Set of {@link FieldKey}
     */
    private Collection<? extends FieldKey> listNationalDisabledFields(Collection<Role> roles) {

        Set<FieldKey> fields = new HashSet<FieldKey>();
        fields.add(FieldKey.PREVIOUSLY_MARKED_FOR_LOCAL_USE);
        fields.add(FieldKey.LOCAL_USE);
        fields.add(FieldKey.LOCAL_DRUG_TEXTS);
        fields.add(FieldKey.OI_ROUTE);

        if (getRequestItemStatus().isPending()) {
            fields.removeAll(listUniquenessCriteria());
        }

        return fields;

    }

    /**
     * gets the default OI Route for the product
     * 
     * @return String that is the drug classification for the primary drug class
     */
    public String getDefaultOiRoute() {

        // iterate through the drug class collection and find the primary drug class
        OiRouteVo defaultOiRoute = null;

        for (OiRouteVo myRoute : getOiRoute()) {
            boolean def = myRoute.isDefaultRoute();

            if (def) {
                defaultOiRoute = myRoute;
                break;
            }
        }

        if ((defaultOiRoute == null) || (defaultOiRoute.getLocalMedicationRoute() == null)) {
            return "N/A";
        } else {
            return defaultOiRoute.getLocalMedicationRoute().getValue();
        }
    }

    /**
     * Set the given ManagedItemVo as this OrderableItem's parent OrderableItem.
     * 
     * @param parent ManagedItemVo
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#selectParent(gov.va.med.pharmacy.peps.common.vo.ManagedItemVo)
     */
    @Override
    public void selectParent(ManagedItemVo parent) {

        if (parent instanceof OrderableItemVo) {
            setOrderableItemParent((OrderableItemVo) parent);
        }
    }

    /**
     * Call {@link #setPreviousOrderableItemParent(OrderableItemVo)}
     * 
     * @param parent previous parent ManagedItemVo
     */
    @Override
    public void setPreviousParent(ManagedItemVo parent) {

        if (parent instanceof OrderableItemVo) {
            setPreviousOrderableItemParent((OrderableItemVo) parent);
        }
    }

    /**
     * Set the OI Type to {@link #NATIONAL}
     */
    public void setNational() {

        setOrderableItemType(OrderableItemType.NATIONAL);
    }

    /**
     * Set the OI Type to {@link #LOCAL}
     */
    public void setLocal() {

        setOrderableItemType(OrderableItemType.LOCAL);
    }

    /**
     * Create a blank OrderableItemVo template.
     * 
     * @param environment {@link Environment} in which the template is being made
     * @return OrderableItemVo
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#blankTemplate()
     */
    @Override
    public ManagedItemVo blankTemplate(Environment environment) {

        OrderableItemVo orderableItem = new OrderableItemVo();
        orderableItem.setOiName("");
        orderableItem.setProducts(Collections.EMPTY_LIST);
        orderableItem.setProductCount(0);
        orderableItem.setId(null);
        orderableItem.setItemStatus(ItemStatus.ACTIVE);
        orderableItem.setRequestItemStatus(RequestItemStatus.PENDING);
        orderableItem.setNonVaMed(true);

        orderableItem.setCategories(new ArrayList<Category>());

        if (environment.isNational()) {
            orderableItem.setNational();
        }

        orderableItem.setLocalUse(false);

        return orderableItem;
    }

    /**
     * Create a template from the given OrderableItemVo.
     * 
     * @param managedItemVo OrderableItemVo
     * @param environment {@link Environment} in which the template is being made
     * @return OrderableItemVo
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#makeTemplate(gov.va.med.pharmacy.peps.common.vo.ManagedItemVo)
     */
    @Override
    protected ManagedItemVo makeTemplate(ManagedItemVo managedItemVo, Environment environment) {

        OrderableItemVo orderableItem = (OrderableItemVo) managedItemVo;
        orderableItem.setCreatedBy(null);
        orderableItem.setModifiedBy(null);
        orderableItem.setCreatedDate(null);
        orderableItem.setModifiedDate(null);
        orderableItem.setId(null);
        orderableItem.setItemStatus(ItemStatus.ACTIVE);
        orderableItem.setRequestItemStatus(RequestItemStatus.PENDING);
        orderableItem.setOiName("");
        orderableItem.setProducts(Collections.EMPTY_LIST);
        orderableItem.setProductCount(null);
        orderableItem.makeAllDataFieldsEditable();
        orderableItem.setPreviouslyMarkedForLocalUse(false);
        orderableItem.setLocalUse(false);

        orderableItem.setNational();

        return orderableItem;
    }

    /**
     * Create a blank LOI template.
     * 
     * @param environment {@link Environment} in which the template is being made
     * @return OrderableItemVo
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#blankLocalTemplate()
     */
    @Override
    public ManagedItemVo blankLocalTemplate(Environment environment) {

        OrderableItemVo template = (OrderableItemVo) blankTemplate(environment);
        template.setLocal();

        // And if that OI is a national OI, then we are creating a new LOI from the existing NOI.
        if (isNational()) {
            template.setOrderableItemParent(this);
        } else {
            template.setOrderableItemParent(getOrderableItemParent());
        }

        return template;
    }

    /**
     * Retrieve a template instance of the local to the current national ManagedItemVo. Only applies for ManagedItemVo types
     * that have a national/local relationship, which currently is only OrderableItemVo. Default implementation returns a
     * template from {@link #template()}.
     * 
     * @param environment {@link Environment} in which the template is being made
     * @return OrderableItemVo
     */
    @Override
    public ManagedItemVo localTemplate(Environment environment) {

        OrderableItemVo template = (OrderableItemVo) template(environment);
        template.setLocal();

        if (isNational()) {
            template.setOrderableItemParent(this);
        } else {
            template.setOrderableItemParent(getOrderableItemParent());
        }

        return template;
    }

    /**
     * getNationalFormularyIndicator
     * 
     * @return nationalFormularyIndicator property
     */
    public boolean getNationalFormularyIndicator() {

        return nationalFormularyIndicator;
    }

    /**
     * setNationalFormularyIndicator
     * 
     * @param nationalFormularyIndicator nationalFormularyIndicator property
     */
    public void setNationalFormularyIndicator(boolean nationalFormularyIndicator) {

        this.nationalFormularyIndicator = nationalFormularyIndicator;
    }

    /**
     * getVistaOiName
     * 
     * @return oiVistaName property
     */
    public String getVistaOiName() {

        return vistaOiName;
    }

    /**
     * setVistaOiName
     * 
     * @param vistaOiName oiVistaName property
     */
    public void setVistaOiName(String vistaOiName) {
        if (StringUtils.isNotBlank(vistaOiName)) {
            this.vistaOiName = trimToEmpty(vistaOiName.toUpperCase(Locale.US));
        } else {
            this.vistaOiName = "";
        }
    }

    /**
     * getGenericName
     * 
     * @return genericName property
     */
    public GenericNameVo getGenericName() {

        return genericName;
    }

    /**
     * setGenericName
     * 
     * @param genericName genericName property
     */
    public void setGenericName(GenericNameVo genericName) {

        this.genericName = genericName;
    }

    /**
     * getLabDisplayAdministration in OrderableItemVo.
     * 
     * @return labDisplayAdministration collection
     */
    public Collection<LabDisplayAdministrationVo> getLabDisplayAdministration() {

        return labDisplayAdministration;
    }

    /**
     * setLabDisplayAdministration in OrderableItemVo.
     * 
     * @param labAdmin Collection of Lab during administration
     */
    public void setLabDisplayAdministration(Collection<LabDisplayAdministrationVo> labAdmin) {
        this.labDisplayAdministration = new ArrayList<LabDisplayAdministrationVo>();

        if (labAdmin != null && !labAdmin.isEmpty()) {
            this.labDisplayAdministration.addAll(labAdmin);
        }
    }

    /**
     * getLabDisplayOrderEntry in OrderableItemVo.
     * 
     * @return labDisplayOrderEntry collection
     */
    public Collection<LabDisplayOrderEntryVo> getLabDisplayOrderEntry() {

        return labDisplayOrderEntry;
    }

    /**
     * setLabDisplayOrderEntry in OrderableItemVo.
     *  in OrderableItemVo.
     * @param labOrder Collection of labs during order entry
     */
    public void setLabDisplayOrderEntry(Collection<LabDisplayOrderEntryVo> labOrder) {

        labDisplayOrderEntry = new ArrayList<LabDisplayOrderEntryVo>();

        // Add all the lab orders
        if (labOrder != null && !labOrder.isEmpty()) {
            this.labDisplayOrderEntry.addAll(labOrder);
        }
    }

    /**
     * getLabDisplayFinishingAnOrder in OrderableItemVo.
     * 
     * @return labDisplayFinishingAnOrder collection
     */
    public Collection<LabDisplayFinishingAnOrderVo> getLabDisplayFinishingAnOrder() {

        return labDisplayFinishingAnOrder;
    }

    /**
     * setLabDisplayFinishingAnOrder in OrderableItemVo.
     * 
     * @param labFinish Collection of Labs during finishing an order
     */
    public void setLabDisplayFinishingAnOrder(Collection<LabDisplayFinishingAnOrderVo> labFinish) {

        this.labDisplayFinishingAnOrder = new ArrayList<LabDisplayFinishingAnOrderVo>();

        // add the finishing of labs if they are not null or empty.
        if (labFinish != null && !labFinish.isEmpty()) {
            labDisplayFinishingAnOrder.addAll(labFinish);
        }
    }

    /**
     * getVitalsDisplayAdministration in OrderableItemVo.
     * 
     * @return vitalsDisplayAdministration
     */
    public Collection<VitalsDisplayAdministrationVo> getVitalsDisplayAdministration() {

        return vitalsDisplayAdministration;
    }

    /**
     * setVitalsDisplayAdministration in OrderableItemVo.
     * 
     * @param vitalsAdmin Collection of vitals during administration
     */
    public void setVitalsDisplayAdministration(Collection<VitalsDisplayAdministrationVo> vitalsAdmin) {

        this.vitalsDisplayAdministration = new ArrayList<VitalsDisplayAdministrationVo>();

        // set the vitalsDisplay of admin for the OI if they are not null.
        if (vitalsAdmin != null && !vitalsAdmin.isEmpty()) {
            this.vitalsDisplayAdministration.addAll(vitalsAdmin);
        }
    }

    /**
     * getVitalsDisplayOrderEntry in OrderableItemVo.
     * 
     * @return vitalsDisplayOrderEntry
     */
    public Collection<VitalsDisplayOrderEntryVo> getVitalsDisplayOrderEntry() {

        return vitalsDisplayOrderEntry;
    }

    /**
     * setVitalsDisplayOrderEntry in OrderableItemVo.
     * 
     * @param vitalsOrder collection of vitals during order entry
     */
    public void setVitalsDisplayOrderEntry(Collection<VitalsDisplayOrderEntryVo> vitalsOrder) {

        this.vitalsDisplayOrderEntry = new ArrayList<VitalsDisplayOrderEntryVo>();

        // if the vitals incoming for this set method has data.
        if (vitalsOrder != null && !vitalsOrder.isEmpty()) {
            this.vitalsDisplayOrderEntry.addAll(vitalsOrder);
        }
    }

    /**
     * getVitalsDisplayFinishAnOrder in OrderableItemVo.
     * 
     * @return vitalsDisplayFinishAnOrder
     */
    public Collection<VitalsDisplayFinishAnOrderVo> getVitalsDisplayFinishAnOrder() {

        return vitalsDisplayFinishAnOrder;
    }

    /**
     * setVitalsDisplayFinishAnOrder in OrderableItemVo.
     * 
     * @param vitalsFinish collection of vitals during finishing an order
     */
    public void setVitalsDisplayFinishAnOrder(Collection<VitalsDisplayFinishAnOrderVo> vitalsFinish) {

        this.vitalsDisplayFinishAnOrder = new ArrayList<VitalsDisplayFinishAnOrderVo>();

        if (vitalsFinish != null && !vitalsFinish.isEmpty()) {
            this.vitalsDisplayFinishAnOrder.addAll(vitalsFinish);
        }
    }

    /**
     * getLocalDrugTexts in OrderableItemVo.
     * 
     * @return drugTexts property
     */
    public List<DrugTextVo> getLocalDrugTexts() {

        return localDrugTexts;
    }

    /**
     * setLocalDrugTexts in OrderableItemVo.
     * 
     * @param localDrugTexts drugTexts property
     */
    public void setLocalDrugTexts(List<DrugTextVo> localDrugTexts) {

        // create the local drugText array
        this.localDrugTexts = new ArrayList<DrugTextVo>();

        if (localDrugTexts != null && !localDrugTexts.isEmpty()) {
            this.localDrugTexts.addAll(localDrugTexts);
        }
    }

    /**
     * getNationalDrugTexts in OrderableItemVo.
     * 
     * @return drugTexts property
     */
    public List<DrugTextVo> getNationalDrugTexts() {

        return nationalDrugTexts;
    }

    /**
     * setNationalDrugTexts in OrderableItemVo.
     * 
     * @param nationalDrugTexts drugTexts property
     */
    public void setNationalDrugTexts(List<DrugTextVo> nationalDrugTexts) {

        this.nationalDrugTexts = new ArrayList<DrugTextVo>();

        if (nationalDrugTexts != null && !nationalDrugTexts.isEmpty()) {
            this.nationalDrugTexts.addAll(nationalDrugTexts);
        }
    }

    /**
     * Retrieve the parent of this OrderableItemVo.
     * 
     * @return parent ManagedItemVo
     */
    @Override
    public ManagedItemVo getParent() {

        return this.getOrderableItemParent();
    }

    /**
     * Retrieve the previous parent of this OrderableItemVo.
     * 
     * @return previous parent ManagedItemVo
     */
    @Override
    public ManagedItemVo getPreviousParent() {

        return this.getPreviousOrderableItemParent();
    }

    /**
     * getDefaultSortedFieldKey
     * 
     * @return {@link FieldKey#OI_NAME}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#getDefaultSortedFieldKey()
     */
    @Override
    public FieldKey getDefaultSortedFieldKey() {

        return FieldKey.OI_NAME;
    }

    /**
     * getAdminSchedules
     * 
     * @return adminSchedules property
     */
    public Collection<AdministrationScheduleAssocVo> getAdminSchedules() {

        return adminSchedules;
    }

    /**
     * setAdminSchedules
     * 
     * @param adminSchedules adminSchedules property
     */
    public void setAdminSchedules(Collection<AdministrationScheduleAssocVo> adminSchedules) {

        this.adminSchedules = new ArrayList<AdministrationScheduleAssocVo>();

        if (adminSchedules != null && !adminSchedules.isEmpty()) {
            this.adminSchedules.addAll(adminSchedules);
        }
    }

    /**
     * getOiScheduleType
     * 
     * @return oiScheduleTypeVo property
     */
    public OiScheduleTypeVo getOiScheduleType() {

        return oiScheduleType;
    }

    /**
     * setOiScheduleType
     * 
     * @param oiScheduleTypeVo oiScheduleTypeVo property
     */
    public void setOiScheduleType(OiScheduleTypeVo oiScheduleTypeVo) {

        this.oiScheduleType = oiScheduleTypeVo;
    }

    /**
     * getNonVaMed
     * 
     * @return nonVaMed property
     */
    public Boolean getNonVaMed() {

        return nonVaMed;
    }

    /**
     * setNonVaMed
     * 
     * @param nonVaMed nonVaMed property
     */
    public void setNonVaMed(Boolean nonVaMed) {

        this.nonVaMed = nonVaMed;
    }

}
