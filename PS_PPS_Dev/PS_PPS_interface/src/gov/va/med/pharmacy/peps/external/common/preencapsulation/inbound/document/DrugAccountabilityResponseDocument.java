/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.response.DrugAccountabilityResponse;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class DrugAccountabilityResponseDocument extends
    gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<DrugAccountabilityResponse> {

    private static final String[] CDATA_ELEMENTS = {};
    private static final String[] SCHEMA_FILES =
        new String[] { "xml/schema/drug/drugaccountability/drugAccountabilityResponse.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final DrugAccountabilityResponseDocument INSTANCE = new DrugAccountabilityResponseDocument();

    /**
     * Protected constructor
     */
    private DrugAccountabilityResponseDocument() {
        super(DrugAccountabilityResponse.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static DrugAccountabilityResponseDocument instance() {
        return INSTANCE;
    }

}
