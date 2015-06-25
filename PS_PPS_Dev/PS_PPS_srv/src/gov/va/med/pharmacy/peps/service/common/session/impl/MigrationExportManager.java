/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.session.impl;


import org.springframework.core.task.SimpleAsyncTaskExecutor;

import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationExportState;
import gov.va.med.pharmacy.peps.service.common.session.MigrationExportProcess;


/**
 * 
 * MigrationExportManager
 */
public interface MigrationExportManager {

    /**
     * getter for the MigrationExport Process
     *
     * @return MigrationExportProcess
     */
    MigrationExportProcess getMigrationExportProcess();
    
    /**
     * setter for the Export process
     * @param pMigrationExportProcess pMigrationExportProcess
     */
    void setMigrationExportProcess(MigrationExportProcess pMigrationExportProcess);
    
    /**
     * starts the Export Process
     * @return MigrationExportState MigrationExportState
     */
    MigrationExportState startExportProcess();
    
    /**
     * returns the XxportStatus
     * @return MigrationExportState
     */
    MigrationExportState getExportStatus();
    
    /**
     * gets the TaskExportExecutor
     * @return SimpleAsyncTaskExecutor
     */
    SimpleAsyncTaskExecutor getTaskExportExecutor();
    
    /**
     * setter for the SimpleAsyncTaskExecutor
     * @param pTaskExportExecutor TaskExportExecutor
     */
    void setTaskExportExecutor(SimpleAsyncTaskExecutor pTaskExportExecutor);
    
    /**
     * stops Export process
     */
    void stopExportProcess();
    
}
