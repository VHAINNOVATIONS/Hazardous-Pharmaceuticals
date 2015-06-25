/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import gov.va.med.crypto.VistaKernelHash;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * object that is created when a call comes into the server
 */
public class Request {
    private String vlv;
    private String rpc;
    private String rcx;
    private String rto;
    private String sec;
    private String secParam;
    private String div;
    private String ras;
    private List parameters;

    /**
     * default constructor
     */
    public Request() {
        this.parameters = new ArrayList(PPSConstants.I3);
    }

    /**
     * sets the value of the vistALink version number
     * @param vlv value of vistALink version
     */
    public void setVlv(String vlv) {
        this.vlv = vlv;
    }

    /**
     * gets the value of the vistALink version number
     * @return value of vistALink version
     */
    public String getVlv() {
        return vlv;
    }

    /**
     * sets the name of the remote procedure call
     * @param rpc value to set remote procedure call to
     */
    public void setRpc(String rpc) {
        this.rpc = rpc;
    }

    /**
     * gets the name of the remote procedure call
     * @return value of the remote procedure call
     */
    public String getRpc() {
        return rpc;
    }

    /**
     * setter for rcx
     * @param rcx value to set rcx to
     */
    public void setRcx(String rcx) {
        this.rcx = rcx;
    }

    /**
     * getter for rcx
     * @return value of rcx
     */
    public String getRcx() {
        return rcx;
    }

    /**
     * sets the desired value for the remote time out
     * @param rto value to set the timeout to in ms?
     */
    public void setRto(String rto) {
        this.rto = rto;
    }

    /**
     * gets the value of the remote time out in ms?
     * @return value of rto
     */
    public String getRto() {
        return rto;
    }

    /**
     * sets the security method for this call
     * @param sec value to set the security method to
     */
    public void setSec(String sec) {
        this.sec = sec;
    }

    /**
     * gets the security method for this call
     * @return value of security method
     */
    public String getSec() {
        return sec;
    }

    /**
     * sets any securtiy parameters passed along with the call
     * @param secParam value to set the security parameters to
     */
    public void setSecParam(String secParam) {
        this.secParam = secParam;
    }

    /**
     * gets the value of the security parameter for this call
     * @return value of security parameters
     */
    public String getSecParam() {
        return secParam;
    }

    /**
     * sets the division value for this call
     * @param div value of the division
     */
    public void setDiv(String div) {
        this.div = div;
    }

    /**
     * gets the division for this call
     * @return value of division
     */
    public String getDiv() {
        return div;
    }

    /**
     * sets wheather the sender is authenticated or notauthenticated
     * @param ras either authenticated or notauthenticated
     */
    public void setRas(String ras) {
        this.ras = ras;
    }

    /**
     * gets wheather the sender was authenticated or notauthenticated
     * @return either authenticated or notauthenticated string
     */
    public String getRas() {
        return ras;
    }

    /**
     * sets any parameters that were sent along with this call
     * @param parameters List of values of the parameters sent with the call
     */
    public void setParameters(List parameters) {
        this.parameters = parameters;
    }

    /**
     * gets the parameters that came in with this call
     * @return List containing the parameters sent with the call
     */
    public List getParameters() {
        return parameters;
    }

    /**
     * checks to see if the requestor is authenticated
     * @return true if authenticated and false otherwise
     */
    public boolean isAuthenticated() {
        return "authenticated".equals(ras);
    }

    /**
     * Builds a string representation of the Request object
     * @return string representing object
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }


    /**
     * Class used when an incoming parameter in a call is a string
     */
    public static class StringParameter {
        private String value;

        /**
         * Creates the object and sets the internal value to the value of the desired parameter
         * @param value value to set the parameter to
         */
        public StringParameter(String value) {
            this.value = value;
        }

        /**
         * gets the value of the parameter that was set in this object
         * @return string representing value
         */
        public String getValue() {
            return value;
        }

        /**
         * This method is used to obtain the decrypted version of the string parameter for this call
         * @return the decrypted string parameter
         */
        public String getDecryptedValue() {
            return VistaKernelHash.decrypt(getValue());
        }

        /**
         * toString
         * @return string representing object
         * 
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }


    /**
     * Class used when an array parameter is passed in a call
     */
    public static class ArrayParameter {
        private List value;

        /**
         * Creates the arrayParameter object with its value set to a list of the parameters that were passed in through 
         * the call
         * @param value a list of values for the array
         */
        public ArrayParameter(List value) {
            this.value = value;
        }

        /**
         * Gets the value of the array that was passed in as this call.
         * @return List representing the values
         */
        public List getValue() {
            return value;
        }

        /**
         * toString
         * @return string representing the object
         * 
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }

}
