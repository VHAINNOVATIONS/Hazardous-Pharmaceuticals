/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability;


 

/**
 * Process VistA Migration NDC UPN Synchronization status messages.
 */
public interface MigrationNDCUPNSynchFileStatusCapability {

    /**
     * Process VistA status message for MigrationNdcUpnSynchFileStatusCapability.
     * 
     * @param xml request XML from VistA
     * @return response XML
     */
    String processRequest(String xml);

    /**
     * Message sent count for MigrationNdcUpnSynchFileStatusCapability.
     * 
     * @return count
     */
    long getVistaMessageSentCount();

    /**
     * Message processed count for MigrationNdcUpnSynchFileStatusCapability.
     * 
     * @return count
     */
    long getVistaMessageProcessedCount();
}
