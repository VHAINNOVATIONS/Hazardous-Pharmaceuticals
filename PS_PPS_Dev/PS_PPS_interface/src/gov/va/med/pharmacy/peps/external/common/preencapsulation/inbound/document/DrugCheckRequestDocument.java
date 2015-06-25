/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.request.PEPSRequest;
import gov.va.med.pharmacy.peps.external.common.utility.XmlDocument;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class DrugCheckRequestDocument extends XmlDocument<PEPSRequest> {

    private static final String[] CDATA_ELEMENTS = {};
    private static final String[] SCHEMA_FILES = { "xml/schema/drug/check/drugCheckRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final DrugCheckRequestDocument INSTANCE = new DrugCheckRequestDocument();

    /**
     * Protected constructor
     */
    private DrugCheckRequestDocument() {
        super(PEPSRequest.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static DrugCheckRequestDocument instance() {
        return INSTANCE;
    }

}
