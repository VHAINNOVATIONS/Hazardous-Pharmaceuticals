/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


/**
 * Represents an administration Schedule
 */
public class AdministrationScheduleVo extends ManagedDataVo {

    private static final long serialVersionUID = 1L;

    private Long frequency;
    private String otherLanguageExpansion;
    private String scheduleOutpatientExpansion;
    private String packagePrefix;
    private String standardAdministrationTimes;
    private String standardShifts;
    private AdministrationScheduleTypeVo adminScheduleType;
    private String standardShift;

    private Collection<WardMultipleVo> wardMultiple = new ArrayList<WardMultipleVo>();
    private Collection<HospitalLocationVo> hospitalLocationMultiple = new ArrayList<HospitalLocationVo>();

    /**
     * Get the schedule name, also the inherited getValue()
     * 
     * @return value property
     */
    public String getScheduleName() {

        return getValue();
    }

    /**
     * Set the scheduleName, also the inherited setValue()
     * 
     * @param scheduleName property
     */
    public void setScheduleName(String scheduleName) {

        setValue(scheduleName);
    }

    /**
     * getFrequency.
     * @return frequency property
     */
    public Long getFrequency() {

        return frequency;
    }

    /**
     * setFrequency.
     * @param frequency frequency property
     */
    public void setFrequency(Long frequency) {

        this.frequency = frequency;
    }

    /**
     * getOtherLanguageExpansion.
     * @return otherLanguageExpansion property
     */
    public String getOtherLanguageExpansion() {

        return otherLanguageExpansion;
    }

    /**
     * setOtherLanguageExpansion.
     * @param otherLanguageExpansion otherLanguageExpansion property
     */
    public void setOtherLanguageExpansion(String otherLanguageExpansion) {

        this.otherLanguageExpansion = trimToEmpty(otherLanguageExpansion);
    }

    /**
     * getScheduleOutpatientExpansion.
     * @return scheduleOutpatientExpansion property
     */
    public String getScheduleOutpatientExpansion() {

        return scheduleOutpatientExpansion;
    }

    /**
     * setScheduleOutpatientExpansion.
     * @param scheduleOutpatientExpansion scheduleOutpatientExpansion property
     */
    public void setScheduleOutpatientExpansion(String scheduleOutpatientExpansion) {

        this.scheduleOutpatientExpansion = trimToEmpty(scheduleOutpatientExpansion);
    }

    /**
     * getPackagePrefix.
     * @return packagePrefix property
     */
    public String getPackagePrefix() {

        return packagePrefix;
    }

    /**
     * setPackagePrefix.
     * @param packagePrefix packagePrefix property
     */
    public void setPackagePrefix(String packagePrefix) {

        this.packagePrefix = trimToEmpty(packagePrefix);
    }

    /**
     * getStandardAdministrationTimes.
     * @return standardAdmininstrationTimes property
     */
    public String getStandardAdministrationTimes() {

        return standardAdministrationTimes;
    }

    /**
     * setStandardAdministrationTimes.
     * @param standardAdmininstrationTimes standardAdmininstrationTimes property
     */
    public void setStandardAdministrationTimes(String standardAdmininstrationTimes) {

        this.standardAdministrationTimes = trimToEmpty(standardAdmininstrationTimes);
    }

    /**
     * getStandardShifts.
     * @return standardShift property
     */
    public String getStandardShifts() {

        return standardShifts;
    }

    /**
     * setStandardShifts.
     * @param incomingStandardShift standardShift property
     */
    public void setStandardShifts(String incomingStandardShift) {

        this.standardShifts = trimToEmpty(incomingStandardShift);
    }

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    public EntityType getEntityType() {

        return EntityType.ADMINISTRATION_SCHEDULE;
    }

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {

        return DomainGroup.GROUP_6;
    }

    /**
     * Returns true if the domain is standardized
     * 
     * @return boolean
     */
    public boolean isStandardized() {

        return false;
    }

    /**
     * getAdminScheduleType.
     * @return scheduleType property
     */
    public AdministrationScheduleTypeVo getAdminScheduleType() {

        return adminScheduleType;
    }

    /**
     * setAdminScheduleType.
     * @param scheduleType scheduleType property
     */
    public void setAdminScheduleType(AdministrationScheduleTypeVo scheduleType) {

        this.adminScheduleType = scheduleType;
    }

    /**
     * List all required fields for this ValueObject, with the pre-condition that the current instance is not a read-only
     * one.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> requiredFields = super.handleListRequiredFields(environment, roles);

        requiredFields.add(FieldKey.PACKAGE_PREFIX);

        return requiredFields;
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
     * Returns true if the domain is an NDF domain
     * 
     * @return boolean
     */
    public boolean isNdf() {

        return false;
    }

    /**
     * getStandardShift.
     * @deprecated
     * @return standardShift property
     */
    public String getStandardShift() {

        return standardShift;
    }

    /**
     * setStandardShift.
     * @deprecated
     * @param standardShift standardShift property
     */
    public void setStandardShift(String standardShift) {

        this.standardShift = trimToEmpty(standardShift);
    }

    /**
     * getWardMultiple.
     * @return wards property
     */
    public Collection<WardMultipleVo> getWardMultiple() {

        return wardMultiple;
    }

    /**
     * setWardMultiple.
     * @param wards wards property
     */
    public void setWardMultiple(Collection<WardMultipleVo> wards) {

        this.wardMultiple = wards;
    }

    /**
     * getHospitalLocationMultiple.
     * @return hospitalLocationMultiple property
     */
    public Collection<HospitalLocationVo> getHospitalLocationMultiple() {

        return hospitalLocationMultiple;
    }

    /**
     * setHospitalLocationMultiple.
     * @param hospitalLocationMultiple hospitalLocationMultiple property
     */
    public void setHospitalLocationMultiple(Collection<HospitalLocationVo> hospitalLocationMultiple) {

        this.hospitalLocationMultiple = hospitalLocationMultiple;
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

        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        fields.add(FieldKey.ITEM_STATUS);

        return fields;
    }
}
