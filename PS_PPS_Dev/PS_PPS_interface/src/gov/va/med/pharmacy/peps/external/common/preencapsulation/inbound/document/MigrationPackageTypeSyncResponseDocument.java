/**
 * Source file created in 2011 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document;


import gov.va.med.pharmacy.peps.external.common.utility.XmlDocument;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.packagetype.response.PackageTypeMigrationSynchResponse;


/**
 * MigrationPackageTypeSyncResponseDocument's brief summary
 * 
 * Marshal and unmarshal XML into Java objects.
 *
 */
public class MigrationPackageTypeSyncResponseDocument extends XmlDocument<PackageTypeMigrationSynchResponse> {
    private static final String[] CDATA_ELEMENTS = {};
    private static final String[] SCHEMA_FILES = new String[] {"xml/schema/migration/packageTypeMigrationSyncResponse.xsd"};
    private static final String[] XSL_FILES = new String[] {};
    private static final MigrationPackageTypeSyncResponseDocument INSTANCE = new MigrationPackageTypeSyncResponseDocument();
    
    /**
     * Protected constructor
     */
    private MigrationPackageTypeSyncResponseDocument() {
        super(PackageTypeMigrationSynchResponse.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static MigrationPackageTypeSyncResponseDocument instance() {
        return INSTANCE;
    }


}
