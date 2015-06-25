/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Represents a column of a table that is displayed as in input box (text field, radio button, drop down, etc.)
 */
public class InputColumn extends Column {
    private static final long serialVersionUID = 1L;
    
    /**
     * Set the FieldKey to display in this column. Input columns are by default not sortable.
     * 
     * @param fieldKey FieldKey
     */
    public InputColumn(FieldKey fieldKey) {
        super(fieldKey);
        
        setSortable(false);
    }
}
