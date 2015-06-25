/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;


/**
 * NDCTradeNameTest
 *
 */


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.common.vo.ReportCaptureNdfVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ReportDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.test.DomainCapabilityTestCase;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;


/**
 * Test the base class of NDCTradeNameTest. 
 */
public class NDCTradeNameTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(NDCTradeNameTest.class);
    private static final int FILE_SIZE = 300;
    
    private ReportDomainCapability reportDomainCapability;
    private ManagedItemCapability managedItemCapability;
    private NdcDomainCapability ndcDomainCapability;
    private ProductDomainCapability productDomainCapability;
     

    /**
     * Get instance of {@link RulesCapability}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

        reportDomainCapability = getNationalCapability(ReportDomainCapability.class);
        managedItemCapability = getNationalCapability(ManagedItemCapability.class);
        productDomainCapability = getNationalCapability(ProductDomainCapability.class);
        ndcDomainCapability = getNationalCapability(NdcDomainCapability.class);
    }
   
    /**
     * clear
     */
    protected void clear() {
        reportDomainCapability = null;
        managedItemCapability = null;
        ndcDomainCapability = null;
        productDomainCapability = null;
    }
    
    /**
     * NDC Test
     * @throws PharmacyException PharmacyException
     */
    public void testGatherTradeName() throws PharmacyException {
        try {
            Map<String, String> map = new HashMap<String, String>();
            List<Long> eplIds = this.reportDomainCapability.getIds(ReportType.NDC_LIST_PRINT_TEMPLATE);
            List<Long> actionList = new ArrayList<Long>(FILE_SIZE);
            int i = 0;
            
            for (Long eplId : eplIds) {
                actionList.add(eplId);
                i++;

                if (actionList.size() >= FILE_SIZE) {
                    LOG.debug("Report Processing: " + i + "of " + eplIds.size());
                    List<ReportCaptureNdfVo> rtList = reportDomainCapability.getCaptureNdfData(actionList);

                    for (ReportCaptureNdfVo vo : rtList) {
                        String key = vo.getNdc2to6() + vo.getNdc7to10() + "^" + vo.getTradeName();
                        String value = vo.getNdc11to12();
                    
                        if (map.containsKey(key)) {
                            StringBuffer updatedValue = new StringBuffer();
                            updatedValue.append(map.get(key)).append(", ").append(value);
                            map.put(key, updatedValue.toString());
                        } else {
                            map.put(key, value);
                        }
                    }
                    
                    actionList.clear();
                }
            }
            
            Iterator it = map.entrySet().iterator(); 
            
            
            // Create file 
            FileWriter fstream = new FileWriter("out.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            
            while (it.hasNext()) { 
                Map.Entry pairs = (Map.Entry) it.next(); 
                out.write(pairs.getKey() + "^" + pairs.getValue() + "/r/n"); 
                it.remove(); // avoids a ConcurrentModificationException
                LOG.debug("Writing " + pairs.getKey());
            } 
            
            //Close the output stream
            out.close();
        } catch (Exception e) {
            LOG.debug("Error: " + e.getMessage());
            fail(e.toString());
        }
            
    }

    
}


