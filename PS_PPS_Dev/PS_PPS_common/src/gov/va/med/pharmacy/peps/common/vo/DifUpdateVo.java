/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;




/**
 * Object representing what is needed for a DIF update
 */
public class DifUpdateVo extends ValueObject {
    private static final long serialVersionUID = 1L;
    
    private SiteUpdateScheduleVo siteUpdateScheduleVo;
    private byte[] updateFile;
    private String updateFileName;
    
    /**
     * getSiteUpdateScheduleVo.
     * @return siteUpdateScheduleVo property
     */
    public SiteUpdateScheduleVo getSiteUpdateScheduleVo() {
        return siteUpdateScheduleVo;
    }
    
    /**
     * setSiteUpdateScheduleVo.
     * @param siteUpdateScheduleVo siteUpdateScheduleVo property
     */
    public void setSiteUpdateScheduleVo(SiteUpdateScheduleVo siteUpdateScheduleVo) {
        this.siteUpdateScheduleVo = siteUpdateScheduleVo;
    }
    
    /**
     * getUpdateFile.
     * @return updateFile property
     */
    public byte[] getUpdateFile() {
        return updateFile;
    }
    
    /**
     * setUpdateFile.
     * @param updateFile updateFile property
     */
    public void setUpdateFile(byte[] updateFile) {
        this.updateFile = updateFile;
    }

    /**
     * getUpdateFileName.
     * @return updateFileName property
     */
    public String getUpdateFileName() {
        return updateFileName;
    }

    /**
     * setUpdateFileName.
     * @param updateFileName updateFileName property
     */
    public void setUpdateFileName(String updateFileName) {
        this.updateFileName = trimToEmpty(updateFileName);
    } 
    
    
}
