/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.diff;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;


/**
 * Represent a difference between two values of a field within a ValueObject.
 */
public class Difference implements Serializable {

    private static final int MAX_STRING_LEN_CHARS = 500;
    private static final int MAX_LIST_STRING_LEN_CHARS = 5 * MAX_STRING_LEN_CHARS;
    private static final String EDITABLE = "[Editable] ";
    private static final String NON_EDITABLE = "[Non-Editable] ";
    private static final String NEW_LINE = "  \n";
    private static final String NO_VALUE = "No Value Specified";
    private static final String NO_SELECTION = "No Selection Made";

    private static final long serialVersionUID = 1L;

    private FieldKey fieldKey;
    private Object oldValue;
    private Object newValue;

    /**
     * Set the FieldKey, old value, and new value.
     * 
     * @param fieldKey FieldKey representing the field for this difference
     * @param oldValue Object old value before the change
     * @param newValue Object new, current value
     */
    public Difference(FieldKey fieldKey, Object oldValue, Object newValue) {

        this.fieldKey = fieldKey;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * getFieldKey.
     * @return fieldKey property
     */
    public FieldKey getFieldKey() {

        return fieldKey;
    }

    /**
     * setFieldKey.
     * @param fieldKey fieldKey property
     */
    public void setFieldKey(FieldKey fieldKey) {

        this.fieldKey = fieldKey;
    }

    /**
     * getOldValue.
     * @return oldValue property
     */
    public Object getOldValue() {

        return oldValue;
    }

    /**
     * getOldValueShortString.
     * @return oldValue.toShortString
     */
    public String getOldValueShortString() {

        return getShortString(oldValue, false);
    }

    /**
     * This method returns the short string representation of this object.
     * @param value value to get short string for
     * @return short string for the given value
     */
    protected String getShortString(Object value) {

        if (value == null) {
            return NO_VALUE;
        } else if (value instanceof DataField) {
            DataField dField = (DataField) value;

            String dfOutput = null;

            if (dField.isEmpty()) {
                dfOutput = NO_SELECTION;
            } else {
                dfOutput = limitLengthOfString(dField.toShortString());
            }

            if (dField.isEditable()) {
                return EDITABLE + dfOutput;
            } else {
                return NON_EDITABLE + dfOutput;
            }
            
            // Limit the length of the string if this is a ValueObject
        } else if (value instanceof ValueObject) {
            return limitLengthOfString(((ValueObject) value).toShortString());
        } else if (value instanceof Collection) {
            Object[] values = ((Collection) value).toArray();

            if (values.length <= 0) {
                return NO_SELECTION;
            } else if (values.length == 1) {
                return getShortString(values[0]);
            }

            StringBuffer sb = new StringBuffer();

            for (Object oNext : values) {
                sb.append(getShortString(oNext) + NEW_LINE);
            }

            return limitLengthOfString(sb.toString(), MAX_LIST_STRING_LEN_CHARS);
        } else {
            return limitLengthOfString(value.toString());
        }
    }

    /**
     * This method returns the short string representation of this object.
     * @param value value to get short string for
     * @param isNewValue true if newValue request
     * @return short string for the given value
     */
    protected String getShortString(Object value, Boolean isNewValue) {

        if (value == null) {
            return NO_VALUE;
        } else if (value instanceof DataField) {
            DataField dField = (DataField) value;

            String dfOutput = null;

            if (dField.isEmpty()) {
                dfOutput = NO_SELECTION;
            }  else if (getFieldKey().equals(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT)) {
                dfOutput = limitLengthOfString(dField.toShortString());
                
                if ("0".equalsIgnoreCase(dfOutput)) {
                    dfOutput = "0.00";
                }
            } else {
                dfOutput = limitLengthOfString(dField.toShortString());
            }

            // if the data field is an editable data field.
            if (dField.isEditable()) {
                return EDITABLE + dfOutput;
            } else {
                return NON_EDITABLE + dfOutput;
            }
        } else if (value instanceof ValueObject) {
            return limitLengthOfString(((ValueObject) value).toShortString());
        } else if (value instanceof Collection) {
            Object[] values = ((Collection) value).toArray();
            Object[] compValues = null;

            if (isNewValue) {
                compValues = ((Collection) this.oldValue).toArray();
            } else {
                compValues = ((Collection) this.newValue).toArray();
            }

            // removed the nonused values
            List diff = ListUtils.subtract(Arrays.asList(values), Arrays.asList(compValues));

            if (diff.size() <= 0) {
                return NO_SELECTION;
            } else if (diff.size() == 1) {
                return getShortString(diff);
            }

            StringBuffer sb = new StringBuffer();

            for (Object oNext : diff) {
                sb.append(getShortString(oNext) + NEW_LINE);
            }

            return limitLengthOfString(sb.toString(), MAX_LIST_STRING_LEN_CHARS);
        } else {
            return limitLengthOfString(value.toString());
        }
    }

    /**
     * Truncate a String if it is longer than the given max allowable length.
     * 
     * @param inString to limit length
     * @param maxLen maximum length
     * @return truncated string
     * 
     */
    protected String limitLengthOfString(String inString, int maxLen) {

        if (inString == null) {
            return NO_VALUE;
        }

        if (inString.length() > maxLen) {
            return inString.substring(0, maxLen) + " ...(more not shown)";
        }

        return inString;
    }

    /**
     * Truncate a string if it is longer than the max allowable length.
     * 
     * @param inString to limit length
     * @return truncated string
     */
    protected String limitLengthOfString(String inString) {

        return limitLengthOfString(inString, MAX_STRING_LEN_CHARS);
    }

    /**
     * setOldValue.
     * @param oldValue oldValue property
     */
    public void setOldValue(Object oldValue) {

        this.oldValue = oldValue;
    }

    /**
     * getNewValue.
     * 
     * @return newValue property
     */
    public Object getNewValue() {

        return newValue;
    }

    /**
     * getNewValueShortString.
     * @return newValue.toShortString
     */
    public String getNewValueShortString() {

        return getShortString(newValue, true);
    }

    /**
     * setNewValue.
     * @param newValue newValue property
     */
    public void setNewValue(Object newValue) {

        this.newValue = newValue;
    }

    /**
     * Equals returned by Jakarta Commons EqualsBuilder
     * 
     * @param obj This is the Object to check equals against
     * @return true if equal
     */
    @Override
    public boolean equals(Object obj) {

        // create a surrogate.
        Difference thatOne = (Difference) obj;

        return (this.fieldKey.getKey().equals(thatOne.getFieldKey().getKey())
            && this.getNewValueShortString().equals(thatOne.getNewValueShortString()) && this.getOldValueShortString()
                .equals(thatOne.getOldValueShortString()));

        //return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * HashCode built by Jakarta Commons HasCodeBuilder for this object.
     * 
     * @return int hash
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this); // Difference.java
    }

    /**
     * toString returned by Jakarta Commons ToStringBuilder for Difference.java
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
