/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.test;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.service.common.capability.RulesCapability;
import gov.va.med.pharmacy.peps.service.common.capability.impl.DefaultRulesCapabilityImpl;
import gov.va.med.pharmacy.peps.service.common.utility.ManagedItemCapabilityFactory;

import junit.framework.TestCase;


/**
 * Test to be sure all EntityTypes have domain capabilities (at least configured in Spring).
 */
public class ManagedItemCapabilityFactoryTest extends TestCase {
    private ManagedItemCapabilityFactory managedItemCapabilityFactory;

    /**
     * setUp
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        this.managedItemCapabilityFactory = SpringTestConfigUtility
            .getNationalSpringBean(ManagedItemCapabilityFactory.class);

    }

    /**
     * Verify that DomainCapabilities exist for each of the EntityTypes
     */
    public void testDomainCapabilityMapping() {
        List<EntityType> missing = new ArrayList<EntityType>(0);

        for (EntityType entityType : EntityType.values()) {
            
            // User, Category, Reduced Copay and Status history do not use the domain capability but use the EntityType
            // so they can use the sequence number generator. 
            if (!(entityType.equals(EntityType.USER) || entityType.equals(EntityType.VUID_STATUS_HISTORY) 
                || entityType.equals(EntityType.CATEGORY) || entityType.equals(EntityType.REDUCED_COPAY))) {
                if (managedItemCapabilityFactory.getDomainCapability(entityType) == null) {
                    missing.add(entityType);
                }
            }
        }

        StringBuffer missingTypes = new StringBuffer();

        for (EntityType entityType : missing) {
            missingTypes.append(entityType);
            missingTypes.append(", ");
        }

        assertTrue("All EntityTypes should have domain capabilities. The " + missingTypes.toString()
            + " EntityTypes do not. ", missing.isEmpty());
    }

    /**
     * Verify the RulesCapabilities exit for each of the EntityTypes (at the very least the
     * {@link DefaultRulesCapabilityImpl} should be returned.
     */
    public void testRulesCapabilityMapping() {
        List<EntityType> missing = new ArrayList<EntityType>(0);

        for (EntityType entityType : EntityType.values()) {
            RulesCapability rulesCapability = managedItemCapabilityFactory.getRulesCapability(entityType);

            if (rulesCapability == null) {
                missing.add(entityType);
            }
        }

        StringBuffer missingTypes = new StringBuffer();

        for (EntityType entityType : missing) {
            missingTypes.append(entityType);
            missingTypes.append(", ");
        }

        assertTrue(
            "All EntityTypes should have rules capabilities. The " + missingTypes.toString() + " EntityTypes do not.",
            missing.isEmpty());
    }
}
