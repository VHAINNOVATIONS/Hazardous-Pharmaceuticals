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
public interface ReportExportProcess extends Runnable {
    
    /**
     * Stops the process running
     */
    void stopProcess();

    /**
     * Runs a task within the process
     */
    void run();

    /**
     * Registers the observer
     * @param pObserver pObserver
     */
    void registerObserver(ReportObserver pObserver);

    /**
     * Removes the observer
     * @param pObserver pObserver
     */
    void removeObserver(ReportObserver pObserver);

    /**
     * Notifies the Observer 
     */
    void notifyObservers();

    /**
     * Counter used for number of records processed
     * @return int counter
     */
    int getRecordCounter();

    /**
     * setReportProcessStatus
     * @param reportProcessStatus The reportProcessStatus
     */
    void setReportProcessStatus(ReportProcessStatus reportProcessStatus);

    /**
     * setExportState
     * @param exportState The exportState
     */
    void setExportState(ReportExportState exportState);

    /**
     * getExportState
     * @return ReportExportState 
     */
    ReportExportState getExportState();

}
