/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * Data representing an Ingredient
 */
public class IngredientVo extends ManagedDataVo {

    private static final long serialVersionUID = 1L;

    private String vuid = "";
    private IngredientVo primaryIngredient;

    //Attributes added during migration
    private String ingredientIen;
    private String ingredientStatus = "Active";
    private boolean masterEntryForVuid = true;
    private List<VuidStatusHistoryVo> effectiveDates;

    /**
     * the constructor
     */
    public IngredientVo() {

        super();
    }

    /**
     * Gets a list of the Ingredient effective dates and the status on those dates
     * 
     * @return the list of ingredient effective date objects
     */
    public List<VuidStatusHistoryVo> getEffectiveDates() {

        return effectiveDates;
    }

    /**
     * Sets a list of the effective dates and the status on those dates
     * 
     * @param effectiveDates effectiveDates
     */
    public void setEffectiveDates(List<VuidStatusHistoryVo> effectiveDates) {

        this.effectiveDates = effectiveDates;
    }

    /**
     * Gets the Master Entry for VUID
     * 
     * @return Master Entry for VUID status
     */
    public boolean getMasterEntryForVuid() {

        return masterEntryForVuid;
    }

    /**
     * Sets the Master VUID for entry status
     * @param masterEntryForVuid masterEntryForVuid
     * 
     */
    public void setMasterEntryForVuid(boolean masterEntryForVuid) {

        this.masterEntryForVuid = masterEntryForVuid;
    }

    /**
     * Gets the Ingredient status
     * 
     * @return Ingredient status
     */
    public String getIngredientStatus() {

        return ingredientStatus;
    }

    /**
     * Sets the Ingredient status
     * @param ingredientStatus ingredientStatus
     */
    public void setIngredientStatus(String ingredientStatus) {

        this.ingredientStatus = ingredientStatus;
    }

    /**
     * Gets the Ingredient IEN
     * 
     * @return IEN
     */
    public String getNdfIngredientIen() {

        return ingredientIen;
    }

    /**
     * Sets the Ingredient IEN.
     * @param ndfIngredientIen drugIngredientIen
     */
    public void setIngredientIen(String ndfIngredientIen) {

        this.ingredientIen = ndfIngredientIen;
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
     * getPrimaryIngredient.
     * @return primaryIngredientName property
     */
    public IngredientVo getPrimaryIngredient() {

        return primaryIngredient;
    }

    /**
     * setPrimaryIngredient.
     * 
     * @param primaryIngredient primaryIngredient property
     */
    public void setPrimaryIngredient(IngredientVo primaryIngredient) {

        this.primaryIngredient = primaryIngredient;
    }

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    public EntityType getEntityType() {

        return EntityType.INGREDIENT;
    }

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {

        return DomainGroup.INGREDIENT;
    }

    /**
     * Returns true if the domain is standardized for IngredientVo
     * 
     * @return boolean
     */
    public boolean isStandardized() {

        return true;
    }

    /**
     * Returns true if this is a local only domain for IngredientVo
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for IngredientVo
     * 
     * @return boolean
     */
    public boolean isNdf() {

        return true;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one for IngredientVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        fields.add(FieldKey.EFFECTIVE_DATES);

        if (Environment.LOCAL.equals(environment)) {
            fields.add(FieldKey.ITEM_STATUS);
        }

        if (!getRequestItemStatus().isPending()) {
            fields.add(FieldKey.VALUE);
        }

        return fields;
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
    public IngredientVo blankTemplate(Environment environment) {

        IngredientVo ingredient = new IngredientVo();

        ingredient.setPrimaryIngredient(primaryIngredient);

        return ingredient;

    }

    /**
     * List all second review fields for The ingredientVo
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} for the current user
     * @return Set<FieldKey> all second review fields for this object.
     * 
     */
    @Override
    protected Set<FieldKey> handleListSecondReviewFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> fields = super.handleListSecondReviewFields(environment, roles);
        fields.add(FieldKey.ITEM_STATUS);
        fields.add(FieldKey.INACTIVATION_DATE);
        fields.add(FieldKey.PRIMARY_INGREDIENT);

        return fields;
    }

    /**
     * Used to remove the Primary Ingredient drop down field when adding a new Primary Ingredient.
     * 
     * @return collection
     */
    public Collection<FieldKey> listAllIngredientFields() {

        Collection<FieldKey> fields = listAllWizardFields();
        fields.remove(FieldKey.PRIMARY_INGREDIENT);

        return fields;
    }

    /**
     * Returns fields used in the Add Primary Ingredient wizard.
     *
     * @return Collection
     */
    public Collection<FieldKey> getAllIngredientFields() {

        return listAllIngredientFields();
    }
}
