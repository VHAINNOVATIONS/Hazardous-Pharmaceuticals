/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;



/**
 * Data representing a Product's Active Ingredient
 */
public class ActiveIngredientVo extends ValueObject  {
    private static final long serialVersionUID = 1L;

    private IngredientVo ingredient;
    private DrugUnitVo drugUnit;
    private String strength;
    private boolean active;

    /**
     * getIngredient.
     * 
     * @return ingredient property
     */
    public IngredientVo getIngredient() {
        return ingredient;
    }

    /**
     * setIngredient.
     * 
     * @param ingredient ingredient property
     */
    public void setIngredient(IngredientVo ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * getDrugUnit.
     * 
     * @return drugUnitVo property
     */
    public DrugUnitVo getDrugUnit() {
        return drugUnit;
    }

    /**
     * setDrugUnit.
     * 
     * @param drugUnitVo drugUnit property
     */
    public void setDrugUnit(DrugUnitVo drugUnitVo) {
        this.drugUnit = drugUnitVo;
    }

    /**
     * getStrength.
     * 
     * @return strength property
     */
    public String getStrength() {
        return strength;
    }

    /**
     * setStrength.
     * 
     * @param strength strength property
     */
    public void setStrength(String strength) {
        this.strength = trimToEmpty(strength);
    }

    /**
     * getActive.
     * 
     * @return activeYn property
     */
    public boolean getActive() {
        return active;
    }

    /**
     * setActive.
     * 
     * @param activeYn activeYn property
     */
    public void setActive(boolean activeYn) {
        this.active = activeYn;
    }

    /**
     * Returns the active ingredient ShortString 
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toShortString() {
        
        //original string that produced ACETAMINOPHEN, MG, 500
        //String s4 = "<li style='list-style: none; margin-left:0px; display: block; padding: 0px;'>" 
        //    + ingredient.toShortString() + (drugUnit == null ? "" : ", " 
        //    + drugUnit.toShortString()) + ", " + strength + "</li>";
        
        String s4 = "<li style='list-style: none; margin-left:0px; display: block; padding: 0px;'>" 
            + (ingredient == null ? "" : ingredient.toShortString()) + " " 
            + (strength == null ? "" : strength) + " " + (drugUnit == null ? "" : drugUnit.toShortString()) 
            + "</li>";
      
        return s4;
    }
}
