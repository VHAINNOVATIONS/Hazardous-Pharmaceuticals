/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.pdm.PdmDomain;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class PdmDomainDocument extends gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<PdmDomain> {

    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] {
        "xml/schema/common.xsd", "xml/schema/pdm/dosageFormFile.xsd", "xml/schema/pdm/administrationScheduleFile.xsd",
        "xml/schema/pdm/drugTextFile.xsd", "xml/schema/pdm/orderUnitFile.xsd", "xml/schema/pdm/rxConsultFile.xsd",
        "xml/schema/pdm/medicationInstructionFile.xsd", "xml/schema/pdm/pharmacySystemFile.xsd",
        "xml/schema/pdm/standardMedicationRoutesFile.xsd", "xml/schema/pdm/medicationRoutesFile.xsd",
        "xml/schema/pdm/doseUnitsFile.xsd", "xml/schema/domain/pdmDomain.xsd" };
    private static final String[] XSL_FILES = new String[] { "xml/xsl/inactivationRules.xsl" };
    private static final PdmDomainDocument INSTANCE = new PdmDomainDocument();

    /**
     * Protected constructor
     */
    private PdmDomainDocument() {
        super(PdmDomain.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static PdmDomainDocument instance() {
        return INSTANCE;
    }
}
