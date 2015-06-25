/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.InterfaceCounterVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.InterfaceCounterDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplInterfaceCounterDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplInterfaceCounterDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplInterfaceCounterDoKey;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.InterfaceCounterConverter;


/**
 * Perform CRUD operations on interface counters.
 */
public class InterfaceCounterDomainCapabilityImpl implements InterfaceCounterDomainCapability {

    private EplInterfaceCounterDao eplInterfaceCounterDao;
    private InterfaceCounterConverter interfaceCounterConverter;

    /**
     * Retrieve all InterfaceCounterVo.
     * 
     * @return List<InterfaceCounterVo>
     */
    public List<InterfaceCounterVo> retrieveAll() {
        List<EplInterfaceCounterDo> eplDos = eplInterfaceCounterDao.retrieveAscending(EplInterfaceCounterDo.KEY);
        List<InterfaceCounterVo> consoleVos = interfaceCounterConverter.convert(eplDos);

        return consoleVos;
    }

    /**
     * Increment the counter by one and update the DB.
     * 
     * @param interfaceName interface name
     * @param counterName counter name
     * @param user {@link UserVo} performing the action
     * @return incremented counter value
     */
    public synchronized long incrementCounter(String interfaceName, String counterName, UserVo user) {

        Map<String, Object> criteria = new HashMap<String, Object>();
        criteria.put(EplInterfaceCounterDo.KEY, new EplInterfaceCounterDoKey(interfaceName, counterName));

        List<EplInterfaceCounterDo> results = eplInterfaceCounterDao.retrieve(criteria);

        if (results.size() != 1) {
            return -1;
        }

        // Not safe!
        EplInterfaceCounterDo counter = results.get(0);
        counter.setCounterValue(counter.getCounterValue() + 1);
        eplInterfaceCounterDao.update(counter, user);

        return counter.getCounterValue();
    }

    /**
     * Retrieve counter value.
     * 
     * @param interfaceName interface name
     * @param counterName counter name
     * @return counter value
     */
    public long getCounterValue(String interfaceName, String counterName) {
        Map<String, Object> criteria = new HashMap<String, Object>();
        criteria.put(EplInterfaceCounterDo.KEY, new EplInterfaceCounterDoKey(interfaceName, counterName));

        List<EplInterfaceCounterDo> results = eplInterfaceCounterDao.retrieve(criteria);

        if (results.size() != 1) {
            return -1;
        }

        return results.get(0).getCounterValue();
    }

    /**
     * setEplInterfaceCounterDao
     * @param inEplInterfaceCounterDao eplInterfaceCounterDao property
     */
    public void setEplInterfaceCounterDao(EplInterfaceCounterDao inEplInterfaceCounterDao) {
        this.eplInterfaceCounterDao = inEplInterfaceCounterDao;
    }

    /**
     * setInterfaceCounterConverter
     * @param inInterfaceCounterConverter interfaceCounterConverter property
     */
    public void setInterfaceCounterConverter(InterfaceCounterConverter inInterfaceCounterConverter) {
        this.interfaceCounterConverter = inInterfaceCounterConverter;
    }

}
