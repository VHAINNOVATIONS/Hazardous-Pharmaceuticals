/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 *
 * Enum for the preference type for the method of displaying drugClassifications
 */
public enum DrugClassSortPreference {
    
    /**
     * CODE.
     */
    CODE("code"),
    
    /**
     * CLASSIFICATION
     */
    CLASSIFICATION("classification");
    
    private String value;
   
    /**
     * Constructor sets the value up for string use
     * 
     * @param value string
     */
    private DrugClassSortPreference(String value) {
        this.value = value;
    }

    /**
     * getValue.
     * @return value property
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * is this enum the CODE.
     * @return boolean
     */
    public boolean isCode() {
        return CODE.equals(this);
    }
    
    /**
     * 
     * is this enum the Classification.
     * @return boolean
     */
    public boolean isClassification() {
        return CLASSIFICATION.equals(this);
    }
}
