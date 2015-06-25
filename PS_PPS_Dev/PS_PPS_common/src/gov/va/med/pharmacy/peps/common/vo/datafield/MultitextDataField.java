/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.datafield;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * DataField representing a list of text values which can be selected or entered by the user.
 * 
 * @param <T> Type of multi-text (can be String or number -- something that is convertible to text)
 */
public class MultitextDataField<T> extends ListDataField<T> {
    private static final long serialVersionUID = 1L;

    /**
     * Set the DataFieldKey.
     * 
     * @param key DataFieldKey key for this DataField instance
     */
    protected MultitextDataField(FieldKey<? extends MultitextDataField<T>> key) {
        super(key);
    }
    
    /**
     * All {@link MultitextDataField} are multi-select.
     * 
     * @return true
     */
    public boolean isMultiselect() {
        return true;
    }
}
