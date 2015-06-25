/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * PreferenceGroupVo's brief summary
 * 
 * Details of PreferenceGroupVo's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class PreferenceGroupVo extends ValueObject {

    private static final long serialVersionUID = 1L;
    
    private PreferenceType preferenceType;
    private boolean approved;
    private boolean rejected;
    private boolean modified;
    private boolean inactivated;

    /**
     * c'tor
     * 
     */
    public PreferenceGroupVo() {

    }

    /**
     * getPreferenceType
     * 
     * @return preferenceName property
     */
    public PreferenceType getPreferenceType() {
        return preferenceType;
    }

    /**
     * setPreferenceType
     * 
     * @param preferenceName preferenceName property
     */
    public void setPreferenceType(PreferenceType preferenceName) {
        this.preferenceType = preferenceName;
    }

    /**
     * isApproved
     * 
     * @return approved property
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * setApproved
     * 
     * @param approved approved property
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     * isRejected
     * 
     * @return rejected property
     */
    public boolean isRejected() {
        return rejected;
    }

    /**
     * setRejected
     * 
     * @param rejected rejected property
     */
    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    /**
     * isModified
     * 
     * @return modified property
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * setModified
     * 
     * @param modified modified property
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    /**
     * isInactivated
     * 
     * @return inactivated property
     */
    public boolean isInactivated() {
        return inactivated;
    }

    /**
     * setInactivated
     * 
     * @param inactivated inactivated property
     */
    public void setInactivated(boolean inactivated) {
        this.inactivated = inactivated;
    }

}
