/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.common.vo.InterfaceCounterVo;
import gov.va.med.pharmacy.peps.domain.common.capability.InterfaceCounterDomainCapability;


/**
 * InterfaceCounterDomainCapabilityTest.
 */
public class InterfaceCounterDomainCapabilityTest extends DomainCapabilityTestCase {
    private InterfaceCounterDomainCapability interfaceCounterDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        this.interfaceCounterDomainCapability = getNationalCapability(InterfaceCounterDomainCapability.class);
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testUpdateCounters() throws PharmacyException {
        List<InterfaceCounterVo> counters = interfaceCounterDomainCapability.retrieveAll();

        for (InterfaceCounterVo counter : counters) {
            long newValue = counter.getCounterValue() + 1;

            assertEquals("increment failed", newValue, interfaceCounterDomainCapability.incrementCounter(counter
                .getInterfaceName(), counter.getCounterName(), getTestUser()));
            assertEquals("retrieve failed", newValue, interfaceCounterDomainCapability.getCounterValue(counter
                .getInterfaceName(), counter.getCounterName()));
        }
    }
}
