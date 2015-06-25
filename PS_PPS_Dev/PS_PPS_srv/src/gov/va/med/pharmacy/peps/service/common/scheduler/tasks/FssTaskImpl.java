/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler.tasks;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import gov.va.med.pharmacy.peps.service.common.capability.FdbSchedulerProcessCapability;
import gov.va.med.pharmacy.peps.service.common.capability.FssCapability;



/**
 * 
 * FdbFssTaskImpl
 *
 */
public class FssTaskImpl implements FssTask {


    private static final Logger LOG = org.apache.log4j.Logger.getLogger(FssTaskImpl.class);

    @Autowired
    private FssCapability fssCapability;
    
    @Autowired
    private FdbSchedulerProcessCapability fdbSchedulerProcessCapability;
    
    
    @Override
    public void printMe() {
    }

    @Override
    public void runFssProcess() {
        LOG.debug(".....................synchronize FSS process................ ");
        
        if (fdbSchedulerProcessCapability.getMessageStatus()) {
            LOG.debug(".....................Running synchronize FSS process................ ");
            fssCapability.synchronizedFSSUpdate();
        }
    }

    
    /**
     * get getFssCapability
     * @return the fssCapability
     */
    public FssCapability getFssCapability() {
        return fssCapability;
    }



    /**
     * set setFssCapability
     * @param fssCapability the fssCapability to set
     */
    public void setFssCapability(
            FssCapability fssCapability) {
        this.fssCapability = fssCapability;
    }
    
    /**
     * get FdbSchedulerProcessCapability
     * @return the fdbSchedulerProcessCapability
     */
    public FdbSchedulerProcessCapability getFdbSchedulerProcessCapability() {
        return fdbSchedulerProcessCapability;
    }

    /**
     * setFdbSchedulerProcessCapability
     * @param fdbSchedulerProcessCapability the fdbSchedulerProcessCapability to set
     */
    public void setFdbSchedulerProcessCapability(
            FdbSchedulerProcessCapability fdbSchedulerProcessCapability) {
        this.fdbSchedulerProcessCapability = fdbSchedulerProcessCapability;
    }

    
}
