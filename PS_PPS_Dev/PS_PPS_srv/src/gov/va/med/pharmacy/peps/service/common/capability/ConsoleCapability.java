/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;


import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;


/**
 * Performs common actions for the Status Console (System Information)
 */
public interface ConsoleCapability {

    /**
     * Retrieves this site's version info, local or national Includes PEPS, FDB/COTS, Vista version info, etc
     * 
     * @return SiteConfigVo
     */
    SiteConfigVo getSiteVersionInfo();
}
