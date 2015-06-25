/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * This class hold CSV response status & messages 
 */
public class CSVResponseMessage {
    
    private boolean error;
    private String responseMessage;

    /**
     * isError
     * @return error
     */
    public boolean isError() {
        return error;
    }
    
    /**
     * setError
     * @param pError pError
     */
    public void setError(boolean pError) {
        this.error = pError;
    }
    
    /**
     * getResponseMessage
     * @return responseMessage responseMessage
     */
    public String getResponseMessage() {
        return responseMessage;
    }
    
    /**
     * setResponseMessage
     * @param pResponseMessage pResponseMessage
     */
    public void setResponseMessage(String pResponseMessage) {
        this.responseMessage = pResponseMessage;
    }
}
