/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.stub;


import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.callback.InterfaceCounterDomainCapabilityCallback;


/**
 * Stub.
 */
public class InterfaceCounterDomainCapabilityStub implements InterfaceCounterDomainCapabilityCallback {
    private static final Map<String, Integer> COUNTERS = new HashMap<String, Integer>();

    /**
     * Increment the counter by one and update the DB.
     * 
     * @param interfaceName interface name
     * @param counterName counter name
     * @param user {@link UserVo} performing the action
     * @return incremented counter value
     */
    public synchronized long incrementCounter(String interfaceName, String counterName, UserVo user) {
        String key = interfaceName + counterName;

        if (!COUNTERS.containsKey(key)) {
            COUNTERS.put(key, 0);
        }

        COUNTERS.put(key, COUNTERS.get(key) + 1);

        return COUNTERS.get(key);
    }

    /**
     * Retrieve counter value.
     * 
     * @param interfaceName interface name
     * @param counterName counter name
     * @return counter value
     */
    public synchronized long getCounterValue(String interfaceName, String counterName) {
        String key = interfaceName + counterName;

        if (!COUNTERS.containsKey(key)) {
            COUNTERS.put(key, 0);
        }

        return COUNTERS.get(key);
    }
}
