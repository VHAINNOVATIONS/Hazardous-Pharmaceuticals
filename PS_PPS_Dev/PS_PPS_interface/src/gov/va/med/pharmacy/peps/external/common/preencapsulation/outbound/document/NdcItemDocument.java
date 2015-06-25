/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.ndcitem.NdcItem;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class NdcItemDocument extends gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<NdcItem> {

    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] {
        "xml/schema/common.xsd", "xml/schema/ndf/drugManufacturerFile.xsd", "xml/schema/ndf/ndcUpnFile.xsd",
        "xml/schema/item/ndcItem.xsd" };
    private static final String[] XSL_FILES = new String[] { "xml/xsl/inactivationRules.xsl" };
    private static final NdcItemDocument INSTANCE = new NdcItemDocument();

    /**
     * Protected constructor
     */
    private NdcItemDocument() {
        super(NdcItem.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static NdcItemDocument instance() {
        return INSTANCE;
    }

}
