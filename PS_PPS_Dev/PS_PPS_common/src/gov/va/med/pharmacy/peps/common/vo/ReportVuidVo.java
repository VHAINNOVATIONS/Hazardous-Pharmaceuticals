/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;


/**
 * 
 * ReportVuidVo
 *
 */
public class ReportVuidVo extends ValueObject {

    private static final long serialVersionUID = 1L;
    private String name;
    private String vuid;
    private Date inactivationDate;
    private String ndfProductIen;
    private String ien;

    /**
     * setIen
     * @param ien Ien
     */
    public void setIen(String ien) {

        this.ien = ien;
    }

    /**
     * getNdfIen
     * @return ndfIen
     */
    public String getIen() {

        return ien;
    }

    /**
     * setName
     * @param name name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * getName
     * @return name
     */
    public String getName() {

        return name;
    }

    /**
     * setVuid
     * @param vuid vuid
     */
    public void setVuid(String vuid) {

        this.vuid = vuid;
    }

    /**
     * getVuid
     * @return vuid
     */
    public String getVuid() {

        return vuid;
    }

    /**
     * setNdfIen
     * @param ndfIen ndfIen
     */
    public void setNdfIen(String ndfIen) {

        this.ndfProductIen = ndfIen;
    }

    /**
     * getNdfIen
     * @return ndfIen
     */
    public String getNdfIen() {

        return ndfProductIen;
    }

    /**
     * setInactivationDate
     * @param inactivationDate inactivationDate
     */
    public void setInactivationDate(Date inactivationDate) {

        this.inactivationDate = inactivationDate;
    }

    /**
     * getInactivationDate
     * @return inactivationDate
     */
    public Date getInactivationDate() {

        return inactivationDate;
    }

    /**
     * getVaProductName
     * 
     * @return the vaProductName
     */
    public String getVaProductName() {

        return name;
    }

    /**
     * getIngredientName
     * @return String name
     */
    public String getIngredientName() {

        return name;
    }

    /**
     * getVaDrugClass
     * @return String name
     */
    public String getVaDrugClass() {

        return name;

    }

    /**
     * getGenericName
     * @return String name
     */
    public String getGenericName() {

        return name;

    }

}
