/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Represents a column of a table displayed as plain text.
 */
public class TextColumn extends Column {
    private static final long serialVersionUID = 1L;

    /**
     * Set the FieldKey to display in this column
     * 
     * @param fieldKey FieldKey
     */
    public TextColumn(FieldKey fieldKey) {
        super(fieldKey);
    }

    /**
     * Set the FieldKey to display in this column
     * 
     * @param fieldKey FieldKey
     * @param sortable boolean true if this column should be sortable
     */
    public TextColumn(FieldKey fieldKey, boolean sortable) {
        super(fieldKey, sortable);
    }
}
