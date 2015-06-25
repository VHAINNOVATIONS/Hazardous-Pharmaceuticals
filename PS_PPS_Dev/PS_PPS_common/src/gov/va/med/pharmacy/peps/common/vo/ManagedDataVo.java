/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


/**
 * Peps-managed domains
 */
public abstract class ManagedDataVo extends ManagedItemVo {
    private static final long serialVersionUID = 1L;

    private String value;

    /**
     * return the value property
     * 
     * @return short string value
     */
    @Override
    public String toShortString() {
        return getValue();
    }

    /**
     * getValue
     * 
     * @return value property
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter
     * 
     * @param value property
     */
    public void setValue(String value) {

        this.value = StringUtils.upperCase(trimToEmpty(value));
    }

    /**
     * Returns true if the domain is standardized
     * 
     * @return boolean
     */
    public abstract boolean isStandardized();

    /**
     * Returns true if the domain is an NDF domain
     * 
     * @return boolean
     */
    public abstract boolean isNdf();

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    public abstract DomainGroup getDomainGroup();

    /**
     * Create a blank template for this {@link ManagedDataVo}
     * 
     * @param environment {@link Environment} in which the template is being made
     * @return blank template
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#blankTemplate()
     */
    @Override
    public ManagedItemVo blankTemplate(Environment environment) {
        return ManagedItemVo.newInstance(getEntityType());

    }

    /**
     * Make the given {@link ManagedItemVo} a template.
     * 
     * @param managedItemVo {@link ManagedItemVo}
     * @param environment {@link Environment} in which the template is being made
     * @return template {@link ManagedItemVo}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#makeTemplate(gov.va.med.pharmacy.peps.common.vo.ManagedItemVo)
     */
    @Override
    protected ManagedItemVo makeTemplate(ManagedItemVo managedItemVo, Environment environment) {
        ManagedDataVo managedData = (ManagedDataVo) managedItemVo;
        managedData.setId(null);
        managedData.setInactivationDate(null);

        return managedData;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        if (environment.isLocal() && getDomainGroup().canEditLocal() || environment.isNational()
            && getDomainGroup().canEditNational()) {
            fields.add(FieldKey.VUID);
            fields.add(FieldKey.EFFECTIVE_DATES);
        } else {
            fields.addAll(listAllFields());
        }

        // National and Pending can be edited, all others not so much
        if (!(environment.isNational() && getRequestItemStatus().isPending())) {
            fields.addAll(listUniquenessCriteria());
        }

        return fields;
    }

    /**
     * Returns a list of all fields appropriate for display on the wizard.
     * 
     * @return collection
     */
    public Collection<FieldKey> listAllWizardFields() {
        Collection<FieldKey> fields = listAllFields();
        fields.remove(FieldKey.REJECTION_REASON_TEXT);
        fields.remove(FieldKey.ITEM_STATUS);
        fields.remove(FieldKey.REQUEST_ITEM_STATUS);
        fields.remove(FieldKey.REQUEST_REJECTION_REASON);
        fields.remove(FieldKey.ID);
        fields.remove(FieldKey.VA_DATA_FIELDS);
        fields.remove(FieldKey.LOCAL_USE);
        fields.remove(FieldKey.INACTIVATION_DATE);
        fields.remove(FieldKey.VUID);
        fields.remove(FieldKey.CREATED_BY);
        fields.remove(FieldKey.CREATED_DATE);
        fields.remove(FieldKey.MODIFIED_BY);
        fields.remove(FieldKey.MODIFIED_DATE);
        fields.remove(FieldKey.PARTIAL_SAVE);
        fields.remove(FieldKey.PREVIOUSLY_MARKED_FOR_LOCAL_USE);
        fields.remove(FieldKey.REVISION_NUMBER);
        fields.remove(FieldKey.GCN_SEQUENCE_NUMBER);
        fields.remove(FieldKey.SPANISH_TEXT);
        fields.remove(FieldKey.MASTER_ENTRY_FOR_VUID);

        /* until each of the domains is created separately so it doesn't use reflection, remove the following */
        fields.remove(FieldKey.MANUFACTURER_IEN);
        fields.remove(FieldKey.PACKAGETYPE_IEN);
        fields.remove(FieldKey.GENERIC_IEN);
        fields.remove(FieldKey.INGREDIENT_IEN);
        fields.remove(FieldKey.DRUGCLASS_IEN);
        fields.remove(FieldKey.DOSAGE_FORM_IEN);
        fields.remove(FieldKey.DRUG_UNIT_IEN);
        fields.remove(FieldKey.DISPENSE_UNIT_IEN);    
        fields.remove(FieldKey.EFFECTIVE_DATES);
        fields.remove(FieldKey.MARKED_FOR_PEPS_SECOND_REVIEW);
        fields.remove(FieldKey.PSR_NAME);
        fields.remove(FieldKey.ACTIVE_INGREDIENT);
        fields.remove(FieldKey.DRUG_CLASSES);
        fields.remove(FieldKey.SYNONYMS);
        

        return fields;
    }

    /**
     * Description
     *
     * @return Collection
     */
    public Collection<FieldKey> getAllWizardFields() {
        return listAllWizardFields();
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

        return uniqueness;
    }

    /**
     * List all required fields for this ValueObject, including the uniqueness criteria
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListRequiredFields(environment, roles);
        fields.addAll(listUniquenessCriteria());

        return fields;
    }

    /**
     * The default sorted {@link FieldKey} for managed domains is {@link FieldKey#VALUE}.
     * 
     * @return {@link FieldKey} to sort by
     */
    @Override
    public FieldKey getDefaultSortedFieldKey() {
        return FieldKey.VALUE;
    }
}
