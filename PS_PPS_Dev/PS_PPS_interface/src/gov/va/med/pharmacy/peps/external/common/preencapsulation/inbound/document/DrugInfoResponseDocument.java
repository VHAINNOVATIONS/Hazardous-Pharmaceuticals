/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.info.response.DrugInfoResponse;
import gov.va.med.pharmacy.peps.external.common.utility.XmlDocument;


/**
 * DrugInfoResponseDocument
 */
public class DrugInfoResponseDocument extends XmlDocument<DrugInfoResponse> {

    private static final String[] CDATA_ELEMENTS = {};
    private static final String[] SCHEMA_FILES = { "xml/schema/drug/info/drugInfoResponse.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final DrugInfoResponseDocument INSTANCE = new DrugInfoResponseDocument();

    /**
     * Protected constructor
     */
    private DrugInfoResponseDocument() {
        super(DrugInfoResponse.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static DrugInfoResponseDocument instance() {
        return INSTANCE;
    }

}
