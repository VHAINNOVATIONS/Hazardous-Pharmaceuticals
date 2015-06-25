/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import org.apache.log4j.Logger;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessCommand;
import gov.va.med.pharmacy.peps.service.common.reports.ReportExportState;
import gov.va.med.pharmacy.peps.service.common.reports.ReportObserver;
import gov.va.med.pharmacy.peps.service.common.session.ReportExportManager;
import gov.va.med.pharmacy.peps.service.common.session.ReportExportProcess;
import gov.va.med.pharmacy.peps.service.common.session.ReportIngredientProcess;
import gov.va.med.pharmacy.peps.service.common.session.ReportNdfProcess;
import gov.va.med.pharmacy.peps.service.common.session.ReportWarningProcess;


/**
 * ReportExportManagerImpl's brief summary
 * 
 * Details of ReportExportManagerImpl's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class ReportExportManagerImpl implements ReportExportManager, ReportObserver {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(ReportExportManagerImpl.class);
    private static final int DELAY = 2000; // norm 2000

    private ReportExportState exportState;
    private ReportExportState ndfState;
    private ReportExportState ingredientState;
    private ReportExportState warningState;
    private SimpleAsyncTaskExecutor reportExportExecutor;
    private ReportExportProcess reportExportProcess;
    private ReportNdfProcess reportNdfProcess;
    private ReportIngredientProcess reportIngredientProcess;
    private ReportWarningProcess reportWarningProcess;
    private boolean running = false;
    private boolean exportComplete;

    @Override
    public ReportExportState startProcess(ReportType reportType) {
        switch (reportType) {
            case NDC_LIST_PRINT_TEMPLATE:
                command(ProcessCommand.START, reportType);

                return ndfState;
            case LIST_INGREDIENTS:
                command(ProcessCommand.START, reportType);

                return ingredientState;
            case PRINT_PRODUCTS_WARNING_LABELS:
                command(ProcessCommand.START, reportType);

                return warningState;
            default:
                command(ProcessCommand.START, reportType);

                return exportState;
        }

    }

    @Override
    public ReportExportState getStatus(ReportType reportType) {

        switch (reportType) {
            case NDC_LIST_PRINT_TEMPLATE:
                if (ndfState != null) {
                    this.running = ndfState.isRunning();
                    this.exportComplete = ndfState.isExportComplete();
                }

                return ndfState;
            case LIST_INGREDIENTS:
                if (ingredientState != null) {
                    this.running = ingredientState.isRunning();
                    this.exportComplete = ingredientState.isExportComplete();
                }

                return ingredientState;
            case PRINT_PRODUCTS_WARNING_LABELS:
                if (warningState != null) {
                    this.running = warningState.isRunning();
                    this.exportComplete = warningState.isExportComplete();
                }

                return warningState;
            default:
                if (exportState != null) {
                    this.running = exportState.isRunning();
                    this.exportComplete = exportState.isExportComplete();
                }

                return exportState;
        }
    }

    @Override
    public ReportExportState stopProcess() {
        command(ProcessCommand.STOP, null);

        return exportState;
    }

    /**
     * command
     *
     * @param pCommand ProcessCommand
     * @param reportType Report to generate
     */
    private void command(ProcessCommand pCommand, ReportType reportType) {

        switch (pCommand) {

            case START:
                switch (reportType) {
                    case NDC_LIST_PRINT_TEMPLATE:
                        this.reportExportExecutor.execute(reportNdfProcess);
                        sleepThread(DELAY); // delay to allow for the state to be

                        if (reportNdfProcess != null) {
                            ndfState = reportNdfProcess.getExportState();
                            setExportComplete(ndfState.isExportComplete());
                            this.setRunning(ndfState.isRunning());
                            reportNdfProcess.registerObserver(this);
                        }

                        break;
                    case LIST_INGREDIENTS:
                        this.reportExportExecutor.execute(reportIngredientProcess);
                        sleepThread(DELAY); // delay to allow for the state to be

                        if (reportIngredientProcess != null) {
                            ingredientState = reportIngredientProcess.getExportState();
                            setExportComplete(ingredientState.isExportComplete());
                            this.setRunning(ingredientState.isRunning());
                            reportIngredientProcess.registerObserver(this);
                        }

                        break;
                    case PRINT_PRODUCTS_WARNING_LABELS:
                        this.reportExportExecutor.execute(reportWarningProcess);
                        sleepThread(DELAY); // delay to allow for the state to be

                        if (reportWarningProcess != null) {
                            warningState = reportWarningProcess.getExportState();
                            setExportComplete(warningState.isExportComplete());
                            this.setRunning(warningState.isRunning());
                            reportWarningProcess.registerObserver(this);
                        }

                        break;
                    default:
                        this.reportExportExecutor.execute(reportExportProcess);
                        sleepThread(DELAY); // delay to allow for the state to be

                        if (reportExportProcess != null) {
                            exportState = reportExportProcess.getExportState();
                            setExportComplete(exportState.isExportComplete());
                            this.setRunning(exportState.isRunning());
                            reportExportProcess.registerObserver(this);
                        }

                        break;
                }

            case STATUS:
                switch (reportType) {
                    case NDC_LIST_PRINT_TEMPLATE:
                        if (reportNdfProcess != null) {
                            ndfState = reportNdfProcess.getExportState();
                            break;
                        }
                    case LIST_INGREDIENTS:
                        if (reportIngredientProcess != null) {
                            ingredientState = reportIngredientProcess.getExportState();
                            break;
                        }
                    case PRINT_PRODUCTS_WARNING_LABELS:
                        if (reportWarningProcess != null) {
                            warningState = reportWarningProcess.getExportState();
                            break;
                        }
                    default:
                        if (reportExportProcess != null) {
                            exportState = reportExportProcess.getExportState();
                            break;
                        }
                }
            case STOP:
                switch (reportType) {
                    case NDC_LIST_PRINT_TEMPLATE:
                        reportNdfProcess.stopProcess();
                        break;
                    case LIST_INGREDIENTS:
                        reportIngredientProcess.stopProcess();
                        break;
                    case PRINT_PRODUCTS_WARNING_LABELS:
                        reportWarningProcess.stopProcess();
                        break;
                    default:
                        reportExportProcess.stopProcess();
                        break;
                }
            default:

                break;
        }
    }

    /**
     * sets the thread to sleep
     * @param count count
     */
    private void sleepThread(long count) {
        try {
            
            // set the thread to sleep for count milliseconds.
            Thread.sleep(count);
        } catch (InterruptedException x) {
            x.getStackTrace();
        }
    }

    /**
     * Observers update method
     * @param pExportState export state 
     */
    @Override
    public void update(ReportExportState pExportState) {
        LOG.debug("ReportExportManagerImpl==>update(), observer ");

        if (pExportState != null) {
            this.setReportExportState(pExportState);
            this.setRunning(pExportState.isRunning());
            this.setExportComplete(pExportState.isExportComplete());

            LOG.debug("update().isRunning: " + pExportState.isRunning());
            LOG.debug("update().isExportComplete: " + pExportState.isExportComplete());
        }

    }

    /**
     * description
     * 
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * description
     * 
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * description
     * 
     * @return the exportComplete
     */
    public boolean isExportComplete() {
        return exportComplete;
    }

    /**
     * description
     * 
     * @param exportComplete the exportComplete to set
     */
    public void setExportComplete(boolean exportComplete) {
        this.exportComplete = exportComplete;
    }

    /**
     * description
     * 
     * @return the reportExportProcess
     */
    public ReportExportProcess getReportExportProcess() {
        return reportExportProcess;
    }

    /**
     * description
     * 
     * @param reportExportProcess the reportExportProcess to set
     */
    public void setReportExportProcess(ReportExportProcess reportExportProcess) {
        this.reportExportProcess = reportExportProcess;
    }

    /**
     * description
     * 
     * @return the reportExportState
     */
    public ReportExportState getReportExportState() {
        return exportState;
    }

    /**
     * description
     * 
     * @param reportExportState the reportExportState to set
     */
    public void setReportExportState(ReportExportState reportExportState) {
        this.exportState = reportExportState;
    }

    /**
     * description
     * 
     * @return the reportExportExecutor
     */
    public SimpleAsyncTaskExecutor getReportExportExecutor() {
        return reportExportExecutor;
    }

    /**
     * description
     * 
     * @param reportExportExecutor the reportExportExecutor to set
     */
    public void setReportExportExecutor(SimpleAsyncTaskExecutor reportExportExecutor) {
        this.reportExportExecutor = reportExportExecutor;
    }

    /**
     * setReportNdfProcess
     * @param reportNdfProcess the reportNdfProcess to set
     */
    public void setReportNdfProcess(ReportNdfProcess reportNdfProcess) {
        this.reportNdfProcess = reportNdfProcess;
    }

    /**
     * getReportNdfProcess
     * @return the reportNdfProcess
     */
    public ReportNdfProcess getReportNdfProcess() {
        return reportNdfProcess;
    }

    /**
     * setReportIngredientProcess
     * @param reportIngredientProcess the reportIngredientProcess to set
     */
    public void setReportIngredientProcess(ReportIngredientProcess reportIngredientProcess) {
        this.reportIngredientProcess = reportIngredientProcess;
    }

    /**
     * getReportIngredientProcess
     * @return the reportIngredientProcess
     */
    public ReportIngredientProcess getReportIngredientProcess() {
        return reportIngredientProcess;
    }

    /**
     * setReportWarningProcess
     * @param reportWarningProcess the reportWarningProcess to set
     */
    public void setReportWarningProcess(ReportWarningProcess reportWarningProcess) {
        this.reportWarningProcess = reportWarningProcess;
    }

    /**
     * getReportWarningProcess
     * @return the reportWarningProcess
     */
    public ReportWarningProcess getReportWarningProcess() {
        return reportWarningProcess;
    }

}
