/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.session;


import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.MigrationFileDomainCapability;
import gov.va.med.pharmacy.peps.service.common.capability.MigrationCapability;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationProcessState;


/**
 * MigrationProcess
 */
public interface MigrationProcess extends Runnable {

    /**
     * The run thread
     */
    void run();

    /**
     * suspends the process
     */
    void suspendProcess();

    /**
     * resumes the process
     */
    void resumeProcess();

    /**
     * stop the process
     */
    void stopProcess();

    /**
     * get the state
     * @return MigrationProcessState
     */
    MigrationProcessState getState();

    /**
     * setter for the MigrationProcessState
     * @param pState the state
     */
    void setState(MigrationProcessState pState);

    /**
     * getter for the MigrationCapability
     * @return MigrationCapability
     */
    MigrationCapability getMigrationCapability();

    /**
     * setter for the MigrationCapability
     * @param migrationCapability MigrationCapability
     */
    void setMigrationCapability(MigrationCapability migrationCapability);

    /**
     * getter for theMigrationCSVService
     * @return MigrationCSVService 
     */
    MigrationCSVService getMigrationCSVService();

    /**
     * setter for the MigrationCSVService
     * @param migrationCSVService  MigrationCSVService
     */
    void setMigrationCSVService(MigrationCSVService migrationCSVService);

    
    /**
     * setter for the user
     * @param user the user
     */
    void setUser(UserVo user);

    /**
     * get MigrationFileDomainCapability
     * @return MigrationFileDomainCapability
     */
    MigrationFileDomainCapability getMigrationFileDomainCapability();

    /**
     * setter for MigrationFileDomainCapability
     * @param pMigrationFileDomainCapability MigrationFileDomainCapability
     */
    void setMigrationFileDomainCapability(
            MigrationFileDomainCapability pMigrationFileDomainCapability);
}
