/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.callback;


import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Manage interface counters.
 */
public interface InterfaceCounterDomainCapabilityCallback {

    /**
     * INTERFACE_VISTA_SYNCHRONIZATION
     */
    String INTERFACE_VISTA_SYNCHRONIZATION = "VISTA_SYNCHRONIZATION";

    /**
     * COUNTER_MESSAGE_ID
     */
    String COUNTER_MESSAGE_ID = "MESSAGE_ID";
    
    /**
     * COUNTER_MESSAGE_PROCESSED
     */
    String COUNTER_MESSAGE_PROCESSED = "MESSAGE_PROCESSED";
    
    /**
     * COUNTER_MESSAGE_SENT
     */
    String COUNTER_MESSAGE_SENT = "MESSAGE_SENT";

    /**
     * Increment the counter by one and update the DB.
     * 
     * @param interfaceName interface name
     * @param counterName counter name
     * @param user {@link UserVo} performing the action
     * @return incremented counter value
     */
    long incrementCounter(String interfaceName, String counterName, UserVo user);

    /**
     * Retrieve counter value.
     * 
     * @param interfaceName interface name
     * @param counterName counter name
     * @return counter value
     */
    long getCounterValue(String interfaceName, String counterName);

}
