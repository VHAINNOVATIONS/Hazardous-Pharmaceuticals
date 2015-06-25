/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session;


/**
 * Process VistA Synchronization status messages.
 */
public interface SynchronizationStatusService {

    /**
     * Process status update.
     * 
     * @param status VistA Status
     * @return Response
     */
    String processStatus(String status);

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
