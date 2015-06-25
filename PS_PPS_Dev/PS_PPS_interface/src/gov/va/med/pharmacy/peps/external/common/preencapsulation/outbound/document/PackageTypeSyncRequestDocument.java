/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.packagetypesyncrequest.PackageTypeSyncRequest;


/**
 * PackageTypeSyncRequestDocument's brief summary
 * 
 * Marshal and unmarshal Package Type Sync requests into Java Objects.
 *
 */
public class PackageTypeSyncRequestDocument  
    extends
    gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<PackageTypeSyncRequest> {
    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/sync/packageTypeSyncRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final PackageTypeSyncRequestDocument INSTANCE = new PackageTypeSyncRequestDocument();

    /**
     * Protected constructor
     */
    private PackageTypeSyncRequestDocument() {
        super(PackageTypeSyncRequest.class, CDATA_ELEMENTS, SCHEMA_FILES,
                XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static PackageTypeSyncRequestDocument instance() {
        return INSTANCE;
    }



}
