/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


import java.util.List;


/**
 * MigratedDomain Interface
 *
 */
public interface MigratedDomain {
   
    /**
     * getPercent
     * @return percent
     */
    String getPercent();

    /**
     * setPercent
     * @param pPercent pPercent
     */
    void setPercent(String pPercent);

    /**
     * getCount
     * @return count
     */
    int getCount();

    /**
     * setCount
     * @param pCount pCount
     */
    void setCount(int pCount);

    
    /**
     * getDuplicatesNoMigrated
     * @return duplicatesnotMigrated
     */
    int getDuplicatesNoMigrated();

    /**
     * setDuplicatesNoMigrated
     * @param pDuplicatesNoMigrated pDuplicatesNoMigrated
     */
    void setDuplicatesNoMigrated(int pDuplicatesNoMigrated);

    /**
     * getTotalErrors
     * @return totalErrors
     */
    int getTotalErrors();

    /**
     * setTotalErrors
     * @param pTotalErrors pTotalErrors
     */
    void setTotalErrors(int pTotalErrors);

    /**
     * getTotalMigrated
     * @return totalMigrated
     */
    int getTotalMigrated();

    /**
     * setTotalMigrated
     * @param pTotalMigrated pTotalMigrated
     */
    void setTotalMigrated(int pTotalMigrated);

    /**
     * getIen
     * @return ien
     */
    int getIen();

    /**
     * setIen
     * @param pIen pIen
     */
    void setIen(int pIen);

    /**
     * getItemName
     * @return itemname
     */
    String getItemName();

    /**
     * setItemName
     * @param pItemName pItemName
     */
    void setItemName(String pItemName);

    /**
     * getFieldName
     * @return fieldName
     */
    String getFieldName();

    /**
     * setFieldName
     * @param pFieldName pFieldName
     */
    void setFieldName(String pFieldName);

    /**
     * getFieldValue
     * @return fieldValue
     */
    String getFieldValue();

    /**
     * setFieldValue
     * @param pFieldValue pFieldValue
     */
    void setFieldValue(String pFieldValue);

    /**
     * getReasonForError
     * @return reasonForError
     */
    String getReasonForError();

    /** 
     * setReasonForError
     * @param pReasonForError pReasonForError
     */
    void setReasonForError(String pReasonForError);

    /**
     * getProcessDomainType
     * @return ProcessDomainType
     */
    ProcessDomainType getProcessDomainType();

    /**
     * setProcessDomainType
     * @param pProcessDomainType pProcessDomainType
     */
    void setProcessDomainType(ProcessDomainType pProcessDomainType);

    /**
     * isEndOfFile
     * @return endOfFile
     */
    boolean isEndOfFile();

    /**
     * setEndOfFile
     * @param pEndOfFile pEndOfFile
     */
    void setEndOfFile(boolean pEndOfFile);

    /**
     * getMaxRecords
     * @return maxRecords
     */
    int getMaxRecords();

    /**
     * setMaxRecords
     * @param maxRecords maxRecords
     */
    void setMaxRecords(int maxRecords);

    /**
     * setErrorDetails
     * @param pErrorDetails pErrorDetails
     */
    void setErrorDetails(ErrorDetails pErrorDetails);

    /**
     * getErrorDetails
     * @return ErrorDetails
     */
    ErrorDetails getErrorDetails();

    /**
     * getName
     * @return name;
     */
    String getName();

    /**
     * setErrorDetailList
     * @param pErrorDetailList pErrorDetailList
     */
    void setErrorDetailList(List<ErrorDetails> pErrorDetailList);

    /**
     * getErrorDetailList
     * @return List<ErrorDetails>
     */
    List<ErrorDetails> getErrorDetailList();

    /**
     * getFileNumber
     * @return fileNumber
     */
    String getFileNumber();

    /**
     * getManufacturersMigrated
     * @return manufacturersMigrated
     */
    int getManufacturersMigrated();

    /**
     * setManufacturersMigrated
     * @param pManufacturersMigrated pManufacturersMigrated
     */
    void setManufacturersMigrated(int pManufacturersMigrated);

    /**
     * getPackageTypesMigrated
     * @return packageTypesMigrated
     */
    int getPackageTypesMigrated();

    /**
     * setPackageTypesMigrated
     * @param pPackageTypesMigrated pPackageTypesMigrated
     */
    void setPackageTypesMigrated(int pPackageTypesMigrated);

}
