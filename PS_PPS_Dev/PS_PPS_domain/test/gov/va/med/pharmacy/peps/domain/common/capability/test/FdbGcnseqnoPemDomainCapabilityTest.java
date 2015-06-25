/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;

import gov.va.med.pharmacy.peps.common.vo.DrugMonographGcnseqnoAssocsVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbGcnseqnoPemDomainCapability;;


/**
 * FdbGenericNameDomainCapabilityTest
 *
 */
public class FdbGcnseqnoPemDomainCapabilityTest extends DomainCapabilityTestCase {
    private static final Logger LOG = Logger.getLogger(FdbGcnseqnoPemDomainCapabilityTest.class);
    private static final int NUMBER_ROWS = 120;
    private FdbGcnseqnoPemDomainCapability fdbGcnseqnoPemDomainCapability;

    @Override
    @Before
    public void setUp() throws Exception {
        fdbGcnseqnoPemDomainCapability = getNationalCapability(FdbGcnseqnoPemDomainCapability.class);
    }

    /**
     * testDeleteAll
     */
    public void testDeleteAll() {
        try {
            fdbGcnseqnoPemDomainCapability.deleteAll();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * testCreate in FdbGcenseqnoPemDomainCapabilityTest
     */
    public void testCreate() {
        try {   
            for (Integer x = NUMBER_ROWS; x > 0; x--) {

                DrugMonographGcnseqnoAssocsVo vo = new DrugMonographGcnseqnoAssocsVo();
                vo.setGcnSeqNo(x.longValue());
                vo.setMonographId(x.longValue());
                vo.setCreatedBy(getTestUser().getUsername());
                vo.setCreatedDate(new Date());
                fdbGcnseqnoPemDomainCapability.create(vo, getTestUser());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * testRetreiveAll in FdbGcenseqnoPemDomainCapabilityTest
     */
    public void testRetreiveAll() {
        
        try {   
            List<DrugMonographGcnseqnoAssocsVo> vos = fdbGcnseqnoPemDomainCapability.retrieveAll();
            LOG.debug("Size of all values is " + vos.size());
            for (int x = 0; x < vos.size(); x++) {
                DrugMonographGcnseqnoAssocsVo vo = vos.get(x);
                LOG.debug("VO is " + vo.getMonographId() + ":" + vo.getGcnSeqNo());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    /**
     * testRetreive in FdbGcenseqnoPemDomainCapabilityTest
     */
    public void testRetreives() {
        
        try {   
            List<DrugMonographGcnseqnoAssocsVo> vos = fdbGcnseqnoPemDomainCapability.retrieveAll();
            LOG.debug("Size of all values is " + vos.size());
            for (int x = 0; x < vos.size(); x++) {
                DrugMonographGcnseqnoAssocsVo vo = vos.get(x);
                
                List<DrugMonographGcnseqnoAssocsVo> monVos = 
                    fdbGcnseqnoPemDomainCapability.retrieveByMonograph(vo.getMonographId());
                List<DrugMonographGcnseqnoAssocsVo> gcnVos = 
                    fdbGcnseqnoPemDomainCapability.retrieveByGcnseqno(vo.getGcnSeqNo());
                
                for (int y = 0; y < monVos.size(); y++) {
                    assertEquals("MonographIds should be the same, ",
                        monVos.get(y).getMonographId(), 
                        vo.getMonographId());
                }
                
                for (int y = 0; y < gcnVos.size(); y++) {
                    assertEquals("GcnSeqNos should be the same, ",
                                gcnVos.get(y).getMonographId(), 
                                vo.getMonographId());
                }
                

            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
   public void testDeleteOne() {
        
        try {   
            List<DrugMonographGcnseqnoAssocsVo> vos = fdbGcnseqnoPemDomainCapability.retrieveAll();
            LOG.debug("Size of all values is " + vos.size());
            if (vos.size() > 0 ) {
                DrugMonographGcnseqnoAssocsVo vo = vos.get(1);
                fdbGcnseqnoPemDomainCapability.delete(vo.getMonographId(), vo.getGcnSeqNo());
                
                List<DrugMonographGcnseqnoAssocsVo> monVos = 
                    fdbGcnseqnoPemDomainCapability.retrieveByMonograph(vo.getMonographId());
                
                if (monVos != null && monVos.size() > 0){
                    for (int y = 0; y < monVos.size(); y++) {
                        assertEquals("This item should have been deleted.",
                            monVos.get(y).getGcnSeqNo(), 
                            vo.getGcnSeqNo());
                    }
                }

            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    
}
