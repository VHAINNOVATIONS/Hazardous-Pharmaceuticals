/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.PEPSResponse;
import gov.va.med.pharmacy.peps.external.common.utility.XmlDocument;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class DrugCheckResponseDocument extends XmlDocument<PEPSResponse> {

    private static final String[] CDATA_ELEMENTS = { toNamespace(PEPSResponse.class) + "^reference" };
    private static final String[] SCHEMA_FILES = { "xml/schema/drug/check/drugCheckResponse.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final DrugCheckResponseDocument INSTANCE = new DrugCheckResponseDocument();

    /**
     * Protected constructor
     */
    private DrugCheckResponseDocument() {
        super(PEPSResponse.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static DrugCheckResponseDocument instance() {
        return INSTANCE;
    }

}
