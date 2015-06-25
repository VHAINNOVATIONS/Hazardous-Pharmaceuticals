/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.domain.common.capability.SiteConfigDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplSiteConfigDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.SiteConfigConverter;


/**
 * Perform CRUD operations on SiteConfigVo
 */
public class SiteConfigDomainCapabilityImpl implements SiteConfigDomainCapability {
    private EplSiteConfigDao eplSiteConfigDao;
    private EnvironmentUtility environmentUtility;
    private SiteConfigConverter siteConfigConverter;

    /**
     * Retrieve the current site configuration .
     * 
     * @return {@link SiteConfigVo}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.SiteConfigDomainCapability#retrieve(java.lang.String)
     */
    public SiteConfigVo retrieve() {
        return retrieve(environmentUtility.getSiteNumber());
    }

    /**
     * Retrieve the site configuration with the given site ID.
     * 
     * @param id String site ID
     * @return {@link SiteConfigVo}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.SiteConfigDomainCapability#retrieve(java.lang.String)
     */
    public SiteConfigVo retrieve(String id) {
        return siteConfigConverter.convert(eplSiteConfigDao.retrieve(Integer.valueOf(id)));
    }

    /**
     * Retrieve all site configuration information available.
     * 
     * @return Collection<SiteConfigVo>
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.SiteConfigDomainCapability#retrieve()
     */
    public List<SiteConfigVo> retrieveAll() {
        return siteConfigConverter.convert(eplSiteConfigDao.retrieve());
    }

    /**
     * Description
     * 
     * @param eplSiteConfigDao eplSiteConfigDao property
     */
    public void setEplSiteConfigDao(EplSiteConfigDao eplSiteConfigDao) {
        this.eplSiteConfigDao = eplSiteConfigDao;
    }

    /**
     * Description
     * 
     * @param environmentUtility environmentUtility property
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

    /**
     * Description
     * 
     * @param siteConfigConverter siteConfigConverter property
     */
    public void setSiteConfigConverter(SiteConfigConverter siteConfigConverter) {
        this.siteConfigConverter = siteConfigConverter;
    }
}
