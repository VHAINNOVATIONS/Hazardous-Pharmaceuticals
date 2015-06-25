/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility;


import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;


/**
 * This helper class is used to provide support for data fields that have not yet been implemented in the data model
 * yet are needed by current presentation and service code.  This class' methods allow presentation and service
 * code to be developed until the data model and data load is able to support the new fields.  <strong>Production 
 * code should have an empty implementation of this method!</strong>  
 */
public class DataFieldPersistenceHelper {

    /**
     * Prevent instance of this class from being used.
     */
    private DataFieldPersistenceHelper() {
    }

    /**
     * Synthesize product data fields that are not yet persisted in the database.  This method allows presentation 
     * and service code to be developed until the data model and data load is able to support the new fields.  
     * <strong>Production code should have an empty implementation of this method!</strong>  
     * 
     * @param fields The DataFields instance to add the fields to.
     */
    public static void removeAllFieldsNotReadyToPersist(DataFields fields) {
        
//        fields.removeDataField(FieldKey.ATC_CHOICE);
    }

    /**
     * Synthesize product data fields that are not yet persisted in the database.  This method allows presentation 
     * and service code to be developed until the data model and data load is able to support the new fields.  
     * <strong>Production code should have an empty implementation of this method!</strong>  
     * 
     * @param fields The DataFields instance to add the fields to.
     */
    public static void addAllFieldsNotYetPersisted(DataFields fields) {
        addProductFieldsNotYetPersisted(fields);
    }

    /**
     * Synthesize product data fields that are not yet persisted in the database.  This method allows presentation 
     * and service code to be developed until the data model and data load is able to support the new fields.  
     * <strong>Production code should have an empty implementation of this method!</strong>  
     * 
     * @param fields The DataFields instance to add the fields to.
     */
    public static void addProductFieldsNotYetPersisted(DataFields fields) {
        
//        // Add an ATC Mode list data field.
//        ListDataField atcMode = (ListDataField) DataField.newInstance(FieldKey.ATC_CHOICE);
//        
//        final String oneAtc = "One ATC";
//        
//        atcMode.addStringSelection(oneAtc);
//        atcMode.addStringSelection("Multiple ATCs");
//        atcMode.addStringDefaultSelection(oneAtc);
//        
//        fields.setDataField(atcMode);
    }
    
}
