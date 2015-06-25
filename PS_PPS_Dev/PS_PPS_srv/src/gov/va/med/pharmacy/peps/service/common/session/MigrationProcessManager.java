/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.session;


import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationProcessState;


/**
 * MigrationProcessManager
 */
public interface MigrationProcessManager {

    /**
     * Starts the migration process
     * 
     * @param user   user
     * @return returns the MigrationProcessState
     */
    MigrationProcessState startMigration(UserVo user);

    /**
     * Stops the migration process
     * 
     * @return MigrationProcessState
     */
    MigrationProcessState stopMigration();

    /**
     * pauses the migration process
     * 
     * @return MigrationProcessState
     */
    MigrationProcessState pauseMigration();

    /**
     * resumes the migration process
     * 
     * @return MigrationProcessState
     */
    MigrationProcessState resumeMigration();

    /**
     * returns the migrationProcessState
     * 
     * @return MigrationProcessState
     */
    MigrationProcessState getMigrationProcessState();

    /**
     * setter for UserVo
     * @param pUser the user
     */
    void setUser(UserVo pUser);

    /**
     * getter for UserVo
     * 
     * @return UserVo
     */
    UserVo getUser();

    /**
     * returns the MigrationProcessState
     * @return MigrationProcessState
     */
    MigrationProcessState startDataBaseReset();

    /**
     * returns the MigrationProcessState
     * @return MigrationProcessState
     */
    MigrationProcessState getDataBaseResetState();

}
