/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.facility.vista.request.VistaDomainsRequest;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class LocalVistADomainRequestDocument extends
    gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<VistaDomainsRequest> {

    private static final String[] CDATA_ELEMENTS = {};
    private static final String[] SCHEMA_FILES = new String[] {
        "xml/schema/common.xsd", "xml/schema/facility/localVistADomainRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final LocalVistADomainRequestDocument INSTANCE = new LocalVistADomainRequestDocument();

    /**
     * Protected constructor
     */
    private LocalVistADomainRequestDocument() {
        super(VistaDomainsRequest.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static LocalVistADomainRequestDocument instance() {
        return INSTANCE;
    }

}
