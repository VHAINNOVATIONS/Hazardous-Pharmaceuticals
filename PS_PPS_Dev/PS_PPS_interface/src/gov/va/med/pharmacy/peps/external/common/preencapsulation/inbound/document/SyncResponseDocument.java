/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.inbound.sync.syncresponse.SyncResponse;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;


/**
 * SyncResponseDocument's brief summary
 * 
 * Details of SyncResponseDocument's operations, special dependencies or
 * protocols developers shall know about when using the class.
 * 
 */
public class SyncResponseDocument
        extends
        gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<SyncResponse> {
    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/sync/syncResponse.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final SyncResponseDocument INSTANCE = new SyncResponseDocument();

    /**
     * Protected constructor
     */
    private SyncResponseDocument() {
        super(SyncResponse.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static SyncResponseDocument instance() {
        return INSTANCE;
    }

}
