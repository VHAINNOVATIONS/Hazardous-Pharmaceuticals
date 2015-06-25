/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.pmi.request.PmiRequest;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class PmiRequestDocument extends gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<PmiRequest> {
    private static final String[] CDATA_ELEMENTS = {};
    private static final String[] SCHEMA_FILES = new String[] {"xml/schema/common.xsd",
                                                               "xml/schema/drug/pmi/patientMedicationInformationRequest.xsd"};
    private static final String[] XSL_FILES = new String[] {};
    private static final PmiRequestDocument INSTANCE = new PmiRequestDocument();

    /**
     * Protected constructor
     */
    private PmiRequestDocument() {
        super(PmiRequest.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static PmiRequestDocument instance() {
        return INSTANCE;
    }

}
