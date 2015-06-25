/**
 * Source file created in 2011 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document;


import gov.va.med.pharmacy.peps.external.common.utility.XmlDocument;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.manufacturer.response.ManufacturerMigrationSynchResponse;


/**
 * MigrationManufacturerSyncResponseDocument's brief summary
 * 
 * Marshal and unmarshal XML into Java objects.
 *
 */
public class MigrationManufacturerSyncResponseDocument extends XmlDocument<ManufacturerMigrationSynchResponse> {
    private static final String[] CDATA_ELEMENTS = {};
    private static final String[] SCHEMA_FILES = new String[] {"xml/schema/migration/manufacturerMigrationSyncResponse.xsd"};
    private static final String[] XSL_FILES = new String[] {};
    private static final MigrationManufacturerSyncResponseDocument INSTANCE = new MigrationManufacturerSyncResponseDocument();
    
    /**
     * Protected constructor
     */
    private MigrationManufacturerSyncResponseDocument() {
        super(ManufacturerMigrationSynchResponse.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static MigrationManufacturerSyncResponseDocument instance() {
        return INSTANCE;
    }

}
