/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.session.impl;


import gov.va.med.pharmacy.peps.common.vo.StatusConsoleVo;
import gov.va.med.pharmacy.peps.service.local.capability.ConsoleCapability;
import gov.va.med.pharmacy.peps.service.local.session.ConsoleService;


/**
 * (System Information) 
 */
public class ConsoleServiceImpl implements ConsoleService {

    private ConsoleCapability consoleCapability;
   
    /**
     * A Local Status Console (System Information) has a national site config vo 
     *  and a list with 1 local site config (this site) (always only 1)
     * A National Status Console (System Information) has a national site config vo 
     *  and a list with 0..n local site config from the national db table 
     * 
     * @return StatusConsoleVo
     */
    public StatusConsoleVo getConsole() {     
        return consoleCapability.getConsole();
    }

    /**
     * setConsoleCapability
     * @param consoleCapability capability
     */
    public void setConsoleCapability(ConsoleCapability consoleCapability) {
        this.consoleCapability = consoleCapability;
    }
   
}
