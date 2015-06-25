/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing a Migration Control Class
 */
public class MigrationFileVo extends ValueObject {
    private static final long serialVersionUID = 1L;
    private String fileId;
    private String migrationFileName;
    private Integer rowsProcessedQty;
    private Integer rowsMigratedQty;
    private Integer rowsNotMigratedQty;
    private Integer errorQty;

    /**
     * the constructor
     */
    public MigrationFileVo() {
    }

    /**
     * getFileID
     * @return fileId
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * setFileId for MigrationFileVo.
     * @param fileId fileId
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    
    /**
     * getMigrationFileName
     * @return migrationFileName
     */
    public String getMigrationFileName() {
        return migrationFileName;
    }

    /**
     * setMigrationFileName
     * @param migrationFileName migrationFileName
     */
    public void setMigrationFileName(String migrationFileName) {
        this.migrationFileName = migrationFileName;
    }

    /**
     * getRowsProcessedQty
     * @return rowsProcessedQty
     */
    public Integer getRowsProcessedQty() {
        return rowsProcessedQty;
    }

    /**
     * setRowsProcessedQty
     * @param rowsProcessedQty rowsProcessedQty
     */
    public void setRowsProcessedQty(Integer rowsProcessedQty) {
        this.rowsProcessedQty = rowsProcessedQty;
    }

    /**
     * getRowsMigratedQty
     * @return rowsMigratedQty
     */
    public Integer getRowsMigratedQty() {
        return rowsMigratedQty;
    }

    /**
     * setRowsMigratedQty
     * @param rowsMigratedQty rowsMigratedQty
     */
    public void setRowsMigratedQty(Integer rowsMigratedQty) {
        this.rowsMigratedQty = rowsMigratedQty;
    }

    /** 
     * getRowsNotMigratedQty
     * @return rowsNotMigratedQty
     */
    public Integer getRowsNotMigratedQty() {
        return rowsNotMigratedQty;
    }

    /**
     * setRowsNotMigratedQty
     * @param rowsNotMigratedQty rowsNotMigratedQty
     */
    public void setRowsNotMigratedQty(Integer rowsNotMigratedQty) {
        this.rowsNotMigratedQty = rowsNotMigratedQty;
    }

    /** 
     * getErrorQty
     * @return errorQty
     */
    public Integer getErrorQty() {
        return errorQty;
    }

    /**
     * setErrorQty
     * @param errorQty errorQty
     */
    public void setErrorQty(Integer errorQty) {
        this.errorQty = errorQty;
    }

    /**
     * Returns true if the domain is standardized for MigrationFileVo.
     * 
     * @return boolean
     */
    public boolean isStandardized() {
        return true;
    }

    /**
     * Returns true if the domain is an NDF domain
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return true;
    }

    /**
     * Returns true if this is a local only domain
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return false;
    }
}
