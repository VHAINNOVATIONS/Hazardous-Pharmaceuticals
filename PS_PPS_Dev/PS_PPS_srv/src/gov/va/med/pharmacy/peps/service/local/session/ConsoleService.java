/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.session;


import gov.va.med.pharmacy.peps.common.vo.StatusConsoleVo;


/**
 * ConsoleService
 */
public interface ConsoleService {

    /**
     * A Local Status Console (System Information) has a national site config vo and a list with 1 local site config (this
     * site) (always only 1) A National Status Console (System Information) has a national site config vo and a list with
     * 0..n local site config from the national db table
     * 
     * @return StatusConsoleVo
     */
    StatusConsoleVo getConsole(); // this is the local version
}
