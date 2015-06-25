/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.reports.test;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.ReportCaptureNdfVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.domain.common.capability.ReportDomainCapability;
import gov.va.med.pharmacy.peps.service.common.session.DomainService;
import gov.va.med.pharmacy.peps.service.common.session.ReportsService;
import gov.va.med.pharmacy.peps.service.common.utility.ReportDrugClassCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ReportExclusionsCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ReportListIngredientCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ReportNdfCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ReportNoActiveNdcCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ReportProposedInactivationCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ReportVuidCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ReportWarningLableCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;

import junit.framework.TestCase;


/**
 * Test Report.CSV output files
 *
 */
public class ReportCsvTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(ReportCsvTest.class);
    private static final String MSG = "List should not be null.";
    private static final int FILE_SIZE = 10;
    private static final Boolean DESCRIPTION = true;
    private static final Date START_DATE = null;
    private static final Date STOP_DATE = null;
    private static final String FR_NULL = "field reportsService is null.";
    private static final String FD_NULL = "field reportDomainCapability is null.";


    private ReportsService reportsService;
    private DomainService domainService;
    private ReportDomainCapability reportDomainCapability;

    /** Overide for testcase
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        LOG.info("---------- " + getName() + " ----------");
        reportsService = SpringTestConfigUtility.getNationalSpringBean(ReportsService.class);
        reportDomainCapability = SpringTestConfigUtility.getNationalSpringBean(ReportDomainCapability.class);
        domainService = SpringTestConfigUtility.getNationalSpringBean(DomainService.class);
    }

    /**
     * Test NDC Report
     */
    public void testNDCPrintReport() {

        assertNotNull(FR_NULL, reportsService);
        assertNotNull(FD_NULL, reportsService.getReportDomainCapability());

        ReportNdfCsvFile reportNdfCsvFile = new ReportNdfCsvFile();

        try {
            reportNdfCsvFile.createFile();
        } catch (Exception e) {
            LOG.error("Cound not open NDC file for export:  " + e);
        }

        List<Long> list = new ArrayList<Long>();
        list = reportDomainCapability.getIds(ReportType.NDC_LIST_PRINT_TEMPLATE);

        // do 10 at a time.
        assertNotNull(MSG, list);

        List<Long> actionList = new ArrayList<Long>(FILE_SIZE);
        reportDomainCapability.getCaptureNdfData(actionList);

        for (Long eplId : list) {
            actionList.add(eplId);

            if (actionList.size() >= FILE_SIZE) {
                List<ReportCaptureNdfVo> rtList = reportDomainCapability.getCaptureNdfData(actionList);

                try {
                    reportNdfCsvFile.printNDC(rtList);
                } catch (Exception e) {
                    LOG.error("Cound  not open NDC file for export: " + e);
                }

                actionList.clear();
            }
        }

        if (actionList.size() > 0) {
            List<ReportCaptureNdfVo> rtList = reportDomainCapability.getCaptureNdfData(actionList);

            try {
                reportNdfCsvFile.printNDC(rtList);
            } catch (Exception e) {
                LOG.error("Cound not open NDC file for export: " + e);
            }

            actionList.clear();
        }

        reportNdfCsvFile.closeExport();
    }

    /**
     * Test List Ingredient Report
     */
    public void testListIngredientsReport() {

        assertNotNull(FR_NULL, reportsService);
        assertNotNull(FD_NULL, reportsService.getReportDomainCapability());

        ReportListIngredientCsvFile reportListIngredientCsvFile = new ReportListIngredientCsvFile();

        try {
            reportListIngredientCsvFile.createFile();
        } catch (Exception e) {
            LOG.error("Cound create List Ingredients file for export: " + e);
        }

        List<Long> list = reportDomainCapability.getIds(ReportType.LIST_INGREDIENTS);

        // do 10 at a time.
        assertNotNull(MSG, list);

        List<Long> actionList = new ArrayList<Long>(FILE_SIZE);

        for (Long eplId : list) {
            actionList.add(eplId);

            if (actionList.size() >= FILE_SIZE) {
                List<ReportProductVo> rtList = reportDomainCapability.getProductIngredientData(actionList);

                try {
                    reportListIngredientCsvFile.printListIngredient(rtList);
                } catch (Exception e) {
                    LOG.error("Cound not open Ingredient file for export:  " + e);
                }

                actionList.clear();
            }
        }

        if (actionList.size() > 0) {
            List<ReportProductVo> rtList = reportDomainCapability.getProductIngredientData(actionList);

            try {
                reportListIngredientCsvFile.printListIngredient(rtList);
            } catch (Exception e) {
                LOG.error("Cound not open Ingredient file for export: " + e);
            }

            actionList.clear();
        }

        reportListIngredientCsvFile.closeExport();

    }

    /**
     * Test Warning Labels Report
     */
    public void testWarningLabelsReport() {

        assertNotNull(FR_NULL, reportsService);
        assertNotNull(FD_NULL, reportsService.getReportDomainCapability());

        ReportWarningLableCsvFile reportWarningLableCsvFile = new ReportWarningLableCsvFile();

        try {
            reportWarningLableCsvFile.createFile();
        } catch (Exception e) {
            LOG.error("Cound not open Warning Label file for export: " + e);
        }

        List<ReportProductVo> list = reportDomainCapability.getProductWarningLabelData();
        List<ReportProductVo> actionList = new ArrayList<ReportProductVo>(FILE_SIZE);

        for (ReportProductVo vo : list) {
            actionList.add(vo);

            if (actionList.size() >= FILE_SIZE) {

                for (ReportProductVo rpVo : actionList) {
                    rpVo = domainService.getDrugReferenceCapability().retrieveWarningLabels(rpVo);
                }

                try {
                    reportWarningLableCsvFile.printWarningLabelReport(actionList);
                } catch (Exception e) {
                    LOG.error("Cound not create Warning Label file for export: " + e);
                }

                actionList.clear();

            }
        }

        reportWarningLableCsvFile.closeExport();
    }

    /**
     *Test DrugClassReport 
     */
    public void testDrugClassReport() {

        assertNotNull(FR_NULL, reportsService);
        assertNotNull(FD_NULL, reportsService.getReportDomainCapability());

        ReportDrugClassCsvFile reportDrugClassCsvFile = new ReportDrugClassCsvFile(reportsService);
        reportDrugClassCsvFile.createFile(DESCRIPTION);
        reportDrugClassCsvFile.printDrugClassReport(DESCRIPTION);
    }

    /**
     * Test NoActiveNdcReport 
     */
    public void testNoActiveNdcReport() {

        assertNotNull(FR_NULL, reportsService);
        assertNotNull(FD_NULL, reportsService.getReportDomainCapability());

        ReportNoActiveNdcCsvFile reportNoActiveNdcCsvFile = new ReportNoActiveNdcCsvFile(reportsService);
        reportNoActiveNdcCsvFile.createFile();
    }

    /**
     * testExclusionsReport
     */
    public void testExclusionsReport() {

        assertNotNull(FR_NULL, reportsService);
        assertNotNull(FD_NULL, reportsService.getReportDomainCapability());

        ReportExclusionsCsvFile reportExclusionsCsvFile = new ReportExclusionsCsvFile(reportsService);
        reportExclusionsCsvFile.createFile(START_DATE, STOP_DATE);
    }

    /**
     * testProposedInactivationReport
     */
    public void testProposedInactivationReport() {

        assertNotNull(FR_NULL, reportsService);
        assertNotNull(FD_NULL, reportsService.getReportDomainCapability());

        ReportProposedInactivationCsvFile reportProposedInactivationCsvFile =
            new ReportProposedInactivationCsvFile(reportsService);
        reportProposedInactivationCsvFile.createFile(START_DATE, STOP_DATE);
    }

    /**
     * testVuidReport
     */
    public void testVuidReport() {
        String startDateString = "Nov 4, 2009 8:14 PM";
        Date startDate = null;

        // Get the default MEDIUM/SHORT DateFormat
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

        // Parse the date
        try {
            startDate = format.parse(startDateString);
        } catch (Exception e) {
            fail(e.toString());
        }

        assertNotNull(FR_NULL, reportsService);
        assertNotNull(FD_NULL, reportsService.getReportDomainCapability());

        ReportVuidCsvFile reportVuidCsvFile = new ReportVuidCsvFile(reportsService);
        reportVuidCsvFile.createFile(startDate);
    }
}
