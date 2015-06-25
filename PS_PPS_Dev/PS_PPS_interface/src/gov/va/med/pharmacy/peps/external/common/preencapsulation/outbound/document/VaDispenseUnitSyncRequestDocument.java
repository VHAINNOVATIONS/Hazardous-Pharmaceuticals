/**
 * Source file created in 2011 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vadispenseunitsyncrequest.VaDispenseUnitSyncRequest;



/**
 * VaDispenseUnitSyncRequestDocument's brief summary
 * 
* Marshal and unmarshal VA Dispense Unit Sync requests into Java Objects.
 * 
 *
 */
public class VaDispenseUnitSyncRequestDocument 
    extends
    gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<VaDispenseUnitSyncRequest> {
    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/sync/vaDispenseUnitSyncRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final VaDispenseUnitSyncRequestDocument INSTANCE = new VaDispenseUnitSyncRequestDocument();

    /**
     * Protected constructor
     */
    private VaDispenseUnitSyncRequestDocument() {
        super(VaDispenseUnitSyncRequest.class, CDATA_ELEMENTS, SCHEMA_FILES,
                XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static VaDispenseUnitSyncRequestDocument instance() {
        return INSTANCE;
    }


}
