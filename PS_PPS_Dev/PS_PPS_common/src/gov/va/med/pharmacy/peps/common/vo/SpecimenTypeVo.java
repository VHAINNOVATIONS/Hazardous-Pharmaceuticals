/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Specimen type
 */
public class SpecimenTypeVo extends ValueObject implements Selectable {

    private static final long serialVersionUID = 1L;

    private String value;

    /**
     * The SpeciminTyoeVo GetId field
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {

        return getValue();
    }

    /**
     * Description
     * 
     * @return value property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    public String getValue() {

        return value;
    }

    /**
     * Description
     * 
     * @param value property
     */
    public void setValue(String value) {

        this.value = trimToEmpty(value);
    }

    /**
     * Description
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#toShortString()
     */
    public String toShortString() {

        return getValue();
    }

    /**
     * Added specific code for null value objects to = others for the SpecimenTypeVo.
     * 
     * @param other object to compare too
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {

        if (other instanceof SpecimenTypeVo) {
            return super.equals(other);
        }

        if ((this.value == null || this.value.length() == 0) && other == null) {

            return true;
        }

        return false;
    }

    /**
     * Override of hashCode to prevent PMD errors for the SpecimenTypeVo.
     * 
     * @return hashCode
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#hashCode()
     */
    public int hashCode() {

        int specimenTypeHasCode = super.hashCode();
        
        return specimenTypeHasCode;
        
    }
}
