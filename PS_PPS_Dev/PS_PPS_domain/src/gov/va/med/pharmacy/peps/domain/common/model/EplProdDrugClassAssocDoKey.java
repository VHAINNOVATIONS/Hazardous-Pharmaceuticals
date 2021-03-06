/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.model;


/**
 * EplProdDrugClassAssocDoKey generated by hbm2java
 * 
 * @hibernate.class
 */
public class EplProdDrugClassAssocDoKey implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = 1L;
    public static final String DRUG_CLASS_ID_FK = "drugClassIdFk";
    public static final String EPL_ID_PRODUCT_FK = "eplIdProductFk";
    
    private Long drugClassIdFk;
    private Long eplIdProductFk;

    // Constructors

    /** default constructor */

    public EplProdDrugClassAssocDoKey() {
    }

    /** full constructor */

    public EplProdDrugClassAssocDoKey(Long drugClassIdFk, Long eplIdProductFk) {
        this.drugClassIdFk = drugClassIdFk;
        this.eplIdProductFk = eplIdProductFk;
    }

    // Property accessors
    public Long getDrugClassIdFk() {
        return this.drugClassIdFk;
    }

    public void setDrugClassIdFk(Long drugClassIdFk) {
        this.drugClassIdFk = drugClassIdFk;
    }

    public Long getEplIdProductFk() {
        return this.eplIdProductFk;
    }

    public void setEplIdProductFk(Long eplIdProductFk) {
        this.eplIdProductFk = eplIdProductFk;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }

        if ((other == null)) {
            return false;
        }

        if (!(other instanceof EplProdDrugClassAssocDoKey)) {
            return false;
        }

        EplProdDrugClassAssocDoKey castOther = (EplProdDrugClassAssocDoKey) other;

        return ((this.getDrugClassIdFk() == castOther.getDrugClassIdFk()) || (this.getDrugClassIdFk() != null
            && castOther.getDrugClassIdFk() != null && this.getDrugClassIdFk().equals(castOther.getDrugClassIdFk())))
            && ((this.getEplIdProductFk() == castOther.getEplIdProductFk()) || (this.getEplIdProductFk() != null
                && castOther.getEplIdProductFk() != null && this.getEplIdProductFk().equals(castOther.getEplIdProductFk())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getDrugClassIdFk() == null ? 0 : this.getDrugClassIdFk().hashCode());
        result = 37 * result + (getEplIdProductFk() == null ? 0 : this.getEplIdProductFk().hashCode());

        return result;
    }

}
