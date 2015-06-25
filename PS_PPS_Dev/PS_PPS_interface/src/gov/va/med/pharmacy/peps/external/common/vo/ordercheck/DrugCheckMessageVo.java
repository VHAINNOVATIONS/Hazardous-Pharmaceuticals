/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Messages returned by a drug check
 */
public class DrugCheckMessageVo extends ValueObject {
    private static final long serialVersionUID = 1L;
    private DrugCheckVo drug;
    private String severity = "";
    private String type = "";
    private String drugName = "";
    private String text = "";

    /**
     * constructor 
     */
    public DrugCheckMessageVo() {
        super();
    }

    /**
     * getDrug
     * @return DrugCheckVo
     */
    public DrugCheckVo getDrug() {
        return drug;
    }

    /**
     * setDrug
     * @param drug DrugCheckVo
     */
    public void setDrug(DrugCheckVo drug) {
        this.drug = drug;
    }

    /**
     * getSeverity
     * @return String
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * setSeverity
     * @param severity String
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * getType
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * setType
     * @param type String
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getDrugName
     * @return String
     */
    public String getDrugName() {
        return drugName;
    }

    /**
     * setDrugName
     * @param name String
     */
    public void setDrugName(String name) {
        this.drugName = name;
    }

    /**
     * getText
     * @return String
     */
    public String getText() {
        return text;
    }

    /**
     * setText
     * @param text String
     */
    public void setText(String text) {
        this.text = text;
    }
}
