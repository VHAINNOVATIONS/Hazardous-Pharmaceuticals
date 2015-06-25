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

import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;


/**
 * Data representing a Product's dosage form
 */
public class DosageFormVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private Collection<DispenseUnitPerDoseVo> dfDispenseUnitsPerDose = new ArrayList<DispenseUnitPerDoseVo>();
    private Collection<DosageFormNounVo> dfNouns = new ArrayList<DosageFormNounVo>();
    private Collection<DosageFormUnitVo> dfUnits = new ArrayList<DosageFormUnitVo>();
    private ExcludeDosageCheck excludeFromDosageChks;
    private String dosageFormName;
    private Collection<LocalMedicationRouteVo> localMedRoutes = new ArrayList<LocalMedicationRouteVo>();
    
    //Attributes added during migration
    private String dosageFormIen;


    /**
     * getEntityType.
     * @return entity type of this vo
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedDataVo#getEntityType()
     */
    public EntityType getEntityType() {
        return EntityType.DOSAGE_FORM;
    }

    /**
     * Create a blank template for this {@link ManagedDataVo}
     * 
     * @param environment {@link Environment} in which the template is being made
     * @return blank template
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#blankTemplate()
     */
    @Override
    public DosageFormVo blankTemplate(Environment environment) {
        DosageFormVo dosage = new DosageFormVo();

        Collection<DispenseUnitPerDoseVo> unitsPerDose = new ArrayList<DispenseUnitPerDoseVo>();

        dosage.setDfDispenseUnitsPerDose(unitsPerDose);

        // set default value of exclude from dosage checks to "NO"
        dosage.setExcludeFromDosageChks(ExcludeDosageCheck.NO);

        return dosage;

    }

    /**
     * getDosageFormIen.
     * @return the IEN for this dosage form
     */
    public String getDosageFormIen() {
        return dosageFormIen;
    }

    /**
     * Set the IEN for this dosage form
     * @param dosageFormIen DosageFormIen
     *
     */
    public void setDosageFormIen(String dosageFormIen) {
        this.dosageFormIen = dosageFormIen;
    }

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {
        return DomainGroup.DOSAGE_FORM;
    }

    /**
     * isStandardized.
     * 
     * @return boolean that indicates if the domain is standardized
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedDataVo#isStandardized() 
     */
    public boolean isStandardized() {
        return false;
    }

    /**
     * Returns true if this is a local only domain for DosageFormVo
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * getDosageFormName.
     * @return code property
     */
    public String getDosageFormName() {
        return dosageFormName;
    }

    /**
     * setDosageFormName.
     * @param dosageFormName String
     */
    public void setDosageFormName(String dosageFormName) {
        if (dosageFormName == null) {
            this.dosageFormName = null;
        } else {
            this.dosageFormName = trimToEmpty(dosageFormName.toUpperCase(Locale.US));
        }

    }

    /**
     * getValue.
     * @return String value
     * 
     * @deprecated Use {@link #getDosageFormName()} instead! This shouldn't be 
     * deprecated as the Selectable Interface uses this to display values
     */
    @Deprecated
    @Override
    public String getValue() {
        return getDosageFormName();
    }

    /**
     * setValue.
     * @param value String
     * 
     * @deprecated Use {@link #setDosageFormName(String)} instead!
     */
    @Deprecated
    @Override
    public void setValue(String value) {
        setDosageFormName(value);
    }

    /**
     * Returns true if the domain is an NDF domain for DosageFormVo.
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return false;
    }

    /**
     * getDfDispenseUnitsPerDose.
     * @return dfDispenseUnitsPerDose property
     */
    public Collection<DispenseUnitPerDoseVo> getDfDispenseUnitsPerDose() {
        return dfDispenseUnitsPerDose;
    }

    /**
     * setDfDispenseUnitsPerDose.
     * @param dfDispenseUnitsPerDose dfDispenseUnitsPerDose property
     */
    public void setDfDispenseUnitsPerDose(Collection<DispenseUnitPerDoseVo> dfDispenseUnitsPerDose) {
        this.dfDispenseUnitsPerDose = new ArrayList<DispenseUnitPerDoseVo>();

        if (dfDispenseUnitsPerDose != null && !dfDispenseUnitsPerDose.isEmpty()) {
            this.dfDispenseUnitsPerDose.addAll(dfDispenseUnitsPerDose);
        }
    }

    /**
     * getDfNouns.
     * @return dfNouns property
     */
    public Collection<DosageFormNounVo> getDfNouns() {
        return dfNouns;
    }

    /**
     * setDfNouns.
     * @param dfNouns dfNouns property
     */
    public void setDfNouns(Collection<DosageFormNounVo> dfNouns) {
        this.dfNouns = new ArrayList<DosageFormNounVo>();

        if (dfNouns != null && !dfNouns.isEmpty()) {
            this.dfNouns.addAll(dfNouns);
        }
    }

    /**
     * getDfUnits.
     * @return dfUnits property
     */
    public Collection<DosageFormUnitVo> getDfUnits() {
        return dfUnits;
    }

    /**
     * setDfUnits.
     * @param dfUnits dfUnits property
     */
    public void setDfUnits(Collection<DosageFormUnitVo> dfUnits) {
        this.dfUnits = new ArrayList<DosageFormUnitVo>();

        if (dfUnits != null && !dfUnits.isEmpty()) {
            this.dfUnits.addAll(dfUnits);
        }
    }

    /**
     * List all non-editable fields for this DosageFormVo.
     * 
     * Note: Though the DosageFormVo isEditable() could be used directly, using the authorization framework (via the 
     * returned list)
     * eases the implementation of the custom JSP tag files that render the fields.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All non-editable fields for this object.
     */
    @Override
    public Set<FieldKey> listNonEditableFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = new HashSet<FieldKey>();

        // data fields for DosageFormVo.
        for (DataField dataField : getVaDataFields().getDataFields().values()) {
            if (!dataField.isEditable()) {
                fields.add(dataField.getKey());
            }
        }

        return fields;
    }

    /**
     * getLocalMedRoutes.
     * @return localMedRoutes property
     */
    public Collection<LocalMedicationRouteVo> getLocalMedRoutes() {
        return localMedRoutes;
    }

    /**
     * setLocalMedRoutes.
     * @param localMedRoutes localMedRoutes property
     */
    public void setLocalMedRoutes(Collection<LocalMedicationRouteVo> localMedRoutes) {
        this.localMedRoutes = new ArrayList<LocalMedicationRouteVo>();

        if (localMedRoutes != null && !localMedRoutes.isEmpty()) {
            this.localMedRoutes.addAll(localMedRoutes);
        }
    }

    /**
     * Sub-class custom handler for non VA data field type Objects that need to be set on to 'this' item based on data in the
     * specified localVo. This method is called by 'mergeNonDataFieldsForUpdate()'. <BR>
     * Sub-classes should only override this method if there are non-national-VADF fields that need to be specially handled
     * before they can be set on 'this' item, or else if these are local-only fields that are not of type DataField stored in
     * 'this' items 'dataFields' property (as 'mergeNonDataFieldsForUpdate()' handles moving this over). <strong>For every
     * field custom handled here, a FieldKey for that field must be returned in the collection, or else these fields might be
     * clobbered by the 'mergeNonDataFieldsForUpdate()' method that calls this one.</strong>
     * 
     * @param localVo the local managedItemVo to merge with 'this' managedItemVo.
     * @return Collection<FieldKey> The FieldKeys handled within this method.
     */
    @Override
    protected Collection<FieldKey> handleMergeLocalFields(ManagedItemVo localVo) {
        List<FieldKey> handledFields = new ArrayList<FieldKey>();

        // the current Vo is the one coming from the National

        DosageFormVo localDosageForm = (DosageFormVo) localVo;

        handledFields.add(FieldKey.DF_NOUNS);
        this.setDfNouns(localDosageForm.getDfNouns());        

        return handledFields;
    }

    /**
     * getExcludeFromDosageChks.
     * @return excludeFromDosageChks property
     */
    public ExcludeDosageCheck getExcludeFromDosageChks() {
        return excludeFromDosageChks;
    }

    /**
     * setExcludeFromDosageChks.
     * @param excludeFromDosageChks excludeFromDosageChks property
     */
    public void setExcludeFromDosageChks(ExcludeDosageCheck excludeFromDosageChks) {
        this.excludeFromDosageChks = excludeFromDosageChks;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one for this DosageFormVo..
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        if (environment.isLocal()) {
            fields.add(FieldKey.ITEM_STATUS);
        }

        return fields;
    }

    /**
     * List all second review fields for this DosageFormVo.
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
        fields.add(FieldKey.DOSAGE_FORM_NAME);
        fields.add(FieldKey.INACTIVATION_DATE);

        return fields;
    }

    /**
     * List all required fields for this ValueObject, with the pre-condition that the current instance is not a read-only
     * on for DoseFormVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> requiredFields = super.handleListRequiredFields(environment, roles);

        requiredFields.add(FieldKey.DOSAGE_FORM_NAME);
        requiredFields.remove(FieldKey.VALUE);

        return requiredFields;
    }

    /**
     * List all uniqueness criteria for this DosageFormVo..
     * 
     * @return Set<FieldKey> All uniqueness fields for this object.
     */
    @Override
    public Set<FieldKey> listUniquenessCriteria() {
        Set<FieldKey> uniqueness = new HashSet<FieldKey>();
        uniqueness.add(FieldKey.DOSAGE_FORM_NAME);

        return uniqueness;
    }
    
    /**
     * Strips out local only data from the NDC
     * 
     * @return A NDC minus any local only data
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#removeLocalData()
     */
    @Override
    public DosageFormVo removeLocalData() {
        DosageFormVo copy = this.copy();

        copy.getVaDataFields().removeDataField(FieldKey.CONJUNCTION);
        copy.getVaDataFields().removeDataField(FieldKey.VERB);
        copy.getVaDataFields().removeDataField(FieldKey.OTHER_LANGUAGE_VERB);

        copy.getVaDataFields().removeDataField(FieldKey.OTHER_LANGUAGE_PREPOSITION);
        copy.getVaDataFields().removeDataField(FieldKey.PREPOSITION);
        
        copy.setLocalMedRoutes(Collections.EMPTY_LIST);
        
        copy.setDfNouns(Collections.EMPTY_LIST);
        

        return copy;
    }

    
    /**
     * return the dosageFormName property
     * 
     * @return short string dosageFormName
     */
    @Override
    public String toShortString() {
        return getDosageFormName();
    }
}
