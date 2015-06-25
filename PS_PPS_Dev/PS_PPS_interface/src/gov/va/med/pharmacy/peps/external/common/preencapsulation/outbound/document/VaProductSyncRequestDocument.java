/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vaproductsyncrequest.VaProductSyncRequest;


/**
 * VaProductSyncRequestDocument's brief summary
 * 
 * Marshal and unmarshal VA Product Sync Requests into Java Objects.
 *
 */
public class VaProductSyncRequestDocument  
    extends gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<VaProductSyncRequest> {
    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/sync/vaProductSyncRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final VaProductSyncRequestDocument INSTANCE = new VaProductSyncRequestDocument();
    
    /**
     * Protected constructor
     */
    private VaProductSyncRequestDocument() {
        super(VaProductSyncRequest.class, CDATA_ELEMENTS, SCHEMA_FILES,
                XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static VaProductSyncRequestDocument instance() {
        return INSTANCE;
    }

}
