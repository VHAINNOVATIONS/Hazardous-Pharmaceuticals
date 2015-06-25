/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;


/**
 * Perform CRUD operations on SiteConfigVo
 */
public interface SiteConfigDomainCapability {

    /**
     * Retrieve the current site configuration information.
     * 
     * @return SiteConfigVo
     */
    SiteConfigVo retrieve();
    
    /**
     * Retrieve the site configuration with the given site ID.
     * 
     * @param id String site ID
     * @return {@link SiteConfigVo}
     */
    SiteConfigVo retrieve(String id);
    
    /**
     * Retrieve all site configuration information available.
     * 
     * @return Collection<SiteConfigVo>
     */
    List<SiteConfigVo> retrieveAll();
}
