/**
 * Source file created in 2008 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.service.local.capability;


import gov.va.med.pharmacy.peps.common.vo.DifUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;


/**
 * SiteUpdateScheduleCapability
 */
public interface SiteUpdateScheduleCapability extends
    gov.va.med.pharmacy.peps.service.common.capability.SiteUpdateScheduleCapability {

    /**
     * This method allows for local scheduling of DIF update to be set
     */
    void init();
    
    /**
     * This method processes DIF schedule update progress passed from national to local.
     * 
     * @param vo SiteUpdateScheduleVo
     */
    void processFromNational(SiteUpdateScheduleVo vo);
        
    /**
     * This method allows for the DIF update file to be send from national to local
     * 
     * @param vo DifUpdateVo
     */
    void processFromNational(DifUpdateVo vo);
    
    /**
     * Begins the process of the DIF update
     * 
     * @param manual indicates if this is a manual udate or not
     * @return boolean
     */
    boolean performUpdate(boolean manual);
}
