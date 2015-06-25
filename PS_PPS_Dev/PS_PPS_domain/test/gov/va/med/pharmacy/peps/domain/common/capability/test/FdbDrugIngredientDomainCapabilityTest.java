/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import org.junit.Before;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FdbDrugIngredientVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugIngredientDomainCapability;



/**
 * FdbDrugIngredientDomainCapabilityTest
 *
 */
public class FdbDrugIngredientDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final int NUMBER_ROWS = 120;
    private FdbDrugIngredientDomainCapability fdbDrugIngredientDomainCapability;

    @Override
    @Before
    public void setUp() throws Exception {
        fdbDrugIngredientDomainCapability = getNationalCapability(FdbDrugIngredientDomainCapability.class);
    }

    /**
     * testDeleteAll
     */
    public void testDeleteAll() {
        try {
            fdbDrugIngredientDomainCapability.deleteAll();
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

                FdbDrugIngredientVo vo = new FdbDrugIngredientVo();
                vo.setFdbId(x.longValue());
                vo.setFdbDrugIngredient("FdbDrugIngredient" + x);
                vo.setEplId(x + PPSConstants.NINENINENINEZEROLONG); 
                fdbDrugIngredientDomainCapability.create(vo, getTestUser());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

}
