/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;


import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ReportCaptureNdfVo;
import gov.va.med.pharmacy.peps.common.vo.ReportIngredientsVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.common.vo.ReportVuidApprovalVo;
import gov.va.med.pharmacy.peps.common.vo.ReportVuidVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ReportDomainCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.service.common.reports.ReportExportState;
import gov.va.med.pharmacy.peps.service.common.session.ReportsService;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;

import junit.framework.TestCase;





/**
 * Test the base class of ReportServiceTest. Also test that a sub class still has access to the super's Spring injected
 * classes.
 */
public class ReportServiceTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(ReportServiceTest.class);
    private static final String MSG = "List should not be null.";
    private static final String NAME = "Name: ";
    private static final String IEN = " Ien: ";
    private static final String VUID = " Vuid: ";
    private static final String INACTIVATION_DATE = " InactivationDate: ";
    private static final int FILE_SIZE = 10;

    private ReportDomainCapability reportDomainCapability;
    private DrugReferenceCapability drugReferenceCapability;
    private ReportsService reportsService;

    /**
     * Get instance of {@link RulesCapability}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        LOG.info("---------- " + getName() + " ----------");
        reportDomainCapability = SpringTestConfigUtility.getNationalSpringBean(ReportDomainCapability.class);
        drugReferenceCapability = SpringTestConfigUtility.getNationalSpringBean(DrugReferenceCapability.class);
        reportsService = SpringTestConfigUtility.getNationalSpringBean(ReportsService.class);
    }

    /**
     * testReportExportStart
     * @throws InterruptedException InterruptedException
     */
    public void testReportExportStart() throws InterruptedException {
        boolean status = false;
        int counter = 0;
        
        ReportExportState state = reportsService.startProcess(ReportType.NDC_LIST_PRINT_TEMPLATE);
        assertNotNull("Start Process Null", state);
        
        while (!status) {
            LOG.info("In loop");            
            Thread.sleep(PPSConstants.I2000);
            status = reportsService.getStatus(ReportType.NDC_LIST_PRINT_TEMPLATE).isExportComplete();
            LOG.info("Status: " + status);
            counter++;
            LOG.info(counter);
        }
    }

    /**
     * Ingredient Print Report
     */
    public void testIngredientPrintReport() {
        List<Long> list = reportDomainCapability.getIds(ReportType.LIST_INGREDIENTS);

        // do 10 at a time.
        int fileSize = FILE_SIZE;
        assertNotNull(MSG, list);

        List<Long> actionList = new ArrayList<Long>(fileSize);

        for (Long eplId : list) {
            actionList.add(eplId);

            if (actionList.size() >= fileSize) {
                printIR(actionList);
                actionList.clear();
            }
        }

        if (actionList.size() > 0) {
            printIR(actionList);
            actionList.clear();
        }

    }

    /**
     * IngredientReportPrint
     * @param actionList actionList
     */
    private void printIR(List<Long> actionList) {
        List<ReportProductVo> rtList = reportDomainCapability.getProductIngredientData(actionList);
        assertTrue("Should have gotten as many responses and epl_ids!", rtList.size() == actionList.size());

        for (ReportProductVo vo : rtList) {
            LOG.info(" For product: " + vo.getVaProductName());

            if (vo.getIngredients() == null) {
                LOG.info(" No Ingredients.");
            } else {
                for (ReportIngredientsVo ingVo : vo.getIngredients()) {
                    LOG.info("Ingredient Name: " + ingVo.getIngredient());
                    LOG.info("DosageForm: " + ingVo.getDosageForm());
                    LOG.info("Strength: " + ingVo.getStrength());
                    LOG.info("Unit: " + ingVo.getUnit());
                }
            }
        }
    }

    /**
     * Ingredient Print Report
     */
    public void testNDCPrintReport() {
        List<Long> list = reportDomainCapability.getIds(ReportType.NDC_LIST_PRINT_TEMPLATE);

        // do 10 at a time.
        int fileSize = FILE_SIZE;
        assertNotNull(MSG, list);

        List<Long> actionList = new ArrayList<Long>(fileSize);

        for (Long eplId : list) {
            actionList.add(eplId);

            if (actionList.size() >= fileSize) {
                printNDC(actionList);
                actionList.clear();
            }
        }

        if (actionList.size() > 0) {
            printNDC(actionList);
            actionList.clear();
        }

    }

    /**
     * IngredientReportPrint
     * @param actionList actionList
     */
    private void printNDC(List<Long> actionList) {
        List<ReportCaptureNdfVo> rtList = reportDomainCapability.getCaptureNdfData(actionList);
        assertTrue("Should have gotten as many responses and epl_ids", rtList.size() == actionList.size());

        for (ReportCaptureNdfVo vo : rtList) {
            LOG.info("-------------- NDC " + vo.getNdc() + "-----------------");
            LOG.info("ndc2to6: " + vo.getNdc2to6());
            LOG.info("ndc7to10: " + vo.getNdc7to10());
            LOG.info("ndc11to12: " + vo.getNdc11to12());
            LOG.info("upn: " + vo.getUpn());
            LOG.info("tradeName: " + vo.getTradeName());
            LOG.info("productNumber: " + vo.getProductNumber());
            LOG.info("dssFeederKey: " + vo.getDssFeederKey());
            LOG.info("productname: " + vo.getVaProductName());
            LOG.info("vaGenericName: " + vo.getVaGenericName());
            LOG.info("packageType: " + vo.getPackageType());
            LOG.info("vaDrugClassCode: " + vo.getVaDrugClassCode());
            LOG.info("vaDrugClassClassification: " + vo.getVaDrugClassClassification());
            LOG.info("manufacturer: " + vo.getManufacturer());
            LOG.info("RouteOfAdministration: " + vo.getRouteOfAdministration());
            LOG.info("strength: " + vo.getStrength());
            LOG.info("unit: " + vo.getUnit());
            LOG.info("dosageForm: " + vo.getDosageForm());
            LOG.info("nationalFormularyName: " + vo.getNationalFormularyName());
            LOG.info("csFederalSchedule: " + vo.getCsFederalSchedule());
            LOG.info("RxOtcIndicator: " + vo.getRxOtcIndicator());
            LOG.info("vaPrintName: " + vo.getVaPrintName());
            LOG.info("nationalFormularyIndicator: " + vo.getNationalFormularyIndicator());
            LOG.info("vaDispenseUnit: " + vo.getVaDispenseUnit());
            LOG.info("cmopId: " + vo.getCmopId());
            LOG.info("markForCmop: " + vo.getMarkForCmop());
            LOG.info("packageSize: " + vo.getPackageSize());
            LOG.info("productInactivationDate: " + vo.getProductInactivationDate());
            LOG.info("ndcInactivationDate: " + vo.getNdcInactivationDate());
        }
    }

    /**
     * Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     */
    public void testWarningLabelReport() {
        List<ReportProductVo> list = reportDomainCapability.getProductWarningLabelData();

        assertNotNull(MSG, list);

        for (ReportProductVo vo : list) {
            vo = drugReferenceCapability.retrieveWarningLabels(vo);
            LOG.info("For the product: " + vo.getVaProductName());

            if (vo.getWarningLabels() == null) {
                LOG.info(" No warning labels.");
            } else {
                LOG.info(" The warning labels count is " + vo.getWarningLabels().size());
            }
        }

    }

    /**
     * Test No active NDCS
     */
    public void testNoActiveNDC() {
        List<ReportProductVo> list = reportDomainCapability.getProductNoActiveNdcData();

        assertNotNull(MSG, list);

        for (ReportProductVo vo : list) {
            LOG.info("For product: " + vo.getVaProductName());
            LOG.info("ID: " + vo.getId());
        }

    }

    /**
     * Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     */
    public void testProposedInactivation() {
        String startDateString = "Nov 4, 2004 8:14 PM";
        String endDateString = "Jan 1, 2013 7:14 PM";
        Date startDate = null;
        Date endDate = null;

        // Get the default DateFormat to use the MEDIUM and SHORT formats.
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

        // Parse the date
        try {
            startDate = format.parse(startDateString);
            endDate = format.parse(endDateString);
        } catch (Exception e) {
            fail(e.toString());
        }

        List<ReportProductVo> list = reportDomainCapability.getProductProposedInactivationDate(startDate, endDate);

        assertNotNull(MSG, list);

        for (ReportProductVo vo : list) {
            LOG.info("With both datest: " + vo.getVaProductName());
        }

        list = reportDomainCapability.getProductProposedInactivationDate(null, endDate);

        assertNotNull(MSG, list);

        for (ReportProductVo vo : list) {
            LOG.info("With only end date: " + vo.getVaProductName());
        }

        list = reportDomainCapability.getProductProposedInactivationDate(startDate, null);

        assertNotNull(MSG, list);

        for (ReportProductVo vo : list) {
            LOG.info("With only start date: " + vo.getVaProductName());
        }

        list = reportDomainCapability.getProductProposedInactivationDate(null, null);

        assertNotNull(MSG, list);

        for (ReportProductVo vo : list) {
            LOG.info("With no dates: " + vo.getVaProductName());
        }

    }

    /**
     * Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     */
    public void testProductDrugExclusion() {

        String startDateString = "Nov 5, 2003 8:14 PM";
        String endDateString = "Jan 2, 2013 8:14 PM";
        Date startDate = null;
        Date endDate = null;

        // Get the default MEDIUM/SHORT DateFormat
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

        // Parse the date
        try {
            startDate = format.parse(startDateString);
            endDate = format.parse(endDateString);
        } catch (Exception e) {
            fail(e.toString());
        }

        List<ReportProductVo> list = reportDomainCapability.getProductExclusionData(startDate, endDate);

        assertNotNull(MSG, list);

        for (ReportProductVo vo : list) {
            LOG.info("For product with both dates: " + vo.getVaProductName());
        }

        list = reportDomainCapability.getProductExclusionData(null, endDate);

        assertNotNull(MSG, list);

        for (ReportProductVo vo : list) {
            LOG.info("For products with end date only: " + vo.getVaProductName());
        }

        list = reportDomainCapability.getProductExclusionData(startDate, null);

        assertNotNull(MSG, list);

        for (ReportProductVo vo : list) {
            LOG.info("For product with start date only: " + vo.getVaProductName());
        }

        list = reportDomainCapability.getProductExclusionData(null, null);

        assertNotNull(MSG, list);

        for (ReportProductVo vo : list) {
            LOG.info("For products dates unsepcified: " + vo.getVaProductName());
        }

    }

    /**
     * Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     */
    public void testVuidReport() {

        String startDateString = "Nov 4, 2009 8:14 PM";
        Date startDate = null;

        // Get the default MEDIUM/SHORT DateFormat for the testVuidReport.
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

        // Parse the date
        try {
            startDate = format.parse(startDateString);
        } catch (Exception e) {
            fail(e.toString());
        }

        ReportVuidApprovalVo list = reportDomainCapability.getVuidApprovalReportIngredient(startDate);
        ReportVuidApprovalVo modList = reportDomainCapability.getVuidModifiedReportIngredient(startDate);
        assertNotNull("New Ingredient List should not be null.", list.getNewIngredientList());
        assertNotNull("Modified Ingredient List should not be null.", modList.getModifiedIngredientList());

        LOG.debug("New Ingredient list.");

        for (ReportVuidVo vo : list.getNewIngredientList()) {
            String inactivationDate = "";

            if (vo.getInactivationDate() != null) {
                inactivationDate = format.format(vo.getInactivationDate());
            }

            LOG.info(NAME + vo.getName() + IEN + vo.getNdfIen() + VUID + vo.getVuid() + INACTIVATION_DATE + inactivationDate);
        }

        LOG.debug("Modified Ingredient list.");

        for (ReportVuidVo vo : modList.getModifiedIngredientList()) {
            String inactivationDate = "";

            if (vo.getInactivationDate() != null) {
                inactivationDate = format.format(vo.getInactivationDate());
            }

            LOG.info(NAME + vo.getName() + IEN + vo.getNdfIen() + VUID + vo.getVuid() + INACTIVATION_DATE + inactivationDate);
        }

        list = reportDomainCapability.getVuidApprovalReportProducts(startDate);
        modList = reportDomainCapability.getVuidModifiedReportProducts(startDate);
        assertNotNull("New Product List should not be null.", list.getNewProductList());
        assertNotNull("Modified Product List should not be null.", modList.getModifedProductList());

        LOG.debug("New Product list.");

        for (ReportVuidVo vo : list.getNewProductList()) {
            String inactivationDate = "";

            if (vo.getInactivationDate() != null) {
                inactivationDate = format.format(vo.getInactivationDate());
            }

            LOG.info(NAME + vo.getName() + IEN + vo.getNdfIen() + VUID + vo.getVuid() + INACTIVATION_DATE + inactivationDate);
        }

        LOG.debug("Modified Product list.");

        for (ReportVuidVo vo : modList.getModifedProductList()) {
            String inactivationDate = "";

            if (vo.getInactivationDate() != null) {
                inactivationDate = format.format(vo.getInactivationDate());
            }

            LOG.info(NAME + vo.getName() + IEN + vo.getNdfIen() + VUID + vo.getVuid() + INACTIVATION_DATE + inactivationDate);
        }

        list = reportDomainCapability.getVuidApprovalReportGeneric(startDate);
        modList = reportDomainCapability.getVuidModifiedReportGeneric(startDate);
        assertNotNull("New Generic List should not be null.", list.getNewGenericList());
        assertNotNull("Modified Generic List should not be null.", modList.getModifiedGenericList());

        LOG.debug("New Generic list.");

        for (ReportVuidVo vo : list.getNewGenericList()) {
            String inactivationDate = "";

            if (vo.getInactivationDate() != null) {
                inactivationDate = format.format(vo.getInactivationDate());
            }

            LOG.info(NAME + vo.getName() + IEN + vo.getNdfIen() + VUID + vo.getVuid() + INACTIVATION_DATE + inactivationDate);
        }

        LOG.debug("Modified Generic list.");

        for (ReportVuidVo vo : modList.getModifiedGenericList()) {
            String inactivationDate = "";

            if (vo.getInactivationDate() != null) {
                inactivationDate = format.format(vo.getInactivationDate());
            }

            LOG.info(NAME + vo.getName() + IEN + vo.getNdfIen() + VUID + vo.getVuid() + INACTIVATION_DATE + inactivationDate);
        }

        list = reportDomainCapability.getVuidApprovalReportDrugClasses(startDate);
        assertNotNull("Drug Class List should not be null.", list.getNewDrugClassList());

        LOG.debug("New Drug Class list.");

        for (ReportVuidVo vo : list.getNewDrugClassList()) {
            String inactivationDate = "";

            if (vo.getInactivationDate() != null) {
                inactivationDate = format.format(vo.getInactivationDate());
            }

            LOG.info(NAME + vo.getName() + IEN + vo.getNdfIen() + VUID + vo.getVuid() + INACTIVATION_DATE + inactivationDate);
        }
    }

}
