/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.capability.FdbSchedulerProcessCapability;
import gov.va.med.pharmacy.peps.service.common.scheduler.FdbSchedulerControlBean;
import gov.va.med.pharmacy.peps.service.common.scheduler.SchedulerState;
import gov.va.med.pharmacy.peps.service.common.session.FdbSchedulerProcessService;





/**
 * SchedulerServiceImpl
 */
public class FdbSchedulerProcessServiceImpl implements FdbSchedulerProcessService {

    @Autowired
    private FdbSchedulerProcessCapability fdbSchedulerProcessCapability;
    
    /**
     * SchedulerService default constructor
     */
    public FdbSchedulerProcessServiceImpl() {
    }

    /**
     * execute Job Command
     * 
     * @param pSchedulerControl pSchedulerControl
     * @param pRunMode the run mode
     * @return Scheduler state.
     */
    @Override
    public SchedulerState executeJobCommand(FdbSchedulerControlBean pSchedulerControl, Boolean pRunMode) {
        return fdbSchedulerProcessCapability.executeJobCommand(pSchedulerControl, pRunMode);
    }
    

    /**
     * setFdbSchedulerProcessCapability
     * @param pFdbSchedulerProcessCapability the fdbSchedulerProcessCapability to set
     */
    public void setFdbSchedulerProcessCapability(FdbSchedulerProcessCapability pFdbSchedulerProcessCapability) {
        fdbSchedulerProcessCapability = pFdbSchedulerProcessCapability;
    }

    
    /**
     * updates Host Name
     * @param hostName hostName
     * @param pUser user         
     */
    @Override
    public void updateHostName(String hostName, UserVo pUser) {
        fdbSchedulerProcessCapability.updateHostName(hostName, pUser);
    }

    
    /**
     * updateMessagingState
     * @param messagingState messagingState
     * @param pUser pUser
     */
    @Override
    public void updateMessagingState(Boolean messagingState, UserVo pUser) {
        fdbSchedulerProcessCapability.updateMessagingState(messagingState, pUser);
        
    }
    
    
    /**
     * retrieves NationalSettingsMap
     * @return Map
     */
    public Map<String, Object> retrieveNationalSettingsMap() {
        return fdbSchedulerProcessCapability.retrieveNationalSettingsMap();
    }
    
    
}
