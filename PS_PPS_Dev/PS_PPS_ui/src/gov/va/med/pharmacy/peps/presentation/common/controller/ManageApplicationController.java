/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.capability.BuildVersionCapability;
import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.QuickActionService;
import gov.va.med.pharmacy.peps.service.national.session.ConsoleService;


/**
 * Manage Application Tab
 */
@Controller
public class ManageApplicationController extends PepsController {

    private static final String URL_SYSTEM_ACTIONS = "/systemActions.go";
    private static final String URL_CONSOLE = "/console.go";
    private static final String RET_SYS_ACT_EDIT = "system.actions.edit";

    @Autowired
    private BuildVersionCapability buildVersionCapability;

    @Autowired
    private ConsoleService consoleService;

    @Autowired
    private QuickActionService quickActionService;

    /**
     * System Information GET
     * @param model Spring Model
     * @return console view
     */
    @RequestMapping(value = URL_CONSOLE, method = RequestMethod.GET)
    public String displayConsoleInformation(Model model) {
        pageTrail.clearTrail();
        pageTrail.addPage("viewSystemInfo", "View System Information", true);

        model.addAttribute(ControllerConstants.NATIONAL_STATUS, retrieveNationalConsoleStatus());
        model.addAttribute(ControllerConstants.LOCAL_STATUS, retrieveLocalConsoleStatus());
        model.addAttribute("printTemplate", ControllerConstants.CONSOLE_STATUS_PRINT_TEMPLATE);

        return "console";
    }

    /**
     * System Actions GET
     * @param tabKey 
     * @param model 
     * @return system.actions.edit view
     */
    @RequestMapping(value = URL_SYSTEM_ACTIONS, method = RequestMethod.GET)
    public String getSystemActions(@RequestParam(value = ControllerConstants.TAB_KEY, required = false,
        defaultValue = ControllerConstants.SEND_DRUG_FILE) String tabKey, Model model) {

        model.addAttribute(ControllerConstants.TAB_KEY, tabKey);

        return RET_SYS_ACT_EDIT;
    }

    /**
     * System Actions POST
     * @param tabKey 
     * @param model 
     * @return system.actions.edit view
     */
    @RequestMapping(value = URL_SYSTEM_ACTIONS, method = RequestMethod.POST)
    public String postSystemActions(@RequestParam(value = ControllerConstants.TAB_KEY, required = false,
        defaultValue = ControllerConstants.SEND_DRUG_FILE) String tabKey, Model model) {
        String status = sendEntireDrugFile();
        model.addAttribute("status", status);
        model.addAttribute(ControllerConstants.TAB_KEY, tabKey);

        return RET_SYS_ACT_EDIT;
    }

    /**
     * About Tab GET
     * @param model Spring Model
     * @return about view
     */
    @RequestMapping(value = "/about.go", method = RequestMethod.GET)
    public String displayAbout(Model model) {
        String page = "about";
        pageTrail.clearTrail();
        pageTrail.addPage(page, "About", true);

        model.addAttribute(ControllerConstants.BUILD_INFO, getBuildInfo());

        return page;
    }

    /**
     * 
     * Retrieves the console data (System Information) for national sites
     *
     * @return list of national statuses 
     */
    private List<SiteConfigVo> retrieveNationalConsoleStatus() {

        // pull out the National site config vo and make it a list for the national display table
        ArrayList<SiteConfigVo> tmpNationalSiteConfigList = new ArrayList<SiteConfigVo>();
        tmpNationalSiteConfigList.add(consoleService.getConsole().getNationalSiteConfigInfo());

        return tmpNationalSiteConfigList;
    }

    /**
     * Retrieves the console data (System Information) for all local sites
     * 
     * @return list of local statuses
     */
    private List<SiteConfigVo> retrieveLocalConsoleStatus() {
        return consoleService.getConsole().getLocalSiteConfigInfoList();
    }

    /**
     * 
     * Returns build information to display to the user on the about page
     *
     * @return the buildInfo
     */
    public ManageApplicationController.BuildInformation getBuildInfo() {
        BuildInformation buildInfo = new BuildInformation();
        String str = buildVersionCapability.getBaseline();
        CharSequence cs = null;
        StringBuffer versionInfo = new StringBuffer();

        if (str != null && !str.isEmpty()) {
            try {
                cs = str.subSequence(str.indexOf("Build-"), str.indexOf("."));
            } catch (IndexOutOfBoundsException e) {
                log("build information not available.");
            }
        }

        versionInfo.append(buildVersionCapability.getVersion());

        if (cs != null) {
            versionInfo.append(" ").append(cs);
        }

        // Adding Build number to version
        buildInfo.setBuildVersion(versionInfo.toString());

        
        buildInfo.setBuildDate(buildVersionCapability.getDate());
        buildInfo.setBuildBaseline(str);

        return buildInfo;
    }

    /**
     * log
     *
     * @param str String
     */
    private void log(String str) {

    }

    /**
     * Send drug file to Vista.
     * 
     * @return SUCCESS
     */
    private String sendEntireDrugFile() {

        String fileSent;

        try {
            quickActionService.sendDrugFileToExternalInterface(getUser());
            fileSent = "Request to send entire drug file was successful";
        } catch (InterfaceException e) {

            // Note: Catching a generic interface exception is generally a bad idea, but in this case we need to catch
            // anything coming from the interface and display a message indicating a problem

            fileSent = "There was an issue sending the drug file. Please check the logs for details.";
        }

        return fileSent;
    }

    /**
     * Inner class used to set/get user build information by the form
     *  
     * 
     */
    public static class BuildInformation {

        private String buildVersion;
        private String buildDate;
        private String buildBaseline;

        /**
         * Empty constructor
         *
         */
        public BuildInformation() {

        }

        /**
         * setBuildVersion
         * @param buildVersion the buildVersion to set
         */
        public void setBuildVersion(String buildVersion) {
            this.buildVersion = buildVersion;
        }

        /**
         * getBuildVersion
         * @return the buildVersion
         */
        public String getBuildVersion() {
            return buildVersion;
        }

        /**
         * setBuildDate
         * @param buildDate the buildDate to set
         */
        public void setBuildDate(String buildDate) {
            this.buildDate = buildDate;
        }

        /**
         * getBuildDate
         * @return the buildDate
         */
        public String getBuildDate() {
            return buildDate;
        }

        /**
         * setBuildBaseline
         * @param buildBaseline the buildBaseline to set
         */
        public void setBuildBaseline(String buildBaseline) {
            this.buildBaseline = buildBaseline;
        }

        /**
         * getBuildBaseline
         * @return the buildBaseline
         */
        public String getBuildBaseline() {
            return buildBaseline;
        }

    };

}
