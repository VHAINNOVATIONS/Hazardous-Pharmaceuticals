/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.reports;


/**
 * ReportExportState
 */
public class ReportExportState {

    private boolean running;
    private boolean exportComplete;
    private ReportProcessStatus reportProcessStatus;
    private int recordCounter;
    private int recordTotal;

    /**
     * isRunning
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * setRunning
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * exportComplete
     * @return the exportComplete
     */
    public boolean isExportComplete() {
        return exportComplete;
    }

    /**
     * Set export complete
     * @param exportComplete the exportComplete to set
     */
    public void setExportComplete(boolean exportComplete) {
        this.exportComplete = exportComplete;
    }

    /**
     * getReportProcessStatus
     * @return the reportProcessStatus
     */
    public ReportProcessStatus getReportProcessStatus() {
        return reportProcessStatus;
    }

    /**
     * setReportProcessStatus
     * @param reportProcessStatus the reportProcessStatus to set
     */
    public void setReportProcessStatus(ReportProcessStatus reportProcessStatus) {
        this.reportProcessStatus = reportProcessStatus;
    }

    /**
     * getRecordCounter
     * @return the recordCounter
     */
    public int getRecordCounter() {
        return recordCounter;
    }

    /**
     * setRecordCounter
     * @param recordCounter the recordCounter to set
     */
    public void setRecordCounter(int recordCounter) {
        this.recordCounter = recordCounter;
    }

    /**
     * setRecordTotal
     * @param recordTotal the recordTotal to set
     */
    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

    /**
     * getRecordTotal
     * @return the recordTotal
     */
    public int getRecordTotal() {
        return recordTotal;
    }

}
