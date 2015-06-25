/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.NdfSynchQueueVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdfSynchQueueDomainCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.VistaFileSynchCapability;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.service.common.capability.FdbSchedulerProcessCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.scheduler.EplNationalInfo;
import gov.va.med.pharmacy.peps.service.common.scheduler.FdbJobNames;
import gov.va.med.pharmacy.peps.service.common.scheduler.FdbSchedulerControlBean;
import gov.va.med.pharmacy.peps.service.common.scheduler.JobCommands;
import gov.va.med.pharmacy.peps.service.common.scheduler.JobStatus;
import gov.va.med.pharmacy.peps.service.common.scheduler.JobStatusInfo;
import gov.va.med.pharmacy.peps.service.common.scheduler.ProcessStatus;
import gov.va.med.pharmacy.peps.service.common.scheduler.SchedulerState;
import gov.va.med.pharmacy.peps.service.common.scheduler.SchedulerStatus;
import gov.va.med.pharmacy.peps.service.common.utility.ManagedItemCapabilityFactory;


/**
 * 
 * FdbSchedulerProcessCapabiltyImpl
 * 
 */
public class FdbSchedulerProcessCapabiltyImpl implements
        FdbSchedulerProcessCapability {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(FdbSchedulerProcessCapabiltyImpl.class);
    private static final int TIMEOUT = 600;
    @Autowired
    private JobDetailBean fdbAddJob;
    @Autowired
    private JobDetailBean fdbUpdateJob;
    @Autowired
    private JobDetailBean stsJob;
    @Autowired
    private JobDetailBean fssJob;
    @Autowired
    private JobDetailBean inactivationCheckJob;

    @Autowired
    private SpringBeanJobFactory jobFactory;
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private CronTriggerBean fdbAddTrigger;

    @Autowired
    private SchedulerState schedulerState;
    private boolean intialized;
    private NationalSettingDomainCapability nationalSettingDomainCapability;
    private NdfSynchQueueDomainCapability ndfSynchQueueDomainCapability;
    private ManagedItemCapability managedItemCapability;
    private VistaFileSynchCapability vistaFileSynchCapability;

    private PlatformTransactionManager transactionManager;

    private ManagedItemCapabilityFactory managedItemCapabilityFactory;
    
    private String errorMessage;

    /**
     * This method initilizes the schedulerState
     */
    private void init() {
        try {

            LOG.debug("init()->>");

            getNationalSettingsData();

            FdbSchedulerControlBean schedulerControl1 = new FdbSchedulerControlBean();
            schedulerControl1.setJobCommands(JobCommands.PAUSE);
            schedulerControl1.setJobName(FdbJobNames.FDB_ADD_JOB.getJobName());
            schedulerControl1.setFdbJobNames(FdbJobNames.FDB_ADD_JOB);
            scheduler.pauseJob(FdbJobNames.FDB_ADD_JOB.getJobName(), fdbAddJob.getGroup());
            this.updateJobStatus(FdbJobNames.FDB_ADD_JOB, fdbAddJob.getGroup(), JobStatus.PAUSED, 0, 0, true);

            FdbSchedulerControlBean schedulerControl2 = new FdbSchedulerControlBean();
            schedulerControl2.setJobCommands(JobCommands.PAUSE);
            schedulerControl2.setJobName(FdbJobNames.FDB_UPDATE_JOB.getJobName());
            schedulerControl2.setFdbJobNames(FdbJobNames.FDB_UPDATE_JOB);
            scheduler.pauseJob(FdbJobNames.FDB_UPDATE_JOB.getJobName(), fdbUpdateJob.getGroup());
            this.updateJobStatus(FdbJobNames.FDB_UPDATE_JOB, fdbUpdateJob.getGroup(), JobStatus.PAUSED, 0, 0, true);

            FdbSchedulerControlBean schedulerControl3 = new FdbSchedulerControlBean();
            schedulerControl3.setJobCommands(JobCommands.PAUSE);
            schedulerControl3.setJobName(FdbJobNames.STS_JOB.getJobName());
            schedulerControl3.setFdbJobNames(FdbJobNames.STS_JOB);
            scheduler.pauseJob(FdbJobNames.STS_JOB.getJobName(), stsJob.getGroup());
            this.updateJobStatus(FdbJobNames.STS_JOB, stsJob.getGroup(), JobStatus.PAUSED, 0, 0, true);

            FdbSchedulerControlBean schedulerControl4 = new FdbSchedulerControlBean();
            schedulerControl4.setJobCommands(JobCommands.PAUSE);
            schedulerControl4.setJobName(FdbJobNames.FSS_JOB.getJobName());
            schedulerControl4.setFdbJobNames(FdbJobNames.FSS_JOB);
            scheduler.pauseJob(FdbJobNames.FSS_JOB.getJobName(), fssJob.getGroup());
            this.updateJobStatus(FdbJobNames.FSS_JOB, fssJob.getGroup(), JobStatus.PAUSED, 0, 0, true);

            FdbSchedulerControlBean schedulerControl5 = new FdbSchedulerControlBean();
            schedulerControl5.setJobCommands(JobCommands.PAUSE);
            schedulerControl5.setJobName(FdbJobNames.INACTIVATION_CHECK_JOB.getJobName());
            schedulerControl5.setFdbJobNames(FdbJobNames.INACTIVATION_CHECK_JOB);
            scheduler.pauseJob(FdbJobNames.INACTIVATION_CHECK_JOB.getJobName(), inactivationCheckJob.getGroup());
            this.updateJobStatus(FdbJobNames.INACTIVATION_CHECK_JOB, inactivationCheckJob.getGroup(),
                    JobStatus.PAUSED, 0, 0, true);

            List<String> jobNames = getJobNamesFromArray();
            this.getSchedulerState().setJobNames(jobNames);

            this.setIntialized(true);
            scheduler.start();

        } catch (SchedulerException e) {
            LOG.error("Error: ExecuteJobCommand==> SchedulerException: " + e.getMessage());
        }

    }

    /**
     * execute Job Command
     * @param schedulerControl schedulerControl
     * @param pRunMode the run mode
     * @return Scheduler state.
     */
    @Override
    public SchedulerState executeJobCommand(FdbSchedulerControlBean schedulerControl, Boolean pRunMode) {
        initialize();

        return executeCommand(schedulerControl);
    }

    /**
     * initialize scheuduler
    */
    private void initialize() {
        if (!this.isIntialized()) {
            init();
        }
    }

    /**
     * Executes Job commands
     * @param schedulerControl schedulerControl
     * @return scheduler state
    */
    private SchedulerState executeCommand(FdbSchedulerControlBean schedulerControl) {

        String groupName = getGroupName(schedulerControl.getJobName());
        FdbJobNames fdbJobName = schedulerControl.getFdbJobNames();
        JobCommands jobCommand = schedulerControl.getJobCommands();
        String cronExpr = generateCronExpression(schedulerControl);

        LOG.debug("executeCommand()-->> groupName: " + groupName + " fdbJobName: "
                    + fdbJobName + " jobCommand: " + jobCommand.toString());

        try {
            switch (jobCommand) {
                case SCHEDULE:
                    LOG.debug("executeCommand()-->> SCHEDULE");
                    rescheduleJob(schedulerControl, cronExpr);
                    updateJobStatus(fdbJobName, groupName, JobStatus.RUNNING,
                               schedulerControl.getHour(), schedulerControl.getMins(), false);
                    schedulerState = getSchedulerStatus();

                    break;
                case START:
                    if (scheduler.isShutdown() || scheduler.isInStandbyMode()) {
                        scheduler.start();
                        scheduler.resumeAll();
                        schedulerState = getSchedulerStatus();
                        LOG.debug("executeCommand()-->> START");
                    }

                    break;
                case PAUSE:
                    if (!scheduler.isShutdown()) {
                        scheduler.pauseJob(fdbJobName.getJobName(), groupName);
                        updateJobStatus(fdbJobName, groupName, JobStatus.PAUSED,
                                schedulerControl.getHour(), schedulerControl.getMins(), false);

                        schedulerState = getSchedulerStatus();
                        LOG.debug("executeCommand()-->> PAUSE");
                    }

                    break;
                case PAUSE_ALL:
                    if (!scheduler.isShutdown()) {
                        pauseAllJobs();
                        schedulerState = getSchedulerStatus();
                    }

                    break;
                case RESUME:
                    if (!scheduler.isShutdown()) {
                        scheduler.resumeJob(fdbJobName.getJobName(), groupName);
                        updateJobStatus(fdbJobName, groupName, JobStatus.RUNNING,
                                schedulerControl.getHour(), schedulerControl.getMins(), false);
                        schedulerState = getSchedulerStatus();
                        LOG.debug("executeCommand()-->> RESUME");
                    }

                    break;

                case RESUME_ALL:
                    if (!scheduler.isShutdown()) {
                        resumeAllJobs();
                        schedulerState = getSchedulerStatus();
                    }

                    break;
                case STOP:
                    if (!scheduler.isShutdown()) {
                        LOG.debug("executeCommand()-->> STOP");
                        scheduler.standby();
                        pauseAllJobs();
                        schedulerState = getSchedulerStatus();
                    }

                    break;
                case SHUTDOWN:
                    scheduler.shutdown();

                    break;
                case STATUS:
                    LOG.debug("executeCommand()-->>STATUS");
                    schedulerState.setServerCurrentTime(Calendar.getInstance().getTime());

                    if (!scheduler.isShutdown()) {
                        LOG.debug("executeCommand()-->> scheduler.isShutDown" + scheduler.isShutdown());
                        schedulerState = getSchedulerStatus();
                    }

                    break;
                default:

                    break;
            }

        } catch (SchedulerException e) {
            LOG.error("Error: SchedulerException:  " + e.getMessage());
        } catch (ParseException e) {
            LOG.error("Error: ParseException: " + e.getMessage());
        }

        return schedulerState;
    }

    /**
     * 
     * Description
     *
     * @param jobName
     * @param groupName
     */
    private void updateTriggerStatus() {

        Trigger[] fdbAddTriggers;
        LOG.debug("updateTriggerStatus -->>");

        try {
            fdbAddTriggers = this.getTriggers(fdbAddJob.getName(), fdbAddJob.getGroup());
            this.getSchedulerState().addJobTriggersMap(fdbAddJob.getName(), fdbAddTriggers);

            Trigger[] fdbUpdateTriggers = this.getTriggers(fdbUpdateJob.getName(), fdbUpdateJob.getGroup());
            this.getSchedulerState().addJobTriggersMap(fdbUpdateJob.getName(), fdbUpdateTriggers);

            Trigger[] stsTriggers = this.getTriggers(stsJob.getName(), stsJob.getGroup());
            this.getSchedulerState().addJobTriggersMap(stsJob.getName(), stsTriggers);

            Trigger[] fssTriggers = this.getTriggers(fssJob.getName(), fssJob.getGroup());
            this.getSchedulerState().addJobTriggersMap(fssJob.getName(), fssTriggers);

            Trigger[] inactivationTriggers = this.getTriggers(inactivationCheckJob.getName(), inactivationCheckJob.getGroup());
            this.getSchedulerState().addJobTriggersMap(inactivationCheckJob.getName(), inactivationTriggers);

        } catch (SchedulerException e) {
            LOG.error(" Error:  SchedulerException: " + e.getMessage());
        }

    }

    /**
        * resumes all Jobs
        *
        * @throws SchedulerException SchedulerException
        */
    private void resumeAllJobs() throws SchedulerException {

        Map<String, JobStatusInfo> jobStatusInfoMap = getSchedulerState().getJobStatusInfoMap();

        LOG.debug(".................resuming All Jobs..................");
        scheduler.resumeAll();

        for (Map.Entry<String, JobStatusInfo> entry : jobStatusInfoMap.entrySet()) {

            LOG.debug(entry.getKey() + "/" + entry.getValue());
            JobStatusInfo statusInfo = entry.getValue();

            updateJobStatus(statusInfo.getFdbJobNames(), statusInfo.getGroupName(),
                    JobStatus.RUNNING, statusInfo.getHour(), statusInfo.getMins(), false);

            LOG.debug("1.statusInfo: jobName: " + statusInfo.getJobName());
            LOG.debug("1.statusInfo: jobGroupName:  " + statusInfo.getGroupName());
            LOG.debug("1.statusInfo: job status: " + statusInfo.getStatus());

        }

    }

    /**
     * pause All Jobs
     *
     * @throws SchedulerException SchedulerException
     */
    private void pauseAllJobs() throws SchedulerException {

        Map<String, JobStatusInfo> jobStatusInfoMap = getSchedulerState().getJobStatusInfoMap();
        LOG.debug(".................pausing All Jobs..................");
        scheduler.pauseAll();

        for (Map.Entry<String, JobStatusInfo> entry : jobStatusInfoMap.entrySet()) {
            JobStatusInfo statusInfo = entry.getValue();
            updateJobStatus(statusInfo.getFdbJobNames(), statusInfo.getGroupName(),
                    JobStatus.PAUSED, statusInfo.getHour(), statusInfo.getMins(), false);
        }
    }

    /**
     * 
     * Reshedules a job
     *
     * @param schedulerControl schedulerControl
     * @param cronExpr cron expression
     * @throws ParseException ParseException
     * @throws SchedulerException SchedulerException
     */
    public void rescheduleJob(FdbSchedulerControlBean schedulerControl, String cronExpr)
        throws ParseException, SchedulerException {

        String groupName = getGroupName(schedulerControl.getJobName());

        Map<String, Trigger[]> triggerMap = this.getSchedulerState().getJobTriggerMap();
        Trigger[] jobTriggers = triggerMap.get(schedulerControl.getJobName()); // we are only using one trigger per job.
        String triggerName = jobTriggers[0].getName();
        String triggerGroup = jobTriggers[0].getGroup();

        LOG.debug("rescheduleJob-->> Cron Expression: " + cronExpr);
        LOG.debug("jobName: " + schedulerControl.getJobName());
        LOG.debug("triggerName:  " + triggerName);
        LOG.debug("triggerGroup:  " + triggerGroup);

        CronTriggerBean myTrigger = new CronTriggerBean();
        myTrigger.setName(triggerName);
        myTrigger.setGroup(triggerGroup);
        myTrigger.setCronExpression(new CronExpression(cronExpr));
        myTrigger.setStartTime(new Date());
        myTrigger.setEndTime(null); // test this
        myTrigger.setJobName(schedulerControl.getJobName());
        myTrigger.setJobGroup(groupName);

        Trigger[] newTriggers = new Trigger[2];
        newTriggers[0] = myTrigger;
        SchedulerState state = getSchedulerState();
        state.addJobTriggersMap(schedulerControl.getJobName(), newTriggers);

        this.getScheduler().rescheduleJob(triggerName, triggerGroup, myTrigger);

    }

    /**
     * Update the JobStatusInfo and put in map
     * @param pFdbJobName job status
     * @param groupName group name
     * @param pJobStatus pJobStatus
     * @param pHour hours
     * @param pMins mins
     * @param init init
     */
    private void updateJobStatus(FdbJobNames pFdbJobName, String groupName,
            JobStatus pJobStatus, Integer pHour, Integer pMins, boolean init) {

        JobStatusInfo jobStatusInfo;
        Trigger[] fdbTriggers;
        SchedulerState state = getSchedulerState();

        LOG.debug("updateJobStatus()->> jobName: "
                    + pFdbJobName + "groupName: " + groupName + " JobStatus: " + pJobStatus.toString() + "init: " + init);

        try {

            if (init) {
                fdbTriggers = this.getTriggers(pFdbJobName.getJobName(), groupName);
                state.addJobTriggersMap(pFdbJobName.getJobName(), fdbTriggers);
            } else {
                Map<String, Trigger[]> triggerMap = state.getJobTriggerMap();
                fdbTriggers = triggerMap.get(pFdbJobName.getJobName()); // we are only using one trigger per job.
            }

            if (state.getJobStatusInfoMap().containsKey(pFdbJobName.getJobName())) {
                jobStatusInfo = state.getJobStatusInfoMap().get(pFdbJobName.getJobName());
            } else {
                jobStatusInfo = new JobStatusInfo();
            }

            // get eplNational data from 
            EplNationalInfo eplNationalInfo = state.getEplNationalInfoMap().get(pFdbJobName);

            if (eplNationalInfo != null) {
                jobStatusInfo.setProcessStatus(eplNationalInfo.getProcessStatus());
                jobStatusInfo.setLastSuccessRunDate(eplNationalInfo.getLastSuccessRunDate());
            }

            jobStatusInfo.setJobName(pFdbJobName.getJobName());
            jobStatusInfo.setGroupName(groupName);
            jobStatusInfo.setStatus(pJobStatus);
            jobStatusInfo.setFdbJobNames(pFdbJobName);
            jobStatusInfo.setNextFireTime(fdbTriggers[0].getNextFireTime());
            jobStatusInfo.setHour(pHour);
            jobStatusInfo.setMins(pMins);

            state.addJobStatusInfoToMap(jobStatusInfo);

        } catch (SchedulerException e) {
            LOG.error("Error: SchedulerException: " + e.getMessage());
        }

    }

    /**
     * update All JobsStatus
     *
     * @throws SchedulerException SchedulerException
     */
    private void updateAllJobsStatus() throws SchedulerException {

        Map<String, JobStatusInfo> jobStatusInfoMap = getSchedulerState().getJobStatusInfoMap();
        LOG.debug(".................updating All Jobs..................");

        for (Map.Entry<String, JobStatusInfo> entry : jobStatusInfoMap.entrySet()) {
            JobStatusInfo statusInfo = entry.getValue();

            LOG.debug("statusInfo: jobName: " + statusInfo.getJobName());
            LOG.debug("statusInfo: jobGroupName:  " + statusInfo.getGroupName());
            LOG.debug("statusInfo: job status: " + statusInfo.getStatus());
            LOG.debug("statusInfo: next fire time: " + statusInfo.getNextFireTime());
            LOG.debug("statusInfo: Process status: " + statusInfo.getProcessStatus());

            updateJobStatus(statusInfo.getFdbJobNames(), statusInfo.getGroupName(),
                    statusInfo.getStatus(), statusInfo.getHour(), statusInfo.getMins(), false);
        }
    }

    /**
     * returns the scheduler state 
     * @return scheduler state
     */
    private SchedulerState getSchedulerStatus() {
        SchedulerState state = null;

        try {

            state = getNationalSettingsData();

            if (scheduler.isInStandbyMode()) {
                state.setSchedulerStatus(SchedulerStatus.STANDBY);
            } else if (scheduler.isShutdown()) {
                state.setSchedulerStatus(SchedulerStatus.SHUTDOWN);
            } else {
                state.setSchedulerStatus(SchedulerStatus.RUNNING);
            }

            updateTriggerStatus();
            updateAllJobsStatus();

            state.setScheduler(scheduler);

        } catch (SchedulerException e) {
            LOG.error("Error:getSchedulerStatus(): SchedulerException: " + e.getMessage());
        }

        return state;
    }

    /**
     *  returns triggers for JobName, JobGroup
     * @param pJobName job name
     * @param pJobGroup goup name
     * @return Triggers
     * @throws SchedulerException SchedulerException
     */
    private Trigger[] getTriggers(String pJobName, String pJobGroup) throws SchedulerException {

        Trigger[] triggers = scheduler.getTriggersOfJob(pJobName, pJobGroup);

        return triggers;
    }

    /**
     * creates a list of job names
     * 
     * @return returns list of job names
     * @throws SchedulerException
     *             SchedulerException
     */
    private List<String> getJobNamesFromArray() throws SchedulerException {

        List<String> jobNamesList = new ArrayList<String>();

        String[] jobNames1 = scheduler.getJobNames(fdbAddJob.getGroup());

        for (int i = 0; i < jobNames1.length; i++) {
            jobNamesList.add(jobNames1[i]);
        }

        return jobNamesList;
    }

    /**
     * Returns the GroupName
     * 
     * @param pJobName
     *            the job name
     * @return returns the groupName
     */
    private String getGroupName(String pJobName) {

        // just need to get any job, since all jobs are part of the same group.
        String groupName = fdbAddJob.getGroup();

        return groupName;
    }

    /**
     * populates the SchedulerState 
     * with data form VistaMessageInfo and EplNationalInfo.
     * @return SchedulerState
     */
    private SchedulerState getNationalSettingsData() {

        LOG.debug("getNationalSettingsData()->>");

        Map<String, Object> nationalSettingsMap = retrieveNationalSettingsMap();
        SchedulerState state = this.getSchedulerState();

        Map<FdbJobNames, EplNationalInfo> nationalInfoMap = createNationalInfoMap(nationalSettingsMap);
        state.setEplNationalInfoMap(nationalInfoMap);

        NationalSettingVo vo1 = (NationalSettingVo) nationalSettingsMap.get(NationalSetting.HOST_NAME.toString());
        state.setFdaHostName(vo1.getStringValue());

        NationalSettingVo vo2 = (NationalSettingVo) nationalSettingsMap.get(NationalSetting.MESSAGE_STATUS.toString());
        state.setMessagingRunning(vo2.getBooleanValue());

        NationalSettingVo vo3 = (NationalSettingVo) nationalSettingsMap.get(NationalSetting.MESSAGE_ERROR.toString());
        state.setErrorMessage(vo3.getStringValue());

        NationalSettingVo vo4 = (NationalSettingVo) nationalSettingsMap.get(NationalSetting.NUM_MSG_QUEUE.toString());
        state.setMessagesOnQueueCount(vo4.getIntegerValue().intValue());

        NationalSettingVo vo5 =
            (NationalSettingVo) nationalSettingsMap.get(NationalSetting.MESSAGE_QUEUE_IN_PROGRESS.toString());
        state.setMessagingQueueInProcess(vo5.getBooleanValue());

        return state;
    }

    /**
     * get Message Status
     *
     * @return messages status
     */
    public boolean getMessageStatus() {

        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        NationalSettingVo vo = null;
        boolean success = false;

        try {
            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);

            Map<String, Object> nationalSettingsMap = retrieveNationalSettingsMap();
            vo = (NationalSettingVo) nationalSettingsMap.get(NationalSetting.MESSAGE_STATUS.toString());

            if (vo != null) {
                success = vo.getBooleanValue();
            }
        } catch (TransactionException e) {
            LOG.error("TransactionException, in getMessageStatus()  " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error("The Underlying cause is " + e.getCause().getMessage());
            }
        } catch (Exception e) {
            LOG.error("Exception, in getMessageStatus()  " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error("This Underlying cause is " + e.getCause().getMessage());
            }
        } finally {
            transactionManager.commit(status);
        }

        return success;
    }

    /**
     * updates the Host Name
     * @param hostName hostName
     * @param pUser pUser
     */
    @Override
    public void updateHostName(String hostName, UserVo pUser) {

        NationalSettingVo vo = new NationalSettingVo();
        vo.setKeyName(NationalSetting.HOST_NAME.toString());
        vo.setStringValue(hostName);
        updateNationalSettings(vo, pUser);

    }

    /**
     * update MessagingState
     * @param messagingState messagingState
     * @param pUser pUser
     */
    @Override
    public void updateMessagingState(Boolean messagingState, UserVo pUser) {

        SchedulerState state = null;

        LOG.debug("getSchedulerStatus()");

        state = getNationalSettingsData();

        if (state.isMessagingQueueInProgress()) {

            // No changes to Message Status while Message Queuing is in Process
            return;
        }

        NationalSettingVo vo = new NationalSettingVo();

        if (messagingState) {

            // Set Message Queuing to be in process
            NationalSettingVo messageQueuingVo = new NationalSettingVo();
            messageQueuingVo.setKeyName(NationalSetting.MESSAGE_QUEUE_IN_PROGRESS.toString());
            messageQueuingVo.setBooleanValue(true);
            updateNationalSettings(messageQueuingVo, pUser);
            
            vo.setKeyName(NationalSetting.MESSAGE_STATUS.toString());
            vo.setBooleanValue(messagingState);
            vo.setDateValue(new Date());
            updateNationalSettings(vo, pUser);

            try {
                // Empty the VistA queue
                emptyQueue(pUser);
            } catch (Exception e) {
                vo.setKeyName(NationalSetting.MESSAGE_STATUS.toString());
                vo.setBooleanValue(!messagingState);
                vo.setDateValue(new Date());
                updateNationalSettings(vo, pUser);
                LOG.error("Excpetion occured while procesing messages so putting the state back in false.");
                
            }

            // Set Message Queuing in Process to false
            messageQueuingVo.setKeyName(NationalSetting.MESSAGE_QUEUE_IN_PROGRESS.toString());
            messageQueuingVo.setBooleanValue(false);
            updateNationalSettings(messageQueuingVo, pUser);

        }

        vo.setKeyName(NationalSetting.MESSAGE_STATUS.toString());
        vo.setBooleanValue(messagingState);

        // also setting the Date as it is used in other settings.
        vo.setDateValue(new Date());
        updateNationalSettings(vo, pUser);

    }

    /**
     * Generate a CRON expression is a string comprising 5 or 6 fields separated by white space.
     * minutes mandatory = yes. allowed values = {@code  0-59    * / , -}
     * hours mandatory = yes. allowed values = {@code 0-23   * / , -}
     * dayOfMonth mandatory = yes. allowed values = {@code 1-31  * / , - ? L W}
     * 
     * m month mandatory = yes. allowed values = {@code 1-12 or JAN-DEC    * / , -}
     * dayOfWeek mandatory = yes. allowed values = {@code 0-6 or SUN-SAT * / , - ? L #}
     * year mandatory = no. allowed values = {@code 1970–2099    * / , -}
     * @return a CRON Formatted String.
     * 
     * 
        * * * * * ?
        - - - - - -
        | | | | | |
        | | | | | +----- day of week (MON-SUN)
        | | | | +------- month (1 - 12)
        | | | +--------- day of month (1 - 31)
        | | +----------- hour (0 - 23)
        | +------------- min (0 - 59)
        +------------- sec (0 - 59)
     */

    /**
     * Generates cron expresion based on hours/minutes
     * @param schedulerControl schedulerControl 
     * @return the cron expresssion
     */
    private String generateCronExpression(FdbSchedulerControlBean schedulerControl) {
        return String.format("%1$s %2$s %3$s %4$s %5$s %6$s", "0", schedulerControl.getMins(),
                schedulerControl.getHour(), "*", "*", "?");
    }

    /**
     * retrieveNationalSettingsMap
     *
     * @return Map
     */
    public Map<String, Object> retrieveNationalSettingsMap() {

        Map<String, Object> nationalMap = new HashMap<String, Object>();
        List<NationalSettingVo> nationalSettingList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo nationalSettingVo2 : nationalSettingList) {
            nationalMap.put(nationalSettingVo2.getKeyName(), nationalSettingVo2);

            //            LOG.debug("key: " + nationalSettingVo2.getKeyName());
        }

        return nationalMap;
    }

    /**
     * updates NationalSettings Vo by seting the vo with the key/value
     * @param vo NationalSettingVo
     * @param pUser pUser
     */
    public void updateNationalSettings(NationalSettingVo vo, UserVo pUser) {
       
        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;

        try {
            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);
            Map<String, Object> nsMap = retrieveNationalSettingsMap();
    
            if (nsMap != null) {
                NationalSettingVo pVo = (NationalSettingVo) nsMap.get(vo.getKeyName());
    
                pVo.setKeyName(vo.getKeyName());
                pVo.setStringValue(vo.getStringValue());
                pVo.setBooleanValue(vo.getBooleanValue());
                pVo.setDateValue(vo.getDateValue());
                pVo.setDecimalValue(vo.getDecimalValue());
                pVo.setIntegerValue(vo.getIntegerValue());
                pVo.setCreatedBy(vo.getCreatedBy());
                pVo.setCreatedDate(vo.getCreatedDate());
    
                nationalSettingDomainCapability.update(pVo, pUser);
    
            }
        } catch (TransactionException e) {
            LOG.error("TransactionException, in getMessageStatus()  " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error("The Underlying cause is " + e.getCause().getMessage());
            }
        } catch (Exception e) {
            LOG.error("Exception, in getMessageStatus()  " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error("This Underlying cause is " + e.getCause().getMessage());
            }
        } finally {
            transactionManager.commit(status);
        }

    }

    /**
     * create National Info map
     * @param natitionalSettingsMap natitionalSettingsMap
     * @return Map
     */
    private Map<FdbJobNames, EplNationalInfo> createNationalInfoMap(Map<String, Object> natitionalSettingsMap) {

        LOG.debug("createNationalInfoMap()-->");

        SchedulerState schedState = this.getSchedulerState();

        Map<FdbJobNames, EplNationalInfo> eplNationalInfoMap = schedState.getEplNationalInfoMap();
        eplNationalInfoMap.clear();

        // fdb_add_job
        EplNationalInfo eplNationalInfo1 = new EplNationalInfo();
        eplNationalInfo1.setFdbJobNames(FdbJobNames.FDB_ADD_JOB);
        NationalSettingVo lastRunVo1 = (NationalSettingVo)
                        natitionalSettingsMap.get(NationalSetting.FDB_ADD_LAST_RUN.toString());
        eplNationalInfo1.setLastSuccessRunDate(lastRunVo1.getDateValue());

        NationalSettingVo runStateVo1 = (NationalSettingVo)
                        natitionalSettingsMap.get(NationalSetting.FDB_ADD_RUN_STATE.toString());
        eplNationalInfo1.setProcessStatus(ProcessStatus.valueOf(runStateVo1.getStringValue()));
        eplNationalInfoMap.put(FdbJobNames.FDB_ADD_JOB, eplNationalInfo1);

        // fdb_update_job
        EplNationalInfo eplNationalInfo2 = new EplNationalInfo();
        eplNationalInfo2.setFdbJobNames(FdbJobNames.FDB_UPDATE_JOB);
        NationalSettingVo lastRunVo2 = (NationalSettingVo)
                    natitionalSettingsMap.get(NationalSetting.FDB_UPDATE_LAST_RUN.toString());
        eplNationalInfo2.setLastSuccessRunDate(lastRunVo2.getDateValue());

        NationalSettingVo runStateVo2 = (NationalSettingVo)
                    natitionalSettingsMap.get(NationalSetting.FDB_UPDATE_RUN_STATE.toString());
        eplNationalInfo2.setProcessStatus(ProcessStatus.valueOf(runStateVo2.getStringValue()));
        eplNationalInfoMap.put(FdbJobNames.FDB_UPDATE_JOB, eplNationalInfo2);

        // FSS_job
        EplNationalInfo eplNationalInfo3 = new EplNationalInfo();
        eplNationalInfo3.setFdbJobNames(FdbJobNames.FSS_JOB);
        NationalSettingVo lastRunVo3 = (NationalSettingVo)
                    natitionalSettingsMap.get(NationalSetting.FSS_UPDATE_LAST_RUN.toString());
        eplNationalInfo3.setLastSuccessRunDate(lastRunVo3.getDateValue());

        NationalSettingVo runStateVo3 = (NationalSettingVo)
                    natitionalSettingsMap.get(NationalSetting.FSS_RUN_STATE.toString());
        eplNationalInfo3.setProcessStatus(ProcessStatus.valueOf(runStateVo3.getStringValue()));
        eplNationalInfoMap.put(FdbJobNames.FSS_JOB, eplNationalInfo3);

        // STS_job
        EplNationalInfo eplNationalInfo4 = new EplNationalInfo();
        eplNationalInfo4.setFdbJobNames(FdbJobNames.STS_JOB);
        NationalSettingVo lastRunVo4 = (NationalSettingVo)
                    natitionalSettingsMap.get(NationalSetting.STS_UPDATE_LAST_RUN.toString());
        eplNationalInfo4.setLastSuccessRunDate(lastRunVo4.getDateValue());

        NationalSettingVo runStateVo4 = (NationalSettingVo)
                    natitionalSettingsMap.get(NationalSetting.STS_RUN_STATE.toString());
        eplNationalInfo4.setProcessStatus(ProcessStatus.valueOf(runStateVo4.getStringValue()));
        eplNationalInfoMap.put(FdbJobNames.STS_JOB, eplNationalInfo4);

        // Inactivation check Job
        EplNationalInfo eplNationalInfo5 = new EplNationalInfo();
        eplNationalInfo5.setFdbJobNames(FdbJobNames.INACTIVATION_CHECK_JOB);
        NationalSettingVo lastRunVo5 = (NationalSettingVo)
                    natitionalSettingsMap.get(NationalSetting.INACTIVATION_CHECK_LAST_RUN.toString());
        eplNationalInfo5.setLastSuccessRunDate(lastRunVo5.getDateValue());

        NationalSettingVo runStateVo5 =
            (NationalSettingVo) natitionalSettingsMap.get(NationalSetting.INACTIVATION_RUN_STATE.toString());
        eplNationalInfo5.setProcessStatus(ProcessStatus.valueOf(runStateVo5.getStringValue()));
        eplNationalInfoMap.put(FdbJobNames.INACTIVATION_CHECK_JOB, eplNationalInfo5);

        return eplNationalInfoMap;
    }

    /**
     * Get the {@link ManagedItemDomainCapability} for the given {@link EntityType} using the
     * {@link ManagedItemCapabilityFactory}.
     * 
     * @param <T> Type of {@link ManagedItemDomainCapability}
     * @param entityType {@link EntityType}
     * @return {@link ManagedItemDomainCapability}
     */

    /**
     * emptyQueue empties the VistA queue
     * @param user User not really used
     */
    private void emptyQueue(UserVo user) {

        // Get the list of messages to be sent
        List<NdfSynchQueueVo> queueList = ndfSynchQueueDomainCapability.retrieve();

        
        // Go through the list for each of the Types of messages needed
        if (queueList != null) {

            List<Difference> diffList = new ArrayList<Difference>();
            Difference diff = new Difference(FieldKey.INACTIVATION_DATE, "", "");
            diffList.add(diff);

           
            // Send Drug Units
            EntityType itemType = EntityType.DRUG_UNIT;
            emptyType(queueList, diffList, user, itemType);

            // Send Dispense Units
            itemType = EntityType.DISPENSE_UNIT;
            emptyType(queueList, diffList, user, itemType);
 
            // Send VA Generic Name
            itemType = EntityType.GENERIC_NAME;
            emptyType(queueList, diffList, user, itemType);

            // Send Dosage Form
            itemType = EntityType.DOSAGE_FORM;
            emptyType(queueList, diffList, user, itemType);

            // Send Drug Class
            diff.setFieldKey(FieldKey.PARENT_DRUG_CLASS);
            itemType = EntityType.DRUG_CLASS;
            emptyType(queueList, diffList, user, itemType);


            // Send Drug Ingredient
            diff.setFieldKey(FieldKey.INACTIVATION_DATE);
            itemType = EntityType.INGREDIENT;
            emptyType(queueList, diffList, user, itemType);


            // Send VA Product
            itemType = EntityType.PRODUCT;
            emptyType(queueList, diffList, user, itemType);

            // Send PACKAGE_TYPE
            itemType = EntityType.PACKAGE_TYPE;
            emptyType(queueList, diffList, user, itemType);
            
            // Send MANUFACTURER
            itemType = EntityType.MANUFACTURER;
            emptyType(queueList, diffList, user, itemType);
            
            // Send NDC
            itemType = EntityType.NDC;
            emptyType(queueList, diffList, user, itemType);
            
        }

        
        // reset error message to empty if no items left in queue
        List<NationalSettingVo> settingList = nationalSettingDomainCapability.retrieve();
    
        for (NationalSettingVo setting : settingList) {
    
            if (setting.getKeyName().equalsIgnoreCase(NationalSetting.NUM_MSG_QUEUE.toString())) {
                if (setting.getIntegerValue() == 0) {
                    for (NationalSettingVo setting2 : settingList) {
                        if (setting2.getKeyName().equalsIgnoreCase(NationalSetting.MESSAGE_ERROR.toString())) {
                            setting2.setStringValue("");
                            nationalSettingDomainCapability.update(setting2, user);
                            break;
                        }
                    }
                }
                
                break;
            }
        }

    }


    /**
     * Description
     *
     * @param queueList List of queued messages
     * @param diffList Faked diff list to cause it to be sent
     * @param user User not really used
     * @param itemType Entity Type being sent
     * @return true if no error logged
     */
    private boolean emptyType(List<NdfSynchQueueVo> queueList, List<Difference> diffList, UserVo user, EntityType itemType) {
        
        List<NationalSettingVo> settingList = nationalSettingDomainCapability.retrieve();
        NationalSettingVo numQueueMessagesSetting = new NationalSettingVo();

        for (NationalSettingVo setting : settingList) {
            if (setting.getKeyName().equalsIgnoreCase(NationalSetting.NUM_MSG_QUEUE.toString())) {
                numQueueMessagesSetting = setting;
                break;
            }
        }
        
        
        for (NdfSynchQueueVo synchQueueVo : queueList) {

            try {

                if (synchQueueVo.getItemType().equalsIgnoreCase(itemType.toString())) {
                    ManagedItemVo item = managedItemCapability.retrieve(synchQueueVo.getIdFk(), itemType);
                    LOG.debug("Found a " + itemType + " and it is a value of " + item.getValue());
               
                    if (synchQueueVo.getActionType().equalsIgnoreCase(ItemAction.ADD.toString())) {
                        LOG.debug("ADD and send");
                        vistaFileSynchCapability.sendNewItemToVista(item, user, true, true);
                        LOG.debug("ADD and update");
                        managedItemCapability.update(item, user);
                        LOG.debug("ADD and done");
                    } else {
                        LOG.debug("Modfy and send");
                        vistaFileSynchCapability.sendModifiedItemToVista(item, diffList, user, true, true);
                        LOG.debug("Modfy and done");
                    }
                    
                    ndfSynchQueueDomainCapability.deleteItemById(synchQueueVo.getId());
                    LOG.debug("after delete");
                    numQueueMessagesSetting.setIntegerValue(numQueueMessagesSetting.getIntegerValue() - 1);
                }
                
            } catch (Exception e) {
                String error = "Error while trying to empty queue for ViatA - EPL_ID " + synchQueueVo.getId()
                    + "/n    ItemType - " + synchQueueVo.getItemType()
                    + "/n    Action - " + synchQueueVo.getActionType() + "/n";
                    
                LOG.error(error);

                for (NationalSettingVo setting : settingList) {

                    if (setting.getKeyName().equalsIgnoreCase(NationalSetting.MESSAGE_ERROR.toString())) {
                        setting.setStringValue(error);
                        nationalSettingDomainCapability.update(setting, user);
                        break;
                    }
                }
                
                nationalSettingDomainCapability.update(numQueueMessagesSetting, user);
                
                return false;

            }
            
        }
        
        nationalSettingDomainCapability.update(numQueueMessagesSetting, user);
        return true;

    }
    
    /**
     * Add Current Request to the VistA Queue
     *
     * @param managedItem Item to be added (by reference) 
     * @param itemType The item's type
     * @param action The action - Add or Modify
     * @param user - The current user
     */
    protected void addToQueue(ManagedItemVo managedItem, EntityType itemType, ItemAction action, UserVo user) {
        NdfSynchQueueVo ndfSynchQueueVo = new NdfSynchQueueVo();
        ndfSynchQueueVo.setIdFk(managedItem.getId());
        ndfSynchQueueVo.setItemType(itemType.toString());
        ndfSynchQueueVo.setActionType(action.value());

        //        NdfSynchQueueDomainCapability 
//        ndfSynchQueueDomainCapability = 
//            managedItemCapabilityFactory.getDomainCapability(EntityType.NDF_SYNCH_QUEUE);
        
        ndfSynchQueueDomainCapability.createWithoutDuplicateCheck(ndfSynchQueueVo, user);
        List<NationalSettingVo> settingList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo setting : settingList) {

            if (setting.getKeyName().equalsIgnoreCase(NationalSetting.NUM_MSG_QUEUE.toString())) {
                setting.setIntegerValue(setting.getIntegerValue() + 1);
                nationalSettingDomainCapability.update(setting, user);
                break;
            }
        }
        
        
    }
    
    /**
     * returns instance of the scheduler    
     * @return the scheduler
     */
    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * setter for the scheduler
     * @param pScheduler sets the scheduler
     */
    @Override
    public void setScheduler(Scheduler pScheduler) {
        this.scheduler = pScheduler;
    }

    /**
     * get Scheduler State
     * @return the scheduler state
     */
    public SchedulerState getSchedulerState() {
        return schedulerState;
    }

    /**
     * setter for the scheduler state
     * @param pSchedulerState sets the scheduler state
     */
    public void setSchedulerState(SchedulerState pSchedulerState) {
        this.schedulerState = pSchedulerState;
    }

    /**
     * returns jobFactory
     * @return jobFactory
     */
    public SpringBeanJobFactory getJobFactory() {
        return jobFactory;
    }

    /**
     * setter for the jobFactory
     * @param pJobFactory sets the jobFactory
     */
    public void setJobFactory(SpringBeanJobFactory pJobFactory) {
        this.jobFactory = pJobFactory;
    }

    /**
     * gets FdbAddJob
     * @return the fdbAddJob
     */
    public JobDetailBean getFdbAddJob() {
        return fdbAddJob;
    }

    /**
     * sets NationalSettingDomainCapability
     * @param nationalSettingDomainCapability the nationalSettingDomainCapability to set
     */
    public void setNationalSettingDomainCapability(
            NationalSettingDomainCapability nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;
    }

    /**
     * sets FdbAddJob
     * @param fdbAddJob the fdbAddJob to set
     */
    public void setFdbAddJob(JobDetailBean fdbAddJob) {
        this.fdbAddJob = fdbAddJob;
    }

    /**
     * gets FdbAddTrigger
     * @return the fdbAddTrigger
     */
    public CronTriggerBean getFdbAddTrigger() {
        return fdbAddTrigger;
    }

    /**
     * sets FdbAddTrigger
     * @param fdbAddTrigger the fdbAddTrigger to set
     */
    public void setFdbAddTrigger(CronTriggerBean fdbAddTrigger) {
        this.fdbAddTrigger = fdbAddTrigger;
    }

    /**
     * gets FdbUpdateJob
     * @return the fdbUpdateJob
     */
    public JobDetailBean getFdbUpdateJob() {
        return fdbUpdateJob;
    }

    /**
     * sets  FdbUpdateJob
     * @param fdbUpdateJob the fdbUpdateJob to set
     */
    public void setFdbUpdateJob(JobDetailBean fdbUpdateJob) {
        this.fdbUpdateJob = fdbUpdateJob;
    }

    /**
     *  gets StsJob 
     * @return the stsJob
     */
    public JobDetailBean getStsJob() {
        return stsJob;
    }

    /**
     * sets StsJob
     * @param stsJob the stsJob to set
     */
    public void setStsJob(JobDetailBean stsJob) {
        this.stsJob = stsJob;
    }

    /**
     * gets FssJob
     * @return the FssJob
     */
    public JobDetailBean getFssJob() {
        return fssJob;
    }

    /**
     * sets fssJob
     * @param fssJob the fssJob to set
     */
    public void setFssJob(JobDetailBean fssJob) {
        this.fssJob = fssJob;
    }

    /**
     * is Intialized
     * @return the intialized
     */
    public boolean isIntialized() {
        return intialized;
    }

    /**
     * sets Intialized
     * @param intialized the intialized to set
     */
    public void setIntialized(boolean intialized) {
        this.intialized = intialized;
    }

    /**
     * getS InactivationCheckJob
     * @return the inactivationCheckJob
     */
    public JobDetailBean getInactivationCheckJob() {
        return inactivationCheckJob;
    }

    /**
     * setS InactivationCheckJob
     * @param inactivationCheckJob the inactivationCheckJob to set
     */
    public void setInactivationCheckJob(JobDetailBean inactivationCheckJob) {
        this.inactivationCheckJob = inactivationCheckJob;
    }

    /**
     * get TransactionManager
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * set TransactionManager
     * @param transactionManager the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

   

    /**
     * getNdfSynchQueueDomainCapability for FdbSchedulerProcessCapabilityImpl.
     * @return the ndfSynchQueueDomainCapability
     */
    public NdfSynchQueueDomainCapability getNdfSynchQueueDomainCapability() {
        return ndfSynchQueueDomainCapability;
    }

    /**
     * setNdfSynchQueueDomainCapability for FdbSchedulerProcessCapabilityImpl.
     * @param ndfSynchQueueDomainCapability the ndfSynchQueueDomainCapability to set
     */
    public void setNdfSynchQueueDomainCapability(NdfSynchQueueDomainCapability ndfSynchQueueDomainCapability) {
        this.ndfSynchQueueDomainCapability = ndfSynchQueueDomainCapability;
    }

    /**
     * getManagedItemCapabilityFactory for FdbSchedulerProcessCapabilityImpl.
     * @return the managedItemCapabilityFactory
     */
    public ManagedItemCapabilityFactory getManagedItemCapabilityFactory() {
        return managedItemCapabilityFactory;
    }

    /**
     * setManagedItemCapabilityFactory
     * @param managedItemCapabilityFactory the managedItemCapabilityFactory to set
     */
    public void setManagedItemCapabilityFactory(ManagedItemCapabilityFactory managedItemCapabilityFactory) {
        this.managedItemCapabilityFactory = managedItemCapabilityFactory;
    }

    /**
     * getVistaFileSynchCapability
     * @return the vistaFileSynchCapability
     */
    public VistaFileSynchCapability getVistaFileSynchCapability() {
        return vistaFileSynchCapability;
    }

    /**
     * setVistaFileSynchCapability
     * @param vistaFileSynchCapability the vistaFileSynchCapability to set
     */
    public void setVistaFileSynchCapability(VistaFileSynchCapability vistaFileSynchCapability) {
        this.vistaFileSynchCapability = vistaFileSynchCapability;
    }

    /**
     * getManagedItemCapability
     * @return the managedItemCapability
     */
    public ManagedItemCapability getManagedItemCapability() {
        return managedItemCapability;
    }

    /**
     * setManagedItemCapability
     * @param managedItemCapability the managedItemCapability to set
     */
    public void setManagedItemCapability(ManagedItemCapability managedItemCapability) {
        this.managedItemCapability = managedItemCapability;
    }
    

}
