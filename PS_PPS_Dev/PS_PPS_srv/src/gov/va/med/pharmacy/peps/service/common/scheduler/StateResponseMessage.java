/**
 * Source file created in 2007 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.scheduler;


/**
 * StateResponseMessage
 */
public class StateResponseMessage {

    private boolean error;
    private String responseMessage;
    
    /**
     * checks for error
     * @return boolean
     */
    public boolean isError() {
        
        return error;
    }
    
    /**
     * sets Error
     *
     * @param pError pError
     */
    public void setError(boolean pError) {
        
        this.error = pError;
    }
    
    /**
     * gets ResponseMessage
     * @return response message
     */
    public String getResponseMessage() {
        
        return responseMessage;
    }
    
    /**
     * sets ResponseMessage
     * @param pResponseMessage pResponseMessage
     */
    public void setResponseMessage(String pResponseMessage) {
        
        this.responseMessage = pResponseMessage;
    }

}
