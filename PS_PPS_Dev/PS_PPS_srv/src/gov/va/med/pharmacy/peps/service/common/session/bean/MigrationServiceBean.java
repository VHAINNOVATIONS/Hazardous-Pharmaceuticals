/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.session.bean;


import java.util.Map;

import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.migration.ExportCSVFileData;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationExportState;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationProcessState;
import gov.va.med.pharmacy.peps.service.common.session.MigrationProcessManager;
import gov.va.med.pharmacy.peps.service.common.session.MigrationService;
import gov.va.med.pharmacy.peps.service.common.session.impl.MigrationExportManager;





/**
 * Perform security actions on a user
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject"
 *                local-extends="javax.ejb.EJBLocalObject"
 */
public class MigrationServiceBean extends
        AbstractPepsStatelessSessionBean<MigrationService> implements
        MigrationService {
    private static final long serialVersionUID = 1L;

    /**
     * starts the Migration
     * 
     * @param user the user
     * @return MigrationProcessState MigrationProcessState
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public MigrationProcessState startMigration(UserVo user) {
        return getService().startMigration(user);
    }

    /**
     * stopMigration
     * 
     * @return UserVo containing user information
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public MigrationProcessState stopMigration() {
        return getService().stopMigration();
    }

    /**
     * pauseMigration
     * 
     * @return MigrationProcessState
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public MigrationProcessState pauseMigration() {
        return getService().pauseMigration();
    }

    /**
     * resumeMigration
     * 
     * @return MigrationProcessState  MigrationProcessState
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public MigrationProcessState resumeMigration() {
        return getService().resumeMigration();
    }

    /**
     * gets the MigrationProcessState
     * 
     * @return MigrationProcessState
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public MigrationProcessState getMigrationProcessState() {
        return getService().getMigrationProcessState();
    }

    /**
     * gets the MigrationProcessManager
     * @return MigrationProcessManager
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     */
    public MigrationProcessManager getMigrationProcessManager() {
        return getService().getMigrationProcessManager();
    }

    /**
     * sets the MigrationProcessManager
     * 
     * @param pMigrationprocessManager MigrationprocessManager
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     */
    public void setMigrationProcessManager(
            MigrationProcessManager pMigrationprocessManager) {
        getService().setMigrationProcessManager(pMigrationprocessManager);

    }

    /**
     * sets the MigrationProcessState
     * 
     * @param pState state
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     */
    public void setMigrationProcessState(MigrationProcessState pState) {
        getService().setMigrationProcessState(pState);

    }

    /**
     * resets the Database
     * 
     * @param iRow row

     * @return true/false
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public boolean resetDatabase(int iRow) {
        return getService().resetDatabase(iRow);

    }

    /**
     * populate the Migration Domains
     * 
     * @param pMigrationState pMigrationState
     * s
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     */
    @Override
    public void populateMigrationDomains(MigrationProcessState pMigrationState) {
        getService().populateMigrationDomains(pMigrationState);

    }

    /**
     * populate the MigrationDomain Errors
     * 
     * @param pMigrationState pMigrationState
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void populateMigrationDomainErrors(
            MigrationProcessState pMigrationState) {
        getService().populateMigrationDomainErrors(pMigrationState);

    }

    /**
     * gets Migration Export Manager
     * 
     * @return MigrationExportManager
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     */
    @Override
    public MigrationExportManager getMigrationExportManager() {
        return getService().getMigrationExportManager();
    }

    /**
     * set Migratio nExport Manager
     * 
     * @param pMigrationExportManager pMigrationExportManager

     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     */
    @Override
    public void setMigrationExportManager(
            MigrationExportManager pMigrationExportManager) {
        getService().setMigrationExportManager(pMigrationExportManager);
    }

    /**
     * starts Export Process
     * 
     * @return MigrationExportState
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public MigrationExportState startExportProcess() {
        return getService().startExportProcess();
    }

    /**
     * get Export Status
     * 
     * @return MigrationExportState
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public MigrationExportState getExportStatus() {
        return getService().getExportStatus();
    }

    /**
     * stops Export Process
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     */
    @Override
    public void stopExportProcess() {
        getService().stopExportProcess();
    }

    /**
     * clears Migration Error List
     * 
     * @param domainMap domainMap
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void clearMigrationErrorList(Map<Object, Object> domainMap) {
        getService().clearMigrationErrorList(domainMap);

    }

    /**
     * deletes All ImportFiles
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"

     */
    @Override
    public void deleteAllImportFiles() {
        getService().deleteAllImportFiles();
    }

    /**
     * delete Import File
     * 
     * @param pExportCsvFileData pExportCsvFileData

     * @return true/false
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     */
    @Override
    public boolean deleteImportFile(ExportCSVFileData pExportCsvFileData) {
        return getService().deleteImportFile(pExportCsvFileData);

    }

    /**
     * MigrationProcessState
     * 
     * @return MigrationProcessState 
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     */
    @Override
    public MigrationProcessState startDataBaseReset() {
        return getService().startDataBaseReset();
    }

    /**
     * gets the DataBase Reset Status
     * 
     * @return MigrationProcessState
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public MigrationProcessState getDataBaseResetStatus() {
        return getService().getDataBaseResetStatus();
    }
}
