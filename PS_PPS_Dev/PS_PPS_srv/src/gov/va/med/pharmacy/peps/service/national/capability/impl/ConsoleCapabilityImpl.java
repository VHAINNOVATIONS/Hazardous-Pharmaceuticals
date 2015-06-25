/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.capability.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.LocalConsoleInfoVo;
import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.StatusConsoleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ConsoleDomainCapability;
import gov.va.med.pharmacy.peps.service.national.capability.ConsoleCapability;
import gov.va.med.pharmacy.peps.service.national.messagingservice.outbound.capability.SendToLocalCapability;


/**
 * National implementation of ConsoleCapabilities (System Information) 
 */
public class ConsoleCapabilityImpl extends gov.va.med.pharmacy.peps.service.common.capability.impl.ConsoleCapabilityImpl
    implements ConsoleCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ConsoleCapabilityImpl.class);
    private static final long DELAY_TIME = 60L * 1000L; // 1 minute = 60 seconds * 1000 millieseconds

    private SendToLocalCapability sendToLocalCapability;
    private ConsoleDomainCapability consoleDomainCapability;
    private Date timeOfLastSendToAllLocals = new Date(0); // yes, inti value is the epoch

    /** 
     * Inserts the given consoleInfo (System Information) into the national database
     * 
     * @param inputSiteConfigVo SiteConfigVo the received local SiteConfigVo
     * @param user {@link UserVo} performing the action
     * @throws ValueObjectValidationException  exception
     * @throws DuplicateItemException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.national.capability.ConsoleCapability#
     *  insertFromLocal(gov.va.med.pharmacy.peps.common.vo.SiteConfigVo)
     */
    public void insertFromLocal(SiteConfigVo inputSiteConfigVo, UserVo user) throws ValueObjectValidationException,
        DuplicateItemException {

        // construct the PEPS local version vo for this site then update/insert into national db table
        LocalConsoleInfoVo newPepsLocalConsolveInfo = constructPepsLocalConfigVo(inputSiteConfigVo);
        updateOrInsert(newPepsLocalConsolveInfo, user);

        // construct the COTS/FDB local version vo for this site then update/insert into national db table
        LocalConsoleInfoVo newCotsLocalConsolveInfo = constructCotsLocalConfigVo(inputSiteConfigVo);
        updateOrInsert(newCotsLocalConsolveInfo, user);

        // construct the Vista local version vo for this site then update/insert into national db table
        LocalConsoleInfoVo newVistaLocalConsolveInfo = constructVistaLocalConfigVo(inputSiteConfigVo);
        updateOrInsert(newVistaLocalConsolveInfo, user);
    }

    /**
     * Constructs the PEPS LocalConsoleInfoVo from the received local SiteConfigVo
     * 
     * @param inputSiteConfigVo input local SiteConfigVo
     * @return LocalConsoleInfoVo  - contains PEPS info
     */
    private LocalConsoleInfoVo constructPepsLocalConfigVo(SiteConfigVo inputSiteConfigVo) {
        LocalConsoleInfoVo newPepsLocalConsolveInfo = new LocalConsoleInfoVo();
        newPepsLocalConsolveInfo.setSiteNumber(inputSiteConfigVo.getSiteNumber());
        newPepsLocalConsolveInfo.setSiteName(inputSiteConfigVo.getSiteName());
        newPepsLocalConsolveInfo.setServerName(inputSiteConfigVo.getSiteServerName());
        newPepsLocalConsolveInfo.setSoftwareUpdateType(LocalConsoleInfoVo.PPS);
        newPepsLocalConsolveInfo.setSoftwareUpdateVersion(inputSiteConfigVo.getSitePepsDbVersion());
        newPepsLocalConsolveInfo.setVersionInstallDtm(new Date().toString()); //inputSiteConfigVo.getSiteVersionInfoDate());

        return newPepsLocalConsolveInfo;
    }

    /**
     * Constructs the COTS/FDB LocalConsoleInfoVo from the received local SiteConfigVo
     * 
     * @param inputSiteConfigVo input local SiteConfigVo
     * @return LocalConsoleInfoVo  - contains COTS/FDB info
     */
    public LocalConsoleInfoVo constructCotsLocalConfigVo(SiteConfigVo inputSiteConfigVo) {
        LocalConsoleInfoVo newCotsLocalConsolveInfo = new LocalConsoleInfoVo();
        newCotsLocalConsolveInfo.setSiteNumber(inputSiteConfigVo.getSiteNumber());
        newCotsLocalConsolveInfo.setSiteName(inputSiteConfigVo.getSiteName());
        newCotsLocalConsolveInfo.setServerName(inputSiteConfigVo.getSiteServerName());
        newCotsLocalConsolveInfo.setSoftwareUpdateType(LocalConsoleInfoVo.COTS);
        newCotsLocalConsolveInfo.setSoftwareUpdateVersion(inputSiteConfigVo.getSiteCotsDbVersion());

        //   newCotsLocalConsolveInfo.setVersionInstallDtm(inputSiteConfigVo.getSiteVersionInfoDate());

        return newCotsLocalConsolveInfo;
    }

    /**
     * Constructs the Vista LocalConsoleInfoVo from the received local SiteConfigVo
     * 
     * @param inputSiteConfigVo input local SiteConfigVo
     * @return LocalConsoleInfoVo  - contains Vista info
     */
    public LocalConsoleInfoVo constructVistaLocalConfigVo(SiteConfigVo inputSiteConfigVo) {
        LocalConsoleInfoVo newVistaLocalConsolveInfo = new LocalConsoleInfoVo();
        newVistaLocalConsolveInfo.setSiteNumber(inputSiteConfigVo.getSiteNumber());
        newVistaLocalConsolveInfo.setSiteName(inputSiteConfigVo.getSiteName());
        newVistaLocalConsolveInfo.setServerName(inputSiteConfigVo.getSiteServerName());
        newVistaLocalConsolveInfo.setSoftwareUpdateType(LocalConsoleInfoVo.VISTA);
        newVistaLocalConsolveInfo.setSoftwareUpdateVersion(inputSiteConfigVo.getSiteVistaVersion());

        //  newVistaLocalConsolveInfo.setVersionInstallDtm(inputSiteConfigVo.getSiteVersionInfoDate());

        return newVistaLocalConsolveInfo;
    }

    /**
     * update/insert LocalConsoleInfoVo into national db table
     * 
     * @param inputConsoleInfoVo LocalConsoleInfoVo
     * @param user {@link UserVo} performing the action 
     */
    private void updateOrInsert(LocalConsoleInfoVo inputConsoleInfoVo, UserVo user) {

        // hibernate is automatically doing an Update or Insert function here
        consoleDomainCapability.update(inputConsoleInfoVo, user);
    }

    /**
     * Sets sendToLocalCapability
     * 
     * @param sendToLocalCapability sendToLocalCapability
     */
    public void setSendToLocalCapability(SendToLocalCapability sendToLocalCapability) {
        this.sendToLocalCapability = sendToLocalCapability;
    }

    /**
     * Retrieves this site's status console (System Information) 
     * A Local Status Console (System Information) has a national site config vo 
     *  and a list with 1 local site config (this site) (always only 1)
     * A National Status Console (System Information) has a national site config vo 
     *  and a list with 0..n local site config from the national db table 
     * 
     * @return StatusConsoleVo 
     */
    public StatusConsoleVo getConsole() {

        // this is the NATIONAL version builder!
        StatusConsoleVo thisConsole = new StatusConsoleVo();

        // get this site (this is NATIONAL) and stick it in the NATIONAL info in StatusConsoleVo 
        SiteConfigVo myNationalSiteConfig = new SiteConfigVo();
        myNationalSiteConfig = getSiteVersionInfo();
        thisConsole.setNationalSiteConfigInfo(myNationalSiteConfig);

        // get the LOCAL site config info list from the National DB table and place in the Local list
        //thisConsole.setLocalSiteConfigInfoList(retrieveAllLocalInfoFromNationalTable()); 

        return thisConsole;
    }

    /**
     * setConsoleDomainCapability
     * @param consoleDomainCapability consoleDomainCapability property
     */
    public void setConsoleDomainCapability(ConsoleDomainCapability consoleDomainCapability) {
        this.consoleDomainCapability = consoleDomainCapability;
    }

    /**
     * (System Information) 
     * The local version info is stored in the db table as 1 record per version type per site (so 3 records per site)
     * The DB returns this info in a sorted list, ordered on Site Number, Software Update Type.
     * Hence the list can be processed in sequential order by Site Number.
     * Need to reassemble the 3 records for the same site into one vo and add to the list.
     * Must deal with unexpected software versions (not PEPS, COTS, VISTA)
     * Also, the returned list from the domain has 0..n entries; NOTE the 0 entries 
     * 
     * @return List<SiteConfigVo> the assembled list of all local version info from the national db table
     */
    @SuppressWarnings("unused")
    private List<SiteConfigVo> retrieveAllLocalInfoFromNationalTable() {

        final String INITIAL_SITE_NUMBER = "-1";

        // this gets the 3 LocalConsoleInfoVo per site, one per software type (PEPS, COTS, VISTA) 
        // The DB returns this info in a sorted list, ordered on Site Number, Software Update Type.
        // Hence the list can be processed in sequential order by Site Number.
        List<LocalConsoleInfoVo> localConsoleListBySiteNumberAndType = consoleDomainCapability.retrieveLocalConsoleInfo();
        List<SiteConfigVo> assembledLocalSiteConfigList = new ArrayList<SiteConfigVo>();

        SiteConfigVo workingSiteConfigInfo = new SiteConfigVo();
        String workingSiteNumber = INITIAL_SITE_NUMBER;

        // iterate thru ordered list, and assemble the SiteConfigVo using the same site number
        // when site number changes, add working vo to output list and update the working site number
        for (Iterator<LocalConsoleInfoVo> iter = localConsoleListBySiteNumberAndType.iterator(); iter.hasNext();) {
            LocalConsoleInfoVo readLocalConsoleInfo = (LocalConsoleInfoVo) iter.next();
            String readSiteNumber = readLocalConsoleInfo.getSiteNumber();

            // compare the site number strings, 
            // If different, add to output list & create a new working vo
            // add info to existing working site config vo
            if (!workingSiteNumber.equalsIgnoreCase(readSiteNumber)) {

                // special case for first vo, we will never be here if 0 entries from db
                if (!workingSiteNumber.equalsIgnoreCase(INITIAL_SITE_NUMBER)) {
                    assembledLocalSiteConfigList.add(workingSiteConfigInfo);
                }

                // working with a new site number from the ordered list
                workingSiteConfigInfo = createWorkingVo(readLocalConsoleInfo);
                workingSiteNumber = readSiteNumber;
            }

            // add data to the working vo, doesn't matter if 1st/last in list or for site number
            workingSiteConfigInfo = addVersionDataToWorkingVo(workingSiteConfigInfo, readLocalConsoleInfo);
        }

        // don't add the last one if it has nothing in it (special case of an empty db table!)
        if (!workingSiteNumber.equalsIgnoreCase(INITIAL_SITE_NUMBER)) {
            assembledLocalSiteConfigList.add(workingSiteConfigInfo);
        }

        return assembledLocalSiteConfigList;
    }

    /**
     * (System Information) 
     * Create a new SiteConfigVo with site number, site name, server name
     * The Date is expected to come from the PEPS version 
     * 
     * @param inputLocalConsoleInfo LocalConsoleInfoVo - the input local console vo
     * @return SiteConfigVo - this is the newly created vo with no version info
     */
    private SiteConfigVo createWorkingVo(LocalConsoleInfoVo inputLocalConsoleInfo) {

        SiteConfigVo newSiteConfigInfo = new SiteConfigVo();
        newSiteConfigInfo.setSiteNumber(inputLocalConsoleInfo.getSiteNumber());
        newSiteConfigInfo.setSiteName(inputLocalConsoleInfo.getSiteName());
        newSiteConfigInfo.setSiteServerName(inputLocalConsoleInfo.getServerName());
        newSiteConfigInfo.setSiteCotsDbVersion("Error COTS");
        newSiteConfigInfo.setSitePepsDbVersion("Error PEPS");
        newSiteConfigInfo.setSiteVistaVersion("Error Vista");
        newSiteConfigInfo.setSiteVersionInfoDate("Error PEPS Date");

        return newSiteConfigInfo;
    }

    /**
     * (System Information) 
     * add data to the working version vo
     * PEPS version has to add the date as well as version
     * 
     * @param inputSiteConfigInfo - this is the source vo we are building
     * @param inputLocalConsoleInfo - this is the source vo for 1 of the versions
     * @return SiteConfigVo - this is the vo we have added info to 
     */
    private SiteConfigVo addVersionDataToWorkingVo(SiteConfigVo inputSiteConfigInfo,
                                                   LocalConsoleInfoVo inputLocalConsoleInfo) {

        SiteConfigVo workingSiteConfigInfo = new SiteConfigVo();
        workingSiteConfigInfo = inputSiteConfigInfo;

        String inputVersion = inputLocalConsoleInfo.getSoftwareUpdateType().trim().toUpperCase();

        // we get the date from the PEPS version
        if (LocalConsoleInfoVo.PPS.equalsIgnoreCase(inputVersion)) {
            workingSiteConfigInfo.setSitePepsDbVersion(inputLocalConsoleInfo.getSoftwareUpdateVersion());
            workingSiteConfigInfo.setSiteVersionInfoDate(inputLocalConsoleInfo.getVersionInstallDtm());
        } else if (LocalConsoleInfoVo.COTS.equalsIgnoreCase(inputVersion)) {
            workingSiteConfigInfo.setSiteCotsDbVersion(inputLocalConsoleInfo.getSoftwareUpdateVersion());
        } else if (LocalConsoleInfoVo.VISTA.equalsIgnoreCase(inputVersion)) {
            workingSiteConfigInfo.setSiteVistaVersion(inputLocalConsoleInfo.getSoftwareUpdateVersion());
        } else { // an unhandled software update type has been encountered; report it and continue
            LOG.warn("REPORT TO DEVELOPERS:  Site number " + workingSiteConfigInfo.getSiteNumber()
                + " Unhandled System Information software update type has been encountered >" + inputVersion + "<");
        }

        return workingSiteConfigInfo;
    }

    /**
     * (System Information) 
     * Retrieves the national site's version info for the local's
     * 
     * @return SiteConfigVo
     */
    public SiteConfigVo getNationalSiteVersionInfo() {

        // get this site (this is NATIONAL) 
        SiteConfigVo myNationalSiteConfig = new SiteConfigVo();
        myNationalSiteConfig = getSiteVersionInfo();

        return myNationalSiteConfig;
    }

    /**
     * Sends a request for the local's version information (System Information)
     * contains a time block that prevents a flood of requests within the block period
     * the time block is bypassed by the refresh all function 
     */
    public void requestLocalSystemInformation() {

        // if they asked for this within the last minute - block it
        Date now = new Date();
        long blockTime = timeOfLastSendToAllLocals.getTime() + DELAY_TIME;
        Date blockDate = new Date(blockTime);

        if (now.after(blockDate)) {

            // send an empty SiteConfigVo to the local - tells it to send its version info
            sendToLocalCapability.send(new SiteConfigVo());
            timeOfLastSendToAllLocals = new Date();
        } else {
            LOG.info("Attempted to send version request too soon=" + (blockTime - now.getTime()) + " milliseconds");
        }

    }

    /**
     * Deletes all local version records (System Information) from the national table
     * Then sends a request for the local's version information (System Information) 
     * This bypasses the request time block function since we always want to send a request after clearing the db records 
     */
    public void refreshAllSystemInformation() {

        // delete all records from the national table
        consoleDomainCapability.deleteAll();

        // sends a request for the local's version information (System Information)
        // we clear the time of last send so this will always send the request
        timeOfLastSendToAllLocals = new Date(0);
        requestLocalSystemInformation();
    }

}
