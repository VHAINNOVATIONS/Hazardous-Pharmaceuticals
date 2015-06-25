/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.service.common.reports.ReportExportState;


/**
 * ReportExportManager
 *
 */
public interface ReportExportManager {

    /**
     * Starts the export process
     * @param reportType type of report to export
     * @return return
     */
    ReportExportState startProcess(ReportType reportType);

    /**
     * Updates the current status
     * @param reportType type of report to export
     * @return return
     */
    ReportExportState getStatus(ReportType reportType);

    /**
     * stops process
     * @return return
     */
    ReportExportState stopProcess();
}
