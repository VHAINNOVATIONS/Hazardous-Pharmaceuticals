/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability;




/**
 * Process VistA Synchronization status messages.
 */
public interface SynchronizationStatusCapability {

    /**
     * Process VistA status message..
     * 
     * @param xml request XML from VistA
     * @return response XML
     */
    String processRequest(String xml);

    /**
     * Message sent count.
     * 
     * @return count
     */
    long getVistaMessageSentCount();

    /**
     * Message processed count.
     * 
     * @return count
     */
    long getVistaMessageProcessedCount();
}
