/**
 * Source file created in 2007 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.local.capability;


import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.StatusConsoleVo;


/**
 * Performs Console (System Information) actions for the local level
 */
public interface ConsoleCapability extends gov.va.med.pharmacy.peps.service.common.capability.ConsoleCapability {
    
    /**
     * handles a request from national for an update of System Information
     * 
     * @param inputSiteConfigVo SiteConfigVo
     */
    void processFromNational(SiteConfigVo inputSiteConfigVo);
    
    /**
     * Retrieves this site's status console (System Information) 
     * A Local Status Console has a national site config vo and a list with 1 local site config (this site) (always only 1)
     * A National Status Console has a national site config vo and a list with 0..n local site config 
     *  from the national db table 
     * 
     * @return StatusConsoleVo
     */
    StatusConsoleVo getConsole();

}
