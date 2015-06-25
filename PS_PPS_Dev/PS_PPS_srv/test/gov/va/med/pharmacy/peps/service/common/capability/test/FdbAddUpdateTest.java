/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;


import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoUpdateVo;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceAutoCapability;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;

import junit.framework.TestCase;


/**
 * Test the base class of DefaultRulesCapability. Also test that a sub class still has access to the super's Spring injected
 * classes.
 */
public class FdbAddUpdateTest extends TestCase {
    private static final Logger LOG = Logger.getLogger(FdbAddUpdateTest.class);
    private DrugReferenceAutoCapability drugReferenceAutoCapability;


    /**
     * Get instance of {@link RulesCapability}
     * 
     * @throws Exception Exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        LOG.info("---------- " + getName() + " ----------");

        this.drugReferenceAutoCapability = SpringTestConfigUtility.getNationalSpringBean(DrugReferenceAutoCapability.class);
    }

    /**
     * testGetAdds
     */
    public void testGetAdds() {

        String startDateString = "Aug 6, 2011 8:14 PM";
        Date startDate = null;

        // Get the default MEDIUM/SHORT DateFormat
        DateFormat format =
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
        
        try {
            startDate = format.parse(startDateString);
            List<FdbAutoAddVo> list = drugReferenceAutoCapability.getFdbAddedItems(startDate);
            LOG.info("FDB Added Items list has  " + list.size() + "  items.");
            int i = 0;
            
            for (FdbAutoAddVo str : list) {
                LOG.debug("Update NDC:  " + str.getNdc());
                
                if (i++ > PPSConstants.I100) {
                    break;
                }
            }
        } catch (Exception e) {
            fail("Should have thrown a ValueObjectValidationException,  not just a ValidationException");
        }
    }
         

    /**
     * testGetUpdates
     */
    public void testGetUpdates() {

        String startDateString = "Aug 5, 2011 8:14 PM";
        Date startDate = null;

        // Get the default MEDIUM/SHORT DateFormat
        DateFormat format =
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
        
        try {
            startDate = format.parse(startDateString);
            List<FdbAutoUpdateVo> list = drugReferenceAutoCapability.getFdbUpdatedItems(startDate);
            LOG.info("FDB Updated Items list has  " + list.size() + " items.");
            int i = 0;
            
            for (FdbAutoUpdateVo str : list) {
                LOG.debug("Update NDC: " + str.getFdbProductName());
                
                if (i++ > PPSConstants.I100) {
                    break;
                }
            }
        } catch (Exception e) {
            fail(" Should have thrown a ValueObjectValidationException,  not just a ValidationException");
        }
    }

    
}

