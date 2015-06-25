/**
 * Source file created in 2011 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.utility;


/**
 * VistALinkResponseInfo's brief summary
 * 
 * Class used to send information back from a sendToVistALink routine.
 *
 */
public class VistALinkResponseInfo {
    
    /**
     * This is the error string sent back from VistALink
     * 
     */
    private String errorResponseString = "";
    
    /**
     * This is the returned IEN of the sent item
     */
    private String ien = "";

    /**
     * Return the errorResponseString
     * @return String
     */
    public String getErrorResponseString() {
        return errorResponseString;
    }

    /**
     * Return the ien
     * @return String
     */
    public String getIen() {
        return ien;
    }

    /**
     * set the errorResponse String
     * 
     * @param es String
     */
    public void setErrorResponseString(String es) {
        this.errorResponseString = es;
    }

    /**
     * set the ien String
     * 
     * @param is String
     * 
     */
    public void setIen(String is) {
        this.ien = is;
    }

    /**
     * Set boolean if error message has content
     * @return boolean
     */
    public boolean isError() {
        if ((errorResponseString != null) && (errorResponseString.length() > 0)) {
            return false;
        } 
        
        return false;
    }

    /**
     * Set boolean if IEN has content
     * @return boolean
     */
    public boolean isIen() {
        if ((ien != null) && (ien.length() > 0)) {
            return true;
        } 
        
        return false;
    }

}
