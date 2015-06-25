/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.test;


import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.VistaAuthoritativeDomainCapability;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.VistaDomainName;

import junit.framework.TestCase;


/**
 * Test pulling domains from VistA.
 */
public class VistaAuthoritativeDomainTest extends TestCase {
    private VistaAuthoritativeDomainCapability capability;

    /**
     * setUp
     * @throws Exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
            new String[] {"classpath*:xml/spring/test/*Context.xml", "classpath*:xml/local/spring/test/*Context.xml",
                          "classpath*:xml/local/spring/test/CommonContext-Local-1.xml",
                          "classpath*:xml/spring/test/Callback.xml"});
        this.capability = (VistaAuthoritativeDomainCapability) context.getBean("vistaAuthoritativeDomainCapability");
    }

    /**
     * Test VistA domains retrieve.
     */
    public void testVistADomains() {
        VistaDomainName[] names = new VistaDomainName[] {VistaDomainName.OUTPATIENT_SITES,
                                                         VistaDomainName.HOSPITAL_LOCATIONS, VistaDomainName.WARDS,
                                                         VistaDomainName.LABS, VistaDomainName.VITALS,
                                                         VistaDomainName.IFCAP_ITEM_NUMBERS, VistaDomainName.SPECIMEN_TYPES,
                                                         VistaDomainName.VIST_A_VERSION};

        Map<VistaDomainName, List<?>> domains = capability.retrieveVistADomains(names, null);

        assertTrue("domain size is incorrect", domains.size() == names.length);
    }

}
