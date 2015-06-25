/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;


/**
 * Data representing a Drug Text Domain.
 */
public class DrugTextVo extends ManagedDataVo {
    private static final long serialVersionUID = 1L;
    private String textLocal;
    private String textNational;
    private Collection<DrugTextSynonymVo> drugTextSynonyms = new ArrayList<DrugTextSynonymVo>(0);
    private DrugTextType drugTextType; // represents national or local

    /**
     * getTextLocal.
     * @return drugText property
     */
    public String getTextLocal() {
        return textLocal;
    }

    /**
     * setTextLocal.
     * @param textLocal drugText property
     */
    public void setTextLocal(String textLocal) {
        this.textLocal = trimToEmpty(textLocal);
    }

    /**
     * Returns the entity for the managed item.
     * 
     * @return EntityType
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.DRUG_TEXT;
    }

    /**
     * Returns true if the domain is standardized.
     * 
     * @return boolean
     */
    @Override
    public boolean isStandardized() {
        return false;
    }

    /**
     * returns the domain group of the managed Data for the DrugTextVo.
     * 
     * @return domainGroup
     */
    @Override
    public DomainGroup getDomainGroup() {
        return DomainGroup.DRUG_TEXT;
    }

    /**
     * Returns true if this is a local only domain
     * 
     * @return boolean
     */
    @Override
    public boolean isLocalOnlyData() {
        return drugTextType.isLocal();
    }

    /**
     * Returns true if the domain is an NDF domain for the DrugTextVo
     * 
     * @return boolean
     */
    @Override
    public boolean isNdf() {
        return false;
    }

    /**
     * Returns if this item requires two reviews before adds/modifies are approved or rejected for the DrugTextVo.
     * <p>
     * Differs from {@link FieldKey#isRequiresSecondApproval()} as this method is used during adds and the FieldKey method is
     * used during mods. In addition, this method only applies to {@link ManagedItemVo}, not individual fields.
     * 
     * @return boolean True if this item requires two reviews, otherwise, only one review is needed so return fals.
     */
    @Override
    public boolean isTwoReviewItem() {
        return false;
    }

    /**
     * List all required fields for this ValueObject, with the pre-condition that the current instance is not a read-only
     * one for the DrugTextVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> requiredFields = super.handleListRequiredFields(environment, roles);

        if (environment.isLocal()) {
            requiredFields.add(FieldKey.TEXT_LOCAL);
        } else {
            requiredFields.add(FieldKey.TEXT_NATIONAL);
        }

        return requiredFields;
    }

    /**
     * getDrugTextType for the DrugTextVo.
     * @return drugTextType property
     */
    public DrugTextType getDrugTextType() {
        return drugTextType;
    }

    /**
     * setDrugTextType.
     * @param drugTextType drugTextType property
     */
    public void setDrugTextType(DrugTextType drugTextType) {
        this.drugTextType = drugTextType;
    }

    /**
     * Create a blank template for this {@link ManagedDataVo}
     * 
     * @param environment {@link Environment} in which the template is being made
     * @return blank template
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#blankTemplate()
     */
    public DrugTextVo blankTemplate(Environment environment) {
        DrugTextVo drugText = (DrugTextVo) (ManagedItemVo.newInstance(getEntityType()));

        if (environment.isLocal()) {
            drugText.setDrugTextType(DrugTextType.LOCAL);
        } else {
            drugText.setDrugTextType(DrugTextType.NATIONAL);
        }

        return drugText;

    }

    /**
     * Returns a list of all fields appropriate for display on the wizard.
     * 
     * @return collection
     */
    public Collection<FieldKey> listAllWizardFields() {
        Collection<FieldKey> fields = super.listAllWizardFields();

        return fields;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one for the DrugTextVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        fields.add(FieldKey.DRUG_TEXT_TYPE);

        if (Environment.NATIONAL.equals(environment)) {
            if (isNewInstance()) {
                fields.remove(FieldKey.VALUE);
            }
        } else {
            
            // at this point environment is local
            // only remove if drug text type is national
            
            
            if (drugTextType.isLocal()) {
                fields.remove(FieldKey.VALUE);
                fields.remove(FieldKey.TEXT_LOCAL);
            }

        }

        return fields;

    }

    /**
     * List all non-editable fields for this DrugTextVo.
     * 
     * Note: Though the DrugtEXTvO isEditable() could be used directly, using the authorization framework 
     * (via the returned list)
     * eases the implementation of the custom JSP tag files that render the fields.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All non-editable fields for this DrugTextVo.
     */
    @Override
    public Set<FieldKey> listNonEditableFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = new HashSet<FieldKey>();

        // set the datafields for the drugTextVo
        for (DataField dataField : getVaDataFields().getDataFields().values()) {
            if (!dataField.isEditable()) {
                fields.add(dataField.getKey());
            }
        }

        return fields;
    }

    /**
     * getDrugTextSynonyms.
     * @return drugTextSynonyms property
     */
    public Collection<DrugTextSynonymVo> getDrugTextSynonyms() {
        return drugTextSynonyms;
    }

    /**
     * setDrugTextSynonyms.
     * @param drugTextSynonyms drugTextSynonyms property
     */
    public void setDrugTextSynonyms(Collection<DrugTextSynonymVo> drugTextSynonyms) {
        this.drugTextSynonyms = new ArrayList<DrugTextSynonymVo>();

        if (drugTextSynonyms != null && !drugTextSynonyms.isEmpty()) {
            this.drugTextSynonyms.addAll(drugTextSynonyms);
        }
    }

    /**
     * getTextNational.
     * @return textNational property
     */
    public String getTextNational() {
        return textNational;
    }

    /**
     * setTextNational.
     * @param textNational textNational property
     */
    public void setTextNational(String textNational) {
        this.textNational = textNational;
    }
}
