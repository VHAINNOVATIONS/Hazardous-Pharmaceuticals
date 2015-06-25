/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.List;


/**
 * 
 * ReportDrugClassVo
 *
 */
public class ReportDrugClassVo extends ValueObject {
    
    /** FIELD_SEPERATOR */
    public static final String FIELD_SEPERATOR = " | ";

    private static final long serialVersionUID = 1L;
    
    private String vuid;
    private String code;
    private String description;
    private String classification;
    private String drugClassIen;
    private int classType;
    private int parentClassType;
    private Long id;
    private List<ReportDrugClassVo> secondaryDrugClasses;
    private List<ReportDrugClassVo> tertiaryDrugClasses;

    /**
     * setCode
     * 
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * getCode
     * 
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * setVuid
     * 
     * @param vuid the vuid to set
     */
    public void setVuid(String vuid) {
        this.vuid = vuid;
    }

    /**
     * getVuid
     * 
     * @return the vuid
     */
    public String getVuid() {
        return vuid;
    }

    /**
     * setDescription
     * 
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getDescription
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * setDrugClassIen
     * @param drugClassIen the drugClassIen to set
     */
    public void setDrugClassIen(String drugClassIen) {
        this.drugClassIen = drugClassIen;
    }

    /**
     * getDrugClassIen
     * 
     * @return the drugClassIen
     */
    public String getDrugClassIen() {
        return drugClassIen;
    }

    /**
     * setClassification
     * 
     * @param classification the classification to set
     */
    public void setClassification(String classification) {
        this.classification = classification;
    }

    /**
     * getClassification
     * 
     * @return the classification
     */
    public String getClassification() {
        return classification;
    }

    /**
     * setClassType
     * 
     * @param classType the classType to set
     */
    public void setClassType(int classType) {
        this.classType = classType;
    }

    /**
     * getClassType
     * 
     * @return the classType
     */
    public int getClassType() {
        return classType;
    }

    /**
     * setParentClassType
     * 
     * @param parentClassType the parentClassType to set
     */
    public void setParentClassType(int parentClassType) {
        this.parentClassType = parentClassType;
    }

    /**
     * getParentClassType
     * 
     * @return the parentClassType
     */
    public int getParentClassType() {
        return parentClassType;
    }

    /**
     * setId
     * 
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getId
     * 
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * setSecondaryDrugClasses
     * 
     * @param secondaryDrugClasses the secondaryDrugClasses to set
     */
    public void setSecondaryDrugClasses(List<ReportDrugClassVo> secondaryDrugClasses) {
        this.secondaryDrugClasses = secondaryDrugClasses;
    }

    /**
     * getSecondaryDrugClasses
     * 
     * @return the secondaryDrugClasses
     */
    public List<ReportDrugClassVo> getSecondaryDrugClasses() {
        return secondaryDrugClasses;
    }

    /**
     * setTertiaryDrugClasses
     * 
     * @param tertiaryDrugClasses the tertiaryDrugClasses to set
     */
    public void setTertiaryDrugClasses(List<ReportDrugClassVo> tertiaryDrugClasses) {
        this.tertiaryDrugClasses = tertiaryDrugClasses;
    }

    /**
     * getTertiaryDrugClasses
     * 
     * @return the tertiaryDrugClasses
     */
    public List<ReportDrugClassVo> getTertiaryDrugClasses() {
        return tertiaryDrugClasses;
    }
}
