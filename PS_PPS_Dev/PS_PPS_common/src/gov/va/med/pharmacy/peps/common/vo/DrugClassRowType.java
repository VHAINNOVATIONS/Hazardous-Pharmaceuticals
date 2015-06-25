/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;



/**
 * Types available to a Drug Class Row Type
 */
public class DrugClassRowType {
    private String code;
    private String classification;
    private String drugClassIen;

    /**
     * the constructor
     */
    public DrugClassRowType() {
        code = null;
        classification = null;
        drugClassIen = "";
    }

    /**
     * Gets the IEN
     * 
     * @return drug class row IEN
     */
    public String getDrugClassIen() {
        return drugClassIen;
    }

    /**
     * Sets the code
     * @param drugClassIen drugClassIen
     * 
     */
    public void setDrugClassIen(String drugClassIen) {
        this.drugClassIen = drugClassIen;
    }

    /**
     * Gets the code
     * 
     * @return drug class code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code
     * @param code code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the classification
     * 
     * @return classification
     */
    public String getClassification() {
        return classification;
    }

    /**
     * Sets the classification status
     * @param classification classification
     * 
     */
    public void setClassification(String classification) {
        this.classification = classification;
    }
}
