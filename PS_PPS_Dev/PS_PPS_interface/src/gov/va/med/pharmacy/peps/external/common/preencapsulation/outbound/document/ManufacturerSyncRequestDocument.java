/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.manufacturersyncrequest.ManufacturerSyncRequest;


/**
 * ManufacturerSyncRequestDocument's brief summary
 * 
 * Marshal and unmarshal Manufacturer Sync requests into Java Objects.
 *
 */
public class ManufacturerSyncRequestDocument  
    extends
    gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<ManufacturerSyncRequest> {
    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/sync/manufacturerSyncRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final ManufacturerSyncRequestDocument INSTANCE = new ManufacturerSyncRequestDocument();

    /**
     * Protected constructor
     */
    private ManufacturerSyncRequestDocument() {
        super(ManufacturerSyncRequest.class, CDATA_ELEMENTS, SCHEMA_FILES,
                XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static ManufacturerSyncRequestDocument instance() {
        return INSTANCE;
    }

    
}
