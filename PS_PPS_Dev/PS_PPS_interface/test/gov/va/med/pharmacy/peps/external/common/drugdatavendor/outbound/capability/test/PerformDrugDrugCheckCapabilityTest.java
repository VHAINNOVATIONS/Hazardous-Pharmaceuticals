/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.test;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.PerformDrugChecksCapability;
import gov.va.med.pharmacy.peps.external.common.test.InterfaceTestCase;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.OrderCheckResultsVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.OrderCheckVo;


/**
 * Verify the drug-drug check returns values
 */
public class PerformDrugDrugCheckCapabilityTest extends InterfaceTestCase {
    private PerformDrugChecksCapability capability;
    private OrderCheckVo checkVo;

    /**
     * Instantiate and add drugs to drugsToScreen collection
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {

        // get the spring injected drug check capability
        this.capability = getSpringBean(PerformDrugChecksCapability.class);

        Collection<DrugCheckVo> drugsToScreen = new ArrayList<DrugCheckVo>();

        DrugCheckVo aspirin = new DrugCheckVo();
        aspirin.setGcnSeqNo("12000"); // GCN sequence number
        aspirin.setProspective(false);

        DrugCheckVo warfarin = new DrugCheckVo();
        warfarin.setGcnSeqNo("6559"); // GCN sequence number
        warfarin.setProspective(false);

        drugsToScreen.add(aspirin);
        drugsToScreen.add(warfarin);

        this.checkVo = new OrderCheckVo();
        checkVo.setDrugsToScreen(drugsToScreen);
        checkVo.setProspectiveOnly(false);
        checkVo.setDrugDrugCheck(true);
    }

    /**
     * Verify check method returns some results
     */
    public void testDrugDrugCheck() {
        OrderCheckResultsVo results = capability.performDrugChecks(checkVo);

        assertTrue("dose check returned results", results.getDrugDrugCheckResults().getChecks().size() > 0);
    }
}
