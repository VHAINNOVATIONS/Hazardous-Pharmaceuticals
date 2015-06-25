/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Represents a column displayed in a table
 */
public class Column extends ValueObject {
    private static final long serialVersionUID = 1L;
 
    private FieldKey fieldKey;
    private boolean sortable;

    /**
     * Set the FieldKey to display in this column
     * <p>
     * Defaults {@link #sortable} to true.
     * 
     * @param fieldKey FieldKey
     */
    public Column(FieldKey fieldKey) {
        this(fieldKey, true);
    }

    /**
     * Set the FieldKey to display in this column and if the column is sortable.
     * <p>
     * NOTE: If the given {@link FieldKey} is not sortable, then the sortable will always defualt to false, no matter the
     * value provided as a parameter.
     * 
     * @param fieldKey {@link FieldKey}
     * @param sortable boolean true if this column should be sortable
     * 
     * @see FieldKey#isSortable()
     */
    public Column(FieldKey fieldKey, boolean sortable) {
        this.fieldKey = fieldKey;

        if (fieldKey.isSortable()) {
            this.sortable = sortable;
        } else {
            this.sortable = false;
        }
    }

    /**
     * getFieldKey.
     * @return fieldKey property
     */
    public FieldKey getFieldKey() {
        return fieldKey;
    }

    /**
     * isInputColumn.
     * @return boolean true if this instance of Column is for an InputColumn
     */
    public boolean isInputColumn() {
        return this instanceof InputColumn;
    }

    /**
     * isFlowLinkColumn.
     * @return boolean true if this instance of Column is for a FlowLinkColumn
     */
    public boolean isFlowLinkColumn() {
        return this instanceof FlowLinkColumn;
    }

    /**
     * isLocalizableResumeFlowLinkColumn.
     * @return boolean true if this instance of Column is for a LocalizableResumeFlowLinkColumn
     */
    public boolean isLocalizableResumeFlowLinkColumn() {
        return this instanceof LocalizableResumeFlowLinkColumn;
    }

    /**
     * isResumeFlowLinkColumn.
     * @return boolean true if this instance of Column is for a ResumeFlowLinkColumn
     */
    public boolean isResumeFlowLinkColumn() {
        return this instanceof ResumeFlowLinkColumn;
    }

    /**
     * isLinkColumn.
     * @return boolean true if this instance of Column is for a LinkColumn
     */
    public boolean isLinkColumn() {
        return this instanceof LinkColumn;
    }

    /**
     * isLocalizableLinkColumn.
     * @return boolean true if this instance of Column is for a LocalizableResumeFlowLinkColumn
     */
    public boolean isLocalizableLinkColumn() {
        return this instanceof LocalizableLinkColumn;
    }

    /**
     * isRedirectFlowLinkColumn.
     * @return boolean true if this instance of Column is for a RedirectFlowLinkColumn
     */
    public boolean isRedirectFlowLinkColumn() {
        return this instanceof RedirectFlowLinkColumn;
    }

    /**
     * isPopupLinkColumn.
     * @return boolean true if this instance of Column is for a pop-up link
     */
    public boolean isPopupLinkColumn() {
        return this instanceof PopupLinkColumn;
    }

    /**
     * Return true if this column is allowed to be sorted (it may not actually be sorted).
     * 
     * @return sortable property
     * @see #isSorted()
     */
    public boolean isSortable() {
        return sortable;
    }

    /**
     * isTextColumn.
     * @return boolean true if this instance of Column is for a TextColumn
     */
    public boolean isTextColumn() {
        return this instanceof TextColumn;
    }

    /**
     * setFieldKey.
     * @param fieldKey fieldKey property
     */
    public void setFieldKey(FieldKey fieldKey) {
        this.fieldKey = fieldKey;
    }

    /**
     * Set if this column should be allowed to be sorted (not if it is sorted).
     * 
     * @param sortable sortable property
     * @see #setSorted()
     */
    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }
}
