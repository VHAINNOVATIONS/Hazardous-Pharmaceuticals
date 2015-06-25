/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.ReportCaptureNdfVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ReportDomainCapability;
import gov.va.med.pharmacy.peps.service.common.reports.ReportExportState;
import gov.va.med.pharmacy.peps.service.common.reports.ReportObserver;
import gov.va.med.pharmacy.peps.service.common.reports.ReportProcessStatus;
import gov.va.med.pharmacy.peps.service.common.reports.ReportSubject;
import gov.va.med.pharmacy.peps.service.common.session.DomainService;
import gov.va.med.pharmacy.peps.service.common.session.ReportExportProcess;
import gov.va.med.pharmacy.peps.service.common.utility.ReportListIngredientCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ReportNdfCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ReportWarningLableCsvFile;


/** Generates 3 reports in a background process. */

public class ReportExportProcessImpl implements ReportExportProcess, Runnable, ReportSubject {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(ReportExportProcessImpl.class);
    private static final int FILE_SIZE = 300;
    private static final Long USER_ID = 42L;
    private static final int TIMEOUT = 600;
    private static final int SLEEP = 5000;
    private static final String ERROR = "Cound not open NDC file for export: ";
    private static final String LIST_ERROR = "FAILED TO retrieve List. Error Message is ";
    private static final String OF = " of ";
    private volatile boolean running;
    private volatile ReportExportState exportState;
    private List<ReportObserver> observers;
    private boolean debug = false;
    private NationalSettingDomainCapability nationalSettingDomainCapability;
    private DomainService domainService;
    private PlatformTransactionManager transactionManager;
    private ReportDomainCapability reportDomainCapability;


    /**
     * run method for ResportExportProcessImpl
     */
    @Override
    public void run() {
        LOG.debug("=======> Starting run() method in  ReportExportProcessImpl <====================");
        initialize();
        runProcesses();

        notifyObservers();
        LOG.debug("=======> Exiting run() in ReportExportProcessImpl <=======================");

    }

    /** Method runs process */

    private synchronized void runProcesses() {

        while (running) {
            try {
                LOG.debug("ReportExportProcessImpl Export process started...");
                resetValues();
                runTask();

                // if we are done with all exports
                if (this.getExportState().isExportComplete()) {
                    LOG.debug("ReportExportProcessImpl all file Exports are completed!");
                }

            } catch (InterruptedException e) {
                LOG.debug("ReportExportProcessImpl Report Generate Process Interupted" + e);
                this.stopProcess();
            }

            notifyObservers();

        }

        this.stopProcess();

    }

    /** 
     * ReportExportProcessImpl
     * Reset state values 
     * */

    private void resetValues() {
        ReportExportState state = this.getExportState();
        state.setExportComplete(false);
        state.setRecordCounter(0);
        state.setRunning(true);
        state.setReportProcessStatus(ReportProcessStatus.RUNNING);
        
//        completed = false;
    }

    /** 
     * ReportExportProcessImpl
     * Initialize process 
     */
    private void initialize() {

        LOG.debug("initializing report export process...");
        this.setExportState(new ReportExportState());
        running = true;

        getExportState().setReportProcessStatus(ReportProcessStatus.RUNNING);
        getExportState().setRunning(running);
        getExportState().setExportComplete(false);
        observers = new Vector<ReportObserver>();
    }

    /** 
     * ReportExportProcessImpl
     * Stop process 
     */
    public synchronized void stopProcess() {
        LOG.debug("==>>>ReportExportProcessImipl stopped...");
        running = false;
        getExportState().setReportProcessStatus(ReportProcessStatus.STOPPED);
        getExportState().setRunning(false);
        
//        suspended = false;
    }

    /** Run tasks within process for ReportExportProcessImipl
     * @throws InterruptedException thrown if process interrupted 
     */
    private void runTask() throws InterruptedException {
        LOG.debug("starting ReportExportProcessImipl runTask()...");

        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        doNdfExport();
        doIngredientListExport();
        doWarningLabelExport();

        this.getExportState().setExportComplete(true);
        running = false;

//        ReportExportState state = this.getExportState();
//        state.setExportComplete(true);

    }

    /**
     * ReportExportProcessImpl
     * Executes NDF Report
     * @throws InterruptedException thrown if process interrupted 
     */
    private void doNdfExport() throws InterruptedException {
        LOG.debug("Exporting NDF Capture Report");

        this.getExportState().setReportProcessStatus(ReportProcessStatus.RUNNING);

        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        List<Long> list = new ArrayList<Long>();

        try {

            //Set start time for the REPORT_CAPTURE_START
            setDateValue(NationalSetting.REPORT_CAPTURE_START, getDateTime());
            ReportNdfCsvFile reportNdfCsvFile = new ReportNdfCsvFile();

            // Create the FILE in doNdfExport
            try {
                reportNdfCsvFile.createFile();
            } catch (Exception e) {
                LOG.error(ERROR + e);
            }

            // if in debug mode then SLEEP for SLEEP milliseconds to allow the local machine to process.
            if (debug) {
                Thread.sleep(SLEEP);
            }

            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);
            list = reportDomainCapability.getIds(ReportType.NDC_LIST_PRINT_TEMPLATE);
            transactionManager.commit(status);

            // do 10 at a time.

            List<Long> actionList = new ArrayList<Long>(FILE_SIZE);
            int i = 0;

            for (Long eplId : list) {
                actionList.add(eplId);
                i++;

                // ReportExportProcessImpl process arrayList
                if (actionList.size() >= FILE_SIZE) {
                    LOG.debug("Report Processing: " + i + OF + list.size());
                    def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    def.setTimeout(TIMEOUT);
                    status = transactionManager.getTransaction(def);
                    List<ReportCaptureNdfVo> rtList = reportDomainCapability.getCaptureNdfData(actionList);
                    transactionManager.commit(status);

                    // Attempt to print the rtList.
                    try {
                        reportNdfCsvFile.printNDC(rtList);
                    } catch (Exception e) {
                        LOG.error(ERROR + e);
                    }

                    // clealr the actionList
                    actionList.clear();
                }
            }

            if (actionList.size() > 0) {
                def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                def.setTimeout(TIMEOUT);
                status = transactionManager.getTransaction(def);
                List<ReportCaptureNdfVo> rtList = reportDomainCapability.getCaptureNdfData(actionList);
                transactionManager.commit(status);

                try {
                    reportNdfCsvFile.printNDC(rtList);
                } catch (Exception e) {
                    LOG.error(ERROR + e);
                }

                actionList.clear();
            }

            reportNdfCsvFile.closeExport();

        } catch (TransactionException e) {
            LOG.error(LIST_ERROR + e.getMessage());
            this.stopProcess();
        }

        //Set stop time
        setDateValue(NationalSetting.REPORT_CAPTURE_COMPLETE, getDateTime());

    }

    /** 
     * Generates Ingredient list Report
     * @throws InterruptedException 
     */
    private void doIngredientListExport() throws InterruptedException {
        LOG.debug("Exporting Ingredient List Report");

        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        int i = 0;

        try {

            //Set start time for the doIngredientListExport.
            setDateValue(NationalSetting.REPORT_INGREDIENT_START, getDateTime());

            if (debug) {
                Thread.sleep(SLEEP);
            }

            ReportListIngredientCsvFile reportListIngredientCsvFile = new ReportListIngredientCsvFile();

            try {
                reportListIngredientCsvFile.createFile();
            } catch (Exception e) {
                LOG.error("doIngredientListExport.Cound create List Ingredients file for export: " + e);
            }

            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);
            List<Long> list = reportDomainCapability.getIds(ReportType.LIST_INGREDIENTS);
            transactionManager.commit(status);

            // do 10 at a time.

            List<Long> actionList = new ArrayList<Long>(FILE_SIZE);

            for (Long eplId : list) {
                actionList.add(eplId);
                i++;

                if (actionList.size() >= FILE_SIZE) {
                    LOG.info("Ingredient Processing: " + i + OF + list.size());
                    def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    def.setTimeout(TIMEOUT);
                    status = transactionManager.getTransaction(def);
                    List<ReportProductVo> rtList = reportDomainCapability.getProductIngredientData(actionList);
                    transactionManager.commit(status);

                    try {
                        reportListIngredientCsvFile.printListIngredient(rtList);
                    } catch (Exception e) {
                        LOG.error("Cound not open Ingredient file for export: " + e);
                    }

                    actionList.clear();
                }
            }

            if (actionList.size() > 0) {
                def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                def.setTimeout(TIMEOUT);
                
                // set the status to DefaultTransactionDefinition
                status = transactionManager.getTransaction(def);
                List<ReportProductVo> rtList = reportDomainCapability.getProductIngredientData(actionList);
                transactionManager.commit(status);

                try {
                    reportListIngredientCsvFile.printListIngredient(rtList);
                } catch (Exception e) {
                    LOG.error("Cound not open Ingredient file for export2: " + e);
                }

                actionList.clear();
            }

            reportListIngredientCsvFile.closeExport();

            //Set end time
            setDateValue(NationalSetting.REPORT_INGREDIENT_COMPLETE, getDateTime());

        } catch (TransactionException e) {
            LOG.error(LIST_ERROR + e.getMessage());
            this.stopProcess();
        }

    }

    /**
     * Generates Warning Label Export
     * @throws InterruptedException 
     */
    private void doWarningLabelExport() throws InterruptedException {

        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;

        try {

            //Set end time
            setDateValue(NationalSetting.REPORT_WARNING_START, getDateTime());

            ReportWarningLableCsvFile reportWarningLableCsvFile = new ReportWarningLableCsvFile();

            try {
                reportWarningLableCsvFile.createFile();
            } catch (Exception e) {
                LOG.error("Cound not open Warning Label file for export: " + e);
            }

            if (debug) {
                Thread.sleep(SLEEP);
            }

            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);
            List<ReportProductVo> list = reportDomainCapability.getProductWarningLabelData();
            LOG.debug("Warning Label list size: " + list.size());
            transactionManager.commit(status);

            List<ReportProductVo> actionList = new ArrayList<ReportProductVo>(FILE_SIZE);
            int i = 0;

            for (ReportProductVo vo : list) {
                LOG.info("Loading: " + i + OF + list.size() + " which is " + " Product "
                    + vo.getVaProductName() + ":" + vo.getGcnSeqNo());
                actionList.add(vo);
                i++;

                if (actionList.size() >= FILE_SIZE) {

                    for (ReportProductVo rpVo : actionList) {
                        def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                        def.setTimeout(TIMEOUT);
                        status = transactionManager.getTransaction(def);
                        rpVo = domainService.getDrugReferenceCapability().retrieveWarningLabels(rpVo);
                        transactionManager.commit(status);
                    }

                    // attempt to print the warning label report.
                    try {
                        reportWarningLableCsvFile.printWarningLabelReport(actionList);
                    } catch (Exception e) {
                        LOG.error("Cound not write to Warning Label file for export: " + e);
                    }

                    actionList.clear();

                }
            }

            LOG.debug("Action List Size at print remaining: " + actionList.size());

            if (actionList.size() > 0) {
                LOG.debug("Entered Print Remaining: " + actionList.size());
                def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                def.setTimeout(TIMEOUT);
                status = transactionManager.getTransaction(def);

                for (ReportProductVo rpVo : actionList) {
                    def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    def.setTimeout(TIMEOUT);
                    status = transactionManager.getTransaction(def);
                    rpVo = domainService.getDrugReferenceCapability().retrieveWarningLabels(rpVo);
                    transactionManager.commit(status);
                }

                try {
                    reportWarningLableCsvFile.printWarningLabelReport(actionList);
                } catch (Exception e) {
                    LOG.error("Cound not create Warning Label file for export: " + e);
                }

                actionList.clear();

            }

            reportWarningLableCsvFile.closeExport();

        } catch (TransactionException e) {
            LOG.error(LIST_ERROR + e.getMessage());
            this.stopProcess();
        }

        //Set end time
        setDateValue(NationalSetting.REPORT_WARNING_COMPLETE, getDateTime());

    }

    /**
     * getUser
     * @return UserVo
     */
    private UserVo getUser() {
        
        // create the user for the ReportExportProcessImpl.
        UserVo user = new UserVo();
        user.setFirstName("FDBAuto");
        user.setLastName("AddProcess");
        user.setStationNumber("999");
        user.setUsername("FdbAutoAddProcess");
        user.setLocation("National");
        user.setId(USER_ID);

        /*  Add the NATIONAL_SERVICE_MANAGER role to the user*/
        user.addRole(Role.PSS_PPSN_MANAGER);

        return user;

    }

    /**
     * Obtains the current date/time to set the date start or stop date value for the ReportExportProcessImpl.
     * @return dateValue
     */
    private Date getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        Date dateValue = new Date();
        LOG.debug("getDateTime: " + dateFormat.format(dateValue));
        
        return dateValue;
    }

    /**
     * This is the ReportExportProcessImpl method to set the StartDate.
     * @param type type
     * @param date date
     */
    private void setDateValue(NationalSetting type, Date date) {

        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            TransactionStatus status = transactionManager.getTransaction(def);

            // retrieve the national settings
            List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

            for (NationalSettingVo vo : settingsList) {
                if (type.toString().equals(vo.getKeyName())) {
                    vo.setDateValue(date);
                    nationalSettingDomainCapability.update(vo, getUser());
                }
            }

            // commit
            transactionManager.commit(status);

        } catch (TransactionException e) {
            LOG.error("TransactionException, in setDateValue " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error("The Underlying cause is " + e.getCause().getMessage());
            }
        } catch (Exception e) {
            LOG.error("SetDateValue National Settings Exception" + type + ":" + e.getMessage());
        }

    }

    @Override
    public void registerObserver(ReportObserver pObserver) {
        observers.add(pObserver);

    }

    /**
     * This is the ReportExportProcessImpl method to removeObserver
     * @param pObserver The observer to remove.
     */
    @Override
    public void removeObserver(ReportObserver pObserver) {
        observers.remove(pObserver);

    }

    /**
     * This is the ReportExportProcessImpl method to notifyObservers
     */
    @Override
    public void notifyObservers() {

        // use the for loop to iterate through the observers.  This will update each observer in the list.
        for (int loopIndex = 0; loopIndex < observers.size(); loopIndex++) {
            ReportObserver observer = (ReportObserver) observers.get(loopIndex);
            observer.update(this.getExportState());
        }

    }

    /**
     * This is the ReportExportProcessImpl method to Get export state
     * @return the exportState
     */
    public ReportExportState getExportState() {
        return exportState;
    }

    /**
     * This is the ReportExportProcessImpl method to Set export state
     * @param exportState the exportState to set
     */
    public void setExportState(ReportExportState exportState) {
        this.exportState = exportState;
    }

    /**
     * getRecordCounter for ReportExportProcessImpl.
     * @return int
     */
    @Override
    public int getRecordCounter() {
        return 0;
    }

    /**
     * setReportProcessStatus for ReportExportProcessImpl.
     * @param reportProcessStatus ReportProcessStatus
     */
    @Override
    public void setReportProcessStatus(ReportProcessStatus reportProcessStatus) {
    }

    /**
     * getReportDomainCapability for ReportExportProcessImpl
     * @return reportDomainCapability ReportDomainCapability
     */
    public ReportDomainCapability getReportDomainCapability() {
        return reportDomainCapability;
    }

    /**
     * Sets reportDomainCapability for ReportExportProcessImpl
     * @param reportDomainCapability reportDomainCapability
     */
    public void setReportDomainCapability(ReportDomainCapability reportDomainCapability) {
        this.reportDomainCapability = reportDomainCapability;
    }

    /**
     * getNationalSettingDomainCapability for ReportExportProcessImpl
     * @return nationalSettingDomainCapability 
     */
    public NationalSettingDomainCapability getNationalSettingDomainCapability() {
        return nationalSettingDomainCapability;
    }

    /**
     * setNationalSettingDomainCapability for ReportExportProcessImpl
     * @param nationalSettingDomainCapability nationalSettingDomainCapability
     */
    public void setNationalSettingDomainCapability(NationalSettingDomainCapability nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;
    }

    /**
     * getDomainService for ReportExportProcessImpl
     * @return domainService
     */
    public DomainService getDomainService() {
        return domainService;
    }

    /**
     * setDomainService for ReportExportProcessImpl
     * @param domainService domainService
     */
    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }

    /**
     * getTransactionManager for ReportExportProcessImpl
     * @return transactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * setTransactionManager for ReportExportProcessImpl
     * @param transactionManager transactionManager
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
