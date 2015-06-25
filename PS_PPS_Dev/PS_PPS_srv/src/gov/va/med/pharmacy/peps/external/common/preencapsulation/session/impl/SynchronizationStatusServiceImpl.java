/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session.impl;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.SynchronizationStatusCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.SynchronizationStatusService;


/**
 * Process VistA Synchronization status messages.
 */
public class SynchronizationStatusServiceImpl implements SynchronizationStatusService {
    private SynchronizationStatusCapability synchronizationStatusCapability;

    /**
     * Process status update.
     * 
     * @param status VistA Status
     * @return Response
     */
    public String processStatus(String status) {
        return synchronizationStatusCapability.processRequest(status);
    }

    /**
     * Message sent count.
     * 
     * @return count
     */
    public long getVistaMessageSentCount() {
        return synchronizationStatusCapability.getVistaMessageSentCount();
    }

    /**
     * Message processed count.
     * 
     * @return count
     */
    public long getVistaMessageProcessedCount() {
        return synchronizationStatusCapability.getVistaMessageProcessedCount();
    }

    /**
     * Set capability.
     * 
     * @param synchronizationStatusCapability capability
     */
    public void setSynchronizationStatusCapability(SynchronizationStatusCapability synchronizationStatusCapability) {
        this.synchronizationStatusCapability = synchronizationStatusCapability;
    }
}
