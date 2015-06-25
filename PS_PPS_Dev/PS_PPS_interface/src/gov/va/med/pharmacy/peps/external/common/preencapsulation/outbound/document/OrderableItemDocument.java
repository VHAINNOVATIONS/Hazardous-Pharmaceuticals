/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.orderableitem.OrderableItem;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class OrderableItemDocument extends gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<OrderableItem> {

    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] {
        "xml/schema/common.xsd", "xml/schema/pdm/dosageFormFile.xsd", "xml/schema/pdm/standardMedicationRoutesFile.xsd",
        "xml/schema/pdm/medicationRoutesFile.xsd", "xml/schema/pdm/pharmacyOrderableItemFile.xsd",
        "xml/schema/item/orderableItem.xsd" };
    private static final String[] XSL_FILES = new String[] { "xml/xsl/inactivationRules.xsl" };
    private static final OrderableItemDocument INSTANCE = new OrderableItemDocument();

    /**
     * Protected constructor
     */
    private OrderableItemDocument() {
        super(OrderableItem.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static OrderableItemDocument instance() {
        return INSTANCE;
    }
}
