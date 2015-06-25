/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;


/**
 * Data representing the Migration variables.
 */
public class MigrationErrorVo extends ValueObject {
    private static final long serialVersionUID = 1L;
 
    private String id;
   
    /**
     * fileId is the external key pointer to the MIGRATION_FILE table
     */
    private String fileId;
    private String migrationUniqueName;
    private String migrationRowId;
    private String migrationMultiRowId;
    private String migrationFieldName;
    private String migrationMultiFieldName;
    private String migrationFieldValue;
    private String detailedErrorText;
    private Date processedDTM;
    private String createdBy;
    
    /**
     * getMigrationUniqueName
     * @return migrationUniqueName
     */
    public String getMigrationUniqueName() {
        return migrationUniqueName;
    }

    /**
     * setMigrationUniqueName
     * @param migrationUniqueName migrationUniqueName
     */
    public void setMigrationUniqueName(String migrationUniqueName) {
        this.migrationUniqueName = migrationUniqueName;
    }

    /** 
     * getMigrationFieldValue
     * @return migrationFieldValue
     */
    public String getMigrationFieldValue() {
        return migrationFieldValue;
    }

    /**
     * setMigrationFieldValue
     * @param migrationFieldValue migrationFieldValue
     */
    public void setMigrationFieldValue(String migrationFieldValue) {
        this.migrationFieldValue = migrationFieldValue;
    }

    /**
     * getFieleId
     * @return fileId
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * setFileId
     * @param fileId fileId
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * getMigrationRowId
     * @return migrationRowId
     */
    public String getMigrationRowId() {
        return migrationRowId;
    }
    
    /**
     * setMigrationRowId
     * @param migrationRowId migrationRowId
     */
    public void setMigrationRowId(String migrationRowId) {
        this.migrationRowId = migrationRowId;
    }

    /**
     * setId
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * getId
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * setMigrationMultiRowId
     * @param migrationMultiRowId migrationMultiRowId
     */
    public void setMigrationMultiRowId(String migrationMultiRowId) {
        this.migrationMultiRowId = migrationMultiRowId;
    }
    
    /**
     * getMigrationMultiRowId
     * @return migrationMultiRowId
     */
    public String getMigrationMultiRowId() {
        return migrationMultiRowId;
    }

    /**
     * setMigrationFieldName
     * @param migrationFieldName migrationFieldName
     */
    public void setMigrationFieldName(String migrationFieldName) {
        this.migrationFieldName = migrationFieldName;
    }

    /**
     * getMigrationFieldName
     * @return migrationFieldName
     */
    public String getMigrationFieldName() {
        return migrationFieldName;
    }
    
    /** 
     * setMigrationMultiFileName
     * @param migrationMultiFieldName migrationMultiFileName
     */
    public void setMigrationMultiFieldName(String migrationMultiFieldName) {
        this.migrationMultiFieldName = migrationMultiFieldName;
    }
    
    /** 
     * getMigrationMultiFieldName
     * @return migrationMultiFieldName
     */
    public String getMigrationMultiFieldName() {
        return migrationMultiFieldName;
    }

    /** 
     * setDetailedErrorText
     * @param detailedErrorText detailedErrorText
     */
    public void setDetailedErrorText(String detailedErrorText) {
        this.detailedErrorText = detailedErrorText;
    }
    
    /**
     * getDetailedErrorText
     * @return detailedErrorText
     */
    public String getDetailedErrorText() {
        return detailedErrorText;
    }
    
    /**
     * setProcessedDTM
     * @param processedDTM processedDTM
     */
    public void setProcessedDTM(Date processedDTM) {
        this.processedDTM = processedDTM;
    }
    
    /**
     * getProcessedDTM
     * @return processedDTM
     */
    public Date getProcessedDTM() {
        return processedDTM;
    }

    /**
     * setCreatedBy
     * @param createdBy createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    /** 
     * getCreatedBy
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }
}
