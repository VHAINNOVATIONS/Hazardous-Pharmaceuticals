/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugunitsyncrequest.DrugUnitSyncRequest;


/**
 * DrugUnitSyncRequestDocument's brief summary
 * 
 * Marshal and unmarshal Drug Unit Sync requests into Java Objects.
 * 
 */
public class DrugUnitSyncRequestDocument
        extends
        gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<DrugUnitSyncRequest> {
    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/sync/drugUnitSyncRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final DrugUnitSyncRequestDocument INSTANCE = new DrugUnitSyncRequestDocument();

    /**
     * Protected constructor
     */
    private DrugUnitSyncRequestDocument() {
        super(DrugUnitSyncRequest.class, CDATA_ELEMENTS, SCHEMA_FILES,
                XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static DrugUnitSyncRequestDocument instance() {
        return INSTANCE;
    }

}
