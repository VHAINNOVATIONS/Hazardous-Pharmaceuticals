/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


import java.util.ArrayList;
import java.util.List;


/**
 * MigratedDomainBase
 *
 */
public class MigratedDomainBase implements MigratedDomain {

    private String percent;
    private int count;
    private int duplicatesNoMigrated;
    private int totalErrors;
    private int totalMigrated;
    private int ien;
    private String itemName;
    private String fieldName;
    private String fieldValue;
    private String reasonForError;
    private boolean endOfFile;
    private int maxRecords;
    private ProcessDomainType processDomainType;
    private ErrorDetails errorDetails;
    private List<ErrorDetails> errorDetailList = new ArrayList<ErrorDetails>();

    /**
     * MigratedDomainBase
     */
    public MigratedDomainBase() {
        errorDetails = new ErrorDetails();
    }

    /**
     * getPercent
     * @return percent
     */
    public String getPercent() {
        return percent;
    }

    /**
     * setPercent
     * @param pPercent pPercent
     */
    public void setPercent(String pPercent) {
        percent = pPercent;
    }

    /**
     * getCount
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * setCount
     * @param pCount pCount
     */
    public void setCount(int pCount) {
        count = pCount;
    }

    /**
     * getDuplicatesNoMigrated() 
     * @return duplicatesNoMigrated;
     */
    public int getDuplicatesNoMigrated() {
        return duplicatesNoMigrated;
    }

    /**
     * setDuplicatesNoMigrated
     * @param  pDuplicatesNoMigrated pDuplicatesNoMigrated
     */
    public void setDuplicatesNoMigrated(int pDuplicatesNoMigrated) {
        duplicatesNoMigrated = pDuplicatesNoMigrated;
    }

    /**
     * getTotalErrors
     * @return totalErrors
     */
    public int getTotalErrors() {
        return totalErrors;
    }

    /**
     * setTotalErrors
     * @param pTotalErrors pTotalErrors
     */
    public void setTotalErrors(int pTotalErrors) {
        totalErrors = pTotalErrors;
    }

    /**
     * getTotalMigrated
     * @return totalMigrated
     */
    public int getTotalMigrated() {
        return totalMigrated;
    }

    /**
     * setTotalMigrated
     * @param pTotalMigrated pTotalMigrated
     */
    public void setTotalMigrated(int pTotalMigrated) {
        totalMigrated = pTotalMigrated;
    }

    /**
     * getIen
     * @return ien
     */
    public int getIen() {
        return ien;
    }

    /**
     * setIen
     * @param pIen pIen
     */
    public void setIen(int pIen) {
        ien = pIen;
    }

    /**
     * getItemName
     * @return itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * setItemName
     * @param pItemName pItemName
     */
    public void setItemName(String pItemName) {
        itemName = pItemName;
    }

    /**
     * getFieldName
     * @return fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * setFieldName
     * @param pFieldName pFieldName
     */
    public void setFieldName(String pFieldName) {
        fieldName = pFieldName;
    }

    /**
     * getFieldValue
     * @return fieldValue
     */
    public String getFieldValue() {
        return fieldValue;
    }

    /**
     * setFieldValue
     * @param pFieldValue pFieldValue
     */
    public void setFieldValue(String pFieldValue) {
        fieldValue = pFieldValue;
    }

    /**
     * getReasonForError
     * @return reasonForError
     */
    public String getReasonForError() {
        return reasonForError;
    }

    /**
     * setReasonForError
     * @param pReasonForError pReasonForError
     */
    public void setReasonForError(String pReasonForError) {
        reasonForError = pReasonForError;
    }

    /**
     * getProcessDomainType
     * @return ProcessDomainType
     */
    public ProcessDomainType getProcessDomainType() {
        return processDomainType;
    }

    /**
     * setProcessDomainType
     * @param pProcessDomainType pProcessDomainType
     */
    public void setProcessDomainType(ProcessDomainType pProcessDomainType) {
        processDomainType = pProcessDomainType;
    }

    /**
     * isEndOfFile
     * @return endOfFile
     */
    public boolean isEndOfFile() {
        return endOfFile;
    }

    /**
     * setEndOfFile
     * @param pEndOfFile pEndOfFile
     */
    public void setEndOfFile(boolean pEndOfFile) {
        endOfFile = pEndOfFile;
    }

    /**
     * getMaxRecords
     * @return maxRecords maxRecords
     */
    public int getMaxRecords() {
        return maxRecords;
    }

    /**
     * setMaxRecords
     * @param pMaxRecords maxRecords
     */
    public void setMaxRecords(int pMaxRecords) {
        this.maxRecords = pMaxRecords;
    }

    /**
     * getErrorDetails
     * @return errorDetails errorDetails
     */
    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }

    /**
     * setErrorDetails
     * @param pErrorDetails errorDetails
     */
    public void setErrorDetails(ErrorDetails pErrorDetails) {
        this.errorDetails = pErrorDetails;
    }

    /**
     * getname
     * @return name
     */
    public String getName() {
        return null;
    }

    /**
     * setErrorDetailList
     * @param pErrorDetailList pErrorDetailList
     */
    public void setErrorDetailList(List<ErrorDetails> pErrorDetailList) {
        this.errorDetailList = pErrorDetailList;
    }

    /**
     * getErrorDetailList
     * @return errorDetailList
     */
    public List<ErrorDetails> getErrorDetailList() {
        return errorDetailList;
    }

    /**
     * getManufacturersMigrated
     * @return 0
     */
    public int getManufacturersMigrated() {
        return 0;
    }

    /**
     * setManufacturersMigrated
     * @param pManufacturersMigrated pManufacturersMigrated
     */
    public void setManufacturersMigrated(int pManufacturersMigrated) {
    }

    /**
     * getPackageTypesMigrated
     * @return 0
     */
    public int getPackageTypesMigrated() {
        return 0;
    }

    /**
     * setPackageTypesMigrated
     * @param pPackageTypesMigrated pPackageTypesMigrated
     */
    public void setPackageTypesMigrated(int pPackageTypesMigrated) {
    }

    /**
     * getFileNumber
     * @return null;
     */
    public String getFileNumber() {
        return null;
    }

}
