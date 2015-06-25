/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;



import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;
import gov.va.med.pharmacy.peps.service.common.scheduler.FdbJobNames;
import gov.va.med.pharmacy.peps.service.common.scheduler.FdbSchedulerControlBean;
import gov.va.med.pharmacy.peps.service.common.scheduler.JobCommands;
import gov.va.med.pharmacy.peps.service.common.scheduler.SchedulerCommands;
import gov.va.med.pharmacy.peps.service.common.scheduler.SchedulerState;
import gov.va.med.pharmacy.peps.service.common.session.FdbSchedulerProcessService;


/**
 * SchedulerController
 * 
 * SchedulerController handles the start, stop of the Scheduler,  
 * rescheduling of the job, pausing a running job and resuming 
 * of a paused job.
 *
 */
@Controller
@RoleNeeded(roles = { Role.PSS_PPSN_SUPERVISOR })
public class FdbSchedulerController extends PepsController {

    private static final Logger LOG = Logger.getLogger(FdbSchedulerController.class);
    
    @Autowired
    private FdbSchedulerProcessService fdbSchedulerProcessService;
    

    /**
     * default constructor
     */
    public FdbSchedulerController() {
    }

    /**
     * fdbSchedulerControlBean
     * @return FdbSchedulerControlBean
     */
    @ModelAttribute("fdbSchedulerControlBean")
    public FdbSchedulerControlBean get() {
        return new FdbSchedulerControlBean();
    }
    
    /**
     * This controller method takes user to the home page
     * @param mode mode
     * @param pModel model 
     * @return return URL
     */
    @RequestMapping(value = "/schedulerHome.go", method = RequestMethod.GET)
    public String schedulerHome(@RequestParam(value = "mode", required = false) String mode, Model pModel) {

        pageTrail.clearTrail();
        pageTrail.addPage("fdbDifControlPanel", "FDB Scheduler", true);
        
        SchedulerState schedulerState  = getSchedulerState();
        
        if (StringUtils.isNotBlank(mode)) {
            if ("schedAccess".equals(mode)) {
                pModel.addAttribute("schedAccess", Boolean.TRUE);
            }
        }
            
        // add the following attributes.
        pModel.addAttribute("commandValues", SchedulerCommands.values());
        pModel.addAttribute("jobCommandValues", JobCommands.values());
        pModel.addAttribute("schedulerState", schedulerState);
        pModel.addAttribute("role", isMigrationRole());
        
        // return the scheduleHome value
        return "scheduler-home";
    }
    
    
    /**
     * This controller method starts the Jobs
     * @param pCommand the command to execute
     * @param pModel pModel
     * @return URL
     */
    @RequestMapping(value = "/status.go", method = RequestMethod.GET)
    public String status(@RequestParam(value = "command", required = false) JobCommands pCommand, Model pModel) {

        LOG.debug("SchedulerController->> command: " + pCommand);
        SchedulerState schedulerState  = getSchedulerState();
        pModel.addAttribute("commandValues", SchedulerCommands.values());
        pModel.addAttribute("jobCommandValues", JobCommands.values());
        pModel.addAttribute("schedulerState", schedulerState);
        pModel.addAttribute("role", isMigrationRole());
        
        return "scheduler-home";
    }    

    
    /**
     * This controller method starts the Jobs
     * @param pCommand the command to execute
     * @param pJobName the pJobName
     * @param hours the hours
     * @param mins the mins
     * @param pModel pModel
     * @return URL
     */
    @RequestMapping(value = "/jobStart.go", method = RequestMethod.GET)
    public String jobStart(@RequestParam(value = "command", required = false) JobCommands pCommand,
            @RequestParam(value = "jobName", required = false) FdbJobNames pJobName,
            @RequestParam(value = "hour", required = false) Integer hours,
            @RequestParam(value = "mins", required = false) Integer mins, Model pModel) {

        LOG.debug("jobStart->> command: " + pCommand);
        FdbSchedulerControlBean schedulerControl = new  FdbSchedulerControlBean();
        schedulerControl.setJobCommands(pCommand);        
        schedulerControl.setJobName(pJobName.getJobName());
        schedulerControl.setFdbJobNames(pJobName);
        schedulerControl.setHour(hours);
        schedulerControl.setMins(mins);
        
        SchedulerState schedulerState = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        
        // add the attributes for the job
        pModel.addAttribute("commandValues", SchedulerCommands.values());
        pModel.addAttribute("jobCommandValues", JobCommands.values());
        pModel.addAttribute("schedulerState", schedulerState);
        pModel.addAttribute("role", isMigrationRole());
        
        return "scheduler-home";
    }    
    
    
    /**
     * update Host Name
     * @param hostName hostName
     * @param pModel pModel
     * @return URL
     */
    @RequestMapping(value = "/updateHostName.go",  method = RequestMethod.GET)
    public String updateHostName(@RequestParam(value = "hostName") String hostName, Model pModel) {
        
        LOG.debug("updateHostName->> hostName: " + hostName);
        
        fdbSchedulerProcessService.updateHostName(hostName, this.getUser());
        SchedulerState schedulerState  = getSchedulerState();
        
        pModel.addAttribute("commandValues", SchedulerCommands.values());
        pModel.addAttribute("jobCommandValues", JobCommands.values());
        pModel.addAttribute("schedulerState", schedulerState);
        pModel.addAttribute("role", isMigrationRole());
        
        return "scheduler-home";
    }
    
    /**
     * update MessagingState  
     *
     * @param messagingState messagingState
     * @param pModel pModel
     * @return URL
     */
    @RequestMapping(value = "/updateMessagingState.go",  method = RequestMethod.GET)
    public String updateMessagingState(@RequestParam(value = "messagingState") Boolean messagingState, Model pModel) {
        
        LOG.debug("updateMessagingState->> messagingState: " + messagingState);
        fdbSchedulerProcessService.updateMessagingState(messagingState, this.getUser());
        SchedulerState schedulerState  = getSchedulerState();
        
        pModel.addAttribute("commandValues", SchedulerCommands.values());
        pModel.addAttribute("jobCommandValues", JobCommands.values());
        pModel.addAttribute("schedulerState", schedulerState);
        pModel.addAttribute("role", isMigrationRole());
        
        return "scheduler-home";
    }
    
    /**
     * 
     * Starts the Scheduler, if jobs are stopped it will start all jobs
     *
     * @param pCommand pCommand
     * @param pModel pModel
     * @return URL
     */
    @RequestMapping(value = "/startScheduler.go", method = RequestMethod.GET)
    public String startScheduler(@RequestParam(value = "command", required = false) JobCommands pCommand, Model pModel) {

        LOG.debug("jobStart->> command: " + pCommand);
        FdbSchedulerControlBean schedulerControl = new  FdbSchedulerControlBean(); 
        schedulerControl.setJobCommands(pCommand);        
        SchedulerState schedulerState = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        
        pModel.addAttribute("commandValues", SchedulerCommands.values());
        pModel.addAttribute("jobCommandValues", JobCommands.values());
        pModel.addAttribute("schedulerState", schedulerState);
        pModel.addAttribute("role", isMigrationRole());
        
        return "scheduler-home";
    }    
    
    
    /**
     * returns the schedulers State
     *
     * @return SchedulerState
     */
    private SchedulerState getSchedulerState() {
        
        FdbSchedulerControlBean schedulerControl = new  FdbSchedulerControlBean(); 
        schedulerControl.setJobCommands(JobCommands.STATUS);
        
        return fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
    }
    
    /**
     * isMigrationRole
     * @return true if user has PSS_PPSN_MIGRATOR role, otherwise false
     */
    private boolean isMigrationRole() {
        
        return getUser().hasRole(Role.PSS_PPSN_MIGRATOR);        
    }
  
    
}
