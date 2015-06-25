/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document;


import gov.va.med.pharmacy.peps.external.common.utility.XmlDocument;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.ndc.response.NdcMigrationSynchResponse;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class MigrationNdcUpnSynchFileStatusDocument extends XmlDocument<NdcMigrationSynchResponse> {

    private static final String[] CDATA_ELEMENTS = {};
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/migration/ndcMigrationSynchResponse.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final MigrationNdcUpnSynchFileStatusDocument INSTANCE = new MigrationNdcUpnSynchFileStatusDocument();

    /**
     * Protected constructor
     */
    private MigrationNdcUpnSynchFileStatusDocument() {
        super(NdcMigrationSynchResponse.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static MigrationNdcUpnSynchFileStatusDocument instance() {
        return INSTANCE;
    }

}
