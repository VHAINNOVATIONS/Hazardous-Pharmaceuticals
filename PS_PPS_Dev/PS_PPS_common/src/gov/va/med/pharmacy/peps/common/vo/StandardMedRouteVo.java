/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.utility.FieldKeyUtility;


/**
 * Data representing a Product route of administration
 */
public class StandardMedRouteVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;

    private String vuid = "";
    private boolean masterEntryForVuid;
    private boolean defaultRoute;
    private String firstDatabankMedRoute;
    private StandardMedRouteVo replacementStandardMedRoute;
    private List<VuidStatusHistoryVo> effectiveDates;

    /**
     * Gets a list of the effective dates and the status for those dates
     * 
     * @return the list of effective date objects
     */
    public List<VuidStatusHistoryVo> getEffectiveDates() {
        return effectiveDates;
    }

    /**
     * Sets a list of the effective dates and the status on those dates
     * @param effectiveDates effectiveDates
     */
    public void setEffectiveDates(List<VuidStatusHistoryVo> effectiveDates) {
        this.effectiveDates = effectiveDates;
    }
    
    /**
     * isMasterEntryForVuid.
     * @return master entry for vuid state
     */
    public boolean isMasterEntryForVuid() {
        return masterEntryForVuid;
    }

    /**
     * Sets the master entry for vuid state
     * 
     * @param masterEntryForVuid masterEntryForVuid 
     */
    public void setMasterEntryForVuid(boolean masterEntryForVuid) {
        this.masterEntryForVuid = masterEntryForVuid;
    }
    
    /**
     * getVuid.
     * @return vuid property
     */
    public String getVuid() {
        return vuid;
    }

    /**
     * setVuid.
     * @param vuid vuid property
     */
    public void setVuid(String vuid) {
        this.vuid = trimToEmpty(vuid);
    }

    /**
     * isDefaultRoute.
     * 
     * @return boolean
     */
    public boolean isDefaultRoute() {
        return defaultRoute;
    }

    /**
     * setDefaultRoute.
     * @param defaultRoute defaultRoute property
     */
    public void setDefaultRoute(boolean defaultRoute) {
        this.defaultRoute = defaultRoute;
    }

    /**
     * setFirstDatabankMedRoute.
     * @param firstDatabankMedRoute firstDatabankMedRoute property
     */
    public void setFirstDatabankMedRoute(String firstDatabankMedRoute) {
        this.firstDatabankMedRoute = trimToEmpty(firstDatabankMedRoute);
    }

    /**
     * getFirstDatabankMedRoute.
     * @return firstDatabankMedRoute property
     */
    public String getFirstDatabankMedRoute() {
        return firstDatabankMedRoute;
    }

    /**
     * toShortString returns toString unless overridden in VO
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toShortString() {
        return getValue();
    }

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    public EntityType getEntityType() {
        return EntityType.STANDARD_MED_ROUTE;
    }

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {
        return DomainGroup.STANDARD_MED_ROUTE;
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
     * Returns true if this is a local only domain
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return false;
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
     * List all required fields for this ValueObject, with the pre-condition that the current instance is not a read-only
     * one.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> requiredFields = super.handleListRequiredFields(environment, roles);

        requiredFields.add(FieldKey.FDB_MED_ROUTE);

        return requiredFields;
    }

    /**
     * getReplacementStandardMedRoute.
     * @return replacementStandardMedRoute property
     */
    public StandardMedRouteVo getReplacementStandardMedRoute() {
        return replacementStandardMedRoute;
    }

    /**
     * setReplacementStandardMedRoute.
     * @param replacementStandardMedRoute replacementStandardMedRoute property
     */
    public void setReplacementStandardMedRoute(StandardMedRouteVo replacementStandardMedRoute) {
        this.replacementStandardMedRoute = replacementStandardMedRoute;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one fo the StandardMedRouteVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        List<FieldKey> associatedKeys = FieldKeyUtility.getSortedFieldKeys(getEntityType(), Locale.getDefault());

        for (FieldKey fieldKey : associatedKeys) {
            fields.add(fieldKey);
        }

        return fields;
    }
}
