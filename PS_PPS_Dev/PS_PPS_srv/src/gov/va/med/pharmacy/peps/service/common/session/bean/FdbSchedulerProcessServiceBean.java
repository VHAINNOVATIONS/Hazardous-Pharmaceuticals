/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.bean;


import java.util.Map;

import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.capability.FdbSchedulerProcessCapability;
import gov.va.med.pharmacy.peps.service.common.scheduler.FdbSchedulerControlBean;
import gov.va.med.pharmacy.peps.service.common.scheduler.SchedulerState;
import gov.va.med.pharmacy.peps.service.common.session.FdbSchedulerProcessService;


/**
 * Scheduler Service Bean
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class FdbSchedulerProcessServiceBean extends AbstractPepsStatelessSessionBean<FdbSchedulerProcessService> 
    implements FdbSchedulerProcessService {
    private static final long serialVersionUID = 1L;


    /**
     * executes Job Command
     * @param pSchedulerControl pSchedulerControl
     * @param pRunMode the run mode
     * 
     * @return SchedulerState
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public SchedulerState executeJobCommand(FdbSchedulerControlBean pSchedulerControl, Boolean pRunMode) {
        return this.getService().executeJobCommand(pSchedulerControl, pRunMode);
    }

    
    /**
     * sets FdbSchedulerProcessCapability
     * 
     * @param fdbSchedulerProcessCapability fdbSchedulerProcessCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setFdbSchedulerProcessCapability(
            FdbSchedulerProcessCapability fdbSchedulerProcessCapability) {
        getService().setFdbSchedulerProcessCapability(fdbSchedulerProcessCapability);
        
    }

    /**
     * update HostName
     * 
     * @param hostName hostName
     * @param pUser pUser
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void updateHostName(String hostName, UserVo pUser) {
        this.getService().updateHostName(hostName, pUser);
    }

    /**
     * updates MessagingState
     * 
     * @param messagingState messagingState
     * @param pUser pUser
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void updateMessagingState(Boolean messagingState, UserVo pUser) {
        this.getService().updateMessagingState(messagingState, pUser);
    }


    /**
     * retrieves NationalSettings Map
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * @return Map 
     */
    @Override
    public Map<String, Object> retrieveNationalSettingsMap() {
        return this.getService().retrieveNationalSettingsMap();
    }

}

