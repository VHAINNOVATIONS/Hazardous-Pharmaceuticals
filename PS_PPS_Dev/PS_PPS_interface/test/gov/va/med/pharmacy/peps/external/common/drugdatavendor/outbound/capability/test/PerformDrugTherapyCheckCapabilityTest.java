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
 * Validate drug-therapy check returns some results
 */
public class PerformDrugTherapyCheckCapabilityTest extends InterfaceTestCase {
    private PerformDrugChecksCapability capability;
    private OrderCheckVo checkVo;

    /**
     * Instantiate and add drugs to drugsToScreen collection
     */
    protected void setUp() {

        // set the drugCheckCapability
        this.capability = getSpringBean(PerformDrugChecksCapability.class);

        Collection<DrugCheckVo> drugsToScreen = new ArrayList<DrugCheckVo>();

        DrugCheckVo aspirin = new DrugCheckVo();
        aspirin.setGcnSeqNo("12001");
        aspirin.setProspective(false);

        DrugCheckVo warfarin = new DrugCheckVo();
        warfarin.setGcnSeqNo("12000");
        warfarin.setProspective(false);

        drugsToScreen.add(aspirin);
        drugsToScreen.add(warfarin);

        this.checkVo = new OrderCheckVo();
        checkVo.setDrugsToScreen(drugsToScreen);
        checkVo.setDuplicateAllowance(false);
        checkVo.setProspectiveOnly(false);
        checkVo.setDrugTherapyCheck(true);
    }

    /**
     * Verify execute method returns some results
     */
    public void testDrugTherapyCheck() {
        OrderCheckResultsVo results = capability.performDrugChecks(checkVo);

        assertTrue("dose check returned results", results.getDrugTherapyCheckResults().getChecks().size() > 0);
    }
}
