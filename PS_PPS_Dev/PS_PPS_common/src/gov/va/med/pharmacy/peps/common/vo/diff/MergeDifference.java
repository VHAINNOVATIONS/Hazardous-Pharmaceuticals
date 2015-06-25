/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.diff;


import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * MergeDifference's brief summary
 * 
 * Details of MergeDifference's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class MergeDifference extends Difference {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    private Object modifiersValue;

    /**
     * Description here
     *
     * @param fieldKey FieldKey representing the field for this difference
     * @param oldValue The value the first user has set
     * @param newValue The value the current user has set
     * @param modifiersValue The original value of the item
     */
    public MergeDifference(FieldKey fieldKey, Object oldValue, Object newValue, Object modifiersValue) {

        super(fieldKey, oldValue, newValue);

        this.modifiersValue = modifiersValue;

    }

    /**
     * setModifiersValue
     * @param modifiersValue the modifiersValue to set
     */
    public void setModifiersValue(Object modifiersValue) {

        this.modifiersValue = modifiersValue;
    }

    /**
     * getModifiersValue
     * @return the modifiersValue
     */
    public Object getModifiersValue() {

        return modifiersValue;
    }

    /**
     * getNewValueShortString.
     * @return newValue.toShortString
     */
    public String getModifiersValueShortString() {

        return getShortString(modifiersValue);
    }

    /**
     * Equals returned by Jakarta Commons EqualsBuilder
     * 
     * @param obj This is the object that will be checked to see if they are equal.
     * @return true if equal
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        MergeDifference thatOne = (MergeDifference) obj;

        return (this.getFieldKey().getKey().equals(thatOne.getFieldKey().getKey())
            && this.getNewValueShortString().equals(thatOne.getNewValueShortString())
            && this.getOldValueShortString().equals(thatOne.getOldValueShortString()) && this.getModifiersValueShortString()
                .equals(thatOne.getModifiersValueShortString()));
    }

    /**
     * HashCode built by Jakarta Commons HasCodeBuilder for the MergeDifference file.
     * @return int hash
     */
    @Override
    public int hashCode() {

        // return the hashcode
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * toString returned by Jakarta Commons ToStringBuilder for the MergeDifferenceVo
     * @return String properties and values of the ValueObject
     */
    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
