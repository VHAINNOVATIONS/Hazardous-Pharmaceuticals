/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability;


/**
 * All VistALink commands must implement a setRequest and getReponse() methods.
 */
public interface SendTestMessageCapability {

    /**
     * Send VistALink test message
     * 
     * @param testMessage String message
     * @return String returned by VistALink
     */
    String sendTestMessage(String testMessage);  
}
