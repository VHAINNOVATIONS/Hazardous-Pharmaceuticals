/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration.process;


import gov.va.med.pharmacy.peps.service.common.migration.ExportCSVFileData;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessStatus;


/**
 * MigrationExportState
 *
 */
public class MigrationExportState {

    private ProcessStatus status;
    private boolean running;
    private boolean exportComplete;
    private boolean ndcExportComplete;
    private boolean oiExportComplete;
    private boolean productExportComplete;
    private int recordCounter;
    private int ndcRecordCounter;
    private int oiRecordCounter;
    private int productRecordCounter;
    private ExportCSVFileData exportData;
    private int failureCounter;
    private int ndcMaxRecords;
    private int oiMaxRecords;
    private int productMaxRecords;

    /**
     * isExportComplete
     * @return exportComplete
     */
    public boolean isExportComplete() {
        return exportComplete;
    }

    /**
     * setExportComplete
     * @param pExportComplete pExportComplete
     */
    public void setExportComplete(boolean pExportComplete) {
        this.exportComplete = pExportComplete;
    }

    /**
     * isRunning
     * @return running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * setRunning
     * @param pRunning pRunning
     */
    public void setRunning(boolean pRunning) {
        this.running = pRunning;
    }

    /**
     * getStatus
     * @return status
     */
    public ProcessStatus getStatus() {
        return status;
    }

    /**
     * setStatus
     * @param pStatus pStatus
     */
    public void setStatus(ProcessStatus pStatus) {
        this.status = pStatus;
    }

    /**
     * setRecordCounter
     * @param pCount pCount
     */
    public void setRecordCounter(int pCount) {
        this.recordCounter = pCount; 
    }

    /**
     * getRecordCounter
     * @return recordCounter
     */
    public int getRecordCounter() {
        return recordCounter;
    }

    /**
     * getExportData
     * @return ExportCSVFileData
     */
    public ExportCSVFileData getExportData() {
        return exportData;
    }

    /**
     * setExportData
     * @param pExportData pExportData
     */
    public void setExportData(ExportCSVFileData pExportData) {
        this.exportData = pExportData;
    }

    /**
     * setFailureCounter
     * @param pFailureCounter pFailureCounter
     */
    public void setFailureCounter(int pFailureCounter) {
        this.failureCounter = pFailureCounter;
    }

    /**
     * getFailureCounter
     * @return failureCounter
     */
    public int getFailureCounter() {
        return failureCounter;
    }

    /**
     * isNdcExportComplete
     * @return boolean
     */
    public boolean isNdcExportComplete() {
        return ndcExportComplete;
    }

    /**
     * setNdcExportComplete
     * @param pNdcExportComplete pNdcExportComplete
     */
    public void setNdcExportComplete(boolean pNdcExportComplete) {
        this.ndcExportComplete = pNdcExportComplete;
    }

    /**
     * isOiExportComplete
     * @return oiExportComplete
     */
    public boolean isOiExportComplete() {
        return oiExportComplete;
    }

    /**
     * setOiExportComplete
     * @param pOiExportComplete pOiExportComplete
     */
    public void setOiExportComplete(boolean pOiExportComplete) {
        this.oiExportComplete = pOiExportComplete;
    }

    /**
     * isProductExportComplete
     * @return productExportComplete
     */
    public boolean isProductExportComplete() {
        return productExportComplete;
    }

    /**
     * setProductExportComplete
     * @param pProductExportComplete pProductExportComplete
     */
    public void setProductExportComplete(boolean pProductExportComplete) {
        this.productExportComplete = pProductExportComplete;
    }

    /**
     * getNdcRecordCounter
     * @return ndcRecordCounter
     */
    public int getNdcRecordCounter() {
        return ndcRecordCounter;
    }

    /**
     * setNdcRecordCounter
     * @param pNdcRecordCounter pNdcRecordCounter
     */
    public void setNdcRecordCounter(int pNdcRecordCounter) {
        this.ndcRecordCounter = pNdcRecordCounter;
    }
    
    /**
     * getOiRecordCounter
     * @return oiRecordCounter
     */
    public int getOiRecordCounter() {
        return oiRecordCounter;
    }

    /**
     * setOiRecordCounter
     * @param pOiRecordCounter pOiRecordCounter
     */
    public void setOiRecordCounter(int pOiRecordCounter) {
        this.oiRecordCounter = pOiRecordCounter;
    }

    /**
     * getProductRecordCounter
     * @return productRecordCounter
     */
    public int getProductRecordCounter() {
        return productRecordCounter;
    }

    /**
     * setProductRecordCounter
     * @param pProductRecordCounter pProductRecordCounter
     */
    public void setProductRecordCounter(int pProductRecordCounter) {
        this.productRecordCounter = pProductRecordCounter;
    }

    /**
     * getProductMaxRecords
     * @return productMaxRecords
     */
    public int getProductMaxRecords() {
        return productMaxRecords;
    }

    /**
     * setProductMaxRecords
     * @param pProductMaxRecords pProductMaxRecords
     */
    public void setProductMaxRecords(int pProductMaxRecords) {
        this.productMaxRecords = pProductMaxRecords;
    }

    /**
     * setNdcMaxRecords
     * @param pNdcMaxRecords pNdcMaxRecords
     */
    public void setNdcMaxRecords(int pNdcMaxRecords) {
        this.ndcMaxRecords = pNdcMaxRecords;
    }

    /**
     * getNdcMaxRecords
     * @return ndcMaxRecords
     */
    public int getNdcMaxRecords() {
        return ndcMaxRecords;
    }

    /**
     * getOiMaxRecords
     * @return oiMaxRecords
     */
    public int getOiMaxRecords() {
        return oiMaxRecords;
    }
    
    /**
     * setOiMaxRecords
     * @param pOiMaxRecords pOiMaxRecords
     */
    public void setOiMaxRecords(int pOiMaxRecords) {
        this.oiMaxRecords = pOiMaxRecords;
    }

}
