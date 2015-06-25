/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * Data representing a Product route of administration
 */
public class LocalMedicationRouteVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private String abbreviation;
    private Boolean displayOnIvp;
    private Boolean ivFlag;
    private String otherLanguageExpansion;
    private String outpatientExpansion;
    private Boolean promptForInjectionSite;
    private StandardMedRouteVo standardMedRoute;

    private Collection<PackageUsageVo> packageUsages = new ArrayList<PackageUsageVo>();

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    public EntityType getEntityType() {
        return EntityType.LOCAL_MEDICATION_ROUTE;
    }

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {
        return DomainGroup.LOCAL_MEDICATION_ROUTE;
    }

    /**
     * Returns true if the domain is Standardized
     * 
     * @return boolean
     */
    public boolean isStandardized() {
        return false;
    }

    /**
     * getAbbreviation
     * 
     * @return abbreviation property
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * setAbbreviation
     * 
     * @param abbreviation abbreviation property
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = trimToEmpty(abbreviation);
    }

    /**
     * getDisplayOnIvp
     * 
     * @return displayOnIVP property
     */
    public Boolean getDisplayOnIvp() {
        return displayOnIvp;
    }

    /**
     * setDisplayOnIvp
     * 
     * @param displayOnIVP displayOnIVP property
     */
    public void setDisplayOnIvp(Boolean displayOnIVP) {
        this.displayOnIvp = displayOnIVP;
    }

    /**
     * getIvFlag
     * 
     * @return ivFlag property
     */
    public Boolean getIvFlag() {
        return ivFlag;
    }

    /**
     * setIvFlag
     * 
     * @param ivFlag ivFlag property
     */
    public void setIvFlag(Boolean ivFlag) {
        this.ivFlag = ivFlag;
    }

    /**
     * getOtherLanguageExpansion
     * 
     * @return otherLanguageExpansion property
     */
    public String getOtherLanguageExpansion() {
        return otherLanguageExpansion;
    }

    /**
     * setOtherLanguageExpansion for LocalMedicationRouteVo.
     * 
     * @param otherLanguageExpansion otherLanguageExpansion property
     */
    public void setOtherLanguageExpansion(String otherLanguageExpansion) {
        this.otherLanguageExpansion = trimToEmpty(otherLanguageExpansion);
    }

    /**
     * getOutpatientExpansion for LocalMedicationRouteVo.
     * 
     * @return outPatientExpansion property
     */
    public String getOutpatientExpansion() {
        return outpatientExpansion;
    }

    /**
     * setOutpatientExpansion
     * 
     * @param outPatientExpansion outPatientExpansion property
     */
    public void setOutpatientExpansion(String outPatientExpansion) {
        this.outpatientExpansion = trimToEmpty(outPatientExpansion);
    }

    /**
     * getPromptForInjectionSite
     * 
     * @return promptForInjectionSite property
     */
    public Boolean getPromptForInjectionSite() {
        return promptForInjectionSite;
    }

    /**
     * setPromptForInjectionSite
     * 
     * @param promptForInjectionSite promptForInjectionSite property
     */
    public void setPromptForInjectionSite(Boolean promptForInjectionSite) {
        this.promptForInjectionSite = promptForInjectionSite;
    }

    /**
     * StandardMedRouteVo
     * 
     * @return standardMedRoute property
     */
    public StandardMedRouteVo getStandardMedRoute() {
        return standardMedRoute;
    }

    /**
     * setStandardMedicationRoute
     * 
     * @param standardMedRoute standardMedicationRoute property
     */
    public void setStandardMedRoute(StandardMedRouteVo standardMedRoute) {
        this.standardMedRoute = standardMedRoute;
    }

    /**
     * Returns true if this is a local only domain  for LocalMedicationRouteVo
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return true;
    }

    /**
     * Returns true if the domain is an NDF domain for LocalMedicationRouteVo
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return false;
    }

    /**
     * getPackageUsages  for LocalMedicationRouteVo
     * 
     * @return packageUsages property
     */
    public Collection<PackageUsageVo> getPackageUsages() {
        return packageUsages;
    }

    /**
     * setPackageUsages  for LocalMedicationRouteVo
     * 
     * @param packageUsages packageUsages property 
     */
    public void setPackageUsages(Collection<PackageUsageVo> packageUsages) {
        this.packageUsages = new ArrayList<PackageUsageVo>();

        if (packageUsages != null && !packageUsages.isEmpty()) {

            this.packageUsages.addAll(packageUsages);
        }
    }

    /**
     * List all uniqueness criteria for this ValueObject.
     * 
     * @return Set<FieldKey> All uniqueness fields for this object.
     */
    @Override
    public Set<FieldKey> listUniquenessCriteria() {
        Set<FieldKey> uniqueness = new HashSet<FieldKey>();
        uniqueness.add(FieldKey.VALUE);
        uniqueness.add(FieldKey.ABBREVIATION);

        return uniqueness;
    }
}
