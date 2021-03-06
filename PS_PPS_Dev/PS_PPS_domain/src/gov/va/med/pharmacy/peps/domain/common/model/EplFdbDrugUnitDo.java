

package gov.va.med.pharmacy.peps.domain.common.model;


// Generated Nov 18, 2011 1:24:26 PM by Hibernate Tools 3.2.4.GA

import java.util.Date;


/**
 * EplFdbDrugUnitDo generated by hbm2java
 * 
 * @hibernate.class
 * 
 */
public class EplFdbDrugUnitDo extends gov.va.med.pharmacy.peps.domain.common.model.DataObject implements java.io.Serializable {

    // Fields
    public static final String FDB_DRUG_STRENGTH_UNITS = "fdbDrugStrengthunits";
    public static final String EPL_DRUG_UNITS_FK = "eplDrugUnitsFk";

    private String fdbDrugStrengthunits;
    private Long eplDrugUnitsFk;
    private String createdBy;
    private Date createdDtm;
    private String lastModifiedBy;
    private Date lastModifiedDtm;

    private static final long serialVersionUID = 1L;

    /**
     * Default Constructor
     */
    public EplFdbDrugUnitDo() {
    }

    /**
     * Minimal Constructor
     * 
     * @param id
     * @param fdbDrugStrengthunits
     * @param createdBy
     * @param createdDtm
     */
    public EplFdbDrugUnitDo(String fdbDrugStrengthunits, String createdBy, Date createdDtm) {
        this.fdbDrugStrengthunits = fdbDrugStrengthunits;
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
    }

    /**
     * Full Constructor
     * 
     * @param id
     * @param fdbDrugStrengthunits
     * @param createdBy
     * @param createdDtm
     * @param lastModifiedBy
     * @param lastModifiedDtm
     * @param eplFdbDrugUnitsAssocs
     */
    public EplFdbDrugUnitDo(String fdbDrugStrengthunits, Long eplDrugUnitsFk, String createdBy,
                            Date createdDtm, String lastModifiedBy, Date lastModifiedDtm) {
        this.fdbDrugStrengthunits = fdbDrugStrengthunits;
        this.eplDrugUnitsFk = eplDrugUnitsFk;
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
        this.setLastModifiedBy(lastModifiedBy);
        this.setLastModifiedDtm(lastModifiedDtm);
    }

    /**
     * 
     * @return fdbDrugStrengthunits
     */
    public String getFdbDrugStrengthunits() {
        return this.fdbDrugStrengthunits;
    }

    /**
     * 
     * @param fdbDrugStrengthunits
     */
    public void setFdbDrugStrengthunits(String fdbDrugStrengthunits) {
        this.fdbDrugStrengthunits = fdbDrugStrengthunits;
    }

    /**
     * 
     * @return eplDrugUnitsFk
     */
    public Long getEplDrugUnitsFk() {
        return eplDrugUnitsFk;
    }

    /**
     * 
     * @param eplDrugUnitsFk
     */
    public void setEplDrugUnitsFk(Long eplDrugUnitsFk) {
        this.eplDrugUnitsFk = eplDrugUnitsFk;
    }

    @Override
    public String getCreatedBy() {
        return this.createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getCreatedDtm() {
        return this.createdDtm;
    }

    @Override
    public void setCreatedDtm(Date createdDtm) {
        this.createdDtm = createdDtm;
    }

    @Override
    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    @Override
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public Date getLastModifiedDtm() {
        return this.lastModifiedDtm;
    }

    @Override
    public void setLastModifiedDtm(Date lastModifiedDtm) {
        this.lastModifiedDtm = lastModifiedDtm;
    }

    /* (non-Javadoc)
     * @see gov.va.med.pharmacy.peps.domain.common.model.DataObject#toString()
     */
    @Override
    public String toString() {
        return "EplFdbDrugUnitDo [fdbDrugStrengthunits=" + fdbDrugStrengthunits
               + ", eplDrugUnitsFk=" + eplDrugUnitsFk + ", createdBy=" + createdBy + ", createdDtm=" + createdDtm
               + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDtm=" + lastModifiedDtm + "]";
    }

    /* (non-Javadoc)
     * @see gov.va.med.pharmacy.peps.domain.common.model.DataObject#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
        result = prime * result + ((createdDtm == null) ? 0 : createdDtm.hashCode());
        result = prime * result + ((eplDrugUnitsFk == null) ? 0 : eplDrugUnitsFk.hashCode());
        result = prime * result + ((fdbDrugStrengthunits == null) ? 0 : fdbDrugStrengthunits.hashCode());
        result = prime * result + ((lastModifiedBy == null) ? 0 : lastModifiedBy.hashCode());
        result = prime * result + ((lastModifiedDtm == null) ? 0 : lastModifiedDtm.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see gov.va.med.pharmacy.peps.domain.common.model.DataObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        EplFdbDrugUnitDo other = (EplFdbDrugUnitDo) obj;
        if (createdBy == null) {
            if (other.createdBy != null)
                return false;
        } else if (!createdBy.equals(other.createdBy))
            return false;
        if (createdDtm == null) {
            if (other.createdDtm != null)
                return false;
        } else if (!createdDtm.equals(other.createdDtm))
            return false;
        if (eplDrugUnitsFk == null) {
            if (other.eplDrugUnitsFk != null)
                return false;
        } else if (!eplDrugUnitsFk.equals(other.eplDrugUnitsFk))
            return false;
        if (fdbDrugStrengthunits == null) {
            if (other.fdbDrugStrengthunits != null)
                return false;
        } else if (!fdbDrugStrengthunits.equals(other.fdbDrugStrengthunits))
            return false;
        if (lastModifiedBy == null) {
            if (other.lastModifiedBy != null)
                return false;
        } else if (!lastModifiedBy.equals(other.lastModifiedBy))
            return false;
        if (lastModifiedDtm == null) {
            if (other.lastModifiedDtm != null)
                return false;
        } else if (!lastModifiedDtm.equals(other.lastModifiedDtm))
            return false;
        return true;
    }
}
