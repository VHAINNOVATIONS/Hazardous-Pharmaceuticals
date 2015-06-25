/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.capability;


import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.StatusConsoleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * National implementation of Console Capabilities (System Information)
 */
public interface ConsoleCapability extends gov.va.med.pharmacy.peps.service.common.capability.ConsoleCapability {

    /**
     * Inserts the given local's SiteConfigVo into the national database (System Information)
     * 
     * @param inputSiteConfigVo data to add
     * @param user {@link UserVo} performing the action
     * @throws ValueObjectValidationException  exception
     * @throws DuplicateItemException  exception
     */
    void insertFromLocal(SiteConfigVo inputSiteConfigVo, UserVo user) throws ValueObjectValidationException,
        DuplicateItemException;

    /**
     * Retrieves this site's status console (System Information) A Local Status Console has a national site config vo and a
     * list with 1 local site config (this site) (always only 1) A National Status Console has a national site config vo and
     * a list with 0..n local site config from the national db table
     * 
     * @return StatusConsoleVo
     */
    StatusConsoleVo getConsole();

    /**
     * Retrieves the national site's version info for the local's (System Information)
     * 
     * @return SiteConfigVo
     */
    SiteConfigVo getNationalSiteVersionInfo();

    /**
     * Sends a request for the local's version information (System Information) for ConsoleCapability.
     */
    void requestLocalSystemInformation();

    /**
     * Deletes all local version records (System Information) from the national table Then sends a request for the local's
     * version information (System Information)
     */
    void refreshAllSystemInformation();

}
