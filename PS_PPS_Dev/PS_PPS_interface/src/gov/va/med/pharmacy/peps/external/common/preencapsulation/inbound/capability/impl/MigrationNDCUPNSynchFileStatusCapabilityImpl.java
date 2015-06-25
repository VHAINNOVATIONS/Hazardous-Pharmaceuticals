/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.impl;


import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.callback.InterfaceCounterDomainCapabilityCallback;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.SynchronizationStatusCapability;

 
/**
 * Process VistA Synchronization status messages.
 */
public class MigrationNDCUPNSynchFileStatusCapabilityImpl implements SynchronizationStatusCapability {
    private InterfaceCounterDomainCapabilityCallback interfaceCounter;
    private EnvironmentUtility environmentUtility;

    /**
     * MigrationNDCUPNSynchFileStatusCapabilityImpl
     * Process VistA status message for NDC/UPN synch.
     * 
     * @param xml request XML from VistA
     * @return response count 
     */
    public String processRequest(String xml) {
        
        // processRequest for NDCUPNSynchFileStatusCapabilityImpl
        return Long.toString(interfaceCounter.incrementCounter(
            InterfaceCounterDomainCapabilityCallback.INTERFACE_VISTA_SYNCHRONIZATION,
            InterfaceCounterDomainCapabilityCallback.COUNTER_MESSAGE_PROCESSED, UserVo.getSystemUser(environmentUtility)));
    }

    /**
     * Migration NDCUPNSynchFileStatusCapabilityImpl
     * Message sent count.
     * 
     * @return count
     */
    public long getVistaMessageSentCount() {
        return interfaceCounter.getCounterValue(InterfaceCounterDomainCapabilityCallback.INTERFACE_VISTA_SYNCHRONIZATION,
            InterfaceCounterDomainCapabilityCallback.COUNTER_MESSAGE_SENT);
    }

    /**
     * MigrationNDCUPNSynchFileStatusCapabilityImpl
     * Message processed count.
     * 
     * @return count
     */
    public long getVistaMessageProcessedCount() {
        return interfaceCounter.getCounterValue(InterfaceCounterDomainCapabilityCallback.INTERFACE_VISTA_SYNCHRONIZATION,
            InterfaceCounterDomainCapabilityCallback.COUNTER_MESSAGE_PROCESSED);
    }

    /**
     * MigrationNDCUPNSynchFileStatusCapabilityImpl
     * Callback.
     * 
     * @param icounter counter
     */
    public void setInterfaceCounterDomainCapability(InterfaceCounterDomainCapabilityCallback icounter) {
        this.interfaceCounter = icounter;
    }

    /**
     * MigrationNDCUPNSynchFileStatusCapabilityImpl
     * setEnvironmentUtility
     * @param environmentUtility environmentUtility property
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

}
