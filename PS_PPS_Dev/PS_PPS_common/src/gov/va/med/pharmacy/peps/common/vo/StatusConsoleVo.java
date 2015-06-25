/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.List;

 
/**
 * Data representing the complete Status Console 
 * as appropriate for this site (Local or National)
 * A Local Status Console (System Information) has a national site config vo 
 *  and a list with 1 local site config (this site) (always only 1)
 * A National Status Console (System Information) has a national site config vo 
 *  and a list with 0..n local site config from the national db table 
 */
public class StatusConsoleVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private SiteConfigVo nationalSiteConfigInfo;
    private List<SiteConfigVo> localSiteConfigInfoList;

    /**
     * Description
     * 
     * @return nationalSiteConfigInfo property
     */
    public SiteConfigVo getNationalSiteConfigInfo() {
        return nationalSiteConfigInfo;
    }

    /**
     * Description
     * 
     * @param nationalSiteConfigInfo nationalSiteConfigInfo property
     */
    public void setNationalSiteConfigInfo(SiteConfigVo nationalSiteConfigInfo) {
        this.nationalSiteConfigInfo = nationalSiteConfigInfo;
    }

    /**
     * Description
     * 
     * @return localSiteConfigInfoList property
     */
    public List<SiteConfigVo> getLocalSiteConfigInfoList() {
        return localSiteConfigInfoList;
    }

    /**
     * Description
     * 
     * @param inputLocalSiteConfigInfoList localSiteConfigInfoList property
     */
    public void setLocalSiteConfigInfoList(List<SiteConfigVo> inputLocalSiteConfigInfoList) {
        this.localSiteConfigInfoList = new ArrayList<SiteConfigVo>(); 
            
        if (inputLocalSiteConfigInfoList != null && !inputLocalSiteConfigInfoList.isEmpty()) {
            this.localSiteConfigInfoList.addAll(inputLocalSiteConfigInfoList);
        }         
    }

}
