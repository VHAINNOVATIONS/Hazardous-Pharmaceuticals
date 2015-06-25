/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.utility.XmlDocument;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.migration.data.request.DrugMigrationRequest;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class MigrationRequestDocument extends XmlDocument<DrugMigrationRequest> {

    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/migration/drugMigrationRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final MigrationRequestDocument INSTANCE = new MigrationRequestDocument();

    /**
     * Protected constructor
     */
    private MigrationRequestDocument() {
        super(DrugMigrationRequest.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static MigrationRequestDocument instance() {
        return INSTANCE;
    }

}
