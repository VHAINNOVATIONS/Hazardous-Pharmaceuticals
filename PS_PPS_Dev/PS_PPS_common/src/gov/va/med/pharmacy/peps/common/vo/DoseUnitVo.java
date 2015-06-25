/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


/**
 * Managed data for dose unit.
 */
public class DoseUnitVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private Collection<DoseUnitSynonymVo> doseUnitSynonyms = new ArrayList<DoseUnitSynonymVo>(0);
    private String fdbDoseUnit;
    private DoseUnitVo replacementDoseUnit;
    private String doseUnitName;
    private Boolean doseIndicator;

    /**
     * getValue.
     * @return String value
     * 
     * @deprecated Use {@link #getDoseUnitName()} instead!
     */
    @Deprecated
    @Override
    public String getValue() {

        return getDoseUnitName();
    }

    /**
     * setValue.
     * 
     * @param value String
     * 
     * @deprecated Use {@link #setDoseUnitName(String)} instead!
     */
    @Deprecated
    @Override
    public void setValue(String value) {
        if (value == null) {
            setDoseUnitName("");
        } else {
            setDoseUnitName(value.toUpperCase(Locale.US));
        } 
    }

    /**
     * List all required fields for this ValueObject, with the pre-condition that the current instance is not a read-only
     * one for the DrugUnitVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.handleListRequiredFields(environment, roles);

        fields.add(FieldKey.FDB_DOSE_UNIT);
        fields.add(FieldKey.DOSE_UNIT_NAME);
        fields.remove(FieldKey.VALUE);

        return fields;
    }

    /**
     * List all second review fields for this ValueObject
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} for the current user
     * @return Set<FieldKey> all second review fields for this object.
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#handleListSecondReviewFields(
     *      gov.va.med.pharmacy.peps.common.vo.Environment,
     *      java.util.Collection)
     */
    @Override
    protected Set<FieldKey> handleListSecondReviewFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.handleListSecondReviewFields(environment, roles);
        fields.add(FieldKey.ITEM_STATUS);
        fields.add(FieldKey.DOSE_UNIT_NAME);
        fields.add(FieldKey.INACTIVATION_DATE);

        return fields;
    }

    /**
     * Returns a list of all fields appropriate for display on the wizard.
     * 
     * @return collection
     */
    @Override
    public Collection<FieldKey> listAllWizardFields() {

        Collection<FieldKey> fields = super.listAllWizardFields();

        fields.remove(FieldKey.VALUE);

        return fields;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one for DoseUnitVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        return fields;
    }

    /**
     * List all uniqueness criteria for this DoseUnitVo
     * 
     * @return Set<FieldKey> All uniqueness fields for this object.
     */
    @Override
    public Set<FieldKey> listUniquenessCriteria() {

        Set<FieldKey> uniqueness = new HashSet<FieldKey>();
        uniqueness.add(FieldKey.DOSE_UNIT_NAME);

        return uniqueness;
    }

    /**
     * getFdbDoseUnit.
     * @return fdbDoseUnit property
     */
    public String getFdbDoseUnit() {

        return fdbDoseUnit;
    }

    /**
     * setFdbDoseUnit.
     * @param fdbDoseUnit fdbDoseUnit property
     */
    public void setFdbDoseUnit(String fdbDoseUnit) {

        this.fdbDoseUnit = trimToEmpty(fdbDoseUnit);
    }

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    @Override
    public EntityType getEntityType() {

        return EntityType.DOSE_UNIT;
    }

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    @Override
    public DomainGroup getDomainGroup() {

        return DomainGroup.GROUP_3;
    }

    /**
     * Returns true if the domain is standardized
     * 
     * @return boolean
     */
    @Override
    public boolean isStandardized() {

        return false;
    }

    /**
     * Returns true if this is a local only domain
     * 
     * @return boolean
     */
    @Override
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * Returns true if the domain is an NDF domain
     * 
     * @return boolean
     */
    @Override
    public boolean isNdf() {

        return false;
    }

    /**
     * public Collection<DoseUnitSynonymVo> getDoseUnitSynonyms() {
     *
     * @return synonyms property
     */
    public Collection<DoseUnitSynonymVo> getDoseUnitSynonyms() {

        return doseUnitSynonyms;
    }

    /**
     * setDoseUnitSynonyms.
     * @param doseUnitSynonyms synonyms property
     */
    public void setDoseUnitSynonyms(Collection<DoseUnitSynonymVo> doseUnitSynonyms) {

        this.doseUnitSynonyms = new ArrayList<DoseUnitSynonymVo>();

        if (doseUnitSynonyms != null && !doseUnitSynonyms.isEmpty()) {
            this.doseUnitSynonyms.addAll(doseUnitSynonyms);
        }
    }

    /**
     * getReplacementDoseUnit.
     * @return replacementDoseUnit property
     */
    public DoseUnitVo getReplacementDoseUnit() {

        return replacementDoseUnit;
    }

    /**
     * setReplacementDoseUnit.
     * @param replacementDoseUnit replacementDoseUnit property
     */
    public void setReplacementDoseUnit(DoseUnitVo replacementDoseUnit) {

        this.replacementDoseUnit = replacementDoseUnit;
    }

    /**
     * getDoseUnitName.
     * @return doseUnitName property
     */
    public String getDoseUnitName() {

        return doseUnitName;
    }

    /**
     * setDoseUnitName.
     * @param doseUnitName doseUnitName property
     */
    public void setDoseUnitName(String doseUnitName) {

        this.doseUnitName = trimToEmpty(doseUnitName.toUpperCase(Locale.US));
    }

    /**
     * return the doseUnitName property
     * 
     * @return short string doseUnitName
     */
    @Override
    public String toShortString() {

        return getDoseUnitName();
    }

    /**
     * return the doseindicator property
     *
     * @return Boolean the doseindicator property
     */
    public Boolean getDoseIndicator() {

        return doseIndicator;
    }

    /**
     * set the doseindicator property
     *
     * @param doseIndicator the dose indicator property
     */
    public void setDoseIndicator(Boolean doseIndicator) {

        this.doseIndicator = doseIndicator;
    }

}
