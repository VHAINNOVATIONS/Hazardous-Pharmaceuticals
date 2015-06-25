/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import org.apache.log4j.Logger;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessCommand;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationExportState;
import gov.va.med.pharmacy.peps.service.common.migration.process.Observer;
import gov.va.med.pharmacy.peps.service.common.session.MigrationExportProcess;


/**
 * 
 * MigrationExportManagerImpl
 */
public class MigrationExportManagerImpl implements MigrationExportManager, Observer {

    private static final Logger LOG = org.apache.log4j.Logger
            .getLogger(MigrationExportManagerImpl.class);

    private MigrationExportState exportState;
    private MigrationExportProcess migrationExportProcess;
    private SimpleAsyncTaskExecutor taskExportExecutor;
    private boolean processInitialized;
    private boolean running;
    private boolean exportComplete;

   /**
   * start migration process
   * @return MigrationExportState MigrationExportState
   */
    public MigrationExportState startExportProcess() {
        if (!isProcessInitialized()) {
            initializeProcess();
        }

        command(ProcessCommand.START);
        
        return exportState;
    }

    /**
     * getExportStatus
     * @see gov.va.med.pharmacy.peps.service.common.session.impl.MigrationExportManager#getExportStatus()
     * @return MigrationExportState
     */
    public MigrationExportState getExportStatus() {
        command(ProcessCommand.STATUS);
        
        if (exportState != null) {
            this.running = exportState.isRunning();
            this.exportComplete = exportState.isExportComplete();
        }
        
        return exportState;
    }

    /**
     * stopExportProcess
     * @see gov.va.med.pharmacy.peps.service.common.session.impl.MigrationExportManager#stopExportProcess()
     */
    public void stopExportProcess() {
        command(ProcessCommand.STOP);
        processInitialized = false;
    }

    
    /**
     * executes command 
     *
     * @param pCommand pCommand
     */
    private void command(ProcessCommand pCommand) {
        
        switch (pCommand) {
        
            case START:
                LOG.debug("export is running flag: " + running);
                
                if (exportState == null) {
                    LOG.debug("exportState: " + exportState);
                } else {
                    LOG.debug("exportState.isExportComplete(): " + exportState.isExportComplete());
                }

                if (isExportComplete() || !isRunning()) {
                    taskExportExecutor.execute(migrationExportProcess);
                    sleepThread(PPSConstants.I2000); // delay to allow for the state to be

                    
                    if (migrationExportProcess != null) {
                        exportState = migrationExportProcess.getExportState();
                        setExportComplete(exportState.isExportComplete());
                        this.setRunning(exportState.isRunning());
                        migrationExportProcess.registerObserver(this);
                    }
                }
                
                break;
            case STATUS:
                if (migrationExportProcess != null) {
                    exportState = migrationExportProcess.getExportState();
                    break;
                }
            case STOP:
                migrationExportProcess.stopProcess();
                break;
            default:
                break;
        }
    }

    /**
     * sets the thread to sleep
     * @param count count
     */
    private void sleepThread(long count) {
        try {
            Thread.sleep(count);
        } catch (InterruptedException x) {
            x.getStackTrace();
        }
    }

    /**
     * Initializes process
     */
    private void initializeProcess() {
        processInitialized = true;
    }

    /**
    * check if process has been initialized
    * @return true/false 
    */
    private boolean isProcessInitialized() {
        return this.processInitialized;
    }

    /**
     * set the process initialized
     * @param pProcessInitialized pProcessInitialized
     */
    public void setProcessInitialized(boolean pProcessInitialized) {
        this.processInitialized = pProcessInitialized;
    }

    /**
     * returns the Migration Export Process
     * @return MigrationExportProcess
     */
    public MigrationExportProcess getMigrationExportProcess() {
        return migrationExportProcess;
    }

    /**
     * setter of the Migration Export Process
     * @param pMigrationExportProcess pMigrationExportProcess
     */
    public void setMigrationExportProcess(
            MigrationExportProcess pMigrationExportProcess) {
        this.migrationExportProcess = pMigrationExportProcess;
    }

    /**
     * get the Export State
     *
     * @return MigrationExportState MigrationExportState
     */
    public MigrationExportState getExportState() {
        return exportState;
    }

    /**
     * set Export State
     *
     * @param pExportState pExportState
     */
    public void setExportState(MigrationExportState pExportState) {
        this.exportState = pExportState;
    }

    /**
     * gets the TaskExportExecutor
     * @return SimpleAsyncTaskExecutor SimpleAsyncTaskExecutor
     */
    public SimpleAsyncTaskExecutor getTaskExportExecutor() {
        return taskExportExecutor;
    }

    /** 
     * sets the TaskExportExecutor
     * @param pTaskExportExecutor pTaskExportExecutor
     */
    public void setTaskExportExecutor(
            SimpleAsyncTaskExecutor pTaskExportExecutor) {
        this.taskExportExecutor = pTaskExportExecutor;
    }

    /**
     * is Export Complete?
     * @return true/false
     */
    public boolean isExportComplete() {
        return exportComplete;
    }

    /**
     * sets the ExportComplete flag
     * @param pExportComplete pExportComplete
     */
    public void setExportComplete(boolean pExportComplete) {
        this.exportComplete = pExportComplete;
    }

    /**
     * is Running ?
     * @return true/false
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * sets the Running flag
     * @param running true/false
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    
    /**
     * Observers update method
     * @param pExportState export state 
     */
    @Override
    public void update(MigrationExportState pExportState) {
        LOG.debug("MigrationExportManagerImpl==>update(), observer ");
        
        if (pExportState != null) {
            this.setExportState(pExportState);
            this.setRunning(pExportState.isRunning());
            this.setExportComplete(pExportState.isExportComplete());

            LOG.debug("update().isRunning: " + pExportState.isRunning());
            LOG.debug("update().isExportComplete: "
                    + pExportState.isExportComplete());
        }

    }

}
