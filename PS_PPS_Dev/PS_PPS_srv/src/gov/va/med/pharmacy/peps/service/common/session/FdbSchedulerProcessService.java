/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;



import java.util.Map;


import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.capability.FdbSchedulerProcessCapability;
import gov.va.med.pharmacy.peps.service.common.scheduler.FdbSchedulerControlBean;
import gov.va.med.pharmacy.peps.service.common.scheduler.SchedulerState;



/**
 * 
 * SchedulerService's brief summary
 * 
 * Details of SchedulerService's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public interface FdbSchedulerProcessService {

  
    
    /**
     * execute Job commands, PAUSE, RESUME & SCHEDULE
     * @param pSchedulerControl pSchedulerControl
     * @param pRunMode the run mode
     * @return scheduler state
     */
    SchedulerState executeJobCommand(FdbSchedulerControlBean pSchedulerControl, Boolean pRunMode);
    
      
    
    /**
     * set FdbSchedulerProcessCapability
     * @param fdbSchedulerProcessCapability the fdbSchedulerProcessCapability to set
     */
    void setFdbSchedulerProcessCapability(FdbSchedulerProcessCapability fdbSchedulerProcessCapability);


    /**
     * updates Host Name
     *
     * @param hostName hostName
     * @param pUser pUser
     */
    void updateHostName(String hostName, UserVo pUser);


    /**
     * updateMessagingState
     * @param messagingState messagingState
     * @param pUser pUser
     */
    void updateMessagingState(Boolean messagingState, UserVo pUser);
    
    
    /**
     * retrieves NationalSettingsMap
     * @return Map
     */
    Map<String, Object> retrieveNationalSettingsMap();
}
