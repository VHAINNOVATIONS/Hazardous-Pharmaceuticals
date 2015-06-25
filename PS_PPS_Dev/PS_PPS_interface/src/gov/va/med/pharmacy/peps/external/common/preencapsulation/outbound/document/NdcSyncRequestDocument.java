/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.ndcsyncrequest.NdcSyncRequest;


/**
 * NdcSyncRequestDocument's brief summary
 * 
 * Marshal and unmarshal NDC Sync requests into Java Objects.
 *
 */
public class NdcSyncRequestDocument 
    extends
    gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<NdcSyncRequest> {
    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/sync/ndcSyncRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final NdcSyncRequestDocument INSTANCE = new NdcSyncRequestDocument();

    /**
     * Protected constructor
     */
    private NdcSyncRequestDocument() {
        super(NdcSyncRequest.class, CDATA_ELEMENTS, SCHEMA_FILES,
                XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static NdcSyncRequestDocument instance() {
        return INSTANCE;
    }

    
}
