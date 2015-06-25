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
public interface ReportIngredientProcess extends Runnable {
    
    /**
     * Stops the process
     */
    void stopProcess();

    /**
     * Runs a task within the process
     */
    void run();

    /**
     * Registers observer
     * @param pObserver pObserver
     */
    void registerObserver(ReportObserver pObserver);

    /**
     * Removes observer
     * @param pObserver pObserver
     */
    void removeObserver(ReportObserver pObserver);

    /**
     * Notifies Observer 
     */
    void notifyObservers();

    /**
     * Counter used for number of records
     * @return int counter
     */
    int getRecordCounter();

    /**
     * setReportProcessStatus
     * @param reportProcessStatus reportProcessStatus
     */
    void setReportProcessStatus(ReportProcessStatus reportProcessStatus);

    /**
     * setExportState
     * @param exportState exportState
     */
    void setExportState(ReportExportState exportState);

    /**
     * getExportState
     * @return ReportExportState
     */
    ReportExportState getExportState();

}
