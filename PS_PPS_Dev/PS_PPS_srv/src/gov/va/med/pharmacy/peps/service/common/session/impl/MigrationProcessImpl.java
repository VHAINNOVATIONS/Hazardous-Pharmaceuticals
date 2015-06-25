/**
 * Source file created in 2007 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationFileVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.MigrationFileDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ResetNationalDatabaseDomainCapability;
import gov.va.med.pharmacy.peps.service.common.capability.MigrationCapability;
import gov.va.med.pharmacy.peps.service.common.migration.DatabaseResetErrorMessage;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedDomain;
import gov.va.med.pharmacy.peps.service.common.migration.MockMigrationCapabilityImpl;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessDomainType;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessDomainType.DomainState;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessStatus;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationProcessState;
import gov.va.med.pharmacy.peps.service.common.migration.process.Observer;
import gov.va.med.pharmacy.peps.service.common.session.MigrationCSVService;
import gov.va.med.pharmacy.peps.service.common.session.MigrationProcess;
import gov.va.med.pharmacy.peps.service.common.utility.NdcCsvFile;


/**
 * MigrationProcessImpl
 */
public class MigrationProcessImpl implements MigrationProcess, Runnable {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(MigrationProcessImpl.class);
    
    private List<IngredientVo> noPrimaryIngredients = new ArrayList<IngredientVo>();
    private MockMigrationCapabilityImpl mockMigrationCapability = new MockMigrationCapabilityImpl();
    
    private volatile boolean suspended;
    private volatile boolean running;
    private volatile MigrationProcessState migrationState;
    private List<Observer> observers;
    private MigrationCSVService migrationCSVService;
    private NdcCsvFile ndcCsvFile;
    private UserVo user;

    private PlatformTransactionManager transactionManager;
    private int recordCounter = 0;
    private int ien = 0;
    private boolean eof = false;
    private volatile boolean dbResetRunning = false;
    private int dbCount = 1;
    private int dbProgress = 0;
    private boolean ingredientListEofFlag = false;
    
    /**
     * Toggle DEBUG, true = use mock data, false= make call to vista.
    */

    private boolean debug = false;

    @Autowired
    private MigrationCapability migrationCapability;
    private MigrationFileDomainCapability migrationFileDomainCapability;
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;



    /**
     * Run method
     * @see gov.va.med.pharmacy.peps.service.common.session.MigrationProcess#run()
     */
    @Override
    public void run() {
        if (!running) {
            try {
                LOG.debug("********************************************************" 
                        +
                    "***************************************************************");
                LOG.debug("*********************************** Starting run() method" 
                        +
                    " in MigrationProcessImpl *************************************");
                LOG.debug("***********************************************************" 
                        +
                    "************************************************************");

                runDbResetTask();
                
                if (this.getState().isDatabaseResetCompleted()) {
                    initialize();
                    runProcesses();
                } else {
                    LOG.error("Error! database reset did not complete successfully!");
                }
            } catch (Exception x) {
                LOG.error("Exception occured in run method, message:  " + x.getMessage() + " cause: " + x.getCause()
                          + " stacktrace: " + x.getStackTrace());
                stopDatabaseReset();
                this.stopProcess();
            }
            
            LOG.debug("*****************************************************" 
                    +
                "*******************************************************************");
            LOG.debug("*********************************** Exiting run()" 
                    +
                " method in MigrationProcessImpl  **************************************");
            LOG.debug("*******************************************************" 
                    +
                "*****************************************************************");
        }
    }
    
    /**
     * runDbResetTask
     */
    private void runDbResetTask() {
        boolean dbsuccess = true;
        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        initializeDatabaseReset();

        LOG.debug("********************************************************" 
                +
            "***************************************************************");
        LOG.debug("Starting runDbResetTask() ");
        LOG.debug("**********************************************************" 
                +
            "*************************************************************");

        while (dbResetRunning && dbCount < MigrationProcessState.DB_MAX_COUNT) {
            try {
                def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                def.setTimeout(PPSConstants.I1900);
                status = transactionManager.getTransaction(def);
                dbsuccess = resetNationalDatabaseDomainCapability.resetDatabase(dbCount);

                if (!dbsuccess) {
                    flagDatabaseError("Error! resetting database at count: " + dbCount);
                    break;
                }

                this.getState().setDbCount(dbCount);
                dbProgress = (int) ((this.getState().getDbCount() / (MigrationProcessState.DB_MAX_COUNT - 1)) 
                    * PPSConstants.I100);
                this.getState().setDbPercent(dbProgress);
                LOG.debug("running runDbResetTask(), count: " + dbCount);

                dbCount++;
                
                if (debug) {
                    Thread.sleep(PPSConstants.I2500);
                }

                transactionManager.commit(status);

            } catch (TransactionException e) {
                LOG.error("TransactionException, in runDbResetTask()  " + e.getMessage());
                
                if (e.getCause() != null) {
                    LOG.error("Underlying cause is " + e.getCause().getMessage());
                }
                
                flagDatabaseError("TransactionException, in runDbResetTask()");

            } catch (InterruptedException e) {
                LOG.error("Underlying cause is " + e.getCause().getMessage());
                flagDatabaseError("InterruptedException, in runDbResetTask()");
            }
        }

        if (dbCount >= MigrationProcessState.DB_MAX_COUNT) {
            this.getState().setDatabaseResetCompleted(true);
            
            if (dbProgress >= PPSConstants.I100) {
                dbProgress = PPSConstants.I100;
                this.getState().setDbPercent(dbProgress);
            }

            LOG.debug("DatabaseRest completed successfully!");
        }

        stopDatabaseReset();

        LOG.debug("******************************************************" 
                +
            "*****************************************************************");
        LOG.debug("exiting runDbResetTask() ");
        LOG.debug("******************************************************" 
                +
            "*****************************************************************");

    }

    /**
     * stops the DatabaseReset
     */
    private void stopDatabaseReset() {
        this.getState().setStatus(ProcessStatus.DB_RESET_STOPPED);
        this.dbResetRunning = false;
        this.getState().setDbResetRunning(dbResetRunning);
    }

    /**
     * flags the DatabaseError
     * @param pMessage pMessage
     */
    private void flagDatabaseError(String pMessage) {
        LOG.error(pMessage);
        this.getState().getDatabaseResetErrorMessage().setError(true);
        this.getState().getDatabaseResetErrorMessage().setResponseMessage(pMessage);
    }

    /**
     * initializeDatabaseReset
     */
    private void initializeDatabaseReset() {
        dbCount = 1;
        dbResetRunning = true;
        setState(new MigrationProcessState());
        this.getState().setDbCount(dbCount);
        this.getState().setDatabaseResetCompleted(false);
        this.getState().setDatabaseResetErrorMessage(new DatabaseResetErrorMessage());

        this.getState().setDbResetRunning(true);
        this.getState().setStatus(ProcessStatus.DB_RESET_RUNNING);

    }

    /**
     * intialize process.
     */
    private void initialize() {
        LOG.debug("initializing process..");

        ingredientListEofFlag = false;
        suspended = false;
        running = true;
        getState().setStatus(ProcessStatus.RUNNING);
        getState().setRunning(running);
        getState().setMigrationProceessCompleted(false);
        noPrimaryIngredients.clear();
        migrationCSVService.setProductVoMap(null);
        resetAllDomainVariables(ProcessDomainType.DRUG_UNITS_ACTIVE);
        resetAllDomainVariables(ProcessDomainType.VA_DISPENSE_UNIT_ACTIVE);
        resetAllDomainVariables(ProcessDomainType.VA_GENERIC_NAME_ACTIVE);
        resetAllDomainVariables(ProcessDomainType.DOSAGE_FORM_ACTIVE);
        resetAllDomainVariables(ProcessDomainType.DRUG_CLASS_0);
        resetAllDomainVariables(ProcessDomainType.DRUG_INGREDIENTS_ACTIVE);
        resetAllDomainVariables(ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE);
        resetAllDomainVariables(ProcessDomainType.VA_PRODUCT_ACTIVE);
        resetAllDomainVariables(ProcessDomainType.NDC_CSV_FILE_ACTIVE);
        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        
        try {
            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(PPSConstants.I200);
            status = transactionManager.getTransaction(def);
        } catch (Exception e) {
            LOG.error("BBBBBB: exception in starting transaction management: " + e.getMessage());
        }
        
        this.migrationCapability.init();
        
        try {
            transactionManager.commit(status);
        } catch (TransactionException e) {
            LOG.error("FAILED INITIALIZING HASH MAPS " + " Error Message is " + e.getMessage());
            
            if (e.getCause() != null) {
                LOG.error("Underlying cause is " + e.getCause().getMessage());
            }
        }
    }

    /**
     * resetAllDomainVariables
     *
     * @param pProcessDomainType pProcessDomainType
     */
    private void resetAllDomainVariables(ProcessDomainType pProcessDomainType) {
        MigrationProcessState state = this.getState();
        state.setRecordCount(0);

        MigratedDomain migratedDomain = state.getMigratedDomain(pProcessDomainType);
        migratedDomain.setCount(0);
        migratedDomain.setIen(0);
        migratedDomain.setDuplicatesNoMigrated(0);
        migratedDomain.setEndOfFile(false);
        migratedDomain.getErrorDetailList().clear();
        migratedDomain.setErrorDetails(null);
        migratedDomain.setManufacturersMigrated(0);
        migratedDomain.setFieldName("");
        migratedDomain.setFieldValue("");
        migratedDomain.setItemName("");
        migratedDomain.setMaxRecords(0);
        migratedDomain.setPackageTypesMigrated(0);
        migratedDomain.setPercent("0");
        migratedDomain.setReasonForError("");
        migratedDomain.setTotalErrors(0);
        migratedDomain.setTotalMigrated(0);

    }

    /**
     * runProcesses - main loop
     * @throws InterruptedException InterruptedException
     */
    private void runProcesses() throws InterruptedException {
        LOG.debug("process started...");

        runMigrationTask(ProcessDomainType.DRUG_UNITS_ACTIVE);
        runMigrationTask(ProcessDomainType.DRUG_UNITS_INACTIVE);
        runMigrationTask(ProcessDomainType.VA_DISPENSE_UNIT_ACTIVE);
        runMigrationTask(ProcessDomainType.VA_DISPENSE_UNIT_INACTIVE);
        runMigrationTask(ProcessDomainType.VA_GENERIC_NAME_ACTIVE);
        runMigrationTask(ProcessDomainType.VA_GENERIC_NAME_INACTIVE);
        runMigrationTask(ProcessDomainType.DOSAGE_FORM_ACTIVE);
        runMigrationTask(ProcessDomainType.DOSAGE_FORM_INACTIVE);
        runMigrationTask(ProcessDomainType.DRUG_CLASS_0);
        runMigrationTask(ProcessDomainType.DRUG_CLASS_1);
        runMigrationTask(ProcessDomainType.DRUG_CLASS_2);
        runMigrationTask(ProcessDomainType.DRUG_INGREDIENTS_ACTIVE);
        runMigrationTask(ProcessDomainType.DRUG_INGREDIENTS_LIST);
        ingredientListEofFlag = true;
        runMigrationTask(ProcessDomainType.DRUG_INGREDIENTS_INACTIVE);
        runMigrationTask(ProcessDomainType.DRUG_INGREDIENTS_LIST);
        runMigrationTask(ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE);
        LOG.debug("Finished OI task");
        
        try {
            migrationCSVService.getOiCsvFile().closeImport();
        } catch (MigrationException me) {
            LOG.error("Exception closing OI CSV File: " + me.getMessage());
        } catch (Exception e) {
            LOG.error("Unknown exception closing OI CSV File: " + e.getMessage());
        }

        LOG.debug("Start VA Product Active");
        runMigrationTask(ProcessDomainType.VA_PRODUCT_ACTIVE);
        LOG.debug("Start VA Product InActive");
        runMigrationTask(ProcessDomainType.VA_PRODUCT_INACTIVE);
        
        try {
            migrationCSVService.getProductCsvFile().closeImport();
        }  catch (MigrationException me) {
            LOG.error("Exception closing Product CSV File: " + me.getMessage());
        }  catch (Exception e) {
            LOG.error("Unknown exception closing Product CSV File: " + e.getMessage());
        }

        this.updateCmopIdGenerator();
        
        LOG.debug("Start NDC");

        migrationCapability.sendStartNDCMessage();
        runMigrationTask(ProcessDomainType.NDC_CSV_FILE_ACTIVE);
        migrationCapability.sendStopNDCMessage();
        
        try {
            migrationCSVService.getNdcCsvFile().closeImport();
        }  catch (MigrationException me) {
            LOG.error("Exception closing NDC CSV File: " + me.getMessage());
        }  catch (Exception e) {
            LOG.error("Unknown exception closing NDC CSV File: " + e.getMessage());
        }
        
    
        migrationCapability.clear();
        this.stopProcess();
        this.getState().setMigrationProceessCompleted(true);

    }

    /**
     * updateCmopIdGenerator
     */
    private void updateCmopIdGenerator() {
        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;

        try {
            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(PPSConstants.I200);
            status = transactionManager.getTransaction(def);
            this.migrationCapability.updateCmopIdGenerator();
            transactionManager.commit(status);
        } catch (Exception e) {
            LOG.error("Exception in updateCmopIdGenerator: " + e.getMessage());
        }

    }
    
    /**
      * runMigrationTask - start a migration task
      * @param pProcessDomainType pProcessDomainType
      * @throws InterruptedException InterruptedException
      */
    private void runMigrationTask(ProcessDomainType pProcessDomainType) throws InterruptedException {
        
        //if process has been stopped, just return. 
        
        if (!running) {
            return;
        }

        MigratedDomain migratedDomain = null;
        LOG.debug("runMigrationTask()==> Start runMigrationTask():   " + pProcessDomainType.toString());

        // opens multipart file for import for NDC/OI
        // if it returns false, either NDC or OI failed to open for import.
        boolean csvImport = true;
        
        try {
            csvImport = openFileForImport(pProcessDomainType);
            
            if (!csvImport) {
                if (ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE.equals(pProcessDomainType)) {
                    eof = true;
                }
            }
        } catch (Exception e) {
            LOG.error("AAAAA: CouldNotOpen File for import: " + e.getMessage());
            eof = true;
        }

        //create product map before running products.
        if (pProcessDomainType.equals(ProcessDomainType.VA_PRODUCT_ACTIVE) && csvImport) {
            createProductMap(pProcessDomainType);
        }

        while (running && !eof) {
            waitWhileSuspended();
            DefaultTransactionDefinition def = null;
            TransactionStatus status = null;
            
            try {
                def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                def.setTimeout(PPSConstants.I200);
                status = transactionManager.getTransaction(def);
            } catch (Exception e) {
                LOG.error("AAAAA: exception in starting transaction management: " + e.getMessage());
            }

            try {

                doDomainMigration(pProcessDomainType, ien);
                migratedDomain = (MigratedDomain) migrationState.getMigratedDomain(pProcessDomainType);

                ien = migrationState.getMigratedDomain(pProcessDomainType).getIen();
                LOG.debug("runMigrationTask()==>IEN: " + ien);

                eof = migrationState.isRunningEof();

                //sets individual domain record count
                migratedDomain.setCount(migrationState.getRecordCount());

                LOG.debug(pProcessDomainType.getName() + " record count: " + migratedDomain.getCount());
                LOG.debug(pProcessDomainType.getName() + " get percent: " + migratedDomain.getPercent());
                LOG.debug("runMigrationTask()==>recordCounter: " + migrationState.getRecordCount());
                LOG.debug("----------------------------------------------------------------");
                LOG.debug("runMigrationTask()==>" + pProcessDomainType.getName() + ".getStrLastIEN(): "
                          + migratedDomain.getIen());
                LOG.debug("runMigrationTask()==>" + pProcessDomainType.getName() + ".getISuccessfullyMigrated(): "
                          + migratedDomain.getTotalMigrated());
                LOG.debug("runMigrationTask()==>" + pProcessDomainType.getName() + ".getIErroredOnMigration(): "
                          + migratedDomain.getTotalErrors());
                LOG.debug("runMigrationTask()==>" + pProcessDomainType.getName() + ".getIDuplicatesNotMigrated(): "
                          + migratedDomain.getDuplicatesNoMigrated());
                LOG.debug("runMigrationTask()==>" + pProcessDomainType.getName() + " .isEndOfFile(): "
                          + migratedDomain.isEndOfFile());
                LOG.debug("----------------------------------------------------------------");

                if (debug) {
                    Thread.sleep(PPSConstants.I500);
                }
                
                try {
                    transactionManager.commit(status);
                } catch (TransactionException e) {
                    LOG.error("FAILED TO PRODCESS NDCS in Rows " + migrationState.getRecordCount() + " for "
                              + pProcessDomainType.getRecordFetchQty() + ". Error Message is " + e.getMessage());
                    
                    if (e.getCause() != null) {
                        LOG.error("Underlying cause is " + e.getCause().getMessage());
                    }
                    
                    Thread.sleep(PPSConstants.I500);

                }
                
                LOG.debug("runMigrationTask()==> " + pProcessDomainType.getName() + " eof: " + eof);

            } catch (Exception e) {
                LOG.error("runMigrationTask===>>> Exception Exception " + e.getMessage());
                this.stopProcess();
                transactionManager.rollback(status);

            }
        }

        migratedDomain = (MigratedDomain) migrationState.getMigratedDomain(pProcessDomainType);

        if (migratedDomain.isEndOfFile() || !running) {
            recordCounter = 0;
            migrationState.setRecordCount(0);

            saveDomainVo(pProcessDomainType, migratedDomain);
        }

        LOG.debug("completed processing: " + pProcessDomainType.toString());

        LOG.debug("runMigrationTask()==>" + pProcessDomainType.getName() + ".getStrLastIEN(): "
                  + migratedDomain.getIen());
        LOG.debug("runMigrationTask()==>" + pProcessDomainType.getName() + ".getISuccessfullyMigrated(): "
                  + migratedDomain.getTotalMigrated());
        LOG.debug("runMigrationTask()==>" + pProcessDomainType.getName() + ".getIErroredOnMigration(): "
                  + migratedDomain.getTotalErrors());
        LOG.debug("runMigrationTask()==>" + pProcessDomainType.getName() + ".getIDuplicatesNotMigrated(): "
                  + migratedDomain.getDuplicatesNoMigrated());
        LOG.debug("runMigrationTask()==>" + pProcessDomainType.getName() + " .isEndOfFile(): "
                  + migratedDomain.isEndOfFile());

        ien = 0;
        eof = false;
    }

    /**
    * Do domain migration 
    * @param pProcessDomainType pProcessDomainType
    * @param pIen ien
    */
    private void doDomainMigration(ProcessDomainType pProcessDomainType, int pIen) {
        MigrationVariablesVo vo = null;

        if (debug) {
            vo = doMockMigrationCapability(pProcessDomainType, pIen);
        } else {
            vo = doMigrationCapability(pProcessDomainType, pIen);
        }

        MigratedDomain migratedDomain = (MigratedDomain) migrationState.getMigratedDomain(pProcessDomainType);
        migratedDomain.setIen(Integer.parseInt(vo.getStrLastIEN()));
        int duplicatetsNotMigrated = migratedDomain.getDuplicatesNoMigrated();
        migratedDomain.setDuplicatesNoMigrated(vo.getIDuplicatesNotMigrated() + duplicatetsNotMigrated);
        int totalErrors = migratedDomain.getTotalErrors();
        migratedDomain.setTotalErrors(vo.getIErroredOnMigration() + totalErrors);
        int sucessfullyMigrated = migratedDomain.getTotalMigrated();
        migratedDomain.setTotalMigrated(vo.getISuccessfullyMigrated() + sucessfullyMigrated);

        if (pProcessDomainType == ProcessDomainType.NDC_CSV_FILE_ACTIVE) {
            int manufacturerMigrated = migratedDomain.getManufacturersMigrated();
            migratedDomain.setManufacturersMigrated(vo.getINumManufacturersMigrated() + manufacturerMigrated);
            int packageTypesMigrated = migratedDomain.getPackageTypesMigrated();
            migratedDomain.setPackageTypesMigrated(vo.getINumPackageTypesMigrated() + packageTypesMigrated);
            LOG.debug("Manufacturer size is " + migratedDomain.getManufacturersMigrated() + " package type size is "
                      + migratedDomain.getPackageTypesMigrated());
        }

        recordCounter += (vo.getISuccessfullyMigrated() + vo.getIErroredOnMigration() + vo.getIDuplicatesNotMigrated());
        migrationState.setRecordCount(recordCounter);

        if (findDomainEofFile(vo, migratedDomain, pProcessDomainType) && vo.isEndOfFile()) {
            migratedDomain.setEndOfFile(vo.isEndOfFile());
            LOG.debug("doDomainMigration==>- Reached EOF!!");
        }

        migrationState.setRunningEof(vo.isEndOfFile());

        if (pProcessDomainType == ProcessDomainType.NDC_CSV_FILE_ACTIVE
            || pProcessDomainType == ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE) {
            int totalRowsCount = (int) migrationCSVService.getTotalRowsCount();
            LOG.debug("---doDomainMigration==> totolRowsCount: " + totalRowsCount);
            migratedDomain.setMaxRecords(totalRowsCount);
            migratedDomain.setPercent(calculatePercentage(pProcessDomainType, migrationState.getRecordCount(),
                                                          totalRowsCount));
        }  else {
            migratedDomain.setPercent(calculatePercentage(pProcessDomainType, migrationState.getRecordCount(),
                                                          pProcessDomainType.getMaxRecord()));
        }

        LOG.debug("---doDomainMigration==> Percent: " + migratedDomain.getPercent());
        LOG.debug("---doDomainMigration==> Task: " + pProcessDomainType.getMessage() + "  "
                  + pProcessDomainType.getName() + " ien: " + migratedDomain.getIen());
    }

    /**
     * finds the EOF for DrugClass, Drug Igredients & inactive
     * @param vo  MigrationVariablesVo
     * @param pMigratedDomain MigratedDomain
     * @param pProcessDomainType pProcessDomainType
     * @return true/false
     */
    private boolean findDomainEofFile(MigrationVariablesVo vo, MigratedDomain pMigratedDomain,
                                      ProcessDomainType pProcessDomainType) {
        boolean foundEof = false;
        switch(pProcessDomainType.getDomainState()) {
            case ACTIVE: 
                
                if (pProcessDomainType == ProcessDomainType.NDC_CSV_FILE_ACTIVE 
                        || pProcessDomainType == ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE) {
                    foundEof = true;
                }
                
                break;
            case INACTIVE: 
                if (pProcessDomainType != ProcessDomainType.DRUG_INGREDIENTS_INACTIVE)
                {
                    foundEof = true;
                }
                
                break;
                    
            case INGREDIENTS_LIST:
                if (ingredientListEofFlag) {
                    foundEof = true;
                }
                
                break;

            case CLASS2:
                foundEof = true;
                break;
                
            default: 
                   
                break;
        }
        
        return foundEof;

    }

    /**
     * Remove later, testing only
     * doMockMigrationCapability
     * @param pProcessDomainType pProcessDomainType
     * @param pIen ien
     * @return MigrationVariablesVo
     */
    private MigrationVariablesVo doMockMigrationCapability(ProcessDomainType pProcessDomainType, int pIen) {
        MigrationVariablesVo vo = null;
        switch (pProcessDomainType) {
            case DRUG_UNITS_ACTIVE:
            case DRUG_UNITS_INACTIVE:
                vo =
                        mockMigrationCapability.migrateDrugUnits(String.valueOf(pIen),
                                                                 pProcessDomainType.getRecordFetchQty(),
                                                                 pProcessDomainType.getDomainState().getState(),
                                                                 migrationState.getRecordCount(), pProcessDomainType);
                break;
            case VA_DISPENSE_UNIT_ACTIVE:
            case VA_DISPENSE_UNIT_INACTIVE:
                vo =
                        mockMigrationCapability.migrateDrugUnits(String.valueOf(pIen),
                                                                 pProcessDomainType.getRecordFetchQty(),
                                                                 pProcessDomainType.getDomainState().getState(),
                                                                 migrationState.getRecordCount(), pProcessDomainType);
                break;
            case VA_GENERIC_NAME_ACTIVE:
            case VA_GENERIC_NAME_INACTIVE:
                vo =
                        mockMigrationCapability.migrateDrugUnits(String.valueOf(pIen),
                                                                 pProcessDomainType.getRecordFetchQty(),
                                                                 pProcessDomainType.getDomainState().getState(),
                                                                 migrationState.getRecordCount(), pProcessDomainType);
                break;
            case DOSAGE_FORM_ACTIVE:
            case DOSAGE_FORM_INACTIVE:
                vo =
                        mockMigrationCapability.migrateDrugUnits(String.valueOf(pIen),
                                                                 pProcessDomainType.getRecordFetchQty(),
                                                                 pProcessDomainType.getDomainState().getState(),
                                                                 migrationState.getRecordCount(), pProcessDomainType);
                break;
            case DRUG_CLASS_0:
            case DRUG_CLASS_1:
            case DRUG_CLASS_2:
                vo =
                        mockMigrationCapability.migrateDrugUnits(String.valueOf(pIen),
                                                                 pProcessDomainType.getRecordFetchQty(),
                                                                 pProcessDomainType.getDomainState().getState(),
                                                                 migrationState.getRecordCount(), pProcessDomainType);
                break;
            case DRUG_INGREDIENTS_ACTIVE:
            case DRUG_INGREDIENTS_INACTIVE:
                vo =
                        mockMigrationCapability.migrateDrugUnits(String.valueOf(pIen),
                                                                 pProcessDomainType.getRecordFetchQty(),
                                                                 pProcessDomainType.getDomainState().getState(),
                                                                 migrationState.getRecordCount(), pProcessDomainType);
                break;
            case ORDERABLE_ITEMS_CSV_FILE_ACTIVE:
                vo = doMigrateOrderableItems(pIen, pProcessDomainType);
                break;
            case VA_PRODUCT_ACTIVE:
            case VA_PRODUCT_INACTIVE:
                vo =
                        mockMigrationCapability.migrateDrugUnits(String.valueOf(pIen),
                                                                 pProcessDomainType.getRecordFetchQty(),
                                                                 pProcessDomainType.getDomainState().getState(),
                                                                 migrationState.getRecordCount(), pProcessDomainType);
                break;
            case NDC_CSV_FILE_ACTIVE:
                vo = doMigrateNdc(pProcessDomainType);
                break;
            default:
                break;
        }

        return vo;
    }

    /**
     * do the MigrationCapability
     * @param pProcessDomainType pProcessDomainType
     * @param pIen IEN 
     * @return MigrationVariablesVo
     */
    private MigrationVariablesVo doMigrationCapability(ProcessDomainType pProcessDomainType, int pIen) {
        MigrationVariablesVo vo = null;
        LOG.debug("doMigrationCapability" + pProcessDomainType.getName() + ":"
                  + pProcessDomainType.getDomainState().getState());
        
        try {
            switch (pProcessDomainType) {
                case DRUG_UNITS_ACTIVE:
                case DRUG_UNITS_INACTIVE:
                    vo =
                            migrationCapability.migrateDrugUnits(String.valueOf(pIen),
                                                                 pProcessDomainType.getRecordFetchQty(),
                                                                 pProcessDomainType.getDomainState().getState());
                    break;
                case VA_DISPENSE_UNIT_ACTIVE:
                case VA_DISPENSE_UNIT_INACTIVE:
                    vo =
                            migrationCapability.migrateDispenseUnits(String.valueOf(pIen),
                                                                     pProcessDomainType.getRecordFetchQty(),
                                                                     pProcessDomainType.getDomainState().getState());
                    break;
                case VA_GENERIC_NAME_ACTIVE:
                case VA_GENERIC_NAME_INACTIVE:
                    vo =
                            migrationCapability.migrateVAGeneric(String.valueOf(pIen),
                                                                 pProcessDomainType.getRecordFetchQty(),
                                                                 pProcessDomainType.getDomainState().getState());
                    break;
                case DOSAGE_FORM_ACTIVE:
                case DOSAGE_FORM_INACTIVE:
                    vo =
                            migrationCapability.migrateDosageForm(String.valueOf(pIen),
                                                                  pProcessDomainType.getRecordFetchQty(),
                                                                  pProcessDomainType.getDomainState().getState());
                    break;
                case DRUG_CLASS_0:
                case DRUG_CLASS_1:
                case DRUG_CLASS_2:
                    vo =
                            migrationCapability.migrateDrugClass(String.valueOf(pIen),
                                                                 pProcessDomainType.getRecordFetchQty(),
                                                                 pProcessDomainType.getDomainState().getState());
                    break;
                case DRUG_INGREDIENTS_ACTIVE:
                case DRUG_INGREDIENTS_INACTIVE:
                case DRUG_INGREDIENTS_LIST:
                    vo = doDrugIngredient(String.valueOf(pIen), pProcessDomainType, migrationState.getRecordCount());
                    break;
                case ORDERABLE_ITEMS_CSV_FILE_ACTIVE:
                    vo = doMigrateOrderableItems(pIen, pProcessDomainType);
                    break;
                case VA_PRODUCT_ACTIVE:
                case VA_PRODUCT_INACTIVE:
                    vo = doMigrateProductItems(String.valueOf(pIen), pProcessDomainType);
                    break;
                case NDC_CSV_FILE_ACTIVE:
                    vo = doMigrateNdc(pProcessDomainType);
                    break;
                    
                default:
                   
                    break;
            }
        } catch (Exception e) {
            LOG.error("AAAAA:Uncaught exception in doMigrationCapability while migrating the "
                      + pProcessDomainType.getName() + " Exception is " + e.getMessage());
            vo = new MigrationVariablesVo();
            vo.setEndOfFile(true);
        }

        return vo;
    }

    /**
     *  Migrate Drug Ingredients
     * @param pIen IEN
     * @param pProcessDomainType pProcessDomainType
     * @param pRecordCount recordCount
     * @return MigrationVariablesVo
     */
    private MigrationVariablesVo doDrugIngredient(String pIen, ProcessDomainType pProcessDomainType, int pRecordCount) {
        MigrationVariablesVo vo = null;

        if (debug) {
            if (pProcessDomainType.getDomainState() == DomainState.INGREDIENTS_LIST) {
                vo =
                        mockMigrationCapability.migrateDrugIngredient(noPrimaryIngredients, pRecordCount,
                                                                      pProcessDomainType);
            } else {
                
                //Get Active Ingredients
                vo =
                        mockMigrationCapability.migrateDrugIngredient(pIen, pProcessDomainType.getRecordFetchQty(),
                                                                      pProcessDomainType.getDomainState().getState(),
                                                                      pRecordCount, pProcessDomainType);
                
                for (IngredientVo iVo : vo.getIngredientList()) {
                    noPrimaryIngredients.add(iVo);
                }
            }
        } else {
            if (pProcessDomainType.getDomainState() == DomainState.INGREDIENTS_LIST) {
                vo = migrationCapability.migrateDrugIngredient(noPrimaryIngredients);
                vo.setStrLastIEN("0");
                noPrimaryIngredients.clear();
            } else {
                vo =
                        migrationCapability.migrateDrugIngredient(pIen, pProcessDomainType.getRecordFetchQty(),
                                                                  pProcessDomainType.getDomainState().getState());
                
                for (IngredientVo iVo : vo.getIngredientList()) {
                    LOG.debug("Added Ingredient " + iVo.getValue() + " to Ingredeint List.");
                    noPrimaryIngredients.add(iVo);
                }
            }
        }

        return vo;

    }

    /**
     * Save domainVo
     * @param pProcessDomainType pProcessDomainType
     * @param migratedDomain migratedDomain
     */
    public synchronized void saveDomainVo(ProcessDomainType pProcessDomainType, MigratedDomain migratedDomain) {
        DefaultTransactionDefinition def =
                new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        def.setTimeout(PPSConstants.I60);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            List<MigrationFileVo> domainNames = migrationFileDomainCapability.retrieveMigrationFile();

            for (MigrationFileVo dbDomain : domainNames) {
                if (migratedDomain.getFileNumber() != null) {
                    if (migratedDomain.getFileNumber().equals(ProcessDomainType.NDC_CSV_FILE_ACTIVE.getFileNumber())) {
                        if (dbDomain.getFileId().equals(ProcessDomainType.PACKAGE_TYPES.getFileNumber())) {
                            dbDomain.setRowsMigratedQty(migratedDomain.getPackageTypesMigrated());
                            migrationFileDomainCapability.update(dbDomain, this.getUser());
                        } else if (dbDomain.getFileId().equals(ProcessDomainType.MANUFACTURES.getFileNumber())) {
                            dbDomain.setRowsMigratedQty(migratedDomain.getManufacturersMigrated());
                            migrationFileDomainCapability.update(dbDomain, this.getUser());
                        } else if (dbDomain.getFileId().equals(ProcessDomainType.NDC_CSV_FILE_ACTIVE.getFileNumber())) {
                            dbDomain.setRowsMigratedQty(migratedDomain.getTotalMigrated());
                            dbDomain.setErrorQty(migratedDomain.getTotalErrors());
                            dbDomain.setRowsNotMigratedQty(migratedDomain.getDuplicatesNoMigrated());
                            dbDomain.setRowsProcessedQty(migratedDomain.getCount());
                            migrationFileDomainCapability.update(dbDomain, this.getUser());
                        }
                    } else if (dbDomain.getFileId().equals(pProcessDomainType.getFileNumber())) {
                        dbDomain.setRowsMigratedQty(migratedDomain.getTotalMigrated());
                        dbDomain.setErrorQty(migratedDomain.getTotalErrors());
                        dbDomain.setRowsNotMigratedQty(migratedDomain.getDuplicatesNoMigrated());
                        dbDomain.setRowsProcessedQty(migratedDomain.getCount());
                        migrationFileDomainCapability.update(dbDomain, this.getUser());
                        break;
                    }

                } else {
                    LOG.error("saveDomainVo==>migratedDomain.getFileNumber() == null, for " + migratedDomain.getName());
                    break;
                }
            }
            
            transactionManager.commit(status);
            LOG.debug("runMigrationTask()==> " + pProcessDomainType.getName() + " eof: " + eof);
        } catch (DataAccessException e) {
            LOG.error("DataAccessException " + e.getMessage());
            transactionManager.rollback(status);
            this.stopProcess();
        }

    }

    /**
     * This method does the NDC migrations
     * @param pProcessDomainType  pProcessDomainType
     * @return will return NULL for EOF 
     */
    private MigrationVariablesVo doMigrateNdc(ProcessDomainType pProcessDomainType) {
        return migrationCSVService.migrateNDCs(pProcessDomainType);
    }

    /**
     * Migrate orderable Items
     * @param pIen IEN
     * @param pProcessDomainType pProcessDomainType
     * @return MigrationVariablesVo
     */
    private synchronized MigrationVariablesVo doMigrateOrderableItems(int pIen, ProcessDomainType pProcessDomainType) {
        return migrationCSVService.migrateOrderableItems(pProcessDomainType);
    }

    /**
     * Migrate product items
     * @param pIen IEN
     * @param pProcessDomainType pProcessDomainType
     * @return MigrationVariablesVo
     */
    private synchronized MigrationVariablesVo doMigrateProductItems(String pIen, ProcessDomainType pProcessDomainType) {
        return migrationCSVService.migrateProducts(pIen, pProcessDomainType);
    }

    /**
     * Open multipartFile for import, makes it available for NDC, OI or PRODUCTS
     * @param pProcessDomainType pProcessDomainType
     * @return TRUE/FALSE
     */
    private synchronized boolean openFileForImport(ProcessDomainType pProcessDomainType) {
        boolean success = true;
        
        try {
            if (pProcessDomainType == ProcessDomainType.NDC_CSV_FILE_ACTIVE) {
                success = migrationCSVService.openNdcFileForImport();
            } else if (pProcessDomainType == ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE) {
                success = migrationCSVService.openOiFileForImport();
            } else if (pProcessDomainType == ProcessDomainType.VA_PRODUCT_ACTIVE
                     || pProcessDomainType == ProcessDomainType.VA_PRODUCT_INACTIVE) {
                success = migrationCSVService.openProductFileForImport();
            }
        } catch (MigrationException me) {
            LOG.error("openFileForInport()==>>:  " + pProcessDomainType.getName() + " " + me.getMessage());
            migrationCapability.saveMigrationErrorMessage(pProcessDomainType.getFileNumber(), me);
            success = false;
        }

        return success;
    }

    /**
    *  Calculates the running percentage
    * @param pProcessDomainType pProcessDomainType
    * @param pRecordCount pRecordCount
    * @param pMaxRecords pMaxRecords
    * @return percentage
    */
    private String calculatePercentage(ProcessDomainType pProcessDomainType, int pRecordCount, int pMaxRecords) {
        
        //Percent is converted to string to avoid decimal expansion due to dividing 1/3 results in a infinite large number.
        
        BigDecimal bdCount = new BigDecimal(pRecordCount);
        BigDecimal maxRecord = (BigDecimal) new BigDecimal(pMaxRecords - 1); // subtract 1 for the header
        BigDecimal percent = bdCount.divide(maxRecord, 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));

        // if percent is > than 100, set to 100
        if (percent.compareTo(BigDecimal.valueOf(new Double("100.0"))) >= 0) {
            percent = BigDecimal.valueOf(new Double("100.0"));
            LOG.debug("--------------------------->>> reached 100%, RecordCount: " + pRecordCount);
        }
        
        return percent.toPlainString();
    }

    /**
    * creates Product Map
    * @param pProcessDomainType pProcessDomainType
    */
    private void createProductMap(ProcessDomainType pProcessDomainType) {
        if (migrationCSVService.getProductCsvFile() != null) {
            if (migrationCSVService.getProductVoMap() == null) {
                migrationCSVService.createProductVoMap(pProcessDomainType);
            }
        }
    }

    /**
    * suspends process (pauses)
    */
    public synchronized void suspendProcess() {
        LOG.debug("process paused...");
        suspended = true;
        getState().setStatus(ProcessStatus.PAUSED);
    }

    /**
     * resumes the process
     */
    public synchronized void resumeProcess() {
        LOG.debug("process resumed...");
        getState().setStatus(ProcessStatus.RESUMED);
        suspended = false;
        getState().setStatus(ProcessStatus.RUNNING);
    }

    /**
    * waitWhileSuspended - loops until suspended is false
    * @throws InterruptedException InterruptedException
    */
    private void waitWhileSuspended() throws InterruptedException {
        while (suspended) {
            Thread.sleep(PPSConstants.I200);
        }
    }

    /**
    * stops running process
    */
    public synchronized void stopProcess() {
        LOG.debug("==>>> process stopped...");
        running = false;
        suspended = false;
        getState().setStatus(ProcessStatus.STOPPED);
        getState().setRunning(false);
        
        if (getState().isDbResetRunning()) {
            stopDatabaseReset();
        }

    }

    /**
     * returns the process state
     * @return MigrationProcessState
     */
    public synchronized MigrationProcessState getState() {
        return migrationState;
    }

    /**
     * setter for the MigrationProcessState
     * @param pState pState
     */
    public void setState(MigrationProcessState pState) {
        this.migrationState = pState;
    }

    /**
     * registers observers
     * @param pObserver pObserver
     */
    public synchronized void registerObserver(Observer pObserver) {
        observers.add(pObserver);
    }

    /**
     * removes observers
     * @param pObserver pObserver 
     */
    public synchronized void removeObserver(Observer pObserver) {
        observers.remove(pObserver);
    }

    
    /**
     * gets MigrationCapability
     * 
     * @return MigrationCapability
     */
    public MigrationCapability getMigrationCapability() {
        return migrationCapability;
    }

    /**
     * sets the MigrationCapability
     * @param pMigrationCapability pMigrationCapability
     */
    public void setMigrationCapability(MigrationCapability pMigrationCapability) {
        this.migrationCapability = pMigrationCapability;
    }

    /**
     * gets the MigrationCSVService
     * @return MigrationCSVService MigrationCSVService
     */
    public MigrationCSVService getMigrationCSVService() {
        return migrationCSVService;
    }
    
    /**
     * sets the MigrationCSVService
     * @param pMigrationCSVService pMigrationCSVService
     */
    public void setMigrationCSVService(MigrationCSVService pMigrationCSVService) {
        this.migrationCSVService = pMigrationCSVService;
    }

    /**
     * gets the NdcCsvFile
     *
     * @return NdcCsvFile
     */
    public NdcCsvFile getNdcCsvFile() {
        return ndcCsvFile;
    }

    /**
     * sets the Ndc sCsvFile
     * @param pNdcCsvFile pNdcCsvFile
     */
    public void setNdcCsvFile(NdcCsvFile pNdcCsvFile) {
        this.ndcCsvFile = pNdcCsvFile;
    }

    /**
     * gets the TransactionManager
     * @return PlatformTransactionManager a
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * sets the TransactionManager
     * @param pTransactionManager pTransactionManager
     */
    public void setTransactionManager(PlatformTransactionManager pTransactionManager) {
        this.transactionManager = pTransactionManager;
    }

    
    /**
     * setUser
     * @param pUser user
     */
    @Override 
    public void setUser(UserVo pUser) {
        this.user = pUser;
    }

    /**
     * gets the user
     * @return userVo user
     */
    public UserVo getUser() {
        return user;
    }

    /**
     * gets the MigrationFileDomainCapability
     * @return MigrationFileDomainCapability
     */
    public MigrationFileDomainCapability getMigrationFileDomainCapability() {
        return migrationFileDomainCapability;
    }

    /**
     * sets the MigrationFileDomainCapability
     * @param pMigrationFileDomainCapability pMigrationFileDomainCapability
     * 
     */
    public void setMigrationFileDomainCapability(MigrationFileDomainCapability pMigrationFileDomainCapability) {
        this.migrationFileDomainCapability = pMigrationFileDomainCapability;
    }

    /**
     * gets the ResetNationalDatabaseDomainCapability
     * @return ResetNationalDatabaseDomainCapability
     */
    public ResetNationalDatabaseDomainCapability getResetNationalDatabaseDomainCapability() {
        return resetNationalDatabaseDomainCapability;
    }

    /**
     * sets the ResetNationalDatabaseDomainCapability
     * @param pResetNationalDatabaseDomainCapability pResetNationalDatabaseDomainCapability
     */
    public void setResetNationalDatabaseDomainCapability(ResetNationalDatabaseDomainCapability
            pResetNationalDatabaseDomainCapability) {
        this.resetNationalDatabaseDomainCapability = pResetNationalDatabaseDomainCapability;
    }

}
