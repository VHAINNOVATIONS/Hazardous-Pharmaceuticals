/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.capability.impl;


import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.ServiceException;
import gov.va.med.pharmacy.peps.common.vo.DifUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.national.capability.SiteUpdateScheduleCapability;
import gov.va.med.pharmacy.peps.service.national.messagingservice.outbound.capability.SendToLocalCapability;


/**
 * Site Update Schedule Capability contains code common for processing DIF updates at National.
 */
public class SiteUpdateScheduleCapabilityImpl extends
    gov.va.med.pharmacy.peps.service.common.capability.impl.SiteUpdateScheduleCapabilityImpl implements
    SiteUpdateScheduleCapability {

    private static final Logger LOG = Logger.getLogger(SiteUpdateScheduleCapabilityImpl.class);
    private static final String NAT_RUNNING = "National Status of national server running ";
    private SendToLocalCapability sendToLocalCapability;

    private boolean nationalRunning = false;
    private boolean localRunning = false;
    private int numLocalsRunning = 0;

    /**
     * Begin the national update process. This action is triggered from PEPS National
     * 
     * @return boolean
     */
    public boolean performUpdate() {

        if (canPerformUpdate()) {
            LOG.debug("No local sites are running a DIF update, starting national");

            // We absolutely must flip the flag off if we turn it on, hence, the finally
            try {
                setNationalRunning(true);
                executeDifUpdate();
            } finally {
                setNationalRunning(false);

            }

            return true;

        } else {
            LOG.debug("Some local sites are running a DIF update, cancelling national update");

            // At least one local is performing an update so we can not proceed with nationals update
            // Need to reschedule when National will attempt another update

            return false;
        }
    }

    /**
     * Checks if DIF update can be performed
     * 
     * @return boolean
     */
    public boolean canPerformUpdate() {
        return !(isAnyRunning());
    }

    /**
     * Perform all the steps required to execute the DIF update at local and ensure the National is aware of update progress
     */
    private void executeDifUpdate() {

        // First set that national is performing an update
        SiteUpdateScheduleVo natSiteUpdate = retrieveSiteUpdateSchedule(getEnvironmentUtility().getSiteNumber());

        // Obtain the current FDB issue date value to compare with after external update occurs.
        SiteConfigVo natSiteConfig = retrieveFDBVersion();
        String beforeCotsVer = natSiteConfig.getSiteCotsDbVersion();

        // Now execute the DIF update using the UpdateDIF.jar utility
        executeExternalUpdate();

        // After execution of the external update we need to obtain the current version now and compare to "before"
        // If the issue date from before is different than after, we can assume the DIF update was successful
        natSiteConfig = retrieveFDBVersion();
        String afterCotsVer = natSiteConfig.getSiteCotsDbVersion();

        if (difVersionChanged(beforeCotsVer, afterCotsVer)) {

            // The update caused the issue date values to change so the update was successful
            LOG.debug("The issue date have changed so the update was successful");

            // We'll send this later in the finally clause, but update the version now
            natSiteUpdate.setSoftwareUpdateVersion(afterCotsVer);

            // Send update file to locals
            DifUpdateVo update = new DifUpdateVo();
            update.setSiteUpdateScheduleVo(natSiteUpdate);

            File updateFile = getUpdateFile();
            updateNdcImages(updateFile);

            // Finally, send the update file down to the locals
            try {

                natSiteUpdate.setMd5Sum(getMD5Checksum(updateFile));
                byte[] bytes = FileUtils.readFileToByteArray(updateFile);
                update.setUpdateFile(bytes);

                LOG.debug("Sending DIF update file to local)");
                sendToLocalCapability.send(update);
            } catch (Exception e) {
                LOG.error("Error sending DIF update to locals!", e);
            }
        } else {

            // The issue date values weren't altered so the update was not successful
            // Need to find a way to tell the user the update was not successful
            LOG.debug("The issue dates did not change so the update was NOT successful");

        }

    }

    /**
     * Check to determine the the IssueDate field in the DIF database has been altered since the update was attempted
     * 
     * @param before version information from before the update was started
     * @param after version information from after the update was attempted
     * @return true/false
     */
    private boolean difVersionChanged(String before, String after) {
        StringTokenizer beforeParser = new StringTokenizer(before, SiteConfigVo.FIELD_SEPERATOR);
        String beforeIssueDate = beforeParser.nextToken();

        StringTokenizer afterParser = new StringTokenizer(after, SiteConfigVo.FIELD_SEPERATOR);
        String afterIssueDate = afterParser.nextToken();

        if (beforeIssueDate.equals(afterIssueDate)) {

            // Values are the same so the update didn't go through
            return false;
        }

        // Values are different so we can assume the update was successful
        return true;

    }

    /**
     * This method processes DIF schedule update progress passed from local to national.
     * 
     * @param vo SiteUpdateScheduleVo
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo
     */
    public SiteUpdateScheduleVo processFromLocal(SiteUpdateScheduleVo vo, UserVo user) {

        // Need to lookup this site in National
        SiteUpdateScheduleVo natLocSiteUpdate = retrieveSiteUpdateSchedule(vo.getSiteNumber());

        if (natLocSiteUpdate.getId() == null) {

            // This item is new so call create
            return create(vo, user);
        } else {

            // This item is an update to the progress at National
            vo.setId(natLocSiteUpdate.getId());

            return update(vo, user);
        }
    }

    /**
     * setSendToLocalCapability
     * 
     * @param sendToLocalCapability property
     */
    public void setSendToLocalCapability(SendToLocalCapability sendToLocalCapability) {
        this.sendToLocalCapability = sendToLocalCapability;
    }

    /**
     * Indicates if the national server is running or not
     * 
     * @return boolean
     */
    public boolean isNationalRunning() {
        LOG.debug(NAT_RUNNING + nationalRunning);

        return nationalRunning;
    }

    /**
     * Sets if the national server is running
     * 
     * @param nationalRunning boolean
     */
    public void setNationalRunning(boolean nationalRunning) {
        this.nationalRunning = nationalRunning;

        LOG.debug(NAT_RUNNING + this.nationalRunning);
    }

    /**
     * Returns true if national or one or more locals is running
     * 
     * @return boolean
     */
    public boolean isAnyRunning() {

        // First thing to try is to check to see if any local update are in progress
        // List<SiteUpdateScheduleVo> sitesInProgress = this.getSiteUpdateScheduleDomainCapability()
        // .retrieveSiteUpdateScheduleInProgress();

        boolean anyRunning = nationalRunning || localRunning;
        LOG.debug("National Status of any server running " + anyRunning);

        return anyRunning;
    }

    /**
     * Message that a local has begun running a DIF update process
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     */
    public void localRunning(SiteUpdateScheduleVo siteUpdateScheduleVo) {
        LOG.debug("National Informed a Local is running");

        this.localRunning = true;
        numLocalsRunning++;
    }

    /**
     * Message that a local has finished running a DIF update process
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     */
    public void localFinished(SiteUpdateScheduleVo siteUpdateScheduleVo) {
        LOG.debug("National Informed a Local is finished");

        numLocalsRunning--;
        this.localRunning = numLocalsRunning > 0;
    }

    /**
     * Save the given byte array as the update file for the updater to use later.
     * 
     * @param bytes byte array containing update file data
     */
    public void saveUpdateFile(byte[] bytes) {
        try {
            FileUtils.writeByteArrayToFile(getUpdateFile(), bytes);
        } catch (IOException e) {
            throw new ServiceException(e, ServiceException.COTS_UPDATE_FAILURE);
        }
    }
}
