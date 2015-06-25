/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.request.DrugAccountabilityRequest;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class DrugAccountabilityRequestDocument extends
    gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<DrugAccountabilityRequest> {

    private static final String[] CDATA_ELEMENTS = {};
    private static final String[] SCHEMA_FILES =
        new String[] { "xml/schema/drug/drugaccountability/drugAccountabilityRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final DrugAccountabilityRequestDocument INSTANCE = new DrugAccountabilityRequestDocument();

    /**
     * Protected constructor
     */
    private DrugAccountabilityRequestDocument() {
        super(DrugAccountabilityRequest.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static DrugAccountabilityRequestDocument instance() {
        return INSTANCE;
    }

}
