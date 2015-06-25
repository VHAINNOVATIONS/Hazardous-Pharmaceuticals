/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * DatabaseResetErrorMessage
 *
 */
public class DatabaseResetErrorMessage {

    private boolean error;
    private String responseMessage;

    /**
     * isError for DatabaseResetErrorMessage
     * @return error
     */
    public boolean isError() {
        return error;
    }

    /**
     * setError for DatabaseResetErrorMessage
     * @param pError pError
     */
    public void setError(boolean pError) {
        this.error = pError;
    }

    /**
     * getResponseMessage
     * @return responseMessage
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * setResponseMessage
     * @param pResponseMessage responseMessage
     */
    public void setResponseMessage(String pResponseMessage) {
        this.responseMessage = pResponseMessage;
    }
}
