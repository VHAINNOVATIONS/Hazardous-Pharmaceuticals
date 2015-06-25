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
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ReportDomainCapability;
import gov.va.med.pharmacy.peps.service.common.reports.ReportExportState;
import gov.va.med.pharmacy.peps.service.common.reports.ReportObserver;
import gov.va.med.pharmacy.peps.service.common.reports.ReportProcessStatus;
import gov.va.med.pharmacy.peps.service.common.reports.ReportSubject;
import gov.va.med.pharmacy.peps.service.common.session.DomainService;
import gov.va.med.pharmacy.peps.service.common.session.ReportWarningProcess;
import gov.va.med.pharmacy.peps.service.common.utility.ReportWarningLableCsvFile;


/** Generates 3 reports in a background process. */

public class ReportWarningProcessImpl implements ReportWarningProcess, Runnable, ReportSubject {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(ReportWarningProcessImpl.class);
    private static final int TIMEOUT = 600;
    private static final int SLEEP = 5000;

    private static final int FILE_SIZE = 300;
    private static final Long USER_ID = 42L;
    
    private volatile boolean running;
    private volatile ReportExportState exportState;
    private List<ReportObserver> observers;
    private boolean debug = false;

    private ReportDomainCapability reportDomainCapability;
    private NationalSettingDomainCapability nationalSettingDomainCapability;
    private PlatformTransactionManager transactionManager;
    private DomainService domainService;
    
//    private volatile boolean completed;
//    private boolean suspended;

    @Override
    public void run() {
        LOG.debug("==============> Starting run() in  ReportExportProcessImpl <====================");
        initialize();
        runProcesses();

        notifyObservers();
        LOG.debug("==============> Exiting run() in ReportExportProcessImpl <=======================");

    }

    /** Method runs process */

    private synchronized void runProcesses() {

        while (running) {
            try {
                LOG.debug("The Export process started...");
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
            
            // invoke the observers
            notifyObservers();
        }

        this.stopProcess();

    }

    /** The method will Reset state values */

    private void resetValues() {
        ReportExportState state = this.getExportState();
        state.setExportComplete(false);
        state.setRecordCounter(0);
        state.setRunning(true);
        state.setReportProcessStatus(ReportProcessStatus.RUNNING);
    }

    /** The method is the ReportWarningProcessImpl method to Initialize process */

    private void initialize() {

        LOG.debug("initializing report export process...");
        this.setExportState(new ReportExportState());
        running = true;
        
        // set the export status values for this initialization.
        getExportState().setReportProcessStatus(ReportProcessStatus.RUNNING);
        getExportState().setRunning(running);
        getExportState().setExportComplete(false);
        getExportState().setRecordCounter(0);
        getExportState().setRecordTotal(0);
        observers = new Vector<ReportObserver>();
        
//        suspended = false;
    }

    /** The method is the ReportWarningProcessImpl method to Stop process */

    public synchronized void stopProcess() {
        LOG.debug("==>>>Report Export process stopped...");
        running = false;      
        getExportState().setReportProcessStatus(ReportProcessStatus.STOPPED);
        getExportState().setRunning(false);
        
//        suspended = false;
    }

    /** The method is the ReportWarningProcessImpl method to Run tasks within process 
     * @throws InterruptedException thrown if process interrupted 
     */
    private void runTask() throws InterruptedException {
        LOG.debug("starting runTask()...");

        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        doWarningLabelExport();

        this.getExportState().setExportComplete(true);
        running = false;

    }

    /**
     * The method is the ReportWarningProcessImpl method to Generates Warning Label Export
     * @throws InterruptedException 
     */
    private void doWarningLabelExport() throws InterruptedException {

        ReportExportState state = this.getExportState();        
        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        

        try {

            //Set start time
            setDateValue(NationalSetting.REPORT_WARNING_START, getDateTime());

            ReportWarningLableCsvFile reportWarningLableCsvFile = new ReportWarningLableCsvFile();

            try {
                reportWarningLableCsvFile.createFile();
            } catch (Exception e) {
                LOG.error("ReportWarningProcessImpl for export: " + e);
            }

            if (debug) {
                Thread.sleep(SLEEP);
            }

            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);
            List<ReportProductVo> list = reportDomainCapability.getProductWarningLabelData();
            LOG.debug("ReportWarningProcessImpl Warning Label list size: " + list.size());
            state.setRecordTotal(list.size());
            transactionManager.commit(status);
            
            // Notify observer
            notifyObservers();

            List<ReportProductVo> actionList = new ArrayList<ReportProductVo>(FILE_SIZE);
            int i = 0;

            for (ReportProductVo vo : list) {
                LOG.info("Loading: " + i + " of " + list.size() + " which is Product "
                    + vo.getVaProductName() + ":" + vo.getGcnSeqNo());
                actionList.add(vo);
                i++;

                if (actionList.size() >= FILE_SIZE) {

                    // Do the actionList while the ReportWarningProcessImpl file.
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
                        LOG.error("Cound not write to Warning Label file for export: " + e);
                    }

                    actionList.clear();
                    
                    // Update record count                    
                    state.setRecordCounter(i);
                    notifyObservers();

                }                
            }

            LOG.debug("Action List Size at print remaining: " + actionList.size());

            // Process the action list if it has a size larger than zero.
            if (actionList.size() > 0) {
                LOG.debug("Entered Print Remaining: " + actionList.size());
                def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                def.setTimeout(TIMEOUT);
                status = transactionManager.getTransaction(def);

                // process the action list to retrieve the warning labels.
                for (ReportProductVo rpVo : actionList) {
                    def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    def.setTimeout(TIMEOUT);
                    status = transactionManager.getTransaction(def);
                    rpVo = domainService.getDrugReferenceCapability().retrieveWarningLabels(rpVo);
                    transactionManager.commit(status);
                }

                // Print the warning label report.
                try {
                    reportWarningLableCsvFile.printWarningLabelReport(actionList);
                } catch (Exception e) {
                    LOG.error("Cound not create Warning Label file for export: " + e);
                }

                actionList.clear();
                
                // Update record count
                state.setRecordCounter(i);
                notifyObservers();

            }

            reportWarningLableCsvFile.closeExport();

        } catch (TransactionException e) {
            LOG.error("FAILED TO retrieve List. Error Message is " + e.getMessage());
            this.stopProcess();
        }

        //Set end time for the Report Warning Complete label when it finisheds
        setDateValue(NationalSetting.REPORT_WARNING_COMPLETE, getDateTime());

    }

    /**
     * getUser for ReportWarningprocessImpl
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

        /*NATIONAL_SERVICE_MANAGER is the Role to Add*/
        user.addRole(Role.PSS_PPSN_MANAGER);

        return user;

    }

    /**
     * Obtains the current date/time to set the date start or stop date value.
     * 
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

        return dateValue;
    }

    /**
     * setDateValue for ReportWarningProcessImpl
     * @param type type
     * @param date date
     */
    private void setDateValue(NationalSetting type, Date date) {

        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            TransactionStatus status = transactionManager.getTransaction(def);

            // retrieve the list of National Settings to get the one with the correct name.
            List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

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
            LOG.error("setDateValue Exception getting National Settings for " + type + ":" + e.getMessage());
        }

    }

    /**
     * registerObserver for ReportWarningProcessImpl
     * @param pObserver The observer to add
     */
    @Override
    public void registerObserver(ReportObserver pObserver) {
        observers.add(pObserver);

    }

    /**
     * removeObserver for ReportWarningProcessImpl
     * @param pObserver The observer to remove
     */
    @Override
    public void removeObserver(ReportObserver pObserver) {
        observers.remove(pObserver);

    }

    /**
     * notifyObservers for ReportWarningProcessImpl
     */
    @Override
    public void notifyObservers() {

        // Update each of the observers in the list.
        for (int loopIndex = 0; loopIndex < observers.size(); loopIndex++) {
            ReportObserver observer = (ReportObserver) observers.get(loopIndex);
            observer.update(this.getExportState());
        }

    }

    /**
     * Get export state for ReportWarningProcessImpl
     * @return the exportState
     */
    public ReportExportState getExportState() {
        return exportState;
    }

    /**
     * Set export state for ReportWarningPrcessImpl
     * @param exportState the exportState to set
     */
    public void setExportState(ReportExportState exportState) {
        this.exportState = exportState;
    }

    /**
     * getRecordCounter for ReportWarningProcessImpl
     * @return zero
     */
    @Override
    public int getRecordCounter() {
        return 0;
    }

    
    /**
     * setReportProcessStatus for ReportWarningProcessImpl
     * @param reportProcessStatus reportProcessStatus
     */
    @Override
    public void setReportProcessStatus(ReportProcessStatus reportProcessStatus) {
    }

    /**
     * getReportDomainCapability for ReportWarningProcessImpl
     * @return reportDomainCapability ReportDomainCapability
     */
    public ReportDomainCapability getReportDomainCapability() {
        return reportDomainCapability;
    }

    /**
     * Sets reportDomainCapability for ReportWarningProcessImpl
     * @param reportDomainCapability reportDomainCapability
     */
    public void setReportDomainCapability(ReportDomainCapability reportDomainCapability) {
        this.reportDomainCapability = reportDomainCapability;
    }

    /**
     * getNationalSettingDomainCapability for ReportWarningProcessImpl
     * @return nationalSettingDomainCapability 
     */
    public NationalSettingDomainCapability getNationalSettingDomainCapability() {
        return nationalSettingDomainCapability;
    }

    /**
     * setNationalSettingDomainCapability for ReportWarningProcessImpl
     * @param nationalSettingDomainCapability nationalSettingDomainCapability
     */
    public void setNationalSettingDomainCapability(NationalSettingDomainCapability nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;
    }

    /**
     * getDomainService for ReportWarningProcessImpl
     * @return domainService
     */
    public DomainService getDomainService() {
        return domainService;
    }

    /**
     * setDomainService for ReportWarningProcessImpl
     * @param domainService domainService
     */
    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }

    /**
     * getTransactionManager for ReportWarningProcessImpl
     * @return transactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * setTransactionManager for ReportWarningProcessImpl
     * @param transactionManager transactionManager
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
