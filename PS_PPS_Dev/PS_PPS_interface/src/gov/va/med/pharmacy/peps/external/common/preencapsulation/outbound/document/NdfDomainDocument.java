/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.ndf.NdfDomain;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class NdfDomainDocument extends gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<NdfDomain> {

    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] {
        "xml/schema/common.xsd", "xml/schema/ndf/drugManufacturerFile.xsd", "xml/schema/ndf/vaGenericFile.xsd",
        "xml/schema/ndf/drugIngredientsFile.xsd", "xml/schema/ndf/drugUnitsFile.xsd", "xml/schema/ndf/vaDispenseUnitFile.xsd",
        "xml/schema/ndf/vaDrugClassFile.xsd", "xml/schema/domain/ndfDomain.xsd" };
    private static final String[] XSL_FILES = new String[] { "xml/xsl/inactivationRules.xsl" };
    private static final NdfDomainDocument INSTANCE = new NdfDomainDocument();

    /**
     * Protected constructor
     */
    private NdfDomainDocument() {
        super(NdfDomain.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static NdfDomainDocument instance() {
        return INSTANCE;
    }

}
