/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Pharmacy System Warning Label Source 
 */
public enum PharmacySystemWarningLabelSource {

    /** COTS */
    COTS,

    /** PPS */
    PEPS;
     
    /**
     * If true, VistA translates this to 'N'
     * 
     * @return boolean true if this PharmacySystemWarningLabelSource is COTS
     */
    public boolean isCots() {
        return COTS.equals(this);
    }

    /**
     * isPps
     * 
     * @return boolean true if this PharmacySystemWarningLabelSource is PEPS
     */
    public boolean isPeps() {
        return PEPS.equals(this);
    }
}
