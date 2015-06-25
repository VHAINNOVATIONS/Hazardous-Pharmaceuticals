/**
 * Source file created in 2012 by Southwest Research Institute
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


/**
 * Represent a difference between two values of a field within a ValueObject.
 */
public class GroupDifference extends Difference implements Serializable {
    
    private static final int MAX_STRING_LEN_CHARS = 500;
    private static final int MAX_LIST_STRING_LEN_CHARS = 5 * MAX_STRING_LEN_CHARS;
    private static final String NO_VALUE = "No Value Specified";
    private static final String NO_SELECTION = "No Selection Made";
    private static final String NEW_LINE = "  \n";

    private static final long serialVersionUID = 1L;

    private FieldKey fieldKey;
    private Object oldValue; //really currentValue from ManagedItemCapabilityImpl
    private Object newValue;
    private boolean isNewRow;
    private int numberOfChanges;
    private boolean fieldChanged;
    private String oldValueShortString;
    private String newValueShortString;
    private boolean removedRow;

    /**
     * Set the FieldKey, old value, and new value.
     * 
     * @param fieldKey FieldKey representing the field for this difference
     * @param oldValue Object old value before the change
     * @param newValue Object new, current value
     * @param isNewRow boolean isNewRow
     * @param fieldChanged boolean fieldChanged
     * @param removedRow boolean removedRow
     * @param oldValueShortString String oldValueShortString
     */
    public GroupDifference(FieldKey fieldKey, Object oldValue, Object newValue, boolean isNewRow, 
        boolean fieldChanged, boolean removedRow, String oldValueShortString, String newValueShortString) {

        super(fieldKey, oldValue, newValue);

        this.fieldKey = fieldKey;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.isNewRow = isNewRow;
        this.fieldChanged = fieldChanged;
        this.removedRow = removedRow;
        this.oldValueShortString = oldValueShortString;
        this.newValueShortString = newValueShortString;

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
    protected String getShortString(Object value, boolean isNewValue) {

        if (value == null) {

            return NO_VALUE;
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
            
            List diff = ListUtils.subtract(Arrays.asList(values), Arrays.asList(compValues));

            if (!isNewValue) {
                if (getShortString(value).equalsIgnoreCase(NO_SELECTION)) {
                    return "None";
                } else {
                    return getShortString(value);
                } 
            } else if (isNewValue && fieldChanged) {
                diff = Arrays.asList(compValues);
            } else if (isNewValue && isNewRow) {
                diff = ListUtils.union(Arrays.asList(compValues), Arrays.asList(diff));
            } else {
                diff = Arrays.asList(compValues);
            }
            
            StringBuffer sb = new StringBuffer();
            String[] oldValues = oldValueShortString.split("\n"); 
            String[] newValues = newValueShortString.split("\n"); 
            String[] shortValues = getShortString(value).split("\n");
            String[] diffValues = getShortString(diff).split("\n");

            int oldValuesLength = oldValues.length;
            int shortValuesLength = shortValues.length;
            int diffValuesLength = diffValues.length;
            int newValuesLength = newValues.length;
            int incr = 0;
            int oNextCount = 0;
            String rowsRemovedSet = "";
            
            for (Object oNext : diff) {
                boolean matched = false;
                System.out.println("");
                System.out.println("oNext  = " + getShortString(oNext));
                
                if (isNewValue && fieldChanged) {
                    
                    for (int y = 0; y < oldValuesLength; y++) {
                        
                        //if the old value is changed, replace it with the new value 
                        if (getShortString(oNext).equalsIgnoreCase(oldValues[y].trim())) {
                            sb.append(shortValues[y] + NEW_LINE); //add new value
                            matched = true;
                            break;
                            
                        } 
                    } 
                    
                    //if the old value is unchanged, add the old value
                    if (!matched) {
                        sb.append(getShortString(oNext) + NEW_LINE);
                        System.out.println("adding old value" + getShortString(oNext));
                    }
                    
                    oNextCount++;
                    
                    //after the fieldChanged items, add any new rows
                    if (shortValuesLength > oldValuesLength
                        && oNextCount == diffValuesLength) {
                        for (int x = oldValuesLength; x < shortValuesLength; x++) {
                            System.out.println("adding new row " + shortValues[x]);
                            sb.append(shortValues[x] + NEW_LINE);
                        }   
                       
                    }
                    
                } //end if (isNewValue && fieldChanged)
                
                //removing a row without changing a field
                if (isNewValue && removedRow) {
                    boolean foundIt = false;
                    
                    if (shortValues[0].equalsIgnoreCase(NO_SELECTION)) {
                        for (int z = 0; z < oldValuesLength; z++) {
                            
                            //compare the fields and don't add the removed field
                            if (getShortString(oNext).equalsIgnoreCase(oldValues[z].trim())) {
                                foundIt = true;
                                break;
                            } 
                        }
                        
                        if  (!foundIt) {
                            sb.append(getShortString(oNext) + NEW_LINE);
                        }
                        
                    } else {
                        StringBuffer w = new StringBuffer();
                        
                        //remove multiple rows when shortValues != No Selection Made
                        //remove the new values from old values to get the list of removed fields
                        if (rowsRemovedSet == "") {
                            
                            int ctr = 0;
                            
                            for (String a : oldValues) {
                                boolean inTheList = false;
                                
                                for (String b : newValues) {
                                    
                                    if (a.trim().equalsIgnoreCase(b.trim())) {
                                        inTheList = true;
                                        ctr++;
                                        break;
                                    } 
                                }
                                
                                if (!inTheList) {
                                    rowsRemovedSet = rowsRemovedSet + a + NEW_LINE;
                                    incr++;
                                }
                                
                            }
                            
                            //removed multiple rows and added a new row
                            if (ctr < newValuesLength) {
                                boolean gotIt = false;

                                for (String c :diffValues) {
                                    if (c.equalsIgnoreCase(newValueShortString)) {
                                        gotIt = true;
                                    }
                                }
                                
                                if (!gotIt) {
                                    w.append(newValueShortString + NEW_LINE);
                                }
                            }                            
                        } // end if (rowsRemovedSet == "")

                        
                        String[] rowsRemoved = rowsRemovedSet.split("\n");
                        int rowsRemovedLength = rowsRemoved.length;

                        for (int r = 0; r < rowsRemovedLength; r++) {

                            if (getShortString(oNext).equalsIgnoreCase(rowsRemoved[r].trim())) {
                                foundIt = true;
                                break;
                            } 
                        }
                        
                        if  (!foundIt) {
                            System.out.println("adding " + getShortString(oNext));
                            sb.append(getShortString(oNext) + NEW_LINE);
                        }
                        
                        sb.append(w);
                        
                    } //end else

                } 
                
                //adding original values and new rows when there are no associated field changes
                if (isNewValue && isNewRow && !fieldChanged) {
                    sb.append(getShortString(oNext) + NEW_LINE);            
                }
                
            } // end for (Object oNext : diff) {
            
            
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
     * isNewRow.
     * @return isNewRow isNewRow property
     */
    public boolean isNewRow() {
    
        return isNewRow;
    }

    /**
     * setNewRow.
     * @param newRow newRow property
     */
    public void setNewRow(boolean newRow) {
    
        this.isNewRow = newRow;
    }

    /**
     * getNumberOfChanges.
     * @return numberOfChanges numberOfChanges property
     */
    public int getNumberOfChanges() {
    
        return numberOfChanges;
    }

    /**
     * setNumberOfChanges.
     * @param numberOfChanges numberOfChanges property
     */
    public void setNumberOfChanges(int numberOfChanges) {
    
        this.numberOfChanges = numberOfChanges;
    }

    /**
     * isFieldChanged.
     * @return fieldChanged fieldChanged property
     */
    public boolean isFieldChanged() {
    
        return fieldChanged;
    }

    /**
     * setFieldChanged.
     * @param fieldChanged fieldChanged property
     */
    public void setFieldChanged(boolean fieldChanged) {
    
        this.fieldChanged = fieldChanged;
    }

    /**
     * setOldValueShortString.
     * @param oldValueShortString oldValueShortString property
     */
    public void setOldValueShortString(String oldValueShortString) {
    
        this.oldValueShortString = oldValueShortString;
    }

    /**
     * Equals returned by Jakarta Commons EqualsBuilder
     * 
     * @param obj Object to check equals against
     * @return true if equal
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        Difference thatOne = (Difference) obj;

        return (this.fieldKey.getKey().equals(thatOne.getFieldKey().getKey())
            && this.getNewValueShortString().equals(thatOne.getNewValueShortString()) && this.getOldValueShortString()
                .equals(thatOne.getOldValueShortString()));

        //return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * HashCode built by Jakarta Commons HasCodeBuilder
     * 
     * @return int hash
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * toString returned by Jakarta Commons ToStringBuilder
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
