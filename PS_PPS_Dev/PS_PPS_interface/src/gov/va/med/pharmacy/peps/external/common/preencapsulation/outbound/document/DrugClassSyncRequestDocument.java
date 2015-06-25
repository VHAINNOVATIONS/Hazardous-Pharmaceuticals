/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugclasssyncrequest.DrugClassSyncRequest;


/**
 * DrugClassSyncRequestDocument's brief summary
 * 
 * Marshal and unmarshal Drug Class Sync Requests into Java Objects.
 *
 */
public class DrugClassSyncRequestDocument  
    extends
    gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<DrugClassSyncRequest> {
    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/sync/drugClassSyncRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final DrugClassSyncRequestDocument INSTANCE = new DrugClassSyncRequestDocument();

    /**
     * Protected constructor
     */
    private DrugClassSyncRequestDocument() {
        super(DrugClassSyncRequest.class, CDATA_ELEMENTS, SCHEMA_FILES,
                XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static DrugClassSyncRequestDocument instance() {
        return INSTANCE;
    }
}
