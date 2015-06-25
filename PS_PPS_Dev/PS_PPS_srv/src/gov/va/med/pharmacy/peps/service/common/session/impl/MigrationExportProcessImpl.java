/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainType;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainVo;
import gov.va.med.pharmacy.peps.service.common.migration.ExportCSVFileData;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessStatus;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationExportState;
import gov.va.med.pharmacy.peps.service.common.migration.process.Observer;
import gov.va.med.pharmacy.peps.service.common.migration.process.Subject;
import gov.va.med.pharmacy.peps.service.common.session.FdbMappingService;
import gov.va.med.pharmacy.peps.service.common.session.MigrationCSVService;
import gov.va.med.pharmacy.peps.service.common.session.MigrationExportProcess;
import gov.va.med.pharmacy.peps.service.common.utility.DomainMappingCsvFile;


/**
 * 
 * MigrationExportProcessImpl
 */
public class MigrationExportProcessImpl implements MigrationExportProcess,
        Runnable, Subject {

    private static final Logger LOG = org.apache.log4j.Logger
            .getLogger(MigrationExportProcessImpl.class);
    private volatile boolean suspended;
    private volatile boolean running;
    private volatile boolean completed;
    private int recordCounter = 0;

    private volatile MigrationExportState exportState;
    private PlatformTransactionManager transactionManager;
    private MigrationCSVService migrationCSVService;
    private FdbMappingService fdbMappingService;
    private List<Observer> observers;

    private boolean debug = false;

    /**
     * Run method
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        LOG.debug("=============> Starting run() in  MigrationExportProcess <====================");
        initialize();
        runProcesses();
        notifyObservers();

        LOG.debug("=============> Exiting run() in MigrationExportProcess <=======================");
    }

    /**
     * intialize process.
     */
    private void initialize() {
        LOG.debug("initializing migration export process...");
        this.setExportState(new MigrationExportState());
        suspended = false;
        running = true;
        migrationCSVService.setExportState(this.getExportState());
        getExportState().setStatus(ProcessStatus.RUNNING);
        getExportState().setRunning(running);
        getExportState().setExportComplete(false);
        observers = new Vector<Observer>();

    }
    
    /**
     * runProcesses
     */
    private synchronized void runProcesses() {
        LOG.debug("Export process started...");
        
        while (running) {
            try {
                LOG.debug("Export process started...");
                resetValues();
                runExportTask(ExportCSVFileData.DOMAIN_MAPPING);
                resetValues();
                runExportTask(ExportCSVFileData.NDC);
                resetValues();
                runExportTask(ExportCSVFileData.ORDERABLE_ITEMS);
                resetValues();
                runExportTask(ExportCSVFileData.PRODUCT);

                // if we are done with all exports
                if (this.getExportState().isExportComplete()) {
                    LOG.debug("all file Exports are completed!");
                }
                
                notifyObservers();
            } catch (InterruptedException e) {
                LOG.error("Interrupted Exception occured " + e.getMessage());
            }
            
            this.stopProcess();
        }
    }

    /**
     * resets all variables
     */
    private void resetValues() {
        completed = false;
        MigrationExportState state = this.getExportState();
        state.setNdcExportComplete(false);
        state.setOiExportComplete(false);
        state.setProductExportComplete(false);
        state.setExportComplete(false);
        state.setFailureCounter(0);
        state.setRecordCounter(0);
        state.setRunning(true);
        state.setStatus(ProcessStatus.RUNNING);
        migrationCSVService.resetExportValues();

    }

    /**
     * runExportTask
     * 
     * @param pExportData ExportCSVFileData
     * @throws InterruptedException exception
     */
    private void runExportTask(ExportCSVFileData pExportData) throws InterruptedException {
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        switch (pExportData) {

//            case DOMAIN_MAPPING:
//                doDomainMappingExport();
//                break;
            case NDC:
                doNDCExport();
                break;
            case ORDERABLE_ITEMS:
                doOrderableItemsExport();
                break;
            case PRODUCT:
                doProductExport();
                this.getExportState().setExportComplete(true);
                break;
                
            default:
                break;     
        }
    }

    /**
     * doDomainMappingExport
     *
     */
    private void doDomainMappingExport() {
        LOG.debug("running domain mapping export");
        
        List<FdbDomainVo> fdbDrugClassList = fdbMappingService.getFdbDomains(FdbDomainType.DRUG_CLASS, null);
        List<FdbDomainVo> fdbDrugIngredientList = fdbMappingService.getFdbDomains(FdbDomainType.DRUG_INGREDIENT, null);
        List<FdbDomainVo> fdbDrugUnitList = fdbMappingService.getFdbDomains(FdbDomainType.DRUG_UNIT, null);
        List<FdbDomainVo> fdbDosageFormList = fdbMappingService.getFdbDomains(FdbDomainType.DOSAGE_FORM, null);
        List<FdbDomainVo> fdbGenericNameList = fdbMappingService.getFdbDomains(FdbDomainType.GENERIC_NAME, null);

        DomainMappingCsvFile domainMappingCsvFile = new DomainMappingCsvFile();

        try {
            domainMappingCsvFile.createFile();
        } catch (Exception e) {
            LOG.error("Could not open Domain Mapping file for export: " + e);
        }

        try {
            domainMappingCsvFile.printDomainMapping(fdbDrugClassList, FdbDomainType.DRUG_CLASS,
                fdbMappingService.getEplTerms(FdbDomainType.DRUG_CLASS));
            domainMappingCsvFile.printDomainMapping(fdbDrugIngredientList, FdbDomainType.DRUG_INGREDIENT,
                fdbMappingService.getEplTerms(FdbDomainType.DRUG_INGREDIENT));
            domainMappingCsvFile.printDomainMapping(fdbDrugUnitList, FdbDomainType.DRUG_UNIT,
                fdbMappingService.getEplTerms(FdbDomainType.DRUG_UNIT));
            domainMappingCsvFile.printDomainMapping(fdbDosageFormList, FdbDomainType.DOSAGE_FORM,
                fdbMappingService.getEplTerms(FdbDomainType.DOSAGE_FORM));
            domainMappingCsvFile.printDomainMapping(fdbGenericNameList, FdbDomainType.GENERIC_NAME,
                fdbMappingService.getEplTerms(FdbDomainType.GENERIC_NAME));
        } catch (Exception e) {
            LOG.error("Problem writing Fdb Domain Mapping list: " + e);
        }

        domainMappingCsvFile.closeExport();
        
    }

    /**
     * Strats the Product Export
     * 
     * @throws InterruptedException exception
     */
    private void doProductExport() throws InterruptedException {
        LOG.debug("running Product export");
        this.getExportState().setStatus(ProcessStatus.RUNNING);
        this.getExportState().setExportData(ExportCSVFileData.PRODUCT);

        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        List<Long> list = new ArrayList<Long>();
        
        try {
            def = new DefaultTransactionDefinition(
                    TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(PPSConstants.I200);
            status = transactionManager.getTransaction(def);
            list = migrationCSVService.getIds(EntityType.PRODUCT);
            LOG.debug("Retrieved " + list.size()
                    + " product ids from the database.");
            transactionManager.commit(status);
        } catch (TransactionException e) {
            LOG.error("FAILED TO retrieve Prodcut List. Error Message is "
                    + e.getMessage());
            
            if (e.getCause() != null) {
                LOG.error("Underlying cause is " + e.getCause().getMessage());
            }
        }
        
        LOG.error("Start Product Migration");
        int index = 0;
        int incremental = 0;
        migrationCSVService.getExportState().setProductMaxRecords(list.size());
        
        if (migrationCSVService.openFileForExport(EntityType.PRODUCT)) {

            while (running && !completed) {
                try {
                    def = new DefaultTransactionDefinition(
                            TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    def.setTimeout(PPSConstants.I200);
                    status = transactionManager.getTransaction(def);
                    List<Long> writeIds = new ArrayList<Long>();
                    
                    for (index = 0; index < 1 && incremental < list.size(); index++, incremental++) {
                        writeIds.add(list.get(incremental));
                    }
                    
                    migrationCSVService.exportProductCSVFile(writeIds);

                    if (debug) {
                        Thread.sleep(PPSConstants.I1200);
                    }
                    
                    MigrationExportState state = migrationCSVService
                            .getExportState();
                    state.setProductRecordCounter(state
                            .getProductRecordCounter() + writeIds.size());
                    
                    if (incremental >= list.size()) {
                        completed = true;
                        state.setProductExportComplete(true);
                    }
                    
                    setExportState(state);

                    LOG.debug("doProductExport()==>> recordCount: "
                            + state.getProductRecordCounter());
                    transactionManager.commit(status);
                    
                    if (state.isProductExportComplete()) {
                        break;
                    }

                    notifyObservers();
                } catch (TransactionException e) {
                    LOG.error("The Transaction FAILED TO retrieve OI List. Error Message is " + e.getMessage());
                    
                    if (e.getCause() != null) {
                        LOG.error("The Underlying cause is " + e.getCause().getMessage());
                    }
                }
            } // end while
        } else {
            LOG.error("The system could not open OI File for export. ");
        }

        migrationCSVService.closeFileForExport(EntityType.PRODUCT);
        LOG.debug("doProductExport()==>> completed export....");
    }

    /**
     * runs the orderable Items export
     * 
     * @throws InterruptedException exception
     */
    private void doOrderableItemsExport() throws InterruptedException {

        LOG.debug("running OI export");
        this.getExportState().setStatus(ProcessStatus.RUNNING);
        this.getExportState().setExportData(ExportCSVFileData.ORDERABLE_ITEMS);

        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        List<Long> list = new ArrayList<Long>();
        
        try {
            def = new DefaultTransactionDefinition(
                    TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(PPSConstants.I200);
            status = transactionManager.getTransaction(def);
            list = migrationCSVService.getIds(EntityType.ORDERABLE_ITEM);
            LOG.debug("Retrieved " + list.size() + " oi ids from the database.");
            transactionManager.commit(status);
        } catch (TransactionException e) {
            LOG.error("FAILED TO retrieve OI List. Error Message is "
                    + e.getMessage());
            
            if (e.getCause() != null) {
                LOG.error("Underlying cause is " + e.getCause().getMessage());
            }
        }

        LOG.error("Start OI Migration");
        int index = 0;
        int incremental = 0;
        migrationCSVService.getExportState().setOiMaxRecords(list.size());
        
        if (migrationCSVService.openFileForExport(EntityType.ORDERABLE_ITEM)) {

            while (running && !completed) {
                try {
                    def = new DefaultTransactionDefinition(
                            TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    def.setTimeout(PPSConstants.I200);
                    status = transactionManager.getTransaction(def);
                    List<Long> writeIds = new ArrayList<Long>();
                    
                    for (index = 0; index < PPSConstants.I10 && incremental < list.size(); index++, incremental++) {
                        writeIds.add(list.get(incremental));
                    }
                    
                    migrationCSVService.exportOrderableItemsCSVFile(writeIds);

                    if (debug) {
                        Thread.sleep(PPSConstants.I1200);
                    }
                    
                    MigrationExportState state = migrationCSVService
                            .getExportState();
                    
                    if (incremental >= list.size()) {
                        completed = true;
                        state.setOiExportComplete(true);
                    }
                    
                    setExportState(state);

                    LOG.debug("doOIExport()==>> recordCount: "
                            + state.getOiRecordCounter());
                    transactionManager.commit(status);
                    
                    if (state.isOiExportComplete()) {
                        break;
                    }

                    notifyObservers();
                } catch (TransactionException e) {
                    LOG.error("FAILED TO retrieve OI List. Error Message is "
                            + e.getMessage());
                    
                    if (e.getCause() != null) {
                        LOG.error("Underlying cause is "
                                + e.getCause().getMessage());
                    }
                }
            } // end while
        } else {
            LOG.error("Could not open OI File for export. ");
        }

        migrationCSVService.closeFileForExport(EntityType.ORDERABLE_ITEM);

        LOG.debug("doNDCExport()==>> completed export....");

    }

    /**
     * runs the NDC export
     * 
     * @throws InterruptedException exception
     */
    private void doNDCExport() throws InterruptedException {
        LOG.debug("running NDC export");
        this.getExportState().setStatus(ProcessStatus.RUNNING);
        this.getExportState().setExportData(ExportCSVFileData.NDC);

        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        List<Long> list = new ArrayList<Long>();
        
        try {
            def = new DefaultTransactionDefinition(
                    TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(PPSConstants.I200);
            status = transactionManager.getTransaction(def);
            list = migrationCSVService.getIds(EntityType.NDC);
            LOG.debug("Retrieved " + list.size()
                    + " ndc ids from the database.");
            transactionManager.commit(status);
        } catch (TransactionException e) {
            LOG.error("FAILED TO retrieve NDC List. Error Message is "
                    + e.getMessage());
            
            if (e.getCause() != null) {
                LOG.error("Underlying cause is " + e.getCause().getMessage());
            }
        }

        LOG.error("Start NDC Migration");
        int index = 0;
        int incremental = 0;
        migrationCSVService.getExportState().setNdcMaxRecords(list.size());
        
        if (migrationCSVService.openFileForExport(EntityType.NDC)) {

            while (running && !completed) {
                try {
                    def = new DefaultTransactionDefinition(
                            TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    def.setTimeout(PPSConstants.I200);
                    status = transactionManager.getTransaction(def);
                    List<Long> writeIds = new ArrayList<Long>();
                    
                    for (index = 0; index < PPSConstants.I10 && incremental < list.size(); index++, incremental++) {

                        writeIds.add(list.get(incremental));
                    }
                    
                    migrationCSVService.exportNDCCSVFile(writeIds);

                    if (debug) {
                        Thread.sleep(PPSConstants.I1200);
                    }
                    
                    MigrationExportState state = migrationCSVService
                            .getExportState();
                    state.setNdcRecordCounter(state.getNdcRecordCounter()
                            + writeIds.size());
                    
                    if (incremental >= list.size()) {
                        completed = true;
                        state.setNdcExportComplete(true);
                    }
                    
                    setExportState(state);

                    LOG.debug("doNDCExport()==>> recordCount: "
                            + state.getNdcRecordCounter());
                    transactionManager.commit(status);
                    
                    if (state.isNdcExportComplete()) {
                        break;
                    }
                    
                    notifyObservers();
                } catch (TransactionException e) {
                    LOG.error("FAILED TO retrieve NDC List. Error Message is "
                            + e.getMessage());
                    
                    if (e.getCause() != null) {
                        LOG.error("Underlying cause is "
                                + e.getCause().getMessage());
                    }
                }
            } // end while
        } else {
            LOG.error("Could not open NDC File for export. ");
        }
        
        migrationCSVService.closeFileForExport(EntityType.NDC);

        LOG.debug("doNDCExport()==>> completed export....");

    }

    /**
     * stops the Export process
     */
    public synchronized void stopProcess() {
        LOG.debug("==>>>Export process stopped...");
        running = false;
        suspended = false;
        getExportState().setStatus(ProcessStatus.STOPPED);
        getExportState().setRunning(false);
    }

    /**
     * gets the ExportState
     * @return MigrationExportState MigrationExportState
     */
    @Override
    public MigrationExportState getExportState() {
        return exportState;
    }

    /**
     * setter for the Export State
     * @param pState state
     */
    @Override
    public void setExportState(MigrationExportState pState) {
        exportState = pState;
    }

    /**
     * Registers all observers
     * @param pObserver observer
     */
    @Override
    public void registerObserver(Observer pObserver) {
        observers.add(pObserver);
    }

    /**
     * removes observers
     * @param pObserver observer
     */
    @Override
    public void removeObserver(Observer pObserver) {
        observers.remove(pObserver);
    }

    /**
     * notifiy all Observers
     */
    @Override
    public void notifyObservers() {
        for (int loopIndex = 0; loopIndex < observers.size(); loopIndex++) {
            Observer observer = (Observer) observers.get(loopIndex);
            observer.update(this.getExportState());
        }
    }

    /**
     * getter for the PlatformTransactionManager
     * @return PlatformTransactionManager PlatformTransactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * setter for the PlatformTransactionManager
     * @param pTransactionManager  pTransactionManager
     */
    public void setTransactionManager(
            PlatformTransactionManager pTransactionManager) {
        this.transactionManager = pTransactionManager;
    }

    /**
     * get the MigrationCSV Service
     * @return MigrationCSVService MigrationCSVService
     */
    public MigrationCSVService getMigrationCSVService() {
        return migrationCSVService;
    }

    /**
     * 
     * settter for the MigrationCSVService
     * @param pMigrationCSVService pMigrationCSVService
     */
    public void setMigrationCSVService(MigrationCSVService pMigrationCSVService) {
        this.migrationCSVService = pMigrationCSVService;
    }

    /**
     * description
     * @param fdbMappingService the fdbMappingService to set
     */
    public void setFdbMappingService(FdbMappingService fdbMappingService) {
        this.fdbMappingService = fdbMappingService;
    }

    /**
     * description
     * @return the fdbMappingService
     */
    public FdbMappingService getFdbMappingService() {
        return fdbMappingService;
    }

}
