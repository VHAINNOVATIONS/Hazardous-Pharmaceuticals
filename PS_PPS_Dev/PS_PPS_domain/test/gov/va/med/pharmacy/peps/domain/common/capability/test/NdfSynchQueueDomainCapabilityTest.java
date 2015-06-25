/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;



import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.NdfSynchQueueVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NdfSynchQueueDomainCapability;


/**
 * 
 * FdbUpdateDomainCapabilityTest unit tests
 * 
 */
public class NdfSynchQueueDomainCapabilityTest extends DomainCapabilityTestCase {
    
    private static final Logger LOG = Logger.getLogger(NdfSynchQueueDomainCapabilityTest.class);
    private NdfSynchQueueDomainCapability ndfSynchQueueCapability;
    
    
    /**
     * Setup() method
     */
    @Override
    protected void setUp() {
        ndfSynchQueueCapability = getNationalCapability(NdfSynchQueueDomainCapability.class);
    }
    
    
    /**
     * This method gets all the FdbMfg in the db.
     * @throws Exception Exception
     */
    @Test
    public void testCreate() throws Exception {
        
        NdfSynchQueueVo synchVo = new NdfSynchQueueVo();
        synchVo.setIdFk("9995");
        synchVo.setItemType(EntityType.INGREDIENT.toString());
        synchVo.setActionType("ADD");

        NdfSynchQueueVo outVo = ndfSynchQueueCapability.create(synchVo, getTestUser());
        assertNotNull("Vo should not be null. ", outVo.getId());
        
        NdfSynchQueueVo synchVo2 = new NdfSynchQueueVo();
        synchVo2.setIdFk("9996");
        synchVo2.setItemType(EntityType.PRODUCT.toString());
        synchVo2.setActionType("MODIFY");

        NdfSynchQueueVo outVo2 = ndfSynchQueueCapability.create(synchVo2, getTestUser());
        assertNotNull("Vo2 should not be null. ", outVo2.getId());
       
    }
    
   
    /**
     * 
     * test find package type
     *
     */
    @Test 
    public void testFind() {
        
        
        List<NdfSynchQueueVo> list = ndfSynchQueueCapability.retrieve();
        
        for (NdfSynchQueueVo vo : list) {

            LOG.debug("ID: "  + vo.getId());
            LOG.debug("FK: "  + vo.getIdFk());
            LOG.debug("ITEM: "  + vo.getItemType());
            LOG.debug("ACTION: "  + vo.getActionType());
        }
        
        assertTrue("Returned data is ", list.size() > 0);
        
    }
    
   
    
    /**
    * delete all the pending list entries 
    */
    @Test
    public void testDelete() {
        
        try {
            List<NdfSynchQueueVo> list = ndfSynchQueueCapability.retrieve();
            
            for (NdfSynchQueueVo vo : list) {
    
                LOG.debug("ID IS "  + vo.getId());
                ndfSynchQueueCapability.deleteItemById(vo.getId());
            }
            
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
