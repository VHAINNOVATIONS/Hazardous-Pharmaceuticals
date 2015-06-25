/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.document;


import gov.va.med.pharmacy.peps.external.common.vo.status.response.Response;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class ResponseDocument extends gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<Response> {
    private static final String[] CDATA_ELEMENTS = {};
    private static final String[] SCHEMA_FILES = new String[] {"xml/schema/common.xsd", "xml/schema/status/response.xsd"};
    private static final String[] XSL_FILES = new String[] {};
    private static final ResponseDocument INSTANCE = new ResponseDocument();

    /**
     * Protected constructor
     */
    private ResponseDocument() {
        super(Response.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }
    
    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static ResponseDocument instance() {
        return INSTANCE;
    }


}
