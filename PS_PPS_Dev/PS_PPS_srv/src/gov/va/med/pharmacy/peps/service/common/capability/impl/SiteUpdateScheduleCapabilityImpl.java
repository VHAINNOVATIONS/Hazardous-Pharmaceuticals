/**
 * Source file created in 2008 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import gov.va.med.pharmacy.peps.common.exception.ServiceException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;
import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.SiteUpdateScheduleDomainCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.NdcImageCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ConsoleCapability;
import gov.va.med.pharmacy.peps.service.common.capability.SiteUpdateScheduleCapability;
import gov.va.med.pharmacy.peps.service.common.utility.ExternalScriptRunner;


/**
 * Site Update Schedule Capability contains code common for processing DIF updates at Local and National.
 */
public class SiteUpdateScheduleCapabilityImpl implements SiteUpdateScheduleCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(SiteUpdateScheduleCapabilityImpl.class);

    private static final String LINUX_FDB_UPDATE_FILE_PROPERTY = "linux.fdb.dif.update.file.path";
    private static final String WINDOWS_FDB_UPDATE_FILE_PROPERTY = "windows.fdb.dif.update.file.path";
    private static final String SCHEDULE_INTERVAL = "1";
    private static final String UPDATE_VERSION = "TBD";

    private SiteUpdateScheduleDomainCapability siteUpdateScheduleDomainCapability;
    private ConsoleCapability consoleCapability;
    private EnvironmentUtility environmentUtility;
    private NdcImageCapability ndcImageCapability;

    /**
     * Insert the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo inserted Vo with new ID
     */
    public SiteUpdateScheduleVo create(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user) {
        return siteUpdateScheduleDomainCapability.create(siteUpdateScheduleVo, user);
    }

    /**
     * Update the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo updated Vo
     */
    public SiteUpdateScheduleVo update(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user) {
        return siteUpdateScheduleDomainCapability.update(siteUpdateScheduleVo, user);
    }

    /**
     * Returns the current scheduled time and interval at which time updates are to occur
     * 
     * @param siteNumber identifier of the site
     * @return SiteUpdateScheduleVo
     */
    public SiteUpdateScheduleVo retrieveSiteUpdateSchedule(String siteNumber) {
        List<SiteUpdateScheduleVo> siteUpdateScheds = siteUpdateScheduleDomainCapability
            .retrieveSiteUpdateSchedule(siteNumber);

        if (siteUpdateScheds != null && !siteUpdateScheds.isEmpty()) {
            return siteUpdateScheds.get(0);
        } else {
            return createDefaultSiteUpdateSchedule();
        }
    }

    /**
     * Creates the default site update schedule, assuming no other is set in the code
     * 
     * @return Default site update schedule
     */
    protected SiteUpdateScheduleVo createDefaultSiteUpdateSchedule() {

        SiteUpdateScheduleVo locSiteUpdate = new SiteUpdateScheduleVo();

        UserVo systemUser = UserVo.getSystemUser(getEnvironmentUtility());

        // A configuration does not exist so lets use a default time of 3 AM (PEPS 1083)
        Calendar cal = new GregorianCalendar();
        Date currentDate = new Date();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, PPSConstants.I3);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);

        if (currentDate.before(cal.getTime())) {

            // Its before 3 AM today so we will schedule it for 3 AM today
            locSiteUpdate.setSiteNumber(getEnvironmentUtility().getSiteNumber());
            locSiteUpdate.setScheduleInterval(SCHEDULE_INTERVAL);
            locSiteUpdate.setScheduleStartDtm(cal.getTime());
            locSiteUpdate.setSoftwareUpdateType(SiteUpdateScheduleVo.COTS);
            locSiteUpdate.setSoftwareUpdateVersion(UPDATE_VERSION);
            locSiteUpdate = create(locSiteUpdate, systemUser);

        } else {

            // Its after 3 AM today so we will schedule it for 3 AM tomorrow
            cal.add(Calendar.DATE, 1);
            locSiteUpdate.setSiteNumber(getEnvironmentUtility().getSiteNumber());
            locSiteUpdate.setScheduleInterval(SCHEDULE_INTERVAL);
            locSiteUpdate.setScheduleStartDtm(cal.getTime());
            locSiteUpdate.setSoftwareUpdateType(SiteUpdateScheduleVo.COTS);
            locSiteUpdate.setSoftwareUpdateVersion(UPDATE_VERSION);
            locSiteUpdate = create(locSiteUpdate, systemUser);

        }

        return locSiteUpdate;
    }

    /**
     * Returns the current version of the FDB DB as part of the entire sites configuration information
     * 
     * @return SiteConfigVo
     */
    public SiteConfigVo retrieveFDBVersion() {
        SiteConfigVo siteConfig = consoleCapability.getSiteVersionInfo();

        return siteConfig;
    }

    /**
     * Retrieve all SiteUpdateScheduleVo for a given site number.
     * 
     * @param siteNumber siteNumber
     * @param softwareUpdateType software update type
     * @return SiteUpdateScheduleVo
     */
    public SiteUpdateScheduleVo retrieveNextScheduleStart(String siteNumber, String softwareUpdateType) {
        SiteUpdateScheduleVo siteUpdateScheduleVo = siteUpdateScheduleDomainCapability.retrieveNextScheduleStart(siteNumber,
            softwareUpdateType);

        if (siteUpdateScheduleVo == null) {
            siteUpdateScheduleVo = new SiteUpdateScheduleVo();
        }

        return siteUpdateScheduleVo;
    }

    /**
     * Generates HEX stream from generated MD5 hash
     * 
     * @param file fully qualified path to the file
     * @return String
     */
    protected String getMD5Checksum(File file) {
        String checksum = "";

        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            checksum = DigestUtils.md5Hex(bytes);
        } catch (Exception e) {
            LOG.error("Error creating checksum", e);
        }

        return checksum;
    }

    /**
     * Determine if we're running in Windows or Linux and return the appropriate File path for the DIF update file.
     * 
     * @return DIF update File
     */
    protected File getUpdateFile() {
        String filePath;

        if (environmentUtility.isWindows()) {
            filePath = PropertyUtility.getProperty(SiteUpdateScheduleCapabilityImpl.class, WINDOWS_FDB_UPDATE_FILE_PROPERTY);
        } else {
            filePath = PropertyUtility.getProperty(SiteUpdateScheduleCapabilityImpl.class, LINUX_FDB_UPDATE_FILE_PROPERTY);
        }

        return new File(filePath);
    }

    /**
     * Update the images stored in the FDB DIF NDC images WAR file.
     * 
     * @param file FDB DIF ZIP update file
     */
    protected void updateNdcImages(File file) {
        ndcImageCapability.updateNdcImages(file);
    }

    /**
     * Call to external scripts to execute the DIF update
     */
    protected void executeExternalUpdate() {
        ExternalScriptRunner runner = null;

        try {
            runner = new ExternalScriptRunner(getEnvironmentUtility().isWindows());
        } catch (IOException e) {
            throw new ServiceException(ServiceException.COTS_UPDATE_FAILURE, e);
        }

        if (!runner.runFdbUpdateScript()) {
            throw new ServiceException(ServiceException.COTS_UPDATE_FAILURE);
        }
    }

    /**
     * description
     * 
     * @param siteUpdateScheduleDomainCapability property
     */
    public void setSiteUpdateScheduleDomainCapability(SiteUpdateScheduleDomainCapability siteUpdateScheduleDomainCapability) {
        this.siteUpdateScheduleDomainCapability = siteUpdateScheduleDomainCapability;
    }

    /**
     * description
     * @param consoleCapability property
     */
    public void setConsoleCapability(ConsoleCapability consoleCapability) {
        this.consoleCapability = consoleCapability;
    }

    /**
     * description
     * 
     * @return SiteUpdateScheduleDomainCapability property
     */
    public SiteUpdateScheduleDomainCapability getSiteUpdateScheduleDomainCapability() {
        return this.siteUpdateScheduleDomainCapability;
    }

    /**
     * description
     * @return ConsoleCapability property
     */
    public ConsoleCapability getConsoleCapability() {
        return this.consoleCapability;
    }

    /**
     * Sets the environment Utility for the SiteUpdateScheduleConverterImpl.
     * 
     * @param environmentUtility environment Utility
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

    /**
     * Gets the environment Utility
     * 
     * @return environmentUtility environment Utility
     */
    public EnvironmentUtility getEnvironmentUtility() {
        return this.environmentUtility;
    }

    /**
     * description
     * @return ndcImageCapability property
     */
    public NdcImageCapability getNdcImageCapability() {
        return ndcImageCapability;
    }

    /**
     * description
     * @param ndcImageCapability ndcImageCapability property
     */
    public void setNdcImageCapability(NdcImageCapability ndcImageCapability) {
        this.ndcImageCapability = ndcImageCapability;
    }
}
