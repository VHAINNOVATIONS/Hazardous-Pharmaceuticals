/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.InterfaceCounterVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.callback.InterfaceCounterDomainCapabilityCallback;


/**
 * Perform CRUD operations on interface counters.
 */
public interface InterfaceCounterDomainCapability extends InterfaceCounterDomainCapabilityCallback {

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

    /**
     * Retrieve all InterfaceCounterVo.
     * 
     * @return List<InterfaceCounterVo>
     */
    List<InterfaceCounterVo> retrieveAll();

}
