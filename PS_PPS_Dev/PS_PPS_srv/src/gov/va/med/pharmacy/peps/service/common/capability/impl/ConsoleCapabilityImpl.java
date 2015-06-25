/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.capability.BuildVersionCapability;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugDataVendorVersionVo;
import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.domain.common.capability.SiteConfigDomainCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.VersionCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.SynchronizationStatusCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.VistaAuthoritativeDomainCapability;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.VistaDomainName;
import gov.va.med.pharmacy.peps.service.common.capability.ConsoleCapability;


/**
 * Performs actions on and for the Console (System Information)
 */
public class ConsoleCapabilityImpl implements ConsoleCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ConsoleCapabilityImpl.class);

    private VersionCapability versionCapability; // FDB
    private EnvironmentUtility environmentUtility;
    private SiteConfigDomainCapability siteConfigDomainCapability; // EPL
    private BuildVersionCapability buildVersionCapability;
    private VistaAuthoritativeDomainCapability vistaAuthoritativeDomainCapability; // Vista version
   
    /**
     * Retrieves this site's version info, local or national
     * 
     * @return SiteConfigVo
     */
    public SiteConfigVo getSiteVersionInfo() {

        // the vo is constructed in four steps: basic info, EPL, COTS/FDB, Vista
        SiteConfigVo thisSiteVersionInfo = new SiteConfigVo();
        thisSiteVersionInfo.setSiteNumber(environmentUtility.getSiteNumber());

        // put Date/time now in the string date field using the vo simpledateformat
        thisSiteVersionInfo.setSiteVersionInfoDate(new String(SiteConfigVo.SITE_CONFIG_SIMPLE_DATE_FORMAT.format(new Date())));

        // get the site epl info - includes site name, server name and epl version
        thisSiteVersionInfo.setSiteName("Austin");
        thisSiteVersionInfo.setSiteServerName("National Pharmacy Server");
        thisSiteVersionInfo.setSitePepsDbVersion(buildVersionCapability.getVersion());

        // get the site cots/fdb info
        SiteConfigVo cotsVersionInfo = retrieveCotsStatus();
        thisSiteVersionInfo.setSiteCotsDbVersion(cotsVersionInfo.getSiteCotsDbVersion());

        // get the site Vista info
       // SiteConfigVo vistaVersionInfo = retrieveVistaStatus();
      //  thisSiteVersionInfo.setSiteVistaVersion(vistaVersionInfo.getSiteVistaVersion());

      //  thisSiteVersionInfo.setVistaMessageSynchronization(vistaSynchronizationStatusCapability.getVistaMessageSentCount()
      //      + " / " + vistaSynchronizationStatusCapability.getVistaMessageProcessedCount());

        return thisSiteVersionInfo;
    }

    /**
     * Retrieves the local EPL status from the service layer the vo contains only a valid site number, epl version, site
     * server name, site number
     * 
     * @return SiteConfigVo
     */
    public SiteConfigVo retrievePepsStatus() {

        // no need to copy parts as this has all fields for epl popultaed
        SiteConfigVo eplVersionInfo = new SiteConfigVo();
        eplVersionInfo = siteConfigDomainCapability.retrieve();

        // this is where we add the build date to the basic version stored in the db table
        // trim off the day from this example "Friday, July 25, 2008 11:26:11 AM CDT"
        String spacer = SiteConfigVo.FIELD_SEPERATOR;
        String pepsVersion = eplVersionInfo.getSitePepsDbVersion();
        String buildDate = buildVersionCapability.getDate();
        int firstCommaPosition = buildDate.indexOf("day, ");

        if (firstCommaPosition > -1) {
            firstCommaPosition = firstCommaPosition + PPSConstants.I5;
            buildDate = buildDate.substring(firstCommaPosition);
        }

        // now put the peps version + trimmed build date into the vo
        eplVersionInfo.setSitePepsDbVersion(pepsVersion + spacer + buildDate);

        return eplVersionInfo;
    }

    /**
     * Retrieves the local FDB (COTS) status from the service layer the vo contains only a valid site number and fdb version
     * 
     * @return SiteConfigVo
     */
    public SiteConfigVo retrieveCotsStatus() {

        DrugDataVendorVersionVo dataVendor = versionCapability.retrieveDrugDataVendorVersion();
        String buildVersion = dataVendor.getBuildVersion();
        String dataVersion = dataVendor.getDataVersion();
        String issueDate = dataVendor.getIssueDate();
        String spacer = SiteConfigVo.FIELD_SEPERATOR;

        SiteConfigVo fdbVersion = new SiteConfigVo();
        fdbVersion.setSiteNumber(environmentUtility.getSiteNumber()); // valid
        fdbVersion.setSiteCotsDbVersion(issueDate + spacer + "Data " + dataVersion + spacer + "Build " + buildVersion);

        return fdbVersion;
    }

    /**
     * Retrieves the local Vista status the vo contains only a valid site number and Vista version
     * 
     * @return SiteConfigVo
     */
    public SiteConfigVo retrieveVistaStatus() {
        SiteConfigVo vistaVersion = new SiteConfigVo();
        vistaVersion.setSiteNumber(environmentUtility.getSiteNumber()); // valid

        try {
            VistaDomainName[] names = new VistaDomainName[] {VistaDomainName.VIST_A_VERSION};
            Map<VistaDomainName, List<?>> domains = vistaAuthoritativeDomainCapability.retrieveVistADomains(names, null);
            String cleanVistaVersion = domains.get(VistaDomainName.VIST_A_VERSION).toString();

            if (cleanVistaVersion.endsWith("]")) {
                cleanVistaVersion = cleanVistaVersion.substring(0, (cleanVistaVersion.length() - 1)); // clips trailing "]"
            }

            if (cleanVistaVersion.startsWith("[")) {
                cleanVistaVersion = cleanVistaVersion.substring(1, cleanVistaVersion.length()); // clips beginning "{"
            }

            vistaVersion.setSiteVistaVersion(cleanVistaVersion.trim());
        } catch (Exception ex) {

            vistaVersion.setSiteVistaVersion("Vista Interface Error");
            LOG.error("Vista Interface Error " + new Date() + "\n" + ex.getStackTrace());
        }

        return vistaVersion;
    }

    /**
     * Sets the Version Capability - this is FDB
     * 
     * @param versionCapability VersionCapability
     */
    public void setVersionCapability(VersionCapability versionCapability) {
        this.versionCapability = versionCapability;
    }

    /**
     * Sets the Drug Reference Capability - this is FDB
     * 
     * @param drugReferenceCapability This is the DrugReferenceCapability.
     * 
     */
    public void setDrugReferenceCapability(DrugReferenceCapability drugReferenceCapability) {
    }

    /**
     * Sets the environment Utility
     * 
     * @param environmentUtility environment Utility
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

    /**
     * setSiteConfigDomainCapability
     * @param siteConfigDomainCapability siteConfigDomainCapability property
     */
    public void setSiteConfigDomainCapability(SiteConfigDomainCapability siteConfigDomainCapability) {
        this.siteConfigDomainCapability = siteConfigDomainCapability;
    }

    /**
     * setBuildVersionCapability
     * @param buildVersionCapability buildVersionCapability property
     */
    public void setBuildVersionCapability(BuildVersionCapability buildVersionCapability) {
        this.buildVersionCapability = buildVersionCapability;
    }

    /**
     * setVistaAuthoritativeDomainCapability
     * @param vistaLinkCapability vistaLinkCapability property
     */
    public void setVistaAuthoritativeDomainCapability(VistaAuthoritativeDomainCapability vistaLinkCapability) {
        this.vistaAuthoritativeDomainCapability = vistaLinkCapability;
    }

    /**
     * setSynchronizationStatusCapability
     * 
     * @param vistaSynchronizationStatusCapabilityIn property
     */
    public void setSynchronizationStatusCapability(SynchronizationStatusCapability vistaSynchronizationStatusCapabilityIn) {
    }

}
