/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.quickaction.drugfiletoexternalinterface.DrugFileToExternalInterface;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class DrugFileToExternalInterfaceDocument extends
    gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<DrugFileToExternalInterface> {

    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] {
        "xml/schema/common.xsd", "xml/schema/quickaction/drugFileToExternalInterface.xsd" };
    private static final String[] XSL_FILES = new String[] { "xml/xsl/inactivationRules.xsl" };

    private static final DrugFileToExternalInterfaceDocument INSTANCE = new DrugFileToExternalInterfaceDocument();

    /**
     * Protected constructor
     */
    private DrugFileToExternalInterfaceDocument() {
        super(DrugFileToExternalInterface.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static DrugFileToExternalInterfaceDocument instance() {
        return INSTANCE;
    }

}
