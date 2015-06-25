/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.migration.ExportCSVFileData;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationExportState;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationProcessState;
import gov.va.med.pharmacy.peps.service.common.session.impl.MigrationExportManager;


/**
 * MigrationService
 */
public interface MigrationService {

    /**
     * starts The migration process
     * @param userVo the user
     * @return MigrationProcessState
     */
    MigrationProcessState startMigration(UserVo userVo);

    /**
     * reset the database
     * @param iRow the row
     * @return true/false
     */
    boolean resetDatabase(int iRow);

    /**
     * stop the migration
     * @return MigrationProcessState
     */
    MigrationProcessState stopMigration();

    /**
     * pause the migration
     * @return MigrationProcessState
     */
    MigrationProcessState pauseMigration();

    /**
     * resume the migration
     * @return MigrationProcessState
     */
    MigrationProcessState resumeMigration();

    
    /**
     * get the migrationProcessState
     * @return MigrationProcessState
     */
    MigrationProcessState getMigrationProcessState();

    /**
     * setter for the MigrationProcessState
     * @param pState MigrationProcessState
     */
    void setMigrationProcessState(MigrationProcessState pState);

    /**
     * getter for the MigrationProcessManager
     * @return MigrationProcessManager
     */
    MigrationProcessManager getMigrationProcessManager();

    /**
     * setter for the MigrationProcessManager
     * @param pMigrationprocessManager MigrationProcessManager
     */
    void setMigrationProcessManager(
            MigrationProcessManager pMigrationprocessManager);

    /**
     * populates the Migration Domains
     * @param pMigrationState pMigrationState
     */
    void populateMigrationDomains(MigrationProcessState pMigrationState);

    /**
     * populate Migration Domain Errors
     * @param pMigrationState pMigrationState
     */
    void populateMigrationDomainErrors(
            MigrationProcessState pMigrationState);

    /**
     * get the Migration Export Manager
     * @return MigrationExportManager
     */
    MigrationExportManager getMigrationExportManager();

    /**
     * setter for the MigrationExportManager
     * @param pMigrationExportManager pMigrationExportManager
     */
    void setMigrationExportManager(
            MigrationExportManager pMigrationExportManager);

    /**
     * 
     * start the Export Process
     * @return MigrationExportState
     */
    MigrationExportState startExportProcess();

    /**
     * gets the Export Status
     * @return MigrationExportState
     */
    MigrationExportState getExportStatus();

    /**
     * stops the Export Process
     */
    void stopExportProcess();

    /**
     * clears the Migration Error List
     * @param domainMap domain map
     */
    void clearMigrationErrorList(Map<Object, Object> domainMap);

    /**
     * deletes All the Import Files
     */
    void deleteAllImportFiles();

    /**
     * deletes the Import File
     * @param pExportCsvFileData pExportCsvFileData
     * @return true/false
     */
    boolean deleteImportFile(ExportCSVFileData pExportCsvFileData);

    
    /**
     * starts the DataBase Reset process
     * @return MigrationProcessState
     */
    MigrationProcessState startDataBaseReset();

    /**
     * gets the DataBase ResetStatus
     * @return MigrationProcessState
     */
    MigrationProcessState getDataBaseResetStatus();

}
