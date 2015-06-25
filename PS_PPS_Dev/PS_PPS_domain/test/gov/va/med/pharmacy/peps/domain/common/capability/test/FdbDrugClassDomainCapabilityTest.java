/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.Date;

import org.junit.Before;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FdbDrugClassVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugClassDomainCapability;


/**
 * FdbDrugClassDomainCapabilityTest
 *
 */
public class FdbDrugClassDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final int NUMBER_ROWS = 120;
    private FdbDrugClassDomainCapability fdbDrugClassDomainCapability;

    @Override
    @Before
    public void setUp() throws Exception {
        fdbDrugClassDomainCapability = getNationalCapability(FdbDrugClassDomainCapability.class);
    }

    /**
     * testDeleteAll
     */
    public void testDeleteAll() {
        
        try {
            fdbDrugClassDomainCapability.deleteAll();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * testCreate in FdbDrugClassDomainCapabilityTest
     */
    public void testCreate() {

        try {
            for (Integer x = NUMBER_ROWS; x > 0; x--) {

                FdbDrugClassVo vo = new FdbDrugClassVo();
                vo.setFdbId(x.longValue()); //id
                vo.setFdbDrugClass("FdbDrugClass" + x);
                vo.setEplId(x + PPSConstants.NINENINENINEZEROLONG); // EPL_DRUG_CLASS_FK
              
                vo.setCreatedBy(getTestUser().getUsername());
                vo.setCreatedDate(new Date());
    
                fdbDrugClassDomainCapability.create(vo, getTestUser());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
        

    }

}
