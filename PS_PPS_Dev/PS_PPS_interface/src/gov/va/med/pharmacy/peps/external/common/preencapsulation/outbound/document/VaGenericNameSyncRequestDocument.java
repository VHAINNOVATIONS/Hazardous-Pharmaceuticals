/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vagenericnamesyncrequest.VaGenericNameSyncRequest;


/**
 * VaGenericNameSyncRequestDocument's brief summary
 * 
 * Marshal and unmarshal VA Generic Name Sync Requests into Java Objects.
 *
 */
public class VaGenericNameSyncRequestDocument 
        extends gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<VaGenericNameSyncRequest> {
    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/sync/vaGenericNameSyncRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final VaGenericNameSyncRequestDocument INSTANCE = new VaGenericNameSyncRequestDocument();

    /**
     * Protected constructor
     */
    private VaGenericNameSyncRequestDocument() {
        super(VaGenericNameSyncRequest.class, CDATA_ELEMENTS, SCHEMA_FILES,
                XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static VaGenericNameSyncRequestDocument instance() {
        return INSTANCE;
    }

}
