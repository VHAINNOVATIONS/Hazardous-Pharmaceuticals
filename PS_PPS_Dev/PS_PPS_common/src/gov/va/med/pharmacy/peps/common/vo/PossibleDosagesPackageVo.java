/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data Representing PossibleDosagesPackage
 */
public class PossibleDosagesPackageVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;
    private String value;

    /**
     * the constructor
     */
    public PossibleDosagesPackageVo() {
        super();
    }

    /**
     * getId for PossibleDosagesPackageVo.
     * 
     * @return id property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {
        return value;
    }

    /**
     * getValue  for PossibleDosagesPackageVo.
     * 
     * @return value property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    public String getValue() {
        return value;
    }

    /**
     * setValue  for PossibleDosagesPackageVo.
     * 
     * @param value of String type
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString  for PossibleDosagesPackageVo.
     * 
     * @return short string value
     */
    @Override
    public String toShortString() {
        return value;
    }
}
