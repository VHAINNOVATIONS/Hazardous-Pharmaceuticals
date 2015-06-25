/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.productitem.ProductItem;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class ProductItemDocument extends gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<ProductItem> {
    private static final String[] CDATA_ELEMENTS = {toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile"};
    private static final String[] SCHEMA_FILES = new String[] {
        "xml/schema/common.xsd", "xml/schema/ndf/vaGenericFile.xsd", "xml/schema/ndf/drugUnitsFile.xsd",
        "xml/schema/pdm/dosageFormFile.xsd", "xml/schema/ndf/vaDispenseUnitFile.xsd", "xml/schema/ndf/drugIngredientsFile.xsd",
        "xml/schema/ndf/vaDrugClassFile.xsd", "xml/schema/pdm/doseUnitsFile.xsd", "xml/schema/pdm/drugTextFile.xsd",
        "xml/schema/pdm/orderUnitFile.xsd", "xml/schema/pdm/standardMedicationRoutesFile.xsd",
        "xml/schema/pdm/medicationRoutesFile.xsd", "xml/schema/pdm/rxConsultFile.xsd", "xml/schema/ndf/vaProductFile.xsd",
        "xml/schema/pdm/drugFile.xsd", "xml/schema/item/productItem.xsd" };
    private static final String[] XSL_FILES = new String[] {"xml/xsl/inactivationRules.xsl"};
    private static final ProductItemDocument INSTANCE = new ProductItemDocument();

    /**
     * Protected constructor
     */
    private ProductItemDocument() {
        super(ProductItem.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static ProductItemDocument instance() {
        return INSTANCE;
    }
}
