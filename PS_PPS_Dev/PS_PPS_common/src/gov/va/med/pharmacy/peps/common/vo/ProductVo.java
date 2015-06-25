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

import org.apache.commons.lang.builder.HashCodeBuilder;

import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupListDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.common.vo.diff.IgnoreDifference;


/**
 * Data representing a VA Product.
 */
public class ProductVo extends NationalProductVo {

    private static final long serialVersionUID = 1L;


    private OrderableItemVo orderableItem;
    private OrderableItemVo previousOrderableItem; // Previously set orderable item.
    private Collection<NdcByOutpatientSiteNdcVo> ndcByOutpatientSiteNdc = new ArrayList<NdcByOutpatientSiteNdcVo>(0);
    
    private Long maxDosePerDay;
    private String tradeName;
    private String tallManLettering;
    private LabTestMonitorVo labTestMonitor;
    private Collection<SynonymVo> synonyms = new ArrayList<SynonymVo>(0);
    private List<DrugTextVo> localDrugTexts = new ArrayList<DrugTextVo>();


    private Collection<AtcCanisterVo> atcCanisters = new ArrayList<AtcCanisterVo>(0);
    private Collection<LocalPossibleDosagesVo> localPossibleDosages = new ArrayList<LocalPossibleDosagesVo>(0);

 

    @IgnoreDifference
    private List<NdcVo> ndcs = new ArrayList<NdcVo>(0);

    private int ndcCount;
    private String localSpecialHandling;
    private Long ien;

    private Collection<IfcapItemNumberVo> ifcapItemNumber = new ArrayList<IfcapItemNumberVo>();
    private SpecimenTypeVo specimenType;
    private String localPrintName;

    private AtcChoice atcChoice;
    private String atcMnemonic;
    private Long oneAtcCanister;
   

    // "lab.display.administration"
    // "lab.display.order.entry"
    // "lab.display.finishing.an.order"
    private Collection<LabDisplayAdministrationVo> labDisplayAdministration = new ArrayList<LabDisplayAdministrationVo>();
    private Collection<LabDisplayOrderEntryVo> labDisplayOrderEntry = new ArrayList<LabDisplayOrderEntryVo>();
    private Collection<LabDisplayFinishingAnOrderVo> labDisplayFinishingAnOrder =
        new ArrayList<LabDisplayFinishingAnOrderVo>();

    // "vitals.display.administration"
    // "vitals.display.order.entry"
    // "vitals.display.finish.an.order"
    private Collection<VitalsDisplayAdministrationVo> vitalsDisplayAdministration =
        new ArrayList<VitalsDisplayAdministrationVo>();
    private Collection<VitalsDisplayOrderEntryVo> vitalsDisplayOrderEntry = new ArrayList<VitalsDisplayOrderEntryVo>();
    private Collection<VitalsDisplayFinishAnOrderVo> vitalsDisplayFinishAnOrder =
        new ArrayList<VitalsDisplayFinishAnOrderVo>();

    @IgnoreDifference
    private Collection<SynonymVo> deletedSynonyms = new ArrayList<SynonymVo>();
    @IgnoreDifference
    private Collection<NationalPossibleDosagesVo> deletedPossibleDosages = new ArrayList<NationalPossibleDosagesVo>();

   
    
    private PossibleDosagesAutoCreate possibleDosagesAutoCreate;
    private ProductPackage productPackage;
    private String serviceCode;
    private Collection<ReducedCopayVo> reducedCopay = new ArrayList<ReducedCopayVo>();
    private FdbProductVo fdbProductVo;



    /**
     * getFdbProductVo
     * @return fdbProductVo
     */
    public FdbProductVo getFdbProductVo() {

        return fdbProductVo;
    }

    /**
     * setFdbProductVo
     * @param fdbProductVo fdbProductVo
     */
    public void setFdbProductVo(FdbProductVo fdbProductVo) {

        this.fdbProductVo = fdbProductVo;
    }

   

    /**
     * getReducedCopay for ProductVo.
     * @return reducedCopay
     */
    public Collection<ReducedCopayVo> getReducedCopay() {

        return reducedCopay;
    }

    /**
     * setReducedCopay
     * @param reducedCopay reducedCopay
     */
    public void setReducedCopay(Collection<ReducedCopayVo> reducedCopay) {

        this.reducedCopay = new ArrayList<ReducedCopayVo>();

        if (reducedCopay != null && !reducedCopay.isEmpty()) {
            this.reducedCopay.addAll(reducedCopay);
        }

    }

   

     /**
     * getPossibleDosagesAutoCreate
     * @return possibleDosagesAutoCreate
     */
    public PossibleDosagesAutoCreate getPossibleDosagesAutoCreate() {

        return possibleDosagesAutoCreate;
    }

    /**
     * setPossibleDosagesAutoCreate
     * @param possibleDosagesAutoCreate : possibleDosagesAutoCreate
     */
    public void setPossibleDosagesAutoCreate(PossibleDosagesAutoCreate possibleDosagesAutoCreate) {

        this.possibleDosagesAutoCreate = possibleDosagesAutoCreate;
    }

    /**
     * getProductPackage
     * @return productPackage
     */
    public ProductPackage getProductPackage() {

        return productPackage;
    }

    /**
     * setProductPackage
     * @param productPackage productPackage
     */
    public void setProductPackage(ProductPackage productPackage) {

        this.productPackage = productPackage;
    }

    /**
     * getServiceCode
     * @return serviceCode
     */
    public String getServiceCode() {

        return serviceCode;
    }

    /**
     * setServiceCode
     * @param serviceCode serviceCode
     */
    public void setServiceCode(String serviceCode) {

        this.serviceCode = serviceCode;
    }

   
    /**
     * getDeletedSynonyms
     * @return deletedSynonyms property
     */
    public Collection<SynonymVo> getDeletedSynonyms() {

        return deletedSynonyms;
    }

    /**
     * setDeletedSynonyms
     * @param deletedSynonyms deletedSynonyms property
     */
    public void setDeletedSynonyms(Collection<SynonymVo> deletedSynonyms) {

        this.deletedSynonyms = deletedSynonyms;
    }

    /**
     * getOrderableItem
     * @return orderableItem property
     */
    public OrderableItemVo getOrderableItem() {

        return orderableItem;
    }

    /**
     * setOrderableItem
     * @param orderableItem orderableItem property
     */
    public void setOrderableItem(OrderableItemVo orderableItem) {

        if (this.orderableItem != null) {
            if ((orderableItem == null) || (orderableItem.getId() != this.orderableItem.getId())) {
                setPreviousOrderableItem(this.orderableItem);
            }
        }

        this.orderableItem = orderableItem;
    }

    /**
     * getPreviousOrderableItem
     * @return previousOrderableItem property
     */
    public OrderableItemVo getPreviousOrderableItem() {

        return previousOrderableItem;
    }

    /**
     * setPreviousOrderableItem
     * @param previousOrderableItem orderableItem property
     */
    public void setPreviousOrderableItem(OrderableItemVo previousOrderableItem) {

        this.previousOrderableItem = previousOrderableItem;
    }


    /**
     * getNdcs
     * @return ndcs property
     */
    public List<NdcVo> getNdcs() {

        return ndcs;
    }

    /**
     * setNdcs
     * @param ndcs ndcs property
     */
    public void setNdcs(List<NdcVo> ndcs) {

        if (ndcs instanceof PaginatedList) {
            this.ndcs = ndcs;
        } else {
            this.ndcs = new ArrayList<NdcVo>();

            if (ndcs != null && !ndcs.isEmpty()) {
                this.ndcs.addAll(ndcs);
            }
        }
    }

    /**
     * If the List of NDCs, {@link #getNdcs()}, is null or empty, set the count of NDCs set when the Product was
     * instantiated.
     * 
     * The value of the count may not reflect the size of the NDC list if NDCs have been added to the List!
     * 
     * @param ndcCount ndcCount property
     */
    public void setNdcCount(int ndcCount) {

        this.ndcCount = ndcCount;
    }

    /**
     * If the List of NDCs, {@link #getNdcs()}, is null or empty, return the count of NDCs set when the Product was
     * instantiated.
     * 
     * The value of the count may not reflect the size of the NDC list if NDCs have been added to the List!
     * 
     * @return int
     */
    public int getNdcCount() {

        if (ndcs == null || ndcs.isEmpty()) {
            return ndcCount;
        } else {
            return ndcs.size();
        }
    }

    /**
     * getProductLongName
     * @return String
     */
    public String getProductLongName() {

        return getVaProductName();
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
     * Sets default values on local-only fields. Should be called when an item is being inserted from National so that
     * local-only fields have values which require them.
     * <p>
     * Calls {@link ManagedItemVo#defaultLocalOnlyFields()} and then sets default values for labs and vitals.
     */
    @Override
    public void defaultLocalOnlyFields() {

        super.defaultLocalOnlyFields();

        OrderableItemVo parent = this.getOrderableItem();
        this.labDisplayAdministration = parent.getLabDisplayAdministration();
        this.labDisplayFinishingAnOrder = parent.getLabDisplayFinishingAnOrder();
        this.labDisplayOrderEntry = parent.getLabDisplayOrderEntry();
        this.vitalsDisplayAdministration = parent.getVitalsDisplayAdministration();
        this.vitalsDisplayFinishAnOrder = parent.getVitalsDisplayFinishAnOrder();
        this.vitalsDisplayOrderEntry = parent.getVitalsDisplayOrderEntry();
    }

    /**
     * Returns true if this is a local only domain for the ProductVo.
     * 
     * @return true if the product has local only data which at national it never will.
     */
    @Override
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * toShortString returns toString unless overridden in ProductVo
     * 
     * @return String properties and values of the ValueObject
     */
    @Override
    public String toShortString() {

        return this.getVaProductName();
    }

    /**
     * List all Multiple Data Fields ({@link GroupListDataField}) whose Add functionality should be disabled for the given
     * {@link Role} in the given {@link Environment}.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All Multiple Data Fields whose Add functionality should be disabled
     */
    @Override
    public Set<FieldKey> listDisabledAddMultipleDataFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.listDisabledAddMultipleDataFields(environment, roles);

        if (roles.contains(Role.PSS_PPSN_MANAGER /*NATIONAL_SERVICE_MANAGER*/)) {
            fields.add(FieldKey.NATIONAL_POSSIBLE_DOSAGES);
        }

        return fields;
    }

    /**
     * List all Multiple Data Fields ({@link GroupListDataField}) whose Modify functionality should be disabled for the
     * given {@link Role} in the given {@link Environment}.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All Multiple Data Fields whose Modify functionality should be disabled
     */
    @Override
    public Set<FieldKey> listDisabledModifyMultipleDataFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.listDisabledModifyMultipleDataFields(environment, roles);

        if (roles.contains(Role.PSS_PPSN_MANAGER /*NATIONAL_SERVICE_MANAGER*/)) {
            fields.add(FieldKey.NATIONAL_POSSIBLE_DOSAGES);
        }

        return fields;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one for the ProductVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        fields.add(FieldKey.CATEGORIES);
        fields.add(FieldKey.EFFECTIVE_DATES);

        if (Environment.NATIONAL.equals(environment)) {
            fields.addAll(listNationalDisabledFields(roles));
        } else {
            fields.addAll(listLocalDisabledFields(roles));
        }

        if (roles.contains(Role.PSS_PPSN_MANAGER /*NATIONAL_SERVICE_MANAGER*/)) {
            fields.add(FieldKey.LOCAL_POSSIBLE_DOSAGES);
        }

        fields.remove(FieldKey.GENERIC_NAME);

        return fields;
    }

    /**
     * Return a list of the fields disabled at National.
     * <p>
     * Request Item status is always disabled. Product Inactivation date is always disabled. At National all the Local only
     * fields and VA Product Name field. are disabled for both pending and approved item.
     * 
     * @param roles Collection of {@link Role} for the current user
     * @return disabled fields represented as a Set of {@link FieldKey}
     */
    private Set<FieldKey> listNationalDisabledFields(Collection<Role> roles) {

        Set<FieldKey> fields = new HashSet<FieldKey>();

        // System DF's are not updated by the user
        //fields.add(FieldKey.CMOP_ID);        
        fields.add(FieldKey.IEN);
        fields.add(FieldKey.VUID);
        fields.add(FieldKey.DOSE);

        fields.add(FieldKey.LOCAL_DRUG_TEXTS);

        if (getRequestItemStatus().isApproved() || getRequestItemStatus().isPending()) {
            fields.addAll(FieldKey.getProductLocalOnlyDataFields());

            // atc canisters multiple only contains local only fields so disable
            fields.add(FieldKey.ATC_CANISTERS);

            // The field key BCMA_UNITS_PER_DOSE contained in the National.Possible.Dosages is
            // also a Local only field
            fields.add(FieldKey.BCMA_UNITS_PER_DOSE);

            // The field key OTHER_LANGUAGE_DOSAGE_NAME contained in Local.Possible.Dosages is local only
            fields.add(FieldKey.OTHER_LANGUAGE_DOSAGE_NAME);
        }

        // if pending get list of field keys and remove from disabled field list from national
        if (getRequestItemStatus().isPending()) {
            fields.removeAll(listUniquenessCriteria());
        }

        // If this item has been approved disable certain fields
        if (getRequestItemStatus().isApproved()) {
            fields.addAll(listUniquenessCriteria());
            fields.add(FieldKey.NATIONAL_FORMULARY_NAME);
            fields.add(FieldKey.NDF_PRODUCT_IEN);
        }

        fields.remove(FieldKey.MONITOR_ROUTINE);
        fields.remove(FieldKey.LAB_MONITOR_MARK);
        fields.remove(FieldKey.SERVICE_CODE);
        fields.remove(FieldKey.OVERRIDE_DF_DOSE_CHK_EXCLUSN);
        fields.remove(FieldKey.LOCAL_POSSIBLE_DOSAGES);

        return fields;
    }

    /**
     * Return a list of the fields disabled at Local.
     * <p>
     * At Local National Data Fields are disabled for approved and pending items. Request Item Status is always disabled.
     * Product Inactivation date is always disabled.
     * 
     * @param roles Collection of {@link Role} for the current user
     * @return disabled fields represented as a Set of {@link FieldKey}
     */
    private Set<FieldKey> listLocalDisabledFields(Collection<Role> roles) {

        Set<FieldKey> fields = new HashSet<FieldKey>();
        fields.add(FieldKey.CMOP_ID);
        fields.add(FieldKey.PREVIOUSLY_MARKED_FOR_LOCAL_USE);
        fields.add(FieldKey.DOSE);
        fields.add(FieldKey.NATIONAL_DRUG_TEXTS);

        // both national data fields and Non VA Data Fields are disabled
        if (getRequestItemStatus().isApproved() || getRequestItemStatus().isPending()) {

            // add national data fields to the collection
            fields.addAll(FieldKey.getProductNationalOnlyDataFields());

            // add non va data fields to the collection
            fields.addAll(FieldKey.getProductNonVaDataFields());

            // make all the local only fields editable
            fields.removeAll(FieldKey.getProductLocalOnlyDataFields());

            // Remove Multiple Datafields that are editable at local
            fields.remove(FieldKey.SYNONYMS);

            fields.remove(FieldKey.LOCAL_POSSIBLE_DOSAGES);
            fields.remove(FieldKey.ATC_CANISTERS);
            fields.remove(FieldKey.NATIONAL_POSSIBLE_DOSAGES);

            // Disable fields in National Possible Dosages that are not editable at Local
            fields.add(FieldKey.DOSE);

            if (getRequestItemStatus().isApproved()) {
                fields.remove(FieldKey.NATIONAL_POSSIBLE_DOSAGES);
            } else {

                // for pending items
                fields.add(FieldKey.NATIONAL_POSSIBLE_DOSAGES);
            }
        }

        fields.add(FieldKey.ITEM_STATUS);

        // get list of uniqueness fields and disable at local
        fields.addAll(listUniquenessCriteria());

        return fields;

    }

    /**
     * List all non-editable fields for this ValueObject. <BR>
     * Note: Though the VO's isEditable() could be used directly, using the authorization framework (via the returned list)
     * eases the implementation of the custom JSP tag files that render the fields.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All non-editable fields for this Product Object
     */
    @Override
    public Set<FieldKey> listNonEditableFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = new HashSet<FieldKey>();

        // for each data field, add the ones that are not editable to this list.
        for (DataField dataField : getVaDataFields().getDataFields().values()) {
            if (!dataField.isEditable()) {
                fields.add(dataField.getKey());
            }
        }

        return fields;
    }

    /**
     * List all required fields for this Product Value Object, with the pre-condition that the current 
     * instance is not a read-only one.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.handleListRequiredFields(environment, roles);

        fields.add(FieldKey.CS_FED_SCHEDULE);
        fields.add(FieldKey.DEA_SCHEDULE);
        fields.add(FieldKey.DRUG_CLASSES);
        fields.add(FieldKey.GENERIC_NAME);
        fields.add(FieldKey.OI_NAME);
        fields.add(FieldKey.VA_PRODUCT_NAME);
        fields.add(FieldKey.DISPENSE_UNIT);
        fields.add(FieldKey.VA_PRINT_NAME);
        fields.add(FieldKey.DISPENSE_UNIT);

        //fields.add(FieldKey.SERVICE_CODE);

        fields.add(FieldKey.CATEGORIES);

        fields.add(FieldKey.NCPDP_DISPENSE_UNIT);
        fields.add(FieldKey.NDCDP_QUANTITY_MULTIPLIER);

        if (environment.isNational()) {

            if (getRequestItemStatus() == RequestItemStatus.APPROVED || (getId() != null && !getId().contains("newItem"))) {
                fields.addAll(listMandatoryDuringSecondReviewFields(environment, roles));
            }

            fields.add(FieldKey.NATIONAL_FORMULARY_NAME);

            //fields.add(FieldKey.FORMULARY); //changed to local only
        } else {
            fields.add(FieldKey.LOCAL_PRINT_NAME);
        }

        return fields;
    }

    /**
     * List all second review fields for this ValueObject
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} for the current user
     * @return The set of FieldKeys for all second review fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListSecondReviewFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = new HashSet<FieldKey>();
        fields.addAll(FieldKey.getSecondApprovalDataFields(EntityType.PRODUCT));
        fields.add(FieldKey.ITEM_STATUS);
        fields.add(FieldKey.INACTIVATION_DATE);

        return fields;
    }

    /**
     * Sub class handler method for merging the current National instance of a {@link ManagedItemVo} with the given Local
     * {@link ManagedItemVo}.
     * <p>
     * Returns a Collection of {@link FieldKey} which have been merged by this method. This will prevent the fields
     * represented by these FieldKeys from being overwritten again by future merging.
     * 
     * @param localItem This is the local instance of the item that needs to be merged.
     * @return Collection of FieldKeys merged within this method
     */
    @Override
    protected Collection<FieldKey> handleMergeLocalFields(ManagedItemVo localItem) {

        ProductVo localProduct = (ProductVo) localItem;

        List<FieldKey> handledFields = new ArrayList<FieldKey>();
        handledFields.add(mergeSynonyms(localProduct));
        handledFields.add(mergePossibleDosages(localProduct));
        handledFields.add(mergeOrdeableItem(localProduct));
        handledFields.add(mergeDrugTexts(localProduct));

        return handledFields;
    }

    /**
     * List all fields which are mandatory during second review for this ValueObject
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} for the current user
     * @return Set<FieldKey> all fields which are mandatory during second review for this object.
     */
    @Override
    protected Set<FieldKey> handleMandatoryDuringSecondReviewFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = new HashSet<FieldKey>();
        fields.add(FieldKey.CMOP_ID); // Mandatory during 2nd approval on addition of product item        

        return fields;
    }

    /**
     * Loop through local synonyms list, compare with the national synonyms list.
     * <p>
     * If national list does contain the local synonym, then add to the national synonym list comparison is done by synonym
     * name, NDC code, and product intended use.
     * 
     * @param localProduct Local instance of ProductVo to merge
     * @return FieldKey.SYNONYMS
     */
    private FieldKey mergeSynonyms(ProductVo localProduct) {

        Collection<SynonymVo> localSynonyms = localProduct.getSynonyms();
        Collection<SynonymVo> nationalSynonyms = getSynonyms();

        Collection<SynonymVo> mergedSynonyms = new ArrayList<SynonymVo>();
        mergedSynonyms.addAll(localSynonyms);

        for (SynonymVo nationalSynonym : nationalSynonyms) {
            boolean existedAtLocal = false;
            SynonymVo synonymMatch = null;

            for (SynonymVo localSynonym : localSynonyms) {
                if (localSynonym.getSynonymName().equals(nationalSynonym.getSynonymName())) {
                    existedAtLocal = true;
                    synonymMatch = localSynonym;

                    // remove this synonym from mergedSynonyms Collection
                    mergedSynonyms.remove(localSynonym);
                    break;
                }

            }

            if (existedAtLocal) {

                // 1) Update a data field's value of the synonym entry if National updated the value to a different value.
                // 2) Keep the existing data field's value of the synonym entry if National left the field blank.
                // synonym intended use
                if ((nationalSynonym.getSynonymIntendedUse() != null)
                    && !isNullOrEmpty(nationalSynonym.getSynonymIntendedUse().getValue())) {
                    synonymMatch.setSynonymIntendedUse(nationalSynonym.getSynonymIntendedUse());
                }

                // synonym orderunit

                if ((nationalSynonym.getSynonymOrderUnit() != null)
                    && !isNullOrEmpty(nationalSynonym.getSynonymOrderUnit().getValue())) {
                    synonymMatch.setSynonymOrderUnit(nationalSynonym.getSynonymOrderUnit());
                }

                // synonym vendor
                if (!isNullOrEmpty(nationalSynonym.getSynonymVendor())) {
                    synonymMatch.setSynonymVendor(nationalSynonym.getSynonymVendor());
                }

                // synonym vsn
                if (!isNullOrEmpty(nationalSynonym.getSynonymVsn())) {
                    synonymMatch.setSynonymVsn(nationalSynonym.getSynonymVsn());
                }

                // ndc code
                if (!isNullOrEmpty(nationalSynonym.getNdcCode())) {
                    synonymMatch.setNdcCode(nationalSynonym.getNdcCode());
                }

                // price per order unit
                if (nationalSynonym.getSynonymPricePerOrderUnit() != null) {
                    synonymMatch.setSynonymPricePerOrderUnit(nationalSynonym.getSynonymPricePerOrderUnit());
                }

                // price per dispense unit
                if (nationalSynonym.getSynonymPricePerDispenseUnit() != null) {
                    synonymMatch.setSynonymPricePerDispenseUnit(nationalSynonym.getSynonymPricePerDispenseUnit());
                }

                // dispense unit per order unit
                if (nationalSynonym.getSynonymDispenseUnitPerOrderUnit() != null) {
                    synonymMatch.setSynonymDispenseUnitPerOrderUnit(nationalSynonym.getSynonymDispenseUnitPerOrderUnit());
                }

                mergedSynonyms.add(synonymMatch);
            } else {
                mergedSynonyms.add(nationalSynonym);
            }
        }

        for (SynonymVo localSynonym : localSynonyms) {
            for (SynonymVo deleted : deletedSynonyms) {
                if (localSynonym.equals(deleted)) {
                    mergedSynonyms.remove(localSynonym);
                }

            }
        }

        setSynonyms(mergedSynonyms);

        return FieldKey.SYNONYMS;
    }

    /**
     * loop through the possible dosages list at local and compare with the possible dosages deleted by national by matching
     * the uniqueness field dispense units per dose then delete that possible dosage
     * 
     * @param localProduct the product at local
     * @return FieldKey
     */
    private FieldKey mergePossibleDosages(ProductVo localProduct) {

        Collection<NationalPossibleDosagesVo> colPossibleDosagesLocal = localProduct.getNationalPossibleDosages();
        Collection<NationalPossibleDosagesVo> colRemovedPossibleDosages = new ArrayList<NationalPossibleDosagesVo>();

        for (NationalPossibleDosagesVo possibleDoseLocal : colPossibleDosagesLocal) {
            for (NationalPossibleDosagesVo deleted : deletedPossibleDosages) {
                if (possibleDoseLocal.getPossibleDosagesDispenseUnitsPerDose().equals(
                    deleted.getPossibleDosagesDispenseUnitsPerDose())) {
                    colRemovedPossibleDosages.add(possibleDoseLocal);
                }

            }
        }

        colPossibleDosagesLocal.removeAll(colRemovedPossibleDosages);

        return FieldKey.NATIONAL_POSSIBLE_DOSAGES;
    }

    /**
     * Merge a change to OI PARENT field, keeping the LOI parent intact if specified.
     * <p>
     * If 'this' national version of the item's OI parent is just the NOI of an local LOI assigned to this item, restore that
     * LOI parent association. Do not trigger the parent/child swapping logic!
     * <p>
     * Else, national changed the NOI parent of the product and indicate OI parent change, to be handled with parent swapping
     * business rules during update process.
     * 
     * @param localProduct Local instance of ProductVo to merge
     * @return FieldKey.ORDERABLE_ITEM
     */
    private FieldKey mergeOrdeableItem(ProductVo localProduct) {

        OrderableItemVo localOiParent = localProduct.getOrderableItem();
        OrderableItemVo nationalOiParent = this.getOrderableItem();

        if (!nationalOiParent.getId().equals(localOiParent.getId())) {
            if (localOiParent.isLocal() && localOiParent.getOrderableItemParent() != null
                && nationalOiParent.getId().equals(localOiParent.getOrderableItemParent().getId())) {
                this.setOrderableItem(localOiParent);
                this.setPreviousOrderableItem(null);
            } else {
                this.setPreviousOrderableItem(localOiParent);
            }
        }

        return FieldKey.ORDERABLE_ITEM;
    }

    /**
     * Finds any drug texts originally placed on the Product's national list that the National manager removed and moves them
     * to the beginning of the Product's local list.
     * <p>
     * Then finds duplicates on the National and Local lists, and removes those from the Local list.
     * 
     * @param localProduct Local instance of ProductVo to merge
     * @return FieldKey.LOCAL_DRUG_TEXTS
     */
    private FieldKey mergeDrugTexts(ProductVo localProduct) {

        List<DrugTextVo> newNationalLabels = getNationalDrugTexts();
        List<DrugTextVo> oldNationalLabels = localProduct.getNationalDrugTexts();
        List<DrugTextVo> newLocalLabels = new ArrayList<DrugTextVo>(oldNationalLabels);
        newLocalLabels.removeAll(newNationalLabels);
        List<DrugTextVo> localLabels = localProduct.getLocalDrugTexts();
        localLabels.addAll(0, newLocalLabels);
        localLabels.removeAll(newNationalLabels);
        setLocalDrugTexts(localLabels);

        return FieldKey.LOCAL_DRUG_TEXTS;
    }

  
    /**
     * getMaxDosePerDay
     * @return maxDosePerDay property
     */
    public Long getMaxDosePerDay() {

        return maxDosePerDay;
    }

    /**
     * setMaxDosePerDay
     * @param maxDosePerDay maxDosePerDay property
     */
    public void setMaxDosePerDay(Long maxDosePerDay) {

        this.maxDosePerDay = maxDosePerDay;
    }

    /**
     * Gets the synonyms from product
     * 
     * @return synonym names
     */
    public String getDisplayableSynonymName() {

        StringBuffer str = new StringBuffer();

        int i = 0;

        for (SynonymVo ing : synonyms) {

            str.append(ing.getSynonymName());
            i++;

            if (i < synonyms.size()) {
                str.append("<p>");
            }

        }

        return str.toString();
    }

    

  
    /**
     * this private method tests if a string is null or empty
     * 
     * @param str the String
     * @return boolean
     */
    private boolean isNullOrEmpty(String str) {

        if ((str != null) && (str.length() > 0)) {
            return false;
        }

        return true;
    }

    /**
     * getTallManLettering
     * @return tallManLettering property
     */
    public String getTallManLettering() {

        return tallManLettering;
    }

    /**
     * setTallManLettering
     * @param tallManLettering tallManLettering property
     */
    public void setTallManLettering(String tallManLettering) {

        this.tallManLettering = trimToEmpty(tallManLettering);
    }

    /**
     * getAtcCanisters
     * @return atcCanisters property
     */
    public Collection<AtcCanisterVo> getAtcCanisters() {

        return atcCanisters;
    }

    /**
     * setAtcCanisters
     * @param atcCanisters atcCanisters property
     */
    public void setAtcCanisters(Collection<AtcCanisterVo> atcCanisters) {

        this.atcCanisters = new ArrayList<AtcCanisterVo>();

        if (atcCanisters != null && !atcCanisters.isEmpty()) {
            this.atcCanisters.addAll(atcCanisters);
        }
    }

    /**
     * getAtcMnemonic
     * 
     * @return String The Product's ATC Mnemonic setting.
     */
    public String getAtcMnemonic() {

        return atcMnemonic;
    }

    /**
     * setAtcMnemonic
     * 
     * @param atcMnemonic The ATC Mnemonic value to set on this Product.
     */
    public void setAtcMnemonic(String atcMnemonic) {

        this.atcMnemonic = trimToEmpty(atcMnemonic);
    }

    /**
     * getOneAtcCanister
     * 
     * @return Long The ATC Canister number configured for this Product, valid only if in the One ATC mode.
     */
    public Long getOneAtcCanister() {

        return oneAtcCanister;
    }

    /**
     * setOneAtcCanister
     * 
     * @param oneAtcCanister The ATC Canister number to configure for this Product, valid only if in One ATC mode.
     */
    public void setOneAtcCanister(Long oneAtcCanister) {

        this.oneAtcCanister = oneAtcCanister;
    }

    /**
     * getAtcChoice
     * 
     * @return AtcChoice The currently selected ATC choice for this product.
     */
    public AtcChoice getAtcChoice() {

        return this.atcChoice;
    }

    /**
     * setAtcChoice
     * 
     * @param atcChoice The ATC mode choice to configure for this Product.
     */
    public void setAtcChoice(AtcChoice atcChoice) {

        this.atcChoice = atcChoice;
    }

    /**
     * isAtcChoiceOne
     * 
     * @return boolean True if this is a one-ATC mode product.
     */
    public boolean isAtcChoiceOne() {

        return (this.atcChoice != null && AtcChoice.ONE_ATC_MODE.equals(this.atcChoice));
    }

    /**
     * isAtcChoiceMultiple
     * 
     * @return boolean True if this is a multiple-ATCs mode product.
     */
    public boolean isAtcChoiceMultiple() {

        return (this.atcChoice != null && AtcChoice.MULTIPLE_ATCS_MODE.equals(this.atcChoice));
    }

    /**
     * isAtcChoiceNotSelected
     * 
     * @return boolean True if not ATC mode has been selected for this product.
     */
    public boolean isAtcChoiceNotSelected() {

        return (this.atcChoice == null);
    }

    /**
     * getLocalPossibleDosages
     * @return localPossibleDossages property
     */
    public Collection<LocalPossibleDosagesVo> getLocalPossibleDosages() {

        return localPossibleDosages;
    }

    /**
     * setLocalPossibleDosages
     * @param localPossibleDosages property
     */
    public void setLocalPossibleDosages(Collection<LocalPossibleDosagesVo> localPossibleDosages) {

        this.localPossibleDosages = new ArrayList<LocalPossibleDosagesVo>();

        if (localPossibleDosages != null && !localPossibleDosages.isEmpty()) {
            this.localPossibleDosages.addAll(localPossibleDosages);
        }
    }

    /**
     * localPossibleDosages
     * 
     * @param lPossibleDosages property
     */
    public void setLocalPossibleDossagesArray(Collection<LocalPossibleDosagesVo> lPossibleDosages) {

        this.localPossibleDosages = new ArrayList<LocalPossibleDosagesVo>();

        if (lPossibleDosages != null && !lPossibleDosages.isEmpty()) {
            this.localPossibleDosages.addAll(lPossibleDosages);
        }
    }

    

    /**
     * getTradeName
     * @return tradeName property
     */
    public String getTradeName() {

        return tradeName;
    }

    /**
     * setTradeName
     * @param tradeName tradeName property
     */
    public void setTradeName(String tradeName) {

        this.tradeName = trimToEmpty(tradeName);
    }

    /**
     * getSynonyms
     * @return synonyms property
     */
    public Collection<SynonymVo> getSynonyms() {

        return synonyms;
    }

    /**
     * setSynonyms
     * @param synonyms synonyms property
     */
    public void setSynonyms(Collection<SynonymVo> synonyms) {

        this.synonyms = new ArrayList<SynonymVo>();

        if (synonyms != null && !synonyms.isEmpty()) {
            this.synonyms.addAll(synonyms);
        }
    }

    /**
     * Set the given ManagedItemVo as this Product's parent OrderableItem.
     * 
     * @param parent ManagedItemVo
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#selectParent(gov.va.med.pharmacy.peps.common.vo.ManagedItemVo)
     */
    @Override
    public void selectParent(ManagedItemVo parent) {

        if (parent instanceof OrderableItemVo) {
            OrderableItemVo orderParent = (OrderableItemVo) (parent);

            setOrderableItem(orderParent);
            this.labDisplayAdministration = orderParent.getLabDisplayAdministration();
            this.labDisplayFinishingAnOrder = orderParent.getLabDisplayFinishingAnOrder();
            this.labDisplayOrderEntry = orderParent.getLabDisplayOrderEntry();
            this.vitalsDisplayAdministration = orderParent.getVitalsDisplayAdministration();
            this.vitalsDisplayFinishAnOrder = orderParent.getVitalsDisplayFinishAnOrder();
            this.vitalsDisplayOrderEntry = orderParent.getVitalsDisplayOrderEntry();
        }
    }

  

   
    /**
     * see ManagedItemVo.updateFromModDifferences.
     * 
     * This method is specifically over riden on product to handle Synonym updates. If there are any changes to the Synonyms,
     * we keep track of them in a private variable so that the local can determine what synonym was deleted. Otherwise this
     * method simply calls the super
     * 
     * @param modDifferences The collection of field-level differences that need to be applied to this item.
     * @param user {@link UserVo} performing the modifications
     * @param environmentUtility {@link EnvironmentUtility} to retrieve current environment information
     */
    @Override
    public void updateFromModDifferences(Collection<ModDifferenceVo> modDifferences, UserVo user,
        EnvironmentUtility environmentUtility) {

        super.updateFromModDifferences(modDifferences, user, environmentUtility);

        for (ModDifferenceVo mod : modDifferences) {
            if (mod.getDifference().getFieldKey().equals(FieldKey.SYNONYMS)) {
                deletedSynonyms = new ArrayList<SynonymVo>();

                Collection<SynonymVo> oldValues = (Collection<SynonymVo>) mod.getDifference().getOldValue();
                Collection<SynonymVo> newValues = (Collection<SynonymVo>) mod.getDifference().getNewValue();

                // Slow, but I can't think of another way to identify deletions vs modifications
                for (SynonymVo old : oldValues) {
                    Long synId = old.getId();

                    boolean contains = false;

                    for (SynonymVo newVal : newValues) {
                        if (newVal.getId() != null) {
                            contains |= (newVal.getId().equals(synId));
                        }// end if

                    }// end for

                    if (!contains) {
                        deletedSynonyms.add(old);
                    }// end if

                }// end for
            }// end if

            // possible dosage deleted by national
            if ((FieldKey.NATIONAL_POSSIBLE_DOSAGES.equals(mod.getDifference().getFieldKey()))
                && environmentUtility.isNational()) {
                deletedPossibleDosages = new ArrayList<NationalPossibleDosagesVo>();
                Collection<NationalPossibleDosagesVo> oldValues =
                    (Collection<NationalPossibleDosagesVo>) mod.getDifference().getOldValue();
                Collection<NationalPossibleDosagesVo> newValues =
                    (Collection<NationalPossibleDosagesVo>) mod.getDifference().getNewValue();

                for (NationalPossibleDosagesVo old : oldValues) {
                    Long possibleDoseId = old.getId();

                    boolean contains = false;

                    for (NationalPossibleDosagesVo newVal : newValues) {
                        if (newVal.getId() != null) {
                            contains |= (newVal.getId().equals(possibleDoseId));
                        }// end if

                    }// end for

                    if (!contains) {
                        deletedPossibleDosages.add(old);
                    }// end if

                }// end for
            }
        }// end for

    }

  


    

    /**
     * Returns True if the collection of ModDifferences contains a change to a uniqueness field on this ManagedItem
     * 
     * false otherwise
     * 
     * @param differences Accepted Mod Differences to this item
     * @return boolean
     */
    @Override
    public boolean hasUpdatedUniquenessCriteria(Collection<ModDifferenceVo> differences) {

        for (ModDifferenceVo modDiff : differences) {
            if (FieldKey.VA_PRODUCT_NAME.equals(modDiff.getDifference().getFieldKey())) {
                return true;
            }
        }

        return false;
    }

   

    /**
     * getSpecimenType
     * 
     * @return specimenType property
     */
    public SpecimenTypeVo getSpecimenType() {

        return specimenType;
    }

    /**
     * setSpecimenType
     * 
     * @param specimenType of type SpecimenTypeVo
     */
    public void setSpecimenType(SpecimenTypeVo specimenType) {

        this.specimenType = specimenType;
    }

    /**
     * getIfcapItemNumber
     * @return ifcapItemNumbers property
     */
    public Collection<IfcapItemNumberVo> getIfcapItemNumber() {

        return ifcapItemNumber;
    }

    /**
     * setIfcapItemNumber
     * @param ifcapItemNumbers ifcapItemNumbers property
     */
    public void setIfcapItemNumber(Collection<IfcapItemNumberVo> ifcapItemNumbers) {

        this.ifcapItemNumber = new ArrayList<IfcapItemNumberVo>();

        if (ifcapItemNumbers != null && !ifcapItemNumbers.isEmpty()) {
            this.ifcapItemNumber.addAll(ifcapItemNumbers);
        }
    }

    /**
     * gets the dosage form from the orderable item
     * 
     * @return DosageFormVo the dosage form for the orderable item
     */
    public DosageFormVo getDosageForm() {

        DosageFormVo dosageForm = null;

        if (orderableItem != null) {
            dosageForm = orderableItem.getDosageForm();
        }

        return dosageForm;
    }

    /**
     * getDefaultSortedFieldKey
     * @return {@link FieldKey#VA_PRODUCT_NAME}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#getDefaultSortedFieldKey()
     */
    @Override
    public FieldKey getDefaultSortedFieldKey() {

        return FieldKey.VA_PRODUCT_NAME;
    }

    /**
     * getDefaultSearchSortedFieldKey
     * 
     * @return {@link FieldKey#GENERIC_NAME}
     */
    @Override
    public FieldKey getDefaultSearchSortedFieldKey() {

        return FieldKey.GENERIC_NAME;
    }

    /**
     * getLocalDrugTexts
     * @return drugTexts property
     */
    public List<DrugTextVo> getLocalDrugTexts() {

        return localDrugTexts;
    }

    /**
     * setLocalDrugTexts
     * @param localDrugTexts drugTexts property
     */
    public void setLocalDrugTexts(List<DrugTextVo> localDrugTexts) {

        this.localDrugTexts = new ArrayList<DrugTextVo>();

        if (localDrugTexts != null && !localDrugTexts.isEmpty()) {
            this.localDrugTexts.addAll(localDrugTexts);
        }
    }

   

    /**
     * getLabTestMonitor
     * @return labTestMonitor property
     */
    public LabTestMonitorVo getLabTestMonitor() {

        return labTestMonitor;
    }

    /**
     * setLabTestMonitor
     * @param labTestMonitor labTestMonitor property
     */
    public void setLabTestMonitor(LabTestMonitorVo labTestMonitor) {

        this.labTestMonitor = labTestMonitor;
    }

    /**
     * getNdcByOutpatientSiteNdc
     * @return ndcByOutpatientSiteNdc property
     */
    public Collection<NdcByOutpatientSiteNdcVo> getNdcByOutpatientSiteNdc() {

        return ndcByOutpatientSiteNdc;
    }

    /**
     * setNdcByOutpatientSiteNdc
     * @param ndcByOutpatientSiteNdc ndcByOutpatientSiteNdc property
     */
    public void setNdcByOutpatientSiteNdc(Collection<NdcByOutpatientSiteNdcVo> ndcByOutpatientSiteNdc) {

        this.ndcByOutpatientSiteNdc = new ArrayList<NdcByOutpatientSiteNdcVo>();

        if (ndcByOutpatientSiteNdc != null && !ndcByOutpatientSiteNdc.isEmpty()) {
            this.ndcByOutpatientSiteNdc.addAll(ndcByOutpatientSiteNdc);
        }
    }

    /**
     * getIen
     * @return ien property
     */
    public Long getIen() {

        return ien;
    }

    /**
     * setIen
     * @param ien ien property
     */
    public void setIen(Long ien) {

        this.ien = ien;
    }

    /**
     * getLabDisplayAdministration
     * 
     * @return labDisplayAdministration collection
     */
    public Collection<LabDisplayAdministrationVo> getLabDisplayAdministration() {

        return labDisplayAdministration;
    }

    /**
     * setLabDisplayAdministration
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
     * getLabDisplayOrderEntry
     * 
     * @return labDisplayOrderEntry collection
     */
    public Collection<LabDisplayOrderEntryVo> getLabDisplayOrderEntry() {

        return labDisplayOrderEntry;
    }

    /**
     * setLabDisplayOrderEntry
     * 
     * @param labOrder Collection of labs during order entry
     */
    public void setLabDisplayOrderEntry(Collection<LabDisplayOrderEntryVo> labOrder) {

        this.labDisplayOrderEntry = new ArrayList<LabDisplayOrderEntryVo>();

        if (labOrder != null && !labOrder.isEmpty()) {
            this.labDisplayOrderEntry.addAll(labOrder);
        }
    }

    /**
     * getLabDisplayFinishingAnOrder
     * 
     * @return labDisplayFinishingAnOrder collection
     */
    public Collection<LabDisplayFinishingAnOrderVo> getLabDisplayFinishingAnOrder() {

        return labDisplayFinishingAnOrder;
    }

    /**
     * setLabDisplayFinishingAnOrder
     * 
     * @param labFinish Collection of Labs during finishing an order
     */
    public void setLabDisplayFinishingAnOrder(Collection<LabDisplayFinishingAnOrderVo> labFinish) {

        this.labDisplayFinishingAnOrder = new ArrayList<LabDisplayFinishingAnOrderVo>();

        if (labFinish != null && !labFinish.isEmpty()) {
            labDisplayFinishingAnOrder.addAll(labFinish);
        }
    }

    /**
     * getVitalsDisplayAdministration
     * 
     * @return vitalsDisplayAdministration
     */
    public Collection<VitalsDisplayAdministrationVo> getVitalsDisplayAdministration() {

        return vitalsDisplayAdministration;
    }

    /**
     * setVitalsDisplayAdministration
     * 
     * @param vitalsAdmin Collection of vitals during administration
     */
    public void setVitalsDisplayAdministration(Collection<VitalsDisplayAdministrationVo> vitalsAdmin) {

        this.vitalsDisplayAdministration = new ArrayList<VitalsDisplayAdministrationVo>();

        if (vitalsAdmin != null && !vitalsAdmin.isEmpty()) {
            this.vitalsDisplayAdministration.addAll(vitalsAdmin);
        }
    }

    /**
     * getVitalsDisplayOrderEntry
     * 
     * @return vitalsDisplayOrderEntry
     */
    public Collection<VitalsDisplayOrderEntryVo> getVitalsDisplayOrderEntry() {

        return vitalsDisplayOrderEntry;
    }

    /**
     * setVitalsDisplayOrderEntry
     * 
     * @param vitalsOrder collection of vitals during order entry
     */
    public void setVitalsDisplayOrderEntry(Collection<VitalsDisplayOrderEntryVo> vitalsOrder) {

        this.vitalsDisplayOrderEntry = new ArrayList<VitalsDisplayOrderEntryVo>();

        if (vitalsOrder != null && !vitalsOrder.isEmpty()) {
            this.vitalsDisplayOrderEntry.addAll(vitalsOrder);
        }
    }

    /**
     * getVitalsDisplayFinishAnOrder
     * 
     * @return vitalsDisplayFinishAnOrder
     */
    public Collection<VitalsDisplayFinishAnOrderVo> getVitalsDisplayFinishAnOrder() {

        return vitalsDisplayFinishAnOrder;
    }

    /**
     * setVitalsDisplayFinishAnOrder
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
     * getLocalPrintName
     * @return localPrintName property
     */
    public String getLocalPrintName() {

        return localPrintName;
    }

    /**
     * setLocalPrintName
     * @param localPrintName localPrintName property
     */
    public void setLocalPrintName(String localPrintName) {

        this.localPrintName = localPrintName;
    }

    /**
     * getLocalSpecialHandling
     * @return localSpecialHandling property
     */
    public String getLocalSpecialHandling() {

        return localSpecialHandling;
    }

    /**
     * setLocalSpecialHandling
     * @param localSpecialHandling localSpecialHandling property
     */
    public void setLocalSpecialHandling(String localSpecialHandling) {

        this.localSpecialHandling = localSpecialHandling;
    }

    /**
     * this method checks if the product has only one ingredient
     * 
     * @return boolean
     */
    public boolean isSingleIngredient() {

        if (getActiveIngredients().size() == 1) {
            return true;
        }

        return false;

    }

    /**
     * Clone this instance of {@link ManagedItemVo} and then remove local-only data from the item that should not be sent up
     * to National in a request.
     * <p>
     * Products should not send up a LOI parent, instead the NOI parent of the LOI is used. Locally selected warning labels
     * should also not be sent to National.
     * 
     * @return copy of Local instance of {@link ManagedItemVo} without local-only data
     */
    @Override
    public ProductVo removeLocalData() {

        ProductVo copy = copy();

        if ((getOrderableItem() != null) && (getOrderableItem().isLocal())) {
            copy.setOrderableItem(getOrderableItem().getOrderableItemParent());

            // when sending data to national need to strip out the local orderable item
            copy.setPreviousOrderableItem(null);

        }

        copy.setVitalsDisplayAdministration(Collections.EMPTY_LIST);
        copy.setVitalsDisplayFinishAnOrder(Collections.EMPTY_LIST);
        copy.setVitalsDisplayOrderEntry(Collections.EMPTY_LIST);

        copy.setLocalDrugTexts(Collections.EMPTY_LIST);

        copy.setIfcapItemNumber(Collections.EMPTY_LIST);

        copy.setLocalPossibleDosages(Collections.EMPTY_LIST);
        copy.setNdcByOutpatientSiteNdc(Collections.EMPTY_LIST);
        copy.setAtcCanisters(Collections.EMPTY_LIST);

        copy.setLocalUse(false);
        copy.setAtcMnemonic(null);
        copy.setSpecimenType(null);
        copy.setLabTestMonitor(null);
        copy.setIen(null);
        copy.setAtcChoice(null);
        copy.setOneAtcCanister(null);

        copy.setPreviouslyMarkedForLocalUse(false);

        copy.getVaDataFields().removeDataField(FieldKey.ACTION_PROFILE_MESSAGE);
        copy.getVaDataFields().removeDataField(FieldKey.APPLICATION_PACKAGE_USE);
        copy.getVaDataFields().removeDataField(FieldKey.TRANSMIT_TO_CMOP);
        copy.getVaDataFields().removeDataField(FieldKey.CORRESPONDING_INPATIENT_DRUG);
        copy.getVaDataFields().removeDataField(FieldKey.CURRENT_INVENTORY);
        copy.getVaDataFields().removeDataField(FieldKey.INPATIENT_PHARMACY_LOCATION);
        copy.getVaDataFields().removeDataField(FieldKey.LOCAL_NON_FORMULARY);
        copy.getVaDataFields().removeDataField(FieldKey.NORMAL_AMOUNT_TO_ORDER);
        copy.getVaDataFields().removeDataField(FieldKey.OP_EXTERNAL_DISPENSE);
        copy.getVaDataFields().removeDataField(FieldKey.PRODUCT_PRICE_PER_DISPENSE_UNIT);
        copy.getVaDataFields().removeDataField(FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT);
        copy.getVaDataFields().removeDataField(FieldKey.REORDER_LEVEL);
        copy.getVaDataFields().removeDataField(FieldKey.LOCAL_SPECIAL_HANDLING);

        return copy;
    }



    /**
     * getDeletedPossibleDosages
     * 
     * @return deletedPossibleDosages property
     */
    public Collection<NationalPossibleDosagesVo> getDeletedPossibleDosages() {

        return deletedPossibleDosages;
    }

    /**
     * setOrderUnit
     * 
     * @param delPossDosages property
     */
    public void setOrderUnit(Collection<NationalPossibleDosagesVo> delPossDosages) {

        this.deletedPossibleDosages = delPossDosages;
    }

  

    /**
     * Tests if this product is marked for unit-dose application package use.
     * 
     * @return boolean True if this product is marked for unit dose.
     */
    public boolean isMarkedForUnitDose() {

        DataFields<DataField> vadfs = getVaDataFields();

        ListDataField<String> appPkgUse = vadfs.getDataField(FieldKey.APPLICATION_PACKAGE_USE);

        return (isLocalUse() && (appPkgUse != null) && (appPkgUse.contains("U-UNIT DOSE")));

    }

    /**
     * Create the text to use as a link for the Product's active ingredients.
     * 
     * @param locale current {@link Locale}
     * @return String the text to use as a link
     */
    public String createActiveIngredientsLinkText(Locale locale) {

        StringBuffer text = new StringBuffer();

        text.append(FieldKey.ACTIVE_INGREDIENT.getLocalizedName(locale, getEntityType()));
        text.append(" (");

        if (getActiveIngredients() == null) {
            text.append("0");
        } else {
            text.append(getActiveIngredients().size());
        }

        // add the closing paragrah character.
        text.append(")");

        if (getActiveIngredients() != null && !getActiveIngredients().isEmpty()) {
            text.append(" ");
            text.append(getActiveIngredients().iterator().next().getIngredient().getValue());
        }

        return text.toString();
    }

    /**
     * 
     * Create the text to use as part of the link for the Product's active ingredients.
     *
     * @return String the text to use as a link
     */
    public String getActiveIngredientsLinkText() {

        StringBuffer text = new StringBuffer();
        text.append(" (");

        if (getActiveIngredients() == null) {
            text.append("0");
        } else {
            text.append(getActiveIngredients().size());
        }

        text.append(")");

        if (getActiveIngredients() != null && !getActiveIngredients().isEmpty()) {
            text.append(" ");
            text.append(getActiveIngredients().iterator().next().getIngredient().getValue());
        }

        return text.toString();
    }

    /**
     * Create the text to use as a link for the Product's synonyms.
     * 
     * @param locale current {@link Locale}
     * @return String the text to use as a link
     */
    public String createSynonymsLinkText(Locale locale) {

        StringBuffer text = new StringBuffer();

        text.append(FieldKey.SYNONYMS.getLocalizedName(locale, getEntityType()));
        text.append(" (");

        if (getSynonyms() == null) {
            text.append("0");
        } else {
            text.append(getSynonyms().size());
        }

        // Need to ensure you put the ')' at the end of the string.
        text.append(")");

        if (getSynonyms() != null && !getSynonyms().isEmpty()) {
            text.append(" ");
            text.append(getSynonyms().iterator().next().getSynonymName());
        }

        return text.toString();
    }

    /**
     * Create the text to use as a part of the link for the Product's synonyms.
     *  
     * @return String the text to use as a link
     */
    public String getSynonymsLinkText() {

        StringBuffer text = new StringBuffer();
        text.append(" (");

        if (getSynonyms() == null) {
            text.append("0");
        } else {
            text.append(getSynonyms().size());
        }

        text.append(")");

        if (getSynonyms() != null && !getSynonyms().isEmpty()) {
            text.append(" ");
            text.append(getSynonyms().iterator().next().getSynonymName());
        }

        return text.toString();
    }

    /**
     * Create the text to use as a link for the Product's drug classes.
     * 
     * @param locale current {@link Locale}
     * @return String the text to use as a link
     */
    public String createDrugClassesLinkText(Locale locale) {

        StringBuffer text = new StringBuffer();

        text.append(FieldKey.DRUG_CLASSES.getLocalizedName(locale, getEntityType()));
        text.append(" (");

        // get the DrugClasses size
        if (getDrugClasses() == null) {
            text.append("0");
        } else {
            text.append(getDrugClasses().size());
        }

        text.append(")");

        // Get the drug Classess class
        if (getDrugClasses() != null && !getDrugClasses().isEmpty()) {
            DrugClassVo drugClass = null;

            for (DrugClassGroupVo drugClassGroup : getDrugClasses()) {
                if (!drugClassGroup.getPrimary()) {
                    drugClass = drugClassGroup.getDrugClass();

                    break;
                }
            }

            // if the Drug Class variable is not null append the string.
            if (drugClass != null) {
                text.append(" ");
                text.append(drugClass.toShortString());
            }
        }

        return text.toString();
    }

    /**
     * Create the text to use as a link for the Product's drug classes.
     * 
     * @return String the text to use as a link
     */
    public String getDrugClassesLinkText() {

        StringBuffer text = new StringBuffer();
        text.append(" (");

        if (getDrugClasses() == null) {
            text.append("0");
        } else {
            text.append(getDrugClasses().size());
        }

        text.append(")");

        if (getDrugClasses() != null && !getDrugClasses().isEmpty()) {
            DrugClassVo drugClass = null;

            for (DrugClassGroupVo drugClassGroup : getDrugClasses()) {
                if (!drugClassGroup.getPrimary()) {
                    drugClass = drugClassGroup.getDrugClass();

                    break;
                }
            }

            if (drugClass != null) {
                text.append(" ");
                text.append(drugClass.toShortString());
            }
        }

        return text.toString();
    }

    /**
     * 
     * Check to see if the product is a supply
     *
     * @return true if product has the category supply
     */
    public boolean isSupply() {

        return getCategories().contains(Category.SUPPLY);
    }
    
    /**]
     * equals method
     * @param o ProductObject
     * @return true if the two are equal on EPLID
     */
    @Override
    public boolean equals(Object o) {
        ProductVo product = (ProductVo) o;
        
        if (product == null) {
            return false;
        } else {
            return product.getVaProductName().equals(this.getVaProductName());
        }
    }
    
    /**
     * HashCode  
     * @return int hash
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = PPSConstants.I175;
        result = PPSConstants.I37 * result + (getId() == null ? 0 : this.getId().hashCode());

        return result;
    }
}


