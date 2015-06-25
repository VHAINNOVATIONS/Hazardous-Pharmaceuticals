/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler.tasks;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import gov.va.med.pharmacy.peps.service.common.capability.AutoAddUpdateCapability;
import gov.va.med.pharmacy.peps.service.common.capability.FdbSchedulerProcessCapability;


/**
 * 
 * RunTaskImpl
 */
public class FdbAddTaskImpl implements FdbAddTask {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(FdbAddTaskImpl.class);
    
    @Autowired
    private AutoAddUpdateCapability autoAddUpdateCapability;
    
    @Autowired
    private FdbSchedulerProcessCapability fdbSchedulerProcessCapability;

    
    @Override
    public void runFdbAddProcess() {
        LOG.debug(".....................Synchronize FDB ADD process................ ");
        
        if (fdbSchedulerProcessCapability.getMessageStatus()) {
            LOG.debug(".... running FDB ADD process .........");
            autoAddUpdateCapability.synchronizeFdbAdd();
        }
        
    }
    
    
    /**
     * printMe - remove later  
     *
     */
    public void printMe() {
    }



    /**
     * get AutoAddUpdateCapability
     * @return the autoAddUpdateCapability
     */
    public AutoAddUpdateCapability getAutoAddUpdateCapability() {
        return autoAddUpdateCapability;
    }



    /**
     * set AutoAddUpdateCapability
     * @param autoAddUpdateCapability the autoAddUpdateCapability to set
     */
    public void setAutoAddUpdateCapability(
            AutoAddUpdateCapability autoAddUpdateCapability) {
        this.autoAddUpdateCapability = autoAddUpdateCapability;
    }


    /**
     * getFdbSchedulerProcessCapability
     * @return the fdbSchedulerProcessCapability
     */
    public FdbSchedulerProcessCapability getFdbSchedulerProcessCapability() {
        return fdbSchedulerProcessCapability;
    }


    /**
     * setFdbSchedulerProcessCapability for FdbAddTaskImpl
     * @param fdbSchedulerProcessCapability the fdbSchedulerProcessCapability to set
     */
    public void setFdbSchedulerProcessCapability(
            FdbSchedulerProcessCapability fdbSchedulerProcessCapability) {
        this.fdbSchedulerProcessCapability = fdbSchedulerProcessCapability;
    }



}

