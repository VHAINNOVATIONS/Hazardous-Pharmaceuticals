/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.Set;


/**
 * Represents an RX Consult domain
 */
public class RxConsultVo extends ManagedDataVo {

    private static final long serialVersionUID = 1L;

    private String consultText;
    private RxConsultVo warningMapping;
    private String spanishTranslation;
    private RxConsultType rxConsultType;

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    @Override
    public EntityType getEntityType() {

        return EntityType.RX_CONSULT;
    }

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    @Override
    public DomainGroup getDomainGroup() {

        return DomainGroup.RX_CONSULT;
    }

    /**
     * Returns true if the domain is standardized for RxConsultVo
     * 
     * @return boolean
     */
    @Override
    public boolean isStandardized() {

        return false;
    }

    /**
     * Returns true if this is a {@link RxConsultType#LOCAL}
     * 
     * @return boolean
     */
    @Override
    public boolean isLocalOnlyData() {

        return RxConsultType.LOCAL.equals(rxConsultType);
    }

    /**
     * Returns true if the domain is an NDF domain for RxConsultVo
     * 
     * @return boolean
     */
    @Override
    public boolean isNdf() {

        return false;
    }

    /**
     * Returns if this RxConsultVo requires two reviews before adds/modifies are approved or rejected.
     * <p>
     * Differs from {@link FieldKey#isRequiresSecondApproval()} as this method is used during adds and the FieldKey method is
     * used during mods. In addition, this method only applies to {@link ManagedItemVo}, not individual fields.
     * 
     * @return boolean True if this item requires two reviews, otherwise, only one review is needed.
     */
    @Override
    public boolean isTwoReviewItem() {

        return false;
    }

    /**
     * Description
     * 
     * @return consultText property
     */
    public String getConsultText() {

        return consultText;
    }

    /**
     * Description
     * 
     * @param consultText consultText property
     */
    public void setConsultText(String consultText) {

        this.consultText = trimToEmpty(consultText);
    }

    /**
     * Description
     * 
     * @return warningMapping property
     */
    public RxConsultVo getWarningMapping() {

        return warningMapping;
    }

    /**
     * Description
     * 
     * @param warningMapping warningMapping property
     */
    public void setWarningMapping(RxConsultVo warningMapping) {

        this.warningMapping = warningMapping;
    }

    /**
     * Description
     * 
     * @return spanishTranslation property
     */
    public String getSpanishTranslation() {

        return spanishTranslation;
    }

    /**
     * Description
     * 
     * @param spanishTranslation spanishTranslation property
     */
    public void setSpanishTranslation(String spanishTranslation) {

        this.spanishTranslation = trimToEmpty(spanishTranslation);
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one.
     * <p>
     * No one can edit a COTS RX Consult. A Local cannot edit a National RX Consult, and just to be safe, a National cannot
     * edit a Local RX Consult (though that should never happen). Otherwise, the RxConsultType is disabled.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {

        if (RxConsultType.COTS.equals(rxConsultType) || (environment.isLocal() && RxConsultType.NATIONAL.equals(rxConsultType))
            || (environment.isNational() && RxConsultType.LOCAL.equals(rxConsultType))) {
            Set<FieldKey> fields = listAllFields();

            if (Environment.NATIONAL.equals(environment) && rxConsultType.isNational()) {
                fields.remove(FieldKey.VALUE);
            } else {

                // environment is local
                if (rxConsultType.isLocal()) {
                    fields.remove(FieldKey.VALUE);
                }

            }

            return fields;

        }

        Set<FieldKey> disabledFields = super.handleListDisabledFields(environment, roles);
        disabledFields.add(FieldKey.RX_CONSULT_TYPE);
        disabledFields.remove(FieldKey.CONSULT_TEXT);
        disabledFields.add(FieldKey.ITEM_STATUS);
        disabledFields.remove(FieldKey.SPANISH_TRANSLATION);
        disabledFields.remove(FieldKey.WARNING_MAPPING);

        if (Environment.NATIONAL.equals(environment)) {
            disabledFields.remove(FieldKey.VALUE);
        } else {

            // environment is local
            if (rxConsultType.isLocal()) {
                disabledFields.remove(FieldKey.VALUE);
            }
        }

        return disabledFields;
    }

    /**
     * List all required fields for this ValueObject, with the pre-condition that the current instance is not a read-only
     * one for the RxConsultVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> requiredFields = super.handleListRequiredFields(environment, roles);

        requiredFields.add(FieldKey.CONSULT_TEXT);

        return requiredFields;
    }

    /**
     * Description
     * 
     * @return rxConsultType property
     */
    public RxConsultType getRxConsultType() {

        return rxConsultType;
    }

    /**
     * Description
     * 
     * @param rxConsultType rxConsultType property
     */
    public void setRxConsultType(RxConsultType rxConsultType) {

        this.rxConsultType = rxConsultType;
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
    public ManagedItemVo blankTemplate(Environment environment) {

        RxConsultVo template = (RxConsultVo) super.blankTemplate(environment);
        template.setRxConsultType(RxConsultType.toRxConsultType(environment));

        return template;
    }

    /**
     * Make the given {@link ManagedItemVo} a template for .
     * RxConsultVo.
     * 
     * @param managedItemVo {@link ManagedItemVo}
     * @param environment {@link Environment} in which the template is being made
     * @return template {@link ManagedItemVo}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedItemVo#makeTemplate(gov.va.med.pharmacy.peps.common.vo.ManagedItemVo)
     */
    @Override
    protected ManagedItemVo makeTemplate(ManagedItemVo managedItemVo, Environment environment) {

        RxConsultVo template = (RxConsultVo) super.makeTemplate(managedItemVo, environment);
        template.setRxConsultType(RxConsultType.toRxConsultType(environment));

        return template;
    }

}
