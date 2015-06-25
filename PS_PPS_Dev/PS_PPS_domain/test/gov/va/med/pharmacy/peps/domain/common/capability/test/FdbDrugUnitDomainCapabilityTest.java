/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.Date;

import org.junit.Before;

import gov.va.med.pharmacy.peps.common.vo.FdbDrugUnitVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugUnitDomainCapability;



/**
 * FdbDrugUnitDomainCapabilityTest
 *
 */
public class FdbDrugUnitDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final int NUMBER_ROWS = 120;
    private FdbDrugUnitDomainCapability fdbDrugUnitDomainCapability;

    @Override
    @Before
    public void setUp() throws Exception {
        fdbDrugUnitDomainCapability = getNationalCapability(FdbDrugUnitDomainCapability.class);
    }

    /**
     * testDeleteAll
     */
    public void testDeleteAll() {
        try {
            fdbDrugUnitDomainCapability.deleteAll();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * testCreate
     */
    public void testCreate() {
        try {
            for (Integer x = NUMBER_ROWS; x > 0; x--) {

                FdbDrugUnitVo vo = new FdbDrugUnitVo();
                vo.setFdbId(x.longValue());
                vo.setFdbDrugStrengthunits("FdbDrugUnit" + x);
                String eplId = "999" + x;
                vo.setEplId(Long.valueOf(eplId)); 
                
                vo.setCreatedBy(getTestUser().getUsername());
                vo.setCreatedDate(new Date());
                
                fdbDrugUnitDomainCapability.create(vo, getTestUser());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

}
