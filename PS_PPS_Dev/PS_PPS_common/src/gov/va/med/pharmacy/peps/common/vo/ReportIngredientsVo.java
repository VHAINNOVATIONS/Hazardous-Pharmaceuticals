/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * 
 * ReportIngredientsVo
 *
 */
public class ReportIngredientsVo {
    private String ingredient;
    private String strength;
    private String unit;
    private String dosageForm;
    
    /**
     * setIngredient
     * @param ingredient ingredient
     */
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
    
    /**
     * getIngredient
     * @return ingredient
     */
    public String getIngredient() {
        return ingredient;
    }

    /**
     * setStrength
     * @param strength strength
     */
    public void setStrength(String strength) {
        this.strength = strength;
    }
    
    /**
     * getStrength
     * @return strength
     */
    public String getStrength() {
        return strength;
    }
    
    /**
     * setUnit
     * @param unit unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    /**
     * getUnit
     * @return unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * setDosageForm
     * @param dosageForm dosageForm
     */
    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }
    
    /**
     * getDosageForm
     * @return dosageForm
     */
    public String getDosageForm() {
        return dosageForm;
    }
    
}

