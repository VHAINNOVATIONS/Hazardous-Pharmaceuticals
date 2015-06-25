/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.MigrationErrorVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationFileVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.MigrationErrorDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.MigrationFileDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ResetNationalDatabaseDomainCapability;
import gov.va.med.pharmacy.peps.service.common.capability.MigrationCapability;
import gov.va.med.pharmacy.peps.service.common.migration.ErrorDetails;
import gov.va.med.pharmacy.peps.service.common.migration.ExportCSVFileData;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedDomain;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessDomainType;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessStatus;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationExportState;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationProcessState;
import gov.va.med.pharmacy.peps.service.common.session.MigrationProcessManager;
import gov.va.med.pharmacy.peps.service.common.session.MigrationService;


/**
 * This is the MigrationServiceImpl class.
 *
 */
public class MigrationServiceImpl implements MigrationService {

    private static final Logger LOG = Logger.getLogger(MigrationServiceImpl.class);
    private static final String POPULATE_DOMAINS = "populateMigrationDomains()==>";
    
    private MigrationProcessState myState;
    private MigrationProcessManager migrationProcessManager;
    private MigrationExportManager migrationExportManager;
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;
    private MigrationFileDomainCapability migrationFileDomainCapability;
    private MigrationErrorDomainCapability migrationErrorDomainCapability;
    private MigrationCapability migrationCapability;

    /**
     * This the default Constructor.
     */
    public MigrationServiceImpl() {
    }

    /**
     * startExportProcess.
     * @return MigrationExportState
     */
    public MigrationExportState startExportProcess() {
        return migrationExportManager.startExportProcess();
    }

    /**
     * stopExportProcess.
     */
    @Override
    public void stopExportProcess() {
        migrationExportManager.stopExportProcess();
    }

    /**
     * getExportStatus.
     * @return MigrationExportState
     */
    public MigrationExportState getExportStatus() {
        return migrationExportManager.getExportStatus();
    }

    /**
     * deleteAllImportFiles.
     */
    public void deleteAllImportFiles() {
        deleteImportFile(ExportCSVFileData.NDC);
        deleteImportFile(ExportCSVFileData.ORDERABLE_ITEMS);
        deleteImportFile(ExportCSVFileData.PRODUCT);
    }

    /**
     * deleteImportFile.
     * @param pExportCsvFileData ExportFile
     * @return true if successful
     */
    public boolean deleteImportFile(ExportCSVFileData pExportCsvFileData) {
        boolean success = false;

        try {
            
            //get import full path - this method does platform check (win/Linux)
            String path = pExportCsvFileData.getFullInportPath();
            File file = new File(path);
            success = file.delete();
        } catch (Exception e) {
            LOG.error("Error While deleting " + pExportCsvFileData.getInportFileName() + " because of the exception: "
                      + e.getMessage());
            success = false;
        }

        return success;
    }

    /**
     * resetDatbase.
     * @param iRow The sql file number
     * @return true if successful
     */
    @Override
    public boolean resetDatabase(int iRow) {
        
        // Now reset the database
        return resetNationalDatabaseDomainCapability.resetDatabase(iRow);
    }

    /**
     * populateMigrationDomains.
     * @param pMigrationState MigrationState
     */
    @Override
    public void populateMigrationDomains(MigrationProcessState pMigrationState) {
        MigratedDomain migratedDomain = null;
        Map<Object, Object> domainMap = pMigrationState.getDomainMapByFileId();
        List<MigrationFileVo> domainNames = migrationFileDomainCapability.retrieveMigrationFile();

        for (MigrationFileVo domain : domainNames) {

            LOG.error("populateMigrationDomains: Process domain: " + domain.getFileId());

            if (domain.getFileId().equals("50.608")) {

                // if fileId is packageType or Manufacturer, then get MigratedNdcDomain
                MigratedDomain migratedDomainNDC = (MigratedDomain) domainMap.get(PPSConstants.VA_NDC_FILE);
                migratedDomainNDC.setPackageTypesMigrated(domain.getRowsMigratedQty());
                LOG.error("populateMigrationDomains()==>Setting migratedNDCDomain: packageTypeMigrated: "
                          + migratedDomainNDC.getPackageTypesMigrated());
                continue;
            } else if (domain.getFileId().equals("55.95")) {

                // if fileId is Manufacturer, then get MigratedNdcDomain
                MigratedDomain migratedDomainNDC = (MigratedDomain) domainMap.get(PPSConstants.VA_NDC_FILE);
                migratedDomainNDC.setManufacturersMigrated(domain.getRowsMigratedQty());
                LOG.error("populateMigrationDomains()==>Setting migratedNDCDomain: manufacturersMigrated: "
                          + migratedDomainNDC.getManufacturersMigrated());
                continue;
            } else {
                migratedDomain = (MigratedDomain) domainMap.get(domain.getFileId());
            }

            //migratedDomain.setFileNumber(domain.getFileId());
            migratedDomain.setItemName(domain.getMigrationFileName());
            migratedDomain.setTotalErrors(domain.getErrorQty().intValue());
            migratedDomain.setDuplicatesNoMigrated(domain.getRowsNotMigratedQty().intValue());
            migratedDomain.setTotalMigrated(domain.getRowsMigratedQty().intValue());
            migratedDomain.setCount(domain.getRowsProcessedQty().intValue());

            LOG.debug(POPULATE_DOMAINS + domain.getMigrationFileName() + ".getFieldId(): "
                      + migratedDomain.getFileNumber());
            LOG.debug(POPULATE_DOMAINS + domain.getMigrationFileName() + ".getStrLastIEN(): "
                      + migratedDomain.getIen());
            LOG.debug(POPULATE_DOMAINS + domain.getMigrationFileName()
                      + ".getISuccerssfullyMigrated(): " + migratedDomain.getTotalMigrated());
            LOG.debug(POPULATE_DOMAINS + domain.getMigrationFileName() + ".getIErroredOnMigration(): "
                      + migratedDomain.getTotalErrors());
            LOG.debug(POPULATE_DOMAINS + domain.getMigrationFileName()
                      + ".getIDuplicatesNotMigrated(): " + migratedDomain.getDuplicatesNoMigrated());
            LOG.debug(POPULATE_DOMAINS + domain.getMigrationFileName() + ".getTotalRecords Processed: "
                      + migratedDomain.getCount());
            LOG.debug(POPULATE_DOMAINS + domain.getMigrationFileName() + ".getManufacturers: "
                      + migratedDomain.getManufacturersMigrated());
            LOG.debug(POPULATE_DOMAINS + domain.getMigrationFileName() + ".getPackageTypes: "
                      + migratedDomain.getPackageTypesMigrated());

        }

    }

    /**
     * populateMigrationDomainErrors
     * @param pMigrationState pMigrationState
     */
    @Override
    public void populateMigrationDomainErrors(MigrationProcessState pMigrationState) {
        MigratedDomain migratedDomain = null;
        Map<Object, Object> domainMap = pMigrationState.getDomainMapByFileId();
        clearMigrationErrorList(domainMap);
        List<MigrationErrorVo> migrationErrors = migrationErrorDomainCapability.retrieveMigrationError();

        for (MigrationErrorVo error : migrationErrors) {
            ErrorDetails errorDetails = new ErrorDetails();

            if (error.getFileId().equals(ProcessDomainType.PACKAGE_TYPES.getFileNumber())) {
                continue;

            } else if (error.getFileId().equals(ProcessDomainType.MANUFACTURES.getFileNumber())) {
                continue;
            } else {
                migratedDomain = (MigratedDomain) domainMap.get(error.getFileId());
                errorDetails.setFileId(error.getFileId());
            }

            errorDetails.setIen(error.getMigrationRowId());
            errorDetails.setItemName(error.getMigrationUniqueName());
            errorDetails.setField(error.getMigrationFieldName());
            errorDetails.setFieldValue(error.getMigrationFieldValue());
            errorDetails.setReasonForError(error.getDetailedErrorText());
            errorDetails.setMultipleField(error.getMigrationMultiFieldName());
            errorDetails.setMultipleIen(Integer.parseInt(error.getMigrationMultiRowId()));

            LOG.debug("populateMigrationDomainErrors()==> DOMAIN NAME==>>>>>: " + migratedDomain.getName());
            LOG.debug("populateMigrationDomainErrors()==> getFileId: " + errorDetails.getFileId());
            LOG.debug("populateMigrationDomainErrors()==> getIen: " + errorDetails.getIen());
            LOG.debug("populateMigrationDomainErrors()==> getField: " + errorDetails.getField());
            LOG.debug("populateMigrationDomainErrors()==> getFieldValue: " + errorDetails.getFieldValue());
            LOG.debug("populateMigrationDomainErrors()==> getItemName: " + errorDetails.getItemName());
            LOG.debug("populateMigrationDomainErrors()==> getMultipleField: " + errorDetails.getMultipleField());
            LOG.debug("populateMigrationDomainErrors()==> getMultipleIen: " + errorDetails.getMultipleIen());
            LOG.debug("populateMigrationDomainErrors()==> getReasonForError: " + errorDetails.getReasonForError());
            LOG.debug("----------------------------------------------------------------------------------");

            migratedDomain.getErrorDetailList().add(errorDetails);
        }

    }

    /**
     * startMigration.
     * @param user user
     * @return MigrationProcessState
     */
    @Override
    public MigrationProcessState startMigration(UserVo user) {
        if (myState == null || myState.getStatus().equals(ProcessStatus.STOPPED)) {
            myState = migrationProcessManager.startMigration(user);
        }

        return myState;
    }

    /**
     * stopMigration.
     * @return MigrationProcessState
     */
    @Override
    public MigrationProcessState stopMigration() {
        if (myState == null || !myState.getStatus().equals(ProcessStatus.STOPPED)) {
            myState = migrationProcessManager.stopMigration();
        }

        return myState;
    }

    /**
     * pauseMigration.
     * @return MigrationProcessState
     */
    @Override
    public MigrationProcessState pauseMigration() {
        if (myState.getStatus().equals(ProcessStatus.RUNNING) || myState.getStatus().equals(ProcessStatus.STARTED)
            || myState.getStatus().equals(ProcessStatus.RUNNING)) {
            myState = migrationProcessManager.pauseMigration();
        }

        return myState;
    }

    /**
     * resume Migration.
     * @return MigrationProcessState
     */
    @Override
    public MigrationProcessState resumeMigration() {
        if (myState.getStatus().equals(ProcessStatus.PAUSED)) {
            myState = migrationProcessManager.resumeMigration();
        }

        return myState;
    }

    /**
     * getMigrationProcessState().
     * @return MigrationProcessState
     */
    @Override
    public MigrationProcessState getMigrationProcessState() {
        return migrationProcessManager.getMigrationProcessState();
    }

    /**
     * setMigrationProcessState.
     * @param pState pState
     */
    @Override
    public void setMigrationProcessState(MigrationProcessState pState) {
        myState = pState;

    }

//    private boolean isMigrationRunning() {
//        return myState == null ? false : myState.isRunning();
//    }

    /**
     * getMigrationProcessManager.
     * @return MigrationProcessManager
     */
    @Override
    public MigrationProcessManager getMigrationProcessManager() {
        return migrationProcessManager;
    }

    /**
     * setMigrationProcessManager.
     * @param pMigrationprocessManager pMigrationprocessManager
     *
     */
    @Override
    public void setMigrationProcessManager(MigrationProcessManager pMigrationprocessManager) {
        this.migrationProcessManager = pMigrationprocessManager;
    }

    /**
     * setResetNationalDatabaseDomainCapability.
     * @param pResetNationalDatabaseDomainCapability pResetNationalDatabaseDomainCapability
     */
    public void setResetNationalDatabaseDomainCapability(ResetNationalDatabaseDomainCapability
            pResetNationalDatabaseDomainCapability) {
        this.resetNationalDatabaseDomainCapability = pResetNationalDatabaseDomainCapability;
    }

    /**
     * getMigrationFileDomainCapability.
     * @return MigrationFileDomainCapability
     */
    public MigrationFileDomainCapability getMigrationFileDomainCapability() {
        return migrationFileDomainCapability;
    }

    /**
     * setMigrationFileDomainCapability.
     * @param pMigrationFileDomainCapability pMigrationFileDomainCapability
     */
    public void setMigrationFileDomainCapability(MigrationFileDomainCapability pMigrationFileDomainCapability) {
        this.migrationFileDomainCapability = pMigrationFileDomainCapability;
    }

    /**
     * getMigrationCapability.
     * @return MigrationCapability
     */
    public MigrationCapability getMigrationCapability() {
        return migrationCapability;
    }

    /**
     * setMigrationCapability
     * @param pMigrationCapability pMigrationCapability
     */
    public void setMigrationCapability(MigrationCapability pMigrationCapability) {
        this.migrationCapability = pMigrationCapability;
    }

    /**
     * getMigrationErrorDomainCapability.
     * @return MigrationErrorDomainCapability
     */
    public MigrationErrorDomainCapability getMigrationErrorDomainCapability() {
        return migrationErrorDomainCapability;
    }

    /**
     * setMigrationErrorDomainCapability.
     * @param pMigrationErrorDomainCapability pMigrationErrorDomainCapability
     */
    public void setMigrationErrorDomainCapability(MigrationErrorDomainCapability pMigrationErrorDomainCapability) {
        this.migrationErrorDomainCapability = pMigrationErrorDomainCapability;
    }

    /**
     * getMigrationExportManager.
     * @return migrationExportManager
     */
    public MigrationExportManager getMigrationExportManager() {
        return migrationExportManager;
    }

    /**
     * setMigrationExportManager.
     * @param pMigrationExportManager pMigrationExportManager
     */
    public void setMigrationExportManager(MigrationExportManager pMigrationExportManager) {
        this.migrationExportManager = pMigrationExportManager;
    }

    /**
     * clearMigrationErrorList.
     * @param domainMap domainMap
     */
    @Override
    public void clearMigrationErrorList(Map<Object, Object> domainMap) {
        resetErrorList(domainMap, ProcessDomainType.DRUG_UNITS_ACTIVE);
        resetErrorList(domainMap, ProcessDomainType.VA_DISPENSE_UNIT_ACTIVE);
        resetErrorList(domainMap, ProcessDomainType.VA_GENERIC_NAME_ACTIVE);
        resetErrorList(domainMap, ProcessDomainType.DOSAGE_FORM_ACTIVE);
        resetErrorList(domainMap, ProcessDomainType.DRUG_CLASS_0);
        resetErrorList(domainMap, ProcessDomainType.DRUG_INGREDIENTS_ACTIVE);
        resetErrorList(domainMap, ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE);
        resetErrorList(domainMap, ProcessDomainType.VA_PRODUCT_ACTIVE);
        resetErrorList(domainMap, ProcessDomainType.NDC_CSV_FILE_ACTIVE);

    }

    /**
     * resetErrorList.
     * @param domainMap domainMap
     * @param drugUnitsActive drugUnitsActive
     */
    private void resetErrorList(Map<Object, Object> domainMap, ProcessDomainType drugUnitsActive) {
        MigratedDomain domain = (MigratedDomain) domainMap.get(drugUnitsActive.getFileNumber());
        domain.getErrorDetailList().clear();
    }

    /**
     * startDataBaseReset.
     * @return MigrationProcessState
     */
    @Override
    public MigrationProcessState startDataBaseReset() {
        if (myState == null || !(myState.getStatus() == ProcessStatus.DB_RESET_RUNNING)) {
            myState = migrationProcessManager.startDataBaseReset();
        }

        return myState;
    }

    /**
     * getDataBaseResetStatus.
     * @return MigrationProcessState
     */
    @Override
    public MigrationProcessState getDataBaseResetStatus() {
        myState = migrationProcessManager.getDataBaseResetState();
        
        return myState;
    }

}
