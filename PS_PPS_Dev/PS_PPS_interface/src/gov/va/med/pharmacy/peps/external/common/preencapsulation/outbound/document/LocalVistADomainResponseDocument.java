/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.facility.vista.response.VistaDomainsResponse;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class LocalVistADomainResponseDocument extends
    gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<VistaDomainsResponse> {

    private static final String[] CDATA_ELEMENTS = {};
    private static final String[] SCHEMA_FILES = new String[] {
        "xml/schema/common.xsd", "xml/schema/facility/localVistADomainResponse.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final LocalVistADomainResponseDocument INSTANCE = new LocalVistADomainResponseDocument();

    /**
     * Protected constructor
     */
    private LocalVistADomainResponseDocument() {
        super(VistaDomainsResponse.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static LocalVistADomainResponseDocument instance() {
        return INSTANCE;
    }

}
