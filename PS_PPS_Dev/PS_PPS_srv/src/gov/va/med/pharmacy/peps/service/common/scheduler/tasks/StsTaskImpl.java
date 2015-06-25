/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler.tasks;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import gov.va.med.pharmacy.peps.service.common.capability.FdbSchedulerProcessCapability;
import gov.va.med.pharmacy.peps.service.common.capability.StsCapability;


/**
 * 
 * StsTaskImpl
 */
public class StsTaskImpl implements StsTask {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(StsTaskImpl.class);
    
    @Autowired
    private StsCapability stsCapability;
    
    @Autowired
    private FdbSchedulerProcessCapability fdbSchedulerProcessCapability;

    @Override
    public void runStsProcess() {
        
        LOG.debug(".....................Synchronize STS process................ ~");
        
        if (fdbSchedulerProcessCapability.getMessageStatus()) {
            LOG.debug(".....................Running synchronize STS process................ ~");
            stsCapability.synchronizedFDBUpdate();
        }
    }
    
    
    /**
     * printMe - remove later  
     *
     */
    public void printMe() {
    }


    /**
     * getS Sts Capability
     * @return the stsCapability
     */
    public StsCapability getStsCapability() {
        return stsCapability;
    }


    /**
     * setS the StsCapability
     * @param stsCapability the stsCapability to set
     */
    public void setStsCapability(StsCapability stsCapability) {
        this.stsCapability = stsCapability;
    }


    /**
     * get FdbSchedulerProcessCapability
     * @return the fdbSchedulerProcessCapability
     */
    public FdbSchedulerProcessCapability getFdbSchedulerProcessCapability() {
        return fdbSchedulerProcessCapability;
    }


    /**
     * fdbSchedulerProcessCapability
     * @param fdbSchedulerProcessCapability the fdbSchedulerProcessCapability to set
     */
    public void setFdbSchedulerProcessCapability(
            FdbSchedulerProcessCapability fdbSchedulerProcessCapability) {
        this.fdbSchedulerProcessCapability = fdbSchedulerProcessCapability;
    }
    
    

    
}
