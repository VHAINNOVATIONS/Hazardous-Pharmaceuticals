/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Pop-up link
 */
public class PopupLinkColumn extends Column {
    private static final long serialVersionUID = 1L;

    private FieldKey otherKey;

    /**
     * PopupLinkColumn.
     * @param fieldKey FieldKey
     * @param otherKey FieldKey
     */
    public PopupLinkColumn(FieldKey fieldKey, FieldKey otherKey) {
        super(fieldKey);

        this.otherKey = otherKey;
    }

    /**
     * PopupLinkColumn.
     * @param fieldKey FieldKey
     * @param sortable boolean
     */
    public PopupLinkColumn(FieldKey fieldKey, boolean sortable) {
        super(fieldKey, sortable);
    }

    /**
     * getOtherKey.
     * @return otherKey property
     */
    public FieldKey getOtherKey() {
        return otherKey;
    }

    /**
     * setOtherKey.
     * @param otherKey otherKey property
     */
    public void setOtherKey(FieldKey otherKey) {
        this.otherKey = otherKey;
    }
}
