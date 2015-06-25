/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;


import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.test.generator.ManufacturerGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.capability.RulesCapability;
import gov.va.med.pharmacy.peps.service.common.capability.impl.DefaultRulesCapabilityImpl;
import gov.va.med.pharmacy.peps.service.common.utility.ManagedItemCapabilityFactory;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;

import junit.framework.TestCase;


/**
 * Test the base class of DefaultRulesCapability. Also test that a sub class still has access to the super's Spring injected
 * classes.
 * 
 */
public class RulesCapabilityTest extends TestCase {
    private static final Logger LOG = Logger.getLogger(RulesCapabilityTest.class);
    private UserVo user;
    private RulesCapability rulesCapability;
    private ManagedItemCapabilityFactory managedItemCapabilityFactory;

    /**
     * Get instance of {@link RulesCapability}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        LOG.info("---------- " + getName() + " ----------");

        this.rulesCapability = SpringTestConfigUtility.getNationalSpringBean((RulesCapability.class));
        this.user = new UserGenerator().getLocalManagerOne();

        this.managedItemCapabilityFactory = SpringTestConfigUtility
            .getNationalSpringBean(ManagedItemCapabilityFactory.class);
    }

    /**
     * Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     */
    public void testEnforceRulesInvalid() {
        try {
            rulesCapability.enforceRules(new ProductVo(), user, false);
            fail("The enforceRules() method should have thrown a ValueObjectValidationException");
        } catch (ValueObjectValidationException e) {
            LOG.info(e.getMessage());
        } catch (ValidationException e) {
            fail("Should have thrown a ValueObjectValidationException, not just a ValidationException");
        }
    }

    /**
     * Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     */
    public void testEnforceRulesValid() {
        try {
            rulesCapability.enforceRules(new ManufacturerGenerator().getFirst(), user, true);
        } catch (ValidationException e) {
            fail("The enforceRules() method should not have thrown a ValueObjectValidationException");
        }
    }

    /**
     * Sub classes should extend {@link DefaultRulesCapabilityImpl}
     */
    public void testSubClassesExtends() {
        StringBuffer wrongTypes = new StringBuffer(0);

        for (EntityType entityType : EntityType.values()) {
            RulesCapability myRules = managedItemCapabilityFactory.getRulesCapability(entityType);

            if (!(myRules instanceof DefaultRulesCapabilityImpl)) {
                wrongTypes.append(StringUtils.unqualify(myRules.getClass().getName()));
                wrongTypes.append(", ");
            }
        }

        assertTrue("All RulesCapabilities should extend DefaultRulesCapability. The " + wrongTypes.toString() + " do not.",
            wrongTypes.length() == 0);
    }

   
}
