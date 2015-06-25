/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration.process;


/**
 * Observer
 *
 */
public interface Observer {
    
    /**
     * update
     * @param pMigrationExportState pMigrationExportState
     */
    void update(MigrationExportState pMigrationExportState);
}
