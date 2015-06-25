/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * Data representing a Drug Class
 */
public class DrugClassVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private String vuid = "";
    private Date inactivationDate;
    private String classification;
    private String code;
    private String description;
    private DrugClassVo parentDrugClass;
    private DrugClassificationTypeVo classificationType;
    
    //Attributes added during migration
    private String drugClassIen;
    private boolean masterEntryForVuid = true;
    private DrugClassRowType drugClassRowType = null;
    private int type;
    private List<VuidStatusHistoryVo> effectiveDates;


    /**
     * the constructor
     */
    public DrugClassVo() {
        super();
    }

    /**
     * Gets a list of the effective dates and the status on those dates for DrugClassVo.
     * 
     * @return the list of effective date objects
     */
    public List<VuidStatusHistoryVo> getEffectiveDates() {
        return effectiveDates;
    }

    /**
     * Sets a list of the effective dates and the status on those dates for DrugClassVo.
     * @param effectiveDates The list of effectiveDates
     */
    public void setEffectiveDates(List<VuidStatusHistoryVo> effectiveDates) {
        this.effectiveDates = effectiveDates;
    }

    /**
     * getType.
     * @return type list {1,2,3}
     */
    public int getType() {
        return type;
    }

    /**
     * Sets type class row object
     * @param type type
     */
    public void setType(int type) {
        this.type = type;
    }
    
    /**
     * getDrugClassRowType.
     * @return drug class row object
     */
    public DrugClassRowType getDrugClassRowType() {
        return drugClassRowType;
    }

    /**
     * Sets drug class row object
     * @param drugClassRowType drugClassRowType
     */
    public void setDrugClassRowType(DrugClassRowType drugClassRowType) {
        this.drugClassRowType = drugClassRowType;
    }

    /**
     * isMasterEntryForVuid.
     * @return master entry for vuid state
     */
    public boolean isMasterEntryForVuid() {
        return masterEntryForVuid;
    }

    /**
     * Sets the master entry for vuid state.
     * @param masterEntryForVuid masterEntryForVuid
     */
    public void setMasterEntryForVuid(boolean masterEntryForVuid) {
        this.masterEntryForVuid = masterEntryForVuid;
    }

    /**
     * getVuid for DrugClassVo.
     * @return vuid property
     */
    public String getVuid() {
        return vuid;
    }

    /**
     * Returns the IEN for Drug Class Unit
     * 
     * @return IEN
     */
    public String getDrugClassIen() {
        return drugClassIen;
    }

    /**
     * Sets the IEN for Drug Class.
     * @param drugClassIen drugClassIen
     * 
     */
    public void setDrugClassIen(String drugClassIen) {
        this.drugClassIen = drugClassIen;
    }

    /**
     * Returns true if the domain is standardized for the DrugClassVo.
     * 
     * @return boolean
     */
    public boolean isStandardized() {
        return true;
    }

    /**
     * Returns true if the domain is an NDF domain for the DrugClassVo.
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return true;
    }

    /**
     * setVuid for the DrugClassVo.
     * @param vuid vuid property
     */
    public void setVuid(String vuid) {
        this.vuid = trimToEmpty(vuid);
    }

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    public EntityType getEntityType() {
        return EntityType.DRUG_CLASS;
    }

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {
        return DomainGroup.DRUG_CLASS_CODE;
    }

    /**
     * getCode.
     * @return code property
     */
    public String getCode() {
        return code;
    }

    /**
     * setCode.
     * @param code code property
     */
    public void setCode(String code) {
        if (code == null) {
            this.code = code;
        } else {
            this.code = trimToEmpty(code.toUpperCase(Locale.US));
        }
    }

    /**
     * getParentDrugClass.
     * @return parentClass property
     */
    public DrugClassVo getParentDrugClass() {
        return parentDrugClass;
    }

    /**
     * setParentDrugClass.
     * @param parentClass parentClass property
     */
    public void setParentDrugClass(DrugClassVo parentClass) {
        this.parentDrugClass = parentClass;
    }

    /**
     * getInactivationDate for DrugClassVo.
     * @return inactivationDate property
     */
    public Date getInactivationDate() {
        return inactivationDate;
    }

    /**
     * setInactivationDate in DrugClassVo.
     * @param inactivationDate inactivationDate property
     */
    public void setInactivationDate(Date inactivationDate) {
        this.inactivationDate = inactivationDate;
    }

    /**
     * getClassificationType.
     * @return classificationType property
     */
    public DrugClassificationTypeVo getClassificationType() {
        return classificationType;
    }

    /**
     * setClassificationType.
     * @param classificationType classificationType property
     */
    public void setClassificationType(DrugClassificationTypeVo classificationType) {
        this.classificationType = classificationType;
    }

    /**
     * setClassification.
     * @param classificationName classificationName property
     */
    public void setClassification(String classificationName) {
        if (classificationName == null) {
            this.classification = null;
        } else {
            this.classification = trimToEmpty(classificationName.toUpperCase(Locale.US));
        }
    }

    /**
     * getClassification.
     * @return classificationName property
     */
    public String getClassification() {
        return classification;
    }

    /**
     * Returns true if this DrugClassVo is a local only domain
     * 
     * @return false always
     */
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * getDescription.
     * @return description property
     */
    public String getDescription() {
        return description;
    }

    /**
     * setDescription.
     * @param description description property
     */
    public void setDescription(String description) {
        this.description = trimToEmpty(description);
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> disabledFields = super.handleListDisabledFields(environment, roles);

        disabledFields.add(FieldKey.VUID);
        disabledFields.add(FieldKey.ITEM_STATUS);
        disabledFields.add(FieldKey.EFFECTIVE_DATES);

        return disabledFields;
    }


  

    
    /**
     * List all required fields for this ValueObject, with the pre-condition that the current instance is not a read-only
     * one.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this DrugClassVo.
     */
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> requiredFields = super.handleListRequiredFields(environment, roles);

        requiredFields.add(FieldKey.CLASSIFICATION_TYPE);

        return requiredFields;
    }

    /**
     * Returns a list of all fields appropriate for display on the wizard.
     * 
     * @return collection
     */
    public Collection<FieldKey> listAllWizardFields() {
        Collection<FieldKey> fields = super.listAllWizardFields();

        fields.remove(FieldKey.VALUE);

        return fields;
    }

    /**
     * List all uniqueness criteria for this ValueObject.
     * 
     * @return Set<FieldKey> All uniqueness fields for this object.
     */
    @Override
    public Set<FieldKey> listUniquenessCriteria() {
        Set<FieldKey> uniqueness = new HashSet<FieldKey>();
        uniqueness.add(FieldKey.CLASSIFICATION);
        uniqueness.add(FieldKey.CODE);

        return uniqueness;
    }

    /**
     * getValue.
     * @return String "code - classification"
     */
    @Override
    public String getValue() {
        return getCode() + " - " + getClassification();
    }
}
