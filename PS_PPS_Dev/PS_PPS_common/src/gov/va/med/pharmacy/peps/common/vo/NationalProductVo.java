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


/**
 * This class is a helper class to the ProductVo file.   It's main purpose is to get around the issue 
 * of having too much complexity in the productVo file.
 */
public abstract class NationalProductVo extends ManagedItemVo {
  
    private static final long serialVersionUID = 1L;
    private static final String N_A = "N/A";
    private String vaProductName;
    private GenericNameVo genericName;
    private String nationalFormularyName;
    private String productStrength;
    private DrugUnitVo productUnit;
    private SingleMultiSourceProductVo singleMultiSourceProduct;
    private StandardMedRouteVo standardMedicationRoute;
    private String code;
    private String description;
    private CsFedScheduleVo csFedSchedule;
    private Collection<DrugClassGroupVo> drugClasses = new ArrayList<DrugClassGroupVo>(0);
    private boolean overrideDfDoseChkExclusn;
    private boolean cmopDispense = true;
    
    //added during Migration
    private boolean masterEntryForVuid = true;
    private List<VuidStatusHistoryVo> effectiveDates = new ArrayList<VuidStatusHistoryVo>();
    private String migratedDosageFormName;
    private Long productDispenseUnitsPerOrd;
    private boolean createPossibleDosage = true;
    private boolean excludeDrgDrgInteractionCheck;
    private String fdaMedGuide;
    
    private List<Category> categories = new ArrayList<Category>();
    private List<SubCategory> subCategories = new ArrayList<SubCategory>();

    
    private Collection<ActiveIngredientVo> activeIngredients = new ArrayList<ActiveIngredientVo>(0);
    private String vuid;
    private String secondaryDrugClass;
    
    private boolean nationalFormularyIndicator;
    private String vaPrintName;
    private DoseUnitVo doseUnitVo;
    private String cmopId;
    private OrderUnitVo orderUnit;
    
    private Long ndfProductIen;
    
    private List<DrugTextVo> nationalDrugTexts = new ArrayList<DrugTextVo>();
    private Collection<NationalPossibleDosagesVo> nationalPossibleDosages = new ArrayList<NationalPossibleDosagesVo>(0);
    private DispenseUnitVo dispenseUnit;
    
    /**
     * getNdfProductIen
     * @return ndfProductIen
     */
    public Long getNdfProductIen() {

        return ndfProductIen;
    }

    /**
     * setNdfProductIen
     * @param ndfProductIen ndfProductIen
     */
    public void setNdfProductIen(Long ndfProductIen) {

        this.ndfProductIen = ndfProductIen;
    }
    
    /**
     * getNationalPossibleDosages
     * @return nationalPossibleDosages property
     */
    public Collection<NationalPossibleDosagesVo> getNationalPossibleDosages() {

        return nationalPossibleDosages;
    }

    /**
     * setNationalPossibleDosages
     * @param nationalPossibleDosages nationalPossibleDosages property
     */
    public void setNationalPossibleDosages(Collection<NationalPossibleDosagesVo> nationalPossibleDosages) {

        this.nationalPossibleDosages = new ArrayList<NationalPossibleDosagesVo>();

        if (nationalPossibleDosages != null && !nationalPossibleDosages.isEmpty()) {
            this.nationalPossibleDosages.addAll(nationalPossibleDosages);
        }
    }

    /**
     * setNationalPossibleDosagesArray
     * @param inNationalPossibleDosages nationalPossibleDosages property
     */
    public void setNationalPossibleDosagesArray(Collection<NationalPossibleDosagesVo> inNationalPossibleDosages) {

        this.nationalPossibleDosages = new ArrayList<NationalPossibleDosagesVo>();

        if (inNationalPossibleDosages != null && !inNationalPossibleDosages.isEmpty()) {
            this.nationalPossibleDosages.addAll(inNationalPossibleDosages);
        }
    }
    
    /**
     * getNationalDrugTexts
     * @return drugTexts property
     */
    public List<DrugTextVo> getNationalDrugTexts() {

        return nationalDrugTexts;
    }

    /**
     * setNationalDrugTexts
     * @param nationalDrugTexts nationalDrugTexts property
     */
    public void setNationalDrugTexts(List<DrugTextVo> nationalDrugTexts) {

        this.nationalDrugTexts = new ArrayList<DrugTextVo>();

        if (nationalDrugTexts != null && !nationalDrugTexts.isEmpty()) {
            this.nationalDrugTexts.addAll(nationalDrugTexts);
        }
    }
    
    /**
     * getDispenseUnit
     * @return vaDispenseUnit property
     */
    public DispenseUnitVo getDispenseUnit() {

        return dispenseUnit;
    }

    /**
     * setDispenseUnit
     * @param vaDU dispenseUnit property
     */
    public void setDispenseUnit(DispenseUnitVo vaDU) {

        this.dispenseUnit = vaDU;
    }

    
    /**
     * getVuid
     * @return vuid property
     */
    public String getVuid() {

        return vuid;
    }

    /**
     * setVuid
     * @param vuid vuid property
     */
    public void setVuid(String vuid) {

        this.vuid = trimToEmpty(vuid);
    }
    
    /**
     * getCategories for ProductVo.
     * @return categories
     */
    public List<Category> getCategories() {

        return categories;
    }

    /**
     * setCategories for ProductVo.
     * @param categories categories
     */
    public void setCategories(List<Category> categories) {

        this.categories = categories;
    }

    /**
     * getSubCategories for ProductVo.
     * @return subCategories
     */
    public List<SubCategory> getSubCategories() {

        return subCategories;
    }

    /**
     * setSubCategories for ProductVo.
     * @param subCategories subCategories
     */
    public void setSubCategories(List<SubCategory> subCategories) {

        this.subCategories = subCategories;
    }
    
    /**
     * getProductDispenseUnitsPerOrd
     * @return productDispenseUnitsPerOrd
     */
    public Long getProductDispenseUnitsPerOrd() {

        return productDispenseUnitsPerOrd;
    }

    /**
     * setProductDispenseUnitsPerOrd
     * @param productDispenseUnitsPerOrd productDispenseUnitsPerOrd
     */
    public void setProductDispenseUnitsPerOrd(Long productDispenseUnitsPerOrd) {

        this.productDispenseUnitsPerOrd = productDispenseUnitsPerOrd;
    }

    /**
     * getCreatePossibleDosage
     * @return createPossibleDosage
     */
    public boolean getCreatePossibleDosage() {

        return createPossibleDosage;
    }

    /**
     * setCreatePossibleDosage
     * @param createPossibleDosage createPossibleDosage
     */
    public void setCreatePossibleDosage(boolean createPossibleDosage) {

        this.createPossibleDosage = createPossibleDosage;
    }

    /**
     * isExcludeDrgDrgInteractionCheck
     * @return excludeDrgDrgInteractionCheck
     */
    public boolean isExcludeDrgDrgInteractionCheck() {

        return excludeDrgDrgInteractionCheck;
    }

    /**
     * setExcludeDrgDrgInteractionCheck
     * @param excludeDrgDrgInteractionCheck excludeDrgDrgInteractionCheck
     */
    public void setExcludeDrgDrgInteractionCheck(boolean excludeDrgDrgInteractionCheck) {

        this.excludeDrgDrgInteractionCheck = excludeDrgDrgInteractionCheck;
    }

    /**
     * getFdaMedGuide
     * @return fdaMedGuide
     */
    public String getFdaMedGuide() {

        return fdaMedGuide;
    }

    /**
     * setFdaMedGuide
     * @param fdaMedGuide fdaMedGuide
     */
    public void setFdaMedGuide(String fdaMedGuide) {

        this.fdaMedGuide = fdaMedGuide;
    }
    
    /**
     * Gets a list of the effective dates and the status on those dates
     * 
     * @return the list of effective date objects
     */
    public List<VuidStatusHistoryVo> getEffectiveDates() {

        return effectiveDates;
    }

    /**
     * Sets a list of the effective dates and the status on those dates
     * @param effectiveDates is a list of dates
     */
    public void setEffectiveDates(List<VuidStatusHistoryVo> effectiveDates) {

        this.effectiveDates = effectiveDates;
    }

  

    /**
     * getMigratedDosageFormName
     * @return migratedDosageFormName
     */
    public String getMigratedDosageFormName() {

        return migratedDosageFormName;
    }

    /**
     * Sets the dosageForm attribute
     * @param migratedDosageFormName migratedDosageFormName
     * 
     */
    public void setMigratedDosageFormName(String migratedDosageFormName) {

        this.migratedDosageFormName = migratedDosageFormName;
    }

    /**
     * isMasterEntryForVuid
     * @return master entry for vuid state
     */
    public boolean isMasterEntryForVuid() {

        return masterEntryForVuid;
    }

    /**
     * Sets the master entry for vuid state
     *
     * @param masterEntryForVuid boolean
     */
    public void setMasterEntryForVuid(boolean masterEntryForVuid) {

        this.masterEntryForVuid = masterEntryForVuid;
    }


    /**
     * getCmopDispense
     * @return cmopDispense property
     */
    public boolean getCmopDispense() {

        return cmopDispense;
    }

    /**
     * cmopDispense
     * @param cmopDispense The CMOP Dispense property
     */
    public void setCmopDispense(boolean cmopDispense) {

        this.cmopDispense = cmopDispense;
    }
    
    /**
     * setOverrideDfDoseChkExclusn
     * @param overrideDfDoseChkExclusn overrideDfDoseChkExclusn property
     */
    public void setOverrideDfDoseChkExclusn(boolean overrideDfDoseChkExclusn) {

        this.overrideDfDoseChkExclusn = overrideDfDoseChkExclusn;
    }

    /**
     * isOverrideDfDoseChkExclusn
     * @return overrideDfDoseChkExclusn property
     */
    public boolean isOverrideDfDoseChkExclusn() {

        return overrideDfDoseChkExclusn;
    }
    
    /**
     * getActiveIngredients
     * @return active ingredients property
     */
    public Collection<ActiveIngredientVo> getActiveIngredients() {

        return activeIngredients;
    }

    /**
     * setActiveIngredients
     * @param activeIngredients active ingredients property
     */
    public void setActiveIngredients(Collection<ActiveIngredientVo> activeIngredients) {

        this.activeIngredients = new ArrayList<ActiveIngredientVo>();

        if (activeIngredients != null && !activeIngredients.isEmpty()) {
            this.activeIngredients.addAll(activeIngredients);
        }
    }
    
    /**
     * getGenericName
     * @return genericName property
     */
    public GenericNameVo getGenericName() {

        return genericName;
    }

    /**
     * setGenericName
     * @param genericName genericName property
     */
    public void setGenericName(GenericNameVo genericName) {

        this.genericName = genericName;
    }
    
    /**
     * getVaProductName
     * @return vaProductName property
     */
    public String getVaProductName() {

        return vaProductName;
    }

    /**
     * setVaProductName
     * @param vaProductName vaProductName property
     */
    public void setVaProductName(String vaProductName) {

        if (vaProductName == null) {
            this.vaProductName = null;
        } else {
            this.vaProductName = trimToEmpty(vaProductName.toUpperCase(Locale.US));
        }
    }
    
    /**
     * getProductStrength
     * @return productStrength
     */
    public String getProductStrength() {

        return productStrength;
    }

    /**
     * setProductStrength attribute
     * @param productStrength of drug
     */
    public void setProductStrength(String productStrength) {

        this.productStrength = productStrength;
    }

    /**
     * setProductUnit
     * @param productUnit 
    */
    public void setProductUnit(DrugUnitVo productUnit) {

        this.productUnit = productUnit;
    }

    /**
     * getProductUnit
     * @return productUnit string
     */
    public DrugUnitVo getProductUnit() {

        return productUnit;
    }
    
    /**
     * getNationalFormularyName
     * @return nationalFormularyName property
     */
    public String getNationalFormularyName() {

        return nationalFormularyName;
    }

    /**
     * setNationalFormularyName
     * @param nationalFormularyName nationalFormularyName property
     */
    public void setNationalFormularyName(String nationalFormularyName) {

        if (nationalFormularyName == null) {
            this.nationalFormularyName = null;
        } else {
            this.nationalFormularyName = trimToEmpty(nationalFormularyName.toUpperCase(Locale.US));
        }
    }

    /**
     * getSingleMultiSourceProduct.
     * @return singleMultiSourceProduct property
     */
    public SingleMultiSourceProductVo getSingleMultiSourceProduct() {

        return singleMultiSourceProduct;
    }

    /**
     * setSingleMultiSourceProduct.
     * @param singleMultiSourceProduct singleMultiSourceProduct property
     */
    public void setSingleMultiSourceProduct(SingleMultiSourceProductVo singleMultiSourceProduct) {

        this.singleMultiSourceProduct = singleMultiSourceProduct;
    }

    
    /**
     * getStandardMedicationRoute
     * @return route property
     */
    public StandardMedRouteVo getStandardMedicationRoute() {

        return standardMedicationRoute;
    }

    /**
     * setStandardMedicationRoute
     * @param route route property
     */
    public void setStandardMedicationRoute(StandardMedRouteVo route) {

        this.standardMedicationRoute = route;
    }
    
    /**
     * getCsFedSchedule
     * @return csFedSchedule property
     */
    public CsFedScheduleVo getCsFedSchedule() {

        return csFedSchedule;
    }

    /**
     * setCsFedSchedule
     * @param csFedSchedule csFedSchedule property
     */
    public void setCsFedSchedule(CsFedScheduleVo csFedSchedule) {

        this.csFedSchedule = csFedSchedule;
    }

    /**
     * getDrugClasses
     * @return drugClass property
     */
    public Collection<DrugClassGroupVo> getDrugClasses() {

        return drugClasses;
    }

    /**
     * setDrugClasses
     * @param drugClass drugClass property
     */
    public void setDrugClasses(Collection<DrugClassGroupVo> drugClass) {

        this.drugClasses = new ArrayList<DrugClassGroupVo>();

        if (drugClass != null && !drugClass.isEmpty()) {
            this.drugClasses.addAll(drugClass);
        }
    }
    
    /**
     * Gets the primary drug class code for the product Used in tables to display code followed by letter i with description
     * as tooltip
     * 
     * @return Primary drug class, or null if no primary exists
     */
    public String getPrimaryDrugClass2() {

        for (DrugClassGroupVo drugClass : drugClasses) {

            if (drugClass.getPrimary()) {

                DrugClassVo drugCode = drugClass.getDrugClass();
                code = drugCode.getCode();
                description = drugCode.getDescription();

                return "<span title=\"" + description + "\">" + code
                    + "  <img src=\"/PRE/images/info.gif\" class=\"ndcImage\" alt=\"" + description + "\" /></span>";
            }
        }

        return null;
    }

    /**
     * Gets the primary drug class code for the product
     * 
     * @return code drug class, or null if no primary exists
     */
    public DrugClassVo getPrimaryDrugClass() {

        for (DrugClassGroupVo drugClass : drugClasses) {

            if (drugClass.getPrimary()) {
                return drugClass.getDrugClass();
            }
        }

        return null;
    }
    
    /**
     * getNationalFormularyIndicator
     * @return nationalFormularyInd property
     */
    public boolean getNationalFormularyIndicator() {

        return nationalFormularyIndicator;
    }

    /**
     * nationalFormularyIndicator
     * @param nationalFormularyIndicator nationalFormularyInd property
     */
    public void setNationalFormularyIndicator(boolean nationalFormularyIndicator) {

        this.nationalFormularyIndicator = nationalFormularyIndicator;
    }
    
    /**
     * getVaPrintName
     * @return vaPrintName property
     */
    public String getVaPrintName() {

        return vaPrintName;
    }

    /**
     * setVaPrintName
     * @param vaPrintName vaPrintName property
     */
    public void setVaPrintName(String vaPrintName) {

        if (vaPrintName == null) {
            this.vaPrintName = null;
        } else {
            this.vaPrintName = trimToEmpty(vaPrintName.toUpperCase(Locale.US));
        }
    }

    /**
     * getDoseUnitVo
     * @return doseUnitVo property
     */
    public DoseUnitVo getDoseUnitVo() {

        return doseUnitVo;
    }

    /**
     * setDoseUnitVo
     * @param doseUnitVo doseUnitVo property
     */
    public void setDoseUnitVo(DoseUnitVo doseUnitVo) {

        this.doseUnitVo = doseUnitVo;
    }

    /**
     * getCmopId
     * @return String CMOP ID
     */
    public String getCmopId() {

        return cmopId;
    }

    /**
     * setCmopId
     * @param cmopId String
     */
    public void setCmopId(String cmopId) {

        this.cmopId = trimToEmpty(cmopId);
    }
    
    /**
     * getOrderUnit
     * @return orderUnit property
     */
    public OrderUnitVo getOrderUnit() {

        return orderUnit;
    }
    
    /**
     * setSecondaryDrugClass
     * @param secondaryDrugClass secondaryDrugClass
     */
    public void setSecondaryDrugClass(String secondaryDrugClass) {

        this.secondaryDrugClass = secondaryDrugClass;
    }

    /**
     * getSecondaryDrugClass
     * @return secondaryDrugClass
     */
    public String getSecondaryDrugClass() {

        return secondaryDrugClass;
    }
    
    /**
     * setOrderUnit
     * @param orderUnit orderUnit property
     */
    public void setOrderUnit(OrderUnitVo orderUnit) {

        this.orderUnit = orderUnit;
    }
    
    /**
     * Gets the activeIngredientNames from product
     * 
     * @return active ingredient names
     */
    public String getDisplayableIngredientName() {

        StringBuffer str = new StringBuffer();
        
        int x = 0;

        for (ActiveIngredientVo ing : activeIngredients) {

            str.append(ing.getIngredient().getValue());
            x++;
            
            if (x < activeIngredients.size()) {
                str.append("<p>");
            } 

        }

        return str.toString();
    }
    
    /**
     * concatenates the strength of each active ingredient by "-" if no strength for an active ingredient is found, returns
     * "N/A"
     * 
     * @return String the unit value
     */
    public String getDisplayableIngredientStrength() {

        StringBuffer activeIngredientBuf = new StringBuffer();

        if ((activeIngredients != null) && (!activeIngredients.isEmpty())) {
            for (ActiveIngredientVo thisActiveIngredient : activeIngredients) {
                String thisActiveIngredientStrength = thisActiveIngredient.getStrength();

                if (StringUtils.isEmpty(thisActiveIngredientStrength)) {
                    activeIngredientBuf.append("NA" + "-");
                } else {
                    activeIngredientBuf.append(thisActiveIngredientStrength + "-");
                }
            }// end for

        }// end if

        if (activeIngredientBuf.length() > 0) {

            // remove the "-" at the end
            return activeIngredientBuf.substring(0, activeIngredientBuf.length() - 1);
        } else {

            // this is an empty String
            return N_A;
        }
    }// end method
    
    /**
     * concatenates the strength of each active ingredient by "-" if no strength for an active ingredient is found, returns
     * "N/A"
     * 
     * @return String the unit value
     */
    public String getDisplayableIngredientUnit() {

        StringBuffer activeIngredientBuf = new StringBuffer();

        if ((activeIngredients != null) && (!activeIngredients.isEmpty())) {
            for (ActiveIngredientVo thisActiveIngredient : activeIngredients) {
                if (thisActiveIngredient.getDrugUnit() == null) {
                    activeIngredientBuf.append("NA" + "-");
                } else {
                    String activeIngredientUnit = thisActiveIngredient.getDrugUnit().getValue();

                    if (StringUtils.isEmpty(activeIngredientUnit)) {
                        activeIngredientBuf.append("NA" + "-");
                    } else {
                        activeIngredientBuf.append(activeIngredientUnit + "-");
                    }// end else
                }
            }// end for

        }// end if

        if (activeIngredientBuf.length() > 0) { // remove the "-" at the end
            return activeIngredientBuf.substring(0, activeIngredientBuf.length() - 1);
        } else { // this is an empty String
            return N_A;
        }
    }// end method

    
    
    /**
     * List all uniqueness criteria for this ProductVo.
     * 
     * @return Set<FieldKey> All uniqueness fields for this object.
     */
    @Override
    public Set<FieldKey> listUniquenessCriteria() {

        Set<FieldKey> uniqueness = new HashSet<FieldKey>();
        uniqueness.add(FieldKey.VA_PRODUCT_NAME);

        return uniqueness;
    }

    /**
     * Returns true if this is a local only domain for the ProductVo.
     * 
     * @return boolean
     */
    @Override
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * Make a Product template from the given ManagedItemVo.
     * 
     * @param managedItemVo ManagedItemVo
     * @param environment {@link Environment} in which the template is being made
     * @return template
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#makeTemplate(gov.va.med.pharmacy.peps.common.vo.ManagedItemVo)
     */
    @Override
    protected ManagedItemVo makeTemplate(ManagedItemVo managedItemVo, Environment environment) {

        ProductVo item = (ProductVo) managedItemVo;

        item.setCreatedBy(null);
        item.setModifiedBy(null);
        item.setCreatedDate(null);
        item.setModifiedDate(null);
        item.setId(null);

        //item.setVaProductName("");
        item.setVuid("");
        item.setGcnSequenceNumber("");

        // set request item status to pending
        item.setRequestItemStatus(RequestItemStatus.PENDING);

        // set VA print name and cmop id to empty string
        //item.setVaPrintName("");
        item.setCmopId("");
        item.setServiceCode(null);
        item.setNdfProductIen(null);

        //item.setSource(ProductDataSourceType.VA); - removed from product
        item.setItemStatus(ItemStatus.ACTIVE);
        item.setNdcs(Collections.EMPTY_LIST);
        item.setNdcCount(0);

        // set possible dosages and local possible dosages to null
        item.setNationalPossibleDosages(null);
        item.setLocalPossibleDosages(Collections.EMPTY_LIST);
        item.makeAllDataFieldsEditable();
        item.setPreviouslyMarkedForLocalUse(false);
        item.setSynonyms(null);

        return item;
    }


    /**
     * Create a blank Product template.
     * 
     * @param environment {@link Environment} in which the template is being made
     * @return blank template
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#blankTemplate()
     */
    @Override
    public ManagedItemVo blankTemplate(Environment environment) {

        ProductVo product = new ProductVo();

        product.setAtcCanisters(null);
        product.setActiveIngredients(null);
        product.setVuid("");
        product.getDrugClasses().clear();
        DrugClassGroupVo drugClassGroup = new DrugClassGroupVo();
        drugClassGroup.setPrimary(true);
        product.getDrugClasses().add(drugClassGroup);

        GenericNameVo tempGenericName = new GenericNameVo();
        tempGenericName.setId(null);
        tempGenericName.setValue("");
        tempGenericName.setVuid("");
        product.setGenericName(tempGenericName);

        product.setSingleMultiSourceProduct(null);

        product.setNdcs(Collections.EMPTY_LIST);
        product.setNdcCount(0);

        product.setLocalPossibleDosages(null);
        product.setNationalPossibleDosages(null);

        product.setId(null);
        product.setItemStatus(ItemStatus.ACTIVE);
        product.setRequestItemStatus(RequestItemStatus.PENDING);

        return product;
    }
}
