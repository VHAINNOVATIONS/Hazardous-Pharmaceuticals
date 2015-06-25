/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.capability.impl;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.StatusConsoleVo;
import gov.va.med.pharmacy.peps.service.local.capability.ConsoleCapability;
import gov.va.med.pharmacy.peps.service.local.messagingservice.outbound.capability.SendToNationalCapability;
import gov.va.med.pharmacy.peps.service.national.session.ConsoleService;


/**
 * Local implementation of Console actions (System Information) 
 * 
 */
public class ConsoleCapabilityImpl extends gov.va.med.pharmacy.peps.service.common.capability.impl.ConsoleCapabilityImpl
    implements ConsoleCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ConsoleCapabilityImpl.class);

    private SendToNationalCapability sendToNationalCapability;
    private ConsoleService nationalConsoleService;

    /**
     * handles a request from national for an update of System Information
     * 
     * Determines its own information then sends it back to national
     * 
     * @param inputSiteConfigVo SiteConfigVo
     * 
     * @see gov.va.med.pharmacy.peps.service.local.capability.ConsoleCapability
     *   #processFromNational(gov.va.med.pharmacy.peps.common.vo.SiteConfigVo)
     */
    public void processFromNational(SiteConfigVo inputSiteConfigVo) {

        //Retrieves local system information
        SiteConfigVo localStatus = getSiteVersionInfo();
        
        //And sends it to national
        sendToNationalCapability.send(localStatus);
    }

    /**
     * Sets sendToNationalCapability
     * 
     * @param sendToNationalCapability sendToNationalCapability
     */
    public void setSendToNationalCapability(SendToNationalCapability sendToNationalCapability) {
        this.sendToNationalCapability = sendToNationalCapability;
    }
       
    /**
     * Retrieves this site's status console (System Information) 
     * A Local Status Console has a national site config vo and a list with 1 local site config (this site) (always only 1)
     * A National Status Console has a national site config vo and a list with 0..n local site config 
     *  from the national db table 
     * 
     * @return StatusConsoleVo 
     */
    public StatusConsoleVo getConsole() {

        StatusConsoleVo thisConsole = new StatusConsoleVo();

        // get this site (this is LOCAL) and stick it in the list for Local info in StatusConsoleVo 
        SiteConfigVo myLocalSiteConfig = new SiteConfigVo();
        myLocalSiteConfig = getSiteVersionInfo();

        List<SiteConfigVo> myLocalSiteConfigList = new ArrayList<SiteConfigVo>();
        myLocalSiteConfigList.add(myLocalSiteConfig);
        thisConsole.setLocalSiteConfigInfoList(myLocalSiteConfigList); 
        
        // get the National site config info via sync call and place in the National attribute
        SiteConfigVo myNationalSiteConfig = new SiteConfigVo();
        
        try {
            myNationalSiteConfig = nationalConsoleService.getNationalSiteVersionInfo();
        } catch (Exception ex) {
            LOG.error("Synchronous call to National for Console information failed with: " + ex.getMessage());
            
            // id: PRE00001333
            // Headline: TC0015 - PEPS0969  National PRE server is not available.
            // State: Assigned
            // Severity: 2. Major
            // Priority: 2. High
            // Description: When the national site is offline 
            //  please replace the text Error with "National PRE server is not available."
            // How about changing Error to "Vista not available".  "FDB not available", "EPL not available" 
            //  and "Last Version Info not available" while you are at it.
            // 
            // construct an ERROR vo
            myNationalSiteConfig.setSiteCotsDbVersion("FDB not available.");
            myNationalSiteConfig.setSiteName("Site not available");
            myNationalSiteConfig.setSiteNumber("999");
            myNationalSiteConfig.setSitePepsDbVersion("EPL not available.");
            myNationalSiteConfig.setSiteServerName("National PRE server is not available.");
            myNationalSiteConfig.setSiteVersionInfoDate("Last Version Info not available.");
            myNationalSiteConfig.setSiteVistaVersion("Vista not available.");
        }
        
        thisConsole.setNationalSiteConfigInfo(myNationalSiteConfig);
        
        return thisConsole;
    }

    /**
     * setNationalConsoleService
     * @param nationalConsoleService nationalConsoleService property
     */
    public void setNationalConsoleService(ConsoleService nationalConsoleService) {
        this.nationalConsoleService = nationalConsoleService;
    }
  
}
