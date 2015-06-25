/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.national.action;


import java.util.ArrayList;

import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.StatusConsoleVo;
import gov.va.med.pharmacy.peps.service.national.session.ConsoleService;


/**
 * National implementation of the Console Action (System Information)
 */
public class ConsoleAction extends gov.va.med.pharmacy.peps.presentation.common.action.ConsoleAction {

    private static final long serialVersionUID = 1L;

    private ConsoleService consoleService;

    /**
     * Retrieves the console data (System Information) for all sites
     * 
     * @return SUCCESS
     */
    public String retrieveConsole() {

        StatusConsoleVo statusConsole = getConsoleService().getConsole();

        // pull out the National site config vo and make it a list for the national display table
        ArrayList<SiteConfigVo> tmpNationalSiteConfigList = new ArrayList<SiteConfigVo>();
        tmpNationalSiteConfigList.add(statusConsole.getNationalSiteConfigInfo());
        setNationalStatus(tmpNationalSiteConfigList);

        // pull out the list of local site config info for the local display table
        setLocalStatus(statusConsole.getLocalSiteConfigInfoList());

        return SUCCESS;
    }

    /**
     * Sends a request for the local's version information (System Information)
     * 
     * @return SUCCESS
     */
    public String requestLocalSystemInformation() {
        consoleService.requestLocalSystemInformation();

        return SUCCESS;
    }

    /**
     * Deletes all local version records (System Information) from the national table Then sends a request for the local's
     * version information (System Information)
     * 
     * @return SUCCESS
     */
    public String refreshAllSystemInformation() {
        consoleService.refreshAllSystemInformation();

        return SUCCESS;
    }

    /**
     * 
     * @return consoleService property
     */
    public ConsoleService getConsoleService() {
        return consoleService;
    }

    /**
     * 
     * @param consoleService consoleService property
     */
    public void setConsoleService(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }
}