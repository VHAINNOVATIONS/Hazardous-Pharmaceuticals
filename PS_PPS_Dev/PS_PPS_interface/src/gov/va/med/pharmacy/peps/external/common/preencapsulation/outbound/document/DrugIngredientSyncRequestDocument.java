/**
 * Source file created in 2011 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugingredientsyncrequest.DrugIngredientSyncRequest;


/**
 * DrugIngredientSyncRequestDocument's brief summary
 * 
 * Marshal and unmarshal Drug Ingredient Sync Requests into Java Ojects.
 *
 */
public class DrugIngredientSyncRequestDocument 
    extends gov.va.med.pharmacy.peps.external.common.utility.XmlDocument<DrugIngredientSyncRequest> {
    private static final String[] CDATA_ELEMENTS = { toNamespace(AbstractWordProcessingField.class) + "^wordProcessingFile" };
    private static final String[] SCHEMA_FILES = new String[] { "xml/schema/sync/drugIngredientSyncRequest.xsd" };
    private static final String[] XSL_FILES = new String[] {};
    private static final DrugIngredientSyncRequestDocument INSTANCE = new DrugIngredientSyncRequestDocument();
    
    /**
     * Protected constructor
     */
    private DrugIngredientSyncRequestDocument() {
        super(DrugIngredientSyncRequest.class, CDATA_ELEMENTS, SCHEMA_FILES,
                XSL_FILES);
    }

    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static DrugIngredientSyncRequestDocument instance() {
        return INSTANCE;
    }

}
