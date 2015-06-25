/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import org.apache.log4j.Logger;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessCommand;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationProcessState;
import gov.va.med.pharmacy.peps.service.common.session.MigrationProcess;
import gov.va.med.pharmacy.peps.service.common.session.MigrationProcessManager;


/**
 * MigrationProcessManagerImpl
 */
public class MigrationProcessManagerImpl implements MigrationProcessManager {

    private static final Logger LOG = Logger.getLogger(MigrationProcessManagerImpl.class);

    private MigrationProcessState state;
    private MigrationProcess migrationProcess;
    private boolean processInitialized;
    private SimpleAsyncTaskExecutor taskExecutor;
    private UserVo user;

    /**
     * gets the TaskExecutor
     * @return SimpleAsyncTaskExecutor
     */
    public SimpleAsyncTaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    /**
     * sets the TaskExecutor
     *
     * @param taskExecutor taskExecutor
     */
    public void setTaskExecutor(SimpleAsyncTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * start migration process
     * @param userIn user
     * @return MigrationProcessState MigrationProcessState
     */
    public MigrationProcessState startMigration(UserVo userIn) {
        if (!isProcessInitialized()) {
            initializeProcess();
        }

        schedulerCommand(ProcessCommand.START);
        
        return state;
    }

    /**
     * Stops the migration Process
     * @return Migration State
     */
    public MigrationProcessState stopMigration() {
        schedulerCommand(ProcessCommand.STOP);
        state = getMigrationProcessState();
        processInitialized = false;
        
        return state;
    }

    /**
     * Pauses the migration process
     * @return  Migration State
     */
    public MigrationProcessState pauseMigration() {
        schedulerCommand(ProcessCommand.PAUSE);
        state = getMigrationProcessState();
        
        return state;
    }

    /**
     * resumes the Migration process
     * 
     * @return  Migration State
     */
    public MigrationProcessState resumeMigration() {
        schedulerCommand(ProcessCommand.RESUME);
        state = getMigrationProcessState();
        
        return state;
    }

    /**
     * shedulerCommand
     * @param pCommand pCommand
     */
    private void schedulerCommand(ProcessCommand pCommand) {
        switch (pCommand) {
        
            case START:
                if (this.getMigrationProcess() != null) {
                    LOG.debug("MigrationProcessManager.schedulerCommand()==>>>migration process is null");
                } else {
                    LOG.debug("MigrationProcessManager.schedulerCommand()==>>>migration process is NOT  null");
                }
                
                // delay to allow for the state to be initialized.
                
                state = migrationProcess.getState();
                migrationProcess.setUser(this.getUser());
                break;
            case STOP:
                migrationProcess.stopProcess();
                migrationProcess.getState();
                break;
            case PAUSE:
                migrationProcess.suspendProcess();
                break;
            case RESUME:
                migrationProcess.resumeProcess();
                break;
            case STATUS:
                if (migrationProcess != null) {
                    state = migrationProcess.getState();
                    break;
                }
            case DB_START:
                taskExecutor.execute(migrationProcess);
                sleepThread(PPSConstants.I2000); // delay to allow for the state to be initialized.
                state = migrationProcess.getState();
                break;
            case DB_STATUS:// could use the STATUS and remove this one.
                if (migrationProcess != null) {
                    state = migrationProcess.getState();
                }
                
                break;
            default:
                break;
        }
    }

    /**
     * gets the User
     * @return UserVo user
     */
    public UserVo getUser() {
        return this.user;
    }

    /**
     * setter for the User
     * @param pUser user 
     */
    public void setUser(UserVo pUser) {
        this.user = pUser;
    }
    
    /**
     * Get the migration process state
     * @return MigrationProcessState MigrationProcessState
     */
    @Override
    public MigrationProcessState getMigrationProcessState() {
        schedulerCommand(ProcessCommand.STATUS);
        
        return state;
    }

    /**
     * initializeProcess
     */
    private void initializeProcess() {
        processInitialized = true;
    }

    /**
     * sleep Thread
     *
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
     * process init?
     * @return true/false
     */
    public boolean isProcessInitialized() {
        return processInitialized;
    }

    /**
     * sets the ProcessInitialized flag
     * @param processInitialized processInitialized
     */
    public void setProcessInitialized(boolean processInitialized) {
        this.processInitialized = processInitialized;
    }

    /**
     * gets the MigrationProcess
     * @return MigrationProcess MigrationProcess
     */
    public MigrationProcess getMigrationProcess() {
        return migrationProcess;
    }

    /**
     * setMigrationProcess
     * @param pMigrationProcess pMigrationProcess
     */
    public void setMigrationProcess(MigrationProcess pMigrationProcess) {
        this.migrationProcess = pMigrationProcess;
    }

    /**
     * starts the DataBase Reset
     * @return MigrationProcessState MigrationProcessState
     */
    @Override
    public MigrationProcessState startDataBaseReset() {
        schedulerCommand(ProcessCommand.DB_START);
        
        return state;
    }

    /**
     * gets the DataBaseReset State
     *@return MigrationProcessState MigrationProcessState
     */
    @Override
    public MigrationProcessState getDataBaseResetState() {
        schedulerCommand(ProcessCommand.DB_STATUS);
        
        return state;
    }

}
