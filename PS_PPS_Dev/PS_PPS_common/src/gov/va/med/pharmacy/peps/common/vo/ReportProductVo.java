/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;
import java.util.List;


/**
 * 
 * ReportProductVo
 *
 */
public class ReportProductVo extends ValueObject {

    /** FIELD_SEPERATOR */
    public static final String FIELD_SEPERATOR = " | ";

    private static final long serialVersionUID = 1L;

    private String id;
    private Date proposedInactivationDate;
    private String excluded;
    private String vaProductName;
    private String gcnSeqNo;
    private boolean spanish = false;
    private List<ReportWarningLabelVo> warningLabels;
    private List<ReportIngredientsVo> ingredient;

    /**
     * setWarningLabels
     * @param warningLabels warningLabels
     */
    public void setWarningLabels(List<ReportWarningLabelVo> warningLabels) {

        this.warningLabels = warningLabels;
    }

    /**
     * getWarningLabels
     * @return warningCodes
     */
    public List<ReportWarningLabelVo> getWarningLabels() {

        return warningLabels;
    }

    /**
     * setSpanish
     * @param bSpanish spanish
     */
    public void setSpanish(boolean bSpanish) {

        this.spanish = bSpanish;
    }

    /**
     * isSpanish
     * @return spanish
     */
    public boolean isSpanish() {

        return spanish;
    }

    /**
     * setIngredients
     * 
     * @param ingredients ingredients
     */
    public void setIngredients(List<ReportIngredientsVo> ingredients) {

        this.ingredient = ingredients;
    }

    /**
     * getIngredients
     * @return ingredients
     */
    public List<ReportIngredientsVo> getIngredients() {

        return ingredient;
    }

    /**
     * setGcnSeqNo
     * @param gcnSeqNo gcnSeqNo
     */
    public void setGcnSeqNo(String gcnSeqNo) {

        this.gcnSeqNo = gcnSeqNo;
    }

    /**
     * getGcnSeqNo
     * @return gcnSeqNo
     */
    public String getGcnSeqNo() {

        return gcnSeqNo;
    }

    /**
     * setProposedInactionDate
     * @param proposedInactivationDt proposedInactivationDate
     */
    public void setProposedInactionDate(Date proposedInactivationDt) {

        this.proposedInactivationDate = proposedInactivationDt;
    }

    /**
     * getProposedInactionDate
     * @return proposedInactivationDate
     */
    public Date getProposedInactionDate() {

        return proposedInactivationDate;
    }

    /**
     * setVaProductName
     * 
     * @param vaProductName the vaProductName to set
     */
    public void setVaProductName(String vaProductName) {

        this.vaProductName = vaProductName;
    }

    /**
     * getVaProductName
     * 
     * @return the vaProductName
     */
    public String getVaProductName() {

        return vaProductName;
    }

    /**
     * setExcluded
     * 
     * @param excluded the excluded to set
     */
    public void setExcluded(String excluded) {

        this.excluded = excluded;
    }

    /**
     * getExcluded
     * 
     * @return the excluded
     */
    public String getExcluded() {

        return excluded;
    }
    
    /**
     * Product ID
     * @return String ID
     */
    public String getId() {
        
        return id;
    }

    
    /**
     * Product ID
     * @param id 
     */
    public void setId(String id) {
    
        this.id = id;
    }
}
