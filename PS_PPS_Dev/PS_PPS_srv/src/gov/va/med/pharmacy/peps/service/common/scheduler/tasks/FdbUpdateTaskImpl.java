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
 * FdbUpdateTaskImpl
 */
public class FdbUpdateTaskImpl implements FdbUpdateTask {


    private static final Logger LOG = org.apache.log4j.Logger.getLogger(FdbUpdateTaskImpl.class);

    @Autowired
    private AutoAddUpdateCapability autoAddUpdateCapability;
    
    @Autowired
    private FdbSchedulerProcessCapability fdbSchedulerProcessCapability;
    
    @Override
    public void printMe() {
    }

    @Override
    public void runFdbUpdateProcess() {
        LOG.debug(".....................synchronize FDB UPDATE process................ ~");
        
        if (fdbSchedulerProcessCapability.getMessageStatus()) {
            LOG.debug(".... running runFdbUpdateProcess() process .........");
            autoAddUpdateCapability.synchronizeFdbUpdate();
        }
    }

    
    /**
     * get AutoAddUpdateCapability for FdbUpdateTaskImpl
     * @return the autoAddUpdateCapability
     */
    public AutoAddUpdateCapability getAutoAddUpdateCapability() {
        return autoAddUpdateCapability;
    }



    /**
     * set AutoAddUpdateCapability for FdbUpdateTaskImpl
     * @param autoAddUpdateCapability the autoAddUpdateCapability to set
     */
    public void setAutoAddUpdateCapability(
            AutoAddUpdateCapability autoAddUpdateCapability) {
        this.autoAddUpdateCapability = autoAddUpdateCapability;
    }

    /**
     * getFdbSchedulerProcessCapability for FdbUpdateTaskImpl
     * @return the fdbSchedulerProcessCapability
     */
    public FdbSchedulerProcessCapability getFdbSchedulerProcessCapability() {
        return fdbSchedulerProcessCapability;
    }

    /**
     * setFdbSchedulerProcessCapability for FdbUpdateTaskImpl
     * @param fdbSchedulerProcessCapability the fdbSchedulerProcessCapability to set
     */
    public void setFdbSchedulerProcessCapability(
            FdbSchedulerProcessCapability fdbSchedulerProcessCapability) {
        this.fdbSchedulerProcessCapability = fdbSchedulerProcessCapability;
    }

}
