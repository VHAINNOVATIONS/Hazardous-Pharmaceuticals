/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document;


import gov.va.med.pharmacy.peps.external.common.utility.XmlDocument;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.data.response.DrugMigrationResponse;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class MigrationResponseDocument extends XmlDocument<DrugMigrationResponse> {

    private static final String[] CDATA_ELEMENTS = {};
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/migration/drugMigrationResponse.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final MigrationResponseDocument INSTANCE = new MigrationResponseDocument();

    /**
     * Protected constructor
     */
    private MigrationResponseDocument() {
        super(DrugMigrationResponse.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static MigrationResponseDocument instance() {
        return INSTANCE;
    }

}
