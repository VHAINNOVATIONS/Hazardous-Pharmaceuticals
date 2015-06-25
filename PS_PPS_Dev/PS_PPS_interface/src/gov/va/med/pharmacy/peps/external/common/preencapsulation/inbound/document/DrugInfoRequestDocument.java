/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.info.request.DrugInfoRequest;
import gov.va.med.pharmacy.peps.external.common.utility.XmlDocument;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class DrugInfoRequestDocument extends XmlDocument<DrugInfoRequest> {

    private static final String[] CDATA_ELEMENTS = {};
    private static final String[] SCHEMA_FILES = { "xml/schema/drug/info/drugInfoRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final DrugInfoRequestDocument INSTANCE = new DrugInfoRequestDocument();

    /**
     * Protected constructor
     */
    private DrugInfoRequestDocument() {
        super(DrugInfoRequest.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static DrugInfoRequestDocument instance() {
        return INSTANCE;
    }

}
