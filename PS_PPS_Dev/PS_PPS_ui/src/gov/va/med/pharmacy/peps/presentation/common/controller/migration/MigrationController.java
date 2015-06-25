/**
 * Source file created in 2007 by Southwest Research Institute
 * 
 * @author lsoto
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.migration;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.presentation.common.context.UserContext;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.pagetrail.PageTrail;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;
import gov.va.med.pharmacy.peps.service.common.migration.CSVResponseMessage;
import gov.va.med.pharmacy.peps.service.common.migration.ExportCSVFileData;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedDomain;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessDomainType;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessStatus;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationExportState;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationProcessState;
import gov.va.med.pharmacy.peps.service.common.session.MigrationCSVService;
import gov.va.med.pharmacy.peps.service.common.session.MigrationService;


/**
 * MigrationController
 *
 */
@Controller
@RoleNeeded(roles = { Role.PSS_PPSN_MIGRATOR })
public class MigrationController {

    private static final Logger LOG = Logger.getLogger(MigrationController.class);

    /** protected variable */
    @Autowired
    protected PageTrail pageTrail;

    @Autowired
    private MigrationService migrationService;
    private MigrationCSVService migrationCSVService;
    private boolean validationComplete = false;

    /**
     * currently not being used. - deprecated 
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @param param1 Optional Param 1
     * @param param2 optional param 2
     * @param param3 optional param 3
     * @return ModelAndView
     * @throws Exception exception
     */
    @RequestMapping(value = "getMigrationState")
    public ModelAndView getMigrationState(
        HttpServletRequest pRequest, HttpServletResponse pResponse,
        @RequestParam(value = "param1", required = false) String param1,
        @RequestParam(value = "param2", required = false) String param2,
        @RequestParam(value = "param3", required = false) String param3)
        throws Exception {
        LOG.debug("MigrationControler->> executing: getMigrationState ");
        Map<String, Object> model = new HashMap<String, Object>();

        MigrationProcessState state = migrationService.getMigrationProcessState();
        LOG.debug("migration state is: " + state.getStatus());

        model.put("migrationState", state);

        return new ModelAndView("migration-page", model);
    }

    /**
     * download
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @param csvSelected csvSelected
     * @return ModelAndView
     * @throws IOException IOException
     */
    @RequestMapping(value = "/download.go", method = RequestMethod.GET)
    public ModelAndView download(
        HttpServletRequest pRequest, HttpServletResponse pResponse,
        @RequestParam(value = "csvSelected", required = false) String csvSelected)
        throws IOException {
        pageTrail.clearTrail();
        pageTrail.addPage("migrationHome", "Migration Control", true);

        Map<String, Object> model = new HashMap<String, Object>();

        MigrationExportState status = migrationService.startExportProcess();

        model.put("exportState", status);

        return new ModelAndView("migration-home", model);
    }

    /**
     * stopExport
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @param csvSelected csvSelect
     * @return ModelAndView
     * @throws IOException IOException
     */
    @RequestMapping(value = "/stopExport.go", method = RequestMethod.GET)
    public ModelAndView stopExport(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse,
        @RequestParam(value = "csvSelected", required = false) String csvSelected)
        throws IOException {
        Map<String, Object> model = new HashMap<String, Object>();

        migrationService.stopExportProcess();

        pageTrail.clearTrail();
        pageTrail.addPage("migrationHome", "Migration Control", true);

        return new ModelAndView("migration-home", model);
    }

    /**
     * getExportStatus
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @param csvSelected csvSelect
     * @return ModelAndView
     * @throws IOException IOException
     */
    @RequestMapping(value = "/getExportStatus.go", method = RequestMethod.GET)
    public ModelAndView getExportStatus(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse,
        @RequestParam(value = "csvSelected", required = false) String csvSelected)
        throws IOException {
        Map<String, Object> model = new HashMap<String, Object>();

        MigrationExportState status = migrationService.getExportStatus();

        List<ExportCSVFileData> exportFiles = migrationCSVService.getExportFilesNamesIfExist();

        model.put("exportFiles", exportFiles);
        model.put("exportState", status);

        pageTrail.clearTrail();
        pageTrail.addPage("migrationHome", "Migration Control", true);

        return new ModelAndView("migration-home", model);
    }

    /**
     * saveExportdata
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @param exportFileSelected exportFileSelected
     * @return ModelAndView
     * @throws IOException IOException
     */
    @RequestMapping(value = "/saveDowloadFile.go", method = RequestMethod.GET)
    public ModelAndView saveExportdata(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse,
        @RequestParam(value = "csvSelected", required = false) ExportCSVFileData exportFileSelected)
        throws IOException {
        Map<String, Object> model = new HashMap<String, Object>();

        MigrationExportState status = migrationService.getExportStatus();

        List<ExportCSVFileData> exportFiles = migrationCSVService.getExportFilesNamesIfExist();

        model.put("exportState", status);

        if (exportFiles.size() > 0) {
            if (exportFileSelected == ExportCSVFileData.NDC) {
                LOG.debug("export is complete, downloading ndcfile...");
                File file = migrationCSVService.getExportFile(ExportCSVFileData.NDC);
                downloadCSVFiles(pResponse, file, ExportCSVFileData.NDC);
            } else if (exportFileSelected == ExportCSVFileData.ORDERABLE_ITEMS) {
                LOG.debug("export is complete, downloading Orderable Item file...");
                File file = migrationCSVService.getExportFile(ExportCSVFileData.ORDERABLE_ITEMS);
                downloadCSVFiles(pResponse, file, ExportCSVFileData.ORDERABLE_ITEMS);
            } else if (exportFileSelected == ExportCSVFileData.PRODUCT) {
                LOG.debug("export is complete, downloading Orderable Item file..");
                File file = migrationCSVService.getExportFile(ExportCSVFileData.PRODUCT);
                downloadCSVFiles(pResponse, file, ExportCSVFileData.PRODUCT);
            } else if (exportFileSelected == ExportCSVFileData.DOMAIN_MAPPING) {
                LOG.debug("export is complete, downloading Domain Mapping file..");
                File file = migrationCSVService.getExportFile(ExportCSVFileData.DOMAIN_MAPPING);
                downloadCSVFiles(pResponse, file, ExportCSVFileData.DOMAIN_MAPPING);
            }

            return null;

        } else {
            pageTrail.clearTrail();
            pageTrail.addPage("migrationHome", "Migration Control", true);

            return new ModelAndView("migration-home", model);
        }

    }

    /**
     * Downloads CSV file for Product, NDC & Orderable Items.
     * 
     * @param pResponse pResponse
     * @param pFile pFile
     * @param exportCSVFileData  exportCSVFileData
     * @throws IOException IOException
     */
    private void downloadCSVFiles(
        HttpServletResponse pResponse,
        File pFile,
        ExportCSVFileData exportCSVFileData)
        throws IOException {
        InputStream fileIn = null;
        ServletOutputStream out = null;

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();

        Format formatter = new SimpleDateFormat("yyyyMMddHHmm", Locale.US);
        String timeStamp = formatter.format(now);

        try {
            fileIn = new FileInputStream(pFile);
            int length = (int) pFile.length();

            pResponse.setContentType("application/octet-stream");
            pResponse.setHeader("Content-Disposition", "attachment;filename=" + exportCSVFileData + timeStamp + ".csv");

            out = pResponse.getOutputStream();
            byte[] outputByte = new byte[length];

            while (fileIn.read(outputByte, 0, length) != -1) {
                out.write(outputByte, 0, length);
            }

            fileIn.close();
            out.flush();
            out.close();

        } catch (Exception x) {
            out.flush();
            out.close();
            LOG.error("==>downloadCSVFile() " + x.getMessage());
        }
    }

    /**
     * Migraion home page (migrationStar.jsp)
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @return ModelAndView
     * @throws Exception Exceptions
     */
    @RequestMapping(value = "/migrationHome.go", method = RequestMethod.GET)
    public ModelAndView migrationHome(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse) throws Exception {
        LOG.debug("MigrationControler->> executing: migrationHome ");

        pageTrail.clearTrail();
        pageTrail.addPage("migrationHome", "Migration Control", true);

        Map<String, Object> model = new HashMap<String, Object>();

        MigrationProcessState state = migrationService.getMigrationProcessState();

        //returns a list of export files  that currently reside on the server.
        List<ExportCSVFileData> exportFiles = migrationCSVService.getExportFilesNamesIfExist();

        if (state == null) {
            model.put("exportFiles", exportFiles);

            return new ModelAndView("migration-home", model);
        } else if (state != null && state.getStatus() == ProcessStatus.RUNNING) {
            return new ModelAndView("redirect:/migrationControl.go", model);
        } else if (state.getStatus() == ProcessStatus.DB_RESET_RUNNING) {
            return new ModelAndView("redirect:/migrationControl.go", model);
        } else if (state.getStatus() == ProcessStatus.PAUSED) {
            return new ModelAndView("redirect:/migrationControl.go", model);
        }

        MigrationExportState exportStatus = migrationService.getExportStatus();

        model.put("exportFiles", exportFiles);
        model.put("exportState", exportStatus);
        model.put("migrationState", state);
        model.put("message1", "Migration home page1");

        return new ModelAndView("migration-home", model);
    }

    /**
     * validateFiles
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @return ModelAndView
     */
    @RequestMapping(value = "/validateFiles.go", method = RequestMethod.POST)
    public ModelAndView validateFiles(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse) {
        LOG.debug("MigrationControler->> executing: validateFiles");
        Map<String, Object> model = new HashMap<String, Object>();

        //make sure we delete the importe files before validation.
        migrationService.deleteAllImportFiles();

        CSVResponseMessage message = migrationCSVService.validateFileHeader(pRequest);

        if (message.isError()) {
            List<ExportCSVFileData> exportFiles = migrationCSVService.getExportFilesNamesIfExist();
            model.put("exportFiles", exportFiles);
            model.put("responseMessage", message);
            validationComplete = false;

            pageTrail.clearTrail();
            pageTrail.addPage("migrationHome", "Migration Control", true);

            return new ModelAndView("migration-home", model);
        }

        model.put("responseMessage", message);
        validationComplete = true;

        return new ModelAndView("redirect:/startResetDataBase.go", model);

    }

    /**
     * startResetDataBase
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @return ModelAndView
     */
    @RequestMapping(value = "/startResetDataBase.go", method = RequestMethod.GET)
    public ModelAndView startResetDataBase(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse) {
        LOG.debug("MigrationControler->> executing: resetDatabase");
        Map<String, Object> model = new HashMap<String, Object>();

        MigrationProcessState dbstate = null;

        if (validationComplete) {
            dbstate = migrationService.startDataBaseReset();
            dbstate.setPauseButtonText("Pause");
            model.put("migrationState", dbstate);
            model.put("dbstatus", dbstate);
            model.put("actionUrl", "getResetDataBaseStatus.go");

            validationComplete = false; // reset here.

            return new ModelAndView("migration-control", model);
        }

        List<ExportCSVFileData> exportFiles = migrationCSVService.getExportFilesNamesIfExist();
        model.put("exportFiles", exportFiles);

        CSVResponseMessage responseMessage = new CSVResponseMessage();
        responseMessage.setError(true);
        responseMessage.setResponseMessage("Files have not been validated!");
        model.put("responseMessage", responseMessage);

        return new ModelAndView("migration-home", model);
    }

    /**
     * getResetDataBaseStatus
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @return ModelAndView
     */
    @RequestMapping(value = "/getResetDataBaseStatus.go")
    public ModelAndView getResetDataBaseStatus(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse) {
        LOG.debug("MigrationControler->> executing: resetDatabase");
        Map<String, Object> model = new HashMap<String, Object>();

        boolean resetDb = true;

        MigrationProcessState dbstate = migrationService.getDataBaseResetStatus();

        if (dbstate.getStatus() == ProcessStatus.DB_RESET_RUNNING) {
            if (dbstate.getDatabaseResetErrorMessage().isError()) {
                LOG.debug("reset database error, stopped at: " + dbstate.getDbCount() + " with flag: " + resetDb);
                CSVResponseMessage responseMessage = new CSVResponseMessage();
                responseMessage.setError(true);
                responseMessage.setResponseMessage(dbstate.getDatabaseResetErrorMessage().getResponseMessage());

                model.put("dbstatus", dbstate);
                model.put("responseMessage", responseMessage);
                model.put("dbProgress", dbstate.getDbPercent());
                dbstate.setPauseButtonText("Pause");

                return new ModelAndView("migration-home", model);

            } else {
                CSVResponseMessage responseMessage = new CSVResponseMessage();
                responseMessage.setError(false);
                responseMessage.setResponseMessage("");
                model.put("responseMessage", responseMessage);
                model.put("dbProgress", dbstate.getDbPercent());

                model.put("dbstatus", dbstate);
                model.put("migrationState", dbstate);
                model.put("dbResetComplete", dbstate.isDatabaseResetCompleted());
                dbstate.setPauseButtonText("Pause");

            }
        } else {
            if (dbstate.getStatus() == ProcessStatus.DB_RESET_STOPPED && !dbstate.isDatabaseResetCompleted()) {
                CSVResponseMessage responseMessage = new CSVResponseMessage();
                responseMessage.setError(true);
                responseMessage.setResponseMessage("Error: Database Reset did not Complete!");
                model.put("responseMessage", responseMessage);
                model.put("dbProgress", dbstate.getDbPercent());
                dbstate.setPauseButtonText("Pause");

                return new ModelAndView("migration-home", model);
            } else {
                return new ModelAndView("redirect:/migrationStart.go", model);
            }
        }

        return new ModelAndView("migration-control", model);

    }

    /**
     * startMigration
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @param runStatus runStatus
     * @return ModelAndView
     */
    @RequestMapping(value = "/migrationStart.go", method = RequestMethod.GET)
    public ModelAndView startMigration(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse,
        @RequestParam(value = "runStatus", required = false) Boolean runStatus) {
        Map<String, Object> model = new HashMap<String, Object>();
        MigrationProcessState state = null;
        LOG.debug("MigrationControler->> executing: startMigration ");

        if (state == null || !state.isRunning()) {
            state = migrationService.startMigration(this.getUserContext(pRequest.getSession()).getUser());
            state.setMigrationComplete(false);

            if (state != null) {
                LOG.debug("migration state is: " + state.getStatus());
                calculateOverAllPercentComplete(state);
                model.put("dbProgress", state.getDbPercent());
                model.put("migrationState", state);
                model.put("actionUrl", "migrationControl.go");
            }
        }

        return new ModelAndView("migration-control", model);
    }

    /**
     * stopMigration
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @return ModelAndView
     * @throws Exception Exception
     */
    @RequestMapping(value = "/migrationStop.go", method = RequestMethod.GET)
    public ModelAndView stopMigration(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse) throws Exception {
        LOG.debug("MigrationControler->> executing: stopMigration ");
        Map<String, Object> model = new HashMap<String, Object>();

        MigrationProcessState state = migrationService.stopMigration();
        LOG.debug("migration state is: " + state.getStatus());
        resetState(state);
        calculateOverAllPercentComplete(state);

        if (state.getStatus() == ProcessStatus.DB_RESET_STOPPED && !state.isRunning()) {
            state.setPauseButtonText("Pause");
            model.put("migrationState", state);
            model.put("dbstatus", state);
            model.put("dbProgress", state.getDbPercent());

            return new ModelAndView("redirect:/migrationHome.go", model);
        }

        model.put("dbProgress", state.getDbPercent());
        model.put("dbstatus", state);
        model.put("migrationState", state);

        return new ModelAndView("redirect:/migrationSummary.go", model);
    }

    /**
     *  pauseMigration
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @return ModelAndView
     * @throws Exception Exception
     */
    @RequestMapping(value = "/migrationPause.go", method = RequestMethod.GET)
    public ModelAndView pauseMigration(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse) throws Exception {
        LOG.debug("MigrationControler->> executing: pauseMigration ");
        Map<String, Object> model = new HashMap<String, Object>();

        MigrationProcessState state = migrationService.pauseMigration();
        LOG.debug("migration state is: " + state.getStatus());

        calculateOverAllPercentComplete(state);
        state.setPauseButtonText("Resume");
        model.put("migrationState", state);
        model.put("dbstatus", state);
        model.put("dbProgress", state.getDbPercent());

        return new ModelAndView("migration-control", model);
    }

    /**
     * resumeMigration
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @return ModelAndView
     * @throws Exception Exception
     */
    @RequestMapping(value = "/migrationResume.go", method = RequestMethod.GET)
    public ModelAndView resumeMigration(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse) throws Exception {
        LOG.debug("MigrationControler->> executing: resumeMigration ");
        Map<String, Object> model = new HashMap<String, Object>();

        MigrationProcessState state = migrationService.resumeMigration();
        LOG.debug("migration state is: " + state.getStatus());

        calculateOverAllPercentComplete(state);
        state.setPauseButtonText("Pause");
        model.put("migrationState", state);
        model.put("dbstatus", state);

        return new ModelAndView("redirect:/migrationControl.go", model);
    }

    /**
     * migrationControl
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @return ModelAndView
     */
    @RequestMapping(value = "/migrationControl.go")
    public ModelAndView migrationControl(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse) {
        LOG.debug("MigrationControler->> executing: migrationControl");
        Map<String, Object> model = new HashMap<String, Object>();

        MigrationProcessState state = migrationService.getMigrationProcessState();
        LOG.debug("migration state: " + state);

        String referer = pRequest.getHeader("Referer");
        LOG.debug("referer : " + referer);

        if (state != null) {
            LOG.debug("migration state is: " + state.getStatus());

            if (state.getStatus() == ProcessStatus.STOPPED && state.isMigrationProceessCompleted()) {
                model.put("migrationState", state);
                LOG.debug("Reached stopped state, state.isMigrationProcessCompleted()");
                calculateOverAllPercentComplete(state);

                return new ModelAndView("redirect:/migrationSummary.go", model);
            } else if (state.getStatus() == ProcessStatus.DB_RESET_RUNNING) {
                state.setPauseButtonText("Pause");
                model.put("migrationState", state);
                model.put("dbstatus", state);
                model.put("dbProgress", state.getDbPercent());

                return new ModelAndView("migration-control", model);
            } else if (state.getStatus() == ProcessStatus.RUNNING) {
                calculateOverAllPercentComplete(state);

                model.put("migrationState", state);
                model.put("dbstatus", state);
                model.put("dbProgress", state.getDbPercent());

                return new ModelAndView("migration-control", model);
            } else if (state.getStatus() == ProcessStatus.PAUSED) {
                model.put("migrationState", state);
                model.put("dbstatus", state);
                model.put("dbProgress", state.getDbPercent());

                return new ModelAndView("migration-control", model);
            }
        }

        return new ModelAndView("redirect:" + referer, model);
    }

    /**
     * migrationSummary
     * @param pRequest pRequest
     * @param model2 modle2
     * @param pResponse pResponse
     * @return ModelAndView
     */
    @RequestMapping(value = "/migrationSummary.go", method = RequestMethod.GET)
    public ModelAndView migrationSummary(
        HttpServletRequest pRequest,
        Model model2,
        HttpServletResponse pResponse) {
        Map<String, Object> model = new HashMap<String, Object>();

        String referer = pRequest.getHeader("Referer");
        MigrationProcessState state = migrationService.getMigrationProcessState();

        if (state != null) {
            if (state.getStatus() == ProcessStatus.STOPPED || state.getStatus() == ProcessStatus.DB_RESET_STOPPED) {

                //retrieves the Migration File and populates the domain objects.
                migrationService.populateMigrationDomains(state);

                model.put("migrationState", state);

                return new ModelAndView("migration-summary", model);
            }
        } else {
            MigrationProcessState newState = new MigrationProcessState();
            migrationService.populateMigrationDomains(newState);

            model.put("migrationState", newState);

            return new ModelAndView("migration-summary", model);
        }

        return new ModelAndView("redirect:" + referer, model);
    }

    /**
     * summaryPrint
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @return ModelAndView
     */
    @RequestMapping(value = "/summaryPrint.go", method = RequestMethod.GET)
    public ModelAndView summaryPrint(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse) {
        Map<String, Object> model = new HashMap<String, Object>();
        LOG.debug("MigrationControler->> executing: summaryPrint");

        MigrationProcessState state = new MigrationProcessState();

        //retrieves the Migration File and populates the domain objects.
        migrationService.populateMigrationDomains(state);

        model.put("migrationState", state);

        return new ModelAndView("migration-summary-print", model);
    }

    /**
     * migrationDetails
     * @param pRequest pRequest
     * @param pResponse pResponse
     * @param singleReport singleReport
     * @param printReport printReport
     * @param pProcessDomainType pProcessDomainType
     * @return ModelAndView
     */
    @RequestMapping(value = "/migrationDetails.go", method = RequestMethod.GET)
    public ModelAndView migrationDetails(
        HttpServletRequest pRequest,
        HttpServletResponse pResponse,
        @RequestParam(value = "singleReport", required = false) Boolean singleReport,
        @RequestParam(value = "printReport", required = false) Boolean printReport,
        @RequestParam(value = "viewReport", required = false) ProcessDomainType pProcessDomainType) {
        Map<String, Object> model = new HashMap<String, Object>();
        Map<Object, Object> domainMap = null;
        MigratedDomain domain = null;

        LOG.debug("MigrationControler->> executing: migrationDetails");

        MigrationProcessState state = new MigrationProcessState();

        migrationService.populateMigrationDomains(state);
        migrationService.populateMigrationDomainErrors(state);
        List<MigratedDomain> domainList = state.getMigratedDomainList();

        if (singleReport) {
            domainMap = state.createDomainMap();
            domain = (MigratedDomain) domainMap.get(pProcessDomainType);

            model.put("domain", domain);
        }

        model.put("migradedDomainList", domainList);
        model.put("singleReport", singleReport);
        model.put("migrationState", state);

        if (printReport) {
            return new ModelAndView("migration-details-print", model);
        } else {
            return new ModelAndView("migration-details", model);
        }
    }

    /**
     * calculates the over-all migration progress
     * 
     * @param state state
     */
    public void calculateOverAllPercentComplete(MigrationProcessState state) {
        int maxvalue =
            state.getMigratedNDCCsvFile().getMaxRecords() + state.getMigratedOrderableItemsCsv().getMaxRecords()
                + ProcessDomainType.DRUG_UNITS_ACTIVE.getAllDomainMaxRecords();
        int total;

        LOG.debug("NDC MaxRecords: " + state.getMigratedNDCCsvFile().getMaxRecords());
        LOG.debug("OI MaxRecords: " + state.getMigratedOrderableItemsCsv().getMaxRecords());
        LOG.debug("calculateOverAllPercentComplete()-->>state.getDrugUnit count:"
                  + state.getMigratedDrugUnit().getCount());
        LOG.debug("calculateOverAllPercentComplete()-->>state.getVaDispenseUnit count: "
                  + state.getMigratedVaDispenseUnit().getCount());
        LOG.debug("calculateOverAllPercentComplete()-->>state.getVaGenericName count: "
                  + state.getMigratedVaGenericName().getCount());
        LOG.debug("calculateOverAllPercentComplete()-->>state.getDosageFormPercent count:  "
                  + state.getMigratedDosageForm().getCount());
        LOG.debug("calculateOverAllPercentComplete()-->>state.getDrugClassPercent count: "
                  + state.getMigratedDrugClass().getCount());
        LOG.debug("calculateOverAllPercentComplete()-->>state.getDrugIngredientsPercent count:  "
                  + state.getMigratedIngredients().getCount());
        LOG.debug("calculateOverAllPercentComplete()-->>state.getOrderableItemsCSVFilePercent count:  "
                  + state.getMigratedOrderableItemsCsv().getCount());
        LOG.debug("calculateOverAllPercentComplete()-->>state.getVaProductPercent count: "
                  + state.getMigratedVaProduct().getCount());
        LOG.debug("calculateOverAllPercentComplete()-->>state.getMigratedNDCCsvFile count:  "
                  + state.getMigratedNDCCsvFile().getCount());

        double sum =
            state.getMigratedDrugUnit().getCount() + state.getMigratedVaDispenseUnit().getCount()
                + state.getMigratedVaGenericName().getCount() + state.getMigratedDosageForm().getCount()
                + state.getMigratedDrugClass().getCount() + state.getMigratedIngredients().getCount()
                + state.getMigratedOrderableItemsCsv().getCount() + state.getMigratedVaProduct().getCount()
                + state.getMigratedNDCCsvFile().getCount();

        total = (int) ((sum / maxvalue) * PPSConstants.I100);
        LOG.debug("calculateOverAllPercentComplete()-->>total percent " + total + "%");
        LOG.debug("calculateOverAllPercentComplete()-->>All domains count " + maxvalue);

        if (state.isMigrationProceessCompleted()) {
            total = PPSConstants.I100;
            LOG.debug("setOverAllProgress was set to 100%");
        }

        // set overall progress
        state.setOverAllProgress(total);
    }

    /**
     * reset state, used only for testing
     * 
     * @param state state
     */
    private void resetState(MigrationProcessState state) {
        state.setRunning(false);
        state.setOverAllProgress(0);
        state.setPauseButtonText("Pause");
    }

    /**
     * getMigrationService
     * @return MigrationService
     */
    public MigrationService getMigrationService() {
        return migrationService;
    }

    /**
     * setMigrationService
     * @param pMigrationService pMigrationService
     */
    public void setMigrationService(MigrationService pMigrationService) {
        this.migrationService = pMigrationService;
    }

    /**
     * getUserContext
     * @param session session
     * @return UserContext
     */
    public UserContext getUserContext(HttpSession session) {
        UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);

        if (userContext == null) {
            userContext = new UserContext();
            session.setAttribute(ControllerConstants.USER_CONTEXT_KEY, new UserContext());
        }

        return userContext;
    }

    /**
     * getMigrationCSVService
     * 
     * @return MigrationCSVService
     */
    public MigrationCSVService getMigrationCSVService() {
        return migrationCSVService;
    }

    /**
     * setMigrationCSVService
     * @param migrationCSVService migrationCSVService
     */
    public void setMigrationCSVService(MigrationCSVService migrationCSVService) {
        this.migrationCSVService = migrationCSVService;
    }

    /**
     * isValidationComplete
     * @return true if successful
     */
    public boolean isValidationComplete() {
        return validationComplete;
    }

    /**
     * setValidationComplete
     * @param pValidationComplete pValidationComplete
     */
    public void setValidationComplete(boolean pValidationComplete) {
        this.validationComplete = pValidationComplete;
    }
}
