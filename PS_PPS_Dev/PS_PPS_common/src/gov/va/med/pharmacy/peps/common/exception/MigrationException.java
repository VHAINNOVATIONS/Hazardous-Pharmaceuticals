/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.exception;


/**
 * The MigrationException class is used to send Migration exceptions
 *
 */
public class MigrationException extends Exception {

    /**
     * serialVersionUID.
     */
    static final long serialVersionUID = 1;

    // Add custom error fields
    private String ien;
    private String itemName;
    private String field;
    private String mIen;
    private String mField;
    private String fieldValue;

    /**
     * Default Constructor
     */
    public MigrationException() {

        // call superclass constructor
        super(); 

        ien = null;
        itemName = null;
        field = null;
        mIen = null;
        mField = null;
        fieldValue = null;
    }

    /**
     * General File Exception
     * @param message : The message to be put in the exception.
     */
    public MigrationException(String message) {

        // Call super class constructor
        super(message); 

    } 

    /**
     * Constructor: file item (not a multiple) exception
     * @param message : The message value;
     * @param ien : The ien of the item
     * @param item : The item causing the fuss
     * @param field : The field
     * @param fieldValue : The value of the field
     */
    public MigrationException(String message, String ien, String item, String field, String fieldValue) {

        // Call super class constructor
        super(message); 

        // Initialize the custom variables
        this.ien = ien;
        this.itemName = item;
        this.field = field;
        this.fieldValue = fieldValue;
    } 

    /**
     * Constructor: file item (not a multiple) exception
     * @param message : The message value;
     * @param ien : The ien of the item
     * @param item : The item causing the fuss
     * @param field : The field
     * @param mIen : The multiple IEN
     * @param mField : The mulitple field
     * @param fieldValue : The value for the field value
     */
    public MigrationException(String message, String ien, String item, String field, String mIen, 
                              String mField, String fieldValue) {

        // Call super class constructor
        super(message); 

        // Initialize the custom variables
        this.ien = ien;
        this.itemName = item;
        this.field = field;
        this.mIen = mIen;
        this.mField = mField;
        this.fieldValue = fieldValue;
    } 

    /**
     * get IEN
     * @return IEN
     */
    public String getIen() {
        return ien;
    }

    /**
     * get itemName
     * @return itemName
     */    
    public String getItem() {
        return itemName;
    }

    /**
     * get field
     * @return field
     */
    public String getField() {
        return field;
    }

    /**
     * get mIen
     * @return mIen
     */
    public String getmIen() {
        return mIen;
    }

    /**
     * get mField
     * @return mField
     */
    public String getmField() {
        return mField;
    }

    /**
     * get fieldValue
     * @return fieldValue
     */
    public String getfieldValue() {
        return fieldValue;
    }

}

