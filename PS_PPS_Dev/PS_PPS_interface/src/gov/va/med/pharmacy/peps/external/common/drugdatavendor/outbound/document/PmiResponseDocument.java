/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.pmi.response.PmiData;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class PmiResponseDocument extends gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<PmiData> {

    private static final String[] CDATA_ELEMENTS = {
        toNamespace(PmiData.class) + "^title", toNamespace(PmiData.class) + "^commonBrandNames",
        toNamespace(PmiData.class) + "^missedDose", toNamespace(PmiData.class) + "^phonetics",
        toNamespace(PmiData.class) + "^howToTake", toNamespace(PmiData.class) + "^drugInteractions",
        toNamespace(PmiData.class) + "^medicalAlert", toNamespace(PmiData.class) + "^notes",
        toNamespace(PmiData.class) + "^overdose", toNamespace(PmiData.class) + "^precautions",
        toNamespace(PmiData.class) + "^storage", toNamespace(PmiData.class) + "^sideEffects",
        toNamespace(PmiData.class) + "^uses", toNamespace(PmiData.class) + "^warnings",
        toNamespace(PmiData.class) + "^disclaimer" };
    private static final String[] SCHEMA_FILES = new String[] {
        "xml/schema/common.xsd", "xml/schema/drug/pmi/patientMedicationInformationResponse.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final PmiResponseDocument INSTANCE = new PmiResponseDocument();

    /**
     * Protected constructor
     */
    private PmiResponseDocument() {
        super(PmiData.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static PmiResponseDocument instance() {
        return INSTANCE;
    }

}
