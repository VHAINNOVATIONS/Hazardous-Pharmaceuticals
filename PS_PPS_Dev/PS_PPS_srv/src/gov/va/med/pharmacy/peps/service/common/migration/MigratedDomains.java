/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * 
 * MigratedDomains
 *
 */
public interface MigratedDomains {

    /**
     * getPercent for MigratedDomains
     * @return percent
     */
    String getPercent();
    
    /**
     * setPercent for MigratedDomains
     * @param pPercent pPercent
     */
    void setPercent(String pPercent); 
    
    /**
     * getCount for MigratedDomains
     * @return count
     */
    int getCount();
    
    /**
     * setCount for MigratedDomains
     * @param pCount pCount
     */
    void setCount(int pCount);
    
    /**
     * getDuplicatesNotMigrated for MigratedDomains
     * @return duplicatesNotMigrated
     */
    int getDuplicatesNoMigrated();
    
    /**
     * setDuplicatesNoMigrated for MigratedDomains
     * @param pDuplicatesNoMigrated pDuplicatesNoMigrated
     */
    void setDuplicatesNoMigrated(int pDuplicatesNoMigrated);
    
    /**
     * getTotalErrors for MigratedDomains
     * @return totalErrors
     */
    int getTotalErrors();
    
    /**
     * setTotalErrors() for MigratedDomains
     * @param pTotalErrors pTotalErrors
     */
    void setTotalErrors(int pTotalErrors);
  
    /**
     * getTotalMigrated() for MigratedDomains
     * @return totalMigrated
     */
    int getTotalMigrated();
    
    /**
     * setTotalMigrated for MigratedDomains
     * @param pTotalMigrated pTotalMigrated
     */
    void setTotalMigrated(int pTotalMigrated);
    
    /**
     * getIen  for MigratedDomains
     * @return ien
     */
    int getIen();
    
    /**
     * setIen for MigratedDomains
     * @param pIen pIEN
     */
    void setIen(int pIen);
    
    
    /**
     * getItemName for MigratedDomains
     * @return itemName
     */
    String getItemName();
    
    /**
     * setItemName() for migratedDomains.
     * @param pItemName pItemName
     */
    void setItemName(String pItemName);
    
    /**
     * getFieldName for migratedDomains.
     * @return fieldName
     */
    String getFieldName();
    
    /**
     * setFieldName for migratedDomains.
     * @param pFieldName pFieldName
     */
    void setFieldName(String pFieldName);
   
    /**
     * getFieldValue for migratedDomains.
     * @return fieldValue
     */
    String getFieldValue();
    
    /**
     * setFieldValue for migratedDomains.
     * @param pFieldValue pFieldValue
     */
    void setFieldValue(String pFieldValue); 
    
    /**
     * getReasonForError for migratedDomains.
     * @return reasonForError
     */
    String getReasonForError();
    
    /**
     * setReasonForError for migratedDomains.
     * @param pReasonForError pReasonForError
     */
    void setReasonForError(String pReasonForError) ;
    
    /**
     * getProcessDomainType for migratedDomains.
     * @return ProcessDomainType
     */
    ProcessDomainType getProcessDomainType();
    
    /**
     * setProcessDomainType for migratedDomains.
     * @param pProcessDomainType pProcessDomainType
     */
    void setProcessDomainType(ProcessDomainType pProcessDomainType);
    
    /**
     * isEndOfFile for migratedDomains.
     * @return endOfFile
     */
    boolean isEndOfFile();
    
    /**
     * setEndOfFile for migratedDomains.
     * @param pEndOfFile pEndOfFile
     */
    void setEndOfFile(boolean pEndOfFile); 
    
}
