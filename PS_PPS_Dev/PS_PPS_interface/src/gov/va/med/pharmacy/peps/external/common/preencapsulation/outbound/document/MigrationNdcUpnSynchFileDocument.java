/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.migration.ndc.request.NdcMigrationSynchRequest;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class MigrationNdcUpnSynchFileDocument
    extends gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<NdcMigrationSynchRequest> {

    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/migration/ndcMigrationSynchRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final MigrationNdcUpnSynchFileDocument INSTANCE = new MigrationNdcUpnSynchFileDocument();

    /**
     * Protected constructor
     */
    private MigrationNdcUpnSynchFileDocument() {
        super(NdcMigrationSynchRequest.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static MigrationNdcUpnSynchFileDocument instance() {
        return INSTANCE;
    }

}
