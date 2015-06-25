/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;
import java.util.Locale;


/**
 * Types available to an DrugText
 */
public class ReducedCopayVo extends ManagedDataVo {

    private static final long serialVersionUID = 1L;

    private Long eplId;
    private Long productFk;
    private Date reducedCopayStopDate;
    private Date reducedCopayStartDate;

    /**
     * the constructor
     */
    public ReducedCopayVo() {

    }

    /**
     * Gets the getEplId
     * 
     * @return eplId
     */
    public Long getEplId() {

        return eplId;
    }

    /**
     * Sets the setEplId
     * @param eplId eplId
     * 
     */
    public void setEplId(Long eplId) {

        this.eplId = eplId;
    }

    /**
     * Gets the getProductFk
     * 
     * @return productFk
     */
    public Long getProductFk() {

        return productFk;
    }

    /**
     * setProductFk
     * @param productFk productFk
     * 
     */
    public void setProductFk(Long productFk) {

        this.productFk = productFk;
    }

    /**
     * Gets the start date of reduced Copay
     * 
     * @return reducedCopayStartDate
     */
    public Date getReducedCopayStartDate() {
        return reducedCopayStartDate;
    }

    /**
     * Sets the start date time
     * @param startDate 
     * 
     */
    public void setReducedCopayStartDate(Date startDate) {
        this.reducedCopayStartDate = startDate;
    }

    /**
     * Gets the stop date of reduced Copay
     * 
     * @return reducedCopayStopDate
     */
    public Date getReducedCopayStopDate() {
        return reducedCopayStopDate;

    }

    /**
     * Sets the stopDate date time
     * @param stopDate 
     * 
     */
    public void setReducedCopayStopDate(Date stopDate) {
        this.reducedCopayStopDate = stopDate;
    }

    /**
     * isNnf
     * @return true;
     */
    @Override
    public boolean isNdf() {

        return true;
    }

    /**
     * isStandardized
     * @return false
     */
    @Override
    public boolean isStandardized() {

        return false;
    }

    /**
     * getDomainGroup
     * @return null
     */
    @Override
    public DomainGroup getDomainGroup() {

        return null;
    }

    /**
     * isLocalOnlyData
     * @return false;
     */
    @Override
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * Returns the dates in readable formats
     * @see gov.va.med.pharmacy.peps.common.vo.ManagedDataVo#toShortString()
     * 
     * @return "Start Date: getReducedCopayStopDate.toString(), Stop Date: getReducedCopayStopDate().toString()"
     */
    @Override
    public String toShortString() {

        StringBuffer sb = new StringBuffer();

        if (getReducedCopayStartDate() != null) {
            sb.append(FieldKey.REDUCED_COPAY_START_DATE.getLocalizedAbbreviation(Locale.getDefault()) + ": "
                + getReducedCopayStartDate().toString());
        }

        if (sb.length() > 0) {
            sb.append("<br \\>");
        }

        if (getReducedCopayStopDate() != null) {
            sb.append(FieldKey.REDUCED_COPAY_STOP_DATE.getLocalizedAbbreviation(Locale.getDefault()) + ": "
                + getReducedCopayStopDate().toString());
        }

        return sb.toString();
    }

}
