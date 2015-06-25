/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.session;



import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationExportState;
import gov.va.med.pharmacy.peps.service.common.migration.process.Observer;


/**
 * MigrationExportProcess
 */
public interface MigrationExportProcess extends Runnable {

    /**
     * get Export state
     * @return export state
     */
    MigrationExportState getExportState();

    /**
     * setter for Export state
     * @param pState the state
     */
    void setExportState(MigrationExportState pState);

    /**
     * stop the process
     */
    void stopProcess();
    
    
    /**
     * register observers
     * @param pObserver observer
     */
    void registerObserver(Observer pObserver);

    /**
     * remove observers
     * @param pObserver observer
     */
    void removeObserver(Observer pObserver);

}
