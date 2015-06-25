/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.test;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.PerformDrugChecksCapability;
import gov.va.med.pharmacy.peps.external.common.test.InterfaceTestCase;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DoseCheckType;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.OrderCheckResultsVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.OrderCheckVo;


/**
 * Validate drug-dose check returns some results
 */
public class PerformDrugDoseCheckCapabilityTest extends InterfaceTestCase {
    private static final String UNIT_MG = "MG";
    private static final String RATE_DAY = "DAY";
    private static final String ROUTE_ORAL = "ORAL";
    private static final String MAINTENANCE_DOSE = "MAINTENANCE";
    private PerformDrugChecksCapability capability;
    private OrderCheckVo checkVo;

    /**
     * Instantiate and add drugs to drugsToScreen collection
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {

        this.capability = getSpringBean(PerformDrugChecksCapability.class);

        Collection<DrugCheckVo> drugsToScreen = new ArrayList<DrugCheckVo>();

        DrugCheckVo aspirin = new DrugCheckVo();
        aspirin.setGcnSeqNo("12000"); // GCN sequence number
        aspirin.setDoseRate(RATE_DAY);
        aspirin.setDoseUnit(UNIT_MG);
        aspirin.setDuration(PPSConstants.I100);
        aspirin.setDurationRate(RATE_DAY);
        aspirin.setFrequency("5");
        aspirin.setSingleDoseAmount(PPSConstants.F0POINT0001);
        aspirin.setDoseType(MAINTENANCE_DOSE);
        aspirin.setDoseRoute(ROUTE_ORAL);
        aspirin.setProspective(true);
        aspirin.setDoseCheck(true);
        aspirin.setDoseCheckType(DoseCheckType.SPECIFIC);

        DrugCheckVo warfarin = new DrugCheckVo();
        warfarin.setGcnSeqNo("6559"); // GCN sequence number
        warfarin.setDoseRate(RATE_DAY);
        warfarin.setDoseUnit(UNIT_MG);
        warfarin.setDuration(PPSConstants.I100);
        warfarin.setDurationRate(RATE_DAY);
        warfarin.setFrequency("5");
        warfarin.setSingleDoseAmount(PPSConstants.F0POINT0001);
        warfarin.setDoseType(MAINTENANCE_DOSE);
        warfarin.setDoseRoute(ROUTE_ORAL);
        warfarin.setProspective(true);
        warfarin.setDoseCheck(true);
        warfarin.setDoseCheckType(DoseCheckType.SPECIFIC);

        drugsToScreen.add(aspirin);
        drugsToScreen.add(warfarin);

        this.checkVo = new OrderCheckVo();
        checkVo.setDrugsToScreen(drugsToScreen);
        checkVo.setProspectiveOnly(false);
        checkVo.setAgeInDays(1);
        checkVo.setBodySurfaceAreaInSqM(1);
        checkVo.setWeightInKg(1);
        checkVo.setDrugDoseCheck(true);
    }

    /**
     * Verify check method returns some results
     */
    public void testDrugDoseCheck() {
        OrderCheckResultsVo results = capability.performDrugChecks(checkVo);
        assertTrue("Dose check should return at least one result", results.getDrugDoseCheckResults().getChecks().size() > 0);
    }
}
