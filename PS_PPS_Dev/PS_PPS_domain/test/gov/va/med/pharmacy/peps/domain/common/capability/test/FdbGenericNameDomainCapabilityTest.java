/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.Date;

import org.junit.Before;

import gov.va.med.pharmacy.peps.common.vo.FdbGenericNameVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbGenericNameDomainCapability;


/**
 * FdbGenericNameDomainCapabilityTest
 *
 */
public class FdbGenericNameDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final int NUMBER_ROWS = 120;
    private FdbGenericNameDomainCapability fdbGenericNameDomainCapability;

    @Override
    @Before
    public void setUp() throws Exception {
        fdbGenericNameDomainCapability = getNationalCapability(FdbGenericNameDomainCapability.class);
    }

    /**
     * testDeleteAll
     */
    public void testDeleteAll() {
        try {
            fdbGenericNameDomainCapability.deleteAll();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * testCreate in FdbGenericnameDomainCapabilityTest
     */
    public void testCreate() {

        try {   
            for (Integer x = NUMBER_ROWS; x > 0; x--) {

                FdbGenericNameVo vo = new FdbGenericNameVo();
                vo.setFdbId(x.longValue());
                vo.setFdbGenericDrugname("FdbGenericName" + x);
                String eplId = "999" + x;
                vo.setEplId(Long.valueOf(eplId)); 
                
                vo.setCreatedBy(getTestUser().getUsername());
                vo.setCreatedDate(new Date());
                fdbGenericNameDomainCapability.create(vo, getTestUser());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

}
