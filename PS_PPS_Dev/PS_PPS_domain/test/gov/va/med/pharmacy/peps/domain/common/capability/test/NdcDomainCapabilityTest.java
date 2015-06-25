/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import org.junit.Test;



import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;


/**
 * 
 * NdcDomainCapabilityTest unit tests
 * 
 */
public class NdcDomainCapabilityTest extends DomainCapabilityTestCase {
    
  //  private static final Logger LOG = Logger.getLogger(NdcDomainCapabilityTest.class);
    private NdcDomainCapability ndcDomainCapability;
    
    
    /**
     * Setup() method
     */
    @Override
    protected void setUp() {
        ndcDomainCapability = getNationalCapability(NdcDomainCapability.class);
    }
    
    
    /**
     * This method gets all the FdbMfg in the db.
     * @throws Exception Exception
     */
    @Test
    public void testAutoAddFind() throws Exception {
        
        String[] goodNdc = { "00182014101",  "62794014601",  "00310013110" };
        String[] badNdc = {"54182014101", "62756614601", "00310077710" };

        try {
            for (String ndc : goodNdc) {
                long eplId = ndcDomainCapability.getIdFromNDC(ndc);

                if (eplId == 0) {
                    fail("The good NDCs should return a value!");
                } else {
                    NdcVo ndcVo = ndcDomainCapability.retrieve(String.valueOf(eplId));
                    assertNotNull("ndc " + ndc + " should not be null!", ndcVo);
                }
            }
            
            for (String ndc : badNdc) {
                long eplId = ndcDomainCapability.getIdFromNDC(ndc);

                if (eplId != 0) {
                    fail("The bad NDCs should not a value! " + ndc);
                } 
            }

            
            
        } catch (Exception e) {
            fail("Exeption in testAutoAddFind.  Exception is " + e.getMessage());
        }
        

       
    }
    
   
  
}
