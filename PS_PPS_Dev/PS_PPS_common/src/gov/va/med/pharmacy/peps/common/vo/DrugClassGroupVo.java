/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing a Product's drug classification group.
 */
public class DrugClassGroupVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private DrugClassVo drugClass;
    private boolean primary;

    /**
     * getDrugClass.
     * 
     * @return DrugClassVo
     */
    public DrugClassVo getDrugClass() {
        return drugClass;
    }

    /**
     * setDrugClass.
     * 
     * @param drugClass the DrugClassVo
     */
    public void setDrugClass(DrugClassVo drugClass) {
        this.drugClass = drugClass;
    }

    /**
     * getPrimary.
     * @return primary property
     */
    public boolean getPrimary() {
        return primary;
    }

    /**
     * setPrimary.
     * 
     * @param primary primary property
     */
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    /**
     * this is the drug Class Group Vo short string method.
     *  
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toShortString() {
        
        StringBuffer s3 = new StringBuffer();

        //added s3 for displaying peps:text responses on confirmation pages, quick actions, etc.
        if (drugClass != null) {
            s3.append("<li style='list-style: none; margin-left:0px; display: block; padding: 0px;'>");
            s3.append(drugClass.getCode());
            s3.append(" - ");
            s3.append(drugClass.getClassification());
            s3.append("</li>");
        }
  
        return s3.toString();
    
    }
}
