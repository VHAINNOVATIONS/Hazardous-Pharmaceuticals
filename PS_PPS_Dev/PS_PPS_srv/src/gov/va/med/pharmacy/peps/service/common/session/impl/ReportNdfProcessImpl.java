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
import gov.va.med.pharmacy.peps.service.common.session.ReportNdfProcess;
import gov.va.med.pharmacy.peps.service.common.utility.ReportNdfCsvFile;


/** Generates 3 reports in a background process. */

public class ReportNdfProcessImpl implements ReportNdfProcess, Runnable, ReportSubject {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(ReportNdfProcessImpl.class);
    private static final int FILE_SIZE = 300;
    private static final Long USER_ID = 42L;
    private static final int TIMEOUT = 600;
    private static final int SLEEP = 5000;
    private static final String ERROR = "Cound not open NDC file for export: ";

    // ReportNDFProcessImpl values
    private volatile boolean running;
    private volatile ReportExportState exportState;
    private List<ReportObserver> observers;
    private boolean debug = false;

    // ReportNDFProcessImpl values
    private ReportDomainCapability reportDomainCapability;
    private NationalSettingDomainCapability nationalSettingDomainCapability;
    private DomainService domainService;
    private PlatformTransactionManager transactionManager;

    /**
     * ReportNDFProcessImpl run() method
     */
    @Override
    public void run() {
        LOG.debug("=============> Starting run() in  ReportNdfProcessImpl <====================");
        initialize();
        runProcesses();

        notifyObservers();
        LOG.debug("=============> Exiting run() in ReportNdfProcessImpl <=======================");

    }

    /** 
     * This is the runProcesses method 
     */
    private synchronized void runProcesses() {

        while (running) {
            try {
                LOG.debug("ReportNdfProcessImplExport process started...");
                resetValues();
                runTask();

                // if we are done with all exports then finish up
                if (this.getExportState().isExportComplete()) {
                    LOG.debug("all file Exports are completed!");
                }
            } catch (InterruptedException e) {
                LOG.debug("ReportNdfProcessImpl. Report Generate Process Interupted" + e);
                this.stopProcess();
            }

            notifyObservers();
        }

        this.stopProcess();
    }

    /** Reset state values for ReportNdfProcessImpl */

    private void resetValues() {
        ReportExportState state = this.getExportState();
        state.setExportComplete(false);
        state.setRecordCounter(0);
        state.setRunning(true);
        state.setReportProcessStatus(ReportProcessStatus.RUNNING);

//        completed = false;
    }

    /** Initialize process for ReportNdfProcessImpl */

    private void initialize() {

        LOG.debug("initializing report export process...");
        this.setExportState(new ReportExportState());
        running = true;
        getExportState().setReportProcessStatus(ReportProcessStatus.RUNNING);
        getExportState().setRunning(running);
        getExportState().setExportComplete(false);
        getExportState().setRecordCounter(0);
        getExportState().setRecordTotal(0);
        observers = new Vector<ReportObserver>();

//        suspended = false;
    }

    /** Stop process for ReportNdfProcessImpl */

    public synchronized void stopProcess() {
        LOG.debug("==>>>Report Export process stopped...");
        running = false;
        getExportState().setReportProcessStatus(ReportProcessStatus.STOPPED);
        getExportState().setRunning(false);

//        suspended = false;
    }

    /** Run tasks within process for the ReportNdfProcessImpl file.
     * @throws InterruptedException thrown if process interrupted 
     */
    private void runTask() throws InterruptedException {
        LOG.debug("starting ReportNdfProcessImpl runTask()...");

        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        doNdfExport();

        this.getExportState().setExportComplete(true);
        running = false;

//        ReportExportState state = this.getExportState();
//        state.setExportComplete(true);

    }

    /**
     * Executes NDF Report
     * @throws InterruptedException thrown if process interrupted 
     */
    private void doNdfExport() throws InterruptedException {
        LOG.debug("Exporting NDF Capture Report");

        this.getExportState().setReportProcessStatus(ReportProcessStatus.RUNNING);

        ReportExportState state = this.getExportState();
        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        List<Long> list = new ArrayList<Long>();

        try {

            //Set start time
            setDateValue(NationalSetting.REPORT_CAPTURE_START, getDateTime());
            ReportNdfCsvFile reportNdfCsvFile = new ReportNdfCsvFile();

            try {
                reportNdfCsvFile.createFile();
            } catch (Exception e) {
                LOG.error(ERROR + e);
            }

            if (debug) {
                Thread.sleep(SLEEP);
            }

            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);
            list = reportDomainCapability.getIds(ReportType.NDC_LIST_PRINT_TEMPLATE);
            state.setRecordTotal(list.size());
            transactionManager.commit(status);

            // Notify observer
            notifyObservers();

            // do 10 at a time.

            List<Long> actionList = new ArrayList<Long>(FILE_SIZE);
            int i = 0;

            for (Long eplId : list) {
                actionList.add(eplId);
                i++;

                if (actionList.size() >= FILE_SIZE) {
                    LOG.debug("Report Processing: " + i + " of " + list.size());
                    def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    def.setTimeout(TIMEOUT);
                    status = transactionManager.getTransaction(def);
                    List<ReportCaptureNdfVo> rtList = reportDomainCapability.getCaptureNdfData(actionList);
                    transactionManager.commit(status);

                    // Now call the report NDC File to print the list of NDCS
                    try {
                        reportNdfCsvFile.printNDC(rtList);
                    } catch (Exception e) {
                        LOG.error("Cound not open NDC file for export: " + e);
                    }

                    // doNdfExport
                    actionList.clear();

                    // Update record count by incrementing the value.                  
                    state.setRecordCounter(i);
                    notifyObservers();
                }
            }

            if (actionList.size() > 0) {
                def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                def.setTimeout(TIMEOUT);
                status = transactionManager.getTransaction(def);
                List<ReportCaptureNdfVo> rtList = reportDomainCapability.getCaptureNdfData(actionList);
                transactionManager.commit(status);

                // doNdfExport
                try {
                    reportNdfCsvFile.printNDC(rtList);
                } catch (Exception e) {
                    LOG.error("Cound not open NDC file for export: " + e);
                }

                actionList.clear();

                // Update record count                    
                state.setRecordCounter(i);
                notifyObservers();
            }

            reportNdfCsvFile.closeExport();

        } catch (TransactionException e) {
            LOG.error("FAILED TO retrieve List. Error Message is " + e.getMessage());
            this.stopProcess();
        }

        //Set stop time
        setDateValue(NationalSetting.REPORT_CAPTURE_COMPLETE, getDateTime());

    }

    /**
     * getUser for ReportNdfProcessImpl
     * @return UserVo
     */
    private UserVo getUser() {
        
        // Create the User
        UserVo user = new UserVo();
        user.setFirstName("FDBAuto");
        user.setLastName("AddProcess");
        user.setStationNumber("999");
        user.setUsername("FdbAutoAddProcess");
        user.setLocation("National");
        user.setId(USER_ID);

        /*set the Role of NATIONAL_SERVICE_MANAGER*/
        user.addRole(Role.PSS_PPSN_MANAGER);

        return user;

    }

    /**
     * Obtains the current date/time to set the date start or stop date value for ReportNdfProcessImpl.
     * @return dateValue
     */
    private Date getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        Date dateValue = new Date();

        try {

            LOG.debug("ReportNdfProcessImpl:getDateTime: " + dateFormat.format(dateValue));

        } catch (Exception e) {
            LOG.error("Exception parsing Start Date/Time: " + e.getMessage());
        }

        return dateValue;
    }

    /**
     * The setDateValue method.
     * @param type type
     * @param date date
     */
    private void setDateValue(NationalSetting type, Date date) {

        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            TransactionStatus status = transactionManager.getTransaction(def);
            List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

            // iterate through all the nationalsettings looking for the type
            for (NationalSettingVo vo : settingsList) {
                if (type.toString().equals(vo.getKeyName())) {
                    vo.setDateValue(date);
                    nationalSettingDomainCapability.update(vo, getUser());
                }
            }

            transactionManager.commit(status);

        } catch (TransactionException e) {
            LOG.error("TransactionException, in setDateValue()  " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error("The Underlying cause is " + e.getCause().getMessage());
            }
        } catch (Exception e) {
            LOG.error("Exception getting setDateValue " + type + ":" + e.getMessage());
        }

    }

    /**
     * registerObserver in ReportNdfProcessImpl
     * @param pObserver pObserver
     */
    @Override
    public void registerObserver(ReportObserver pObserver) {
        observers.add(pObserver);

    }

    /**
     * removeObserver in ReportNdfProcessImpl
     *  @param pObserver pObserver
     */
    @Override
    public void removeObserver(ReportObserver pObserver) {
        observers.remove(pObserver);

    }

    /**
     * notifyObservers in ReportNdfProcessImpl
     */
    @Override
    public void notifyObservers() {

        for (int loopIndex = 0; loopIndex < observers.size(); loopIndex++) {
            ReportObserver observer = (ReportObserver) observers.get(loopIndex);
            observer.update(this.getExportState());
        }

    }

    /**
     * Get export state  in ReportNdfProcessImpl
     * @return the exportState
     */
    public ReportExportState getExportState() {
        return exportState;
    }

    /**
     * Set export state in ReportNdfProcessImpl
     * @param exportState the exportState to set
     */
    public void setExportState(ReportExportState exportState) {
        this.exportState = exportState;
    }

    /**
     * getRecordCounter in ReportNdfProcessImpl.
     * @return zero
     */
    @Override
    public int getRecordCounter() {
        return 0;
    }

    @Override
    public void setReportProcessStatus(ReportProcessStatus reportProcessStatus) {
    }

    /**
     * getReportDomainCapability in ReportNdfProcessImpl.
     * @return reportDomainCapability ReportDomainCapability
     */
    public ReportDomainCapability getReportDomainCapability() {
        return reportDomainCapability;
    }

    /**
     * Sets reportDomainCapability in ReportNdfProcessImpl.
     * @param reportDomainCapability reportDomainCapability
     */
    public void setReportDomainCapability(ReportDomainCapability reportDomainCapability) {
        this.reportDomainCapability = reportDomainCapability;
    }

    /**
     * getNationalSettingDomainCapability in ReportNdfProcessImpl.
     * @return nationalSettingDomainCapability 
     */
    public NationalSettingDomainCapability getNationalSettingDomainCapability() {
        return nationalSettingDomainCapability;
    }

    /**
     * setNationalSettingDomainCapability in ReportNdfProcessImpl.
     * @param nationalSettingDomainCapability nationalSettingDomainCapability
     */
    public void setNationalSettingDomainCapability(NationalSettingDomainCapability nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;
    }

    /**
     * getDomainService in ReportNdfProcessImpl.
     * @return domainService
     */
    public DomainService getDomainService() {
        return domainService;
    }

    /**
     * setDomainService in ReportNdfProcessImpl.
     * @param domainService domainService
     */
    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }

    /**
     * getTransactionManager in ReportNdfProcessImpl.
     * @return transactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * setTransactionManager in ReportNdfProcessImpl.
     * @param transactionManager transactionManager
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
