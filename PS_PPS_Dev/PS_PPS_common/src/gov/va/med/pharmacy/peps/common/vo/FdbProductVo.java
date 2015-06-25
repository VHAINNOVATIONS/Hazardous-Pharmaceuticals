/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.List;


/**
 * Data representing an Ingredient
 */
public class FdbProductVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Long productIdFk;
    private String dgGenericDosageForm;
    private String dgGenericDrugName;
    private String dgHasPackagedDrugs;
    private Long dgGenericDrugId;
    private String dgRoute;
    private String dgSingleIngredient;
    private String dgStrength;
    private String ddConceptType;
    private String ddDosageForm;
    private String ddFederalDeaClassCode;
    private Long ddGcnSeqno;
    private String ddHasPackagedDrugs;
    private Long ddDispenseDrugId;
    private String ddMultisource;
    private String ddDispenseDrugName;
    private String ddObsoleteDate;
    private String ddReplaced;
    private String ddRoute;
    private String ddStatusCode;
    private String ddStrength;
    private String ddStrengthUnit;
    
    private String mappedDosageForm;
    private String mappedDrugUnit;
    private List<String> mappedDrugClasses;
    private List<String> mappedIngredients;
    private String mappedGeneric;

    /**
     * the constructor
     */
    public FdbProductVo() {
        super();
    }

    /**
     * getEplId.
     * @return productIdFk
     */
    public Long getProductIdFk() {
        return productIdFk;
    }

    /**
     * setProductIdFk.
     * @param productIdFk productIdFk
     */
    public void setProductIdFk(Long productIdFk) {
        this.productIdFk = productIdFk;
    }

    /**
     * getDgGenericDosageForm.
     * @return dgGenericDosageForm
     */
    public String getDgGenericDosageForm() {
        return dgGenericDosageForm;
    }

    /**
     * setDgGenericDosageForm.
     * @param dgGenericDosageForm Dispensable Generic Dosage Form 
     */
    public void setDgGenericDosageForm(String dgGenericDosageForm) {
        this.dgGenericDosageForm = dgGenericDosageForm;
    }

    /**
     * getDgGenericDrugName.
     * @return dgGenericDrugName
     */
    public String getDgGenericDrugName() {
        return dgGenericDrugName;
    }

    /**
     * setDgGenericDrugName.
     * @param dgGenericDrugName Dispensable Generic drug name
     */
    public void setDgGenericDrugName(String dgGenericDrugName) {
        this.dgGenericDrugName = dgGenericDrugName;
    }

    /**
     * getDgHasPackagedDrugs.
     * @return dgHasPackagedDrugs
     */
    public String getDgHasPackagedDrugs() {
        return dgHasPackagedDrugs;
    }

    /**
     * setDgHasPackagedDrugs.
     * @param dgHasPackagedDrugs Dispensable Generic  
     */
    public void setDgHasPackagedDrugs(String dgHasPackagedDrugs) {
        this.dgHasPackagedDrugs = dgHasPackagedDrugs;
    }

    /**
     * getDgGenericDrugId.
     * @return dgGenericDrugId
     */
    public Long getDgGenericDrugId() {
        return dgGenericDrugId;
    }

    /**
     * setDgGenericDrugId.
     * @param dgGenericDrugId Dispensable Generic  ID
     */
    public void setDgGenericDrugId(Long dgGenericDrugId) {
        this.dgGenericDrugId = dgGenericDrugId;
    }

    /**
     * getDgRoute.
     * @return dgRoute
     */
    public String getDgRoute() {
        return dgRoute;
    }

    /**
     * setDgRoute.
     * @param dgRoute Dispensable Generic Route
     */
    public void setDgRoute(String dgRoute) {
        this.dgRoute = dgRoute;
    }

    /**
     * getDgSingleIngredient.
     * @return dgSingleIngredient
     */
    public String getDgSingleIngredient() {
        return dgSingleIngredient;
    }

    /**
     * setDgSingleIngredient.
     * @param dgSingleIngredient Dispensable Generic single?
     */
    public void setDgSingleIngredient(String dgSingleIngredient) {
        this.dgSingleIngredient = dgSingleIngredient;
    }

    /**
     * getDgStrength.
     * @return dgStrength 
     */
    public String getDgStrength() {
        return dgStrength;
    }

    /**
     * setDgStrength.
     * @param dgStrength Dispensable Generic strength
     */
    public void setDgStrength(String dgStrength) {
        this.dgStrength = dgStrength;
    }

    /**
     * getDdConceptType.
     * @return ddConceptType
     */
    public String getDdConceptType() {
        return ddConceptType;
    }

    /**
     * setDdConceptType.
     * @param ddConceptType Dispensable Drug concept
     */
    public void setDdConceptType(String ddConceptType) {
        this.ddConceptType = ddConceptType;
    }

    /**
     * getDdDosageForm.
     * @return ddDosageForm
     */
    public String getDdDosageForm() {
        return ddDosageForm;
    }

    /**
     * setDdDosageForm.
     * @param ddDosageForm Dispensable Drug Form
     */
    public void setDdDosageForm(String ddDosageForm) {
        this.ddDosageForm = ddDosageForm;
    }

    /**
     * getDdFederalDeaClassCode.
     * @return ddFederalDeaClassCode
     */
    public String getDdFederalDeaClassCode() {
        return ddFederalDeaClassCode;
    }

    /**
     * setDdFederalDeaClassCode.
     * @param ddFederalDeaClassCode Dispensable Drug DEA Class
     */
    public void setDdFederalDeaClassCode(String ddFederalDeaClassCode) {
        this.ddFederalDeaClassCode = ddFederalDeaClassCode;
    }

    /**
     * getDdGcnSeqno.
     * @return ddGcnSeqno
     */
    public Long getDdGcnSeqno() {
        return ddGcnSeqno;
    }

    /**
     * setDdGcnSeqno.
     * @param ddGcnSeqno Dispensable Drug GCN Seq Num
     */
    public void setDdGcnSeqno(Long ddGcnSeqno) {
        this.ddGcnSeqno = ddGcnSeqno;
    }

    /**
     * getDdHasPackagedDrugs.
     * @return ddHasPackagedDrugs
     */
    public String getDdHasPackagedDrugs() {
        return ddHasPackagedDrugs;
    }

    /**
     * setDdHasPackagedDrugs.
     * @param ddHasPackagedDrugs Dispensable Drug
     */
    public void setDdHasPackagedDrugs(String ddHasPackagedDrugs) {
        this.ddHasPackagedDrugs = ddHasPackagedDrugs;
    }

    /**
     * getDdDispenseDrugId.
     * @return ddDispenseDrugId 
     */
    public Long getDdDispenseDrugId() {
        return ddDispenseDrugId;
    }

    /**
     * setDdDispenseDrugId.
     * @param ddDispenseDrugId Dispensable Drug ID
     */
    public void setDdDispenseDrugId(Long ddDispenseDrugId) {
        this.ddDispenseDrugId = ddDispenseDrugId;
    }

    /**
     * getDdMultisource.
     * @return ddMultisource
     */
    public String getDdMultisource() {
        return ddMultisource;
    }

    /**
     * setDdMultisource.
     * @param ddMultisource Dispensable Drug Source
     */
    public void setDdMultisource(String ddMultisource) {
        this.ddMultisource = ddMultisource;
    }

    /**
     * getDdDispenseDrugName.
     * @return v
     */
    public String getDdDispenseDrugName() {
        return ddDispenseDrugName;
    }

    /**
     * setDdDispenseDrugName.
     * @param ddDispenseDrugName Dispensable Drug  Name
     */
    public void setDdDispenseDrugName(String ddDispenseDrugName) {
        this.ddDispenseDrugName = ddDispenseDrugName;
    }

    /**
     * getDdObsoleteDate.
     * @return ddObsoleteDate
     */
    public String getDdObsoleteDate() {
        return ddObsoleteDate;
    }

    /**
     * setDdObsoleteDate.
     * @param ddObsoleteDate Dispensable Drug date obsolete
     */
    public void setDdObsoleteDate(String ddObsoleteDate) {
        this.ddObsoleteDate = ddObsoleteDate;
    }

    /**
     * getDdReplaced.
     * @return ddReplaced
     */
    public String getDdReplaced() {
        return ddReplaced;
    }

    /**
     * setDdReplaced.
     * @param ddReplaced Dispensable Drug substitute
     */
    public void setDdReplaced(String ddReplaced) {
        this.ddReplaced = ddReplaced;
    }

    /**
     * getDdRoute.
     * @return ddRoute
     */
    public String getDdRoute() {
        return ddRoute;
    }

    /**
     * setDdRoute.
     * @param ddRoute Dispensable Drug route
     */
    public void setDdRoute(String ddRoute) {
        this.ddRoute = ddRoute;
    }

    /**
     * getDdStatusCode.
     * @return ddStatusCode
     */
    public String getDdStatusCode() {
        return ddStatusCode;
    }

    /**
     * setDdStatusCode.
     * @param ddStatusCode Dispensable Drug  status
     */
    public void setDdStatusCode(String ddStatusCode) {
        this.ddStatusCode = ddStatusCode;
    }

    /**
     * getDdStrength.
     * @return ddStrength
     */
    public String getDdStrength() {
        return ddStrength;
    }

    /**
     * setDdStrength.
     * @param ddStrength Dispensable Drug  strength
     */
    public void setDdStrength(String ddStrength) {
        this.ddStrength = ddStrength;
    }

    /**
     * getDdStrengthUnit.
     * @return ddStrengthUnit
     */
    public String getDdStrengthUnit() {
        return ddStrengthUnit;
    }

    /**
     * setDdStrengthUnit.
     * @param ddStrengthUnit Dispensable Drug  strength
     */
    public void setDdStrengthUnit(String ddStrengthUnit) {
        this.ddStrengthUnit = ddStrengthUnit;
    }

    /**
     * getMappedDosageForm.
     * @return mappedDosageForm
     */
    public String getMappedDosageForm() {
        return mappedDosageForm;
    }

    /**
     * setMappedDosageForm.
     * @param mappedDosageForm Dispensable Drug  strength
     */
    public void setMappedDosageForm(String mappedDosageForm) {
        this.mappedDosageForm = mappedDosageForm;
    }
    
    /**
     * getMappedDrugUnit.
     * @return mappedDrugUnit
     */
    public String getMappedDrugUnit() {
        return mappedDrugUnit;
    }

    /**
     * setMappedDrugUnit.
     * @param mappedDrugUnit Dispensable Drug  strength
     */
    public void setMappedDrugUnit(String mappedDrugUnit) {
        this.mappedDrugUnit = mappedDrugUnit;
    }


    /**
     * getMappedGeneric.
     * @return mappedGeneric
     */
    public String getMappedGeneric() {
        return mappedGeneric;
    }

    /**
     * setMappedGeneric.
     * @param mappedGeneric Dispensable Drug  strength
     */
    public void setMappedGeneric(String mappedGeneric) {
        this.mappedGeneric = mappedGeneric;
    }

    /**
     * getMappedDrugClasses.
     * @return mappedDrugClasses
     */
    public List<String> getMappedDrugClasses() {
        return mappedDrugClasses;
    }

    /**
     * setMappedDrugClasses.
     * @param mappedDrugClasses Dispensable Drug  strength
     */
    public void setMappedDrugClasses(List<String> mappedDrugClasses) {
        this.mappedDrugClasses = mappedDrugClasses;
    }
    
    /**
     * getMappedIngredients.
     * @return mappedIngredients
     */
    public List<String> getMappedIngredients() {
        return mappedIngredients;
    }

    /**
     * setMappedIngredients.
     * @param mappedIngredients Dispensable Drug  strength
     */
    public void setMappedIngredients(List<String> mappedIngredients) {
        this.mappedIngredients = mappedIngredients;
    }
    
    
    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {
        return DomainGroup.GROUP_1;
    }

    /**
     * Returns true if the domain is standardized for the FdbProductVo
     * 
     * @return boolean
     */
    public boolean isStandardized() {
        return true;
    }

    /**
     * Returns true if this is a local only domain  for the FdbProductVo
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * Returns true if the domain is an NDF domain  for the FdbProductVo
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return true;
    }
}
