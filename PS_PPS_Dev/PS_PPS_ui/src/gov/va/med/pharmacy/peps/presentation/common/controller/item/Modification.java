/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


/**
 * Modification's brief summary
 * 
 * Details of Modification's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class Modification {

    private Boolean acceptChange;
    private String reason;

    /**
     * Constructor
     *
     */
    public Modification() {
        super();

        acceptChange = true;
    }

    /**
     * getAcceptChange
     *
     * @return Boolean
     */
    public Boolean getAcceptChange() {
        return acceptChange;
    }

    /**
     * setAcceptChange
     *
     * @param acceptChange Boolean
     */
    public void setAcceptChange(Boolean acceptChange) {
        this.acceptChange = acceptChange;
    }

    /**
     * getReason
     *
     * @return String
     */
    public String getReason() {
        return reason;
    }

    /**
     * setReason
     *
     * @param reason String
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

}
