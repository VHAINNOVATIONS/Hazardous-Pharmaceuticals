/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.ReportDrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.common.vo.ReportVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.service.common.reports.ReportExportState;
import gov.va.med.pharmacy.peps.service.common.session.ReportsService;
import gov.va.med.pharmacy.peps.service.common.utility.ReportDrugClassCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ReportExclusionsCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ReportNoActiveNdcCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ReportProposedInactivationCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ReportVuidCsvFile;


/**
 * The controller used to process the reports
 */
@Controller("reportsController")
public class ReportsController extends PepsController {

    private static final Logger LOG = Logger.getLogger(ReportsController.class);
    private static final String FILE_PATH = "./tmp/";
    private static final String REPORT_TYPE_MAP = "reportTypeMap";
    private static final String REPORT_TYPE_DOT = "ReportsType.";
    private static final String SHOW_PRINT = "showPrint";
    private static final String SPACE_REPORT = " Report";
    private static final String REPORT_VO = "reportVo";
    private static final String SELECTED_REPORT_TYPE = "selectedReportType";
    private static final String PRINT_TEMPLATE = "printTemplate";
    private static final String REPORTS = "Reports";
    private static final String REPORTS_DOT = "reports.";
    private static final String CSV = ".csv";

    private static SortedMap<ReportType, String> REPORTS_MAP;
    private static String REPORTS_VIEW = "reports";
    private static SortedMap<ReportType, String> reportTypeMap;

    @Autowired
    private ReportsService reportsService;

    private ReportVo reportVo = new ReportVo();
    private PrintTemplateVo printTemplate;
    private String newView;
    private String selectedReportTitle;

    /** 
     * Request mapping for viewable reports
     * @param rvo ReportVo
     * @param reportPopup reportPopup
     * @param isFirstRun isFirstRun
     * @param model for report data
     * @param request request
     * @param session session
     * @param locale locale
     * @return Report view
     * @throws Exception Exception
     */
    @RequestMapping(value = "/reports", method = { RequestMethod.GET })
    public String reports(
        @ModelAttribute(REPORT_VO) ReportVo rvo,
        @RequestParam(value = "reportPopup", required = false) boolean reportPopup,
        @RequestParam(value = "isFirstRun", required = false, defaultValue = "true") String isFirstRun,
        Model model,
        HttpServletRequest request,
        HttpSession session,
        Locale locale) throws Exception {

        boolean hasRun = StringUtils.isEmpty(isFirstRun) ? true : Boolean.parseBoolean(isFirstRun);

        if (hasRun) {
            initialize(locale);

            // Initialize form elements
            rvo.setReportType(ReportType.NDC_LIST_PRINT_TEMPLATE);
            rvo.setPrintable(rvo.getReportType().isPrintable());
            rvo.setHasStart(rvo.getReportType().getStart());
            rvo.setHasStop(rvo.getReportType().getStop());
            rvo.setHasDesc(rvo.getReportType().getDescription());
            rvo.setGenerateOn(false);

            model.addAttribute(REPORT_TYPE_MAP, REPORTS_MAP);
            model.addAttribute(REPORT_VO, rvo);

//            model.addAttribute("helpID", HelpConstants.REPORT_VIEW);

            return REPORTS_VIEW;
        } else {

            // Update form elements
            this.reportVo.setReportType(rvo.getReportType());
            rvo.setPrintable(rvo.getReportType().isPrintable());
            rvo.setHasStart(rvo.getReportType().getStart());
            rvo.setHasStop(rvo.getReportType().getStop());
            rvo.setHasDesc(rvo.getReportType().getDescription());

            // Reset some of the Vo fields
            rvo.clearAll();

            selectedReportTitle =
                getMessageSource().getMessage(REPORT_TYPE_DOT + rvo.getReportType(), null, locale) + SPACE_REPORT;

//            model.addAttribute("drugClassResultsList", reportVo.getReportDrugClassList());
            model.addAttribute(SHOW_PRINT, reportPopup);
            model.addAttribute(SELECTED_REPORT_TYPE, selectedReportTitle);
            model.addAttribute(REPORT_TYPE_MAP, REPORTS_MAP);
            model.addAttribute(REPORT_VO, rvo);

//            model.addAttribute("helpID", HelpConstants.REPORT_VIEW);

            if (this.reportVo.getReportType() == ReportType.ITEM_AUDIT_HISTORY_PRINT_TEMPLATE) {
                rvo.setVUIDResults(true);

                // Get sync time for VUID                
                rvo.setStartDate(setDateFromList(NationalSetting.MESSAGE_STATUS));
            }

            //stubbed
            model.addAttribute("proposedInactivation", rvo.getStartDate());
            model.addAttribute(PRINT_TEMPLATE, printTemplate);

            return REPORTS_VIEW;
        }
    }

    /**
     * Runs the report against criteria
     * @param rvo Bean used for model attribute
     * @param reportPopup Determines if view is popup
     * @param model model
     * @param request request
     * @param session session
     * @param pResponse pResponse
     * @param locale locale
     * @return newView View of requested report
     * @throws Exception Exception
     */
    @RequestMapping(value = "/reports.update.go", method = { RequestMethod.GET })
    public String runReport(
        @ModelAttribute(REPORT_VO) ReportVo rvo,
        @RequestParam(value = "reportPopup", required = false) boolean reportPopup,
        Model model,
        HttpServletRequest request,
        HttpSession session,
        HttpServletResponse pResponse,
        Locale locale) throws Exception {

        pageTrail.clearTrail();
        pageTrail.addPage("reports", REPORTS, true);

        if (reportPopup) {
            rvo.setReportType(this.reportVo.getReportType());
            rvo.setStartDate(this.reportVo.getStartDate());
            rvo.setStopDate(this.reportVo.getStopDate());
            rvo.setDescription(this.reportVo.isDescription());
            rvo.setHasStart(rvo.getReportType().getStart());
            rvo.setHasStop(rvo.getReportType().getStop());
        } else {

            // Update form elements
            this.reportVo.setReportType(rvo.getReportType());
            rvo.setPrintable(rvo.getReportType().isPrintable());
            rvo.setHasStart(rvo.getReportType().getStart());
            rvo.setHasStop(rvo.getReportType().getStop());
            rvo.setHasDesc(rvo.getReportType().getDescription());
            this.reportVo.setStartDate(rvo.getStartDate());
            this.reportVo.setStopDate(rvo.getStopDate());
        }

        selectedReportTitle =
            getMessageSource().getMessage(REPORT_TYPE_DOT + rvo.getReportType(), null, locale) + SPACE_REPORT;

        if (rvo.getReportType() == ReportType.ITEM_AUDIT_HISTORY_PRINT_TEMPLATE) {
            rvo.setVUIDResults(true);

            rvo.setReportVuidGenericList(reportsService.getVuidApprovalReportGeneric(rvo.getStartDate())
                .getNewGenericList());

            rvo.setReportVuidModifiedGenericList(reportsService.getVuidApprovalReportModifiedGeneric(
                rvo.getStartDate()).getModifiedGenericList());

            rvo.setReportVuidIngredientList(reportsService.getVuidApprovalReportIngredient(rvo.getStartDate())
                .getNewIngredientList());
            rvo.setReportVuidModifiedIngredientList(reportsService.getVuidApprovalReportModifiedIngredient(
                rvo.getStartDate()).getModifiedIngredientList());
            rvo.setReportVuidProductList(reportsService.getVuidApprovalReportProducts(rvo.getStartDate())
                .getNewProductList());
            rvo.setReportVuidModifiedList(reportsService.getVuidApprovalReportModifiedProducts(rvo.getStartDate())
                .getModifedProductList());
            rvo.setReportVuidDrugClassList(reportsService.getVuidApprovalReportDrugClasses(rvo.getStartDate())
                .getNewDrugClassList());
        } else if (rvo.getReportType() == ReportType.VA_DRUG_CLASSIFICATION_REPORT_PRINT_TEMPLATE) {
            rvo.setDrugClassResults(true);
            this.reportVo.setDescription(rvo.isDescription());
            rvo.setReportDrugClassList(getDrugClassReport());
        } else if (rvo.getReportType() == ReportType.NDC_LIST_PRINT_TEMPLATE) {
            rvo.setGenerateOn(processRunning(rvo.getReportType()));
            rvo.setCsvStart(setDateFromList(NationalSetting.REPORT_CAPTURE_START));
            rvo.setCsvComplete(setDateFromList(NationalSetting.REPORT_CAPTURE_COMPLETE));
        } else if (rvo.getReportType() == ReportType.LIST_INGREDIENTS) {
            rvo.setGenerateOn(processRunning(rvo.getReportType()));
            rvo.setCsvStart(setDateFromList(NationalSetting.REPORT_INGREDIENT_START));
            rvo.setCsvComplete(setDateFromList(NationalSetting.REPORT_INGREDIENT_COMPLETE));
        } else if (rvo.getReportType() == ReportType.PRINT_PRODUCTS_WARNING_LABELS) {
            rvo.setGenerateOn(processRunning(rvo.getReportType()));
            rvo.setCsvStart(setDateFromList(NationalSetting.REPORT_WARNING_START));
            rvo.setCsvComplete(setDateFromList(NationalSetting.REPORT_WARNING_COMPLETE));
        } else {
            rvo.setGeneralResults(true);
            rvo.setReportProductList(retrieveReportList(rvo.getStartDate(), rvo.getStopDate()));
        }
        
        // Export Status
        if (reportsService.getStatus(this.getReportType()) != null) {
            ReportExportState state = reportsService.getStatus(this.getReportType());
            rvo.setRecordCount(state.getRecordCounter());
            rvo.setRecordTotal(state.getRecordTotal());
        }

        model.addAttribute("drugClassResultsList", rvo.getReportDrugClassList());
        model.addAttribute(SHOW_PRINT, reportPopup);
        model.addAttribute("isVUIDResults", rvo.isVUIDResults());
        model.addAttribute("isGeneralResults", rvo.isGeneralResults());
        model.addAttribute("isDrugClassResults", rvo.isDrugClassResults());

        model.addAttribute(SELECTED_REPORT_TYPE, selectedReportTitle);
        model.addAttribute(REPORT_TYPE_MAP, REPORTS_MAP);
        model.addAttribute(REPORT_VO, rvo);

//        //stubbed
//        model.addAttribute("proposedInactivation", reportVo.getStartDate());
        model.addAttribute(PRINT_TEMPLATE, printTemplate);

        if (reportPopup) {
            rvo.setPrintable(false);
            newView = "report.popup";
        } else {
            newView = REPORTS_DOT + rvo.getReportType().getView();
        }

//        pageTrail.addPage("reportTitle", selectedReportTitle, true);

        return newView;
    }

    /**
     * Initializes background generate process
     * @param model model
     * @param request request
     * @param session session
     * @param pResponse HttpServletResponse
     * @param locale locale
     * @param rvo reportVo
     * @return newView
     */
    @RequestMapping(value = "/reports.generate.go", method = { RequestMethod.GET })
    public String generateCSV(
        @ModelAttribute(REPORT_VO) ReportVo rvo,
        Model model,
        HttpServletRequest request,
        HttpSession session,
        HttpServletResponse pResponse,
        Locale locale) {

        this.reportVo.setReportType(rvo.getReportType());

        // Disables Generate/Download buttons for page refresh
        rvo.setGenerateOn(false);

        model.addAttribute(REPORT_TYPE_MAP, REPORTS_MAP);
        model.addAttribute(REPORT_VO, rvo);
        model.addAttribute(SELECTED_REPORT_TYPE,
            getMessageSource().getMessage(REPORT_TYPE_DOT + rvo.getReportType(), null, locale) + SPACE_REPORT);

        if (processRunning(rvo.getReportType())) {
            reportsService.startProcess(rvo.getReportType());
        }

        return REPORTS_DOT + rvo.getReportType().getView();
    }

    /**
     * Downloads selected report
     * @param model model
     * @param request request
     * @param session session
     * @param pResponse HttpServletResponse
     * @param locale locale
     * @return newView
     * @throws Exception Exception
     */
    @RequestMapping(value = "/reports.download.go", method = { RequestMethod.GET })
    public String downloadCSV(Model model, HttpServletRequest request,
        HttpSession session, HttpServletResponse pResponse,
        Locale locale) throws Exception {

        switch (reportVo.getReportType()) {
            case NDC_LIST_PRINT_TEMPLATE:
            case PRINT_PRODUCTS_WARNING_LABELS:
            case LIST_INGREDIENTS:
                break;
            case VA_DRUG_CLASSIFICATION_REPORT_PRINT_TEMPLATE:
                ReportDrugClassCsvFile reportDrugClassCsvFile = new ReportDrugClassCsvFile(reportsService);
                reportDrugClassCsvFile.createFile(reportVo.isDescription());
                reportDrugClassCsvFile.printDrugClassReport(reportVo.isDescription());
                break;
            case ACTIVE_PRDUCTS_NO_ACTIVE_NDCS_REPORT_PRINT_TEMPLATE:
                ReportNoActiveNdcCsvFile reportNoActiveNdcCsvFile = new ReportNoActiveNdcCsvFile(reportsService);
                reportNoActiveNdcCsvFile.createFile();
                break;
            case PRODUCTS_WITH_EXCLUSIONS_DRG_DRG_REPORT_PRINT_TEMPLATE:
                ReportExclusionsCsvFile reportExclusionsCsvFile = new ReportExclusionsCsvFile(reportsService);
                reportExclusionsCsvFile.createFile(reportVo.getStartDate(), reportVo.getStopDate());
                break;
            case ACTIVE_PRODUCTS_PROPOSED_INACTIVATION_REPORT_PRINT_TEMPLATE:
                ReportProposedInactivationCsvFile reportProposedInactivationCsvFile =
                    new ReportProposedInactivationCsvFile(reportsService);
                reportProposedInactivationCsvFile.createFile(reportVo.getStartDate(), reportVo.getStopDate());
                break;
            case ITEM_AUDIT_HISTORY_PRINT_TEMPLATE:

                if (reportVo.getStartDate() != null) {
                    ReportVuidCsvFile reportVuidCsvFile = new ReportVuidCsvFile(reportsService);
                    reportVuidCsvFile.createFile(reportVo.getStartDate());
                }

                break;
            default:
                if (!downloadCSV(pResponse, reportVo.getReportType().getView())) {
                    model.addAttribute(REPORT_TYPE_MAP, REPORTS_MAP);
                    model.addAttribute(REPORT_VO, reportVo);
                    model.addAttribute(SELECTED_REPORT_TYPE,
                        getMessageSource().getMessage(REPORT_TYPE_DOT + reportVo.getReportType(), null, locale)
                            + SPACE_REPORT);

                    return "reports.error";
                }
        }

        downloadCSV(pResponse, reportVo.getReportType().getView());

        return null;
    }

    /**
     * Checks if report generate process is running.
     * @param reportType report type to check status for
     * @return true if generate is allowed.
     */
    private Boolean processRunning(ReportType reportType) {
        ReportExportState state = reportsService.getStatus(reportType);
        Boolean v = false;

        if (state == null) {
            v = true;

        } else if (state != null) {
            if (!state.isRunning() && state.isExportComplete()) {
                v = true;
            }
        }

        return v;
    }

    /**
     * Retrieve Date from National Setting for generated reports
     * @param psetting National Setting 
     * @return Date from list.
     */
    public Date setDateFromList(NationalSetting psetting) {

        List<NationalSettingVo> dateList = null;
        dateList = reportsService.getGenerateDates();
        Date dateValue = null;

        for (NationalSettingVo vo : dateList) {
            if (psetting.toString().equals(vo.getKeyName())) {
                dateValue = vo.getDateValue();
                break;
            }
        }

        return dateValue;
    }

    /**
     * Loads report list
     * @param startDate Report criteria start date
     * @param stopDate Report criteria stop date
     * @return tmpReportDataList temporary report data
     * @throws ItemNotFoundException Item not found exception
     */
    private List<ReportProductVo> retrieveReportList(Date startDate, Date stopDate) throws ItemNotFoundException {

        List<ReportProductVo> tmpReportDataList = new ArrayList<ReportProductVo>();
        List<ReportProductVo> tmp;

        if (reportVo.getReportType() != null) {
            switch (reportVo.getReportType()) {

                case PRODUCTS_WITH_EXCLUSIONS_DRG_DRG_REPORT_PRINT_TEMPLATE:

                    tmp = reportsService.getProductExclusionData(startDate, stopDate);

                    if (tmp != null && !tmp.isEmpty()) {
                        tmpReportDataList.addAll(tmp);
                    }

                    printTemplate = ControllerConstants.PRODUCTS_WITH_EXCLUSIONS_DRG_DRG_REPORT_PRINT_TEMPLATE;
                    break;

                case ACTIVE_PRODUCTS_PROPOSED_INACTIVATION_REPORT_PRINT_TEMPLATE:
                    LOG.error("inretrieveReportList:ACTIVE_PRODUCTS_PROPOSED_INACTIVATION_REPORT_PRINT_TEMPLATE");
                    tmp = reportsService.getProductProposedInactivationDate(startDate, stopDate);

                    if (tmp != null && !tmp.isEmpty()) {
                        LOG.error("tmp is not null");
                        tmpReportDataList.addAll(tmp);
                    }

                    printTemplate = ControllerConstants.ACTIVE_PRODUCTS_PROPOSED_INACTIVATION_REPORT_PRINT_TEMPLATE;
                    LOG.error("inretrieveReportList:ACTIVE_PRODUCTS_PROPOSED_INACTIVATION_REPORT_PRINT_TEMPLATE before Break;");
                    break;

                case ACTIVE_PRDUCTS_NO_ACTIVE_NDCS_REPORT_PRINT_TEMPLATE:
                    tmp = reportsService.getProductNoActiveNdcData();

                    if (tmp != null && !tmp.isEmpty()) {
                        tmpReportDataList.addAll(tmp);
                    }

                    printTemplate = ControllerConstants.ACTIVE_PRDUCTS_NO_ACTIVE_NDCS_REPORT_PRINT_TEMPLATE;
                    break;

                default:
                    break;

            }
        }

        return tmpReportDataList;

    }

    /**
     * @return tmpReportDataList
     * @throws ItemNotFoundException item not found exception
     */
    private List<ReportDrugClassVo> getDrugClassReport() throws ItemNotFoundException {
        List<ReportDrugClassVo> tmpReportDataList = new ArrayList<ReportDrugClassVo>();

        printTemplate = ControllerConstants.VA_DRUG_CLASSIFICATION_REPORT_PRINT_TEMPLATE;
        tmpReportDataList = reportsService.getDrugClassData();

        return tmpReportDataList;
    }

    /**
     * @param locale locale
     */
    private void initialize(Locale locale) {
        pageTrail.clearTrail();
        pageTrail.addPage("reports", REPORTS, true);
        REPORTS_MAP = new TreeMap<ReportType, String>();

        for (ReportType element : ReportType.values()) {
            if (element.isPrintable()) {
                REPORTS_MAP.put(element, getMessageSource().getMessage(REPORT_TYPE_DOT + element.name(), null, locale));

            } else {
                REPORTS_MAP.put(element, getMessageSource().getMessage(REPORT_TYPE_DOT + element.name(), null, locale)
                    + " (download only)");
            }
        }
    }

    // Getters and Setters

    /**
     * Set reportService
     * @param nreportsService Service to use
     */
    public void setReportsService(ReportsService nreportsService) {
        reportsService = nreportsService;
    }

    /**
     * Get reportService
     * @return the reportsService
     */
    public ReportsService getReportsService() {
        return reportsService;
    }

    /**
     * Set reportType
     * @param reportType the reportType to set
     */
    public void setReportType(ReportType reportType) {
        reportVo.setReportType(reportType);
    }

    /**
     * Get reportType
     * @return the reportType
     */
    public ReportType getReportType() {
        return reportVo.getReportType();
    }

    /**
     * getReportTypeMap
     * @return the reportType
     */
    public SortedMap<ReportType, String> getReportTypeMap() {
        return reportTypeMap;
    }

    /**
     * setReportTypeMap
     * @param reportTypeMap report type to use
     */
    public void setReportTypeMap(SortedMap<ReportType, String> reportTypeMap) {
        ReportsController.reportTypeMap = reportTypeMap;
    }

    /**
     * Download generated report CSV file. 
     * @param pResponse pResponse
     * @param reportSelected reportType from ReportVo
     * @throws IOException IOException
     * @return true if no exceptions
     */
    private boolean downloadCSV(HttpServletResponse pResponse, String reportSelected) throws IOException {
        InputStream in = null;
        ServletOutputStream out = null;
        String path = FILE_PATH;
        String file = (path + reportSelected + CSV);

        try {
            in = new FileInputStream(file);
            int length = (int) file.length();

            pResponse.setContentType("application/octet-stream");
            pResponse.setHeader("Content-Disposition", "attachment;filename=" + reportSelected + CSV);

            out = pResponse.getOutputStream();
            byte[] outputByte = new byte[length];
            int size = 0;

            // read the output bytes
            while ((size = in.read(outputByte, 0, length)) != -1) {
                out.write(outputByte, 0, size);
            }

            in.close();
            out.flush();
            out.close();

        } catch (Exception x) {

            // flush and close the outfile
            out.flush();
            out.close();
            LOG.error("==>downloadCSVFile() " + x.getMessage());

            return false;
        } 

        return true;
    }

    /**
     * Get reportVo
     * @return reportVo
     */
    public ReportVo getReportVo() {
        return reportVo;
    }

    /**
     * Set reportVo
     * @param reportVo reportVo
     */
    public void setReportVo(ReportVo reportVo) {
        this.reportVo = reportVo;
    }

}
