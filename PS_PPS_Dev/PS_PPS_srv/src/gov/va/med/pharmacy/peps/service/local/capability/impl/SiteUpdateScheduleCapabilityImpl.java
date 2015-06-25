/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.capability.impl;


import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

import gov.va.med.pharmacy.peps.common.vo.DifUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationType;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NotificationDomainCapability;
import gov.va.med.pharmacy.peps.service.local.capability.SiteUpdateScheduleCapability;
import gov.va.med.pharmacy.peps.service.local.utility.DifUpdateScheduler;
import gov.va.med.pharmacy.peps.service.national.session.SiteUpdateScheduleService;


/**
 * Site Update Schedule Capability contains code for processing DIF updates at Local.
 */
public class SiteUpdateScheduleCapabilityImpl extends
    gov.va.med.pharmacy.peps.service.common.capability.impl.SiteUpdateScheduleCapabilityImpl implements
    SiteUpdateScheduleCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(SiteUpdateScheduleCapabilityImpl.class);

    private DifUpdateScheduler difUpdateScheduler;

    // private SiteUpdateScheduleService nationalSiteUpdateScheduleService;
    private NotificationDomainCapability notificationDomainCapability;
    private boolean localRunning = false;

    /**
     * Initializes the time at which the update should occur
     * 
     * 
     * @see gov.va.med.pharmacy.peps.service.local.capability.SiteUpdateScheduleCapability#init()
     */
    public void init() {
   
        SiteUpdateScheduleVo locSiteUpdate = retrieveSiteUpdateSchedule(getEnvironmentUtility().getSiteNumber());

        LOG.debug("Initializing DIF update task for local site: " + getEnvironmentUtility().getSiteNumber());

        if (locSiteUpdate.getId() == null) {
            locSiteUpdate = createDefaultSiteUpdateSchedule();
        }

        // A configuration exist so lets set the time that it needs to run next
        difUpdateScheduler.scheduleNewTimerTask(locSiteUpdate.getScheduleStartDtm());
    }

    /**
     * Update the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     * @return SiteUpdateScheduleVo updatedVo
     */
    public SiteUpdateScheduleVo update(SiteUpdateScheduleVo siteUpdateScheduleVo) {

        // Need to first update the next scheduled time and then perform the EPL save but only if the
        // site being updated is this sites
        if (siteUpdateScheduleVo.getSiteNumber().equals(getEnvironmentUtility().getSiteNumber())) {
            difUpdateScheduler.scheduleNewTimerTask(siteUpdateScheduleVo.getScheduleStartDtm());
        }

        return super.update(siteUpdateScheduleVo, UserVo.getSystemUser(getEnvironmentUtility()));
    }

    /**
     * Update the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo updated Vo
     */
    public SiteUpdateScheduleVo update(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user) {

        SiteUpdateScheduleVo siteUpdateSchedule = super.update(siteUpdateScheduleVo, user);

        difUpdateScheduler.scheduleNewTimerTask(siteUpdateSchedule.getScheduleStartDtm());

        return siteUpdateSchedule;
    }

    /**
     * Update the given SiteUpdateScheduleVo but does not update the scheduled tasks details.
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     * @param internal true/false
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo updated Vo
     */
    private SiteUpdateScheduleVo update(SiteUpdateScheduleVo siteUpdateScheduleVo, boolean internal, UserVo user) {
        return super.update(siteUpdateScheduleVo, user);
    }

    /**
     * Begin the execution of the DIF update process at local
     * 
     * @param manual indicates if this is a manual udate or not
     * @return boolean
     */
    public boolean performUpdate(boolean manual) {

        if (isLocalRunning()) {
            return false;
        } else {
            try {
                setLocalRunning(true);

                // start the local update
                return executeDifUpdate(manual);
            } finally {
                setLocalRunning(false);

            }
        }

    }

    /**
     * Perform all the steps required to execute the DIF update at local and ensure the National is aware of update progress
     * 
     * @param manual A boolean if this was manuall run or not
     * @return boolean true if sucessful, false otherwise
     */
    private boolean executeDifUpdate(boolean manual) {
        UserVo systemUser = UserVo.getSystemUser(getEnvironmentUtility());

        // First set that the current local is performing and update
        SiteUpdateScheduleVo locSiteUpdate = retrieveSiteUpdateSchedule(getEnvironmentUtility().getSiteNumber());

        boolean ret = false;

        // Check to see if MD5 Value is present for local configuration. If its not then no update needs to be attempted
        if (locSiteUpdate.getMd5Sum() != null && locSiteUpdate.getMd5Sum().length() > 0) {

            // Obtain the current FDB issue date value to compare with after external update occurs.
            SiteConfigVo locSiteConfig = retrieveFDBVersion();
            String beforeCotsVer = locSiteConfig.getSiteCotsDbVersion();

            File updateFile = getUpdateFile();

            // Now execute the DIF update using the UpdateDIF.jar utility
            executeExternalUpdate();
            updateNdcImages(updateFile);

            // After execution of the external update we need to obtain the current version now and compare to "before"
            locSiteConfig = retrieveFDBVersion();
            String afterCotsVer = locSiteConfig.getSiteCotsDbVersion();

            if (difVersionChanged(beforeCotsVer, afterCotsVer)) {

                // The update caused the issue date values to change so the update was successful
                LOG.debug("The issue date have changed so the update was successful");
                NotificationVo notification = new NotificationVo();
                notification.setNotificationType(NotificationType.COTS_UPDATE);

                notificationDomainCapability.create(notification, systemUser);
                ret = true;
            } else {
                LOG.debug("The issue dates did not change so the update was NOT successful");

            }

            if (!manual) {

                // Schedule the next time and update will be attempted
                Calendar nxtSchedCal = new GregorianCalendar();
                nxtSchedCal.setTime(locSiteUpdate.getScheduleStartDtm());
                nxtSchedCal.add(Calendar.DATE, Integer.parseInt(locSiteUpdate.getScheduleInterval()));
                SiteUpdateScheduleVo newLocSiteUpdate = new SiteUpdateScheduleVo();
                newLocSiteUpdate.setSiteNumber(locSiteUpdate.getSiteNumber());
                newLocSiteUpdate.setScheduleInterval(locSiteUpdate.getScheduleInterval());
                newLocSiteUpdate.setScheduleStartDtm(nxtSchedCal.getTime());
                newLocSiteUpdate.setSoftwareUpdateType(SiteUpdateScheduleVo.COTS);
                newLocSiteUpdate.setSoftwareUpdateVersion("TBD");
                newLocSiteUpdate = create(newLocSiteUpdate, systemUser);

                // Update the scheduled task to run tomorrow at the same time
                difUpdateScheduler.scheduleNewTimerTask(newLocSiteUpdate.getScheduleStartDtm());
            }

        } else if (!manual) {
            LOG.debug("No file available for update, rescheduling update for tomorrow");

            // Schedule the next time and update will be attempted
            Calendar nxtSchedCal = new GregorianCalendar();
            nxtSchedCal.setTime(locSiteUpdate.getScheduleStartDtm());
            nxtSchedCal.add(Calendar.DATE, Integer.parseInt(locSiteUpdate.getScheduleInterval()));
            locSiteUpdate.setScheduleStartDtm(nxtSchedCal.getTime());
            locSiteUpdate = update(locSiteUpdate);
        }

        return ret;
    }

    /**
     * Check to determine the the IssueDate field in the DIF database has been altered since the update was attempted
     * returns true if the the version has been updated.
     * 
     * @param before version information from before the update was started
     * @param after version information from after the update was attempted
     * @return true/false
     */
    private boolean difVersionChanged(String before, String after) {
        
        // Checks to see if the version has changes by comparin the data strings.
        StringTokenizer beforeParser = new StringTokenizer(before, SiteConfigVo.FIELD_SEPERATOR);
        String beforeIssueDate = beforeParser.nextToken();

        StringTokenizer afterParser = new StringTokenizer(after, SiteConfigVo.FIELD_SEPERATOR);
        String afterIssueDate = afterParser.nextToken();

        return !beforeIssueDate.equals(afterIssueDate);
    }

    /**
     * This method processes DIF schedule update progress passed from national to local.
     * 
     * @param vo SiteUpdateScheduleVo
     */
    public void processFromNational(SiteUpdateScheduleVo vo) {
        UserVo systemUser = UserVo.getSystemUser(getEnvironmentUtility());

        // Need to lookup National entry here at local to determine if National is in progress
        SiteUpdateScheduleVo locNatSiteUpdate = retrieveSiteUpdateSchedule(vo.getSiteNumber());

        if ("999".equals(vo.getSiteNumber())) {
            if (vo.getInProgress().equals("N")) {
                LOG.debug("Received a message indicating that the National DIF update Process has completed.");
            } else {
                LOG.debug("Received a message indicating that the National DIF update Process has started.");
            }
        }

        if (locNatSiteUpdate.getId() == null) {

            // This item is new so call create
            create(vo, systemUser);
        } else {

            // This item is an update to the progress at National
            vo.setId(locNatSiteUpdate.getId());

            update(vo, true, systemUser);
        }
    }

    /**
     * This method allows for the DIF update file to be send from national to local
     * 
     * @param vo DifUpdateVo
     * 
     */
    public void processFromNational(DifUpdateVo vo) {
        try {
            byte[] updateFileBytes = vo.getUpdateFile();
            File updateFile = getUpdateFile();

            FileUtils.writeByteArrayToFile(updateFile, updateFileBytes);

            // Validate file using MD5
            if (vo.getSiteUpdateScheduleVo().getMd5Sum().equals(getMD5Checksum(updateFile))) {

                // The checksum matches so we are good to go
                LOG.debug("Update DIF file received is the same as the one that was sent from national.");

                // Now update local schedule to set MD5 and version fields
                SiteUpdateScheduleVo locSiteUpdate = this
                    .retrieveSiteUpdateSchedule(getEnvironmentUtility().getSiteNumber());
                locSiteUpdate.setMd5Sum(vo.getUpdateFileName() + "|" + vo.getSiteUpdateScheduleVo().getMd5Sum());
                locSiteUpdate.setSoftwareUpdateVersion(vo.getSiteUpdateScheduleVo().getSoftwareUpdateVersion());
                update(locSiteUpdate, true, UserVo.getSystemUser(getEnvironmentUtility()));
            } else {
                LOG.debug("Update DIF file recieved is NOT the same as the one that was sent from national");
            }
        } catch (Exception e) {
            LOG.error("Could not process DIF update from national", e);
        }
    }

    /**
     * setDifUpdateScheduler
     * 
     * @param difUpdateScheduler property
     */
    public void setDifUpdateScheduler(DifUpdateScheduler difUpdateScheduler) {
        this.difUpdateScheduler = difUpdateScheduler;
    }

    /**
     * setNationalSiteUpdateScheduleService
     * @param nationalSiteUpdateScheduleService property
     */
    public void setNationalSiteUpdateScheduleService(SiteUpdateScheduleService nationalSiteUpdateScheduleService) {
       
        //this.nationalSiteUpdateScheduleService = nationalSiteUpdateScheduleService;
    }

    /**
     * NotificationDomainCapability
     * @return notificationDomainCapability property
     */
    public NotificationDomainCapability getNotificationDomainCapability() {
        return notificationDomainCapability;
    }

    /**
     * setNotificationDomainCapability
     * @param notificationDomainCapability notificationDomainCapability property
     */
    public void setNotificationDomainCapability(NotificationDomainCapability notificationDomainCapability) {
        this.notificationDomainCapability = notificationDomainCapability;
    }

    /**
     * isLocalRunning
     * @return localRunning property
     */
    public boolean isLocalRunning() {
        return localRunning;
    }

    /**
     * setLocalRunning
     * @param localRunning localRunning property
     */
    public void setLocalRunning(boolean localRunning) {
        this.localRunning = localRunning;
    }
}
