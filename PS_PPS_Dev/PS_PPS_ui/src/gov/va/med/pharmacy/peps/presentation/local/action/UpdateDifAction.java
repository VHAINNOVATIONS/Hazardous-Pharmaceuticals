/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.local.action;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.presentation.common.action.PepsAction;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.ConversationScope;
import gov.va.med.pharmacy.peps.service.local.session.SiteUpdateScheduleService;


/**
 * Action to display the manage DIF Updates page information.
 */
public class UpdateDifAction extends PepsAction {
    private static final long serialVersionUID = 1L;

    private SiteUpdateScheduleService siteUpdateScheduleService;
    
    @ConversationScope
    private SiteUpdateScheduleVo currSiteUpdateSched;
    
    @ConversationScope
    private boolean isNew = false;

    @ConversationScope
    private String scheduledInterval;

    @ConversationScope
    private Date scheduledStartDtm;
    
    @ConversationScope
    private boolean canUpdate;

    /**
     * Retrieves the current site update schedule configuration
     * 
     * @return SUCCESS
     */
    public String retrieveSiteUpdateSchedule() {
        currSiteUpdateSched =
            siteUpdateScheduleService.retrieveSiteUpdateSchedule(getEnvironmentUtility().getSiteNumber());
        
        if (currSiteUpdateSched.getId() == null) {
            isNew = true;
            this.setScheduledInterval("1");
            this.setScheduledStartDtm(new Date());
        } else {
            isNew = false;
            this.setScheduledInterval(currSiteUpdateSched.getScheduleInterval());
            this.setScheduledStartDtm(currSiteUpdateSched.getScheduleStartDtm());
            this.canUpdate = (currSiteUpdateSched.getMd5Sum() != null && currSiteUpdateSched.getMd5Sum().length() > 0);
        }
        
        return SUCCESS;
    }

    /**
     * Called to determine if we are creating a new entry or updating an existing one
     * 
     * @return SUCCESS
     */
    public String saveSiteUpdateSchedule() {
        
        // Check to see if we are saving a new item or just updating an existing one
        if (isNew) {
            return create();
        }
        else {
            return update();
        }
        
    }
    
    /**
     * Creates a new site update schedule configuration for this site
     * 
     * @return SUCCESS
     */
    public String create() {
        currSiteUpdateSched.setScheduleStartDtm(this.getScheduledStartDtm());
        currSiteUpdateSched.setScheduleInterval(this.getScheduledInterval());
        currSiteUpdateSched.setSiteNumber(getEnvironmentUtility().getSiteNumber());
        currSiteUpdateSched.setSoftwareUpdateType(SiteUpdateScheduleVo.COTS);
        currSiteUpdateSched.setSoftwareUpdateVersion("TBD");
        currSiteUpdateSched = siteUpdateScheduleService.create(currSiteUpdateSched, getUser());
        
        return SUCCESS;
    }

    /**
     * Updates an existing site update schedule configuration for this site
     * 
     * @return SUCCESS
     */
    public String update() {
        currSiteUpdateSched.setScheduleStartDtm(this.getScheduledStartDtm());
        currSiteUpdateSched.setScheduleInterval(this.getScheduledInterval());
        currSiteUpdateSched = siteUpdateScheduleService.update(currSiteUpdateSched, getUser());
        
        return SUCCESS;
    }
    
    /**
     * Performs the update at national
     * 
     * @return SUCCESS
     */
    public String performDifUpdate() {
        if (siteUpdateScheduleService.performUpdate(true)) {
            return SUCCESS;
        }
        else {
            return ERROR;
        }
    }
    

    /**
     * Retrieves the interval
     * 
     * @return String
     */
    public String getScheduledInterval() {
        return scheduledInterval;
    }

    /**
     * Sets the interval
     * 
     * @param scheduledInterval interval
     */
    public void setScheduledInterval(String scheduledInterval) {
        this.scheduledInterval = scheduledInterval;
    }

    /**
     * Retrieves the date/time
     * 
     * @return Date
     */
    public Date getScheduledStartDtm() {
        return scheduledStartDtm;
    }

    /**
     * Sets the date/time
     * 
     * @param scheduledStartDtm date/time
     */
    public void setScheduledStartDtm(Date scheduledStartDtm) {
        this.scheduledStartDtm = scheduledStartDtm;
    }

    /**
     * 
     * @return managedItemService property
     */
    public SiteUpdateScheduleService getSiteUpdateScheduleService() {
        return siteUpdateScheduleService;
    }

    /**
     * 
     * @param siteUpdateScheduleService siteUpdateScheduleService property
     */
    public void setSiteUpdateScheduleService(SiteUpdateScheduleService siteUpdateScheduleService) {
        this.siteUpdateScheduleService = siteUpdateScheduleService;
    }

    /**
     * 
     * @return canUpdate property
     */
    public boolean isCanUpdate() {
        return canUpdate;
    }

    /**
     * 
     * @param canUpdate canUpdate property
     */
    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

}
