/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


/**
 * Managed data for Medication Instruction
 */
public class MedicationInstructionVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private String defaultAdminTimes;
    private String medInstructionExpansion;
    private Long frequency;
    private String instructions;
    private Collection<PossibleIntendedUseVo> intendedUse = new ArrayList<PossibleIntendedUseVo>(0);

    private LocalMedicationRouteVo localMedicationRoute;
    private String otherLanguageExpansion;
    private String plural;
    private String medInstructionSchedule;
    private String medInstructionSynonym;
    private Collection<WardMultipleVo> medInstructionWardMultiple = new ArrayList<WardMultipleVo>();
    private String additionalInstruction;

    /**
     * List all required fields for this ValueObject, with the pre-condition that the current instance is not a read-only
     * one for the MedicationInstructionsVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.handleListRequiredFields(environment, roles);

        fields.add(FieldKey.DEFAULT_ADMIN_TIMES);
        fields.add(FieldKey.MED_INSTRUCTION_EXPANSION);

        return fields;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one for MedInstructionVo
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        fields.add(FieldKey.ITEM_STATUS);

        return fields;
    }

    /**
     * getDefaultAdminTimes
     * 
     * @return defaultAdminTimes property
     */
    public String getDefaultAdminTimes() {
        return defaultAdminTimes;
    }

    /**
     * setDefaultAdminTimes
     * 
     * @param defaultAdminTimes defaultAdminTimes property
     */
    public void setDefaultAdminTimes(String defaultAdminTimes) {
        this.defaultAdminTimes = trimToEmpty(defaultAdminTimes);
    }

    /**
     * getMedInstructionExpansion
     * 
     * @return medInstructionExpansion property
     */
    public String getMedInstructionExpansion() {
        return medInstructionExpansion;
    }

    /**
     * setMedInstructionExpansion
     * 
     * @param medInstructionExpansion String
     */
    public void setMedInstructionExpansion(String medInstructionExpansion) {
        this.medInstructionExpansion = trimToEmpty(medInstructionExpansion);
    }

    /**
     * getFrequency
     * 
     * @return frequency property
     */
    public Long getFrequency() {
        return frequency;
    }

    /**
     * setFrequency
     * 
     * @param frequency frequency property
     */
    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    /**
     * getInstructions
     * 
     * @return instructions property
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * setInstructions
     * 
     * @param instructions instructions property
     */
    public void setInstructions(String instructions) {
        this.instructions = trimToEmpty(instructions);
    }

    /**
     * getIntendedUse
     * 
     * @return packages property
     */
    public Collection<PossibleIntendedUseVo> getIntendedUse() {
        return intendedUse;
    }

    /**
     * setIntendedUse
     * 
     * @param intendedUs property
     */
    public void setIntendedUse(Collection<PossibleIntendedUseVo> intendedUs) {
        this.intendedUse = new ArrayList<PossibleIntendedUseVo>();

        if (intendedUs != null && !intendedUs.isEmpty()) {
            this.intendedUse.addAll(intendedUs);
        }
    }

    /**
     * getOtherLanguageExpansion
     * 
     * @return medInstructionOtherLanguageExpansion property
     */
    public String getOtherLanguageExpansion() {
        return otherLanguageExpansion;
    }

    /**
     * setOtherLanguageExpansion
     * 
     * @param otherLanguageExpansion otherLanguageExpansion property
     */
    public void setOtherLanguageExpansion(String otherLanguageExpansion) {
        this.otherLanguageExpansion = trimToEmpty(otherLanguageExpansion);
    }

    /**
     * getPlural
     * 
     * @return plural property
     */
    public String getPlural() {
        return plural;
    }

    /**
     * setPlural
     * 
     * @param plural plural property
     */
    public void setPlural(String plural) {
        this.plural = trimToEmpty(plural);
    }

    /**
     * getMedInstructionSchedule
     * 
     * @return medInstructionSchedule property
     */
    public String getMedInstructionSchedule() {
        return medInstructionSchedule;
    }

    /**
     * setMedInstructionSchedule
     * 
     * @param medInstructionSchedule medInstructionSchedule property
     */
    public void setMedInstructionSchedule(String medInstructionSchedule) {
        this.medInstructionSchedule = trimToEmpty(medInstructionSchedule);
    }

    /**
     * getMedInstructionSynonym
     * 
     * @return medInstructionSynonym property
     */
    public String getMedInstructionSynonym() {
        return medInstructionSynonym;
    }

    /**
     * setMedInstructionSynonym
     * 
     * @param medInstructionSynonym synonym property
     */
    public void setMedInstructionSynonym(String medInstructionSynonym) {
        this.medInstructionSynonym = trimToEmpty(medInstructionSynonym);
    }

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    public EntityType getEntityType() {
        return EntityType.MEDICATION_INSTRUCTION;
    }

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {
        return DomainGroup.MEDICATION_INSTRUCTION;
    }

    /**
     * Returns true if the domain is standardized for MedicationInstructionsVo.
     * 
     * @return boolean
     */
    public boolean isStandardized() {
        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for MedicationInstructionsVo.
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return false;
    }

    /**
     * getAdditionalInstruction
     * 
     * @return additionalInstruction property
     */
    public String getAdditionalInstruction() {
        return additionalInstruction;
    }

    /**
     * setAdditionalInstruction
     * 
     * @param additionalInstruction additionalInstruction property
     */
    public void setAdditionalInstruction(String additionalInstruction) {
        this.additionalInstruction = trimToEmpty(additionalInstruction);
    }

    /**
     * Returns true if this is a local only domain
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return true;
    }

    /**
     * getMedInstructionWardMultiple
     * 
     * @return ward property
     */
    public Collection<WardMultipleVo> getMedInstructionWardMultiple() {
        return medInstructionWardMultiple;
    }

    /**
     * setMedInstructionWardMultiple
     * 
     * @param medInstructionWardMultiple wards property
     */
    public void setMedInstructionWardMultiple(Collection<WardMultipleVo> medInstructionWardMultiple) {
        this.medInstructionWardMultiple = new ArrayList<WardMultipleVo>();

        if (medInstructionWardMultiple != null && !medInstructionWardMultiple.isEmpty()) {
            this.medInstructionWardMultiple.addAll(medInstructionWardMultiple);
        }
    }

    /**
     * getLocalMedicationRoute
     * 
     * @return medInstructionMedRoute property
     */
    public LocalMedicationRouteVo getLocalMedicationRoute() {
        return localMedicationRoute;
    }

    /**
     * setLocalMedicationRoute
     * 
     * @param medInstructionMedRoute medInstructionMedRoute property
     */
    public void setLocalMedicationRoute(LocalMedicationRouteVo medInstructionMedRoute) {
        this.localMedicationRoute = medInstructionMedRoute;
    }
}
