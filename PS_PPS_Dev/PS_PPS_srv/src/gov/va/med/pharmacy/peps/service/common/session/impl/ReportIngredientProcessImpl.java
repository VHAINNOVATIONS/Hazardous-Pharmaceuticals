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
import gov.va.med.pharmacy.peps.service.common.session.ReportIngredientProcess;
import gov.va.med.pharmacy.peps.service.common.utility.ReportListIngredientCsvFile;


/** Generates the Ingredient list report in a background process. */

public class ReportIngredientProcessImpl implements ReportIngredientProcess, Runnable, ReportSubject {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(ReportIngredientProcessImpl.class);
    private static final String ERROR = "Cound not open Ingredient file for export: ";
    private static final int FILE_SIZE = 300;
    private static final Long USER_ID = 42L;
    private static final int TIMEOUT = 600;
    private static final int SLEEP = 5000;

    private volatile boolean running;
    private volatile ReportExportState exportState;
    private List<ReportObserver> observers;
    private boolean debug = false;

    private ReportDomainCapability reportDomainCapability;
    private NationalSettingDomainCapability nationalSettingDomainCapability;
    private DomainService domainService;
    private PlatformTransactionManager transactionManager;

//    private volatile boolean completed;
//    private boolean suspended;

    @Override
    public void run() {
        LOG.debug("=============> Starting run() in  ReportExportProcessImpl <====================");
        initialize();
        runProcesses();

        notifyObservers();
        LOG.debug("=============> Exiting run() in ReportExportProcessImpl <=======================");

    }

    /** Method runs process */

    private synchronized void runProcesses() {

        while (running) {
            try {
                LOG.debug("Export process started...");
                resetValues();
                runTask();

                // if we are done with all exports
                if (this.getExportState().isExportComplete()) {
                    LOG.debug("all file Exports are completed!");
                }

            } catch (InterruptedException e) {
                LOG.debug("Report Generate Process Interupted" + e);
                this.stopProcess();
            }

            notifyObservers();

        }

        this.stopProcess();

    }

    /** Reset state values */

    private void resetValues() {
        ReportExportState state = this.getExportState();
        state.setExportComplete(false);
        state.setRecordCounter(0);
        state.setRunning(true);
        state.setRecordCounter(0);
        state.setRecordTotal(0);
        state.setReportProcessStatus(ReportProcessStatus.RUNNING);

//        completed = false;
    }

    /** Initialize process */

    private void initialize() {

        LOG.debug("initializing report export process...");
        this.setExportState(new ReportExportState());
        running = true;
        getExportState().setReportProcessStatus(ReportProcessStatus.RUNNING);
        getExportState().setRunning(running);
        getExportState().setExportComplete(false);
        observers = new Vector<ReportObserver>();

//        suspended = false;

    }

    /** Stop process */

    public synchronized void stopProcess() {
        LOG.debug("==>>>Report Export process stopped...");
        running = false;
        getExportState().setReportProcessStatus(ReportProcessStatus.STOPPED);
        getExportState().setRunning(false);

//        suspended = false;
    }

    /** Run tasks within process 
     * @throws InterruptedException thrown if process interrupted 
     */
    private void runTask() throws InterruptedException {
        LOG.debug("starting runTask()...");

        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        doIngredientListExport();

        this.getExportState().setExportComplete(true);
        running = false;

    }

    /** 
     * Generates Ingredient list Report
     * @throws InterruptedException 
     */
    private void doIngredientListExport() throws InterruptedException {
        LOG.debug("Exporting Ingredient List Report");

        ReportExportState state = this.getExportState();
        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        int i = 0;

        try {

            //Set start time
            setDateValue(NationalSetting.REPORT_INGREDIENT_START, getDateTime());

            if (debug) {
                Thread.sleep(SLEEP);
            }

            ReportListIngredientCsvFile reportListIngredientCsvFile = new ReportListIngredientCsvFile();

            try {
                reportListIngredientCsvFile.createFile();
            } catch (Exception e) {
                LOG.error("Cound create List Ingredients file for export: " + e);
            }

            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);
            List<Long> list = reportDomainCapability.getIds(ReportType.LIST_INGREDIENTS);
            state.setRecordTotal(list.size());
            transactionManager.commit(status);

            // Notify observer
            notifyObservers();

            // do 10 at a time.

            List<Long> actionList = new ArrayList<Long>(FILE_SIZE);

            for (Long eplId : list) {
                actionList.add(eplId);
                i++;

                if (actionList.size() >= FILE_SIZE) {
                    LOG.info("Ingredient Processing: " + i + " of " + list.size());
                    def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    def.setTimeout(TIMEOUT);
                    status = transactionManager.getTransaction(def);
                    List<ReportProductVo> rtList = reportDomainCapability.getProductIngredientData(actionList);
                    transactionManager.commit(status);

                    // print the lingredient list
                    try {
                        reportListIngredientCsvFile.printListIngredient(rtList);
                    } catch (Exception e) {
                        LOG.error(ERROR + e);
                    }

                    // the action list needs to be cleared for the next run through the loop
                    actionList.clear();

                    // Update record count
                    state.setRecordCounter(i);
                    notifyObservers();
                }
            }

            if (actionList.size() > 0) {
                def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                def.setTimeout(TIMEOUT);
                status = transactionManager.getTransaction(def);
                List<ReportProductVo> rtList = reportDomainCapability.getProductIngredientData(actionList);
                transactionManager.commit(status);

                try {
                    reportListIngredientCsvFile.printListIngredient(rtList);
                } catch (Exception e) {
                    LOG.error(ERROR + e);
                }

                actionList.clear();

                // Update record count
                state.setRecordCounter(i);
                notifyObservers();
            }

            reportListIngredientCsvFile.closeExport();

            //Set end time
            setDateValue(NationalSetting.REPORT_INGREDIENT_COMPLETE, getDateTime());

        } catch (TransactionException e) {
            LOG.error("FAILED TO retrieve List. Error Message is " + e.getMessage());
            this.stopProcess();
        }

    }

    /**
     * getUser for ReportIngredientProcessImpl
     * @return UserVo
     */
    private UserVo getUser() {
        UserVo user = new UserVo();
        user.setFirstName("FDBAuto");
        user.setLastName("AddProcess");
        user.setStationNumber("999");
        user.setUsername("FdbAutoAddProcess");
        user.setLocation("National");
        user.setId(USER_ID);

        /*NATIONAL_SERVICE_MANAGER*/
        user.addRole(Role.PSS_PPSN_MANAGER);

        return user;

    }

    /**
     * Obtains the current date/time to set the date start or stop date value.
     * @return dateValue
     */
    private Date getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        Date dateValue = new Date();

        try {

            LOG.debug("Date/Time: " + dateFormat.format(dateValue));

        } catch (Exception e) {
            LOG.error("Exception parsing Start Date/Time: " + e.getMessage());
        }

//        transactionManager.commit(status);

        return dateValue;
    }

    /**
     * setStartDate.
     * @param type type
     * @param date date
     */
    private void setDateValue(NationalSetting type, Date date) {

        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            TransactionStatus status = transactionManager.getTransaction(def);

            List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

            for (NationalSettingVo vo : settingsList) {
                if (type.toString().equals(vo.getKeyName())) {
                    vo.setDateValue(date);
                    nationalSettingDomainCapability.update(vo, getUser());
                }
            }

            transactionManager.commit(status);

        } catch (TransactionException e) {
            LOG.error("TransactionException, in setStartDate()  " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error("The Underlying cause is " + e.getCause().getMessage());
            }
        } catch (Exception e) {
            LOG.error("Exception getting National Settings for " + type + ":" + e.getMessage());
        }

    }

    @Override
    public void registerObserver(ReportObserver pObserver) {
        observers.add(pObserver);

    }

    @Override
    public void removeObserver(ReportObserver pObserver) {
        observers.remove(pObserver);

    }

    @Override
    public void notifyObservers() {

        for (int loopIndex = 0; loopIndex < observers.size(); loopIndex++) {
            ReportObserver observer = (ReportObserver) observers.get(loopIndex);
            observer.update(this.getExportState());
        }

    }

    /**
     * Get export state
     * @return the exportState
     */
    public ReportExportState getExportState() {
        return exportState;
    }

    /**
     * Set export state
     * @param exportState the exportState to set
     */
    public void setExportState(ReportExportState exportState) {
        this.exportState = exportState;
    }

    @Override
    public int getRecordCounter() {
        return 0;
    }

    @Override
    public void setReportProcessStatus(ReportProcessStatus reportProcessStatus) {
    }

    /**
     * getReportDomainCapability
     * @return reportDomainCapability ReportDomainCapability
     */
    public ReportDomainCapability getReportDomainCapability() {
        return reportDomainCapability;
    }

    /**
     * Sets reportDomainCapability
     * @param reportDomainCapability reportDomainCapability
     */
    public void setReportDomainCapability(ReportDomainCapability reportDomainCapability) {
        this.reportDomainCapability = reportDomainCapability;
    }

    /**
     * getNationalSettingDomainCapability
     * @return nationalSettingDomainCapability 
     */
    public NationalSettingDomainCapability getNationalSettingDomainCapability() {
        return nationalSettingDomainCapability;
    }

    /**
     * setNationalSettingDomainCapability
     * @param nationalSettingDomainCapability nationalSettingDomainCapability
     */
    public void setNationalSettingDomainCapability(NationalSettingDomainCapability nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;
    }

    /**
     * getDomainService
     * @return domainService
     */
    public DomainService getDomainService() {
        return domainService;
    }

    /**
     * setDomainService
     * @param domainService domainService
     */
    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }

    /**
     * getTransactionManager
     * @return transactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * This method is used by Spring Injection to set the TransactionManager.
     * @param transactionManager transactionManager
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
