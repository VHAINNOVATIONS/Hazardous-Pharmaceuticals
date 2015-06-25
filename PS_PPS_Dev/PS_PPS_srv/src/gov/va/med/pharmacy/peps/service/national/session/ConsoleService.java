/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.session;


import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.StatusConsoleVo;


/**
 * Console service
 */
public interface ConsoleService {

    /**
     * (System Information) A Local Status Console (System Information) has a national site config vo and a list with 1 local
     * site config (this site) (always only 1) A National Status Console (System Information) has a national site config vo
     * and a list with 0..n local site config from the national db table
     * 
     * @return StatusConsoleVo
     */
    StatusConsoleVo getConsole(); // this is the national version

    /**
     * (System Information) Retrieves the national site's version info for the local's
     * 
     * @return SiteConfigVo
     */
    SiteConfigVo getNationalSiteVersionInfo();

    /**
     * Sends a request for the local's version information (System Information)
     */
    void requestLocalSystemInformation();

    /**
     * Deletes all local version records (System Information) from the national table Then sends a request for the local's
     * version information (System Information)
     */
    void refreshAllSystemInformation();
}
