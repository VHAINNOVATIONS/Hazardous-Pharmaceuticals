/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import org.junit.Before;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FdbDosageFormVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDosageFormDomainCapability;



/**
 * FdbDosageFormDomainCapabilityTest
 *
 */
public class FdbDosageFormDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final int NUMBER_ROWS = 120;
    private FdbDosageFormDomainCapability fdbDosageFormDomainCapability;

    @Override
    @Before
    public void setUp() throws Exception {
        fdbDosageFormDomainCapability = getNationalCapability(FdbDosageFormDomainCapability.class);
    }

    /**
     * testDeleteAll
     */
    public void testDeleteAll() {
        
        try {
            fdbDosageFormDomainCapability.deleteAll();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * testCreate
     */
    public void testCreate() {
        try {
            for (Integer x = NUMBER_ROWS; x > 0; x--) {

                FdbDosageFormVo vo = new FdbDosageFormVo();
                vo.setFdbId(x.longValue());
                vo.setDrugDosageFormDesc("FdbDosageForm" + x);
                vo.setEplId(x + PPSConstants.NINENINENINEZEROLONG); 
                fdbDosageFormDomainCapability.create(vo, getTestUser());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
