/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.session.impl;


import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.StatusConsoleVo;
import gov.va.med.pharmacy.peps.service.national.capability.ConsoleCapability;
import gov.va.med.pharmacy.peps.service.national.session.ConsoleService;


/**
 * ConsoleServiceImpl
 */
public class ConsoleServiceImpl implements ConsoleService {

    private ConsoleCapability consoleCapability;


    /**
     * (System Information) A Local Status Console (System Information) has a national site config vo and a list with 1 local
     * site config (this site) (always only 1) A National Status Console (System Information) has a national site config vo
     * and a list with 0..n local site config from the national db table
     * 
     * @return StatusConsoleVo
     */
    @Override
    public StatusConsoleVo getConsole() {

        return consoleCapability.getConsole();
    }

    /**
     * (System Information) Retrieves the national site's version info for the local's
     * 
     * @return SiteConfigVo
     */
    @Override
    public SiteConfigVo getNationalSiteVersionInfo() {
        return consoleCapability.getNationalSiteVersionInfo();
    }

    /**
     * setConsoleCapability
     * @param consoleCapability capability
     */
    public void setConsoleCapability(ConsoleCapability consoleCapability) {
        this.consoleCapability = consoleCapability;
    }

    /**
     * Sends a request for the local's version information (System Information)
     */
    @Override
    public void requestLocalSystemInformation() {
        consoleCapability.requestLocalSystemInformation();
    }

    /**
     * Deletes all local version records (System Information) from the national table Then sends a request for the local's
     * version information (System Information)
     */
    @Override
    public void refreshAllSystemInformation() {
        consoleCapability.refreshAllSystemInformation();
    }

}
