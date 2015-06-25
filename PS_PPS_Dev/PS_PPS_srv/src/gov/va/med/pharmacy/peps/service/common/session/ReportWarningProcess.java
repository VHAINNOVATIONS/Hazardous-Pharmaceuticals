/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


import gov.va.med.pharmacy.peps.service.common.reports.ReportExportState;
import gov.va.med.pharmacy.peps.service.common.reports.ReportObserver;
import gov.va.med.pharmacy.peps.service.common.reports.ReportProcessStatus;


/**
 * Process which generates the Report CSV files
 *
 */
public interface ReportWarningProcess extends Runnable {
    
    /**
     * Stops the process that is running.
     */
    void stopProcess();

    /**
     * Runs a task within the process that is executing.
     */
    void run();

    /**
     * ReportWarningProcess
     * Registers observer
     * @param pObserver pObserver
     */
    void registerObserver(ReportObserver pObserver);

    /**
     * ReportWarningProcess
     * Removes observer
     * @param pObserver pObserver
     */
    void removeObserver(ReportObserver pObserver);

    /**
     * ReportWarningProcess
     * Notifies Observer 
     */
    void notifyObservers();

    /**
     * ReportWarningProcess
     * Counter used for number of records
     * @return int counter
     */
    int getRecordCounter();

    /**
     * ReportWarningProcess
     * setReportProcessStatus
     * @param reportProcessStatus reportProcessStatus
     */
    void setReportProcessStatus(ReportProcessStatus reportProcessStatus);

    /**
     * ReportWarningProcess
     * setExportState
     * @param exportState exportState
     */
    void setExportState(ReportExportState exportState);

    /**
     * ReportWarningProcess
     * getExportState
     * @return ReportExportState
     */
    ReportExportState getExportState();

}
