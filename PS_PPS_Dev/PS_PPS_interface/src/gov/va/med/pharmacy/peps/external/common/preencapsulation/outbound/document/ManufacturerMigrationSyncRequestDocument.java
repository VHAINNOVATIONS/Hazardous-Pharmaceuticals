/**
 * Source file created in 2011 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.utility.XmlDocument;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.migration.manufacturer.request.ManufacturerMigrationSyncRequest;


/**
 * ManufacturerMigrationSyncRequestDocument's brief summary
 * 
 * Marshal and unmarshal XML into Java objects.
 *
 */
public class ManufacturerMigrationSyncRequestDocument extends XmlDocument<ManufacturerMigrationSyncRequest> {
    private static final String[] CDATA_ELEMENTS = {toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile"};
    private static final String[] SCHEMA_FILES = new String[] {"xml/schema/migration/manufacturerMigrationSyncRequest.xsd"};
    private static final String[] XSL_FILES = new String[] {};
    private static final ManufacturerMigrationSyncRequestDocument INSTANCE = new ManufacturerMigrationSyncRequestDocument();
    
    /**
     * Protected constructor
     */
    private ManufacturerMigrationSyncRequestDocument() {
        super(ManufacturerMigrationSyncRequest.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static ManufacturerMigrationSyncRequestDocument instance() {
        return INSTANCE;
        
    }
    
}
