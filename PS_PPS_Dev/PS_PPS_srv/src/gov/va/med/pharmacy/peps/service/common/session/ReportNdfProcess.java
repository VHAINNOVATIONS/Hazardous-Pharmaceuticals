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
public interface ReportNdfProcess extends Runnable {

    /**
     * ReportNdfProcess
     * Stops the process
     */
    void stopProcess();

    /**
     * ReportNdfProcess
     * Runs a task within the process
     */
    void run();

    /**
     * ReportNdfProcess
     * Registers observer
     * @param pObserver pObserver
     */
    void registerObserver(ReportObserver pObserver);

    /**
     * ReportNdfProcess
     * Removes observer
     * @param pObserver pObserver
     */
    void removeObserver(ReportObserver pObserver);

    /**
     * ReportNdfProcess
     * Notifies Observer 
     */
    void notifyObservers();

    /**
     * ReportNdfProcess
     * Counter used for number of records
     * @return int counter
     */
    int getRecordCounter();

    /**
     * ReportNdfProcess
     * setReportProcessStatus
     * @param reportProcessStatus reportProcessStatus
     */
    void setReportProcessStatus(ReportProcessStatus reportProcessStatus);

    /**
     * ReportNdfProcess
     * setExportState
     * @param exportState exportState
     */
    void setExportState(ReportExportState exportState);

    /**
     * ReportNdfProcess
     * getExportState
     * @return ReportExportState
     */
    ReportExportState getExportState();

}
