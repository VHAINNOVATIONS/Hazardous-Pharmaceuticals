/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration.process;


import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.service.common.migration.DatabaseResetErrorMessage;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedDomain;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedDosageForm;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedDrugClass;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedDrugUnit;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedIngredients;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedNDCCsvFile;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedOrderableItemsCsv;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedVaDispenseUnit;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedVaGenericeName;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedVaProduct;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessDomainType;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessStatus;


/**
 * MigrationProcessState
 *
 */
public class MigrationProcessState implements Serializable {

    /**
     * DB_MAX_COUNT
     */
    public static final int DB_MAX_COUNT = 16;
    
    private static final long serialVersionUID = 1L;
    private int progressCount;    

    private MigratedDomain migratedDrugUnit = new MigratedDrugUnit();
    private MigratedDomain migratedVaDispenseUnit = new MigratedVaDispenseUnit();
    private MigratedDomain migratedVaGenericName = new MigratedVaGenericeName();
    private MigratedDomain migratedDosageForm = new MigratedDosageForm();
    private MigratedDomain migratedDrugClass = new MigratedDrugClass();
    private MigratedDomain migratedIngredients = new MigratedIngredients();
    private MigratedDomain migratedOrderableItemsCsv = new MigratedOrderableItemsCsv();
    private MigratedDomain migratedVaProduct = new MigratedVaProduct();
    private MigratedDomain migratedNDCCsvFile = new MigratedNDCCsvFile();

    private List<MigratedDomain> migratedDomainList = new LinkedList<MigratedDomain>();
    private Map<Object, Object> migratedDomainMap = new HashMap<Object, Object>();

    
    private ProcessStatus status;
    private ProcessStatus dbStatus;
    private boolean migrationProceessCompleted;

    private boolean running;
    private int overAllProgress;
    private String pauseButtonText;
    private int recordCount;
    private boolean runningEof;
    private boolean migrationComplete;
    private boolean dbResetRunning;
    private double dbCount;
    private int dbPercent;
    private DatabaseResetErrorMessage databaseResetErrorMessage;
    private boolean databaseResetCompleted;

    /**
     * getDbMaxCount
     * @return DB_MAX_COUNT
     */
    public static int getDbMaxCount() {
        return DB_MAX_COUNT;
    }
    
    /**
    * returns the number of records migrated
    * @return progressCount
    */
    public int getProgressCount() {
        return progressCount;
    }

    /**
    * setter for the progess count
    * @param pCount pCount
    */
    public void setProgressCount(int pCount) {
        progressCount = pCount;
    }

    /**
     * returns process status
     * @return status
     */
    public ProcessStatus getStatus() {
        return status;
    }

    /**
     * This method sets the ProcessStatus for the ProcessState.
     * @param pStatus pStatus
     */
    public void setStatus(ProcessStatus pStatus) {
        this.status = pStatus;
    }

    /**
     * setRunning
     * @param running running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * isRunning
     * @return running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * getOverAllProgress
     * @return overAllProgress
     */
    public int getOverAllProgress() {
        return overAllProgress;
    }

    /**
     * setOverAllProgress
     * @param pOverAllProgress pOverAllProgress
     */
    public void setOverAllProgress(int pOverAllProgress) {
        this.overAllProgress = pOverAllProgress;
    }

    /**
     * getPauseButtonText
     * @return pauseButtonText
     */
    public String getPauseButtonText() {
        return pauseButtonText;
    }

    /**
     * setPauseButtonText
     * @param pauseButtonText pauseButtonText
     */
    public void setPauseButtonText(String pauseButtonText) {
        this.pauseButtonText = pauseButtonText;
    }

    /**
     * getMigratedDrugUnit
     * @return migratedDrugUnit
     */
    public MigratedDomain getMigratedDrugUnit() {
        return migratedDrugUnit;
    }

    /**
     * setMigratedDrugUnit
     * @param pMigratedDrugUnit pMigratedDrugUnit
     */
    public void setMigratedDrugUnit(MigratedDomain pMigratedDrugUnit) {
        this.migratedDrugUnit = pMigratedDrugUnit;
    }

    /**
     * getMigratedVaDispenseUnit
     * @return migratedVaDispenseUnit
     */
    public MigratedDomain getMigratedVaDispenseUnit() {
        return migratedVaDispenseUnit;
    }

    /**
     * setMigratedVaDispenseUnit
     * @param pMigratedVaDispenseUnit pMigratedVaDispenseUnit
     */
    public void setMigratedVaDispenseUnit(MigratedDomain pMigratedVaDispenseUnit) {
        this.migratedVaDispenseUnit = pMigratedVaDispenseUnit;
    }

    /**
     * getMigratedVaGenericName
     * @return migratedVaGenericName
     */
    public MigratedDomain getMigratedVaGenericName() {
        return migratedVaGenericName;
    }

    /**
     * setMigratedVaGenericName
     * @param pMigratedVaGenericName migratedVaGenericName
     */
    public void setMigratedVaGenericName(MigratedDomain pMigratedVaGenericName) {
        this.migratedVaGenericName = pMigratedVaGenericName;
    }

    /**
     * getMigratedDosageForm
     * @return migratedDosageForm
     */
    public MigratedDomain getMigratedDosageForm() {
        return migratedDosageForm;
    }

    /**
     * setMigratedDosageForm
     * @param pMigratedDosageForm pMigratedDosageForm
     */
    public void setMigratedDosageForm(MigratedDomain pMigratedDosageForm) {
        this.migratedDosageForm = pMigratedDosageForm;
    }

    /**
     * getMigratedDrugClass
     * @return migratedDrugClass
     */
    public MigratedDomain getMigratedDrugClass() {
        return migratedDrugClass;
    }

    /**
     * setMigratedDrugClass
     * @param pMigratedDrugClass pMigratedDrugClass
     */
    public void setMigratedDrugClass(MigratedDomain pMigratedDrugClass) {
        this.migratedDrugClass = pMigratedDrugClass;
    }

    /**
     * getMigratedIngredients
     * @return migratedIngredients
     */
    public MigratedDomain getMigratedIngredients() {
        return migratedIngredients;
    }

    /**
     * setMigratedIngredients
     * @param pMigratedIngredients pMigratedIngredients
     */
    public void setMigratedIngredients(MigratedDomain pMigratedIngredients) {
        this.migratedIngredients = pMigratedIngredients;
    }

    /**
     * getMigratedOrderableItemsCsv
     * @return MigratedDomain
     */
    public MigratedDomain getMigratedOrderableItemsCsv() {
        return migratedOrderableItemsCsv;
    }

    /**
     * setMigratedOrderableItemsCsv
     * @param pMigratedOrderableItemsCsv migratedOrderableItemsCsv
     */
    public void setMigratedOrderableItemsCsv(MigratedDomain pMigratedOrderableItemsCsv) {
        this.migratedOrderableItemsCsv = pMigratedOrderableItemsCsv;
    }

    /**
     * getMigratedVaProduct
     * @return MigratedDomain
     */
    public MigratedDomain getMigratedVaProduct() {
        return migratedVaProduct;
    }

    /**
     * setMigratedVaProduct
     * @param pMigratedVaProduct migratedVaProduct
     */
    public void setMigratedVaProduct(MigratedDomain pMigratedVaProduct) {
        this.migratedVaProduct = pMigratedVaProduct;
    }
    
    /**
     * setMigratedNDCCsvFile
     * @param pMigratedNDCCsvFile pMigratedNDCCsvFile
     */
    public void setMigratedNDCCsvFile(MigratedDomain pMigratedNDCCsvFile) {
        this.migratedNDCCsvFile = pMigratedNDCCsvFile;
    }

    /**
     * getMigratedNDCCsvFile
     * @return MigratedDomain
     */
    public MigratedDomain getMigratedNDCCsvFile() {
        return migratedNDCCsvFile;
    }

    /**
     * setRecordCount
     * @param pRecordCount pRecordCount
     */
    public void setRecordCount(int pRecordCount) {
        this.recordCount = pRecordCount;
    }

    /**
     * getRecordCount
     * @return recordCount
     */
    public int getRecordCount() {
        return this.recordCount;
    }

    /**
     * setRunningEof
     * @param pRunningEof pRunningEof
     */
    public void setRunningEof(boolean pRunningEof) {
        this.runningEof = pRunningEof;
    }

    /**
     * isRunningEof
     * @return runningEof
     */
    public boolean isRunningEof() {
        return this.runningEof;
    }

    /**
     * setMigratedDomainList
     * @param migratedDomainList migratedDomainList
     */
    public void setMigratedDomainList(List<MigratedDomain> migratedDomainList) {
        this.migratedDomainList = migratedDomainList;
    }

    /**
     * getMigratedDomainList
     * 
     * @return createMigratedDomainList
     */
    public List<MigratedDomain> getMigratedDomainList() {
        return createMigratedDomainList();
    }

    /**
     * getMigratedDomainMap
     * @return migratedDomainMap
     */
    public Map<Object, Object> getMigratedDomainMap() {
        return migratedDomainMap;
    }

    /**
     * setMigratedDomainMap
     * @param pMigratedDomainMap migratedDomainMap
     */
    public void setMigratedDomainMap(Map<Object, Object> pMigratedDomainMap) {
        this.migratedDomainMap = pMigratedDomainMap;
    }

    /**
     * isMigrationComplete
     * @return migrationComplete
     */
    public boolean isMigrationComplete() {
        return migrationComplete;
    }

    /**
     * setMigrationComplete
     * @param migrationComplete migrationComplete
     */
    public void setMigrationComplete(boolean migrationComplete) {
        this.migrationComplete = migrationComplete;
    }

    /**
     * isDbResetRunning
     * @return dbResetRunning
     */
    public boolean isDbResetRunning() {
        return dbResetRunning;
    }

    /**
     * setDbResetRunning
     * @param pDbResetRunning pDbResetRunning
     */
    public void setDbResetRunning(boolean pDbResetRunning) {
        this.dbResetRunning = pDbResetRunning;
    }

    /**
     * getDbStatus
     * @return dbStatus
     */
    public ProcessStatus getDbStatus() {
        return dbStatus;
    }

    /**
     * setDbStatus
     * @param pDbStatus pDbStatus
     */
    public void setDbStatus(ProcessStatus pDbStatus) {
        this.dbStatus = pDbStatus;
    }

    /**
     * setDbCount
     * @param pDbCount pDbCount
     */
    public void setDbCount(double pDbCount) {
        this.dbCount = pDbCount;
    }

    /**
     * getDbCount
     * @return dbCount
     */
    public double getDbCount() {
        return dbCount;
    }

    /**
     * isMigrationProceessCompleted
     * @return migrationProceessCompleted
     */
    public boolean isMigrationProceessCompleted() {
        return migrationProceessCompleted;
    }

    /**
     * setMigrationProceessCompleted
     * @param migrationProceessCompleted migrationProceessCompleted
     */
    public void setMigrationProceessCompleted(boolean migrationProceessCompleted) {
        this.migrationProceessCompleted = migrationProceessCompleted;
    }

    /**
     * getDatabaseResetErrorMessage
     * @return databaseResetErrorMessage
     */
    public DatabaseResetErrorMessage getDatabaseResetErrorMessage() {
        return databaseResetErrorMessage;
    }

    /**
     * setDatabaseResetErrorMessage
     * @param pDatabaseResetErrorMessage pDatabaseResetErrorMessage
     */
    public void setDatabaseResetErrorMessage(DatabaseResetErrorMessage pDatabaseResetErrorMessage) {
        this.databaseResetErrorMessage = pDatabaseResetErrorMessage;
    }

    /**
     * isDatabaseResetCompleted
     * @return databaseResetCompleted
     */
    public boolean isDatabaseResetCompleted() {
        return databaseResetCompleted;
    }

    /**
     * setDatabaseResetCompleted
     * @param pDatabaseResetCompleted pDatabaseResetCompleted
     */
    public void setDatabaseResetCompleted(boolean pDatabaseResetCompleted) {
        this.databaseResetCompleted = pDatabaseResetCompleted;
    }

    /**
     * getDbPercent
     * @return dbPercent
     */
    public int getDbPercent() {
        return dbPercent;
    }

    /**
     * setDbPercent
     * @param pDbPercent pDbPercent
     */
    public void setDbPercent(int pDbPercent) {
        this.dbPercent = pDbPercent;
    }

    /**
     * createDomainMap
     * maps domain object to type
     * @return Map<Object, Object>
     */
    public Map<Object, Object> createDomainMap() {
        migratedDomainMap.put(ProcessDomainType.DRUG_UNITS_ACTIVE, this.getMigratedDrugUnit());
        migratedDomainMap.put(ProcessDomainType.VA_DISPENSE_UNIT_ACTIVE, this.getMigratedVaDispenseUnit());
        migratedDomainMap.put(ProcessDomainType.VA_GENERIC_NAME_ACTIVE, this.getMigratedVaGenericName());
        migratedDomainMap.put(ProcessDomainType.DOSAGE_FORM_ACTIVE, this.getMigratedDosageForm());
        migratedDomainMap.put(ProcessDomainType.DRUG_CLASS_0, this.getMigratedDrugClass());
        migratedDomainMap.put(ProcessDomainType.DRUG_INGREDIENTS_ACTIVE, this.getMigratedIngredients());
        migratedDomainMap.put(ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE, this.getMigratedOrderableItemsCsv());
        migratedDomainMap.put(ProcessDomainType.VA_PRODUCT_ACTIVE, this.getMigratedVaProduct());
        migratedDomainMap.put(ProcessDomainType.NDC_CSV_FILE_ACTIVE, this.getMigratedNDCCsvFile());

        return migratedDomainMap;
    }

    /**
     * getDomainMapByFileId
     * 
     * @return Map
     */
    public Map<Object, Object> getDomainMapByFileId() {
        migratedDomainMap.put(String.valueOf(ProcessDomainType.DRUG_UNITS_ACTIVE.getFileNumber()),
                              this.getMigratedDrugUnit());
        migratedDomainMap.put(String.valueOf(ProcessDomainType.VA_DISPENSE_UNIT_ACTIVE.getFileNumber()),
                              this.getMigratedVaDispenseUnit());
        migratedDomainMap.put(String.valueOf(ProcessDomainType.VA_GENERIC_NAME_ACTIVE.getFileNumber()),
                              this.getMigratedVaGenericName());
        migratedDomainMap.put(String.valueOf(ProcessDomainType.DOSAGE_FORM_ACTIVE.getFileNumber()),
                              this.getMigratedDosageForm());
        migratedDomainMap.put(String.valueOf(ProcessDomainType.DRUG_CLASS_0.getFileNumber()),
                              this.getMigratedDrugClass());
        migratedDomainMap.put(String.valueOf(ProcessDomainType.DRUG_INGREDIENTS_ACTIVE.getFileNumber()),
                              this.getMigratedIngredients());
        migratedDomainMap.put(String.valueOf(ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE.getFileNumber()),
                              this.getMigratedOrderableItemsCsv());
        migratedDomainMap.put(String.valueOf(ProcessDomainType.VA_PRODUCT_ACTIVE.getFileNumber()),
                              this.getMigratedVaProduct());
        migratedDomainMap.put(String.valueOf(ProcessDomainType.NDC_CSV_FILE_ACTIVE.getFileNumber()),
                              this.getMigratedNDCCsvFile());

        return migratedDomainMap;
    }

    /**
     * createMigratedDomainList
     * 
     * @return List<MigratedDomain>
     */
    private List<MigratedDomain> createMigratedDomainList() {
        migratedDomainList.clear();

        migratedDomainList.add(this.getMigratedDrugUnit());
        migratedDomainList.add(this.getMigratedVaDispenseUnit());
        migratedDomainList.add(this.getMigratedVaGenericName());
        migratedDomainList.add(this.getMigratedDosageForm());
        migratedDomainList.add(this.getMigratedDrugClass());
        migratedDomainList.add(this.getMigratedIngredients());
        migratedDomainList.add(this.getMigratedOrderableItemsCsv());
        migratedDomainList.add(this.getMigratedVaProduct());
        migratedDomainList.add(this.getMigratedNDCCsvFile());

        return migratedDomainList;

    }

    /**
     * getMigratedDomain
     * @param pProcessDomainType pProcessDomainType
     * @return MigratedDomain
     */
    public MigratedDomain getMigratedDomain(ProcessDomainType pProcessDomainType) {

        switch (pProcessDomainType) {
            case DRUG_UNITS_INACTIVE:
                return this.getMigratedDrugUnit();
            case DRUG_UNITS_ACTIVE:
                return this.getMigratedDrugUnit();
            case VA_DISPENSE_UNIT_ACTIVE:
                return this.getMigratedVaDispenseUnit();
            case VA_DISPENSE_UNIT_INACTIVE:
                return this.getMigratedVaDispenseUnit();
            case VA_GENERIC_NAME_ACTIVE:
                return this.getMigratedVaGenericName();
            case VA_GENERIC_NAME_INACTIVE:
                return this.getMigratedVaGenericName();
            case DOSAGE_FORM_ACTIVE:
                return this.getMigratedDosageForm();
            case DOSAGE_FORM_INACTIVE:
                return this.getMigratedDosageForm();
            case DRUG_CLASS_0:
                return this.getMigratedDrugClass();
            case DRUG_CLASS_1:
                return this.getMigratedDrugClass();
            case DRUG_CLASS_2:
                return this.getMigratedDrugClass();
            case DRUG_INGREDIENTS_ACTIVE:
                return this.getMigratedIngredients();
            case DRUG_INGREDIENTS_INACTIVE:
                return this.getMigratedIngredients();
            case DRUG_INGREDIENTS_LIST:
                return this.getMigratedIngredients();
            case ORDERABLE_ITEMS_CSV_FILE_ACTIVE:
                return this.getMigratedOrderableItemsCsv();
            case VA_PRODUCT_ACTIVE:
                return this.getMigratedVaProduct();
            case VA_PRODUCT_INACTIVE:
                return this.getMigratedVaProduct();
            case NDC_CSV_FILE_ACTIVE:
                return this.getMigratedNDCCsvFile();
            default:
                return null;

        }

    }

}
