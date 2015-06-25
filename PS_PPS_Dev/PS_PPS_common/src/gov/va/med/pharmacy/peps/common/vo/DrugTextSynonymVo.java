/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing a Drug Text's Synonym.
 * 
 */
public class DrugTextSynonymVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private String drugTextSynonymName;

    /**
     * Get the Drug Text Synonym Name
     * 
     * @return drugTextSynonymName
     */
    public String getDrugTextSynonymName() {
        return drugTextSynonymName;
    }

    /**
     * setDrugTextSynonymName.
     * 
     * @param drugTextSynonymName DrugTextSynonymName
     */
    public void setDrugTextSynonymName(String drugTextSynonymName) {
        this.drugTextSynonymName = drugTextSynonymName;
    }

}
